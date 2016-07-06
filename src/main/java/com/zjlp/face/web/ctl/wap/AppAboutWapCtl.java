package com.zjlp.face.web.ctl.wap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjlp.face.web.ctl.BaseCtl;

@Controller
public class AppAboutWapCtl extends BaseCtl {

	@RequestMapping("/app/download")
	public String download(Model model) {
		return "/wap/product/appDownload/down";
	}
	
	@RequestMapping("/app/internetadd")
	public String toInternetAdd(Model model) {
		return "/wap/about/internetadd";
	}
	
	/**
	 * 官网介绍入口
	 * @Title: slptpc 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月5日 上午9:35:27  
	 * @author lys
	 */
	@RequestMapping(value="/app/slptpc",method = RequestMethod.GET)
	public String slptpc(){
		return "/wap/about/slptpc";
	}
}
