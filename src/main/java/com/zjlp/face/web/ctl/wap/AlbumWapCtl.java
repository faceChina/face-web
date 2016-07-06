package com.zjlp.face.web.ctl.wap;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.bussiness.AlbumBusiness;
import com.zjlp.face.web.server.product.material.bussiness.PhotoAlbumBusiness;
import com.zjlp.face.web.server.product.material.bussiness.PhotoBusiness;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;

@Controller
@RequestMapping("/wap/{shopNo}/any/album/")
public class AlbumWapCtl extends WapCtl {

	@Autowired
	private PhotoBusiness photoBusiness;
	
	@Autowired
	private AlbumBusiness albumBusiness;
	
	@Autowired
	private PhotoAlbumBusiness photoAlbumBusiness;
	
	/**
	 * 相册下面的图片列表
	 * @Title: photoList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月31日 下午4:26:31
	 */
	@RequestMapping(value="photolist")
	public String photoList(PhotoDto photoDto,Pagination<PhotoDto> pagination,Model model){
		pagination.setPageSize(18);
		photoDto.setShopNo(super.getShopNo());
		pagination = photoBusiness.findPhotoList(photoDto, pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("photoDto", photoDto);
		return "/wap/product/material/photo";
	}
	
	@RequestMapping(value="photolistappend")
	@ResponseBody
	public String photoListAppend(PhotoDto photoDto,Pagination<PhotoDto> pagination,Model model){
		try {
			photoDto.setShopNo(super.getShopNo());
			pagination = photoBusiness.findPhotoList(photoDto, pagination);
			return super.getReqJson(true, JSONObject.fromObject(pagination).toString());
		} catch (AlbumException e) {
			return super.getReqJson(false, "加载数据失败");
		}
	}
	
	/**
	 * 查询专辑下面的相册
	 * @Title: albumlist
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param photoDto
	 * @param pagination
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月31日 下午8:26:31
	 */
	@RequestMapping(value="albumlist")
	public String albumlist(AlbumDto albumDto,Pagination<AlbumDto> pagination,Model model){
		Assert.notNull(albumDto,"参数为空");
		Assert.notNull(albumDto.getPhotoAlbumId(),"专辑编号为空");
		albumDto.setShopNo(super.getShopNo());
		pagination = albumBusiness.findWapAlbumPageList(albumDto, pagination);
		PhotoAlbum photoAlbum =photoAlbumBusiness.getById(albumDto.getPhotoAlbumId());
		model.addAttribute("pagination", pagination);
		model.addAttribute("photoDto", albumDto);
		model.addAttribute("photoAlbum", photoAlbum);
		model.addAttribute("shopNo", super.getShopNo());
		//第一套
		if(photoAlbum.getArticleTemplateType().equals("1")){
			return "/wap/product/material/albumphoto1";
		} else if(photoAlbum.getArticleTemplateType().equals("2")){
			return "/wap/product/material/albumphoto2";
		} else if(photoAlbum.getArticleTemplateType().equals("3")){
			return "/wap/product/material/albumphoto3";
		} else {
			return "/wap/common/error404";
		}
		
	}
	
	@RequestMapping(value="albumlistappend")
	public String albumlistappend(AlbumDto albumDto,Pagination<AlbumDto> pagination,Model model){
		try {
			albumDto.setShopNo(super.getShopNo());
			pagination = albumBusiness.findWapAlbumPageList(albumDto, pagination);
			return super.getReqJson(true, JSONObject.fromObject(pagination).toString());
		} catch (AlbumException e) {
			return super.getReqJson(false, "加载数据失败");
		}
	}
}
