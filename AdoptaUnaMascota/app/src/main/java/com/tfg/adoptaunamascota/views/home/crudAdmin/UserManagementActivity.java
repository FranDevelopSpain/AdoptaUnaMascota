package com.tfg.adoptaunamascota.views.home.crudAdmin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
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
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        userRecyclerView = findViewById(R.id.recyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el adaptador con una lista vacía y pasa el contexto y la instancia de UserManagementActivity
        userAdapter = new UserAdapter(new ArrayList<>(), this, this);
        userRecyclerView.setAdapter(userAdapter);

        String baseUrl = "http://10.0.2.2:8080";

        userRepository = new UserRepository(this, baseUrl);
        Button addUserButton = findViewById(R.id.add_user_button);
        Button updateUserButton = findViewById(R.id.btnUpdate);
        Button deleteUserButton = findViewById(R.id.btnDelete);

        getUsers();

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddUserDialog();
            }
        });

        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User selectedUser = userAdapter.getSelectedUser();
                if (selectedUser != null) {
                    showUpdateUserDialog(selectedUser);
                } else {
                    Toast.makeText(UserManagementActivity.this, "Por favor, seleccione un usuario para actualizar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User selectedUser = userAdapter.getSelectedUser();
                if (selectedUser != null) {
                    deleteUser(selectedUser);
                } else {
                    Toast.makeText(UserManagementActivity.this, "Por favor, seleccione un usuario para eliminar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setSelectedUser(User user) {
        this.selectedUser = user;
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.dialog_user_name);
        final EditText surnameEditText = dialogView.findViewById(R.id.dialog_user_surname);
        final EditText emailEditText = dialogView.findViewById(R.id.dialog_user_email);
        final EditText passwordEditText = dialogView.findViewById(R.id.dialog_user_password);

        builder.setTitle("Agregar Usuario");
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString();
                String surname = surnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(email.equalsIgnoreCase("admin@mail.com")){
                    Toast.makeText(UserManagementActivity.this, "Ya existe un usuario administrador", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User(name, surname, email, password);

                    userRepository.createUser(user, new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                getUsers(); // Actualiza la lista de usuarios
                            } else {
                                Toast.makeText(UserManagementActivity.this, "Error al agregar usuario: " + response.code(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(UserManagementActivity.this, "Error al agregar usuario", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showUpdateUserDialog(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.dialog_user_name);
        final EditText surnameEditText = dialogView.findViewById(R.id.dialog_user_surname);
        final EditText emailEditText = dialogView.findViewById(R.id.dialog_user_email);

        nameEditText.setText(user.getName());
        surnameEditText.setText(user.getSurname());
        emailEditText.setText(user.getEmail());

        builder.setTitle("Actualizar Usuario");
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedName = nameEditText.getText().toString();
                String updatedSurname = surnameEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();

                user.setName(updatedName);
                user.setSurname(updatedSurname);
                user.setEmail(updatedEmail);


                userRepository.updateUser(user.getId(), user, new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            getUsers(); // Actualiza la lista de usuarios
                        } else {
                            Toast.makeText(UserManagementActivity.this, "Error al actualizar usuario: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UserManagementActivity.this, "Error al actualizar usuario", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void deleteUser(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Usuario");
        builder.setMessage("¿Estás seguro de que deseas eliminar este usuario?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        userRepository.deleteUser(user.getId(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getUsers(); // Actualiza la lista de usuarios
                } else {
                    Toast.makeText(UserManagementActivity.this, "Error al eliminar usuario: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserManagementActivity.this, "Error al eliminar usuario", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUsers() {
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
