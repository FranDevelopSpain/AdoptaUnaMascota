package com.tfg.adoptaunamascota.services;

import com.tfg.adoptaunamascota.Adapter;
import com.tfg.adoptaunamascota.models.Admin;
import com.tfg.adoptaunamascota.models.Register;
import com.tfg.adoptaunamascota.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

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

}
