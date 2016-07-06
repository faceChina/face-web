package com.zjlp.face.web.ctl.wap;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.solr.good.GoodSolr;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.dao.CarouselDiagramDao;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;


@Controller
@RequestMapping("/wap/{shopNo}/any/shopType/")
public class ShopTypeWapCtl extends WapCtl {
	
	public static final String REDIRECT_SHOPTYPE_LIST = "/u/good/shopType/list";
	
    @Autowired
    private ShopTypeBusiness shopTypeBusiness;
    
    @Autowired
    private ShopBusiness shopBusiness;
    
    @Autowired
    private  TemplateBusiness templateBusiness;
    
    @Autowired
    private CarouselDiagramDao CarouselDiagramDao;
    
    @Autowired
    private GoodSolr goodSolr;
    
    //获取手机前端商品分类列表
    @RequestMapping(value = "list",method=RequestMethod.GET)
    public String list(@PathVariable String shopNo ,ShopTypeVo shopTypeVo, Pagination<ShopTypeVo> pagination, Model model){
    	shopTypeVo.setShopNo(shopNo);
    	pagination = shopTypeBusiness.findPageShopType(shopTypeVo, pagination);
    	OwTemplateDto owTemplate = _getImage(shopNo,pagination);
    	
    	String code = owTemplate.getCode();
    	Integer templateId = 1;
    	if(code.contains("2")){
    		templateId = 2 ;
    	}else if(code.contains("3")){
    		templateId = 3 ;
    	}
    	String cell = shopBusiness.getShopByNo(shopNo).getCell(); 
        model.addAttribute("pagination", pagination);
        //加载轮播图
        if(code.contains("1")){
	       List<CarouselDiagramDto> carouselDiagramDto = CarouselDiagramDao.findCarouselDiagramByOwTemplateId(owTemplate.getId());
	       model.addAttribute("carouselDiagramDto", carouselDiagramDto);
       }
        model.addAttribute("cell", cell);
        model.addAttribute("shopTypeVo", shopTypeVo);
    	return "/wap/product/good/shopType/list"+templateId+"/list";
    }
    
	/**
     * 商品列表分页数据
     * 
     * @Title: listAppend
     * @Description: (这里用一句话描述这个方法的作用)
     * @param goodDto
     * @param pagination
     * @return
     * @date 2014年8月12日 上午11:01:35
     * @author dzq
     */
    @RequestMapping(value = "append", method = RequestMethod.POST)
    @ResponseBody
    public String listAppend(@PathVariable String shopNo,ShopTypeVo shopTypeVo, Pagination<ShopTypeVo> pagination){
        try{
        	shopTypeVo.setShopNo(shopNo);
        	pagination = shopTypeBusiness.findPageShopType(shopTypeVo, pagination);
        	 _getImage(shopNo,pagination);
        	 System.out.println(JSONObject.fromObject(pagination).toString());
            return super.getReqJson(true, JSONObject.fromObject(pagination).toString());
        }catch(Exception e){
            return super.getReqJson(false, "加载数据失败");
        }
    }
    
    //加载缩略图
    private OwTemplateDto _getImage(String shopNo,Pagination<ShopTypeVo> pagination){
    	Shop shop = shopBusiness.getShopByNo(shopNo);
    	OwTemplateDto owTemplate = templateBusiness.getCurrentGoodTypePageOwTemplate(shopNo, shop.getType());
	   	for (ShopTypeVo vo : pagination.getDatas()) {
				if(StringUtils.isNotBlank(vo.getImgPath())){
					vo.setImgPath(ImageConstants.getCloudstZoomPath(vo.getImgPath(), owTemplate.getGoodTypeImageSize()));
				}
			}
	   	return owTemplate;
    }

}
