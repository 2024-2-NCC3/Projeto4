package com.example.quizzods;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menuJogo extends AppCompatActivity {

    private Button btnJogar;
    private Button btnCreditos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_jogo);

        btnCreditos = findViewById(R.id.btnCreditos);
        btnJogar = findViewById(R.id.btnJogar);


        btnCreditos.setOnClickListener(v -> {
                    Intent intent = new Intent(menuJogo.this, creditos.class);
                    startActivity(intent);
                });

        btnJogar.setOnClickListener(v -> {
            Intent intent = new Intent(menuJogo.this, gameplay.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}