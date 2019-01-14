$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchildelsewhere/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50,hidden:true, key: true },
			{ label: '儿童编码', name: 'migrChildcode', width: 80 }, 			
			{ label: '异动日期', name: 'migrDate', width: 80 }, 			
			{ label: '异地接种单位', name: 'migrDepaCode', width: 80 },
			/*{ label: '本地服务器时间', name: 'migrServerdate', width: 80 }, */
			{ label: '异动类型', name: 'migrVaccState', width: 80 }
        ],
		viewrecords: true,
        height: 400,
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
            changeDateStart:null,
            changeDateEnd:null
		},
		showList: true,
		title: null,
		tChildElsewhere: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tChildElsewhere = {};
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
			var url = vm.tChildElsewhere.id == null ? "../tchildelsewhere/save" : "../tchildelsewhere/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tChildElsewhere),
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
				    url: "../tchildelsewhere/delete",
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
			$.get("../tchildelsewhere/info/"+id, function(r){
                vm.tChildElsewhere = r.tChildElsewhere;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:vm.q,
                page:page
            }).trigger("reloadGrid");
		},
        downloadChild: function () {
            layer.msg('正在下载...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:1000000}) ;
            $.ajax({
                type: "POST",
                url: "../provincePlatform/downloadChild",
                dataType:"json",
                success: function(r){
                    console.log(r);
                    if(r.code == 0){
                        layer.msg(r.msg);
                        vm.showList = true;
                        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                        $("#jqGrid").jqGrid('setGridParam',{
                            postData:{
                            },
                            page: page
                        }).trigger("reloadGrid");
                    }else{
                        layer.msg(r.msg);
                    }
                }
            });
        }
	}
});