package com.example.gymproject.presenters;

import com.example.gymproject.contratosPresenter.ContractLoginPresenter;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.models.LoginModel;
import com.example.gymproject.views.LoginView;

public class LoginPresenter implements ContractLoginPresenter {

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModel(); // Inicializamos el Model
    }

    public void login(String email, String password) {
        // Validar entrada
        if (email.isEmpty() || password.isEmpty()) {
            loginView.showLoginError("Email y contraseña no pueden estar vacíos");
            return;
        }

        loginView.showLoading();

        // Hacer el login a través del modelo
        loginModel.login(email, password, new LoginModel.LoginCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                loginView.hideLoading();
                loginView.showLoginSuccess(usuario);
            }

            @Override
            public void onError(String error) {
                loginView.hideLoading();
                loginView.showLoginError(error);  // Aquí mostramos el mensaje de error personalizado
            }
        });
    }
}
