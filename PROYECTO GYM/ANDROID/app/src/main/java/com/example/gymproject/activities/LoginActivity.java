package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.presenters.LoginPresenter;
import com.example.gymproject.utils.FontUtil;
import com.example.gymproject.views.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private TextView textViewRegister, textViewTitulo, textViewEmail, textViewPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewTitulo = findViewById(R.id.textView3);
        textViewEmail = findViewById(R.id.textView4);  // El campo de texto para "Email"
        textViewPassword = findViewById(R.id.textView5);  // El campo de texto para "Contraseña"

        // Aplicar Poppins-Regular a la mayoría de las vistas
        Typeface poppins = FontUtil.getPoppinsRegularTypeface(this);
        FontUtil.applyFontToViews(poppins, textViewRegister, btnLogin, textViewEmail, textViewPassword);

        // Aplicar Poppins-SemiBold solo al título (textView3)
        Typeface poppinsSemiBold = FontUtil.getPoppinsSemiBoldTypeface(this);
        textViewTitulo.setTypeface(poppinsSemiBold);  // Solo para el título

        // Si también deseas aplicar la fuente a los EditText:
        editTextEmail.setTypeface(poppins);
        editTextPassword.setTypeface(poppins);



        // Inicializar el Presenter
        loginPresenter = new LoginPresenter(this);

        // Configurar evento del botón de login
        btnLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            loginPresenter.login(email, password);
        });

        textViewRegister.setOnClickListener(v -> {
            Log.d("LoginActivity", "Register text clicked"); // Agrega este Log
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            Log.d("LoginActivity", "Lanzando RegisterActivity");
            startActivity(intent);
            Log.d("LoginActivity", "RegisterActivity lanzada");

        });


    }


    @Override
    public void showLoginSuccess(Usuario usuario) {
        Toast.makeText(this, "Inicio de sesión exitoso: " + usuario.getNombre(), Toast.LENGTH_SHORT).show();

        // Redirigir al Dashboard y pasar el ID y nombre del usuario
        Intent intent = new Intent(LoginActivity.this, DashboardUsuarioActivity.class);
        intent.putExtra("idUsuario", usuario.getIdUsuario());
        intent.putExtra("nombreUsuario", usuario.getNombre()); // Pasar el nombre del usuario
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        // Mostrar un ProgressBar si es necesario
    }

    @Override
    public void hideLoading() {
        // Ocultar el ProgressBar si es necesario
    }
}
