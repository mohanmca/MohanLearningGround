package oms;

import java.util.*;


class Solution {

    class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    };

    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> mergedIntervals = new LinkedList<Interval>();
        intervals.sort(Comparator.<Interval>comparingInt(a -> a.start).thenComparingInt(a -> a.end));
        Interval current = intervals.get(0);
        for (int i = 1; i < intervals.size(); i++) {
            if (isOverlap(current, intervals.get(i))) {
                current.start = Math.min(current.start, intervals.get(i).start);
                current.end = Math.max(current.end, intervals.get(i).end);
            } else {
                mergedIntervals.add(current);
                current = intervals.get(i);
            }
        }
        mergedIntervals.add(current);
        return mergedIntervals;
    }

    private boolean isOverlap(Interval i1, Interval i2) {
        int earlyFinish = Math.min(i1.end, i2.end);
        int lateStart = Math.max(i1.start, i2.start);
        return earlyFinish > lateStart;
    }
}


