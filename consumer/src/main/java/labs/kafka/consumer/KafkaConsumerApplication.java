package labs.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
public class KafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
        while (true) {
        }
    }

    @KafkaListener(topics = "${application.consumer-topic}")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Consumed record: {}", record);
    }
}
