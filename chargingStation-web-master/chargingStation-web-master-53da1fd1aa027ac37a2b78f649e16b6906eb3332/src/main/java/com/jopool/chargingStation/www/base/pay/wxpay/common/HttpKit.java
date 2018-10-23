package com.jopool.chargingStation.www.base.pay.wxpay.common;

import com.jopool.chargingStation.www.base.pay.wxpay.response.Attachment;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: xiaomao
 * Date: 15-5-27
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 * https 请求 微信味https的请求
 */
public class HttpKit {
    private static Logger logger = LoggerFactory.getLogger(HttpKit.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * http get 请求
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParameter(key, params.get(key));
            }
        }

        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, params.get(key));
            }
        }

        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    /**
     * @param url
     * @param params
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String get(String url, Map<String, String> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, params, null);
    }

    /**
     * @param url
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String get(String url) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, null);
    }

    /**
     * http post请求
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String post(String url, Map<String, String> params) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addParameter(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    /**
     * 下载资源
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Attachment download(String url) throws ExecutionException, InterruptedException, IOException {
        Attachment att = new Attachment();
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        Future<Response> f = builder.execute();
        if (f.get().getContentType().equalsIgnoreCase("text/plain")) {
            att.setError(f.get().getResponseBody(DEFAULT_CHARSET));
        } else {
            BufferedInputStream bis = new BufferedInputStream(f.get().getResponseBodyAsStream());
            String ds = f.get().getHeader("Content-disposition");
            String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
            String relName = fullName.substring(0, fullName.lastIndexOf("."));
            String suffix = fullName.substring(relName.length() + 1);

            att.setFullName(fullName);
            att.setFileName(relName);
            att.setSuffix(suffix);
            att.setContentLength(f.get().getHeader("Content-Length"));
            att.setContentType(f.get().getContentType());
            att.setFileStream(bis);
        }
        http.close();
        return att;
    }

    public static String post(String url, String s) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        builder.setBody(s);
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    public static void main(String[] args) throws Exception {
        //参考车主微信模块或维修店微信模块里面获取用户信息的测试用例
        HttpKit.download("http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLQzfo8HAZWYYuiaiaFvIbTPxZMMRhK9ajmyI6R7RF4D5jrT6NuSa0rzLphTpGKibNQDmsr0juODFJUg/0");
    }

}
