package com.omolayoseun.copyboard;

import android.content.Context;
import android.widget.Toast;

public class ToastClass {
    static void makeText(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
