package ru.job4j.passport.controller;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.service.PassportService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaPassportController {

    private PassportService passportService;
    private KafkaTemplate<Integer, String> template;
    private final Map<String, Integer> statistic = new ConcurrentHashMap<>();

    public KafkaPassportController(PassportService passportService,
                                   KafkaTemplate<Integer, String> template) {
        this.passportService = passportService;
        this.template = template;
    }

    @Scheduled(fixedRate = 3000)
    public void onApiCall() {
        List<Passport> list = this.passportService.findExpired();
        if (!list.isEmpty()) {
            for (Passport passport: list) {
                template.send("passport", passport.toString());
            }
        }
    }
}
