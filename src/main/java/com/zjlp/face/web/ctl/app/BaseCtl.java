package com.zjlp.face.web.ctl.app;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.JSONInfo;
import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.util.AssConstantsUtil;

@Controller("appBaseCtl")
public class BaseCtl {
	
	public static WgjUser getLoginUser() {  
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  
	    if (authentication != null) {  
	        Object principal = authentication.getPrincipal();  
	        
	        if (principal instanceof WgjUser) {  
	            return (WgjUser) principal;  
	        } else {  
	            return null;  
	        }  
	    } else {  
	        return null;  
	    }  
	} 
	
	protected static Long getUserId(){
		return UserSessionManager.getSessionUserId();
	}
    
    
    public String getSessionId(){
    	HttpServletRequest requests = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	return requests.getSession().getId();
    }
    
    /**
     * 
    * @Title: outFailure
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param code
    * @param msg
    * @return
    * @return String    返回类型
    * @throws
    * @author wxn  
    * @date 2015年3月27日 下午4:20:07
     */
    public String outFailure(int code,String msg){
    	JSONInfo<Object> jsoninfo = new JSONInfo<Object>();
    	jsoninfo.setCode(code);
    	jsoninfo.setMsg(msg);
    	return jsoninfo.toFailureJsonString();
    }
    
    public String outFailure(int code){
    	return this.outFailure(code, "");
    }
    /**
     * 
    * @Title: outSucceed
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @param data
    * @param isFilter
    * @param fields
    * @return
    * @return String    返回类型
    * @throws
    * @author wxn  
    * @date 2015年3月27日 下午4:47:00
     */
    public <T> String outSucceed(T data,final boolean isFilter,String...fields){
		JSONInfo<T> info = new JSONInfo<T>();
		info.setCode(AssConstantsUtil.REQUEST_OK_CODE);
		info.setMsg("");
		info.setJsessionid(getSessionId());
		info.setData(data);
		if (isFilter){
			return info.toJsonString(fields);
		}else{
			return info.toJsonString();
		}
    }
    
	/**
	* @Title: outSucceedWithoutSession
	* @Description: (无SessionId返回,因为该id无效)
	* @param data
	* @param isFilter
	* @param fields
	* @return
	* @return String    返回类型
	* @throws
	* @author Baojiang Yang  
	* @date 2015年9月8日 上午9:12:42 
	*/
	public <T> String outSucceedWithoutSession(T data, final boolean isFilter, String... fields) {
		JSONInfo<T> info = new JSONInfo<T>();
		info.setCode(AssConstantsUtil.REQUEST_OK_CODE);
		info.setMsg("");
		info.setData(data);
		if (isFilter) {
			return info.toJsonString(fields);
		} else {
			return info.toJsonString();
		}
	}

    public <T> String outSucceed(T data){
		JSONInfo<T> info = new JSONInfo<T>();
		info.setCode(AssConstantsUtil.REQUEST_OK_CODE);
		info.setMsg("");
		info.setJsessionid(getSessionId());
		info.setData(data);
		return info.toJsonString();
    }
    
    public <T> String outSucceed(T data,String fields){
		JSONInfo<T> info = new JSONInfo<T>();
		info.setCode(AssConstantsUtil.REQUEST_OK_CODE);
		info.setMsg("");
		info.setJsessionid(getSessionId());
		info.setData(data);
		if (StringUtils.isBlank(fields)){
			return info.toJsonString();
		}else{
			String[] field_args = fields.split(",");
			return info.toJsonString(field_args);
		}
    }
    /**
     * 请求成功
    * @Title: outSucceedByNoData
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @return
    * @return String    返回类型
    * @throws
    * @author wxn  
    * @date 2015年3月27日 下午5:35:07
     */
    public String outSucceedByNoData(){
    	return outSucceed("", false, "");
    }
    
    //分页字段
    public static final String PAGESIZE = "pageSize";
    public static final String CURPAGE = "curPage";
    public static final String START = "start";
    /**
     * 分页信息设置
     * @param jsonObj
     * @return
     */
    public <T> Pagination<T> initPagination(JSONObject jsonObj){
    	AssertUtil.notNull(jsonObj, "param [jsonObj] can't be null.");
    	Pagination<T> pagination = new Pagination<T>();
    	// 分页信息
		if (jsonObj.containsKey(PAGESIZE)){
			pagination.setPageSize(jsonObj.getInt(PAGESIZE));
		}
		if (jsonObj.containsKey(CURPAGE)){
			pagination.setCurPage(jsonObj.getInt(CURPAGE));
		}
		if (jsonObj.containsKey(START)){
			pagination.setStart(jsonObj.getInt(START));
		}
    	return pagination;
    }
}
