package com.zjlp.face.web.constants;

public enum MobilecodeType {

	/** 注册 : 0 */
	mobile_regist_0(1, true),
	
	/** 支付密码设置 : 3->4 */
	mobile_paymentcode_0(3, false),
	
	mobile_paymentcode_1(4, false),
	
	/** 手机号码设置 : 5->6 */
	mobile_phonecode_0(5, false),
	
	mobile_phonecode_1(6, true),
	
	/** 银行卡绑定 */
	mobile_bankcard_0(7, true),
	
	/** 提现 */
	mobile_tx_0(8, false)
	;
	
	//场景
	private int scene;
	//手机号码是否输入
	private boolean isInput;
	
	private MobilecodeType(int scene, boolean isInput) {
		this.scene = scene;
		this.isInput = isInput;
	}
	public int getScene() {
		return scene;
	}
	public boolean isInput() {
		return isInput;
	}
	public static MobilecodeType getByScene(Integer scene) {
		if (null == scene) return null;
		for (MobilecodeType codeType : MobilecodeType.values()) {
			if (codeType.getScene() == scene.intValue()) {
				return codeType;
			}
		}
		return null;
	}
}
