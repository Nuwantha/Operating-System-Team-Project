/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestremainingtimenext;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Gimhani
 */
public class Process implements Comparable<Process>{

    
    
    
    private int startTime;
    private String name;
    private String processId;
    private int serviceTime;
    private int remainingTime;
    private int turnAroundTime;
    private int arivalTime;
    private int endTime;
    boolean inprocess;
    private int position;
    private int waitingTime;

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    Process() {
        remainingTime = serviceTime;
        this.startTime=-1;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    

    public Process(String name, String processId, int serviceTime, int startTime, int position) {
        this.name = name;
        this.processId = processId;
        this.serviceTime = serviceTime;
        this.arivalTime = startTime;
        this.position = position;
        this.startTime=-1;
        remainingTime = serviceTime;//////////////
       
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public Process(String processId, int serviceTime) {
        this.processId = processId;
        this.serviceTime = serviceTime;
        this.remainingTime = serviceTime;
        this.turnAroundTime = 0;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getArivalTime() {
        return arivalTime;
    }

    public void setArivalTime(int arivalTime) {
        this.arivalTime = arivalTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void calculateRemainingTime() {
        remainingTime = remainingTime - turnAroundTime;

    }

    public void start() {
        String CurrentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        //this.arivalTime=CurrentTime;    
    }

    public boolean isInprocess() {
        return inprocess;
    }

    public void setInprocess(boolean inprocess) {
        this.inprocess = inprocess;
    }

    
    public int compareTo(Process p) {
        int compare=((Process)p).getStartTime();
        /* For Ascending order*/
        return p.getStartTime()-compare;
    }
    

}
