package com.zjlp.face.web.util.pattern;


public class TestDataSuit extends AbstractElementSuit<TestData> {

	public TestDataSuit(TestData data) {
		super(data);
	}

	public static void main(String[] args) {
		TestDataSuit root = new TestDataSuit(new TestData(1, "lys"));
		root.addChildByData(new TestData(2, "2"))
		    .addChildByData(new TestData(3, "3"))
		    .addChildByData(new TestData(4, "4"));
		
		root.addChildByData(new TestData(11, "11"))
		    .addChildByData(new TestData(12, "12"))
		    .addChildByData(new TestData(13, "13"));
		
		for (TestData data : root.listDatas()) {
			System.out.println(data);
		}
	}

	@Override
	protected TestDataSuit cover(TestData data) {
		return new TestDataSuit(data);
	}

}
