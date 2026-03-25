public class BinarytreeMethod {
     /*static class Tree {
        public int[] binarytreeArray;
        public int currentSize;
        public int[] probes;
        public int[] records;



        public int Parent(int i) { //Return the parent index
            if ((i - 1) / 2 >= 0)
                return (i - 1) / 2;
            else
                return -1;
        }

        public boolean isRightChild(int i)//Control is the right child or not
        {
            if (i % 2 == 0) {
                return true;
            } else
                return false;
        }

        public void createBinary(int[] keys, int tableSize) {
            int key;
            records = new int[tableSize];
            probes = new int[tableSize];

            binarytreeArray = new int[tableSize];
            currentSize = 0;

            for (int i = 0; i < keys.length; i++) {
                key = keys[i];
                int mod = keys[i] % tableSize;
                if (records[mod] == 0) {
                    records[mod] = key;
                } else {
                    currentSize = 0;
                    binarytreeArray[currentSize] = mod;
                    while (records[mod] != 0) {
                        currentSize++;
                        if (!(isRightChild(currentSize))) {
                            int parentIndex = Parent(currentSize);
                            while (!(isRightChild(parentIndex))) //while the parents right child
                            {
                                parentIndex = Parent(parentIndex);
                            }
                            if (isRightChild(parentIndex) && parentIndex != 0) {
                                int grandParentIndex = Parent(parentIndex);
                                //parentın parentının tablodaki değerini bul

                                int index = binarytreeArray[grandParentIndex];//parenın parentındaki adres değer
                                int parent_key = records[index];//parent parent asıl key
                                int stepSize = parent_key / tableSize;//parent adresin üzerine step size eklenmeli
                                //kedni paretnımın üzerine stepsize eklenmeli
                                int currentParentIn = Parent(currentSize);//
                                mod = (binarytreeArray[currentParentIn] + stepSize) % tableSize;//bu adres değeri yeni blunduğum currentsize indexine yazılmalı
                                binarytreeArray[currentSize] = mod;//tablodan kontrol edeceğim yeni adres buraası
                                //artık bulunduğum yer dolu mu boş mu kontrol etmeliyim
                            }
                            if (parentIndex == 0)//parent index 0 olduysa sol-sol-sol-sol diye giden zincir
                            {
                                int currentParentIn = Parent(currentSize);//
                                int step_size = key / tableSize;
                                mod = (binarytreeArray[currentParentIn] + step_size) % tableSize;//
                                binarytreeArray[currentSize] = mod;
                                //artık bulunduğum yer dolu mu boş mu kontrol etmeliyim

                            }
                        } else//sağ çocuk,//parentı taşımaya çalışıyorum
                        {
                            int parentIndex = Parent(currentSize);
                            int index = binarytreeArray[parentIndex];//parentın adres değeri
                            int parentKey = records[index];//parentın adres değerindeki asıl key
                            int step = parentKey / tableSize;
                            //parent adresin üzerine step size eklenmeli
                            mod = (index + step) % tableSize;//mod recırd tablosu üzerindeki indexleri yani adresleri tutar
                            binarytreeArray[currentSize] = mod;//tablodan kontrol edeceğim yeni adres burası
                            //dolu mu boş mu kontrolü lazım
                        }


                    }
                    //whiledan çıktı, boş yer buldu demek oluyor//artık yerleştirme için yeni fonksiyonlar çağırmalıyım
                    if (records[mod] == 0)//boş yer bulundu//mod tablo üzerindeki adres değerini taşır
                    {
                        while (currentSize != 0) {
                            //currentsize sağ çocuk mu sol çocuk mu
                            if (currentSize % 2 == 0)//sağ çocuk
                            {
                                while (currentSize != 0 && currentSize % 2 == 0)//0.indexe gelene kadar veya sol çocuk olana kadar
                                {
                                    //parentdaki değeri bul ve bulunduğun yere taşı, yeni lokasyonu parent ilan et
                                    int parentIndex = Parent(currentSize);//bulunduğum yerin parent indexi
                                    int index = binarytreeArray[parentIndex];//parentın adres değeri
                                    int parent_key = records[index];//parentın adres değerindeki asıl key
                                    //taşıma işlemi yap(tablo üzerinde)
                                    records[mod] = parent_key;//artık parent boş, yeni index, yeni current size parent olmalı
                                    records[index] = 0;
                                    mod = index;
                                    currentSize = parentIndex;
                                }
                                if (currentSize == 0)//artık 0. index, keyi buraya yerleştir
                                {
                                    int index = binarytreeArray[currentSize];// adres değer
                                    records[index] = key;
                                }
                            } else//currentsize sol çocuk//parent sağ çocuk olana kadar yukarı arama yap, ya sağ çocuk olacak ya da 0.index
                            {
                                int parentIndex = Parent(currentSize);//parentın indexi
                                while (!(isRightChild(parentIndex)))//parent sağ çocuk olana kadar yukarı
                                {
                                    parentIndex = Parent(parentIndex);
                                }

                                if (parentIndex != 0)//parentın parentını bulunduğum adrese taşı, yeni lokasyonu parentın parentı yap
                                {
                                    int grandParentIndex = Parent(parentIndex);//parentın parentı, bunu taşımaya çalışıyorum
                                    //parentın parentının tablodaki değerini bul
                                    int index = binarytreeArray[grandParentIndex];//parenın parentındaki adres değer
                                    int parent_key = records[index];//parent parent asıl key

                                    records[mod] = parent_key;//parent parent artık boşaldı, yeni lokasyonum oras
                                    records[index] = 0;
                                    mod = index;
                                    currentSize = grandParentIndex;
                                } else //(parent_index == 0)//parent index 0 olduysa sol-sol-sol-sol diye giden zincir//key bulunduğum locasyona yerleştirmeliyim
                                {
                                    records[mod] = key;
                                    currentSize = 0;
                                }
                            }
                        }
                    }
                }
            }


            probes = findProbe(keys, records);
            System.out.println("Index\tKeys\tProbes");
            for (int i = 0; i < records.length; i++) {
                if (records[i] != 0) {
                    System.out.println(i + "\t" + records[i] + "\t" + probes[i]);
                }

            }
        }

        static float averageProbe(int[] probe, int numberOfKeys) {
            float average;
            float toplam = 0;
            for (int i = 0; i < probe.length; i++) {
                toplam += probe[i];
            }
            System.out.println("Binary Algorithm Total number of probes = " + toplam);
            average = toplam / numberOfKeys;
            System.out.println("Binary Algorithm Average number of probes = " + average);
            return average;
        }

        public static int[] findProbe(int[] binaryTreeArray, int[] recordCount) {
            int[] probes = new int[recordCount.length];
            int loc;
            int step;
            int size = recordCount.length;
            boolean flag = false;
            for (int i = 0; i < binaryTreeArray.length; i++) {
                flag = false;
                loc = binaryTreeArray[i] % size;
                step = binaryTreeArray[i] / size;
                int k;
                for (k = 0; k < recordCount.length; k++) {
                    if (recordCount[loc] == binaryTreeArray[i]) {
                        flag = true;
                        break;
                    } else {
                        loc = (loc + step) % size;
                    }
                }
                if (flag = true)
                    probes[loc] = k + 1;
            }
            return probes;
        }


        public static int findProbeCountForKey(int key, int[] keysArray) {
            int probeCount = 0;
            for (int i = 0; i < keysArray.length; i++) {
                if (keysArray[i] == key)
                    probeCount++;
            }
            return probeCount;
        }


        static float averageProbeJustReturn(int[] probe, int numberOfKeys) {
            float average;
            float toplam = 0;
            for (int i = 0; i < probe.length; i++) {
                toplam += probe[i];
            }
            average = toplam / numberOfKeys;
            return average;
        }

        public static void packingFactor(int[] recordCount, int size) {
            int count = 0;
            for (int i = 0; i < recordCount.length; i++) {
                if (recordCount[i] != 0) {
                    count++;
                }
            }
            float packingFactor = (float) count / size * 100;
            System.out.println("Packing Factor = " + packingFactor + "%");
        }

        static void searchKey(int key, int[] recordCount) {
            findProbeCountForKey(key, recordCount);
            int size = recordCount.length;
            int modInd = key % size;
            int step = key / size;
            int k;
            for (k = 0; k < recordCount.length; k++) {
                if (recordCount[modInd] == key) {
                    System.out.println("In Binary Algorithm: " + "Key " + key + " found at index " + modInd + " with " + "" + findProbeCountForKey(key, recordCount) + " probe");
                    break;
                } else {
                    modInd = (modInd + step) % size;
                }
            }
            if (k == recordCount.length)
                System.out.println("(Binary) key " + key + " not found");
        }


    }
*/

    public static class Heap {

        public int[] heapArray;
        public int maxSize;
        public int currentSize;
        public int[] records;
        public int[] probes;

        public void CreateHeap(int size){
            maxSize = size;
            currentSize = 0;
            heapArray = new int[maxSize];

        }

        public int Parent(int i){
            if ((i - 1) / 2 >= 0)
                return (i - 1) / 2;
            else
                return -1;
        }

        public boolean isRightChild(int i)//sağ çocuksa true döner
        {
            if (i % 2 == 0)
            {
                return true;
            }
            else
                return false;
        }

        public void createBinary(int[] keysArray, int _size)
        {
            int key;
            int size = _size;
            records = new int[size];
            probes = new int[size];

            CreateHeap(999999999);

            for (int i=0; i< keysArray.length; i++){
                key = keysArray[i];
                int loc = key % size;
                if(records[loc] == 0) {
                    records[loc] = key;
                }

                else {
                    currentSize =0;
                    heapArray[currentSize] = loc;
                    while (records[loc] != 0) {
                        currentSize++;
                        if (!isRightChild(currentSize)) {
                            int parentIndex = Parent(currentSize);
                            while (!isRightChild(parentIndex)) {
                                parentIndex = Parent(parentIndex);
                            }
                            if (isRightChild(parentIndex) && parentIndex != 0)
                            {
                                int grandParentIndex = Parent(parentIndex);
                                int index = heapArray[grandParentIndex];
                                int parent_key = records[index];
                                int step_size = parent_key / size;
                                int currentParentIndex = parent_key % size;
                                loc = (heapArray[currentParentIndex] + step_size) % size;
                                heapArray[currentSize] = loc;
                            }
                            if (parentIndex == 0)
                            {
                                int currentParentIndex = Parent(currentSize);
                                int step_size = key / size;
                                loc = (heapArray[currentParentIndex] + step_size) % size;
                                heapArray[currentSize] = loc;
                            }

                        }
                        else{
                            int parentIndex = Parent(currentSize);
                            int index = heapArray[parentIndex];
                            int parent_key = records[index];
                            int step_size = parent_key / size;
                            loc = (index + step_size) % size;
                            heapArray[currentSize] = loc;

                        }
                    }

                    if (records[loc] == 0)
                    {
                        while(currentSize != 0)
                        {
                            if (currentSize % 2 == 0)
                            {
                                while (currentSize != 0 && currentSize % 2 == 0)
                                {
                                    int parentIndex = Parent(currentSize);
                                    int index = heapArray[parentIndex];
                                    int parent_key = records[index];
                                    records[loc] = parent_key;
                                    records[index] = 0;
                                    loc = index;
                                    currentSize = parentIndex;

                                }
                                if(currentSize == 0)
                                {
                                    int index = heapArray[currentSize];
                                    records[index] = key;
                                }
                            }
                            else {
                                int parentIndex = Parent(currentSize);
                                while(!isRightChild(parentIndex))
                                {
                                    parentIndex = Parent(parentIndex);
                                }
                                if (parentIndex != 0) {
                                    int grandParentIndex = Parent(parentIndex);
                                    int index = heapArray[grandParentIndex];
                                    int parent_key = records[index];
                                    records[loc] = parent_key;
                                    records[index] = 0;
                                    loc = index;
                                    currentSize = grandParentIndex;
                                }
                                else {
                                    records[loc] = key;
                                    currentSize = 0;
                                }
                            }
                        }
                    }
                }
            }
            probes = findProbe(keysArray, records);

            System.out.println("binary");
            System.out.println("i" + "\t" + "key" + "\t" + "probe");
            for (int i = 0; i < records.length; i++) {
                if (records[i] != 0) {
                    System.out.println(i + "\t" + records[i] + "\t" + probes[i]);
                }
                else {
                    System.out.println(i + "\t" + "null" + "\t" + probes[i]);
                }

            }



        }

        public static int[] findProbe(int[] dizi, int[] records) {
            int[] probes = new int[records.length];
            int loc;
            int step_size;
            int size = records.length;
            boolean flag = false;

            for (int i= 0; i< dizi.length; i++) {
                flag = false;
                loc = dizi[i] % size;
                step_size = dizi[i] / size;
                int k;
                for ( k=0; k < records.length; k++) {
                    if (records[loc] == dizi[i]) {

                        flag = true;
                        break;
                    } else {
                        loc = (loc + step_size) % size;
                    }
                }
                if (flag == true) {
                    probes[loc] = k+1;

                }

            }
            return probes;
        }
        public void packingFactor(int size)
        {
            double packingFactor = (double) Main.keys.length / size * 100;
            System.out.println("Packing Factor: " +  packingFactor + "%");
        }

    }


}





