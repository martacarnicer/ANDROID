package com.example.gymproject.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class FontUtil {

    // Cache para las diferentes variantes de la fuente Poppins
    private static Typeface poppinsRegularTypeface;
    private static Typeface poppinsSemiBoldTypeface;

    // Método para cargar Poppins-Regular desde assets
    public static Typeface getPoppinsRegularTypeface(Context context) {
        if (poppinsRegularTypeface == null) {
            poppinsRegularTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf");
        }
        return poppinsRegularTypeface;
    }

    // Método para cargar Poppins-SemiBold desde assets
    public static Typeface getPoppinsSemiBoldTypeface(Context context) {
        if (poppinsSemiBoldTypeface == null) {
            poppinsSemiBoldTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-SemiBold.ttf");
        }
        return poppinsSemiBoldTypeface;
    }

    // Método para aplicar la fuente a múltiples TextViews, EditTexts y Buttons
    public static void applyFontToViews(Typeface typeface, View... views) {
        for (View view : views) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(typeface);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(typeface);
            }
        }
    }
}
