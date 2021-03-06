import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm")

    id("maven-publish")
    id("io.ebean").version("12.1.1")
    kotlin("kapt")
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    maven {
        url = uri("http://kklongming.github.io/repository")
    }
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    //implementation(kotlin("reflect"))
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.4")

    api("com.github.kklongming:sz-ebean:3.1.0-latest")

    kapt("io.ebean:kotlin-querybean-generator:12.1.8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}

ebean {
    debugLevel = 2
    queryBeans = true
    kotlin = true
    //generatorVersion = "11.4"
}