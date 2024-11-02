package com.example.quizzods;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.fecap.pi.quizzods.R;

public class MainActivity extends AppCompatActivity {
    private Button createAccountBtn;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.btnAlterarDados);
        // Inicialize o MediaPlayer com o arquivo de música
        mediaPlayer = MediaPlayer.create(this, R.raw.musica);

        // Iniciar a reprodução da música
        mediaPlayer.start();

        // Opções: pausar e parar
        // mediaPlayer.pause();
        // mediaPlayer.stop();

       /* loginBtn.setOnClickListener(v -> {
                    String username = usernameInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    Log.i("Login", "Username: " + username + ", Password: " + password);
                }); */

        createAccountBtn = findViewById(R.id.btnDeletarConta);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, menuJogo.class);
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

