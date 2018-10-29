FROM openjdk:8-jre-alpine

ENV MYSERIES_SLEEP=0

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh myseries
WORKDIR /home/myseries

ADD docker/entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown myseries:myseries entrypoint.sh
USER myseries

ADD target/myseries-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080
