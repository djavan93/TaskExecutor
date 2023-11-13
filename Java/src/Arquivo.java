import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Arquivo {
    private int valor;
    private Lock lock = new ReentrantLock();

    public Arquivo(){
        this.valor = 0;
    }

    public int escrita(int v){
        lock.lock();
        try {
            this.valor += v;
            return this.valor;
        } finally{
            lock.unlock();
        }

    }

    public int  leitura(){
        return this.valor;
    }

}