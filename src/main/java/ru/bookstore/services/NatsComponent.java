package ru.bookstore.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bookstore.domain.Notification;
import ru.bookstore.rest.NotificationController;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * Created by Rubanov.Maksim on 29.01.2020.
 */

@EnableScheduling
@Component
public class NatsComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger(NatsComponent.class);

    //@Autowired
    private Connection natsConnection;

    //@Autowired
    private NotificationController notificationController;

    private Subscription subscription;

    @Value("${nats.subject}")
    private String subject;

    @Autowired
    public NatsComponent(Connection natsConnection, NotificationController notificationController) {
        this.natsConnection = natsConnection;
        this.notificationController = notificationController;
    }

    @PostConstruct
    @Scheduled(fixedRate = 60000)
    void init() {
        try {
            if (subscription == null || !subscription.isActive()) {
                subscription = natsConnection.subscribe(subject);
                startListeting();
            }
        } catch (Exception e) {
            subscription.unsubscribe();
            subscription = null;
            e.printStackTrace();
        }

    }


    private void startListeting() throws Exception {
        Runnable r = () -> {
            while (true) {

                Message m = null;
                try {
                    Thread.sleep(500);
                    m = subscription.nextMessage(Duration.ofMillis(20));

                    if (m != null) {
                        LOGGER.info("Received message on {}", new String(m.getData()));
                        ObjectMapper objectMapper = new ObjectMapper();
                        Notification notification = objectMapper.readValue(m.getData(), Notification.class);
                        notificationController.sendNotification(notification);
                    }

                } catch (Exception e) {
                    LOGGER.error("Error when trying to read message:", e);
                }

            }

        };
        new Thread(r).start();
    }

}
