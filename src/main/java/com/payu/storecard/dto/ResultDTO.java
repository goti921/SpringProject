package com.payu.storecard.dto;

import java.util.Map;

/**
 * Created by simrandeep.singh on 9/25/16.
 */
public class ResultDTO {
    private Integer status;
    private String message;
    private Object result;
    private Integer errorCode;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ResultDTO(Integer status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ResultDTO(Integer status, String message, Object result, Integer errrorCode) {
        this.status = status;
        this.message = message;
        this.result = result;
        this.errorCode = errrorCode;
    }

    public ResultDTO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultDTO(Map<String, Object> map) {
        this.status = Integer.parseInt(map.get("status").toString());
        if (map.get("message") != null) {
            this.message = map.get("message").toString();
        }
        if (map.get("result") != null) {
            this.result = map.get("result");
        }
    }

    /**
     * @return the errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
