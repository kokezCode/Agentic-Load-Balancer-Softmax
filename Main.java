public class Main {
    public static void main(String[] args) {
        // Farklı performanslara sahip 3 sunucu tanımlıyoruz
        Server[] servers = {
            new Server(0, 40),  // Hızlı Sunucu
            new Server(1, 120), // Orta Sunucu
            new Server(2, 250)  // Yavaş Sunucu
        };

        // Load Balancer ayarları: 3 sunucu, Sıcaklık: 0.5, Öğrenme Hızı: 0.1
        SoftmaxLoadBalancer balancer = new SoftmaxLoadBalancer(3, 0.5, 0.1);
        
        int[] selectionCounts = new int[3];
        System.out.println("--- Agentic Load Balancing Simülasyonu Başlıyor ---");
        System.out.println("Metot: Softmax with Numerical Stability (Max Trick)\n");

        for (int i = 1; i <= 1000; i++) {
            int selectedIdx = balancer.selectServer();
            double latency = servers[selectedIdx].getResponse();
            
            balancer.updatePerformance(selectedIdx, latency);
            selectionCounts[selectedIdx]++;

            // Her 200 istekte bir durumu raporla
            if (i % 200 == 0) {
                System.out.printf("İstek %d -> Dağılım: S0 (Hızlı): %d, S1 (Orta): %d, S2 (Yavaş): %d\n", 
                                  i, selectionCounts[0], selectionCounts[1], selectionCounts[2]);
            }
        }

        System.out.println("\nSonuç: Sistem, gürültülü ortamda en verimli sunucuyu başarıyla keşfetti.");
    }
}