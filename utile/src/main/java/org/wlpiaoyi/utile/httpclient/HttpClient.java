package org.wlpiaoyi.utile.httpclient;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpClient {
    public enum Method {
        GET, POST, PUT, DELETE
    }

    /**
     * GET同步请求
     * @param headerMap
     * @param url
     * @return
     */
    public static Response syncGETResponse(Map<String, String> headerMap, String url) {
        Request request = createRequest(Method.GET, headerMap, url, null);
        return syncResponse(request, null, -1);
    }

    /**
     * POST同步请求
     * @param headerMap
     * @param url
     * @param requestBody
     * @return
     */
    public static Response syncPOSTResponse(Map<String, String> headerMap, String url, RequestBody requestBody) {
        Request request = createRequest(Method.POST, headerMap, url, requestBody);
        return syncResponse(request, null, -1);
    }

    /**
     * PUT同步请求
     * @param headerMap
     * @param url
     * @param requestBody
     * @return
     */
    public static Response syncPUTResponse(Map<String, String> headerMap, String url, RequestBody requestBody) {
        Request request = createRequest(Method.PUT, headerMap, url, requestBody);
        return syncResponse(request, null, -1);
    }

    /**
     * PUT同步请求
     * @param headerMap
     * @param url
     * @param requestBody
     * @return
     */
    public static Response syncDELETEResponse(Map<String, String> headerMap, String url, RequestBody requestBody) {
        Request request = createRequest(Method.DELETE, headerMap, url, requestBody);
        return syncResponse(request, null, -1);
    }

    /**
     * 同步请求
     * @param request
     * @param proxyIp
     * @param proxyPort
     * @return
     */
    private static Response syncResponse(Request request, String proxyIp, int proxyPort) {
        try {
            OkHttpClient httpClient = HttpClient.createClient(request, proxyIp, proxyPort);
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response;
            }
        } catch (SocketTimeoutException e1){
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建连接端
     * @param request
     * @param proxyIp
     * @param proxyPort
     * @return
     */
    private static final OkHttpClient createClient(Request request, String proxyIp, int proxyPort){

        OkHttpClient.Builder build;
        if(request.url().isHttps()){
            build = new OkHttpClient.Builder().sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
        }else{
            build = new OkHttpClient.Builder();
        }
        if(proxyIp != null && proxyIp.length() > 0){
            build.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort)));
        }
        OkHttpClient httpClient = build.build();

        return  httpClient;
    }

    /**
     *
     * @param method
     * @param headerMap
     * @param url
     * @param requestBody
     * @return
     */
    private static Request createRequest(Method method, Map<String, String> headerMap, String url, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headerMap != null && headerMap.size() > 0) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        switch (method){
            case GET:{
                builder.get();
            }
            break;
            case POST:{
                builder.post(requestBody);
            }
            break;
            case PUT:{
                builder.put(requestBody);
            }
            break;
            case DELETE:{
                builder.delete(requestBody);
            }
            break;
        }
        Request request = builder.build();
        return request;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}