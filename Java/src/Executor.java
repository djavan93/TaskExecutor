import java.util.LinkedList;
import java.util.Queue;

public class Executor extends Thread{
    Queue<Tarefa> tarefas;

    TaskExecutor taskExecutor;
    boolean possuiElementos;

    public Executor(String name, TaskExecutor taskExecutor){
        super(name);
        this.taskExecutor = taskExecutor;
        tarefas = new LinkedList<Tarefa>();
        possuiElementos = true;
    }

    public void run(){
        Tarefa tarefa = null;
        while(!taskExecutor.Tarefas.isEmpty()){
            tarefa = taskExecutor.Tarefas.poll();
            if(tarefa != null){
                despacharTarefa(tarefa);
            }
        } 
        possuiElementos = false;

        if(tarefas.size() > 0) {
            acordarTrabalhadores();
        }
    }

    public void guardarResultado(Resultado resultado){
        taskExecutor.Resultados.add(resultado);
    }

	public synchronized void despacharTarefa(Tarefa tarefa) {
		tarefas.add(tarefa);
		notify();
	}

	public synchronized Tarefa pegarTarefa() {
		while (tarefas.size() == 0 && possuiElementos == true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return tarefas.poll();
	}

    public synchronized void acordarTrabalhadores(){
        notifyAll();
    }
}
