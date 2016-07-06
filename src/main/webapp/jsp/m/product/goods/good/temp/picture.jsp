<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 商品图片部分 -->
<tr>
	<th style="vertical-align:top"><b class="clr-attention">*</b>商品图片：</th>
	<td>
		<div class="col-md-12 table-help">
			<p>提示：</p>
			<p>
				1.本地图片大小不能超过<span class="fontcor-red">2M</span>
			</p>
			<p>2.本类目下您最多可以上传<span class="fontcor-red">5张</span>图片。</p>
			<p>
				3.图片尺寸大小<span class="fontcor-red">640*640px</span>
			</p>
			<c:choose>
		    	<c:when test="${not empty goodImgs['0']}">
					<input type="hidden"  id="picpathvalid1" value="true" validate="{picpathvalid:true}">
				</c:when>
				 <c:otherwise>
				 	<input type="hidden"  id="picpathvalid1"  validate="{picpathvalid:true}">
				 </c:otherwise>
			</c:choose>
		    <ul class="details-addimg" id="j-addpic">
		    	<c:choose>
		    		<c:when test="${not empty goodImgs['0']}">
						<li class="addimg" title="0">
							<input type="hidden" target="id" name="goodImgs['${goodImgs['0'].position}'].id" value="${goodImgs['0'].id}">
				  			<input type="hidden" target="name" name="goodImgs['${goodImgs['0'].position}'].position" value="${goodImgs['0'].position}">
				  			<input type="hidden" target="path" name="goodImgs['${goodImgs['0'].position}'].url" value="${goodImgs['0'].url}">
				  			<img name="goodFile" src="${picUrl}${goodImgs['0'].zoomUrl}" alt="" class="goodFile" data-src="">
				  			<span class="action" style=""><i data-name="delete">删除</i></span>
				  		</li>
				  </c:when>
				  <c:otherwise>
				  		<li class="addimg" title="0">
				  			<input type="hidden" target="name" name="goodImgs['0'].position" value="0">
				  			<input type="hidden" target="path" name="goodImgs['0'].url">
				  			<img name="goodFile" src="${resourcePath}img/add100X100.jpg" alt="" class="goodFile" data-src="img/lp/goods-1.jpg">
				  			<span class="action" style="display:none;"><i data-name="delete">删除</i></span>
				  		</li>
				  </c:otherwise>
		    	</c:choose>
		    	<c:choose>
		    		<c:when test="${not empty goodImgs['1']}">
						<li class="addimg" title="1">
				  			<input type="hidden" target="id" name="goodImgs['${goodImgs['1'].position}'].id" value="${goodImgs['1'].id}">
				  			<input type="hidden" target="name" name="goodImgs['${goodImgs['1'].position}'].position" value="${goodImgs['1'].position}">
				  			<input type="hidden" target="path" name="goodImgs['${goodImgs['1'].position}'].url" value="${goodImgs['1'].url}">
				  			<img name="goodFile" src="${picUrl}${goodImgs['1'].zoomUrl}" alt="" class="goodFile" data-src="">
				  			<span class="action" style=""><i data-name="delete">删除</i></span>
				  		</li>
				  </c:when>
				  <c:otherwise>
  						<li class="addimg" title="1">
				  			<input type="hidden" target="name" name="goodImgs['1'].position" value="1">
				  			<input type="hidden" target="path" name="goodImgs['1'].url">
				  			<img name="goodFile" src="${resourcePath}img/add100X100.jpg" alt="" class="goodFile" data-src="img/lp/goods-1.jpg">
				  			<span class="action" style="display:none;"><i data-name="delete">删除</i></span>
				  		</li>
				  </c:otherwise>
		    	</c:choose>
		    	<c:choose>
		    		<c:when test="${not empty goodImgs['2']}">
					  	<li class="addimg" title="2">
				  			<input type="hidden" target="id" name="goodImgs['${goodImgs['2'].position}'].id" value="${goodImgs['2'].id}">
				  			<input type="hidden" target="name" name="goodImgs['${goodImgs['2'].position}'].position" value="${goodImgs['2'].position}">
				  			<input type="hidden" target="path" name="goodImgs['${goodImgs['2'].position}'].url" value="${goodImgs['2'].url}">
				  			<img name="goodFile" src="${picUrl}${goodImgs['2'].zoomUrl}" alt="" class="goodFile" data-src="">
				  			<span class="action" style=""><i data-name="delete">删除</i></span>
				  		</li>
				  </c:when>
				  <c:otherwise>
 							<li class="addimg" title="2">
				  			<input type="hidden" target="name" name="goodImgs['2'].position" value="2">
				  			<input type="hidden" target="path" name="goodImgs['2'].url">
				  			<img name="goodFile" src="${resourcePath}img/add100X100.jpg" alt="" class="goodFile" data-src="img/lp/goods-1.jpg">
				  			<span class="action" style="display:none;"><i data-name="delete">删除</i></span>
				  		</li>
				  </c:otherwise>
		    	</c:choose>
		    	<c:choose>
		    		<c:when test="${not empty goodImgs['3']}">
  						<li class="addimg" title="3">
				  		  	<input type="hidden" target="id" name="goodImgs['${goodImgs['3'].position}'].id" value="${goodImgs['3'].id}">
				  			<input type="hidden" target="name" name="goodImgs['${goodImgs['3'].position}'].position" value="${goodImgs['3'].position}">
				  			<input type="hidden" target="path" name="goodImgs['${goodImgs['3'].position}'].url" value="${goodImgs['3'].url}">
				  			<img name="goodFile" src="${picUrl}${goodImgs['3'].zoomUrl}" alt="" class="goodFile" data-src="">
				  			<span class="action" style=""><i data-name="delete">删除</i></span>
				  		</li>
				  </c:when>
				  <c:otherwise>
				  		<li class="addimg" title="3">
				  			<input type="hidden" target="name" name="goodImgs['3'].position" value="3">
				  			<input type="hidden" target="path" name="goodImgs['3'].url">
				  			<img name="goodFile" src="${resourcePath}img/add100X100.jpg" alt="" class="goodFile" data-src="img/lp/goods-1.jpg">
				  			<span class="action" style="display:none;"><i data-name="delete">删除</i></span>
				  		</li>
				  </c:otherwise>
		    	</c:choose>
		    	<c:choose>
		    		<c:when test="${not empty goodImgs['4']}">
					  	<li class="addimg" title="4">
				  			<input type="hidden" target="id" name="goodImgs['${goodImgs['4'].position}'].id" value="${goodImgs['4'].id}">
				  			<input type="hidden" target="name" name="goodImgs['${goodImgs['4'].position}'].position" value="${goodImgs['4'].position}">
				  			<input type="hidden" target="path" name="goodImgs['${goodImgs['4'].position}'].url" value="${goodImgs['4'].url}">
				  			<img name="goodFile" src="${picUrl}${goodImgs['4'].zoomUrl}" alt="" class="goodFile" data-src="">
				  			<span class="action" style=""><i data-name="delete">删除</i></span>
				  		</li>
				  </c:when>
				  <c:otherwise>
						<li class="addimg" title="4">
				  			<input type="hidden" target="name" name="goodImgs['4'].position" value="4">
				  			<input type="hidden" target="path" name="goodImgs['4'].url">
				  			<img name="goodFile" src="${resourcePath}img/add100X100.jpg" alt="" class="goodFile" data-src="img/lp/goods-1.jpg">
				  			<span class="action" style="display:none;"><i data-name="delete">删除</i></span>
				  		</li>
				  </c:otherwise>
		    	</c:choose>
				</ul>
		</div>
	</td>
</tr>