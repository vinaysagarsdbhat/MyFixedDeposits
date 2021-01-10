package com.vinay.myfixeddeposits.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vinay.myfixeddeposits.model.User;

public class LoginViewModel extends ViewModel {

    public User user;
    private MutableLiveData<String> errorMessageUserName = new MutableLiveData<>();
    private MutableLiveData<String> errorMessagePassword = new MutableLiveData<>();

    public MutableLiveData<String> getErrorMessageUserName() {
        return errorMessageUserName;
    }

    public MutableLiveData<String> getErrorMessagePassword() {
        return errorMessagePassword;
    }

    public boolean validateUser(User user){
        if(!user.getUserName().equals(this.user.getUserName())){
            errorMessageUserName.setValue("Incorrect username");
            errorMessagePassword.setValue("");
            return false;
        } else if(!user.getPassword().equals(this.user.getPassword())){
            errorMessagePassword.setValue("Incorrect password..!!");
            errorMessageUserName.setValue("");
            return false;
        }
        resetErrorMessage();
        return true;
    }

    public void resetErrorMessage(){
        errorMessageUserName.setValue("");
        errorMessagePassword.setValue("");
    }

    public void getUser(){
        user = new User("Vinay","Vinay");
    }
}
