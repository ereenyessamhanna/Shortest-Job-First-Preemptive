package com.egypt.ereeny_shortest_job_first_preemptive;

import java.util.Comparator;


public class ArrivalComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {

        int difference = ((ProcessModel)o1).getArrivalTime() - ((ProcessModel)o2).getArrivalTime();

        if(difference>0) return 1;
        if(difference<0) return -1;

        return 0;
    }
}
