import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {
    
    static LinkedList<TwoDimensionalPath> generatePaths(int n, int m, int x, int y, int c, boolean[][][] reach) {
        assert reach[x][y][c];
        final int nm = n * m;
        boolean[][][] reformedReach = new boolean[n][m][nm];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int k = 0; k < nm; k++)
                    reformedReach[i][j][k] = reach[i][j][k];
        for (int v = 0; v < nm; v++)
            reformedReach[x][y][v] = false;
        LinkedList<TwoDimensionalPath> paths = new LinkedList<TwoDimensionalPath>();
        if (c == 0) {
            TwoDimensionalCoordinate only = new TwoDimensionalCoordinate(x, y);
            TwoDimensionalPath P = new TwoDimensionalPath(only);
            paths.add(P);
        } else {
            TwoDimensionalCoordinate origin = new TwoDimensionalCoordinate(x, y);
            // UP ?
            if (y > 0 && reach[x][y - 1][c - 1]) {
                LinkedList<TwoDimensionalPath> recur = generatePaths(n, m, x, y - 1, c - 1, reformedReach);
                Iterator<TwoDimensionalPath> itrOuter = recur.iterator();
                while (itrOuter.hasNext()) {
                    TwoDimensionalPath next = itrOuter.next();
                    Iterator<TwoDimensionalCoordinate> itrInner = next.getCRDs().iterator();
                    TwoDimensionalPath recurPath = new TwoDimensionalPath(origin);
                    while (itrInner.hasNext()) 
                        recurPath.addCRD(itrInner.next());
                    paths.add(recurPath);
                }
            }
            // DOWN ?
            if (y < m - 1 && reach[x][y + 1][c - 1]) {
                LinkedList<TwoDimensionalPath> recur = generatePaths(n, m, x, y + 1, c - 1, reformedReach);
                Iterator<TwoDimensionalPath> itrOuter = recur.iterator();
                while (itrOuter.hasNext()) {
                    TwoDimensionalPath next = itrOuter.next();
                    Iterator<TwoDimensionalCoordinate> itrInner = next.getCRDs().iterator();
                    TwoDimensionalPath recurPath = new TwoDimensionalPath(origin);
                    while (itrInner.hasNext()) 
                        recurPath.addCRD(itrInner.next());
                    paths.add(recurPath);
                }
            }
            // LEFT ?
            if (x > 0 && reach[x - 1][y][c - 1]) {
                LinkedList<TwoDimensionalPath> recur = generatePaths(n, m, x - 1, y, c - 1, reformedReach);
                Iterator<TwoDimensionalPath> itrOuter = recur.iterator();
                while (itrOuter.hasNext()) {
                    TwoDimensionalPath next = itrOuter.next();
                    Iterator<TwoDimensionalCoordinate> itrInner = next.getCRDs().iterator();
                    TwoDimensionalPath recurPath = new TwoDimensionalPath(origin);
                    while (itrInner.hasNext()) 
                        recurPath.addCRD(itrInner.next());
                    paths.add(recurPath);
                }
            }
            // RIGHT ?
            if (x < n - 1 && reach[x + 1][y][c - 1]) {
                LinkedList<TwoDimensionalPath> recur = generatePaths(n, m, x + 1, y, c - 1, reformedReach);
                Iterator<TwoDimensionalPath> itrOuter = recur.iterator();
                while (itrOuter.hasNext()) {
                    TwoDimensionalPath next = itrOuter.next();
                    Iterator<TwoDimensionalCoordinate> itrInner = next.getCRDs().iterator();
                    TwoDimensionalPath recurPath = new TwoDimensionalPath(origin);
                    while (itrInner.hasNext()) 
                        recurPath.addCRD(itrInner.next());
                    paths.add(recurPath);
                }
            }
        }
        return paths;
    }

    /*
     * Complete the gridlandProvinces function below.
     */
    static int gridlandProvinces(String s1, String s2) {
        /*
         * Write your code here.
         */
        List<TwoDimensionalPath> paths = new LinkedList<TwoDimensionalPath>();
        final int n = s1.length();
        final int m = 2;
        final int nm = n * m;
        boolean[][][] reach = new boolean[n][m][nm];
        int i = 0, j = 0, k = 0;
        int x, y;
        boolean reached;
        for (x = 0; x < n; x++)
            for (y = 0; y < m; y++) {
                // Falsify
                for (i = 0; i < n; i++)
                    for (j = 0; j < m; j++)
                        for (k = 0; k < nm; k++)
                            reach[i][j][k] = false;
                reach[x][y][0] = true;
                do {
                    reached = false;
                    for (k = 1; k < nm; k++)
                        for (i = 0; i < n; i++)
                            for (j = 0; j < m; j++)
                                if (reach[i][j][k - 1]) {
                                    // UP ?
                                    if (j > 0) {
                                        if (!reach[i][j - 1][k]) {
                                            reach[i][j - 1][k] = true;
                                            reached = true;
                                        }
                                    }
                                    // DOWN ?
                                    if (j < m - 1) {
                                        if (!reach[i][j + 1][k]) {
                                            reach[i][j + 1][k] = true;
                                            reached = true;
                                        }
                                    }
                                    // LEFT ?
                                    if (i > 0) {
                                        if (!reach[i - 1][j][k]) {
                                            reach[i - 1][j][k] = true;
                                            reached = true;
                                        }
                                    }
                                    // RIGHT ?
                                    if (i < n - 1) {
                                        if (!reach[i + 1][j][k]) {
                                            reach[i + 1][j][k] = true;
                                            reached = true;
                                        }
                                    }
                                }
                } while (reached);
                for (i = 0; i < n; i++) for (j = 0; j < m; j++)
                    if (reach[i][j][nm - 1]) {
                LinkedList<TwoDimensionalPath> genPaths = generatePaths(n, m, i, j, nm - 1, reach);
                        Iterator<TwoDimensionalPath> itrOuter = genPaths.iterator();
                        while (itrOuter.hasNext()) {
                            TwoDimensionalPath next = itrOuter.next();
                            paths.add(next);
                        }
                    }
            }
        Set<String> distinctAdventures = new HashSet<String>();
        Iterator<TwoDimensionalPath> itrOuter = paths.iterator();
        while (itrOuter.hasNext()) {
            TwoDimensionalPath path = itrOuter.next();
            Iterator<TwoDimensionalCoordinate> itrInner = path.getCRDs().iterator();
            StringBuffer adventureCode = new StringBuffer();
            while (itrInner.hasNext()) {
                TwoDimensionalCoordinate crd = itrInner.next();
                switch (crd.getY()) {
                    case 0:
                        adventureCode.append(s1.charAt(crd.getX()));
                        break;
                    case 1:
                        adventureCode.append(s2.charAt(crd.getX()));
                }
            }
            distinctAdventures.add(adventureCode.toString());
        }
        return distinctAdventures.size();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int p = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        for (int pItr = 0; pItr < p; pItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

            String s1 = scanner.nextLine();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

            String s2 = scanner.nextLine();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

            int result = gridlandProvinces(s1, s2);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}

class TwoDimensionalPath {
    
    private LinkedList<TwoDimensionalCoordinate> CRDs;
    
    public TwoDimensionalPath(TwoDimensionalCoordinate origin) {
        CRDs = new LinkedList<TwoDimensionalCoordinate>();
        CRDs.add(origin);
    }
    
    public void addCRD(TwoDimensionalCoordinate next) {
        CRDs.add(next);
    }
    
    public LinkedList<TwoDimensionalCoordinate> getCRDs() {
        return CRDs;
    }
}

class TwoDimensionalCoordinate {
    
    private final int X;
    private final int Y;
    
    public TwoDimensionalCoordinate(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
    
    public int getX() {
        return X;
    }
    
    public int getY() {
        return Y;
    }
}
