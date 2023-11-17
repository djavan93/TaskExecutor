public class Tarefa {
	private long idTarefa;
	private double custo;
	// 0 - Escrita / 1 - Leitura
	private int tipo;
	private int valor;
	
	public Tarefa(long idTarefa, double custo, int tipo, int valor) {
		this.idTarefa = idTarefa;
		this.custo = custo;
		this.tipo = tipo;
		this.valor = valor;
	}

	public long getIdTarefa() {
		return idTarefa;
	}

	public double getCusto() {
		return custo;
	}

	public int getTipo() {
		return tipo;
	}

	public int getValor() {
		return valor;
	}
}
