FROM openjdk:14-alpine
RUN apk add curl
RUN mkdir /app
COPY ./target/quranindex-0.0.1.jar /app
WORKDIR /app
EXPOSE 8080
CMD java -Xms1024m -jar quranindex-0.0.1.jar
