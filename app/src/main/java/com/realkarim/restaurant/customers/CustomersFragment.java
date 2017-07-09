package com.realkarim.restaurant.customers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.realkarim.restaurant.R;
import com.realkarim.restaurant.dagger.MyApplication;
import com.realkarim.restaurant.network.Customer;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

public class CustomersFragment extends Fragment implements CustomersContract.View {

    @Inject
    CustomersPresenter customerPresenter;

    @BindView(R.id.customers_recycler_view)
    RecyclerView customersRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private CustomersRecyclerViewAdapter customersRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        // Dagger
        ((MyApplication) getActivity().getApplication()).getBaseComponent().inject(this);

        // ButterKnife
        ButterKnife.bind(this, view);

        // Attach view to presenter
        customerPresenter.setView(this);

        // Setup Recycler View
        mLayoutManager = new LinearLayoutManager(getActivity());
        customersRecyclerView.setLayoutManager(mLayoutManager);
        customersRecyclerViewAdapter = new CustomersRecyclerViewAdapter();
        customersRecyclerView.setAdapter(customersRecyclerViewAdapter);

        // Get customers data
        customerPresenter.getCustomers();

        return view;
    }

    @Override
    public void onCustomersDataReceived(ArrayList data) {
        customersRecyclerViewAdapter.updateAdapter(data);
    }

    @Override
    public void showMessage(String message) {

    }

    class CustomersRecyclerViewAdapter extends RecyclerView.Adapter<CustomersRecyclerViewAdapter.ViewHolder> {

        ArrayList<Customer> customers;

        CustomersRecyclerViewAdapter() {
            customers = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_customer, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Customer customer = customers.get(position);
            holder.customer_id.setText(String.valueOf(customer.getId()));
            holder.firstName.setText(customer.getCustomerFirstName());
            holder.lastName.setText(customer.getCustomerLastName());
        }

        @Override
        public int getItemCount() {
            return customers.size();
        }

        public void updateAdapter(ArrayList<Customer> customers) {
            this.customers = customers;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView firstName;
            public TextView lastName;
            public TextView customer_id;

            public ViewHolder(View v) {
                super(v);
                firstName = (TextView) v.findViewById(R.id.first_name);
                lastName = (TextView) v.findViewById(R.id.last_name);
                customer_id = (TextView) v.findViewById(R.id.customer_id);
            }
        }
    }
}
