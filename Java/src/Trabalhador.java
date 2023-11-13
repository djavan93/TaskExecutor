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
        //i. Aguardar o tempo relacionado ao parâmetro Custo
        try {
            sleep((long)tarefa.custo * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //ii. Ler o valor mantido em um arquivo compartilhado entre os tra
        //arquivoCompartilhado.leitura();
        //iii. Somar o valor lido do arquivo ao parâmetro informado na tarefa
        int valorResultado = arquivoCompartilhado.escrita(tarefa.valor);
        //iv. Escrever o resultado da soma no arquivo
        executor.guardarResultado(new Resultado(tarefa.idTarefa, valorResultado, (System.currentTimeMillis() - tempoInicio) ));
        

    }

    public void leitura(){
        //i. Aguardar o tempo relacionado ao parâmetro Custo
        try {
            sleep((long)tarefa.custo * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  
        }
        //ii. Ler o valor mantido em um arquivo compartilhado entre os tra
        int valorResultado = arquivoCompartilhado.leitura();
        executor.guardarResultado(new Resultado(tarefa.idTarefa, valorResultado, (System.currentTimeMillis() - tempoInicio) ));
        
        
    }

    public void run(){
        while(executor.possuiElementos){
            Tarefa novaTarefa = executor.getTarefa();
            if(novaTarefa == null){
                continue;
            }
            else{
                tarefa = novaTarefa;
                tempoInicio = System.currentTimeMillis();
                if (tarefa.tipo == 0) {
                    escrita();
                }
                else{
                    leitura();
                }
            }
        }
    }
}
