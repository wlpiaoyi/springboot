package org.wlpiaoyi.utile.httpclient;

import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HttpClientTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        Response response =  HttpClient.syncGETResponse(null, "https://www.baidu.com");
        byte[] bytes = response.body().bytes();
        System.out.println();
    }

    @After
    public void tearDown() throws Exception {

    }

}
