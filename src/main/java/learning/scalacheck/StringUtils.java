package learning.scalacheck;

import java.util.Arrays;

public class StringUtils {

	public static String truncate(String s, int n) {
		if (n < 0)
			return "";
		if (s.length() <= n)
			return s;
		else
			return s.substring(0, n) + "...";
	}

	public static boolean contains(String content, String substr) {
		return (content.indexOf(substr) != -1);
	}

	public static boolean isPalindrome(String content) {
		boolean result = true;
		Integer length = content.length() - 1;
		for (int i = 0; i <= length; i++) {
			result = result && (content.charAt(i) == content.charAt(length - i));
		}
		return result;
	}

	public static java.util.List<String> tokenize(String content, char chard) {
		return Arrays.asList(content.split(chard + ""));
	}

}
