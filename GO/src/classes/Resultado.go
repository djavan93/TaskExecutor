package classes

type Resultado struct{
	IdTarefa int64
	Resultado int
	Tempo int64
}

func CriarResultado(idTarefa int64, resultado int, tempo int64) *Resultado{
	return &Resultado{
		IdTarefa: idTarefa,
		Resultado: resultado,
		Tempo: tempo,
	}
}
