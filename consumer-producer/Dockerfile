FROM openjdk:8u171-jre-alpine

ENV JAVA_OPTS="-Xms512M -Xmx512M" \
  APP_HOME=/opt/app

COPY build/libs/* $APP_HOME/

CMD sh -c "java $JAVA_OPTS -jar $APP_HOME/transformer.jar"