package com.example.quizzods;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class gameplay extends AppCompatActivity {
    private RadioGroup divQuestoes;
    private RadioButton questao1, questao2, questao3, questao4;
    private Button enviarResposta;
    private int idRespostaCorreta = R.id.questao1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gameplay);

        divQuestoes = findViewById(R.id.divQuestoes);
        questao1 = findViewById(R.id.questao1);
        questao2 = findViewById(R.id.questao2);
        questao3 = findViewById(R.id.questao3);
        questao4 = findViewById(R.id.questao4);
        enviarResposta = findViewById(R.id.btnEnviarResposta);




        enviarResposta.setOnClickListener(v -> {
            retornarCores();
            int idRespostaSelecionada = divQuestoes.getCheckedRadioButtonId();
            if (idRespostaSelecionada == idRespostaCorreta) {
                Toast.makeText(this, "Resposta Correta!",Toast.LENGTH_SHORT).show();
                findViewById(idRespostaSelecionada).setBackgroundColor(Color.GREEN);
            }
            else {
                Toast.makeText(this, "Resposta Incorreta!",Toast.LENGTH_SHORT).show();
                findViewById(idRespostaSelecionada).setBackgroundColor(Color.RED);
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void retornarCores(){
        questao1.setBackgroundColor(Color.WHITE);
        questao2.setBackgroundColor(Color.WHITE);
        questao3.setBackgroundColor(Color.WHITE);
        questao4.setBackgroundColor(Color.WHITE);
    }
}