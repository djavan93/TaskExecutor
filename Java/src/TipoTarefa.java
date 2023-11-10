
public enum TipoTarefa {
	ESCRITA(true), LEITURA(false);
	
	private final boolean valor;
	
	TipoTarefa(boolean valor) {
		this.valor = valor;
	}
}
