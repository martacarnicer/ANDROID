package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.example.gymproject.contratosModel.ContractPerfilModel;
import com.example.gymproject.entities.Acceso;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.presenters.PerfilPresenter;
import com.example.gymproject.views.ViewPerfil;

import java.util.List;

public class PerfilActivity extends AppCompatActivity implements ViewPerfil {
    private EditText etNombre, etApellido, etEmail, etPhone, etPasswordNueva, etPasswordActual;
    private Spinner spinnerTipoCuota, spinnerCentro;
    private Button btnGuardarCambios, btnDarseBaja, btnVolver;
    private PerfilPresenter presenter;
    private Usuario usuario;
    private List<Centro> listaCentros;
    private Acceso accesoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPasswordNueva = findViewById(R.id.etPasswordNueva);
        etPasswordActual = findViewById(R.id.etPasswordActual);
        spinnerTipoCuota = findViewById(R.id.spinnerTipoCuota);
        spinnerCentro = findViewById(R.id.spinnerCentro);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnDarseBaja = findViewById(R.id.btnDarseBaja);
        btnVolver = findViewById(R.id.btnVolver);

        // Aplicar la fuente Poppins
        Typeface poppins = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        etNombre.setTypeface(poppins);
        etApellido.setTypeface(poppins);
        etEmail.setTypeface(poppins);
        etPhone.setTypeface(poppins);
        etPasswordNueva.setTypeface(poppins);
        etPasswordActual.setTypeface(poppins);
        btnGuardarCambios.setTypeface(poppins);
        btnDarseBaja.setTypeface(poppins);
        btnVolver.setTypeface(poppins);

        presenter = new PerfilPresenter(this);

        // Obtener el ID del usuario
        Long idUsuario = getIntent().getLongExtra("idUsuario", -1);
        presenter.cargarDatosUsuario(idUsuario);

        // Configurar el spinner de tipo de cuota
        ArrayAdapter<CharSequence> adapterCuota = ArrayAdapter.createFromResource(this,
                R.array.tipo_cuota_options, android.R.layout.simple_spinner_item);
        adapterCuota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoCuota.setAdapter(adapterCuota);

        // Listener para cambios en el spinner de tipo de cuota
        spinnerTipoCuota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPlan = parentView.getItemAtPosition(position).toString();
                if ("Básica".equals(selectedPlan)) {
                    spinnerCentro.setVisibility(View.VISIBLE);
                    cargarCentros();
                } else {
                    spinnerCentro.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinnerCentro.setVisibility(View.GONE);
            }
        });

        btnGuardarCambios.setOnClickListener(v -> {
            usuario.setNombre(etNombre.getText().toString());
            usuario.setApellido(etApellido.getText().toString());
            usuario.setEmail(etEmail.getText().toString());
            usuario.setTelefono(etPhone.getText().toString());

            String nuevaPassword = etPasswordNueva.getText().toString();
            String tipoCuotaSeleccionada = spinnerTipoCuota.getSelectedItem().toString();

            // Si la cuota es "Básica", actualiza el acceso con el nuevo centro
            if ("Básica".equals(tipoCuotaSeleccionada)) {
                Centro centroSeleccionado = (Centro) spinnerCentro.getSelectedItem();
                if (centroSeleccionado != null) {
                    presenter.actualizarCentroAcceso(usuario.getIdUsuario(), centroSeleccionado.getIdCentro());
                }
            } else if ("Premium".equals(tipoCuotaSeleccionada)) {
                presenter.otorgarAccesoPremium(usuario.getIdUsuario());  // Otorgar acceso a todos los centros
            }

            // Guardar cambios del usuario
            usuario.setTipoCuota(tipoCuotaSeleccionada);
            presenter.guardarCambios(usuario, nuevaPassword);
        });

        btnDarseBaja.setOnClickListener(v -> presenter.darseDeBaja(idUsuario));

        // Listener para el botón "Volver"
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilActivity.this, DashboardUsuarioActivity.class);
            intent.putExtra("idUsuario", idUsuario);
            startActivity(intent);
            finish();
        });
    }

    // Método para cargar los centros en el spinner
    private void cargarCentros() {
        presenter.obtenerCentros(new ContractPerfilModel.CentrosCallback() {
            @Override
            public void onCentrosLoaded(List<Centro> centros) {
                listaCentros = centros;
                ArrayAdapter<Centro> adapter = new ArrayAdapter<>(PerfilActivity.this,
                        android.R.layout.simple_spinner_item, centros);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCentro.setAdapter(adapter);

                // Preseleccionar el centro actual del usuario a través de "Acceso"
                if (usuario.getTipoCuota().equals("Básica") && accesoUsuario.getCentro() != null) {
                    for (int i = 0; i < centros.size(); i++) {
                        if (centros.get(i).getIdCentro().equals(accesoUsuario.getCentro().getIdCentro())) {
                            spinnerCentro.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(PerfilActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void mostrarDatosUsuario(Usuario usuario, Acceso acceso) {
        this.usuario = usuario;
        this.accesoUsuario = acceso;

        etNombre.setText(usuario.getNombre());
        etApellido.setText(usuario.getApellido());
        etEmail.setText(usuario.getEmail());
        etPhone.setText(usuario.getTelefono());
        etPasswordActual.setText("********");

        if (usuario.getTipoCuota().equals("Básica")) {
            spinnerTipoCuota.setSelection(0);
            spinnerCentro.setVisibility(View.VISIBLE);
            cargarCentros();
        } else {
            spinnerTipoCuota.setSelection(1);
            spinnerCentro.setVisibility(View.GONE);
        }
    }

    @Override
    public void mostrarError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarExitoActualizacion() {
        Toast.makeText(this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarExitoBaja() {
        Toast.makeText(this, "Usuario dado de baja", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
