package com.realkarim.restaurant.dagger;

import com.realkarim.restaurant.customers.CustomersFragment;
import com.realkarim.restaurant.tables.TablesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface BaseComponent {
    void inject(CustomersFragment customersFragment);

    void inject(TablesFragment tablesFragment);
}
