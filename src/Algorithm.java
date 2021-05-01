import java.util.*;

public interface Algorithm {

    public char[] operators = new char[]{'L', 'U', 'R', 'D'};
    public char[] lr = new char[]{'L', 'R'};  // first left then right
    public char[] ud = new char[]{'U', 'D'};  // first up then down

    public void run();
    public Node getState();
    public int getNUM();
}
