
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Main {
    //Testes
    private static ArrayList<Integer> N = new ArrayList<Integer>(Arrays.asList(5, 7)); //(5, 7, 9));
    private static ArrayList<Integer> E = new ArrayList<Integer>(Arrays.asList(0, 40)); 
    private static ArrayList<Integer> T = new ArrayList<Integer>(Arrays.asList(1, 16, 256));

    private static long tempoTestes = 0;
    private static long tempoInicio;

    public static void main(String[] args) {
        int numeroTesteContador = 1;
        tempoInicio = System.currentTimeMillis();
        System.out.println("Sistema em executação!\nEm breve o arquivo de resultado estará na pasta resultados");

        try {
            File caminhoArquivo = new File("../resultados"); 
            FileOutputStream arquivoSaida = new FileOutputStream(caminhoArquivo + "/resultado_" + UUID.randomUUID() + ".txt");
            PrintStream printStream = new PrintStream(arquivoSaida);
            System.setOut(printStream);


            for(int i = 0; i < N.size(); i++){
                for(int j = 0; j < E.size(); j++){
                    for(int k = 0; k < T.size(); k++){


                        int[] entradas = lerEntradas(i, j, k);
                        File caminho = new File("../src/resultadosValores/resultadosValores_" + numeroTesteContador + ".txt");
                        TaskExecutor executor = new TaskExecutor(entradas[0], entradas[1], entradas[2], caminho);
                        System.out.print(numeroTesteContador + " - Teste (N = " + entradas[0] + ", E = " + entradas[1] + ", T = " + entradas[2] + "): Tempo = ");
                        
                        //Main.verificarTempo("inicio teste");
                        
                        executor.iniciar();

                        numeroTesteContador++;
                    }
                }
            }

            System.out.println("Tempo gasto pelos trabalhadores para executar todos os testes: " + tempoTestes + " ms");
            System.out.println("Tempo gasto pelo sistema: " + (System.currentTimeMillis() - tempoInicio) + " ms");

            printStream.close();
            arquivoSaida.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void somarTempoTestes(long tempo){
        tempoTestes += tempo;
    }

    private static int[] lerEntradas(int i, int j, int k){
        int[] vetor = {N.get(i), E.get(j), T.get(k)};
        return vetor;
    }

    public static void verificarTempo(String parte){
        System.out.println(parte + " durou " + (System.currentTimeMillis() - tempoInicio));
    }

}
