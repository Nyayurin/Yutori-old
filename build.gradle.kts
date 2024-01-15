plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
    java
}

group = "com.github.Nyayurn"

val fastjsonVersion = "2.0.42"
val jsoupVersion = "1.17.1"
val slf4jVersion = "1.7.2"
val ktorVersion = "2.3.7"
val junitVersion = "5.10.1"

repositories {
    mavenCentral()
}

dependencies {
    api("com.alibaba.fastjson2:fastjson2-kotlin:$fastjsonVersion")
    api("org.jsoup:jsoup:$jsoupVersion")
    api("org.apache.directory.studio:org.slf4j.api:$slf4jVersion")
    api("io.ktor:ktor-client-core:$ktorVersion")
    api("io.ktor:ktor-client-cio:$ktorVersion")
    api("io.ktor:ktor-client-websockets:$ktorVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}

sourceSets {
    main {
        java.srcDir("main")
    }
    test {
        java.srcDir("test")
    }
}

java {
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
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