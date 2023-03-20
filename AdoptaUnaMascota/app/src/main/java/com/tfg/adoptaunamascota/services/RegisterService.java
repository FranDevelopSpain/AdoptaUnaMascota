package com.tfg.adoptaunamascota.services;

import com.tfg.adoptaunamascota.models.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterService {

    @FormUrlEncoded
    @POST("/register")
    Call<Register> USER_CALL_REGISTER(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("mail") String mail,
            @Field("password") String password,
            String password2);

    @FormUrlEncoded
    @GET("/register/users")
    Call<Register> REGISTER_CALL(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("mail") String mail,
            @Field("password") String password
    );

}
