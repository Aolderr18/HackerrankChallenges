import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    // Complete the printShortestPath function below.
    static void printShortestPath(int n, int i_start, int j_start, int i_end, int j_end) {
        // Print the distance along with the sequence of moves.
        final class Velocity {
            
            private final int dX;
            private final int dY;
            private String moveLabel;
            
            public Velocity(int dX, int dY, String moveLabel) {
                this.dX = dX;
                this.dY = dY;
                this.moveLabel = moveLabel;
            }
            
            public int getDX() {
                return dX;
            }
            
            public int getDY() {
                return dY;
            }
            
            public String getMoveLabel() {
                return moveLabel;
            }
        }
        Velocity[] velocities_array = { new Velocity(-2, -1, "UL"), new Velocity(-2, 1, "UR"), new Velocity(0, 2, "R"), new Velocity(2, 1, "LR"), new Velocity(2, -1, "LL"), new Velocity(0, -2, "L") };
        List<Velocity> velocities_list = Arrays.asList(velocities_array);
        final int n_sq = (int) Math.pow(n, 2);
        boolean[][][] canReachIn = new boolean[n][n][n_sq];
        int x, y, m;
        for (x = 0; x < n; x++)
            for (y = 0; y < n; y++)
                for (m = 0; m < n_sq; m++)
                    canReachIn[x][y][m] = false;
        canReachIn[i_start][j_start][0] = true;
        boolean reached;
        do {
            reached = false;
            for (m = 1; m < n_sq; m++)
                for (x = 0; x < n; x++)
                    for (y = 0; y < n; y++)
                        if (canReachIn[x][y][m - 1]) {
                            Iterator<Velocity> velocities_itr = velocities_list.iterator();
                            while (velocities_itr.hasNext()) {
                                Velocity next = velocities_itr.next();
                                int dX = next.getDX();
                                int dY = next.getDY();
                                try {
                                    if (!canReachIn[x + dX][y + dY][m]) {
                                        if (x + dX == i_end && y + dY == j_end) {
                                            canReachIn[x + dX][y + dY][m] = true;
                                            System.out.println(m);
                                            int i = i_start;
                                            int j = j_start;
                                            m = 0;
                                            List<String> path = new ArrayList<String>();
                                            while (i != i_end || j != j_end) {
                                                boolean changedIJ = false;
                                                velocities_itr = velocities_list.iterator();
                                                while (velocities_itr.hasNext()) {
                                                    next = velocities_itr.next();
                                                    dX = next.getDX();
                                                    dY = next.getDY();
                                                    try {
                                                    if (canReachIn[i + dX][j + dY][m + 1]) {
                                                        m++;
                                                        i += dX;
                                                        j += dY;
                                                        changedIJ = true;
                                                        path.add(next.getMoveLabel());
                                                        break;
                                                    }
                                                    } catch (ArrayIndexOutOfBoundsException aioobexc) {
                                                        
                                                    }
                                                }
                                                if (!changedIJ) {
                                                    canReachIn[i][j][m] = false;
                                                    i = i_start;
                                                    j = j_start;
                                                    m = 0;
                                                    path.clear();
                                                }
                                            }
                                            int path_index = 0;
                                            while (path_index < m) {
                                                System.out.print(path.get(path_index) + " ");
                                                path_index++;
                                            }
                                            return;
                                        }
                                        reached = true;
                                        canReachIn[x + dX][y + dY][m] = true;
                                    }
                                } catch (ArrayIndexOutOfBoundsException aioobexc) {
                                    
                                }
                            }
                        }
        } while (reached);
        System.out.println("Impossible");
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String[] i_startJ_start = scanner.nextLine().split(" ");

        int i_start = Integer.parseInt(i_startJ_start[0]);

        int j_start = Integer.parseInt(i_startJ_start[1]);

        int i_end = Integer.parseInt(i_startJ_start[2]);

        int j_end = Integer.parseInt(i_startJ_start[3]);

        printShortestPath(n, i_start, j_start, i_end, j_end);

        scanner.close();
    }
}
