package org.wlpiaoyi.springboot.controller;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.wlpiaoyi.framework.proxy.socket.SocketProxy;
import org.wlpiaoyi.framework.utils.ResponseUtils;
import org.wlpiaoyi.framework.utils.exception.BusinessException;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
public class HttpController {

    @Data
    public static class  ProxyData{
        private int listenPort;
        private boolean isEncryption;
        private Proxy.Type type;
        private String proxyHost;
        private int proxyPort;
    }

    @GetMapping("/getData")
    public ResponseUtils.ResponseData getData(@RequestParam(required = false, defaultValue = "-1") int var){
        try{
            return ResponseUtils.getResponseSuccess(var);
        }catch (Exception e){
            return ResponseUtils.getResponseException(e);
        }
    }


    @PostMapping("/postData-form")
    public ResponseUtils.ResponseData postDataForm(@RequestParam(required = false, defaultValue = "-1") int intVar,
                                              @RequestParam(required = false, defaultValue = "no") String stringVar){
        if(intVar >= 0){
            if(stringVar == null)
                return ResponseUtils.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtils.getResponseMessage(intVar, stringVar);
        }else{
            return ResponseUtils.getResponseException(intVar, stringVar, new BusinessException(intVar, stringVar));
        }
    }

    @Data
    public static class JsonBodyData {
        private int intVar;
        private String stringVar;
    }
    @PostMapping("/postData-json")
    public ResponseUtils.ResponseData postDataJson(@RequestBody JsonBodyData data){
        if(data.intVar >= 0){
            if(data.stringVar == null)
                return ResponseUtils.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtils.getResponseMessage(data.intVar, data.stringVar);
        }else{
            return ResponseUtils.getResponseException(data.intVar, data.stringVar, new BusinessException(data.intVar, data.stringVar));
        }
    }


    @PutMapping("/putData-form")
    public ResponseUtils.ResponseData putDataForm(@RequestParam(required = false, defaultValue = "-1") int intVar,
                                                   @RequestParam(required = false, defaultValue = "no") String stringVar){
        if(intVar >= 0){
            if(stringVar == null)
                return ResponseUtils.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtils.getResponseMessage(intVar, stringVar);
        }else{
            return ResponseUtils.getResponseException(intVar, stringVar, new BusinessException(intVar, stringVar));
        }
    }

    @PutMapping("/putData-json")
    public ResponseUtils.ResponseData putDataJson(@RequestBody JsonBodyData data){
        if(data.intVar >= 0){
            if(data.stringVar == null)
                return ResponseUtils.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtils.getResponseMessage(data.intVar, data.stringVar);
        }else{
            return ResponseUtils.getResponseException(data.intVar, data.stringVar, new BusinessException(data.intVar, data.stringVar));
        }
    }


}
