package com.realkarim.restaurant.customers;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

interface CustomersContract {
    interface View {

        void onCustomersDataReceived(ArrayList data);

        void showMessage(String message);

    }

    interface Presenter {

        void setView(View view);

        void getCustomers();

    }
}
