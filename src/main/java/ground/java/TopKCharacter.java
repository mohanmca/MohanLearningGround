package ground.java;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TopKCharacter {
    public static void main(String[] args) {
        String[] words = generateStrings(20, 8);

        Map<Character, Long> result = Arrays.stream(words)
                .flatMap(word -> word.chars().mapToObj(c -> new Character((char) c)))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        List<Map.Entry<Character, Long>> values = new ArrayList<>(result.entrySet());
        Comparator<Map.Entry<Character, Long>> comp = (e1, e2) -> {
            return (int) (e2.getValue() - e1.getValue());
        };

        Collections.sort(values, comp);

        for (int i = 0; i < 3; i++) {
            System.out.println(values.get(i).getKey() + "-" + values.get(i).getValue());
        }
    }

    private static String[] generateStrings(int n, int len) {
        Random random = new Random();
        String[] words = IntStream.rangeClosed(1, n).mapToObj(i -> randomString(random, len)).toArray(String[]::new);
        System.out.println(Arrays.deepToString(words));
        return words;
    }

    private static String randomString(Random rand, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (rand.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }
}
