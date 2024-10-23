package com.example.gymproject.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // Duración del splash de 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Enlazar el layout para el splash screen

        // Establecer la fuente personalizada para el TextView del splash screen
        TextView motivationalText = findViewById(R.id.motivationalText);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        motivationalText.setTypeface(typeface);

        // Temporizador para mostrar el Splash Screen por 3 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Iniciar la actividad de login después del splash
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class); // Aquí redirigimos a LoginActivity
                startActivity(intent);
                finish(); // Finalizar SplashActivity para evitar regresar con el botón de atrás
            }
        }, SPLASH_DURATION);
    }
}
