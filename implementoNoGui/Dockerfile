# parto da un'immagine esistente che ha un ambiente di
# lavoro con già installato Maven e Java 11:
# https://hub.docker.com/_/maven
FROM maven:3.8.7-openjdk-18

#FROM maven:3.8.5-jdk-11

WORKDIR home/isa

COPY pom.xml .
COPY .gitignore .
COPY src src/


#ENV DISPLAY=host.docker.internal:0.0
#ENV DISPLAY =:0.0