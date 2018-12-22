/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alocacaotarefast2;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Larissa Guder
 */
public class AlocacaoTarefasT2 {

    private static float soma = 0;
    private static float somaCarga = 0;
    private static int coluna = 0;
    private static int linha = 0;
    private static float somacargasmatriz = 0;
    private static int imprimeMATRIZ = 0;
    private static float[][] Alocada = new float[4][4];
    private static float[][] matriz = new float[4][4];
    private static String[][] nomeTarefas = new String[4][4];
    private static float[][] restante = new float[4][4];
    private static int idApp = 0;
    private static int valMax = 0;
    private static int SomaGeralCarga = 0;
    private static int[][] mat = new int[0][0];

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                nomeTarefas[i][j] = "";
            }

        }

        System.out.println("-------- Inicio da Aplicação ---------");

        while (true) {
            int respostaUs;
            System.out.println("************ Insira ************");
            System.out.println("** 0 para uma nova aplicação ***");
            System.out.println("* Qualquer valor para encerrar *");

            respostaUs = entrada.nextInt();

            if (respostaUs == 0) {

                float[] vetorPRINT = new float[7];
                // CHAMA OS MÉTODOS
                int numTarefaPRINT = (NumerodeTarefas());
                System.out.println("----------------------->");

                float carga = 0;

////////////////////////////////
                Alocada = AlocaNaMatriz(carga, vetorPRINT, numTarefaPRINT);
                System.out.println("----------------------->");

                int[][] imprime = Tar_Com(numTarefaPRINT);
                System.out.println("----------------------->");

                ///////////////////////////
                saida(carga, numTarefaPRINT, vetorPRINT, mat);
                if (SomaGeralCarga >= 16) {
                    break;
                }

                if (valMax >= 48) {
                    break;
                }

                System.out.println("\n\n\n------------- PRÓXIMA APLICAÇÃO -------------\n \n \n ");

            } else {
                System.out.println("---------------------------- FIM ----------------------------");
                break;
            }
        }
    }
// ADICIONA O NUMERO DE TAREFAS

    public static int NumerodeTarefas() {
        Scanner entrada = new Scanner(System.in);

        int numTarefa;
        int aplicação = 1;
        System.out.print("Insira o número de tarefas da aplicação " + aplicação + " : ");
        numTarefa = entrada.nextInt();
        valMax += numTarefa;
        while (numTarefa < 0 || numTarefa > 7) {
            System.out.println("O número de tarefas deve ser inferior a 7 e superior a 0!");
            System.out.print("Insira o número de tarefas da aplicação " + aplicação + " : ");
            numTarefa = entrada.nextInt();
        }

        return numTarefa;

    }
// ADICIONA AS CARGAS

    public static float CargaVetor(int numTarefaPRINT) {

        float carga = 0;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite a carga da " + "tarefa :  "); // aqui pede as cargas das tarefas  
        carga = entrada.nextFloat();

        for (; carga > 1 || carga < 0;) {  // aqui verifica se a carga é válida
            System.out.println("A carga deve ser entre 0 e 1");
            System.out.println("Digite novamente a carga da " + " tarefa: ");
            carga = entrada.nextFloat();

        }

        return carga;

    }
// ALOCA AS VARIAVEIS NA MATRIZ

    public static float[][] AlocaNaMatriz(float carga, float[] vetorPRINT, int numTarefaPRINT) {

        for (int i = 0; i < numTarefaPRINT; i++) {
            carga = CargaVetor(numTarefaPRINT);
            vetorPRINT[i] = carga;
            soma += vetorPRINT[i];
            SomaGeralCarga += vetorPRINT[i];//

            idApp++;

            if (soma <= 1 && idApp <= 3) {
                somaCarga = soma;

            } else {
                idApp = 0;
                idApp++;

                if (soma <= 1 && idApp <= 3) {
                    somaCarga = soma;

                }
                if (coluna < 3) {
                    coluna++;
                } else {
                    if (linha == 3 && coluna == 3) {
                        System.out.println();
                        System.out.println("############################");
                        System.out.println("#### APLICAÇÃO ENCERADA ####");
                        System.out.println("######## MPSoC CHEIO #######");
                        System.out.println("############################");

                        System.exit(0);
                    }

                    linha++;
                    coluna = 0;

                }
                soma = vetorPRINT[i];
                somaCarga = soma;

            }

            matriz[linha][coluna] = somaCarga;

            nomeTarefas[linha][coluna] += "|T" + i + "|";

        }

        return matriz;
    }
