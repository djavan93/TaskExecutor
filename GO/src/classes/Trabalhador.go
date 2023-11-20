package classes

import "time"

type Trabalhador struct {
	ArquivoCompartilhado *Arquivo
	Tarefa               *Tarefa
	Executor             *Executor
	TempoInicio          time.Time
	CanalBufferizado     <-chan *Tarefa
}

func CriarTrabalhador(arquivoCompartilhado *Arquivo, executor *Executor, canalBufferizado <-chan *Tarefa) *Trabalhador{
	return &Trabalhador{
		ArquivoCompartilhado: arquivoCompartilhado,
		Executor: executor,
		CanalBufferizado: canalBufferizado,
	}
}

func (t *Trabalhador) Run() {
	for tarefa := range t.CanalBufferizado {
		t.Tarefa = tarefa
		t.TempoInicio = time.Now()

		if t.Tarefa.Tipo == 0 {
			t.Escrita()
		} else {
			t.Leitura()
		}

	}
}

func (t *Trabalhador) Escrita() {
	//time.Sleep(time.Duration(t.Tarefa.Custo * float64(time.Second))/1000)

	valorResultado := t.ArquivoCompartilhado.Escrita(t.Tarefa.Valor)
	resultado := CriarResultado(t.Tarefa.IdTarefa, valorResultado, time.Since(t.TempoInicio).Milliseconds())

	t.Executor.GuardarResultado(resultado)
}

func (t *Trabalhador) Leitura() {
	//time.Sleep(time.Duration(t.Tarefa.Custo * float64(time.Second))/1000)

	valorResultado := t.ArquivoCompartilhado.Leitura()
	resultado := CriarResultado(t.Tarefa.IdTarefa, valorResultado, time.Since(t.TempoInicio).Milliseconds())

	t.Executor.GuardarResultado(resultado)
}
