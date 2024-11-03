package br.fecap.pi.quizzods;

import java.util.List;

public class Questao {

    private String enunciado;
    private List<String> alternativas;
    private int opcaoCorreta;
    private int numeroDaOds;









    public Questao(String enunciado, List<String> alternativas, int opcaoCorreta, int numeroDaOds){
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.numeroDaOds = numeroDaOds;

        if (opcaoCorreta < 0 || opcaoCorreta >= alternativas.size()) {
            throw new IllegalArgumentException("Opção correta fora do índice das alternativas.");
        }
        this.opcaoCorreta = opcaoCorreta;

    }

    public int getNumeroDaOds() {return numeroDaOds;}

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }

    public int getOpcaoCorreta() {
        return opcaoCorreta;
    }







}
