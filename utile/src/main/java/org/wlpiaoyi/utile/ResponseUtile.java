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

    public static ResponseData success(Object data){;
        return ResponseUtile.returnResponse(0,null, null,data);
    }

    public static  ResponseData message(int code, String message){
        return ResponseUtile.returnResponse(code, message, null, null);
    }


    public static  ResponseData exception(int code, Exception e){
        return ResponseUtile.returnResponse(code, null, e, null);
    }

    public static ResponseData returnResponse(int code, String message, Exception e, Object data){
        ResponseData responseData = new ResponseData();
        responseData.code = code;
        responseData.message = message;
        responseData.exception = e;
        responseData.data = data;
        return responseData;
    }

}
