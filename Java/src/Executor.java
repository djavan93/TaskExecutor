import java.util.LinkedList;
import java.util.Queue;

public class Executor extends Thread{
    Queue<Tarefa> tarefas;
    TaskExecutor taskExecutor;
    boolean possuiElementos;

    public Executor(TaskExecutor taskExecutor){
        tarefas = new LinkedList<Tarefa>();
        possuiElementos = true;
    }

    public void run(){
        Tarefa tarefa = taskExecutor.Tarefas.poll();
        while(tarefa != null){
            tarefas.add(tarefa);
            tarefa = taskExecutor.Tarefas.poll();
            notify();
        }
        //Verifica se possui elementos restantes na fila
        while(tarefas.size() > 0){
            notify();
        }
        possuiElementos = false;
        notifyAll();
    }

    public Tarefa getTarefa(){
        Tarefa novaTarefa = tarefas.poll();
        while(novaTarefa == null && possuiElementos == true){
            try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            novaTarefa = tarefas.poll();
        }
    
        return novaTarefa;
    }

    public void guardarResultado(Resultado resultado){
        taskExecutor.Resultados.add(resultado);
    }
}


