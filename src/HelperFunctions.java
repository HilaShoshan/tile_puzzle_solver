import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HelperFunctions {

    /**
     * checks if a given matrix equals to the goal (private field of the class)
     * @param board = the given matrix (some board of a new state returned from 'operator')
     * @return false if it's noe the goal, true else.
     */
    public static boolean isGoal(int[][] goal_matrix, int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != goal_matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * printing functions
     */

    public static void print_openList(HashMap<String, Node> openList, int i) {
        System.out.println("Open-List -- Iteration "+i);
        for (String key : openList.keySet()) {
            System.out.println(key);
        }
        System.out.println("_________________");
    }

    public static void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }

    public static void print_actions(ArrayList<Character> list) {
        System.out.println("prevActions:");
        for (char c : list) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void print_organs(ArrayList<String> list) {
        System.out.println("prevOrgans:");
        for (String s : list) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
