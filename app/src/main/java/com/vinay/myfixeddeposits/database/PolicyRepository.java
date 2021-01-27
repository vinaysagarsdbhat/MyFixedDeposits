package com.vinay.myfixeddeposits.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.google.gson.Gson;
import com.vinay.myfixeddeposits.model.Policy;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;

public class PolicyRepository implements Cloneable, Serializable {

    private AppDatabase appDatabase;
    private static volatile PolicyRepository INSTANCE;

    public static PolicyRepository getInstance(Context context){
        if(INSTANCE == null){
            synchronized (PolicyRepository.class) {
                if(INSTANCE == null)
                    INSTANCE = new PolicyRepository(context);
            }
        }
        return INSTANCE;
    }

    private PolicyRepository(Context context){
        appDatabase = AppDatabase.getDatabase(context);
    }
    private PolicyRepository(){}

    public void addPolicy(Policy policy){
        appDatabase.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.daoMyFixedDeposit().addPolicy(policy);
            }
        });
    }

    public void addPolicies(List<Policy> policies){
        appDatabase.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.daoMyFixedDeposit().addPolicies(policies);
            }
        });
    }

    public void updatePolicy(Policy policy){
        appDatabase.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.daoMyFixedDeposit().updatePolicy(policy);
            }
        });
    }

    public void deletePolicy(Policy policy){
        appDatabase.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.daoMyFixedDeposit().deletePolicy(policy);
            }
        });
    }

    public LiveData<List<Policy>> getPolicy(int id){
        return appDatabase.daoMyFixedDeposit().getPolicy(id);
    }

    public LiveData<List<Policy>> getPolicies(){
        return appDatabase.daoMyFixedDeposit().getAllPolicies();
    }

    public LiveData<List<Policy>> filterPolicies(String query){
        return appDatabase.daoMyFixedDeposit().filterPolicies(query);
    }

    public LiveData<List<Policy>> filterPolicies(SupportSQLiteQuery query){
        return appDatabase.daoMyFixedDeposit().filterPolicies(query);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }


}
