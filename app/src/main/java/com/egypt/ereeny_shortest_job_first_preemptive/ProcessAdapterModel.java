package com.egypt.ereeny_shortest_job_first_preemptive;



public class ProcessAdapterModel {

    private String name;
    private int startTime;
    private int endTime;

    public ProcessAdapterModel(){}

    public ProcessAdapterModel(String name, int startTime, int endTime){

        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
