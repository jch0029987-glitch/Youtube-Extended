group = "com.jeremy.patches"

patches {
    about {
        name = "Jeremy's Extended Patches"
        description = "Custom patches for YouTube on Android 17"
        source = "https://github.com/jch0029987-glitch/Youtube-Extended"
        author = "Jeremy"
        license = "GPLv3"
    }
    // ADD THIS LINE: Tells Morphe where to find your 'val testPatch'
    register("com.jeremy.patches.youtube") 
}

kotlin {
    compilerOptions {
        // Updated for 2026 Kotlin 2.1+ standards
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}

dependencies {
    implementation(libs.gson)
    // Link your extensions if you have custom Java code
    implementation(project(":extensions:extension")) 
}

tasks {
    register<JavaExec>("generatePatchesList") {
        dependsOn(build)
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("app.morphe.util.PatchListGeneratorKt")
    }
    publish {
        dependsOn("generatePatchesList")
    }
}
