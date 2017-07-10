package com.realkarim.restaurant.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class HelperFunctions {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 100);
        return noOfColumns;
    }
}
