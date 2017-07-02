package com.egypt.ereeny_shortest_job_first_preemptive;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;


public class Scheduler {


    private ArrayList inQueue;
    private ArrayList not_in_Queue_yet;
    private BurstComparator burstComparator;
    private ArrivalComparator arrivalComparator;

    private ArrayList<ProcessModel> allProcesses;

    public void init(){
        allProcesses = new ArrayList<>();
        inQueue = new ArrayList();
        not_in_Queue_yet = new ArrayList();
        burstComparator = new BurstComparator();
        arrivalComparator = new ArrivalComparator();
    }

    public void addProcess(ProcessModel processModel){ allProcesses.add(processModel); }

    public Scheduler(){

    }



    public void addProcess_Prim(ProcessModel p){

        if(p.getArrivalTime() == 0){
            inQueue.add(p);}
        else {
            not_in_Queue_yet.add(p);}

    }
    private void swapIfATEquivelent()

    {
        if (not_in_Queue_yet.size() >= 2) {
            int j = 1;
            for (int i = 0; i < not_in_Queue_yet.size()-1; i++) {
                ProcessModel currentProcess = (ProcessModel) not_in_Queue_yet.get(i);
                ProcessModel nextProcess = (ProcessModel) not_in_Queue_yet.get(j);
                if (currentProcess.getArrivalTime() == nextProcess.getArrivalTime() && nextProcess.getBurstTime() < currentProcess.getBurstTime()) {
                    not_in_Queue_yet.set(i, nextProcess);
                    not_in_Queue_yet.set(j, currentProcess);
                }
                j++;

            }
        }
    }


    public void startScheduling() {

        int time = 0;
        int startProcessing = 0;
        Collections.sort(inQueue, burstComparator);
        Collections.sort(not_in_Queue_yet, arrivalComparator);
        swapIfATEquivelent();


        ProcessModel processNow = null;
        ProcessModel processNew = null;

        if (inQueue.size() == 0) {

            inQueue.add(not_in_Queue_yet.get(0));
            not_in_Queue_yet.remove(0);

            ProcessModel firstProcess = (ProcessModel) inQueue.get(0);
            time = firstProcess.getArrivalTime();
            startProcessing = time;

            for (int i = 0; i < not_in_Queue_yet.size(); i++) {
                ProcessModel temp = (ProcessModel) not_in_Queue_yet.get(i);

                if (temp.getArrivalTime() == firstProcess.getArrivalTime()) {
                    inQueue.add(temp);
                    not_in_Queue_yet.remove(i);

                }

            }
        }


        while (true) {

            time += 1;

            if (inQueue.size() != 0) {

                processNow = (ProcessModel) inQueue.get(0);

                if (processNow.getBurstTime() == 1) {

                    Log.i("Scheduling", processNow.getName() + " executed from " + startProcessing + " to " + time);
                    saveStartEnd(processNow, startProcessing, time);
                    startProcessing = time;

                    inQueue.remove(0);

                } else {
                    processNow.decrementBurstTime();
                }

            }

            if (not_in_Queue_yet.size() != 0) {

                processNew = (ProcessModel) not_in_Queue_yet.get(0);

                if (processNew.getArrivalTime() == time) {

                    if (processNow != null) {
                        if (processNow.getBurstTime() > processNew.getBurstTime()) {

                            Log.i("Scheduling", processNow.getName() + " executed from " + startProcessing + " to " + time);
                            saveStartEnd(processNow, startProcessing, time);
                            startProcessing = time;
                        }
                    }

                }

                if (processNew.getArrivalTime() <= time) {
                    inQueue.add(processNew.processClone());
                    not_in_Queue_yet.remove(0);
                    Collections.sort(inQueue, burstComparator);

                }

            }

            if (not_in_Queue_yet.size() == 0 && inQueue.size() == 0) {

                displayStartEnd();

                calculateEachProcessTurnAround();
                displayTurnAroundAvg();

                calculateEachProcessWaitingTime();
                displayWaitingTimeAvg();

                calculateEachProcessResponseTime();
                displayResponseTimeAvg();

                break;
            }

        }

    }



