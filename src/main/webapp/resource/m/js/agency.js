function ObproductList(){
	this.obproductList = [];
}
ObproductList.prototype.Add = function(obj){
	return this.obproductList.push(obj);
}
ObproductList.prototype.Empty = function(){
	return this.obproductList = [];
}
ObproductList.prototype.Count = function(obj){
	return this.obproductList.length;
}
ObproductList.prototype.Get = function(index){
	if(index > -1 && index < this.obproductList.length){
		return this.obproductList[index];
	}
}
/*ObproductList.prototype.Insert = function(obj,index){
	var pointer = -1;
	if(index === 0){
		this.obproductList.unshift(obj);
	}else if(index == this.obproductList.length){
		this.obproductList.push(obj);
		pointer = index;
	}
	return pointer;
}
ObproductList.prototype.IndexOf = function(obj,startIndex){
	var i = startIndex,pointer = -1;
	while (i<this.obproductList.length){
		if(this.obproductList[i] === obj){
			pointer = i;
		}
		i++;
	}
	return pointer;
}
ObproductList.prototype.RemoveIndexAt = function(obj,startIndex){
	if(index === 0){
		this.obproductList.shift();
	}else if(index == this.obproductList.length-1){
		this.obproductList.pop();
	}
}*/

function extend(obj,extension){
	for(var key in obj){
		extension[key] = obj[key];
	}
}

function Subproduct(){
	this.obproducts = new ObproductList();

}
Subproduct.prototype.AddObproduct = function(product){
	this.obproducts.Add(product)
}
Subproduct.prototype.RemoveObproduct = function(product){
	this.obproducts.RemoveIndexAt(this.Obproducts.IndexOf(obproduct,0));
}
Subproduct.prototype.Notify = function(context){
	var obproductCount = this.obproducts.Count();
	for(var i = 0;i<obproductCount;i++){
		console.log(context)
		if(this.obproducts.Get(i).checked && context) continue;
		this.obproducts.Get(i).Update(context);
		//console.log(i);
	}
}
function Obproduct(){
	this.Update = function(value) {
		this.checked = value;
			if(value){
				this.addDom();
			}else{
				this.delDom();
			}
	}
	this.addDom = function(){
		var thiz;
		var title = $(this).next().text();
		var name = $(this).parents('dd').prev().find('b').text();
		var strHtml = [
				'<tr data-id=',this.id,'>',
				'<td>',name,'</td>',
				'<td>',title,'</td>',
				'<td><input type="text" name="discount" class="form-control input-short-5" value=""></td>',
				'</tr>'
			].join(' ');
		$('#j-productList').append(strHtml);
		
	}
	this.delDom = function(){
		$('[data-id="'+this.id+'"]').remove();
	}
}
function setStandard(){
	this.Update = function(value){

	}
}

$(function(){

	//选择代理产品线
	$('#j-checklist').find('input[data-type="all"]').each(function(){
		var thiz = this;

		extend(new Subproduct(),this);

		this["onclick"] = new Function("this.Notify(this.checked)");

		$(this).parent().next().find('input').each(function(){
			extend(new Obproduct(),this);

			this["onclick"] = function(){
				this.Update(this.checked);
			}
			thiz.AddObproduct(this);
		});
		

	});

	//批量修改基准价
	$('#j-standard').on('input',function(){
		var value = this.value;
		$('#j-productList').find('input').each(function(){
			this.value = value;
		})
	});

	//extend(new Obproduct(),check);
})



