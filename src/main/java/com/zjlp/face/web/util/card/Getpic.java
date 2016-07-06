package com.zjlp.face.web.util.card;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 从远程下载图片到本地
 * @ClassName: Getpic 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author liujia
 * @date 2015年2月3日 下午7:34:00
 */
public class Getpic{
    public Getpic(){
    }

    public static boolean saveUrlAs(String fileUrl, String savePath)
    {

         try
         {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, count);
            }
            out.close();
            in.close();
            connection.disconnect();
            return true;

        }
        catch (Exception e)
        {
             System.out.println(e + fileUrl + savePath);
             return false;
        }
    }
    
    /**
     * 根据路径获取文件二进制数据
     * @param fileUrl
     * @return
     * @throws Exception
     */
    public static byte[] getByteByUrl(String fileUrl) throws Exception{
    	try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			return input2byte(connection.getInputStream());
		} catch (Exception e) {
			throw e;
		}
    }
    
    public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }  

    public static void main(String[] args)
    {
         Getpic pic = new Getpic();
         String photoUrl = "http://wx.qlogo.cn/mmopen/QSpiaAd1r3fkQuWajLb7icqHCIg2ibbribicqe4Q6iaXU6N5PHNrFQ7oY3QM60HXOEvdibnuB3Giao0LYaKuiaGT7D1nFwI3aQokhI6rG/0";
         String fileName = photoUrl.substring(photoUrl.lastIndexOf("/"));
         String filePath = "E://";
        @SuppressWarnings("static-access")
		boolean flag = pic.saveUrlAs(photoUrl, filePath + fileName);
        System.out.println("Run ok!\n Get URL file " + flag);
	    }

}

