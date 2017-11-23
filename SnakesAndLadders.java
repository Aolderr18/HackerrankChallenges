import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        //
        Scanner input = new Scanner(System.in);
        int T;
        do {
            T = input.nextInt();
        } while (T < 1 || T > 10);
        while (T-- > 0) {
            int N;
            do {
                N = input.nextInt();
            } while (N < 1 || N > 15);
            int[] ladderBottoms = new int[N];
            int[] ladderHeads = new int[N];
            for (int l = 0; l < N; l++) {
                int bottom, head;
                do {
                    bottom = input.nextInt();
                } while (bottom < 1 || bottom > 99);
                do {
                    head = input.nextInt();
                } while (head < 2 || head > 100);
                ladderBottoms[l] = bottom;
                ladderHeads[l] = head;
            }
            int M;
            do {
                M = input.nextInt();
            } while (M < 1 || M > 15);
            int[] snakeMouths = new int[M];
            int[] snakeTails = new int[M];
            for (int z = 0; z < M; z++) {
                int mouth, tail;
                do {
                    mouth = input.nextInt();
                } while (mouth < 3 || mouth > 99);
                do {
                    tail = input.nextInt();
                } while (tail < 2 || tail > 98);
                snakeMouths[z] = mouth;
                snakeTails[z] = tail;
            }
            try {
                final SnakesAndLaddersBoard salb = new SnakesAndLaddersBoard(ladderBottoms, ladderHeads, snakeMouths, snakeTails);
                int mnor = minNumOfRolls(salb);
                System.out.println(mnor);
            } catch (InvalidSnakesAndLaddersBoardException ivsalbe) {
            //
            }
        }
        input.close();
    }
    
    public static int minNumOfRolls(SnakesAndLaddersBoard salb) {
        int u0, v0;
        final int width0 = salb.getNumberOfLadders();
        final int height0 = (int) (Math.pow(2, salb.getNumberOfLadders()));
        boolean b;
        final boolean[][] shouldAvoidLadder = new boolean[width0][height0];
        for (u0 = 0; u0 < width0; u0++) {
            b = true;
            for (v0 = 0; v0 < height0; v0++) {
                if (v0 % (int) (Math.pow(2, u0)) == 0)
                    b = !b;
                shouldAvoidLadder[u0][v0] = b;
            }
        }
        int u1, v1;
        final int width1 = salb.getNumberOfSnakes();
        final int height1 = (int) (Math.pow(2, salb.getNumberOfSnakes()));
        final boolean[][] shouldAvoidSnake = new boolean[width1][height1];
        for (u1 = 0; u1 < width1; u1++) {
            b = true;
            for (v1 = 0; v1 < height1; v1++) {
                if (v1 % (int) (Math.pow(2, u1)) == 0)
                    b = !b;
                shouldAvoidSnake[u1][v1] = b;
            }
        }
        
        int min = 200;
        for (v0 = 0; v0 < shouldAvoidLadder[0].length; v0++)
            for (v1 = 0; v1 < shouldAvoidSnake[0].length; v1++) {
                boolean[] includeLadders = new boolean[shouldAvoidLadder.length];
                for (u0 = 0; u0 < shouldAvoidLadder.length; u0++)
                    includeLadders[u0] = !shouldAvoidLadder[u0][v0];
                boolean[] includeSnakes = new boolean[shouldAvoidSnake.length];
                for (u1 = 0; u1 < shouldAvoidLadder.length; u1++)
                    includeSnakes[u1] = !shouldAvoidSnake[u1][v1];
                final int nextMin = minNumOfRolls(salb, includeLadders, includeSnakes);
                if (nextMin < min)
                    min = nextMin;
            }
        return min;
    }
    
    private static int minNumOfRolls(SnakesAndLaddersBoard salb, boolean[] includeLadders, boolean[] includeSnakes) {
        Map<Integer, Boolean> ladderIncluded = new LinkedHashMap<Integer, Boolean>();
        int ladderIndex = 0;
        Map<Integer, Boolean> snakeIncluded = new LinkedHashMap<Integer, Boolean>();
        int snakeIndex = 0;
        boolean[] visited = new boolean[100];
        for (int p = 1; p <= 100; p++) {
            switch (salb.getBoardCode()[p - 1]) {
                case 'b':
                    ladderIncluded.put(p, includeLadders[ladderIndex++]);
                    break;
                case 'm':
                    snakeIncluded.put(p, includeSnakes[snakeIndex++]);
            }
            visited[p - 1] = false;
        }
        int rolls = 0;
        int position = 1;
        int diceRoll;
        while (position < 100) {
            diceRoll = 1;
            boolean transportFound = false;
            while (position + diceRoll < 100 && diceRoll < 6 && !transportFound) {
                switch (salb.getBoardCode()[position + diceRoll - 1]) {
                    case 'b':
                        if (ladderIncluded.get(position + diceRoll)) 
                            transportFound = true;
                        break;
                    case 'm':
                        if (snakeIncluded.get(position + diceRoll))
                            transportFound = true;
                }
                if (transportFound)
                    break;
                diceRoll++;
            }
            boolean containsUnwantedTransport;
            do {
                containsUnwantedTransport = false;
                switch (salb.getBoardCode()[position + diceRoll - 1]) {
                    case 'b':
                        if (!ladderIncluded.get(position + diceRoll)) 
                            containsUnwantedTransport = true;
                        break;
                    case 'm':
                        if (!snakeIncluded.get(position + diceRoll)) 
                            containsUnwantedTransport = true;
                }
                if (containsUnwantedTransport)
                    --diceRoll;
            } while (containsUnwantedTransport && diceRoll > 0);
            position += diceRoll;
            position = salb.getTransportCode()[position - 1];
            if (visited[position - 1])
                return 200;
            visited[position - 1] = true;
            ++rolls;
        }
        return rolls;
    }
}

