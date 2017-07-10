package com.realkarim.restaurant.tables;

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
import com.realkarim.restaurant.utilities.HelperFunctions;

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

    class TablesRecyclerViewAdapter extends RecyclerView.Adapter<TablesRecyclerViewAdapter.ViewHolder> {

        ArrayList<Boolean> tables;

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

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView table;

            public ViewHolder(View v) {
                super(v);
                table = (TextView) v.findViewById(R.id.table);
            }
        }
    }
}
