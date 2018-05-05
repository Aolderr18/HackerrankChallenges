import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static int quickestWayUp(int[][] ladders, int[][] snakes) {
        // Complete this function
        boolean[][] canReachTileIn = new boolean[100][100];
        int d, r;
        for (d = 0; d < 100; d++)
            for (r = 0; r < 100; r++)
                canReachTileIn[d][r] = false;
        int[] transfer = new int[100];
        for (d = 0; d < 100; d++)
            transfer[d] = d;
        for (int[] ladder : ladders)
            transfer[ladder[0] - 1] = ladder[1] - 1;
        for (int[] snake : snakes) 
            transfer[snake[0] - 1] = snake[1] - 1;
        int e;
        for (e = 1; e <= 6; e++) {
            if (transfer[e] == 99)
                return 1;
            canReachTileIn[transfer[e]][0] = true;
        }
        for (r = 1; r < 100; r++)
            for (d = 1; d < 100; d++)
                if (canReachTileIn[d][r - 1])
                    for (e = d + 1; e <= d + 6 && e < 100; e++) {
                        if (transfer[e] == 99)
                            return r + 1;
                        canReachTileIn[transfer[e]][r] = true;
                    }
        return -1;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int[][] ladders = new int[n][2];
            for(int ladders_i = 0; ladders_i < n; ladders_i++){
                for(int ladders_j = 0; ladders_j < 2; ladders_j++){
                    ladders[ladders_i][ladders_j] = in.nextInt();
                }
            }
            int m = in.nextInt();
            int[][] snakes = new int[m][2];
            for(int snakes_i = 0; snakes_i < m; snakes_i++){
                for(int snakes_j = 0; snakes_j < 2; snakes_j++){
                    snakes[snakes_i][snakes_j] = in.nextInt();
                }
            }
            int result = quickestWayUp(ladders, snakes);
            System.out.println(result);
        }
        in.close();
    }
}
