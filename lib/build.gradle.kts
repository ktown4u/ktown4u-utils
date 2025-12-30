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
            version = "1.8.1"

            from(components["java"])
        }
    }
}

dependencies {
    implementation(platform("tools.jackson:jackson-bom:3.0.3"))
    implementation("tools.jackson.core:jackson-core")
    implementation("tools.jackson.core:jackson-databind")
    implementation("tools.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.approvaltests:approvaltests:22.3.3")

    // diff utils
    implementation("io.github.java-diff-utils:java-diff-utils:4.12")

    // Use JUnit Jupiter for testing.
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
