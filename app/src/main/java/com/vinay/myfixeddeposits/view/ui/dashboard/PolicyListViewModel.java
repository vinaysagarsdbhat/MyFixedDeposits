package com.vinay.myfixeddeposits.view.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.vinay.myfixeddeposits.database.PolicyRepository;
import com.vinay.myfixeddeposits.model.Policy;

import java.util.List;

public class PolicyListViewModel extends AndroidViewModel {

    public PolicyListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Policy>> getPolicyList() {
        return PolicyRepository.getInstance(getApplication()).getPolicies();
    }

    public LiveData<List<Policy>> filterPolicies(String query) {
        return PolicyRepository.getInstance(getApplication()).filterPolicies(query);
    }

    public LiveData<List<Policy>> filterPolicies(SupportSQLiteQuery query) {
        return PolicyRepository.getInstance(getApplication()).filterPolicies(query);
    }

}
