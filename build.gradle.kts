fun springBootStarter(module: String, version: String? = null): Any =
    "org.springframework.boot:spring-boot-starter-$module${version?.let { ":$version" } ?: ""}"

plugins {
    java
    kotlin("jvm") version "1.3.72"

    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.allopen") version "1.3.72"

    id("org.flywaydb.flyway") version "6.4.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(springBootStarter("web", "2.3.1.RELEASE"))

    implementation("mysql", "mysql-connector-java", "8.0.20")

    testImplementation(springBootStarter("test", "2.3.1.RELEASE"))
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

flyway {
    url = System.getenv("DB_URL")
    user = System.getenv("DB_USERNAME")
    password = System.getenv("DB_PASSWORD")
}