import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    static Integer tableSize =  100;
    static int[] keys = randomDifferentValue();

    public static int[] randomDifferentValue(){
        int[] keys = new int[keycount()];
        Random rnd = new Random();
        for (int i = 0; i < keys.length; i++) {
            keys[i] = rnd.nextInt(101);
            for (int j = 0; j < i; j++) {
                if (keys[i] == keys[j] ) {
                    i--;
                    break;
                }
            }
        }
        return keys;
    }


    private static int keycount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of keys "+ "Table Size"+" ("+ tableSize+")=");
        int keyCount = scanner.nextInt();
        if(keyCount > tableSize) {
            System.out.println("Key count can not be greater than table size. Table size is " + tableSize);
            exit(0);
        }
        return keyCount;
    }


    public static void main(String[] args) {

        System.out.println("Reisch Method");
        Reisch reisch = new Reisch();
        reisch.placeIntoEischArray(keys, tableSize);
        reisch.showLastStatus();
        reisch.packingFactor(tableSize);
        reisch.totalProbeCount(tableSize);
        reisch.averageProbeCount(tableSize);

        printKeys();

        System.out.println("🐾🐾🐾🐾🐾🐾🐾🐾🐾🐾🐾🐾");
        System.out.println("Binary Method");
        BinarytreeMethod.Heap heap = new BinarytreeMethod.Heap();
        heap.createBinary(keys, tableSize);
        heap.packingFactor(tableSize);






       /* System.out.println("******");
        if (Binarytree.averageProbeJustReturn(tree.probes, keys.length) < reisch.averageProbe(tableSize)) {
            System.out.println("According to Average Probe Count Binary tree is better than Reisch");
        }
        else if (Binarytree.averageProbeJustReturn(tree.probes, keys.length) > reisch.averageProbe(tableSize)) {
            System.out.println("According to Average Probe Count Reisch is better than Binary tree");
        }
        else {
            System.out.println("According to Average Probe Count Binary tree and Reisch are equal");
        }*/

        System.out.println("Do you want to search a key? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        if (answer.equals("y")) {
            System.out.println("Enter the key");
            int key = scanner.nextInt();
            reisch.searchKeyIndex(key, tableSize);
        } else {
            System.out.println("Thank you for using my program.");
        }

    }

    public static void printKeys() {
        System.out.println("Keys:");
        for (int i = 0; i < keys.length; i++) {
            System.out.print(keys[i] + "," + " " );
        }
        System.out.println();
    }

}
