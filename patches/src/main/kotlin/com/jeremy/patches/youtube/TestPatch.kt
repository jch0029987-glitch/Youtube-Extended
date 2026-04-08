package com.jeremy.patches.youtube

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.hook.MethodHook

// In Morphe 1.3.1+, use the factory function 'bytecodePatch'
val testPatch = bytecodePatch {
    name = "InitialToast"
    description = "Shows a toast message when YouTube is opened."
    
    // Updated target syntax for 2026
    target("com.google.android.youtube")

    execute { context ->
        // Hooking the main YouTube activity
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
