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
@RequestMapping("/SocketProxy")
public class SocketProxyController {

    @Data
    public static class  ProxyData{
        private int listenPort;
        private boolean isEncryption;
        private Proxy.Type type;
        private String proxyHost;
        private int proxyPort;
    }
    @PostMapping("/create")
    public ResponseUtils.ResponseData create(@RequestBody ProxyData data){
        try{
            if(data.listenPort <= 80){
                throw new BusinessException(201, "listenPort must large than 80, this listenPort is:" + data.listenPort);
            }
            SocketProxy socketProxy = SocketProxy.get(data.listenPort);
            if(socketProxy == null) socketProxy = new SocketProxy(data.isEncryption, data.listenPort);
            data.type = Proxy.Type.SOCKS;
            if(data.proxyHost != null && data.proxyHost.length() > 0){
                if(data.proxyPort <= 80){
                    throw new BusinessException(201, "proxyPort must large than 80, this proxyPort is:" + data.proxyPort);
                }
                socketProxy.setProxy(data.proxyHost, data.proxyPort);
            }else socketProxy.clearProxy();
            socketProxy.start();
            return ResponseUtils.getResponseSuccess(data);
        }catch (Exception e){
            return ResponseUtils.getResponseException(e);
        }
    }

    @DeleteMapping("/close")
    public ResponseUtils.ResponseData close(@RequestParam int listenPort){
        try{
            SocketProxy socketProxy = SocketProxy.remove(listenPort);
            if(socketProxy != null){
                return ResponseUtils.getResponseSuccess(socketProxy);
            }else{
                return ResponseUtils.getResponseMessage(201,"没有找到listenPort对应的代理");
            }
        }catch (Exception e){
            return ResponseUtils.getResponseException(e);
        }
    }

    @GetMapping("/queryOne")
    public ResponseUtils.ResponseData query(@RequestParam int listenPort){
        try{
            Map<String, Object> data = new HashMap<>();
            SocketProxy socketProxy = SocketProxy.get(listenPort);
            if(socketProxy != null){
                data =  new HashMap<String, Object>(){{
                    put("listenPort", socketProxy.getListenPort());
                    put("isEncryption", socketProxy.isEncryption());
                    if(socketProxy.getProxy() != null){
                        put("proxyType", socketProxy.getProxy().type().name());
                        put("proxyAddress", socketProxy.getProxy().address());
                    }
                }};
            }
            return ResponseUtils.getResponseSuccess(data);
        }catch (Exception e){
            return ResponseUtils.getResponseException(e);
        }
    }

    @GetMapping("/queryAll")
    public ResponseUtils.ResponseData query(){
        try{
            Map<Integer, Map<String, Object>> datas = new HashMap<>();
            for (Map.Entry<Integer, SocketProxy> entry : SocketProxy.getServers()){
                datas.put(entry.getKey(), new HashMap<String, Object>(){{
                    put("listenPort", entry.getValue().getListenPort());
                    put("isEncryption", entry.getValue().isEncryption());
                    if(entry.getValue().getProxy() != null){
                        put("proxyType", entry.getValue().getProxy().type().name());
                        put("proxyAddress", entry.getValue().getProxy().address());
                    }
                }});
            }
            return ResponseUtils.getResponseSuccess(datas);
        }catch (Exception e){
            return ResponseUtils.getResponseException(e);
        }
    }


    @GetMapping("/getData")
    public ResponseUtils.ResponseData getData(@RequestParam(required = false, defaultValue = "-1") int intVar, @RequestParam(required = false, defaultValue = "nullStr") String stringVar){
        if(intVar >= 0){
            if(stringVar == null)
                return ResponseUtils.getResponseSuccess("data-getResponseSuccess");
            else
                return ResponseUtils.getResponseMessage(intVar, stringVar);
        }else{
            return ResponseUtils.getResponseException(intVar, stringVar, new BusinessException(intVar, stringVar));
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
