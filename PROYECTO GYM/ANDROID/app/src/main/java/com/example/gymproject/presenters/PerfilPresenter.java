package com.example.gymproject.presenters;

import com.example.gymproject.contratosModel.ContractPerfilModel;
import com.example.gymproject.contratosPresenter.ContractPerfilPresenter;
import com.example.gymproject.entities.Acceso;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.models.PerfilModel;
import com.example.gymproject.views.ViewPerfil;

import java.util.List;

public class PerfilPresenter implements ContractPerfilPresenter {
    private ViewPerfil viewPerfil;
    private ContractPerfilModel model;

    public PerfilPresenter(ViewPerfil viewPerfil) {
        this.viewPerfil = viewPerfil;
        this.model = new PerfilModel();
    }

    @Override
    public void cargarDatosUsuario(Long idUsuario) {
        model.obtenerDatosUsuario(idUsuario, new ContractPerfilModel.PerfilCallback() {
            @Override
            public void onSuccess(Usuario usuario, Acceso acceso) {
                viewPerfil.mostrarDatosUsuario(usuario, acceso);  // Llamamos con Usuario y Acceso
            }

            @Override
            public void onError(String error) {
                viewPerfil.mostrarError(error);
            }
        });
    }

    public void obtenerCentros(ContractPerfilModel.CentrosCallback callback) {
        model.obtenerCentros(new ContractPerfilModel.CentrosCallback() {
            @Override
            public void onCentrosLoaded(List<Centro> centros) {
                callback.onCentrosLoaded(centros);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public void actualizarCentroAcceso(Long idUsuario, Long idCentro) {
        model.actualizarCentroAcceso(idUsuario, idCentro, new ContractPerfilModel.UpdateCallback() {
            @Override
            public void onUpdateSuccess() {
                viewPerfil.mostrarExitoActualizacion();
            }

            @Override
            public void onUpdateError(String error) {
                viewPerfil.mostrarError(error);
            }
        });
    }

    public void otorgarAccesoPremium(Long idUsuario) {
        model.otorgarAccesoPremium(idUsuario, new ContractPerfilModel.UpdateCallback() {
            @Override
            public void onUpdateSuccess() {
                viewPerfil.mostrarExitoActualizacion();
            }

            @Override
            public void onUpdateError(String error) {
                viewPerfil.mostrarError(error);  // Mostrar error si algo sale mal
            }
        });
    }



    @Override
    public void guardarCambios(Usuario usuario, String nuevaPassword) {
        if (!nuevaPassword.isEmpty()) {
            usuario.setPassword(nuevaPassword);
        }
        model.actualizarUsuario(usuario, new ContractPerfilModel.UpdateCallback() {
            @Override
            public void onUpdateSuccess() {
                viewPerfil.mostrarExitoActualizacion();
            }

            @Override
            public void onUpdateError(String error) {
                viewPerfil.mostrarError(error);
            }
        });
    }

    @Override
    public void darseDeBaja(Long idUsuario) {
        model.desactivarUsuario(idUsuario, new ContractPerfilModel.UpdateCallback() {
            @Override
            public void onUpdateSuccess() {
                viewPerfil.mostrarExitoBaja();
            }

            @Override
            public void onUpdateError(String error) {
                viewPerfil.mostrarError(error);
            }
        });
    }
}
