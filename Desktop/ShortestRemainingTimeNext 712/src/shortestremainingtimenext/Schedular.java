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

/**
 *
 * @author Gimhani
 */
public class Schedular {

    public ArrayList<Process> processlist;

    public ArrayList<Process> timelist;
      ArrayList<Process> resultList;

    public Schedular() {
        resultList=new ArrayList<Process>();
        processlist = new ArrayList<Process>();
        timelist = new ArrayList<Process>();
    }

    public ArrayList<Process> ShortestRemainingTimeNext() {
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
                if(processlist.get(minServInd).getStartTime()<0){
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
                if (processlist.get(i).getStartTime() <= Ttime) {
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
   /**  public void sheduleHRRN(){
         int Ttime=0;
         Process max=processlist.get(0);
         Process max1=processlist.get(0);
         ArrayList<Process> currprocess=new ArrayList<Process>();
          
        while(processlist.size()>0){ 
             for(int i=0;i<processlist. size();i++){
              if(processlist.get(i).getStartTime()<=Ttime){
                  currprocess.add(processlist.get(i));
              }    
    }    
            for(int i=0;i<currprocess.size()-1;i++){
                if(currprocess.get(i).getpriority(currprocess.get(i).getwaitingTime(), currprocess.get(i).getServiceTime())>=currprocess.get(i+1).getpriority(currprocess.get(i+1).getwaitingTime(), currprocess.get(i+1).getServiceTime())){
                    max=currprocess.get(i);
                }
                else{
                    max=currprocess.get(i+1);
                }
            }
           while(max.getServiceTime()>0){
               if(max.getStartTime()<=Ttime){ 
                   timelist.add(max.getProcessId());
                   max.setServiceTime(max.getServiceTime()-1);
                   for(int i=0;i<processlist.size();i++){
                       if(processlist.get(i).getStartTime()<=Ttime){
                       processlist.get(i).setwaitingTime();                         
                       
                   }}
               Ttime++;}
                   else{
                       timelist.add("Null"); 
                       Ttime++;
                           }
               }
               processlist.remove(max);
               if(processlist.size()>0){
               max=processlist.get(0);
               }
               else{
                   max=max1;
               }
           currprocess.clear();
     }
       for(int k=0;k<timelist.size();k++){
            System.out.print(timelist.get(k)+" "); 
        } 
     } */
     
      public void feedback() {
        Queue[] queue_set = new Queue[5];//let 5 priority feedback levels
        boolean isProcessesOver = false;
        
        int time = 0;
        while (isProcessesOver != true) {
            if(!processlist.isEmpty()){
            for (Process p : processlist) {
                if (time == p.getArivalTime()) {
                    System.out.println(p.getProcessId());
                    queue_set[0].add(p);
                }
            }
            }
            for (int j = 0; j < 5; j++) {
                if (queue_set[j].peek() != null) {
                    Process p = (Process) queue_set[j].poll();
                    if (p.getArivalTime() <= time) {
                        timelist.add(p);
                        p.setServiceTime(p.getServiceTime() - 1);
                        if (p.getServiceTime() > 0) {
                            if (j != 4) {
                                queue_set[j + 1].add(p);
                            } else {          //if last queue add to same queue as in round robin
                                queue_set[j].add(p);
                            }
                        }
                    } else {
                        timelist.add(null);
                    }
                }
            }

            time++;
        }
        for (int k = 0; k < timelist.size(); k++) {
            System.out.print(timelist.get(k) + " ");
        }
    }
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
            while (minServTime > 0) {
                timelist.add(processlist.get(runingI));
                time++;
                minServTime--;

            }
            processlist.remove(runingI);

        }

        for (int k = 0; k < timelist.size(); k++) {
            System.out.print(timelist.get(k) + " ");
        }

    }

}
