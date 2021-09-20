/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP3;

/**
 *
 * @author Bianca
 */
public class BlocoMemoria {
    //BlocoMemoria.palavras[0] => um dado da ram

    int[] palavras;
    int endBloco;
    boolean atualizado;
    int custo;
    int cacheHit;
    long tempo;
    int tamanho;

    public BlocoMemoria() {
        endBloco = -1;
        atualizado = false;
        custo = 0;
        setCacheHit(0);
    }
    public void setTamanho(int tamanhoAux) {
        tamanho = tamanhoAux;
    }
    public int getTamanho(){
        return tamanho;
    }
    public int getCusto() {
        return custo;
    }

    public void setCusto(int custoAux) {
        custo = custoAux;
    }

    public int[] getPalavras() {
        return palavras;
    }

    public void setPalavras(int[] palavrasAux) {
        palavras = palavrasAux;
    }

    public void setTempo(long tempoAux) {
        tempo = tempoAux;
    }

    public long getTempo(long tempoAux) {
        return tempo;
    }

    public long getTempo() {
        long tempo = System.nanoTime();
        return tempo;
    }

    public int getEndBloco() {
        return endBloco;
    }

    public void setEndBloco(int endBlocoAux) {
        endBloco = endBlocoAux;
    }

    public boolean isAtualizado() {
        return atualizado;
    }

    public void setAtualizado() {
        atualizado = true;
    }

    public void setDesatualizado() {
        atualizado = false;
    }

    public int getCacheHit() {
        return cacheHit;
    }

    public void setCacheHit(int cacheHitAux)
    {
        cacheHit = cacheHitAux;
    }

}
