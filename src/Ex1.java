import java.util.Arrays;

public class Ex1 {
    public static void main(String[] args) throws Exception {

        InitGame game = new InitGame();  // create a game from input.txt file

        System.out.println("Start: ");
        int[][] start_state = game.getStart_state();
        print_matrix(start_state);

        System.out.println();
        System.out.println("Goal: ");
        int[][] goal_state = game.getGoal_state();
        print_matrix(goal_state);
    }

    public static void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }
}
