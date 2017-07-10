package com.realkarim.restaurant.customers;

import com.realkarim.restaurant.network.Customer;
import com.realkarim.restaurant.network.DataRequester;
import com.realkarim.restaurant.utilities.HelperFunctions;
import com.realkarim.restaurant.utilities.PrefUtilsInterface;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

public class CustomersPresenter implements CustomersContract.Presenter {

    private CustomersContract.View view;
    private DataRequester dataRequester;
    private PrefUtilsInterface prefUtilsInterface;

    private String ERROR_PROCESSING_DATA = "Error processing data!";

    public CustomersPresenter(DataRequester dataRequester, PrefUtilsInterface prefUtilsInterface) {
        this.dataRequester = dataRequester;
        this.prefUtilsInterface = prefUtilsInterface;
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
            public void onDataReceived(String arrayList) {
                ArrayList<Customer> customers = HelperFunctions.parseCustomersData(arrayList);
                if(customers == null)
                    view.showMessage(ERROR_PROCESSING_DATA);
                else {
                    // Save and return data
                    prefUtilsInterface.setCustomersData(arrayList);
                    view.onCustomersDataReceived(customers);
                }
            }

            @Override
            public void onError(String error) {
                view.showMessage(error);
            }
        });
    }
}
