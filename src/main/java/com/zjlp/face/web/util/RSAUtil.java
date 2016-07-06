package com.zjlp.face.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.core.util.Base64Encoder;


/**
 * RSA签名公共类
 * @author dzq
 */
public class RSAUtil{

    private static Logger  log = Logger.getLogger(RSAUtil.class);
    private static RSAUtil instance;
    
    private static Cipher cipher;  
    
    static{  
        try {  
            cipher = Cipher.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        }  
    }  

    private RSAUtil()
    {

    }

    public static RSAUtil getInstance()
    {
        if (null == instance)
            return new RSAUtil();
        return instance;
    }

    /**
     * 公钥、私钥文件生成
     * @param keyPath：保存文件的路径
     * @param keyFlag：文件名前缀
     */
    private void generateKeyPair(String key_path, String name_prefix)
    {
        java.security.KeyPairGenerator keygen = null;
        try
        {
            keygen = java.security.KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e1)
        {
            log.error(e1.getMessage());
        }
        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("21cn".getBytes()); // 初始化随机产生器
        keygen.initialize(1024, secrand);
        KeyPair keys = keygen.genKeyPair();
        PublicKey pubkey = keys.getPublic();
        PrivateKey prikey = keys.getPrivate();

        String pubKeyStr = Base64.getBASE64(pubkey.getEncoded());
        System.out.println("pubKeyStr==="+pubKeyStr);
        System.out.println("==============================================");
        String priKeyStr = Base64.getBASE64(prikey.getEncoded());
        System.out.println("priKeyStr===="+priKeyStr);
        File file = new File(key_path);
        if (!file.exists())
        {
            file.mkdirs();
        }
        try
        {
            // 保存私钥
            FileOutputStream fos = new FileOutputStream(new File(key_path
                    + name_prefix + "_RSAKey_private.txt"));
            fos.write(priKeyStr.getBytes());
            fos.close();
            // 保存公钥
            fos = new FileOutputStream(new File(key_path + name_prefix
                    + "_RSAKey_public.txt"));
            fos.write(pubKeyStr.getBytes());
            fos.close();
        } catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }
    /** 
     * 得到公钥 
     *  
     * @param key 
     *            密钥字符串（经过base64编码） 
     * @throws Exception 
     */  
    public static PublicKey getPublicKey(String key) throws Exception {  
        byte[] keyBytes;  
        keyBytes = (new Base64Encoder()).decode(key);  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
        return publicKey;  
    } 
    /** 
     * 使用公钥对明文进行加密，返回BASE64编码的字符串 
     * @param publicKey 
     * @param plainText 
     * @return 
     */  
    public static String encrypt(PublicKey publicKey,String plainText){  
        try {             
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] enBytes = cipher.doFinal(plainText.getBytes());            
            return (new Base64Encoder()).encode(enBytes);  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }
    /**
     * 
    * @Title: getPrivateKey
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param key
    * @return
    * @throws NoSuchAlgorithmException
    * @throws InvalidKeySpecException
    * @return PrivateKey    返回类型
    * @throws
    * @author wxn  
    * @date 2014年12月19日 下午3:44:31
     */
    public static PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException,  
	InvalidKeySpecException{  
    	byte[] keyBytes;
    	 keyBytes = (new Base64Encoder()).decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
		return privateKey;  
	}  


    /**
     * 用私钥解密
    * @Title: decryptData
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param encryptedData
    * @param privateKey
    * @return
    * @return byte[]    返回类型
    * @throws
    * @author wxn  
    * @date 2014年12月19日 下午3:35:37
     */
	public static String decryptData(String encryptedData, PrivateKey privateKey)
	{
		try
		{
			byte[] keyBytes;
		
	    	 keyBytes = (new Base64Encoder()).decode(encryptedData);
	    	 // android虚拟机加密字符需要用RSA/ECB/NoPadding模式实例Cipher来解密。
	    	 // 如果是JAVA虚拟机 则用RSA 模式实例
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			 byte[] enBytes =  cipher.doFinal(keyBytes);
//			 return(new Base64Encoder()).encode(enBytes);  
			 return new String(enBytes,"utf-8").trim();  
		}catch (Exception e){
			return null;
		}
	}

