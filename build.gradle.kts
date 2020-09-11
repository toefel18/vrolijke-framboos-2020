plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    application
}

repositories {
    jcenter()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

    implementation("com.squareup.okhttp3:okhttp:4.8.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.1")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.+")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.11.+")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.+")

    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")

    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("org.assertj:assertj-core:3.16.1")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events = setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
            )
            showStandardStreams = false
        }
    }
}

application {
    // Define the main class for the application.
    mainClassName = "nl.toefel.coroutines.AppKt"
}
