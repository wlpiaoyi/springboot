package org.wlpiaoyi.springboot;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.wlpiaoyi.framework.utils.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class Browser {

    enum ProgressStatus{
        Login,
        GotoDill,
        SearchDill,
        IteratorDill
    }

    private final WebDriver driver;

    private ProgressStatus status;


    public Browser(int width, int height){
        ChromeOptions option = new ChromeOptions();
        option.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        WebDriver driver = new ChromeDriver(option);
        driver.get("https://mms.pinduoduo.com/");
        if(width > 0 && height > 0){
            driver.manage().window().setSize(new Dimension(width, height));
        }else {
            driver.manage().window().maximize();
        }
        this.driver = driver;
    }

    public void quit(){
        this.driver.quit();
    }

    public void login(String mobile, String password){

        List<WebElement> elements = this.driver.findElements(By.className("tab-item"));
        elements.get(1).click();
        try {Thread.sleep(1000);} catch (InterruptedException e) {}
        elements = this.driver.findElements(By.tagName("input"));
        int i = 0;
        for (WebElement element : elements){
            if(element.getAttribute("placeholder").contains("请输入账户名/手机号")){
                element.sendKeys(mobile);
                i++;
            }else if(element.getAttribute("placeholder").contains("请输入密码")){
                element.sendKeys(password);
                i++;
            }
            if(i == 2) break;
        }
        if(i != 2){
            throw new BusinessException("未找到输入框");
        }

        elements = this.driver.findElements(By.tagName("button"));
        WebElement button = null;
        for (WebElement element : elements){
            if(element.getAttribute("data-testid").contains("beast-core-button")
                    && element.findElement(By.tagName("span")).getText().equals("登录")){
                button = element;
                break;
            }
        }
        if(button == null){
            throw new BusinessException("未找到登录按钮");
        }
        button.click();

        this.status = ProgressStatus.Login;
    }

    public void gotoDill(){
        int i = 1000;
        while(i > 0){
            List<WebElement> elements = driver.findElements(By.className("nav-item-group"));
            if(elements != null && elements.size() > 2){
                WebElement element = elements.get(1).findElement(By.tagName("ul"));
                if(element != null){
                    elements = element.findElements(By.tagName("li"));
                    if(elements != null && elements.size() > 1){
                        element = elements.get(0).findElement(By.tagName("a"));
                        if(element != null && element.getAttribute("href").endsWith("/orders/list")){
                            element.click();
                            i = 0;
                        }
                    }
                }
            }
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            i--;
        }

        if(i != -1){
            throw new RuntimeException("登录网络超时!");
        }
        this.status = ProgressStatus.GotoDill;
    }

    public WebElement getSerarchForm(){

        int i = 1000;
        WebElement formEl = null;
        while(i > 0){
            List<WebElement> elements  = driver.findElements(By.tagName("form"));
            for (WebElement element : elements){
                elements = element.findElements(By.className("order-search-label"));
                for (WebElement element1 : elements){
                    if(element1.getText().equals("快速筛选订单：")){
                        i = 0;
                        formEl = element;
                        break;
                    }
                }
                if(i == 0){
                    break;
                }
            }
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            i--;
        }
        if(i != -1){
            throw new RuntimeException("订单打开网络超时!");
        }

        return formEl;
    }

    public void searchDill(){

        List<WebElement> elements;

        WebElement formEl = this.getSerarchForm();
        if(formEl == null){
            throw new RuntimeException("订单搜索表单为空!");
        }
        WebElement searchEle = formEl.findElements(By.xpath("./*")).get(2);

        WebElement searchInputEle = searchEle.findElements(By.xpath("./*")).get(1).findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(1).findElement(By.xpath("./*")).findElement(By.tagName("input"));
        boolean isPayStatus  = searchInputEle.getAttribute("value").equals("待发货");
        if(!isPayStatus){
            searchEle.findElements(By.xpath("./*")).get(0).findElements(By.tagName("li")).get(0).click();
        }

        try {Thread.sleep(500);} catch (InterruptedException e) {}
        elements = searchEle.findElements(By.tagName("button"));
        WebElement buttonSearchEle = null;
        for (WebElement element : elements){
            WebElement element1 = element.findElement(By.tagName("span"));
            if(element1.getText().equals("查询")){
                buttonSearchEle = element;
                break;
            }
        }
        if(buttonSearchEle == null){
            throw new RuntimeException("未找到搜索按钮!");
        }
        buttonSearchEle.click();

        this.status = ProgressStatus.SearchDill;
    }


    public List<org.wlpiaoyi.springboot.entity.Browser> itertorData(){

        List<org.wlpiaoyi.springboot.entity.Browser> datas = null;
        List<WebElement> elements;

        WebElement formEl = this.getSerarchForm();
        if(formEl == null){
            throw new RuntimeException("订单搜索表单为空!");
        }

        int i = 1000;
        while(i > 0){
            elements = formEl.findElements(By.xpath("./*"));
            if(elements.size() >= 5){
                String count =  elements.get(3).findElements(By.xpath("./*")).get(0)
                        .findElements(By.xpath("./*")).get(0)
                        .findElements(By.xpath("./*")).get(0)
                        .findElements(By.xpath("./*")).get(2)
                        .findElements(By.xpath("./*")).get(0)
                        .findElements(By.xpath("./*")).get(0).getText();
                i = 0;
                if(count == null || count.equals("0")) break;
                List<WebElement> dillEls = elements.get(4).findElements(By.xpath("./*")).get(0)
                        .findElements(By.xpath("./*"));
                datas = new ArrayList<>(dillEls.size());
                for (WebElement dillEl : dillEls){
                    WebElement headEle = dillEl.findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(0);
                    List<WebElement> dillInfos = headEle.findElements(By.xpath("./*"));
                    String dillCode = dillInfos.get(0).findElements(By.tagName("span")).get(0).getText().split("：")[1];
                    String dillDate = dillInfos.get(1).findElements(By.tagName("span")).get(1).getText().split("：")[1];
                    String outDate = dillInfos.get(1).findElements(By.tagName("span")).get(2).getText().split("：")[1];

                    WebElement detailEle = dillEl.findElements(By.xpath("./*")).get(1).findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(0).findElements(By.xpath("./*")).get(0).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
                    dillInfos = detailEle.findElements(By.tagName("td"));
                    String imgurl = dillInfos.get(0).findElement(By.tagName("img")).getAttribute("src");
                    String userId = dillInfos.get(0).findElements(By.tagName("p")).get(0).getText().split(":")[1];
                    String productName = dillInfos.get(0).findElements(By.tagName("p")).get(1).getText();
                    String statusName = dillInfos.get(1).findElements(By.xpath("./*")).get(0).getText();
                    String productNum = dillInfos.get(3).getText();
                    String totalPrice = dillInfos.get(4).getText();
                    String payPrice = dillInfos.get(5).findElements(By.xpath("./*")).get(0).getText();
                    if(payPrice.length() > 0){
                        payPrice = payPrice.split("\n")[0];
                        payPrice = payPrice.split("￥")[1];

                    }
                    String userName = dillInfos.get(6).findElements(By.xpath("./*")).get(0).getText();


                    org.wlpiaoyi.springboot.entity.Browser browser = new org.wlpiaoyi.springboot.entity.Browser();
                    browser.setDillCode(dillCode);
                    browser.setDillDate(dillDate);
                    browser.setOutDate(outDate);
                    browser.setImgurl(imgurl);
                    browser.setUserId(userId);
                    browser.setProductName(productName);
                    browser.setStatusName(statusName);
                    browser.setProductNum(productNum);
                    browser.setTotalPrice(totalPrice);
                    browser.setPayPrice(payPrice);
                    browser.setUserName(userName);
                    datas.add(browser);
                }

            }
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            i--;
        }
        if(i != -1){
            throw new RuntimeException("订单打开网络超时!");
        }
        this.status = ProgressStatus.IteratorDill;

        return datas;
    }


//    public static void main(String[] args) {
//
//        org.wlpiaoyi.springboot.Browser browser = new org.wlpiaoyi.springboot.Browser(0, 0);
//        browser.login("18683523851", "Ikamobile2416");
//        browser.gotoDill();
//        browser.searchDill();
//        browser.itertorData();
//        browser.quit();
//
//    }
    //方法4
    public static void setElementValue(WebElement element,String value) {
        element.click();
        element.clear();
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(value);
        element.click();
    }
}
