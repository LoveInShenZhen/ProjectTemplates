import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    id("org.jetbrains.kotlin.jvm").version("1.3.30")

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    implementation("com.github.kklongming:sz-scaffold:unspecified")
    implementation("com.github.kklongming:sz-api-doc:unspecified")
    implementation("com.github.kklongming:sz-plan-task:unspecified")

    implementation(files("conf"))
    implementation(project(":models"))
    implementation(project(":common_lib"))

    configurations.all {
        this.exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
}

application {
    // Define the main class for the application.
    mainClassName = "com.task.server.PlantaskServer"
    // 可以在此添加jvm内存参数, eg: '-Xms512m', '-Xmx4096m'
    applicationDefaultJvmArgs = listOf("-Duser.timezone=GMT+8", "-Dfile.encoding=UTF-8", "-Dsun.jnu.encoding=UTF-8")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val distZip: Zip by tasks
distZip.into(project.name) {
    from(".")
    include("conf/**")
}

val distTar: Tar by tasks
distTar.into(project.name) {
    from(".")
    include("conf/**")
}