package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gymproject.R;
import com.example.gymproject.contratosPresenter.ContractInscribirPresenter;
import com.example.gymproject.models.InscribirModel;
import com.example.gymproject.presenters.InscribirPresenter;

import com.example.gymproject.utils.FontUtil;
import com.example.gymproject.views.InscribirView;
import com.squareup.picasso.Picasso;

public class InscribirActivity extends AppCompatActivity implements InscribirView {

    private TextView textNombreClase, textHoraClase, textInfoClase, textFechaClase, textEmailInstructor, textDescripcionClase, textPlazasDisponibles;
    private Button btnReservar, btnVolver;
    private ContractInscribirPresenter presenter;
    private ImageView imageViewInstructor;  // ImageView para mostrar la foto del instructor

    private Long idUsuario, idClase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscribir);

        // Obtener los IDs de la clase y el usuario desde el Intent
        idClase = getIntent().getLongExtra("idClase", -1);
        idUsuario = getIntent().getLongExtra("idUsuario", -1);

        // Inicializar vistas
        textNombreClase = findViewById(R.id.textNombreClase);
        textHoraClase = findViewById(R.id.textHoraClase);
        textEmailInstructor = findViewById(R.id.textEmailInstructor);
        textDescripcionClase = findViewById(R.id.textDescripcionClase);
        textPlazasDisponibles = findViewById(R.id.textPlazasDisponibles);
        btnReservar = findViewById(R.id.btnReservar);
        btnVolver = findViewById(R.id.btnVolver);
        textFechaClase = findViewById(R.id.textFechaClase);
        imageViewInstructor = findViewById(R.id.imageViewInstructor);  // Inicializar el ImageView


        // Aplicar fuentes
        Typeface poppinsRegular = FontUtil.getPoppinsRegularTypeface(this);
        FontUtil.applyFontToViews(poppinsRegular, textHoraClase, textFechaClase, textInfoClase, textEmailInstructor, textDescripcionClase, textPlazasDisponibles, btnVolver);
        Typeface poppinsSemiBold = FontUtil.getPoppinsSemiBoldTypeface(this);
        textNombreClase.setTypeface(poppinsSemiBold);
        btnReservar.setTypeface(poppinsSemiBold);

        // Inicializar el Presenter y Model
        presenter = new InscribirPresenter(this, new InscribirModel());

        // Cargar los detalles de la clase
        presenter.cargarDetallesClase(idClase);

        // Verificar si el usuario ya está inscrito
        presenter.verificarInscripcion(idUsuario, idClase);

        // Acción del botón de reservar/cancelar
        btnReservar.setOnClickListener(v -> {
            presenter.manejarReserva(idUsuario, idClase);
        });

        // Acción del botón de volver
        btnVolver.setOnClickListener(v -> {
            // Redirige a ClasesActivity y pasa el idUsuario
            Intent intent = new Intent(InscribirActivity.this, ClasesActivity.class);
            intent.putExtra("idUsuario", idUsuario);  // Pasa el ID del usuario
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finaliza la actividad actual
        });
    }

    @Override
    public void mostrarDetallesClase(String nombreClase, String horaClase, String emailInstructor, String descripcion, int plazasDisponibles, String fechaClase, String photoUrl) {
        textNombreClase.setText(nombreClase);
        textHoraClase.setText(horaClase);
        textEmailInstructor.setText("Instructor: " + emailInstructor);
        textDescripcionClase.setText(descripcion);
        textPlazasDisponibles.setText("Plazas disponibles: " + plazasDisponibles);
        textFechaClase.setText(fechaClase);

        // Cargar la imagen del instructor desde la URL usando Picasso o Glide
        Picasso.get()
                .load(photoUrl)
                .into(imageViewInstructor);

    }

    @Override
    public void actualizarPlazasDisponibles(int nuevasPlazasDisponibles) {
        textPlazasDisponibles.setText("Plazas disponibles: " + nuevasPlazasDisponibles);
    }


    @Override
    public void actualizarBotonReserva(boolean estaInscrito) {
        if (estaInscrito) {
            btnReservar.setText("Cancelar Reserva");
            btnReservar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red_cancelar));
        } else {
            btnReservar.setText("Reservar");
            btnReservar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green_reservar));
        }
    }

    @Override
    public void mostrarCargando() {
        Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ocultarCargando() {
        // Ocultar cualquier indicador de carga
    }

    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
