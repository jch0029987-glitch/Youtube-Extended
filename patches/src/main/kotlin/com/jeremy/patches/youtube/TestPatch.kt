package com.jeremy.patches.youtube

import app.morphe.patch.annotation.Patch
import app.morphe.patch.patch.SimpleBytecodePatch
import app.morphe.patch.util.proxy.ProxyMethod
@Patch(
    name = "InitialToast",
    description = "Shows a toast on startup",
    target = "com.google.android.youtube"
)
class TestPatch : SimpleBytecodePatch() {
    override fun execute() {
        ProxyMethod.builder()
            .className("com.google.android.apps.youtube.app.application.ShellApplication")
            .methodName("onCreate")
            .methodDescriptor("()V")
            .injectAfter {
                """
                android.util.Log.d("MORPH_PATCH", "Patch executed");
                android.widget.Toast.makeText(
                    (android.content.Context)this,
                    "Jeremy's Morph Patch Loaded!",
                    android.widget.Toast.LENGTH_LONG
                ).show();
                """.trimIndent()
            }
            .build()
            .apply(context)
    }
}
