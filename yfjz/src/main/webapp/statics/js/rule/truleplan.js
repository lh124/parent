$(function () {
    $("#jqGrid").jqGrid({
        url: '../truleplan/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '儿童编码', name: 'childId', width: 80 }, 			
			{ label: '规划字典ID', name: 'classId', width: 80 }, 			
			{ label: '接种剂次', name: 'injectionTimes', width: 80 }, 			
			{ label: '接种时间', name: 'inoculateTime', width: 80 }, 			
			{ label: '最早接种时间', name: 'firstTime', width: 80 }, 			
			{ label: '及时时间', name: 'timelyTime', width: 80 }, 			
			{ label: '合格时间', name: 'qualifiledTime', width: 80 }, 			
			{ label: '最后可接种时间', name: 'lastTime', width: 80 }, 			
			{ label: '接种状态', name: 'state', width: 80 }, 			
			{ label: '接种信息ID', name: 'inoculateId', width: 80 }			
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
		tRulePlan: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tRulePlan = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.tRulePlan.id == null ? "../truleplan/save" : "../truleplan/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tRulePlan),
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../truleplan/delete",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
			$.get("../truleplan/info/"+id, function(r){
                vm.tRulePlan = r.tRulePlan;
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