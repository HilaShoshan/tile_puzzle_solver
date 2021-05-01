import java.util.ArrayList;

public class Node {

    private int[][] board;
    private ArrayList<Node> path = new ArrayList<>();  // the path to it (not including itself)
    private int cost = 0;

    private ArrayList<Character> prevActions = new ArrayList<Character>();  // the actions we made
    private ArrayList<String> prevOrgans = new ArrayList<String>();  // to what organs (numbers) we did it.

    public Node(int[][] board) {
        this.board = board;
    }

    public Node(int[][] board, int cost, ArrayList<Node> path) {
        this.board = board;
        this.cost = cost;
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
                y = j + 1;
                break;
            case 'u':
                x = i + 1;
                y = j;
                break;
            case 'r':
                x = i;
                y = j - 1;
                break;
            case 'd':
                x = i - 1;
                y = j;
                break;
            default:
                return null;
        }
        int[][] new_board = move(i, j, x, y, c, this.board);
        if (new_board == null) return null;
        this.prevActions.add(c);
        this.prevOrgans.add(Integer.toString(board[x][y]));
        Node newNode = new Node(new_board, cost+5, path);  // the cost for moving one value is 5
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
    private int[][] move(int i, int j, int x, int y, char action, int[][] board) {
        int[][] new_board = new int[board.length][board[0].length];  // create a new board
        if (x >= board.length || y >= board[0].length || x <= 0 || y <= 0)  // exceeding the matrix limits
            return null;
        if (board[x][y] == 0)  // is empty cell too
            return null;  // we can not really move it to the empty place (dont change the board)
        String last_organs = prevOrgans.get(prevOrgans.size()-1);
        if (Integer.toString(board[x][y]).equals(last_organs) && isContrary(action))  // opposite action on one organ
            return null;
        if (last_organs.contains("&")) {  // the last action was moving two organs together
            String[] organs = last_organs.split("&");
            int organ1 = Integer.parseInt(organs[0]);
            int organ2 = Integer.parseInt(organs[1]);
            if ((organ1 == board[x][y] || organ2 == board[x][y]) && isContrary(action)) {  // opposite action on one from two organs
                return null;
            }
        }
        for (int row = 0; row < board.length; x++)
            new_board[row] = board[row].clone();  // copy the board to the new_board
        new_board[i][j] = board[x][y];  // move the organ to it's new place
        new_board[x][y] = 0;  // the old cell is empty now
        return new_board;
    }

    private int[][] move2(int i1, int j1, int x1, int y1, int i2, int j2, int x2, int y2, char action) {
        String str1 = Integer.toString(board[x1][y1])+'&'+Integer.toString(board[x2][y2]);
        String str2 = Integer.toString(board[x2][y2])+'&'+Integer.toString(board[x1][y1]);
        String last_organs = prevOrgans.get(prevOrgans.size()-1);
        if ((str1.equals(last_organs) || str2.equals(last_organs)) && isContrary(action))
            return null;
        int[][] new_board = move(i1, j1, x1, y1, action, this.board);
        if (new_board == null) return null;
        new_board = move(i2, j2, x2, y2, action, new_board);
        return new_board;
     }

    private boolean isContrary(char action) {
        char lastAction = prevActions.get(prevActions.size()-1);
        if ((action == 'l' && lastAction == 'r')
            || (action == 'u' && lastAction == 'd')
            || (action == 'r' && lastAction == 'l')
            || (action == 'd' && lastAction == 'u'))
            return true;
        return false;
    }

    /**
     * Move two adjacent organs according to the action received.
     * @param c = the action to do.
     * @param i1 = the row of the first empty cell
     * @param j1 = the column of the first empty cell
     * @param i2 = the row of the second empty cell
     * @param j2 = the column of the second empty cell
     * @return a new node that represents the new state, or null in case that it's impossible.
     */
    public Node operator(char c, int i1, int j1, int i2, int j2) {
        int x1, y1, x2, y2;
        int costToAdd;
        switch (c) {
            case 'l':
                x1 = i1;
                y1 = j1+1;
                x2 = i2;
                y2 = j2+1;
                costToAdd = 6;
                break;
            case 'u':
                x1 = i1+1;
                y1 = j1;
                x2 = i2+1;
                y2 = j2;
                costToAdd = 7;
                break;
            case 'r':
                x1 = i1;
                y1 = j1-1;
                x2 = i2;
                y2 = j2-1;
                costToAdd = 6;
                break;
            case 'd':
                x1 = i1-1;
                y1 = j1;
                x2 = i2-1;
                y2 = j2;
                costToAdd = 7;
                break;
            default:
                return null;
        }
        int[][] new_board = move2(i1, j1, x1, y1, i2, j2, x2, y2, c);
        if (new_board == null) return null;
        this.prevActions.add(c);
        this.prevOrgans.add(Integer.toString(board[x1][y1])+'&'+Integer.toString(board[x2][y2]));
        Node newNode = new Node(new_board, cost+costToAdd, path);
        newNode.addToPath(this);
        return newNode;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
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

    public ArrayList<Character> getPrevActions() {
        return prevActions;
    }

    public ArrayList<String> getPrevOrgans() {
        return prevOrgans;
    }
}
