package com.tfg.adoptaunamascota.viewmodels;

import android.widget.EditText;

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

    public EditText setUserEmail(String mail){
        login.setMail(mail);
        return null;
    }
    @Bindable
    public String getUserEmail(){
        return login.getMail();
    }
    @Bindable
    public String getUserPassword(){
        return login.getPassword();
    }

    public EditText setUserPassword(String password){
        login.setPassword(password);
        return null;
    }
    public LoginViewModel(Login login){
        this.login = login;
        if (validateIfIsAdmin()||validateIfOtherUser()){
            setToastMessage(sucessMessage);
        }else{
            setToastMessage(errorMessage);
        }
    }
    public boolean validateIfIsAdmin() {
        boolean validar = false;
        if (login.setPassword("admin")) {
            validar = true;
        }else if(login.setMail("admin@mail.com")){
            validar = true;
        }else{
            validar=false;
        }
        return validar;
    }
    public boolean validateIfOtherUser() {
        boolean validar = false;
        if (login.getMail().isEmpty()) {
        }else if(login.getPassword().isEmpty()){
        }else{
            validar=true;
        }
        return validar;
    }
}
