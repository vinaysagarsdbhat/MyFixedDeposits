package com.vinay.myfixeddeposits.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PolicyHolder {

    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
}
