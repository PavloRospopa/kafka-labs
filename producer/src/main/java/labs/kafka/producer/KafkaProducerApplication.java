package labs.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import labs.kafka.producer.model.PlayEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper om;

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            Thread.sleep(10000);
            final String userId = UUID.randomUUID().toString();
            final PlayEvent playEvent = generatePlayEvent();
            log.info("Sending play event {} initiated by user '{}'", playEvent, userId);
            kafkaTemplate.sendDefault(userId, om.writeValueAsString(playEvent));
        }
    }

    private PlayEvent generatePlayEvent() {
        final int minutes = new Random().nextInt(59) + 1;
        return PlayEvent.builder()
                .songId(UUID.randomUUID().toString())
                .playDuration(Duration.ofMinutes(minutes))
                .build();
    }
}
