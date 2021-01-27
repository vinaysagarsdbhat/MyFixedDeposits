package com.vinay.myfixeddeposits.view.ui.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinay.myfixeddeposits.R;


public class AddNewPolicyFragment extends Fragment {

    public static AddNewPolicyFragment newInstance() {
        return new AddNewPolicyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_policy, container, false);
        return view;
    }
}