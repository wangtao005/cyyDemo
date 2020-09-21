//设置cookie
function setCookie(name, value, day) {
	var date = new Date();
	date.setDate(date.getDate() + day);
	document.cookie = name + '=' + value + ';expires=' + date;
}
//获取cookie
function getCookie(name) {
	var reg = RegExp(name + '=([^;]+)');
	var arr = document.cookie.match(reg);
	if (arr) {
		return arr[1];
	} else {
		return '';
	}
}
//删除cookie
function delCookie(name) {
	setCookie(name, null, -1);
}

function __CreateJSPath(js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}
var bootPATH = __CreateJSPath("jquery.min.js");
var rootPath = bootPATH.substring(0,bootPATH.indexOf("/admin/dist/"));

/**
 * 表格载入lodding
 * @param id
 * @returns
 */
function showTableLoddingBar(tableId){
	var table = $("#"+tableId);
	table.css("position","relative");
	table.append('<div class="d-flex justify-content-center loadding_c" style="position:absolute;top:40px;left:0px;right:0px;z-index:2"><div class="spinner-border" role="status"><span class="sr-only">Loading...</span></div></div>');
}
/**
 * 隐藏lodding
 * @param tableId
 * @returns
 */
function hideTableLoddingBar(tableId){
	var table = $("#"+tableId);
	table.css("position","inherit");
	if($("#"+tableId+" .loadding_c").length){
		$("#"+tableId+" .loadding_c").remove();
	}
}
/**
 * 确认弹出框
 * @param title
 * @param content
 * @param callback
 * @returns
 */
function alertBar(title,content,callback){

	if($("#alertBar").length){//预防callback重复调用
		$("#alertBar").modal("dispose");
		$("#alertBar").remove();
	}
	//添加弹出框
	$("#app").append('<div class="modal" id="alertBar" tabindex="-1" role="dialog">'
				+'<div class="modal-dialog"><div class="modal-content"><div class="modal-header">'
				+'<h5 class="modal-title">'+title+'</h5><button type="button" class="close" '
				+'data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>'
				+'</button></div><div class="modal-body">'+content+'</div>'
				+'<div class="modal-footer"><button type="button" class="btn btn-secondary" '
				+'data-dismiss="modal">确定</button></div></div></div></div>');
	$('#alertBar').modal({'show':true,'keyboard':false,'backdrop':'static'});
	var modalHeight = $(window).height() / 2 - $('#alertBar .modal-dialog').height() / 2;
    $('#alertBar').find('.modal-dialog').css({
      'margin-top': modalHeight
    });
    if(callback&&typeof(callback)=="function"){
    	$('#alertBar').on('hidden.bs.modal', function (e) {
    		 callback();
    	})
    }
}
/**
 * 列表控件
 * @param obj
 * @returns
 */
function tableBar(obj){
	this.obj = obj;
	if(!this.obj||typeof(this.obj)!="object"){
		console.log("参数必须为json对象");
		return ;
	}
	if(!this.obj.id){
		console.log("未设置元素id(string)");
		return ;
	}
	if(!this.obj.column){
		console.log("未设置表头参数column(array)");
		return ;
	}
	if(!this.obj.url&&!this.obj.data){
		this.obj.data = [];
	}
	//请求类型
	this.obj.method = this.obj.method||"POST";
	//参数
	this.obj.param = this.obj.param||(this.obj.method.toLowerCase()=="get"?"":{pageIndex:0,pageSize:10});
	if(!this.obj.param.pageIndex){this.obj.param.pageIndex=0;}
	if(!this.obj.param.pageSize){this.obj.param.pageSize=10;}
	//请求内容类型
	this.obj.contentType = this.obj.contentType||"application/json";


	if(!$("#"+this.obj.id+" th").length){
		var tr = "<tr>";
		if(this.obj.serial){
			tr+="<th style='text-align:center;'>序号</th>"
		}
		for(var i=0;i<this.obj.column.length;i++){
			tr+="<th style='text-align:center;'>"+this.obj.column[i].columnName+"</th>"
		}
		tr+="</tr>";
		$("#"+this.obj.id).prepend(tr);
	}
	var tempObj = this.obj;
	if(this.obj.url){
		//请求ajax
		$.ajax({
	        url: this.obj.url, type: this.obj.method, data: this.obj.param?JSON.stringify(this.obj.param):null, contentType: this.obj.contentType, beforeSend: function () {
	        	showTableLoddingBar(tempObj.id);
	        }, success: function (data) {
	        	hideTableLoddingBar(tempObj.id);
	        	if(data.code!=200){
	        		alertBar("提示",data.desc);
	        	}else{
	        		if(data.page){
		        		tempObj.param.pageNo = data.page.pageNo||0;
		        		tempObj.param.totalCount = data.page.totalCount||data.data.length;
		        		tempObj.param.totalPage = data.page.totalPage||1;
	        		}
	        		tempObj.data = data.data;
	        		tableFillData(tempObj);
	        	}

	        },error:function(){
	        	alertBar("提示","加载数据失败");
	        	hideTableLoddingBar(tempObj.id);

	        }
	    })
	}else{
		tempObj.param.pageNo = tempObj.param.pageNo||0;
		tempObj.param.totalCount = tempObj.param.totalCount||tempObj.data.length;
		tempObj.param.totalPage = tempObj.param.totalPage||1;
		tableFillData(tempObj);
	}

}
//填充数据
function tableFillData(tempObj){
   var header = $("#"+tempObj.id+" tr:first").prop("outerHTML");
   var table = $("#"+tempObj.id);
   table.empty();
   table.append(header);
   if(!window.tableParam){//存储表单数据到window中
	   window.tableParam = {};
   }
   window.tableParam[tempObj.id] = tempObj;

   if(!tempObj.data||!tempObj.data.length){
		return;
	}
   	var tableContent = "";
	for(var i=0;i<tempObj.data.length;i++){
		var tr = "<tr>";
		if(tempObj.serial){
			if(tempObj.pageBar){
				tr+="<td  style='text-align:center'>"+(tempObj.param.pageNo*tempObj.param.pageSize+i+1)+"</td>";
			}else{
				tr+="<td  style='text-align:center'>"+(i+1)+"</td>";
			}
	   	}
		for(var j=0;j<tempObj.column.length;j++){
			if(tempObj.column[j].renderer){//自定义内容
				var value = tempObj.column[j].renderer(tempObj.data[i]);
			}else{
				var value = tempObj.data[i][tempObj.column[j].field];
			}
			tr+="<td style='text-align:center;"+(tempObj.column[j].style?tempObj.column[j].style:"")+"'>"+(value==null?"":value)+"</td>"
		}
		tr+="</tr>";
		tableContent+=tr;
	}
	table.append(tableContent);
	if(tempObj.pageBar){
		if(!$("#"+tempObj.pageBar+" .pagination").length){
			$("#"+tempObj.pageBar).append('<nav class="d-inline-block"><ul class="pagination mb-0"></ul></nav>');
		}
		pageBar(tempObj);
	}
}

