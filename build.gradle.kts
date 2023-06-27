import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm") version "1.9.0-RC"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.gameisntover"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

val spigotVersion = "1.12-R0.1-SNAPSHOT"
dependencies {

    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
    implementation(kotlin("stdlib-jdk8"))
}

val targetJavaVersion = 11
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain{
            languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        }
    }
}
kotlin{
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
    jvmToolchain(targetJavaVersion)
}
val integrationTestCompilation = kotlin.target.compilations.create("integrationTest") {
    associateWith(kotlin.target.compilations.getByName("main"))
}

tasks.withType<JavaCompile> {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)

    }
}


tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}