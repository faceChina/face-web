package com.zjlp.face.web.ctl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.bussiness.AlbumBusiness;
import com.zjlp.face.web.server.product.material.bussiness.PhotoAlbumBusiness;
import com.zjlp.face.web.server.product.material.bussiness.PhotoBusiness;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.Photo;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoAlbumDto;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;

@Controller
@RequestMapping("/u/stuff/album/")
public class AlbumCtl  extends BaseCtl{
	
	
	@Autowired
	private AlbumBusiness albumBusiness;
	
	@Autowired
	private PhotoAlbumBusiness photoAlbumBusiness;
	
	@Autowired
	private PhotoBusiness photoBusiness;
	
	
	
	@RequestMapping(value="listAlbum")
	public String albumList(AlbumDto albumDto,Pagination<AlbumDto> pagination, Model model){
		albumDto.setShopNo(super.getShopNo());
		pagination = albumBusiness.findAlbumPageList(albumDto, pagination);
		model.addAttribute("pagination", pagination);
		return "/m/album/photo-manage";
	}
	
	@RequestMapping(value="getAlbum")
	@ResponseBody
	public String getAlbum(Long id, Model model){
		Assert.notNull(id);
		Album album = albumBusiness.getById(id);
		Assert.notNull(album,"没有找到相册");
		return JSONObject.fromObject(album).toString();
	}
	
	
	@RequestMapping(value="addAlbum" , method=RequestMethod.POST)
	public String addAlbum(Album album, Model model){
		album.setShopNo(super.getShopNo());
		album.setIsDefault(false);
		albumBusiness.addAlbum(album,super.getUserId());
		return super.getRedirectUrl("/u/stuff/album/listAlbum");
	}
	
	/**
	 * 修改相册信息
	 * @Title: editAlbum
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param album
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月31日 上午9:47:57
	 */
	@RequestMapping(value="editAlbum" , method=RequestMethod.POST)
	public String editAlbum(Album album,Integer urlType,Long albumId,Pagination<PhotoDto> pagination, Model model){
		album.setShopNo(super.getShopNo());
		album.setIsDefault(false);
		if (StringUtils.isBlank(album.getMemo())) {
			album.setMemo("");
		}
		albumBusiness.editAlbum(album,super.getUserId(),super.getShopNo());
		model.addAttribute("curPage", pagination.getCurPage());
		model.addAttribute("pageSize", pagination.getPageSize());
		if(null != urlType && 1 == urlType){
			model.addAttribute("albumId", albumId);
			model.addAttribute("urlType", urlType);
			return super.getRedirectUrl("/u/stuff/album/listPhoto");
		}
		return super.getRedirectUrl("/u/stuff/album/listAlbum");
	}
	
	@RequestMapping(value="delAlbum" , method=RequestMethod.POST)
	@ResponseBody
	public String delAlbum(Long id,Model model){
		Album album = new Album();
		album.setId(id);
		album.setShopNo(super.getShopNo());
		return albumBusiness.delAlbum(album,super.getUserId());
	}
	
	/**
	 * 根据多个ID查询相册列表
	 * @Title: findAlbumByIds
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param ids
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月31日 下午2:49:50
	 */
	@RequestMapping(value="findAlbumByIds" , method=RequestMethod.POST)
	@ResponseBody
	public String findAlbumByIds(String ids,Model model){
		Assert.hasLength(ids);
		List<AlbumDto> list = albumBusiness.findAlbumByIdList(ids);
		return JSONArray.fromObject(list).toString();
	}
	
	@RequestMapping(value="listPhotoAlbum")
	public String listPhotoAlbum(PhotoAlbumDto photoAlbumDto,Pagination<PhotoAlbumDto> pagination, Model model){
		photoAlbumDto.setShopNo(super.getShopNo());
		pagination = photoAlbumBusiness.findPhotoAlbumPageList(photoAlbumDto, pagination);
		model.addAttribute("pagination", pagination);
		return "/m/album/photo-album-manage";
	}
	
	
	@RequestMapping(value="addPhotoAlbum" , method=RequestMethod.GET)
	public String addPhotoAlbum(Model model){
		return "/m/album/photo-album-add";
	}
	
