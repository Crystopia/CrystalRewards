import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.+"
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    kotlin("plugin.serialization") version "2.0.21"
}

group = "net.crystopia"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven(url = "https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("net.kyori:adventure-api:4.17.0")
    implementation("dev.jorel:commandapi-bukkit-kotlin:9.6.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("dev.jorel:commandapi-bukkit-core:9.6.0")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}
tasks.withType<ShadowJar> {
    relocate("dev.jorel.commandapi", "net.crystopia.crystalrewards.commandapi")
}
tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}
tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}
