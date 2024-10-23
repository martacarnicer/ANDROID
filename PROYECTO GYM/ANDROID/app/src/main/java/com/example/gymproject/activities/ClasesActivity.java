package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.ClasesAdapter;
import com.example.gymproject.entities.Acceso;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Clase;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.models.ClasesModel;
import com.example.gymproject.presenters.AccesoPresenter;
import com.example.gymproject.presenters.ClasesPresenter;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.ApiService;
import com.example.gymproject.views.AccesoView;
import com.example.gymproject.views.ClasesView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClasesActivity extends AppCompatActivity implements ClasesView, AccesoView {
    private RecyclerView recyclerViewClases;
    private ClasesAdapter clasesAdapter;
    private ClasesPresenter clasesPresenter;
    private AccesoPresenter accesoPresenter;
    private ApiService apiService;
    private String tipoCuota;
    private List<Clase> todasLasClases; // Para almacenar todas las clases obtenidas
    private Spinner spinnerClub;


    // Nueva lista para almacenar las clases por día
    private List<Clase> clasesPorDia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clases);

        // Inicialización de componentes de UI
        recyclerViewClases = findViewById(R.id.recyclerViewClases);
        recyclerViewClases.setLayoutManager(new LinearLayoutManager(this));
        spinnerClub = findViewById(R.id.spinnerClub);

        // Configuración del botón para volver al Dashboard
        Button btnVolverDashboard = findViewById(R.id.btnVolverDashboard);
        btnVolverDashboard.setOnClickListener(v -> {
            // Intent para regresar al Dashboard
            Intent intent = new Intent(ClasesActivity.this, DashboardUsuarioActivity.class);
            intent.putExtra("idUsuario", obtenerIdUsuario());  // Pasar el ID del usuario de vuelta al dashboard
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  // Limpiar la pila de actividades si es necesario
            startActivity(intent);
            finish();
        });

        // Obtener ID del usuario desde el Intent
        Long idUsuario = getIntent().getLongExtra("idUsuario", -1);

        // Si el ID es inválido, manejar el caso (quizás mostrar un mensaje o redirigir)
        if (idUsuario == -1) {
            Toast.makeText(this, "No se ha encontrado el usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicialización de presentadores y otros componentes
        clasesPresenter = new ClasesPresenter(this, new ClasesModel());
        accesoPresenter = new AccesoPresenter(this);
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Obtener los accesos del usuario y cargar sus clases
        accesoPresenter.obtenerAccesos(idUsuario);
        obtenerUsuario(idUsuario);  // Cargar detalles del usuario
        cargarClasesDelDia();  // Cargar las clases del día

        // Configuración del RecyclerView y el adaptador
        clasesAdapter = new ClasesAdapter(new ArrayList<>(), this, clase -> {
            // Crear el Intent para abrir InscribirActivity
            Intent intent = new Intent(ClasesActivity.this, InscribirActivity.class);
            intent.putExtra("idClase", clase.getIdClase());
            intent.putExtra("idUsuario", idUsuario);  // Pasar el ID del usuario
            startActivity(intent);
        });

        recyclerViewClases.setAdapter(clasesAdapter);
        inicializarDias();
    }

    // Método actualizado para obtener el día de la semana normalizado
    private String obtenerDiaActualNormalizado() {
        // Usar la zona horaria de España (CET/CEST)
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"), new Locale("es", "ES"));
        Date now = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("es", "ES"));
        String diaActual = sdf.format(now);

        Log.d("ClasesActivity", "Fecha y hora actual: " + now);
        Log.d("ClasesActivity", "Día actual obtenido: " + diaActual);

        return diaActual.substring(0, 1).toUpperCase() + diaActual.substring(1).toLowerCase();
    }


    private void cargarClasesDelDia() {
        String diaActual = obtenerDiaActualNormalizado();
        Centro centroSeleccionado = (Centro) spinnerClub.getSelectedItem();

        if (centroSeleccionado != null) {
            cargarClasesPorCentroYDia(centroSeleccionado.getIdCentro(), diaActual);
        }
    }






    private Long obtenerIdUsuario() {
        return getIntent().getLongExtra("idUsuario", -1);
    }

    private void cargarCentros() {
        Long idUsuario = obtenerIdUsuario(); // Obtén el ID del usuario
        accesoPresenter.obtenerAccesos(idUsuario); // Llamada para obtener los accesos
    }


    private void configurarSpinnerCentros(List<Centro> centros) {
        ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(this, android.R.layout.simple_spinner_item, centros) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setText(centros.get(position).getNombreCentro());
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClub.setAdapter(adapter);

        // Evitamos múltiples llamadas al cargar por primera vez
        spinnerClub.post(() -> {
            // Selecciona el primer centro por defecto y carga sus clases para el día actual
            if (!centros.isEmpty()) {
                Centro centroSeleccionado = centros.get(0); // Seleccionamos el primer centro por defecto
                spinnerClub.setSelection(0);

                // Obtener el día actual usando obtenerDiaActualNormalizado()
                String diaActual = obtenerDiaActualNormalizado();
                cargarClasesPorCentroYDia(centroSeleccionado.getIdCentro(), diaActual); // Cargar clases automáticamente

                highlightDia(diaActual); // Resaltar el día actual
            }
        });


        spinnerClub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Centro centroSeleccionado = (Centro) parent.getItemAtPosition(position);

                // Solo cargar clases si ha cambiado de centro
                if (centroSeleccionado != null) {
                    String diaActual = obtenerDiaActualNormalizado();
                    cargarClasesPorCentroYDia(centroSeleccionado.getIdCentro(), diaActual);  // Cargar clases al cambiar de centro
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona nada
            }
        });


    }




    private void cargarClasesPorCentro(Long idCentro) {
        Log.d("ClasesActivity", "Cargando clases para el centro: " + idCentro);
        clasesPresenter.obtenerClasesPorCentro(idCentro);
    }


    private void obtenerUsuario(Long idUsuario) {
        apiService.getUsuarioPorId(idUsuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    mostrarAccesos(usuario);
                } else {
                    mostrarError("Error al obtener los datos del usuario");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mostrarError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void mostrarClases(List<Clase> clases) {
        // Ocultar ProgressBar después de obtener los datos
        setIsLoadingClases(false);

        if (clases != null && !clases.isEmpty()) {
            recyclerViewClases.setVisibility(View.VISIBLE);
            clasesAdapter.updateData(clases);
        } else {
            recyclerViewClases.setVisibility(View.GONE);
            mostrarError("No se encontraron clases para este día y centro.");
        }
    }


    private void mostrarAccesos(Usuario usuario) {
        this.tipoCuota = usuario.getTipoCuota(); // Asegúrate de guardar el tipo de cuota
        accesoPresenter.obtenerAccesos(usuario.getIdUsuario());

        // Cargar automáticamente las clases del día actual
        String diaActual = new SimpleDateFormat("EEEE", new Locale("es", "ES")).format(new Date());
        Centro centroSeleccionado = (Centro) spinnerClub.getSelectedItem();
        if (centroSeleccionado != null) {
            obtenerClasesPorDia(diaActual);
        }
    }
    @Override
    public void mostrarAccesos(List<Acceso> accesos) {
        List<Centro> centrosDisponibles = new ArrayList<>();

        if ("Básica".equals(tipoCuota)) {
            if (!accesos.isEmpty()) {
                centrosDisponibles.add(accesos.get(0).getCentro());
                spinnerClub.setEnabled(false);
            }
        } else if ("Premium".equals(tipoCuota)) {
            for (Acceso acceso : accesos) {
                centrosDisponibles.add(acceso.getCentro());
            }
            spinnerClub.setEnabled(true);
        }

        if (!centrosDisponibles.isEmpty()) {
            configurarSpinnerCentros(centrosDisponibles);

            // Asegurarnos de que solo cargue una vez
            spinnerClub.post(() -> {
                // Seleccionar automáticamente el primer centro
                Centro centroSeleccionado = centrosDisponibles.get(0);
                spinnerClub.setSelection(0);

                // Obtener el día actual y cargar las clases automáticamente
                String diaActual = obtenerDiaActualNormalizado();  // Usamos obtenerDiaActualNormalizado
                highlightDia(diaActual);  // Resaltar el día actual
                cargarClasesPorCentroYDia(centroSeleccionado.getIdCentro(), diaActual); // Cargar clases
            });
        } else {
            mostrarError("No hay centros disponibles para este usuario.");
        }
    }




    private boolean isLoadingClases = false;

    private void cargarClasesPorCentroYDia(Long idCentro, String dia) {
        if (isLoadingClases) return;

        isLoadingClases = true;
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE); // Mostrar progreso

        String diaCapitalizado = dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();
        clasesPresenter.obtenerClasesPorDiaYCentro(diaCapitalizado, idCentro);
    }

    public void setIsLoadingClases(boolean isLoading) {
        this.isLoadingClases = isLoading;
        findViewById(R.id.progressBar).setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }




    @Override
    public void mostrarError(String mensaje) {
        setIsLoadingClases(false); // Asegurarse de ocultar ProgressBar en caso de error también
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void inicializarDias() {
        findViewById(R.id.textLunes).setOnClickListener(view -> obtenerClasesPorDia("Lunes"));
        findViewById(R.id.textMartes).setOnClickListener(view -> obtenerClasesPorDia("Martes"));
        findViewById(R.id.textMiercoles).setOnClickListener(view -> obtenerClasesPorDia("Miércoles"));
        findViewById(R.id.textJueves).setOnClickListener(view -> obtenerClasesPorDia("Jueves"));
        findViewById(R.id.textViernes).setOnClickListener(view -> obtenerClasesPorDia("Viernes"));
        findViewById(R.id.textSabado).setOnClickListener(view -> obtenerClasesPorDia("Sábado"));
        findViewById(R.id.textDomingo).setOnClickListener(view -> obtenerClasesPorDia("Domingo"));
    }
    private void highlightDia(String dia) {
        resetDias(); // Asegurarse de reiniciar los colores

        // Resaltar el día actual usando ContextCompat.getColor() en lugar de getColor()
        switch (dia) {
            case "Lunes":
                findViewById(R.id.textLunes).setBackgroundColor(ContextCompat.getColor(this, R.color.green_pressed));
                break;
            case "Martes":
                findViewById(R.id.textMartes).setBackgroundColor(ContextCompat.getColor(this, R.color.green_pressed));
                break;
            case "Miércoles":
                findViewById(R.id.textMiercoles).setBackgroundColor(ContextCompat.getColor(this, R.color.green_pressed));
                break;
            case "Jueves":
                findViewById(R.id.textJueves).setBackgroundColor(ContextCompat.getColor(this, R.color.green_pressed));
                break;
            case "Viernes":
                findViewById(R.id.textViernes).setBackgroundColor(ContextCompat.getColor(this, R.color.green_pressed));
                break;
            case "Sábado":
                findViewById(R.id.textSabado).setBackgroundColor(ContextCompat.getColor(this, R.color.green_pressed));
                break;
            case "Domingo":
                findViewById(R.id.textDomingo).setBackgroundColor(ContextCompat.getColor(this, R.color.green_pressed));
                break;
        }
    }




    private void resetDias() {
        int defaultColor = ContextCompat.getColor(this, R.color.gray_default); // O el color que hayas definido para el fondo
        findViewById(R.id.textLunes).setBackgroundColor(defaultColor);
        findViewById(R.id.textMartes).setBackgroundColor(defaultColor);
        findViewById(R.id.textMiercoles).setBackgroundColor(defaultColor);
        findViewById(R.id.textJueves).setBackgroundColor(defaultColor);
        findViewById(R.id.textViernes).setBackgroundColor(defaultColor);
        findViewById(R.id.textSabado).setBackgroundColor(defaultColor);
        findViewById(R.id.textDomingo).setBackgroundColor(defaultColor);
    }


    private void obtenerClasesPorDia(String dia) {
        // Comprobación para asegurarse de que el spinner no es null
        if (spinnerClub == null || spinnerClub.getSelectedItem() == null) {
            mostrarError("Por favor, selecciona un centro antes de elegir un día.");
            return;
        }

        Centro centroSeleccionado = (Centro) spinnerClub.getSelectedItem();

        if (centroSeleccionado != null) {
            // Capitalizar el primer carácter del día y convertir el resto a minúsculas
            String diaCapitalizado = dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();
            clasesPresenter.obtenerClasesPorDiaYCentro(diaCapitalizado, centroSeleccionado.getIdCentro());

            // Resaltar el día seleccionado
            highlightDia(diaCapitalizado);
        } else {
            mostrarError("Por favor, selecciona un centro antes de elegir un día.");
        }
    }




}

