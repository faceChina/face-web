void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
<div class="col-100 buycat-btn">
	<div class="col-50"  onclick="location.href='../cart/cart.html'">
		<button class="btn-buydanger" type="button">加入购物车</button>
	</div>
	<div class="col-50">
		<a class="btn-buydanger" href="../cart/cart-order.html" >立即购买</a>
	</div>
</div>
*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);