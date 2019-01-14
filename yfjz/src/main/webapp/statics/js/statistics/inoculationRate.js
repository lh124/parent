$(function () {
    $("#jqGrid").jqGrid({
        //url: '../inoStatistics/inoculationRate',
        datatype: "json",
        colModel: [
            { label: '疫苗', name: 'planName', width: 130, key: true },
            { label: '剂次', name: 'agentTimes', width: 50 ,align : "center"},
            { label: '应种数', name: 'yinCount', width: 60 ,align : "center"},
            { label: '实种数', name: 'factCount', width: 50 ,align : "center"},
            { label: '接种率', name: 'inoculateRate', width: 60 ,align : "center" ,formatter : formatterRate},
            { label: '及时数', name: 'timelyCount', width: 60 ,align : "center"},
            { label: '及时率', name: 'timelyRate', width: 60,align : "center" ,formatter : formatterRate},
            { label: '合格数', name: 'qualifiedCount', width: 60,align : "center" },
            { label: '合格率', name: 'qualifiedRate', width: 60 ,align : "center",formatter : formatterRate},
            { label: '间短接种数', name: 'shortCount', width: 80 ,align : "center"},
            { label: '间短接种率', name: 'shortRate', width: 80,align : "center",formatter : formatterRate},
            { label: '超期数', name: 'outQualifiedCount', width: 60 ,align : "center",formatter : formatterOut},
            { label: '超期率', name: 'outQualifiedRate', width: 60 ,align : "center",formatter : formatterRate},
            { label: '应种未种数', name: 'yinweiCount', width: 80 ,align : "center" ,formatter : formatterShould},
            { label: '应种未种率', name: 'yinweiRate', width: 80 ,align : "center",formatter: formatterRate}
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
    vm.loadBios();//疫苗类别
    vm.residence();//居住属性
    vm.chilaccount();//户籍属性
    $("#chilBirthdayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    $("#chilBirthdayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    //接种日期
    $("#inoculateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    $("#inoculateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    // var inoculateStart = $("#inoculateStart").val();
    // var inoculateEnd = $("#inoculateEnd").val();
    vm.pinyincommsreach();
});
//查询儿童详情
function showChilds(name,classId,agentTime,selectType){//应种未种孩童弹窗查询
    var text = '应种未种';
    if (selectType == 'out'){
        text = '超期接种';
    }
    layer.open({
        type: 1,
        area: ['1010px', '700px'],
        title:name+'-第'+agentTime+'剂次'+text+'儿童信息',
        content:$("#childInfoView")
    });
    selectChildInfo(selectType,classId,agentTime);
}
/*格式化，添加百分号*/
function formatterRate(value, grid, rows, state) {
    if (value != null && !isNaN(value)) {
        return '<span >'+value+"%" +'</span>';
    }else {
        return "";
    }
}
//应种未种超链接格式化
function formatterShould(value, grid, rows, state){//应种未种数大于0时显示超链接可以查看具体应种未种孩童

    if(value>0 && rows.planName.indexOf("汇总") == -1){
        return '<a href="javascript:showChilds(\''+rows.planName+'\',\''+rows.planId+'\',\''+rows.agentTimes+'\',\'should\');">'+value+'</a>';
    }else{
        return value;
    }
}
//逾期未种超链接格式化
function formatterOut(value, grid, rows, state){//应种未种数大于0时显示超链接可以查看具体应种未种孩童
    if(value>0 && rows.planName.indexOf("汇总") == -1){
        return '<a href="javascript:showChilds(\''+rows.planName+'\',\''+rows.planId+'\',\''+rows.agentTimes+'\',\'out\');">'+value+'</a>';
    }else{
        return value;
    }
}

//vue
var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        chilCommittee:null,
        biotypes:null
    },
    methods: {
        query: function () {
            vm.reload();
        },
        excel: function (event) {
            window.location.href='../inoStatistics/inoculationRateExcel?'+$("#unInoNoteForm").serialize();
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
                url: '../inoStatistics/inoculationRate',
                // page:page,
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
        },
        pinyincommsreach:function(){
            //初始化下拉框
            $('#chilCommittees').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择行政村/居委会',
                // actionsBox:true,
                search:false,
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
        },
        //疫苗类别拼音 暂时不用
        pinyinbiosreach:function(){
            //初始化下拉框
            $('#biotypesId').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择疫苗类别',
                // actionsBox:true,
                search:false,
            });
            // 行政村/居委会拼音搜索
            //选择得到搜索栏input,松开按键后触发事件
            $("#biotypesId").find('.bs-searchbox').find('input').keyup(function (event) {
                //键入的值
                var inputManagerName = $('#biotypesId .open input').val();
                var hunt = $("#biotypes");
                //判定键入的值不为空,才调用ajax
                if (inputManagerName != '') {
                    hunt.empty();
                    var value = null;
                    if("undefined" != typeof vm){
                        value = vm.biotypes;
                    }
                    var con = '<option value=""></option>';
                    var reg = new RegExp("^[A-Za-z]+$");
                    if(reg.test(inputManagerName)){
                        for (var i = 0; i < value.length; i++) {
                            if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                                con += '<option  value="' + value[i].id + '">' + value[i].name + '</option>';
                            }
                        }
                    }else{
                        for (var i = 0; i < value.length; i++) {
                            if(value[i].pinyinInitials != null && value[i].name.indexOf(inputManagerName) == 0){
                                con += '<option  value="' + value[i].id + '">' + value[i].name + '</option>';
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
                        value = vm.biotypes;
                    }
                    var con = '<option value=""></option>';
                    for (var i = 0; i < value.length; i++) {
                        con += '<option  value="' + value[i].id + '">' + value[i].name + '</option>';
                    }
                    hunt.append(con);
                    hunt.selectpicker('refresh');
                    return false;
                }
            });
        },
        //户籍属性
        chilaccount:function(){
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=movetype_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='<option  value=""></option>';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#chilAccount").html(con);
                }
            });
        },
        //居住属性
        residence:function(){
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_residence_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='<option  value=""></option>';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#chilResidence").html(con);
                }
            });
        },
        //疫苗类别
        loadBios:function(){
            var param = new Array();
            $.ajax({
                url: '../truledic/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    if("undefined" != typeof vm){
                        vm.biotypes = seldata;
                    }
                    $.each(seldata, function (i, n) {
                        $("#biotypes").append(" <option value=\"" + n.id + "\">" + n.name + "</option>");
                    })
                    $("#biotypes").selectpicker('refresh');
                }
            });
        }
    }
});