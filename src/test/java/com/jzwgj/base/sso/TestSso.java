package com.jzwgj.base.sso;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
 


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
 
public class TestSso {
        private static DefaultHttpClient  httpClient = new DefaultHttpClient();
        private static boolean casLogined = false;
        public final static String CAS_DOMAIN = "https://g-jia.cn:8443/cas";
        public String appCallback = null;
        private boolean isLogined = false;
 
        public static void main(String[] args) {
        	TestSso testSso = new TestSso("http://192.168.1.122:28080/j_spring_cas_security_check");
        	try {
				testSso.login2("13185062365", "123456");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        public TestSso(String appCallback) {
                this.appCallback = appCallback;
        }
        
    	@SuppressWarnings("deprecation")
    	public String login2(String username, String password) throws Exception {
                    String service = appCallback;
                    String params = "username=" + URLEncoder.encode(username)
                                    + "&password=" + URLEncoder.encode(password) + "&service=" + service
                                    + "&method=json";
                    byte[] entitydata = params.getBytes();
                    HttpURLConnection c = (HttpURLConnection) new URL("https://g-jia.cn:8443/cas/login") 
                                    .openConnection();
                    c.setInstanceFollowRedirects(false);
                    c.setRequestMethod("POST");
                    c.setDoOutput(true);
                    c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    c.setRequestProperty("Content-Length",String.valueOf(entitydata.length));
                    
                    StringBuffer bankXmlBuffer=new StringBuffer();
                    
                    OutputStream outStream = c.getOutputStream();
                    outStream.write(entitydata);
                    outStream.flush();
                    outStream.close();
                    
                    BufferedReader in = null;
                    int responeCode = -1;
                    try {
                       responeCode = c.getResponseCode();
                       System.out.println(responeCode);
                       if (responeCode == 200) {
                    	    in = new BufferedReader(new InputStreamReader(c.getInputStream(),"utf-8"));
                           String inputLine;
                           while((inputLine=in.readLine())!=null){
                           		bankXmlBuffer.append(inputLine);
                           }
    					}
                       System.out.println(bankXmlBuffer.toString());
                       return bankXmlBuffer.toString();
                    } catch (java.io.IOException e) {
                         if (e.getMessage().contains("authentication challenge")) {
                              responeCode = 401;
                         } else {
                              throw e;
                        }
                    } finally{
                    	if (null != in) {
                           	in.close();
    					}
                    }
                  return "false";
            }

 
        public boolean login(String username, String password) throws Exception {
 
                String service = appCallback;
                String lt = getLt();
                String loginApi = CAS_DOMAIN + "/login";
                String params = "username=" + URLEncoder.encode(username)
                                + "&password=" + URLEncoder.encode(password) + "&service=" + service;
                byte[] entitydata = params.getBytes();
                HttpURLConnection c = (HttpURLConnection) new URL(loginApi)
                                .openConnection();
                c.setInstanceFollowRedirects(false);
                c.setRequestMethod("POST");
                c.setDoOutput(true);
                c.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                c.setRequestProperty("Content-Length",
                                String.valueOf(entitydata.length));
                OutputStream outStream = c.getOutputStream();
                outStream.write(entitydata);
                outStream.flush();
                outStream.close();
                int responeCode = -1;
                try {
                        responeCode = c.getResponseCode();
                        System.out.println(responeCode);
                } catch (java.io.IOException e) {
                        if (e.getMessage().contains("authentication challenge")) {
                                responeCode = 401;
                        } else {
                                throw e;
                        }
                }
                if (responeCode == 303) {
                        get(c.getHeaderField("Location"));
                        isLogined = true;
                        casLogined = true;
                        return true;
                } else {
                        isLogined = false;
                        casLogined = true;
                        return false;
                }
        }
 
        public boolean isLogined() {
                return isLogined;
        }
 
        public static boolean casLogined() {
                return casLogined;
        }
 
        public static DefaultHttpClient  getHttpClient() {
                return httpClient;
        }
 
        private static String getLt() throws IOException {
                String httpUrl = CAS_DOMAIN + "/loginTicket";
                HttpPost httpPost = new HttpPost(httpUrl);
                HttpResponse httpRespone = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpRespone.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                                inputStream));
                String str = "";
                String line = null;
                while ((line = reader.readLine()) != null) {
                        str += line;
                }
                return str;
        }
 
        private static String get(String httpUrl) throws Exception {
                HttpGet httpGet = new HttpGet(httpUrl);
                InputStream inputStream = null;
                String bodyStr = "";
                HttpResponse httpRespone = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpRespone.getEntity();
                inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                                inputStream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                        bodyStr += line;
                }
                return bodyStr;
        }
}