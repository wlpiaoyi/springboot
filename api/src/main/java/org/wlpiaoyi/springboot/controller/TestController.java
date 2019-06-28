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
                return ResponseUtile.success("data-success");
            else
                return ResponseUtile.message(intVar, stringVar);
        }else{
            return ResponseUtile.exception(intVar, new BusinessException(intVar, stringVar));
        }
    }

    @PostMapping("/postData-form")
    public ResponseUtile.ResponseData postDataForm(@RequestParam(required = false, defaultValue = "-1") int intVar,
                                              @RequestParam(required = false, defaultValue = "no") String stringVar){
        if(intVar >= 0){
            if(stringVar == null)
                return ResponseUtile.success("data-success");
            else
                return ResponseUtile.message(intVar, stringVar);
        }else{
            return ResponseUtile.exception(intVar, new BusinessException(intVar, stringVar));
        }
    }

    @Data
    public static class PostDataJson{
        private int intVar;
        private String stringVar;
    }

    @PostMapping("/postData-json")
    public ResponseUtile.ResponseData postDataJson(@RequestBody PostDataJson data){
        if(data.intVar >= 0){
            if(data.stringVar == null)
                return ResponseUtile.success("data-success");
            else
                return ResponseUtile.message(data.intVar, data.stringVar);
        }else{
            return ResponseUtile.exception(data.intVar, new BusinessException(data.intVar, data.stringVar));
        }
    }

}
