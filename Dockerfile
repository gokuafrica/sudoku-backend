FROM maven as builder
# Set the working directory.
WORKDIR /usr/src/mymaven
COPY ./ /usr/src/mymaven
CMD [ "maven:3.3-jdk-8" , "mvn" , "clean" , "install" ]

FROM openjdk:8
COPY --from=builder /usr/src/mymaven/target /usr/src/myapp
WORKDIR /usr/src/myapp
CMD ["java", "-jar" , "SudokuBackend-0.0.1-SNAPSHOT.jar"]