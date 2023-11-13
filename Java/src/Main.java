import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Main {
    //Testes
    static ArrayList<Integer> N = new ArrayList<Integer>(Arrays.asList(5, 7)); //(5, 7, 9));
    static ArrayList<Integer> E = new ArrayList<Integer>(Arrays.asList(0, 40)); 
    static ArrayList<Integer> T = new ArrayList<Integer>(Arrays.asList(1, 16, 256));

    static long tempoTestes = 0;
    
    public static void main(String[] args) {
        int numeroTeste = 1;
        long tempoInicio = System.currentTimeMillis();
        try {
            String caminhoArquivo = "resultado_"+ UUID.randomUUID() + ".txt";
            FileOutputStream arquivoSaida = new FileOutputStream(caminhoArquivo);
            PrintStream printStream = new PrintStream(arquivoSaida);
            System.setOut(printStream);
            for(int i = 0; i < N.size(); i++){
                for(int j = 0; j < E.size(); j++){
                    for(int k = 0; k < T.size(); k++){
                        int[] entradas = lerEntradas(i, j, k);
                        TaskExecutor executor = new TaskExecutor(entradas[0], entradas[1], entradas[2]);
                        System.out.print(numeroTeste + " - Teste (N = " + entradas[0] + ", E = " + entradas[1] + ", T = " + entradas[2] + "): Tempo = ");

                        executor.iniciar();

                        numeroTeste++;
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

    private static int[] lerEntradas(int i, int j, int k){
        int[] vetor = {N.get(i), E.get(j), T.get(k)};
        return vetor;
    }
}
