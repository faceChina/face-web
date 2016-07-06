function addNewShop(dom){

	var colorIndex = 0;//事件颜色索引
	var sizeIndex = 0; //事件尺码索引
	var totalColor = 0;//选中颜色数量
	var totalSize = 0;//选中尺码数量
	var colorDom = null;//选中颜色DOM集合
	var sizeDom = null;//选中尺码DOM集合
	var thiz = null; //触发事件this
	var oldsizeDom = null; //删除、添加后尺码集合
	var dataArr = ['barCode','colorName','colorId','picturePath','priceYuan','sizeName','sizeId','stock','skuId']

	//获取JSON
	var getJson = function(){
		var shopArr = [];
		var color = [];
		$('#template>tbody>tr').each(function(a){
			var shopObj = {};
			shopObj[dataArr[2]] = this.className;
			shopObj[dataArr[3]] = $('#template2>tbody>tr.'+this.className).find('input').val();
			$(this).find('td').each(function(b){
				//合并表格的第一个tr
				var sizeSplit = $(this).attr('class').split('-');
				var keyClass = sizeSplit[0];
				if($(this).children().length>0){

					$(this).children().each(function(){
						if(this.tagName != 'INPUT') return;
						shopObj[this.name] = this.value;
					});

				}else{

					if(!(a%totalSize) && b == 0){
						color[0] = keyClass;
						color[1] = $(this).text();
					}
					if(sizeSplit.length>1){
						shopObj[dataArr[6]] = sizeSplit[1];	
					}
					shopObj[keyClass] = $(this).text();	
				}
				
			});
			if(!!(a%totalSize)){
				shopObj[color[0]] = color[1];
			}
			shopArr.push(shopObj);
		});
		return JSON.stringify(shopArr);
	};
	//设置JSON
	var setJson = function(data,type){
		var inputDom;

		if(type == 'color'){
			for(key in data){
				inputDom = $('#j-proColor').find('input[type="checkbox"][value="'+data[key]['propValueId']+'"]');
				inputDom.attr('checked','checked');
				inputDom.prev().val(data[key]['id']);
				inputDom.next().val(data[key]['propValueAlias']);
				inputDisableInit(inputDom);	
			}
			
		}else if(type == 'size'){
			for(key in data){
				inputDom = $('#j-proSize').find('input[type="checkbox"][value="'+data[key]['propValueId']+'"]');
				inputDom.attr('checked','checked');
				inputDom.prev().val(data[key]['id']);
				inputDom.next().val(data[key]['propValueAlias']);
				inputDisable(inputDom);	
			}
		}else{
			$('#template>tbody>tr').each(function(a){
				var keyClass = this.className;
				if(!(a%totalSize)){
					if(!!data[a][dataArr[3]]){
//						$('#template2>tbody>tr.'+keyClass).find('img').attr('src',ROOT_PICURL+data[a][dataArr[3]]).end().find('input').eq(0).val(data[a][dataArr[3]]);
					}
				}
				$(this).find('td').each(function(b){
					if($(this).children().length>0){

						$(this).children().each(function(){
							if(this.tagName != 'INPUT') return;
							this.value = data[a][this.name]
						});
					}
				});
			})
			return;
		}
	};
	//图片上传
	var uploadImg = function(){
		$('.btn-upload img').each(function(){
			imgUpload($(this));
		});
	};
	//添加DOM
	var addDom = function(proColor,data){
		
		var str;
		var str2;
		var tempTr1 = $('#template tr');
		var tempTr2 = $('#template2 tr');

		str2 = [ '<tr class="',$(thiz).val(),'">',
			 		'<td class="',dataArr[1],'">',data,'</td>',
			 		'<td><div class="btn-upload"><input type="hidden" name="itemPath"><img name="itemFile"  src="../../../../resource/m/img/add100X100.jpg" style="width:100px;height:100px;" alt="" class="">',
			 		'<input type="hidden" name="propertyImgs['+$(thiz).val()+'].picturePath" value=""><input type="hidden" name="propertyImgs['+$(thiz).val()+'].propValueId" value="'+$(thiz).val()+'"></div></td>',
			 		'</tr>'
            	].join("");	
		if(proColor == 'j-proColor'){
			if(colorIndex == 1){
				tempTr2.length>1? tempTr2.eq(colorIndex).before(str2): $('#template2').append(str2);
			}else{
				tempTr2.eq(colorIndex-1).after(str2);

			}
			uploadImg();
			
			if(totalColor == 0 || totalSize == 0 ) return;

			sizeDom.each(function(index){
			
				str += [
					'<tr class="',$(thiz).val(),'">',
						'<td class="'+dataArr[4]+'"><input name="'+dataArr[4]+'" type="text" value="0" validate="{pricevalid:true,moneyNotOne:true}" maxlength="12"><input name="'+dataArr[8]+'" type="hidden" value="" ></td>',
						'<td class="'+dataArr[7]+'"><input name="'+dataArr[7]+'" type="text" value="0" validate="{stockvalid:true,integerzero:true}"  maxlength="10" ></td>',
						'<td class="',dataArr[5],'-',$(this).find("input[type=checkbox]").val(),'">',$(this).find("input[type=text]").val(),'</td>',
						'<td class="',dataArr[0],'"><input name="',dataArr[0],'" type="text" value="0" validate="{codeValid:true}"  maxlength="32" ></td>',
					'</tr>'
            	].join("");
			});
			if(colorIndex == 1){
				tempTr1.length>1? tempTr1.eq(totalSize*(colorIndex-1)+1).before(str): $('#template').append(str);
			}else{
				tempTr1.eq(totalSize*(colorIndex-1)).after(str);

			}
			var str1 = '<td rowspan="'+totalSize+'" class="'+dataArr[1]+'">'+data+'</td>';

			$('#template .'+$(thiz).val()).eq(0).find('td').eq(0).before(str1);

		}else if(proColor == 'j-proSize'){
			if(totalColor == 0 || totalSize == 0 ) return;
			colorDom.each(function(index){
				var colorName = $(this).find("input[type=checkbox]").val();
				str = [
					'<tr class="',colorName,'">',
						'<td class="'+dataArr[4]+'"><input name="'+dataArr[4]+'" type="text" value="0" validate="{pricevalid:true,moneyNotOne:true}" maxlength="12"><input name="'+dataArr[8]+'" type="hidden" value="" ></td>',
						'<td class="'+dataArr[7]+'"><input name="'+dataArr[7]+'" type="text" value="0" validate="{stockvalid:true,integerzero:true}"  maxlength="10" ></td>',
						'<td class="'+dataArr[5]+'-',$(thiz).val(),'">',data,'</td>',
						'<td class="',dataArr[0],'"><input name="',dataArr[0],'" type="text" value="0" validate="{codeValid:true}"  maxlength="32" ></td>',
					'</tr>'
            	].join("");	

            	

            	if(sizeIndex == 1){
            		var firstTd = $('#template .'+colorName).eq(0).find('td').eq(0);
            		if(firstTd.length==0){
            			firstTd = '<td rowspan="'+totalSize+'" class="'+dataArr[1]+'">'+$(this).find("input[type=text]").val()+'</td>';
            		}

            		$('#template .'+colorName).length>0?$('#template .'+colorName).eq(sizeIndex-1).before(str):$('#template').append(str);

            		$('#template .'+colorName).eq(0).find('td').eq(0).before(firstTd);
            		
            	}else{
            		$('#template .'+colorName).eq(sizeIndex-2).after(str);
            	}
            	$('#template .'+colorName).eq(0).find('td').eq(0).attr('rowspan',totalSize)
		});
	}
	//记录删除之前尺寸的DOM 
	oldsizeDom = $('#j-proSize').find('li').has('input:checked');
	};
	
	//添加DOM1
	var addDom1= function(proColor,data){
		
		var str;
		var str2;
		var tempTr1 = $('#template tr');
		var tempTr2 = $('#template2 tr');

		if(proColor == 'j-proColor'){
			uploadImg();
			
			if(totalColor == 0 || totalSize == 0 ) return;

			sizeDom.each(function(index){
			
				str += [
					'<tr class="',$(thiz).val(),'">',
						'<td class="'+dataArr[4]+'"><input name="'+dataArr[4]+'" type="text" value="0" validate="{pricevalid:true,moneyNotOne:true}" maxlength="12"><input name="'+dataArr[8]+'" type="hidden" value="" ></td>',
						'<td class="'+dataArr[7]+'"><input name="'+dataArr[7]+'" type="text" value="0" validate="{stockvalid:true,integerzero:true}" maxlength="10" ></td>',
						'<td class="',dataArr[5],'-',$(this).find("input[type=checkbox]").val(),'">',$(this).find("input[type=text]").val(),'</td>',
						'<td class="',dataArr[0],'"><input name="',dataArr[0],'" type="text" value="0" validate="{codeValid:true}"  maxlength="32" ></td>',
					'</tr>'
            	].join("");
			});
			if(colorIndex == 1){
				tempTr1.length>1? tempTr1.eq(totalSize*(colorIndex-1)+1).before(str): $('#template').append(str);
			}else{
				tempTr1.eq(totalSize*(colorIndex-1)).after(str);

			}
			var str1 = '<td rowspan="'+totalSize+'" class="'+dataArr[1]+'">'+data+'</td>';

			$('#template .'+$(thiz).val()).eq(0).find('td').eq(0).before(str1);

		}else if(proColor == 'j-proSize'){
			if(totalColor == 0 || totalSize == 0 ) return;
			colorDom.each(function(index){
				var colorName = $(this).find("input[type=checkbox]").val();
				str = [
					'<tr class="',colorName,'">',
						'<td class="'+dataArr[4]+'"><input name="'+dataArr[4]+'" type="text" value="0" validate="{pricevalid:true,moneyNotOne:true}"  maxlength="12"><input name="'+dataArr[8]+'" type="hidden" value="" ></td>',
						'<td class="'+dataArr[7]+'"><input name="'+dataArr[7]+'" type="text" value="0" validate="{stockvalid:true,integerzero:true}" maxlength="10"></td>',
						'<td class="'+dataArr[5]+'-',$(thiz).val(),'">',data,'</td>',
						'<td class="',dataArr[0],'"><input name="',dataArr[0],'" type="text" value="0" validate="{codeValid:true}"  maxlength="32" ></td>',
					'</tr>'
            	].join("");	

            	

            	if(sizeIndex == 1){
            		var firstTd = $('#template .'+colorName).eq(0).find('td').eq(0);
            		if(firstTd.length==0){
            			firstTd = '<td rowspan="'+totalSize+'" class="'+dataArr[1]+'">'+$(this).find("input[type=text]").val()+'</td>';
            		}

            		$('#template .'+colorName).length>0?$('#template .'+colorName).eq(sizeIndex-1).before(str):$('#template').append(str);

            		$('#template .'+colorName).eq(0).find('td').eq(0).before(firstTd);
            		
            	}else{
            		$('#template .'+colorName).eq(sizeIndex-2).after(str);
            	}
            	$('#template .'+colorName).eq(0).find('td').eq(0).attr('rowspan',totalSize)
		});
	}
	//记录删除之前尺寸的DOM 
	oldsizeDom = $('#j-proSize').find('li').has('input:checked');
	};
	var deleteDom = function(proColor,data){
		var colorCLass = $(thiz).val();
		//size-L
		if(proColor == 'j-proColor'){
			$('.'+colorCLass).remove();
			uploadImg();

		}else if(proColor == 'j-proSize'){
			var li = $(thiz).parent();

				//判断删除LI在原先位置的索引
				sizeIndex = oldsizeDom.index(li)+1;
				//totalSize = oldsizeDom.length;

				colorDom.each(function(){
					var colorName = $(this).find("input[type=checkbox]").val();
					
					if(sizeIndex == 1){
						var firstTd = $('#template .'+colorName).eq(0).find('td').eq(0);
						$('#template .'+colorName).eq(1).find('td').eq(0).before(firstTd);
					}
				
					$('#template .'+colorName).has('.'+dataArr[5]+'-'+colorCLass).remove();
					$('#template .'+colorName).eq(0).find('td').eq(0).attr('rowspan',totalSize)

				});
			//记录删除之前尺寸的DOM 
			oldsizeDom = $('#j-proSize').find('li').has('input:checked');
		}
		
	};
	// 禁止/开放 颜色尺寸修改
	var inputDisable = function(self){
		thiz = self;
		var checked = $(self).is(':checked');
		var li = $(self).parent();
		colorDom = $('#j-proColor').find('li').has('input:checked');
		sizeDom = $('#j-proSize').find('li').has('input:checked');
		var proColor = $(self).closest('ul')[0].id;
		totalColor = colorDom.length;
		totalSize = sizeDom.length;
		if(proColor == 'j-proColor'){
			colorIndex = colorDom.index(li)+1;
		}else{
			sizeIndex = sizeDom.index(li)+1;
		}
		//添加表格
		if(checked){
			$(self).prev().removeAttr('disabled');
			$(self).next().removeAttr('disabled');
			var inpVal = $(self).next().val();
			addDom(proColor,inpVal);
			
		}else{
			$(self).prev().attr('disabled','disabled');
			$(self).next().attr('disabled','disabled');
			deleteDom(proColor);
		}
	};
	// 禁止/开放 颜色尺寸修改
	var inputDisableInit = function(self){
		thiz = self;
		var checked = $(self).is(':checked');
		var li = $(self).parent();
		colorDom = $('#j-proColor').find('li').has('input:checked');
		sizeDom = $('#j-proSize').find('li').has('input:checked');
		var proColor = $(self).closest('ul')[0].id;
		totalColor = colorDom.length;
		totalSize = sizeDom.length;
		if(proColor == 'j-proColor'){
			colorIndex = colorDom.index(li)+1;
		}else{
			sizeIndex = sizeDom.index(li)+1;
		}
		//添加表格
		if(checked){
			$(self).prev().removeAttr('disabled');
			$(self).next().removeAttr('disabled');
			var inpVal = $(self).next().val();
			addDom1(proColor,inpVal);
			
		}else{
			$(self).next().attr('disabled','disabled');
			deleteDom(proColor);
		}
	};
	//颜色修改
	var colorAmend = function(self){
		var amendText = $(self).val();
		var classTr = $(self).prev().val();
		if($('.'+classTr).length>0){
			$('.'+classTr).find('.'+dataArr[1]).text(amendText);
		}	
	};
	//尺码修改
	var sizeAmend = function(self){
		var amendText = $(self).val();
		var classTr = $(self).prev().val();
		if($('.'+dataArr[5]+'-'+classTr).length>0){
			$('.'+dataArr[5]+'-'+classTr).text(amendText);
		}
	};
	//商城价
	var averagePrice = function(self){
		var priceDom = $('#template').find('input[name="'+dataArr[4]+'"]');
		var price = 0;
		priceDom.each(function(index){
			if(index == 0){
				price = this.value;
			}
			if(parseFloat(price) > parseFloat(this.value)){
				price = this.value;
			}
		});
		price = isNaN(parseFloat(price))?0:parseFloat(price).toFixed(2);
		$('#j-price').val(price);
		
	};
	//商品总数
	var shopToal = function(self){
		var toalDom = $('#template').find('input[name="'+dataArr[7]+'"]');
		var totalNum = 0;
		toalDom.each(function(){
			var value = this.value;
			if(value == '') value = 0;
			totalNum += parseFloat(value); 
		});
		var toal = totalNum;
		$('#j-total').val(toal);
		
	};




	//颜色 尺码 复选框绑定事件
	$('#j-proColor,#j-proSize').find('input[type="checkbox"]').on('click',function(){
		inputDisable(this);
	})
	//修改颜色绑定事件
	$('#j-proColor').find('input[type="text"]').on('input',function(event){
		colorAmend(this);
	});
	//修改尺码绑定事件
	$('#j-proSize').find('input[type="text"]').on('input',function(event){
		sizeAmend(this);
	});
	//商城价
	$('#template').on('input','input[name="'+dataArr[4]+'"]',function(event){
		averagePrice(this);
	});

	//商品总数
	$('#template').on('input','input[name="'+dataArr[7]+'"]',function(event){
		shopToal(this);
	});

	//对象
	var init = function(a){
		this.getJson = getJson;
		this.setJson = setJson;
	};

	return new init();
}

