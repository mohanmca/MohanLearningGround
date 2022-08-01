package ground.java;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CompareVersion {
    public int compareVersion(String version1, String version2) {
        List<Integer> l1 = Arrays.stream(version1.split("\\.")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> l2 = Arrays.stream(version2.split("\\.")).map(Integer::parseInt).collect(Collectors.toList());
        return compareVersion(l1, l2);
    }

    public int compareVersion(List<Integer> version1, List<Integer> version2) {
        if(version1.isEmpty() && version2.isEmpty()) return 0;
        if(version1.isEmpty()) return -1;
        if(version2.isEmpty()) return 1;

        int cmp = version1.get(0) - version2.get(0);
        if(cmp!=0) return cmp;
        version1.remove(0);
        version2.remove(0);
        return compareVersion(version1, version2);
    }

    public static void main(String[] args) {
        System.out.println(new CompareVersion().compareVersion("1.0.1", "1.1"));
    }

}
