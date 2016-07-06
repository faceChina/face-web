package com.zjlp.face.web.component.metaq.producer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.jzwgj.metaq.vo.SystemMessage;
import com.jzwgj.metaq.vo.Topic;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.component.metaq.MetaAnsyHelper;
import com.zjlp.face.web.component.metaq.log.MetaLog;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.util.constantsUtil.ConstantsUtil;

@Component
public class SystemMetaOperateProducer {

	private Logger _logger = Logger.getLogger("metaqInfoLog");

	@Autowired
	private UserService userService;
	@Autowired
	private MetaQProviderClinet metaQProviderClinet;
	
	public void senderAnsy(Long userId,String content ,String title) {
		_logger.info("系统消息调用的线程" + Thread.currentThread().getName());
		// 推送开关
		String switchProducer = PropertiesUtil
				.getContexrtParam("SWITCH_METAQ_PRODUCER_SYSTEM");
		if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
			_logger.warn(MetaLog.getString("Switch.Order.Reserve"));
			return;
		}
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			_logger.info(MetaLog.getString("System.Message.Begin", userId.toString(),content));
			SystemMessage systemMessage = new SystemMessage();
			systemMessage.setType(9);
			systemMessage.setContent(content);
			systemMessage.setTitle("");
			systemMessage.setUserId(userId);
			systemMessage.setUserType(ConstantsUtil.U_ADMIN);
			executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.SYSTEMTOPIC, systemMessage.toJson()));
			_logger.info(MetaLog.getString("Reserve.Admin.End", userId.toString(), content));
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
	
	public void ansyAll(List<Subbranch> list) {
		String title = "";
		for (Subbranch subbranch : list) {
			try {
				String content = new StringBuilder("您的公司").append(subbranch.getShopName())
						.append("，已与上级解除绑定关系，历史订单不受影响，点击切换公司进入历史分公司进行管理！").toString();
				this.senderAnsy(subbranch.getUserId(),content,title);
			} catch (Exception e) {
				// 单条发送失败下一条
				continue;
			}
		}
	}
	
}
