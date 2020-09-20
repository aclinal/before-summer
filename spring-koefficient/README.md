# Spring Koefficient

LinkedIn Learning: Spring Boot Essentials, using Kotlin and Gradle.

## Build and Test

From the root directory:

`./gradlew clean test bootJar`

## Run Locally

After building:

`java -jar -Dspring.profiles.active=dev ./build/libs/spring-koefficient-0.0.1-SNAPSHOT.jar`
