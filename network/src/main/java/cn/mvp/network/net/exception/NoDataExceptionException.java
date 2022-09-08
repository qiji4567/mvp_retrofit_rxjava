package cn.mvp.network.net.exception;

import android.text.TextUtils;

/**
 * 服务器返回的异常
 */
public class NoDataExceptionException extends BaseException {
    public NoDataExceptionException(int code,String msg) {
        super(code, !TextUtils.isEmpty(msg) ? msg : "服务器没有返回对应的Data数据");
    }
}
