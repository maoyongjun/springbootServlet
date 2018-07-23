
$(function () {
	$.ajax({
		url:"test",
		type:"POST",
		contentType:"text/html;charset=utf-8",
		dataType:"text",
		success:function(result){
			alert(result);
		},
		error:function(){
			alert(123);
		}
	});
	
	$.fn.editable.defaults.mode = 'inline';//编辑方式为内联方式
    $('#db_dependences').bootstrapTable({
    	method:'POST',
        dataType:'json',
        contentType: "application/x-www-form-urlencoded",
        cache: false,
        striped: true,                              //是否显示行间隔色
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        showColumns:true,
        pagination:true,
        minimumCountColumns:2,
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 5,                       //每页的记录行数（*）
        pageList: [5, 15, 20, 25],        //可供选择的每页的行数（*）
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showExport: true,                    
        exportDataType: 'all',
        exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
        onEditableSave: function (field, row, oldValue, $el) {
            $.ajax({
                success: function (data, status) {
                    if (status == "success") {
                        alert("编辑成功");
                    }
                },
                error: function () {
                    alert("Error");
                },
                complete: function () {

                }
            });
		},
		data: [{
	        id: 1,
	        name: '张三',
	        sex: '男',
	        time: '2017-08-09'
	    }, {
	        id: 2,
	        name: '王五',
	        sex: '女',
	        time: '2017-08-09'
	    }, {
	        id: 3,
	        name: '李四',
	        sex: '男',
	        time: '2017-08-09'
	    }, {
	        id: 4,
	        name: '杨朝来',
	        sex: '男',
	        time: '2017-08-09'
	    }, {
	        id: 5,
	        name: '蒋平',
	        sex: '男',
	        time: '2017-08-09'
	    }, {
	        id: 6,
	        name: '唐灿华',
	        sex: '男',
	        time: '2017-08-09'
	    }],
        columns: [{
	        field: 'id',
	        title: '序号'
	    }, {
	        field: 'name',
	        title: '姓名',
            editable: {
				type: 'text',  
				validate: function (value) {  
					if ($.trim(value) == '') {  
						return '姓名不能为空!';  
					}  
				}
			} 
	    }, {
	        field: 'sex',
	        title: '性别',
            editable: {
				type: 'select',
				pk: 1,
		        source: [
		            {value: 1, text: '男'},
		            {value: 2, text: '女'},
		        ]
			}
	    },  {
	        field: 'time',
	        title: '时间',
	        editable: {
	        	type: 'date',
				format: 'yyyy-mm-dd',    
		        viewformat: 'yyyy-mm-dd',    
		        datepicker: {
		            weekStart: 1
		        }
			} 
	    }]
	});
});
