import java.util.ArrayList;

public class Node {

    private int ID = 0;
    private int[][] board;
    private ArrayList<Node> path = new ArrayList<>();
    private int cost = 0;

    public Node(int[][] board) {
        this.board = board;
    }

    public Node(int[][] board, int cost, int id, ArrayList<Node> path) {
        this.board = board;
        this.cost = cost;
        this.ID = id;
        this.path = path; 
    }

    public void addToPath(Node n) {
        this.path.add(n); 
    }

    /**
     * Move the number on (i,j) location according to the action received (left/up/right/down) if it's possible.
     * @param c is a char representing the action to do.
     *          Can be one of: 'l'=left | 'u'=up | 'r'=right | 'd'=down
     * @param i is the row of the organ in the matrix we want to move.
     * @param j is the column of it.
     * @return a new node that represents the new state,
     * or null if it's impossible (exceeding the matrix limits or non-empty cell where you want to move the node).
     */
    public Node operator(char c, int i, int j) {
        int new_i, new_j;
        int[][] new_board = new int[board.length][board[0].length];  // create a new board
        switch (c) {
            case 'l':
                new_i = i;
                new_j = j-1;
                break;
            case 'u':
                new_i = i-1;
                new_j = j;
                break;
            case 'r':
                new_i = i;
                new_j = j+1;
                break;
            case 'd':
                new_i = i+1;
                new_j = j;
                break;
            default:
                return null;
        }
        new_board = move(i, j, new_i, new_j, new_board);
        if (new_board == null) return null;
        Node newNode = new Node(new_board, cost+5, ID+1, path);  // the cost for moving one value is 5
        newNode.addToPath(this);
        return newNode;
    }

    private int[][] move(int i, int j, int new_i, int new_j, int[][] new_board) {
        if (i >= board.length || j >= board[0].length)  // exceeding the matrix limits
            return null;
        if (board[new_i][new_j] != 0)  // not an empty cell --> can't move
            return null;
        for (int x = 0; x < board.length; x++)
            new_board[x] = board[x].clone();  // copy the board to the new_board
        new_board[new_i][new_j] = board[i][j];  // move the organ to it's new place
        new_board[i][j] = 0;  // the old cell is empty now
        return new_board;
    }

    /**
     * Move two nodes (this node + node n) according to the action received.
     * @param n is another node which is a neighbor of the this node in the board.
     * @param c is a char that can be one from the 4 characters mentioned in the previous function.
     * @param i organ's row.
     * @param j organ's column.
     * @return a new node that represents the new state, or null in case that it's impossible.
     */
    public Node operator(Node n, char c, int i, int j) {

        return null;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    public void setPath(ArrayList<Node> path) {
        this.path = path;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
