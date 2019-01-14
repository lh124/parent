$(function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'',
        noneResultsText:'没有匹配的结果',
        actionsBox:true
    });
    //初始化日期
    $("#jgWidth").css('width',window.width);
    $("#reWidth").css('width',window.width);
    $('.times').datetimepicker(
        {
            format: 'yyyymmdd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN'
        }
    );
    $("#jqGrid").jqGrid({
        url: '../tmgrstockintotal/list',
        datatype: "json",
        subGrid : true,
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true,hidden:true },
			{ label: '入库单号', name: 'stockInCode', width: 80 },
			{ label: '入库人员', name: 'fkInStockUser', width: 80 },
			{ label: '入库时间', name: 'storageTime', width: 80 },
          /*  { label: '入库类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==0?'疫苗':'其他';
                }},*/
			{ label: '仓库', name: 'store.name', width: 80 },
			{ label: '备注', name: 'remark', width: 80 },

        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 10,
		rowList : [10,20,30,50],
        rownumbers: true, 
        rownumWidth: 25,
        multiselect: true,
        autowidth:true,
        multiboxonly:true,
        beforeSelectRow: beforeSelectRow,
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
            console.log(subgrid_table_id+"------------------------")
            $("#" + subgrid_table_id).jqGrid({
                // url: "fetchPatentCases.action?contact.id="+row_id,  // (7)子表格数据对应的url，注意传入的contact.id参数
                url: '../tmgrstockintotal/itemList/'+row_id,
                datatype: "json",
                colModel: [
                    { label: 'id', name: 'id', width: 50, key: true,hidden:true },
                    { label: 'baseId', name: 'baseId', width: 50,hidden:true },
                    { label: 'equipId', name: 'equipId', width: 50, hidden:true },
                    { label: 'totalId', name: 'totalId', width: 50, hidden:true },
                    { label: 'conversion', name: 'conversion', width: 50,hidden:true },
                    { label: '疫苗/产品名称', name: 'productName', width: 80 },
                    { label: '价格', name: 'price', width: 80 ,formatter:function(cellvalue, options, rowObject){
                            return cellvalue.toFixed(2);;
                        }},
                    { label: '生产厂家', name: 'factoryName', width: 80 },
                    { label: '批号', name: 'batchnum', width: 80 },
                    { label: '失效期', name: 'expiryDate', width: 80 },
                    { label: '规格', name: 'dosenorm', width: 80 },
                    { label: '单位', name: 'doseminUnitCode', width: 80 },
                    { label: '剂型', name: 'drug', width: 80 },
                    { label: '数量(支)', name: 'amount', width: 80 ,editable: true},
                    { label: '数量(人份)', name: 'personAmount', width: 80 },
                    { label: '存放位置', name: 'equipmentName', width: 80 },
                    { label: '供货单位', name: 'providerFactory', width: 80 },
                    { label: '供货人', name: 'provider', width: 80 },
                    { label: '操作', name: 'opt', width: 100,formatter:function(cellvalue, options, rowObject){
                            return "<a href='javaScript:void(0)' onclick='editNumber(\""+options.rowId+"\",\""+options.gid+"\")'>修改</a>"+' | '+"<a href='javaScript:void(0)' onclick='saveNumber(\""+options.rowId+"\",\""+options.gid+"\")'>保存</a>";
                        }},
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
                //pager: subgrid_pager_id,
                autowidth:true,
                viewrecords: true,
                height: "100%",
                rowNum: 100,
                //rowList : [10,30,50]
            });
        }
    });
    $("#recordGrid").jqGrid({
        url: '../tmgrstockintotal/modifyList',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true,hidden:true },
            { label: '入库单号', name: 'stockInId', width: 120 },
            { label: '疫苗名称', name: 'productName', width: 120 },
            { label: '批号', name: 'batchnum', width: 120 },
            { label: '失效期', name: 'expiryDate', width: 120 },
            { label: '生产厂家', name: 'factoryName', width: 120 },
            { label: '原入库数量', name: 'preAmount', width: 120 },
            { label: '修改数量', name: 'nowAmount', width: 120 },
            { label: '修改时间', name: 'createTime', width: 120 },
            { label: '修改人员', name: 'createUser', width: 120 },
            { label: '备注', name: 'remark', width: 120 }

        ],
        viewrecords: true,
        height: 'auto',
        rowNum: 10,
        rowList : [10,20,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        pager: "#recordGridPager",
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
            $("#recordGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }})
    vmp.loadDoctorData();
    vmp.loadVaccine();
    $("#myTab li ").on("click",function () {
        $("#jqGrid").trigger("reloadGrid");
        $("#recordGrid").trigger("reloadGrid");
    })
    $('#searchProductName').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择疫苗',
        // actionsBox:true,
        search:false,
    });
    //拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#searchProductNameId").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#searchProductNameId .open input').val();
        var hunt = $("#searchProductName");
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
            var value = null;
            if("undefined" != typeof vmp){
                value = vmp.bioCnSortname;
            }

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].bioCnSortname.indexOf(inputManagerName) == 0){
                        con += '<option value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
                    }
                }
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            event.stopPropagation();
            return false;
        } else{
            //如果输入的字符为空,清除之前option标签
            hunt.empty();
            var value = null;
            if("undefined" != typeof vmp){
                value = vmp.bioCnSortname;
            }
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });

});
var vmp = new Vue({
    el:'#recordTable',
    data:{
        showList: true,
        title: null,
        stockInfo: {},
        bioCnSortname:null
    },
    methods: {
        query: function () {
            var modifyUser=$("#inocDoctor").val();
            var vaccine=$("#searchProductName").selectpicker("val");
            var startDate=$("#startDates").val();
            var endDate=$("#endDates").val();
            $("#recordGrid").jqGrid('setGridParam',{
                postData:{
                    'modifyUser': modifyUser,
                    'vaccine': vaccine,
                    'startDate': startDate,
                    'endDate': endDate,
                },
                page:1
            }).trigger("reloadGrid");
        },
        //接种医生
        loadDoctorData:function(){
            $.ajax({
                url: '../sys/user/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    $("#inocDoctor").append(" <option value='-1'></option>");
                    $.each(seldata, function (i, n) {
                        $("#inocDoctor").append(" <option value=\"" + n.realName + "\">" + n.realName + "</option>");
                    });
                    $("#inocDoctor").selectpicker('refresh');
                }
            });
        },
        loadVaccine:function () {
            //加载疫苗的名称信息
            $.ajax({
                // get请求地址
                url: '../tvacinfo/getAllData',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result=data.page;
                    if("undefined" != typeof vmp){
                        vmp.bioCnSortname = result;
                    }

                    $("#searchProductName").append(" <option value='-1'></option>")
                    $.each(result, function (i, n) {

                        $("#searchProductName").append(" <option value=\"" + n.bioCode + "\">" + n.bioCnSortname + "</option>");
                    })
                    $("#searchProductName").selectpicker('refresh');
                }
            });
        }
    }
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		stockInfo: {}
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
			vm.stockInfo = {};
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
			var url = vm.stockInfo.id == null ? "../tmgrstockintotal/save" : "../tmgrstockintotal/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.stockInfo),
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
				    url: "../tmgrstockintotal/delete",
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
			$.get("../tmgrstockintotal/info/"+id, function(r){
                vm.stockInfo = r.stockInfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		reset:function(){
			$("#searchForm")[0].reset();
            getRows()
		},
        print :function () {
            var ids=$("#jqGrid").getGridParam("selarrrow");
            if(ids.length==0){
                layer.msg("请选择一条入库记录！")
                return;
            }
            window.open("../tmgrstockintotal/print?ids="+ids,"_blank");
            console.log(ids);
        }
	}
});
function beforeSelectRow()
{
    $("#jqGrid").jqGrid('resetSelection');
    return(true);
}
function editNumber(rowId,gid) {
    $('#'+gid).editRow(rowId);
    // alert(gid);

}
function saveNumber(rowId,gid) {
   var ele=$('#'+gid);
    ele.saveRow(rowId);
   var rowData= ele.getRowData(rowId);
   var newAmount= rowData.amount;
    var result=isNaN(newAmount);
    if(result){
        layer.msg("请输入数字！");
        ele.editRow(rowId);
        return;
    }
    var number= parseInt(newAmount);
    if(number<0){
        layer.msg("入库数量不能小于0");
        ele.editRow(rowId);
        return;
    }
    $.ajax({
        type:'get',
        url:'../tmgrstockintotal/updateAmount',
        data:rowData,
        success:function (data) {
            layer.msg(data.msg);
             ele.trigger("reloadGrid")
        }
    })

    // alert(gid);

}