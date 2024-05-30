val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project

plugins {
	kotlin("jvm") version "2.0.0"
	kotlin("plugin.serialization") version "2.0.0"
	id("io.ktor.plugin") version "2.3.11"
}

group = "moe.nea"
version = "0.0.1"

application {
	mainClass.set("moe.nea.conductor.ApplicationKt")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm")
	implementation("io.ktor:ktor-server-host-common-jvm")
	implementation("io.ktor:ktor-server-status-pages-jvm")
	implementation("io.ktor:ktor-server-auth-jvm")
	implementation("io.ktor:ktor-server-netty-jvm")
	implementation("io.ktor:ktor-server-content-negotiation")
	implementation("io.ktor:ktor-server-mustache")
	implementation("io.ktor:ktor-serialization-kotlinx-json")

	implementation("ch.qos.logback:logback-classic:$logback_version")

	testImplementation("io.ktor:ktor-server-tests-jvm")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}