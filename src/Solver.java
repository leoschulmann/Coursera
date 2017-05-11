import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Solver {
    private int moves = 0;
    private MinPQ pq;
    private SearchNode finalStep = null;


    private class SearchNode implements Comparable<SearchNode>{
        private Board board;
        private int move;
        private SearchNode parent;

        @Override
        public int compareTo(SearchNode that) {
            int thisPriority = this.board.manhattan() + this.move;
            int thatPriority = that.board.manhattan() + that.move;
            if (thisPriority < thatPriority) return -1;
            if (thisPriority == thatPriority && this.move < that.move) return -1;
            return 1;
        }

//        @Override
//        public int compareTo(SearchNode thisNode, SearchNode thatNode) {
//            int thisPriority = thisNode.board.manhattan() + thatNode.move;
//            int thatPriority = thatNode.board.manhattan() + thatNode.move;
//            if (thisPriority > thatPriority) return -1;
//            else if (thisPriority == thatPriority && thisNode.move > thatNode.move) return -1;
//            else return 1;
//        }

        public SearchNode(Board board, int move, SearchNode parent) {
            this.board = board;
            this.move = move;
            this.parent = parent;
        }
    }

    // find a solution to the initial bo (using the A* algorithm)
    public Solver(Board initial) {
        pq = new MinPQ<SearchNode>();
//        SearchNode startNode = new SearchNode(initial)
        pq.insert(new SearchNode(initial, moves, null));
        pq.insert(new SearchNode(initial.twin(), moves, null));

        while (true) {
            SearchNode node = (SearchNode)pq.delMin();
            if (node.board.isGoal()) {
                isSolved = true;
                finalStep = node;
                break;
            }
            moves++;
            ArrayList<Board> boards = (ArrayList<Board>) node.board.neighbors();
            for (Board x : boards) {
                if (x.equals(node.board)) boards.remove(x);
                else pq.insert(new SearchNode(x, moves, node));
            }
        }
    }

    private boolean isSolved = false;

    // is the initial bo solvable?
    public boolean isSolvable() {return isSolved;}


    // min number of moves to solve initial bo; -1 if unsolvable
    public int moves() {
        return isSolvable() ? moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> steps = new Stack<>();
//        steps.add(finalStep.board);
        SearchNode step = finalStep;
        for (int i = moves; i >= 0; i--){
            steps.push(step.board);
            step = step.parent;
        }
        return steps;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial bo from file
        In in = new In("c:\\puzzle3x3-03.txt");
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