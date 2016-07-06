void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
      <footer>
<div class="m-nav-bottom m-nav-bottom-fixed" id="navbar">
  <ul>
    <li data-navicon="index" class="active"><a href="../../shop2/index.html"><span></span><p>首页</p></a></li>
    <li data-navicon="cart"><a href="../../../cart/page/cart.html"><span></span><p>购物车</p></a></li>
    <li data-navicon="im"><a href="../../../im/page/im.html"><span></span><p>聊天</p></a></li>
    <li data-navicon="personal"><a href="../../../personal/page/index.html"><span></span><p>我的</p></a></li>
  </ul>
</div>
</footer>
<script type="text/javascript">
	//导航背景定位
	function navbar(str){
		$("#navbar").find("[data-navicon]").removeClass("active");
		$("[data-navicon='"+str+"']").addClass("active");
	};
</script>
*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);