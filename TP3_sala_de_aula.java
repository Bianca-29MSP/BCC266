/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP3;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
//import static java.lang.Math.random;
//import static java.lang.StrictMath.random;
import java.util.Random;

/**
 *
 * @author Bianca
 */
public class TP3_sala_de_aula {
    
    final int tamanhoRam = 1000;// quantidade de blocos => 4k palavras
    final int tamanhoCache1 = 8; // quantidade de blocos =>  
    final int tamanhoCache2 = 16; // quantidade de blocos => 
    final int tamanhoCache3 = 32;
    final int tamanhoPrograma = 10001;//qde de instruções
    final int qdePalavrasBloco = 4;
    final int tamanhoHD = 10000;// 40k palavras
    final int tamanhoProgramaTI = 101;//qde de instruções interrupção
    
    double missC1 = 0;
    double hitC1 = 0;
    double missC2 = 0;
    double hitC2 = 0;
    double missC3 = 0;
    double hitC3 = 0;
    double missRAM = 0;
    double hitRAM = 0;
    double hitHD = 0;
	
    Instrucao[] memoriaInstrucoes;
    BlocoMemoria[] HD = new BlocoMemoria[tamanhoHD];
    BlocoMemoria[] RAM = new BlocoMemoria[tamanhoRam];
    BlocoMemoria[] cache1 = new BlocoMemoria[tamanhoCache1];
    BlocoMemoria[] cache2 = new BlocoMemoria[tamanhoCache2];
    BlocoMemoria[] cache3 = new BlocoMemoria[tamanhoCache3];
				
    public static void main(String[] args) throws IOException
    {
        new TP3_sala_de_aula();
    }
		
    public TP3_sala_de_aula() throws IOException {
        //montarRam();
        montarCacheVazia(tamanhoCache1, cache1);
        montarCacheVazia(tamanhoCache2, cache2);
        montarCacheVazia(tamanhoCache3, cache3);
        montarCacheVazia(tamanhoRam, RAM);
        montarHD(tamanhoHD);
        //montarCacheComDados(tamanhoCache1, 1);
        //montarCacheComDados(tamanhoCache2, 2);
        //montarCacheComDados(tamanhoCache3, 3);
        montarInstrucoesProgramaAleatorio();
        montarProgramaGerador("programa",tamanhoPrograma);
        maquina(0,"programa");

        //System.out.println("terminou");
    }

