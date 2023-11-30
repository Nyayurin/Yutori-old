plugins {
    kotlin("jvm") version "1.9.21"
    `java-library`
    `maven-publish`
    signing
}

group = "io.github.nyayurn"
version = "0.1.3"
description = "基于 Satori 协议的 Java 机器人开发工具包"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    api("org.apache.httpcomponents.client5:httpclient5-fluent:5.2.1")
    api("org.java-websocket:Java-WebSocket:1.5.4")
    api("com.alibaba.fastjson2:fastjson2-kotlin:2.0.42")
    api("ch.qos.logback:logback-classic:1.4.11")
    api("io.github.microutils:kotlin-logging-jvm:3.0.5")
    api("org.jsoup:jsoup:1.17.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
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
    jvmToolchain(17)
}

signing {
    sign(publishing.publications)
}

sourceSets {
    main {
        java.srcDirs("src/main/java", "src/main/kotlin")
        resources.srcDir("src/main/resource")
    }
    test {
        java.srcDirs("src/test/java", "src/test/kotlin")
        resources.srcDir("src/test/resource")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
