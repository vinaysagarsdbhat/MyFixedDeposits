package com.vinay.myfixeddeposits.view.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.vinay.myfixeddeposits.R;
import com.vinay.myfixeddeposits.adapter.PolicyListAdapter;
import com.vinay.myfixeddeposits.database.PolicyRepository;
import com.vinay.myfixeddeposits.model.Policy;

import java.util.List;
import java.util.zip.Inflater;

public class PolicyListFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

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
        setHasOptionsMenu(true);
        policyListViewModel = new ViewModelProvider(this).get(PolicyListViewModel.class);
        policyListViewModel.getPolicyList().observe(getActivity(), new Observer<List<Policy>>() {
            @Override
            public void onChanged(List<Policy> policies) {
                policyListAdapter.injectPolicyList(policies);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setShowAsAction(searchItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query != null && !query.trim().equalsIgnoreCase(""))
            policyListViewModel.filterPolicies("%"+query+"%").observe(this, new Observer<List<Policy>>() {
                @Override
                public void onChanged(List<Policy> policies) {
                    policyListAdapter.injectPolicyList(policies);
                }
            });
        else
            policyListViewModel.getPolicyList().observe(getActivity(), new Observer<List<Policy>>() {
                @Override
                public void onChanged(List<Policy> policies) {
                    policyListAdapter.injectPolicyList(policies);
                }
            });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(query != null && !query.trim().equalsIgnoreCase(""))
            policyListViewModel.filterPolicies("%"+query+"%").observe(this, new Observer<List<Policy>>() {
                @Override
                public void onChanged(List<Policy> policies) {
                    policyListAdapter.injectPolicyList(policies);
                }
            });
        else
            policyListViewModel.getPolicyList().observe(getActivity(), new Observer<List<Policy>>() {
                @Override
                public void onChanged(List<Policy> policies) {
                    policyListAdapter.injectPolicyList(policies);
                }
            });
        return true;
    }
}
