/**
 * A class that represents a point on a matrix
 * Saves the row and the column of it
 */
public class Point {

    private int i,j;

    /**
     * constructor that gets i and j and determine the fields of the class
     * @param i = the roe in the matrix
     * @param j = the column
     */
    Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
