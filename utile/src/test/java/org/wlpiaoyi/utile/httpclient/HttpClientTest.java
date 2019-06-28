package org.wlpiaoyi.utile.httpclient;

import lombok.Data;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class HttpClientTest {

    @Data
    public static class JsonBodyData{
        private int intVar;
        private String stringVar;
    }

    @Before
    public void setUp() throws Exception {}

    @Test
    public void test() throws IOException {
        Response response =  HttpClient.syncGETResponse(null, "http://127.0.0.1:8001/wlpiaoyi/test/getData?intVar=20&stringVar=get-success");
        System.out.println(HttpClient.getResponseText(response));
        response =  HttpClient.syncGETResponse(null, "http://127.0.0.1:8001/wlpiaoyi/test/getData?intVar=-20&stringVar=get-exception");
        System.out.println(HttpClient.getResponseText(response));


        response =  HttpClient.syncPOSTFormResponse(null, "http://127.0.0.1:8001/wlpiaoyi/test/postData-form", new HashMap<String, String>(){{
            put("intVar", "20");
            put("stringVar", "post-form-success");
        }});
        System.out.println(HttpClient.getResponseText(response));

        JsonBodyData jsonData = new JsonBodyData();
        jsonData.setIntVar(20);
        jsonData.setStringVar("post-json-success");
        response =  HttpClient.syncPOSTJsonResponse(null, "http://127.0.0.1:8001/wlpiaoyi/test/postData-json", jsonData);
        System.out.println(HttpClient.getResponseText(response));
    }

    @After
    public void tearDown() throws Exception {

    }

}
