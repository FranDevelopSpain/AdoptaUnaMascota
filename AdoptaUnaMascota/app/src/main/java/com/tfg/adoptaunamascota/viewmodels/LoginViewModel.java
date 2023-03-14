package com.tfg.adoptaunamascota.viewmodels;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.tfg.adoptaunamascota.model.Login;

public class LoginViewModel extends BaseObservable {

    private Login login;

    private String sucessMessage = "Se ha accedido con éxito";
    private String errorMessage = "Usuario o contraseña no valido";

    @Bindable
    private  String toastMessage = null;

    public String getToastMessage(){
        return toastMessage;
    }

    private void setToastMessage(String toastMessage){
        this.toastMessage = toastMessage;
    }

    public void setUserEmail(String mail){
        login.setUser(mail);
    }
    @Bindable
    public String getUserEmail(){
        return login.getUser();
    }
    @Bindable
    public String getUserPassword(){
        return login.getPassword();
    }

    public void setUserPassword(String password){
        login.setPassword(password);
    }
    public LoginViewModel(){
        login = new Login("","");
    }
    public void onLoginClicked(){
        if(isInputDataValid())
            setToastMessage(sucessMessage);
        else
            setToastMessage(errorMessage);
    }
    public boolean isInputDataValid(){
        return !TextUtils.isEmpty(getUserEmail()) && Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches() && getUserPassword().length() > 5;
    }
}
