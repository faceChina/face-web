package com.zjlp.face.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.util.file.PropertiesUtil;
public class ConvertUtil {
	
	protected static final String DATA = "data";
	protected static final String ALL_NUMBER = "^[0-9]*$";
	
    public static  JSONObject formatJson(String data)throws Exception {
		String isDecrypt = PropertiesUtil.getContexrtParam("ASSISTANT_IS_Decrypt");
		JSONObject obj = JSONObject.fromObject(data);
		//取得数据
		String endata= obj.getString("data");
		// 是否需要解密
		if ("TRUE".equalsIgnoreCase(isDecrypt)){
			String dedata = ConvertUtil.decryptData(endata);
			return JSONObject.fromObject(dedata);
		}else{
			return JSONObject.fromObject(endata);
		}
	}
	
    public static String getBodyString(BufferedReader br) {
	     String inputLine;
	     String str = "";
	   try {
	     while ((inputLine = br.readLine()) != null) {
	      str += inputLine;
	     }
	     br.close();
	   } catch (IOException e) {
	     System.out.println("IOException: " + e);
	   }
	   return str;
	}
	
	public static String decryptData(String encryptionString){
		if (null == encryptionString || "".equals(encryptionString)) {
			return null;
		}
		/** 获取密钥 */
		String key = PropertiesUtil.getContexrtParam("ASSISTANT_PRIVATE_RSA_KEY");
		//String key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJPb6UtHkRtCmunLtxgWUUkqKVMqdMrvLxU4UzTRaNddI2tHUszyTSntfz+l1S3BjRBvjx1/yvrFRvneW7lmM9w+e5LPUnIhqnNrl2aeioOJWHz+Ba6qrRXz8kCf6kfsAMG4H2A2xMcb26ZiMPZxFKHinuKcW7bT+bXTFxrQsR/JAgMBAAECgYEAh2vK6F/LzyPZrngeYblPCavL3ZftEFCw1saXrrB9TYLIheD1PTBO7C/RdAH2lcnH4V3LvkDlL3iv4Pp/F/c7Vvvgs/LbpXwnPvYVtdkZ1x3AZRfS/5uSrSoAkiN0zEJnmb3Ywp7YlCYfVlke4u6dhQN+WxvqPl69VMBzNpagXWECQQDlBVUvIqQp6e0Gsp4oOj3HyQtCT+BsaRZkLtMNTq5pcz/83s1H0cIoU8dTT7LCZvRw+yjYgQ5YBY9D0CZBmwdfAkEApUbzmt2klNpf2apadyI+fYcbYBky3kb2q6YZ/xQuCU8eSJC4F2bPDDfxpsIqADj5A8KB74EnB6h1UT9rQONx1wJBAMXuFfDmv3p58aAYPxgFPd+soU5uOkd3iyKKVVzq41G/iU3CQSgQ4Px5a4tVFeltkVUTu/lhkEQCig7Rlj6c/YECQHwqUIrQ5nsZj5bDv1Du/glp/ev1Il0Q7PHJSJB0RZ2ivbqAVnzmNLgWM0o3ZjxikNj9QIaA/aRoLzLJtTa7aGMCQFEkTk/9gIYYKolMwMllO/SN+dO54W1Pc/Dx65ZsEwzgq+UEBb0BjbxbVebVRcaXam6OKIuCW2KwdQuMlY6AqeQ=";
		if (StringUtils.isBlank(key)) {
			return null;
		}
		try {
			PrivateKey privateKey = RSAUtil.getPrivateKey(key);
			String[] arrage = encryptionString.split(",");
			StringBuffer buff = new StringBuffer();
			for(int i=0;i<arrage.length;i++){
				buff.append(RSAUtil.decryptData(arrage[i],privateKey));
			}
			//对数据解密
			String dedata = buff.toString();
			return dedata;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String,Object> analysisHtml(String htmlStr){
		
		String[]  str = {"img src=\"","<h3>","<code>","a href=\""};
		
		int startIndex = htmlStr.indexOf("img src=\"");
		int strlength  = str[0].length();
		startIndex += strlength;
		int endIndex   = htmlStr.indexOf("\"",startIndex);
		String imgStr = htmlStr.substring(startIndex, endIndex);
		
		startIndex  = htmlStr.indexOf("<h3>");
		strlength  = str[1].length();
		startIndex += strlength;
		endIndex = htmlStr.indexOf("</h3>",startIndex);
		String goodname = htmlStr.substring(startIndex, endIndex);
		
		startIndex  = htmlStr.indexOf("<code>");
		strlength  = str[2].length();
		startIndex += strlength;
		endIndex = htmlStr.indexOf("</code>",startIndex);
		String price = htmlStr.substring(startIndex, endIndex);
		
		startIndex  = htmlStr.indexOf("a href=\"");
		strlength  = str[3].length();
		startIndex += strlength;
		endIndex = htmlStr.indexOf("\"",startIndex);
		String ahref = htmlStr.substring(startIndex, endIndex);
		String host = PropertiesUtil.getContexrtParam("HOST_WGJ");
		ahref = host+ahref;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("imgStr", imgStr);
		map.put("goodname", goodname);
		map.put("price", price);
		map.put("ahref", ahref);
		return map;
	}
	
/*	public static void main(String[] arg){
		String html = "<div class=\"commodity\"\"><dl><dt><img src=\"/resources/upload/974/HZJZ000814115795dJtu/goodFile/640_640/1417484597884.jpg\" alt=\"\"/></dt><dd><h3>good160</h3><code>￥55.00</code><a href=\"/app/HZJZ000814115795dJtu/good/detail/10966.htm\" target=\"_blank\" >查看宝贝</a></dd></dl></div>";
		ConvertUtil.analysisHtml(html);
	}*/
	
/*	public static void main(String[] args) {
		String s = "LZSOZj42eNn/pTXwGG1ho4hRMtElQuzKdSvazAhGGzB1FQpvehGNJ4RxZYAI3D9PpuDXgY+dg1Fu8yWBCK6W339pAvCJf8TbRkhM1DfscR85J8ehtb7tgLgMmhgw8zXHPIVm4NalnQsLcQdbiItcnmWGnkDWaPl8WIn/ESyNc/w=";
		ConvertUtil.decryptData(s);
	}*/

	public static List<Long> splitString(String str, String sybol) {
		List<Long> results = new ArrayList<Long>();
		if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(sybol) && str.contains(sybol)) {
			String[] array = str.split(sybol);
			for (String element : array) {
				if (element.matches(ALL_NUMBER)) {
					results.add(Long.valueOf(element));
				}
			}
		} else if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(sybol)
				&& !str.contains(sybol) && str.matches(ALL_NUMBER)) {
			results.add(Long.valueOf(str));
		}
		return results;
	}

