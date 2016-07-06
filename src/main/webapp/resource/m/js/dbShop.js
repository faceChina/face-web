var app = {};
;(function ($, exports) {
    var tempData = [];
    var proArr = []; //每列属性与其他属性组合的最大可能

    var Prorow = '.J_Prorow';
    var checkbox = '.J_Checkbox';
    var Jtable = '#template';
    var JR = '.J_MapRow';
    var JP = $(Prorow);

    var thlen = 3;//表格列数

    var init = function(){
        //计算每个属性的位置;
        var arr = countProRow();

        proArr = reduceArr(arr);

        thlen += proArr.length;

        //勾选时间绑定
        _bindEvent();

        //存储勾选属性
        setTablePro();

        //set默认勾选
        var flag = _proLen();
        if(flag){
            _addTbtr(comJson(tempData));
            if($('#j-proColor').length === 1){
                setPathPic();
            }
            
        }

        
    }
    //存储勾选属性
    var setTablePro = function(){
        JP.each(function(){
            var index = $(this).data('index');
            $(this).find(checkbox + ':checked').each(function(){
                var key = this.value;

                if(!tempData[index]){
                    tempData[index] = [];
                }
                tempData[index].push(key);
            })
        });
        
    }

    /**
     * 插入表格 _addTbtr function
     *
     * @param  {Object} data 传入HTML数据
     * @returns Object
     */
    var _addTbtr = function (data){
        
        for(var i = 0; i<data.length;i++){
            var tr = _domTbtr(data[i]);//生成对象
            var idd = tr['id'];
            var indexId = getIndexTr();
            var num;

            //插入HTML
            if(indexId.length == 0 || idd>indexId[indexId.length-1]){
                $(Jtable + ' tbody').append(tr['tr']);
            }else if(idd<indexId[0]){
                $(JR).eq(0).before(tr['tr'])
            }else{
                num = countNum(idd,indexId)
                $(JR + '[data-id="'+num+'"]').after(tr['tr']);
            }
            
            
        }
        
        //合并表格
        rowspanTd();
        
    }
    //合并表格
    var rowspanTd = function(){
        var checkedlen = getChechedLen();
        var comArr = [];

            comArr = reduceArr(checkedlen);

            if(comArr.length == 1) return;

            $(JR).each(function(index){

                for(var i=comArr.length-2,j =0;i>=0;i--,j++){
                    var rowTd = $(this).find('td');
                    
                    if((index+1)%comArr[i] == 1 || comArr[i] == 1){

                        var k = rowTd.length-thlen+j;
                            rowTd.eq(k).attr('rowspan',comArr[i]);
                        
                    }else{
                        if(rowTd.length == (thlen-j))
                            rowTd.eq(0).remove();
                    }
                    
                }
            })
    }
    //比较TR索引，返回插入索引位置
    var countNum = function(a,b){
        for(var i = 0;i < b.length-1;i++){
            if(b[i] < a && a < b[i+1]){
                return b[i];
            }
        }
    }
    //获取勾选数组长度
    var getChechedLen = function(){
        var arr = [];
        JP.each(function(){
            var len = $(this).find(checkbox + ':checked').length;
            arr.push(len);
        });
        return arr
    }
    //每列属性与其他属性组合的最大可能
    var countProRow = function(){
        var arr = [];
        JP.each(function(){
            var len = $(this).find(checkbox).length;
            arr.push(len);
        });

        //tempArr = reduceArr(arr);

        return arr;
    }
    /**
     * 返回组合方式数组
     *
     * @param  {arr} arr 
     * @returns arr
     */
    var reduceArr = function(arr){
        var tempArr = [];
        arr.reduceRight(function(a,b){
            tempArr.push(a*b);
            return a*b
        
        },1);

        return tempArr;
    }
    /**
     * 返回TR索引
     *
     * @param  {arr} arr 
     * @returns number
     */
    var trIndex = function(arr){
        var a = 0;      
        for(var i = 0;i<arr.length; i++){
            if(i == (arr.length-1)){
                a +=arr[i];
            }else{
                a += (+arr[i]-1)*proArr[arr.length-2-i]
            }

        }
        return a;
        
    }
    //trIndex([2,2,2]);
    var getIndexTr = function(){
        var arr = [];
        $(JR).each(function(){
            arr.push($(this).data('id'));
        });
        return arr;
    }
    /**
     * 返回表格TR的HTML _domTbtr function
     *
     * @param  {Object} data
     * @returns {
     *     tr: HTML的表格DOM
     *     id: tr对应的索引
     *   }
     */
    var _domTbtr = function (data){
        var data = data;
        var sort = [];
        var typeId = '';
        var rowId;
        var tr = document.createElement('tr');
            tr.className = 'J_MapRow';

        for(var key in data){
            var td = document.createElement('td');

                if(data[key].rowspan) td.setAttribute('rowspan',data[key].rowspan)

                if(data[key].type == 'text'){
                    var span = document.createElement('span');
                    span.innerHTML = data[key].text
                    span.className = 'J_map'+key.replace(':','-');

                    //sort += '-'+
                    sort.push(key.split(':')[1])

                    typeId += key.replace(':','-') + '|'

                    td.appendChild(span);
                    
                }else if(data[key].type == 'input'){
                    var input = document.createElement('input');

                    td.className = key;

                    input.name = data[key].name;
                    input.id = key + '-' + trIndex(getInputKey(sort));
                    input.className = 'J_map'+key;
                    input.setAttribute('data-type',key);
                    input.type = 'text';

                    td.appendChild(input);
                }

                //tr.id = sort;
                tr.appendChild(td);
        }
        
        rowId = trIndex(getInputKey(sort));

        typeId = typeId.substring(0,typeId.length-1);

        tr.setAttribute('data-id',rowId);
        tr.setAttribute('data-type',typeId);

        return {
                tr:tr,
                id:rowId
        }
    }
    var getInputKey = function(arr){
        var indexArr = [];
        for(var i=0,len = arr.length;i<len;i++){
            var self = $('.J_Checkbox[value="sx'+(i+1)+':'+arr[i]+'"]');

            indexArr.push(getInputIndex(self));
        }

        return indexArr;
    }
    var getInputIndex = function(self){

        var index = self.parent().index()+1;

        return index;
    }
    /**
     * 递归，计算出组合方式
     *
     * @param  {arr} arr
     * @returns arr
     */
    var doExchange = function (doubleArrays){
        var len = doubleArrays.length;

        if(len>=2){
            var len1=doubleArrays[0].length;
            var len2=doubleArrays[1].length;
            var newlen=len1*len2;
            var temp=new Array(newlen);
            var index=0;
            for(var i=0;i<len1;i++){
                for(var j=0;j<len2;j++){
                    temp[index]=doubleArrays[0][i]+'|'+
                        doubleArrays[1][j];
                    index++;
                }
            }
            var newArray=new Array(len-1);
            for(var i=2;i<len;i++){
                newArray[i-1]= doubleArrays[i];
            }
            newArray[0]=temp;
            return doExchange(newArray);
        }
        else{
            return doubleArrays[0];
        }
    }
    /**
     * 勾选删除
     *
     * @param  {this} self
     * @param  {number} index
     */
    var delHmtl = function(self,index){
       
        var keyStr = self.value.replace(':','-');
        var inputObj = $(self).closest(Prorow).children().has('input:checked');
        var li = $(self).parent();

        var p = inputObj.eq(0).index();
        var k = li.index();
        var oldlen = thlen;

        $(JR + '[data-type*="'+keyStr+'"]').each(function(){
            if(index == 0){
                $(this).remove();
                return;
            }

            var len = $(this).find('td').length;

            if(p>k){

                var t = '';
                    
                for(var i = 0;i<index;i++){
                    if(thlen-len+i < index || len-oldlen-i > 0){
                        if(i == 0 ){
                            t = $(this).find('td').eq(i);
                        }else{
                            t = t.add($(this).find('td').eq(i));
                        }
                    }
                }

                if(t != ''){
                    oldlen = $(this).next().find('td').length;
                    t.prependTo($(this).next());
                }else{
                    oldlen = thlen;
                }
                
            }           
                $(this).remove();
        })

        rowspanTd();
    }
    /**
     * 删除勾选的数组属性
     *
     * @param  {this} self
     * @param  {number} index
     */
    var delObjData = function(self,index){
        
        var key = self.value;

        tempData[index].splice($.inArray(key,tempData[index]),1);
    }
    /**
     * 存储对象数据
     *
     * @param  {this} self
     * @param  {number} index
     * @returns arr
     */
    var objData = function (self,index){
        var data = [], //存储勾选属性
            
            flag = false;

        var key = self.value;
            

        /**
         * 后期变动处理
         * 如key值不包含input索引，可以在这里做处理
         * 从新改变key值
         *
        */

        data[index] = [key];

        if(!tempData[index]){
            tempData[index] = [key];
        }else{
            tempData[index].push(key);
        }
        
        for(var i= 0,len = tempData.length; i < len;i++){
            if(i != index){
                data[i] = tempData[i];
            }
             
        }

        flag = _proLen();
        
        /*if(!flag) return;

        return comJson(data);*/

        return flag ? comJson(data) : false;
    }
    /**
     * 递归获取组合方式,对象
     *
     * @param  {arr} data
     * @returns arr 表格属性数组
     */
    var comJson = function(data){

        var arrData = [],//存储生成表格数组
            //递归获取组合方式
            ret = doExchange(data);
       
        for(var i = 0; i<ret.length; i++){
            var proObj = {},
                retArr = ret[i].split('|');

            for(var y = 0,len = retArr.length;y < len;y++){
                var keyArr = retArr[y].replace(':');
                

                var text = $(checkbox + '[value^="'+retArr[y]+'"]').next().val();

                proObj[retArr[y]] = {'type':'text','name':retArr[y],'text':text};

            }
            
            proObj['salesPrice'] = {'type':'input','name':'scPrice'};
            proObj['stock'] = {'type':'input','name':'stock'};
            proObj['barCode'] = {'type':'input','name':'barCode'};

            arrData.push(proObj);
        }

        return arrData;
    }
    //编辑修改属性
    var editPro= function(self){
        var proId = $(self).next();
        var key = self.value.replace(':','-');
        var trId = '.J_map' + key;
        var oldValue = proId.val();

        proId.removeAttr('disabled');

        proId.on('input',function(){
            $(trId).text(this.value);
        });

        proId.on('blur',function(){
            if(this.value == ''){
                this.value = oldValue;
                $(trId).text(oldValue);
            }
        });

    }
    //禁止修改属性
    var bantPro = function(self){
        var proId = $(self).next();
        proId.attr('disabled','disabled');
    }

    //判断每项属性是否有勾选
    var _proLen = function (){
        var count = 0;
        JP.each(function(){
            var len = $(this).find(checkbox + ':checked').length;

            if(len > 0) count++;
        })

        if(count == proArr.length) return true;

        return false;
    }
    //图片上传DOM
    var picDom = function(self){
        var tr = document.createElement('tr');
        var td1 = document.createElement('td');
        var td2 = document.createElement('td');
        var div = document.createElement('div');
        var img = document.createElement('img');

        td1.className = 'J_map'+$(self).val().replace(':','-');

        var key = $(self).val().split(':')[1];

        var valtext = $(self).next().val();

        td1.innerHTML = valtext;
        div.className = 'btn-upload';
        img.name = 'itemFile';
        img.width = "100";
        img.height = "100";
        img.className = 'itemFile';
        img.setAttribute('data-id',key);
        img.src = '/resource/m/img/add100X100.jpg';
        //img.src = '../img/lp/add100X100.jpg';

        td2.appendChild(div);
        div.appendChild(img);

        tr.appendChild(td1);
        tr.appendChild(td2);

        $('#template2 tbody')[0].appendChild(tr);
        
        imgUpload($(img));

    }
    //删除图片
    var delPicDom = function(self){
        var classId = 'J_map'+$(self).val().replace(':','-');
        //console.log(classId)
        $('#template2 tbody tr').each(function(){
            if($(this).has('.'+classId).length>0){
                $(this).remove();
            }
        })
    }

    //事件绑定
    var _bindEvent = function (){

        //勾选属性
        $(checkbox).on('click',function(){

            //获取UL的data-index属性
            var proId = $(this).closest(Prorow).data('index');
            var obj = false;

            //勾选与取消
            if($(this).is(':checked')){

                obj = objData(this,proId);

                editPro(this);

                if(obj){
                    _addTbtr(obj);
                }

            }else{
                delObjData(this,proId);
                delHmtl(this,proId);
                bantPro(this);
            }

        });

        //勾选颜色
            $('#j-proColor .J_Checkbox').on('click',function(){
                if($(this).is(':checked')){
                    picDom(this);

                }else{
                    delPicDom(this);
                }       
            })
    }
    //默认勾选图片
    var setPathPic = function(){
        $('.J_Checkbox[value^="sx1"]:checked').each(function(){
            picDom(this);
        })
    }
    //获取JSON
    var getJson = function(){
        var shopArr = [];

        $(JR).each(function(){
            var tdId = $(this).find('[class^="J_map"]');
            var obj = {};
                obj['id'] = $(this).data('index') || '';
            var skuProperties = [];
            var popName = [];
                tdId.each(function(index){

                    //console.log(index);

                    var key = this.className.replace('J_map','');
                    var keyName = key.split('-');

                    if(index === 0){
                        //console.log(keyName[1])
                        var src = $('img[data-id="'+keyName[1]+'"]').data('src');
                        if(src&&src.indexOf('path=')!=-1){
                            console.log(src)
                            src=src.substring(src.indexOf('path=')+5);
                        }
                        obj['picturePath'] = src;
                        console.log(JSON.stringify(obj));
                        
                    }
                        

                    if(this.tagName == 'INPUT'){
                        var val = this.value;
                        if(keyName[0] == 'salesPrice') val = +val*100;
                        obj[keyName[0]] = val;
                    }
                });
                var arr = $(this).data('type').split('|');

                arr.forEach(function(a){
                    var b = a.split('-');
                    var c = a.replace('-',':');

                    popName += $('.J_Checkbox[value="'+c+'"]').next().val() + ';'
                    skuProperties += b[1] + ';'
                })

                obj['skuProperties'] = skuProperties;
                obj['name'] = popName;

                shopArr.push(obj);

        });

        //console.log(JSON.stringify(shopArr))
        return JSON.stringify(shopArr);

    }

    //JSON数据排序
    var sortJson = function(data){

        var data = JSON.parse(data);
        
        var arr = new Array(data.length);

        data.forEach(function(obj){
            var keyArr = obj.skuProperties.split(';')
            var keyStr = '';
            
            for(var i = 0,len = keyArr.length-1;i<len;i++){
                keyStr += 'sx' + (i+1) + '-'+ keyArr[i]  + '|';
            }
            
            keyStr = keyStr.substring(0,keyStr.length-1);

            var index = $('.J_MapRow[data-type="'+keyStr+'"]').index();
  
            arr[index] = obj;
        });


        return arr;
    }

    //设置Json
    //
    var setJson = function(data){
        
        if(data == '') return;
        
        var arr = sortJson(data);

        arr.forEach(function(obj,i){
            
            if(obj.id){
                $(JR).eq(i).data('index',obj.id);
            }
            $(JR).eq(i).find('[class^="J_mapsx1"]').each(function(){
  
                if(!obj['picturePath']) return;

                var dataId = this.className.split('-')[1];
                
                var imgDom = $('.itemFile[data-id="'+ dataId +'"]');

                imgDom.attr({
                    'src': ROOT_PICURL + obj['picturePath'],
                    'data-src':obj['picturePath'],
                    });
            })
            $(JR).eq(i).find('input[class^="J_map"]').each(function(){
                var key = $(this).parent()[0].className;
                //console.log(key)
                var val = obj[key];
                if(key == "salesPrice") var val = val/100;
                this.value = val
    
            });

        });
        
    }

    var dbShop = {
        init: init,
        getJson: getJson,
        setJson: setJson
    }

    app.db = dbShop;

})(jQuery,app);


