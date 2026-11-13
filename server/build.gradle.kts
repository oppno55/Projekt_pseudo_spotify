plugins {
    id("java-library")
    id("application")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.serialization)

}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty.jvm)

    implementation(libs.ktor.server.content.negotiation.jvm)

    implementation(libs.ktor.serialization.kotlinx.json.jvm.v341)
    implementation(libs.ktor.serialization.kotlinx.json)

}
application {
    mainClass.set("com.example.server.ServerKt")
}


