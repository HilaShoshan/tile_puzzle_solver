import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that contains all the heuristic functions that can be use on the various algorithms.
 * heuristic says what is the potential to get from the current node to the goal node (an estimation of the cost).
 */
public class Heuristics {

    public static double ManhattanDistance2D(int[][] state) {
        double result = 0;
        HashMap<Integer, Point> goalMap = new HashMap<>();
        for (int i = 0; i < Ex1.GOAL.length; i++) {
            for (int j = 0; j < Ex1.GOAL[0].length; j++) {
                goalMap.put(Ex1.GOAL[i][j], new Point(i,j));
            }
        }
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) continue;  // it's place determines by himself
                result += ManhattanDistancePoints(new Point(i, j), goalMap.get(state[i][j]));
            }
        }
        return result;
    }

    private static double ManhattanDistancePoints(Point p1, Point p2) {
        if (Ex1.NumEmptyCells == 1)
            return 5 * Math.abs(p1.getI()-p2.getI()) + 5 * Math.abs(p1.getJ()-p2.getJ());
        else
            return 3.5 * Math.abs(p1.getI()-p2.getI()) + 3 * Math.abs(p1.getJ()-p2.getJ());
    }
}
