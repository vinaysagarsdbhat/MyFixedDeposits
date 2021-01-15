package com.vinay.myfixeddeposits.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.vinay.myfixeddeposits.R;
import com.vinay.myfixeddeposits.model.Policy;

import java.util.ArrayList;
import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return policies.size();
    }

    class PolicyViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView policyNumber,interestRate;

        public PolicyViewHolder(@NonNull View itemView) {
            super(itemView);
            policyNumber = itemView.findViewById(R.id.policyNumber);
            interestRate = itemView.findViewById(R.id.interestRate);
        }
    }

}
