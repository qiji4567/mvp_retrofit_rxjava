package cn.mrr.mvp.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * =================================================
 *
 * @author qi ji
 * @date 2021/8/23 11:10
 * @Email: 534438777@qq.com
 * @Content: =================================================
 */
public class IPLocationBean implements Serializable {


    /**
     * code : 0
     * message : 成功
     * data : {"provinceName":"浙江省","provinceCode":"320000","areaCode":"322100","areaName":"杭州市"}
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DataBean data;


    @Override
    public String toString() {
        return "IPLocationBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static IPLocationBean objectFromData(String str) {

        return new Gson().fromJson(str, IPLocationBean.class);
    }

    public static class DataBean implements Serializable {
        /**
         * provinceName : 浙江省
         * provinceCode : 320000
         * areaCode : 322100
         * areaName : 杭州市
         */

        @SerializedName("provinceName")
        public String provinceName;
        @SerializedName("provinceCode")
        public String provinceCode;
        @SerializedName("areaCode")
        public String areaCode;
        @SerializedName("areaName")
        public String areaName;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "provinceName='" + provinceName + '\'' +
                    ", provinceCode='" + provinceCode + '\'' +
                    ", areaCode='" + areaCode + '\'' +
                    ", areaName='" + areaName + '\'' +
                    '}';
        }
    }
}
 
 