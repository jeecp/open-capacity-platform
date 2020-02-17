package com.open.capacity.concurrency;

 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * static属性线程不安全
 */
@Slf4j
public class Test28 {

	//请求总数
	private static int requestTotal = 100;
	//并发总数
	public static int concurrencyTotal = 10;
	//计数器
	public static int count = 0;

	private static final String BASE_URL="http://127.0.0.1:9200/api-user/users?page=1&limit=10";
	private static final String TOKEN_URL = "http://127.0.0.1:9200/api-auth/oauth/user/token";
	
	static{
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
	}
	
	public static void invokeToken() throws Exception {
		 HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		// http POST
		// HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpPost httpPost = new HttpPost(TOKEN_URL);
		// HttpGet httpGet = new HttpGet(url);
		// httpPost.addHeader("Authorization", "Basic cGljYzpzZWNyZXQ=");//
//		CredentialsProvider provider = new BasicCredentialsProvider();
//		AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
//		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("webApp", "webApp");
//		provider.setCredentials(scope, credentials);

		
		httpPost.addHeader("client_id" ,"webApp");
		httpPost.addHeader("client_secret" ,"webApp");
		
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("username", "admin"));
		formParams.add(new BasicNameValuePair("password", "admin"));
		HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
		httpPost.setEntity(entity);
//		httpClientBuilder.setDefaultCredentialsProvider(provider);
		CloseableHttpClient httpClient   = httpClientBuilder.build();
		CloseableHttpResponse response = null;
		try {

			response = httpClient.execute(httpPost);

			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			JSONObject  jsonObject = JSONObject.parseObject(content);
			String access_token   = jsonObject.getString("access_token");
			
			log.info("得到:access_token : " + access_token);
			
			
			String userInfo = doAnotherGet(access_token);
			log.info("用户信息: " + userInfo);
			
		} finally {
			if (response != null) {
				response.close();
			}

			httpClient.close();

		}
		
	}
	private static String doAnotherGet(String accessToktn) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建http POST请求
		HttpGet httpGet = new HttpGet(BASE_URL);
		httpGet.addHeader("Authorization", "Bearer " + accessToktn);

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpGet);
			// 判断返回状态是否为200

			String content = EntityUtils.toString(response.getEntity(), "UTF-8");

			return content;
		} finally {
			if (response != null) {
				response.close();
			}

			httpClient.close();
		}
	}

	public static void main(String[] args) {
		
		try {
			ExecutorService executorService = Executors.newCachedThreadPool();

			final Semaphore semaphore = new Semaphore(concurrencyTotal);

			final CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
			
			IntStream.rangeClosed(1, requestTotal).forEach( (i) -> {

			
				executorService.execute(()->{
					
					try {
						semaphore.acquire();
						invokeToken();
						semaphore.release();
					} catch (Exception e) {
						log.error("log exception : {}", e.getMessage());
					}
					countDownLatch.countDown();
					
					
					
				});
				
			
			} );
			
			countDownLatch.await();
			executorService.shutdown();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
