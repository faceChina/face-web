package com.jzwgj.demo;

import org.dbunit.basetest.BaseTest;
import org.dbunit.tools.DataFileCreator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.zjlp.face.web.server.operation.subbranch.dao.SubbranchDao;

public class DbTest extends BaseTest {

	@Autowired
	private SubbranchDao subbranchDao;
	
	@DatabaseSetup("sample.xml")
	@Test
	public void test() {
		System.out.println(subbranchDao.findByUserId(1000L));
	}
	
	public static void main(String[] args) {
    	DataFileCreator creator = new DataFileCreator("jdbc:mysql://192.168.1.251:3306/wgj_dev_s4", "jzkf", "jzwgjkf");
    	creator.withTableNames("sales_order", "user", "purchase_order", "subbranch");
    	creator.create("D:/adsfa.xml");
    }
	
}
