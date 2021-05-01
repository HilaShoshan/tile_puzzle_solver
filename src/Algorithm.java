import java.util.*;

public interface Algorithm {

    public char[] operators = new char[]{'l', 'u', 'r', 'd'};
    public char[] lr = new char[]{'l', 'r'};  // first left then right
    public char[] ud = new char[]{'u', 'd'};  // first up then down

    public void run();
    public Node getState();
    public int getNUM();
}