;(function (window, jQuery, undefined) {

    function init(){
        _bindEvent();
    }

    function _bindEvent(){
        $('.shop-amend .info button').on('click',function(){
            var key = $(this).data('id');
            if(key == 'price'){
                $('[data-id="salesPrice"]').show();
            }else if(key == 'total'){
                $('[data-id="stock"]').show();
            }
        });

        $('.amend-item button').on('click',function(){
            var type = $(this).data('type');
            if(type == 'ok'){
                var idd = $(this).parent().data('id');
                var text = $(this).prev().val();

                if(text == '' || isNaN(+text)){
                    text = 0;
                }

                $('.J_map'+idd).val(text);
                $(this).parent().hide();

                if(idd == 'salesPrice'){
                    $('#j-price').val(text);
                }else if(idd == 'stock'){
                    setToal();
                }

            }else if(type == 'no'){
                $(this).parent().hide();
            }

            $(this).prev().val('')
        });

        $('#template').on('input change','.J_mapsalesPrice',function(){

            var price = minPrice();
            $('#j-price').val(price);

        });

        function setToal(){
            var total = shopTotal();
                
            $('#j-total').val(total);

        }

        $('#template').on('input change','.J_mapstock',function(){

            setToal();

        });
    }

    function shopTotal(){
        var total = 0;
        $('.J_mapstock').each(function(){

            if(this.value == '' || isNaN(+this.value)){
                total += 0;
            }else{
                total += +this.value;
            }
        });

        return total;
    }

    function minPrice(){
        var arr = [];
        var price = 0;
        $('.J_mapsalesPrice').each(function(){
            if(this.value == '' || isNaN(+this.value)){
                price = 0;
            }else{
                price = +this.value;
            }
            arr.push(price);
        });

        return arr.reduce(function(a,b){

            return a > b ? b : a;
        });

        //return arr;
    }

    app.shopAmend = {
        init:init
    }

})(window, jQuery);

