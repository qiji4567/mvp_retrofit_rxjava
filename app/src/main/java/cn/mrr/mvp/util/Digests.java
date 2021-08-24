//package cn.mrr.mvp.util;//我用的是commons-codec-1.9.jar包
//import org.apache.commons.codec.binary.Hex;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.security.MessageDigest;
//import java.security.SecureRandom;
//
//
///**
// * 转载自百度 https://www.iteye.com/blog/fengjianrong-2379764
// */
//public class Digests {
//    /**
//     * 加密遵循RFC2671规范
//     * 将相关参数加密生成一个MD5字符串,并返回
//     */
//    public static String http_da_calc_HA1(String username, String realm, String password,
//                                   String nonce, String nc, String cnonce, String qop,
//                                   String method, String uri, String algorithm){
//        String HA1,HA2;
//        if ("MD5-sess".equals(algorithm)){
//            HA1 = HA1_MD5_sess(username,realm,password,nonce,cnonce);
//        } else{
//            HA1 = HA1_MD5(username,realm,password);
//        }
//        byte[] md5Byte = md5(HA1.getBytes());
//        HA1 = new String(Hex.encodeHex(md5Byte));
//
//        md5Byte = md5(HA2(method, uri).getBytes());
//        HA2 = new String(Hex.encodeHex(md5Byte));
//
//        String original = HA1 + ":" + (nonce +":"+ nc +":"+ cnonce +":"+ qop) + ":" + HA2;
//
//        md5Byte = md5(original.getBytes());
//        return new String(Hex.encodeHex(md5Byte));
//
//    }
//
//    /**
//     * algorithm值为MD5时规则
//     */
//    private static String HA1_MD5(String username, String realm, String password){
//        return username+":"+realm+":"+password;
//    }
//
//    /**
//     * algorithm值为MD5-sess时规则
//     */
//    private static String HA1_MD5_sess(String username, String realm, String password, String nonce, String cnonce){
////      MD5(username:realm:password):nonce:cnonce
//
//        String s = HA1_MD5(username, realm, password);
//        byte[] md5Byte = md5(s.getBytes());
//        String smd5 = new String(Hex.encodeHex(md5Byte));
//
//        return smd5+":"+nonce+":"+cnonce;
//    }
//
//    private static String HA2(String method, String uri){
//        return method+":"+uri;
//    }
//
//    /**
//     * 对输入字符串进行md5散列.
//     */
//    public static byte[] md5(byte[] input) {
//        return digest(input, MD5, null, 1);
//    }
//
//    /**
//     * 对字符串进行散列, 支持md5与sha1算法.
//     */
//    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance(algorithm);
//
//            if (salt != null) {
//                digest.update(salt);
//            }
//
//            byte[] result = digest.digest(input);
//
//            for (int i = 1; i < iterations; i++) {
//                digest.reset();
//                result = digest.digest(result);
//            }
//            return result;
//        } catch (GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        }
//    }
////测试
//    public static void main(String[] args) {
//        String s = http_da_calc_HA1("povodo", "realm@easycwmp", "povodo",
//                "c10c9897f05a9aee2e2c5fdebf03bb5b0001b1ef", "00000001", "d5324153548c43d8", "auth",
//                "GET", "/", "MD5");
//        System.out.println("加密后response为:"+s);
//    }
//
//
//
//
//四、客户端请求（GET方式示例）:
//Java代码  收藏代码
////我这里用的fastjson-1.1.41.jar
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.povodo.map.servlet.StringUtils;
//import org.apache.commons.codec.binary.Hex;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.List;
//import java.util.Map;
//public class HttpRequestUtils {
//
//    static int nc = 0;    //调用次数
//
//    /**
//     * 向指定URL发送GET方法的请求
//     * @param url      发送请求的URL
//     * @param param    请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//     * @param username 验证所需的用户名
//     * @param password 验证所需的密码
//     * @return URL 所代表远程资源的响应结果
//     */
//    public static String sendGet(String url, String param, String username, String password) {
//
//        StringBuilder result = new StringBuilder();
//        BufferedReader in = null;
//        try {
//
//            String wwwAuth = sendGet(url, param);       //发起一次授权请求
//            if (wwwAuth.startsWith("WWW-Authenticate:")) {
//                wwwAuth = wwwAuth.replaceFirst("WWW-Authenticate:", "");
//            } else {
//                return wwwAuth;
//            }
//
//            nc ++;
//
//            String urlNameString = url + (StringUtils.isNotEmpty(param) ? "?" + param : "");
//            URL realUrl = new URL(urlNameString);
//
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//
//            //授权信息
//            String authentication = getAuthorization(wwwAuth, realUrl.getPath(), username, password);
//            connection.setRequestProperty("Authorization", authentication);
//
//            // 建立实际的连接
//            connection.connect();
//
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//
//            nc = 0;
//        } catch (Exception e) {
//            nc = 0;
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (in != null) in.close();
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return result.toString();
//    }
//
//    /**
//     * 生成授权信息
//     * @param authorization 上一次调用返回401的WWW-Authenticate数据
//     * @param username      用户名
//     * @param password      密码
//     * @return 授权后的数据, 应放在http头的Authorization里
//     * @throws IOException 异常
//     */
//    private static String getAuthorization(String authorization, String uri, String username, String password) throws IOException {
//
//        uri = StringUtils.isEmpty(uri) ? "/" : uri;
//        String temp = authorization.replaceFirst("Digest", "").trim();
//        String json = "{\"" + temp.replaceAll("=", "\":").replaceAll(",", ",\"") + "}";
//
//        JSONObject jsonObject = JSON.parseObject(json);
//        String cnonce = new String(Hex.encodeHex(Digests.generateSalt(8)));    //客户端随机数
//        String ncstr = ("00000000" + nc).substring(Integer.toString(nc).length());     //认证的次数,第一次是1，第二次是2...
//        String algorithm = jsonObject.getString("algorithm");
//
//        String response = Digests.http_da_calc_HA1(username, jsonObject.getString("realm"), password,
//                jsonObject.getString("nonce"), ncstr, cnonce, jsonObject.getString("qop"),
//                "GET", uri, algorithm);
//
//        //组成响应authorization
//        authorization = "Digest username=\"" + username + "\"," + temp;
//        authorization += ",uri=\"" + uri
//                + "\",nc=\"" + ncstr
//                + "\",cnonce=\"" + cnonce
//                + "\",response=\"" + response+"\"";
//        return authorization;
//    }
//
//    /**
//     * 向指定URL发送GET方法的请求
//     *
//     * @param url   发送请求的URL
//     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//     * @return URL 所代表远程资源的响应结果
//     */
//    public static String sendGet(String url, String param) {
//        StringBuilder result = new StringBuilder();
//        BufferedReader in = null;
//        try {
//
//            String urlNameString = url + (StringUtils.isNotEmpty(param) ? "?" + param : "");
//            URL realUrl = new URL(urlNameString);
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//
//            connection.connect();
//
//            //返回401时需再次用用户名和密码请求
//            //此情况返回服务器的 WWW-Authenticate 信息
//            if (((HttpURLConnection) connection).getResponseCode() == 401) {
//                Map<String, List<String>> map = connection.getHeaderFields();
//                return "WWW-Authenticate:" + map.get("WWW-Authenticate").get(0);
//            }
//
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("get请求发送失败",e);
//        }
//        // 使用finally块来关闭输入流
//        finally {
//            try {
//                if (in != null) in.close();
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return result.toString();
//    }
//}