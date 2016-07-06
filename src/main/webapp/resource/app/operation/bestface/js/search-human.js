$(function(){
	//搜索联系人
	var searchHuman = (function(){

		var showHuman = function(keyname){

			$('[data-search="item"]').each(function(){
				var flag,
					search = $(this).find('[data-search="name"]'),
					showLen;

					flag = localSearch(keyname,search.text());
					showLen = $(this).siblings(':visible').length;
				
				if(flag){
					$(this).show();
					$(this).prevAll('h3').show();
				}else{
					$(this).hide();
					if(showLen <=1){
						$(this).prevAll('h3').hide();
					}
				}

		
				
			})
		}

		$('[data-search="true" ]').on('input',function(){
			var keyName = this.value;
				showHuman(keyName);
		})
	})();

});

//本地搜索 
function localSearch(val,txt){
	var val = val || '',
		txt = txt || '',
	  	valArr = val.split(""),
	  	regStr="";
	for(var k=0;k<valArr.length;k++){
		regStr += '(?=.*?'+valArr[k]+')';
	}
	var reg=new RegExp(regStr);
	if(txt.match(reg)){
		return true;
	}
	return false;
}