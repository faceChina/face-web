package com.zjlp.face.web.server.product.template.domain.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;
import com.zjlp.face.web.util.template.LoadTempalteData;

public class OwTemplateDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -122008633963450989L;
	//模板细项
	private TemplateDetail templateDetail;
	//编号
  	private Long id;
  	//店铺编号
    private String shopNo;
    //模版CODE
	private String code;
	//模版名称
	private String name;
	//模版图片路径
	private String path;
	//模版状态 -1：删除，0：停用，1：激活
	private Integer status;
	//模版类型1：官网模板，2：商城模板，3：脸谱模板，4：商城分类页模板，5：脸谱分类页模板  6：文章分类列表模版 7：相册模版
	private Integer type;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;
	//是否有模块0.无1.有
	private Integer isModular;
	//模块是否有图标0.无  1.有
	private Integer isImage;
	//模块数量是否 可增 可删 可移 0不可 1可
	private Integer isChange;
	//图片形状 0方型 1圆型
	private Integer shape;
	//模板是否有店招 0无 1有
	private Integer isShopStrokes;
	//模块是否有背景颜色 0无 1有
	private Integer isBackgroundColor;
	//是否有英文名称 0无 1有
	private Integer isNameEn;
	//是否有联系方式
	private Integer isContactWay;
	//是否有自定义模块
	private Integer isUserDefined;
	// 0.无 1.轮播图 2.背景图
	private Integer isCarlOrBg;
	//归类1.热门2.行业3.其他
	private Integer classification;
	//模块图片尺寸是否规则 0.不规则 1.规则
	private Integer isRegular;
	// 模块图标尺寸
    private String modularImageSize;
    // 图标宽度
    private String modularImageWidth;
    // 图标高度
    private String modularImageHeight;
    //自定义模块图标尺寸
    private String customModularImageSize;
    //自定义图片宽
    private String customModularImageWidth;
    //自定义图标高
    private String customModularImageHeight;
	// 模块最大数量
    private Integer maxModularCount = 12;
    // 模块最小数量
    private Integer minModularCount = 1;
    // 轮播图尺寸
    private String carouselImageSize;
    // 轮播图宽度
    private String carouselImageWidth;
    // 轮播图高度
    private String carouselImageHeight;
    // 模块数量
    private Integer modularnum;
    // 模块图片尺寸不规则时,保存模块图片宽度
    private String[] modularWidths;
    // 模块图片尺寸不规则时,保存模块图片高度
    private String[] modularHeights;
    //是否有轮播图
  	private Integer isCarouselImage;
  	//商品分类图片尺寸
  	private String goodTypeImageSize;
  	//商品分类图片宽
  	private String goodTypeImageWidth;
  	//商品分类图片高
  	private String goodTypeImageHeight;
  	//商品分类是否有描述(0：无，1：有)
  	private Integer isDescription;
  	//商品分类是否有文字颜色(0：无，1：有)
  	private Integer isFontColor;
  	//排序
  	private Integer sort;
  	//版本号
  	private String templateVersion;
  	//商品是否推荐至首页
  	private Integer isSpreadIndex;
  	// 模板店招图片路径
   	private String shopStrokesPath;
   	// 自定义模块是否有图片
   	private Integer isImageCustom;
   	// 自定义模块是否有英文名称
   	private Integer isNameEnCustom;
   	// 自定义模块是否有背景颜色
   	private Integer isBackgroundColorCustom;
   	// 是否有模板细项
   	private Integer isTemplateDetail;
   	//自定义模块 名称是否有颜色
   	private Integer isNameZhColorCustom;
   	
   	private Integer isHidden;
	
  	public Integer getIsTemplateDetail() {
		return isTemplateDetail;
	}
	public void setIsTemplateDetail(Integer isTemplateDetail) {
		this.isTemplateDetail = isTemplateDetail;
	}
  	public Integer getIsNameZhColorCustom() {
		return isNameZhColorCustom;
	}
	public void setIsNameZhColorCustom(Integer isNameZhColorCustom) {
		this.isNameZhColorCustom = isNameZhColorCustom;
	}
	public Integer getIsImageCustom() {
		return isImageCustom;
	}
	public void setIsImageCustom(Integer isImageCustom) {
		this.isImageCustom = isImageCustom;
	}
	public Integer getIsNameEnCustom() {
		return isNameEnCustom;
	}
	public void setIsNameEnCustom(Integer isNameEnCustom) {
		this.isNameEnCustom = isNameEnCustom;
	}
	public Integer getIsBackgroundColorCustom() {
		return isBackgroundColorCustom;
	}
	public void setIsBackgroundColorCustom(Integer isBackgroundColorCustom) {
		this.isBackgroundColorCustom = isBackgroundColorCustom;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsRegular() {
		return isRegular;
	}
	public void setIsRegular(Integer isRegular) {
		this.isRegular = isRegular;
	}
	public Integer getIsModular() {
		return isModular;
	}
	public void setIsModular(Integer isModular) {
		this.isModular = isModular;
	}
	public Integer getIsImage() {
		return isImage;
	}
	public void setIsImage(Integer isImage) {
		this.isImage = isImage;
	}
	public Integer getIsChange() {
		return isChange;
	}
	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}
	public Integer getShape() {
		return shape;
	}
	public void setShape(Integer shape) {
		this.shape = shape;
	}
	public Integer getIsShopStrokes() {
		return isShopStrokes;
	}
	public void setIsShopStrokes(Integer isShopStrokes) {
		this.isShopStrokes = isShopStrokes;
	}
	public Integer getIsBackgroundColor() {
		return isBackgroundColor;
	}
	public void setIsBackgroundColor(Integer isBackgroundColor) {
		this.isBackgroundColor = isBackgroundColor;
	}
	public Integer getIsNameEn() {
		return isNameEn;
	}
	public void setIsNameEn(Integer isNameEn) {
		this.isNameEn = isNameEn;
	}
	public Integer getIsContactWay() {
		return isContactWay;
	}
	public void setIsContactWay(Integer isContactWay) {
		this.isContactWay = isContactWay;
	}
	public Integer getIsUserDefined() {
		return isUserDefined;
	}
	public void setIsUserDefined(Integer isUserDefined) {
		this.isUserDefined = isUserDefined;
	}
	public Integer getIsCarlOrBg() {
		return isCarlOrBg;
	}
	public void setIsCarlOrBg(Integer isCarlOrBg) {
		this.isCarlOrBg = isCarlOrBg;
	}
	public Integer getClassification() {
		return classification;
	}
	public void setClassification(Integer classification) {
		this.classification = classification;
	}
	public Integer getMaxModularCount() {
		return maxModularCount;
	}
	public void setMaxModularCount(Integer maxModularCount) {
		this.maxModularCount = maxModularCount;
	}
	public Integer getMinModularCount() {
		return minModularCount;
	}
	public void setMinModularCount(Integer minModularCount) {
		this.minModularCount = minModularCount;
	}
	public String getCarouselImageSize() {
		return carouselImageSize;
	}
	public void setCarouselImageSize(String carouselImageSize) {
		this.carouselImageSize = carouselImageSize;
	}
	public String getCarouselImageWidth() {
		return carouselImageWidth;
	}
	public void setCarouselImageWidth(String carouselImageWidth) {
		this.carouselImageWidth = carouselImageWidth;
	}
	public String getCarouselImageHeight() {
		return carouselImageHeight;
	}
	public void setCarouselImageHeight(String carouselImageHeight) {
		this.carouselImageHeight = carouselImageHeight;
	}
	public Integer getModularnum() {
		return modularnum;
	}
	public void setModularnum(Integer modularnum) {
		this.modularnum = modularnum;
	}
	public String[] getModularWidths() {
		return modularWidths;
	}
	public void setModularWidths(String[] modularWidths) {
		this.modularWidths = modularWidths;
	}
	public String[] getModularHeights() {
		return modularHeights;
	}
	public void setModularHeights(String[] modularHeights) {
		this.modularHeights = modularHeights;
	}
	public Integer getIsCarouselImage() {
		return isCarouselImage;
	}
	public void setIsCarouselImage(Integer isCarouselImage) {
		this.isCarouselImage = isCarouselImage;
	}
	public String getGoodTypeImageSize() {
		return goodTypeImageSize;
	}
	public void setGoodTypeImageSize(String goodTypeImageSize) {
		this.goodTypeImageSize = goodTypeImageSize;
	}
	public String getGoodTypeImageWidth() {
		return goodTypeImageWidth;
	}
	public void setGoodTypeImageWidth(String goodTypeImageWidth) {
		this.goodTypeImageWidth = goodTypeImageWidth;
	}
	public String getGoodTypeImageHeight() {
		return goodTypeImageHeight;
	}
	public void setGoodTypeImageHeight(String goodTypeImageHeight) {
		this.goodTypeImageHeight = goodTypeImageHeight;
	}
	public Integer getIsDescription() {
		return isDescription;
	}
	public void setIsDescription(Integer isDescription) {
		this.isDescription = isDescription;
	}
	public Integer getIsFontColor() {
		return isFontColor;
	}
	public void setIsFontColor(Integer isFontColor) {
		this.isFontColor = isFontColor;
	}
	public String getModularImageSize() {
		return modularImageSize;
	}
	public void setModularImageSize(String modularImageSize) {
		this.modularImageSize = modularImageSize;
	}
	public String getModularImageWidth() {
		return modularImageWidth;
	}
	public void setModularImageWidth(String modularImageWidth) {
		this.modularImageWidth = modularImageWidth;
	}
	public String getModularImageHeight() {
		return modularImageHeight;
	}
	public void setModularImageHeight(String modularImageHeight) {
		this.modularImageHeight = modularImageHeight;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getTemplateVersion() {
		return templateVersion;
	}
	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}
	public Integer getIsSpreadIndex() {
		return isSpreadIndex;
	}
	public void setIsSpreadIndex(Integer isSpreadIndex) {
		this.isSpreadIndex = isSpreadIndex;
	}
	public String getShopStrokesPath() {
		return shopStrokesPath;
	}
	public void setShopStrokesPath(String shopStrokesPath) {
		this.shopStrokesPath = shopStrokesPath;
	}
	public TemplateDetail getTemplateDetail() {
		return templateDetail;
	}
	public void setTemplateDetail(TemplateDetail templateDetail) {
		this.templateDetail = templateDetail;
	}
	
	public String getCustomModularImageSize() {
		return customModularImageSize;
	}
	public void setCustomModularImageSize(String customModularImageSize) {
		this.customModularImageSize = customModularImageSize;
	}
	public String getCustomModularImageWidth() {
		return customModularImageWidth;
	}
	public void setCustomModularImageWidth(String customModularImageWidth) {
		this.customModularImageWidth = customModularImageWidth;
	}
	public String getCustomModularImageHeight() {
		return customModularImageHeight;
	}
	public void setCustomModularImageHeight(String customModularImageHeight) {
		this.customModularImageHeight = customModularImageHeight;
	}
	public Integer getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Integer isHidden) {
		this.isHidden = isHidden;
	}
	public OwTemplateDto() {
		
	}
	//首页模板
	public OwTemplateDto(String code, String shopStrokesPath) throws Exception{
        this.setCode(code);
        this.setModularImageSize(PropertiesUtil.getContexrtParam(Constants.MODULAR_FILE + "_" + this.getCode()+"_STANDARD"));
		if(null != this.getModularImageSize()){
			String[] size = this.getModularImageSize().split("_");
			this.setModularImageWidth(size[0]);
			this.setModularImageHeight(size[1]);
		}
		this.setCustomModularImageSize(PropertiesUtil.getContexrtParam(Constants.MODULAR_FILE + "_" + this.getCode()+"_CUSTOM"));
		if(null != this.getCustomModularImageSize()){
			String[] size = this.getCustomModularImageSize().split("_");
			this.setCustomModularImageWidth(size[0]);
			this.setCustomModularImageHeight(size[1]);
		}
        LoadTempalteData loadTempalteData = new LoadTempalteData();
        List<OwTemplateDto> owTemplates = loadTempalteData.loadTempalteDatas(OwTemplateDto.class, Constants.TEMPALTE_TYPE);;
        for(OwTemplateDto owTemplate : owTemplates){
            if(this.getCode().equals(owTemplate.getCode())){
				this.setTemplateVersion(owTemplate.getTemplateVersion());
                this.setPath(owTemplate.getPath());
                this.setType(owTemplate.getType());
                this.setStatus(owTemplate.getStatus());
                this.setIsImage(owTemplate.getIsImage());
                this.setIsChange(owTemplate.getIsChange());
                this.setShape(owTemplate.getShape());
                this.setModularnum(owTemplate.getModularnum());
                this.setIsRegular(owTemplate.getIsRegular());
                this.setIsShopStrokes(owTemplate.getIsShopStrokes());
				this.setIsBackgroundColor(owTemplate.getIsBackgroundColor());
				this.setIsFontColor(owTemplate.getIsFontColor());
				this.setIsDescription(owTemplate.getIsDescription());
				this.setIsSpreadIndex(owTemplate.getIsSpreadIndex());
				this.setIsCarlOrBg(owTemplate.getIsCarlOrBg());
				this.setIsUserDefined(owTemplate.getIsUserDefined());
				this.setIsNameEn(owTemplate.getIsNameEn());
				this.setIsImageCustom(owTemplate.getIsImageCustom());
				this.setIsNameEnCustom(owTemplate.getIsNameEnCustom());
				this.setIsBackgroundColorCustom(owTemplate.getIsBackgroundColorCustom());
				this.setIsContactWay(owTemplate.getIsContactWay());
				this.setIsTemplateDetail(owTemplate.getIsTemplateDetail());
				this.setIsContactWay(owTemplate.getIsContactWay());
				this.setIsNameZhColorCustom(owTemplate.getIsNameZhColorCustom());
				this.setIsModular(owTemplate.getIsModular());
				this.setIsHidden(owTemplate.getIsHidden());
				
				if(null != owTemplate.getIsCarlOrBg() && 0 != owTemplate.getIsCarlOrBg().intValue()){
					this.setCarouselImageSize(PropertiesUtil.getContexrtParam(Constants.CAROUSEL_HOME_PAGE_FILE + "_" + this.getCode()));
			        String[] carlsize = this.getCarouselImageSize().split("_");
			        this.setCarouselImageWidth(carlsize[0]);
			        this.setCarouselImageHeight(carlsize[1]);
				}
				
				if(StringUtils.isNotBlank(shopStrokesPath)) {
					this.setShopStrokesPath(shopStrokesPath);
				}else {
					this.setShopStrokesPath(owTemplate.getShopStrokesPath());
				}
				if ("0".equals(owTemplate.getIsRegular().toString())) {
                    this.modularWidths = new String[this.modularnum];
                    this.modularHeights = new String[this.modularnum];
                    for(int i = 0; i < this.modularnum; i++){
                        String str = PropertiesUtil.getContexrtParam(Constants.MODULAR_FILE + "_" + this.getCode() + "_" + i);
                        if(null != str){
                            String[] strs = str.split("_");
                            this.modularWidths[i] = strs[0];
                            this.modularHeights[i] = strs[1];
                        }else{
                            this.modularWidths[i] = this.getModularImageWidth();
                            this.modularHeights[i] = this.getModularImageHeight();
                        }
                    }
                }
            }
        }
    }
	// 商品分类页模板
	public OwTemplateDto(String code) throws Exception{
		this.setCode(code);
		String carouselImageSize = PropertiesUtil.getContexrtParam(Constants.CAROUSEL_GOOD_TYPE_FILE+"_"+this.getCode());
		if(StringUtils.isNotBlank(carouselImageSize)){
			this.setCarouselImageSize(carouselImageSize);
			String[] carlsize = this.getCarouselImageSize().split("_");
	        this.setCarouselImageWidth(carlsize[0]);
	        this.setCarouselImageHeight(carlsize[1]);
		}
		String goodTypeImageSize = PropertiesUtil.getContexrtParam(Constants.GOOD_TYPE_FILE+"_"+this.getCode());
		if(StringUtils.isNotBlank(goodTypeImageSize)){
			this.setGoodTypeImageSize(goodTypeImageSize);
			String[] sizes = this.getGoodTypeImageSize().split("_");
			this.setGoodTypeImageWidth(sizes[0]);
			this.setGoodTypeImageHeight(sizes[1]);
		}
		LoadTempalteData loadTempalteData = new LoadTempalteData();
        List<OwTemplateDto> owTemplates = loadTempalteData.loadTempalteDatas(OwTemplateDto.class, Constants.TEMPALTE_TYPE);
        for(OwTemplateDto owTemplate : owTemplates){
			if(this.getCode().equals(owTemplate.getCode())){
				this.setTemplateVersion(owTemplate.getTemplateVersion());
				this.setPath(owTemplate.getPath());
				this.setType(owTemplate.getType());
				this.setStatus(owTemplate.getStatus());
				this.setIsCarouselImage(owTemplate.getIsImage());
				this.setIsDescription(owTemplate.getIsDescription());
				this.setIsFontColor(owTemplate.getIsFontColor());
			}
		}
	}
	
	// 编辑模板首页店招
    public static String _generateShopStrokes(String shopStrokesPath) {
    	if(StringUtils.isNotBlank(shopStrokesPath) 
    			&& shopStrokesPath.indexOf(Constants.BASE_PATH) == -1) {
    		String path = ImageConstants.getCloudstZoomPath(shopStrokesPath,
					PropertiesUtil.getContexrtParam(ImageConstants.SHOP_STROKES_FILE));
    		return path;
    	} else {
    		return shopStrokesPath;
    	}
	}
    // 排序
 	public static List<OwTemplateDto> sortList(List<OwTemplateDto> templateList) {
 		Collections.sort(templateList, new Comparator<OwTemplateDto>() {
 			public int compare(OwTemplateDto template1, OwTemplateDto template2){
 				return template1.getSort().compareTo(template2.getSort());
 			}
 		});
 		return templateList;
 	}
 	
 	public String getTemplateDtoVersion(){
 		if(null != this.code){
 			Pattern pattern = Pattern.compile("V\\d+");
 			Matcher matcher = pattern.matcher(this.code);
 			if(matcher.find()){
 				return matcher.group();
 			}
 		}
 		return null;
 	}
}
