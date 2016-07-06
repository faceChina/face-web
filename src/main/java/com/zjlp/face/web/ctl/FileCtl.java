package com.zjlp.face.web.ctl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zjlp.face.file.service.ImageService;

@Controller
@Scope("prototype")
@RequestMapping("/any/files")
public class FileCtl extends BaseCtl{
	
	private static final Integer BUFFER_SIZE = 1024;
	@Autowired(required=false)
	private ImageService imageService;
	
	@RequestMapping(value ="/upload",method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request,Model model){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (!multipartResolver.isMultipart(request)) {
            return "上传文件异常";
		}
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multiRequest.getFileNames();
		String flag = null;
		try {
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file == null) {
					continue;
				}
				//调用图片服务
				flag = imageService.upload(file.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//下载图片
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public String download(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String filePath) {
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			Assert.hasLength(filePath);
			byte[] img = imageService.getFileByte(filePath);
			in = new ByteArrayInputStream(img);
			response.setHeader("Content-Disposition", "attachment;filename=" + filePath);
			response.setContentType("text/html;charset=UTF-8");
			out = response.getOutputStream();
			byte[] buff = new byte[BUFFER_SIZE];
			int readed = 0;
			while ((readed = in.read(buff, 0, BUFFER_SIZE)) != -1) {
				out.write(buff, 0, readed);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}
}
