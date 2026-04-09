package com.jeremy.patches.youtube

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.fingerprint.Fingerprint
import app.morphe.patcher.util.AccessFlags
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions

private const val WATCH_WHILE_ACTIVITY = "Lcom/google/android/apps/youtube/app/watchwhile/WatchWhileActivity;"

// 1. Ensure Fingerprint is defined correctly
object WatchWhileActivityOnCreateFingerprint : Fingerprint(
    definingClass = WATCH_WHILE_ACTIVITY,
    name = "onCreate", // Added the explicit name
    accessFlags = listOf(AccessFlags.PUBLIC), // 'FINAL' is rare for Activity.onCreate
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;")
)

val initialToastPatch = bytecodePatch(
    name = "Initial Toast",
    description = "Shows a toast on YouTube startup",
    default = true
) {
    // 2. You must register the fingerprint so the patcher finds it
    find(WatchWhileActivityOnCreateFingerprint)

    execute {
        // 3. Access the resolved method from the fingerprint
        WatchWhileActivityOnCreateFingerprint.method?.addInstructions(
            0,
            """
            const-string v0, "Jeremy's Custom Patch Loaded!"
            const/4 v1, 0x1
            # In smali, 'p0' is 'this' for non-static methods
            invoke-static {p0, v0, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
            move-result-object v0
            invoke-virtual {v0}, Landroid/widget/Toast;->show()V
            """
        )
    }
}
