package main

import (
	"fmt"
	"math/rand"
	"os"
	"sync"
	"time"
)

// Arquivo representa o arquivo compartilhado
type Arquivo struct {
	valor int
	mu    sync.Mutex
}

// Escrita atualiza o valor do arquivo
func (a *Arquivo) Escrita(v int) int {
	a.mu.Lock()
	defer a.mu.Unlock()

	a.valor += v
	return a.valor
}

// Leitura retorna o valor atual do arquivo
func (a *Arquivo) Leitura() int {
	return a.valor
}

// Tarefa representa uma tarefa a ser executada
type Tarefa struct {
	idTarefa int64
	custo    float64
	tipo     int // 0 - Escrita / 1 - Leitura
	valor    int
}

// Executor representa um executor de tarefas
type Executor struct {
	tarefas         []Tarefa
	taskExecutor    *TaskExecutor
	possuiElementos bool
}

// DespacharTarefa adiciona uma tarefa à lista de tarefas
func (e *Executor) DespacharTarefa(tarefa Tarefa) {
	e.tarefas = append(e.tarefas, tarefa)
}

// PegarTarefa retorna e remove uma tarefa da lista de tarefas
func (e *Executor) PegarTarefa() Tarefa {
	for len(e.tarefas) == 0 && e.possuiElementos {
		time.Sleep(time.Millisecond) // Espera por tarefas se não houver
	}

	if len(e.tarefas) > 0 {
		tarefa := e.tarefas[0]
		e.tarefas = e.tarefas[1:]
		return tarefa
	}

	return Tarefa{} // Retorna tarefa vazia se não houver mais tarefas
}

// GuardarResultado adiciona um resultado à lista de resultados
func (e *Executor) GuardarResultado(resultado Resultado) {
	e.taskExecutor.Resultados = append(e.taskExecutor.Resultados, resultado)
}

// AcordarTrabalhadores acorda todos os trabalhadores
func (e *Executor) AcordarTrabalhadores() {
	// Não é necessário implementar explicitamente em Go
	// Goroutines serão despertadas automaticamente
}

// Run inicia a execução do Executor
func (e *Executor) Run() {
	for len(e.taskExecutor.Tarefas) > 0 {
		tarefa := e.taskExecutor.Tarefas[0]
		e.taskExecutor.Tarefas = e.taskExecutor.Tarefas[1:]

		if tarefa != (Tarefa{}) {
			e.DespacharTarefa(tarefa)
		}
	}

	e.possuiElementos = false

	if len(e.tarefas) > 0 {
		e.AcordarTrabalhadores()
	}
}

// Trabalhador representa um trabalhador que executa tarefas
type Trabalhador struct {
	arquivoCompartilhado *Arquivo
	tarefa               Tarefa
	executor             *Executor
	tempoInicio          time.Time
}

// Escrita simula uma operação de escrita
func (t *Trabalhador) Escrita() {
	time.Sleep(time.Duration(t.tarefa.custo * float64(time.Second)))

	valorResultado := t.arquivoCompartilhado.Escrita(t.tarefa.valor)
	resultado := Resultado{
		idTarefa:  t.tarefa.idTarefa,
		resultado: valorResultado,
		tempo:     time.Since(t.tempoInicio).Milliseconds(),
	}

	t.executor.GuardarResultado(resultado)
}

// Leitura simula uma operação de leitura
func (t *Trabalhador) Leitura() {
	time.Sleep(time.Duration(t.tarefa.custo * float64(time.Second)))

	valorResultado := t.arquivoCompartilhado.Leitura()
	resultado := Resultado{
		idTarefa:  t.tarefa.idTarefa,
		resultado: valorResultado,
		tempo:     time.Since(t.tempoInicio).Milliseconds(),
	}

	t.executor.GuardarResultado(resultado)
}

// Run inicia a execução do Trabalhador
func (t *Trabalhador) Run() {
	novaTarefa := t.executor.PegarTarefa()
	for novaTarefa != (Tarefa{}) {
		t.tarefa = novaTarefa
		t.tempoInicio = time.Now()

		if t.tarefa.tipo == 0 {
			t.Escrita()
		} else {
			t.Leitura()
		}

		novaTarefa = t.executor.PegarTarefa()
	}
}

// TaskExecutor representa o executor principal
type TaskExecutor struct {
	N          int
	E          int
	T          int
	Tarefas    []Tarefa
	Resultados []Resultado
	arquivo    *Arquivo
}

// Iniciar inicia a execução do TaskExecutor
func (te *TaskExecutor) Iniciar() {
	te.AlimentarTarefas()

	tempoInicioTeste := time.Now()
	executor := &Executor{taskExecutor: te, possuiElementos: true}
	go executor.Run()

	te.IniciarTrabalhadores(executor)

	tempoFinalTeste := time.Since(tempoInicioTeste).Milliseconds()
	fmt.Println(tempoFinalTeste, "ms")
}

// AlimentarTarefas gera tarefas aleatórias
func (te *TaskExecutor) AlimentarTarefas() {
	resultado := int64(1)
	for i := 0; i < te.N; i++ {
		resultado *= 10
	}

	for i := int64(0); i < resultado; i++ {
		custo := rand.Float64() * 0.01
		tipo := 0
		if rand.Float64() <= float64(te.E)/100 {
			tipo = 1
		}
		valor := rand.Intn(11)
		te.Tarefas = append(te.Tarefas, Tarefa{idTarefa: i, custo: custo, tipo: tipo, valor: valor})
	}
}

// IniciarTrabalhadores inicia os trabalhadores
func (te *TaskExecutor) IniciarTrabalhadores(executor *Executor) {
	var wg sync.WaitGroup

	for i := 0; i < te.T; i++ {
		wg.Add(1)
		go func() {
			defer wg.Done()
			trabalhador := &Trabalhador{executor: executor, arquivoCompartilhado: te.arquivo}
			trabalhador.Run()
		}()
	}

	wg.Wait()
}

// Resultado representa o resultado de uma tarefa
type Resultado struct {
	idTarefa  int64
	resultado int
	tempo     int64
}

// pow é uma função auxiliar para calcular potências

var (
	N           = []int{5, 7}
	E           = []int{0, 40}
	T           = []int{1, 16, 256}
	tempoTestes int64
)

func main() {
	numeroTesteContador := 1
	tempoInicio := time.Now()
	fmt.Println("Sistema em execução!\nEm breve o arquivo de resultado estará na pasta resultados")

	tryCreateFolder("resultados")

	for i := 0; i < len(N); i++ {
		for j := 0; j < len(E); j++ {
			for k := 0; k < len(T); k++ {
				entradas := lerEntradas(i, j, k)
				executor := TaskExecutor{N: entradas[0], E: entradas[1], T: entradas[2]}
				fmt.Printf("%d - Teste (N = %d, E = %d, T = %d): Tempo = ", numeroTesteContador, entradas[0], entradas[1], entradas[2])

				executor.Iniciar()

				numeroTesteContador++
			}
		}
	}

	fmt.Println("Tempo gasto pelos trabalhadores para executar todos os testes:", tempoTestes, "ms")
	fmt.Println("Tempo gasto pelo sistema:", time.Since(tempoInicio).Milliseconds(), "ms")
}

func tryCreateFolder(folderName string) {
	_, err := os.Stat(folderName)
	if os.IsNotExist(err) {
		os.Mkdir(folderName, os.ModePerm)
	}
}

func somarTempoTestes(tempo int64) {
	tempoTestes += tempo
}

func lerEntradas(i, j, k int) []int {
	return []int{N[i], E[j], T[k]}
}

// ... (outras funções auxiliares e definições)
