
# Agentic Load Balancer: Softmax Optimization & Numerical Stability

Bu proje, dağıtık sistem mimarilerinde sunucu yük dengeleme (load balancing) problemini, Takviyeli Öğrenme (Reinforcement Learning) prensiplerinden olan **Softmax Algoritması** ile çözen akıllı bir simülasyondur.

## 🎯 Projenin Amacı
Geleneksel Round-Robin veya Random seçim algoritmaları, sunucuların o anki performans durumunu (gecikme sürelerini) dikkate almaz. Bu proje; sunucuların yanıt sürelerini anlık olarak öğrenen, analiz eden ve trafiği en optimize sunucuya otonom bir şekilde yönlendiren bir **Agentic Load Balancer** geliştirmeyi hedefler.

## 🧠 Teknik Mimari ve Algoritma
Proje üç ana bileşenden oluşmaktadır:
1. **Server.java:** Gerçek dünya koşullarını (Gaussian Noise) simüle eden, değişken gecikme sürelerine sahip sunucu modeli.
2. **SoftmaxLoadBalancer.java:** Sistemin karar mekanizması. Sunucu skorlarını (Q-Values) tutar ve olasılıksal seçim yapar.
3. **Main.java:** Simülasyon döngüsünü yöneten ve sonuçları raporlayan ana sınıf.

### 🛡️ Nümerik Stabilite (The Max Trick)
Projenin en kritik mühendislik dokunuşu Softmax hesaplamasındaki **Numerical Stability** çözümüdür. 
- **Sorun:** Üstel fonksiyonlar ($e^x$), yüksek performans skorlarında hızla büyüyerek `Double.POSITIVE_INFINITY` hatasına (Overflow) yol açar.
- **Çözüm:** Tüm skorlardan maksimum skorun çıkarılması ($Q_i - Q_{max}$) yöntemi uygulanmıştır. Bu matematiksel işlem, olasılık sonuçlarını değiştirmeden hesaplamayı güvenli bir aralıkta tutar ve sistemin çökmesini engeller.

## 📊 Simülasyon Çıktıları
Simülasyon 1000 istek üzerinden koşturulmuş ve şu sonuçlar elde edilmiştir:
- **Öğrenme Hızı:** Ajan ilk 200 istek içerisinde en hızlı sunucuyu (%99 doğrulukla) tespit etmiştir.
- **Kararlılık:** Gürültülü (noisy) ortamlarda bile sistem hatalı kararlardan hızla dönerek optimal sunucuda stabilize olmuştur.

## 🚀 Kurulum ve Çalıştırma
Projeyi yerel makinenizde çalıştırmak için:
```bash
javac *.java
java Main
