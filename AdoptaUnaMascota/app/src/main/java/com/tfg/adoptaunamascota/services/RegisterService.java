package com.tfg.adoptaunamascota.services;

import com.tfg.adoptaunamascota.models.Register;
import com.tfg.adoptaunamascota.models.User;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("/register")
    Call<User> USER_CALL_REGISTER(
            Register userData);

    @FormUrlEncoded
    @GET("/register/users")
    Call<Register> REGISTER_CALL_USERS(
            Register userData);
    @GET("/register/users/passwords")
    Call<Register> REGISTER_CALL_USERS_PASSWORD(
            Register userData
    );

}
