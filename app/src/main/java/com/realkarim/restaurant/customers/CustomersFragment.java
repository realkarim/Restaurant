package com.realkarim.restaurant.customers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.realkarim.restaurant.R;
import com.realkarim.restaurant.dagger.MyApplication;
import com.realkarim.restaurant.network.Customer;
import com.realkarim.restaurant.scheduler.AlarmReceiver;
import com.realkarim.restaurant.scheduler.TablesUpdate;
import com.realkarim.restaurant.tables.TablesActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBroadCastReceiver();
    }

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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void setUpBroadCastReceiver() {
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int interval = 10 * 60 * 1000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//        Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_SHORT).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTablesUpdate(TablesUpdate update) {
        setUpBroadCastReceiver();
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

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            public TextView firstName;
            public TextView lastName;
            public TextView customer_id;

            public ViewHolder(View v) {
                super(v);
                firstName = (TextView) v.findViewById(R.id.first_name);
                lastName = (TextView) v.findViewById(R.id.last_name);
                customer_id = (TextView) v.findViewById(R.id.customer_id);

                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TablesActivity.class);
                startActivity(intent);
            }
        }
    }
}
