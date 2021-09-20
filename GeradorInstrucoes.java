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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class GeradorInstrucoes {

    int QTD_INS = 2000; // Quantidade de instrucoes geradas
    int TAM_FOR = 4; // Tamanho do For i1, i2, i3 ..i10
    int TAM_MEM = 1000; // Tamanho da Memoria (Quantidade de Enderecos
    // Possiveis)
    int PROB_FOR = 75; // Probabilidade de Ocorrencia do For (%)

    public GeradorInstrucoes()
    {
        System.out.println("GERADOR");
        int aleatorio;
        // Define seu separador (entre os parametros da sua instrucaoo
        String separador = " ";
        Random r = new Random();
        ArrayList<String> ins = new ArrayList<>();

        // N eh a quantidade de Opcodes disponiveis
        int N = 3;

        // instrucao eh o vetor que conta quantos enderecos tem cada Opcode
        //int instruc[] = { 1, 1, 1, 1, 0, 3, 4, 5 };// Instrucoes numeradas de 0
        int instruc[] = {6, 6, 6, 6, 6, 6, 6, 6};											// a N-1

        // Gerando o for (laco de repeticao) do programa;
        ArrayList<String> repeticao = new ArrayList<>();
        for (int i = 0; i < TAM_FOR; i++) {
            aleatorio = r.nextInt(N);
            String s = " ";

            for (int j = 0; j < instruc[aleatorio]; j++) {
                s += separador + r.nextInt(TAM_MEM);
            }
            repeticao.add(aleatorio + s);
        }

        for (int i = 0; i < QTD_INS;) {
            aleatorio = r.nextInt(100) + 1;

            if (aleatorio <= PROB_FOR && i + TAM_FOR < QTD_INS) {
                i = i + TAM_FOR;
                ins.addAll(repeticao);
            } else {
                i++;
                aleatorio = r.nextInt(N);
                String s = "";

                for (int j = 0; j < instruc[aleatorio]; j++) {
                    s += separador + r.nextInt(TAM_MEM);
                    
                }
                ins.add(aleatorio + s);
            }
        }
        try {
            OutputStream os = new FileOutputStream("instrucoes50.txt");
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            for (int i = 0; i < ins.size(); i++) {
                bw.write(ins.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
        new GeradorInstrucoes();
    }
}
