package com.tfg.adoptaunamascota.views.home.crudAdmin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.UserAdapter;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManagementActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private List<User> userList;
    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        userRecyclerView = findViewById(R.id.user_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el adaptador con una lista vacía
        userAdapter = new UserAdapter(new ArrayList<>());
        userRecyclerView.setAdapter(userAdapter);

        String baseUrl = "http://10.0.2.2:8080";

        userRepository = new UserRepository(this, baseUrl);

        userRepository.getUsers(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    userList = response.body();

                    // Actualiza el adaptador con la lista de usuarios
                    userAdapter.setUserList(userList);

                    // Notifica al RecyclerView que los datos han cambiado
                    userAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(UserManagementActivity.this, "Error de respuesta: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("UserManagementActivity", "Error al obtener usuarios", t);
            }
        });
    }
}
