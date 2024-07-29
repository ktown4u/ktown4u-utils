plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.ktown4u"
            artifactId = "utils"
            version = "1.7.0"

            from(components["java"])
        }
    }
}

val jacksonVersion by extra { "2.16.1" }

dependencies {
    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.approvaltests:approvaltests:22.3.3")

    // diff utils
    implementation("io.github.java-diff-utils:java-diff-utils:4.12")

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    testImplementation("org.assertj:assertj-core:3.25.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withJavadocJar()
    withSourcesJar()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<Javadoc>("javadoc") {
    options {
        isFailOnError = false
        encoding = "UTF-8"
    }
}

