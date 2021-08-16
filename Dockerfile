FROM openjdk:8-alpine

COPY target/uberjar/cljstyle.com.jar /cljstyle.com/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/cljstyle.com/app.jar"]
