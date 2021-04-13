import java.util.concurrent.ArrayBlockingQueue;
import java.util.Queue;
import java.util.*;

public class BFS extends Algorithm {

    private Node state, goal_state;

    public BFS(InitGame game) {  // maybe change the given param!!
        this.state = new Node(game.getStart_state());
        this.goal_state = new Node(game.getGoal_state());
    }

    public int[] run() {
        Queue<Node> L_queue = new ArrayBlockingQueue<Node>(0);  // what is its size??
        Set<Node> L_hash = new HashSet<>();  // parallel to the queue, in order to find organs in O(1)
        Set<Node> C = new HashSet<>();
        Node n, g;
        while (!L_queue.isEmpty()) {
            n = L_queue.peek();  // returns the element at the head the queue
            C.add(n);
            for (char c : operators) {
                g = n.operator(c);
                if (g != null && !C.contains(g) && !L_hash.contains(g)) {
                    if (isGoal(g.getState())) return g.getPath();
                    L_queue.add(g);
                    L_hash.add(g);
                }
            }
        }
        return null;  // return false....
    }

    public boolean isGoal(int[][] state) {
        return false;
    }
}
