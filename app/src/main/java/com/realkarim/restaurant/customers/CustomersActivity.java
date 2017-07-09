package com.realkarim.restaurant.customers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.realkarim.restaurant.R;

public class CustomersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CustomersFragment())
                .commit();
    }
}