app.shopAmend.init();

    //图片上传
    var uploadPic = (function  (){
    var nub = 6;
    var htmlDom = function(){
            return [
                       '<li class="addimg" title="',nub,'" >',
                       '<input type="hidden" target="name" name="goodImgs[',nub,'].position" value="'+nub+'">',
                       '<input type="hidden" target="path" name="goodImgs[',nub++,'].url">',
                       '<img name="goodFile" src="/resource/m/img/add100X100.jpg" alt="" class="" data-src="">',
                      ' <span class="action" style="display:none;"><i data-name="delete">删除</i></span>',
                      '</li>'
                      ].join('');
        };
        var leftAction = function(thiz){
            var liDom = $(thiz).closest('li');
            var prevDom = liDom.prev();
            prevDom.before(liDom);
        };
        var rightAction = function(thiz){
            
            var liDom = $(thiz).closest('li');
            var prevDom = liDom.next();
            prevDom.after(liDom);
        };

        
         //图片添加
        var addgoodsImg = function (thiz,response){
        
            var self = this;
            var el = $('#j-addpic');
            var elclass = thiz.closest('li');
            thiz.attr('src',ROOT_PICURL+response.source);
            if(elclass.attr('title')=='0'){
                $('#picpathvalid1').val(response.path);
                $('#j-addpic').prev('label[for="picpathvalid1"]').remove();
                
            }
            elclass.find('input[target="path"]').val(response.path);
            if(elclass.hasClass('addimg')){
                elclass.find('.action').show();
            }
            if(el.find('li').length <5 && el.find('.addimg').length==0){
                $('input[name="userfile"]').parent().remove();
                var str = htmlDom();
                el.find('li:last').after(str);
                $("img[name='goodFile']").each(function(){
                    imgUpload($(this));
                });
            }
        };
        /*图片删除*/
    var delLi = function (thiz){
            var el = $('#j-addpic');
            art.dialog.confirm("确定删除？", function() {
                $(thiz).closest('li').find("img").attr("src","/resource/m/img/add100X100.jpg");
                $(thiz).closest('li').find("span").attr("style","display:none;");
                $(thiz).closest('li').find('input[target="path"]').val("");
                $(thiz).closest('li').find('input[target="id"]').val("");
                if($(thiz).closest('li').attr('title')=='0'){
                    $('#picpathvalid1').val("");
                }
                $("img[name='goodFile']").add("img[name='itemFile']").each(function(){
                    imgUpload($(this));
                });
            }, function() {
                return true;
            });
        };
        
        
        return {
            addgoodsImg:addgoodsImg,
            delLi:delLi,
            leftAction:leftAction,
            rightAction:rightAction
        };
    })();

    $(function(){
            $('#j-addpic').on('click','i',function(){
            var num = $(this).index();
            uploadPic.delLi(this);
        });
    });



