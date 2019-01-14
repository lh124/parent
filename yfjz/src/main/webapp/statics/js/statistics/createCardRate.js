$(function () {
    $("#jqGrid").jqGrid({
        //url: '../createCard/inoculationRate',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'committeeId', key: true, hidden: true },
			{ label: '区域划分名称', name: 'committeeName', width: 30},
			{ label: '儿童数', name: 'childCount', width: 50 ,align : "center"},
			{ label: '建卡数', name: 'createCardCount', width: 60 ,align : "center"},
			{ label: '建卡率', name: 'createCardRate', width: 50 ,align : "center"},
			{ label: '及时数', name: 'timelyNumber', width: 60 ,align : "center"},
			{ label: '及时率', name: 'timelyRate', width: 60}
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: -1,
		//rowList : [10,30,50],
       // rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        //multiselect: true,
       // pager: "#jqGridPager",
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

    vm.loadInfoStatusData();//个案状态
    vm.loadCommiteeData();//行政村
    $("#createCardDateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    $("#createCardDateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    //初始化下拉框
    $('#chilCommittees').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择行政村/居委会',
        // actionsBox:true,
        search:false,
    });

    $("#birthDayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.birthDayStart = $("#birthDayStart").val();
    });
    $("#birthDayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.birthDayEnd = $("#birthDayEnd").val();
    });

    // 行政村/居委会拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilCommitteeIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#chilCommitteeIdParent .open input').val();
        var hunt = $("#chilCommittees");
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
            var value = null;
            if("undefined" != typeof vm){
                value = vm.chilCommittee;
            }
            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].name.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
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
            if("undefined" != typeof vm){
                value = vm.chilCommittee;
            }
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });
});
//vue
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
        chilCommittee:null
	},
	methods: {
		query: function () {
			vm.reload();
		},
        excel: function (event) {
            window.location.href='../createCard/childCardRateExcel?'+$("#unInoNoteForm").serialize();
		},
        print:function () {
		    $("#unprintdiv").hide();
            window.focus();
            window.print();
            $("#unprintdiv").show();
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			var formdata = $("#unInoNoteForm").serialize();
            $("#jqGrid").jqGrid('clearGridData');  //清空表格
            $("#jqGrid").jqGrid('setGridParam',{
                url: '../createCard/childCardRate',
                page:page,
                postData : formdata
            }).trigger("reloadGrid");
		},
        //个案状态
        loadInfoStatusData:function(){
            var param = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_info_status',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.data;
                    $.each(seldata, function (i, n) {
                        $("#infostatus").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
                    })
                    $("#infostatus").selectpicker('refresh');
                }
            });
        },
        //行政村
        loadCommiteeData:function(){
            var param = new Array();
            $.ajax({
                url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    if("undefined" != typeof vm){
                        vm.chilCommittee = seldata;
                    }
                    $.each(seldata, function (i, n) {
                        $("#chilCommittees").append(" <option value=\"" + n.code + "\">" + n.name + "</option>");
                    })
                    $("#chilCommittees").selectpicker('refresh');
                }
            });
        }

	}
});