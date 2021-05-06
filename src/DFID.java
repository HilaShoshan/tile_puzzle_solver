import java.util.ArrayList;
import java.util.HashMap;

/**
 * class that implements the recursive DFID algorithm with loop avoidance
 */
public class DFID implements Algorithm {

    private Node state; //temp;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private HashMap<String, Node> H; // saves the vertices on the current path - for the loop avoidance

    enum Result {
        FOUND,
        CUTOFF,
        FAILED
    }

    public DFID(InitGame game) {
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
        this.open = game.getOpen();
    }

    @Override
    public void run() {
        int depth = 1;  // the limit for the Limited_DFS
        while (depth > 0) {  // infinite loop
            H = new HashMap<>();
            if (limited_DFS(state, depth, H) != Result.CUTOFF) return;
            depth++;
        }
    }

    /**
     * A function that runs DFS algorithm until the given limit
     * @param limit is the limit where we should stop
     * @return true if the function has come to a conclusion (found the goal or failed after scanning the entire tree,
     * there is no path), and false if it stopped because of the limit.
     */
    private Result limited_DFS(Node n, int limit, HashMap<String, Node> Hash) {
        if (HelperFunctions.isGoal(goal_matrix, n.getBoard())) {
            state = n;
            return Result.FOUND;
        }
        if (limit == 0) return Result.CUTOFF;  // we're in the maximum level
        Hash.put(n.toString(), n);
        boolean isCutoff = false;
        ArrayList<Point> emptyCells = HelperFunctions.findEmptyCells(n.getBoard());
        Result result;
        Node g;
        if (emptyCells.size() == 2) {  // two empty cells
            Point first = emptyCells.get(0);
            Point second = emptyCells.get(1);
            if (HelperFunctions.isAbove(first, second)) {  // one below the other
                for (char c : lr) {
                    g = n.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                    if (g != null) {
                        if (Hash.containsKey(g.toString())) continue;
                        //else g.setFather(n);
                        result = limited_DFS(g, limit-1, Hash);
                        if (result == Result.CUTOFF) isCutoff = true;
                        else if (result != Result.FAILED) return result;
                    }
                }
            }
            if (HelperFunctions.isNext(first, second)) {  // one next the other
                for (char c : ud) {
                    g = n.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                    if (g != null) {
                        if (Hash.containsKey(g.toString())) continue;
                        //else g.setFather(n);
                        result = limited_DFS(g, limit-1, Hash);
                        if (result == Result.CUTOFF) isCutoff = true;
                        else if (result != Result.FAILED) return result;
                    }
                }
            }
        }   // not relevant to input.txt
        for (Point p : emptyCells) {
            for (char c : operators) {  // check moving one item
                g = n.operator(c, p.getI(), p.getJ());
                if (g != null) {
                    if (Hash.containsKey(g.toString())) continue;
                    //else g.setFather(n);
                    result = limited_DFS(g, limit-1, Hash);
                    if (result == Result.CUTOFF) isCutoff = true;
                    else if (result != Result.FAILED) return result;
                }
            }
        }
        Hash.remove(n.toString());
        if (isCutoff) return Result.CUTOFF;
        else return Result.FAILED;
    }

    @Override
    public Node getState() {
        return this.state;
    }
}
