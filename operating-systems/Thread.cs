using System;
using System.Diagnostics;
using System.Threading;
using System.Collections.Generic;

namespace sadas
{
    //threadin parametre göndermek için gönderdiğimiz struct. sadece obje alabildiği için struct oluşturduk.
    struct Param
    {
        public int index;
        public long start;
        public long finish;
    }
    class Program
    {
        //decimel tanımladık çünkü 10^10 un toplamının long ve int ve double için max değeri geçmesi
        //her threadten toplamlarını tuttuğumuz liste
        static List<decimal> toplamlar;
        static long sayi = 1000000000; // 10^10
        static long time = 0;
       

        static void Main(string[] args)
        {
            //gauss metotu çalıştırılıyor 
            Thread gauss = new Thread(Gauss);
            gauss.Start();
            gauss.Join(); // bitmesini bekliyor

            Param pr = new Param();

            //kaç tane thread açılacağını belirliyor.
            for (int a = 1; a <= 32; a++)
            {
                //thread sayısını tuttuğumuz liste 
                List<Thread> threads = new List<Thread>();
                toplamlar = new List<decimal>();

                //threadlere gönderilen param
              
            
                long temp = sayi / a;
                pr.start = 1; 
                pr.finish = temp;

                //threadleri açan for a=1 ken 1 thread açılır
                for (int j = 0; j < a; j++)
                {

                    toplamlar.Add(0); //her threadin toplamı için eleman eklenir 
                    pr.index = j; //kaçıncı thread olduğu 
                    Param localPr = pr; //çakışmasını önlüyor (2. ve 1. alınan değerlerin farklı olmasını sağlıyor) thread paramlarının çakışmamasını sağlıyor
                    threads.Add(new Thread(new ParameterizedThreadStart(Toplam))); //thread listesine yeni thread eklenir 
                    threads[j].Start(localPr); //her thread kendi local prsi ile başlatılır
                    pr.start = pr.finish + 1; //bir sonraki thread için start değeri ayarlanır
                    pr.finish = pr.start + temp; //finish değeri
                    pr.finish = pr.finish > sayi ? sayi : pr.finish; //son değer kontrolü
                }

                foreach (var item in threads) //her threadin bitmesi bekleniyor
                {
                    item.Join();
                }
                decimal toplam = 0;  //toplamları yazdırmak için değişken 

                foreach (var item in toplamlar)
                {
                    toplam = toplam + item; //her threadten bulunan toplam sonuç eklenir yeniden toplanır
                }

                Console.WriteLine(a + " iplik icin Sonuc=" + toplam + " " + time);
            }

        }

        static void Toplam(object paramaters) 
        {
            Param param = (Param)paramaters;  //parameters param structkına convert edilir
            decimal toplam = 0; //threadin bulacağı toplam değeri 
            var sw = Stopwatch.StartNew(); //zaman tutucu buna bak
            for (long i = param.start; i <= param.finish; i++) 
            {
                toplam = toplam + i;     //paramın başlangıç ve bitiş değeri ile threadin bulacağı toplam hesaplanır
            }
            sw.Stop();
            lock (toplamlar) toplamlar[param.index] = toplam; //her indeks için threadler belirlenir
            time = sw.ElapsedMilliseconds; //threadler aynı anda çalıştığı için kısmen hepsi aynı anda biter (
          
        }

        //gauss ile hesaplama
        static void Gauss()
        {
            var sw = Stopwatch.StartNew();
            decimal cevap = sayi / 2;
            cevap = cevap * (sayi + 1);
            Console.Write("Gauss Metodu Sonucu=" + cevap + " ");
            sw.Stop();
            Console.Write(sw.ElapsedMilliseconds + "\n");
        }


    }
}