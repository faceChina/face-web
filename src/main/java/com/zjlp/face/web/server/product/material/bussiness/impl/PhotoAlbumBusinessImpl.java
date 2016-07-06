package com.zjlp.face.web.server.product.material.bussiness.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.bussiness.PhotoAlbumBusiness;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.AlbumPhotoAlbumRelation;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoAlbumDto;
import com.zjlp.face.web.server.product.material.service.AlbumPhotoAlbumRelationService;
import com.zjlp.face.web.server.product.material.service.AlbumService;
import com.zjlp.face.web.server.product.material.service.PhotoAlbumService;

@Service
public class PhotoAlbumBusinessImpl implements PhotoAlbumBusiness{

	@Autowired
	private PhotoAlbumService photoAlbumService;
	
	@Autowired
	private AlbumPhotoAlbumRelationService albumPhotoAlbumRelationService;
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired(required=false)
	private ImageService imageService;
	
	@Override
	public Pagination<PhotoAlbumDto> findPhotoAlbumPageList(
			PhotoAlbumDto photoAlbumDto, Pagination<PhotoAlbumDto> pagination)
			throws AlbumException {
		try {
			return photoAlbumService.findPhotoAlbumPageList(photoAlbumDto, pagination);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}


	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "AlbumException" })
	public void addPhotoAlbum(PhotoAlbumDto photoAlbumDto,Long userId) throws AlbumException {
		
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try {
			AssertUtil.notNull(photoAlbumDto, "参数为空");
			AssertUtil.notNull(userId, "参数为空");
			
			PhotoAlbum photoAlbum = new PhotoAlbum();
			photoAlbum.setArticleTemplateType(photoAlbumDto.getArticleTemplateType());
			photoAlbum.setName(photoAlbumDto.getName());
			photoAlbum.setPicPath(photoAlbumDto.getPicPath());
			photoAlbum.setShopNo(photoAlbumDto.getShopNo());
			
			photoAlbumService.addPhotoAlbum(photoAlbum);
			AssertUtil.notNull(photoAlbum.getId());
			
			if(StringUtils.isNotBlank(photoAlbumDto.getAlbumItem())) {
				String[] albumIds = photoAlbumDto.getAlbumItem().split(",");
				for (int i = 0; i < albumIds.length; i++) {
					Long albumId = Long.valueOf(albumIds[i]);
					//2.1查询文章是否存在
					Album album = albumService.getAlbumById(albumId);
					AssertUtil.notNull(album);
					//2.2新增文章分类与文章关联关系
					AlbumPhotoAlbumRelation apar = new AlbumPhotoAlbumRelation();
					apar.setAlbumId(album.getId());
					apar.setPhotoAlbumId(photoAlbum.getId());
					apar.setSort(i);
					albumPhotoAlbumRelationService.addAlbumPhotoAlbumRelation(apar);
				}
			}
			
            //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto(null,userId,photoAlbumDto.getShopNo(),"PHOTO_ALBUM",photoAlbum.getId().toString(),ImageConstants.PHOTO_ALBUM_FILE,1);
            dto.setImgData(photoAlbumDto.getPicPath());
            list.add(dto);
            String flag = imageService.addOrEdit(list);
			
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
          
            PhotoAlbum editPhotoAlbum = new PhotoAlbum();
            editPhotoAlbum.setId(photoAlbum.getId());
            editPhotoAlbum.setPicPath(fbpDto.get(0).getImgData());
            photoAlbumService.editPhotoAlbum(editPhotoAlbum);
		} catch (Exception e) {
		 	//回滚TFS
        	if(null != list){
        		imageService.remove(list);
        	}
			throw new AlbumException(e);
		}
	}


	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "AlbumException" })
	public void editPhotoAlbum(PhotoAlbumDto photoAlbumDto,Long userId,String shopNo) throws AlbumException {
		Assert.notNull(photoAlbumDto,"参数为空");
		Assert.notNull(photoAlbumDto.getId(),"修改专辑编号为空");
		Assert.notNull(userId,"userId参数为空");
		Assert.notNull(shopNo,"shopNo参数为空");
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try {
			PhotoAlbum checkPa = photoAlbumService.getById(photoAlbumDto.getId());
			AssertUtil.notNull(checkPa, "没有找到相册专辑数据");
			AssertUtil.isTrue(checkPa.getShopNo().equals(shopNo), "当前数据不是当前店铺所有");
			//修改
			PhotoAlbum photoAlbum = photoAlbumDto;
			photoAlbumService.editPhotoAlbum(photoAlbum);
			//删除专辑关联相册关系数据
			albumPhotoAlbumRelationService.delAlbumPhotoAlbumRelationByPhotoAlbumId(photoAlbum.getId());
			//添加新的关联关系
			if(StringUtils.isNotBlank(photoAlbumDto.getAlbumItem())) {
				String[] albumIds = photoAlbumDto.getAlbumItem().split(",");
				for (int i = 0; i < albumIds.length; i++) {
					Long albumId = Long.valueOf(albumIds[i]);
					//2.1查询文章是否存在
					Album album = albumService.getAlbumById(albumId);
					AssertUtil.notNull(album);
					//2.2新增文章分类与文章关联关系
					AlbumPhotoAlbumRelation apar = new AlbumPhotoAlbumRelation();
					apar.setAlbumId(album.getId());
					apar.setPhotoAlbumId(photoAlbum.getId());
					apar.setSort(i);
					albumPhotoAlbumRelationService.addAlbumPhotoAlbumRelation(apar);
				}
			}
			//上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto(null,userId,shopNo,"PHOTO_ALBUM",photoAlbum.getId().toString(),ImageConstants.PHOTO_ALBUM_FILE,1);
            dto.setImgData(photoAlbumDto.getPicPath());
            list.add(dto);
            String flag = imageService.addOrEdit(list);
			
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
          
            PhotoAlbum editPhotoAlbum = new PhotoAlbum();
            editPhotoAlbum.setId(photoAlbum.getId());
            editPhotoAlbum.setPicPath(fbpDto.get(0).getImgData());
            photoAlbumService.editPhotoAlbum(editPhotoAlbum);
		} catch (Exception e) {
			//回滚TFS
        	if(null != list){
        		imageService.remove(list);
        	}
			throw new AlbumException(e);
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "AlbumException" })
	public void changeAlbum(String albumList,PhotoAlbum photoAlbum) throws AlbumException {
		try {
			AssertUtil.notNull(albumList, "参数为空");
			AssertUtil.notNull(photoAlbum, "参数为空");
			AssertUtil.notNull(photoAlbum.getId(), "参数为空");
			
			List<Album> aList = JsonUtil.toArrayBean(albumList, PhotoAlbum.class);
			
			List<AlbumPhotoAlbumRelation> aparList = albumPhotoAlbumRelationService.findAlbumPhotoAlbumRelationByPhotoAlbumId(photoAlbum.getId());
			Integer k = albumPhotoAlbumRelationService.getMaxSortByPhotoAlbumId(photoAlbum.getId());
			
			Map<Long,AlbumPhotoAlbumRelation> newMap = new HashMap<Long, AlbumPhotoAlbumRelation>();
			Map<Long,AlbumPhotoAlbumRelation> oldMap = new HashMap<Long, AlbumPhotoAlbumRelation>();
			
			for (AlbumPhotoAlbumRelation apar : aparList) {
				oldMap.put(apar.getAlbumId(), apar);
			}
			for (Album album : aList) {
				if(oldMap.containsKey(album.getId())){
					newMap.put(album.getId(), oldMap.get(album.getId()));
				}else{
					AlbumPhotoAlbumRelation albumPhotoAlbumRelation = new AlbumPhotoAlbumRelation();
					albumPhotoAlbumRelation.setAlbumId(album.getId());
					albumPhotoAlbumRelation.setPhotoAlbumId(photoAlbum.getId());
					albumPhotoAlbumRelation.setSort(k++);
					albumPhotoAlbumRelationService.addAlbumPhotoAlbumRelation(albumPhotoAlbumRelation);
				}
			}
			for (Map.Entry<Long,AlbumPhotoAlbumRelation> entry : oldMap.entrySet()) {
				if( !newMap.containsKey(entry.getKey())){
					albumPhotoAlbumRelationService.delAlbumPhotoAlbumRelation(entry.getValue().getId());
				}
			}
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "AlbumException" })
	public String delPhotoAlbum(PhotoAlbum photoAlbum,Long userId) throws AlbumException {
		try {
			AssertUtil.notNull(photoAlbum, "参数为空");
			AssertUtil.notNull(photoAlbum.getId(), "参数为空");
			AssertUtil.notNull(userId, "用户ID为空");
			
			albumPhotoAlbumRelationService.delAlbumPhotoAlbumRelationByPhotoAlbumId(photoAlbum.getId());
			
			photoAlbumService.delPhotoAlbum(photoAlbum.getId());
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();	
			return "删除失败";
		}
	}


	@Override
	public List<PhotoAlbum> findPhotoAlbumByShopNo(String shopNo)
			throws AlbumException {
		try {
			AssertUtil.notNull(shopNo, "店铺编号为空");
			return	photoAlbumService.findPhotoAlbumByShopNo(shopNo);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
		 
	}


	@Override
	public PhotoAlbumDto getById(Long id) throws AlbumException {
		PhotoAlbumDto photoAlbum = photoAlbumService.getById(id);
		List<AlbumPhotoAlbumRelation> list = albumPhotoAlbumRelationService.findAlbumPhotoAlbumRelationByPhotoAlbumId(id);
		StringBuffer sb = new StringBuffer();
		for (AlbumPhotoAlbumRelation albumPhotoAlbumRelation : list) {
			sb.append(albumPhotoAlbumRelation.getAlbumId()).append(",");
		}
		photoAlbum.setAlbumItem(sb.toString());
		return photoAlbum;
	}

}
