package com.realkarim.restaurant.tables;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.realkarim.restaurant.R;
import com.realkarim.restaurant.dagger.MyApplication;
import com.realkarim.restaurant.scheduler.AlarmReceiver;
import com.realkarim.restaurant.scheduler.TablesUpdate;
import com.realkarim.restaurant.utilities.HelperFunctions;
import com.realkarim.restaurant.utilities.PrefUtilsInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karim Mostafa on 7/10/17.
 */

public class TablesFragment extends Fragment implements TablesContract.View {

    @Inject
    TablesPresenter tablesPresenter;

    @Inject
    PrefUtilsInterface prefUtilsInterface;

    @BindView(R.id.tables_recycler_view)
    RecyclerView tablesRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private TablesRecyclerViewAdapter tablesRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tables, container, false);

        // Dagger
        ((MyApplication) getActivity().getApplication()).getBaseComponent().inject(this);

        // ButterKnife
        ButterKnife.bind(this, view);

        // Attach view to presenter
        tablesPresenter.setView(this);

        // Setup Recycler View
        mLayoutManager = new GridLayoutManager(getActivity(), HelperFunctions.calculateNoOfColumns(getActivity()));
        tablesRecyclerView.setLayoutManager(mLayoutManager);
        tablesRecyclerViewAdapter = new TablesRecyclerViewAdapter();
        tablesRecyclerView.setAdapter(tablesRecyclerViewAdapter);

        // Get tables data
        tablesPresenter.getTables();

        return view;
    }

    @Override
    public void onTablesDataReceived(ArrayList data) {
        tablesRecyclerViewAdapter.updateAdapter(data);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTablesUpdate(TablesUpdate update) {
        onTablesDataReceived(update.data);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    class TablesRecyclerViewAdapter extends RecyclerView.Adapter<TablesRecyclerViewAdapter.ViewHolder> {

        ArrayList<Boolean> tables;
        private Integer selectedTable = -1;

        TablesRecyclerViewAdapter() {
            tables = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_table, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Boolean table = tables.get(position);
            if (table) {
                holder.table.setText(getString(R.string.available));
                holder.table.setBackgroundColor(getResources().getColor(R.color.available));
            } else {
                holder.table.setText(getString(R.string.reserved));
                holder.table.setBackgroundColor(getResources().getColor(R.color.reserved));
            }
        }

        @Override
        public int getItemCount() {
            return tables.size();
        }

        public void updateAdapter(ArrayList<Boolean> tables) {
            this.tables = tables;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView table;

            public ViewHolder(View v) {
                super(v);
                table = (TextView) v.findViewById(R.id.table);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Integer position = getAdapterPosition();
                if (position != selectedTable && tables.get(position) == false)
                    showMessage(getString(R.string.isAlreadyReserved));
                else {
                    if (selectedTable != -1) {
                        tables.set(selectedTable, true);
                        notifyItemChanged(selectedTable);
                        if (position == selectedTable) {
                            selectedTable = -1;
                            return;
                        }
                    }
                    tables.set(position, false);
                    selectedTable = position;
                    notifyItemChanged(position);

                    // Save updated table
                    prefUtilsInterface.setTablesData(HelperFunctions.convertTablesDataToString(tables));
                }
            }
        }
    }
}
