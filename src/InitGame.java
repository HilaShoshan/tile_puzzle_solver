import javax.swing.*;
import java.io.*;
import java.util.Arrays;

/**
 * This class initializes the game board and other features of the game.
 * All information is collected from "input.txt" file.
 * In the constructor, we read the input file and save all the relevant attributes in private fields.
 * It is assumed that the input file is correct.
 */
public class InitGame {

    private String algorithm;  // which algorithm to use
    private Boolean time;  // whether to print the run time or not
    private Boolean open;  // whether to print the "open list" at each phase the algorithm runs or not
    private int N, M;  // the size of the board (N = num of rows, M = num of columns).
    private int start_state[][], goal_state[][];  // The initial state and the final state of the board.

    // constructor
    public InitGame() throws Exception {
        File file = new File("input.txt");  // read input.txt
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int row = 1;
        while ((line = br.readLine()) != null) {
            switch (row) {
                case 1:
                    this.algorithm = line;
                    break;
                case 2:
                    if (line.charAt(0) == 'w')  // "with time"
                        this.time = true;
                    else  // "no time"
                        this.time = false;
                    break;
                case 3:
                    if (line.charAt(0) == 'w')  // "with open"
                        this.open = true;
                    else  // "no open"
                        this.open = false;
                    break;
                case 4:  // in the form of NxM (rows x cols)
                    String[] size = line.split("x");
                    this.N = Integer.parseInt(size[0]);
                    this.M = Integer.parseInt(size[1]);
                    this.start_state = new int[N][M];
                    this.goal_state = new int[N][M];
                    break;
                default:
                    int i = row - 5;  // i represents the index of the row on the start_state matrix we want to fill.
                    if(i < N) fill_matrix(line, i, "s");
                    else if(i > N) {  // i == N is the "Goal state" row
                        int x = i - N - 1;  // x represents the index of the row on goal_state matrix
                        fill_matrix(line, x, "g");
                    }
            }
            row++;
        }
    }

    private void fill_matrix(String line, int i, String s) {
        // System.out.println(" i  =  " + i);
        String[] numbers = line.split(",");  // get an array of all the numbers on this row of the board
        for (int j = 0; j < M; j++) {  // fill the columns
            if (!numbers[j].equals("_")) {  // a number
                int num = Integer.parseInt(numbers[j]);
                if (s.equals("s"))  // need to fill start_state matrix
                    start_state[i][j] = num;
                else  // s is "g", need to fill goal_state matrix
                    goal_state[i][j] = num;
            }  // if it's a "_", will remain 0 on matrix[i][j]
        }
        /*if (s.equals("s")) print_matrix(start_state);
        else print_matrix(goal_state);*/
    }

    private void print_matrix(int[][] mat) {
        for (int[] row : mat)
            System.out.println(Arrays.toString(row));
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Boolean getTime() {
        return time;
    }

    public void setTime(Boolean time) {
        this.time = time;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int[][] getStart_state() {
        return start_state;
    }

    public void setStart_state(int[][] start_state) {
        this.start_state = start_state;
    }

    public int[][] getGoal_state() {
        return goal_state;
    }

    public void setGoal_state(int[][] goal_state) {
        this.goal_state = goal_state;
    }
}
