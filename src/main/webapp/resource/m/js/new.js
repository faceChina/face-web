
$('.new_nav_lm').on('mousemove', function(event) {
	$(this).addClass('new_nav_jg').siblings().removeClass('new_nav_jg');
});
$('.new_nav_lm').on('mouseleave', function(event) {
	$('.new_nav_lm').removeClass('new_nav_jg');
});
$('.subnav_u').children('li').click(function(event) {
	$(this).toggleClass('red').siblings().removeClass('red');
	$('.new_mainNav').find('.red').text($(this).text());
	$('.subnav_h').find('.red').text($(this).text());
});
$('.new_bc a').click(function(event) {
	$('.new_nav_lm').removeClass('new_nav_jg');
});

$('.inputfocus').focus(function(event) {
	$(this).next('.new_sx_fl').show().children().on('click',function(event) {
		$(this).parent().hide().prev().val($(this).text());
				
	});

	
});
$('.inputfocus').blur(function(event) {
	$(this).next('.new_sx_fl').children().on('click',function(event) {
		$(this).parent().hide().prev().val($(this).text());
		$(this).next('.new_sx_fl').hide();		
	});
	
	
	
});

	//商品选择联动效果
	$('.fuxuan').click(function(event) {
		var ysval = $(this).next().val();
	if($(this).attr("checked")){
	
		$(this).next().removeAttr('disabled');
		$(this).next().blur(function(event) {
			$(this).css('border','');
			if($(this).val() == ''){
				$(this).val(ysval);
				$('#'+$(this).prev().attr('name')).find('input:first').val($(this).val());
			}else{
				
				$('#'+$(this).prev().attr('name')).find('input:first').val($(this).val());
			}
		});

		$('.new_xzsp').append('<tr id="'+$(this).attr("name")+'" class="new_form_input"><td><input class="newborder" name="color" type="text" value="'+$(this).next().val()+'" disabled="disabled" Readonly\/><\/td><td><img class="sptp_img" width="80" src="" /><p class="sptpp">上传图片<input class="sptp" type="file" name="picturePath" multiple="multiple" /></p><div class="sptpp_none"></div><\/td><td><table class="shuliang"><\/table><\/td><td  class="none"><table class="xinghao"><\/table><\/td><td  class="none"><table class="bianhao"><\/table><\/td><\/tr>');
		if($('.shuxing:checked').length>0){
				$('.shuliang:hidden').append(
					function(html){
						var html = '';
						for(var i=0;i<$('.shuxing:checked').length;i++){

							html = html+'<tr><td class="'+$('.shuxing:checked').eq(i).attr('name')+'"><input onkeyup="checkvalue(this);" onblur="" name="stock" type="text" value="0" \/><input  type="hidden" value="" name="goodItemId" \/><\/td><\/tr>';
							
						}
						
						return html;
					});

				$('.none:hidden .xinghao').append(
					function(html){
						var html = '';
						for(var i=0;i<$('.shuxing:checked').length;i++){

							html = html+'<tr><td  class="'+$('.shuxing:checked').eq(i).attr('name')+'"><input name="sizeValueCode"  class="newborder" value="'+$('.shuxing:checked').eq(i).val()+'" type="text" disabled="disabled" Readonly\/><\/td><\/tr>';
							
						}
						
						return html;
					});

				$('.none:hidden .bianhao').append(
					function(html){
						var html = '';
						for(var i=0;i<$('.shuxing:checked').length;i++){

							html = html+'<tr><td class="'+$('.shuxing:checked').eq(i).attr('name')+'"><input name="barCode" type="text" \/><\/td><\/tr>';
							
						}
						
						return html;
					});
				
				$('.none').show();
				$('.shuliang').show();

		}
	}else{
		$(this).removeAttr("checked").next().attr("disabled","true");

		if($(this).next().val() == ''){
			$(this).next().val() == $(this).next().val();
		}
		$('#'+$(this).attr("name")).remove();
	}
	fuzhiname();
	$('.sptp').each(function(){
		imgUpload($(this));
	});
});

