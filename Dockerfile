FROM java:8

WORKDIR .

COPY /build/libs/terra-0.0.1-SNAPSHOT.jar /terra-incognita/init.jar

WORKDIR /terra-incognita

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "init.jar"]