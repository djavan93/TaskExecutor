package classes

import "sync"

type Arquivo struct {
	Valor int
	Mu    sync.Mutex
}

func CriarArquivo() *Arquivo{
	return &Arquivo{
		Valor: 0,
	}
}

func (a *Arquivo) Escrita(v int) int {
	a.Mu.Lock()
	defer a.Mu.Unlock()

	a.Valor += v
	return a.Valor
}

func (a *Arquivo) Leitura() int {
	return a.Valor
}
