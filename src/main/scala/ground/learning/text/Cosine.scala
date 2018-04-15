//package ground.learning.text
//import java.util.Arrays;
//import java.util.Map;
//import java.util.function.Function;
//import org.apache.commons.text.similarity.CosineSimilarity;
//import java.util.stream.Collectors._;
//
//object Cosine extends App {
//  val record = "The package descriptions in the JavaDoc give an overview of the available features and various project reports are provided.";
//  val duplicate_record = "The  descriptions of the package in the JavaDoc give an overview of features and various project reports are provided.";
//
//  System.out.println("starting");
//  val r1 = Arrays.stream(record.split(" ")).collect(groupingBy(Function.identity(), summingInt(x => 1)));
//  val r2 = Arrays.stream(duplicate_record.split(" ")).collect(groupingBy(Function.identity(), summingInt(x => 1)));
//  val similarity = new CosineSimilarity();
//  val t = similarity.cosineSimilarity(r1, r2);
//  System.out.println(t);
//
//}