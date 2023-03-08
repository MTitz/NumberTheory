/* Program PermutationTest for testing class Permutation */

public class PermutationTest
{
    public static void permTest(int n)
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

    public static void main(String[] args)
    {
        for (int i = 1; i <= 6; ++i) {
            permTest(i);
        }

        System.out.println("Testing permutations with other initial values");
        System.out.println();
        Permutation p5_8 = new Permutation(4, 5);
        while (p5_8.hasNext()) {
             System.out.println(" " + p5_8);
             p5_8.next();
        }

        System.out.println();
        Permutation p0_1 = new Permutation(2, 0);
        while (p0_1.hasNext()) {
             System.out.println(" " + p0_1);
             p0_1.next();
        }
    }
}
