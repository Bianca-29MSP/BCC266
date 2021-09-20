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
import java.lang.System;
import TP3.TP3_sala_de_aula;
import java.io.File;
import java.io.RandomAccessFile;
import TP3.BlocoMemoria;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MMU {

    public static BlocoMemoria buscarNasMemoriasAssociativa(Endereco e, BlocoMemoria[] RAM, BlocoMemoria[] cache1, BlocoMemoria[] cache2, BlocoMemoria[] cache3) throws IOException {

        long tempo = System.nanoTime();

        int custo = 0;

        for (int i = 0; i < cache1.length; i++) {
            if (cache1[i].getEndBloco() == e.getEndBloco()) {
                custo += 10;
                tempo = cache1[i].getTempo();
                cache1[i].setCusto(custo);
                cache1[i].setCacheHit(1);
                cache1[i].setTempo(tempo);
                cache1[i].setAtualizado();

                return cache1[i];
            }
        }
        for (int j = 0; j < cache2.length; j++) {
            if (cache2[j].getEndBloco() == e.getEndBloco()) {

                custo += 110;
                tempo = cache2[j].getTempo();
                cache2[j].setCusto(custo);
                cache2[j].setCacheHit(2);
                cache2[j].setTempo(tempo);
                cache2[j].setAtualizado();
                return levaCache2paraCache1(cache1, cache2, j, custo);

            }
        }
        for (int b = 0; b < cache3.length; b++) {
            if (cache3[b].getEndBloco() == e.getEndBloco()) {

                custo += 1100;
                tempo = cache3[b].getTempo();
                cache3[b].setCusto(custo);
                cache3[b].setCacheHit(3);
                cache3[b].setTempo(tempo);
                cache3[b].setAtualizado();
                int posi = levaCache3paraCache2(cache2, cache3, b, custo);
                return levaCache2paraCache1(cache1, cache2, posi, custo);

            }
        }
        for(int k = 0; k < RAM.length; k++)
        {

            if (RAM[k].getEndBloco() == e.getEndBloco())
            {
                custo += 11100;
                RAM[k].setCusto(custo);
                RAM[k].setCacheHit(4);
                RAM[k].setAtualizado();
                int pos = levaCache3paraCache2(cache3, RAM, k, custo);
                int posicao = levaCache3paraCache2(cache2, cache3, pos, custo);
                return levaCache2paraCache1(cache1, cache2, posicao, custo);
                
            }
        }
        int indice = Position(RAM);
        custo += 111000;
        
        
        RAM[indice] = transferencia_HD(RAM, e ,indice);
        RAM[indice].setCacheHit(5);
        int p1 = Position(cache3);
        int p2 = levaCache3paraCache2(cache3, RAM, p1, custo);
        int p3 = levaCache3paraCache2(cache2, cache3, p2, custo);
        return levaCache2paraCache1(cache1, cache2, p3, custo);
                
        
        //return null;
    }

    private static int verificaCheio(BlocoMemoria[] cache1)
    {
        int cont = 0;
        for (int j = 0; j < cache1.length; j++) {
            if (cache1[j].isAtualizado()) {
                cont++;
            }
        }
        if (cont == cache1.length) {
            return 1;
        } else {
            return 0;
        }

    }

    private static int levaCache3paraCache2(BlocoMemoria[] cache2, BlocoMemoria[] cache3, int pos, int custo)
    {

        int posicao = Position(cache2);

        if (!cache2[posicao].isAtualizado()) {
            BlocoMemoria aux = cache2[posicao];
            cache2[posicao] = cache3[pos];
            cache3[pos] = aux;
            cache2[posicao].setCusto(custo);

        } else {
            BlocoMemoria aux = cache2[posicao];
            cache2[posicao] = cache3[pos];
            cache3[pos] = aux;
            cache2[posicao].setCusto(custo);
        }

        //cache2[posicao].setCusto(custo);
        return posicao;

    }

    private static BlocoMemoria levaCache2paraCache1(BlocoMemoria[] cache1, BlocoMemoria[] cache2, int pos, int custo) {

        int posicao = Position(cache1);

        if (!cache1[posicao].isAtualizado()) {
            BlocoMemoria aux = cache1[posicao];
            cache1[posicao] = cache2[pos];
            cache2[pos] = aux;
            cache1[posicao].setCusto(custo);

        } else {
            BlocoMemoria aux = cache1[posicao];
            cache1[posicao] = cache2[pos];
            cache2[pos] = aux;
            cache1[posicao].setCusto(custo);
        }

        //cache1[posicao].setCusto(custo);
        return cache1[posicao];

    }

    private static int Position(BlocoMemoria[] cache1) {
        long compara = Long.MIN_VALUE;
        int posicao = 0;

        if (verificaCheio(cache1) == 0)//nao ta cheio
        {

            for (int g = 0; g < cache1.length; g++) {
                if (cache1[g].isAtualizado() == false) {
                    posicao = g;
                }
            }

        } else if (verificaCheio(cache1) == 1) //LRU
        {

            long saida = System.nanoTime();

            for (int h = 0; h < cache1.length; h++) {
                long diferenca = saida - cache1[h].tempo;

                if (diferenca > compara) {
                    compara = diferenca;
                    posicao = h;
                }
            }
        }
        return posicao;
    }

    public static BlocoMemoria buscarNasMemorias(Endereco e, BlocoMemoria[] RAM, BlocoMemoria[] cache1, BlocoMemoria[] cache2, BlocoMemoria[] cache3) {

        //mapeamento direto a memoria
        int posicaoCache1 = e.getEndBloco() % cache1.length;
        int posicaoCache2 = e.getEndBloco() % cache2.length;
        int posicaoCache3 = e.getEndBloco() % cache3.length;
        int custo = 0;

        if (cache1[posicaoCache1].getEndBloco() == e.getEndBloco()) {
            custo += 10;
            cache1[posicaoCache1].setCusto(custo);
            cache1[posicaoCache1].setCacheHit(1);

            return cache1[posicaoCache1];
        } else {

            if (cache2[posicaoCache2].getEndBloco() == e.getEndBloco()) {
                custo += 110;
                cache2[posicaoCache2].setCacheHit(2);

                return testaCache1Cache2(posicaoCache1, posicaoCache2, cache1, cache2, custo);
            } else {
                if (cache3[posicaoCache3].getEndBloco() == e.getEndBloco()) {
                    custo += 1100;
                    cache3[posicaoCache3].setCacheHit(3);
                    cache2[posicaoCache2] = cache3[posicaoCache3];
                    return testaCache1Cache2(posicaoCache1, posicaoCache2, cache1, cache2, custo);
                } else {
                    //buscar na RAM E O REPASSAR A C2 E DEPOIS A C1
                    custo += 11100;
                    if (!cache3[posicaoCache3].isAtualizado())// se cache3 tiver com lixo
                    {
                        cache3[posicaoCache3] = RAM[e.getEndBloco()];//cache 2 recebe a bloco que ta na RAM
                        cache3[posicaoCache3].setCacheHit(4);
                        cache2[posicaoCache2] = cache3[posicaoCache3];
                        return testaCache1Cache2(posicaoCache1, posicaoCache2, cache1, cache2, custo);

                    } else// caso o q tenha no cache nao seja lixo
                    {
                        RAM[cache3[posicaoCache3].getEndBloco()] = cache3[posicaoCache3];//vai salvar na RAM
                        RAM[cache3[posicaoCache3].getEndBloco()].setDesatualizado();//virar false

                        cache3[posicaoCache3] = RAM[e.getEndBloco()];// RAM repassa para cache3
                        cache3[posicaoCache3].setCacheHit(4);
                        cache2[posicaoCache2] = cache3[posicaoCache3];//cache3 repassa para cache2
                        return testaCache1Cache2(posicaoCache1, posicaoCache2, cache1, cache2, custo);//cache 2 passa pra 1
                    }
                }
            }

        }
    }

    private static BlocoMemoria testaCache1Cache2(int posicaoCache1, int posicaoCache2, BlocoMemoria[] cache1, BlocoMemoria[] cache2, int custo) {

        if (!cache1[posicaoCache1].isAtualizado()) {
            cache1[posicaoCache1] = cache2[posicaoCache2];
            //cache2[posicaoCache2] = //qq lixo
        } else {
            BlocoMemoria aux = cache1[posicaoCache1];
            cache1[posicaoCache1] = cache2[posicaoCache2];
            cache2[posicaoCache2] = aux;
        }

        cache1[posicaoCache1].setCusto(custo);

        return cache1[posicaoCache1];
    }
    public static BlocoMemoria transferencia_HD(BlocoMemoria[] RAM, Endereco e, int indice) throws IOException
    {
        int r0, r1, r2, r3;
        int w0, w1, w2, w3;
        int palavra[] = new int[4];
        RandomAccessFile file = null;
        System.out.println("Indice" + indice);
       
        try
        {
            file = new RandomAccessFile(new File("HD.bin"), "r");
            //long filePointer = file.getFilePointer();
            
            System.out.println("Ponteiro de leitura 1" + file.getFilePointer());
            file.seek(e.endBloco);
            System.out.println("Ponteiro de leitura 2" + file.getFilePointer());
            w0 = (int) file.read();
            w1 = (int) file.read();
            w2 = (int) file.read();
            w3 = (int) file.read();
            System.out.println("Ponteiro de leitura 3" + file.getFilePointer());
           
             
            file.seek(0);
            file.close();
 
            if (RAM[indice].isAtualizado() == true)
            {
                RAM[indice].setDesatualizado();
                
                palavra = RAM[indice].getPalavras();
                
                r0 = RAM[indice].palavras[0];
                r1 = RAM[indice].palavras[1];
                r2 = RAM[indice].palavras[2];
                r3 = RAM[indice].palavras[3];
                

                file = new RandomAccessFile(new File("HD.bin"), "w");
      
                file.seek(RAM[indice].endBloco);
                file.write(r0);
                file.write(r1);
                file.write(r2);
                file.write(r3);
                
                RAM[indice].endBloco = e.endBloco;
                palavra[0] = w0;
                palavra[1] = w1;
                palavra[2] = w2;
                palavra[3] = w3;

                RAM[indice].setPalavras(palavra);

                file.seek(0);
                file.close();

            }else
            {
                RAM[indice].endBloco = e.endBloco;
         
                palavra[0] = w0;
                palavra[1] = w1;
                palavra[2] = w2;
                palavra[3] = w3;

                RAM[indice].setPalavras(palavra);
            }
            
            return RAM[indice];

        }catch(FileNotFoundException ex)
        {
            Logger.getLogger(MMU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }   
        
    
    /*public int posicao_HD(Endereco e) throws FileNotFoundException
    {
        int posicao = 0;
       
        try
        {
            RandomAccessFile file = null;
            file = new RandomAccessFile(new File("HD.bin"), "r");
            int procurado = e.endBloco;
            //long filePointer = file.getFilePointer();
            
            long atual = 0;
            file.seek(0);
            
            while(atual < file.length())
            {
                
                int resultado = file.readInt();
                
                if(resultado == procurado)
                {
                    posicao = (int) atual;
                }
                atual = file.getFilePointer();
            }
            file.close();
            
        }catch(IOException r)
        {
        }
        
        return posicao;
    }*/

}