  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestremainingtimenext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gimhani
 */
public class Schedular {

    public ArrayList<Process> processlist;

    public ArrayList<Process> timelist;
    ArrayList<Process> resultList;
    ArrayList<Process> completedList;

    public Schedular() {
        resultList = new ArrayList<Process>();
        processlist = new ArrayList<Process>();
        timelist = new ArrayList<Process>();
        completedList = new ArrayList<Process>();

    }
////////////////////////////////

    //completed
    public void sheduleSRT() {
        int minServTime, minServInd, time = 0, i;
        while (processlist.size() > 0) {
            minServTime = Integer.MAX_VALUE;
            minServInd = -1;
            i = 0;
            //travel through list
            while (i < processlist.size()) {
                if (processlist.get(i).getRemainingTime() == 0) {
                    Process endProcess = processlist.get(i);
                    endProcess.setEndTime(time);
                    endProcess.setTurnAroundTime();
                    endProcess.setNormalizedTurnaroundTime();
                    resultList.add(endProcess);
                    processlist.remove(i);
                    continue;
                }
                if (processlist.get(i).getArivalTime() <= time) {
                    if (processlist.get(i).getRemainingTime() < minServTime || (processlist.get(i).getRemainingTime() == minServTime && minServInd > i)) {
                        minServTime = processlist.get(i).getRemainingTime();
                        minServInd = i;
                    }
                }
                i++;
            }
            if (minServInd >= 0) {
                if (processlist.get(minServInd).getStartTime() < 0) {
                    processlist.get(minServInd).setStartTime(time);
                }
                timelist.add((processlist.get(minServInd)));
                processlist.get(minServInd).setRemainingTime((processlist.get(minServInd).getRemainingTime()) - 1);
            } else {
                //timelist.add(null);
            }
            time++;
        }
        for (int k = 0; k < timelist.size(); k++) {
            System.out.print(timelist.get(k) + " ");
        }
    }

    public void sheduleFCFS() {

        int Ttime = 0;
        int j = 0;
        Process min = processlist.get(0);//two precesses to save processes
        Process min1 = processlist.get(0);
        ArrayList<Process> currprocess = new ArrayList<Process>();//save process list

        while (processlist.size() > 0) {
            for (int i = 0; i < processlist.size(); i++) {
                //in specific time check if process is available
                if (processlist.get(i).getArivalTime()<= Ttime) {
                    currprocess.add(processlist.get(i));
                }
            }
            //get first process in queue
            for (Process l : currprocess) {
                if (l.getArivalTime() <= min.getArivalTime()) {
                    min = l;
                }
            }
            //processing stage of the selected process
            min.setStartTime(Ttime);//set start time of the selected process
            while (min.getRemainingTime()> 0) {
                if (min.getArivalTime() <= Ttime) {
                    timelist.add(min);
                    min.setRemainingTime(min.getRemainingTime() - 1);

                    Ttime++;
                } else {
                    timelist.add(null);//if no process is in given timeslot
                    Ttime++;
                }
            }
            min.setEndTime(Ttime);//set end time of the selected process
            min.setTurnAroundTime();//set turnaround time of the selected process
            min.setNormalizedTurnaroundTime();
            resultList.add(min);
            processlist.remove(min);
            if (processlist.size() > 0) {
                min = processlist.get(0);//make the first process null
            } else {
                min = min1;
            }
            currprocess.clear();

        }
        for (int k = 0; k < timelist.size(); k++) {
            System.out.print(timelist.get(k).getProcessId() + " ");
        }
    }
    
    public void sheduleHRRN() {
        int Ttime = 0;
        Process max = processlist.get(0);//two precesses to save processes
        Process max1 = processlist.get(0);
        ArrayList<Process> currprocess = new ArrayList<Process>();//save process list

        while (processlist.size() > 0) {
            for (int i = 0; i < processlist.size(); i++) {
                if (processlist.get(i).getArivalTime() <= Ttime) {
                    currprocess.add(processlist.get(i));
                }
            }
            //calculate HRR for the processes
            for (int i = 0; i < currprocess.size(); i++) {
                currprocess.get(i).setPriority(currprocess.get(i).getWaitingTime(), currprocess.get(i).getRemainingTime());

            }
            //get HRR process in queue
            for (Process i : currprocess) {
                if (i.priority > max.priority) {
                    max = i;
                }
            }
            max.setStartTime(Ttime);
            //processing stage of the selected process
            while (max.getRemainingTime() > 0) {
                //check if the process in available in the given timeslot
                if (max.getArivalTime() <= Ttime)
                {
                    timelist.add(max);
                    max.setRemainingTime(max.getRemainingTime() - 1);
                    for (int i = 0; i < processlist.size(); i++) {
                        if (processlist.get(i).getArivalTime() <= Ttime) {
                            processlist.get(i).setWaitingTime();

                        }
                    }
                    Ttime++;
                } else {
                    timelist.add(null);//if no process available in the given timeslot
                    Ttime++;
                }
            }
            max.setEndTime(Ttime);//set end time
            max.setTurnAroundTime();//set turnaround time
            max.setNormalizedTurnaroundTime();
            resultList.add(max);
            processlist.remove(max);
            if (processlist.size() > 0) {
                max = processlist.get(0);//make the selected process null
            } else {
                max = max1;
            }
            currprocess.clear();
        }
        for (int k = 0; k < timelist.size(); k++) {
            System.out.print(timelist.get(k) + " ");
        }
    }

