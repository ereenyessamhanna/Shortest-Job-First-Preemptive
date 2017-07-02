package com.egypt.ereeny_shortest_job_first_preemptive;

import android.app.Application;

import java.util.ArrayList;



public class MyApplication extends Application {
    static ArrayList<ProcessAdapterModel> processAdapterModelArrayList;
    static float turnAroundAvg;
    static float waitingAvg;
    static float responseAvg;

    public static void initProcessAdapterModelArrayList() {
        processAdapterModelArrayList = new ArrayList<>();
    }

    public static void addProcessForAdapterArrayList(String name, int startTime, int endTime) {

        ProcessAdapterModel newProcessAdapterModel = new ProcessAdapterModel(name, startTime, endTime);
        processAdapterModelArrayList.add(newProcessAdapterModel);

    }

    public static float getTurnAroundAvg() {
        return turnAroundAvg;
    }

    public static void setTurnAroundAvg(float turnAroundAvg) {
        MyApplication.turnAroundAvg = turnAroundAvg;
    }

    public static float getWaitingAvg() {
        return waitingAvg;
    }

    public static void setWaitingAvg(float waitingAvg) {
        MyApplication.waitingAvg = waitingAvg;
    }

    public static float getResponseAvg() {return responseAvg;}

    public static void setResponseAvg(float responseAvg) {
        MyApplication.responseAvg = responseAvg;
    }




}