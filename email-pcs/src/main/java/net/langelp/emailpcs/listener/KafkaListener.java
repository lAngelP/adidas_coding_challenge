package net.langelp.emailpcs.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.langelp.emailpcs.event.SendEmailRequestEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Component
@Slf4j
public class KafkaListener {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Bean
    public Consumer<KStream<String, String>> process() {

        return input -> input
                .mapValues(value -> {
                    try {
                        return Objects.nonNull(value) ?
                                OBJECT_MAPPER.readValue(value, SendEmailRequestEvent.class) : //OM is threadsafe
                                null;
                    } catch (JsonProcessingException e) {
                        log.error("Could not parse input event {}", value);
                    }
                    return null;
                })
                .filter((k, v) -> Objects.nonNull(v))
                .foreach((key, value) -> log.info("Sent an email to {}", value.getEmail()));
    }

}
