package com.realkarim.restaurant;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.realkarim.restaurant.dagger.MyApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomersActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView textView;

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        // ButterKnife
        ButterKnife.bind(this);

        // Dagger
        ((MyApplication) getApplication()).getBaseComponent().inject(this);

        textView.setText("Hello ButterKnife!");

        Toast.makeText(context, "Hello Dagger!", Toast.LENGTH_LONG).show();
    }
}
