package com.realkarim.restaurant.dagger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.realkarim.restaurant.customers.CustomersPresenter;
import com.realkarim.restaurant.network.DataRequester;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

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
    CustomersPresenter providesCustomersPresenter(DataRequester dataRequester) {
        CustomersPresenter customersPresenter = new CustomersPresenter(dataRequester);
        return customersPresenter;
    }

    @Provides
    Gson providesGson() {
        return new Gson();
    }

    @Provides
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    Handler providesHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    DataRequester providesDataRequester(Context context, OkHttpClient okHttpClient, Handler handler, Gson gson){
        DataRequester dataRequester = new DataRequester(context, okHttpClient, handler, gson);
        return dataRequester;
    }

}
