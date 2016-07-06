package com.zjlp.face.web.security.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CasLoginHttpUtil {
	
	@SuppressWarnings("deprecation")
	public static String login(String casLoginUrl,String appCallback,String username, String password) throws Exception {
                String service = appCallback;
                String params = "username=" + URLEncoder.encode(username)
                                + "&password=" + URLEncoder.encode(password) + "&service=" + service
                                + "&method=json";
                byte[] entitydata = params.getBytes();
                HttpURLConnection c = (HttpURLConnection) new URL(casLoginUrl) 
                                .openConnection();
                c.setInstanceFollowRedirects(false);
                c.setRequestMethod("POST");
                c.setDoOutput(true);
                c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                c.setRequestProperty("Content-Length",String.valueOf(entitydata.length));
                c.setConnectTimeout(5000);
                c.setReadTimeout(3000000);
                StringBuffer bankXmlBuffer=new StringBuffer();
                
                OutputStream outStream = c.getOutputStream();
                outStream.write(entitydata);
                outStream.flush();
                outStream.close();
                
                BufferedReader in = null;
                int responeCode = -1;
                try {
                   responeCode = c.getResponseCode();
                   if (responeCode == 200) {
                	    in = new BufferedReader(new InputStreamReader(c.getInputStream(),"utf-8"));
                       String inputLine;
                       while((inputLine=in.readLine())!=null){
                       		bankXmlBuffer.append(inputLine);
                       }
					}
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
}