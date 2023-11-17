public class Resultado {
    private long idTarefa;
    private int resultado;
    private long tempo;

    public Resultado(long idTarefa, int resultado, long tempo) {
        this.idTarefa = idTarefa;
        this.resultado = resultado;
        this.tempo = tempo;
    }

    public long getIdTarefa() {
        return idTarefa;
    }

    public int getResultado() {
        return resultado;
    }

    public long getTempo() {
        return tempo;
    }
}
