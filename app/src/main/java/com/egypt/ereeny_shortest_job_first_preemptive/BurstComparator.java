package com.egypt.ereeny_shortest_job_first_preemptive;

import java.util.Comparator;


public class BurstComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        int difference = ((ProcessModel)o1).getBurstTime() - ((ProcessModel)o2).getBurstTime();

        if(difference>0) return 1;
        if(difference<0) return -1;

        return 0;
    }
}
