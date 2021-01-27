package com.vinay.myfixeddeposits.database;

import android.content.Context;

public class BankRepository {

    private AppDatabase appDatabase;
    private volatile BankRepository INSTANCE;

    public BankRepository(Context context) {
    }

    public BankRepository getInstance(Context context){
        if(INSTANCE == null){
            synchronized (BankRepository.class){
                if (INSTANCE == null) {
                    INSTANCE = new BankRepository(context);
                }
            }
        }
        return INSTANCE;
    }
}
