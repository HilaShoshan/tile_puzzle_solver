import com.sun.xml.internal.ws.encoding.MtomCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IDAstar implements Algorithm {

    private Node state, n, g, g_twin;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private Stack<Node> L = new Stack<>();
    private HashMap<String, Node> H = new HashMap<>();

    public IDAstar(InitGame game) {
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
        this.open = game.getOpen();
    }

    @Override
    public void run() {
        int t = f(state);
        int minF;
        ArrayList<Point> emptyCells;
        int iteration = 0;
        while (t != Integer.MAX_VALUE) {
            minF = Integer.MAX_VALUE;
            L.push(state);
            H.put(state.toString(), state);
            state.setOUT(false);
            while (!L.isEmpty()) {
                if (open) HelperFunctions.print_openList(H, iteration);
                n = L.pop();
                if (n.isOUT()) {  // n marked as out
                    H.remove(n.toString());
                } else {
                    n.setOUT(true);
                    L.push(n);
                    emptyCells = HelperFunctions.findEmptyCells(n.getBoard());
                    for (int i = 0; i < emptyCells.size(); i++) {
                        for (String operator : OPERATORS) {
                            g = n.doOperator(emptyCells, i, operator);
                            if (g == null) {
                                continue;
                            }
                            if (f(g) > t) {
                                minF = Math.min(minF, f(g));
                                continue;
                            }
                            if (H.containsKey(g.toString()) && H.get(g.toString()).isOUT())
                                continue;
                            if (H.containsKey(g.toString()) && !H.get(g.toString()).isOUT()) {
                                g_twin = H.get(g.toString());
                                if (f(g_twin) > f(g)) {
                                    L.remove(g_twin);
                                    H.remove(g.toString());
                                } else continue;
                            }
                            if (HelperFunctions.isGoal(goal_matrix, g.getBoard())) {
                                state = g;
                                return;
                            }
                            L.push(g);
                            H.put(g.toString(), g);
                        }
                    }
                }
                iteration++;
            }
            t = minF;
        }
    }

    private int f(Node node) {
        return node.getCost() + 3*Heuristics.ManhattanDistance2D(node.getBoard(), goal_matrix);
    }

    @Override
    public Node getState() {
        return state;
    }
}
