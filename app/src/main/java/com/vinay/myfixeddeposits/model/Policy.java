package com.vinay.myfixeddeposits.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Policy {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int depositAmount,maturityAmount,interest,policyType;
    private String holder,certificateNumber,nominee,bankName,durationString,remarks,dateOfDeposit,dateOfMaturity,belongsTo;
    private double rateOfInterest,durationInt;


    //empty constructor
    public Policy() {}

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        this.depositAmount = depositAmount;
    }

    public int getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(int maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public double getDurationInt() {
        return durationInt;
    }

    public void setDurationInt(double durationInt) {
        this.durationInt = durationInt;
    }

    public String getDateOfDeposit() {
        return dateOfDeposit;
    }

    public String getReadableDateOfDeposit() {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfDeposit);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.format(date1);
        } catch (Exception e){
            e.printStackTrace();
        }
        return dateOfDeposit;
    }

    public void setDateOfDeposit(String dateOfDeposit) {
        this.dateOfDeposit = dateOfDeposit;
    }

    public String getDateOfMaturity() {
        return dateOfMaturity;
    }

    public String getReadableDateOfMaturity() {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfMaturity);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.format(date1);
        } catch (Exception e){
            e.printStackTrace();
        }
        return dateOfMaturity;
    }

    public void setDateOfMaturity(String dateOfMaturity) {
        this.dateOfMaturity = dateOfMaturity;
    }

    public int getPolicyType() {
        return policyType;
    }

    public void setPolicyType(int policyType) {
        this.policyType = policyType;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
