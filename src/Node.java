import java.util.ArrayList;

public class Node {

    private int ID = 0;
    private int[][] board;
    private ArrayList<Node> path = new ArrayList<>();
    private int cost = 0;
    private String prevAction; 

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
     * Move the numbers around the empty cell (i,j) according to the action received (left/up/right/down) if it's possible.
     * @param c is a char representing the action to do.
     *          Can be one of: 'l'=left | 'u'=up | 'r'=right | 'd'=down
     * @param i is the row of the empty cell.
     * @param j is the column of it.
     * @return a new node that represents the new state,
     * or null if it's impossible (exceeding the matrix limits).
     */
    public Node operator(char c, int i, int j) {
        int x, y;  // the location of the organ on matrix we want to move
        switch (c) {
            case 'l':
                x = i;
                y = j+1;
                break;
            case 'u':
                x = i+1;
                y = j;
                break;
            case 'r':
                x = i;
                y = j-1;
                break;
            case 'd':
                x = i-1;
                y = j;
                break;
            default:
                return null;
        }
        int[][] new_board = move(i, j, x, y);
        if (new_board == null) return null;
        Node newNode = new Node(new_board, cost+5, ID+1, path);  // the cost for moving one value is 5
        newNode.addToPath(this);
        return newNode;
    }

    /**
     * move (if possible) the organ to the empty cell on the board
     * @param i = the row of the empty cell (to move there)
     * @param j = the column of the empty cell
     * @param x = the row of the organ we want to move (some organ around the empty cell)
     * @param y = the column of the organ we want to move
     * @return the new board or null if it's impossible
     */
    private int[][] move(int i, int j, int x, int y) {
        int[][] new_board = new int[board.length][board[0].length];  // create a new board
        if (x >= board.length || y >= board[0].length || x <= 0 || y <= 0)  // exceeding the matrix limits
            return null;
        if (board[x][y] == 0)  // is empty cell too
            return null;  // we can not really move it to the empty place (dont change the board)
        for (int row = 0; row < board.length; x++)
            new_board[row] = board[row].clone();  // copy the board to the new_board
        new_board[i][j] = board[x][y];  // move the organ to it's new place
        new_board[x][y] = 0;  // the old cell is empty now
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
