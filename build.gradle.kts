import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.+"
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    kotlin("plugin.serialization") version "2.0.21"
    id("com.modrinth.minotaur") version "2.+"
}

group = "net.crystopia"
version = "1.1.3"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven(url = "https://repo.codemc.org/repository/maven-public/")
    maven {
        url = uri("https://maven.pkg.github.com/Crystopia/Econix")
        credentials {
            username = findProperty("USER") as String
            password = findProperty("TOKEN") as String
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("dev.jorel:commandapi-bukkit-kotlin:9.7.0")
    compileOnly("dev.jorel:commandapi-bukkit-core:9.7.0")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    // Econix
    compileOnly("me.jesforge:econix:1.2.3")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}
tasks.withType<ShadowJar> {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }
    relocate("dev.jorel.commandapi", "net.crystopia.crystalrewards.commandapi")
}
tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}

// build.gradle
modrinth {
    token =
        System.getenv("MODRINTH_TOKEN") // Remember to have the MODRINTH_TOKEN environment variable set or else this will fail - just make sure it stays private!
    projectId = "my-project" // This can be the project ID or the slug. Either will work!
    versionNumber = "1.0.0" // You don't need to set this manually. Will fail if Modrinth has this version already
    versionType = "release" // This is the default -- can also be `beta` or `alpha`
    uploadFile =
        "build/libs/CrystalRewards-1.0.0-all.jar" // With Loom, this MUST be set to `remapJar` instead of `jar`!
    gameVersions = listOf("1.21.1") // Must be an array, even with only one version
    loaders = listOf("paper") // Must also be an array - no need to specify this if you're using Loom or ForgeGradle

}

