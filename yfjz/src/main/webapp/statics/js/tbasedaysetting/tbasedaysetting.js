$(function () {
    $("#jqGrid").jqGrid({
        url: '../tbasedaysetting/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '接种点编码 类似5201110301', name: 'departId', width: 80 }, 			
			{ label: '接种日设置类型 1. 接种点-周设置，2.接种点-居委会/行政村-周设置，3.接种点-疫苗-周设置，4.接种点-月设置，5.接种点-居委会/行政村-月设置，6.接种点-疫苗-月设置', name: 'settingType', width: 80 }, 			
			{ label: '接种点设置的接种日，如果接种日设置类型为1, 该字段保存1到7之间的集合（json对象的字符），如果接种日设置类型为4，该字段保存1到31之间的集合，如果接种日设置类型为其他值，该值不设置', name: 'days', width: 80 }			
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
		tBaseDaySetting: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tBaseDaySetting = {};
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
			var url = vm.tBaseDaySetting.id == null ? "../tbasedaysetting/save" : "../tbasedaysetting/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tBaseDaySetting),
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
				    url: "../tbasedaysetting/delete",
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
			$.get("../tbasedaysetting/info/"+id, function(r){
                vm.tBaseDaySetting = r.tBaseDaySetting;
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