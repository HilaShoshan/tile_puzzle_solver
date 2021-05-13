import java.util.Comparator;

/**
 * A class that implementing a Node comparator, which compare nodes by their f values, and their creation time.
 * A "greater" node is the one with the greater f value.
 * If both node have the same f value, the node with greater ID is the "greater".
 */
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
