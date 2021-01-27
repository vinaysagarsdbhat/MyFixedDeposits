package com.vinay.myfixeddeposits.view.ui.dashboard;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.google.gson.Gson;
import com.vinay.myfixeddeposits.R;
import com.vinay.myfixeddeposits.adapter.PolicyListAdapter;
import com.vinay.myfixeddeposits.database.PolicyRepository;
import com.vinay.myfixeddeposits.model.Policy;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PolicyListFragment extends Fragment{

    private RecyclerView policyListRecyclerView;
    private PolicyListViewModel policyListViewModel;
    private PolicyListAdapter policyListAdapter;
    private String orderBy = "dateOfMaturity";
    private String asc_dsc = "asc";
    private Spinner filter;

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
        getActivity().getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                applyFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
               applyFilter(query);
                return false;
            }
        });

        filter  = (Spinner) menu.findItem(R.id.filter)
                .getActionView();
        final List<String> filterList = new ArrayList<>();
        final List<String> filterListMap = new ArrayList<String>();
        filterList.add("--Filter--");
        filterListMap.add("");

        filterList.add("Deposit Date");
        filterListMap.add("dateOfDeposit");

        filterList.add("Deposit Amt");
        filterListMap.add("depositAmount");

        filterList.add("Maturity Date");
        filterListMap.add("dateOfMaturity");

        filterList.add("Maturity Amt");
        filterListMap.add("maturityAmount");

        filterList.add("Interest");
        filterListMap.add("interest");

        filterList.add("Interest Rate");
        filterListMap.add("rateOfInterest");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,filterList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);
        //filter.setPopupBackgroundResource(R.drawable.spinner_background);
        filter.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) filter.getSelectedView()).setTextColor(Color.WHITE);
            }
        });

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    orderBy = filterListMap.get(i);
                    policyListViewModel.filterPolicies(new SimpleSQLiteQuery("select * from Policy order by "+orderBy+" "+asc_dsc)).observe(getActivity(), new Observer<List<Policy>>() {
                        @Override
                        public void onChanged(List<Policy> policies) {
                            policyListAdapter.injectPolicyList(policies);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                if(asc_dsc.equalsIgnoreCase("asc"))
                    asc_dsc = "desc";
                else
                    asc_dsc = "asc";

                policyListViewModel.filterPolicies(new SimpleSQLiteQuery("select * from Policy order by "+orderBy+" "+asc_dsc)).observe(getActivity(), new Observer<List<Policy>>() {
                    @Override
                    public void onChanged(List<Policy> policies) {
                        policyListAdapter.injectPolicyList(policies);
                    }
                });
                return true;
            case R.id.add:
                //showAddDialog();
                //startActivity(new Intent(getApplicationContext(),AddNewPolicy.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                //finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void applyFilter(String query) {
        if(query != null && !query.trim().equalsIgnoreCase(""))
            policyListViewModel.filterPolicies("%"+query+"%").observe(getActivity(), new Observer<List<Policy>>() {
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
    }

    public static int dp2px(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
