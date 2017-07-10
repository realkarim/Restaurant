package com.realkarim.restaurant.dagger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.realkarim.restaurant.customers.CustomersPresenter;
import com.realkarim.restaurant.network.DataRequester;
import com.realkarim.restaurant.tables.TablesPresenter;
import com.realkarim.restaurant.utilities.PrefUtils;
import com.realkarim.restaurant.utilities.PrefUtilsInterface;

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
    CustomersPresenter providesCustomersPresenter(DataRequester dataRequester, PrefUtilsInterface prefUtilsInterface) {
        CustomersPresenter customersPresenter = new CustomersPresenter(dataRequester, prefUtilsInterface);
        return customersPresenter;
    }

    @Provides
    @Singleton
    TablesPresenter providesTablesPresenter(DataRequester dataRequester, PrefUtilsInterface prefUtilsInterface) {
        TablesPresenter tablesPresenter = new TablesPresenter(dataRequester, prefUtilsInterface);
        return tablesPresenter;
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
    DataRequester providesDataRequester(Context context, OkHttpClient okHttpClient, Handler handler, Gson gson, PrefUtilsInterface prefUtilsInterface) {
        DataRequester dataRequester = new DataRequester(context, okHttpClient, handler, gson, prefUtilsInterface);
        return dataRequester;
    }

    @Provides
    PrefUtilsInterface providesPrefUtilsInterface(Context context) {
        PrefUtils prefUtils = new PrefUtils(context);
        return prefUtils;
    }

}
