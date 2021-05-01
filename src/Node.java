import java.util.ArrayList;
import java.util.Arrays;

public class Node {

    private int[][] board;
    private ArrayList<Node> path = new ArrayList<>();  // the path to it (not including itself)
    private int cost = 0;
    private ArrayList<Character> prevActions = new ArrayList<Character>();  // the actions we made
    private ArrayList<String> prevOrgans = new ArrayList<String>();  // to what organs (numbers) we did it.

    public Node(int[][] board) {
        this.board = board;
    }

    public Node(int[][] board, int cost, ArrayList<Node> path, ArrayList<Character> prevActions, ArrayList<String> prevOrgans) {
        this.board = board;
        this.cost = cost;
        this.path = path;
        this.prevActions = prevActions;
        this.prevOrgans = prevOrgans;
    }

    public void addToPath(Node n) {
        this.path.add(n); 
    }

    public void addPrevAction(char c) {
        this.prevActions.add(c);
    }

    public void addPrevOrgan(String s) {
        this.prevOrgans.add(s);
    }

    /**
     * Move the numbers around the empty cell (i,j) according to the action received (left/up/right/down) if it's possible.
     * @param c is a char representing the action to do.
     *          Can be one of: 'L'=left | 'U'=up | 'R'=right | 'D'=down
     * @param i is the row of the empty cell.
     * @param j is the column of it.
     * @return a new node that represents the new state,
     * or null if it's impossible (exceeding the matrix limits).
     */
    public Node operator(char c, int i, int j) {
        int x, y;  // the location of the organ on matrix we want to move
        switch (c) {
            case 'L':
                x = i;
                y = j + 1;
                break;
            case 'U':
                x = i + 1;
                y = j;
                break;
            case 'R':
                x = i;
                y = j - 1;
                break;
            case 'D':
                x = i - 1;
                y = j;
                break;
            default:
                return null;
        }
        int[][] new_board = move(i, j, x, y, c, this.board);
        if (new_board == null) return null;
        Node newNode = new Node(new_board, cost+5, copyPath(path), copyActions(prevActions), copyOrgans(prevOrgans));
                                // the cost for moving one value is 5
        newNode.addPrevAction(c);
        newNode.addPrevOrgan(Integer.toString(board[x][y]));
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
        if (!prevActions.isEmpty() && isContrary(Integer.toString(board[x][y]), action))  // it's not the first action and it's Contrary
            return null;
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[0].length; col++)
                new_board[row][col] = board[row][col];  // copy the board to the new_board
        new_board[i][j] = board[x][y];  // move the organ to it's new place
        new_board[x][y] = 0;  // the old cell is empty now
        return new_board;
    }

    private boolean isContrary(String number, char action) {
        String last_organs = prevOrgans.get(prevOrgans.size()-1);
        if (number.equals(last_organs) && isContraryAction(action))  // opposite action on one organ
            return true;
        if (last_organs.contains("&")) {  // the last action was moving two organs together
            String[] organs = last_organs.split("&");
            if ((organs[0].equals(number) || organs[1].equals(number)) && isContraryAction(action)) {  // opposite action on one from two organs
                return true;
            }
        }
        return false;
    }

    private boolean isContraryAction(char action) {
        char lastAction = prevActions.get(prevActions.size()-1);
        if ((action == 'L' && lastAction == 'R')
            || (action == 'U' && lastAction == 'D')
            || (action == 'R' && lastAction == 'L')
            || (action == 'D' && lastAction == 'U'))
            return true;
        return false;
    }

    private int[][] move2(int i1, int j1, int x1, int y1, int i2, int j2, int x2, int y2, char action) {
        String str1 = Integer.toString(board[x1][y1])+'&'+Integer.toString(board[x2][y2]);
        String str2 = Integer.toString(board[x2][y2])+'&'+Integer.toString(board[x1][y1]);
        String last_organs = prevOrgans.get(prevOrgans.size()-1);
        if ((str1.equals(last_organs) || str2.equals(last_organs)) && isContraryAction(action))
            return null;
        int[][] new_board = move(i1, j1, x1, y1, action, this.board);
        if (new_board == null) return null;
        new_board = move(i2, j2, x2, y2, action, new_board);
        return new_board;
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
            case 'L':
                x1 = i1;
                y1 = j1+1;
                x2 = i2;
                y2 = j2+1;
                costToAdd = 6;
                break;
            case 'U':
                x1 = i1+1;
                y1 = j1;
                x2 = i2+1;
                y2 = j2;
                costToAdd = 7;
                break;
            case 'R':
                x1 = i1;
                y1 = j1-1;
                x2 = i2;
                y2 = j2-1;
                costToAdd = 6;
                break;
            case 'D':
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
        Node newNode = new Node(new_board, cost+costToAdd, copyPath(path), copyActions(prevActions), copyOrgans(prevOrgans));
        newNode.addPrevAction(c);
        newNode.addPrevOrgan(Integer.toString(board[x1][y1])+'&'+Integer.toString(board[x2][y2]));
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

    public ArrayList<Character> copyActions(ArrayList<Character> actions) {
        ArrayList<Character> newActions = new ArrayList<>();
        for (char c : actions)
            newActions.add(c);
        return newActions;
    }

    public ArrayList<String> copyOrgans(ArrayList<String> organs) {
        ArrayList<String> newOrgans = new ArrayList<>();
        for (String s : organs)
            newOrgans.add(s);
        return newOrgans;
    }

    public ArrayList<Node> copyPath(ArrayList<Node> path) {  // maybe should deep copy the Node itself too!!
        ArrayList<Node> newPath = new ArrayList<>();
        for (Node n : path)
            newPath.add(n);
        return newPath;
    }

    public static void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }
}
