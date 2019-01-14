$(function () {
    $("#jqGrid").jqGrid({
        url: '../tbaseschool/list',
        datatype: "json",
        colModel: [			
			{ label: 'code', name: 'code', width: 50, key: true },
			{ label: '名称', name: 'name', width: 80 }, 			
			{ label: '组织', name: 'orgId', width: 80 }, 			
			{ label: '详细地址', name: 'address', width: 80 }, 			
			{ label: '联系电话', name: 'phone', width: 80 }, 			
			{ label: '状态  0：正常；-1：删除', name: 'status', width: 80 }, 			
			{ label: '启用时间', name: 'createTime', width: 80 }, 			
			{ label: '删除状态：0：正常；1：删除', name: 'deleteStatus', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
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
		showList: true,
		title: null,
		tBaseSchool: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tBaseSchool = {};
		},
		update: function (event) {
			var code = getSelectedRow();
			if(code == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(code)
		},
		saveOrUpdate: function (event) {
			var url = vm.tBaseSchool.code == null ? "../tbaseschool/save" : "../tbaseschool/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tBaseSchool),
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
		del: function (event) {
			var codes = getSelectedRows();
			if(codes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tbaseschool/delete",
				    data: JSON.stringify(codes),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(code){
			$.get("../tbaseschool/info/"+code, function(r){
                vm.tBaseSchool = r.tBaseSchool;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});