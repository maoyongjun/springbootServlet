
$(function () {
	
    
	var json ="";
	$.ajax({
		url:"label/query",
		type:"get",
		contentType:"text/html;charset=utf-8",
		dataType:"text",
		success:function(result){
//			console.log(result);
			setTable(result);
		},
		error:function(){
			alert(123);
		}
	});
	$("#query").click(queryData);
	$("#addLabelsubmit").click(addLabelsubmit);
	$("#deleteLabel").click(deleteLabel);
	$("#queryLabel").click(queryLabel);
	$("#queryLabelsubmit").click(queryLabelAction);
	$("#saveRowbtn").click(saveRowbtnAction);
	
	
	$('#addRowbtn').click(function(){
		
		var parentSkuno = $('#queryParentSkuno').val();
		var skuno = $('#querySkuno').val();
		var label = $('#queryLabel').val();
		var others = $('#queryOthers').val();
		
	    var data = {"skuno":"","parentSkuno":"","skuno":"","label":"","others":"","isPreDo":"","qty":""};
	    $('#db_dependences').bootstrapTable('append',data);    
	});
	
});

function saveRowbtnAction(){
	var rows = $('#db_dependences').bootstrapTable('getData', true);
//	console.log(rows);
	var data="";
	for(var i in rows){
		var json = JSON.stringify(rows[i]);
		console.log("--------");
		if(rows[i].id==undefined){
//			console.log(rows[i].skuno);
			data+=json+",";
		}
	}
	data="["+data.substr(0,data.length-1)+"]";
	console.log(data);
	
	$.ajax({
		url:"label/addList",
		type:"post",
		contentType:"application/json;charset=utf-8",
		data:data,
		dataType:"json",
		success:function(result){
			$('#db_dependences').bootstrapTable('refresh', { url: 'label/query'});	
		},
		error:function(){
			alert("call error");
		}
	});
}



function queryLabelAction(){
	var parentSkuno = $('#queryParentSkuno').val();
	var skuno = $('#querySkuno').val();
	var label = $('#queryLabel').val();
	var others = $('#queryOthers').val();
	if(parentSkuno==undefined){
		parentSkuno="";
	}
	if(skuno==undefined){
		skuno="";
	}
	if(label==undefined){
		label="";
	}
	if(others==undefined){
		others="";
	}
	var queryStr = "parentSkunos="+parentSkuno+"&skunos="+skuno
	+"&label="+label+"&others="+others
	console.log(queryStr);
	$('#db_dependences').bootstrapTable('refresh', { url: 'label/query?'+queryStr});
	$("#myQueryModal").modal("hide");
}

function queryLabel(){
	$("#myQueryModal").modal("show");
}

function deleteLabel(){
	var a= $("#db_dependences").bootstrapTable('getSelections');
	if(a.length!=0){
	    console.log(a[0].id);
	    var ids ="";
	    for(var i=0;i<a.length;i++){
	    	ids=ids+" "+a[i].id;
	    }
	    console.log(ids);
	    

		
	 	$.ajax({
			url:"label/delete",
			type:"post",
			contentType:"application/json;charset=utf-8",
			data:"{\"ids\":\""+ids+"\"}",
			dataType:"json",
			success:function(result){
				console.log(result);
				$('#db_dependences').bootstrapTable('refresh', { url: 'label/query'});
			},
			error:function(){
				alert(123);
			}
		});
	}else{
		alert("请选中至少一行")
		
	}
}


function addLabelsubmit(){
	var json = JSON.stringify($('#addLabelForm').serializeJSON());
	console.log(json);
	if(json.indexOf("\"skuno\":\"\"")!=-1){
		alert("子階成品料號不能為空");
		return ;
	}
	$.ajax({
		url:"label/add",
		type:"post",
		contentType:"application/json;charset=utf-8",
		data:json,
		dataType:"json",
		success:function(result){
			if(result.msg=='OK'){
				$('#myModal').modal('hide');
			}else{
				alert("add error");
			}
		},
		error:function(){
			alert("call error");
		}
	});
}

