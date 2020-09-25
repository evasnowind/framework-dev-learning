package com.prayerlaputa.springaop.core;

import java.io.Serializable;

/**
 * 通用响应对象
 */
public class CommonResp implements Serializable {

    private int code;

    private String msg;

    private Object data;

    public static CommonResp success(Object data) {
        CommonResp r = new CommonResp();
        r.code = 0;
        r.msg = "";
        r.data = data;

        return r;
    }

    public static CommonResp success() {
        CommonResp r = new CommonResp();
        r.code = 0;
        r.msg = "";
        r.data = null;

        return r;
    }

    public static CommonResp fail(int resultCode) {
        CommonResp r = new CommonResp();
        r.code = resultCode;
        r.msg = "";
        r.data = null;

        return r;
    }

    public static CommonResp fail(int resultCode, String errorMsg) {
        CommonResp r = new CommonResp();
        r.code = resultCode;
        r.msg = errorMsg;
        r.data = null;

        return r;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
