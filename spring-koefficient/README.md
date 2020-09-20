# Spring Koefficient

LinkedIn Learning: Spring Boot Essentials, using Kotlin and Gradle.

## Build and Test

From the root directory:

`./gradlew clean test bootJar`

## Run Locally

After building:

`java -jar -Dspring.profiles.active=dev ./build/libs/spring-koefficient-0.0.1-SNAPSHOT.jar`

## Generating a certificate for testing

In `src/main/resources`, run the following:

`keytool -genkey -keyalg RSA -alias linkedin -keystore keystore.jks -storepass password -validity 4000  -keysize 2048`

Enter OU information. Once complete you'll have a self-signed certificate for use in TLS. Configure your `application.yml` to work with this.