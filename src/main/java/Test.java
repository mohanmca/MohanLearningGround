import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Test {
    public int maximumBags(int[] capacity, int[] rocks, int additionalRocks) {

        int[] ncapacity = IntStream.range(0, capacity.length).boxed().sorted(Comparator.comparingInt(i -> (capacity[i] - rocks[i]))).mapToInt(i -> i).toArray();


        int i = 0;
        while (additionalRocks > 0) {
            int required = capacity[ncapacity[i]] - rocks[ncapacity[i]];
            if (required <= additionalRocks) {
                additionalRocks -= required;
                i++;
            } else {
                break;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.maximumBags(new int[]{2, 3, 4, 5}, new int[]{1, 2, 4, 4}, 2);
    }
}
