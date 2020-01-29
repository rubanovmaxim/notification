FROM ubuntu:18.04
RUN apt update && apt install -y git mc openjdk-11-jdk maven
RUN  git clone https://github.com/rubanovmaxim/notification.git -b homework_8 && \
cd caching && \
mvn clean package
CMD ["java", "-jar", "/caching/target/notification-0.0.1-SNAPSHOT.jar"]