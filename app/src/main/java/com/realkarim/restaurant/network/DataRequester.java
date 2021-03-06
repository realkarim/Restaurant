package com.realkarim.restaurant.network;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.realkarim.restaurant.R;
import com.realkarim.restaurant.utilities.HelperFunctions;
import com.realkarim.restaurant.utilities.PrefUtils;
import com.realkarim.restaurant.utilities.PrefUtilsInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

public class DataRequester {

    private Context context;
    private OkHttpClient okHttpClient;
    private Handler mainHandler;
    private Gson gson;
    private PrefUtilsInterface prefUtilsInterface;

    public DataRequester(Context context, OkHttpClient okHttpClient, Handler mainHandler, Gson gson, PrefUtilsInterface prefUtilsInterface) {
        this.context = context;
        this.okHttpClient = okHttpClient;
        this.mainHandler = mainHandler;
        this.gson = gson;
        this.prefUtilsInterface = prefUtilsInterface;
    }

    public void requestCustomersData(final ResponseCallback responseCallback) {
        // Check for internet connection
        if (!HelperFunctions.isConnectedToInternet(context)) {
            // Notify user about the internet problem
            responseCallback.onError(context.getString(R.string.no_internet));
            // Return saved data if available
            responseCallback.onDataReceived(prefUtilsInterface.getCustomersData());
            return;
        }
        // Create URL
        String baseURL = context.getString(R.string.customers_url);
        Uri builtUri = Uri.parse(baseURL)
                .buildUpon()
                .build();
        final String finalURL = builtUri.toString();

        // Create request
        Request request = new Request.Builder()
                .url(finalURL)
                .build();

        // Send request
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // Return error message
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        responseCallback.onError(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final Integer responseCode = response.code();
                final String responseString = response.body().string();

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Return response
                        if (responseCode == 200) {
                            responseCallback.onDataReceived(responseString);
                        } else
                            responseCallback.onError("Error fetching customers data!");
                    }
                });
            }
        });
    }

    public void requestTablesData(final ResponseCallback responseCallback) {
        // Check for internet connection
        if (!HelperFunctions.isConnectedToInternet(context)) {
            // Notify user about the internet problem
            responseCallback.onError(context.getString(R.string.no_internet));
            return;
        }
        // Create URL
        String baseURL = context.getString(R.string.tables_url);
        Uri builtUri = Uri.parse(baseURL)
                .buildUpon()
                .build();
        final String finalURL = builtUri.toString();

        // Create request
        Request request = new Request.Builder()
                .url(finalURL)
                .build();

        // Send request
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // Return error message
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        responseCallback.onError(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final Integer responseCode = response.code();
                final String responseString = response.body().string();

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Return response
                        if (responseCode == 200) {
                            responseCallback.onDataReceived(responseString);
                        } else
                            responseCallback.onError(context.getString(R.string.error_fetching));
                    }
                });
            }
        });

    }

    public interface ResponseCallback {
        void onDataReceived(String response);

        void onError(String error);
    }

}
