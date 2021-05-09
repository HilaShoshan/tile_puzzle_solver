import java.io.PrintWriter;

public class Ex1 {
    public static void main(String[] args) throws Exception {

        InitGame game = new InitGame();  // create a game from input.txt file
        long startTime, estimatedTime;  // counting the running time of the algorithm

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
        if (state.getFather() == null) {
            writer.println("no path");
        } else {
            String path = "";
            String toAdd;
            Node temp = state;
            while (temp.getFather() != null) {
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
