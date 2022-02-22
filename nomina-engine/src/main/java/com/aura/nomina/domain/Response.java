package com.aura.nomina.domain;

public class Response {
    //private String result; //DB result

    private String sysMsg; //DB exception (if any)
    private Integer sysCode; //DB err code

    private String userMsg; //Translated code

    /**
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }*/

    public String getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(String sysMsg) {
        this.sysMsg = sysMsg;
    }

    public Integer getSysCode() {
        return sysCode;
    }

    public void setSysCode(Integer sysCode) {
        this.sysCode = sysCode;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public Response(//String result,
                    String sysMsg, Integer sysCode, String userMsg) {
        //this.result = result;
        this.sysMsg = sysMsg;
        this.sysCode = sysCode;
        this.userMsg = userMsg;
    }

    public Response() {
    }
}
