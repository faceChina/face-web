void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
<link rel="stylesheet" type="text/css" href="../../../styles/public.css">

<script type="text/javascript" src="../../../../base/js/jquery-2.0.3.min.js"></script>

<script type="text/javascript" src="../../../../base/js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="../../../../base/js/artDialog/iframeTools.js"></script>
<link rel="stylesheet" type="text/css" href="../../../../base/js/artDialog/skins/myself.css">

<script type="text/javascript" src="../../../../base/js/slideout-jquery.js"></script>
<script type="text/javascript" src="../../../../base/js/tab-jquery.js"></script>

<script type="text/javascript" src="../../../js/addel.js"></script>
<script type="text/javascript" src="../../../js/addel-radius.js"></script>
<script type="text/javascript" src="../../../js/form-submit.js"></script>
<script type="text/javascript" src="../../../js/onoff.js"></script>

<script type="text/javascript" src="../../../js/validata/jquery.validate.min.js"></script>
<script type="text/javascript" src="../../../js/validata/additional-methods.js"></script>
<script type="text/javascript" src="../../../js/mob-public.js"></script>

*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);