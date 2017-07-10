package com.realkarim.restaurant.tables;

import com.realkarim.restaurant.R;
import com.realkarim.restaurant.network.DataRequester;
import com.realkarim.restaurant.utilities.HelperFunctions;
import com.realkarim.restaurant.utilities.PrefUtilsInterface;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class TablesPresenter implements TablesContract.Presenter {

    private TablesContract.View view;
    private DataRequester dataRequester;
    private PrefUtilsInterface prefUtilsInterface;

    private String ERROR_PROCESSING_DATA = "Error processing data!";

    public TablesPresenter(DataRequester dataRequester, PrefUtilsInterface prefUtilsInterface) {
        this.dataRequester = dataRequester;
        this.prefUtilsInterface = prefUtilsInterface;
    }

    @Override
    public void setView(TablesContract.View view) {
        this.view = view;
    }

    @Override
    public void getTables() {
        // Get last saved data if available
        if (prefUtilsInterface.getTablesData() != null) {
            view.onTablesDataReceived(HelperFunctions.parseTablesData(prefUtilsInterface.getTablesData()));
            return;
        }
        // Fetch tables data
        dataRequester.requestTablesData(new DataRequester.ResponseCallback() {
            @Override
            public void onDataReceived(String arrayList) {
                final ArrayList<Boolean> tables = HelperFunctions.parseTablesData(arrayList);

                if(tables == null)
                    view.showMessage(ERROR_PROCESSING_DATA);
                else {
                    // Save and return data
                    prefUtilsInterface.setTablesData(arrayList);
                    view.onTablesDataReceived(tables);
                }
            }

            @Override
            public void onError(String error) {
                view.showMessage(error);
            }
        });
    }
}
