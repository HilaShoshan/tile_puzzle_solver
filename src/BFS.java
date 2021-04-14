import java.util.*;

public class BFS extends Algorithm {

    private Node state, temp, goal_state;

    public BFS(InitGame game) {  // maybe change the given param!!
        this.state = new Node(game.getStart_state());
        this.goal_state = new Node(game.getGoal_state());
    }

    public int[] run() {
        Queue<Node> L_queue = new LinkedList<>();
        Set<Node> L_hash = new HashSet<>();  // parallel to the queue, in order to find organs in O(1)
        Set<Node> C = new HashSet<>();  // closeList
        L_queue.add(state);
        L_hash.add(state);
        while (!L_queue.isEmpty()) {
            state = L_queue.peek();  // returns the element at the head the queue
            C.add(state);
            for (int i = 0; i < state.getBoard().length; i++)
                for (int j = 0; j < state.getBoard()[0].length; j++)
                    for (char c : operators) {
                        temp = state.operator(c, i, j);
                        if (temp != null && !C.contains(temp) && !L_hash.contains(temp)) {
                            if (isGoal(temp.getBoard())) return temp.getPath();
                            L_queue.add(temp);
                            L_hash.add(temp);
                        }
                    }
        }
        return null;  // return false....
    }

    public boolean isGoal(int[][] board) {
        return false;
    }
}
