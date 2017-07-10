package com.realkarim.restaurant.tables;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.realkarim.restaurant.R;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class TablesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TablesFragment())
                .commit();
    }
}
