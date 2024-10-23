package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.adapters.ReservaAdapter;
import com.example.gymproject.entities.Reserva;
import com.example.gymproject.models.MisReservasModel;
import com.example.gymproject.presenters.MisReservasPresenter;
import com.example.gymproject.utils.FontUtil;
import com.example.gymproject.views.MisReservasView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ReservasActivity extends AppCompatActivity implements MisReservasView {
    private RecyclerView recyclerViewReservas;
    private ReservaAdapter reservaAdapter;
    private MisReservasPresenter presenter;
    private Long idUsuario;  // Variable para almacenar el ID del usuario

    private TextView textNoReservasProximas;
    private TextView textNoReservasPasadas;

    // Referencias a las pestañas
    private TextView tabProximas;
    private TextView tabPasadas;
    private Button btnVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        // Obtener el ID del usuario desde el Intent
        idUsuario = getIntent().getLongExtra("idUsuario", -1L);  // -1L por defecto si no se encuentra

        if (idUsuario == -1L) {
            mostrarError("Error: No se pudo obtener el ID del usuario.");
            return;
        }

        // Initialize views
        recyclerViewReservas = findViewById(R.id.recyclerViewReservas);
        textNoReservasProximas = findViewById(R.id.textNoReservasProximas);
        textNoReservasPasadas = findViewById(R.id.textNoReservasPasadas);

        tabProximas = findViewById(R.id.tabProximas);  // Referencias a las pestañas
        tabPasadas = findViewById(R.id.tabPasadas);

        reservaAdapter = new ReservaAdapter(new ArrayList<>(), this);
        recyclerViewReservas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReservas.setAdapter(reservaAdapter);
        btnVolver = findViewById(R.id.btnVolver);

        // Aplicar la fuente Poppins a todos los TextViews y Buttons
        Typeface poppinsRegular = FontUtil.getPoppinsRegularTypeface(this);
        Typeface poppinsSemiBold = FontUtil.getPoppinsSemiBoldTypeface(this);
        FontUtil.applyFontToViews(poppinsRegular, textNoReservasProximas, textNoReservasPasadas, tabProximas, tabPasadas, btnVolver);
        tabProximas.setTypeface(poppinsSemiBold);  // Pestaña activa en semi-bold
        tabPasadas.setTypeface(poppinsSemiBold);   // Pestaña activa en semi-bold


        // Inicializar presenter
        presenter = new MisReservasPresenter(this, new MisReservasModel());

        // Cargar las reservas iniciales (Próximas por defecto)
        cargarReservasProximas();
        setActiveTab(tabProximas);  // Poner la pestaña Próximas como activa al inicio

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(ReservasActivity.this, DashboardUsuarioActivity.class);
            intent.putExtra("idUsuario", idUsuario);  // Pasa el ID de usuario si es necesario
            startActivity(intent);
            finish();  // Finaliza la actividad actual
        });
    }

    public void cargarReservasProximas() {
        ocultarTodo();
        reservaAdapter.actualizarReservas(new ArrayList<>());  // Limpiar el adaptador
        presenter.cargarReservasProximas(idUsuario);  // Cargar las próximas reservas con el ID dinámico
        setActiveTab(tabProximas);  // Cambiar el estilo de las pestañas
    }

    public void cargarReservasPasadas() {
        ocultarTodo();
        reservaAdapter.actualizarReservas(new ArrayList<>());  // Limpiar el adaptador
        presenter.cargarReservasPasadas(idUsuario);  // Cargar las reservas pasadas con el ID dinámico
        setActiveTab(tabPasadas);  // Cambiar el estilo de las pestañas
    }


    public void mostrarProximas(View view) {
        cargarReservasProximas();  // Cargar las reservas próximas al cambiar de pestaña
    }

    public void mostrarPasadas(View view) {
        cargarReservasPasadas();  // Cargar las reservas pasadas al cambiar de pestaña
    }

    // Método para cambiar el estado activo de las pestañas
    private void setActiveTab(TextView activeTab) {
        tabProximas.setSelected(false);
        tabPasadas.setSelected(false);
        activeTab.setSelected(true);  // Marcar la pestaña seleccionada
    }

    // Método para ocultar todas las vistas hasta que los datos estén cargados
    private void ocultarTodo() {
        recyclerViewReservas.setVisibility(View.GONE);  // Ocultar el RecyclerView
        textNoReservasProximas.setVisibility(View.GONE);
        textNoReservasPasadas.setVisibility(View.GONE);
    }

    @Override
    public void mostrarReservasProximas(List<Reserva> reservas) {
        ocultarCargando();
        reservaAdapter.actualizarReservas(new ArrayList<>());  // Limpiar la lista antes de agregar nuevas reservas

        if (reservas != null && !reservas.isEmpty()) {
            recyclerViewReservas.setVisibility(View.VISIBLE);
            reservaAdapter.actualizarReservas(reservas);  // Agregar las nuevas reservas
            textNoReservasProximas.setVisibility(View.GONE);  // Ocultar el mensaje de "no hay próximas reservas"
        } else {
            textNoReservasProximas.setVisibility(View.VISIBLE);  // Mostrar el mensaje si no hay próximas reservas
        }
    }

    @Override
    public void mostrarReservasPasadas(List<Reserva> reservas) {
        ocultarCargando();
        reservaAdapter.actualizarReservas(new ArrayList<>());  // Limpiar la lista antes de agregar nuevas reservas

        if (reservas != null && !reservas.isEmpty()) {
            recyclerViewReservas.setVisibility(View.VISIBLE);
            reservaAdapter.actualizarReservas(reservas);  // Agregar las nuevas reservas pasadas
            textNoReservasPasadas.setVisibility(View.GONE);  // Ocultar el mensaje de "no hay pasadas reservas"
        } else {
            textNoReservasPasadas.setVisibility(View.VISIBLE);  // Mostrar el mensaje si no hay reservas pasadas
        }
    }


    @Override
    public void mostrarMensajeNoReservasProximas() {
        ocultarCargando();
        textNoReservasProximas.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostrarMensajeNoReservasPasadas() {
        ocultarCargando();
        textNoReservasPasadas.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostrarCargando() {
        findViewById(R.id.progressBarCargando).setVisibility(View.VISIBLE);
        recyclerViewReservas.setVisibility(View.GONE);  // Oculta el RecyclerView mientras se cargan los datos
    }

    @Override
    public void ocultarCargando() {
        findViewById(R.id.progressBarCargando).setVisibility(View.GONE);
        recyclerViewReservas.setVisibility(View.VISIBLE);  // Mostrar el RecyclerView una vez que los datos se hayan cargado
    }

    @Override
    public void mostrarError(String mensaje) {
        // Mostrar el mensaje de error
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
