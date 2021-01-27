package com.vinay.myfixeddeposits.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.vinay.myfixeddeposits.model.Policy;

import java.util.List;

@Dao
public interface PolicyDao{

    @Insert
    Long addPolicy(Policy policy);

    @Insert
    Long[] addPolicies(List<Policy> countryModel);

    @Query("Select * from Policy")
    LiveData<List<Policy>> getAllPolicies();

    @Query("Select * from Policy where holder LIKE :query or bankName LIKE :query or certificateNumber LIKE :query or remarks LIKE :query")
    LiveData<List<Policy>> filterPolicies(String query);

    @RawQuery(observedEntities = Policy.class)
    LiveData<List<Policy>> filterPolicies(SupportSQLiteQuery query);

    @Query("Select * from Policy where id =:policyId")
    LiveData<List<Policy>> getPolicy(int policyId);

    @Update
    void updatePolicy(Policy policy);

    @Delete
    void deletePolicy(Policy policy);
}
