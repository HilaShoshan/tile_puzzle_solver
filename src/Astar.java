import java.util.*;

/**
 * Implementing the Informed-search A* algorithm, with closed-list.
 */
public class Astar implements Algorithm {

    private Node state, temp;

    // data structures for the algorithm
    private PriorityQueue<Node> L_queue;  // sorted by the node's f-value (lower f value => better grade)
    private HashMap<String, Node> L_hash = new HashMap<>();
    private HashMap<String, Node> C = new HashMap<>();  // closeList

    public Astar(int[][] start) {
        this.state = new Node(start);
        L_queue = new PriorityQueue<Node>(new NodeComparator());
        L_queue.add(state);
        L_hash.put(state.toString(), state);
    }

    @Override
    public void run() {
        while (!L_queue.isEmpty()) {
            state = L_queue.remove();
            L_hash.remove(state.toString());
            if (HelperFunctions.isGoal(state.getBoard())) return;
            C.put(state.toString(), state);
            ArrayList<Point> emptyCells = HelperFunctions.findEmptyCells(state.getBoard());
            for (int i = 0; i < emptyCells.size(); i++) {
                for (String operator : OPERATORS) {  // check moving one item
                    temp = state.doOperator(emptyCells, i, operator);
                    CheckAndAdd();
                }
            }
        }
    }

    private void CheckAndAdd() {
        if (temp != null) {
            if (!C.containsKey(temp.toString()) && !L_hash.containsKey(temp.toString())) {
                L_hash.put(temp.toString(), temp);
                L_queue.add(temp);
            }
            else if (L_hash.containsKey(temp.toString())) {
                Node tempInL = L_hash.get(temp.toString());
                int fQueue = tempInL.getCost() + 5*Heuristics.ManhattanDistance2D(tempInL.getBoard());
                int fNew = temp.getCost() + 5*Heuristics.ManhattanDistance2D(tempInL.getBoard());
                if (fQueue > fNew) {
                    L_hash.remove(temp.toString());  // remove tempInL from the queue
                    L_hash.put(temp.toString(), temp);
                    L_queue.remove(tempInL);
                    L_queue.add(temp);
                }
            }
        }
    }

    @Override
    public Node getState() {
        return this.state;
    }
}
