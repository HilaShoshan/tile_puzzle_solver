import com.sun.xml.internal.ws.encoding.MtomCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IDAstar implements Algorithm {

    private Node state, n, g, g_twin;

    // data structures for the algorithm
    private Stack<Node> L = new Stack<>();
    private HashMap<String, Node> H = new HashMap<>();

    public IDAstar(int[][] start) {
        this.state = new Node(start);
    }

    @Override
    public void run() {
        HelperFunctions.setF_manhattan(state, HelperFunctions.findEmptyCells(Ex1.GOAL).size());
        double t = state.getF();
        double minF;
        ArrayList<Point> emptyCells;
        int iteration = 0;
        while (t != Integer.MAX_VALUE) {
            minF = Integer.MAX_VALUE;
            L.push(state);
            H.put(state.toString(), state);
            state.setOUT(false);
            while (!L.isEmpty()) {
                if (Ex1.OPEN) HelperFunctions.print_openList(H, iteration);
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
                            HelperFunctions.setF_manhattan(g, emptyCells.size());
                            if (g.getF() > t) {
                                minF = Math.min(minF, g.getF());
                                continue;
                            }
                            if (H.containsKey(g.toString()) && H.get(g.toString()).isOUT())
                                continue;
                            if (H.containsKey(g.toString()) && !H.get(g.toString()).isOUT()) {
                                g_twin = H.get(g.toString());
                                if (g_twin.getF() > g.getF()) {
                                    L.remove(g_twin);
                                    H.remove(g.toString());
                                } else continue;
                            }
                            if (HelperFunctions.isGoal(g.getBoard())) {
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

    @Override
    public Node getState() {
        return state;
    }
}