function queryData(){
	
//	$('#mydialog').popover({
//		collapsible: true,
//		minimizable: true,
//		maximizable: true,
//		modal:true,
//		buttons: [{
//		text: 'submit',
//		iconCls: 'icon-ok',
//		handler: function() {
//			addSoAction();
//		}
//		}, {
//		text: 'cancel',
//		handler: function() {
//			$('#mydialog').popover('close');
//		}
//		}]
//	});
}

function setTable(json){
//	alert(json);
	$.fn.editable.defaults.mode = 'inline';//编辑方式为内联方式
    $('#db_dependences').bootstrapTable({
    	method:'GET',
        dataType:'json',
        contentType: "application/x-www-form-urlencoded",
        cache: false,
        striped: true,                              //是否显示行间隔色
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        showColumns:true,
        pagination:true,
        minimumCountColumns:2,
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 15, 20, 25],        //可供选择的每页的行数（*）
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showExport: true,                    
        exportDataType: 'all',
        exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
        onEditableSave: function (field, row, oldValue, $el) {
        	if (row.id !=""&&row.id!=undefined){
        		
        	console.log(row);
//        	alert(row);
            $.ajax({
            	url:"label/update",
        		type:"post",
        		data:JSON.stringify( row ),
        		contentType:"application/json;charset=utf-8",
                success: function (result) {
//                    alert(result);
                },
                error: function () {
                    alert("Error");
                },
                complete: function () {

                }
            });
        	}
        },
//      onEditableHidden: function(field, row, $el, reason) { // 当编辑状态被隐藏时触发
//          if(reason === 'save') {
//              var $td = $el.closest('tr').children();
//          //    $td.eq(-1).html((row.price*row.number).toFixed(2));
//          //    $el.closest('tr').next().find('.editable').editable('show'); //编辑状态向下一行移动
//          } else if(reason === 'nochange') {
//              $el.closest('tr').next().find('.editable').editable('show');
//          }
//      },
		data:$.parseJSON(json),
        columns: [{
            checkbox: true
        },{
	        field: 'id',
	        title: '序号'
	    }, {
	        field: 'parentSkuno',
	        title: '總成料號',
            editable: {
				type: 'text',  
				validate: function (value) {  
					if ($.trim(value) == '') {  
						return '總成料號不能为空!';  
					}  
				}
			} 
	    }, {
	        field: 'skuno',
	        title: '子階成品料號',
            editable: {
				type: 'text',  
				validate: function (value) {  
					if ($.trim(value) == '') {  
						return '子階成品料號不能为空!';  
					}  
				}
			} 
	    }, {
	        field: 'description',
	        title: '描述'
            
	    }, {
	        field: 'version',
	        title: '版次'
            
	    },{
	        field: 'label',
	        title: 'HHPN',
            editable: {
				type: 'text',  
				validate: function (value) {  
					if ($.trim(value) == '') {  
						return 'HHPN不能为空!';  
					}  
				}
			} 
	    }, 
	    
	    {
	        field: 'qty',
	        title: '數量',
            editable: {
				type: 'text',  
				validate: function (value) {  
					if ($.trim(value) == '') {  
						return '數量不能为空!';  
					}  
				}
			} 
	    },{
	        field: 'isPreDo',
	        title: '是否前加工',
            editable: {
				type: 'select',
				pk: 1,
		        source: [
		            {value: '前加工', text: '前加工'},
		            {value: 'SN需展開不打印', text: 'SN需展開不打印'},
		            {value: 'SN需展開并前加工', text: 'SN需展開并前加工'}
		        ],
		        noeditFormatter: function (value,row,index) {
                    var result={filed:"isPreDo",value:value,class:"badge",style:"background:#333;padding:5px 10px;"};
                    return result;
                }
			}
	    },
	    {
	        field: 'others',
	        title: '備註',
            editable: {
				type: 'text',  
				validate: function (value) {  
					if ($.trim(value) == '') {  
						return '備註不能为空!';  
					}  
				}
			} 
	    }
	    
	    ]
	});
}
