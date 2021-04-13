public class Node {

    private int ID;
    private int[][] state;
    // path
    // cost

    public Node(int[][] state) {
        this.state = state;
    }

    /**
     * Move the node according to the action received (left/up/right/down) if it's possible.
     * @param c is a char representing the action to do.
     *          Can be one of: 'l'=left | 'u'=up | 'r'=right | 'd'=down
     * @return a new node that represents the new state,
     * or null if it's impossible (exceeding the matrix limits or non-empty cell where you want to move the node),
     */
    public Node operator(char c) {
        return null;
    }

    /**
     * Move two nodes (this node + node n) according to the action received.
     * @param n is another node which is a neighbor of the this node in the board.
     * @param c is a char that can be one from the 4 characters mentioned in the previous function.
     * @return a new node that represents the new state, or null in case that it's impossible.
     */
    public Node operator(Node n, char c) {
        return null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }
}
