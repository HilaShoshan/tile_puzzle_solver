import java.util.*;

/**
 * האלגוריתם משתמש בתור עדיפויות, ומוציא ממנו קודקודים לפי הציון שהם יקבלו
 * הפונקציה של הציון היא f, כאשר ציון טוב = ציון נמוך יותר
 * אלגוריתמים שונים יגדירו את f בצורה שונה
 * f היא פונקציה שמקבלת קודקוד ומגדירה לו מספר.
 * h היא פונקציה שאומרת מה הפוטנציאל להגיע מהקודקוד הנוכחי לקודקוד המטרה, שזה בעצם אומר כמה עולה להגיע מקודקוד מסוים לקודקוד המטרה.
 * g היא פונקציה שאומרת מה העלות להגיע מקודקוד ההתחלה לקודקוד אחר.
 */
public class Astar implements Algorithm {

    private Node state, temp;
    private int[][] goal_matrix;
    private boolean open;

    // data structures for the algorithm
    private PriorityQueue<Node> L_queue;
    private HashMap<String, Node> L_hash = new HashMap<>();
    private HashMap<String, Node> C = new HashMap<>();  // closeList

    public Astar(InitGame game) {
        this.state = new Node(game.getStart_state());
        this.goal_matrix = game.getGoal_state();
        L_queue = new PriorityQueue<Node>(new NodeComparator(goal_matrix));
        this.open = game.getOpen();
        L_queue.add(state);
        L_hash.put(state.toString(), state);
    }

    @Override
    public void run() {
        while (!L_queue.isEmpty()) {
            state = L_queue.remove();
            L_hash.remove(state.toString());
            if (HelperFunctions.isGoal(goal_matrix, state.getBoard()));
            C.put(state.toString(), state);
            ArrayList<Point> emptyCells = HelperFunctions.findEmptyCells(state.getBoard());
            if (emptyCells.size() == 2) {  // two empty cells
                Point first = emptyCells.get(0);
                Point second = emptyCells.get(1);
                char[] operatorsToTry = null;
                if (HelperFunctions.isAbove(first, second)) operatorsToTry = lr;
                else if (HelperFunctions.isNext(first, second)) operatorsToTry = ud;
                if (operatorsToTry != null) {  // the empty cells in the matrix are adjacent to each other.
                    for (char c : operatorsToTry) {
                        temp = state.operator(c, first.getI(), first.getJ(), second.getI(), second.getJ());
                        CheckAndAdd();
                    }
                }
            }
            for (Point p : emptyCells) {
                for (char c : operators) {  // check moving one item
                    temp = state.operator(c, p.getI(), p.getJ());
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
            else if (L_hash.containsKey(temp.toString()) && L_hash.get(temp.toString()).getCost() > temp.getCost()) {
                L_hash.replace(temp.toString(), temp);
            }
        }
    }

    @Override
    public Node getState() {
        return this.state;
    }
}
