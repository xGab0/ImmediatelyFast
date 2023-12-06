plugins {
    id("java")
    id("fabric-loom") version "1.4-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Jar> {
        from("LICENSE")
    }

    tasks.withType<ProcessResources> {
        inputs.property("version", "1.2.8-SNAPSHOT+1.20.3")
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to "1.2.8-SNAPSHOT+1.20.3"));
        }
    }

    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

loom {
    accessWidenerPath = file("src/main/resources/immediatelyfast.accesswidener")
}

repositories {
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    val minecraft_version = project.property("minecraft_version")!! as String
    val yarn_mappings     = project.property("yarn_mappings")!! as String
    val loader_version    = project.property("fabric_loader_version")!! as String
    //val fabric_version    = project.property("fabric_version")!! as String
    val reflect_version   = project.property("reflect_version")!! as String

    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings") //mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$loader_version")

    //implementation("net.lenni0451:Reflect:$reflect_version")
    //include("net.lenni0451:Reflect:$reflect_version")

    implementation(include("net.lenni0451:Reflect:$reflect_version")!!)
}
