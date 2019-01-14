$(function () {

    $("#jqGrid").jqGrid({
        url: '../tchildinoculate/uploadRecord',
        datatype: "json",
        colModel: [			
			{ label: '儿童编码', name: 'chil_code', width: 170, key: true },
			{ label: '姓名', name: 'chil_name', width: 80 },
			{ label: '性别', name: 'chil_sex', width: 60,
                formatter:function (value,row,index) {
                    return value == 1?'男':'女';
                }
            },
			{ label: '出生日期', name: 'chil_birthday', width: 150,
                formatter:function (value,row,index) {
                    return value.substring(0,10);
                }
            },
			{ label: '母亲姓名', name: 'chil_mother', width: 80 },
			{ label: '父亲姓名', name: 'chil_father', width: 80 },
            { label: '疫苗名称', name: 'bio_name', width: 160 },
            { label: '疫苗批号', name: 'inoc_batchno', width: 110 },
			{ label: '剂次', name: 'inoc_time', width: 80 },
			{ label: '接种时间', name: 'inoc_date', width: 180
				/*formatter:function (value,row,index) {
					return value.substring(0,10);
				}*/
			},
			{ label: '接种护士', name: 'inoc_nurse', width: 180
			}
        ],
		viewrecords: true,
        height: 520,
        rowNum: 30,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 35,
        autowidth:true,
		shrinkToFit:false,
		autoScroll: true,
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
        	/* */
			/*$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });*/
        },
		onSelectRow:function(rowid,iRow,iCol,e){



		}
    });

    $("#inoculateDateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.inoculateDateStart = $("#inoculateDateStart").val();
    });
    $("#inoculateDateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.inoculateDateEnd = $("#inoculateDateEnd").val();
    });


});


var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			chilCode: null,
			chilName:null,

            inoculateDateStart:null,
            inoculateDateEnd:null,


		},
		showList: true,
		title: null,
		tChildInfo: {}
	},
	methods: {
		query: function () {
           vm.reload();
		},
		reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:vm.q,
                page:page
            }).trigger("reloadGrid");
		}

	}
});
//选择一条记录
function getSelectedRow_child() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
   /* if(!rowKey){
        alert("请选择一条记录");
        return ;
    }*/

    return rowKey;
}
//选择一条记录
function getSelectedRow_ino() {
    var grid = $("#historyTable");
    var rowKey = grid.getGridParam("selrow");
    /*if(!rowKey){
        alert("请选择一条记录");
        return ;
    }*/

    return rowKey;
}


