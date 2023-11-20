package classes

type Tarefa struct{
	IdTarefa int64
	Custo float64
	Tipo int // 0 - Escrita / 1 - Leitura
	Valor int
}

func CriarTarefa(novoId int64, custo float64, tipo int, valor int) *Tarefa {
	return &Tarefa{
		IdTarefa: novoId, 
		Custo: custo, 
		Tipo: tipo, 
		Valor: valor,
	}
}
