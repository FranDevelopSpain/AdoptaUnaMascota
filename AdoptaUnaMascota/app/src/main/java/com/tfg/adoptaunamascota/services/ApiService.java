package com.tfg.adoptaunamascota.services;

import com.tfg.adoptaunamascota.models.Admin;
import com.tfg.adoptaunamascota.models.Register;
import com.tfg.adoptaunamascota.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    Call<User> USER_CALL(
        @Field("mail") String mail,
        @Field("password") String password
    );
    @FormUrlEncoded
    @POST("/login")
    Call<Admin> ADMIN_CALL(
            @Field("mail") String mail,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("/register")
    Call<User> USER_CALL_REGISTER(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("mail") String mail,
            @Field("password") String password);

    @FormUrlEncoded
    @GET("/register/users")
    Call<Register> REGISTER_CALL_USERS(
            Register userData);
    @GET("/register/users/passwords")
    Call<Register> REGISTER_CALL_USERS_PASSWORD(
            Register userData
    );

}
