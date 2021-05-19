import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HelperFunctions {

    /**
     * checks if a given matrix equals to the goal.
     * @param board = the given matrix (some board of a new state returned from 'operator')
     * @return false if it's not the goal, true else.
     */
    public static boolean isGoal(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != Ex1.GOAL[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * finds the empty cells in state's board.
     * @param board is the board matrix of some state.
     * @return an Arraylist of all the empty points in the board
     */
    public static ArrayList<Point> findEmptyCells(int[][] board) {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {  // find an empty cell
                    result.add(new Point(i,j));  // add it to the result list
                }
            }
        }
        return result;
    }

    public static boolean isAdjacent(ArrayList<Point> emptyCells) {
        if (emptyCells.size() != 2) return false;
        Point first = emptyCells.get(0);
        Point second = emptyCells.get(1);
        if ((Math.abs(first.getI()-second.getI())==1 && first.getJ() == second.getJ())  // one below the other
            || (first.getI() == second.getI() && Math.abs(first.getJ()-second.getJ())==1)) {  // one next the other
            return true;
        }
        return false;
    }

    public static boolean isAbove(Point first, Point second) {
        if (Math.abs(first.getI()-second.getI())==1 && first.getJ() == second.getJ()) {
            return true;
        }
        return false;
    }

    public static boolean isNext(Point first, Point second) {
        if (first.getI() == second.getI() && Math.abs(first.getJ()-second.getJ())==1) {
            return true;
        }
        return false;
    }

    /**
     * printing functions
     */

    public static void print_openList(HashMap<String, Node> openList, int i) {
        System.out.println("Open-List -- Iteration "+i);
        for (String key : openList.keySet()) {
            if (!openList.get(key).isOUT())  // if the node does not mark as "out"
                System.out.println(key);
        }
        System.out.println("_________________");
    }

    public static void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }

}
