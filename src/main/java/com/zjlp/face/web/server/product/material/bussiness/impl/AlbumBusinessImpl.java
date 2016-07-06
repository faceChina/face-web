package com.zjlp.face.web.server.product.material.bussiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.bussiness.AlbumBusiness;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;
import com.zjlp.face.web.server.product.material.service.AlbumPhotoAlbumRelationService;
import com.zjlp.face.web.server.product.material.service.AlbumService;
import com.zjlp.face.web.server.product.material.service.PhotoService;

@Service
public class AlbumBusinessImpl implements AlbumBusiness{
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private AlbumPhotoAlbumRelationService albumPhotoAlbumRelationService;
	
	@Autowired(required=false)
	private ImageService imageService;

	@Override
	public Pagination<AlbumDto> findAlbumPageList(AlbumDto albumDto,
			Pagination<AlbumDto> pagination) throws AlbumException{
		try {
			AssertUtil.notNull(albumDto, "参数为空");
			AssertUtil.notNull(albumDto.getShopNo(), "参数为空");
			return albumService.findAlbumPageList(albumDto, pagination);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}
	
	@Override
	public Pagination<AlbumDto> findWapAlbumPageList(AlbumDto albumDto,
			Pagination<AlbumDto> pagination) throws AlbumException{
		try {
			AssertUtil.notNull(albumDto, "参数为空");
			AssertUtil.notNull(albumDto.getShopNo(), "参数为空");
			return albumService.findWapAlbumPageList(albumDto, pagination);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addAlbum(Album album,Long userId) throws AlbumException {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try {
			AssertUtil.notNull(album, "参数为空");
			Date date = new Date();
			album.setCreateTime(date);
			album.setUpdateTime(date);
			albumService.addAlbum(album);
			
			AssertUtil.notNull(album.getId(), "albumId");
			
            //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto(null,userId,album.getShopNo(),"ALBUM",album.getId().toString(),ImageConstants.ALBUM_FILE,1);
            dto.setImgData(album.getPath());
            list.add(dto);
            String flag = imageService.addOrEdit(list);
            
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
          
            Album editAlbum = new Album();
            editAlbum.setId(album.getId());
            editAlbum.setPath(fbpDto.get(0).getImgData());
            albumService.editAlbum(editAlbum);
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
	public String delAlbum(Album album,Long userId) throws AlbumException {
		
		try {
			AssertUtil.notNull(album, "参数为空");
			AssertUtil.notNull(album.getId(), "ID为空");
			AssertUtil.notNull(album.getShopNo(), "shopNo为空");
			
			Album queryAlbum = albumService.getAlbumById(album.getId());
			AssertUtil.notNull(queryAlbum, "数据异常");
			
			if(queryAlbum.getIsDefault()){
				return "FAIL";
			}
			
           if(0 == albumService.getCountPhoto(queryAlbum.getId())){
				albumService.delAlbum(queryAlbum.getId());
				albumPhotoAlbumRelationService.delAlbumPhotoAlbumRelationByAlbumId(queryAlbum.getId());
				return "SUCCESS";
           	}
			
			Album defaultAlbum = albumService.getDefaultAlbumByShopNo(queryAlbum.getShopNo());
			
			photoService.movePhoto(queryAlbum.getId(), defaultAlbum.getId());
			albumService.delAlbum(queryAlbum.getId());
			albumPhotoAlbumRelationService.delAlbumPhotoAlbumRelationByAlbumId(queryAlbum.getId());
			
			List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
	         //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto(null,userId,album.getShopNo(),"ALBUM",album.getId().toString(),ImageConstants.ALBUM_FILE,1);
            dto.setImgData(album.getPath());
            list.add(dto);
            imageService.remove(list);
            
			return "SUCCESS";
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	public void editAlbum(Album album,Long userId,String shopNo) throws AlbumException {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try {
			AssertUtil.notNull(album, "参数为空");
			AssertUtil.notNull(album.getId(), "ID为空");
			AssertUtil.notNull(userId, "userId为空");
			AssertUtil.hasLength(shopNo, "shopNo为空");
			album.setUpdateTime(new Date());
			albumService.editAlbum(album);
			
			 //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto(null,userId,album.getShopNo(),"ALBUM",album.getId().toString(),ImageConstants.ALBUM_FILE,1);
            dto.setImgData(album.getPath());
            list.add(dto);
            String flag = imageService.addOrEdit(list);
            
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
          
            Album editAlbum = new Album();
            editAlbum.setId(album.getId());
            editAlbum.setPath(fbpDto.get(0).getImgData());
            albumService.editAlbum(editAlbum);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}

	@Override
	public List<Album> findAlbumByShopNo(String shopNo) throws AlbumException {
		try {
			AssertUtil.notNull(shopNo, "店铺编号为空");
			return albumService.findAlbumByShopNo(shopNo);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}
	
	@Override
	public List<Album> findAlbumByName(Album album) throws AlbumException {
		try {
			AssertUtil.notNull(album, "参数为空");
			AssertUtil.notNull(album.getShopNo(), "店铺编号为空");
			return albumService.findAlbumByName(album);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}

	@Override
	public Album getById(Long id) throws AlbumException {
		return albumService.getById(id);
	}

	@Override
	public List<AlbumDto> findAlbumByIdList(String idsStr)
			throws AlbumException {
		Assert.hasLength(idsStr);
		try {
			String[] strs = idsStr.split(",");
			AssertUtil.isTrue(strs.length > 0, "参数异常");
			List<Long> ids = new ArrayList<Long>();
			for (String s : strs) {
				Long id = Long.valueOf(s);
				ids.add(id);
			}
			return albumService.findAlbumByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AlbumException(e);
		}
	}
	
	
}
