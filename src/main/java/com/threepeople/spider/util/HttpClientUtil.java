package com.threepeople.spider.util;

import com.threepeople.spider.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
@Slf4j
public class HttpClientUtil {
    private static final HttpClientBuilder httpClientBuilder = HttpClients.custom();
    static {
        /*
         一、绕过不安全的https请求的证书验证
         */
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", trustHttpsCertificates())
                .build();
        /*
          二、创建连接池
         */
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        httpClientBuilder.setConnectionManager(cm);
        /*
        三、设置请求默认配置
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(3000)
                .setConnectionRequestTimeout(5000)
                .build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        /*
        四、设置一些header
         */
        List<Header> defaultHeaders = new ArrayList<>();
        // 伪装成浏览器
        BasicHeader userAgentHeader = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
        defaultHeaders.add(userAgentHeader);
        httpClientBuilder.setDefaultHeaders(defaultHeaders);
    }

    /**
     * 构造安全连接工厂
     * @return SSLConnectionSocketFactory
     */
    private static ConnectionSocketFactory trustHttpsCertificates() {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                // 判断是否信任url
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            });
            SSLContext sslContext = sslContextBuilder.build();
            return new SSLConnectionSocketFactory(sslContext,
                    new String[]{"SSLv2Hello","SSLv3","TLSv1","TLSv1.1","TLSv1.2"}
                    ,null, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            log.error("构造安全连接工厂失败",e);
            throw new RuntimeException("构造安全连接工厂失败");
        }
    }

    /**
     * 发送get请求
     * @param url 请求url
     * @param charset 默认"UTF-8"
     * @return 返回结果字符串
     */
    public static String executeGet(String url,String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (StringUtils.isBlank(charset)) {
            charset = Constants.UTF_8;
        }
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        // 构造httpGet请求对象
        HttpGet httpGet = new HttpGet(url);
        // 可关闭的响应
        CloseableHttpResponse response = null;
        try {
            log.info("prepare to execute url:{}",url);
            response = closeableHttpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, charset);
            }else {
                log.error("响应码失败,url:{},响应码:{}",url,statusLine.getStatusCode());
            }
        }catch (Exception e) {
            log.error("executeGet error,url:{}",url,e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return null;
    }

}
