//图片上传
    var uploadPic = (function  (){
    var nub = 6,
        picSrc = '../img/add-pic.jpg';
   /* var htmlDom = function(){
        return [
                '<li class="addimg" title="',nub,'" >',
                   '<input type="hidden" target="id" name="mainFile[',nub,'].id">',
                   '<input type="hidden" target="name" name="mainFile[',nub,'].name">',
                   '<input type="hidden" target="path" name="mainFile[',nub++,'].path">',
                   '<img name="goodFile" src="../img/add-backpic.jpg" alt="" class="" data-src="">',
                   '<span class="action" style="display:none;"><i data-name="delete">删除</i><i data-name="link">链接</i></span>',
                '</li>'
                  ].join('');
        };*/
        var setPicSrc = function(src){
            picSrc = src;
        }
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
            //var el = $('#j-addpic');
            var elclass = thiz.closest('li');
            thiz.attr('src','/any/files/showImgs.htm?filePath='+response.filePath+'&zoomImgSize=640_640&inputName=goodFile');
            elclass.find('input[target="id"]').val(response.fileId);
            elclass.find('input[target="name"]').val(response.fileName);
            elclass.find('input[target="path"]').val(response.filePath);
            
            elclass.find('.action').show();
            /*if(elclass.hasClass('addimg')){
            
                elclass.removeClass('addimg');
                elclass.find('.action').show();
            }*/
            /*if(el.find('li').length <5 && el.find('.addimg').length==0){
               // $('input[name="userfile"]').parent().remove();
                var str = htmlDom();
                el.find('li:last').after(str);
                $("img[name='goodFile']").each(function(){
                    imgUpload($(this));
                });
            }*/
            //getPicArr();
        };
        /*图片删除*/
    var delLi = function (thiz){
            var liDom = $(thiz).closest('li');
            liDom.find('img').attr('src',picSrc);
            
            liDom.find('.action').hide();
            //$('input[name="userfile"]').parent().remove();
            /*if(el.find('.addimg').length==0){
                var str = htmlDom();
                $('#j-addpic').find('li:last').after(str);
            }*/
            /*$("img[name='goodFile']").add("img[name='itemFile']").each(function(){
                imgUpload($(this));
            });*/
        };
        
        return {
            addgoodsImg:addgoodsImg,
            delLi:delLi,
            leftAction:leftAction,
            rightAction:rightAction,
            setPicSrc:setPicSrc
        };
    })();