/* Program Micropuzzle19.java to solve micropuzzle 19 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   Who's who

   0  Baker,  1  Cooper,  2  Parson,  3  Smith
   permutation is mapping name -> vocation
*/

public class Micropuzzle19
{
    private static final int BAKER = 0;
    private static final int COOPER = 1;
    private static final int PARSON = 2;
    private static final int SMITH = 3;

    public static String vocationString(int n) {
        switch(n) {
            case 0: return "baker";
            case 1: return "cooper";
            case 2: return "parson";
            case 3: return "smith";
            default: return "error";
        }
    }

    public static void main(String[] args)
    {
        Permutation permutation = new Permutation(4, 0);
        while (permutation.hasNext()) {
            int[] vocation = permutation.getPermutation();
            boolean isSolution = true;
            for (int k = 0; k < 4; ++k) {
                if (k == vocation[k]) {
                    isSolution = false;
                    break;
                }
                if (vocation[k] == COOPER && k == vocation[SMITH]) {
                    isSolution = false;
                    break;
                }
                if (vocation[k] == BAKER && (k == PARSON || k == vocation[BAKER])) {
                    isSolution = false;
                    break;
                }
            }
            if (isSolution) {
                System.out.println("Mapping " + permutation + "  Mr. Baker's vocation: " + vocationString(vocation[BAKER]));
            }
            permutation.next();
        }
    }
}