$('.shuxing').click(function(event) {
	if($(this).attr("checked")){
		$('.none').show();
		$('.shuliang').show();
		var num = getshcm($(this));
		var trnone = $('.shuliang:visible').find('tr').length;
		var thiz = $(this);
		if (trnone == 0) {
			$('.shuliang:visible').append('<tr><td class="'+thiz.attr('name')+'"><input onkeyup="checkvalue(this);" name="stock" type="text" value="0" \/><input  type="hidden" value="" name="goodItemId" \/><\/td><\/tr>');
			$('.none:visible .xinghao').append('<tr><td class="'+thiz.attr('name')+'"><input name="sizeValueCode"  class="newborder" value="'+thiz.val()+'" type="text" disabled="disabled" Readonly\/><\/td><\/tr>');
			$('.none:visible .bianhao').append('<tr><td class="'+thiz.attr('name')+'"><input name="barCode" type="text" \/><\/td><\/tr>');

		}else{

			if (num>0) {
				$('.new_xzsp > tbody > tr').each(function(index) {
					$(this).find('.shuliang:visible  tr').eq(num-1).after('<tr><td class="'+thiz.attr('name')+'"><input onkeyup="checkvalue(this);" name="stock" type="text" value="0"\/><input  type="hidden" value="" name="goodItemId"  \/><\/td><\/tr>');
					$(this).find('.none:visible .xinghao tr').eq(num-1).after('<tr><td class="'+thiz.attr('name')+'"><input name="sizeValueCode"  class="newborder" value="'+thiz.val()+'" type="text" disabled="disabled" Readonly\/><\/td><\/tr>');
					$(this).find('.none:visible .bianhao tr').eq(num-1).after('<tr><td class="'+thiz.attr('name')+'"><input name="barCode" type="text" \/><\/td><\/tr>');
				});
			
			}else if(num==0){
				$('.new_xzsp > tbody > tr').each(function(index) {
					$(this).find('.shuliang:visible tr').eq(num).before('<tr><td class="'+thiz.attr('name')+'"><input onkeyup="checkvalue(this);" name="stock" type="text" value="0"\/><input  type="hidden" value="" name="goodItemId" \/><\/td><\/tr>');
					$(this).find('.none:visible .xinghao  tr').eq(num).before('<tr><td class="'+thiz.attr('name')+'"><input name="sizeValueCode"  class="newborder" value="'+thiz.val()+'" type="text" disabled="disabled" Readonly\/><\/td><\/tr>');
					$(this).find('.none:visible .bianhao tr').eq(num).before('<tr><td class="'+thiz.attr('name')+'"><input name="barCode" type="text" \/><\/td><\/tr>');
				});
			};
		};

		

		

	}else{
		
		$('.'+$(this).attr("name")).remove();
		if($('.shuxing:checked').length == 0){
			$('.none').hide();
		}
	}
	fuzhiname();
	$('.sptp').each(function(){
		imgUpload($(this));
	});
});//end

function imgUpload(uploadBtn){
    var url = "/any/files/goodFile/upload.htm";
	 	new AjaxUpload(uploadBtn, {
	        action: url,
	        data: {
	            'productNumber':productNumber
	        },
	        autoSubmit: true,
	        responseType: 'json',
	        onSubmit: function(file,ext){
	        	var imageSuffix = new RegExp('jpg|png|jpeg|JPG|PNG|JPEG');
	        	if (!(ext && imageSuffix.test(ext.toUpperCase()))){
                	art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
                    return false;               
                }
	        },
	        onComplete: function(file, response){
	        	if(response.code == -1){
	        		art.dialog.alert(response.desc);
	        		return ;
	        	}
	        	if(response.fileName&&response.shopNo&&response.fileId){
	        		uploadBtn.parent().prev().show().attr("src",'/any/files/showImgs.htm?filePath='+response.filePath+'&zoomImgSize=640_640&inputName=goodFile');
	        		var filedId = uploadBtn.closest('tr').attr('id');
	           		var fileAttr=response.filePath+","+response.shopNo+","+response.fileId+","+filedId+"@";
	        		var colorFilesAttr = $('#colorFilesAttr').val();
	        		colorFilesAttr= colorFilesAttr+fileAttr;
	        		$('#colorFilesAttr').val(colorFilesAttr);
	        	}
	        }
	}); 
}

function getshcm(obj){
	var num;
	$('.new_shcm').find('input:checked').each(function(index){
		if ($(this).val() == obj.val()) {
			num = index;
		};
	});
	return num;
}

function checkvalue(obj){//获取this对象判断value值只能为数字 
    	var str = obj.value;  	  
    	obj.value =str.replace(/[^0-9]+/g,"");
  }
function fuzhiname(){
	$('.shuliang').each(function(index, el) {
		$(this).find('input[type="text"]').each(function(index) {
			var inputclass = $(this).closest('table').parent().parent().attr('id')+'_'+$(this).parent().attr('class');
			var shunxu = index;
			$(this).addClass(inputclass);
			$(this).closest('table').parent().siblings().each(function(index, el) {
				if ($(this).find('input').length == 1) {
					$(this).find('input').addClass(inputclass);
				}else{
					$(this).find('input').eq(shunxu).addClass(inputclass);
				}
			});
		});
		$(this).find('input[type="hidden"]').each(function(index) {
			var inputclass = $(this).closest('table').parent().parent().attr('id')+'_'+$(this).parent().attr('class');
			$(this).addClass(inputclass);
		});
	});
}

function andstock(obj){
	var num=0;
	obj.each(function(){
		num += ($(this).val() == '')?0:parseInt($(this).val());
	});
		
	return num;
}
