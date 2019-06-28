package org.wlpiaoyi.springboot.controller;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.wlpiaoyi.utile.ResponseUtile;
import org.wlpiaoyi.utile.exception.BusinessException;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/getData")
    public ResponseUtile.ResponseData getData(@RequestParam(required = false, defaultValue = "-1") int intVar,
                                              @RequestParam(required = false, defaultValue = "no") String stringVar){
        if(intVar >= 0){
            if(stringVar == null)
                return ResponseUtile.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtile.getResponseMessage(intVar, stringVar);
        }else{
            return ResponseUtile.getResponseException(intVar, stringVar, new BusinessException(intVar, stringVar));
        }
    }

    @PostMapping("/postData-form")
    public ResponseUtile.ResponseData postDataForm(@RequestParam(required = false, defaultValue = "-1") int intVar,
                                              @RequestParam(required = false, defaultValue = "no") String stringVar){
        if(intVar >= 0){
            if(stringVar == null)
                return ResponseUtile.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtile.getResponseMessage(intVar, stringVar);
        }else{
            return ResponseUtile.getResponseException(intVar, stringVar, new BusinessException(intVar, stringVar));
        }
    }

    @Data
    public static class JsonBodyData {
        private int intVar;
        private String stringVar;
    }
    @PostMapping("/postData-json")
    public ResponseUtile.ResponseData postDataJson(@RequestBody JsonBodyData data){
        if(data.intVar >= 0){
            if(data.stringVar == null)
                return ResponseUtile.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtile.getResponseMessage(data.intVar, data.stringVar);
        }else{
            return ResponseUtile.getResponseException(data.intVar, data.stringVar, new BusinessException(data.intVar, data.stringVar));
        }
    }


    @PutMapping("/putData-form")
    public ResponseUtile.ResponseData putDataForm(@RequestParam(required = false, defaultValue = "-1") int intVar,
                                                   @RequestParam(required = false, defaultValue = "no") String stringVar){
        if(intVar >= 0){
            if(stringVar == null)
                return ResponseUtile.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtile.getResponseMessage(intVar, stringVar);
        }else{
            return ResponseUtile.getResponseException(intVar, stringVar, new BusinessException(intVar, stringVar));
        }
    }

    @PutMapping("/putData-json")
    public ResponseUtile.ResponseData putDataJson(@RequestBody JsonBodyData data){
        if(data.intVar >= 0){
            if(data.stringVar == null)
                return ResponseUtile.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtile.getResponseMessage(data.intVar, data.stringVar);
        }else{
            return ResponseUtile.getResponseException(data.intVar, data.stringVar, new BusinessException(data.intVar, data.stringVar));
        }
    }


}
