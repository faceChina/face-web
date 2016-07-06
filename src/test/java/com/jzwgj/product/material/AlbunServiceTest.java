package com.jzwgj.product.material;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;
import com.zjlp.face.web.server.product.material.service.AlbumService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class AlbunServiceTest {

	@Autowired
	private AlbumService albumService;
	
	@Test
	public void test() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(15l);
		ids.add(16l);
		List<AlbumDto> albumDtos = albumService.findAlbumByIds(ids);
		System.out.println(JSONObject.fromObject(albumDtos));
	}

}
