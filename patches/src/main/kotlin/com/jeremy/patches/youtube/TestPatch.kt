package com.jeremy.patches.youtube

import app.morphe.patcher.annotation.MorphPatch
import app.morphe.patcher.patch.BytecodePatch // Use the specialized BytecodePatch
import app.morphe.patcher.context.BytecodeContext
import app.morphe.patcher.util.hook.MethodHook

@MorphPatch(
    name = "InitialToast",
    description = "Shows a toast message when YouTube is opened.",
    target = "com.google.android.youtube"
)
// In v1.3+, we extend BytecodePatch instead of the sealed Patch class
class TestPatch : BytecodePatch() { 

    override fun execute(context: BytecodeContext) {
        // Targeted hook for the main YouTube Activity
        MethodHook.builder()
            .className("com.google.android.apps.youtube.app.watchwhile.WatchWhileActivity")
            .methodName("onCreate")
            .methodDescriptor("(Landroid/os/Bundle;)V")
            .injectAfter {
                """
                android.widget.Toast.makeText(this, "Jeremy's Custom Patch Loaded!", android.widget.Toast.LENGTH_LONG).show();
                """
            }
            .build()
            .apply(context)
    }
}
