import java.util.Arrays;
import java.util.stream.*;

class Test {
  public static void main(String[] args) {
    var colors = Arrays.asList("red", "green", "blue", "yellow");
    java.util.Collections.reverse(colors).forEach(System.out::println);  
  }
}
