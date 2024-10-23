package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.example.gymproject.presenters.DashboardPresenter;
import com.example.gymproject.views.DashboardView;

public class DashboardUsuarioActivity extends AppCompatActivity implements DashboardView {

    private TextView textViewNombreUsuario, textMisReservas, textViewSaludo, textMisClases, textNotificaciones, textMiPerfil;
    private Button btnSalirApp;
    private DashboardPresenter dashboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_usuario);

        // Referenciar los TextViews y el botón
        textViewSaludo = findViewById(R.id.textViewSaludo);
        textMisReservas = findViewById(R.id.textMisReservas);
        textViewNombreUsuario = findViewById(R.id.textViewNombreUsuario);
        textMisClases = findViewById(R.id.textMisClases);
        textNotificaciones = findViewById(R.id.textNotificaciones);
        textMiPerfil = findViewById(R.id.textMiPerfil);
        btnSalirApp = findViewById(R.id.btnSalirApp);

        // Aplicar la fuente Poppins-SemiBold al saludo y nombre
        Typeface poppinsSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        textViewSaludo.setTypeface(poppinsSemiBold);
        textViewNombreUsuario.setTypeface(poppinsSemiBold);

        // Aplicar la fuente Poppins-Medium a los textos de las secciones
        Typeface poppinsMedium = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf");
        textMisClases.setTypeface(poppinsMedium);
        textNotificaciones.setTypeface(poppinsMedium);
        textMiPerfil.setTypeface(poppinsMedium);
        textMisReservas.setTypeface(poppinsMedium);

        // Aplicar la fuente Poppins-Regular al botón de "Salir de la App"
        Typeface poppinsRegular = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        btnSalirApp.setTypeface(poppinsRegular);

        // Inicializar el Presenter
        dashboardPresenter = new DashboardPresenter(this);

        // Obtener el ID del usuario desde el Intent
        Intent intent = getIntent();
        Long idUsuario = intent.getLongExtra("idUsuario", -1L);  // Por defecto es -1L si no se encuentra el ID

        if (idUsuario != -1L) {
            // Solicitar los datos del usuario
            dashboardPresenter.obtenerDatosUsuario(idUsuario);
        } else {
            showError("Error al obtener el ID del usuario");
        }

        // Listener para "Mis Clases"
        textMisClases.setOnClickListener(v -> {
            if (idUsuario != -1L) {
                Intent clasesIntent = new Intent(DashboardUsuarioActivity.this, ClasesActivity.class);
                clasesIntent.putExtra("idUsuario", idUsuario);  // Asegúrate de pasar el ID
                startActivity(clasesIntent);
            } else {
                showError("Error al obtener el ID del usuario");
            }
        });

        // Listener para "Mis Reservas"
        textMisReservas.setOnClickListener(v -> {
            if (idUsuario != -1L) {
                Intent reservasIntent = new Intent(DashboardUsuarioActivity.this, ReservasActivity.class);
                reservasIntent.putExtra("idUsuario", idUsuario);  // Pasar el ID del usuario
                startActivity(reservasIntent);
            } else {
                showError("Error al obtener el ID del usuario");
            }
        });

        // Listener para "Notificaciones"
        textNotificaciones.setOnClickListener(v -> {
            if (idUsuario != -1L) {
                Intent notificacionesIntent = new Intent(DashboardUsuarioActivity.this, NotificacionActivity.class);
                notificacionesIntent.putExtra("idUsuario", idUsuario);  // Pasar el ID del usuario
                startActivity(notificacionesIntent);
            } else {
                showError("Error al obtener el ID del usuario");
            }
        });

        // Listener para "Mi Perfil"
        textMiPerfil.setOnClickListener(v -> {
            if (idUsuario != -1L) {
                Intent perfilIntent = new Intent(DashboardUsuarioActivity.this, PerfilActivity.class);
                perfilIntent.putExtra("idUsuario", idUsuario);  // Pasar el ID del usuario
                startActivity(perfilIntent);
            } else {
                showError("Error al obtener el ID del usuario");
            }
        });

        // Listener para "Salir de la App"
        btnSalirApp.setOnClickListener(v -> {
            finishAffinity();  // Cierra la aplicación
        });
    }

    @Override
    public void showUserDetails(String nombre) {
        textViewNombreUsuario.setText(nombre + "!");
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        // Mostrar un indicador de carga si es necesario
    }

    @Override
    public void hideLoading() {
        // Ocultar el indicador de carga si es necesario
    }

    @Override
    public void mostrarAvisoNotificaciones(boolean hayNotificaciones) {
        if (hayNotificaciones) {
            textNotificaciones.setText("Notificaciones (Nuevas)");
            textNotificaciones.setTextColor(getResources().getColor(R.color.red_cancelar, null));
        } else {
            textNotificaciones.setText("Notificaciones");
            textNotificaciones.setTextColor(getResources().getColor(R.color.black, null));
        }
    }
}