final class SnakesAndLaddersBoard {
    private final char[] boardCode; /*
                               * 'b' = Ladder Bottom
                               * 'h' = Ladder Head
                               * 'm' = Snake Mouth
                               * 't' = Snake Tail
                               */
    private final int numberOfLadders;
    private final int numberOfSnakes;
    
    private final int[] transportCode;
    
    public SnakesAndLaddersBoard(int[] ladderBottoms, int[] ladderHeads, int[] snakeMouths, int[] snakeTails) throws InvalidSnakesAndLaddersBoardException {
        for (int lb : ladderBottoms) {
            for (int lh : ladderHeads)
                if (lb == lh)
                    throw new InvalidSnakesAndLaddersBoardException("Invalid");
            for (int sm : snakeMouths)
                if (lb == sm)
                    throw new InvalidSnakesAndLaddersBoardException("Invalid");
            for (int st : snakeTails)
                if (lb == st)
                    throw new InvalidSnakesAndLaddersBoardException("Invalid");
        }
        for (int lh : ladderHeads) {
            for (int sm : snakeMouths)
                if (lh == sm)
                    throw new InvalidSnakesAndLaddersBoardException("Invalid");
            for (int st : snakeTails)
                if (lh == st)
                    throw new InvalidSnakesAndLaddersBoardException("Invalid");
        }
        for (int sm : snakeMouths)
            for (int st : snakeTails)
                if (sm == st)
                    throw new InvalidSnakesAndLaddersBoardException("Invalid");
        // Count the number of ladders and snakes
        int numberOfLadderBottoms = ladderBottoms.length, numberOfLadderHeads = ladderHeads.length, numberOfSnakeMouths = snakeMouths.length, numberOfSnakeTails = snakeTails.length;
        numberOfLadders = numberOfLadderHeads;
        numberOfSnakes = numberOfSnakeMouths;
        if (!(numberOfLadderBottoms == numberOfLadderHeads))
            throw new InvalidSnakesAndLaddersBoardException("Invalid");
        if (!(numberOfSnakeMouths == numberOfSnakeTails))
            throw new InvalidSnakesAndLaddersBoardException("Invalid");
        transportCode = new int[100];
        int s;
        for (s = 0; s < transportCode.length; s++)
            transportCode[s] = s + 1;
        for (s = 0; s < ladderBottoms.length; s++) 
            transportCode[ladderBottoms[s] - 1] = ladderHeads[s];
        for (s = 0; s < snakeMouths.length; s++)
            transportCode[snakeMouths[s] - 1] = snakeTails[s];
        boardCode = new char[100];
        for (s = 0; s < boardCode.length; s++)
            boardCode[s] = 'r';
        
        for (int lb : ladderBottoms)
            boardCode[lb - 1] = 'b';
        for (int lh : ladderHeads)
            boardCode[lh - 1] = 'h';
        for (int sm : snakeMouths)
            boardCode[sm - 1] = 'm';
        for (int st : snakeTails)
            boardCode[st - 1] = 't';
    }
    
    public char[] getBoardCode() {
        return boardCode;
    }
    
    public int getNumberOfLadders() {
        return numberOfLadders;
    }
    
    public int getNumberOfSnakes() {
        return numberOfSnakes;
    }
    
    public int[] getTransportCode() {
        return transportCode;
    }
}

final class InvalidSnakesAndLaddersBoardException extends Exception {
    public InvalidSnakesAndLaddersBoardException(String exc) {
        super(exc);
    }
}
