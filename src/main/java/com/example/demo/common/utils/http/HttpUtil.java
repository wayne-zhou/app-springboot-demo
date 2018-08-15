package com.example.demo.common.utils.http;


import com.example.demo.common.utils.JsonUtil;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtil {

	protected static Logger loggerg = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * Http request ：Post
	 *
	 * @param requestObject
	 * @param url
	 * @return result map
	 */
	public static String postHttpRequest(String url, Object requestObject) throws Exception{
		return postHttpRequest(url, requestObject, 60 * 1000);
	}

	/**
	 * Http request ：Post
	 *
	 * @param requestObject
	 * @param url
	 * @param timeOut (Millisecond)
	 * @return result map
	 */
	public static String postHttpRequest(String url, Object requestObject, Integer timeOut) throws Exception{
		CloseableHttpResponse response = null;
		try {
			String reqeustString = "[]";
			if (requestObject != null) {
				reqeustString= JsonUtil.objectToJson(requestObject);
			}
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			// set Timeout
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut)
					.setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new StringEntity(reqeustString, ContentType.APPLICATION_JSON));
			response = httpclient.execute(httpPost);
			// get http status code
			int resStatu = response.getStatusLine().getStatusCode();
			String responseString = null;
			if (resStatu == HttpStatus.SC_OK) {
				responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			} else {
				loggerg.info(url + ", Post invoke fail, response status: " + resStatu);
			}
			return responseString;
		} catch (Exception e) {
			loggerg.error("Error", e);
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
					response.close();
				} catch (Exception e) {
					loggerg.error("Error", e);
				}
			}
		}
	}

}
