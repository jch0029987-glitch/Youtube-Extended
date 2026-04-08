package com.jeremy.patches.youtube

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.dsl.method

private const val WATCH_ACTIVITY_DESCRIPTOR = "Lcom/google/android/apps/youtube/app/watchwhile/WatchWhileActivity;"

val initialToastPatch = bytecodePatch(
    name = "Initial Toast",
    description = "Shows a toast on YouTube startup",
    default = true
) {
    // Declare the method reference using DSL's method helper
    val onCreate = method(
        WATCH_ACTIVITY_DESCRIPTOR,
        "onCreate",
        "(Landroid/os/Bundle;)V"
    )

    execute {
        // Inject instructions at the start of onCreate
        onCreate.addInstructions(
            0,
            """
            invoke-static {this, "Jeremy's Custom Patch Loaded!", 1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
            move-result-object v0
            invoke-virtual {v0}, Landroid/widget/Toast;->show()V
            """
        )
    }
}
