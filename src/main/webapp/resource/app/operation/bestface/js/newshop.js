//图片上传
var picUpload = (function(){
	window.URL = window.URL || window.webkitURL;

	 $('body').on('change','[data-upload="up"]',function(){
	 		handleFiles(this);
	 });

	 $('body').on('click','[data-upload="del"]',function(){
	 		handleDel(this);
	 });

	 //删除图片
	 function handleDel(obj){
	 	var parentEl = $(obj).closest('[data-upload="true"]'),
	 		imgEl = parentEl.find('.m-pic-icon'),
	 		fileEl = parentEl.find('[data-upload="up"]');

	 		$(obj).hide();
	 		imgEl.html('');
	 		fileEl.val('');


	 }

	 //上传图片
	function handleFiles(obj) {
		var files = obj.files,
			img = new Image(),
			parentEl = $(obj).closest('[data-upload="true"]'),
	 		imgEl = parentEl.find('.m-pic-icon'),
	 		delEl = parentEl.find('[data-upload="del"]');
		if(window.URL){
			//File API
			// alert(files[0].name + "," + files[0].size + " bytes");
			img.src = window.URL.createObjectURL(files[0]); //创建一个object URL，并不是你的本地路径
			img.width = 200;
			img.onload = function(e) {
			 window.URL.revokeObjectURL(this.src); //图片加载后，释放object URL
			}

			imgEl.html(img);
			delEl.show();
		}
	}
})();

//添加属性
var addAttr = (function(){

	var str = '',
		moduleNum = 0,
		sortId = 0,
		boxId = $('#j-upload');
	
	var setId = function(id){
		boxId.unbind("click");
		boxId = $(id);
		_bind();
	}
	var setSort = function(idnum){
		sortId = idnum == ''? 0 : parseInt(idnum);
		moduleNum = sortId;
	}
	
	var setHtml = function(strhtml){
		str = strhtml;
	}
	var addHtml = function(strhtml){
		var addStr = strhtml.replace(/\{\{sort\}\}/g,sortId++);
		$('[data-attr="add"]').before(addStr);	
		moduleNum++;
	}
	var delHtml = function(obj){
		$(obj).closest('[data-attr="true"]').remove();
		moduleNum--;
	}
	var moveUp = function(obj){
		var moduleEl = $(obj).closest('[data-attr="true"]'),
			prevEl = moduleEl.prev('[data-attr="true"]'),
			idEl = moduleEl.find('[data-sort="true"]');

		if(moduleNum>=1){
			if(idEl.length >0){
				var prevIdEl = prevEl.find('[data-sort="true"]'),
				 	prevIdVal = prevIdEl.val(),
				 	idVal = idEl.val();

				 	prevIdEl.val(idVal);
				 	idEl.val(prevIdVal);

			}

			moduleEl.prev('[data-attr="true"]').before(moduleEl);

			
		}
	}
	
	//添加
	$('[data-attr="add"]').on('click',function(){
		addHtml(str);
	});
	
	var _bind = function(){
		console.log(boxId)
		//删除
		boxId.on('click','[data-attr="del"]',function(){
			delHtml(this);
		});

		//上移
		boxId.on('click','[data-attr="move-up"]',function(){
			moveUp(this);
		});
	}
	
	_bind();

	

	return {
		setHtml:setHtml,
		setSort:setSort,
		setId: setId
	}

})();
