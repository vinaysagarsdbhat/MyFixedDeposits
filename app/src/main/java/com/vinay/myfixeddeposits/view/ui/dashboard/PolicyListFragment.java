package com.vinay.myfixeddeposits.view.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.vinay.myfixeddeposits.R;
import com.vinay.myfixeddeposits.adapter.PolicyListAdapter;
import com.vinay.myfixeddeposits.model.Policy;

import java.util.List;
import java.util.zip.Inflater;

public class PolicyListFragment extends Fragment {

    private RecyclerView policyListRecyclerView;
    private PolicyListViewModel policyListViewModel;
    private PolicyListAdapter policyListAdapter;

    public static PolicyListFragment newInstance() {
        Bundle args = new Bundle();
        PolicyListFragment fragment = new PolicyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.policy_list_fragment,container, false);
        policyListRecyclerView = v.findViewById(R.id.policyListRecyclerView);
        policyListAdapter = new PolicyListAdapter();
        policyListRecyclerView.setAdapter(policyListAdapter);
        policyListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        policyListViewModel = new ViewModelProvider(this).get(PolicyListViewModel.class);
        policyListViewModel.getPolicyList().observe(getActivity(), new Observer<List<Policy>>() {
            @Override
            public void onChanged(List<Policy> policies) {
                Log.e("All Policies = ", new Gson().toJson(policies));
                policyListAdapter.injectPolicyList(policies);
            }
        });
    }
}
