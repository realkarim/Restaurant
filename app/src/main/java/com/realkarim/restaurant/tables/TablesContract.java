package com.realkarim.restaurant.tables;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public interface TablesContract {
    interface View {

        void onTablesDataReceived(ArrayList data);

        void showMessage(String message);

    }

    interface Presenter {

        void setView(View view);

        void getTables();

    }
}
