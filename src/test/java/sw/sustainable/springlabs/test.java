package sw.sustainable.springlabs;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class test {

    @Test
    public void test() {
        Deque<Integer> q =  new ArrayDeque<>();
        q.add(1);
        q.add(2);
        q.add(3);

        System.out.println(q.pollLast());
        q.addFirst(3);
        System.out.println(q.poll());

    }
}
