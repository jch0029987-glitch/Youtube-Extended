package com.jeremy.patches.youtube

import app.morphe.patcher.annotation.Patch
import app.morphe.patcher.patch.Patch
import app.morphe.patcher.util.proxy.ProxyMethod

@Patch(
    name = "InitialToast",
    description = "Shows a toast message when YouTube is opened for testing.",
    target = "com.google.android.youtube"
)
class TestPatch : Patch() {
    override fun execute() {
        // Hooks the main activity startup
        ProxyMethod.builder()
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
