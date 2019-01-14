$(function () {
    $("#jqGrid").jqGrid({
        url: '../tbasehospital/list',
        datatype: "json",
        colModel: [			
			{ label: 'hospitalCode', name: 'hospitalCode', width: 50, key: true },
			{ label: '医院名称', name: 'hospitalName', width: 80 }, 			
			{ label: '医院所在区域编码', name: 'orgAreaCode', width: 80 }, 			
			{ label: '状态: 0：正常；-1：删除', name: 'status', width: 80 }, 			
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
		tBaseHospital: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tBaseHospital = {};
		},
		update: function (event) {
			var hospitalCode = getSelectedRow();
			if(hospitalCode == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(hospitalCode)
		},
		saveOrUpdate: function (event) {
			var url = vm.tBaseHospital.hospitalCode == null ? "../tbasehospital/save" : "../tbasehospital/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tBaseHospital),
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
			var hospitalCodes = getSelectedRows();
			if(hospitalCodes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tbasehospital/delete",
				    data: JSON.stringify(hospitalCodes),
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
		getInfo: function(hospitalCode){
			$.get("../tbasehospital/info/"+hospitalCode, function(r){
                vm.tBaseHospital = r.tBaseHospital;
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