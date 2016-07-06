void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
<link rel="stylesheet" type="text/css" href="css/reset.css">
<link rel="stylesheet" type="text/css" href="css/public.css">
<script type="text/javascript" src="../base/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../base/js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="../base/js/artDialog/iframeTools.js"></script>
<link rel="stylesheet" type="text/css" href="../base/js/artDialog/skins/myself.css">

<script type="text/javascript" src="../base/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="../base/js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="../base/js/validate/additional-methods.js"></script>
<script type="text/javascript" src="../base/js/validate/additional-methods-new.js"></script>
<script type="text/javascript" src="../base/js/validate/messages_zh.js"></script>

*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);