//商品数量验证
function stockValid(){
    var sizeLen = $('#j-proSize input[type="checkbox"]:checked').length
    var i = 0;
    var b = 0;
    $('#template input[name="stock"]').each(function(index){
        if(this.value >0) i++;
        
        if(!((index+1)%sizeLen)){
            if(i == 0){
                b++;
            }
            i = 0;
        }
    });
    if(b>0) return false;

    return true;
}

//商品价格验证
function priceValid(){
    var proPrice = parseFloat($('#marketPriceYuan').val());
    var b = 0;
    $('.J_mapsalesPrice').each(function(index){
        if(parseFloat(this.value) > proPrice){
            b++;
        }   
    });
    if(b>0) return false;
    return true;
}
    //图片验证
function picpathvalid(){
    if($('#picpathvalid1').val() != ''){
        return true;
    }
    return false;
}


//商品属性尺码验证
function checkpop(){
    var b = 0;
    var c = 0;
    $('#j-proColor input:checked').each(function(){
        if(this.value != ''){
            b++;
        }
    });
    $(' #j-proSize input:checked').each(function(){
        if(this.value != ''){
            c++;
        }
    });

    return b>0 && c>0? true:false;
}

// 商品的数量验证   
jQuery.validator.addMethod("stockvalid", function(value, element) {
  return this.optional(element) || stockValid(element);   
}, "请输入商品的数量");
// 商品价格验证   
jQuery.validator.addMethod("pricevalid", function(value, element) {
  return this.optional(element) || priceValid(element);   
}, "商品价格不能高于市场价");
 // 条形码验证   
jQuery.validator.addMethod("codeValid", function(value, element) {
  return this.optional(element) || /^([a-z0-9A-Z!@#$_])*?$/.test(value); 
}, "条形码输入错误");
 //商品属性尺码验证
jQuery.validator.addMethod("picpathvalid", function(value, element) {   
    return picpathvalid(); 
}, "请上传商品首图");
// 
jQuery.validator.addMethod("checkpop", function(value, element) {
      return checkpop(); 
}, "请勾选商品属性与尺码");