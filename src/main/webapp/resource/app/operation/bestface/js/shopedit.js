
//alert(1)
		var batchShop = (function(){
			var typeEl = $('[data-active="true"]'),
			editEl = $('[data-edit="true"]'),
			checkEl = typeEl.find('[data-type="check"]');
			
			//移除页面HTML
			var delHtml = function(self){
				$(self).closest('[data-btn="true"]').remove();
			}

			//获取商品单个ID
			var getId = function(self){

				var id = $(self).closest('[data-id]').attr('data-id');
				//console.log(id)
				return id;
			}

			//获取批量操作商品ID
			var getCheck = function(){
				var idArr = [];
				$('[data-active="true"]').find('[data-type="check"].checked').each(function(){
					delHtml(this);
					idArr.push(getId(this));
				});
				//console.log(idArr);
				//return idArr;
				//返回JSON对象
				return JSON.stringify(idArr);
			}

			//批量操作动画
			var showEdit = function(){
				var typeEl = $('[data-active="true"]'),
				editEl = $('[data-edit="true"]'),
				checkEl = typeEl.find('[data-type="check"]');
				
				$('body').css('padding-bottom','44px');
				
				
				editEl.show();
				typeEl.css('float','left');

				checkEl.css('display','block').siblings().hide();

				$('[data-show="show"]').animate({
					"margin-top":-110
				},500);
			}

			//退出批量操作动画
			var closeFun = function(){
				var typeEl = $('[data-active="true"]'),
				editEl = $('[data-edit="true"]'),
				checkEl = typeEl.find('[data-type="check"]');
				
				$('body').css('padding-bottom','0');
				editEl.hide();
				typeEl.css('float','right');

				checkEl.hide().siblings().show();

				$('[data-show="show"]').animate({
					"margin-top":0
				},500);
			}

			//批量操作
			$("[data-batch='true']").on('click',function(){
				showEdit();
			});

			//全选
			$('[data-type="checkAll"]').on('click',function(){
				var typeEl = $('[data-active="true"]'),
				checkEl = typeEl.find('[data-type="check"]');
				checkEl.addClass('checked');
			})

			//退出编辑
			editEl.find('[data-type="colse"]').on('click',function(){
				closeFun();
			
			});

			//复选框
			$('#content').on("click",'[data-type="check"]',function(){
				$(this).toggleClass('checked')
			});
			
			return {
				delHtml:delHtml,
				getId:getId,
				getCheck:getCheck
			}

		})();


