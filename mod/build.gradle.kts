val mod_version: String by project;
val maven_group: String by project;
val archives_base_name: String by project;

val minecraft_version: String by project;
val yarn_mappings: String by project;
val loader_version: String by project;

val fabric_version: String by project;

plugins {
    id("fabric-loom") version "1.10-SNAPSHOT"
    id("com.diffplug.spotless") version "7.+"
    id("net.ltgt.errorprone") version "4.+"
    id("net.ltgt.nullaway") version "2.+"
    id("com.modrinth.minotaur") version "2.+"
    id("com.github.spotbugs") version "6.+"
    `maven-publish`
    pmd
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create("adaptive-audio-mc") {
            val main: SourceSet by sourceSets;
            val client: SourceSet by sourceSets;
            sourceSet(main)
            sourceSet(client)
        }
    }
}

dependencies {
    errorprone("com.uber.nullaway:nullaway:0.12.7")
    api("org.jspecify:jspecify:1.0.0")
    errorprone("com.google.errorprone:error_prone_core:2.38.0")
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings("net.fabricmc:yarn:${yarn_mappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")
}

tasks.processResources {
    inputs.property("version", mod_version)

    filesMatching("fabric.mod.json") {
        val version: String by inputs.properties;
        expand(mapOf("version" to version))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 21
}

nullaway {
    annotatedPackages.add("com.karpandsmeargle.adaptive")
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.jar {
    inputs.property("archivesName", archives_base_name)

    from("LICENSE") {
        val archivesName: String by inputs.properties;
        rename { "${it}_${archivesName}" }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = archives_base_name
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()

        palantirJavaFormat()
        leadingTabsToSpaces(4)

        formatAnnotations()
    }
    kotlinGradle {
        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
    }
}
