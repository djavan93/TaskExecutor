
public class Tarefa {
	private int idTarefa;
	private double custo;
	private TipoTarefa tipo;
	private int valor;
	
	public Tarefa(int idTarefa, double custo, TipoTarefa tipo, int valor) {
		this.idTarefa = idTarefa;
		
		if(0 <= custo && custo <= 0.01) {
			this.custo = custo;
		}
		else {
			System.out.println("O custo não está entra 0 e 0.01");
		}
		
		this.tipo = tipo;
		
		if(0 <= valor && valor <= 10) {
			this.valor = valor;
		}
		else {
			System.out.println("O valor não está entre 0 e 10");
		}
	}
}
