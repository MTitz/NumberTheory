public class GenPartitions
{
    private static int[] a;

    private static void recursivePartition(int m, int B, int N)
    {
        if (m == 0) {
            for (int i = 0; i < N; ++i) {
                System.out.print(a[i]);
                if (i < N-1)
                    System.out.print(' ');
            }
            System.out.println();
        } else {
            for (int i = 1; i <= Math.min(B, m); ++i) {
                a[N] = i;
                recursivePartition(m-i, i, N+1);
            }
        }
    }

    public static void generatePartitions(int m)
    {
        if (m < 0) {
            System.err.println("Cannot generate partitions of a negative number.");
            return;
        }
        a = null;
        a = new int[m];
        recursivePartition(m, m, 0);
    }

    public static void main(String[] args)
    {
        if (args.length == 0) {
            System.err.println("Usage: GenPartitions n");
            System.err.println("  to generate a list of the partitions of n");
            return;
        }
        for (int i = 0; i < args.length; ++i)
        {
            if ( i > 0)
                System.out.println();
            int n = Integer.parseInt(args[i]);
            generatePartitions(n);
        }
    }
}
