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
            sleep((long)tarefa.getCusto() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int valorResultado = arquivoCompartilhado.escrita(tarefa.getValor());
        executor.guardarResultado(new Resultado(tarefa.getIdTarefa(), valorResultado, (System.currentTimeMillis() - tempoInicio) ));
    }

    public void leitura(){
        try {
            sleep((long)tarefa.getCusto() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  
        }
        
        int valorResultado = arquivoCompartilhado.leitura();
        executor.guardarResultado(new Resultado(tarefa.getIdTarefa(), valorResultado, (System.currentTimeMillis() - tempoInicio) ));
    }

    public void run(){
        tarefa = executor.pegarTarefa();
        while(tarefa != null){
            tempoInicio = System.currentTimeMillis();

            if (tarefa.getTipo() == 0) {
                escrita();
            }
            else {
                leitura();
            }

            tarefa = executor.pegarTarefa();
        }
    }
}
