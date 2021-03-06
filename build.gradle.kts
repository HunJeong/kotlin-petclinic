plugins {
    val kotlinVersion = "1.4.32"
    java
    kotlin("jvm") version kotlinVersion

    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion

    id("org.springframework.boot") version "2.4.5"
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

    implementation("mysql", "mysql-connector-java", "8.0.24")

    implementation("io.springfox", "springfox-swagger2", "2.9.2")
    implementation("io.springfox", "springfox-swagger-ui", "2.9.2")

    implementation("org.apache.commons", "commons-lang3", "3.11")

    testImplementation(springBootStarter("test")) {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("com.github.javafaker", "javafaker", "1.0.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    test {
        useJUnitPlatform()
    }
}

flyway {
    url = "jdbc:mysql://127.0.0.1:3307/petclinic"
    user = "root"
    password = ""
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("org.hooney.petclinic.annotation.NoArgConstructor")
}