package com.realkarim.restaurant.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.gson.Gson;
import com.realkarim.restaurant.network.Customer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

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

    public static Boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static ArrayList<Customer> parseCustomersData(String array) {
        ArrayList<Customer> customersArrayList = null;
        try {
            JSONArray jsonArray = new JSONArray(array);
            customersArrayList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                Gson gson = new Gson();
                Customer customer = gson.fromJson("" + jsonArray.get(i), Customer.class);
                customersArrayList.add(customer);
            }

        } catch (JSONException e) {
            Log.e("Helper Function", e.getMessage());
            return null;
        }
        return customersArrayList;
    }

    public static ArrayList<Boolean> parseTablesData(String responseString) {
        ArrayList<Boolean> tables = new ArrayList<>();
        try {
            // Parse response
            JSONArray jsonArray = new JSONArray(responseString);
            for (int i = 0; i < jsonArray.length(); i++)
                tables.add(jsonArray.getBoolean(i));
        } catch (JSONException e) {
            Log.e("Helper Function", e.getMessage());
            return null;
        }

        return tables;
    }

    public static String convertTablesDataToString(ArrayList<Boolean> arrayList) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < arrayList.size(); i++) {
            jsonArray.put(arrayList.get(i));
        }
        String dataString = jsonArray.toString();
        return dataString;
    }
}
