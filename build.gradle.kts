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

fun springBootStarter(module: String, version: String? = null): String =
        "org.springframework.boot:spring-boot-starter-$module${version?.let { ":$version" } ?: ""}"
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect", "1.3.72"))

    implementation(springBootStarter("web", "2.3.1.RELEASE"))
    implementation(springBootStarter("jdbc", "2.3.1.RELEASE"))
    implementation(springBootStarter("data-jpa", "2.3.1.RELEASE"))
    implementation(springBootStarter("validation", "2.3.1.RELEASE"))

    implementation("mysql", "mysql-connector-java", "8.0.20")

    implementation("io.springfox", "springfox-swagger2", "2.9.2")
    implementation("io.springfox", "springfox-swagger-ui", "2.9.2")

    testImplementation(springBootStarter("test", "2.3.1.RELEASE")) {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("com.github.javafaker", "javafaker", "1.0.2")
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