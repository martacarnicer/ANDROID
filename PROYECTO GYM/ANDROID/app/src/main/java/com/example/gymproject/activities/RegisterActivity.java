package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymproject.R;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.presenters.RegisterPresenter;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.AuthService;
import com.example.gymproject.services.CentroService;
import com.example.gymproject.utils.FontUtil;
import com.example.gymproject.views.RegisterView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements RegisterView { // Implementar RegisterView

    private AuthService authService;
    private TextView textViewCentro; // Declaración de textViewCentro
    private Spinner spinnerCentro; // Asegúrate de declarar también el spinnerxº

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("RegisterActivity", "RegisterActivity iniciada");


        // Crear instancia de Retrofit
        authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);

        // Configurar los elementos del formulario
        TextView textViewTitulo = findViewById(R.id.textView3);
        TextView textViewNombre = findViewById(R.id.textViewNombre);
        TextView textViewApellido = findViewById(R.id.textViewApellido);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewPassword = findViewById(R.id.textViewPassword);
        TextView textViewPhone = findViewById(R.id.textViewPhone);
        TextView textViewPlan = findViewById(R.id.textViewPlan);
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etApellido = findViewById(R.id.etApellido);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etPhone = findViewById(R.id.etPhone);
        Spinner spinnerPlan = findViewById(R.id.spinnerPlan);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView textViewLogin = findViewById(R.id.textViewLogin);
        Spinner spinnerCentro = findViewById(R.id.spinnerCentro);
        textViewCentro = findViewById(R.id.textViewCentro); // Asegúrate de inicializar también textViewCentro


        // Aplicar la fuente Poppins a todos los elementos del formulario
        Typeface poppins = FontUtil.getPoppinsRegularTypeface(this);
        FontUtil.applyFontToViews(poppins, textViewNombre, textViewApellido, textViewEmail,
                textViewPassword, textViewPhone, textViewPlan, etNombre, etApellido, etEmail,
                etPassword, etPhone, btnRegister, textViewLogin);

        // Aplicar Poppins-SemiBold solo al título (textView3)
        Typeface poppinsSemiBold = FontUtil.getPoppinsSemiBoldTypeface(this);
        textViewTitulo.setTypeface(poppinsSemiBold);  // Solo para el título

        // Listener para redirigir a la pantalla de inicio de sesión
        textViewLogin.setOnClickListener(v -> {
            // Redirigir al LoginActivity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual para evitar que el usuario vuelva atrás
        });


        // Configurar el Spinner con opciones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.plan_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlan.setAdapter(adapter);


        spinnerPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPlan = parentView.getItemAtPosition(position).toString();
                if ("Básica".equals(selectedPlan)) {
                    textViewCentro.setVisibility(View.VISIBLE); // Mostrar el texto
                    spinnerCentro.setVisibility(View.VISIBLE); // Mostrar el spinner
                    cargarCentros(spinnerCentro); // Cargar los centros en el spinner
                } else {
                    textViewCentro.setVisibility(View.GONE); // Ocultar el texto
                    spinnerCentro.setVisibility(View.GONE); // Ocultar el spinner
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Aquí puedes dejarlo vacío si no hay ninguna acción a realizar
                textViewCentro.setVisibility(View.GONE); // Asegúrate de ocultar en caso de que no se seleccione nada
                spinnerCentro.setVisibility(View.GONE); // Asegúrate de ocultar en caso de que no se seleccione nada
            }
        });




        btnRegister.setOnClickListener(v -> {
            // Recoger los valores del formulario
            String nombre = etNombre.getText().toString().trim();
            String apellido = etApellido.getText().toString().trim();
            String email = etEmail.getText().toString().trim().toLowerCase();
            String password = etPassword.getText().toString().trim();
            String telefono = etPhone.getText().toString().trim();
            String tipoCuota = spinnerPlan.getSelectedItem().toString();
            Centro centroSeleccionado = (Centro) spinnerCentro.getSelectedItem(); // Obtenemos el centro seleccionado

            Long idCentro = centroSeleccionado != null ? centroSeleccionado.getIdCentro() : null; // Obtener el ID del centro

            // Validación para campos vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar si se ha seleccionado un plan válido
            if (tipoCuota.equals("Selecciona un tipo de cuota")) {
                Toast.makeText(RegisterActivity.this, "Por favor selecciona un tipo de cuota válido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar formato del email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(RegisterActivity.this, "Por favor ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear objeto Usuario
            Usuario usuario = new Usuario(nombre, apellido, email, password, telefono, tipoCuota);

            // Aquí debes crear la instancia del presenter
            RegisterPresenter registerPresenter = new RegisterPresenter(this); // Asegúrate de pasar `this` como RegisterView

            // Llamada al backend para registrar al usuario
            registerPresenter.register(nombre, apellido, email, password, telefono, tipoCuota, idCentro); // Ahora llama a register con todos los parámetros necesarios
        });
    }

    // Método para cargar los centros en el spinner
    private void cargarCentros(Spinner spinnerCentro) {
        CentroService centroService = RetrofitClient.getRetrofitInstance().create(CentroService.class);
        centroService.obtenerCentros().enqueue(new Callback<List<Centro>>() {
            @Override
            public void onResponse(Call<List<Centro>> call, Response<List<Centro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Centro> centros = response.body();
                    ArrayAdapter<Centro> adapter = new ArrayAdapter<>(RegisterActivity.this,
                            android.R.layout.simple_spinner_item, centros);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCentro.setAdapter(adapter);
                } else {
                    Toast.makeText(RegisterActivity.this, "Error al obtener los centros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Centro>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void showRegisterSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        // Puedes mostrar un loading spinner aquí si es necesario
    }

    @Override
    public void hideLoading() {
        // Ocultar el loading spinner aquí si es necesario
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finalizar la actividad actual para evitar que el usuario vuelva atrás
    }


}