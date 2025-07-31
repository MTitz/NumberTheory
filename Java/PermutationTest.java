/* Program PermutationTest for testing class Permutation */

public class PermutationTest
{
    public static void permutationTest(int n)
    {
        System.out.print("Testing permutations of 1.." + n);
        Permutation permutation = new Permutation(n);
        System.out.println(", initially: " + permutation);
        int count = 0;
        while (permutation.hasNext()) {
            System.out.print("  ");
            int[] p = permutation.getPermutation();
            for (int k = 0; k < p.length; ++k) {
                System.out.print(p[k]);
            }
            ++count;
            if (count % 6 == 0) {
                System.out.println();
            }
            permutation.next();
        }
        if (count % 6 != 0) {
            System.out.println();
        }
        System.out.println(" together " + count + " permutations.");
        System.out.println();
    }

    public static void permutationTest(int n, int initialValue)
    {
        System.out.println();
        Permutation permutation = new Permutation(n, initialValue);
        while (permutation.hasNext()) {
             System.out.println(" " + permutation);
             permutation.next();
        }
    }

    public static void main(String[] args)
    {
        for (int i = 1; i <= 6; ++i) {
            permutationTest(i);
        }

        System.out.println("Testing permutations with other initial values");
        permutationTest(4, 5);
        permutationTest(2, 0);
        permutationTest(3, 0);
        permutationTest(3,-1);
    }
}
