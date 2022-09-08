package cn.mvp.network.net.exception;


import cn.mvp.network.net.common.ErrorCode;

/**
 * <pre>
 *   Created by qiji on 2021/1/6.
 *   Description:
 * </pre>
 */
public class BaseException extends RuntimeException {

    protected final int errorCode;

    public BaseException(int errorCode, String cause) {
        super(ErrorCode.getErrorMessage(errorCode, cause), new Throwable(cause));
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
