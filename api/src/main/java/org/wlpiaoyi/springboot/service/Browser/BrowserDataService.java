package org.wlpiaoyi.springboot.service.Browser;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.wlpiaoyi.springboot.Browser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BrowserDataService {

    private static final Map<String, Browser> browserMap = new HashMap<>();


    public void create(String mobile, String password){
        Browser browser = new Browser(0, 0);
        if(mobile != null && password != null) browser.login(mobile, password);
        browserMap.put(mobile, browser);
    }

    public boolean quite(String mobile){
        Browser browser = browserMap.get(mobile);
        if(browser == null){
            return false;
        }
        browser.quit();
        browserMap.remove(mobile);
        return true;
    }

    public boolean gotoDill(String mobile){
        Browser browser = browserMap.get(mobile);
        if(browser == null){
            return false;
        }
        browser.gotoDill();
        return true;

    }

    public boolean searchDill(String mobile){
        Browser browser = browserMap.get(mobile);
        if(browser == null){
            return false;
        }
        browser.searchDill();
        return true;

    }

    public List<org.wlpiaoyi.springboot.entity.Browser> getDatas(String mobile){
        Browser browser = browserMap.get(mobile);
        if(browser == null){
            return null;
        }
        return browser.itertorData();
    }


}
