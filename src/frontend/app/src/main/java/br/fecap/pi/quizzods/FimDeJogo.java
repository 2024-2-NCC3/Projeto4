package br.fecap.pi.quizzods;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FimDeJogo extends AppCompatActivity {

    private TextView textoResultado;
    private Button btnTelaInicial;
    private Button btnReiniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim_de_jogo);
        EdgeToEdge.enable(this);


        textoResultado = findViewById(R.id.textoResultado);
        btnTelaInicial = findViewById(R.id.btnTelaInicial);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        Intent intent = getIntent();
        int numeroQuestao = intent.getIntExtra("NUMERO_QUESTAO", 0);
        int pontosTotais = intent.getIntExtra("PONTOS_TOTAIS", 0);
        int vidas = intent.getIntExtra("VIDAS", 0);
        textoResultado.toString().toUpperCase();

        if (numeroQuestao > 9) {

            textoResultado.setText("Você Venceu!");
        }
        else {
            textoResultado.setText("Você Perdeu!");
        }

        btnTelaInicial.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, menuJogo .class);
            startActivity(intent1);
        });

        btnReiniciar.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, gameplay.class);
            startActivity(intent2);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}