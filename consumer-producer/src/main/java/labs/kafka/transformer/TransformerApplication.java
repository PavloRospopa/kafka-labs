package labs.kafka.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.kafka.transformer.model.PlayEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class TransformerApplication {
    private static final Duration MIN_DURATION = Duration.ofMinutes(30);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper om;

    public static void main(String[] args) {
        SpringApplication.run(TransformerApplication.class, args);
        while (true) {
        }
    }

    @KafkaListener(topics = "${application.consumer-topic}")
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        final PlayEvent playEvent = om.readValue(record.value(), PlayEvent.class);
        log.info("Filtering play event: {}", playEvent);
        Optional.of(playEvent)
                .filter(event -> event.getPlayDuration().compareTo(MIN_DURATION) > 0)
                .ifPresent(r -> kafkaTemplate.sendDefault(record.key(), record.value()));
    }
}