// grafo das tarefas comunicantes

    public static int[][] Tar_Com(int numTarefaPRINT) {
        Scanner valor = new Scanner(System.in);
        System.out.println("Para ir a próxima aplicação, digite -1");
        // define variaveis
        int mat[][];

        // recebe o numero de vertices
        // zera a matriz
        mat = new int[numTarefaPRINT][numTarefaPRINT];
        for (int i = 0; i < numTarefaPRINT; i++) {
            for (int j = 0; j < numTarefaPRINT; j++) {
                mat[i][j] = 0;

            }

        }
        // o usuario informa os vizinhos
        int vizinho;
        for (int i = 0; i < numTarefaPRINT; i++) {
            System.out.println("Informe as tarefas comunicantes da tarefa: " + i);
            vizinho = valor.nextInt();
            // para terminar o usuario informa -1
            while (vizinho > numTarefaPRINT) {
                System.out.println("Ultrapassou o limite!");
                System.out.println("Informe as tarefas comunicantes da tarefa: " + i);
                vizinho = valor.nextInt();

            }
            while (vizinho != -1) {

                mat[i][vizinho] = 1;
                mat[vizinho][i] = 1;
                vizinho = valor.nextInt();
            }

        }
        return mat;

    }

    // IMPRIME TUDO
    public static void saida(float carga, int numTarefaPRINT, float[] vetorPRINT, int[][] mat) {

        System.out.println("\n");
        System.out.println("------------------ RESULTADOS ------------------");
        System.out.println("NÚMERO DE TAREFAS :|" + numTarefaPRINT + "|");
        System.out.print("Carga das tarefas : ");
        System.out.print(Arrays.toString(vetorPRINT));
        System.out.println("\n");

        imprimematriz(Alocada);
        EspacoRestante(Alocada);
        IdDaApp(Alocada);

    }

    // GRAFO VIZINHOS
    // APP ALOCADAS
    public static void imprimematriz(float[][] Alocada) {
        DecimalFormat df = new DecimalFormat("#0.0");

        System.out.println("\n");
        System.out.println("-------------------- ALOCAÇÃO DAS TAREFAS --------------------");
        System.out.println();

        System.out.println("\n");
        // resultado da alocação
        for (int i = 0; i < Alocada.length; i++) {
            for (int j = 0; j < Alocada.length; j++) {
                somacargasmatriz += Alocada[i][j];

                System.out.print("|" + i + "||" + j + "|" + " [" + df.format(Alocada[i][j]) + "]" + "\t");
            }
            System.out.println();
        }

    }

    // ESPAÇO RESTANTE
    public static void EspacoRestante(float[][] Alocada) {
        DecimalFormat df = new DecimalFormat("#0.0");

        System.out.println("\n");
        System.out.println(" ---------------------- ESPAÇO RESTANTE ----------------------");
        System.out.println();
        float valor = 0;
        System.out.println("\n");
        // resultado da alocação
        for (int i = 0; i < Alocada.length; i++) {
            for (int j = 0; j < Alocada.length; j++) {

                valor = 1 - Alocada[i][j];
                restante[i][j] = valor;

                System.out.print("|" + i + "||" + j + "|" + " [" + df.format(restante[i][j]) + "]" + "\t");
            }
            System.out.println();
        }
    }

    // IMPRIME O ID
    public static void IdDaApp(float[][] Alocada) {
        System.out.println("");
        System.out.println("------------------ LOCALIZAÇÃO DAS APLICAÇÕES ------------------");
        for (int i = 0; i < Alocada.length; i++) {
            for (int j = 0; j < Alocada.length; j++) {
                System.out.println("|" + i + "||" + j + "|" + "[" + nomeTarefas[i][j] + "]" + "\t");

            }
            System.out.println();

        }
    }

}
