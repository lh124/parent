$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchilddownload/list',
        datatype: "json",
        subGrid : true,
        colModel: [			
			{ label: 'id', name: 'id', width: 50,hidden:true, key: true },
            { label: '儿童编码', name: 'neboCode', width: 140,
                formatter:function updateChildFormatter(cellValue, options, rowdata, action){
                    /*return   "<a href=\"javascript:void(0);\" onclick=\"updateChild(" + cellValue + ")\">"+cellValue+"</a>";*/
                    return "<a href=\"javascript:void(0);\" onclick='updateChild("+JSON.stringify(rowdata).replace(/"/g, '&quot;')+")'>"+cellValue+"</a>";
                }
            },
            { label: '姓名', name: 'neboName', width: 80 },
            { label: '性别', name: 'neboSex', width: 40 ,
				formatter:function (value) {
					if(value==1){
						return "男";
					}else{
						return "女";
					}
                }
			},
            { label: '出生时间', name: 'neboBirthtime', width: 150 },
            { label: '出生体重', name: 'neboWeight', width: 50 },
            { label: '母亲姓名', name: 'neboMother', width: 80 },
           /* { label: '母亲电话', name: 'neboMotherTel', width: 80 },*/
            { label: '父亲姓名', name: 'neboFather', width: 80 },
            /*{ label: '父亲电话', name: 'neboFatherTel', width: 80 },*/
            /*{ label: '民族', name: 'neboNatiId', width: 80 },*/
           /* { label: '乙肝未种原因', name: 'nebiHepbReason', width: 80 },*/
			/*{ label: '新增时间', name: 'neboAdddate', width: 80 },*/
			{ label: '现住址居委会', name: 'neboAddressCommunity', width: 80 }, 			
			{ label: '现住址乡镇名称', name: 'neboAddressDepaIdName', width: 80 }, 			
			/*{ label: '现住址乡镇', name: 'neboAddressHabiId', width: 80 },*/
			{ label: '出生医院', name: 'neboAdduser', width: 100 },
			{ label: '父亲手机', name: 'neboFatherTel', width: 100 },
			{ label: '母亲手机', name: 'neboMotherTel', width: 100 },
			{ label: '电话', name: 'neboTel', width: 100 },
			{ label: '户籍地居委会', name: 'neboHabiaddressCommunity', width: 130 },

			{ label: '下载时间', name: 'createtime', width: 80 }
        ],
		viewrecords: true,
        height: 750,
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
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
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
                url: '../tchildinoculate/list?chilCode='+row_id,
                datatype: "json",
                colModel: [
                    { label: 'id', name: 'id', width: 50, key: true,hidden:true },
                    { label: '疫苗名称', name: 'inocBactCode', width: 80 },
                    { label: '接种剂次', name: 'inocTime', width: 80 },
                    { label: '接种日期', name: 'inocDate', width: 80 },
                    { label: '疫苗批号', name: 'inocBatchno', width: 80 },
                    { label: '接种部位', name: 'inocInplId', width: 80 },
                    { label: '生产厂家', name: 'inocCorpCode', width: 80 }

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
                viewrecords: true,
                height: "100%",
                rowNum: 10,
                rowList : [10,30,50]
            });
        }

    });
    $("#neboBirthtimeStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        newBornVm.q.neboBirthtimeStart = $("#neboBirthtimeStart").val();
    });
    $("#neboBirthtimeEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        newBornVm.q.neboBirthtimeEnd = $("#neboBirthtimeEnd").val();
    });
});

var newBornVm = new Vue({
	el:'#rrapp',

	data:{
        q:{
            neboBirthtimeStart:null,
            neboBirthtimeEnd:null
        },
		showList: true,
		title: null,
		tChildDownload: {}
	},
	methods: {
		query: function () {
            newBornVm.reload();
		},
		add: function(){
            newBornVm.showList = false;
            newBornVm.title = "新增";
            newBornVm.tChildDownload = {};
		},
		update: function (chilCode) {
			var chilCode = chilCode;
			if(chilCode == null){
				return ;
			}
			//vm.showList = false;
            //vm.title = "修改";
            
           // vm.getInfo(id)
            $("#chilLeaveremark").attr({"disabled":"disabled"});
            //vm.getInfo(chilCode);
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '修改',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ["98%", "98%"],
                content: '../child/childEdit.html?chilCode=' + chilCode
            });
		},
		saveOrUpdate: function (event) {
			var url = newBornVm.tChildDownload.id == null ? "../tchilddownload/save" : "../tchilddownload/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(newBornVm.tChildDownload),
                contentType:'application/json;charset=UTF-8',
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
                            newBornVm.reload();
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
				    url: "../tchilddownload/delete",
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
			$.get("../tchilddownload/info/"+id, function(r){
                newBornVm.tChildDownload = r.tChildDownload;
            });
		},
		reload: function (event) {
            newBornVm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:newBornVm.q,
                page:page
            }).trigger("reloadGrid");
		},
        downloadNewBorn: function () {
            layer.confirm('确定要下载新生儿么？', function(index) {
                layer.msg('正在下载...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 1000000});
                $.ajax({
                    type: "POST",
                    url: "../provincePlatform/saveNewBorn?count=0",
                    dataType: "json",
                    success: function (r) {
                        console.log(r);
                        if (r.code == 0) {
                            layer.msg("下载成功");
                            newBornVm.showList = true;
                            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                            $("#jqGrid").jqGrid('setGridParam', {
                                postData: {},
                                page: page
                            }).trigger("reloadGrid");

                        } else {
                            layer.msg(r.msg);
                        }
                    }
                });
                layer.close(index);
            });
        }
	}
});

function updateChild(rowdata) {
    newBornVm.update(rowdata.neboCode);
}