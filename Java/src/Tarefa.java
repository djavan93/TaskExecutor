
public class Tarefa {
	long idTarefa;
	double custo;
	// 0 - Escrita / 1 - Leitura
	int tipo;
	int valor;
	
	public Tarefa(long idTarefa, double custo, int tipo, int valor) {
		this.idTarefa = idTarefa;
		this.custo = custo;
		this.tipo = tipo;
		this.valor = valor;
	}
}
