package com.realkarim.restaurant.customers;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

public class CustomersPresenter implements CustomersContract.Presenter {

    private CustomersContract.View view;

    public CustomersPresenter(){

    }

    @Override
    public void setView(CustomersContract.View view) {
        this.view = view;
    }

    @Override
    public void getCustomers() {

    }
}
