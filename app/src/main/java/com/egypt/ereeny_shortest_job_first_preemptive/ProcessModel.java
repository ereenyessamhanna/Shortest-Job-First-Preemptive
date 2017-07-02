package com.egypt.ereeny_shortest_job_first_preemptive;

import java.util.ArrayList;


public class ProcessModel {

    private String name;
    private int arrivalTime;
    private int burstTime;

    private float turnaroundTime;
    private float waitingTime;
    private float responseTime;

    private ArrayList<Integer> startTime ;
    private ArrayList<Integer> endTime;


    public ProcessModel(){}

    public ProcessModel(int arrivalTime, int burstTime, String name){

        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.name = name;

        this.startTime = new ArrayList<>();
        this.endTime = new ArrayList<>();
    }

    public void decrementBurstTime(){
        burstTime -= 1;
    }

    public ProcessModel processClone(){ return new ProcessModel(arrivalTime, burstTime, name);}

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public float getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(float turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public float getResponseTime() {return responseTime;}

    public void setResponseTime(float responseTime) {this.responseTime = responseTime;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(float waitingTime) {
        this.waitingTime = waitingTime;
    }


    // ---------------------------------------------------------------------------------------- //

    public void setStartTime(int startTime){ this.startTime.add(startTime);}

    public void setEndTime(int endTime){ this.endTime.add(endTime);}

    // start or end doesn't matter both are the same
    public int getArrayListSize(){ return this.startTime.size();}

    public int getStartTimeIndexValue(int index){ return this.startTime.get(index);}

    public int getEndTimeIndexValue(int index){ return this.endTime.get(index);}


}
