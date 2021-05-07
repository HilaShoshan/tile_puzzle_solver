import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    private int[][] goal_matrix;

    public NodeComparator(int[][] goal_matrix) {
        this.goal_matrix = goal_matrix;
    }

    @Override
    public int compare(Node n1, Node n2) {
        int g1 = n1.getCost();
        int g2 = n2.getCost();
        int h1 = Heuristics.ManhattanDistance2D(n1.getBoard(), goal_matrix);
        int h2 = Heuristics.ManhattanDistance2D(n2.getBoard(), goal_matrix);
        if (g1+h1 == g2+h2) return 0;
        if (g1+h1 < g2+h2) return -1;
        return 1;
    }
}
