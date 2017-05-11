import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

import static edu.princeton.cs.algs4.StdRandom.uniform;


public class Board {
    private int n = 0;
    private int[][] arr;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        arr = new int[n][n];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j <blocks.length; j++)
                arr[i][j] = blocks[i][j];
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int counter = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (row == n && col == n) continue;
                if (arr[row - 1][col - 1] != (row - 1) * n + col) counter++;
            }
        }
        return counter;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int counter = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (arr[row - 1][col - 1] == 0) continue;
                int a = arr[row - 1][col - 1];
                int aCol = a % n;
                int aRow = (a / n) + 1;
                if (aCol == 0) {
                    aCol = n;
                    aRow--;
                }
                counter += Math.abs(row - aRow) + Math.abs(col - aCol);
            }
        }
        return counter;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (row == n && col == n) continue;
                if (arr[row - 1][col - 1] != (row - 1) * n + col) return false;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] arraysEvilTwin = makeArrCopy();

        int col1 = 0;
        int col2 = 0;
        int row1 = 0;
        int row2 = 0;
        while (true) {
            col1 = uniform(1, n + 1);
            col2 = uniform(1, n + 1);
            row1 = uniform(1, n + 1);
            row2 = uniform(1, n + 1);
            if (arraysEvilTwin[row1 - 1][col1 - 1] == 0 || arraysEvilTwin[row2 - 1][col2 - 1] == 0) continue;
            if (col1 != col2 || row1 != row2) break;
        }
        int num1 = arraysEvilTwin[row1 - 1][col1 - 1];
        arraysEvilTwin[row1 - 1][col1 - 1] = arraysEvilTwin[row2 - 1][col2 - 1];
        arraysEvilTwin[row2 - 1][col2 - 1] = num1;
        return new Board(arraysEvilTwin);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (that.arr[row - 1][col - 1] != this.arr[row - 1][col - 1]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int blankRow = 0;
        int blankCol = 0;
        // поиск нуля
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (arr[row - 1][col - 1] == 0) {
                    blankRow = row;
                    blankCol = col;
                    break;
                }
                if ((blankRow != 0 && blankCol != 0) && (arr[blankRow - 1][blankCol - 1] == 0)) break;
            }
        }       // максимум 4 варианта действий, минимум 2 (возвртный вариант убьет solver)
        if (blankRow != 1) neighbors.add(new Board(swtch(blankRow, blankCol, blankRow - 1, blankCol)));
        if (blankRow != n) neighbors.add(new Board(swtch(blankRow, blankCol, blankRow + 1, blankCol)));
        if (blankCol != 1) neighbors.add(new Board(swtch(blankRow, blankCol, blankRow, blankCol - 1)));
        if (blankCol != n) neighbors.add(new Board(swtch(blankRow, blankCol, blankRow, blankCol + 1)));

        return neighbors;
    }

    // служебный метод для обмена двух тайлов
    private int[][] swtch(int zeroRow, int zeroCol, int rowThat, int colThat) {
        int[][] borda = makeArrCopy();
        int victim = borda[rowThat - 1][colThat - 1];
        borda[rowThat - 1][colThat - 1] = 0;
        borda[zeroRow - 1][zeroCol - 1] = victim;
        return borda;
    }

    private int[][] makeArrCopy() {
        int[][] newborn = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                newborn[row][col] = arr[row][col];
            }
        }
        return newborn;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", arr[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
    }
}