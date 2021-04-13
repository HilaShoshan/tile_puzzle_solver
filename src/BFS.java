import java.util.concurrent.ArrayBlockingQueue;
import java.util.Queue;
import java.util.*;

public class BFS extends Algorithm {

    private Node state, goal_state;

    public BFS(InitGame game) {  // maybe change the given param!!
        this.state = new Node(game.getStart_state());
        this.goal_state = new Node(game.getGoal_state());
    }

    public void run() {
        Queue<Node> L_queue = new ArrayBlockingQueue<Node>(0);  // what is its size??
        Hashtable<Integer, Node> L_hash = new Hashtable();  // parallel to the queue, in order to find organs in O(1)
        Hashtable<Integer, Node> C = new Hashtable();
        Node n;
        while (!L_queue.isEmpty()) {
            n = L_queue.peek();  // returns the element at the head the queue
            C.put(n.getID(), n);
        }
    }
}
