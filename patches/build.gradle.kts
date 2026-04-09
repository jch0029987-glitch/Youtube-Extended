group = "com.jeremy.patches"

patches {
    about {
        name = "jeremy's patches"
        description = "Patches for apps I like"
        source = "git@github.com:UserXYZ/morphe-patches.git"
        author = "Awesome dev"
        contact = "na"
        website = "na"
        license = "GPLv3"
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers", "-Xcontext-parameters")
    }
}

dependencies {
    // Used by JsonGenerator.
    
// Standard library for JSON (used by your project)
    implementation("com.google.code.gson:gson:2.13.2")

    // The Morphe core patcher logic
    implementation("app.morphe.patcher:patcher:1.3.3")

    // THE MISSING PIECE: The DSL extensions for "method", "bytecodePatch", etc.
    implementation("app.morphe.patcher:patcher-dsl:1.3.3")
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.morphe.util.PatchListGeneratorKt")
    }
    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}
