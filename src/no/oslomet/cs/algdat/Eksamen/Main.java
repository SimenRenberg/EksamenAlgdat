package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

import static java.util.Comparator.naturalOrder;

public class Main {
    public static void main(String[] args) {
        Integer[] a = {1,2,3,4,5,6,7,8,9,10};
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a ) tre.leggInn(verdi);
        System.out.println(tre.antall());
    }
}
