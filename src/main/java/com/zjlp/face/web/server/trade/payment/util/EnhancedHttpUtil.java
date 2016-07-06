package com.zjlp.face.web.server.trade.payment.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public class EnhancedHttpUtil {

	static Logger log = Logger.getLogger(EnhancedHttpUtil.class);
	
	private static HttpClient httpClient;
	
	private static TrustManager truseAllManager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	/**
	 * 获得非线程安全的HttpClient对象
	 * 
	 * @return
	 */
	/*public static HttpClient getClient(){
		if (null == httpClient) {
			httpClient = new DefaultHttpClient();
			ClientConnectionManager mgr = httpClient.getConnectionManager();  
			httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager( 
					httpClient.getParams(),mgr.getSchemeRegistry()),httpClient.getParams()); 
			httpClient.getParams().setParameter(ConnManagerPNames.TIMEOUT, 1000);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 4000);
		}
		return httpClient;
	}*/
	
	/**
	 * 获得线程安全的HttpClient对象，能够适应多线程环境
	 * 
	 * @return
	 */
	public static synchronized HttpClient getHttpClient() {
		if (null == httpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "UTF-8");
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, 1000);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(params, 1000);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 4000);

			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			try {
				SSLContext sslcontext = SSLContext.getInstance("TLS");
				sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
				SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				schReg.register(new Scheme("https", sf, 443));
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			httpClient = new DefaultHttpClient(conMgr, params);
		}
		return httpClient;
	}

	/**
	 * 获得Post请求对象
	 * 
	 * @param uri
	 *            请求地址，也可以带参数
	 * @param params
	 *            如果为null，则不添加由BasicNameValue封装的参数
	 * @return
	 */
	public static HttpPost getPost(String uri, List<BasicNameValuePair> params) {
		HttpPost post = new HttpPost(uri);
		try {
			if (params != null) {
				post.setEntity(new UrlEncodedFormEntity(params));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post;
	}
	
	public static HttpPost getPost(String uri, String params) {
		HttpPost post = new HttpPost(uri);
		try {
			post.setEntity(new StringEntity(params, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}
	
	/**
	 * 获得Get请求对象
	 * 
	 * @param uri
	 *            请求地址，也可以带参数
	 * @return
	 */
	public static HttpGet getGet(String uri) {
		HttpGet get = new HttpGet(uri);
		return get;
	}

	/**
	 * 用户使用的方法 功能：从服务器获得字符串
	 * 
	 * @param get
	 * @return
	 */
	public static String getString(HttpGet get) {

		HttpClient httpClient = getHttpClient();
		HttpResponse response;
		try {
			response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				get.abort();
				log.info("响应失败,请求终止.");
				return null;
			}
			log.info( "响应成功.");
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.info( e.getMessage());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			log.info( e.getMessage());
			return null;
		}
	}
	
	/**
	 * 用户使用的方法 功能：从服务器获得字符串
	 * 
	 * @param post
	 * @return
	 */
	public static String getString(HttpPost post) {

		HttpClient httpClient = getHttpClient();
		HttpResponse response;
		try {
			response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				post.abort();
				log.info("响应失败,请求终止.");
				return null;
			}
			log.info( "响应成功.");
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.info( e.getMessage());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			log.info( e.getMessage());
			return null;
		}
	}
	
	/**
	 * 用户使用的方法 功能：请求服务器，返回字符串
	 * 
	 * @param post
	 *            post 请求对象
	 * @param requestLimit
	 *            请求失败限制次数
	 * @return
	 */
	public static String getString(HttpPost post, int requestLimit) {

		if (requestLimit < 1) {
			return null;
		}
		HttpResponse response;
		int currCount = 0; // 当前请求次数
		String result = null;

		while (currCount < requestLimit) {

			HttpClient httpClient = getHttpClient();
			currCount++;
			try {
				response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					log.info( "响应成功.");
					return EntityUtils.toString(response.getEntity());
				} else {
					post.abort();
					log.info( "响应失败,请求终止.");
					result = "响应失败,请求终止.";
				}
			} catch (ClientProtocolException e) {
				log.info( e.getMessage());
				if (currCount > requestLimit) {
					result = "请求失败.";
					break;
				}
				System.out.println("ClientProtocolException");
			} catch (IOException e) {
				log.info( e.getMessage());
				if (e instanceof ConnectTimeoutException) {
					result = "连接超时.";
				} else {
					result = "IO异常.";
				}
				if (currCount > requestLimit) {
					break;
				}
				System.out.println("IOException");
			} finally {
				System.out.println("finally");
			}
		}
		return result;
	}
}