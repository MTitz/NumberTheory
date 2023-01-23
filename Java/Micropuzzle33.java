/* Program Micropuzzle33.java to solve micropuzzle 33 from
   J. J. Clessa "Math and Logic Puzzles for PC Enthusiasts"
   The ladies of the committee
*/

public class Micropuzzle33
{
    enum Lady {
        Audrey, Betty, Cynthia, Doris, Elaine, Freda;
    }

    public static boolean checkConditions(Lady captain, Lady vicecaptain, Lady treasurer)
    {
        // condition 1
        if (captain == Lady.Elaine && (vicecaptain == Lady.Audrey || treasurer == Lady.Audrey)) return false;
        if (treasurer == Lady.Freda && (captain == Lady.Audrey || vicecaptain == Lady.Audrey)) return false;

        // condition 2
        if (treasurer == Lady.Betty && (captain == Lady.Cynthia || vicecaptain == Lady.Cynthia)) return false;

        // condition 3
        if (captain == Lady.Audrey && vicecaptain == Lady.Betty && treasurer == Lady.Elaine) return false;
        if (captain == Lady.Audrey && vicecaptain == Lady.Elaine && treasurer == Lady.Betty) return false;
        if (captain == Lady.Betty && vicecaptain == Lady.Audrey && treasurer == Lady.Elaine) return false;
        if (captain == Lady.Betty && vicecaptain == Lady.Elaine && treasurer == Lady.Audrey) return false;
        if (captain == Lady.Elaine && vicecaptain == Lady.Audrey && treasurer == Lady.Betty) return false;
        if (captain == Lady.Elaine && vicecaptain == Lady.Betty && treasurer == Lady.Audrey) return false;

        // condition 4
        if (captain == Lady.Freda && (vicecaptain == Lady.Elaine || treasurer == Lady.Elaine)) return false;
        if (vicecaptain == Lady.Freda && (captain == Lady.Elaine || treasurer == Lady.Elaine)) return false;
        if (treasurer == Lady.Freda && (captain == Lady.Elaine || vicecaptain == Lady.Elaine)) return false;

        // condition 5
        if (vicecaptain == Lady.Betty) return false;

        // condition 6
        if (captain == Lady.Freda && (vicecaptain == Lady.Audrey || treasurer == Lady.Audrey)) return false;
        if (vicecaptain == Lady.Freda && treasurer == Lady.Audrey) return false;

        // condition 7
        if (vicecaptain == Lady.Cynthia && (captain == Lady.Audrey || treasurer == Lady.Audrey)) return false;
        if (vicecaptain == Lady.Cynthia && (captain == Lady.Betty || treasurer == Lady.Betty)) return false;
        if (treasurer == Lady.Cynthia && (captain == Lady.Audrey || vicecaptain == Lady.Audrey)) return false;
        if (treasurer == Lady.Cynthia && (captain == Lady.Betty || vicecaptain == Lady.Betty)) return false;

        // condition 8
        if (captain == Lady.Doris) return false;
        if (vicecaptain == Lady.Doris && captain != Lady.Betty) return false;
        if (treasurer == Lady.Doris && captain != Lady.Betty) return false;

        // condition 9
        if (captain == Lady.Betty && vicecaptain == Lady.Doris && treasurer != Lady.Elaine) return false;
        if (captain == Lady.Betty && vicecaptain != Lady.Elaine && treasurer == Lady.Doris) return false;
        if (captain == Lady.Doris && vicecaptain == Lady.Betty && treasurer != Lady.Elaine) return false;
        if (captain == Lady.Doris && vicecaptain != Lady.Elaine && treasurer == Lady.Betty) return false;
        if (captain != Lady.Elaine && vicecaptain == Lady.Betty && treasurer == Lady.Doris) return false;
        if (captain != Lady.Elaine && vicecaptain == Lady.Doris && treasurer == Lady.Betty) return false;

        // condition 10
        if (vicecaptain == Lady.Elaine && captain != Lady.Audrey) return false;
        if (treasurer == Lady.Elaine && captain != Lady.Audrey) return false;

        return true;
    }

    public static void main(String[] args)
    {
        for (Lady captain : Lady.values()) {
            for (Lady vicecaptain : Lady.values()) {
                if (captain == vicecaptain)
                    continue;
                for (Lady treasurer : Lady.values()) {
                    if (captain == treasurer || vicecaptain == treasurer)
                        continue;
                    if (checkConditions(captain, vicecaptain, treasurer))
                        System.out.printf("%-7s %-7s %-7s%n", captain, vicecaptain, treasurer);
                }
            }
        }
    }
}
