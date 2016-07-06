package com.zjlp.face.web.server.user.weixin.bussiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.file.xml.XmlHelper;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.WechatException;
import com.zjlp.face.web.server.user.weixin.bussiness.WechatBusiness;
import com.zjlp.face.web.server.user.weixin.domain.MessageBody;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;
import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;
import com.zjlp.face.web.server.user.weixin.domain.wechat.Article;
import com.zjlp.face.web.server.user.weixin.domain.wechat.NewsMessage;
import com.zjlp.face.web.server.user.weixin.domain.wechat.TextMessage;
import com.zjlp.face.web.server.user.weixin.service.WechatService;
import com.zjlp.face.web.server.user.weixin.util.MessageUtil;

@Service
public class WechatBusinessImpl implements WechatBusiness {
	
	@Autowired
	private WechatService wechatService;
	@Autowired
	private ImageService imageService;
	
	/** 组装图文信息 2：修改时 */
	private static final Integer TYPE = 2;
	/** 返回消息类型：文本 */
	private static final String RESP_MESSAGE_TYPE_TEXT = "text";
	/** 返回消息类型：图文 */
	private static final String RESP_MESSAGE_TYPE_NEWS = "news";
	/** 回复事件： 3、关键词回复 */
	private static final Integer EVENT_TYPE = 3;
	// 图片路径
	private static final String WEHCAT_IMAGE_PATH = "$IMAGEPATH$";

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addMessage(String weixinItem, MessageSettingDto msDto) throws WechatException{
		MessageBody messageBody = new MessageBody();
		Date date = new Date();
		String content = null;
		String[] paths = null;
		try {
			AssertUtil.notNull(msDto, "参数回复消息内容为空");
			AssertUtil.notNull(msDto.getRecoveryMode(), "参数回复模式为空");
			JSONArray jsonArray = JSONArray.fromObject(weixinItem);
			//1.新增信息主体
			if(Constants.MODE_TEXT.equals(msDto.getRecoveryMode())){
				//创建文本消息
				content = this._generateText(msDto, date);
			} else {
				//创建图文消息  
				AssertUtil.hasLength(weixinItem, "参数回复消息图文内容为空");
				content = _generateNews(msDto, jsonArray, date);
			}
			messageBody = _initMessageBody(msDto, content, date);
			wechatService.addMessageBody(messageBody);
			AssertUtil.notNull(messageBody.getId(), "消息主体主键为空,新增信息主体失败");
			if(!Constants.MODE_TEXT.equals(msDto.getRecoveryMode())) {
				//上传图片到TFS
				List<FileBizParamDto> list = this._generateFileBizParamDtoList(jsonArray,
						PropertiesUtil.getContexrtParam(ImageConstants.WECHAT_FILE),msDto.getUserId(),msDto.getShopNo(),
						"MESSAGEBODY", messageBody.getId().toString(), ImageConstants.WECHAT_FILE, Constants.FILE_LABEL_USER);
				String flag = imageService.addOrEdit(list);
	            
	            JSONObject jsonObject = JSONObject.fromObject(flag);
	            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
	            String dataJson = jsonObject.getString("data");
	            JSONArray pathArray = JSONArray.fromObject(dataJson);
	            List<FileBizParamDto> fbpDto = pathArray.toList(pathArray, FileBizParamDto.class);
	            String path = fbpDto.get(0).getImgData();
	            if(StringUtils.isNotBlank(path)) {
	            	paths = path.split(",");
	            }
//	            paths = fbpDto.
			}
            // 编辑消息主体
            wechatService.editMessageBody(_generateMessageBody(paths, messageBody));
			//2.新增消息设置
			msDto.setMessageBodyId(messageBody.getId());
			MessageSetting ms = this._initMessageSetting(msDto, date);
			wechatService.addMessageSetting(ms);
			AssertUtil.notNull(ms.getId(), "消息设置主键为空");
			//3.新增消息内容
			if(Constants.MODE_TEXT.equals(msDto.getRecoveryMode())){
				//初始文本消息内容
				MessageContent messageContent = this._initMessageTextContent(msDto, date);
				wechatService.addMessageContent(messageContent);
				AssertUtil.notNull(messageContent.getId(), "消息内容主键为空,新增消息内容失败");
			} else {
				//初始图文消息内容
				List<MessageContent> mList = this._initMessageAticlContent(msDto, jsonArray, date, paths, null);
				for (MessageContent mc : mList) {
					wechatService.addMessageContent(mc);
					AssertUtil.notNull(mc.getId(), "消息内容主键,新增消息内容失败");
				}
			}
			//4.关注时，消息回复时，相同的回复事件下，关闭其他回复模式
			if(Constants.ENVNT_TYPE_ATTENTION.equals(msDto.getEventType()) || 
			   Constants.ENVNT_TYPE_REPLY.equals(msDto.getEventType())) {
				MessageSetting meg = new MessageSetting();
				meg.setEventType(msDto.getEventType());
				meg.setRecoveryMode(msDto.getRecoveryMode());
				meg.setShopNo(msDto.getShopNo());
				meg.setStatus(Constants.NOTDEFAULT);
				wechatService.editMessageSettingStatus(meg);
			}
			
		} catch (Exception e) {
			throw new WechatException(e);
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editMessage(String weixinItem, MessageSettingDto msDto) {
		Date date = new Date();
		String content = "";
		String[] paths = null;
		try {
			AssertUtil.notNull(msDto, "参数消息设置为空");
			
			//1.编辑消息设置
			wechatService.editMessageSetting(_initMessageSetting(msDto, date));
			//2.编辑消息主体
			JSONArray jsonArray = JSONArray.fromObject(weixinItem);
			if(Constants.MODE_TEXT.equals(msDto.getRecoveryMode())){
				//创建文本消息
				content = this._generateText(msDto, date);
				wechatService.editMessageBody(_initMessageBody(msDto, content, date));
			} else {
				//创建图文消息  
				AssertUtil.hasLength(weixinItem, "参数图文内容内容为空");
				content = _generateNews(msDto, jsonArray, date);
				wechatService.editMessageBody(_initMessageBody(msDto, content, date));
				//上传图片到TFS
				List<FileBizParamDto> list = this._generateFileBizParamDtoList(jsonArray,
						PropertiesUtil.getContexrtParam(ImageConstants.WECHAT_FILE),msDto.getUserId(),msDto.getShopNo(),
						"MESSAGEBODY", msDto.getMessageBodyId().toString(), ImageConstants.WECHAT_FILE, Constants.FILE_LABEL_USER);
				String flag = imageService.addOrEdit(list);
	            
	            JSONObject jsonObject = JSONObject.fromObject(flag);
	            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
	            String dataJson = jsonObject.getString("data");
	            JSONArray pathArray = JSONArray.fromObject(dataJson);
	            List<FileBizParamDto> fbpDtos = pathArray.toList(pathArray, FileBizParamDto.class);
	            String path = fbpDtos.get(0).getImgData();
	            if(StringUtils.isNotBlank(path)) {
	            	paths = path.split(",");
	            }
	            // 编辑图片 TODO
	            MessageBody messageBody = wechatService.getMessageBodyById(msDto.getMessageBodyId());
	            // 编辑消息主体
	            wechatService.editMessageBody(_generateMessageBody(paths, messageBody));
			}
			//3.新增消息内容
			if(Constants.MODE_TEXT.equals(msDto.getRecoveryMode())){
				//删除消息内容
				wechatService.delMessageContentByMessageBodyId(msDto.getMessageBodyId());
				//初始文本消息内容
				MessageContent messageContent = this._initMessageTextContent(msDto, date);
				wechatService.addMessageContent(messageContent);
				AssertUtil.notNull(messageContent.getId(), "消息内容主键为空，新增消息内容失败");
			} else {
				//初始图文消息内容
				List<MessageContent> mList = this._initMessageAticlContent(msDto, jsonArray, date, paths, TYPE);
				for (MessageContent mc : mList) {
					if(null == mc.getId()) {
						wechatService.addMessageContent(mc);
					}
					wechatService.editMessageContent(mc);
				}
			}
			//4.关注时，消息回复时，相同的回复事件下，关闭其他回复模式
			if(Constants.ENVNT_TYPE_ATTENTION.equals(msDto.getEventType()) || 
			   Constants.ENVNT_TYPE_REPLY.equals(msDto.getEventType())) {
				MessageSetting meg = new MessageSetting();
				meg.setEventType(msDto.getEventType());
				meg.setRecoveryMode(msDto.getRecoveryMode());
				meg.setShopNo(msDto.getShopNo());
				meg.setStatus(Constants.NOTDEFAULT);
				wechatService.editMessageSettingStatus(meg);
			}
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}
	
	
	private MessageBody _generateMessageBody(String[] paths, MessageBody messageBody) {
		String content = messageBody.getMessageContent();
		if (null != paths && paths.length != 0) {
			for(int i=0; i < paths.length; i++) {
				if(i==0) {
					String bigPath = ImageConstants.getCloudstZoomPath(paths[i], Constants.WECHAT_BIG_SIZE);
					content = content.replace(WEHCAT_IMAGE_PATH+i, bigPath);
				} else {
					String smallPath = ImageConstants.getCloudstZoomPath(paths[i], Constants.WECHAT_SMALL_SIZE);
					content = content.replace(WEHCAT_IMAGE_PATH+i, smallPath);
				}
			}
		}
		MessageBody message = new MessageBody();
		message.setId(messageBody.getId());
		message.setMessageContent(content);
		return message;
	}

	@Override
	public Pagination<MessageSettingDto> findMessageSettingPageList(
			MessageSettingDto dto, Pagination<MessageSettingDto> pagination) throws WechatException{
		
		try {
			AssertUtil.notNull(pagination.getStart(), "分页器起始为空");
			AssertUtil.notNull(pagination.getPageSize(), "分页器每页行数为空");
			dto.setEventType(EVENT_TYPE);
			dto.setStatus(Constants.ISDEFAULT);
			//查询消息设置分页列表
			pagination = wechatService.findMessageSettingPageList(dto, pagination);
			return pagination;
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}
	
	@Override
	public List<MessageContent> findMessageContentByMessageBodyId(Long messageBodyId) {
		try {
			AssertUtil.notNull(messageBodyId, "参数消息主体主键为空");
			//根据消息主体id查询消息内容
			return wechatService.findMessageContentByMessageBodyId(messageBodyId);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	@Override
	public MessageContent getMessageContentById(Long id) {
		
		try {
			AssertUtil.notNull(id, "参数消息内容主键为空");
			//根据主键查询消息内容
			MessageContent messageContent = wechatService.getMessageContentById(id);
			//取略缩图图片路径
			if(null != messageContent && StringUtils.isNotBlank(messageContent.getPicPath())) {
				String picPath = ImageConstants.getCloudstZoomPath(messageContent.getPicPath(),Constants.WECHAT_BIG_SIZE);
				messageContent.setPicPath(picPath);
			}
			//添加链接开头
			if(null != messageContent && StringUtils.isNotBlank(messageContent.getLinkPath()) 
					&& messageContent.getLinkPath().indexOf("http://")==-1) {
				String path = "http://" + messageContent.getLinkPath();
				messageContent.setLinkPath(path);
			}
			return messageContent;
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}
	
	@Override
	public MessageSetting getMessageSettingId(Long id) {
		try {
			AssertUtil.notNull(id, "参数消息设置主键为空");
			//根据主键查询消息设置
			return wechatService.getMessageSettingById(id);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	
	@Override
	public void removeMessageSetting(Long id) throws WechatException {
		
		try {
			AssertUtil.notNull(id, "参数消息设置主键为空");
			MessageSetting messageSetting = new MessageSetting();
			messageSetting.setId(id);
			messageSetting.setUpdateTime(new Date());
			messageSetting.setStatus(Constants.UNVALID);
			//逻辑删除消息设置
			wechatService.removeMessageSetting(messageSetting);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}
	
	@Override
	public void deleteMessageContent(Long id) {
		//删除消息内容
		try {
			AssertUtil.notNull(id, "参数消息内容主键为空");
			wechatService.delMessageContentById(id);
		} catch (Exception e) {
			throw new WechatException(e);
		}
		
	}
	
	//生成文本消息内容
	private String _generateText(MessageSettingDto msDto, Date date) {
        TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(Constants.TOUSERNAME);
        textMessage.setFromUserName(Constants.FROMUSERNAME);
        textMessage.setCreateTime(date.getTime());
        textMessage.setMsgType(RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent(msDto.getMessageContent());
        return XmlHelper.objectToXMLCDATA(textMessage);
	}
	
	//生成图文消息内容
	public String _generateNews(MessageSettingDto msDto, JSONArray jsonArray, Date date) throws InstantiationException, IllegalAccessException {
		 NewsMessage newsMessage = new NewsMessage();
         newsMessage.setToUserName(Constants.TOUSERNAME);  
         newsMessage.setFromUserName(Constants.FROMUSERNAME);  
         newsMessage.setCreateTime(date.getTime());  
         newsMessage.setMsgType(RESP_MESSAGE_TYPE_NEWS);
         List<Article> articleList = new ArrayList<Article>();
         for(int i=0; i<jsonArray.size(); i++) {
        	 StringBuffer pic = new StringBuffer();
        	 JSONObject weixinJson = jsonArray.getJSONObject(i);
        	 if(StringUtils.isNotBlank(weixinJson.getString("picPath"))) {
        		 pic.append(Constants.WECHAT_PIC_URL).append(WEHCAT_IMAGE_PATH).append(i);
     		 }
         	 Article article = new Article();
         	 article.setTitle(weixinJson.getString("title"));
         	 if(Constants.MODE_MULTI.equals(msDto.getRecoveryMode())) {
         		article.setDescription("");
         	 } else {
         		article.setDescription(weixinJson.getString("description"));
         	 }
         	 article.setPicUrl(pic.toString());
         	 if(weixinJson.getInt("linkType")==1){
         		 article.setUrl(this._generateUrl(Constants.PATH,msDto.getShopNo(),Constants.HOMEPATH, null));
         	 }else {
         		 article.setUrl(this._generateUrl(Constants.PATH,msDto.getShopNo(),Constants.CONTENT_PATH, i));  
         	 }
         	 articleList.add(article);
         }
         // 设置图文消息个数  
         newsMessage.setArticleCount(articleList.size());
         // 设置图文消息包含的图文集合
         newsMessage.setArticles(articleList);  
         // 将图文消息对象转换成xml字符串  
         return MessageUtil.newsMessageToXml(newsMessage);
	}
	
	//生成消息的url
	public String _generateUrl(String startpath, String shopNo, String endPath, Integer sort) {
		StringBuffer url = new StringBuffer();
		if(null==sort) {
			url.append(startpath).append(shopNo).append(endPath);
		} else {
			url.append(startpath).append(shopNo).append(endPath).append(sort);
		}
		
		return url.toString();
	}
	
	//初始化消息设置
	private MessageSetting _initMessageSetting(MessageSettingDto msDto,Date date) {
		MessageSetting ms = new MessageSetting();
		//消息设置表
		if(msDto.getEventType() == 3){
			ms.setName(msDto.getName());
			ms.setMatchingType(msDto.getMatchingType());
			ms.setKeyWord(msDto.getKeyWord());
		} else {
			ms.setMatchingType(null);
			ms.setKeyWord(null);
		}
		ms.setEventType(msDto.getEventType());
		ms.setUpdateTime(date);
		ms.setId(msDto.getId());
		if(null == msDto.getId()) {
			ms.setCreateTime(date);
			ms.setShopNo(msDto.getShopNo());
			ms.setRecoveryMode(msDto.getRecoveryMode());
			ms.setMessageBodyId(msDto.getMessageBodyId());
			ms.setStatus(Constants.ISDEFAULT);
		}
		return ms;
	}
	
	//初始化消息主体
	private MessageBody _initMessageBody(MessageSettingDto msDto, String content, Date date) {
		MessageBody messageBody = new MessageBody();
		messageBody.setMessageContent(content);
		messageBody.setUpdateTime(date);
		messageBody.setId(msDto.getMessageBodyId());
		if(null == msDto.getMessageBodyId()) {
			messageBody.setShopNo(msDto.getShopNo());
			messageBody.setType(msDto.getRecoveryMode());
			messageBody.setInformationType(Constants.INFORMATION_TYPE_MESSAGE);
			messageBody.setCreateTime(date);
			messageBody.setStatus(Constants.ISDEFAULT);
		}
		return messageBody;
	}
	
	//初始化文本消息内容
	private MessageContent _initMessageTextContent(MessageSettingDto msDto, Date date) {
		MessageContent messageContent = new MessageContent();
			messageContent.setMessageBodyId(msDto.getMessageBodyId());
			messageContent.setContent(msDto.getMessageContent());
			messageContent.setCreateTime(date);
			messageContent.setUpdateTime(date);
		return messageContent;
	}
	
	//初始化图文消息内容
	private List<MessageContent> _initMessageAticlContent(MessageSettingDto msDto, JSONArray jsonArray, Date date, String[] paths, Integer type) {
		List<MessageContent> mList = new ArrayList<MessageContent>();
		for(int i=0; i<jsonArray.size(); i++){
			MessageContent messageContent = new MessageContent();
			JSONObject weixinJson = jsonArray.getJSONObject(i);
			if(TYPE.equals(type)&&StringUtils.isNotBlank(weixinJson.getString("messageContentId"))) {
				messageContent.setId(weixinJson.getLong("messageContentId"));
			}
			messageContent.setTitle(weixinJson.getString("title"));
			messageContent.setContent(weixinJson.getString("content"));
			// 设置图片
			if (null != paths && paths.length != 0) {
				messageContent.setPicPath(paths[i]);
			}
			messageContent.setSort(i);
			if(weixinJson.getInt("linkType")==1){
				messageContent.setLinkPath(this._generateUrl(Constants.PATH,msDto.getShopNo(),Constants.HOMEPATH, null));
            }else {
            	messageContent.setLinkPath(weixinJson.getString("linkPath"));
			}
			messageContent.setLinkType(weixinJson.getInt("linkType"));
			if(Constants.MODE_MULTI.equals(msDto.getRecoveryMode())) {
				messageContent.setDescription("");
         	 } else {
         		messageContent.setDescription(weixinJson.getString("description"));
         	 }
			messageContent.setMessageBodyId(msDto.getMessageBodyId());
			messageContent.setCreateTime(date);
			messageContent.setUpdateTime(date);
			mList.add(messageContent);
		}
		return mList;
	}

	@Override
	public ToolSetting getToolSettingByShopNo(String shopNo) throws WechatException{
		
		try {
			AssertUtil.notNull(shopNo, "参数店铺编号为空");
			// 根据店铺编号查询消息回复设置
			return wechatService.getToolSettingByShopNo(shopNo);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	@Override
	public void editToolSetting(Integer type, Integer status, ToolSetting toolSetting) throws WechatException {
		try {
			AssertUtil.notNull(type, "参数回复类型为空");
			AssertUtil.notNull(type, "参数回复状态为空");
			AssertUtil.notNull(toolSetting, "参数消息回复设置为空");
			if(Constants.ENVNT_TYPE_ATTENTION.equals(type)){
	        	// 关注时回复
	        	toolSetting.setAttentionSwitch(status);
	        }else if(Constants.ENVNT_TYPE_REPLY.equals(type)){
	        	// 消息时回复
	        	toolSetting.setReplySwitch(status);
	        }else if(Constants.ENVNT_TYPE_KEYWORD_RECOVERY.equals(type)){
	        	// 关键词回复
	        	toolSetting.setKeywordRecoverySwitch(status);
	        }
			// 编辑消息回复设置
			wechatService.editToolSetting(toolSetting);
		} catch (Exception e) {
			throw new WechatException(e);
		}
		
	}

	@Override
	public MessageSetting getMessageSetting(MessageSetting messageSetting) throws WechatException{
		try {
			AssertUtil.notNull(messageSetting.getEventType(), "回复消息的事件类型为空");
			AssertUtil.notNull(messageSetting.getShopNo(), "店铺编号为空");
			// 根据事件类型查询回复消息设置
			return wechatService.getMessageSetting(messageSetting);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	/**
	 * 组装上传图片参数
	 * @Title: _generateFileBizParamDtoList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param path
	 * @param zoomSize
	 * @param userId
	 * @param shopNo
	 * @param tableName
	 * @param id
	 * @return
	 * @date 2015年3月27日 上午11:54:59  
	 * @author ah
	 */
	private List<FileBizParamDto>  _generateFileBizParamDtoList(JSONArray jsonArray, String zoomSize,
			Long userId, String shopNo, String tableName, String id, String file, Integer type) {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		FileBizParamDto dto = new FileBizParamDto();
		StringBuffer imageData = new StringBuffer();
		for(int i=0; i<jsonArray.size(); i++){
			JSONObject weixinJson = jsonArray.getJSONObject(i);
			imageData.append(weixinJson.getString("picPath"));
			imageData.append(",");
		}
		imageData.deleteCharAt(imageData.lastIndexOf(","));
		dto.setImgData(imageData.toString());
		dto.setZoomSizes(zoomSize);
		dto.setUserId(userId);
		dto.setShopNo(shopNo);
		dto.setTableName(tableName);
		dto.setTableId(id);
		dto.setCode(file);
		dto.setFileLabel(type);
		list.add(dto);
        return list;
	}
	
}
