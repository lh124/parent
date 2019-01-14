$(function () {
    $("#jqGrid").jqGrid({
        url: '../truleplanconsult/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '儿童编码', name: 'childCode', width: 80 },  			
			{ label: '规划名称', name: 'className', width: 120 }, 			
			{ label: '接种剂次', name: 'injectionTimes', width: 80 }, 			
			{ label: '接种时间', name: 'inoculateTime', width: 80 }, 			
			{ label: '最早接种时间', name: 'firstTime', width: 80 }, 			
			{ label: '及时时间', name: 'timelyTime', width: 80 }, 			
			{ label: '合格时间', name: 'qualifiledTime', width: 80 }, 			
			{ label: '最晚时间', name: 'lastTime', width: 80 }, 			
			{ label: '接种状态', name: 'state', width: 80 }, 			
			{ label: '接种信息ID', name: 'inoculateId', width: 80 }, 			
			{ label: '最早月龄', name: 'firstMonth', width: 80 }, 			
			{ label: '及时月龄', name: 'timelyMonth', width: 80 }, 			
			{ label: '合格月龄', name: 'qualifiledMonth', width: 80 }, 			
			{ label: '最晚接种月龄', name: 'lastMonth', width: 80 }			
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
		tRulePlanConsult: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tRulePlanConsult = {};
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
			var url = vm.tRulePlanConsult.id == null ? "../truleplanconsult/save" : "../truleplanconsult/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tRulePlanConsult),
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
				    url: "../truleplanconsult/delete",
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
			$.get("../truleplanconsult/info/"+id, function(r){
                vm.tRulePlanConsult = r.tRulePlanConsult;
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