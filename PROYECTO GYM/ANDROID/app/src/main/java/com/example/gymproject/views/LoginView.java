// LoginView.java
package com.example.gymproject.views;

import com.example.gymproject.entities.Usuario;

public interface LoginView {
    void showLoginSuccess(Usuario usuario);  // Cambiar a Usuario
    void showLoginError(String error);
    void showLoading();
    void hideLoading();
}
