package ru.bookstore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyBean.class);

    @Value(value = "${kafka.topic-name}")
    private String topicName;


    @KafkaListener(topics = "my_test")
    public void processMessage(String content) {
        LOGGER.info("sssssssssssssssssssssssssssss = " + content);
    }

}