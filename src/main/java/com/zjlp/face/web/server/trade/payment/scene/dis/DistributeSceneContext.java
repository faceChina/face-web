package com.zjlp.face.web.server.trade.payment.scene.dis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("distributeSceneContext")
public class DistributeSceneContext {

	//默认分配金额场景
	@Autowired
	private DistributeScene defaultDistributeScene;
	
	//代理分配金额场景
	/*@Autowired
	private DistributeScene proxyDistributeScene;*/
	
	@Autowired
	private DistributeScene subbranchDistributeScene;
	//推广分配金额场景
	@Autowired
	private DistributeScene popularizeDistributeScene;
	
	@Autowired
	private DistributeScene employeeDistributeScene;
	
	/**
	 * 获取分配金额场景
	 * @Title: getDistributeScene
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param type
	 * @return
	 * @return DistributeScene
	 * @author phb
	 * @date 2015年5月12日 下午2:24:22
	 */
	public DistributeScene getDistributeScene(Integer type){
		DistributeScene distributeScene = null;
		switch (type) {
		case 1://普通订单
			distributeScene = defaultDistributeScene;
			break;
		case 2://协议订单
			distributeScene = defaultDistributeScene;
			break;
		case 3://预约订单
			break;
		case 4://分店订单
			distributeScene = subbranchDistributeScene;
			break;
		case 5://推广订单
			distributeScene = popularizeDistributeScene;
			break;
		case 6://员工引入
			distributeScene = employeeDistributeScene;
			break;
		default:
			break;
		}
		return distributeScene;
	}
}
