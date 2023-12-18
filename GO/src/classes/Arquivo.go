package classes

import (
	"os"
	"sync"
	"fmt"
	"bufio"
	"strconv"
)

type Arquivo struct {
	Arquivo *os.File
	Mu    sync.Mutex
}

func CriarArquivo(arquivo *os.File) *Arquivo{
	return &Arquivo{
		Arquivo: arquivo,
	}
}

func (a *Arquivo) Escrita(v int) int {
	numero := a.Leitura()
	var err error

	
	err = a.Arquivo.Truncate(0)
	if err != nil {
		return 0
	}
	//a.Mu.Lock()
	conteudo := strconv.Itoa((numero + v))
	_, err = a.Arquivo.Write([]byte(conteudo))
	if err != nil {
		fmt.Println("Erro ao escrever no arquivo:", err)
		return 0
	}
	//defer a.Mu.Unlock()
	return (numero + v)
}

func (a *Arquivo) Leitura() int {
	scanner := bufio.NewScanner(a.Arquivo)
	var err error

	var conteudo string

	for scanner.Scan() {
		conteudo += scanner.Text()
	}

	if err := scanner.Err(); err != nil {
		return 0
	}

	numero := 0
	if conteudo != "" {
		numero, err = strconv.Atoi(conteudo)
		if err != nil {
			fmt.Println("Erro ao converter a string para inteiro:", err)
			return 0
		}
	}
	return numero
}