    private void saveStartEnd(ProcessModel process, int startTime, int endTime ){
        for(int i=0; i< allProcesses.size(); i++){
            if(allProcesses.get(i).getName().equals(process.getName())){
                allProcesses.get(i).setStartTime(startTime);
                allProcesses.get(i).setEndTime(endTime);
                MyApplication.addProcessForAdapterArrayList(allProcesses.get(i).getName(), startTime, endTime);

            }
        }
    }

    private void displayStartEnd(){

        for(int i=0; i< allProcesses.size(); i++)
        {
            for (int j=0;  j<allProcesses.get(i).getArrayListSize(); j++)
            {

                Log.i("StartEnd", allProcesses.get(i).getName() + "  " +
                        allProcesses.get(i).getStartTimeIndexValue(j) + " ---> "
                        + allProcesses.get(i).getEndTimeIndexValue(j) );
            }

        }

    }

    private void calculateEachProcessTurnAround(){
        for (int i=0; i< allProcesses.size(); i++)
        {

            for (int j=0; j< allProcesses.get(i).getArrayListSize(); j++)
            {
                ProcessModel currentProcess = allProcesses.get(i);
                currentProcess.setTurnaroundTime(currentProcess.getEndTimeIndexValue(currentProcess.getArrayListSize()-1) -  currentProcess.getArrivalTime());
            }

        }
    }

    private void displayTurnAroundAvg(){

        float turnAroundAvg = 0;
        for(int i=0; i< allProcesses.size(); i++)
        {
            ProcessModel currentProcess = allProcesses.get(i);
            turnAroundAvg += currentProcess.getTurnaroundTime();
            Log.i("turnAround", currentProcess.getName() + " turnAround =  " + currentProcess.getTurnaroundTime());
        }

        Log.i("turnAroundAvg", "turnAroundAvg = " + turnAroundAvg/allProcesses.size() );
        MyApplication.setTurnAroundAvg(turnAroundAvg/allProcesses.size());

    }

    private void calculateEachProcessWaitingTime(){

        for (int i=0; i< allProcesses.size(); i++)
        {
            ProcessModel currentProcess = allProcesses.get(i);
            float tempWaitingTime = 0;

            for (int j=0; j<currentProcess.getArrayListSize(); j++)
            {
                if(j == 0 )
                {tempWaitingTime += currentProcess.getStartTimeIndexValue(0) - currentProcess.getArrivalTime();}

                if(  j != 0 && currentProcess.getArrayListSize() >= 1)
                {tempWaitingTime += currentProcess.getStartTimeIndexValue(j) - currentProcess.getEndTimeIndexValue(j-1);}

            }

            currentProcess.setWaitingTime(tempWaitingTime);
        }


    }

    private void displayWaitingTimeAvg(){

        float waitingTimeAvg = 0;
        for(int i=0; i< allProcesses.size(); i++)
        {
            ProcessModel currentProcess = allProcesses.get(i);
            waitingTimeAvg += currentProcess.getWaitingTime();
            Log.i("waitingTime", currentProcess.getName() + " waitingTime =  " + currentProcess.getWaitingTime());
        }

        Log.i("waitingTimeAvg", "waitingTimeAvg = " + waitingTimeAvg/allProcesses.size() );
        MyApplication.setWaitingAvg(waitingTimeAvg/allProcesses.size());

    }

    private void calculateEachProcessResponseTime(){
        for(int i=0; i< allProcesses.size(); i++){

            allProcesses.get(i).setResponseTime(allProcesses.get(i).getStartTimeIndexValue(0) - allProcesses.get(i).getArrivalTime());

        }
    }

    private void displayResponseTimeAvg(){

        float responseTimeAvg = 0;
        for(int i=0; i< allProcesses.size(); i++)
        {
            ProcessModel currentProcess = allProcesses.get(i);
            responseTimeAvg += currentProcess.getResponseTime();
            Log.i("responseTime", currentProcess.getName() + " responseTime =  " + currentProcess.getResponseTime());

        }

        Log.i("responseTimeAvg", "responseTimeAvg = " + responseTimeAvg/allProcesses.size() );
        MyApplication.setResponseAvg(responseTimeAvg/allProcesses.size());


    }

}
