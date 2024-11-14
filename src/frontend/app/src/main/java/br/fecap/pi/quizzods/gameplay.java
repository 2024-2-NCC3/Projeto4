package br.fecap.pi.quizzods;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Arrays;

public class gameplay extends AppCompatActivity {
    private RadioGroup divQuestoes;
    private RadioButton questao1, questao2, questao3, questao4;
    private Button enviarResposta;
    private int idRespostaCorreta = R.id.questao1;
    private TextView questaoNumero, questaoTexto;
    private TextView vidasRestantes;
    private TextView pontos;
    private ConstraintLayout quadradoODS;


//Lista de questões (enunciado, alternativas, resposta correta, numero da ODS)
    private List<Questao> questoes = Arrays.asList(
            new Questao("O ODS 6 está relacionado a qual temática?",
                    Arrays.asList("Água potável e saneamento",
                            "Igualdade de gênero",
                            "Energia limpa e acessível",
                            "Combate à fome e desnutrição"),
                    1, 6),
            new Questao("O ODS 5 está relacionado a qual temática?",
                        Arrays.asList("Água limpa",
                                "Educação de qualidade",
                                "Igualdade de gênero",
                                "Ação contra a mudança global do clima"),
                    2, 5),

            new Questao("O ODS 4 está relacionado a qual temática?",
                    Arrays.asList("Educação de qualidade",
                            "Redução das desigualdades",
                            "Trabalho decente e crescimento econômico",
                            "Paz, justiça e instituições eficazes"),
                    1, 4),

        new Questao("Qual ODS visa erradicar a pobreza em todas as suas formas?",
                    Arrays.asList("ODS 2",
                            "ODS 1",
                            "ODS 3",
                            "ODS 4"),
                    1, 0),

     new Questao("O ODS 1 visa acabar com a pobreza em todas as suas formas em todos os lugares. Qual das seguintes metas específicas está diretamente relacionada a esse objetivo?",
                    Arrays.asList("Reduzir a proporção de homens, mulheres e crianças vivendo na pobreza extrema.",
                            "Garantir acesso a serviços de saúde de qualidade.",
                            "Promover a educação inclusiva.",
                            "Aumentar a eficiência energética."),
                 0, 1),
     new Questao("O ODS 5 busca alcançar a igualdade de gênero e empoderar todas as mulheres e meninas. Qual das alternativas a seguir descreve uma das metas desse ODS?",
                    Arrays.asList("Aumentar a participação das mulheres em cargos de liderança na política.",
                            "Garantir acesso à educação primária universal.",
                            "Reduzir a desigualdade econômica entre países.",
                            " Proteger os ecossistemas marinhos."),
                 0, 5),
     new Questao("O ODS 7 visa garantir o acesso a energia acessível, confiável, sustentável e moderna para todos. Qual das seguintes estratégias é uma prioridade deste ODS?",
                    Arrays.asList("Promover a agricultura sustentável.",
                            "Aumentar a eficiência energética em todos os setores.",
                            "Reduzir o consumo de água.",
                            "Proteger a biodiversidade terrestre."),
                 1, 7),
     new Questao("O ODS 10 trata da redução das desigualdades dentro e entre os países. Qual das alternativas a seguir representa um desafio específico abordado por esse ODS?",
                    Arrays.asList("Acesso igualitário à educação de qualidade.",
                            "Garantir saúde acessível a todos.",
                            "Impedir a discriminação em todas as suas formas.",
                            "Promover a sustentabilidade urbana."),
                 2, 10),
     new Questao("O ODS 12 visa garantir padrões de consumo e produção sustentáveis. Qual das seguintes iniciativas poderia ser uma forma de promover esse objetivo?",
                    Arrays.asList("Aumentar a produção agrícola sem restrições.",
                            "Promover a redução de resíduos e o reaproveitamento de materiais.",
                            "Incentivar a exploração de combustíveis fósseis.",
                            "Expandir áreas urbanas sem planejamento."),
                 1, 12),
     new Questao("O ODS 13 foca na ação contra a mudança global do clima. Qual das alternativas abaixo é uma das metas desse objetivo?",
                    Arrays.asList("Aumentar as emissões de gases de efeito estufa.",
                            "Incentivar a produção de plásticos não biodegradáveis.",
                            "Expandir a agricultura em áreas de risco climático.",
                            "Integrar medidas relativas à mudança do clima nas políticas nacionais"),
                 3, 13),
     new Questao("O ODS 14 visa conservar e usar de forma sustentável os oceanos, os mares e os recursos marinhos. Qual das opções a seguir é uma meta importante desse ODS?",
                    Arrays.asList("Aumentar a captura de peixes em áreas protegidas.",
                            "Proteger ecossistemas marinhos e costeiros.",
                            "Incentivar a poluição marinha.",
                            "Expandir a exploração mineral no fundo do mar."),
                 1, 14),
     new Questao("O ODS 15 se concentra na proteção, restauração e promoção do uso sustentável dos ecossistemas terrestres. Qual das seguintes ações é uma prioridade desse ODS?",
                    Arrays.asList("Promover a gestão sustentável das florestas.",
                            "Aumentar o desmatamento para a agricultura.",
                            "Incentivar a urbanização descontrolada.",
                            "Aumentar a poluição do solo."),
                 0, 15),
     new Questao("O ODS 16 busca promover sociedades pacíficas e inclusivas. Qual das opções a seguir é um dos objetivos desse ODS?",
                    Arrays.asList("Aumentar a corrupção em instituições governamentais.",
                            "Promover o estado de direito em nível nacional e internacional.",
                            "Incentivar a exclusão social.",
                            "Reduzir a liberdade de expressão."),
                 1, 16)
    );


