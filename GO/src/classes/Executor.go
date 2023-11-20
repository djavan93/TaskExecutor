package classes

type Executor struct {
	TaskExecutor    *TaskExecutor
	PossuiElementos bool
	CanalBufferizado chan<- *Tarefa
}

func CriarExecutor(taskExecutor *TaskExecutor, canalBufferizado chan<- *Tarefa) *Executor{
	return &Executor{
		TaskExecutor: taskExecutor,
		PossuiElementos: true,
		CanalBufferizado: canalBufferizado,
	}
}

func (e *Executor) Run() {
	for len(e.TaskExecutor.Tarefas) > 0 {
		tarefa := e.TaskExecutor.Tarefas[0]
		e.TaskExecutor.Tarefas = e.TaskExecutor.Tarefas[1:]

		e.DespacharTarefa(tarefa)
	}

	close(e.CanalBufferizado)
}

func (e *Executor) DespacharTarefa(tarefa *Tarefa) {
	e.CanalBufferizado <- tarefa
}

func (e *Executor) GuardarResultado(resultado *Resultado) {
	e.TaskExecutor.Resultados = append(e.TaskExecutor.Resultados, resultado)
}