    void maquina(int PC, String programa) throws IOException {
        //registradores

        long start = System.nanoTime();
        //int PC = 0;
        int opcode = Integer.MAX_VALUE;
        int custo = 0;
        Random random = new Random();
      

        while (opcode != -1) {
            Instrucao umaInstrucao = memoriaInstrucoes[PC];
            opcode = umaInstrucao.getOpcode();

            //TP3
            if (opcode != -1) {
                //BlocoMemoria dadoMemoriaAdd1 = MMU.buscarNasMemorias(umaInstrucao.getAdd1(), RAM, cache1, cache2, cache3);
                //BlocoMemoria dadoMemoriaAdd2 = MMU.buscarNasMemorias(umaInstrucao.getAdd2(), RAM, cache1, cache2, cache3);
                //BlocoMemoria dadoMemoriaAdd3 = MMU.buscarNasMemorias(umaInstrucao.getAdd3(), RAM, cache1, cache2, cache3);

                BlocoMemoria dadoMemoriaAdd1 = MMU.buscarNasMemoriasAssociativa(umaInstrucao.getAdd1(), RAM, cache1, cache2, cache3);
                BlocoMemoria dadoMemoriaAdd2 = MMU.buscarNasMemoriasAssociativa(umaInstrucao.getAdd2(), RAM, cache1, cache2, cache3);
                BlocoMemoria dadoMemoriaAdd3 = MMU.buscarNasMemoriasAssociativa(umaInstrucao.getAdd3(), RAM, cache1, cache2, cache3);

                //incrementando custos
                custo += dadoMemoriaAdd1.getCusto();
                custo += dadoMemoriaAdd2.getCusto();
                custo += dadoMemoriaAdd3.getCusto();
                
                System.out.println("dadoMemoriaAdd1.getCacheHit(): " + dadoMemoriaAdd1.getCacheHit());
                System.out.println("dadoMemoriaAdd2.getCacheHit(): " + dadoMemoriaAdd2.getCacheHit());
                System.out.println("dadoMemoriaAdd3.getCacheHit(): " + dadoMemoriaAdd3.getCacheHit());

                switch (dadoMemoriaAdd1.getCacheHit())
                {
                    case 1:
                        hitC1++;
                        break;
                    case 2:
                        missC1++;
                        hitC2++;
                        break;
                    case 3:
                        missC1++;
                        missC2++;
                        hitC3++;
                        break;
                    case 4:
                        missC1++;
                        missC2++;
                        missC3++;
                        hitRAM++;
                        break;
                    case 5:
                        missC1++;
                        missC2++;
                        missC3++;
                        missRAM++;
                        hitHD++;
                        break;
                    default:
                        break;
                }

                switch (dadoMemoriaAdd2.getCacheHit())
                {
                    case 1:
                        hitC1++;
                        break;
                    case 2:
                        missC1++;
                        hitC2++;
                        break;
                    case 3:
                        missC1++;
                        missC2++;
                        hitC3++;
                        break;
                    case 4:
                        missC1++;
                        missC2++;
                        missC3++;
                        hitRAM++;
                        break;
                    case 5:
                        missC1++;
                        missC2++;
                        missC3++;
                        missRAM++;
                        hitHD++;
                        break;
                    default:
                        break;
                }

                switch (dadoMemoriaAdd3.getCacheHit())
                {
                    case 1:
                        hitC1++;
                        break;
                    case 2:
                        missC1++;
                        hitC2++;
                        break;
                    case 3:
                        missC1++;
                        missC2++;
                        hitC3++;
                        break;
                    case 4:
                        missC1++;
                        missC2++;
                        missC3++;
                        hitRAM++;
                        break;
                    case 5:
                        missC1++;
                        missC2++;
                        missC3++;
                        missRAM++;
                        hitHD++;
                        break;
                    default:
                        break;
                }

                switch (opcode){
                    //levar para cache1 dados externos
                    case 0: {
                        //System.out.println("Não há demanda por levar dados externos para as memórias. ");
                        if(programa.equals("programa"))
                        {
                            int chance = random.nextInt(4);
                            if(chance == 3)
                            {
                                System.out.println("TRATADOR DE INTERRUPÇÕES - INICIO");
                                montarProgramaGerador("TratadorDeInterrupcao",tamanhoProgramaTI);
                                maquina(0,"TratadorDeInterrupcao");
                                System.out.println("TRATADOR DE INTERRUPÇÕES - FIM");
                                System.out.println();
                                
                                montarProgramaGerador("programa",tamanhoPrograma);
                            }
                        }
                        break;
                    }
                    case 1: {
                        //somar
                        int conteudo1 = dadoMemoriaAdd1.getPalavras()[umaInstrucao.getAdd1().getEndPalavra()];
                        int conteudo2 = dadoMemoriaAdd2.getPalavras()[umaInstrucao.getAdd2().getEndPalavra()];
                        int soma = conteudo1 + conteudo2;

                        //salvando resultado na cache1
                        dadoMemoriaAdd3.getPalavras()[umaInstrucao.getAdd3().getEndPalavra()] = soma;

                        dadoMemoriaAdd3.setAtualizado();

                        //System.out.println("somando "+ soma + "\n");
                        break;
                    }
                    case 2: {
                        //subtrair
                        int conteudo1 = dadoMemoriaAdd1.getPalavras()[umaInstrucao.getAdd1().getEndPalavra()];
                        int conteudo2 = dadoMemoriaAdd2.getPalavras()[umaInstrucao.getAdd2().getEndPalavra()];
                        int sub = conteudo1 - conteudo2;

                        //salvando resultado na cache1
                        dadoMemoriaAdd3.getPalavras()[umaInstrucao.getAdd3().getEndPalavra()] = sub;

                        dadoMemoriaAdd3.setAtualizado();

                        //System.out.println("subtraindo "+ sub + "\n");
                        break;
                    }
                }

                PC++;
                //tratador de interrupcao
                
            }//end if
            if(programa.equals("programa"))
            {
                int chance = random.nextInt(4);
                
                if (chance == 1)
                {
                    System.out.println("TRATADOR DE INTERRUPÇÕES - INICIO");
                    montarProgramaGerador("TratadorDeInterrupcao", tamanhoProgramaTI);
                    maquina(0, "TratadorDeInterrupcao");
                    System.out.println("TRATADOR DE INTERRUPÇÕES - FIM");
                    System.out.println();

                    montarProgramaGerador("programa", tamanhoPrograma);
                }
            }
        }//end while

        double totalHitMissC1 = hitC1 + missC1;
        double totalHitMissC2 = hitC2 + missC2;
        double totalHitMissC3 = hitC3 + missC3;

        double taxaC1 = (hitC1 * 100) / totalHitMissC1;
        double taxaC2 = (hitC2 * 100) / totalHitMissC2;
        double taxaC3 = (hitC3 * 100) / totalHitMissC3;

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        //System.out.println("START: " + start );
        //System.out.println("FINISH: " + finish);
        //System.out.println("TEMPO: " + timeElapsed + "\n");
        System.out.println("HIT E MISS TOTAL:");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format("%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %8s", "C1 HIT", "|", "C1 MISS", "|", "C2 HIT", "|", "C2 MISS", "|", "C3 HIT", "|", "C3 MISS", "|"));
        System.out.println(String.format("%1s %1s %1s %1s %1s %1s %1s %1s %1s %1s %1s", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------"));
        System.out.println(String.format("%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %8s", hitC1, "|", missC1, "|", hitC2, "|", missC2, "|", hitC3, "|", missC3, "|"));
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\n\n\n");

        System.out.println("ESTATISTICAS:");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format("%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %9s %10s %1s", "CACHE 1", "|", "CACHE 2", "|", "CACHE 3", "|", "TAXA C1 %", "|", "TAXA C2 %", "|", "TAXA C3 %", "|", "CUSTO TOTAL", "|", "TEMPO DE EXECUÇÃO", "|"));
        System.out.println(String.format("%1s %1s %1s %1s %1s %1s %1s %1s %1s %1s %1s %1s %1s %1s %1s", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------", "|", "-------------------"));
        System.out.println(String.format("%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %8s", cache1.length, "|", cache2.length, "|", cache3.length, "|", taxaC1, "|", taxaC2, "|", taxaC3, "|", custo, "|", timeElapsed, "|"));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    private void montarCacheComDados(int tamanho, int tipoCache) {
        Random r = new Random();

        for (int i = 0; i < tamanho; i++) {
            BlocoMemoria aux = new BlocoMemoria();
            //forcando cache estar vazia
            aux.setEndBloco(Integer.MIN_VALUE);
            int[] palavras = new int[qdePalavrasBloco];

            for (int j = 0; j < qdePalavrasBloco; j++) {
                palavras[j] = r.nextInt(1000000);
            }
            aux.setPalavras(palavras);

            if (tipoCache == 1)
            {
                cache1[i] = aux;
            } else if (tipoCache == 2)
            {
                cache2[i] = aux;

            } else if (tipoCache == 3)
            {
                cache3[i] = aux;
            }
        }

    }

    private void montarCacheVazia(int tamanho, BlocoMemoria[] qqCache) {
        for (int i = 0; i < tamanho; i++) {
            BlocoMemoria aux = new BlocoMemoria();
            //for�ando cache estar vazia
            aux.setEndBloco(Integer.MIN_VALUE);

            qqCache[i] = aux;

        }

    }

    /*private void montarRam()// NAO PRECISA POIS A RAM TEM Q COMECAR VAZIA
    {
        Random r = new Random();
        for (int i = 0; i < tamanhoRam; i++) {
            BlocoMemoria aux = new BlocoMemoria();
            aux.setEndBloco(i);
            int[] palavras = new int[qdePalavrasBloco];

            for (int j = 0; j < qdePalavrasBloco; j++) {
                palavras[j] = r.nextInt(1000000);
            }
            aux.setPalavras(palavras);
            RAM[i] = aux;
        }
    }*/
    private void montarHD(int tamanhoHD)
    {
        BlocoMemoria HD = new BlocoMemoria();
        Random random = new Random();
        RandomAccessFile file = null;
        try
        {
            file = new RandomAccessFile(new File("HD.bin"),"rw");
            
            for (int i = 0; i < tamanhoHD; i++)
            {   
                HD.setEndBloco(i);
                int[] palavras = new int[qdePalavrasBloco];

                for (int j = 0; j < qdePalavrasBloco; j++)
                {
                    palavras[j] = random.nextInt(1000000);
                }
                HD.setPalavras(palavras);
                file.write(HD.getEndBloco());
                //file.wri
                //file.write(HD.getEndPalavra());
                for (int j = 0; j < qdePalavrasBloco; j++)
                {
                   file.write(palavras[j]);
                }
            }
            file.close();
        }catch (Exception e)
        {
            e.printStackTrace();  
        }
    }

    private void montarProgramaGerador(String nome, int tamanho) {
        
        memoriaInstrucoes = null;
        memoriaInstrucoes = new Instrucao[tamanho];

        //ler do arquivo uma linha
        try
        {
            File f = new File(nome + ".txt");
            FileReader fis = new FileReader(f);
            BufferedReader br = new BufferedReader(fis);

            String linha = null;
            Instrucao umaInstrucao = null;
            int index = 0;

            while ((linha = br.readLine()) != null)
            {
                String[] palavras = linha.split(":");
                umaInstrucao = new Instrucao();

                umaInstrucao.setOpcode(Integer.parseInt(palavras[0]));

                Endereco e1 = new Endereco();
                int e1_endBloco = Integer.parseInt(palavras[1]);
                int e1_endPalavra = Integer.parseInt(palavras[2]);
                e1_endPalavra = e1_endPalavra % 4; //mod 4
                e1.setEndBloco(e1_endBloco);
                e1.setEndPalavra(e1_endPalavra);
                umaInstrucao.setAdd1(e1);

                Endereco e2 = new Endereco();
                int e2_endBloco = Integer.parseInt(palavras[3]);
                int e2_endPalavra = Integer.parseInt(palavras[4]);
                e2_endPalavra = e2_endPalavra % 4; //mod 4
                e2.setEndBloco(e2_endBloco);
                e2.setEndPalavra(e2_endPalavra);
                umaInstrucao.setAdd2(e2);

                Endereco e3 = new Endereco();
                int e3_endBloco = Integer.parseInt(palavras[5]);
                int e3_endPalavra = Integer.parseInt(palavras[6]);
                e3_endPalavra = e3_endPalavra % 4; //mod 4
                e3.setEndBloco(e3_endBloco);
                e3.setEndPalavra(e3_endPalavra);
                umaInstrucao.setAdd3(e3);

                memoriaInstrucoes[index] = umaInstrucao;
                index++;
            }
            fis.close();
            br.close();

            //inserindo a ultima instrucao do programa que faz o halt
            umaInstrucao = new Instrucao();
            umaInstrucao.setOpcode(-1);

            memoriaInstrucoes[tamanhoPrograma - 1] = umaInstrucao;

        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private void montarInstrucoesProgramaAleatorio() {
        //0 => salvar na memória
        //1 => opcode => somar
        //2 => opcode => subtrair
        //-1 => halt

        memoriaInstrucoes = new Instrucao[tamanhoPrograma];

        Instrucao umaInstrucao;

        Random r = new Random();

        for (int i = 0; i < tamanhoPrograma - 1; i++) {

            umaInstrucao = new Instrucao();
            umaInstrucao.setOpcode(r.nextInt(3));

            Endereco add1 = new Endereco();
            add1.setEndBloco(r.nextInt(tamanhoRam));
            add1.setEndPalavra(r.nextInt(qdePalavrasBloco));
            umaInstrucao.setAdd1(add1);

            Endereco add2 = new Endereco();
            add2.setEndBloco(r.nextInt(tamanhoRam));
            add2.setEndPalavra(r.nextInt(qdePalavrasBloco));
            umaInstrucao.setAdd2(add2);

            Endereco add3 = new Endereco();
            add3.setEndBloco(r.nextInt(tamanhoRam));
            add3.setEndPalavra(r.nextInt(qdePalavrasBloco));
            umaInstrucao.setAdd3(add3);

            memoriaInstrucoes[i] = umaInstrucao;
        }

        //inserindo a ultima instrucao do programa que faz o halt
        umaInstrucao = new Instrucao();
        umaInstrucao.setOpcode(-1);

        memoriaInstrucoes[tamanhoPrograma - 1] = umaInstrucao;

    }

}
