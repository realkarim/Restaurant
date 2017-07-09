package com.realkarim.restaurant.dagger;

import android.app.Application;
import android.content.Context;

import com.realkarim.restaurant.customers.CustomersPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Karim Mostafa on 7/9/17.
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    CustomersPresenter providesCustomersPresenter() {
        CustomersPresenter customersPresenter = new CustomersPresenter();
        return customersPresenter;
    }

}
