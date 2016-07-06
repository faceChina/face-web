package com.zjlp.face.web.ctl.message;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.ctl.wap.WapCtl;
import com.zjlp.face.web.exception.ext.FancyMessageException;
import com.zjlp.face.web.server.product.material.bussiness.FancyMessageBusiness;
import com.zjlp.face.web.server.product.material.domain.FancyMessageItem;

@Controller
@RequestMapping("/message/fancy/")
public class FancyMessageCtl extends WapCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private FancyMessageBusiness fancyMessageBusiness;
	
	@RequestMapping(value="details/{id}" , method=RequestMethod.GET)
	public String detail(@PathVariable("id")String id,Model model) throws FancyMessageException{
		try {
			AssertUtil.notNull(id, "刷脸精选细项ID不能为空！","精选已不存在");
			if(!StringUtils.isNumeric(id)){
				AssertUtil.isTrue(false, "刷脸精选细项ID错误!","精选已不存在");
			}
			
			FancyMessageItem fancyMessageItem = fancyMessageBusiness.selectByPrimaryKeyEditBrowerTime(Long.parseLong(id));
			AssertUtil.notNull(fancyMessageItem, "精选已不存在！","精选已不存在");
			model.addAttribute("fancyMessageItem", fancyMessageItem);
		} catch (Exception e) {
			throw new FancyMessageException(e.getMessage(),e);
		}
		
		return "/wap/message/fancy/details";
	}
	
	@RequestMapping(value="like", method=RequestMethod.POST)
    @ResponseBody
	public String like(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			AssertUtil.notNull(id, "刷脸精选细项ID不能为空！","精选已不存在");
			if(!StringUtils.isNumeric(id)){
				AssertUtil.isTrue(false, "刷脸精选细项ID错误!","精选已不存在");
			}
			
			fancyMessageBusiness.like(Long.parseLong(id));
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			return super.getReqJson(false, "");
		}
		return super.getReqJson(true, "");
	}
}
