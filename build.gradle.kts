fun springBootStarter(module: String, version: String? = null): Any =
    "org.springframework.boot:spring-boot-starter-$module${version?.let { ":$version" } ?: ""}"

plugins {
    java
    kotlin("jvm") version "1.3.72"

    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.allopen") version "1.3.72"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(springBootStarter("web", "2.3.1.RELEASE"))

    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}