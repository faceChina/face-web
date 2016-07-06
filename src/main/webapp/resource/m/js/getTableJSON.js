jQuery.fn.extend({
			getJSONNormal : function() {
				var arr=[];
				var obj2={};
				$(this).find("tbody").find("tr").each(function() {
						var trIndex = $(this).index();
						var obj={};
						$(this).find("td").each(function() {
							if($(this).find("input[type!='radio'][type!='checkbox']").length>0){
								var len=$(this).find("input[type!='radio'][type!='checkbox']").length;
								for(var i=0;i<len;i++){
									var input = $(this).find("input").eq(i);
									var name = input.attr("name");
									var val = input.val();
									obj[name]=val;
								}
							}else if($(this).find("input:radio:checked").length>0){
								var input = $(this).find("input:checked");
								var name = input.attr("name");
								var val = input.val();
								obj[name]=val;
							}else if($(this).find("input:checkbox:checked").length>0){
								var val="";
								$(this).find("input:checkbox:checked").each(function(){
									val+=$(this).val();
								});
								var input = $(this).find("input:checkbox:checked");
								var name = input.attr("name");
								obj[name]=val;
							}else if($(this).find("select option:selected").length>0){
								var name = $(this).find("select").attr("name");
								var val = $(this).find("select option:selected").text();
								obj[name]=val;
							}
						});
						//arr.push(obj);
						obj2[trIndex]=obj;
					});
					console.log(JSON.stringify(obj2))
			},
	
		/*getJSON:function(){
			var len=0;	//总行数
			var arr=[];	
			$(this).find("tbody").find("tr").each(function() {
					//第N行第1列
					var name1=$(this).find("td").eq(0).find("input").attr("name");
					var val1=$(this).find("td").eq(0).find("input").val();
					//第N行第2列
					var name2=$(this).find("td").eq(1).find("input").attr("name");
					var val2=$(this).find("td").eq(1).find("input").val();
					
					//根据第N行第3列中表单的数量及索引值，去获取第N行第4，5列的值并配对，并计算总行数
					$(this).find("td").eq(2).find("input").each(function(){
						if($(this).val()!=0){
							//console.log(len);
							var index=$(this).index();
							//console.log(index);
							
							//第N行第4列
							var name4=$(this).parent("td").next().find("input").eq(index).attr("name");
							var val4=$(this).parent("td").next().find("input").eq(index).val();
							
							//第N行第5列
							var name5=$(this).parent("td").next().next().find("input").eq(index).attr("name");
							var val5=$(this).parent("td").next().next().find("input").eq(index).val();
							
							//第N行第3列
							var name3=$(this).attr("name");
							var val3=$(this).val();
							
							//加入对象
							var obj={};
							obj[name1]=val1;
							obj[name2]=val2;
							obj[name3]=val3;
							obj[name4]=val4;
							obj[name5]=val5;
							
							arr[len]=obj;
							len++;
						}
					});
			});	
			console.log(JSON.stringify(arr))
		}*/
	
	getJSONSpecial:function(num){
			//var len=0;	//总行数
			var arr=[];
			//var obj2={};
			$(this).find("tbody").find("tr").each(function() {
				var obj={};
				$(this).find("td").each(function(){
					$(this).find("input").each(function(){
						var name=$(this).attr("name");
						var val=$(this).val();
						obj[name]=val;
						arr.push(obj)
					});
					
				});
				
				//var l=$(this).find("td").length;
				/*$(this).find("td").eq(num-1).find("input").each(function(){
					if($(this).val()!=0){
						var index=$(this).index();
						var obj={};
						
						$(this).parent("td").siblings().each(function(){
							var l2=$(this).find("input").length;
							if(l2<=1){
								var name=$(this).find("input").attr("name");
								var val=$(this).find("input").val();
								obj[name]=val;
							}else{
								var name=$(this).find("input").eq(index).attr("name");
								var val=$(this).find("input").eq(index).val();
								obj[name]=val;
							}
						});
						
						var name=$(this).attr("name");
						var val=$(this).val();
						
						obj[name]=val;
						arr[len]=obj;
						//obj2[index]=obj;
						len++;
					}
				});*/
			});	
			
			console.log(JSON.stringify(arr))
		},
		showJSON:function(str){
			var obj=JSON.parse(str);
			console.log(obj)
		}
});