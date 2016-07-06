package com.zjlp.face.web.ctl.wap;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.server.product.good.business.ClassificationBusiness;
import com.zjlp.face.web.server.product.good.domain.Classification;
@Controller
@RequestMapping("/test/good/tools")
public class TestGoodCtl {
	
	@Autowired
	private ClassificationBusiness classificationBusiness;
	
	
	@RequestMapping("/test/good/tools")
	public String init(Model model){
		List<Classification> classifications = classificationBusiness.findClassificationByPid(0L, 0);
		model.addAttribute("classifications", classifications);
		return "/m/apiPropTools";
	}
	
	
	@RequestMapping("/test/good/getClassifications")
	@ResponseBody
	public String getClassifications(Long pid,Integer level){
		List<Classification> classifications = classificationBusiness.findClassificationByPid(0L, 0);
		return JSONArray.fromObject(classifications).toString();
	}
}
