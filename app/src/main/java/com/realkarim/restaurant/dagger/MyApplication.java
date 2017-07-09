package com.realkarim.restaurant.dagger;

import android.app.Application;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

public class MyApplication extends Application{

    private BaseComponent baseComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        baseComponent = DaggerBaseComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public BaseComponent getBaseComponent() {
        return baseComponent;
    }
}
