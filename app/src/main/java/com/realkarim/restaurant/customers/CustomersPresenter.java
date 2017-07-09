package com.realkarim.restaurant.customers;

import com.realkarim.restaurant.network.DataRequester;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

public class CustomersPresenter implements CustomersContract.Presenter {

    private CustomersContract.View view;

    private DataRequester dataRequester;

    public CustomersPresenter(DataRequester dataRequester) {
        this.dataRequester = dataRequester;
    }

    @Override
    public void setView(CustomersContract.View view) {
        this.view = view;
    }

    @Override
    public void getCustomers() {
        // Fetch customers data
        dataRequester.requestCustomersData(new DataRequester.ResponseCallback() {
            @Override
            public void onDataReceived(ArrayList arrayList) {
                view.onCustomersDataReceived(arrayList);
            }

            @Override
            public void onError(String error) {
                view.showMessage(error);
            }
        });
    }
}
