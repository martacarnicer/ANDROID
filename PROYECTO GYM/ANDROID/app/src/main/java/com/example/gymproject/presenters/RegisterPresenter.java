package com.example.gymproject.presenters;

import com.example.gymproject.contratosPresenter.ContractRegisterPresenter;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.models.RegisterModel;
import com.example.gymproject.views.RegisterView;

import java.util.List;

public class RegisterPresenter implements ContractRegisterPresenter {

    private RegisterView registerView;
    private RegisterModel registerModel;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
        this.registerModel = new RegisterModel();
    }

    // Método para manejar el registro del usuario
    public void register(String nombre, String apellido, String email, String password, String telefono, String tipoCuota, Long idCentro) {
        registerView.showLoading();

        Usuario usuario = new Usuario(nombre, apellido, email, password, telefono, tipoCuota);

        registerModel.registerUser(usuario, new RegisterModel.RegisterCallback() {
            @Override
            public void onSuccess(Object result) {
                Usuario usuarioRegistrado = (Usuario) result;
                registerView.hideLoading();
                registerView.showRegisterSuccess("Registro exitoso");

                // Registrar accesos según el tipo de cuota
                if ("Básica".equals(tipoCuota)) {
                    registerModel.insertarAcceso(usuarioRegistrado.getIdUsuario(), idCentro, new RegisterModel.RegisterCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            registerView.redirectToLogin();
                        }

                        @Override
                        public void onError(String error) {
                            registerView.showRegisterError(error);
                        }
                    });
                } else {
                    // Registro de accesos premium
                    registerModel.obtenerCentros(new RegisterModel.CentrosCallback() {
                        @Override
                        public void onCentrosLoaded(List<Centro> centros) {
                            for (Centro centro : centros) {
                                registerModel.insertarAcceso(usuarioRegistrado.getIdUsuario(), centro.getIdCentro(), new RegisterModel.RegisterCallback() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        registerView.redirectToLogin();
                                    }

                                    @Override
                                    public void onError(String error) {
                                        registerView.showRegisterError(error);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(String error) {
                            registerView.showRegisterError(error);
                        }
                    });
                }
            }

            @Override
            public void onError(String error) {
                registerView.hideLoading();
                registerView.showRegisterError(error);
            }
        });
    }


}

