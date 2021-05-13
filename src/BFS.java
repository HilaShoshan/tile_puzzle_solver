import java.util.*;

/**
 * Implementing the Uniformed-search BFS algorithm, with closed-list.
 */
public class BFS implements Algorithm {

    private Node state, temp;

    // data structures for the algorithm
    private Queue<Node> L_queue = new LinkedList<>();
    private HashMap<String, Node> L_hash = new HashMap<>(); // parallel to the queue, in order to find items in O(1)
    private HashMap<String, Node> C = new HashMap<>();  // closeList

    public BFS(int[][] start) {
        this.state = new Node(start);
    }

    @Override
    public void run() {
        L_queue.add(state);
        L_hash.put(state.toString(), state);
        int i = 0;
        while (!L_queue.isEmpty()) {
            if (Ex1.OPEN) HelperFunctions.print_openList(L_hash, i);
            state = L_queue.remove();  // remove and return the element at the head the queue
            L_hash.remove(state.toString());
            C.put(state.toString(), state);
            ArrayList<Point> emptyCells = HelperFunctions.findEmptyCells(state.getBoard());  // have to be a list of one or two cells
            for (int index = 0; index < emptyCells.size(); index++) {
                for (String operator : OPERATORS) {
                    temp = state.doOperator(emptyCells, index, operator);
                    if (CheckAndAdd()) return;
                }
            }
            i++;
        }
    }

    /**
     * helper function for 'run' method.
     * checks if the temp board that returns from 'operator' is not null and should be added to the queue
     * also checks if it's the goal, and if so - determines state = temp
     * @return true if we done (the goal was found), false else.
     */
    private boolean CheckAndAdd() {
        if (temp != null && !C.containsKey(temp.toString()) && !L_hash.containsKey(temp.toString())) {
            if (HelperFunctions.isGoal(temp.getBoard())) {
                state = temp;
                return true;
            }
            L_queue.add(temp);
            L_hash.put(temp.toString(), temp);
        }
        return false;
    }

    @Override
    public Node getState() {
        return state;
    }

}
