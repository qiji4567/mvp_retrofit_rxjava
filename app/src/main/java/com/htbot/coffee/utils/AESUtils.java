package com.htbot.coffee.utils;

import com.blankj.utilcode.util.ArrayUtils;
import com.htbot.coffee.application.MyApplication;
import com.htbot.coffee.mvp.module.MakingOrderBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static final String CHAR_ENCODING = "UTF-8";

    public static final String AES_ALGORITHM = "AES";
    private static final int KEY_SIZE = 128; // AES-128位

    private static final String key = "UntObXo46Hp4YRJh";
    private static final String iv = "UntObXo46Hp4YRJh";

    /**
     * @Description 加密
     * @Author
     * @Date
     */
    public static String encrypt(String data) {
        return encrypt(data, key);
    }

    public static void main(String[] args) {
//        System.out.println(encrypt("123123"));
    }

    /**
     * @Description 解密
     * @Author
     * @Date
     */
    public static String decrypt(String data) {
        return decrypt(data, key);
    }

    /**
     * 加密
     *
     * @param data 待加密内容
     * @param key  加密秘钥
     * @return 十六进制字符串
     */
    public static String encrypt(String data, String key) {
        if (key.length() < 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        } else if (key.length() > 16) {
            key = key.substring(0, 16);
        }
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_ALGORITHM);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器

            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());

            byte[] byteContent = data.getBytes(CHAR_ENCODING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);// 初始化
            byte[] result = cipher.doFinal(byteContent);

            String encode = Base64.getEncoder().encodeToString(result);

            return encode.replace("\r\n", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param data 待解密内容(十六进制字符串)
     * @param key  加密秘钥
     * @return
     */
    public static String decrypt(String data, String key) {
        if (key.length() < 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        } else if (key.length() > 16) {
            key = key.substring(0, 16);
        }
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);// 初始

            byte[] encrypted1 = Base64.getDecoder().decode(data);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "UTF-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建加密解密密钥
     *
     * @param key 加密解密密钥
     * @return
     */
    private static SecretKeySpec genKey(String key) {
        SecretKeySpec secretKey;
        try {
            secretKey = new SecretKeySpec(key.getBytes(CHAR_ENCODING), AES_ALGORITHM);
            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, AES_ALGORITHM);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("genKey fail!", e);
        }
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 使用HMAC-MD5生成签名
     */
    public static String generateSig(String jsonData, String serialNumber) {
        try {

            String data = serialNumber + AESUtils.encrypt(jsonData);

            // 使用HMAC-MD5算法生成签名
            String secretKey = "IWEklmQVNi6rKY4Q";
            Mac mac = Mac.getInstance("HmacMD5");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacMD5");
            mac.init(secretKeySpec);
            byte[] result = mac.doFinal(data.getBytes("UTF-8"));

            // 将签名结果转为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createRequestOperateBodyList(List<MakingOrderBean> makingOrderBeanList) {
        // 创建 Data 对象
        JSONObject dataJson = new JSONObject();
        JSONObject dataObject = new JSONObject();

        try {
            // 创建嵌套的 data 对象
            // 创建一个JSONArray来存放订单信息
            JSONArray listArray = new JSONArray();

            // 遍历 makingOrderBeanList，生成符合格式的订单对象
            for (MakingOrderBean order : makingOrderBeanList) {
                JSONObject orderJson = new JSONObject();
                orderJson.put("corpId", MyApplication.instance.getCorpId() + ""); // 假设 corpId 为 2
                orderJson.put("orderId", order.getOrderId()); // 获取订单ID
                orderJson.put("orderSubId", order.getOrderSubId()); // 获取订单子ID
                // 将订单信息加入 list 数组
                listArray.put(orderJson);
            }
            // 将 listArray 添加到 dataObject 中
            dataObject.put("list", listArray);
            // 将 dataObject 添加到 dataJson 中
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 返回封装好的 JSON 字符串
        return dataObject.toString();
    }

    public static String createRequestOperateBodyNoData(HashMap<String, Object> postMap) {
        // 创建 Data 对象
        JSONObject dataJson = new JSONObject();
        try {
            // 创建嵌套的 data 对象
            JSONObject dataObject = new JSONObject();

            // 将 postMap 中的内容添加到 dataObject 中
            for (Map.Entry<String, Object> entry : postMap.entrySet()) {
                dataObject.put(entry.getKey(), entry.getValue());
            }
            dataJson.put("data", dataObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // 返回封装好的 JSON 字符串
        return dataJson.toString();
    }

    public static String createRequestOperateBody(Map<String, Object> postMap) {
        // 创建 Data 对象
        JSONObject dataJson = new JSONObject();
        try {
            // 创建嵌套的 data 对象
            JSONObject dataObject = new JSONObject();
            for (Map.Entry<String, Object> entry : postMap.entrySet()) {
                Object value = entry.getValue();
                if (value.getClass().isArray()) {
                    if (value instanceof int[]) {
                        dataObject.put(entry.getKey(), new JSONArray(Arrays.asList(ArrayUtils.toObject((int[]) value))));
                    } else {
                        dataObject.put(entry.getKey(), new JSONArray(Arrays.asList(value)));
                    }
                } else {
                    dataObject.put(entry.getKey(), value);
                }
            }
//            // 将 postMap 中的内容添加到 dataObject 中
//            for (Map.Entry<String, Object> entry : postMap.entrySet()) {
//                dataObject.put(entry.getKey(), entry.getValue());
//            }
            if (!dataObject.has("data")) {
                dataObject.put("data", new JSONObject()); // 空的 data 对象
            }
            // 将 dataObject 放入 dataJson 中
            dataJson.put("data", dataObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 返回封装好的 JSON 字符串
        return dataJson.toString();
    }

    public static String createRequestBody(Map<String, Object> postMap) {
        // 创建 Data 对象
        JSONObject dataJson = new JSONObject();
        try {
            String postString = createPostData(postMap);
            String sig = generateSig(postString, MyApplication.instance.getSerialNumber());
            dataJson.put("data", AESUtils.encrypt(postString));
            dataJson.put("serialNumber", MyApplication.instance.getSerialNumber());
            dataJson.put("timeStamp", System.currentTimeMillis());
            dataJson.put("sig", sig);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // 返回封装好的 JSON 字符串
        return dataJson.toString();
    }

    public static String createPostData(Map<String, Object> postMap) {
        JSONObject dataJson = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : postMap.entrySet()) {
                Object value = entry.getValue();
                if (value.getClass().isArray()) {
                    if (value instanceof int[]) {
                        dataJson.put(entry.getKey(), new JSONArray(Arrays.asList(ArrayUtils.toObject((int[]) value))));
                    } else {
                        dataJson.put(entry.getKey(), new JSONArray(Arrays.asList(value)));
                    }
                } else {
                    dataJson.put(entry.getKey(), value);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return dataJson.toString();
    }

    public static String encryptUserAndPwd(String data, String publicKeyString) {
        try {
            String cleanKeyString = publicKeyString
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "")
                    .replaceAll("[^a-zA-Z0-9+/=]", "");
            if (cleanKeyString.isEmpty()) {
                throw new IllegalArgumentException("清理后的公钥字符串为空，无法进行 Base64 解码。请检查原始公钥内容。");
            }
            byte[] keyBytes = Base64.getDecoder().decode(cleanKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(keySpec));
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
