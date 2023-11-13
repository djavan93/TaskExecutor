import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    //Testes
    static ArrayList<Integer> N = new ArrayList<Integer>(Arrays.asList(5, 7, 9));
    static ArrayList<Integer> E = new ArrayList<Integer>(Arrays.asList(0, 40));
    static ArrayList<Integer> T = new ArrayList<Integer>(Arrays.asList(1, 16, 256));

    static long tempoTotal = 0;
    
    public static void main(String[] args) {
        int contador = 0;
        for(int i = 0; i < N.size(); i++){
            for(int j = 0; j < E.size(); j++){
                for(int k = 0; k < T.size(); k++){
                    int[] entradas = lerEntradas(i, j, k);
                    TaskExecutor executor = new TaskExecutor(entradas[0], entradas[1], entradas[2]);
                    System.out.print(contador + " - ");
                    executor.iniciar();
                    contador++;
                }
            }
        }

        System.out.println("Tempo gasto para executar todos os testes: " + tempoTotal + " ms");
        
    }

    private static int[] lerEntradas(int i, int j, int k){
        int[] vetor = {N.get(i), E.get(j), T.get(k)};
        return vetor;
    }
}
