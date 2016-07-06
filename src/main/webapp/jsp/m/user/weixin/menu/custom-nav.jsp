<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="UTF-8" />
		<title>消息管理-自动回复设置</title>	
		<!-- top -->
		<%@ include file="../../../common/base.jsp" %>
		<%@ include file="../../../common/validate.jsp" %>
		<script type="text/javascript" src="http://apps.bdimg.com/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
		<!--top end -->
		<script type="text/javascript">
			$(function(){
				tab("customnav");
			});
		</script>
	</head>
	<body>
		<!-- header -->
		<%@include file="../../../common/header.jsp"%>
		<!-- header end -->
	
		<!-- body -->
		<div class="container" id="j-content">
			<div class="row">
				<div class="col-md-2 " id="vvv">
					<!--nav-left -->
					<%@ include file="../../../common/left.jsp" %>			
					<!--nav-left end-->
				</div>
				<div class="col-md-10">
					<c:set var="crumbs" value="custommenu"/>
					<%@include file="../../../common/crumbs.jsp"%>
					<div class="row">
						<div class="box">
							<div class="title">
								<ul class="nav nav-tabs">
									<li class="active"><a href="#members-set" data-toggle="tab">自定义菜单</a></li>
								</ul>
							</div>
							<div class="content">
								<div class="alert alert-warning clearfix" style="text-align:left">
									<div class="pull-left">
										<!-- <p>通过认证的订阅号或者服务号才能使用自定义菜单</p> -->
										<p>1级菜单最多只能开启3个，2级子菜单最多开启5个</p>
	                                    <p>创建自定义菜单后，由于微信客户端缓存，需要24小时微信客户端才会展现出来。建议测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。</p>
	                                </div>
								</div>
								<div class="customnav clearfix">
									<div class="customnav-left">
										<h3 class="customnav-header">
											<span>菜单管理</span>
											<a href="javascript:void(0)" onclick="toAddMenu()">+ 新增菜单</a>
										</h3>
										<div class="customnav-content">
											<ul class="customnav-menu" id="jmenu">
												<c:forEach items="${customMenuVos }" var="customMenu" varStatus="menuIndex">
													<li class="customnav-item">
														<div class="clearfix">
															<span class="pull-left">
																<i class="iconfont icon-14052230" onclick="toggleSubMenu(this)"></i>
																<c:choose>
																	<c:when test="${ customMenu.code != null && customMenu.code != '' && fn:startsWith(customMenu.code,'modular') }">
																		<a href="javascript:void(0)"  onclick="toShowInfo(this,1)" data-tag="${menuIndex.index }"  data-pid="${customMenu.id }"  data-parentname="${customMenu.name }" data-code="1" data-codename="${customMenu.code }">${customMenu.name }</a>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:void(0)"  onclick="toShowInfo(this,1)" data-tag="${menuIndex.index }"  data-pid="${customMenu.id }"  data-parentname="${customMenu.name }" data-code="2" data-codename="${customMenu.link }">${customMenu.name }</a>
																	</c:otherwise>
																</c:choose>
															</span>
															<span class="pull-right">
																<a href="javascript:void(0)" onclick="toAddSubMenu(this)">+ 新增</a>
																<a href="javascript:void(0)" onclick="toUpdateMenu(this)">修改</a>
																<a href="javascript:void(0)" onclick="toDelMenu(this)">删除</a>
															</span>
														</div>
														<ol class="customnav-sub ui-sortable">
															<c:forEach items="${customMenu.subCustomMenus}" var="submenu" varStatus="submenuIndex">
																<li class="clearfix"  id="pic_1">
																	<span class="pull-left">
																		<c:choose>
																			<c:when test="${ submenu.code != null && submenu.code != '' && fn:startsWith(submenu.code,'modular') }">
																				<a href="javascript:void(0)"  onclick="toShowInfo(this,2)" data-tag="${menuIndex.index}${submenuIndex.index}" data-id="${submenu.id }" data-pid="${submenu.pid }" data-subname="${submenu.name }" data-parentname="${customMenu.name }" data-code="1" data-codename="${submenu.code }">${submenu.name }</a>
																			</c:when>
																			<c:otherwise>
																				<a href="javascript:void(0)"  onclick="toShowInfo(this,2)" data-tag="${menuIndex.index}${submenuIndex.index}" data-id="${submenu.id }" data-pid="${submenu.pid }" data-subname="${submenu.name }" data-parentname="${customMenu.name }" data-code="2" data-codename="${submenu.link }">${submenu.name }</a>
																			</c:otherwise>
																		</c:choose>
																	</span>
																	<span class="pull-right">
																		<a href="javascript:void(0)" onclick="toUpdateSubMenu(this)">修改</a>
																		<a href="javascript:void(0)" onclick="toDelSubMenu(this)">删除</a>
																	</span>
																</li>
															</c:forEach>
														</ol>
													</li>
												</c:forEach>
											</ul>
										</div>
									</div>
									<div class="customnav-right">
										<h3 class="customnav-header">
											<span>菜单信息</span>
										</h3>
										<div class="customnav-content" id="jshowinfo">
									    </div><!-- customnav-right end -->
									</div><!-- customnav end -->
								</div><!-- content end -->
								<div style="text-align:center;margin-top:10px;">
									<button type="button" class="btn btn-danger btn-lg" onclick="creatMenu(this)">生成菜单</button>
								</div>
							</div><!-- box end -->
						</div><!-- row end -->
					</div>
				</div>
			</div>
		</div>
		<!-- body end -->
	
		<!-- footer -->
		<%@ include file="../../../common/footer.jsp" %>
		<!-- footer end -->
	
		<!-- 添加/修改 菜单名称 -->
		<div id="j-addMenu" style="display:none;">
	        <!--主菜单-->
			<form class="form-horizontal">
			  <div class="form-group">
			    <label class="col-sm-3 control-label">菜单名称：</label>
			    <div class="col-sm-6">
			      <input type="email" name="menu" class="form-control"  id="" placeholder="" validate="{required:true,maxlength:4,messages:{required:'请输入主菜单名称',maxlength:'一级菜单最多4个汉字'}}">
			    </div>
			  </div>
			</form>
	
	        <!--子菜单-->
	        <form class="form-horizontal">
	            <div class="form-group">
	                <label class="col-sm-3 control-label">菜单名称：</label>
	                <div class="col-sm-6">
	                    <input type="email" name="menu" class="form-control"  id="" placeholder="" validate="{required:true,maxlength:7,messages:{required:'请输入二级菜单名称',maxlength:'二级菜单最多7个汉字，多出来的部分将会以“...”代替'}}">
	                </div>
	            </div>
	        </form>
		</div>
	
		<!-- 添加其他信息 -->
		<div id="j-addOthers" style="display:none;">
		</div>
		<script type="text/javascript">
	        var formflag1=$("#j-addMenu .form-horizontal:eq(0)").validate(),
	            formflag2=$("#j-addMenu .form-horizontal:eq(1)").validate();
	
	        var codeJson = ${customMenuModularJson};
	
	        $(function(){
	            $.metadata.setType("attr", "validate");
	        })
	        //拖动排序
			$( "#jmenu .customnav-sub").each(function(){
				$(this).sortable({
					 update:function(event,ui){
						 var sorted = $(this).sortable("serialize", {key:"sort"});
					 }
				 });
			});
	
	//         $(function(){
	//             //开关
	//             $("[data-onoff]").each(function(){
	//                 var self = this,
	//                         activeEl = $(self).parents('td').prev().find('[data-onoff]');
	//                 $(this).onoff({
	//                     on:function(){
	
	//                         activeEl.show();
	//                         return true;
	//                     },
	//                     off:function(){
	
	//                         activeEl.hide();
	//                         return true;
	//                     }
	//                 })
	//             });
	//         })
	
			//菜单类型选择切换
			function toSelectMenuType(thiz){
				var str = $(thiz).val();
				if(str == "链接至外部链接"){
					$("#jlink").show();
					$("#jmodule").hide();
				}else if(str == "链接至模块"){
					$("#jlink").hide();
					$("#jmodule").show();
				}
			}
	
			//新增菜单
			function toAddMenu(){
	            var menuVal=$("#j-addMenu .form-horizontal:eq(0) input");
	            var menulength=$('#jmenu>li').length;
	            if(formflag1){
	                formflag1.resetForm();
	            }
	            menuVal.val("");
	            if(menulength>2){
	                art.dialog({
	                    width:"450px",
	                    title:"温馨提示",
	                    content:"1级菜单最多只能开启3个",
	                    lock: true,
	                    ok:true,
	                    cancel:true
	                })
	            }else{
	                art.dialog({
	                    width:"450px",
	                    title:"添加菜单",
	                    content:$("#j-addMenu .form-horizontal:eq(0)")[0],
	                    lock: true,
	                    ok:function(){
	                        if(formflag1.form()){
	                            toInsertFirst(menuVal.val());
	                        }else{
	                            return false;
	                        }
	                    },
	                    cancel:true
	                })
	            }
			}
	
			//新增二级菜单
			function toAddSubMenu(thiz){
				console.log(1)
	            var menuVal=$("#j-addMenu .form-horizontal:eq(1) input");
	            if(formflag2){
	                formflag2.resetForm();
	            }
	            menuVal.val("");
	            var submenulength=$(thiz).parents('li.customnav-item').find('.ui-sortable li').length;
	            console.log(submenulength)
	            if(submenulength>4){
	                art.dialog({
	                    width:"450px",
	                    title:"温馨提示",
	                    content:"2级子菜单最多开启5个",
	                    lock: true,
	                    ok:true,
	                    cancel:true
	                })
	            }else{
	                art.dialog({
	                    width:"450px",
	                    title:"添加菜单",
	                    content:$("#j-addMenu .form-horizontal:eq(1)")[0],
	                    lock: true,
	                    ok:function(){
	
	                        if(formflag2.form()){
	                            toInsertSecond(thiz,menuVal.val());
	                        }else{
	                            return false;
	                        }
	
	                    },
	                    cancel:true
	                })
	            }
	
			}
	
	
	
	
			//插入到一级菜单中
			function toInsertFirst(val){
				var menu=$("#jmenu>li"),len=menu.length;
				var str = ''
					+'<li class="customnav-item">'
					+'	<div class="clearfix">'
					+'		<span class="pull-left">'
					+'			<i class="iconfont icon-14052230" onclick="toggleSubMenu(this)"></i>'
					+'			<a href="javascript:void(0)" onclick="toShowInfo(this,1)" data-tag="'+len+'" data-pid=""  data-parentname="'+val+'" data-code="1" data-codename="modular_index" >'+val+'</a>'
					+'		</span>'
					+'		<span class="pull-right">'
					+'			<a href="javascript:void(0)" onclick="toAddSubMenu(this)">+ 新增</a>'
					+'			<a href="javascript:void(0)" onclick="toUpdateMenu(this)">修改</a>'
					+'			<a href="javascript:void(0)" onclick="toDelMenu(this)">删除</a>'
					+'		</span>'
					+'	</div>'
					+'<ol class="customnav-sub ui-sortable">'
					+'</ol>'
					+'</li>';
				$(str).appendTo($("#jmenu"));
			}
	
			//插入到二级菜单中
			function toInsertSecond(thiz,val){
	            var parentMenu=$(thiz).parent().siblings(".pull-left").find("a"),
	            	subMenu=$(thiz).parents(".customnav-item").find(".customnav-sub>li"),
	            	sublen=subMenu.length,
	            	parentTag=parentMenu.attr("data-tag");
	            	
				var str = ''
					+'<li class="clearfix">'
					+'	<span class="pull-left">'
					+'		<a href="javascript:void(0)" onclick="toShowInfo(this,2)" data-tag="'+(parentTag+sublen)+'" data-id="" data-pid="'+parentMenu.data("pid")+'" data-subname="'+val+'" data-parentname="'+parentMenu.text()+'" data-code="1" data-codename="modular_index">'+val+'</a>'
					+'	</span>'
					+'	<span class="pull-right">'
					+'		<a href="javascript:void(0)" onclick="toUpdateSubMenu(this)">修改</a>'
					+'		<a href="javascript:void(0)" onclick="toDelSubMenu(this)">删除</a>'
					+'	</span>'
					+'</li>';
				$(str).appendTo($(thiz).parents("li.customnav-item").find("ol.customnav-sub"));
			}
	
			//修改菜单
			function toUpdateMenu(thiz){
	            var menu=$(thiz).parent('.pull-right').siblings(".pull-left").find('a');
	            var submenu=$(thiz).parents('.clearfix').siblings(".customnav-sub").find('li .pull-left a');
	            var menuVal=$("#j-addMenu .form-horizontal:eq(0) input");
	            menuVal.val(menu.text());
	            if(formflag1){
	                formflag1.resetForm();
	            }
				art.dialog({
					width:"450px",
					title:"修改菜单",
					content:$("#j-addMenu .form-horizontal:eq(0)")[0],
	                lock: true,
					ok:function(){
	                    if(formflag1.form()){
	                        menu.text(menuVal.val());
	                        menu.attr("data-parentname",menuVal.val());
	                        submenu.attr("data-parentname",menuVal.val());
	                    }else{
	                        return false;
	                    }
	                },
					cancel:true
				})
			}
	
			//修改菜单
			function toUpdateSubMenu(thiz){
	            var menu=$(thiz).parent('.pull-right').siblings(".pull-left").find('a');
	            var menuVal=$("#j-addMenu  .form-horizontal:eq(1) input");
	            if(formflag2){
	                formflag2.resetForm();
	            }
	            menuVal.val(menu.text());
	
				art.dialog({
					width:"450px",
					title:"修改菜单",
					content:$("#j-addMenu .form-horizontal:eq(1)")[0],
	                lock: true,
					ok:function(){
	                    if(formflag2.form()){
	                        menu.text(menuVal.val());
	                        menu.attr("data-subname",menuVal.val());
	                    }else{
	                        return false;
	                    }
					},
					cancel:true
				})
			}
	
			//删除菜单
			function toDelMenu(thiz){
				art.dialog.confirm("确认删除菜单？",function(){
					$(thiz).closest("li").remove();
				},function(){
	
				});
			}
	
			//删除二级菜单
			function toDelSubMenu(thiz){
				art.dialog.confirm("确认删除菜单？",function(){
					$(thiz).closest("li").remove();
				},function(){
	
				});
			}
	
			//显示二级菜单
			function toggleSubMenu(thiz){
				$(thiz).toggleClass("icon-14052230")
					   .toggleClass("icon-14052229");
				$(thiz).parents("li.customnav-item")
					   .find("ol.customnav-sub")
					   .toggleClass("hide");
			}
	
	
	        //提交数据生成菜单
	        function creatMenu(thiz){
	
	            //$(thiz).attr('disabled',true);
	
	            var menulist=$('#jmenu>li'),menuJson={},subMenu={},i= 0,j= 0,mjson="",mjobj;
	            for(i==0;i<menulist.length;i++){
	                (function(i){
	                    mjson+="{";
	                    if(menulist.eq(i).find(".customnav-sub>li").length){
	
	                        mjson+='"id":'+'"'+menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-pid')+'",';
	                        mjson+='"name":'+'"'+menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-parentname')+'",';
	
	
	                        var sub_menu_lists=menulist.eq(i).find(".customnav-sub>li");
	                        mjson+='"subMenu":[';
	                        for(j=0;j<sub_menu_lists.length;j++){
	                            (function(j){
	                                var type = sub_menu_lists.eq(j).find(".pull-left a").attr('data-code');
	                                mjson+='{"id":'+'"'+sub_menu_lists.eq(j).find(".pull-left a").attr('data-id')+'",';
	                                mjson+='"name":'+'"'+sub_menu_lists.eq(j).find(".pull-left a").attr('data-subname')+'",';
	                                mjson+='"pid":'+'"'+sub_menu_lists.eq(j).find(".pull-left a").attr('data-pid')+'",';
	                                if(type == "1") {
	                            		mjson+='"code":'+'"'+sub_menu_lists.eq(j).find(".pull-left a").attr('data-codename')+'"';
	                            	} else if(type == "2") {
	                            		mjson+='"code":'+'"'+"link_path"+'",';
	                            		mjson+='"link":'+'"'+sub_menu_lists.eq(j).find(".pull-left a").attr('data-codename')+'"';
	                            		
	                            	}
	                                mjson+='},';
	                              
	                            })(j)
	                        }
	                            mjson=mjson.substring(0,mjson.length-1);
	                            mjson+=']';
	                       
	                    }else{
	                    	var type = menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-code');
	                    	mjson+='"id":'+'"'+menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-pid')+'",';
	                        mjson+='"name":'+'"'+menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-parentname')+'",';
	                        mjson+='"subMenu":"",';
	                    	if(type == "1") {
	                    		mjson+='"code":'+'"'+menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-codename')+'"';
	                    	} else if(type == "2") {
	                    		var link = menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-codename');
	                    		if(null == link || '' == link) {
	                    			alertMsg("菜单类型为链接至外部连接的菜单，外部链接必须填写！");
	                    			return;
	                    		}
	                    		mjson+='"code":'+'"'+"link_path"+'",';
	                    		mjson+='"link":'+'"'+menulist.eq(i).children("div.clearfix").find(".pull-left a").attr('data-codename')+'"';
	                    	}
	                    	
	                    }
	                    mjson+="},";
	                })(i)
	            };
	            mjson=mjson.substring(0,mjson.length-1);
	            mjson="["+mjson+"]";
	            mjobj = JSON.parse(mjson);
	            $.ajax({
	            	url:"${ctx }/u/weixin/menu/addOrEdit${ext }",
	            	type : "post",
	            	data: {
	            		customMenuJson : JSON.stringify(mjobj)
	            	},
	            	dataType: "text",
		          	contentType: "application/x-www-form-urlencoded; charset=utf-8",
		          	success: function (data) {
			        	  	var result = eval('('+ data +')');
							if(result.success){
								art.dialog.tips("保存成功！");
							}else{
								alertMsg(result.info);
							}
			          },
			          error: function () {
			        	  alertMsg("服务器繁忙！");
			          }
	            });
	
	//            art.dialog.tips('数据正在提交..', 3);
	
	//             art.dialog({
	//                 content: '提交成功！已经保存在服务器，3秒自动刷新...！',
	//                 icon: 'succeed',
	//                 fixed: true,
	//                 lock: true,
	//                 time: 3
	//             });
	
	//             setTimeout(function(){
	//                 window.location.reload();
	//             },3000)//三秒后自动刷新页面
	        }
	
	
			//展示菜单信息
			function toShowInfo(thiz,id){
				$(thiz).parents("#jmenu")
					   .find("li.clearfix,div.clearfix")
					   .removeClass("active")
					   .end()
					   .end()
					   .parents(".clearfix")
					   .addClass("active");
	
	
				var len = $(thiz).parents(".customnav-item").find(".customnav-sub").children().length;
	
	            var arrtxt=["链接模块","外部链接","链接至模块","链接至外部连接"],
	                    code=$(thiz).attr("data-code"),
	                    codename=$(thiz).attr("data-codename"),
	                    codewordname,
	                    pid=$(thiz).attr('data-pid'),
	                    parentname=$(thiz).attr("data-parentname"),
	                    tag=$(thiz).attr("data-tag");
	                  
	
				if(len !=0 && id==1){//有子级菜单时
	
					$("#jshowinfo").html("<div style='text-align:center;line-height:300px;'>已有子菜单，无法设置动作</div>");
	
				}else if(id==2){
	
	                var     subid=$(thiz).attr("data-id"),
	                        subname=$(thiz).attr("data-subname"),
	                        datastr=pid+"|"+parentname+"|"+subid+"|"+subname+"|"+code+"|"+codename+"|"+tag;
	
	                if(code==1){
	                    for(var key in codeJson){
	
	                        if(codeJson[key].code==codename){
	
	                            codewordname=codeJson[key].name;
	
	                        }
	
	                    }
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datastr+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+$(thiz).attr("data-subname")+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>'+$(thiz).attr("data-parentname")+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>'+arrtxt[2]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>'+arrtxt[0]+'：</th>'
	                            +'			<td>'+codewordname+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	                }else if(code==2){
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datastr+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+$(thiz).attr("data-subname")+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>'+$(thiz).attr("data-parentname")+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>'+arrtxt[3]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>'+arrtxt[1]+'：</th>'
	                            +'			<td>'+codename+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	                }
	
	                $("#jshowinfo").html(str);
	            }else{
	                datastr=pid+"|"+parentname+"|"+code+"|"+codename+"|"+tag;
	
	                if(code==1){
	                    for(var key in codeJson){
	
	                        if(codeJson[key].code==codename){
	
	                            codewordname=codeJson[key].name;
	
	                        }
	
	                    }
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datastr+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+$(thiz).attr("data-parentname")+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>无</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>'+arrtxt[2]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>'+arrtxt[0]+'：</th>'
	                            +'			<td>'+codewordname+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	                }else if(code==2){
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datastr+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+$(thiz).attr("data-parentname")+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>无</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>'+arrtxt[3]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>'+arrtxt[1]+'：</th>'
	                            +'			<td>'+codename+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	                }
	
					$("#jshowinfo").html(str)
	            }
			}
			
			//修改
			function toUpdate(thiz){
	            var table=$(thiz).parents(".table-striped"),
	                menuattr=table.attr('data-str').split('|'),
	                    code,codename;
	
	            if(menuattr.length==7){
	                code=menuattr[4];
	                codename=menuattr[5];
	            }else if(menuattr.length==5){
	                code=menuattr[2];
	                codename=menuattr[3];
	            }
	            var modularList = "";
	            	var custommenus = eval('${customMenuModularJson}');
	            	for(var x in custommenus) {
	            		var modular = '<label><input type="radio" name="module" id="module1" value="" data-name="'+custommenus[x].name+'" data-code="'+custommenus[x].code+'" data-url="'+custommenus[x].url+'"> '+custommenus[x].name+'</label>';
	            		modularList+=modular;
	            	}
	            if(code==1){
	                var str='<form class="form-horizontal" style="margin-top:20px;" data-setstr="'+menuattr.join("|")+'">'
	                        +'<div class="form-group">'
	                        +' <label class="col-sm-3 control-label">菜单类型：</label>'
	                        +'<div class="col-sm-6">'
	                        +'<select class="form-control" onclick="toSelectMenuType(this)">'
	                        +'<option >链接至外部链接</option>'
	                        +'<option selected>链接至模块</option>'
	                        +'</select>'
	                        +'</div>'
	                        +'</div>'
	                        +' <div class="form-group" id="jlink">'
	                        +' <label class="col-sm-3 control-label">外部链接：</label>'
	                        +' <div class="col-sm-6">'
	                        +' <input type="email" class="form-control" id="" placeholder="" >'
	                        +' </div>'
	                        +'  </div>'
	                        +'  <div class="form-group" id="jmodule" style="display:none;">'
	                        +' <label class="col-sm-3 control-label">模块：</label>'
	                        +' <div class="col-sm-6 form-control-static">'
	                        +modularList
	
	                        +'</div>'
	                        +'   </div>'
	                        +'   <div class="form-group">'
	                        +'<div class="col-sm-offset-3 col-sm-9">'
	                        +'  <button type="button" class="btn btn-default" onclick="toSave(this)">保存</button>'
	                        +'  </div>'
	                        +'  </div>'
	                        +'  </form>';
	            }else if(code==2){
	                var str='<form class="form-horizontal" style="margin-top:20px;" data-setstr="'+menuattr.join("|")+'">'
	                        +'<div class="form-group">'
	                        +' <label class="col-sm-3 control-label">菜单类型：</label>'
	                        +'<div class="col-sm-6">'
	                        +'<select class="form-control" onclick="toSelectMenuType(this)">'
	                        +'<option selected>链接至外部链接</option>'
	                        +'<option>链接至模块</option>'
	                        +'</select>'
	                        +'</div>'
	                        +'</div>'
	                        +' <div class="form-group" id="jlink">'
	                        +' <label class="col-sm-3 control-label">外部链接：</label>'
	                        +' <div class="col-sm-6">'
	                        +' <input type="email" class="form-control" id="" placeholder="" value="'+codename+'">'
	                        +' </div>'
	                        +'  </div>'
	                        +'  <div class="form-group" id="jmodule" style="display:none;">'
	                        +' <label class="col-sm-3 control-label">模块：</label>'
	                        +' <div class="col-sm-6 form-control-static">'
	                        +modularList
	                        +'</div>'
	                        +'   </div>'
	                        +'   <div class="form-group">'
	                        +'<div class="col-sm-offset-3 col-sm-9">'
	                        +'  <button type="button" class="btn btn-default" onclick="toSave(this)">保存</button>'
	                        +'  </div>'
	                        +'  </div>'
	                        +'  </form>';
	            }
	
	            $("#j-addOthers").html(str)
				$("#jshowinfo").html($("#j-addOthers").html());
	
	            if(code==1){
	                var radio_index=0;
	
	                $('.form-control-static label').find('input[data-code='+codename+']').attr('checked',true);
	
	            }
	            toSelectMenuType($('.form-horizontal select.form-control'))
			}
			
			//保存
			function toSave(thiz){
	            var datamenu=$(thiz).parents("form.form-horizontal").data("setstr"),
	                    code=parseInt($(thiz).parents(".form-horizontal").find("select option:selected").index()),//这里的code是select选中的类别，刚好和正常的code相反
	                    codename,
	                    codewordname;
	            if(code==0){
	
	                codename=$(thiz).parents(".form-horizontal").find('input[type=email]').val();
	
	            }else if(code==1){
	                codename=$(thiz).parents(".form-horizontal").find('.form-control-static label input[type=radio]:checked').attr('data-code');
	
	
	            }
	
	
	            datamenu=datamenu.split("|");
	            datamenu[(datamenu.length-2)]=codename;
	
	            if(datamenu.length==5){
	
	                if(code==1){
	
	                    for(var key in codeJson){
	
	                        if(codeJson[key].code==codename){
	
	                            codewordname=codeJson[key].name;
	
	                        }
	
	                    }
	                    datamenu[2]=1; //调整模块方式
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datamenu.join("|")+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+datamenu[1]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>无</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>链接至模块</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>链接模块：</th>'
	                            +'			<td>'+codewordname+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	
	                }else if(code==0){
	                    datamenu[2]=2; //调整模块方式
	
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datamenu.join("|")+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+datamenu[1]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>无</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>链接至外部连接</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>外部链接：</th>'
	                            +'			<td>'+codename+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	
	                }
	
	
	
	                $("[data-tag='"+datamenu[4]+"']").attr({
	                    'data-code':datamenu[2],
	                    'data-codename':datamenu[3]
	                })
	
	
	            }else if(datamenu.length==7){
	
	                if(code==1){
	
	                    datamenu[4]=1; //调整模块方式
	
	
	                    for(var key in codeJson){
	
	                        if(codeJson[key].code==codename){
	
	                            codewordname=codeJson[key].name;
	
	                        }
	
	                    }
	
	
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datamenu.join("|")+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+datamenu[3]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>'+datamenu[1]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>链接至模块</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>链接模块：</th>'
	                            +'			<td>'+codewordname+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	
	                }else if(code==0){
	
	                    datamenu[4]=2; //调整模块方式
	
	                    var str = ''
	                            +'<table class="table table-striped table-thleft" data-str="'+datamenu.join("|")+'">'
	                            +'	<tbody>'
	                            +'		<tr>'
	                            +'			<th width="20%">菜单名称：</th>'
	                            +'			<td>'+datamenu[3]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>父级菜单：</th>'
	                            +'			<td>'+datamenu[1]+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>菜单类型：</th>'
	                            +'			<td>链接至外部连接</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>外部链接：</th>'
	                            +'			<td>'+codename+'</td>'
	                            +'		</tr>'
	                            +'		<tr>'
	                            +'			<th>&nbsp;</th>'
	                            +'			<td><a href="javascript:void(0)" class="btn btn-default" onclick="toUpdate(this)">修改</a></td>'
	                            +'		</tr>'
	                            +'	</tbody>'
	                            +'</table>'
	
	                }
	                $("[data-tag='"+datamenu[6]+"']").attr({
	                    'data-code':datamenu[4],
	                    'data-codename':datamenu[5]
	                })
	            }
	
					$("#jshowinfo").html(str);
			};
	
	
	
	
		</script>
		</body>
</html>