//商品明细图片
function itemFile(file, response,uploadBtn){
	if(response.source&&response.path){
		uploadBtn.attr('src',ROOT_PICURL+response.source);
		uploadBtn.parent().find('input[name="itemPath"]').val(response.path);
		uploadBtn.next().val(response.path);
	}
}
//商品主图图片
function goodFile(file, response,uploadBtn){
	if(response.source&&response.path){
		uploadPic.addgoodsImg(uploadBtn,response);
	}
}

function selectCallBack(file, response,uploadBtn){
	if(uploadBtn.attr('name')=='itemFile'){
		itemFile(file, response,uploadBtn);
	}else if(uploadBtn.attr('name')=='goodFile'){
		goodFile(file, response,uploadBtn);
		$('#picpathvalid').val(true);
	}else{
		art.dialog.alert(response.desc);
	}
}

function imgUpload(uploadBtn){
	var url = "/any/files/upload.htm";
	var read=new FileReader();
	
	var ajax=new AjaxUpload(uploadBtn, {
        action: url,
        autoSubmit: false,
        responseType: 'json',
        onChange:function(file,ext){
        	var imageSuffix = new RegExp('jpg|png|jpeg|JPG|PNG|JPEG');
        	if (!(ext && imageSuffix.test(ext.toUpperCase()))){
           	art.dialog.alert("只支持上传jpg|jpeg|png格式图片");
               return false;               
           }
        	read.readAsDataURL(this._input.files[0]);
        },
        onComplete: function(file, response){
        	if(response.flag != 'SUCCESS'){
        		art.dialog.alert(response.info);
        		return ;
        	}
        	selectCallBack(file,response,uploadBtn);
        }
	}); 
	read.onload=function(){
		var img=new Image();
		img.src=read.result;
		if(img.width<640){
			art.dialog.alert("图片宽度必须大于640px ");
			ajax._input.value="";
			return false;
		}
		ajax.submit();
	};
}



