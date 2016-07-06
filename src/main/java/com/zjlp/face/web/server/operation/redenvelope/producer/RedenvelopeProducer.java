package com.zjlp.face.web.server.operation.redenvelope.producer;

import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;

/**
 * 红包基础服务
 * @ClassName: RedenvelopeService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author zyl
 * @date 2015年10月13日 上午11:53:51
 */
public interface RedenvelopeProducer {

	SendRedenvelopeRecord getSendRocordById(Long id);

}
