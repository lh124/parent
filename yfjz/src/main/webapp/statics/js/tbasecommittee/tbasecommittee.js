$(function () {
    $("#jqGrid").jqGrid({
        url: '../tbasecommittee/list',
        datatype: "json",
        colModel: [			
			{ label: 'code', name: 'code', width: 50, key: true },
			{ label: '行政村名称', name: 'name', width: 80 }, 			
			{ label: '机构编码', name: 'orgId', width: 80 }, 			
			{ label: '新增时间', name: 'createTime', width: 80 }			
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
		tBaseCommittee: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tBaseCommittee = {};
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
			var url = vm.tBaseCommittee.code == null ? "../tbasecommittee/save" : "../tbasecommittee/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tBaseCommittee),
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
				    url: "../tbasecommittee/delete",
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
			$.get("../tbasecommittee/info/"+code, function(r){
                vm.tBaseCommittee = r.tBaseCommittee;
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