package com.example.demo.common.utils.http;

import com.example.demo.common.utils.JsonUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouwei on 2018/3/15
 **/
public class PoolingHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(PoolingHttpUtil.class);

    //最大连接数
    private static final int CONNECT_TOTAL = 50;
    //每个路由的基础连接数
    private static final int CONNECT_ROUTE = 50;
    //超时时间 默认
    private static final Integer DEFAULT_TIME_OUT = 30 * 1000;
    //连接池获取连接时间
    private static final int GET_CONNECT_TIME_OUT = 2 * 1000;
    //清理连接池中空闲连接存活的时间
    private static final int CLEAR_ALIVE_TIME = 60*1000;

    private static PoolingHttpClientConnectionManager poolConnManager;

    private static ConnectionMonitorThread monitorThread;

    private static void init() throws Exception{
        logger.info("PoolingHttpUtil initialize start……");
        //支持SSL
        SSLContext sslcontext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true; //信任所有
            }
        }).build();
        SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslcontext,
                new String[] { "TLSv1", "TLSv2", "TLSv3" }, null, NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sf).build();

        //初始化连接池
        poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 最大连接数
        poolConnManager.setMaxTotal(CONNECT_TOTAL);
        // 每个路由的最大连接数
        poolConnManager.setDefaultMaxPerRoute(CONNECT_ROUTE);
        //清理线程
        monitorThread = new ConnectionMonitorThread(poolConnManager, CLEAR_ALIVE_TIME);
        monitorThread.start();
    }

    /**
     * 创建并返回请求连接
     */
    private static CloseableHttpClient getClient() throws Exception{
        if(poolConnManager == null){
            init();
        }
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager).build();
        if(poolConnManager !=null && poolConnManager.getTotalStats() != null) {
            logger.debug("poolConnManager ："+poolConnManager.getTotalStats().toString());
        }
        return httpClient;
    }

    /**
     * 获取超时时间
     */
    private static RequestConfig getTimeOutRequestConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout( DEFAULT_TIME_OUT)
                .setConnectionRequestTimeout(GET_CONNECT_TIME_OUT)
                .setSocketTimeout(DEFAULT_TIME_OUT)
                .build();
        return requestConfig;
    }

    /**
     * 提交Post请求
     */
    public static String doPost(String url, Object param) throws Exception {

        CloseableHttpResponse resp = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(JsonUtil.objectToJson(param), ContentType.APPLICATION_JSON) );
            // 设置超时时间
            httpPost.setConfig(getTimeOutRequestConfig());
            // 提交请求
            resp = getClient().execute(httpPost);
            // 响应状态
            int respStatus = resp.getStatusLine().getStatusCode();
            if (respStatus >= 300) {
                logger.info(url + "Post invoke fail, response status: " + respStatus);
            }
            // 响应结果
           return EntityUtils.toString(resp.getEntity(), "UTF-8");
        } finally {
            if (resp != null) {
                try {
                    EntityUtils.consume(resp.getEntity());
                    resp.close();
                } catch (Exception e) {
                    logger.error("close httpResponse fail", e);
                }
            }
        }
    }

    /**
     * 销毁
     */
    public static void destory(){
        monitorThread.shutdown();
        poolConnManager.shutdown();
    }


    static class ConnectionMonitorThread extends Thread{
        final HttpClientConnectionManager connMgr;
        int idleTime;
        boolean shutdown = false;

        ConnectionMonitorThread(HttpClientConnectionManager connMgr, int idleTime) {
            this.connMgr = connMgr;
            this.idleTime = idleTime;
        }

        public void shutdown() {
            shutdown = true;
        }

        @Override
        public void run() {
            while (!shutdown) {
                try {
                    synchronized (connMgr) {
                        //每2秒清理一次连接池
                        Thread.sleep(2000);
                        //关闭过期连接
                        connMgr.closeExpiredConnections();
                        //关闭空闲的连接
                        connMgr.closeIdleConnections(idleTime, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
