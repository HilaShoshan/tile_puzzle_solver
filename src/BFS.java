import java.util.*;

public class BFS implements Algorithm {

    private Node state, temp;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private Queue<Node> L_queue = new LinkedList<>();
    private HashMap<String, Node> L_hash = new HashMap<>(); // parallel to the queue, in order to find items in O(1)
    private HashMap<String, Node> C = new HashMap<>();  // closeList

    public BFS(InitGame game) {
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
        this.open = game.getOpen();
    }

    @Override
    public void run() {
        L_queue.add(state);
        L_hash.put(state.toString(), state);
        int i = 0;
        while (!L_queue.isEmpty()) {
            if (open) HelperFunctions.print_openList(L_hash, i);
            state = L_queue.remove();  // remove and return the element at the head the queue
            L_hash.remove(state.toString());
            C.put(state.toString(), state);
            ArrayList<Point> emptyCells = HelperFunctions.findEmptyCells(state.getBoard());  // have to be a list of one or two cells
            if (emptyCells.size() == 2) {  // two empty cells
                Point first = emptyCells.get(0);
                Point second = emptyCells.get(1);
                if (HelperFunctions.isAbove(first, second)) {// one below the other
                    for (char c : lr) {
                        temp = state.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                        if (CheckAndAdd()) return;  // Done (found the goal)
                    }
                }
                if (HelperFunctions.isNext(first, second)) {  // one next the other
                    for (char c : ud) {
                        temp = state.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                        if (CheckAndAdd()) return;
                    }
                }
            }
            for (Point p : emptyCells) {
                for (char c : operators) {  // check moving one item
                    temp = state.operator(c, p.getI(), p.getJ());
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
            if (HelperFunctions.isGoal(goal_matrix, temp.getBoard())) {
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
