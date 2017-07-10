package com.realkarim.restaurant.dagger;

import com.realkarim.restaurant.PresentersUniteTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface MockBaseComponent {
    void inject(PresentersUniteTest presentersUniteTest);
}
