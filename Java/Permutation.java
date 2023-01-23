public class Permutation
{
    public Permutation(int n, int initValue)
    {
        if (n < 0) {
            throw new IllegalArgumentException();
        } else {
            perm = new int[n];
            for (int i = 0; i < n; ++i) {
                perm[i] = i + initValue;
            }
        }
    }

    public Permutation(int n)
    {
        this(n, 1);
    }

    public final int[] getPermutation()
    {
        return perm;
    }

    public final boolean hasNext()
    {
        return perm != null;
    }

    public final void next()
    {
        int n = perm.length;
        int i = n-2;
        while (i >= 0 && perm[i+1] < perm[i]) {
            --i;
        }
        if (i < 0) {
            perm = null;
            return;
        }
        int j = n-1;
        while (perm[j] < perm[i]) {
            --j;
        }
        int t = perm[j];
        perm[j] = perm[i];
        perm[i] = t;

        // Revert in place array perm[i+1],..., perm[n-1],
        // together (n-1)-(i+1)+1 = n-i-1 elements
        int m = (n-i-1) / 2;
        for (int h = 0; h < m; ++h) {
            int tmp = perm[i+1 + h];
            perm[i+1 + h] = perm[n-1 - h];
            perm[n-1 - h] = tmp;
        }
    }

    @Override public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof Permutation))
            return false;
        Permutation p = (Permutation)o;
        return java.util.Arrays.equals(this.perm, p.perm);
    }

    @Override public String toString()
    {
        if (perm == null)
            return "<null>";
        StringBuffer buffer = new StringBuffer();
        buffer.append('(');
        for (int i = 0; i < perm.length; ++i) {
            if (i > 0)
                buffer.append(',');
            buffer.append(perm[i]);
        }
        buffer.append(')');
        return buffer.toString();
    }

    private int[] perm;
}
