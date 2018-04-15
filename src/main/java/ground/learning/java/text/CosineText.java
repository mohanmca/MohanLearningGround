package ground.learning.java.text;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.text.similarity.CosineSimilarity;

import static java.util.stream.Collectors.*;

public class CosineText {

	static String record = "The package descriptions in the JavaDoc give an overview of the available features and various project reports are provided.";
	static String duplicate_record = "The  descriptions of the package in the JavaDoc give an overview of features and various project reports are provided.";

	public static void main(String[] args) {
		System.out.println("starting");
		Map<CharSequence, Integer> r1 = Arrays.stream(record.split(" "))
				.collect(groupingBy(Function.identity(), summingInt(x -> 1)));
		Map<CharSequence, Integer> r2 = Arrays.stream(duplicate_record.split(" "))
				.collect(groupingBy(Function.identity(), summingInt(x -> 1)));
		CosineSimilarity similarity = new CosineSimilarity();
		Object t = similarity.cosineSimilarity(r1, r2);
		System.out.println(t);  
	} 

}
