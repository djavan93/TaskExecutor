import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Arquivo {
    private File caminho;
    private Lock lock;

    public Arquivo(File caminho){
        this.caminho = caminho;
        lock = new ReentrantLock();
        try (Writer writer = new FileWriter(caminho, false)) {
            writer.write((""+0).trim());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int escrita(int v){
        int valorNovo = leitura();
        String texto = (""+(valorNovo + v)).trim();
        Writer writer;
        try {
            synchronized (this){      
                writer = new FileWriter(caminho, false);
                writer.write(texto);
            }
            writer.close();
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
            while(numero[0] == '\u0000'){
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
}
