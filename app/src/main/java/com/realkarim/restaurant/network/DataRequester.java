package com.realkarim.restaurant.network;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.realkarim.restaurant.R;

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

    public DataRequester(Context context, OkHttpClient okHttpClient, Handler mainHandler, Gson gson) {
        this.context = context;
        this.okHttpClient = okHttpClient;
        this.mainHandler = mainHandler;
        this.gson = gson;
    }

    public void requestCustomersData(final ResponseCallback responseCallback) {

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

                // Parse response
                String responseString = response.body().string();
                final ArrayList<Customer> customers = parseCustomersData(responseString);

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Return response
                        if (responseCode == 200) {
                            responseCallback.onDataReceived(customers);
                        } else
                            responseCallback.onError("Error fetching customers data!");
                    }
                });
            }
        });

    }


    public interface ResponseCallback {
        void onDataReceived(ArrayList arrayList);

        void onError(String error);
    }

    private ArrayList<Customer> parseCustomersData(String array){
        ArrayList<Customer> customersArrayList = null;
        try {
            JSONArray jsonArray = new JSONArray(array);
            customersArrayList = new ArrayList<>();

            for(int i=0;i<jsonArray.length();i++) {
                Customer customer = gson.fromJson("" + jsonArray.get(i), Customer.class);
                customersArrayList.add(customer);
            }

        } catch (JSONException e) {
            Log.e("Data Requester", e.getMessage());
        }
        return customersArrayList;
    }
}
