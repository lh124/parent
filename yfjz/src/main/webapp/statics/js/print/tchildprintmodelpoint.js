$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchildprintmodelpoint/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '接种疫苗', name: 'bioName', width: 80 }, 			
			{ label: '疫苗唯一标识名', name: 'ename', width: 80 }, 			
			{ label: '接种日期X', name: 'inoculateDatex', width: 80 }, 			
			{ label: '接种日期Y', name: 'inoculateDatey', width: 80 }, 			
			{ label: '接种部位X', name: 'textx', width: 80 }, 			
			{ label: '接种部位Y', name: 'texty', width: 80 }, 			
			{ label: '疫苗批号X', name: 'batchnumx', width: 80 }, 			
			{ label: '疫苗批号Y', name: 'batchnumy', width: 80 }, 			
			{ label: '生产企业X坐标', name: 'factoryNamex', width: 80 }, 			
			{ label: '生产企业X坐标', name: 'factoryNamey', width: 80 }, 			
			{ label: '接种单位X坐标', name: 'departnamex', width: 80 }, 			
			{ label: '接种单位Y坐标', name: 'departnamey', width: 80 }, 			
			{ label: '接种医生X', name: 'doctorx', width: 80 }, 			
			{ label: '接种医生Y坐标', name: 'doctory', width: 80 }, 			
			{ label: '失效期坐标X', name: 'expirationDatex', width: 80 }, 			
			{ label: '失效期坐标Y', name: 'expirationDatey', width: 80 }, 			
			{ label: '打印选择类型', name: 'printtype', width: 80 }, 			
			{ label: '状态,0正常,1停用', name: 'status', width: 80 }, 			
			{ label: '删除状态：0：正常；1：删除', name: 'deleteStatus', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', width: 80 }, 			
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
		tChildPrintModelPoint: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tChildPrintModelPoint = {};
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
			var url = vm.tChildPrintModelPoint.id == null ? "../tchildprintmodelpoint/save" : "../tchildprintmodelpoint/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tChildPrintModelPoint),
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
				    url: "../tchildprintmodelpoint/delete",
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
			$.get("../tchildprintmodelpoint/info/"+id, function(r){
                vm.tChildPrintModelPoint = r.tChildPrintModelPoint;
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