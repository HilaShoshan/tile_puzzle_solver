import java.io.PrintWriter;
import java.util.Arrays;

public class Ex1 {
    public static void main(String[] args) throws Exception {

        InitGame game = new InitGame();  // create a game from input.txt file
        long startTime, estimatedTime;  // counting the running time of the algorithm

        /*System.out.println("Start: ");
        int[][] start_state = game.getStart_state();
        print_matrix(start_state);

        System.out.println();
        System.out.println("Goal: ");
        int[][] goal_state = game.getGoal_state();
        print_matrix(goal_state);
         */

        String algorithm = game.getAlgorithm();
        Algorithm algo;
        startTime = System.currentTimeMillis();
        switch (algorithm) {
            case "BFS":
                algo = new BFS(game);
                algo.run();
                break;
            case "DFID":
                algo = new DFID(game);
                algo.run();
                break;
            case "A*":
                algo = new Astar(game);
                algo.run();
                break;
            case "IDA*":
                algo = new IDAstar(game);
                algo.run();
                break;
            case "DFBnB":
                algo = new DFBnB(game);
                algo.run();
                break;
            default:
                throw new Exception("Invalid Input");
        }
        estimatedTime = System.currentTimeMillis() - startTime;
        Node state = algo.getState();
        PrintWriter writer = new PrintWriter("result.txt", "UTF-8");  // change to output.txt!!!
        if (state.getPath().size() == 0) {
            writer.println("no path");
        } else {
            for (int i = 0; i < state.getPrevActions().size(); i++) {
                writer.print(state.getPrevOrgans().get(i)+state.getPrevActions().get(i)+'-');
                
            }
        }

        writer.println("The first line");
        writer.println("The second line");
        writer.close();
    }

    public static void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }
}
