package com.zjlp.face.web.ctl.message;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.ctl.BaseCtl;
import com.zjlp.face.web.exception.ext.FancyMessageException;
import com.zjlp.face.web.server.product.material.bussiness.GwInformationBusiness;
import com.zjlp.face.web.server.product.material.domain.GwInformation;

@Controller
@RequestMapping("/")
public class GwInformationCtl extends BaseCtl{
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private GwInformationBusiness gwInformationBusiness;
	
    @RequestMapping(value="index")
	public String index(Model model){
    	Pagination<GwInformation> pagination = new Pagination<GwInformation>();
    	pagination.setStart(0);
    	pagination.setPageSize(4);
    	GwInformation gwInformation = new GwInformation();
    	gwInformation.setStatus(1);
    	pagination = gwInformationBusiness.findGwInformationPageList(gwInformation, pagination);
    	
    	List<GwInformation> list = pagination.getDatas();
    	
    	if(null!=list && list.size()>0){
    		model.addAttribute("informationFirst", list.get(0));
        	model.addAttribute("informationList", list);
    	}
    	
		return "/m/index/zjlp-index";
	}
    
    @RequestMapping(value="information/index",method=RequestMethod.GET)
	public String informationIndex(Pagination<GwInformation> pagination,Model model){
    	pagination.setPageSize(15);
    	GwInformation gwInformation = new GwInformation();
    	gwInformation.setStatus(1);
    	pagination = gwInformationBusiness.findGwInformationPageList(gwInformation, pagination);
    	
    	List<GwInformation> list = pagination.getDatas();
    	Integer totalPage = pagination.getTotalPage();
    	Integer curPage = pagination.getCurPage();
    	
    	model.addAttribute("informationList", list);
    	model.addAttribute("totalPage", totalPage);
    	model.addAttribute("curPage", curPage);
    	
		return "/m/index/information/index";
	}
    
    @RequestMapping(value="information/details/{id}",method=RequestMethod.GET)
	public String detail(@PathVariable("id")Long id,Model model) throws FancyMessageException{
    	GwInformation gwInformation = gwInformationBusiness.getByPrimaryKey(id);
    	
    	model.addAttribute("information", gwInformation);

		return "/m/index/information/detail";
	}
}
 
