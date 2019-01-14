$(function () {
    $("#jqGrid").jqGrid({
        url: '../tmgrcheck/list',
        datatype: "json",
        subGrid : true,
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true,hidden:true},
			{ label: '盘点单号', name: 'checkCode', width: 80 }, 			
			{ label: '盘点仓库', name: 'storeName', width: 80 },
			{ label: '盘点时间', name: 'checkTime', width: 80 }, 			
			{ label: '盘点人', name: 'fkCheckUserId', width: 80 }, 			
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 10,
		rowList : [10,20,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
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
            $("#" + subgrid_id).html("<table  id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"' class='scroll'></div>");
            // (6)创建jqGrid对象
            $("#" + subgrid_table_id).jqGrid({
                // url: "fetchPatentCases.action?contact.id="+row_id,  // (7)子表格数据对应的url，注意传入的contact.id参数
                url: '../tmgrcheck/itemList/'+row_id,
                datatype: "json",
                colModel: [
                    { label: 'id', name: 'id', width: 50, key: true ,hidden:true},
                    { label: '疫苗/产品名称', name: 'productName', width: 150 },
                    { label: '批号', name: 'batchnum', width: 80 },
                    { label: '失效期', name: 'expiryDate', width: 80 },
                    { label: '生产厂家', name: 'factoryName', width: 150 },
                    { label: '规格', name: 'dosenorm', width: 80 },
                    { label: '剂型', name: 'drug', width: 80 },
                    { label: '计量单位', name: 'doseminUnitCode', width: 80 },
                    { label: '单价(元)', name: 'price', width: 80,formatter:function(cellvalue, options, rowObject){
                            if(cellvalue!=''&&cellvalue!=undefined&&cellvalue!=null){
                                return cellvalue.toFixed(2);
                            }else{
                                return cellvalue;
                            }
                        }},
                    { label: '数量(支)', name: 'sysNumber', width: 80 },
                    { label: '实际数量(支)', name: 'realNumber', width: 80,editable:true,/*edittype :"number"*/},
                    { label: '差异数量(支)', name: 'diffNumber', width: 80 },
                    { label: '差异原因', name: 'cause', width: 80 , editable:true},
                    { label: '处理方法', name: 'handle', width: 80, editable:true },
                    { label: '盘点状态', name: 'checkStatus', width: 80,formatter:function(cellvalue, options, rowObject){
                            if(cellvalue==0){
                                return '<span class="label label-info">正常</span>';
                            }else if(cellvalue==1){
                                return '<span class="label label-success">盘盈</span>';
                            }else if(cellvalue==-1){
                                return '<span class="label label-warning">盘亏</span>';
                            }
                        }}
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
               // pager: subgrid_pager_id,
                autowidth:true,
                viewrecords: true,
                height: "100%",
                rowNum: 1000,
                rowList : [10,30,50]
            });
        }
    });

    $("#checkItem").jqGrid({
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true ,hidden:true},
            { label: '疫苗/产品名称', name: 'productName', width: 150 },
            { label: '批号', name: 'batchnum', width: 80 },
            { label: '失效期', name: 'expiryDate', width: 80 },
            { label: '生产厂家', name: 'factoryName', width: 150 },
            { label: '规格', name: 'dosenorm', width: 80 },
            { label: '剂型', name: 'drug', width: 80 },
            { label: '计量单位', name: 'doseminUnitCode', width: 80 },
            { label: '单价(元)', name: 'price', width: 80,formatter:function(cellvalue, options, rowObject){
                    if(cellvalue!=''&&cellvalue!=undefined&&cellvalue!=null){
                        return cellvalue.toFixed(2);
                    }else{
                        return cellvalue;
                    }
                }},
            { label: '数量(支)', name: 'amount', width: 80 },
            { label: '实际数量(支)', name: 'realNumber', width: 80,editable:true,/*edittype :"number"*/},
    		{ label: '差异数量(支)', name: 'diffNumber', width: 80 },
            { label: '差异原因', name: 'cause', width: 80 , editable:true},
            { label: '处理方法', name: 'handle', width: 80, editable:true }

        ],
        userDataOnFooter:true,
        viewrecords: true,
        height: 385,
        rowNum: 1000,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
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
            $("#checkItem").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        onSelectRow:function (rowid,status) {
            jQuery('#checkItem').editRow(rowid);
            $("#" + rowid+'_realNumber').on("blur",function(){
                var row=$("#checkItem").getRowData(rowid);
                var real=$(this).val();
                var diff=real-row.amount;
                if(real!=null&&real!=''){
                  $("#checkItem").setCell(rowid,'diffNumber',diff);
                }
            })
        },
    });


    vm.loadData();//初始化下拉框 仓库
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tMgrCheck: {},
        items:[],
        storeId:null
	},
	methods: {
		query: function () {
            var searchOrder=$("#searchOrder").val();
            var searchUser=$("#searchUser").val();
            var startDate=$("#startDate").val();
            var endDate=$("#endDate").val();
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'searchOrder': searchOrder,
                    'searchUser': searchUser,
                    'startDate': startDate,
                    'endDate': endDate,
                },
                page:1
            }).trigger("reloadGrid");
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tMgrCheck = {};
			vm.reset();
			$("#checkItem").jqGrid("clearGridData");
			vm.getOrderId();
            // vm.items={};
            var storeId=$("#store").val();
			vm.loadData();
            if(storeId!=null||storeId!=''||storeId!=undefined){
                vm.reloadList();
            }
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
        loadData:function(){
            var param = new Array();//定义数组
            //下拉选框
            $.ajax({
                // get请求地址
                url: '../tmgrstore/list?page=1&limit=1000&type=0',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result=data.page.list;
                    $.each(result, function (index, item) {
                        param.push({"text":item.name,"value":item.id});
                    });
                    vm.items = param;
                }
            });
        },
		saveOrUpdate: function (event) {
			/*var url = vm.tMgrCheck.id == null ? "../tmgrcheck/save" : "../tmgrcheck/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tMgrCheck),
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
			});*/
			var ele=  $("#checkItem");
			var order=$("#order").val();//盘点编号
			var checkUser=$("#checkUser").val();//盘点人员
			var checkTime=$("#checkTime").val();//盘点时间
			var storeId=vm.storeId;//仓库编号
            if(order==undefined||order==''||order==null){
                layer.msg("盘点编号不能为空")
                return;
            }
            if(checkUser==undefined||checkUser==''||checkUser==null){
                layer.msg("盘点人员不能为空")
                return;
            }
            if(checkTime==undefined||checkTime==''||checkTime==null){
                layer.msg("盘点时间不能为空")
                return;
            }
            if(storeId==undefined||storeId==''||storeId==null){
                layer.msg("盘点仓库不能为空")
                return;
            }
			endEdit(ele);//结束编辑模式
            var rows=getRows(ele);//获取表格的所有数据
            for(var i=0;i<rows.length;i++){
                var number=rows[i].realNumber;
                if(number==''||number==undefined||number==null){
                    layer.msg("请填写实际数量(支)");
                    return;
                }
            }
            var url ="../tmgrcheck/save";
            var result={
                "order":order,
                "checkUser":checkUser,
                "checkTime":checkTime,
                "storeId":storeId,
                "rows":rows
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(result),
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功');
                        vm.reset();
                        vm.reload();
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
				    url: "../tmgrcheck/delete",
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
			$.get("../tmgrcheck/info/"+id, function(r){
                vm.tMgrCheck = r.tMgrCheck;
            });
		},
		reload: function (event) {
			vm.showList = true;
            vm.items={};
            // $("#store").empty();
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        reset:function(){
            $("#searchForm")[0].reset();

        },
		reloadList:function () {
            /**
             * 根据仓库ID查询所有的库存信息
             * @type {null}
             */
			var storeid=vm.storeId;
            $("#checkItem").setGridParam( //G,P要大写
                {
                    url:'../tmgrstockinfo/list',
                    postData:{"storeId":storeid}
                }
            ) .trigger("reloadGrid");

        },
        getOrderId:function(){
            $.ajax(
                $.get("../tmgrstockinfo/getOrderNumber",{"type":"check"},function (data) {
                    $("#order").val(data);
                })
            )
        }
	}
});
