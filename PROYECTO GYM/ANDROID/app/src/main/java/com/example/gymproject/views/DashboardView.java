// DashboardView.java
package com.example.gymproject.views;


public interface DashboardView {
    void showUserDetails(String nombre);
    void showError(String error);
    void showLoading();
    void hideLoading();
    void mostrarAvisoNotificaciones(boolean hayNotificaciones);  // Nuevo método para notificaciones
}
