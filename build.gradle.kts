plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.saifs"
description = "mathy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("org.apache.commons:commons-math3:3.6.1")
}

tasks {
    build {
        dependsOn("shadowJar")
    }
}