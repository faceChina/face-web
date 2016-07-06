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

import com.alibaba.dubbo.common.utils.StringUtils;
import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.bussiness.PhotoBusiness;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.Photo;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;
import com.zjlp.face.web.server.product.material.service.AlbumService;
import com.zjlp.face.web.server.product.material.service.PhotoService;

@Service
public class PhotoBusinessImpl implements PhotoBusiness{

	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired(required=false)
	private ImageService imageService;
	
	@Override
	public void editPhotoName(Photo photo,String shopNo) throws AlbumException {
		Assert.notNull(photo);
		Assert.notNull(photo.getId());
		try {
			Photo p = photoService.getById(photo.getId());
			AssertUtil.isTrue(null != p && p.getShopNo().equals(shopNo), "操作的数据不属于当前店铺");
			photo.setUpdateTime(new Date());
			AssertUtil.notNull(photo, "参数为空");
			photoService.editPhoto(photo);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "AlbumException" })
	public void delPhoto(String idsStr,String shopNo,Long userId) throws AlbumException {
		AssertUtil.hasLength(idsStr, "参数为空");
		try {
			List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
			String[] ids = idsStr.split(",");
			for (String idStr : ids) {
				if(StringUtils.isBlank(idStr)){
					continue;
				}
				Long id = Long.valueOf(idStr);
				//验证
				Photo photo = photoService.getById(id);
				AssertUtil.isTrue(photo.getShopNo().equals(shopNo), "操作的数据不属于当前店铺");
				//删除数据
				photoService.delPhoto(id);
				//删除TFS图片
				FileBizParamDto bizParamDto = new FileBizParamDto(null,userId,shopNo,"PHOTO",id.toString(),ImageConstants.PHOTO_FILE,1);
				list.add(bizParamDto);
			}
			//删除图片服务器上图片资源
			String flag = imageService.remove(list);
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "删除图片失败:"+flag);
		} catch (Exception e) {
			throw new AlbumException(e);
		}	
	}


	@Override
	public void movePhoto(String idsStr, Long albumId,String shopNo) throws AlbumException {
		try {
			AssertUtil.hasLength(idsStr, "图片ID为空");
			AssertUtil.notNull(albumId, "相册ID为空");
			Album album = albumService.getAlbumById(albumId);
			AssertUtil.notNull(album, "没有该相册");
			for (String idStr : idsStr.split(",")) {
				if(StringUtils.isBlank(idStr)){
					continue;
				}
				Long id = Long.valueOf(idStr);
				//验证
				Photo photo = photoService.getById(id);
				AssertUtil.isTrue(null != photo && photo.getShopNo().equals(shopNo), "操作的数据不属于当前店铺");
				Photo editPhoto = new Photo();
				editPhoto.setId(id);
				editPhoto.setAlbumId(albumId);
				editPhoto.setUpdateTime(new Date());
				photoService.moveOnePhoto(editPhoto);
			}
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}


	@Override
	public Pagination<PhotoDto> findPhotoList(PhotoDto photoDto,
			Pagination<PhotoDto> pagination) throws AlbumException {
		try {
			return photoService.findPhotoPageList(photoDto, pagination);
		} catch (Exception e) {
			throw new AlbumException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "AlbumException" })
	public void addPhoto(Photo photo, Long userId) throws AlbumException {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try {
			AssertUtil.notNull(photo, "参数为空");
			AssertUtil.notNull(photo.getPath(), "path路径为空");
			AssertUtil.notNull(photo.getShopNo(), "参数为空");
			AssertUtil.notNull(userId, "用户ID为空");
			
			Integer photoSor = photoService.getMaxPhotoMax(photo.getShopNo());
			if(null == photoSor){
				photoSor = 0;
			}
			photo.setSort(++photoSor);
			photo.setName("图片名称");
			photoService.addPhoto(photo);
			AssertUtil.notNull(photo.getId(), "ID为空");
			
	        //上传图片到TFS
            FileBizParamDto dto = new FileBizParamDto(null,userId,photo.getShopNo(),"PHOTO",photo.getId().toString(),ImageConstants.PHOTO_FILE,1);
            dto.setImgData(photo.getPath());
            list.add(dto);
            String flag = imageService.addOrEdit(list);
            
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
			
            //修改图片TFS路径
            Photo editPhoto = new Photo();
            editPhoto.setId(photo.getId());
            editPhoto.setPath(fbpDto.get(0).getImgData());
            photoService.editPhoto(editPhoto);
		} catch (Exception e) {
		  	//回滚TFS
        	if(null != list){
        		imageService.remove(list);
        	}
			throw new AlbumException(e);
		}
		
	}

}
