package com.vinay.myfixeddeposits.view.ui.dashboard;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vinay.myfixeddeposits.database.PolicyRepository;
import com.vinay.myfixeddeposits.model.Policy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class ImportPolicyViewModel extends AndroidViewModel {

    private MutableLiveData<List<Policy>> policies = new MutableLiveData<List<Policy>>();
    private MutableLiveData<Integer> totalPoliciesAvailableForImport = new MutableLiveData<Integer>();


    public ImportPolicyViewModel(@NonNull Application application) {
        super(application);
        policies.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Policy>> getPolicies() {
        return policies;
    }

    public MutableLiveData<Integer> getTotalPoliciesAvailableForImport() {
        return totalPoliciesAvailableForImport;
    }

    public void importPolicy(File file) throws Exception{
        Timber.plant(new Timber.DebugTree());
        StringBuilder text = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            text.append(line);
        }
        br.close();
        Type listOfTestObject = new TypeToken<List<Policy>>(){}.getType();

        List<Policy> policiesList = new Gson().fromJson(text.toString(), listOfTestObject);
        totalPoliciesAvailableForImport.postValue(policiesList.size());
        Timber.i(new GsonBuilder().setPrettyPrinting().create().toJson(policiesList));
        for (int i = 0; i < policiesList.size() ; i++) {
            PolicyRepository.getInstance(getApplication().getApplicationContext()).addPolicy(policiesList.get(i));
            policiesList.get(i).setId(0);
            policies.getValue().add(policiesList.get(i));
            policies.setValue(policies.getValue());
        }
    }
}