package com.realkarim.restaurant.customers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.realkarim.restaurant.R;
import com.realkarim.restaurant.dagger.MyApplication;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Karim Mostafa on 7/9/17.
 */

public class CustomersFragment extends Fragment implements CustomersContract.View {

    @Inject
    CustomersPresenter customerPresenter;

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

        return view;
    }

    @Override
    public void onCustomersDataReceived(ArrayList data) {

    }

    @Override
    public void showMessage(String message) {

    }
}
