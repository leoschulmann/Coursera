import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Solver {
    private int currentMove = 0;
    private MinPQ<SearchNode> pq;
    private SearchNode finalStep = null;
    private Board twinBoard;
    private boolean isSolved = false;


    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moveWhenCreated;
        private SearchNode parent;

        public SearchNode(Board board, int moveWhenCreated, SearchNode parent) {
            this.board = board;
            this.moveWhenCreated = moveWhenCreated;
            this.parent = parent;
        }

        @Override
        public int compareTo(SearchNode that) {
            int thisPriority = this.board.manhattan() + this.moveWhenCreated;
            int thatPriority = that.board.manhattan() + that.moveWhenCreated;
            if (thisPriority < thatPriority) return -1;
            if (thisPriority == thatPriority && this.moveWhenCreated < that.moveWhenCreated) return -1;
            return 1;
        }

//        }
    }

    // find a solution to the initial bo (using the A* algorithm)
    public Solver(Board initial) {
        if  (initial == null) throw new NullPointerException("null argument!");
        pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, currentMove, null));
        twinBoard = initial.twin();
        pq.insert(new SearchNode(twinBoard, currentMove, null));

        currentMove++;

        while (true) {
            SearchNode currentNode = pq.delMin();
            if (currentNode.board.isGoal()) {

                finalStep = currentNode;
                SearchNode o = currentNode;
                while (true) {
                    if (o.parent == null) {
                        if (o.board.equals(twinBoard)) {
                            isSolved = false;
                            break;
                        } else {
                            isSolved = true;
                            break;
                        }
                    } else o = o.parent;
                }
            break;
        }
        currentMove = currentNode.moveWhenCreated + 1;
        ArrayList<Board> boards = (ArrayList<Board>) currentNode.board.neighbors();
        for (Board x : boards) {
            if (currentMove == 1) pq.insert(new SearchNode(x, currentMove, currentNode));
            else if (!x.equals(currentNode.parent.board)) {
                pq.insert(new SearchNode(x, currentMove, currentNode));
            }
        }
    }
}

    // is the initial bo solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of currentMove to solve initial bo; -1 if unsolvable
    public int moves() {
        return isSolvable() ? currentMove : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> steps = new Stack<>();
        SearchNode step = finalStep;
        for (int i = currentMove; i >= 0; i--) {
            steps.push(step.board);
            step = step.parent;
        }
        Collections.reverse(steps);
        if (steps.get(0).equals(twinBoard)) {
            isSolved = false;
            return null;
        }
        return steps;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial bo from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}