package com.vinay.myfixeddeposits.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vinay.myfixeddeposits.model.Bank;
import com.vinay.myfixeddeposits.model.Policy;

import java.util.List;

@Dao
public interface BankDao {

    @Insert
    Long addBank(Bank bank);

    @Query("Select * from Bank")
    LiveData<List<Bank>> getAllBanks();

    @Query("Select * from Bank where name =:bankName")
    LiveData<List<Bank>> getBankByName(String bankName);

    @Update
    void updateBank(Bank bank);

    @Delete
    void deleteBank(Bank bank);
}
