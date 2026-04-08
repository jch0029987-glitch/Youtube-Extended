package com.jeremy.patches.youtube

import app.morphe.patcher.annotation.MorphPatch // Updated import
import app.morphe.patcher.patch.Patch
import app.morphe.patcher.context.BytecodeContext // Need this for the type argument
import app.morphe.patcher.util.hook.MethodHook // New replacement for ProxyMethod

@MorphPatch( // Annotation name updated in v1.3
    name = "InitialToast",
    description = "Shows a toast message when YouTube is opened.",
    target = "com.google.android.youtube"
)
class TestPatch : Patch<BytecodeContext>() { // Added <BytecodeContext> type argument
    override fun execute(context: BytecodeContext) { // Added context parameter
        
        // Using the updated MethodHook API for 2026
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
