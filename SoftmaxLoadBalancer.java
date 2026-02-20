import java.util.Arrays;

public class SoftmaxLoadBalancer {
    private double[] qValues; // Sunucuların tahmini performans skorları
    private double tau;       // Temperature (Sıcaklık) parametresi
    private double alpha;     // Öğrenme hızı (Learning Rate)

    public SoftmaxLoadBalancer(int serverCount, double tau, double alpha) {
        this.qValues = new double[serverCount];
        this.tau = tau;
        this.alpha = alpha;
        Arrays.fill(qValues, 0.0); // Başlangıçta tüm sunucular eşit bilinir
    }

    public int selectServer() {
        // --- NÜMERİK STABİLİTE (MAX TRICK) ---
        // exp(x) fonksiyonunda overflow (sonsuzluk hatası) oluşmaması için 
        // tüm skorlardan en büyük olanı çıkarıyoruz.
        double maxQ = Arrays.stream(qValues).max().getAsDouble();
        
        double[] probabilities = new double[qValues.length];
        double sumExp = 0;

        for (int i = 0; i < qValues.length; i++) {
            // Nümerik olarak stabilize edilmiş Softmax hesaplaması
            probabilities[i] = Math.exp((qValues[i] - maxQ) / tau);
            sumExp += probabilities[i];
        }

        // Rastgele seçim (Olasılık dağılımına göre)
        double r = Math.random();
        double cumulative = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulative += (probabilities[i] / sumExp);
            if (r <= cumulative) return i;
        }
        return qValues.length - 1;
    }

    public void updatePerformance(int serverId, double latency) {
        // Gecikme ne kadar düşükse ödül o kadar yüksek (1000/ms)
        double reward = 1000.0 / latency;
        // Üstel Hareketli Ortalama (EMA) ile skor güncelleme
        qValues[serverId] += alpha * (reward - qValues[serverId]);
    }
}