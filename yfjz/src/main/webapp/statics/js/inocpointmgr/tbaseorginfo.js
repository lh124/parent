$(function () {
    $("#departGrid").jqGrid({
        url: '../tbaseorginfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id',hidden:true, width: 50, key: true },
			{ label: '机构编码', name: 'orgId', width: 80 }, 			
			{ label: '机构名称', name: 'orgName', width: 80 }, 			
			{ label: '机构地址', name: 'address', width: 80 }, 			
			{ label: '负责人邮箱', name: 'email', width: 80 }, 			
			{ label: '负责人', name: 'manager', width: 80 }, 			
			{ label: '负责人电话', name: 'phone', width: 80 }

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

var departVM = new Vue({
	el:'#departrrapp',
	data:{
		showList: true,
		title: null,
		tBaseOrgInfo: {}
	},
	methods: {
		query: function () {
            departVM.reload();
		},
		add: function(){
            departVM.showList = false;
            departVM.title = "新增";
            departVM.tBaseOrgInfo = {
                orgId :orgId,
                orgName:orgName

			};
		},
		update: function (event) {
			var id = getSelectedRow_org();
			if(id == null){
				return ;
			}
            departVM.showList = false;
            departVM.title = "修改";

            departVM.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = departVM.tBaseOrgInfo.id == null ? "../tbaseorginfo/save" : "../tbaseorginfo/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(departVM.tBaseOrgInfo),
                contentType:'application/json;charset=UTF-8',
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
                            departVM.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows("departGrid");
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tbaseorginfo/delete",
				    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#departGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../tbaseorginfo/info/"+id, function(r){
                departVM.tBaseOrgInfo = r.tBaseOrgInfo;
            });
		},
		reload: function (event) {
            departVM.showList = true;
			var page = $("#departGrid").jqGrid('getGridParam','page');
			$("#departGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
		}
	}
});

//选择一条记录
function getSelectedRow_org() {
    var grid = $("#departGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return rowKey;
}
