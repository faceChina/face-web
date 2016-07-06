package com.zjlp.face.web.server.product.material.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.dao.AlbumDao;
import com.zjlp.face.web.server.product.material.dao.PhotoDao;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;
import com.zjlp.face.web.server.product.material.service.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService{

	@Autowired
	private AlbumDao albumDao;
	
	@Autowired
	private PhotoDao photoDao;
	
	
	@Override
	public Pagination<AlbumDto> findAlbumPageList(AlbumDto albumDto,
			Pagination<AlbumDto> pagination) {
		Integer totalRow = albumDao.getCount(albumDto);
		List<AlbumDto> datas = albumDao.findPageList(albumDto, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public Pagination<AlbumDto> findWapAlbumPageList(AlbumDto albumDto,
			Pagination<AlbumDto> pagination) {
		Integer totalRow = albumDao.getWapCount(albumDto);
		List<AlbumDto> datas = albumDao.findWapPageList(albumDto, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public void addAlbum(Album album) {
		albumDao.insertSelective(album);
	}


	@Override
	public Album getDefaultAlbumByShopNo(String shopNo) {
		List<Album> albumList = albumDao.findDefaultAlbumByShopNo(shopNo);
		if(null == albumList || 0 == albumList.size()){
			Album album = new Album();
			Date date = new Date();
			album.setIsDefault(true);
			album.setName("默认");
			album.setShopNo(shopNo);
			album.setCreateTime(date);
			album.setUpdateTime(date);
			albumDao.insertSelective(album);
			return album;
		}
		this._delAlbum(albumList);
		return albumList.get(0);
	}
	
	private void _delAlbum(List<Album> albumList){
		if(1 < albumList.size()){
			for(int i=1;i<=albumList.size()-1;i++){
				int sumPhoto = albumDao.getCountPhoto(albumList.get(i).getId());
				if(0 == sumPhoto){
					albumDao.deleteByPrimaryKey(albumList.get(i).getId());
					continue;
				}
				photoDao.editMovePhoto(albumList.get(i).getId(), albumList.get(0).getId());
				albumDao.deleteByPrimaryKey(albumList.get(i).getId());
			}
		}
	}

	@Override
	public int getCountPhoto(Long albumId) {
		return albumDao.getCountPhoto(albumId);
	}


	@Override
	public void delAlbum(Long id) {
		albumDao.deleteByPrimaryKey(id);
	}


	@Override
	public Album getAlbumById(Long id) {
		return albumDao.selectByPrimaryKey(id);
	}


	@Override
	public void editAlbum(Album album) {
		albumDao.updateByPrimaryKeySelective(album);
	}


	@Override
	public List<Album> findAlbumByShopNo(String shopNo) {
		return albumDao.findAlbumByShopNo(shopNo);
	}
	
	@Override
	public List<Album> findAlbumByName(Album album) {
		return albumDao.findAlbumByName(album);
	}


	@Override
	public Album getById(Long id) {
		return albumDao.selectByPrimaryKey(id);
	}


	@Override
	public List<AlbumDto> findAlbumByIds(List<Long> ids) {
		return albumDao.findByIdList(ids);
	}

	@Override
	public Integer getAlbumCountByShopNo(String shopNo) {
		return albumDao.getCount(shopNo);
	}
	
}