	/**
	 * 获取全包含集的差部分
	 * 
	 * @param bigSet
	 * @param smallSet
	 * @return
	 */
	public static List<Long> getDifferenceSet(List<Long> bigSet, List<Long> smallSet) {
		List<Long> results = new ArrayList<Long>();
		if (CollectionUtils.isEmpty(bigSet) && CollectionUtils.isNotEmpty(smallSet)) {
			return smallSet;
		} else if (CollectionUtils.isNotEmpty(bigSet) && CollectionUtils.isEmpty(smallSet)) {
			return bigSet;
		} else if (CollectionUtils.isNotEmpty(bigSet) && CollectionUtils.isNotEmpty(smallSet)) {
			if (bigSet.size() == smallSet.size()) {
				return results;
			} else if (bigSet.size() > smallSet.size() && bigSet.containsAll(smallSet)) {
				for (Iterator<Long> it = bigSet.iterator(); it.hasNext();) {
					Long big = it.next();
					for (Long small : smallSet) {
						if (big.equals(small)) {
							it.remove();
						}
					}
				}
				return bigSet;
			} else if (bigSet.size() < smallSet.size() && smallSet.containsAll(bigSet)) {
				for (Iterator<Long> it = smallSet.iterator(); it.hasNext();) {
					Long small = it.next();
					for (Long big : bigSet) {
						if (big.equals(small)) {
							it.remove();
						}
					}
				}
				return smallSet;
			}
		}
		return results;
	}

}
