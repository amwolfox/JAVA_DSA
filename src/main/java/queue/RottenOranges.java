package queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class RottenOranges {
    public static void main(String[] args) {
        Queue<Integer> q = new ArrayDeque<Integer>();
        q.add(1);
        q.remove();
        System.out.println(q.peek());
    }
}
