package com.vinay.myfixeddeposits.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.vinay.myfixeddeposits.R;
import com.vinay.myfixeddeposits.model.Policy;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PolicyListAdapter extends RecyclerView.Adapter<PolicyListAdapter.PolicyViewHolder> {

    private List<Policy> policies = new ArrayList<>();

    public void injectPolicyList(List<Policy> policies){
        this.policies = policies;
        notifyDataSetChanged();
    }

    public void addNewPolicy(Policy policy){
        policies.add(policy);
        notifyDataSetChanged();
    }

    public void clearPolicyList(){
        policies.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.policy_row,parent,false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, int position) {
        holder.policyNumber.setText(policies.get(position).getCertificateNumber());
        holder.interestRate.setText(policies.get(position).getRateOfInterest() + "%");
        holder.maturityAmount.setText(NumberFormat.getCurrencyInstance(new Locale("en","IN")).format(policies.get(position).getMaturityAmount()).substring(0,NumberFormat.getCurrencyInstance(new Locale("en","IN")).format(policies.get(position).getMaturityAmount()).length()-3));
        holder.holder.setText(policies.get(position).getHolder());
        holder.depositDate.setText(policies.get(position).getReadableDateOfDeposit());
        holder.maturityDate.setText(policies.get(position).getReadableDateOfMaturity());
        holder.bankName.setText(policies.get(position).getBankName());
        try {
            holder.readableDuration.setText(policies.get(position).getReadableDuration());
        } catch (Exception e) {
            holder.readableDuration.setText("Err");
            e.printStackTrace();
        }
    }

    public void setFilter(List<Policy> policies) {
        this.policies.clear();
        this.policies.addAll(policies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return policies.size();
    }

    class PolicyViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView policyNumber,interestRate,maturityAmount,holder,depositDate,maturityDate,readableDuration,bankName;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            policyNumber = itemView.findViewById(R.id.policyNumber);
            interestRate = itemView.findViewById(R.id.interestRate);
            maturityAmount = itemView.findViewById(R.id.maturityAmount);
            holder = itemView.findViewById(R.id.holder);
            depositDate = itemView.findViewById(R.id.depositDate);
            maturityDate = itemView.findViewById(R.id.maturityDate);
            readableDuration = itemView.findViewById(R.id.readableDuration);
            bankName = itemView.findViewById(R.id.bankName);
        }
    }

}
