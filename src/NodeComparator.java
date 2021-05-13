import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node n1, Node n2) {
        if (n1.getF() < n2.getF()) return -1;
        else if (n1.getF() > n2.getF()) return 1;
        else  {  // f1 == f2
            if (n1.getID() < n2.getID()) return -1;
            else return 1;  // ID is unique, so it must be n1.ID > n2.ID!
        }
    }
}