/**
 * 分页插件
 * @param pageNo
 * @param pageSize
 * @param totalCount
 * @param totalPage
 * @returns
 */
function pageBar(tempObj){
	var pagination = $("#"+tempObj.pageBar+" .pagination");
	pagination.empty();
	//上一页
	if(tempObj.param.pageNo<=0){
		pagination.append('<li class="page-item disabled"><a class="page-link"  href="javascript:void(0)" tabindex="-1">首页</a></li>');
		pagination.append('<li class="page-item disabled"><a class="page-link"  href="javascript:void(0)" tabindex="-1">上一页</a></li>');

	}else{
		pagination.append('<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="goTablePage(\''+tempObj.id+'\',\'first\')" tabindex="-1">首页</a></li>');
		pagination.append('<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="goTablePage(\''+tempObj.id+'\',\'prev\')" tabindex="-1">上一页</a></li>');
	}
	var index = tempObj.param.pageNo<6?0:(tempObj.param.totalPage-tempObj.param.pageNo+5>10?tempObj.param.pageNo-5:(tempObj.param.totalPage-10>0?tempObj.param.totalPage-10:0));
	var last = tempObj.param.totalPage-index>10?index+10:tempObj.param.totalPage;

	for(var i=index;i<last;i++){
		if(tempObj.param.pageNo==i){
			pagination.append('<li class="page-item active"><a class="page-link"  href="javascript:void(0)">'+(i+1)+' <span class="sr-only">(current)</span></a></li>');
		}else{
			pagination.append('<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="goTablePage(\''+tempObj.id+'\',\''+(i+1)+'\')">'+(i+1)+'</a></li>');
		}
	}
	//下一页
	if(tempObj.param.totalPage==tempObj.param.pageNo+1){
		pagination.append('<li class="page-item disabled"><a class="page-link" href="javascript:void(0)">下一页</a></li>');
		pagination.append('<li class="page-item disabled"><a class="page-link" href="javascript:void(0)">尾页</a></li>');
	}else{
		pagination.append('<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="goTablePage(\''+tempObj.id+'\',\'next\')">下一页</a></li>');
		pagination.append('<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="goTablePage(\''+tempObj.id+'\',\'last\')">尾页</a></li>');
	}

}
/**
 * 翻页跳转
 * @param id
 * @param active
 * @returns
 */
function goTablePage(id,active){
	var tempObj = window.tableParam[id];
	if(active=="first"){
		tempObj.param.pageIndex = 0;
	}else if(active=="prev"){
		tempObj.param.pageIndex = tempObj.param.pageNo*tempObj.param.pageSize-tempObj.param.pageSize;
	}else if(active=="next"){
		tempObj.param.pageIndex = tempObj.param.pageNo*tempObj.param.pageSize+tempObj.param.pageSize;
	}else if(active=="last"){
		tempObj.param.pageIndex = (tempObj.param.totalPage-1)*tempObj.param.pageSize;
	}else{
		tempObj.param.pageIndex = (Number(active)-1)*tempObj.param.pageSize;
	}
	tableBar(tempObj)

}

$.fn.serializeJson=function(){  
    var serializeObj={};  
    var array=this.serializeArray();  
    var str=this.serialize();  
    $(array).each(function(){  
        if(serializeObj[this.name]){  
            if($.isArray(serializeObj[this.name])){  
                serializeObj[this.name].push(this.value);  
            }else{  
                serializeObj[this.name]=[serializeObj[this.name],this.value];  
            }  
        }else{  
            serializeObj[this.name]=this.value;   
        }  
    });  
    return serializeObj;  
};
