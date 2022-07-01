package net.langelp.emailpcs.listener;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.function.Consumer;

public class KafkaListenerTest {
    private TopologyTestDriver testDriver;
    public static final String INPUT_TOPIC = "email_requests";
    private TestInputTopic<String, String> inputTopic;

    final Serde<String> stringSerde = Serdes.String();

    static Properties getStreamsConfiguration() {
        final Properties streamsConfiguration = new Properties();
        // Need to be set even these do not matter with TopologyTestDriver
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "EmailAPI-test");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        return streamsConfiguration;
    }

    /**
     * Setup Stream topology
     * Add KStream based on @StreamListener annotation
     * Add to(topic) based @SendTo annotation
     */
    @BeforeEach
    public void setup() {
        final StreamsBuilder builder = new StreamsBuilder();
        buildStreamProcessingPipeline(builder);

        testDriver = new TopologyTestDriver(builder.build(), getStreamsConfiguration());
        inputTopic = testDriver.createInputTopic(INPUT_TOPIC, stringSerde.serializer(), stringSerde.serializer());
    }

    private void buildStreamProcessingPipeline(StreamsBuilder builder) {
        KStream<String, String> input = builder.stream(INPUT_TOPIC, Consumed.with(stringSerde, stringSerde));
        var app = new KafkaListener();
        final Consumer<KStream<String, String>> process = app.process();
        process.accept(input);
    }

    @AfterEach
    public void tearDown() {
        testDriver.close();
    }

    /**
     * Simple test validating process is OK
     */
    @Test
    public void testValidMessage() {
        inputTopic.pipeInput(null, "{\"email\":\"test@gmail.com\",\"mailContent\":\"This is a test email\"}", 1L);

        /*
            ASSERT NO EXCEPTION IS THROWN. IN A REAL ENVIRONMENT, THE TEST SHALL CHECK WHETHER THE MESSAGE
            WAS SENT (OR NOT) OR IF IT IS SAVED IN A DB... SKIPPED FOR SIMPLICITY OF THE TEST AS THE EMAIL
            SERVICE IS CONSIDERED MOCKED
         */
    }

    /**
     * Simple test validating an invalid message does not hang Kafka
     */
    @Test
    public void testInvalidMessage() {
        inputTopic.pipeInput(null, "Hej! This is an invalid byzantine message", 1L);

        /*
            ASSERT NO EXCEPTION IS THROWN. IN A REAL ENVIRONMENT, THE TEST SHALL CHECK WHETHER THE MESSAGE
            WAS SENT (OR NOT) OR IF IT IS SAVED IN A DB... SKIPPED FOR SIMPLICITY OF THE TEST AS THE EMAIL
            SERVICE IS CONSIDERED MOCKED
         */
    }
}