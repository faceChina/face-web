/* 和 iScroll.js 结合使用，计算元素宽高 */
var calculate = function(options){
	options.dom = $(options.dom);
	options.child = $(options.child);
	options.ul = $(options.ul);
	options.li = $(options.li);

	//根据宽高值的不同，计算尺寸
	function size(){
		if(options.width == "" || options.width == null) options.width = "100%";
		if(options.height == "" || options.height == null) options.height = "100%";
		
		
		var parentDomWidth = options.dom.parent().width();
		var parentDomHeight = options.dom.parent().height();
		
		if(options.width.indexOf("%") > -1){
			width = parseFloat(options.width)/100 * parentDomWidth;
		}else{
			width = parseFloat(options.width);
		}

		if(options.height.indexOf("%") > -1){
			height = parseFloat(options.height)/100 * width;
		}else{
			height = parseFloat(options.height);
		}
		
		options.dom.width(width).height(height);

		var domWidth = options.dom.width();
		var domHeight = options.dom.height();
		
		var len = options.li.length;
		
		if(options.type == "horizontal"){
			var liWidth = domWidth/options.cell;
			var liHeight = domHeight;
			var childWidth = liWidth*len;
			options.li.css({
				"width":liWidth,
				"height":liHeight
			});
			
			options.child.css({
				"width":childWidth,
				"height":liHeight
			});
			options.dom.css({
				"width":domWidth,
				"height":liHeight
			});
			
		}
		
		if(options.type == "vertical"){
			var liWidth = domWidth;
			var liHeight = domHeight/options.cell;
			var childWidth = liWidth;
			options.li.css({
				"width":liWidth,
				"height":liHeight
			});
			
			options.child.css({
				"width":liWidth,
				"height":"auto"
			})
			
			options.dom.css({
				"width":liWidth,
				"height":domHeight
			});
		}
	};
	
	size();
	
	$(window).resize(function(){
		size();
	});
}