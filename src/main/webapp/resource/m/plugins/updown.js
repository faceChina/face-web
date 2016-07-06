$.extend({puburl:{}});//后台地址
$.extend($.puburl,{
	setting:{
		url:""
	}
});

function moveUp(obj,change){
	//alert($(obj).parent("td").parent("tr").index())
	var onthis = $(obj).parent("td").parent("tr");
	var getup = onthis.prev();
	var subId = onthis.find("input[move-row='moveId']").val();
	var tarId = getup.find("input[move-row='moveId']").val();
	if(!subId){
		return;
	}
	if(!tarId){
		return;
	}
	//请求后台，根据id互换sort
	$.post($.puburl.setting.url, { subId: subId, tarId: tarId },
	  function(json){
		if(json == 1){
			getup.before(onthis);
			//互换两行sort
			if(change){
				var subSort = onthis.find("input[move-row='moveSort']").val();
				var tarSort = getup.find("input[move-row='moveSort']").val();
				onthis.find("input[move-row='moveSort']").val(tarSort);
				getup.find("input[move-row='moveSort']").val(subSort);
			}
		}else{
			alert('失败');
		}
    });
}

function moveDown(obj,change){
	var onthis = $(obj).parent("td").parent('tr');
	var getdown = onthis.next();
	var subId = onthis.find("input[move-row='moveId']").val();
	var tarId = getdown.find("input[move-row='moveId']").val();
	if(!subId){
		return;
	}
	if(!tarId){
		return;
	}
	$.post($.puburl.setting.url, { subId: subId, tarId: tarId },
	  function(json){
		if(json == 1){
			getdown.after(onthis);
			//互换两行sort
			if(change){
				var subSort = onthis.find("input[move-row='moveSort']").val();
				var tarSort = getdown.find("input[move-row='moveSort']").val();
				onthis.find("input[move-row='moveSort']").val(tarSort);
				getdown.find("input[move-row='moveSort']").val(subSort);
			}
		}else{
			alert('失败');
		}
	});
}
