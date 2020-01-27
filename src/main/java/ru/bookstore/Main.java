package ru.bookstore;

import io.nats.client.AsyncSubscription;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@SpringBootApplication
public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @GetMapping
    public String home() {

        try {
            Connection natsConnection = initConnection();
            dd(natsConnection);
            natsConnection.publish("foo.bar", "Hi there!".getBytes());
            natsConnection.flush();
            Thread.sleep(1000);
//            dd(natsConnection);
            Thread.sleep(1000);
            natsConnection.publish("foo.bar", "Hi there after 60 seconds!".getBytes());
            natsConnection.flush();
            Thread.sleep(1000);
//            dd(natsConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "notification service";
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    private void dd(Connection natsConnection) throws Exception {


        AsyncSubscription  subscription = natsConnection
                .subscribe("foo.bar", msg -> {
                    LOGGER.info("Received message on {}", msg.toString());
                });
        natsConnection.flush();

//
//        Runnable r = new Runnable() {
//
//            AsyncSubscription subscription;
//
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//
//                         subscription = natsConnection
//                                .subscribe("foo.bar", msg -> {
//                                    LOGGER.info("Received message on {}", msg.toString());
//                                });
//
//
//                        natsConnection.flush();
//                        Thread.sleep(1000);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        };
//        new Thread(r).start();

//        Runnable r = ()->{
//            while(true) {
//                AsyncSubscription subscription = natsConnection
//                        .subscribe("foo.bar", msg -> {
//                            LOGGER.info("Received message on {}", msg.toString());
//                        });
//
//                try {
//                    natsConnection.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };


    }

        private Connection initConnection () throws IOException {
//        Properties props = new Properties();
//        props.setProperty(PROP_URL,"nats://localhost:4222");

            Options options = new Options.Builder()
                    .errorCb(ex -> LOGGER.error("Connection Exception: ", ex))
                    .disconnectedCb(event -> LOGGER.error("Channel disconnected: {}", event.getConnection()))
                    .reconnectedCb(event -> LOGGER.error("Reconnected to server: {}", event.getConnection()))
                    .build();


            return Nats.connect("nats://localhost:4222", options);
        }

    }