    /////////////////////////////////
    /*public ArrayList<Process> ShortestRemainingTimeNext() {
     int minServTime, minServInd, time = 0, i;
     while (processlist.size() > 0) {
     minServTime = Integer.MAX_VALUE;
     minServInd = -1;
     i = 0;
     while (i < processlist.size()) {
     if (processlist.get(i).getServiceTime() == 0) {
     processlist.get(i).setEndTime(time);
     resultList.add(processlist.get(i));
     processlist.remove(i);

     continue;
     }
     if (processlist.get(i).getArivalTime() <= time) {
     if (processlist.get(i).getServiceTime() < minServTime || (processlist.get(i).getServiceTime() == minServTime && minServInd > i)) {
     minServTime = processlist.get(i).getServiceTime();
     minServInd = i;
     }
     }
     i++;
     }
     if (minServInd >= 0) {
     timelist.add((processlist.get(minServInd)));
     if (processlist.get(minServInd).getStartTime() < 0) {
     processlist.get(minServInd).setStartTime(time);
     }
     processlist.get(minServInd).setServiceTime((processlist.get(minServInd).getServiceTime()) - 1);
     } else {
     timelist.add(null);
     }
     time++;
     }

     return processlist;
     }

     public void sheduleFCFS() {

     int Ttime = 0;
     int j = 0;
     Process min = processlist.get(0);
     Process min1 = processlist.get(0);
     ArrayList<Process> currprocess = new ArrayList<Process>();

     while (processlist.size() > 0) {
     for (int i = 0; i < processlist.size(); i++) {
     if (processlist.get(i).getArivalTime() <= Ttime) {
     currprocess.add(processlist.get(i));
     }
     }

     for (Process l : currprocess) {
     if (l.getStartTime() <= min.getStartTime()) {
     min = l;
     }
     }
     while (min.getServiceTime() > 0) {
     if (min.getStartTime() <= Ttime) {
     timelist.add(min);
     min.setServiceTime(min.getServiceTime() - 1);

     Ttime++;
     } else {
     timelist.add(null);
     Ttime++;
     }
     }
     processlist.remove(min);
     if (processlist.size() > 0) {
     min = processlist.get(0);
     } else {
     min = min1;
     }
     currprocess.clear();

     }
     for (int k = 0; k < timelist.size(); k++) {
     System.out.print(timelist.get(k).getProcessId() + " ");
     }

     }

     /**
     * public void sheduleHRRN(){ int Ttime=0; Process max=processlist.get(0);
     * Process max1=processlist.get(0); ArrayList<Process> currprocess=new
     * ArrayList<Process>();
     *
     * while(processlist.size()>0){ for(int i=0;i<processlist. size();i++){
     * if(processlist.get(i).getStartTime()<=Ttime){
     * currprocess.add(processlist.get(i)); } } for(int i=0;i<currprocess.size()-1;i++){
     * if(currprocess.get(i).getpriority(currprocess.get(i).getwaitingTime(), currprocess.get(i).getServiceTime())>=currprocess.get(i+1).getpriority(currprocess.get(i+1).getwaitingTime(),
     * currprocess.get(i+1).getServiceTime())){ max=currprocess.get(i); } else{
     * max=currprocess.get(i+1); } } while(max.getServiceTime()>0){
     * if(max.getStartTime()<=Ttime){ timelist.add(max.getProcessId());
     * max.setServiceTime(max.getServiceTime()-1); for(int
     * i=0;i<processlist.size();i++){ * if(processlist.get(i).getStartTime()<=Ttime){
     * processlist.get(i).setwaitingTime();      *
     * }}
     * Ttime++;}
     * else{
     * timelist.add("Null");
     * Ttime++;
     * }
     * }
     * processlist.remove(max);
     * if(processlist.size()>0){ max=processlist.get(0); } else{ max=max1; }
     * currprocess.clear(); } for(int k=0;k<timelist.size();k++){
     * System.out.print(timelist.get(k)+" "); } }
     */
    public void feedback() {
        // Sorts the array list using comparator according to the starttime
        Collections.sort(processlist);
        ArrayList<Process>[] multilevel = new ArrayList[5]; //5 multi levels
        for (int k = 0; k < 5; k++) {
            multilevel[k] = new ArrayList<Process>();   //initializing each queue
        }

        int timeQuantum = 2;
        int tempQuantum = timeQuantum;
        int time = 0;
        int i = 0;
        int j = 0;
        //int size = 0;
        int inter_size = 0;
        int process_count=processlist.size();
        while (process_count>0) {

            while (j<5) {
                for (Process processlist1 : processlist) {
                    if (processlist1.getArivalTime() == time) {
                        multilevel[0].add(processlist1);
                        process_count--;
                        
                    }
                }
                //////
                if (tempQuantum == 0) {                        //if the timequantum allocated has finished for the recently executed process
                    if (multilevel[j].get(i).getRemainingTime() == 0) {    //check if the process has finished
                        multilevel[j].remove(i);
                    } else {
                        Process temp = multilevel[j].remove(i);   // if not add that process to the end of the queue

                        if (j == 4) {
                            multilevel[j].add(temp);
                        } else if (j < 4) {
                            multilevel[j + 1].add(temp);
                        }
                    }
                    tempQuantum = timeQuantum;

                }else{
                if (multilevel[j].size() > 0) {
                    if (multilevel[j].get(i).getRemainingTime() != 0) {
                        multilevel[j].get(i).setRemainingTime((multilevel[j].get(i).getRemainingTime()) - 1); //execute the process 
                        tempQuantum--;
                        timelist.add(multilevel[j].get(i));
                    }else{    //check if the process currenly has finished
                        multilevel[j].remove(i);
                        tempQuantum = timeQuantum;

                    }
                }
                }
                //////////////////
                int last_j=j;
                for (int k = 0; k < j; k++) {
                    if (multilevel[k].size() > 0) {
                        j = k;
                        System.out.println(j);
                        inter_size = 0;
                    }
                }
                if (last_j!=j) {
                    time++;
                    break;
                }
                time++;

            }
            int size = 0;
            for (int k = 0; k < 5; k++) {
                size += multilevel[k].size();

            }
            System.out.println("whole:"+size);
            if (size == 0) {
                break;
            }
            j++;
        }
        for (int k = 0; k < timelist.size(); k++) {
            System.out.print(timelist.get(k) + " ");
        }
    }

