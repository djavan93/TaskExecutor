public class Trabalhador extends Thread{
    private Arquivo arquivoCompartilhado;
    private Tarefa tarefa;
    private Executor executor;
    
    private long tempoInicio;

    public Trabalhador(Executor executor, Arquivo arquivoCompartilhado){
        this.executor = executor;
        this.arquivoCompartilhado = arquivoCompartilhado;
    }
    
    public void escrita(){
        try {
            sleep((long)tarefa.custo * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int valorResultado = arquivoCompartilhado.escrita(tarefa.valor);
        executor.guardarResultado(new Resultado(tarefa.idTarefa, valorResultado, (System.currentTimeMillis() - tempoInicio) ));
    }

    public void leitura(){
        try {
            sleep((long)tarefa.custo * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  
        }
        
        int valorResultado = arquivoCompartilhado.leitura();
        executor.guardarResultado(new Resultado(tarefa.idTarefa, valorResultado, (System.currentTimeMillis() - tempoInicio) ));
    }

    public void run(){
        Tarefa novaTarefa = executor.pegarTarefa();
        while(novaTarefa != null){
            tarefa = novaTarefa;
            tempoInicio = System.currentTimeMillis();
            if (tarefa.tipo == 0) {
                escrita();
            }
            else{
                leitura();
            }
            novaTarefa = executor.pegarTarefa(); 
        }
    }
}
