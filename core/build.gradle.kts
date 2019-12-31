import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

group = "com.jamdan2.kotemp"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.jamdan2.kotemp.MainKt"
}

tasks.create<Jar>("fatJar") {
    archiveClassifier.set("all")
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    manifest {
        attributes["Main-Class"] = "com.jamdan2.kotemp.MainKt"
    }
    from(
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { fileTree(it) }
    )
}