//通用添加商品属性
function generalNewShop(dom){
	var tableDom = $(dom);
	var addBtn = $('.j-addshop');
	var delBtn = '.j-delshop'
	var lenTr = tableDom.find('tr').length;
	var getJson = function(){
		var getArr = [];
		tableDom.find('tr').each(function(){
			var getObj = {};
			var key = $(this).find('input').eq(0).val();
			var val = $(this).find('input').eq(1).val();
			getObj[key] = val;
			getArr.push(getObj);
		});
		return JSON.stringify(getArr);
	};
	var maxlen = 6;
	var addDom = function(){
		var key = new Date().getTime();
		lenTr = tableDom.find('tr').length;
		if(lenTr == maxlen) return;
		var str = 
				['<tr>',
					'<td>属性：</td>',
					'<td><input type="text" name="goodPropertyMap['+key+'].propName" value="" class="form-control" maxlength="20" validate="{required:true}"/></td>',
					'<td>属性值：</td>',
					'<td><input type="text" name="goodPropertyMap['+key+'].propValueName" value="" class="form-control" maxlength="20" validate="{required:true}"/></td>',
					'<td><button type="button" class="btn btn-default j-delshop">删除</button></td>',
				'</tr>'
				].join("");
		tableDom.append(str);
	};
	var delDom = function(self){
		lenTr = tableDom.find('tr').length;
		if(lenTr == 1) return;
		$(self).closest('tr').remove();
	};
	var init = function(){
		this.getJson = getJson;
	}

	addBtn.on('click',function(){
		addDom();
	});
	tableDom.on('click',delBtn,function(){
		delDom(this);
	});

	return new init();

}


	//图片上传
	var uploadPic = (function  (){
	var nub = 6;
	var htmlDom = function(){
				return [
				           '<li class="addimg" title="',nub,'" >',
				           '<input type="hidden" target="name" name="goodImgs[',nub,'].position" value="'+nub+'">',
				           '<input type="hidden" target="path" name="goodImgs[',nub++,'].url">',
				           '<img name="goodFile" src="/resource/m/img/add100X100.jpg" alt="" class="" data-src="">',
				          ' <span class="action" style="display:none;"><i data-name="delete">删除</i></span>',
				          '</li>'
				          ].join('');
		};
		var leftAction = function(thiz){
			var liDom = $(thiz).closest('li');
			var prevDom = liDom.prev();
			prevDom.before(liDom);
		};
		var rightAction = function(thiz){
			
			var liDom = $(thiz).closest('li');
			var prevDom = liDom.next();
			prevDom.after(liDom);
		};

		
		 //图片添加
		var addgoodsImg = function (thiz,response){
		
			var self = this;
			var el = $('#j-addpic');
			var elclass = thiz.closest('li');
			thiz.attr('src',ROOT_PICURL+response.source);
			if(elclass.attr('title')=='0'){
				$('#picpathvalid1').val(response.path);
				$('#j-addpic').prev('label[for="picpathvalid1"]').remove();
				
			}
			elclass.find('input[target="path"]').val(response.path);
			if(elclass.hasClass('addimg')){
				elclass.find('.action').show();
			}
			if(el.find('li').length <5 && el.find('.addimg').length==0){
				$('input[name="userfile"]').parent().remove();
				var str = htmlDom();
				el.find('li:last').after(str);
				$("img[name='goodFile']").each(function(){
					imgUpload($(this));
				});
			}
		};
		/*图片删除*/
	var delLi = function (thiz){
			var el = $('#j-addpic');
			art.dialog.confirm("确定删除？", function() {
				$(thiz).closest('li').find("img").attr("src","/resource/m/img/add100X100.jpg");
				$(thiz).closest('li').find("span").attr("style","display:none;");
				$(thiz).closest('li').find('input[target="path"]').val("");
				$(thiz).closest('li').find('input[target="id"]').val("");
				if($(thiz).closest('li').attr('title')=='0'){
					$('#picpathvalid1').val("");
				}
				$("img[name='goodFile']").add("img[name='itemFile']").each(function(){
					imgUpload($(this));
				});
			}, function() {
				return true;
			});
		};
		
		
		return {
			addgoodsImg:addgoodsImg,
			delLi:delLi,
			leftAction:leftAction,
			rightAction:rightAction
		};
	})();
	
	$(function(){
			$('#j-addpic').on('click','i',function(){
			var num = $(this).index();
			uploadPic.delLi(this);
		});
	});
	

	//商品数量验证
	function stockValid(){
		var sizeLen = $('#j-proSize input[type="checkbox"]:checked').length
		var i = 0;
		var b = 0;
		$('#template input[name="stock"]').each(function(index){
			if(this.value >0) i++;
			
			if(!((index+1)%sizeLen)){
				if(i == 0){
					b++;
				}
				i = 0;
			}
		});
		if(b>0) return false;

		return true;
	}

	//商品价格验证
	function priceValid(){
		var proPrice = parseFloat($('input[name="marketPriceYuan"]').val());
		var b = 0;
		$('#template input[name="price"]').each(function(index){
			if(parseFloat(this.value) > proPrice){
				b++;
			}	
		});
		if(b>0) return false;
		return true;
	}
		//图片验证
	function picpathvalid(){
		if($('#picpathvalid1').val() != ''){
			return true;
		}
		return false;
	}
	
	
	//商品属性尺码验证
	function checkpop(){
		var b = 0;
		var c = 0;
		$('#j-proColor input:checked').each(function(){
			if(this.value != ''){
				b++;
			}
		});
		$('	#j-proSize input:checked').each(function(){
			if(this.value != ''){
				c++;
			}
		});
	
		return b>0 && c>0? true:false;
	}
	
	// 商品的数量验证   
	jQuery.validator.addMethod("stockvalid", function(value, element) {
	  return this.optional(element) || stockValid(element);   
	}, "请输入商品的数量");
	// 商品价格验证   
	jQuery.validator.addMethod("pricevalid", function(value, element) {
	  return this.optional(element) || priceValid(element);   
	}, "商品价格不能高于市场价");
	 // 条形码验证   
	jQuery.validator.addMethod("codeValid", function(value, element) {
	  return this.optional(element) || /^([a-z0-9A-Z!@#$_])*?$/.test(value); 
	}, "条形码输入错误");
	 //商品属性尺码验证
    jQuery.validator.addMethod("picpathvalid", function(value, element) {	
	    return picpathvalid(); 
	}, "请上传商品首图");
	// 
    jQuery.validator.addMethod("checkpop", function(value, element) {
		  return checkpop(); 
    }, "请勾选商品属性与尺码");