    /**
     * 读取密钥文件内容
     * @param key_file:文件路径
     * @return
     */
    private static String getKeyContent(String key_file)
    {
        File file = new File(key_file);
        BufferedReader br = null;
        InputStream ins = null;
        StringBuffer sReturnBuf = new StringBuffer();
        try
        {
            ins = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            String readStr = null;
            readStr = br.readLine();
            while (readStr != null)
            {
                sReturnBuf.append(readStr);
                readStr = br.readLine();
            }
        } catch (IOException e)
        {
            return null;
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                    br = null;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (ins != null)
            {
                try
                {
                    ins.close();
                    ins = null;
                } catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return sReturnBuf.toString();
    }

    /**
     * 签名处理
     * @param prikeyvalue：私钥文件
     * @param sign_str：签名源内容
     * @return
     */
    public static String sign(String prikeyvalue, String sign_str)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64
                    .getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature
                    .getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes("UTF-8"));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
        } catch (java.lang.Exception e)
        {
            log.error("签名失败," + e.getMessage());
        }
        return null;
    }

    /**
     * 签名验证
     * @param pubkeyvalue：公钥
     * @param oid_str：源串
     * @param signed_str：签名结果串
     * @return
     */
    public static boolean checksign(String pubkeyvalue, String oid_str,
            String signed_str)
    {
        try
        {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64
                    .getBytesBASE64(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64.getBytesBASE64(signed_str);// 这是SignatureData输出的数字签名
            java.security.Signature signetcheck = java.security.Signature
                    .getInstance("MD5withRSA");
            signetcheck.initVerify(pubKey);
            signetcheck.update(oid_str.getBytes("UTF-8"));
            return signetcheck.verify(signed);
        } catch (java.lang.Exception e){
            log.error("签名验证异常," + e.getMessage());
           // log.error("原文："+oid_str);
           // log.error("密文："+signed_str);
        }
        return false;
    }

    public static void main(String[] args)
    {
        // 商户（RSA）私钥 TODO 强烈建议将私钥
        String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM7BtISEErbKGNcskmkyB/i25xZKVh+kIeowfeWsbAA5eBJ1xoa682T3Qz4CWuz/t4mBczbvQkLUsi0+OzMGRG8+7aoWmW7E73C+BRy6i3LfvWZ4u7Z+2OH3h4g7ObFIcTeJa54VvPlkisX9Zko2b09BhtZUYgR0dc8mNpCmRqwjAgMBAAECgYEAkO6QD+RVCfUY/Jyt9TexBtOPobxyKrPvYi6j0f/PpUijtq0AgSlDvJ7nb+xOuJt4mNc5YGTPWfGnBLf+34GhLed/8Y2xNTqFfOg9Jg12pdWrmnFs5F2kLOr6eR/CfYFFqMHQ9Z9+JQciiD5BXmY/8Do3j1azNotwpgpWojdx3AECQQDt+qYgOQyQYo62ZOIyKpEBvEHASAiK2Nfzlw8Kl7nIbVX9pGNkfeDkN7zs6wcV7n+4hs2oMTShg5YrU0BHvxmBAkEA3mnK74SiEUXCR39WlMpAMkwJefoT8BL4p/8pckdZ46Ix+JaSzU+QqMLBjclczp6CZmKRrSKKEGG7HUcO78rvowJAc9FTfkUldzNwDxZj+1Q6BCUxvrmP5rsHxlYTDO2wjfmgKvQRJzwX8hmqSYdMiIDtCcoZVqyz15Mpx2YZ15EKgQJAeeNof9MkLmsYia5TeL9OZ0Icf2h5vLvo4ciIokRQEtw0npOGaFYOZS42fMm5vtJHjGzAgS3IlCm7LdRfbzK8GQJBALSXc599v6NDVvbgjpxD6ayI/wst7DTE2ErbEUwv25RGmyPnGodm7YJyemuOdyShNLNJfA8lGDnJNohNREIegmw=";
        // 银通支付（RSA）公钥
        String RSA_YT_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOwbSEhBK2yhjXLJJpMgf4tucWSlYfpCHqMH3lrGwAOXgSdcaGuvNk90M+Alrs/7eJgXM270JC1LItPjszBkRvPu2qFpluxO9wvgUcuoty371meLu2ftjh94eIOzmxSHE3iWueFbz5ZIrF/WZKNm9PQYbWVGIEdHXPJjaQpkasIwIDAQAB";
        // RSAUtil.getInstance().generateKeyPair("D:\\CertFiles\\inpour\\",
        // "ll_yt");
        
        String data = "{\"username\": \"13200009999\",\"password\": \"123456\"}";
        
        String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT2+lLR5EbQprpy7cYFlFJKilTKnTK7y8VOFM00WjXXSNrR1LM8k0p7X8/pdUtwY0Qb48df8r6xUb53lu5ZjPcPnuSz1JyIapza5dmnoqDiVh8/gWuqq0V8/JAn+pH7ADBuB9gNsTHG9umYjD2cRSh4p7inFu20/m10xca0LEfyQIDAQAB";
        String priKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJPb6UtHkRtCmunLtxgWUUkqKVMqdMrvLxU4UzTRaNddI2tHUszyTSntfz+l1S3BjRBvjx1/yvrFRvneW7lmM9w+e5LPUnIhqnNrl2aeioOJWHz+Ba6qrRXz8kCf6kfsAMG4H2A2xMcb26ZiMPZxFKHinuKcW7bT+bXTFxrQsR/JAgMBAAECgYEAh2vK6F/LzyPZrngeYblPCavL3ZftEFCw1saXrrB9TYLIheD1PTBO7C/RdAH2lcnH4V3LvkDlL3iv4Pp/F/c7Vvvgs/LbpXwnPvYVtdkZ1x3AZRfS/5uSrSoAkiN0zEJnmb3Ywp7YlCYfVlke4u6dhQN+WxvqPl69VMBzNpagXWECQQDlBVUvIqQp6e0Gsp4oOj3HyQtCT+BsaRZkLtMNTq5pcz/83s1H0cIoU8dTT7LCZvRw+yjYgQ5YBY9D0CZBmwdfAkEApUbzmt2klNpf2apadyI+fYcbYBky3kb2q6YZ/xQuCU8eSJC4F2bPDDfxpsIqADj5A8KB74EnB6h1UT9rQONx1wJBAMXuFfDmv3p58aAYPxgFPd+soU5uOkd3iyKKVVzq41G/iU3CQSgQ4Px5a4tVFeltkVUTu/lhkEQCig7Rlj6c/YECQHwqUIrQ5nsZj5bDv1Du/glp/ev1Il0Q7PHJSJB0RZ2ivbqAVnzmNLgWM0o3ZjxikNj9QIaA/aRoLzLJtTa7aGMCQFEkTk/9gIYYKolMwMllO/SN+dO54W1Pc/Dx65ZsEwzgq+UEBb0BjbxbVebVRcaXam6OKIuCW2KwdQuMlY6AqeQ=";
        PublicKey publicKey;
        PrivateKey privateKey;
        String st = "ZSysx2tRMhFu5beqGi8YQ88F3hgaZ8baM+9cehH76A84dS+fxzfLUefDH5R7mIsIOLrHwwpCt0mm6ZD3FAGGwQZiXpGHQOcJiIC38v1cnddLiIalwcNCc8oxts/zBseK0EKwqJrMYSb/g6eRvzd1vHQQcF6YO3mqJHJAZOMDscY=";
		try {
			publicKey = RSAUtil.getPublicKey(pubkey);
			 String str = RSAUtil.encrypt(publicKey, data);
			 System.out.println(str);
			 
			 privateKey = RSAUtil.getPrivateKey(priKey);
			 String str1=RSAUtil.decryptData(st, privateKey);
			 System.out.println(str1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //String sign = RSAUtil.sign(RSA_PRIVATE, "app_request=3&busi_partner=101001&dt_order=20140423163028&money_order=0.01&no_order=10000000000002115468&notify_url=http://localhost:8080/jzwgj/LianLian/payConsumerAsyn.htm&oid_partner=201404171000001184&sign_type=RSA&url_return=http://localhost:8080/jzwgj/LianLian/payConsumer.htm&user_id=1&version=1.1");
        
       // System.out.println(sign);
       // System.out.println(RSAUtil.checksign(RSA_YT_PUBLIC, "app_request=3&busi_partner=101001&dt_order=20140423163028&money_order=0.01&no_order=10000000000002115468&notify_url=http://localhost:8080/jzwgj/LianLian/payConsumerAsyn.htm&oid_partner=201404171000001184&sign_type=RSA&url_return=http://localhost:8080/jzwgj/LianLian/payConsumer.htm&user_id=1&version=1.1", sign));
    }
}
