import java.lang.reflect.Array;
import java.util.*;

public class BFS implements Algorithm {

    private Node state, temp, goal_state;
    private int NUM = 0;

    // data structures for the algorithm
    private Queue<Node> L_queue = new LinkedList<>();
    private HashMap<int[][], Node> L_hash = new HashMap<>(); // parallel to the queue, in order to find organs in O(1)
    private HashMap<int[][], Node> C = new HashMap<>();  // closeList

    public BFS(InitGame game) {  // maybe change the given param!!
        this.state = new Node(game.getStart_state());
        this.goal_state = new Node(game.getGoal_state());
    }

    @Override
    public void run() {
        L_queue.add(state);
        L_hash.put(state.getBoard(), state);
        while (!L_queue.isEmpty()) {
            state = L_queue.remove();  // remove and return the element at the head the queue
            C.put(state.getBoard(), state);
            ArrayList<Point> emptyCells = findEmptyCells();  // have to be a list of one or two cells
            if (emptyCells.size() == 2) {  // two empty cells
                Point first = emptyCells.get(0);
                Point second = emptyCells.get(1);
                if (Math.abs(first.getI()-second.getI())==1 && first.getJ() == second.getJ()) {  // one below the other
                    for (char c : lr) {
                        temp = state.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                        if (temp != null) NUM++;
                        if (Check()) return;  // Done (found the goal)
                    }
                }
                if (first.getI() == second.getI() && Math.abs(first.getJ()-second.getJ())==1) {  // one next the other
                    for (char c : ud) {
                        temp = state.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                        if (temp != null) NUM++;
                        if (Check()) return;
                    }
                }
            }
            for (Point p : emptyCells) {
                for (char c : operators) {  // check moving one item
                    temp = state.operator(c, p.getI(), p.getJ());
                    /*System.out.println("state");
                    print_matrix(state.getBoard());
                    print_actions(state.getPrevActions());
                    print_organs(state.getPrevOrgans());*/
                    if (temp != null) {
                        NUM++;
                        /*System.out.println("temp");
                        print_matrix(temp.getBoard());
                        print_actions(temp.getPrevActions());
                        print_organs(temp.getPrevOrgans());*/
                    }
                    /*else {
                        System.out.println("temp\nNULL");
                    }
                    System.out.println("_____________________________________________");*/

                    // System.out.println(NUM);
                    if (Check()) return;
                }
            }
        }
    }

    private boolean Check() {
        if (temp != null && !C.containsKey(temp.getBoard()) && !L_hash.containsKey(temp.getBoard())) {
            if (isGoal(temp.getBoard())) {
                state = temp;
                return true;
            }
            L_queue.add(temp);
            L_hash.put(temp.getBoard(), temp);
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

    public boolean isGoal(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != goal_state.getBoard()[i][j]) {
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

    @Override
    public int getNUM() {
        return NUM;
    }

    public static void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
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
