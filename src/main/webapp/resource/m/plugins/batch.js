	function addSingle(str) {
		$(str).appendTo($("#template"));
	}
	/*删除*/
	function del(el) {
		art.dialog.confirm('确认删除？', function() {
			$(el).parent("td").parent("tr").remove();
		}, function() {
			//art.dialog.tips('执行取消操作');
			return true;
		});
	}
	/*编辑*/
	function editor(el) {
		var tr = $(el).parent("td").parent("tr");
		tr.find("input").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);
		tr.find("img").css({
			display : "none"
		});
		tr.find(".uploadImg").css({
			display : "block"
		});
		tr.find("select").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);
		tr.find(".addLinkToTemp").attr("disabled", false);
		tr.find(".addLinkToPro").attr("disabled", false);
		tr.find(".addLinkToOut").attr("disabled", false);
		$(el).css({
			display : "none"
		});
		tr.find(".btn-save").css({
			display : "inline-block"
		});
	}
	/*保存*/
	function save(el,index) {
		art.dialog.confirm('你确定要保存？', function () {
		    //art.dialog.tips('执行确定操作');
			var tr = $(el).parent("td").parent("tr");
			tr.find("input").css({
				border : "none"
			}).attr("disabled", true);
			tr.find("img").css({
				display : "inline-block"
			});
			tr.find(".uploadImg").css({
				display : "none"
			});
			tr.find("select").css({
				border : "none"
			}).attr("disabled", true);
			tr.find(".addLinkToTemp").attr("disabled", true);
			tr.find(".addLinkToPro").attr("disabled", true);
			tr.find(".addLinkToOut").attr("disabled", true);
			$(el).css({
				display : "none"
			});
			tr.find(".btn-editor").css({
				display : "inline-block"
			});
			alert("保存");
		}, function () {
		    //art.dialog.tips('执行取消操作');
		});
	}
	/*全部编辑*/
	function editorAll() {
		$("#template").find("input").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);
		$("#template").find("img").css({
			display : "none"
		});
		$("#template").find(".uploadImg").css({
			display : "block"
		});
		$("#template").find("select").css({
			border : "1px solid #ccc"
		}).attr("disabled", false);
		$("#template").find(".addLinkToTemp").attr("disabled", false);
		$("#template").find(".addLinkToPro").attr("disabled", false);
		$("#template").find(".addLinkToOut").attr("disabled", false);
		$("#template").find(".btn-editor").css({
			display : "none"
		});
		$("#template").find(".btn-save").css({
			display : "inline-block"
		});
	}
	/* 保存fun*/
	function funSaveAll(){
		$("#template").find("input").css({
				border : "none"
			}).attr("disabled", true);
			$("#template").find("img").css({
				display : "inline-block"
			});
			$("#template").find(".uploadImg").css({
				display : "none"
			});
			$("#template").find("select").css({
				border : "none"
			}).attr("disabled", true);
			$("#template").find(".addLinkToTemp").attr("disabled", true);
			$("#template").find(".addLinkToPro").attr("disabled", true);
			$("#template").find(".addLinkToOut").attr("disabled", true);
			$("#addTemp").attr("disabled", true);
			$("#template").find(".btn-editor").css({
				display : "inline-block"
			});
			$("#template").find(".btn-save").css({
				display : "none"
			});
	}

	/*全部保存*/
	function saveAll() {
		art.dialog.confirm('你确定要保存？', function () {
		    funSaveAll();
		}, function () {
		});
	}

	/*上移*/
	function up(el) {
		//var tbody = $(el).parent("td").parent("tr").parent("tbody");
		var tr = $(el).parent("td").parent("tr");
		var index = tr.index();
		if (index != 0) {
			tr.insertBefore(tr.prev());
		} else {
			return false;
		}
	}

	/*下移*/
	function down(el) {
		var tbody = $(el).parent("td").parent("tr").parent("tbody");
		var tr = $(el).parent("td").parent("tr");
		var len = tbody.find("tr").length;
		var index = tr.index();
		if (index != len - 1) {
			tr.insertAfter(tr.next());
		} else {
			return false;
		}
	}
$(document).ready(function() {

});

