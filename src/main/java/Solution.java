import java.util.*;

class Solution {
    public static void main(String[] args) {
        System.out.println(generateValidParentheses(3));
    }

    static class Value {
        public String p;
        public int open;
        public int close;
        public Value(String p, int open, int close) {
            this.p = p;
            this.open = open;
            this.close = close;
        }
    }

    public static List<String> generateValidParentheses(int num) {
        List<String> result = new ArrayList<String>();
        Queue<Value> queue = new LinkedList<Value>();
        queue.offer(new Value("",0,0));

        while(!queue.isEmpty())  {
            for(int i=queue.size(); i>0;i--) {
                Value v = queue.poll();
                if(v.open == num && v.close == num) {
                    result.add(v.p);
                    continue;
                } else if(v.open < num) {
                    queue.offer(new Value(v.p+"(", v.open+1, v.close));
                } if(v.close < v.open) {
                    queue.offer(new Value(v.p+")", v.open, v.close+1));
                }
            }
        }

        return result;
    }

}
