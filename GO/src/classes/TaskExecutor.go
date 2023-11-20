package classes

import (
	"math"
	"math/rand"
	"sync"
	"time"
)

type TaskExecutor struct {
	N          int
	E          int
	T          int
	Tarefas    []*Tarefa
	Resultados []*Resultado
	Arquivo    *Arquivo
}

func CriarTaskExecutor(n int, e int, t int) TaskExecutor{
	return TaskExecutor{
		N: n,
		E: e,
		T: t,
		Arquivo: CriarArquivo(),
	}
}

func (te *TaskExecutor) Iniciar() int64{
	te.AlimentarTarefas()
	canalBufferizado := make(chan *Tarefa, (te.T + 50))

	tempoInicioTeste := time.Now()
	executor := CriarExecutor(te, canalBufferizado)
	go executor.Run()

	te.IniciarTrabalhadores(executor, canalBufferizado)

	tempoFinalTeste := time.Since(tempoInicioTeste).Milliseconds()
	
	return tempoFinalTeste
}

func (te *TaskExecutor) AlimentarTarefas() {
	potenciaDe10 := int64(math.Pow(float64(10), float64(te.N)))

	for i := int64(0); i < potenciaDe10; i++ {
		custo := rand.Float64() * 0.01
		tipo := 0
		if rand.Float64() <= float64(te.E)/100 {
			tipo = 1
		}
		valor := rand.Intn(11)
		te.Tarefas = append(te.Tarefas, CriarTarefa(i, custo, tipo, valor))
	}
}

func (te *TaskExecutor) IniciarTrabalhadores(executor *Executor, canalBufferizado <-chan *Tarefa) {
	var wg sync.WaitGroup

	for i := 0; i < te.T; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			trabalhador := CriarTrabalhador(te.Arquivo, executor, canalBufferizado)
			trabalhador.Run()
		}()
	}

	wg.Wait()
}