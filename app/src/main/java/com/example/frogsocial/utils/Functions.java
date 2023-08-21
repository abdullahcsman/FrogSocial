package com.example.frogsocial.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Functions {
    private static Snackbar snackbar;

    public static void snackBarMessage(Context context, View view) {

        snackbar = Snackbar.make(view, "No Internet Found!", Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void hideSnackBar() {
        if (snackbar != null)
            snackbar.dismiss();
    }
    public static boolean isSnakbarShowing() {
        return snackbar != null && snackbar.isShown();
    }
}