    private int pontosTotais = 0;
    private int sequencia = 0;
    private int numeroQuestao = 1;
    private int vidas = 5;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gameplay);

        Collections.shuffle(questoes);
        divQuestoes = findViewById(R.id.divQuestoes);
        questao1 = findViewById(R.id.questao1);
        questao2 = findViewById(R.id.questao2);
        questao3 = findViewById(R.id.questao3);
        questao4 = findViewById(R.id.questao4);
        vidasRestantes = findViewById(R.id.vidasRestantes);
        questaoTexto = findViewById(R.id.questaoTexto);
        quadradoODS = findViewById(R.id.quadradoOds);
        pontos = findViewById(R.id.pontos);

        questaoNumero = findViewById(R.id.questaoNumero);
        enviarResposta = findViewById(R.id.btnEnviarResposta);

        atualizarQuestao(); //começa o jogo






        enviarResposta.setOnClickListener(v -> {
            retornarCores();
            int idRespostaSelecionada = divQuestoes.getCheckedRadioButtonId(); //caso a resposta esteja correta
            int indiceRespostaSelecionada = divQuestoes.indexOfChild(findViewById(idRespostaSelecionada));
            if (indiceRespostaSelecionada == questoes.get(numeroQuestao).getOpcaoCorreta()) {
                Toast.makeText(this, "Resposta Correta!",Toast.LENGTH_SHORT).show();
                findViewById(idRespostaSelecionada).setBackgroundColor(Color.GREEN);

                numeroQuestao++; //sistema de pontuação
                sequencia++;
                if(sequencia < 3){
                    pontosTotais += 1;
                }
                else if(sequencia <= 5){
                    pontosTotais += 3;
                    if(sequencia == 5){
                        vidas++;
                    }
                }
                else if(sequencia <= 7){
                    pontosTotais += 5;
                }
                else if(sequencia <= 10){
                    pontosTotais += 10;
                }

                questaoNumero.setText("" + numeroQuestao);
                if(numeroQuestao <= 10){
                    pontos.setText("PONTOS: " + pontosTotais);
                    new android.os.Handler().postDelayed(() -> {
                        retornarCores();
                        atualizarQuestao();

                    }, 1000);
                }
                else{
                    MudarDeTela(numeroQuestao, pontosTotais, vidas);
                }



            }
            else { //Em caso de resposta errada
                Toast.makeText(this, "Resposta Incorreta!",Toast.LENGTH_SHORT).show();
                findViewById(idRespostaSelecionada).setBackgroundColor(Color.RED);
                vidas -=1;
                sequencia = 0;
                vidasRestantes.setText("VIDAS: " + vidas);
                if(vidas == 0){
                    Toast.makeText(this, "Você Perdeu!",Toast.LENGTH_SHORT).show();
                    enviarResposta.setEnabled(false);
                    MudarDeTela(numeroQuestao, pontosTotais, vidas);
                }

            }
            divQuestoes.clearCheck();

        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void atualizarQuestao(){ //seta a nova questao
        Questao questaoAtual = questoes.get(numeroQuestao);
        questaoTexto.setText(questaoAtual.getEnunciado());

        questao1.setText(questaoAtual.getAlternativas().get(0));
        questao2.setText(questaoAtual.getAlternativas().get(1));
        questao3.setText(questaoAtual.getAlternativas().get(2));
        questao4.setText(questaoAtual.getAlternativas().get(3));
        setarCorDoQuadradoODS(questaoAtual, quadradoODS);
    }

    private void retornarCores(){ //após acertar a questao seta elas de volta a cor original
        questao1.setBackgroundColor(Color.WHITE);
        questao2.setBackgroundColor(Color.WHITE);
        questao3.setBackgroundColor(Color.WHITE);
        questao4.setBackgroundColor(Color.WHITE);
    }

    private int obterCorDaOds(int numeroDaOds){
        switch (numeroDaOds) {
            case 0:
                return R.color.black; //caso nao queira mostrar a ods
            case 1:
                return R.color.ods1;
            case 2:
                return R.color.ods2;
            case 3:
                return R.color.ods3;
            case 4:
                return R.color.ods4;
            case 5:
                return R.color.ods5;
            case 6:
                return R.color.ods6;
            case 7:
                return R.color.ods7;
            case 8:
                return R.color.ods8;
            case 9:
                return R.color.ods9;
            case 10:
                return R.color.ods10;
            case 11:
                return R.color.ods11;
            case 12:
                return R.color.ods12;
            case 13:
                return R.color.ods13;
            case 14:
                return R.color.ods14;
            case 15:
                return R.color.ods15;
            case 16:
                return R.color.ods16;
            case 17:
                return R.color.ods17;
        }
        return numeroDaOds;
    }

    public void setarCorDoQuadradoODS(Questao questao, ConstraintLayout quadradoODS) {
        int corDaOds = obterCorDaOds(questao.getNumeroDaOds());
        int cor = getResources().getColor(corDaOds,getTheme());
        quadradoODS.setBackgroundColor(cor);


    }

    public void MudarDeTela(int numeroQuestao, int pontosTotais, int vidas){
        Intent intent = new Intent(gameplay.this, FimDeJogo.class);

        intent.putExtra("NUMERO_QUESTAO", numeroQuestao);
        intent.putExtra("PONTOS_TOTAIS", pontosTotais);
        intent.putExtra("VIDAS", vidas);

        startActivity(intent);
    }
}
