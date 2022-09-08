package cn.mvp.network.net.converter;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import cn.mvp.network.net.exception.NoDataExceptionException;
import cn.mvp.network.net.exception.RefreshTokenExpiredException;
import cn.mvp.network.net.exception.ServerResponseException;
import cn.mvp.network.net.exception.TokenExpiredException;
import cn.mvp.network.net.module.BaseBean;
import cn.mvp.network.utils.GsonUtils;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static cn.mvp.network.net.common.ErrorCode.INVALID_LOGIN_STATUS;
import static cn.mvp.network.net.common.ErrorCode.REFRESH_TOKEN_EXPIRED;
import static cn.mvp.network.net.common.ErrorCode.SUCCESS;

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String json = value.string();
        //第一次解析
        BaseBean response = GsonUtils.json2Bean(json, BaseBean.class);
        if (response.getCode() == SUCCESS) {
            if (response.getData() == null)
                throw new NoDataExceptionException(response.getCode(),response.getMsg());
            T result = adapter.fromJson(json);
            return result;
        } else if (response.getCode() == REFRESH_TOKEN_EXPIRED) {
            throw new TokenExpiredException(response.getCode(), response.getMsg());
        } else if (response.getCode() == INVALID_LOGIN_STATUS) {
            throw new RefreshTokenExpiredException(response.getCode(), response.getMsg());
        } else if (response.getCode() != SUCCESS) {
            // 特定 API 的错误，在相应的 DefaultObserver 的 onError 的方法中进行处理
            throw new ServerResponseException(response.getCode(), response.getMsg());
        }
        return null;
    }
}
