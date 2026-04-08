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
    implementation(libs.gson)
    implementation(libs.morphePatcher)   // ← must be camelCase (morphePatcher)
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
