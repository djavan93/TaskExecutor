import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Executor extends Thread{
    private Queue<Tarefa> tarefas;

    TaskExecutor taskExecutor;
    boolean possuiElementos;

    public Executor(String name, TaskExecutor taskExecutor){
        super(name);
        this.taskExecutor = taskExecutor;
        tarefas = new LinkedList<Tarefa>();
        possuiElementos = true;
    }

    public void run(){
        while(taskExecutor.Tarefas.size() > 0){
            despacharTarefa(taskExecutor.Tarefas.remove());
        } 
        possuiElementos = false;
    }

    public void guardarResultado(Resultado resultado){
        taskExecutor.Resultados.add(resultado);
    }

	public synchronized void despacharTarefa(Tarefa tarefa) {
		tarefas.add(tarefa);
		notify();
	}

	public synchronized Tarefa pegarTarefa() {
		while (tarefas.size() == 0) {
			//System.out.print("Buffer is empty. ");
			//System.out.print(Thread.currentThread().getName() + " suspended.\n");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Tarefa tarefa = tarefas.poll();
		//System.out.println(Thread.currentThread().getName() + " removed " + tarefa);
		return tarefa;
	}
}


