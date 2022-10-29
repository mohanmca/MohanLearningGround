package ground.learning.java.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RotateValueInListOfObjects {
    public static void main(String[] args) {
        List<Pair<Integer, String>> pairs = new ArrayList(Arrays.asList(Pair.of(1, "one"), Pair.of(2, "two"), Pair.of(3, "three"), Pair.of(4, "four")));
        List<Pair<Integer, String>> original = new ArrayList(Arrays.asList(Pair.of(1, "one"), Pair.of(2, "two"), Pair.of(3, "three"), Pair.of(4, "four")));
        System.out.println(Arrays.deepToString(pairs.toArray(new Pair[0])));
        rotateRight(pairs);
        System.out.println(Arrays.deepToString(pairs.toArray(new Pair[0])));
        rotateLeft(pairs);
        System.out.println(Arrays.deepToString(pairs.toArray(new Pair[0])));
        rotateLeft(original);
        System.out.println(Arrays.deepToString(original.toArray(new Pair[0])));
    }

    private static void rotateRight(List<Pair<Integer, String>> pairs) {
        String temp = pairs.get(0).getSecond();
        for (int i = 1; i < pairs.size(); i++) {
            pairs.get(i - 1).setSecond(pairs.get(i).getSecond());
        }
        pairs.get(pairs.size() - 1).setSecond(temp);
    }

    private static void rotateLeft(List<Pair<Integer, String>> pairs) {
        String temp = pairs.get(pairs.size() - 1).getSecond();
        for (int i = pairs.size() - 2; i >= 0; i--) {
            pairs.get((i + 1) % pairs.size()).setSecond(pairs.get(i).getSecond());
        }
        pairs.get(0).setSecond(temp);
    }
}

class Pair<T, R> {
    T first;
    R second;

    public static <T, R> Pair of(T t, R r) {
        return new Pair(t, r);
    }

    public Pair(T t, R r) {
        this.first = t;
        this.second = r;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(R second) {
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
