$(function () {
    $("#jqGrid").jqGrid({
    	url: '../truleplanconsult/list',
        datatype: "json",
        subGrid : true,
        colModel: [			
			{ label: 'id', name: 'id', width: 100, key: true, hidden: true},
			{ label: '规划名称', name: 'className', width: 120 }, 			
			{ label: '接种剂次', name: 'injectionTimes', width: 50 }, 			
			{ label: '接种状态', name: 'state', width: 50 }, 			
			{ label: '最早月龄', name: 'firstMonth', width: 60 }, 			
			{ label: '及时月龄', name: 'timelyMonth', width: 60 }, 			
			{ label: '合格月龄', name: 'qualifiledMonth', width: 60 }, 			
			{ label: '最晚接种月龄', name: 'lastMonth', width: 60 },
			{ label: '操作', name : 'id',width : 80,align : "center",
                formatter: function (value, grid, rows, state) {
                    var df=rows.id;
                    var addReplace = "vm.add('" + df + "','"+rows.className+rows.injectionTimes+"')";
                return '<input type="button" style="height:20px;padding:1px;" class="btn btn-primary"  value="添加替代"  onclick="'+ addReplace + '"/>'
                +'&nbsp;&nbsp;'; }
			 }
        ],
		viewrecords: true,
        height: 400,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
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
        },
        subGridRowExpanded: function(subgrid_id, row_id) {  // (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数  
            var subgrid_table_id;  
            subgrid_table_id = subgrid_id + "_t";   // (3)根据subgrid_id定义对应的子表格的table的id  
              
            var subgrid_pager_id;  
            subgrid_pager_id = subgrid_id + "_pgr"  // (4)根据subgrid_id定义对应的子表格的pager的id  
              
            // (5)动态添加子报表的table和pager   
            $("#" + subgrid_id).html("<table style='color:blue' id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"' class='scroll'></div>");
            // (6)创建jqGrid对象
            $("#" + subgrid_table_id).jqGrid({  
               // url: "fetchPatentCases.action?contact.id="+row_id,  // (7)子表格数据对应的url，注意传入的contact.id参数  
            	url: '../trulereplace/list/'+row_id,
                datatype: "json",
                colModel: [			
        			{ label: 'id', name: 'id', width: 50, key: true,hidden:true },
        			{ label: '疫苗国标码', name: 'bioClassId', width: 80 },
        			{ label: '接种剂次', name: 'agentTimes', width: 80 }, 			
        			{ label: '替代类别', name: 'replaceType', width: 80 }, 			
        			{ label: '开始替代月龄', name: 'startMonth', width: 80 }, 			
        			{ label: '结束替代月龄', name: 'endMonth', width: 80 }, 			
        			{ label: '间隔天数', name: 'intervalDay', width: 80 },
                    { label: '操作', name : 'id',width : 80,align : "center",
                        formatter: function (value, grid, rows, state) {
                            var repid=rows.id;
                            var delReplace = "vm.del('" + repid + "')";
                            return '<input type="button" style="height:20px;padding:1px;" class="btn btn-danger"  value="删除"  onclick="'+ delReplace + '"/>'
                                +'&nbsp;&nbsp;'; }
                    }
                ],
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
                	$("#" + subgrid_table_id).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
                }, 
                pager: subgrid_pager_id,  
                viewrecords: true,  
                height: "100%",  
                rowNum: 10,
        		rowList : [10,30,50]  
           });  
       }  
    });

    //初始化疫苗选择框
    $("#vaccine").jqGrid({
        url: '../tvacinfo/list',
        datatype: "json",
        colModel: [
            { label: '国标编码', name: 'bioCode', width: 70 ,search:true},
            { label: '疫苗名称', name: 'bioName', width: 130 ,search:true},
            { label: '中文简称', name: 'bioCnSortname', width: 100 ,search:true},
            { label: '英文简称', name: 'bioEnSortname', width: 80 ,search:true},
        ],
        viewrecords: true,
        width:600,
        height: 385,
        rowNum: 1000,
        rowList : [10,30,50],
        rownumWidth: 25,
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
            $("#vaccine").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        //选中一行，然后将数据保存到输入框中，关闭弹出框
        onSelectRow:function (rowid,status) {
            var row=$("#vaccine").jqGrid("getRowData",rowid);
            $("#biocode").val(row.bioCode);
            $("#bioclassid").val(row.bioName);
            //vm.tMgrStockBase = {fkVaccineCode:row.bioCode,productName:row.bioName};
            $('#myModal').modal('hide')
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tRuleReplace: {},
		tRuleReplaceOther: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(id,name){
			vm.showList = false;
			vm.title = "新增";
			vm.tRuleReplace = {};
            vm.tRuleReplace.planConsultId=id;
            vm.tRuleReplaceOther = {};
            vm.tRuleReplaceOther.className=name;
		},
		saveOrUpdate: function (event) {
            vm.tRuleReplace.bioClassId = $("#biocode").val();
			var url = vm.tRuleReplace.id == null ? "../trulereplace/save" : "../trulereplace/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tRuleReplace),
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
		del: function (id) {
			confirm('确定要删除该替代关系？', function(){
				$.ajax({
					type: "POST",
				    url: "../trulereplace/delete/"+id,
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
			$.get("../trulereplace/info/"+id, function(r){
                vm.tRuleReplace = r.tRuleReplace;
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