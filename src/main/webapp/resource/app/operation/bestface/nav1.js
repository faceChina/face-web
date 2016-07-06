void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
<div class="nav-bottom" id="navbar">
  <ul>
    <li data-navicon="index" class="active"><a href="../../../../bestface/freeshop/index2.html"><span></span><p>首页</p></a></li>
    <li data-navicon="order"><a href="../../order/page/order-seller.html"><span></span><p>订单</p></a></li>
    <li data-navicon="goods"><a href="../../../../bestface/freeshop/listshop-sell.html"><span></span><p>商品</p></a></li>
    <li data-navicon="purse"><a href="../../accounts/page/purse.html"><span></span><p>钱包</p></a></li>
    <li data-navicon="set"><a href="../../distribution/page/mine.html"><span></span><p>我的</p></a></li>
  </ul>
</div>
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

