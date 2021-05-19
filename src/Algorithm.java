/**
 * An Algorithm Interface, that the whole 5 algorithm will implement.
 */
public interface Algorithm {

    public static String[] OPERATORS = new String[]{"LL", "UU", "RR", "DD", "L", "U", "R", "D"};

    public void run();
    public Node getState();
    public boolean isNoPath();
}
