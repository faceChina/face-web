	function addSingle(str) {
		$(str).appendTo($("#template"));
	}
	/*删除*/
	function del(el) {
		art.dialog.confirm('确认删除？', function() {
			$(el).parent("td").parent("tr").remove();
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
		});
	}

	/*编辑*/
	function editor(el) {
		var tr = $(el).parent("td").parent("tr");

		tr.find("input").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);

		tr.find("img").css({
			display : "none"
		});

		tr.find(".uploadImg").css({
			display : "inline-block"
		});
		tr.find("[data-del]").css({
			display : "inline-block"
		});

		tr.find("select").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);

		tr.find(".addLinkToTemp").attr("disabled", false);
		tr.find(".addLinkToPro").attr("disabled", false);
		tr.find(".addLinkToOut").attr("disabled", false);

		$(el).css({
			display : "none"
		})
		tr.find(".btn-save").css({
			display : "inline-block"
		});
		
		tr.find("p").css({
			display : "none"
		});

		tr.find(".article-change").css({
			display : "block"
		});
	}

	/*保存*/
	function save(el) {
		art.dialog.confirm('你确定要保存？', function () {
		    //art.dialog.tips('执行确定操作');
			var tr = $(el).parent("td").parent("tr");
			tr.find("input").attr("disabled", true);
	
			tr.find("img").css({
				display : "inline-block"
			});
	
			tr.find(".uploadImg").css({
				display : "none"
			});
			tr.find("[data-del]").css({
				display : "none"
			});
			
			tr.find("p").css({
				display : "block"
			});

			tr.find(".article-change").css({
				display : "none"
			});
	
			tr.find("select").attr("disabled", true);
	
			tr.find(".addLinkToTemp").attr("disabled", true);
			tr.find(".addLinkToPro").attr("disabled", true);
			tr.find(".addLinkToOut").attr("disabled", true);
	
			$(el).css({
				display : "none"
			})
			tr.find(".btn-editor").css({
				display : "inline-block"
			});
		}, function () {
		    //art.dialog.tips('执行取消操作');
		});
		
		
		
	}

	/*全部编辑*/
	function editorAll() {
		$("#template").find("input").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);

		$("#template").find("img").css({
			display : "none"
		});

		$("#template").find(".uploadImg").css({
			display : "block"
		});

		$("#template").find("select").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);

		$("#template").find(".addLinkToTemp").attr("disabled", false);
		$("#template").find(".addLinkToPro").attr("disabled", false);
		$("#template").find(".addLinkToOut").attr("disabled", false);

		$("#template").find(".btn-editor").css({
			display : "none"
		});
		$("#template").find(".btn-save").css({
			display : "inline-block"
		});
	}
	/* 保存fun*/
	function funSaveAll(){
		$("#template").find("input").attr("disabled", true);
	
			$("#template").find("img").css({
				display : "inline-block"
			});
	
			$("#template").find(".uploadImg").css({
				display : "none"
			});
	
			$("#template").find("select").attr("disabled", true);
	
			$("#template").find(".addLinkToTemp").attr("disabled", true);
			$("#template").find(".addLinkToPro").attr("disabled", true);
			$("#template").find(".addLinkToOut").attr("disabled", true);
	
			$("#addTemp").attr("disabled", true);
	
			$("#template").find(".btn-editor").css({
				display : "inline-block"
			});
			$("#template").find(".btn-save").css({
				display : "none"
			});
	}

	/*全部保存*/
	function saveAll() {
		art.dialog.confirm('你确定要保存？', function () {
		   // art.dialog.tips('执行确定操作');
		    funSaveAll();
		}, function () {
		    //art.dialog.tips('执行取消操作');
		});
		
		
	}

	/*上移*/
	function up(el) {
		var tbody = $(el).parent("td").parent("tr").parent("tbody");
		var tr = $(el).parent("td").parent("tr");
		var index = tr.index();
		if (index != 0) {
			tr.insertBefore(tr.prev());
		} else {
			return false;
		}
	}

	/*下移*/
	function down(el) {
		var tbody = $(el).parent("td").parent("tr").parent("tbody");
		var tr = $(el).parent("td").parent("tr");
		var len = tbody.find("tr").length;
		var index = tr.index();
		if (index != len - 1) {
			tr.insertAfter(tr.next());
		} else {
			return false;
		}
	}
	
	/*上移置顶*/
	function upFirst(el) {
		var tbody = $(el).parent("td").parent("tr").parent("tbody");
		var tr = $(el).parent("td").parent("tr");
		var index = tr.index();
		if (index != 0) {
			tr.prependTo(tbody);
		} else {
			return false;
		}
	}

	/*下移置底*/
	function downLast(el) {
		var tbody = $(el).parent("td").parent("tr").parent("tbody");
		var tr = $(el).parent("td").parent("tr");
		var len = tbody.find("tr").length;
		var index = tr.index();
		if (index != len - 1) {
			tr.appendTo(tbody);
		} else {
			return false;
		}
	}

	/*添加链接至模块*/
	function addTemp(el) {
		art.dialog({
			lock : true,
			width : '600px',
			title : "添加链接至模块",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToTemp"),
			button : [ {
				name : '保存',
				callback : function() {
					//art.dialog.alert("保存成功！")
					console.log($("#j-addToTemp").find("input:radio:checked").val()+"=="+$("#j-addToTemp").find("input:radio:checked").attr("data-list"))
					art.dialog.tips("保存成功！")
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}

	/*添加链接至模块--文章列表*/
	function addTempArtical(el) {
		var list = art.dialog.list;
		art.dialog({
			lock : true,
			width : '1000px',
			title : "文章列表（可多选）",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToTemp-artical"),
			button : [ {
				name : '保存',
				callback : function() {
					//art.dialog.alert("保存成功！")；
					var str=""
					$("#j-addToTemp-artical").find("input:checkbox:checked").each(function(){
						str+=$(this).val()+",";
					});
					$(el).parent().find("input").attr("checked",true);
					$(el).parent().find("input").attr("data-list",str);
					art.dialog.tips("保存成功！")
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}

	/*添加链接至模块--商品分类 或 添加链接至商品*/
	function addPro(el) {
		art.dialog({
			lock : true,
			width : '1000px',
			title : "添加商品",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToPro"),
			button : [ {
				name : '保存',
				callback : function() {
					//art.dialog.alert("保存成功！")；
					
					var str=$("#j-addToPro").find("input:radio:checked").val();
					$(el).parent().find("input").attr("checked",true);
					$(el).parent().find("input").attr("data-list",str);
					console.log(str);
					
					art.dialog.tips("保存成功！")
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}

	//添加外部链接
	function addLink() {
		art.dialog({
			lock : true,
			width : '400px',
			title : "添加外部链接",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addToLinks"),
			button : [ {
				name : '保存',
				callback : function() {
					//art.dialog.alert("保存成功！")；
					art.dialog.tips("保存成功！")
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		});
	}
	
	 //添加链接至文章列表页
	function articleChange() {
		art.dialog({
			lock : true,
			width : '600px',
			title : "添加链接至文章列表页",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-articleChange"),
			button : [ {
				name : '保存',
				callback : function() {
					art.dialog.alert("保存成功！")
				},
				focus : true
			}, {
				name : '取消'
			} ]
		});
	}
	
	//添加链接至文章列表页 排序
	function articleSort() {
		art.dialog({
			lock : true,
			width : '600px',
			title : "添加链接至文章列表页",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-articleSort"),
			button : [ {
				name : '保存',
				callback : function() {
					art.dialog.alert("保存成功！")
				},
				focus : true
			}, {
				name : '取消'
			} ]
		});
	}
	
	//申请提现
	function extractWithdrawal() {
		$('#j-extractWithdrawal strong').text($('.j-balance strong').text());
		art.dialog({
			lock : true,
			width : '400px',
			title : "提示",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-extractWithdrawal"),
			button : [ {
				name : '确定提交',
				callback : function() {
					
					var flag = $("#extractWithdrawal").validate({
						rules:{
							price:{
								required:true,
								moneyNotZero: true,
								lessTo:'.color-danger'
							},
							password:{
								required:true,
								minlength: 6
							}
						},
						messages:{
							price:{
								required:"请输入金额",
								lessTo:'可用余额不足',
								moneyNotZero:$.format("金额必须大于0.00，保留两位小数")
							},
							password:{
								required:"请输入密码",
								minlength:$.format("密码不能小于{0}个字符")
							}
						}
					}).form();
					if(flag){
						art.dialog.alert("提现申请成功");
						return true;
					}else{
						return false;
					}
				},
				focus : true
			}]
		});
	}
	//提取佣金
	function extractCommission() {
		$('#j-extractCommission strong').text($('.j-balance strong').text());
		art.dialog({
			lock : true,
			width : '400px',
			title : "提示",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-extractCommission"),
			button : [ {
				name : '确定提交',
				callback : function() {
					var flag = $("#extractWithdrawal").validate({
						rules:{
							price:{
								required:true,
								moneyNotZero: true,
								lessTo:'.color-danger'
							}
						},
						messages:{
							price:{
								required:"请输入金额",
								lessTo:'可用余额不足',
								moneyNotZero:$.format("金额必须大于0.00，保留两位小数")
							}
						}
					}).form();
					if(flag){
						art.dialog.alert("转出成功！请到我的收入中查看！");
						return true;
					}else{
						return false;
					}
				},
				focus : true
			}]
		});
	}
	
	//修改密码
	function amend(){
		$('#f-amend').text();
		art.dialog({
			lock : true,
			width : '400px',
			title : "密码修改",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("f-amend"),
			button : [ {
				name : '确定',
				callback : function() {
				var flag = $('#j-chagepassword').validate({
					rules:{
						oldpassword:{
							required: true,
						},
						password: {
					    	required: true,
					    	minlength: 6
					    },
					    confirm_password: {
					    	required: true,
					    	minlength: 6,
					    	equalTo: "#password"
					    }
					},
					messages:{
						oldpassword:{
							required: '密码错误',
						},
						password: {
					    	required: '请输入新密码',
					    	minlength: $.format("密码不能小于{0}个字 符")
					    },
					    confirm_password: {
					    	required: '请输入确认密码',
					    	minlength: $.format("密码不能小于{0}个字 符"),
					    	equalTo: "两次输入密码不一致"
					    }
					}
				}).form();
				if(flag){
					//$('#j-chagepassword')[0].reset();清空表单
					art.dialog.alert("修改成功");

					return true;
				}else{
					return false;
				}
				},
				focus : true
			}]
		});
	}

	//购买产品
	function mediaProduct(){
		art.dialog({
			lock : true,
			width : '550px',
			title : "产品购买",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-product"),
			button : [ {
				name : '确定',
				callback : function() {
					var flag = $('#buypro').validate({
						rules:{
							username:'required',
							mobile:{
								required:true,
								mobile:true
							}
						},
						messages:{
							username:'必须填写！',
							mobile:{
								required:"请输入手机号码",
								mobile:$.format("手机号码错误！")
							}
						}
					}).form();
					if(flag){
						art.dialog.alert("提交成功");
						return true;
					}else{
						return false;
					}
				},
				focus : true
			}, {
				name : '取消'
			} ]
		});
	}
	//购买续费
	function mediaRenewal(){
		art.dialog({
			lock : true,
			width : '550px',
			title : "产品续费",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-product"),
			button : [ {
				name : '确定',
				callback : function() {
					var flag = $('#buypro').validate({
						rules:{
							username:'required',
							mobile:{
								required:true,
								mobile:true
							}
						},
						messages:{
							username:'必须填写！',
							mobile:{
								required:"请输入手机号码",
								mobile:$.format("手机号码错误！")
							}
						}
					}).form();
					if(flag){
						art.dialog.alert("提交成功");
						return true;
					}else{
						return false;
					}
				},
				focus : true
			}, {
				name : '取消'
			} ]
		});
	}
	//创建子帐号
	function createAdm(){
		var flag = $('#j-chilaccount')
			.validate({
				rules:{
					user:{
						required: true,
					},
					password: {
				    	required: true,
				    	minlength: 6
				    },
				    confirm_password: {
				    	required: true,
				    	minlength: 6,
				    	equalTo: "#password"
				    }
				},
				messages:{
					user:{
						required: '请输入姓名',
					},
					password: {
				    	required: '请输入新密码',
				    	minlength: $.format("密码不能小于{0}个字 符")
				    },
				    confirm_password: {
				    	required: '请输入确认密码',
				    	minlength: $.format("密码不能小于{0}个字 符"),
				    	equalTo: "两次输入密码不一致"
				    }
				}
			}).form();
		if(flag){
			createAdmm();
			return true;
		}else{
			return false;
		}
		
	}
	//创建子帐号函数
	function createAdmm(){
		art.dialog({
			lock : true,
			width : '550px',
			title : '提示',
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-product"),
			button : [{
				name : '修改支付密码',
				callback :function(){
					$(".nav.nav-tabs").find("li").eq(0).removeClass('active');
					$(".nav.nav-tabs").find("li").eq(1).addClass('active');
					$(".content.tab-content").find(".tab-pane.panel-body").eq(0).removeClass('active');
					$(".content.tab-content").find(".tab-pane.panel-body").eq(1).addClass('active');
				},focus : true
			},{
				name : '确定',
				callback : function() {
					art.dialog.tips("修改成功");
				}
			} ]
		});
	}
	//修改子帐号密码
	function editorAlll(thiz){
		art.dialog({
			lock : true,
			width : '550px',
			title : '提示',
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-status"),
			button : [{
				name : '确定',
				callback :function(){
						var flag=$("#jformcode").validate({
							rules:{
								code:{
									required:true
								}
							},
							messages:{
								code:{
									required:"请输入验证码"
								}
							}
						}).form();
						
						if(flag){
							editorAll();
						}else{
							return false;
						}
					
					
						if(thiz.hasOwnProperty('id') && thiz.id  == 'updatee'){
							//funSaveAll();
							//editorAll();
						}else{
							
							art.dialog({
							lock : true,
							width : '550px',
							title : '一键重置支付密码设置',
							background : '#000', // 背景色
							opacity : 0.1, // 透明度
							content : document.getElementById("j-resetporsd"),
							button : [{
								name : '确定重置',
								callback :function(){
									var bool=$("#jformpassword").validate({
										rules:{
											pwd:{
												required:true,
												minlength:6
											},
											repwd:{
												required:true,
												minlength:6,
												equalTo:"#pwd"
											}
										},
										messages:{
											pwd:{
												required:"请输入新密码",
												minlength:$.format("密码不能小于{0}个字符")
											},
											repwd:{
												required:"确认新密码",
												minlength:$.format("密码不能小于{0}个字符"),
												equalTo: "两次输入密码不一致不一致"
											}
										}
									}).form();
									
									if(bool){
										icoText('恭喜，支付密码重置成功！','ok-ico');
										
										art.dialog({
											lock : true,
											width : '500px',
											title : '提示',
											background : '#000', // 背景色
											opacity : 0.1, // 透明度
											content : document.getElementById("j-promptico"),
											button : [{
												name : '确定'
											}]
										});
									}else{
										return false;
									}
								},
								focus : true
							},{
								name: '取消'
							} ]
						});
					}
				},focus : true
			}]
		});

	}
	//子帐号绑定产品查看
	function productLook(titlee){
		art.dialog({
			lock : true,
			width : '700px',
			title : titlee+'账号下绑定的产品',
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-productLook"),
			button : [{
				name : '确定'
			} ]
		});
	}
	function icoText(text,className){
		$('#j-promptico').find('dt').attr('class',className)
					 .end().find('dd').text(text);
	}
	function icoTextAlert(text,className,callback) {
		icoText(text,className);
		art.dialog({
		lock : true,
		width : '570px',
		title : '温馨提示',
		background : '#000', // 背景色
		opacity : 0.1, // 透明度
		content : document.getElementById("j-promptico"),
		button : [{
			name : '确定重置',
			callback :callback,focus : true
		},{
			name: '取消'
		}]
	});
	}
	/* 一键重置 */
	function resetAll(){
		icoTextAlert('一键重置，将使该员工管辖的所有产品支付密码进行重置!','sigh-ico',function(){
				var flag = 1;
				editorAlll(this);
			})
		
	
	}
	
	
	
	//子账户钱包余额提取
	function balances(){
		art.dialog({
			lock : true,
			width : '700px',
			title : '子账户钱包余额提取',
			content:'将子账户钱包余额一键提取到主账户钱包，方便快捷！',
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			button : [
			     {
					name : '确定提取',
					callback : function(){
						art.dialog.alert("提取成功！")
					},
					focus: true
			     },
				 {
					 name:'取消'
				 }]
		});
	}
	
	//添加银行卡
	function addbank(){
		$('#j-addbank').text();
		art.dialog({
			lock : true,
			width : '570px',
			title : "添加消费银行卡",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-addbank"),
			button : [ {
				name : '确定',
				callback : function() {
					var flag = $('#addbank').validate({
						rules:{
							bankcard:{
								required:true,
							},
							username:{
								required:true,
							},
							idcardno:{
								idcardno:true,
								required:true
							},
							mobile:{
								required:true,
								mobile:true
							}
						},
						messages:{
							bankcard:{
								required:'银行卡号不能为空',
							},
							username:{
								required:'真实姓名不能为空',
							},
							idcardno:{
								required:'身份证不能为空',
							},
							mobile:{
								required:'电话号码不能为空',
								mobile:'电话号码错误'
							}
						}
					}).form();
					if(flag){
						art.dialog.alert("添加成功");
						return true;
					}else{
						return false;
					}
				},
				focus : true
			},
			{
				name:'取消'
			}
			]
		});
	}
		//添加银行卡
	function register(){
		$('#j-addbank').text();
		art.dialog({
			lock : true,
			width : '570px',
			title : "注册账号",
			background : '#000', // 背景色
			opacity : 0.1, // 透明度
			content : document.getElementById("j-register"),
			button : [ {
				name : '确定',
				callback : function() {
					art.dialog.tips("注册成功！");
				},
				focus : true
			},
			{
				name:'取消'
			}
			]
		});
	}

$(document).ready(function() {
	scrollUp();

	/* 新闻弹出*/
	$('#rollnews').click(function(event) {
	/* Act on the event */
	$(this).hide();
	});
	$('#scrollup li').on('click', function(event) {
		event.preventDefault();
		/* Act on the event */
		$('#rollnews').show();
		console.log($(this).index());
		$('#rollnews .news-list').eq($(this).attr('data-list')).show().siblings().hide();
	});

});

function scrollUp(){
	var $this = $("#scrollup");
	var scrollTimer;
	$this.hover(function(){
	clearInterval(scrollTimer);
	},function(){
	scrollTimer = setInterval(function(){
			 scrollNews( $this );
		}, 5000 );
	}).trigger("mouseleave");
	}
/*新闻滚动*/
function scrollNews(obj){
var $self = obj.find("ul:first"); 
var lineHeight = $self.find("li:first").height(); //获取行高
$self.animate({ "marginTop" : -lineHeight +"px" }, 1000 , function(){
 $self.css({marginTop:0}).find("li:first").appendTo($self); //appendTo能直接移动元素
})
}
