package org.wlpiaoyi.utile;

import lombok.Data;
import org.wlpiaoyi.utile.exception.BusinessException;

public class ResponseUtile {

    @Data
    public static class ResponseData{

        private int code;

        private String message;

        private Exception exception;

        private Object data;

    }

    public static ResponseData getResponseSuccess(Object data){;
        return ResponseUtile.getResponse(200,"success", null,data);
    }

    public static  ResponseData getResponseData(int code, Object data){
        return ResponseUtile.getResponse(code, null, null, data);
    }
    public static  ResponseData getResponseMessage(int code, String message){
        return ResponseUtile.getResponse(code, message, null, null);
    }

    public static  ResponseData getResponseException(Exception e){
        if(e instanceof BusinessException){
            BusinessException bEx = (BusinessException) e;
            return ResponseUtile.getResponse(bEx.getCode(), bEx.getMessage(), null, null);
        }else{
            return ResponseUtile.getResponse(500, null, e, null);
        }
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
