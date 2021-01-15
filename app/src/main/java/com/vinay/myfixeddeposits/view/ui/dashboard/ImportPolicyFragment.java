package com.vinay.myfixeddeposits.view.ui.dashboard;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.FileUtils;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.vinay.myfixeddeposits.R;
import com.vinay.myfixeddeposits.Util.FileUtil;
import com.vinay.myfixeddeposits.model.Policy;

import java.io.File;
import java.util.List;

public class ImportPolicyFragment extends Fragment {

    private ImportPolicyViewModel importPolicyViewModel;
    private MaterialButton btnImport;
    private MaterialTextView policyCount;
    private static final int GALLERY=12;

    public static ImportPolicyFragment newInstance() {
        return new ImportPolicyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.import_policy_fragment, container, false);
        policyCount = v.findViewById(R.id.policyCount);
        btnImport = v.findViewById(R.id.btnImport);
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importPolicy();
            }
        });
        ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        return v;
    }


    private void importPolicy(){

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("text/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(i, "Select a File to Upload"),
                    GALLERY);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext().getApplicationContext(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Uri uploadFileUri = data.getData();
            File file = new File(FileUtil.getRealPath(getContext().getApplicationContext(),uploadFileUri));
            try {
                importPolicyViewModel.importPolicy(file);
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        importPolicyViewModel = new ViewModelProvider(this).get(ImportPolicyViewModel.class);
        importPolicyViewModel.getPolicies().observe(getActivity(), new Observer<List<Policy>>() {
            @Override
            public void onChanged(List<Policy> policies) {
                policyCount.setText(""+policies.size() + "Policies have been imported successfully..!!");
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getBackup();

                } else {
                    Log.d("yes","no");
                    Toast.makeText(getActivity(),"Enable permission",Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}