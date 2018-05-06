package com.student.practice.done.practiceDP.typeB;

import java.util.Arrays;
import java.util.Comparator;

public class JobScheduling {

    public static void main(String[] args) {
        JobScheduling app = new JobScheduling();
        Job job135 = app.new Job(1, 3, 5);
        Job job256 = app.new Job(2, 5, 6);
        Job job465 = app.new Job(4, 6, 5);
        Job job674 = app.new Job(6, 7, 4);
        Job job792 = app.new Job(7, 9, 2);
        Job job5811 = app.new Job(5, 8, 11);
        Job[] arrJob = new Job[]{job135, job256, job465, job674, job792, job5811};
        ValueAndPreviousIndex[] jobSchedulingDP = app.getJobSchedulingDP2(arrJob);
        app.getPrintJobSchedulingFromJobSchedulingDP1(arrJob, jobSchedulingDP);
    }

    private void getPrintJobSchedulingFromJobSchedulingDP1(Job[] arrJob, ValueAndPreviousIndex[] jobSchedulingDP) {

        int jsIndex = 0;

        Arrays.sort(arrJob, Comparator.comparingInt(Job::getLast));

        for (int current = 0; current <= arrJob.length - 1; current++) {
            if (jobSchedulingDP[current].getValue() > jobSchedulingDP[jsIndex].getValue()) {
                jsIndex = current;
            }
        }

        while (jsIndex > ValueAndPreviousIndex.NO_INDEX) {
            System.out.println("start = " + arrJob[jsIndex].getStart() + ", last = " + arrJob[jsIndex].getLast() + ", value = " + arrJob[jsIndex].getValue());
            jsIndex = jobSchedulingDP[jsIndex].getPreviousIndex();
        }

    }

    private ValueAndPreviousIndex[] getJobSchedulingDP2(Job[] arrJob) {

        Arrays.sort(arrJob, Comparator.comparingInt(Job::getLast));

        ValueAndPreviousIndex[] jobSchedulingDP = new ValueAndPreviousIndex[arrJob.length];

        for (int current = 0; current <= arrJob.length - 1; current++) {
            jobSchedulingDP[current] = new ValueAndPreviousIndex(arrJob[current].getValue(), ValueAndPreviousIndex.NO_INDEX);
        }

        for (int current = 1; current <= arrJob.length - 1; current++) {
            for (int previous = 0; previous <= current - 1; previous++) {

                if (arrJob[previous].getLast() < arrJob[current].getStart()) {
                    if (jobSchedulingDP[previous].getValue() + arrJob[current].getValue() > jobSchedulingDP[current].getValue()) {
                        jobSchedulingDP[current] = new ValueAndPreviousIndex(jobSchedulingDP[previous].getValue() + arrJob[current].getValue(), previous);
                    }
                }

            }
        }

        return jobSchedulingDP;
    }

    private class Job {

        private final int start;
        private final int last;
        private final int value;

        Job(int start, int last, int value) {
            this.start = start;
            this.last = last;
            this.value = value;
        }

        int getStart() {
            return start;
        }

        int getLast() {
            return last;
        }

        int getValue() {
            return value;
        }

    }

    private class ValueAndPreviousIndex {

        private static final int NO_INDEX = -1;

        private final int value;
        private final int previousIndex;

        ValueAndPreviousIndex(int value, int previousIndex) {
            this.value = value;
            this.previousIndex = previousIndex;
        }

        int getValue() {
            return value;
        }

        int getPreviousIndex() {
            return previousIndex;
        }

    }

}
