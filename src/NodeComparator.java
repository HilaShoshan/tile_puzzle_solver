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
        if (g1+h1 < g2+h2) return -1;
        else if (g1+h1 > g2+h2) return 1;
        else  {  // g1+h1 == g2+h2
            if (n1.getID() < n2.getID()) return -1;
            else return 1;  // ID is unique, so it must be n1.ID > n2.ID!
        }
    }
}
