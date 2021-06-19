package main.util;

import java.util.LinkedList;
import java.util.List;

public class PermutationGenerator<T> {
    public static <T> List<T[]> generate(T[] a) {
        List<T[]> permutations = new LinkedList<>();
        generate(a, 0, permutations);
        return permutations;
    }

    private static <T> void generate(T[] a, int currentIndex, List<T[]> permutations) {
        if (currentIndex == a.length - 1) {
            permutations.add(a.clone());
            //System.out.println(String.valueOf(a));
        }

        for (int i = currentIndex; i < a.length; i++) {
            swap(a, currentIndex, i);
            generate(a, currentIndex + 1, permutations);
            swap(a, currentIndex, i);
        }
    }

    private static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
