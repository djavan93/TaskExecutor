import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Arquivo {
    private File caminho;
    private Path caminhoEscrita;
    private Lock lock;
    private BufferedWriter bufferedWriter;

    public Arquivo(File caminho){
        this.caminho = caminho;
        lock = new ReentrantLock();
        try (Writer writer = new FileWriter(caminho, false)) {
            writer.write((""+0).trim());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        caminhoEscrita = Paths.get(""+caminho);
    }

    public int escrita(int v){
        int valorNovo = leitura();
        String texto = (""+(valorNovo + v)).trim();
        try {
            synchronized (this){      
                bufferedWriter = Files.newBufferedWriter(caminhoEscrita);
                bufferedWriter.write(texto);
                bufferedWriter.close();
            }
            notificar();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return valorNovo; 
    }

    public int leitura(){
        char[] numero = new char[10];
        String numeroFinal;
        int valorNovo = 0;    
        try (Reader reader = new FileReader(caminho)) {
            reader.read(numero);
            while(numero[0] == '\u0000'){
                esperar();
                reader.read(numero);
            }
            reader.close();
            numeroFinal = new String(numero).trim();
            valorNovo = Integer.parseInt(numeroFinal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valorNovo;
    }

    public synchronized void esperar() {
            try {
                wait(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }

    public synchronized void notificar() {
        notify();
    }
}
