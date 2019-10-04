package org.wlpiaoyi.springboot.service.Browser;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wlpiaoyi.springboot.entity.Browser;

import java.util.*;

@Slf4j
@Service
public class BrowserTaskService {

    public interface BrowserTaskListner{
        void inDatas(Object taskData);
    }

    @Data
    public class BrowserTaskData{
        private String mobile;
        private long preTime;
        private long offTime;
        private BrowserTaskListner listner;
        private List<Browser> datas;
    }


    public class BrowserThread extends Thread{

        private BrowserDataService dataService;

        private final BrowserTaskData taskData;

        public BrowserThread(BrowserTaskData taskData, BrowserDataService dataService){
            this.taskData = taskData;
            this.dataService = dataService;
        }

        @Override
        public void run() {
            super.run();
            this.taskData.preTime = System.currentTimeMillis()/1000;
            this.dataService.searchDill(this.taskData.mobile);
            this.taskData.datas = this.dataService.getDatas(this.taskData.mobile);
            if(this.taskData.listner != null && this.taskData.datas != null){
                this.taskData.listner.inDatas(this.taskData.datas);
            }
        }
    }

    @Autowired
    private BrowserDataService browserDataService;

    private final static Set<BrowserTaskData> TASK_BROWSERS = new HashSet<>();

    @Scheduled(cron = "0/5 * * * * ?")
    public void task(){
        synchronized (TASK_BROWSERS){
            long currentTime = System.currentTimeMillis() / 1000;
            for (BrowserTaskData taskData : TASK_BROWSERS){
                if(currentTime - taskData.offTime >= taskData.preTime){
                    new BrowserThread(taskData, this.browserDataService).run();
                }
            }
        }
    }

    public BrowserTaskData contains(String mobile){
        synchronized (TASK_BROWSERS){
            for (BrowserTaskData taskData : TASK_BROWSERS){
                if(taskData.mobile.equals(mobile)){
                    return taskData;
                }
            }
        }
        return null;
    }

    public void addTaskData(String mobile ,long offTime, BrowserTaskListner listner){
        synchronized (TASK_BROWSERS){
            BrowserTaskData taskData = new BrowserTaskData();
            taskData.mobile = mobile;
            taskData.offTime = offTime;
            taskData.listner = listner;
            TASK_BROWSERS.add(taskData);
        }
    }

    public void removeTaskData(String mobile){
        synchronized (TASK_BROWSERS){
            for (BrowserTaskData taskData : TASK_BROWSERS){
                if(taskData.mobile.equals(mobile)){
                    TASK_BROWSERS.remove(taskData);
                    this.browserDataService.quite(mobile);
                    break;
                }
            }
        }
    }



}
