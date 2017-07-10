package com.realkarim.restaurant.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.realkarim.restaurant.tables.TablesContract;
import com.realkarim.restaurant.utilities.HelperFunctions;
import com.realkarim.restaurant.utilities.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private PrefUtils prefUtils;

    public AlarmReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        prefUtils = new PrefUtils(context);

        // Get saved tables data
        String tables = prefUtils.getTablesData();

        // Make all tables available
        ArrayList<Boolean> tablesArray = HelperFunctions.parseTablesData(tables);
        if(tablesArray == null)
            return;
        for (int i = 0; i < tablesArray.size(); i++)
            tablesArray.set(i, true);

        // Save updated tables data
        tables = HelperFunctions.convertTablesDataToString(tablesArray);
        prefUtils.setTablesData(tables);

        // Display updated data
        EventBus.getDefault().post(new TablesUpdate(tablesArray));
    }
}
