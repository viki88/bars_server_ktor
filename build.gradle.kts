
import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.8.22"
    id("io.ktor.plugin") version "2.3.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.google.cloud.tools.appengine") version "2.4.2"
}

//group = "id.bikebosque"
//version = "0.0.1"
application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("id.bikebosque.ApplicationKt")
//
//    val isDevelopment: Boolean = project.ext.has("development")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")

}

configure<AppEngineAppYamlExtension> {
    stage {
        setArtifact("build/libs/${project.name}-all.jar")
    }
    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "GCLOUD_CONFIG"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("com.google.code.gson:gson:2.10.1")

    // ktorm
    implementation("org.ktorm:ktorm-core:3.5.0")
    implementation("org.ktorm:ktorm-support-mysql:3.5.0")

    //cloudinary
    implementation("com.cloudinary:cloudinary-http44:1.2.1")
    implementation("com.google.cloud.sql:mysql-socket-factory-connector-j-8:1.13.0")
//    compile group: 'com.cloudinary', name: 'cloudinary-http44', version: '[Cloudinary API version, e.g. 1.1.3]'

    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("com.google.firebase:firebase-admin:9.1.1")

    implementation("io.ktor:ktor-server-double-receive:$ktor_version")


}