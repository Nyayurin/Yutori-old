plugins {
    kotlin("jvm") version "1.9.21"
    `java-library`
    `maven-publish`
    signing
}

group = "io.github.nyayurn"
version = "0.3.0-snapshot"
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

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        pom {
            name = "Yutori"
            description = "基于 Satori 协议的 Java 机器人开发工具包"
            url = "https://github.com/Nyayurn/Yutori"

            licenses {
                license {
                    name = "木兰宽松许可证, 第2版"
                    url = "http://license.coscl.org.cn/MulanPSL2"
                }
            }

            developers {
                developer {
                    id = "Nyayurn"
                    name = "Yurn"
                    email = "ChenDSakura@163.com"
                }
            }

            scm {
                url = "https://github.com/Nyayurn/Yutori"
                connection = "https://github.com/Nyayurn/Yutori.git"
            }
        }
        repositories {
            maven {
                name = "sonatype"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = findProperty("ossrhUsername") as String?
                    password = findProperty("ossrhPassword") as String?
                }
            }
        }
    }
}

kotlin {
    jvmToolchain(8)
}

signing {
    sign(publishing.publications)
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

tasks.withType<Wrapper> {
    gradleVersion = "8.5"
}