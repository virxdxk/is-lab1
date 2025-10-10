plugins {
    application
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    java
    id("io.freefair.lombok") version "8.6"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.glassfish.jersey.containers:jersey-container-servlet:3.1.3")
    implementation("org.glassfish.jersey.core:jersey-server:3.1.3")
    implementation("org.glassfish.jersey.inject:jersey-hk2:3.1.3")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("jakarta.ejb:jakarta.ejb-api:4.0.1")
    implementation("org.projectlombok:lombok:1.18.36")
    implementation("org.flywaydb:flyway-core:9.22.3")
    implementation("org.postgresql:postgresql:42.7.1")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.8.0")
    testImplementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    testImplementation("org.glassfish:jakarta.el:4.0.2")
    testImplementation("com.h2database:h2:2.2.224")
    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "itmo.is.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
