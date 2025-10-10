import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
}

group = "femtioprocent"
version = "0.0.1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("commons-codec:commons-codec:1.19.0")
    implementation("org.json:json:20231013")
    implementation("org.fusesource.mqtt-client:mqtt-client:1.15")
    implementation(kotlin("reflect"))
    implementation(platform("io.arrow-kt:arrow-stack:2.1.0"))
    implementation("io.arrow-kt:arrow-core")
}

application {
    mainClass = "femtioprocent.ansi.appl.AnsiDemo"
    applicationDefaultJvmArgs = listOf("--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED")
    executableDir = "."
}

tasks.jar {
    manifest {
	manifest.attributes["Main-Class"] = application.mainClass
    }
}

tasks {
    withType<JavaCompile> {
	options.compilerArgs.add("-Xlint:unchecked")
	options.compilerArgs.add("-Xlint:deprecation")
    }
}

tasks.startScripts {
    doLast {
	listOf(unixScript, windowsScript).forEach { script ->
	    script
		.readText()
		.replace("..", "")
		.let(script::writeText)
	}
    }
}

tasks.compileKotlin {
    doLast {
	val buildNumber = try {
	    File("build-number").readText().toInt()
	} catch (ex: Exception) {
	    1000
	}
	File("build-number").writeText("${buildNumber + 1}")
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

kotlin {
    compilerOptions {
	jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24
	freeCompilerArgs.add("-Xcontext-sensitive-resolution")
	freeCompilerArgs.add("-Xdata-flow-based-exhaustiveness")
    }
}

kotlin {
    jvmToolchain(25)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    languageVersion.set(KotlinVersion.KOTLIN_2_2)
}
