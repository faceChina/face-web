var rollLoad = {
    elemt:0, //获取添加字符串的高度
    totalRow:1, //最大条数
    idd:'',//最外层的ID
    totalheight:0,//容器的高度
    url:'',//数据访问URL
    start:0,//起始条
    curPage:1,
    param:{},
    //currentTime:'',
    loadType:'Json',
    callback : function(){},
    init:function(obj, call_back){//传入默认值
    	callback = call_back;
    	var winht = $(window).height(),
        screenNum;
        this.elemt = $(obj.elemt).outerHeight();
        this.totalRow = obj.totalRow;
        this.idd= obj.idd;
        this.url=obj.url;
        this.start=Number(obj.start);
        this.param=obj.param;
       /* if(obj.currentTime){
            this.currentTime = obj.currentTime;
        }*/
        screenNum = Math.round(winht/this.elemt)>10?(Math.round(winht/this.elemt)-10):0; //根据屏幕 计算第一次加载数量
        if(obj.loadType=='html'){
        	 this.loadType = obj.loadType;
        	 if(screenNum>0) this.addHtmltext(screenNum);
        }else{
            if(screenNum>0) this.addJsontext(screenNum);
        }
        this.loading();
    },
    loading:function(){ //触发滚动事件
        var thiz = this;
        $(window).scroll(function(){  
           var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)  
            thiz.totalheight = parseFloat($(window).height()) + parseFloat(srollPos);
            if(($(document).height()) <= thiz.totalheight  && thiz.start < thiz.totalRow) { 
            	if(thiz.loadType == 'html'){
            		thiz.addHtmltext(10);
            	}else{
            		thiz.addJsontext(10);
            	}
            }  
        });
    },
    addJsontext:function(num){ //添加字符串
    	var thiz = this;
    	thiz.curPage+=1;
        if(thiz.totalRow==0)return ;
        if(thiz.totalRow==thiz.start)return ;
        thiz.param.curPage=thiz.curPage;
        thiz.param.start=thiz.start;
        thiz.param.pageSize=num;
    	$.post(thiz.url,thiz.param,function(data){
    		var result =JSON.parse(data);
    		if(result.success){
    			var paging = JSON.parse(result.info);
    			var htmlStr = getAppendHtml(paging.datas);
		        thiz.start=thiz.start + num;
		        var main = $(thiz.idd); 
		        main.append(htmlStr);
		        callback();
    		}else{
    			alert(result.info);
    		}
    	});
    },
    addHtmltext:function(num){ //添加字符串
    	var thiz = this;
    	thiz.curPage+=1;
        if(thiz.totalRow==0)return ;
        if(thiz.totalRow==thiz.start)return ;
        thiz.param.curPage=thiz.curPage;
        thiz.param.start=thiz.start;
        thiz.param.pageSize=num;
    	$.post(thiz.url,thiz.param,function(data){
    		if(data){
		        thiz.start+=num;
		        var main = $(thiz.idd); 
		        main.append(data);
		        callback();
    		}else{
    			alert("加载失败!");
    		}
    	});
    }
}

