import java.util.*;

public class BFS implements Algorithm {

    private Node state, temp;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private Queue<Node> L_queue = new LinkedList<>();
    private HashMap<String, Node> L_hash = new HashMap<>(); // parallel to the queue, in order to find organs in O(1)
    private HashMap<String, Node> C = new HashMap<>();  // closeList

    public BFS(InitGame game) {  // maybe change the given param!!
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
        this.open = game.getOpen();
    }

    @Override
    public void run() {
        L_queue.add(state);
        L_hash.put(state.toString(), state);
        System.out.println(state);
        int i = 0;
        while (!L_queue.isEmpty()) {
            System.out.println("Queue size: " + L_queue.size());
            System.out.println("Hash size: " + L_hash.size());
            System.out.println("______________________________");
            if (open) print_openList(i);
            state = L_queue.remove();  // remove and return the element at the head the queue
            L_hash.remove(state.toString());
            C.put(state.toString(), state);
            ArrayList<Point> emptyCells = findEmptyCells();  // have to be a list of one or two cells
            if (emptyCells.size() == 2) {  // two empty cells
                Point first = emptyCells.get(0);
                Point second = emptyCells.get(1);
                if (Math.abs(first.getI()-second.getI())==1 && first.getJ() == second.getJ()) {  // one below the other
                    for (char c : lr) {
                        temp = state.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                        if (CheckAndAdd()) return;  // Done (found the goal)
                    }
                }
                if (first.getI() == second.getI() && Math.abs(first.getJ()-second.getJ())==1) {  // one next the other
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
            if (isGoal(temp.getBoard())) {
                state = temp;
                return true;
            }
            L_queue.add(temp);
            L_hash.put(temp.toString(), temp);
        }
        return false;
    }

    /**
     * finds the empty cells in state's board.
     * returns a list of points (pairs in the form (i,j)).
     * @return
     */
    private ArrayList<Point> findEmptyCells() {
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[0].length; j++) {
                if (state.getBoard()[i][j] == 0) {  // find an empty cell
                    result.add(new Point(i,j));  // add it to the result list
                }
            }
        }
        return result;
    }

    /**
     * checks if a given matrix equals to the goal (private field of the class)
     * @param board = the given matrix (some board of a new state returned from 'operator')
     * @return false if it's noe the goal, true else.
     */
    public boolean isGoal(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != goal_matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Node getState() {
        return state;
    }

    public static void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }

    public void print_openList(int i) {
        System.out.println("Open-List -- Iteration "+i);
        for (String key : L_hash.keySet()) {
            System.out.println(key);
        }
        System.out.println("_________________");
    }

    public static void print_actions(ArrayList<Character> list) {
        System.out.println("prevActions:");
        for (char c : list) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void print_organs(ArrayList<String> list) {
        System.out.println("prevOrgans:");
        for (String s : list) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
