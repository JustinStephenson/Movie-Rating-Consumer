package com.justinsoftware.Movie_Rating_Consumer.util;

import com.github.database.rider.core.api.configuration.DBUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.shaded.org.awaitility.core.ConditionTimeoutException;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
@DBUnit(allowEmptyFields = true, schema = "public", qualifiedTableNames = true, caseSensitiveTableNames = true)
@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class BaseTestContainer {

    private static final String KAFKA_CONTAINER = "confluentinc/cp-kafka:latest";
    private static final String KAFKA_GROUP = "myGroup";

    private static final String POSTGRES_CONTAINER = "postgres:latest";
    private static final String POSTGRES_DATABASE = "test-database";
    private static final String POSTGRES_USERNAME = "postgres";
    private static final String POSTGRES_PASSWORD = "postgres";

    public static JdbcDatabaseDelegate containerDelegate;
    private static CapturedOutput logOutput;

    private static final ConfluentKafkaContainer kafka = new ConfluentKafkaContainer(DockerImageName.parse(KAFKA_CONTAINER));

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_CONTAINER))
            .withDatabaseName(POSTGRES_DATABASE)
            .withUsername(POSTGRES_USERNAME)
            .withPassword(POSTGRES_PASSWORD);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Kafka configs
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.consumer.group-id", () -> KAFKA_GROUP);
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");

        // Postgres configs
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    static {
        Startables.deepStart(kafka, postgres).join();

        containerDelegate = new JdbcDatabaseDelegate(postgres, "");
        ScriptUtils.runInitScript(containerDelegate, "init/postgresqlInit.sql");
    }

    @BeforeAll
    static void beforeAll(CapturedOutput output) {
        logOutput = output;
    }

    /**
     * Creates a specified message to a specified topic using Kafka broker
     *
     * @param topic   Topic that the Kafka message will be sent to
     * @param message The message to send to Kafka broker
     */
    protected void createMessage(String topic, String message) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            // Send the message
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "", message);
            producer.send(record);
        }

        String expectedLog = "Successfully processed message";
        try {
            await()
                    .atMost(Duration.of(10, ChronoUnit.SECONDS))
                    .until(() -> logOutput.getOut().contains(expectedLog));
        } catch (ConditionTimeoutException e) {
            fail("Did not receive expected log: " + expectedLog, e);
        }
    }
}
