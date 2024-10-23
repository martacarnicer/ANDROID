package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.NotificacionAdapter;
import com.example.gymproject.contratosPresenter.ContractNotificacionPresenter;
import com.example.gymproject.entities.Notificacion;
import com.example.gymproject.presenters.NotificacionPresenter;
import com.example.gymproject.views.NotificacionView;

import java.util.Collections;
import java.util.List;

public class NotificacionActivity extends AppCompatActivity implements NotificacionView {

    private ContractNotificacionPresenter presenter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView avisoNotificaciones;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewNotificaciones);
        avisoNotificaciones = findViewById(R.id.avisoNotificaciones);
        btnVolver = findViewById(R.id.btnVolver);
        progressBar = findViewById(R.id.progressBar);  // Aquí inicializamos el ProgressBar

        // Aplicar la fuente Poppins
        Typeface poppinsRegular = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        avisoNotificaciones.setTypeface(poppinsRegular);
        btnVolver.setTypeface(poppinsRegular);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener el id del usuario
        Long idUsuario = getIntent().getLongExtra("idUsuario", -1L);  // Asegúrate de obtener el ID del Intent

        if (idUsuario != -1L) {
            // Inicializar el presenter y obtener las notificaciones
            presenter = new NotificacionPresenter(this);
            presenter.obtenerNotificaciones(idUsuario);
        } else {
            Toast.makeText(this, "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show();
        }

        // Acción del botón "Volver"
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(NotificacionActivity.this, DashboardUsuarioActivity.class);
            intent.putExtra("idUsuario", idUsuario);  // Pasar de nuevo el ID al Dashboard
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void mostrarNotificaciones(List<Notificacion> notificaciones) {
        // Invertir la lista para que la notificación más reciente aparezca arriba
        Collections.reverse(notificaciones);

        // Configurar el adaptador con la lista invertida
        NotificacionAdapter adapter = new NotificacionAdapter(notificaciones);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarCargando() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarCargando() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void mostrarAvisoNotificaciones(boolean hayNotificaciones) {
        if (hayNotificaciones) {
            avisoNotificaciones.setText("Tienes nuevas notificaciones");
            avisoNotificaciones.setVisibility(View.VISIBLE);
        } else {
            avisoNotificaciones.setVisibility(View.GONE);
        }
    }
}
