package br.fecap.pi.quizzods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.fecap.pi.quizzods.R;

public class menuJogo extends AppCompatActivity {

    private Button btnJogar;
    private Button btnCreditos;
    private ImageView imageUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_jogo);

        btnCreditos = findViewById(R.id.btnCreditos);
        btnJogar = findViewById(R.id.btnJogar);
        imageUsuario = findViewById(R.id.imageUsuario);
        String username = getIntent().getStringExtra("USERNAME");





        btnCreditos.setOnClickListener(v -> {
                    Intent intent = new Intent(menuJogo.this, creditos.class);
                    startActivity(intent);

                });

        btnJogar.setOnClickListener(v -> {
            Intent intent = new Intent(menuJogo.this, gameplay.class);
            startActivity(intent);
            intent.putExtra("USERNAME", username);
            Log.d("menuJogo", "Username: " + username);
        });

      imageUsuario.setOnClickListener(new View.OnClickListener(){
          public void onClick(View v){
              Intent intent = new Intent (menuJogo.this, deletarUsuario.class);
              startActivity(intent);

          }







      });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}