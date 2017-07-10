package com.realkarim.restaurant.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.realkarim.restaurant.R;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class PrefUtils implements PrefUtilsInterface{

    private Context context;

    public PrefUtils(Context context){
        this.context = context;
    }

    @Override
    public void setCustomersData(String data) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(context.getString(R.string.customers_key), data).apply();
    }

    @Override
    public String getCustomersData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(context.getString(R.string.customers_key), null);
    }

    @Override
    public void setTablesData(String data) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(context.getString(R.string.tables_key), data).apply();
    }

    @Override
    public String getTablesData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(context.getString(R.string.tables_key), null);
    }
}
