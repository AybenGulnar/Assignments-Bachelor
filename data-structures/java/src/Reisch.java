import java.util.Random;

public class Reisch {
    private int[] keys = Main.keys;
    static int[] reischArray = new int[Main.tableSize];

    static String[] links = new String[Main.tableSize];
    Random rnd = new Random();


    public void placeIntoEischArray(int[] keysArray, int size) {
        int homeAddress;
        int randomAddress;

        for (int i = 0; i < keys.length; i++) {
            homeAddress = keysArray[i] % size;

            if (reischArray[homeAddress] == 0) {
                reischArray[homeAddress] = keys[i];
            } else if (reischArray[homeAddress] != 0 && links[homeAddress] == null) {

                do {
                    randomAddress = rnd.nextInt(size);
                } while (reischArray[randomAddress] != 0);

                reischArray[randomAddress] = keys[i];
                links[homeAddress] = randomAddress + "";
            } else if (reischArray[homeAddress] != 0 && links[homeAddress] != null) {

                do {
                    randomAddress = rnd.nextInt(size);
                } while (reischArray[randomAddress] != 0);

                reischArray[randomAddress] = keys[i];
                int tempAddress = Integer.parseInt(links[homeAddress]);
                links[homeAddress] = randomAddress + "";
                links[randomAddress] = tempAddress + "";
            }
        }
    }

    public void showLastStatus() {
        System.out.println("Index\tKeys\tLinks");
        for (int i = 0; i < reischArray.length; i++)
            System.out.println(i + "\t\t" + reischArray[i] + "\t\t" + links[i]);
    }

    public void searchKeyIndex(int key,int size) {
        int probe = 1;
        if (reischArray[key % size] == key) {
            System.out.println("In Reisch Algorithm: "+"Key " + key + " found in index " + key % size + " with " + probe + " probe");
        } else if (links[key % size] != null) {
            probe++;
            int temp = Integer.parseInt(links[key % size]);
            if (reischArray[temp] == key) {
                System.out.println("(Reisch) Key " + key + " found in index " + temp + " with " + probe + " probe");
            } else {
                System.out.println("(Reisch) Key " + key + " not found");
            }
        } else {
            System.out.println("(Reisch) Key " + key + " not found");
        }
    }

    public void totalProbeCount(int size) {
        int probe = 0;
        for (int key : keys) {
            if (reischArray[key % size] == key) {
                probe++;
            } else if (links[key % size] != null) {
                probe++;
                int temp = Integer.parseInt(links[key % size]);
                if (reischArray[temp] == key) {
                    probe++;
                }
            }
        }
        System.out.println("Total probe count is " + probe);
    }

    public void packingFactor(int size) {
        int count = 0;
        for (int j : reischArray) {
            if (j != 0) {
                count++;
            }
        }
        double pf = ((double) count / (double) size) * 100;
        System.out.println("Packing Factor: " + pf + " %");
    }

    public float averageProbeCount(int size){
        int probe = 0;
        for (int key : keys) {
            if (reischArray[key % size] == key) {
                probe++;
            } else if (links[key % size] != null) {
                probe++;
                int temp = Integer.parseInt(links[key % size]);
                if (reischArray[temp] == key) {
                    probe++;
                }
            }
        }
        double apc = (double) probe / (double) keys.length;
        System.out.println("Reisch Algorithm Average Probe Count: " + apc);
        return (float) apc;
    }

    public float averageProbe(int size){
        int probe = 0;
        for (int key : keys) {
            if (reischArray[key % size] == key) {
                probe++;
            } else if (links[key % size] != null) {
                probe++;
                int temp = Integer.parseInt(links[key % size]);
                if (reischArray[temp] == key) {
                    probe++;
                }
            }
        }
        double apc = (double) probe / (double) keys.length;
        return (float) apc;
    }


}
