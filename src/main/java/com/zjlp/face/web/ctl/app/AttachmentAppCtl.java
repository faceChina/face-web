package com.zjlp.face.web.ctl.app;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zjlp.face.file.service.ImageService;

@Controller
@Scope("prototype")
@RequestMapping("/assistant/ass/any/files/")
public class AttachmentAppCtl extends BaseCtl {

	Logger _logger = Logger.getLogger(getClass());
	
	
    @Autowired
    private ImageService imageService;
	
	/**
     * 上传图片
     * 
     * @Title: upload
     * @Description: (适用于商品等图片，从配置文件中读取尺寸)
     * @param inputName
     * @param request
     * @param model
     * @return
     * @date 2014年8月5日 下午5:31:47
     * @author Administrator
     */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
    public String upload(HttpServletRequest request){
		
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
			return flag;
		} catch (Exception e) {
			 _logger.error("上传文件失败",e);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("code", -1);
            map.put("desc", "上传文件失败");
			JSONObject result = JSONObject.fromObject(map);
			return result.toString();
		}
	}

}
