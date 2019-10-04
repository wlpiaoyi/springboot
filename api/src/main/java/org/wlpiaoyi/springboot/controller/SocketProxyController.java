package org.wlpiaoyi.springboot.controller;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.wlpiaoyi.proxy.socket.SocketProxy;
import org.wlpiaoyi.utile.ResponseUtile;
import org.wlpiaoyi.utile.exception.BusinessException;

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
    public ResponseUtile.ResponseData create(@RequestBody ProxyData data){
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
            return ResponseUtile.getResponseSuccess(data);
        }catch (Exception e){
            return ResponseUtile.getResponseException(e);
        }
    }

    @DeleteMapping("/close")
    public ResponseUtile.ResponseData close(@RequestParam int listenPort){
        try{
            SocketProxy socketProxy = SocketProxy.remove(listenPort);
            if(socketProxy != null){
                return ResponseUtile.getResponseSuccess(socketProxy);
            }else{
                return ResponseUtile.getResponseMessage(201,"没有找到listenPort对应的代理");
            }
        }catch (Exception e){
            return ResponseUtile.getResponseException(e);
        }
    }

    @GetMapping("/queryOne")
    public ResponseUtile.ResponseData query(@RequestParam int listenPort){
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
            return ResponseUtile.getResponseSuccess(data);
        }catch (Exception e){
            return ResponseUtile.getResponseException(e);
        }
    }

    @GetMapping("/queryAll")
    public ResponseUtile.ResponseData query(){
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
            return ResponseUtile.getResponseSuccess(datas);
        }catch (Exception e){
            return ResponseUtile.getResponseException(e);
        }
    }


    @GetMapping("/getData")
    public ResponseUtile.ResponseData getData(@RequestParam(required = false, defaultValue = "-1") int intVar, @RequestParam(required = false, defaultValue = "nullStr") String stringVar){
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
