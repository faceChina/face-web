<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
//签到信息
function toRegistration(el, cardId, str,integral){
	$.post("${ctx}/wap/${shop.no}/buy/member/regist/regist${ext}", {"cardId" : cardId}, function(jsonData){
		var data = JSON.parse(jsonData);
		if (data.success) {
			var record = JSON.parse(data.info);
			artTip("签到成功! +"+record.currentAddIntegral+"个积分,明天可领"+record.nexAddIntegral+"个积分，接着领哦！",true);
			$(el).attr('disabled',true).html(str);
			if(integral!=null && integral!='' && integral!='undefined'){
				document.getElementById(integral).innerHTML = parseInt(document.getElementById(integral).innerHTML) + record.currentAddIntegral;
			}
		} else {
			artTip(data.info, true);
		}
	});
};

/* 签到  */
function toSignin(el,cardId,integral){
	$.post("${ctx}/wap/${shop.no}/buy/member/regist/regist${ext}", {"cardId" : cardId}, function(jsonData){
		var data = JSON.parse(jsonData);
		if (data.success) {
			var record = JSON.parse(data.info);
			artTip("签到成功! +"+record.currentAddIntegral+"个积分,明天可领"+record.nexAddIntegral+"个积分，接着领哦！");
			$(el).html('<i class="iconfont icon-emoji fnt-14"></i> 已签到');
			if(integral!=null && integral!='' && integral!='undefined'){
				document.getElementById(integral).innerHTML = '<i class="iconfont icon-dian"></i>' + (parseInt(${memberCardDto.availableIntegral }) + record.currentAddIntegral) + '积分';
			}
		} else {
			artTip(data.info);
		}
	});
};
</script>