package com.zjlp.face.web.util.pattern;

public class TestData {

	private int id;
	private String name;
	public TestData(){}
	public TestData(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "TestData [id=" + id + ", name=" + name + "]";
	}
	
}
