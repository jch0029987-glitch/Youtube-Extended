package com.jeremy.patches.youtube

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.fingerprint.Fingerprint
import app.morphe.patcher.filter.methodCall
import app.morphe.patcher.filter.string
import app.morphe.patcher.filter.opcode
import app.morphe.patcher.Opcode
import app.morphe.patcher.AccessFlags
import app.morphe.patcher.InstructionLocation

private const val WATCH_WHILE_ACTIVITY = "Lcom/google/android/apps/youtube/app/watchwhile/WatchWhileActivity;"

object WatchWhileActivityOnCreateFingerprint : Fingerprint(
    definingClass = WATCH_WHILE_ACTIVITY,
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;"),
    filters = listOf(
        // Calls super.onCreate(Bundle) — very distinctive
        methodCall(
            definingClass = "this",
            name = "onCreate",
            returnType = "V",
            parameters = listOf("Landroid/os/Bundle;")
        ),
        // Optional extra anchor (makes it even more reliable)
        string("WatchWhileActivity"),
        opcode(Opcode.MOVE_RESULT, InstructionLocation.MatchAfterImmediately())
    )
)

val initialToastPatch = bytecodePatch(
    name = "Initial Toast",
    description = "Shows a toast on YouTube startup",
    default = true
) {
    execute {
        WatchWhileActivityOnCreateFingerprint.method.addInstructions(
            0,
            """
            const-string v0, "Jeremy's Custom Patch Loaded!"
            const/4 v1, 0x1
            invoke-static {p0, v0, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
            move-result-object v0
            invoke-virtual {v0}, Landroid/widget/Toast;->show()V
            """
        )
    }
}
