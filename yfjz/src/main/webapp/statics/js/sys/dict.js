$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/dict/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', width: 30, key: true,hidden: true},
			{ label: '数据类型', name: 'ttype', width: 60 },
			{ label: '数据名称', name: 'text', width: 60 },
			{ label: '数据值', name: 'value', width: 100 },
			{ label: '备注', name: 'remark', width: 80 }
        ],
		viewrecords: true,
        height: $(window).height() - 130,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			ttype: null
		},
		showList: true,
		title: null,
		dict: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dict = {};
		},
		update: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			$.get("../sys/dict/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.dict = r.dict;
            });
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/dict/delete",
                    contentType:'application/json;charset=UTF-8',
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.dict.id == null ? "../sys/dict/save" : "../sys/dict/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.dict),
                contentType:'application/json;charset=UTF-8',
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'ttype': vm.q.ttype},
                page:page
            }).trigger("reloadGrid");
		}
	}
});