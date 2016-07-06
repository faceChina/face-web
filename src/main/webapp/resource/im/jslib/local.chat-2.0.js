/***
 * jquery local chat
 * @version v2.0 
 * @createDate -- 2012-5-28
 * @author hoojo
 * @email hoojo_@126.com
 * @blog http://hoojo.cnblogs.com & http://blog.csdn.net/IBM_hoojo
 * @requires jQuery v1.2.3 or later, send.message.editor-1.0.js
 * Copyright (c) 2012 M. hoo
 **/
 
;(function ($) {
 
    if (/1\.(0|1|2)\.(0|1|2)/.test($.fn.jquery) || /^1.1/.test($.fn.jquery)) {
        alert('WebIM requires jQuery v1.2.3 or later!  You are using v' + $.fn.jquery);
        return;
    }
    
    var faceTimed, count = 0;
    
    var _opts = defaultOptions = {
    	imArr:[],
        version: 2.0,
        chat: "#chat",
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
        sendMessageIFrame: function (receiverId) {
            return $("iframe[name='sendMessage" + receiverId + "']").get(0).contentWindow;
        },
        receiveMessageDoc: function (receiverId) {
            receiverId = receiverId || "";
            var docs = [];
            $.each($("iframe[name^='receiveMessage" + receiverId + "']"), function () {
                docs.push($(this.contentWindow.document));
            });
            return docs;
            //return $($("iframe[name^='receiveMessage" + receiverId + "']").get(0).contentWindow.document);
        },
        sender: "", // 发送者
        receiver: "", // 接收者
        receiverShow: "",//接受者显示
        setTitle: function (chatEl) {
            var receiver = this.getReceiver(chatEl);
            var str = receiver;
            if(userData[receiver] != undefined){
            	str = userData[receiver]+'('+receiver+')';
            } else if(receiver.indexOf('anonymous_') != -1){
            	str = '匿名用户('+receiver+')';
            }
            chatEl.find(".title").html("<b>"+str+"</b>");
        },
        getReceiver: function (chatEl) {
            var receiver = chatEl.attr("receiver");
            if (~receiver.indexOf("@")) {
                receiver = receiver.split("@")[0];
            }
            return receiver;
        },
        
        // 接收消息iframe样式
        receiveStyle: [
               '<html>',
                   '<head><style type="text/css">',
                   'body{border:0;margin:0;padding:0px;height:98%;cursor:text;background-color:white;font-size:12px;font-family: "微软雅黑"; word-wrap:break-word;}',
                   '.msg{margin-left: 1em;margin-bottom:5px;}p{margin:0;padding:0;}.me{color: blue;margin-bottom:5px;}.you{color:green;margin-bottom:5px;}',
                   '.commodity{width:60%;background:#fff;border:1px solid #ccc;padding:1em;color:#333;}',
                   '.commodity dl{padding:0;margin:0;zoom:1;}',
                   '.commodity dl:before,.commodity dl:after{display:table;clear:both;content:"";}',
                   '.commodity dl dt{float:left;display:inline;margin-right:1em;}',
                   '.commodity dl dt img{display:block;width:80px;height:80px;}',
                   '.commodity dl dd h3{font-weight:normal;font-size:12px;margin:0;padding:0;}',
                   '.commodity dl dd code{display:block;font-weight:bold;font-size:16px;line-height:30px;font-family: "微软雅黑","Helvetica Neue",Helvetica,Arial,sans-serif;}',
                   '.commodity .btn{text-decoration:none;display:block;margin: 0 auto;width: 50%;} ',
                   '</style></head>',
                   '<body></body>',
               '</html>'
        ].join(""),
        writeReceiveStyle: function (receiverId) {
            this.receiveMessageDoc(receiverId)[0].get(0).write(this.receiveStyle);
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
        
        /***
         * 发送消息的格式模板                    
         * flag = true 表示当前user是自己，否则就是对方
         **/ 
        receiveMessageTpl: function (userName, styleTpl, content, flag) {
        	var hasCommodity = /commodity/.test(content);
        	var urlFlag = false;
        	if(!hasCommodity){
        		urlFlag = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/.test(content);
        	}
            var userCls = flag ? "me" : "you";
            if (styleTpl && flag) {
            		content = [ "<span style='", styleTpl, "'>", content, "</span>" ].join(""); 
            }
            userName = userData[userName] != undefined ? userData[userName] : userName;
            console.log(content)
	        if(urlFlag){
	        	return [
	                    '<p class="', userCls, '">',userName , '  ', _opts.getDatetime(), '</p>',
	                    '<p class="msg"><a href="',content,'" target="_blank">', content, '</a></p>'
	                ].join("");
	    	}else{
	    		return [
	                    '<p class="', userCls, '">',userName , '  ', _opts.getDatetime(), '</p>',
	                    '<p class="msg">', content, '</p>'
	                ].join("");	
	    	}
            
        },
        
        /***
         * 向接收区域写聊天记录
         **/ 
        receiveOldMessageTpl: function (userName, styleTpl, content, flag ,oldTime) {
        	var hasCommodity = /commodity/.test(content);
        	var urlFlag = false;
        	if(!hasCommodity){
        		urlFlag = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/.test(content);
        	}
            var userCls = flag ? "me" : "you";
            if (styleTpl && flag) {
            		content = [ "<span style='", styleTpl, "'>", content, "</span>" ].join(""); 
            }
            userName = userData[userName] != undefined ? userData[userName] : userName;
	        if(urlFlag){
	    		//content = 
	        	return [
	                    '<p class="', userCls, '">',userName , '  ', oldTime, '</p>',
	                    '<p class="msg"><a href="',content,'" target="_blank">', content, '</a></p>'
	                ].join("");
	    	}else{
	    		return [
	                    '<p class="', userCls, '">',userName , '  ', oldTime, '</p>',
	                    '<p class="msg">', content, '</p>'
	                ].join("");	
	    	}
        },
        
        // 工具类按钮触发事件返回html模板
        sendMessageStyle: {
             cssStyle: {
                 bold: "font-weight: bold;",
                 underline: "text-decoration: underline;",
                 italic: "font-style: oblique;"
             },
             setStyle: function (style, val) {
            	 var currentId = $(event.target).parent().attr('id');
    			 if(_opts.sendMessageStyle[currentId] === undefined){
    				_opts.sendMessageStyle[currentId] = {};
    			 }
                 if (val) {
                     _opts.sendMessageStyle[currentId][style] = val;
                 } else {
                     var styleVal = _opts.sendMessageStyle[currentId][style];
                     if (!styleVal || styleVal === undefined) {
                    	 $(event.target).attr('id',style);
                         _opts.sendMessageStyle[currentId][style] = true;
                     } else {
                    	 $(event.target).removeAttr('id');
                         _opts.sendMessageStyle[currentId][style] = false;
                     }
                 }
                 //_opts.sendMessageStyle.setStyleTplCurrent(currentId);
             },
             getStyleTpl: function (userJID) {
                 var tpl = "";
                 if(userJID != undefined && _opts.sendMessageStyle["tool"+userJID] !=undefined){
                	 $.each(_opts.sendMessageStyle["tool"+userJID], function (style, item) {
                          if (item === true) {
                              tpl += _opts.sendMessageStyle.cssStyle[style];
                          } else if ((typeof item) === "string") {
                              tpl += style + ":" + item + ";";
                          }
       
                      });
                 }
                 return tpl;
             }
        },
        writeSystemMessage: function (states) {
        	$("#statesId").val(states);
        	if(states == 2 && loginTime == 0){
        		$('input[name="register"]').val(1);
        		remote.jsjac.chat.login(document.userForm);
        		loginTime++;
        	} 
        },
        // 向接收消息iframe区域写消息
        writeReceiveMessage: function (receiverId, userName, content, flag) {
            if (content) {
                // 发送消息的样式
                var styleTpl = _opts.sendMessageStyle.getStyleTpl(receiverId);
                var receiveMessageDoc = _opts.receiveMessageDoc(receiverId);
                $.each(receiveMessageDoc, function () {
                    var $body = this.find("body");
                    // 向接收信息区域写入发送的数据
                    $body.append(_opts.receiveMessageTpl(userName, styleTpl, content, flag));
                    // 滚动条滚到底部
                    this.scrollTop(this.height());
                });
            }
        },
        
        // 向接收消息iframe区域写历史消息
        writeReceiveOldMessage: function (receiverId, userName, content,oldTime, flag) {
            if (content) {
                // 发送消息的样式
                var styleTpl = _opts.sendMessageStyle.getStyleTpl(receiverId);
                var receiveMessageDoc = _opts.receiveMessageDoc(receiverId);
                $.each(receiveMessageDoc, function () {
                    var $body = this.find("body");
                    // 向接收信息区域写入发送的数据
                   if($body.find("p").length >0){
                    	$(""+_opts.receiveOldMessageTpl(userName, styleTpl, content, flag ,oldTime)).insertBefore($body.find("p").eq(0))
                    }else{
                    	$body.append(_opts.receiveOldMessageTpl(userName, styleTpl, content, flag ,oldTime));
                    }
                });
            }
        },
        
        
        // 发送消息
        sendHandler: function ($chatMain) {
            var doc = $chatMain.find("iframe[name^='sendMessage']").get(0).contentWindow.document;
            
            var content = $(doc.body).html();
            content = $.trim(content).replace(/</g, '&lt;').replace(/>/g, '&gt;');;
            content = content.replace(new RegExp("<br>", "gm"), "");
            
            // 获取即将发送的内容
            if (content) {
                var sender = $chatMain.attr("sender");
                var receiverId = $chatMain.attr("id");
                if($("#statesId").val() != 1){
                	_opts.writeReceiveMessage(receiverId, sender, "<b style='color:red;'>您已离线,无法发送消息</b>", true);
                	return;
                }
                // 接收区域写消息
                _opts.writeReceiveMessage(receiverId, sender, content, true);
                var receiver = $chatMain.find("#to").val();
                // 判断是否是手机端会话，如果是就发送纯text，否则就发送html代码
                var flag = _opts.isMobileClient(receiver);
                if (flag) {
                    var text = $(doc.body).text();
                    text = $.trim(text);
                    if (text) {
                        // 远程发送消息
                        remote.jsjac.chat.sendMessage(text, receiver);
                        content = text;
                    }
                } else { // 非手机端通信 可以发送html代码
                   var styleTpl = _opts.sendMessageStyle.getStyleTpl(receiverId);
                    content = [ "<span style='", styleTpl, "'>", content, "</span>" ].join("");
                    remote.jsjac.chat.sendMessage(content, receiver);
                }
                var sender = $("#imUserId").val();
                
                //保存聊天记录
                $.ajax({
                	url:'/im/message/save.htm',
    				data:{sender:sender,receiver:receiver,message:content},
    				type:'post',
    				dataType:'text',
    				success:function(data){
    				},
    				error:function(){
    				}
    			});
                
                // 清空发送区域
                $(doc).find("body").html("");
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
        // 创建表情html elements
        createFaceElement: function ($chat) {
            var faces = [];
            for (var i = 1; i < 100; i++) {
                 faces.push(this.faceElTpl(i));
                 if (i % 11 == 0) {
                     faces.push("<br/>");
                 } 
            }
            $chat.find("#face").html(faces.join(""));
            this.faceHandler($chat);
        },
        // 插入表情
        faceHandler: function ($chat) {
            $chat.find("#face img").click(function () {
                 $chat.find("#face").hide(150);
                 var imgEL = "<img src='" + $(this).attr("gif") + "'/>";
                 var $chatMain = $(this).parents(".chat-main");
                 var win = $chatMain.find("iframe[name^='sendMessage']").get(0).contentWindow;
                 var doc = win.document;
                 sendMessageEditor.insertAtCursor(imgEL, doc, win);
            });
            // 表情隐藏
            $chat.find("#face, #face img").mouseover(function () {
                window.clearTimeout(faceTimed);
            }).mouseout(function () {
                window.clearTimeout(faceTimed);
                faceTimed = window.setTimeout(function () {
                    $chat.find("#face").hide(150);
                }, 700);
            });
        },
        /***
         * 发送消息工具栏按钮事件方法
         **/
        toolBarHandler: function () {
            var $chat = $(this).parents(".chat-main");
            var targetCls = $(this).attr("class");
            var receiverId = $chat.attr('id');
            
            if (targetCls == "face") {
                $chat.find("#face").show(150);
                /*window.clearTimeout(faceTimed);
                faceTimed = window.setTimeout(function () {
                    $chat.find("#face").hide(150);
                }, 1000);*/
            } else if (this.tagName == "DIV") {
                _opts.sendMessageStyle.setStyle(targetCls);
            } else if (this.tagName == "SELECT") {
                _opts.sendMessageStyle.setStyle($(this).attr("name"), $(this).val());
                if ($(this).attr("name") == "color") {
                    $(this).css("background-color", $(this).val());
                }
            }
            
            // 设置sendMessage iframe的style css
           
            _opts.writeSendStyle(receiverId);
        },
        // 设置sendMessage iframe的style css
        writeSendStyle: function (userJID) {
        	var receiverId =userJID;
        	//var styleTpl = $('#'+receiverId).attr('tpl') === undefined? '':$('#'+receiverId).attr('tpl');//绑定每个窗口
            var styleTpl = _opts.sendMessageStyle.getStyleTpl(receiverId);
            var styleEL = ['<style type="text/css">body{', styleTpl,'}</style>'].join("");
            
            $("body").find("iframe[name='sendMessage"+receiverId+"']").each(function () {
                var $head = $(this.contentWindow.document).find("head");
                if ($head.find("style").size() > 1) {
                    $head.find("style:gt(0)").remove();
                }
                if (styleTpl) {
                    $head.append(styleEL);
                }
            });
        },                
        
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
 
        // 聊天界面html元素
        chatLayoutTemplate: function (userJID, sender, receiver, product, flag) {
            var display = "";
            if (flag) {
                display = "style='display: none;'";
            }
            return [
                    '<div class="chat-main" id="', userJID,'" sender="', sender, '" receiver="', receiver, '" style="z-index:10000">',
                    	'<div id="chat"><div class="radius">',
                    		'<div class="title"></div>',
                    		'<div class="info"><div class="left"><div class="receive-message">',
                    			'<iframe name="receiveMessage', userJID,'" frameborder="0" width="100%" height="100%"></iframe>',					
                    		'</div>',
					'<div class="tool-bar" id="tool', userJID,'">',
						 '<select name="font-family" class="family">',
                                    '<option>宋体</option>',
                                    '<option>黑体</option>',
                                    '<option>幼圆</option>',
                                    '<option>华文行楷</option>',
                                    '<option>华文楷体</option>',
                                    '<option>华文楷体</option>',
                                    '<option>华文彩云</option>',
                                    '<option>华文隶书</option>',
                                    '<option>微软雅黑</option>',
                                    '<option>Fixedsys</option>',
                                '</select>',
                                
                                '<select name="font-size">',
                                    '<option value="12px">大小</option>',
                                    '<option value="10px">10</option>',
                                    '<option value="12px">12</option>',
                                    '<option value="14px">14</option>',
                                    '<option value="16px">16</option>',
                                    '<option value="18px">18</option>',
                                    '<option value="20px">20</option>',
                                    '<option value="24px">24</option>',
                                    '<option value="28px">28</option>',
                                    '<option value="36px">36</option>',
                                    '<option value="42px">42</option>',
                                    '<option value="52px">52</option>',
                                '</select>',
                                '<select name="color">',
                                    '<option value="" selected="selected">颜色</option>',
                                    '<option value="#000000" style="background-color:#000000"></option>',
                                    '<option value="#FFFFFF" style="background-color:#FFFFFF"></option>',
                                    '<option value="#008000" style="background-color:#008000"></option>',
                                    '<option value="#800000" style="background-color:#800000"></option>',
                                    '<option value="#808000" style="background-color:#808000"></option>',
                                    '<option value="#000080" style="background-color:#000080"></option>',
                                    '<option value="#800080" style="background-color:#800080"></option>',
                                    '<option value="#808080" style="background-color:#808080"></option>',
                                    '<option value="#FFFF00" style="background-color:#FFFF00"></option>',
                                    '<option value="#00FF00" style="background-color:#00FF00"></option>',
                                    '<option value="#00FFFF" style="background-color:#00FFFF"></option>',
                                    '<option value="#FF00FF" style="background-color:#FF00FF"></option>',
                                    '<option value="#FF0000" style="background-color:#FF0000"></option>',
                                    '<option value="#0000FF" style="background-color:#0000FF"></option>',
                                    '<option value="#008080" style="background-color:#008080"></option>',
                                '</select>',
                                '<div class="bold"></div>',
                                '<div class="underline"></div>',
                                '<div class="italic"></div>',
                                '<div class="face"></div>',
                                '<div class="history">消息记录</div>',
					'</div>',
					'<div class="send-message">',
						'<iframe name="sendMessage', userJID,'" width="510px" height="120px" frameborder="0"></iframe>',
					'</div>',
					'<div class="bottom-bar">',
						'<input type="text" id="to" name="to" value="" style="width: 100px; display: none;"/><input type="button" value="关闭" id="close"/>',
						'<input type="button" value="发送(Ctrl+Enter)" id="send"/>',
					'</div>',
					'</div>',
			'</div>',
		'</div>',
	'</div>',
'</div>',
           ].join("");
        },
        initWebIM: function (userJID, receiver) {
            var  product =_opts.imProduct.getProduct();
            var chatEl = $(_opts.chatLayoutTemplate(userJID, _opts.sender, receiver, product));
            $("body").append(chatEl);                        
            
            // 拖拽
            $("#" + userJID).easyDrag();
            // 初始化sendMessageEditor相关信息
            sendMessageEditor.iframe = this.sendMessageIFrame(userJID);
            sendMessageEditor.init(userJID);    
            
            _opts.setTitle(chatEl);
            _opts.writeReceiveStyle(userJID);
            _opts.writeSendStyle(userJID);
            _opts.createFaceElement(chatEl);
            
            // 查看更多详情
            chatEl.find(".more").click(function () {
                var $ul = $(this).parents("ul");
                $ul.find(".more").toggle();
                $ul.find(".info").toggle();
                $ul.find(".pic").toggle();
            });
            
            // 收缩详情
            chatEl.find(".split").toggle(function () {
                $(".product-info").hide();
                $(this).parents(".radius").css("border-right-width", "0");
            }, function () {
                $(".product-info").show();
                $(this).parents(".radius").css("border-right-width", "8px");
            });
            
            // 工具类绑定事件 settings.toolBarHandler
            chatEl.find(".tool-bar").children().click(this.toolBarHandler);
            chatEl.find("#send").click(function () {
                 var $chatMain = $(this).parents(".chat-main");
                 var flag = _opts.isMobileClient(receiver);
                 if(flag){
                	 console.log(11)
                 }else{
                	 _opts.sendHandler($chatMain);
                 }
                
             });
            
            //关闭窗口
             chatEl.find("#close").click(function () {
                 var $chatMain = $(this).parents(".chat-main");
                 for(var i=0;i<_opts.imArr.length;i++){
                	 for(key in _opts.imArr[i]){
                		if(key == $chatMain.attr("id")){
                			_opts.imArr.splice(i,1);
                		}
                	 }
                 }
                 $chatMain.remove();
             });
             
             //窗口交替置顶显示
             chatEl.add(chatEl.find('iframe').contents()).click(function () {
                 $(".chat-main").css("z-index", 9999);
                 chatEl.css({"z-index": 10000});
                 
             });
             
             /*$(".no-msg").click(function () {
      	       $(".chat-main:hidden").each(function (i, item) {
      	           var top = i * 10 + 50;
      	           var left = i * 20 + 50;
      	           $(this).show(500).css({top: top, left: left});
      	       });
      	   });*/
             
             $(this.sendMessageIFrame(userJID).document).keydown(function (event) {
                 var e = event || window.event;
                 var keyCode = e.which || e.keyCode;
                 if (e.ctrlKey && keyCode == 13) {
                     var $chatMain = $("#" + $(this).find("body").attr("jid"));
                     _opts.sendHandler($chatMain);
                 }
             });
             $(this.sendMessageIFrame(userJID).document).keyup(function (event) {
                 var e = event || window.event;
                 var keyCode = e.which || e.keyCode;
                 if(e.ctrlKey && keyCode == 86){
                	 var msgText =$(this).find("body").text();
                	 $(this).find("body").html(msgText);
                	// $("#" + $(this).find("body").attr("jid")).text(msgText)
                 }
             });
        },
        
        // 建立新聊天窗口
        newWebIM: function (settings) {
            var chatUser = remote.userAddress(settings.receiver);
            var userJID = "u" + hex_md5(chatUser);
            if (!$("div[id='" + userJID + "']").get(0)) {
            	_opts.initWebIM(userJID, chatUser);
            	$("#" + userJID).find(remote.receiver).val(chatUser);
            	//指定窗口显示位置
            	//判断是否弹出窗口
            	if($(".chat-main").length >1){
            		console.log(_opts.imArr[_opts.imArr.length-1])
            		//alert(1)
            		 for(key in _opts.imArr[_opts.imArr.length-1]){
            			 $("#" + userJID).show(220).css({
    	               		 "left":parseInt(_opts.imArr[_opts.imArr.length-1][key]["left"])+50,
    	               		 "top":parseInt(_opts.imArr[_opts.imArr.length-1][key]["top"])+50
    	               	 });
            		 }
            	}else{
            		$("#" + userJID).show(220).css({
	               		 "left":0,
	               		 "top":0
	               	 });
            	}
            	
            	//将窗口位置做为对象存入全局数组中
            	 var imOffset = {};
            	 var imObject = {};
            	 imOffset["left"] = $("#" + userJID).offset().left;
            	 imOffset["top"] = $("#" + userJID).offset().top;
            	 imObject[userJID] = imOffset;
            	 _opts.imArr.push(imObject);
            	 
            	 //查看更多历史记录
            	 var iframeBody = $("#"+userJID).find("iframe").eq(0).contents().find("body");
            	 iframeBody.append('<a href="javascript:void(0);" style="display:block;text-align:center;" onclick="window.parent.moreHistroy(\''+userJID+'\')">查看更多消息</a>');
               	
            	 var params = {
            			"sender":$("#imUserId").val(),
              			"receiver":settings.receiver,
              			"userJID":userJID
            	 };
            	 _opts.loadingMessage(params);
            }
        },
        //加载历史消息
        loadingMessage : function(params){
        	var userJID = $("#"+params["userJID"]);
        	var curPage = userJID.attr("data-curPage") == undefined ? 1 : userJID.attr("data-curPage");
        	var start = userJID.attr("data-start") == undefined ? 0 : userJID.attr("data-start");
        	var totalRow = userJID.attr("data-totalRow") == undefined ? 1 : userJID.attr("data-totalRow");
        	var currentTime = userJID.attr("data-currentTime") == undefined ? 0 : userJID.attr("data-currentTime");
        	if(totalRow == 0)return;
        	if(totalRow == start)return;
         	$.ajax({
         		url:'/im/message/list.htm',
         		data:{
         			"sender":params["sender"],
         			"receiverStr":params["receiver"],
         			"start":start,
         			"pageSize":10,
         			"curPage":curPage,
         			"currentTime":currentTime
         		},
         		type:'post',
         		dataType:'text',
         		success:function(data){
         			var json = eval("("+data+")");
         			if(!json.success)return;
         			var dats = eval("("+json.info+")");
         			var messages = dats.pagination.datas;
         			var iframeBody = $("#"+params["userJID"]).find("iframe").eq(0).contents().find("body");
         			//加载下一页前，生成并保存当前滚动条所在标签对象
         			var pObject = null;
         			if(curPage != 1){
         				pObject = iframeBody.find("p").eq(0);
         			}
         			for(i in messages){
         				currentTime = messages[i].currentTime;
         				var date = messages[i].sendTime;
         				date = (date["year"]+1900)+"/"+(date["month"]+1)+"/"+date["date"]+" "
         					+_opts.datetimeFormat(date["hours"])+":"
         					+_opts.datetimeFormat(date["minutes"])+":"
         					+_opts.datetimeFormat(date["seconds"]);
         				if(messages[i].sender == $("#imUserId").val()){
         					$.WebIM.writeReceiveOldMessage(params["userJID"], '', messages[i].message,date,true);
         				}else{
         					$.WebIM.writeReceiveOldMessage(params["userJID"], params["receiver"], messages[i].message,date,false);
         				}
         			}
         			//最后一页时，删除“查看更多消息”
         			if(curPage == dats.pagination.totalPage){
         				 iframeBody.find("a").eq(0).remove();
         			}
         			//记录滚动条当前位置
         			if(pObject){
         				iframeBody.scrollTop(pObject.offset().top-16);
         			}
         			// 第一页时，滚动条滚到底部
         			if(curPage == 1){
         				iframeBody.scrollTop(iframeBody.height());
         			}
         			
         			//记录当前页
         			userJID.attr("data-curPage",++curPage);
         			userJID.attr("data-totalRow",dats.pagination.totalRow);
         			userJID.attr("data-start",dats.pagination.end);
         			userJID.attr("data-currentTime",dats.imMessageDto.currentTime);
         		}
         	});
        },
        //getXXX setXXX
        imProduct: {
        	setProduct: function(data){
        		 _opts.imProduct.obj = data;
            },
            getProduct: function(){
	            data = _opts.imProduct.obj;
            	return data;
            },
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
            var haveMessage = $(".have-msg");
            
            // 首次初始webIM
            if (!$("#" + userJID).get(0)) {
            	//匿名聊天时保存聊天记录
            	if(user.indexOf( "anonymous_") != -1){
            		// 初始IM面板；
            		_opts.initWebIM(userJID, user);
            		//将窗口位置做为对象存入全局数组中
	               	 var imOffset = {};
	               	 var imObject = {};
	               	 imOffset["left"] = $("#" + userJID).offset().left;
	               	 imOffset["top"] = $("#" + userJID).offset().top;
	               	 imObject[userJID] = imOffset;
	               	 _opts.imArr.push(imObject);
            	}
            	haveMessage.addClass('twinkle');
            	
            	var userStr ='';
                userStr = haveMessage.attr('userJID');
                if( userStr!=undefined){
                	 if(userStr.indexOf(userName)<0){
                     	userStr = userName+','+haveMessage.attr('userJID');
                     }
                }else{
                	userStr = userName;
                }
                haveMessage.attr('userJID',userStr);
            } 
            // 设置消息接受者的名称
            $("#" + userJID).find(remote.receiver).val(user);
            if ($("#" + userJID).get(0)) {
               /* // 消息提示
                if ($("div[id='" + userJID + "']:hidden").get(0)) {
                    var userStr ='';
                    userStr = haveMessage.attr('userJID');
                    if( userStr!=undefined){
                    	 if(userStr.indexOf(userJID)<0){
                         	userStr = userJID+','+haveMessage.attr('userJID');
                         }
                    }else{
                    	userStr = userJID;
                    }
                    haveMessage.attr('userJID',userStr);
                    //$(".no-msg").hide();
                }*/
                _opts.messageTip(userJID);
                // 向chat接收信息区域写消息
                _opts.writeReceiveMessage(userJID, userName, content,false);
            } 
        },
        
        // 消息提示
        messageTip: function (el) {
            if (count % 2 == 0) {
               // window.focus();
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
    //$.WebIM.imProduct = _opts.imProduct;
    $.WebIM.messageHandler = _opts.messageHandler;
    $.WebIM.writeReceiveOldMessage = _opts.writeReceiveOldMessage;
    $.WebIM.loadingMessage = _opts.loadingMessage;
})(jQuery);