package com.example.gymproject.services;


import com.example.gymproject.entities.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/usuarios/login")
    Call<Usuario> login(@Body Usuario usuario);

    @POST("/usuarios/registro")
    Call<Usuario> registrarUsuario(@Body Usuario usuario);



}
