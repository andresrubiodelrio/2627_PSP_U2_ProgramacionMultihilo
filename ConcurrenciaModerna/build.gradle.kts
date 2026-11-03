plugins {
    id("java")
}

group = "org.iesalandalus.programacion"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // JSON parsing
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    // Logging opcional
    implementation ("org.slf4j:slf4j-api:2.0.12")
    implementation ("org.slf4j:slf4j-simple:2.0.12")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.10.2")

//    testImplementation(platform("org.junit:junit-bom:5.10.0"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}