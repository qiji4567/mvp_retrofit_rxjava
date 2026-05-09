package cn.mrr.liubei.net;

public class ApiResponse<T> {

    private int code;
    private String msg;
    private String message;
    private T data;

    public boolean isSuccess() {
        return code == 0 || code == 200;
    }

    public String getDisplayMessage() {
        if (msg != null && msg.length() > 0) {
            return msg;
        }
        if (message != null && message.length() > 0) {
            return message;
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}