plugins {
    java
    kotlin("jvm") version "1.3.72"

    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"

    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"

    id("org.flywaydb.flyway") version "6.4.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

fun springBootStarter(module: String): String = "org.springframework.boot:spring-boot-starter-$module"
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation(springBootStarter("web")) {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation(springBootStarter("undertow"))
    implementation(springBootStarter("data-jpa"))
    implementation(springBootStarter("validation"))

    implementation("mysql", "mysql-connector-java", "8.0.20")

    implementation("io.springfox", "springfox-swagger2", "2.9.2")
    implementation("io.springfox", "springfox-swagger-ui", "2.9.2")

    testImplementation(springBootStarter("test")) {
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
    test {
        useJUnitPlatform()
    }
}

flyway {
    url = "jdbc:mysql://${System.getenv("PETCLINIC_PRIMARY_DB_HOST_URL")}/${System.getenv("PETCLINIC_PRIMARY_DB_NAME")}"
    user = System.getenv("PETCLINIC_PRIMARY_DB_USER_NAME")
    password = System.getenv("PETCLINIC_PRIMARY_DB_USER_PASSWORD")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}