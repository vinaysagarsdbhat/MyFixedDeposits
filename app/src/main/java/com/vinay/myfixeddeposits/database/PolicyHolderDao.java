package com.vinay.myfixeddeposits.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vinay.myfixeddeposits.model.Policy;
import com.vinay.myfixeddeposits.model.PolicyHolder;

import java.util.List;

@Dao
public interface PolicyHolderDao {

    @Insert
    Long addPolicyHolder(PolicyHolder policyHolder);

    @Query("Select * from PolicyHolder")
    LiveData<List<PolicyHolder>> getAllPolicyHolders();

    @Query("Select * from PolicyHolder where name=:policyHolderName")
    LiveData<List<PolicyHolder>> getPolicyHolderByName(String policyHolderName);

    @Update
    void updatePolicyHolder(PolicyHolder policyHolder);

    @Delete
    void deletePolicyHolder(PolicyHolder policyHolder);
}
