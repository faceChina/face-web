void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!


<script type="text/javascript">
$("#j-scrollToTop").click(function(){
  $(document.body).scrollTop(0);
});
$("#j-scrollToTop").hide();
$(window).scroll(function(){
  var bodyScrollTop = $(document.body).scrollTop();
  if(bodyScrollTop > 300){
    $("#j-scrollToTop").show();
  }else{
    $("#j-scrollToTop").hide();
  }
});
</script>



*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);