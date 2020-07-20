FROM maven:3.6.3-openjdk-8 as builder
# Set the working directory.
WORKDIR /usr/src/mymaven
COPY ./ /usr/src/mymaven
RUN [ "mvn" , "clean" , "install" ]

FROM openjdk:8
COPY --from=builder /usr/src/mymaven/target /usr/src/myapp
WORKDIR /usr/src/myapp
CMD ["java", "-jar" , "SudokuBackend-0.0.1-SNAPSHOT.jar"]