    public void scheduleRoundRobin() {
        // Sorts the array list using comparator according to the starttime
        Collections.sort(processlist);
        int timeQuantum = 2; //// time Quantum SHOULD be passed by user
        int tempQuantum = timeQuantum;
        int time = 0;
        int i = 0;

        ArrayList<Process> tempProcessList = new ArrayList<Process>();

        while (true) {

            for (int j = 0; j < processlist.size(); j++) {
                if (processlist.get(j).getArivalTime() == time) {
                    tempProcessList.add(processlist.get(j));
                    processlist.remove(j);
                }

            }
            if (processlist.size() == 0 && tempProcessList.size() == 0) {
                break;
            }

            if (tempProcessList.size() == 0) {
                timelist.add(null);
            }

            if (tempQuantum == 0) {                        //if the timequantum allocated has finished for the recently executed process
                if (tempProcessList.get(i).getServiceTime() == 0) {    //check if the process has finished
                    tempProcessList.remove(i);
                } else {
                    Process temp = tempProcessList.remove(i);   // if not add that process to the end of the queue
                    tempProcessList.add(temp);
                }
                tempQuantum = timeQuantum;

            }

            if (tempProcessList.get(i).getServiceTime() != 0) {
                tempProcessList.get(i).setServiceTime((tempProcessList.get(i).getServiceTime()) - 1); //execute the process 
                tempQuantum--;
                timelist.add(tempProcessList.get(i));
            }

            if (tempProcessList.get(i).getServiceTime() == 0) {    //check if the process currenly has finished
                tempProcessList.remove(i);
                tempQuantum = timeQuantum;

            }
            time++;

        }

        for (int k = 0; k < timelist.size(); k++) {
            System.out.print(timelist.get(k).getProcessId() + " ");
        }

    }
//compled

    public void SPN() {
        int minServTime, runingI, time = 0, i;
        runingI = Integer.MAX_VALUE;
        while (processlist.size() > 0) {
            minServTime = Integer.MAX_VALUE;
            i = 0;
            while (i < processlist.size()) {
                if (processlist.get(i).getServiceTime() < minServTime && processlist.get(i).getArivalTime() <= time) {
                    runingI = i;
                    minServTime = processlist.get(i).getServiceTime();
                }
                i++;

            }
            Process RuningProcess = processlist.get(runingI);
            RuningProcess.setStartTime(time);
            while (minServTime > 0) {
                timelist.add(processlist.get(runingI));
                time++;
                minServTime--;

            }
            RuningProcess.setEndTime(time - 1);
            RuningProcess.setTurnAroundTime();
            resultList.add(processlist.get(runingI));
            processlist.remove(runingI);

        }

    }

}
