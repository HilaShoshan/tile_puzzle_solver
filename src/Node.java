import java.util.ArrayList;

/**
 * A class that implements a state of the game.
 * Each state is a vertex (Node) on the searching graph, that built during the algorithm running.
 */
public class Node {

    private static int NUM = 0;  // counting the number of nodes created so far.

    private int ID = 0;  // unique ID - smaller ID means that the Node is created first (on lower iteration or "lower" operator at the same iteration).
    private int[][] board;  // the board at this state of game (0 represents an empty cell).
    private int cost = 0;  // the cost from the begging to the current Node (the sum of weights on "edges").
    private double f;  // the f value calculating with the heuristic function at some algorithms.
    private char prevAction = ' ';  // what action we made to get the current state
    private String prevItem = "";  // to what item did we do this
    private Node father = null;  // from which node did we get the current one
    private boolean OUT = false;  // using on IDA* and DFBnB algorithm

    /**
     * A basic constructor (mostly for the starting state)
     */
    public Node(int[][] board) {
        NUM++;
        this.ID = NUM;
        this.board = board;
    }

    /**
     * A constructor that uses to create a child of a node
     */
    public Node(int[][] board, int cost, char prevAction, String prevItem) {
        NUM++;
        this.ID = NUM;
        this.board = board;
        this.cost = cost;
        this.prevAction = prevAction;
        this.prevItem = prevItem;
    }

    /**
     * This method gets as input an emptyCells ArrayList and an operator to do on the board,
     * checks if asking to do a dual-operator, or a single one, and whether it is possible.
     * @param emptyCells = an ArrayList with the empty cells on the current board.
     * @param index = the index that we should look at on the emptyCells list.
     *              [meaningless if a dual-operator has asked to be performed].
     * @param operator_str = a string that represents the operator we want to perform (from OPERATORS array on Algorithm interface)
     * @return null if it's impossible to perform this operator on this board, else returns the (child) Node
     * that operator method returns.
     */
    public Node doOperator(ArrayList<Point> emptyCells, int index, String operator_str) {
        if (operator_str.length() == 2 && emptyCells.size() != 2) return null;
        char operator_ch = operator_str.charAt(0);
        if (index == 1 && operator_str.length() == 2) return null;
        if (operator_str.length() == 2) {  // the index is 0 (for doing the dual operator one time)!
            Point first = emptyCells.get(0);
            Point second = emptyCells.get(1);
            switch (operator_ch) {
                case 'L':
                case 'R': {
                    if (HelperFunctions.isAbove(first, second)) {  // one below the other
                        return this.operator(operator_ch, first.getI(), first.getJ(), second.getI(), second.getJ());
                    } else return null;
                }
                case 'U':
                case 'D': {
                    if (HelperFunctions.isNext(first, second)) {  // one next the other
                        return this.operator(operator_ch, first.getI(), first.getJ(), second.getI(), second.getJ());
                    } else return null;
                }
                default:
                    return null;
            }
        }  // else ..
        Point p = emptyCells.get(index);  // take the point we want to work on now
        return this.operator(operator_ch, p.getI(), p.getJ());
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
        int x, y;  // the location of the item on matrix we want to move
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
        Node newNode = new Node(new_board, cost+5, c, Integer.toString(board[x][y]));  // the cost for moving one-element is 5
        newNode.setFather(this);
        return newNode;
    }

    /**
     * move (if possible) an item to the empty cell on the board.
     * @param i = the row of the empty cell (to move there)
     * @param j = the column of the empty cell
     * @param x = the row of the item we want to move (some item around the empty cell)
     * @param y = the column of the item we want to move
     * @return the new board or null if it's impossible
     */
    private int[][] move(int i, int j, int x, int y, char action, int[][] board) {
        int[][] new_board = new int[board.length][board[0].length];  // create a new board
        if (x >= board.length || y >= board[0].length || x < 0 || y < 0) // exceeding the matrix limits
            return null;
        if (board[x][y] == 0)  // is empty cell too
            return null;  // we can not really move it to the empty place (dont change the board)
        if (prevAction!=' ' && isContrary(Integer.toString(board[x][y]), action))  // it's not the first action and it's Contrary
            return null;
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[0].length; col++)
                new_board[row][col] = board[row][col];  // copy the board to the new_board
        new_board[i][j] = board[x][y];  // move the item to it's new place
        new_board[x][y] = 0;  // the old cell is empty now
        return new_board;
    }

    /**
     * A helper function that checks if a action we want to perform is contrary (opposite) to the previous one.
     * uses 'isContraryAction' function too
     * @param number = the number we want to move (the value of it)
     * @param action = a char that represents the action we want to perform on the number.
     * @return true if the action is contrary, else returns false.
     */
    private boolean isContrary(String number, char action) {
        if (number.equals(prevItem) && isContraryAction(action))  // opposite action on one item
            return true;
        if (prevItem.contains("&")) {  // the last action was moving two items together
            String[] items = prevItem.split("&");
            if ((items[0].equals(number) || items[1].equals(number)) && isContraryAction(action)) {  // opposite action on one from two items
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given action is contrary to the previous action (as action only - no matter the number we want to move).
     * @param action = a char that represents the action.
     * @return true if it's contrary, else returns false.
     */
    private boolean isContraryAction(char action) {
        if ((action == 'L' && prevAction == 'R')
            || (action == 'U' && prevAction == 'D')
            || (action == 'R' && prevAction == 'L')
            || (action == 'D' && prevAction == 'U'))
            return true;
        return false;
    }

    /**
     * Move two adjacent items according to the action received.
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
        Node newNode = new Node(new_board, cost+costToAdd, c, Integer.toString(board[x1][y1])+'&'+Integer.toString(board[x2][y2]));
        newNode.setFather(this);
        return newNode;
    }

    /**
     * try to move (x1,y1) to (i1,j1) and (x2,y2) to (i2,j2), according to the action receives.
     * @return the new board or null if it's impossible.
     */
    private int[][] move2(int i1, int j1, int x1, int y1, int i2, int j2, int x2, int y2, char action) {
        if (x1 >= board.length || y1 >= board[0].length || x1 < 0 || y1 < 0
            || x2 >= board.length || y2 >= board[0].length || x2 < 0 || y2 < 0) // exceeding the matrix limits
            return null;
        String str1 = Integer.toString(board[x1][y1])+'&'+Integer.toString(board[x2][y2]);
        String str2 = Integer.toString(board[x2][y2])+'&'+Integer.toString(board[x1][y1]);
        String last_items;
        if (prevAction != ' ') {
            if ((str1.equals(prevItem) || str2.equals(prevItem)) && isContraryAction(action))
                return null;
        }
        int[][] new_board = move(i1, j1, x1, y1, action, this.board);
        if (new_board == null) return null;
        new_board = move(i2, j2, x2, y2, action, new_board);
        return new_board;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public static int getNUM() {
        return NUM;
    }

    public char getPrevAction() {
        return prevAction;
    }

    public String getPrevItem() {
        return prevItem;
    }

    public Node getFather() {
        return father;
    }

    public void setFather(Node father) {
        this.father = father;
    }

    public int getID() {
        return ID;
    }

    public boolean isOUT() {
        return OUT;
    }

    public void setOUT(boolean OUT) {
        this.OUT = OUT;
    }

    public String toString() {
        String s = "";
        for (int[] x : board) {
            for (int y : x) {
                s += y + " ";
            }
            s+= "\n";
        }
        return s;
    }
}
