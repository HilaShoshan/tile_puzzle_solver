import java.io.PrintWriter;

/**
 * The main class.
 * Initializes the game using InitGame class.
 * Measures the run time of the program.
 * Runs the right algorithm.
 * Write to output.txt file all the asked details.
 */
public class Ex1 {

    public static int[][] GOAL;
    public static boolean OPEN;
    public static int NumEmptyCells;  // 1 or 2

    public static void main(String[] args) throws Exception {

        InitGame game = new InitGame();  // create a game from input.txt file
        long startTime, estimatedTime;  // counting the running time of the algorithm

        GOAL = game.getGoal_state();  // init the goal matrix of that game
        OPEN = game.getOpen();
        NumEmptyCells = game.getNumEmptyCells();
        int[][] start = game.getStart_state();
        String algorithm = game.getAlgorithm();

        Algorithm algo;
        startTime = System.currentTimeMillis();
        switch (algorithm) {
            case "BFS":
                algo = new BFS(start);
                algo.run();
                break;
            case "DFID":
                algo = new DFID(start);
                algo.run();
                break;
            case "A*":
                algo = new Astar(start);
                algo.run();
                break;
            case "IDA*":
                algo = new IDAstar(start);
                algo.run();
                break;
            case "DFBnB":
                algo = new DFBnB(start);
                algo.run();
                break;
            default:
                throw new Exception("Invalid Input");
        }
        estimatedTime = System.currentTimeMillis() - startTime;
        Node state = algo.getState();
        PrintWriter writer = new PrintWriter("result.txt", "UTF-8");  // change to output.txt!!!
        if (state.getFather() == null) {
            writer.println("no path");
        } else {
            String path = "";
            String toAdd;  // a string that will be added to path each time
            Node temp = state;
            while (temp.getFather() != null) {  // restores the path from the fathers of the Node
                toAdd = temp.getPrevItem() + temp.getPrevAction();
                if (path != "") toAdd+= "-";
                path = toAdd + path;
                temp = temp.getFather();
            }
            writer.println(path);
        }
        writer.println("Num: " + state.getNUM());
        writer.println("Cost: " + state.getCost());
        if (game.getTime()) {
            writer.println(estimatedTime/1000.0 + " seconds");
        }
        writer.close();
    }
}
