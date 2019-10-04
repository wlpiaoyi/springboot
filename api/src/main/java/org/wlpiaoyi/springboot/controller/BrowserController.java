package org.wlpiaoyi.springboot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wlpiaoyi.springboot.service.Browser.BrowserDataService;
import org.wlpiaoyi.springboot.service.Browser.BrowserTaskService;
import org.wlpiaoyi.utile.ResponseUtile;

@Slf4j
@RestController
@RequestMapping("/browser")
public class BrowserController {

    @Autowired
    private BrowserDataService dataService;

    @Autowired
    private BrowserTaskService taskService;

    @PostMapping("/create")
    public ResponseUtile.ResponseData postDataForm(@RequestParam(required = false) String mobile,
                                                   @RequestParam(required = false) String password){
        try{
            this.dataService.create(mobile, password);
            return ResponseUtile.getResponseSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtile.getResponseException(e);
        }
    }

    @GetMapping("quite")
    public ResponseUtile.ResponseData quite(@RequestParam String mobile){
        try{
            return ResponseUtile.getResponseSuccess(this.dataService.quite(mobile));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtile.getResponseException(e);
        }
    }

    @GetMapping("gotoDill")
    public ResponseUtile.ResponseData gotoDill(@RequestParam String mobile){
        try{
            return ResponseUtile.getResponseSuccess(this.dataService.gotoDill(mobile));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtile.getResponseException(e);
        }
    }

    class BrowserTaskListner implements BrowserTaskService.BrowserTaskListner {

        private BrowserDataService dataService;

        BrowserTaskListner(BrowserDataService dataService){
            this.dataService = dataService;
        }

        @Override
        public void inDatas(Object taskData) {
            this.dataService.quite(((BrowserTaskService.BrowserTaskData)taskData).getMobile());
        }
    }

    @GetMapping("startListener")
    public ResponseUtile.ResponseData searchDill(@RequestParam String mobile, @RequestParam(defaultValue = "60") long offTime){
        try{
            this.taskService.addTaskData(mobile, offTime, new BrowserTaskListner(this.dataService));
            return ResponseUtile.getResponseSuccess(null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtile.getResponseException(e);
        }
    }


}
