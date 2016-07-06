package com.zjlp.face.web.ctl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.business.GoodShopTypeRelationBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.ImgTemplateVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;


@Controller
@RequestMapping("/u/good/shopType/")
public class ShopTypeCtl extends BaseCtl {
	
	public static final String REDIRECT_SHOPTYPE_LIST = "/u/good/shopType/list";
	
	public static final String REDIRECT_APPOINTMENT_SHOPTYPE_LIST="/u/appoint/good-type-list";
	
    @Autowired
    private ShopTypeBusiness shopTypeBusiness;
    
    @Autowired
    private ShopBusiness shopBusiness;
    
    @Autowired
    private  TemplateBusiness templateBusiness;
    
    @Autowired
    private GoodShopTypeRelationBusiness goodShopTypeRelationBusiness;
    
	/** tfs保存图片*/
	@Autowired
	private ImageService imageService;
    
    //获取PC后台商品分类列表
    @RequestMapping(value = "list",method=RequestMethod.GET)
    public String list(ShopTypeVo shopTypeVo, Pagination<ShopTypeVo> pagination, Model model){
    	shopTypeVo.setShopNo(super.getShopNo());
    	shopTypeVo.setServiceId(Constants.SERVICE_ID_COMMON);
    	pagination = shopTypeBusiness.findPageShopType(shopTypeVo, pagination);
    	String cell = shopBusiness.getShopByNo(super.getShopNo()).getCell(); 
        model.addAttribute("pagination", pagination);
        model.addAttribute("cell", cell);
    	return "/m/product/goods/shopType/goods";
    }
    //获取标准图标
    @RequestMapping(value="tempImgList")
    public String tempImgList(String type, String color, Pagination<ImgTemplateVo> pagination, Model model) {
		pagination = shopTypeBusiness.findShopTypeTempImg(type, color, pagination);
		model.addAttribute("type", type);
		model.addAttribute("color", color);
		model.addAttribute("pagination", pagination);
		return "/m/product/goods/shopType/icon-data";
    }
    
    //获取初始化新增
    @RequestMapping(value = "add",method=RequestMethod.GET)
    public String addInit(Model model){
    	return "/m/product/goods/shopType/goods-add";
    }
    
    //新增商品分类
    @RequestMapping(value = "add",method=RequestMethod.POST)
    public String add(ShopType shopType,HttpSession session,Boolean isPic){
    	shopType.setShopNo(super.getShopNo());
    	_checkName(shopType.getName());
    	shopTypeBusiness.addShopType(shopType);
    	//上传图片
    	_imgUpload(shopType,session,isPic);
    	shopType.setSort(shopType.getId());
    	shopTypeBusiness.editShopType(shopType);
    	if(Constants.SERVICE_ID_APPOINTMENT.equals(shopType.getServiceId())){
    		/**预约商品分类*/
    		return super.getRedirectUrl(REDIRECT_APPOINTMENT_SHOPTYPE_LIST);
    	}
    	return super.getRedirectUrl(REDIRECT_SHOPTYPE_LIST);
    }

	//初始化修改商品分类
    @RequestMapping(value = "edit/{shopTypeId}",method=RequestMethod.GET)
    public String editInit(@PathVariable Long shopTypeId,Model model){
    	ShopType shopType = shopTypeBusiness.getShopTypeById(shopTypeId);
    	model.addAttribute(shopType);
    	return "/m/product/goods/shopType/goods-add";
    }
    
    //修改商品分类
    @RequestMapping(value = "edit/{shopTypeId}",method=RequestMethod.POST)
    public String editShopType(@PathVariable Long shopTypeId,HttpSession session,ShopType shopType,Boolean isPic){
    	shopType.setShopNo(super.getShopNo());
    	shopType.setId(shopTypeId);
        _checkName(shopType.getName());
    	_imgUpload(shopType,session,isPic);
    	shopTypeBusiness.editShopType(shopType);
    	if(Constants.SERVICE_ID_APPOINTMENT.equals(shopType.getServiceId())){
    		/**预约商品分类*/
    		return super.getRedirectUrl(REDIRECT_APPOINTMENT_SHOPTYPE_LIST);
    	}
    	return super.getRedirectUrl(REDIRECT_SHOPTYPE_LIST);
    }
    
