package ground.learning.java.generics;

import java.util.List;

public class SuperExtendsGenerics {
 
	class P {}
 
	class A extends P {}
 
	class B extends A {}
 
	public static void main(String[] args) {
 
		List<A> listOfA = null;
		List<P> listOfP = null;
		List<B> listOfB = null;
 
		//printExtends(listOfP); // The method printExtends(List<? extends A>) in the type Test is not applicable for the arguments (List<P>)
		printExtends(listOfA);
		printExtends(listOfB);
 
		printSuper(listOfP);
		printSuper(listOfA);
		//printSuper(listOfB); // The method printSuper(List<? super A>) in the type Test is not applicable for the arguments (List<B>)
	}
 
	public static void printExtends(List<? extends A> list) {
	}
 
	public static void printSuper(List<? super A> list) {
	}
 
}