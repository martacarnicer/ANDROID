package com.example.gymproject.contratosPresenter;

import com.example.gymproject.entities.Usuario;

public interface ContractPerfilPresenter {
    void cargarDatosUsuario(Long idUsuario);
    void guardarCambios(Usuario usuario, String nuevaPassword);
    void darseDeBaja(Long idUsuario);
}
