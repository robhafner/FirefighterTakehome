package main.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PermutationGeneratorTest {

    @Test
    public void zero() {
        String[] a = new String[]{};
        List<String[]> permutations = PermutationGenerator.<String>generate(a);
        Assert.assertEquals(0, permutations.size());
    }

    @Test
    public void one() {
        String[] a = new String[]{"A"};
        List<String[]> permutations = PermutationGenerator.<String>generate(a);
        Assert.assertEquals(1, permutations.size());
        Assert.assertEquals(1, permutations.get(0).length);
        Assert.assertEquals("A", permutations.get(0)[0]);
    }

    @Test
    public void two() {
        String[] a = new String[]{"A", "B"};
        List<String[]> permutations = PermutationGenerator.<String>generate(a);
        Assert.assertEquals(2, permutations.size());
        Assert.assertEquals(2, permutations.get(0).length);
        Assert.assertEquals("A", permutations.get(0)[0]);
        Assert.assertEquals("B", permutations.get(0)[1]);
        Assert.assertEquals("B", permutations.get(1)[0]);
        Assert.assertEquals("A", permutations.get(1)[1]);
    }

    @Test
    public void three() {
        String[] a = new String[]{"A", "B", "C"};
        List<String[]> permutations = PermutationGenerator.<String>generate(a);
        Assert.assertEquals(6, permutations.size());

        Assert.assertEquals(3, permutations.get(0).length);
        Assert.assertEquals("A", permutations.get(0)[0]);
        Assert.assertEquals("B", permutations.get(0)[1]);
        Assert.assertEquals("C", permutations.get(0)[2]);

        Assert.assertEquals(3, permutations.get(1).length);
        Assert.assertEquals("A", permutations.get(1)[0]);
        Assert.assertEquals("C", permutations.get(1)[1]);
        Assert.assertEquals("B", permutations.get(1)[2]);

        Assert.assertEquals(3, permutations.get(2).length);
        Assert.assertEquals("B", permutations.get(2)[0]);
        Assert.assertEquals("A", permutations.get(2)[1]);
        Assert.assertEquals("C", permutations.get(2)[2]);

        Assert.assertEquals(3, permutations.get(3).length);
        Assert.assertEquals("B", permutations.get(3)[0]);
        Assert.assertEquals("C", permutations.get(3)[1]);
        Assert.assertEquals("A", permutations.get(3)[2]);

        Assert.assertEquals(3, permutations.get(3).length);
        Assert.assertEquals("C", permutations.get(4)[0]);
        Assert.assertEquals("B", permutations.get(4)[1]);
        Assert.assertEquals("A", permutations.get(4)[2]);

        Assert.assertEquals(3, permutations.get(3).length);
        Assert.assertEquals("C", permutations.get(5)[0]);
        Assert.assertEquals("A", permutations.get(5)[1]);
        Assert.assertEquals("B", permutations.get(5)[2]);
    }
}
