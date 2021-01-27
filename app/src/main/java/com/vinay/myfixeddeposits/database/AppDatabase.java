package com.vinay.myfixeddeposits.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.vinay.myfixeddeposits.model.Bank;
import com.vinay.myfixeddeposits.model.Policy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Policy.class, Bank.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PolicyDao getPolicyDao();
    //public abstract BankDao bankDao();


    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 1;
    private ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "ploicy_database").build();
                }
            }
        }
        return INSTANCE;
    }

    public ExecutorService getExecutorService(){
        return databaseWriteExecutor;
    }


    



}
