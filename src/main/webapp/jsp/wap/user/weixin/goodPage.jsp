<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../common/base.jsp"%>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<title>${titleShopName}-详情页</title>
<%@ include file="../../common/top.jsp"%>
<script type="text/javascript">
wx.config({
    debug: false,
    appId: 'wxbeebf508a15aa382',
    timestamp: 1422009542,
    nonceStr: 'rfOEfBdBznhLFkZW',
    signature:"${signature}",
    jsApiList: [
      'addContact',
      'onMenuShareAppMessage'
    ]
});

function viewProfile(){    
    if (typeof WeixinJSBridge != "undefined" && WeixinJSBridge.invoke){    
        WeixinJSBridge.invoke('addContact',{    
            'username':'wxbeebf508a15aa382',    /* 你的公众号原始ID */
            'scene':'57'   
        },function(resp){
        	alert(resp);
            alert(resp.err_msg);    /* 在这里，我们把err_msg输出 */
        });    
    }    
}
function weixinAddContact(name){
	alert(name);
	WeixinJSBridge.invoke("addContact", {webtype: "1",username: name}, function(e) {
		WeixinJSBridge.log(e.err_msg);
		//e.err_msg:add_contact:added 已经添加
		//e.err_msg:add_contact:cancel 取消添加
		//e.err_msg:add_contact:ok 添加成功
		if(e.err_msg == 'add_contact:added' || e.err_msg == 'add_contact:ok'){
		    //关注成功，或者已经关注过
		}
	})
}
function addWxContact(wxid) {
//     if (typeof WeixinJSBridge == 'undefined') return false;
        WeixinJSBridge.invoke('addContact', {
            webtype: '1',
            username: 'wxbeebf508a15aa382'
        }, function(d) {
            // 返回d.err_msg取值，d还有一个属性是err_desc
            // add_contact:cancel 用户取消
            // add_contact:fail　关注失败
            // add_contact:ok 关注成功
            // add_contact:added 已经关注
            WeixinJSBridge.log(d.err_msg);
            if(d.err_msg == 'add_contact:added'){
    		    //关注成功，或者已经关注过
    		    alert("已关注");
    		} else if(d.err_msg == 'add_contact:ok') {
    			alert("关注成功");
    		} else if(d.err_msg == 'add_contact:cancel') {
    			alert("取消关注");
    		} else if(d.err_msg == 'add_contact:fail') {
    			alert("关注失败");
    		}
        });
};
// function add() {
// 	addWxContact;
// }
</script>
</head>
<body>
<form action="${ctx }/wap/HZJZ1503261737oJAjR1/any/list${ext}">
	<input name="shopTypeId" value="71"><br>
	<input name="shopTypeName" value="商品分类4"><br>
	<button type="submit">提交</button>
</form>
<br><br><br><br>
<a href="javascript:viewProfile();" id="post-user" class="activity-meta">    
<span class="text-ellipsis">微无锡</span><i class="icon_link_arrow"></i>    
</a><br><br><br><br>
<a href="javascript:addWxContact('wxbeebf508a15aa382');">关注</a>
</body>
</html>