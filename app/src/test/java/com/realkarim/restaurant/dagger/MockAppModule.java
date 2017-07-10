package com.realkarim.restaurant.dagger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.realkarim.restaurant.network.DataRequester;
import com.realkarim.restaurant.utilities.PrefUtils;
import com.realkarim.restaurant.utilities.PrefUtilsInterface;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import okhttp3.OkHttpClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class MockAppModule extends AppModule {
    public MockAppModule(Application application) {
        super(application);
    }

    @Override
    DataRequester providesDataRequester(Context context, OkHttpClient okHttpClient, Handler handler, Gson gson, PrefUtilsInterface prefUtilsInterface) {
        DataRequester dataRequester = Mockito.mock(DataRequester.class);

        // Mock DataRequester Class
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((DataRequester.ResponseCallback) args[0]).onDataReceived(anyString());
                return null; // void method, so return null
            }
        }).when(dataRequester).requestCustomersData(any(DataRequester.ResponseCallback.class));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                ((DataRequester.ResponseCallback) args[0]).onDataReceived(anyString());
                return null; // void method, so return null
            }
        }).when(dataRequester).requestTablesData(any(DataRequester.ResponseCallback.class));
        return dataRequester;
    }



    @Override
    Handler providesHandler() {
        return new Handler();
    }

    @Override
    PrefUtilsInterface providesPrefUtilsInterface(Context context) {
        // Mock PrefUtils class
        PrefUtils prefUtils = Mockito.mock(PrefUtils.class);
        return prefUtils;
    }

}
