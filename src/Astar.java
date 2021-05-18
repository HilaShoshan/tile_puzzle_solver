import java.util.*;

/**
 * Implement the Informed-search A* algorithm, with closed-list.
 */
public class Astar implements Algorithm {

    private Node state, temp;

    // data structures for the algorithm
    private PriorityQueue<Node> L_queue;  // sorted by the node's f-value (lower f value => better grade)
    private HashMap<String, Node> L_hash = new HashMap<>();
    private HashMap<String, Node> C = new HashMap<>();  // closeList

    ArrayList<Point> emptyCells;

    public Astar(int[][] start) {
        this.state = new Node(start);
        state.setF(state.getCost() + Heuristics.ManhattanDistance2D(state.getBoard()));
        L_queue = new PriorityQueue<Node>(new NodeComparator());  // init a PriorityQueue of Nodes with comparator that compare the nodes' f-value.
        L_queue.add(state);  // add the initial state to queue
        L_hash.put(state.toString(), state);  // and hash
    }

    @Override
    public void run() {
        while (!L_queue.isEmpty()) {
            state = L_queue.remove();
            L_hash.remove(state.toString());
            if (HelperFunctions.isGoal(state.getBoard())) return;
            C.put(state.toString(), state);
            emptyCells = HelperFunctions.findEmptyCells(state.getBoard());
            for (int i = 0; i < emptyCells.size(); i++) {
                for (String operator : OPERATORS) {  // check moving one item
                    temp = state.doOperator(emptyCells, i, operator);
                    CheckAndAdd();
                }
            }
        }
    }

    /**
     * A Helper function that checks if temp (state's child) should be entered to the queue, or be replaced,
     * and do what need to be done.
     */
    private void CheckAndAdd() {
        if (temp != null) {
            temp.setF(temp.getCost() + Heuristics.ManhattanDistance2D(temp.getBoard()));
            if (!C.containsKey(temp.toString()) && !L_hash.containsKey(temp.toString())) {
                L_hash.put(temp.toString(), temp);
                L_queue.add(temp);
            }
            else if (L_hash.containsKey(temp.toString())) {
                Node tempInL = L_hash.get(temp.toString());
                tempInL.setF(tempInL.getCost() + Heuristics.ManhattanDistance2D(tempInL.getBoard()));
                // HelperFunctions.setF_manhattan(temp);
                if (tempInL.getF() > temp.getF()) {
                    L_hash.remove(tempInL.toString());  // remove tempInL from the hash
                    L_hash.put(temp.toString(), temp);  // insert temp
                    L_queue.remove(tempInL);  // do the same to the queue
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
