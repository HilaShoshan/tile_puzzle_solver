import javafx.print.Collation;

import java.util.*;

public class DFBnB implements Algorithm {

    private Node state, n, g;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private Stack<Node> L = new Stack<>();
    private HashMap<String, Node> H = new HashMap<>();
    private ArrayList<Node> children = new ArrayList<>();
    private ArrayList<Point> emptyCells;

    public DFBnB(InitGame game) {
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
        this.open = game.getOpen();
        L.push(state);
        H.put(state.toString(), state);
    }

    @Override
    public void run() {
        int t = Integer.MAX_VALUE;
        int iteration = 1;
        while (!L.isEmpty()) {
            n = L.pop();  // remove front and saves it as Node n
            if (n.isOUT()) {
                H.remove(n.toString());
            } else {
                n.setOUT(true);
                L.push(n);
                findChildren(n);  // after this function, the "children" ArrayList contains all the children of n, and sorted by children's f values
                Iterator<Node> itr = children.iterator();
                while (itr.hasNext()) {
                    g = itr.next();
                    boolean contains_g = H.containsKey(g.toString());
                    if (f(g) >= t) {
                        itr.remove();
                        while (itr.hasNext()) {
                            itr.next();
                            itr.remove();  // remove g and all the nodes after it from children
                        }
                    } else if (contains_g) {
                        Node g_twin = H.get(g.toString());
                        if (g_twin.isOUT())  // If H contains g’=g and g’ is marked as "out"
                            itr.remove();  // remove g from children
                        else {  // If H contains g’=g and g’ is not marked as "out"
                            if (f(g_twin) <= f(g)) itr.remove();
                            else {
                                L.remove(g_twin);
                                H.remove(g.toString());
                            }
                        }
                    } else if (HelperFunctions.isGoal(goal_matrix, g.getBoard())) {  // if we reached here, f(g) < t
                        t = f(g);
                        state = g;  // like setting the "result" to path(g)
                        itr.remove();
                        while (itr.hasNext()) {
                            itr.next();
                            itr.remove();  // remove g and all the nodes after it from children
                        }
                    }
                }
                // insert children list in a reverse order to L and H
                for (int i = children.size()-1; i >= 0; i--) {
                    L.push(children.get(i));
                    H.put(children.get(i).toString(), children.get(i));
                }
            }
            iteration++;
        }
    }

    private int f(Node node) {
        return node.getCost() + 3*Heuristics.ManhattanDistance2D(node.getBoard(), goal_matrix);
    }

    private void findChildren(Node n) {
        emptyCells = HelperFunctions.findEmptyCells(n.getBoard());
        Node child;
        for (int i = 0; i < emptyCells.size(); i++) {
            for (String operator : OPERATORS) {
                child = n.doOperator(emptyCells, i, operator);
                if (child != null) children.add(child);
            }
        }
        Collections.sort(children, new NodeComparator(goal_matrix));
    }

    @Override
    public Node getState() {
        return state;
    }
}
