// RegisterView.java
package com.example.gymproject.views;

import com.example.gymproject.entities.Centro;

import java.util.List;

public interface RegisterView {
    void showRegisterSuccess(String message);
    void showRegisterError(String error);
    void showLoading();
    void hideLoading();
    void redirectToLogin();
}