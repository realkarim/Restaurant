package com.realkarim.restaurant.tables;

import com.realkarim.restaurant.network.DataRequester;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class TablesPresenter implements TablesContract.Presenter {

    private TablesContract.View view;
    private DataRequester dataRequester;


    public TablesPresenter(DataRequester dataRequester) {
        this.dataRequester = dataRequester;
    }

    @Override
    public void setView(TablesContract.View view) {
        this.view = view;
    }

    @Override
    public void getTables() {
        // Fetch tables data
        dataRequester.requestTablesData(new DataRequester.ResponseCallback() {
            @Override
            public void onDataReceived(ArrayList arrayList) {
                view.onTablesDataReceived(arrayList);
            }

            @Override
            public void onError(String error) {
                view.showMessage(error);
            }
        });
    }
}
