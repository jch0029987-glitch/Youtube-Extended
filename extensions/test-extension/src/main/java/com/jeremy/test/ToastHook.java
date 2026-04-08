package com.jeremy.test;

import android.app.Activity;
import android.widget.Toast;

public class ToastHook {
    public static void show(Activity activity) {
        activity.runOnUiThread(() -> 
            Toast.makeText(activity, "Jeremy's Custom Patch Loaded!", Toast.LENGTH_LONG).show()
        );
    }
}
