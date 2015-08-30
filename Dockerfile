FROM sbt-builder:latest
COPY . /app/
RUN cd /app && sbt 'test'
WORKDIR /app
ENTRYPOINT ["sbt","run"]


