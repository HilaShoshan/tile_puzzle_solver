import java.util.ArrayList;
import java.util.Iterator;

public class Tester {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);

        Iterator<Integer> itr = list.iterator();
        int i;

        while (itr.hasNext()) {
            i = itr.next();
            if (i == 2) {
                itr.remove();
                System.out.println(list);
                while (itr.hasNext()) {
                    itr.next();
                    itr.remove();  // remove g and all the nodes after it from children
                }
            }
        }
        System.out.println(list);
    }
}
