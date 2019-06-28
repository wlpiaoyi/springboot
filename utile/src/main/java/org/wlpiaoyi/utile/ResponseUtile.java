package org.wlpiaoyi.utile;

import lombok.Data;

public class ResponseUtile {

    @Data
    public static class ResponseData{

        private int code;

        private String message;

        private Exception exception;

        private Object data;

    }

    public static ResponseData getResponseSuccess(Object data){;
        return ResponseUtile.getResponse(0,"success", null,data);
    }

    public static  ResponseData getResponseMessage(int code, String message){
        return ResponseUtile.getResponse(code, message, null, null);
    }

    public static  ResponseData getResponseException(int code, String message, Exception e){
        return ResponseUtile.getResponse(code, message, e, null);
    }

    public static ResponseData getResponse(int code, String message, Exception e, Object data){
        ResponseData responseData = new ResponseData();
        responseData.code = code;
        responseData.message = message;
        responseData.exception = e;
        responseData.data = data;
        return responseData;
    }

}