    //删除商品分类
    @RequestMapping(value = "delete/{shopTypeId}",method=RequestMethod.POST)
    public String delete(@PathVariable Long shopTypeId){
    	goodShopTypeRelationBusiness.deleteAllGoodShopTypeByShopTypeId(shopTypeId);
    	shopTypeBusiness.deleteShopTypeById(shopTypeId);
    	return super.getRedirectUrl(REDIRECT_SHOPTYPE_LIST);
    }
    //修改电话
    @RequestMapping(value = "editShopPhone", method = RequestMethod.POST)
    @ResponseBody
    public String editShopPhone(@RequestParam(required = true) String cell){
        try{
            String shopNo = super.getShopNo();
            Shop shop = new Shop();
            shop.setCell(cell);
            shop.setNo(shopNo);
            shopBusiness.editShop(shop);
            return super.getReqJson(true, "操作成功");
        }catch(Exception e){
            return super.getReqJson(false, "操作失败");
        }
    }
    
    /**
     * 列表排序
     * 
     * @Title: sortGoods
     * @Description: (这里用一句话描述这个方法的作用)
     * @param subId
     * @param tarId
     * @return
     * @date 2014年7月17日 下午8:56:25
     * @author dzq
     */
    @RequestMapping(value = "sort")
    @ResponseBody
    public String sortShopType(String subId, String tarId){
        shopTypeBusiness.sortShopType(Long.parseLong(subId), Long.parseLong(tarId));
        return "1";
    }
    
  //上传图标或图片
    private void _imgUpload(ShopType shopType,HttpSession session,Boolean isPic){
    	if(isPic){    		
    		System.out.println("是图片");
    		String shopNo = shopType.getShopNo();
    		Long userId = shopBusiness.getShopByNo(shopNo).getUserId();
    		OwTemplateDto owTemplateGt = (OwTemplateDto) session.getAttribute("owTemplateGt");
    		//尺寸(未完)
    		String zoomImg= owTemplateGt.getGoodTypeImageSize();
    		FileBizParamDto goodTypeFile = this._getFileBizParam(ImageConstants.GOOD_TYPE_FILE,shopNo
            		  ,userId,shopType.getId(),"Shop_type",zoomImg);
		  	goodTypeFile.setImgData(shopType.getImgPath());
		  	List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();
		  	bizParamDtos.add(goodTypeFile);
	        try {
	        	String resultJson =  imageService.addOrEdit(bizParamDtos);
	        	JSONObject jsonObject = JSONObject.fromObject(resultJson);
	            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败,"+jsonObject.getString("info"));
	            shopType.setImgPath(jsonObject.getJSONArray("data").getJSONObject(0).getString("imgData"));
        	}catch (Exception e) {
    			throw new GoodException(e.getMessage(),e);
            }
    	}	
    }
    
	private FileBizParamDto _getFileBizParam(String code,String shopNo,Long userId,Long tableId,String tableName,String ZoomSizes){
		FileBizParamDto goodFile = new FileBizParamDto();
		goodFile.setCode(code);
		goodFile.setUserId(userId);
        goodFile.setShopNo(shopNo);
        goodFile.setTableId(String.valueOf(tableId));
        goodFile.setTableName(tableName);
        goodFile.setZoomSizes(ZoomSizes);
        goodFile.setFileLabel(1);
        return goodFile;
	}
	
	private void _checkName(String name){
		AssertUtil.isTrue(name.length()<=10, "商品分类字数超出范围");
	}
	
}


