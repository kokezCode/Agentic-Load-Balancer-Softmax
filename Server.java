import java.util.Random;

public class Server {
    private int id;
    private double baseLatency; // Sunucunun temel gecikme süresi (ms)
    private Random random = new Random();

    public Server(int id, double baseLatency) {
        this.id = id;
        this.baseLatency = baseLatency;
    }

    public double getResponse() {
        // Non-stationary (sabit olmayan) ortam simülasyonu: 
        // Sunucu hızı anlık gürültülerle (noise) sürekli değişir.
        double noise = random.nextGaussian() * 15; 
        return Math.max(5, baseLatency + noise); 
    }

    public int getId() { return id; }
}