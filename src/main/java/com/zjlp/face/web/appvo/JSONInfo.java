package com.zjlp.face.web.appvo;

import flexjson.JSONSerializer;

/**
 * 应答数据
* @ClassName: JSONInfo
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年3月27日 下午1:25:36
*
* @param <T>
 */
public class JSONInfo<T> {
	/**ssisonId**/
	private String jsessionid;
	/**应答编码**/
	private int code;
	/**应答消息**/
	private String msg;	
	/**应答数据**/
	private T  data ;
	/**应答数据是否加密，0否 1加密**/
	private final static int isEncryption = 0;
	
	public String getJsessionid() {
		return jsessionid;
	}
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	/**
	 * 获取JSONInfo对象转换成json格式字符串
	* @Title: toJsonString
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年3月27日 下午3:01:16
	 */
	public String toJsonString(){
		String dataStr = new JSONSerializer().exclude("*.class").deepSerialize(this.data);
		return getJSONInfoJson(dataStr);
	}
	/**
	 * 保留参数字段属性
	* @Title: toJsonString
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param fields
	* @return
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年3月27日 下午4:19:30
	 */
	public String toJsonString(String... fields){
		String dataStr=new JSONSerializer().include(fields).exclude("*").deepSerialize(this.data);
		return getJSONInfoJson(dataStr);
	}
	
	public String toFailureJsonString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"isEncryption\":").append(isEncryption).append(",\"data\":")
		.append("{\"code\":").append(code).append(",\"msg\":\"").append(msg)
		.append("\"}}");
		return sb.toString();
	}
	
	private String getJSONInfoJson(String jsonStr){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"isEncryption\":").append(isEncryption).append(",\"data\":")
		.append("{\"code\":").append(code).append(",\"msg\":\"").append(msg)
		.append("\",\"jsessionid\":\"").append(jsessionid)
		.append("\",\"data\":")
		.append(jsonStr).append("}}");
		return sb.toString();
	}
}
