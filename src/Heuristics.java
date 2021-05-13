import java.util.HashMap;

public class Heuristics {

    public static int ManhattanDistance2D(int[][] state) {
        int result = 0;
        HashMap<Integer, Point> goalMap = new HashMap<>();
        for (int i = 0; i < Ex1.GOAL.length; i++) {
            for (int j = 0; j < Ex1.GOAL[0].length; j++) {
                goalMap.put(Ex1.GOAL[i][j], new Point(i,j));
            }
        }
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) continue;
                result += ManhattanDistancePoints(new Point(i, j), goalMap.get(state[i][j]));
            }
        }
        return result;
    }

    private static int ManhattanDistancePoints(Point p1, Point p2) {
        return Math.abs(p1.getI()-p2.getI()) + Math.abs(p1.getJ()-p2.getJ());
    }
}
