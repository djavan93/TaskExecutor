import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class TaskExecutor {
    int N;
    int E;
    int T;

    Queue<Tarefa> Tarefas;
    Queue<Resultado> Resultados;

    Arquivo arquivo;

    public TaskExecutor(int N, int E, int T, File caminho){
        this.N = N;
        this.E = E;
        this.T = T;

        Tarefas = new LinkedList<Tarefa>();
        Resultados = new LinkedList<Resultado>();

        arquivo = new Arquivo(caminho);
    }

    public void iniciar(){
        alimentarTarefas();
        long tempoInicioTeste = System.currentTimeMillis();
        Executor executor = new Executor(this);
        executor.start();
        
        iniciarTrabalhadores(executor);

        long tempoFinalTeste = System.currentTimeMillis() - tempoInicioTeste;
        System.out.println(tempoFinalTeste + " ms");
        Main.somarTempoTestes(tempoFinalTeste);

    }

    public void alimentarTarefas(){
        for(long i = 0; i < Math.pow(10, N); i++){
            double custo = Math.random() * 0.01;
            int tipo = Math.random() <= (double)E/100 ? 0 : 1;
            int valor = (int) Math.floor(Math.random() * 11);
            Tarefas.add(new Tarefa(i, custo, tipo, valor));
        }
    }

    public void iniciarTrabalhadores(Executor executor){
        Trabalhador[] trabalhadores = new Trabalhador[T];

        for (int i = 0; i < T; i++) {
			trabalhadores[i] = new Trabalhador(executor, arquivo);
		}
		for (int i = 0; i < T; i++) {
			trabalhadores[i].start();
            trabalhadores[i].setPriority(10);
		}
        
		try {
			for (int i = 0; i < T; i++) {
				trabalhadores[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
