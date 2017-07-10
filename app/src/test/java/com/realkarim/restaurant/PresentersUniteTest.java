package com.realkarim.restaurant;

import android.app.Application;
import android.util.Log;

import com.realkarim.restaurant.customers.CustomersContract;
import com.realkarim.restaurant.customers.CustomersPresenter;
import com.realkarim.restaurant.dagger.DaggerMockBaseComponent;
import com.realkarim.restaurant.dagger.MockAppModule;
import com.realkarim.restaurant.network.Customer;
import com.realkarim.restaurant.tables.TablesContract;
import com.realkarim.restaurant.tables.TablesPresenter;
import com.realkarim.restaurant.utilities.HelperFunctions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Karim Mostafa on 7/10/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HelperFunctions.class, Log.class})
@PowerMockIgnore("javax.net.ssl.*")
public class PresentersUniteTest {

    @Inject
    CustomersPresenter customersPresenter;

    @Inject
    TablesPresenter tablesPresenter;

    @Before
    public void setUp() {
        DaggerMockBaseComponent.builder()
                .appModule(new MockAppModule(new Application()))
                .build()
                .inject(this);


        // Mock Log class
        mockStatic(Log.class);

        // Mock HelperFunctions static methods
        mockStatic( HelperFunctions.class );
        when(HelperFunctions.parseCustomersData(anyString())).thenReturn(new ArrayList<Customer>());
        when(HelperFunctions.parseTablesData(anyString())).thenReturn(new ArrayList<Boolean>());
        when(HelperFunctions.convertTablesDataToString(any(ArrayList.class))).thenReturn("");
    }

    @Test
    public void testLoadCustomersData() {
        CustomersContract.View view = Mockito.spy(CustomersContract.View.class);

        customersPresenter.setView(view);
        customersPresenter.getCustomers();
        verify(view).onCustomersDataReceived(any(ArrayList.class));
    }

    @Test
    public void testLoadTablesData() {
        TablesContract.View view = Mockito.spy(TablesContract.View.class);

        tablesPresenter.setView(view);
        tablesPresenter.getTables();
        verify(view).onTablesDataReceived(any(ArrayList.class));
    }
}

