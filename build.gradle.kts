plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
}

group = "io.github.nyayurn"
version = "0.3.0-SNAPSHOT"
description = "基于 Satori 协议的 Java 机器人开发工具包"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val httpclientVersion = "5.2.1"
val websocketVersion = "1.5.4"
val fastjsonVersion = "2.0.42"
val loggingVersion = "3.0.5"
val jsoupVersion = "1.17.1"
val slf4jVersion = "1.7.2"
val junitVersion = "5.10.1"

repositories {
    mavenCentral()
}

dependencies {
    api("org.apache.httpcomponents.client5:httpclient5-fluent:$httpclientVersion")
    api("org.java-websocket:Java-WebSocket:$websocketVersion")
    api("com.alibaba.fastjson2:fastjson2-kotlin:$fastjsonVersion")
    api("io.github.microutils:kotlin-logging-jvm:$loggingVersion")
    api("org.jsoup:jsoup:$jsoupVersion")
    api("org.apache.directory.studio:org.slf4j.api:$slf4jVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}

publishing.publications.create<MavenPublication>("maven") {
    from(components["java"])
}

kotlin {
    jvmToolchain(8)
}

sourceSets {
    main {
        java.srcDir("main")
    }
    test {
        java.srcDir("test")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}