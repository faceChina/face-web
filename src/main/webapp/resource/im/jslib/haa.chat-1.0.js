/***
 * jquery phone chat
 * @version v2.0 
 * @createDate -- 2012-5-28
 * @author ming
 * @email 421543076@qq.com
 * @requires jQuery v1.2.3 or later, send.message.editor-1.0.js
 * Copyright (c) 2014 M. hoo
 **/
 
;(function ($) {
 
    if (/1\.(0|1|2)\.(0|1|2)/.test($.fn.jquery) || /^1.1/.test($.fn.jquery)) {
        alert('WebIM requires jQuery v1.2.3 or later!  You are using v' + $.fn.jquery);
        return;
    }
    
    var faceTimed, count = 0;
    var _opts = defaultOptions = {
        version: 2.0,
        chat: ".chat",
        chatEl: function () {
            var $chat = _opts.chat;
            if ((typeof _opts.chat) == "string") {
                $chat = $(_opts.chat);
            } else if ((typeof _opts.chat) == "object") {
                if (!$chat.get(0)) {
                    $chat = $($chat);
                }
            } 
            return $chat;
        },
        sender: "", // 发送者
        receiver: "", // 接收者
        senderType:"",
        setTitle: function (receiver) {
        	var receiver = _opts.getReceiver(receiver);
            var str = receiver;
            console.log("111:"+userData[receiver]);
            if(userData[receiver] != '' && userData[receiver] != undefined && userData[receiver] != null ){
            	str = userData[receiver]+'('+receiver+')';
            } else if(receiver.indexOf('anonymous_') != -1){
            	str = '匿名用户('+receiver+')';
            }
            return str;
        },
        getReceiver: function (receiver) {
            if (~receiver.indexOf("@")) {
                receiver = receiver.split("@")[0];
            }
            return receiver;
        },
        
        datetimeFormat: function (v) {
            if (~~v < 10) {
                return "0" + v;
            }
            return v;
        },
        getDatetime: function () {
            // 设置当前发送日前
            var date = new Date();
            var datetime = date.getFullYear() + "/" + (date.getMonth()+1) + "/" + date.getDate();
            datetime += " " + _opts.datetimeFormat(date.getHours()) 
                        + ":" + _opts.datetimeFormat(date.getMinutes()) 
                        + ":" + _opts.datetimeFormat(date.getSeconds());
            return datetime;
        },
        getDate: function () {
            // 设置当前发送日前
            var date = new Date();
            var datetime = date.getFullYear() + "/" + date.getMonth() + "/" + date.getDate();
            return datetime;
        },
        getTime: function () {
            // 设置当前发送日前
            var date = new Date();
            var datetime = " " + _opts.datetimeFormat(date.getHours()) 
                        + ":" + _opts.datetimeFormat(date.getMinutes()) 
                        + ":" + _opts.datetimeFormat(date.getSeconds());
            return datetime;
        },
        /***
         * 消息提醒 条数               
         **/ 
        receivePopTpl: function (userName,userId,content) {
        	//receiver
        	var receiverName = _opts.setTitle(userName);
            return [
					'<div class="chat-info" data-name="',userName,'" data-id="',userId,'">',
					'<h4>',receiverName,'<small>',_opts.getTime(),'</small></h4>',
					'<p><span>',content,'</span><i style="display:none;"></i></p>',
					'</div>'
	            ].join("");
        },
        /***
         * 发送消息的格式模板                    
         * flag = true 表示当前user是自己，否则就是对方
         **/ 
        receiveMessageTpl: function (userName, styleTpl, content, flag) {
        	
            var userCls = flag ? "me" : "you";
            
            //判断是否显示时间，1分钟以内不显示时间
            var now = new Date().getTime();
            if(now > msgTime+60000){
            	msgTime = now;
	            return [
	                '<div class="time">', _opts.getDatetime(), '</div>',
	                '<div class="',userCls,'">', content, '</div>'
	            ].join("");
            }else{
            	
            	return [
            	    '<div class="time">', '</div>',
	                '<div class="',userCls,'">', content, '</div>'
	            ].join("");
            }
        },
        /***
         * 历史消息
         **/ 
        receiveOldMessageTpl: function (userName, styleTpl, content, flag, sendTime) {
            var userCls = flag ? "me" : "you";
            return [
                '<div class="time">', sendTime, '</div>',
                '<div class="',userCls,'">', content, '</div>'
            ].join("");
        },
        writeSystemMessage: function (states) {
        	
        	var typeTxt = states == 1 ? "当前已上线":"当前已离线";
        	if(states == 2 && loginTime == 0){
        		$('input[name="register"]').val(1);
        		remote.jsjac.chat.login(document.userForm);
        		loginTime++;
        		return;
        	}  	
        	if(states){	
        		
        		var content = "<div class='line time'><i><span>"+typeTxt+"</span></div>";
        		  //var messageId = $('#'+receiverId+' .receive-message');
        		if($('.receive-message').length == 0){
        			_opts.senderType = content;
        			return;
        		}else{
        			if($('.receive-message').find('.line').length>0 ){
        				$('.receive-message').find('.line span').text(typeTxt);
        				return;
        			}
        			$('.receive-message').append(content);
        		}
        		
        	}
        },
        // 向接收消息iframe区域写消息
        writeReceiveMessage: function (receiverId, userName, content, flag) {
            if (content) {
                // 发送消息的样式
      
                var styleTpl = '';
            	// 向接收信息区域写入发送的数据
                var messageId = $('#'+receiverId+' .receive-message');
                messageId.append(_opts.receiveMessageTpl(userName, styleTpl, content, flag));
                // 滚动条滚到底部
                //alert(1)
                $('body ').scrollTop(messageId.outerHeight(true));
            }
        },
        /**
         * 历史消息*/
        writeReceiveOldMessage: function (receiverId, userName, content, sendTime, flag) {
            if (content) {
                // 发送消息的样式
                var styleTpl = '';
            	// 向接收信息区域写入发送的数据
                var messageId = $('#'+receiverId+' .icon-loading');
                messageId.after(_opts.receiveOldMessageTpl(userName, styleTpl, content, flag, sendTime));
                //messageId.append($("#phonePro").css({"display":"block"}));
                // 滚动条滚到底部
               // $(_opts.chat).scrollTop($('.receive-message').outerHeight(true));
            }
        },
        /**处理消息列表*/
        writePOPMessage: function (userName,userJID, content) {

        	if(content.indexOf('class="commodity"')>0) content = content.match(/<h3>(.*?)<\/h3>/)[1];
        	
        	if($('#j-popmeg').children().length>0){
            	var popDom = $("[data-id=" + userJID+"]");
            	if (popDom.length>0) {
            		popDom.find('p span').text(content);
            	}else{
            		$('#j-popmeg').append(_opts.receivePopTpl(userName,userJID, content));
            	}
            	popDom = $("[data-id=" + userJID+"]");
            	$('#j-popmeg').prepend(popDom);
            }else{
            	$('#j-popmeg').append(_opts.receivePopTpl(userName,userJID, content));
            }
        },
        /**处理提示消息数量*/
        writePOPMessageNum: function (userJID,flag) {
        	var popDom = $("[data-id=" + userJID+"]");
        	if(flag){
        		//累加消息数量
        		var num = popDom.find('p i').text();
        		popDom.find('p i').text(++num).show();
        	}else{
        		//清除消息数量
        		popDom.find('p i').text("").hide();
        	}
        },
        // 发送消息
        sendHandler: function (userJID, sender, receiver) {
            var content = $('#'+userJID).find('textarea').val();
            content = $.trim(content).replace(/</g, '&lt;').replace(/>/g, '&gt;');
            //content = content.replace(new RegExp("<br>", "gm"), "");
            // 获取即将发送的内容
            if (content) {
            	var receiverId = userJID;
                // 接收区域写消息
                _opts.writeReceiveMessage(receiverId, sender, content, true);
               
                // 判断是否是手机端会话，如果是就发送纯text，否则就发送html代码
                remote.jsjac.chat.sendMessage(content, receiver);             
                // 清空发送区域
                $('#'+userJID).find('textarea').val('');
                //处理消息列表
                _opts.writePOPMessage(receiver,userJID, content);
                
                //保存聊天记录
                $.ajax({
                	url:'/wap/'+$("#shopNo").val()+'/im/message/save.htm',
    				data:{sender:$("#imUserId").val(),receiver:receiver,message:content},
    				type:'post',
    				dataType:'text',
    				success:function(data){
    				},
    				error:function(){
    				}
    			});
            }
        }, 
        
        // 发送链接
        sendLink: function (content,$chatMain) {
            if (content) {
                var sender = $chatMain.attr("sender");
                var receiverId = $chatMain.attr("id");
                // 接收区域写消息
                _opts.writeReceiveMessage(receiverId, sender, content, true);
                var receiver = $chatMain.attr("receiver");
                // 判断是否是手机端会话，如果是就发送纯text，否则就发送html代码
                remote.jsjac.chat.sendMessage(content, receiver);
                //保存聊天记录
                $.ajax({
                	url:'/wap/'+$("#shopNo").val()+'/im/message/save.htm',
    				data:{sender:$("#imUserId").val(),receiver:receiver,message:content},
    				type:'post',
    				dataType:'text',
    				success:function(data){
    				},
    				error:function(){
    				}
    			});
            }
        }, 
        
        faceImagePath: "images/emotions/",
        faceElTpl: function (i) {
            return [
                "<img src='",
                this.faceImagePath,
                (i - 1),
                "fixed.bmp' gif='",
                this.faceImagePath,
                (i - 1),
                ".gif'/>"
            ].join("");
        },
        // 聊天界面html元素
        chatLayoutTemplate: function (userJID, sender, receiver, flag) {
        	var userName =_opts.setTitle(receiver);
        	//是否需要显示聊天对话页面
        	var display = "";
            if (flag) {
                display = "style='display: none;'";
            }
            //是否是匿名用户
            var anonymous = "";
            if(receiver.indexOf('anonymous_') != -1) {
            	anonymous = "style='display: none;'";
            }
            
		        	return [
		        	        '<div class="phone-im"  id="', userJID,'" sender="', sender, '" receiver="', receiver, '"',display,'>',
		        	        '<div class="user"><span class="colse">返回</span>',userName,'</div>',
		        	'<div class="chat">',
		    		'<div class="receive-message clearfix">',
		    			'<div class="icon-loading" onclick="moreHistroy(\'',userJID,'\')"',anonymous,'>',
		    				'<span>点击显示历史记录</span>',
		    			'</div>',
		    		'</div> ',
		    		'<div class="send-message" id="footer">',
		    			'<div class="send-button">',
		    				'<input type="hidden" id="to" name="to" value="" />',
		    				'<button type="button" class="btn btn-default send">发送</button>',
		    			'</div>',
		    			'<div class="send-text">',
		    				'<textarea id="jtextarea" rows="1" class="form-control" style="resize:none;"></textarea>',
		    			'</div>	',
		    		'</div>',
		    	'</div>',
		    '</div>',
        	            ].join("");
        },
      
        /***
         * 发送消息工具栏按钮事件方法
         **/             
        isMobileClient: function (receiver) {
            var moblieClients = ["iphone", "ipad", "ipod", "wp7", "android", "blackberry", "Spark", "warning", "symbian"];
            var flag = false;
            for (var i in moblieClients) {
                if (~receiver.indexOf(moblieClients[i])) {
                    return true;
                }
            }
            return false;
        },
        initWebIM: function (userJID,receiver,flag) {
            var chatHtml = $(_opts.chatLayoutTemplate(userJID, _opts.sender, receiver,flag));
            $("body").append(chatHtml);        
            var chatEl = $('#'+userJID);
            chatEl.find('.receive-message').append(_opts.senderType);
            sendMessageEditor.iframe = chatEl.find('textarea');
            chatEl.find(".send").click(function () {
                var $chatMain = $(this).parents(".chat-main");
               	 _opts.sendHandler(userJID, _opts.sender, receiver);
            });
            chatEl.find(".colse").click(function () {
            	chatEl.hide();
            });

        },
        
        // 建立新聊天窗口
        newWebIM: function (settings) {
            var chatUser = remote.userAddress(settings.receiver);
            var userJID = "u" + hex_md5(chatUser);
         
            //窗口是否存在
            if ($("div[id='" + userJID + "']").length>0) {
            	$('#'+userJID).show();
            	_opts.writePOPMessageNum(userJID,false);
            	return;
            }
           
            /*$(_opts.chat).parent().attr({
            	'id':userJID,
            	'sender':_opts.sender,
            	'receiver':chatUser
            });*/
            _opts.receiver = settings.receiver;
        
            _opts.initWebIM(userJID, chatUser);
            $("#" + userJID).find(remote.receiver).val(chatUser);
            
            /*var senderId = $("#imUserId").val();
            var params = {
        			"sender":senderId,
          			"receiver":settings.receiver,
          			"userJID":userJID
        	 };
		     //获取聊天记录
		   	 _opts.loadingMessage(params);*/
		   	 //_opts.writeSystemMessage("以上是历史消息");
        },
      //加载历史消息
        loadingMessage : function(params){
        	var receiver = params["receiver"].toUpperCase();
        	var userJID = $("#"+params["userJID"]);
        	var curPage = userJID.attr("data-curPage") == undefined ? 1 : userJID.attr("data-curPage");
        	var start = userJID.attr("data-start") == undefined ? 0 : userJID.attr("data-start");
        	var totalRow = userJID.attr("data-totalRow") == undefined ? 1 : userJID.attr("data-totalRow");
        	var currentTime = $("#currentTime").val();
        	if(totalRow == 0)return;
        	if(totalRow == start)return;
        	
        	$('<img src="/resource/im/img/icon-loading.gif" alt=""/>').css({
        		"margin-right":"10px"
        	}).insertBefore($("#"+params["userJID"]+" .icon-loading span").html("加载中..."));
        	
         	$.ajax({
         		url:'/haa/im/message/list.htm',
         		data:{
         			"sender":params["sender"],
         			"receiverStr":receiver,
         			"start":start,
         			"pageSize":10,
         			"curPage":curPage,
         			"currentTime":currentTime
         		},
         		type:'post',
         		dataType:'text',
         		success:function(data){
         			$("#"+params["userJID"]+" .icon-loading img").remove();
         			var imDom = $("#"+params["userJID"] +" .receive-message");
         			var json = eval("("+data+")");
         			var dats = eval("("+json.info+")");
         			var messages = dats.datas;
         			if(messages == ''){
         				$("#"+params["userJID"]+" .icon-loading span").html("没有聊天记录");
         				return;
         			}
         			//加载下一页前，生成并保存当前滚动条所在标签对象
         			var bodyHeightFirst = 0;
         			var bodyHeightLast = 0;
         			if(curPage != 1){
         				
         				bodyHeightFirst = imDom.outerHeight(true);
         			}
         			for(i in messages){
         				var date = messages[i].sendTime;
         				var timeStr = "";
         				//相邻两消息的同一分钟内不显示时间
         				if(i!=0 && ((messages[i-1].sendTime["minutes"] != date["minutes"])
         					|| (messages[i-1].sendTime["hours"] != date["hours"] 
 							&& messages[i-1].sendTime["minutes"] == date["minutes"]) )){
         					timeStr = (date["year"]+1900)+"/"+(date["month"]+1)+"/"+date["date"]+" "
			     					+_opts.datetimeFormat(date["hours"])+":"
			     					+_opts.datetimeFormat(date["minutes"])+":"
			     					+_opts.datetimeFormat(date["seconds"]);
         				}
         				if(messages[i].sender == $("#imUserId").val()){
         					$.WebIM.writeReceiveOldMessage(params["userJID"], '', messages[i].message,timeStr,true);
         				}else{
         					$.WebIM.writeReceiveOldMessage(params["userJID"], params["receiver"], messages[i].message,timeStr,false);
         				}
         			}
         			
         			//最后一页时,隐藏加载loading图标,，变成“查看更多消息”，然后隐藏
         			if(curPage == dats.totalPage){
         				$("#"+params["userJID"]+" .icon-loading span").html("已没有更多记录");
         			}else{
         				$("#"+params["userJID"]+" .icon-loading span").html("点击显示更多记录");
         			}
         			
         			if(curPage == 1){
         				// 第一页时，滚动到底部
         				$('body').scrollTop(imDom.outerHeight(true));
         			}else{
         				// 否则，滚动条不动
         				bodyHeightLast = imDom.outerHeight(true);
      
         				$('body').scrollTop(bodyHeightLast - bodyHeightFirst);
         			}
         			
         			//记录当前页
         			userJID.attr("data-curPage",++curPage);
         			userJID.attr("data-totalRow",dats.totalRow);
         			userJID.attr("data-start",dats.end);
         		}
         	});
        },
        // 远程发送消息时执行函数
        messageHandler: function (user, content) {
        	voicePlay();
            var userName = user.split("@")[0];
            var tempUser = user;
            if (~tempUser.indexOf("/")) {
                tempUser = tempUser.substr(0, tempUser.indexOf("/"));
            }
            var userJID = "u" + hex_md5(tempUser);
            
            // 首次初始webIM
            if (!$("#" + userJID).get(0)) {
                // 初始IM面板；
                _opts.initWebIM(userJID, user,true);
            } 
            // 设置消息接受者的名称
            $("#" + userJID).find(remote.receiver).val(user);
            //处理消息列表
            _opts.writePOPMessage(userName,userJID, content);
            //处理提示消息数量
            _opts.writePOPMessageNum(userJID,true);
        
            if ($("#" + userJID).get(0)) {
                // 消息提示
                if ($("div[id='" + userJID + "']:hidden").get(0)) {
                    var haveMessage = $(".have-msg");
                    var userArr ='';
                    userArr = haveMessage.attr('userJID')==undefined?userJID:(userJID+','+haveMessage.attr('userJID'));
                    haveMessage.attr('userJID',userArr);
                    haveMessage.addClass('twinkle');
                }
                _opts.messageTip(userJID);
                // 向chat接收信息区域写消息
                _opts.writeReceiveMessage(userJID, userName, content, false);
            } 
        },
        // 消息提示
        messageTip: function (el) {
            if (count % 2 == 0) {
                window.focus();
                document.title = "你来了新消息，请查收！";
            } else {
                document.title = "";                
            }
            if (count > 4) {
                document.title = "";    
                count = 0;            
            } else {
                window.setTimeout(_opts.messageTip, 1000);
                count ++;
            }
        }
    };
    // 初始化远程聊天程序相关方法
    var initRemoteIM = function (settings) {
        // 初始化远程消息
        remote.jsjac.chat.init();
        
        // 设置客户端写入信息方法
        remote.jsjac.chat.writeReceiveMessage = settings.writeReceiveMessage;
        remote.jsjac.chat.writeSystemMessage = settings.writeSystemMessage;
    
        // 注册事件
        $(window).bind({
             unload: remote.jsjac.chat.unloadHandler,
             error: remote.jsjac.chat.errorHandler,
             beforeunload: remote.jsjac.chat.logout
        });
    }
    
    $.extend({
        WebIM: function (opts) {
            opts = opts || {};
            // 覆盖默认配置
            defaultOptions = $.extend(defaultOptions, defaultOptions, opts);
            var settings = $.extend({}, defaultOptions, opts);    
            initRemoteIM(settings);
            
            //settings.newWebIM(settings);
            $.WebIM.settings = settings;
        }
    });
    
    $.WebIM.settings = $.WebIM.settings || _opts;
    $.WebIM.initWebIM = _opts.initWebIM;
    $.WebIM.newWebIM = _opts.newWebIM;
    $.WebIM.messageHandler = _opts.messageHandler;
    $.WebIM.writeReceiveMessage = _opts.writeReceiveMessage;
    $.WebIM.writeReceiveOldMessage = _opts.writeReceiveOldMessage;
    $.WebIM.sendLink = _opts.sendLink;
    $.WebIM.loadingMessage = _opts.loadingMessage;
})(jQuery);