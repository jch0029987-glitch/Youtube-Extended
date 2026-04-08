package com.jeremy.patches.youtube

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.hook.MethodHook

/**
 * Jeremy's Test Patch
 * Optimized for Morphe 1.3.1 and Android 17 (Cinnamon Bun)
 */
val initialToastPatch = bytecodePatch {
    name = "InitialToast"
    description = "Shows a custom toast message on YouTube startup."
    
    // In 2026, we call target as a function within the builder
    target("com.google.android.youtube")

    execute { context ->
        // We use MethodHook instead of the deprecated ProxyMethod
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
