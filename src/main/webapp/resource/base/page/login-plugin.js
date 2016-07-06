void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
<div class="login" id="j-login" style="display:none;width:100%">
	<form method="post" action="cart-order.html" id="jform" class="form-login">
  		<div class="list-row list-row-clearborder ">
    		<div class="list-col">
      			<div class="list-inline box-flex"><input type="text" name="username" id="username" class="form-border" placeholder="请填写您的手机号码"></div>
    		</div>
    		<div class="list-col">
      			<div class="list-inline box-flex"><input type="password" name="password" id="password" class="form-border" placeholder="请填写您的密码"></div>
    		</div>
		</div>
		<div style="padding:0 10px;">
			<a href="../../public/page/register.html" class="left">免费注册</a>
			<a href="../../public/page/password-find.html" class="right clr-light">忘记密码？</a>
		<div>
	</form>
</div>
*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);