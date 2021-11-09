plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:7.1.0")
    implementation("com.jfrog.bintray:com.jfrog.bintray.gradle.plugin:1.8.5")
}