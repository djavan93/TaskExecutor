package main

import (
	"fmt"
	"TaskExecutor/classes"
	"time"
	"github.com/google/uuid"
	"os"
	"strconv"
  _ "net/http/pprof"
	"log"
	"net/http"
)

var (
	N           = []int{5, 7}
	E           = []int{0, 40}
	T           = []int{1}//16, 256}
	tempoTestes int64
)

func main() {
	go func() {
		log.Println(http.ListenAndServe("localhost:6060", nil))
	}()
	numeroTesteContador := 1
	tempoInicio := time.Now()
	fmt.Println("Sistema em execução!\nEm breve o arquivo de resultado estará na pasta resultados")

	arquivo, err := os.Create("resultados/resultado_" + uuid.New().String() + ".txt")
	if err != nil {
		fmt.Println("Erro ao criar o arquivo:", err)
		return
	}
	defer arquivo.Close()

	conteudo := ""

	for i := 0; i < len(N); i++ {
		for j := 0; j < len(E); j++ {
			for k := 0; k < len(T); k++ {
				entradas := lerEntradas(i, j, k)

				resultadoValores, err := os.Create("resultadosValores/resultadosValores_" + strconv.Itoa(numeroTesteContador) + ".txt")
				if err != nil {
					fmt.Println("Erro ao criar o arquivo:", err)
					return
				}
				defer arquivo.Close()

				executor := classes.CriarTaskExecutor(entradas[0], entradas[1], entradas[2], resultadoValores)

				conteudo += strconv.Itoa(numeroTesteContador) + " - Teste (N = " + strconv.Itoa(entradas[0]) + ", E = " + strconv.Itoa(entradas[1]) + ", T = " + strconv.Itoa(entradas[2]) + "): Tempo = "

				tempoTeste := executor.Iniciar()
				tempoTestes += tempoTeste

				conteudo += strconv.FormatInt(tempoTeste, 10) + "ms\n"

				numeroTesteContador++
			}
		}
	}

	conteudo += "Tempo gasto pelos trabalhadores para executar todos os testes:" + strconv.FormatInt(tempoTestes, 10) + "ms\n"
	conteudo += "Tempo gasto pelo sistema:" + strconv.FormatInt(time.Since(tempoInicio).Milliseconds(), 10) + "ms\n"

	_, err = arquivo.Write([]byte(conteudo))
	if err != nil {
		fmt.Println("Erro ao escrever no arquivo:", err)
	} else {
		fmt.Println("Conteúdo foi escrito com sucesso!")
	}
}

func somarTempoTestes(tempo int64) {
	tempoTestes += tempo
}

func lerEntradas(i, j, k int) []int {
	return []int{N[i], E[j], T[k]}
}
