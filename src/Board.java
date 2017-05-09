public class Board {
    private int n = 0;
    private int[][] arr;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        arr = blocks;
        n = arr.length;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming(){
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
        for (int row = 1; row <= n; row++){
            for (int col = 1; col <= n; col++){
                if  (row == n && col == n) continue;
                if (arr[row - 1][col - 1] != (row - 1) * n + col) {
                    int thisnumber = arr[row - 1][col - 1];
                    if  (thisnumber % n == 0) counter += Math.abs((row - thisnumber/n) + (n - col));
                    else counter += Math.abs((row - thisnumber/n + 1) + (n - col));
                }
            }
        }
        return counter;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 1; row <= n; row++){
            for (int col = 1; col <= n; col++){
                if  (row == n && col == n) continue;
                if (arr[row - 1][col - 1] != (row - 1) * n + col) return false;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin()

    // does this board equal y?
    public boolean equals(Object y)

    // all neighboring boards
    public Iterable<Board> neighbors()

    // string representation of this board (in the output format specified below)
    public String toString()

    // unit tests (not graded)
    public static void main(String[] args) {}
}