import java.util.*;

public interface Algorithm {

    public static char[] operators = new char[]{'L', 'U', 'R', 'D'};
    public static char[] lr = new char[]{'L', 'R'};  // first left then right
    public static char[] ud = new char[]{'U', 'D'};  // first up then down

    public static String[] OPERATORS = new String[]{"LL", "UU", "RR", "DD", "L", "U", "R", "D"};

    public void run();
    public Node getState();
}
