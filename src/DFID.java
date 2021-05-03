import java.util.ArrayList;
import java.util.HashMap;

/**
 * class that implements the recursive DFID algorithm with loop avoidance
 */
public class DFID implements Algorithm {

    private Node state, temp;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private HashMap<String, Node> H = new HashMap<>(); // saves the vertices on the current path - for the loop avoidance

    public DFID(InitGame game) {
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
        this.open = game.getOpen();
    }

    @Override
    public void run() {
        int depth = 1;  // the limit for the Limited_DFS
        while (depth > 0) {  // infinite loop
            if (limited_DFS(depth)) return;
        }
    }

    /**
     * A function that runs DFS algorithm until the given limit
     * @param limit is the limit where we should stop
     * @return true if the function has come to a conclusion (found the goal or failed after scanning the entire tree,
     * there is no path), and false if it stopped because of the limit.
     */
    private boolean limited_DFS(int limit) {
        if (HelperFunctions.isGoal(goal_matrix, state.getBoard())) return true;
        if (limit == 0) return false;  // cutoff
        H.put(state.toString(), state);
        boolean isCutoff = false;
        ArrayList<Point> emptyCells = HelperFunctions.findEmptyCells(state.getBoard());
        return false;
    }

    @Override
    public Node getState() {
        return null;
    }
}