	@RequestMapping(value="addPhotoAlbum" , method=RequestMethod.POST)
	public String savePhotoAlbum(PhotoAlbumDto photoAlbumDto, Model model){
		photoAlbumDto.setShopNo(super.getShopNo());
		//photoAlbumDto.setIsDefault(false);
		photoAlbumBusiness.addPhotoAlbum(photoAlbumDto,super.getUserId());
		return super.getRedirectUrl("/u/stuff/album/listPhotoAlbum");
	}
	
	@RequestMapping(value="editPhotoAlbum" , method=RequestMethod.GET)
	public String initEditPhotoAlbum(Long id,Model model){
		Assert.notNull(id,"参数为空");
		PhotoAlbumDto photoAlbum = photoAlbumBusiness.getById(id);
		model.addAttribute("photoAlbum", photoAlbum);
		return "/m/album/photo-album-add";
	}
	
	@RequestMapping(value="editPhotoAlbum" , method=RequestMethod.POST)
	public String editPhotoAlbum(PhotoAlbumDto photoAlbumDto, Model model){
		photoAlbumBusiness.editPhotoAlbum(photoAlbumDto,super.getUserId(),super.getShopNo());
		return super.getRedirectUrl("/u/stuff/album/listPhotoAlbum");
	}
	
	@RequestMapping(value="delPhotoAlbum" , method=RequestMethod.POST)
	@ResponseBody
	public String delPhotoAlbum(Long id,Model model){
		PhotoAlbum photoAlbum = new PhotoAlbum();
		photoAlbum.setId(id);
		return photoAlbumBusiness.delPhotoAlbum(photoAlbum,super.getUserId());
	}
	
	
	@RequestMapping(value="queryAlbumList" , method=RequestMethod.POST)
	@ResponseBody
	public String queryAlbumList(Album album,Model model){
		album.setShopNo(super.getShopNo());
		List<Album> albumList = albumBusiness.findAlbumByName(album);
		return JSONArray.fromObject(albumList).toString();
	}
	
	@RequestMapping(value="listPhoto")
	public String listPhoto(PhotoDto photoDto,Integer urlType,Pagination<PhotoDto> pagination, Model model){
		photoDto.setShopNo(super.getShopNo());
		pagination = photoBusiness.findPhotoList(photoDto, pagination);
		List<Album> albumList = albumBusiness.findAlbumByShopNo(super.getShopNo());
		if(null != photoDto && null != photoDto.getAlbumId()){
			//当前相册
			Album curent = albumBusiness.getById(photoDto.getAlbumId());
			model.addAttribute("album", curent);
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("albumList", albumList);
		model.addAttribute("size", pagination.getDatas().size());
		if(null != urlType && 1== urlType){
			return "/m/album/photo-manage-list";
		}
		return "/m/album/photo-list";
	}
	
	
	
	
	@RequestMapping(value="uploadPhoto", method=RequestMethod.POST)
	public String uploadPhoto(Photo photo,Integer urlType, Model model){
		photo.setShopNo(super.getShopNo());
		photoBusiness.addPhoto(photo, super.getUserId());
		model.addAttribute("urlType", urlType);
		model.addAttribute("albumId", photo.getAlbumId());
		return super.getRedirectUrl("/u/stuff/album/listPhoto");
	}
	
	/**
	 * 删除图片
	 * @Title: delPhoto
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param ids
	 * @param model
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月30日 下午4:37:31
	 */
	@RequestMapping(value="delPhoto", method=RequestMethod.POST)
	@ResponseBody
	public String delPhoto(String ids, Model model){
		Assert.hasLength(ids);
		try {
			photoBusiness.delPhoto(ids,super.getShopNo(),super.getUserId());
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
	
	@RequestMapping(value="updatePhoto", method=RequestMethod.POST)
	public String updatePhoto(Photo photo,Long nowAlbumId,Integer urlType,Pagination<PhotoDto> pagination, Model model){
		Assert.notNull(photo);
		photoBusiness.editPhotoName(photo,super.getShopNo());
		model.addAttribute("urlType", urlType);
		model.addAttribute("albumId", nowAlbumId);
		model.addAttribute("curPage", pagination.getCurPage());
		model.addAttribute("pageSize", pagination.getPageSize());
		return super.getRedirectUrl("/u/stuff/album/listPhoto");
	}
	
	@RequestMapping(value="movePhoto", method=RequestMethod.POST)
	@ResponseBody
	public String movePhoto(String ids,Long albumId, Model model){
		Assert.hasLength(ids);
		try {
			photoBusiness.movePhoto(ids, albumId,super.getShopNo());
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
}
