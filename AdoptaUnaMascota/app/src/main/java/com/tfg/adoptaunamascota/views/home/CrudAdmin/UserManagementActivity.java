package com.tfg.adoptaunamascota.views.home.CrudAdmin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.repository.UserRepository;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManagementActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        userRepository = new UserRepository(this, "http://192.168.43.1:300/");

        userRepository.getUsers(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    userList = response.body();

                    // Mostrar la lista de usuarios en la vista
                    // (por ejemplo, utilizando un RecyclerView o ListView)
                } else {
                    // Manejar el error de respuesta
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Manejar el error de la solicitud
            }
        });
    }
}
