import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IDAstar implements Algorithm {

    private Node state, n, g;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private Stack<Node> L = new Stack<>();
    private HashMap<String, Node> H = new HashMap<>();

    public IDAstar(InitGame game) {
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
    }

    @Override
    public void run() {
        int t = 3*Heuristics.ManhattanDistance2D(state.getBoard(), goal_matrix);
        int minF;
        ArrayList<Point> emptyCells;
        while (t != Integer.MAX_VALUE) {
            minF = Integer.MAX_VALUE;
            L.push(state);
            H.put(state.toString(), state);
            while (!L.isEmpty()) {
                n = L.pop();
                if (n.isOUT()) {  // n marked as out
                    H.remove(n.toString());
                } else {
                    n.setOUT(true);
                    L.push(n);
                    emptyCells = HelperFunctions.findEmptyCells(n.getBoard());
                    if (emptyCells.size() == 2) {  // two empty cells
                        for (char operator : operators) {  // try to do dual operators (move two items together)
                            g = n.doDualOperator(emptyCells, operator);
                        }
                    }
                    for (Point p : emptyCells) {
                        for (char c : operators) {  // check moving one item
                            g = n.operator(c, p.getI(), p.getJ());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Node getState() {
        return null;
    }
}
