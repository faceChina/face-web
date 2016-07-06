$(function(){

		var $dataSelectAll=$("[data-select-all]"),
		$dataSelectSingle=$("[data-select-single]"),
		$delShopGoods=$(".delShopGoods");
		//编辑/完成
		$("a[data-id='c-t-a-edit']").off().on("click",function(){
			var $obj=$(this),
				status=$obj.data("status"),
				$itemright=$(".item-right"),
				$objprevi=$(".iconfont-yuanquan");
				//编辑
				if (status=="0") {
					$objprevi.removeClass("none");
					$itemright.addClass("l68");
					$obj.text("完成");
					$obj.data("status","1");
					isShowDelBt();
				}
				else{
					$objprevi.addClass("none");
					$itemright.removeClass("l68");
					$obj.text("编辑");
					$obj.data("status","0");
					$delShopGoods.hide();
				}
		});
			// 是否显示删除bt
		 isShowDelBt=function(){
			var selectedLen=getselectedLen();
				if(selectedLen>0){
					$delShopGoods.show();
				}
				else{
					$delShopGoods.hide();
				}
		};
		// 单选
		$(".c-list").off().on("click","[data-select-single]",function(){
			var $obj=$(this);
				if ($obj.hasClass("icon-yuanquanweixuan")) {
					$obj.removeClass("icon-yuanquanweixuan");
					$obj.addClass("icon-roundcheckfill");
				}
				else{
					$obj.removeClass("icon-roundcheckfill");
					$obj.addClass("icon-yuanquanweixuan");
				}
				if (isHasAllSelected($obj)){
					$dataSelectAll.removeClass("icon-yuanquanweixuan");
					$dataSelectAll.addClass("icon-roundcheckfill");
				}
				else{
					$dataSelectAll.addClass("icon-yuanquanweixuan");
					$dataSelectAll.removeClass("icon-roundcheckfill");
				}
				isShowDelBt();
				
		});

		//全选
		$dataSelectAll.off().on("click",function(){
			var $obj=$(this);
			$dataSelectSingle=$("[data-select-single]");
			if($obj.hasClass("icon-yuanquanweixuan")){
				$obj.removeClass("icon-yuanquanweixuan");
				$obj.addClass("icon-roundcheckfill");
				$dataSelectSingle.removeClass("icon-yuanquanweixuan");
				$dataSelectSingle.addClass("icon-roundcheckfill");
			}
			else{
				$obj.removeClass("icon-roundcheckfill");
				$obj.addClass("icon-yuanquanweixuan");
				$dataSelectSingle.removeClass("icon-roundcheckfill");
				$dataSelectSingle.addClass("icon-yuanquanweixuan");
			}
			isShowDelBt();
		});
	
		// 是否全选
		var isHasAllSelected=function(obj){
			var _isAllSelected=false,
				$obj=$("[data-select-single]");
			$obj.each(function(){
				var $obj=$(this);
				if (!$obj.hasClass("icon-roundcheckfill")) {

					_isAllSelected=false;
					return false;
				}
				else{
					return _isAllSelected=true;
				}
			});
			return _isAllSelected;
		}
		// 多少张券
		var getselectedLen=function(){
			return $(".c-list .icon-roundcheckfill").length;
		}

		// 删除
		$delShopGoods.off().on("click",function(){
			var selectedLen=getselectedLen(),
				 listId="",
					isAllSelected=$dataSelectAll.hasClass("icon-roundcheckfill")?true:false,
					tipfont='您确定要全部删除优惠券？';
				if (!isAllSelected) {
					tipfont='确定要删除这<span class="red">'+selectedLen+'</span>张券吗';
				}
			artDialogComfirm(null,function(artDialog){
				if (!isAllSelected) {
					$(".c-list-item .icon-roundcheckfill").each(function(){
						listId+=$(this).data("nameid")+",";
					});
					listId=listId.substring(0,listId.length-1);
				}
			  	ajaxDelListCoupons(listId,isAllSelected);
			},'<div class="delinfo">'+tipfont+'</div>');
		});


	});
