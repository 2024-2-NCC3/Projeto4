package br.fecap.pi.quizzods;

import android.content.Intent;
import android.graphics.Color;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
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
    private SoundPool soundPool;
    private int soundIdAcerto;
    private int soundIdErro;


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
                     1, 16),
        new Questao("Qual ODS é dedicado à parcerias para a implementação dos objetivos?",
                    Arrays.asList("ODS 17",
                                "ODS 11",
                            "ODS 7",
                            "ODS 6"),
                     0, 17),
        new Questao("O ODS 1 visa acabar com a pobreza em todas as suas formas em todos os lugares. Qual das seguintes metas específicas está diretamente relacionada a esse objetivo?",
                Arrays.asList("Reduzir a proporção de homens, mulheres e crianças vivendo na pobreza extrema.",
                        "Garantir acesso a serviços de saúde de qualidade.",
                        "Promover a educação inclusiva.",
                        "Aumentar a eficiência energética"),
                0, 1),
        new Questao("O ODS 5 busca alcançar a igualdade de gênero e empoderar todas as mulheres e meninas. Qual das alternativas a seguir descreve uma das metas desse ODS?",
                Arrays.asList("Aumentar a participação das mulheres em cargos de liderança na política.",
                        "Garantir acesso à educação primária universal.",
                        "Reduzir a desigualdade econômica entre países",
                        "Proteger os ecossistemas marinhos"),
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
                        "Impedir a discriminação em todas as suas formas",
                        "Promover a sustentabilidade urbana"),
                2, 10),
        new Questao("O ODS 12 visa garantir padrões de consumo e produção sustentáveis. Qual das seguintes iniciativas poderia ser uma forma de promover esse objetivo?",
                Arrays.asList("Aumentar a produção agrícola sem restrições.",
                        "Promover a redução de resíduos e o reaproveitamento de materiais.",
                        "Incentivar a exploração de combustíveis fósseis",
                        "Expandir áreas urbanas sem planejamento."),
                1, 12),
        new Questao("O ODS 13 foca na ação contra a mudança global do clima. Qual das alternativas abaixo é uma das metas desse objetivo?",
                Arrays.asList("Aumentar as emissões de gases de efeito estufa",
                        "Integrar medidas relativas à mudança do clima nas políticas nacionais.",
                        "Incentivar a produção de plásticos não biodegradáveis.",
                        "Expandir a agricultura em áreas de risco climático."),
                1, 13),
        new Questao("O ODS 14 visa conservar e usar de forma sustentável os oceanos, os mares e os recursos marinhos. Qual das opções a seguir é uma meta importante desse ODS?",
                Arrays.asList("Aumentar a captura de peixes em áreas protegidas",
                        "Proteger ecossistemas marinhos e costeiros",
                        "Incentivar a poluição marinha",
                        "Expandir a exploração mineral no fundo do mar"),
                1, 14),
        new Questao("O ODS 15 se concentra na proteção, restauração e promoção do uso sustentável dos ecossistemas terrestres. Qual das seguintes ações é uma prioridade desse ODS?",
                Arrays.asList("Aumentar o desmatamento para a agricultura.",
                        "Promover a gestão sustentável das florestas.",
                        "Incentivar a urbanização descontrolada.",
                        "Aumentar a poluição do solo"),
                1, 15),
        new Questao("O ODS 16 busca promover sociedades pacíficas e inclusivas. Qual das opções a seguir é um dos objetivos desse ODS?",
                Arrays.asList("Aumentar a corrupção em instituições governamentais.",
                        "Promover o estado de direito em nível nacional e internacional",
                        " Incentivar a exclusão social.",
                        "Reduzir a liberdade de expressão."),
                1, 16),
        new Questao("O ODS 17 é sobre parcerias para a implementação dos objetivos. Qual das alternativas a seguir descreve um aspecto fundamental deste ODS?",
                Arrays.asList("Reduzir o comércio internacional",
                        "Fortalecer a cooperação internacional e o financiamento",
                        "Incentivar a concorrência desleal entre países",
                        "Limitar o acesso à tecnologia"),
                1, 17),
        new Questao("O ODS 4 visa garantir educação inclusiva e equitativa. Qual das seguintes ações está alinhada a esse objetivo?",
                Arrays.asList("Aumentar a matrícula em escolas particulares.",
                        "Garantir que todas as jovens tenham acesso à educação de qualidade",
                        "Focar apenas na educação secundária",
                        "Reduzir o financiamento para a educação pública"),
                1, 4),
        new Questao("O ODS 3 foca em assegurar uma vida saudável e promover o bem-estar para todos. Qual das seguintes iniciativas poderia ajudar a alcançar esse objetivo?",
                Arrays.asList("Reduzir o acesso a serviços de saúde.",
                        "Promover campanhas de vacinação.",
                        "Incentivar o uso de substâncias nocivas.",
                        "Expandir áreas de poluição urbana."),
                1, 3),
        new Questao("O ODS 8 promove o trabalho decente e o crescimento econômico. Qual das alternativas abaixo representa uma meta desse objetivo?",
                Arrays.asList("Aumentar a jornada de trabalho sem compensação.",
                        "Promover o emprego pleno e produtivo.",
                        "Incentivar o trabalho infantil.",
                        "Estabelecer salários abaixo do mínimo"),
                1, 8),
        new Questao("O ODS 9 busca construir infraestrutura resiliente e promover a inovação. Qual das seguintes afirmações é verdadeira sobre este ODS?",
                Arrays.asList("Focar exclusivamente em infraestrutura urbana.",
                        "Ignorar a necessidade de transporte sustentável.",
                        "Aumentar a dependência de tecnologias obsoletas",
                        "Incentivar a pesquisa e o desenvolvimento sustentável."),
                3, 9),
        new Questao("O ODS 15 aborda a conservação da biodiversidade. Qual das seguintes ações é uma prioridade desse objetivo?",
                Arrays.asList("Promover o uso excessivo de pesticidas.",
                        "Ignorar a perda de habitat",
                        "Expandir a agricultura em áreas de proteção ambiental.",
                        "Restaurar os ecossistemas degradados."),
                3, 15),
        new Questao("O ODS 11 busca tornar as cidades e os assentamentos humanos inclusivos. Qual das alternativas a seguir representa uma meta importante desse ODS?",
                Arrays.asList("Expandir as favelas sem planejamento.",
                        "Aumentar a especulação imobiliária",
                        "Incentivar a poluição do ar nas cidades",
                        "Melhorar a acessibilidade em áreas urbanas"),
                3, 11),
        new Questao("O ODS 2 visa acabar com a fome, alcançar a segurança alimentar e promover a agricultura sustentável. Qual das seguintes ações se alinha a este objetivo?",
                Arrays.asList("Incentivar a monocultura em larga escala.",
                        "Reduzir o apoio a pequenos agricultores",
                        "Aumentar a dependência de alimentos importados.",
                        "Promover a agricultura familiar e sustentável"),
                3, 2),
        new Questao("O ODS 6 é sobre água e saneamento. Qual das seguintes metas é crucial para alcançar este objetivo?",
                Arrays.asList("Garantir o acesso universal a água potável e segura.",
                        "Incentivar a escassez de água em regiões urbanas",
                        "Ignorar as necessidades de saneamento básico.",
                        "Aumentar a poluição das fontes de água."),
                0, 6),
        new Questao("O ODS 16 busca promover a paz e a justiça. Qual das seguintes práticas se alinha a esse objetivo?",
                Arrays.asList("Fomentar a corrupção nas instituições.",
                        "Ignorar a violência doméstica",
                        "Promover a censura à imprensa",
                        "Proteger os direitos humanos."),
                3, 16),
        new Questao("O ODS 17 foca em parcerias. Qual das seguintes iniciativas é uma parte fundamental deste ODS?",
                Arrays.asList("Isolar países em desenvolvimento",
                        "Reduzir o financiamento internacional para a educação.",
                        "Promover acordos comerciais desleais.",
                        "Estabelecer colaborações globais para o desenvolvimento sustentável."),
                3, 17),
        new Questao("O ODS 14 trata da conservação dos oceanos. Qual das seguintes ações é uma prioridade deste ODS?",
                Arrays.asList("Reduzir a acidificação dos oceanos.",
                        "Promover a pesca ilegal.",
                        "Ignorar a poluição marinha.",
                        "Aumentar o lixo plástico nos oceanos."),
                0, 14),
        new Questao("O ODS 12 visa garantir padrões de consumo e produção sustentáveis. Qual das seguintes iniciativas pode ajudar a promover este objetivo?",
                Arrays.asList("Incentivar a obsolescência programada",
                        "Estimular a produção de bens não recicláveis",
                        "Aumentar o desperdício alimentar.",
                        "Promover a eficiência no uso de recursos naturais"),
                3, 12),
        new Questao("O ODS 1 busca acabar com a pobreza. Qual das seguintes afirmações descreve um dos desafios relacionados a esse objetivo?",
                Arrays.asList("Restringir o acesso ao emprego formal",
                        "Promover o aumento da desigualdade econômica.",
                        " Ignorar as necessidades das populações vulneráveis.",
                        "Garantir que todos tenham acesso a serviços bancários."),
                3, 1),
        new Questao("O ODS 8 promove o trabalho decente. Qual das seguintes práticas se alinha a este objetivo?",
                Arrays.asList("Promover salários justos e dignos.",
                        "Incentivar o trabalho informal sem proteção",
                        "Ignorar as condições de trabalho seguras",
                        "Reduzir os direitos trabalhistas"),
                0, 8),
        new Questao("O ODS 4 foca na educação de qualidade. Qual das alternativas abaixo representa um desafio específico relacionado a esse objetivo?",
                Arrays.asList("Garantir que todos os jovens tenham acesso a educação superior.",
                        "Aumentar o número de escolas em áreas urbanas",
                        "Reduzir a taxa de analfabetismo entre adultos",
                        "Incentivar o uso de tecnologia em sala de aula"),
                2, 4),
        new Questao("O ODS 7 busca assegurar energia acessível e sustentável. Qual das seguintes afirmações é verdadeira sobre este objetivo?",
                Arrays.asList("Incentivar o uso de energia fóssil como prioridade.",
                        "Promover a energia renovável e a eficiência energética",
                        "Focar apenas no abastecimento de eletricidade.",
                        "Ignorar as necessidades de energia em comunidades rurais."),
                1, 7),
        new Questao("O ODS 5 visa a igualdade de gênero. Qual das ações a seguir é fundamental para alcançar esse objetivo?",
                Arrays.asList("Incentivar a violência de gênero.",
                        "Promover campanhas de conscientização sobre igualdade.",
                        "Reduzir a participação das mulheres na política.",
                        "Ignorar as necessidades das meninas na educação."),
                1, 5),
        new Questao("O ODS 11 é sobre cidades sustentáveis. Qual das alternativas a seguir é uma meta deste objetivo?",
                Arrays.asList("Expandir a urbanização sem planejamento.",
                        "Garantir acesso a transporte público seguro e acessível.",
                        "Ignorar as questões de poluição do ar",
                        "Aumentar o número de carros nas cidades"),
                1, 11),
        new Questao("O ODS 14 foca na conservação dos oceanos. Qual das seguintes ações é uma prioridade deste objetivo?",
                Arrays.asList("Reduzir a sobrepesca em áreas críticas.",
                        "Ignorar a poluição plástica nos oceanos.",
                        "Promover a exploração de recursos marinhos sem restrições.",
                        "Aumentar o despejo de resíduos nos mares."),
                0, 14),
        new Questao("O ODS 15 trata da vida terrestre e da biodiversidade. Qual das alternativas abaixo é uma ação essencial para alcançar esse objetivo?",
                Arrays.asList("Promover a recuperação de áreas degradadas.",
                        "Aumentar a degradação dos habitats naturais",
                        "Ignorar a extinção de espécies.",
                        " Incentivar a expansão de áreas urbanas em zonas rurais"),
                0, 15),
        new Questao("O ODS 10 busca reduzir desigualdades. Qual das alternativas a seguir é uma das metas deste ODS?",
                Arrays.asList("Promover a inclusão social e econômica",
                        "Ignorar as disparidades entre ricos e pobres.",
                        "Aumentar as desigualdades entre países desenvolvidos e em desenvolvimento",
                        "Focar apenas nas desigualdades de gênero"),
                0, 10),
        new Questao("O ODS 16 é sobre paz e justiça. Qual das ações abaixo se alinha a este objetivo?",
                Arrays.asList("Reduzir a liberdade de expressão",
                        "Proteger os direitos das minorias",
                        "Ignorar a violência contra grupos vulneráveis",
                        "Promover a impunidade"),
                1, 16),
        new Questao("O ODS 2 visa acabar com a fome. Qual das seguintes ações pode contribuir para alcançar esse objetivo?",
                Arrays.asList("Aumentar a dependência de alimentos importados",
                        "Reduzir o apoio a pequenos agricultores",
                        "Promover a agricultura sustentável e diversificada",
                        "Incentivar a produção em larga escala sem considerar a sustentabilidade."),
                2, 2),
        new Questao("O ODS 17 é sobre parcerias para a implementação dos objetivos. Qual das seguintes práticas é fundamental para fortalecer essas parcerias?",
                Arrays.asList("Isolar países em desenvolvimento de cooperações globais.",
                        "Ignorar as necessidades locais na implementação de projetos",
                        "Promover a troca de tecnologias e conhecimentos entre países.",
                        "Reduzir o financiamento internacional para desenvolvimento sustentável.s"),
                2, 17)
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

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2) // Número máximo de sons simultâneos
                .build();

        // Carrega os sons
        soundIdAcerto = soundPool.load(this, R.raw.correct, 1); // Substitua "acerto" pelo nome do arquivo
        soundIdErro = soundPool.load(this, R.raw.wrong, 1);

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

        vidasRestantes.setText("VIDAS: " + vidas );


        enviarResposta.setOnClickListener(v -> {


            retornarCores();
            int idRespostaSelecionada = divQuestoes.getCheckedRadioButtonId(); //caso a resposta esteja correta

            if (idRespostaSelecionada == -1) {
                Toast.makeText(this, "Selecione uma resposta!",Toast.LENGTH_SHORT).show();
                return;
            }

            int indiceRespostaSelecionada = divQuestoes.indexOfChild(findViewById(idRespostaSelecionada));
            if (indiceRespostaSelecionada == questoes.get(numeroQuestao).getOpcaoCorreta()) {
                findViewById(idRespostaSelecionada).setBackgroundColor(Color.GREEN);
                soundPool.play(soundIdAcerto, 1.0f, 1.0f, 1, 0, 1.0f);

                questao1.setEnabled(false);
                questao2.setEnabled(false);
                questao3.setEnabled(false);
                questao4.setEnabled(false);
                enviarResposta.setEnabled(false);

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
                if(numeroQuestao < 10){
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
                soundPool.play(soundIdErro, 1.0f, 1.0f, 1, 0, 1.0f);
                findViewById(idRespostaSelecionada).setEnabled(false);
                vidas -=1;
                sequencia = 0;
                vidasRestantes.setText("VIDAS: " + vidas);
                if(vidas == 0){
                    new android.os.Handler().postDelayed(() -> {
                        enviarResposta.setEnabled(false);
                        MudarDeTela(numeroQuestao, pontosTotais, vidas);

                    }, 1000);

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

        questao1.setEnabled(true);
        questao2.setEnabled(true);
        questao3.setEnabled(true);
        questao4.setEnabled(true);
        enviarResposta.setEnabled(true);
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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        String username = getIntent().getStringExtra("USERNAME");

        intent.putExtra("NUMERO_QUESTAO", numeroQuestao);
        intent.putExtra("PONTOS_TOTAIS", pontosTotais);
        intent.putExtra("VIDAS", vidas);
        intent.putExtra("USERNAME", username);
        Log.d("menuJogo", "Username: " + username);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release(); // Libera os recursos
            soundPool = null;
        }
    }
}
