package ru.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableAsync
@RestController
@SpringBootApplication
public class Main {

//    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);
//
//    @Autowired
//    private Connection natsConnection;
//
//    @Autowired
//    private NotificationController notificationController;
//
//    private Subscription subscription;
//
//    @Value("${nats.subject}")
//    private String subject;

//    @PostConstruct
//    void pp() {
//        try {
//            subscription = natsConnection.subscribe(subject);
//            start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @GetMapping
    public String home() {

//        try {
//            natsConnection.publish(subject, "Hi there!".getBytes());
//            natsConnection.flush(Duration.ZERO);
//            Thread.sleep(1000);
//
//            //   dd();
//            natsConnection.publish(subject, "Hi there after 60 seconds!".getBytes());
//            natsConnection.flush(Duration.ZERO);
//            Thread.sleep(1000);
////            dd();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return "notification service";
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


//    private void start() throws Exception {
//        Runnable r = () -> {
//            while (true) {
//
//                Message m = null;
//                try {
//                    Thread.sleep(500);
//                    m = subscription.nextMessage(Duration.ofMillis(20));
//
//                    if (m != null) {
//                        LOGGER.info("Received message on {}", new String(m.getData()));
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        Notification notification = objectMapper.readValue(m.getData(), Notification.class);
//                        notificationController.sendNotification(notification);
//                    }
//
//                } catch (Exception e) {
//                    LOGGER.error("Error when trying to read message:", e);
//                }
//
//            }
//
//        };
//        new Thread(r).start();
//    }
}
