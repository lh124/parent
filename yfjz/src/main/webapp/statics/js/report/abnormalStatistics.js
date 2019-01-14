$(function () {

    abVm.loadTimePicker();
    abVm.loadInfoStatusData();//个案状态
    abVm.loadCommiteeData();//行政村
    abVm.loadAbnormal();//异常情况
    abVm.residence();//居住属性
    abVm.chilaccount();//户籍属性
    abVm.LoadSchools();//学校
    abVm.pinyincommsearch();//行政村拼音查询
    var now = new Date();
    var preMonth = new Date(getMonthBeforeFormatAndDay(-1,'-', now.getDate()));//向前推一个月
    var date=yfjzformatter(preMonth);
    var nowDate=yfjzformatter(now);
    $("#inoculateStart").val(date);
    $("#inoculateEnd").val(nowDate);
    $("#abnormalGrid").jqGrid({
        url: '../abnormal/list',
        datatype: "json",
        colModel: [
            {label: '儿童编码', name: 'childCode', width: 170, key: true},
            {label: '姓名', name: 'childName', width: 70},
            {label: '接种证号', name: 'cardNumber', width: 120},
            {label: '异常状态', name: 'abStatus', width: 80},
            {label: '异常详细情况', name: 'abnormalDetail', width: 130},
            {label: '性别', name: 'sex', width: 50},
            {label: '出生日期', name: 'birthTime', width: 160},
            {label: '居委会|行政村', name: 'committee', width: 80},
            {label: '入学|入托机构', name: 'school', width: 80},
            {label: '居住属性', name: 'residenceStatus', width: 80},
            {label: '现居地址', name: 'currentAddress', width: 180},
            {label: '户籍地址', name: 'residenceAddress', width: 180},
            {label: '联系电话', name: 'chilMobile', width: 120},
        ],
        viewrecords: true,
        height: 'auto',
        //width:1200,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 45,
        autowidth: true,
        multiselect: false,
        pager: "#abnormalPage",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page:"page",
            rows:"limit",
            order: "order",
            sidx: "sidx"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            // $("#abnormalGrid").closest(".ui-jqgrid-bdiv").css("overflow-x", "hidden");

        },
        onSelectRow: function (rowid, status) {
        }
    })
})

//vue 初始化
var abVm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        chilCommittee:null
    },
    methods: {

        query: function () {
            var param=abVm.getParam();
            //根据查询条件查询结果
            $('#abnormalGrid').jqGrid('clearGridData');
            //清空查询条件
            var postData = $('#abnormalGrid').jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });
            $("#abnormalGrid").jqGrid('setGridParam', {
                url: "../abnormal/list?&_" + (new Date()).getTime(),
                postData: param,
                page:1,
                mtype: 'POST'
            }).trigger("reloadGrid");
        },
        excel:function(){
            //出生日期开始
            var chilBirthdayStart = $("#chilBirthdayStart").val();
            var chilBirthdayEnd  = $("#chilBirthdayEnd").val();
            // 接种日期
            var inoculateStart = $("#inoculateStart").val();
            var inoculateEnd = $("#inoculateEnd").val();
            // 居住属性:
            var chilResidences = $('#chilResidence').selectpicker('val')==null?"":$('#chilResidence').selectpicker('val');
            //居委会/行政村:
            var chilCommittees = $('#chilCommittees').selectpicker('val')==null?"":$('#chilCommittees').selectpicker('val');
            // 个案状态:
            var infostatus = $('#infostatus').selectpicker('val')==null?"":$('#infostatus').selectpicker('val');

            //入学|入托机构:
            var schools = $('#school').selectpicker('val')==null?"":$('#school').selectpicker('val');
            //异常状态
            var abnormalStatus=$('#abnormal').selectpicker('val');

            layer.confirm("确定要下载生成的报表文件吗？",function (index) {
                window.location.href="../abnormal/excel?birthStart="+chilBirthdayStart+"&birthEnd="+chilBirthdayEnd
                    +"&inoculateStart="+inoculateStart+"&inoculateEnd="+inoculateEnd
                    +"&chilCommittees="+chilCommittees+"&chilResidences="+chilResidences+"&infostatus="+infostatus+"&schools="+schools+"&abnormalStatus="+abnormalStatus+"&page=1&limit=100000";
                layer.close(index);
            });
        },
        print:function(){
            //出生日期开始
            var chilBirthdayStart = $("#chilBirthdayStart").val();
            var chilBirthdayEnd  = $("#chilBirthdayEnd").val();
            // 接种日期
            var inoculateStart = $("#inoculateStart").val();
            var inoculateEnd = $("#inoculateEnd").val();
            // 居住属性:
            var chilResidences = $('#chilResidence').selectpicker('val')==null?"":$('#chilResidence').selectpicker('val');
            //居委会/行政村:
            var chilCommittees = $('#chilCommittees').selectpicker('val')==null?"":$('#chilCommittees').selectpicker('val');
            // 个案状态:
            var infostatus = $('#infostatus').selectpicker('val')==null?"":$('#infostatus').selectpicker('val');

            //入学|入托机构:
            var schools = $('#school').selectpicker('val')==null?"":$('#school').selectpicker('val');
            //异常状态
            var abnormalStatus=$('#abnormal').selectpicker('val');

            window.open("../abnormal/print?birthStart="+chilBirthdayStart+"&birthEnd="+chilBirthdayEnd
                +"&inoculateStart="+inoculateStart+"&inoculateEnd="+inoculateEnd
                +"&chilCommittees="+chilCommittees+"&chilResidences="+chilResidences+"&infostatus="+infostatus+"&schools="+schools+"&abnormalStatus="+abnormalStatus+"&page=1&limit=100000","_blank");
        },
        //封装查询条件
        getParam:function () {
            //出生日期开始
            var chilBirthdayStart = $("#chilBirthdayStart").val();
            var chilBirthdayEnd  = $("#chilBirthdayEnd").val();
            // 接种日期
            var inoculateStart = $("#inoculateStart").val();
            var inoculateEnd = $("#inoculateEnd").val();
            //居委会/行政村:
            var chilCommittees = $('#chilCommittees').selectpicker('val');
            // 居住属性:
            var chilResidences = $('#chilResidence').selectpicker('val');
            // 个案状态:
            var infostatus = $('#infostatus').selectpicker('val');
            //入学|入托机构:
            var schools = $('#school').selectpicker('val');

            //异常状态
            var abnormalStatus=$('#abnormal').selectpicker('val');

            var param = {};
            param.birthStart = chilBirthdayStart;
            param.birthEnd = chilBirthdayEnd;
            param.inoculateStart = inoculateStart;
            param.inoculateEnd = inoculateEnd;
            if(chilCommittees!=""&&chilCommittees!=undefined&&chilCommittees!=null){
                param.chilCommittees = chilCommittees;
            }
            if(chilResidences!=""&&chilResidences!=undefined&&chilResidences!=null){
                param.chilResidences = chilResidences;
            }
            if(infostatus!=""&&infostatus!=undefined&&infostatus!=null){
                param.infostatus = infostatus;
            }
            if(schools!=""&&schools!=undefined&&schools!=null){
                param.schools = schools;
            }
            param.abnormalStatus = abnormalStatus;
            return param;
        },
        reload: function (event) {
            abVm.showList = true;
        },
        //初始化时间控件
        loadTimePicker:function(){
            //出生日期
            $("#chilBirthdayStart").datetimepicker({
                language: 'zh-CN',//显示中文
                format: 'yyyy-mm-dd',//显示格式
                minView: "month",//设置只显示到月份
                autoclose:true, //选择完时间后自动关闭，默认false（不关闭）
                initialDate: new Date(),
                forceParse:true,
                autoclose: true,//选中自动关闭
                todayBtn: true,//显示今日按钮
                locale: moment.locale('zh-cn')
            });
            $("#chilBirthdayEnd").datetimepicker({
                language: 'zh-CN',//显示中文
                format: 'yyyy-mm-dd',//显示格式
                minView: "month",//设置只显示到月份
                autoclose:true, //选择完时间后自动关闭，默认false（不关闭）
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
                autoclose:true, //选择完时间后自动关闭，默认false（不关闭）
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
                autoclose:true, //选择完时间后自动关闭，默认false（不关闭）
                initialDate: new Date(),
                forceParse:true,
                autoclose: true,//选中自动关闭
                todayBtn: true,//显示今日按钮
                locale: moment.locale('zh-cn')
            });
        },
        //个案状态
        loadInfoStatusData:function(){
            $.ajax({
                url: '../child/listdiccode?ttype=child_info_status',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.data;
                    $.each(seldata, function (i, n) {
                        $("#infostatus").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
                    });
                    $("#infostatus").selectpicker('refresh');
                }
            });
        },
        pinyincommsearch:function(){
            //初始化下拉框
            $('#chilCommittees').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择疫苗',
                // actionsBox:true,
                search:false,
            });
            //选择得到搜索栏input,松开按键后触发事件
            $("#chilCommitteeIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
                //键入的值
                var inputManagerName = $('#chilCommitteeIdParent .open input').val();
                var hunt = $("#chilCommittees");
                //判定键入的值不为空,才调用ajax
                if (inputManagerName != '') {
                    hunt.empty();
                    var value = null;
                    if("undefined" != typeof abVm){
                        value = abVm.chilCommittee;
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
                    if("undefined" != typeof abVm){
                        value = abVm.chilCommittee;
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

        //行政村
        loadCommiteeData:function(){
            $.ajax({
                url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    if("undefined" != typeof abVm){
                        abVm.chilCommittee = seldata;
                    }
                    $.each(seldata, function (i, n) {
                        $("#chilCommittees").append(" <option value=\"" + n.code + "\">" + n.name + "</option>");
                    });
                    $("#chilCommittees").selectpicker('refresh');
                }
            });
        },
        //户籍属性
        chilaccount:function(){
            $.ajax({
                url: '../child/listdiccode?ttype=movetype_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#chilAccount").html(con);
                }
            });
        },
        //居住属性
        residence:function(){
            $.ajax({
                url: '../child/listdiccode?ttype=child_residence_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    $.each(results.data, function (i, n) {
                        $("#chilResidence").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
                    });
                    $("#chilResidence").selectpicker('refresh');
                }
            });

        },
        //学校
        LoadSchools:function(){
            $.ajax({
                url: '../tbaseschool/list?org_id='+orgId+'&page=1&limit=10000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var sc=results.page.list;
                    $.each(sc, function (i, n) {
                        $("#school").append(" <option value=\"" + n.code + "\">" + n.name + "</option>");
                    });
                    $("#school").selectpicker('refresh');
                }
            });

        },
        //异常状态
        loadAbnormal:function(){
            var param = new Array();
            param.push({"id":"","name":""});
            param.push({"id":"register","name":"取号未登记"});
            param.push({"id":"inoculate","name":"登记未接种"});
            param.push({"id":"inoculateNotAbserve","name":"接种未留观"});
            param.push({"id":"observe","name":"留观未完成"});
            $.each(param, function (i, n) {
                $("#abnormal").append(" <option value=\"" + n.id + "\">" + n.name + "</option>");
            });
            $("#abnormal").selectpicker('refresh');
        },
        reset:function () {
            //出生日期开始
            $("#chilBirthdayStart").val("");
            $("#chilBirthdayEnd").val("");
            // 接种日期
            $("#inoculateStart").val("");
            $("#inoculateEnd").val("");

            $('#abnormal').selectpicker('val', "");
            $("#abnormal").selectpicker('refresh');

            $('#chilResidence').selectpicker('val', "");
            $("#chilResidence").selectpicker('refresh');

            $('#chilCommittees').selectpicker('val', "");
            $("#chilCommittees").selectpicker('refresh');

            $('#infostatus').selectpicker('val', "");
            $("#infostatus").selectpicker('refresh');

            $('#school').selectpicker('val', "");
            $("#school").selectpicker('refresh');
        }
    }
});

$(function () {


});

/**
 * 使用jquery的inArray方法判断元素是否存在于数组中
 * @param {Object} arr 数组
 * @param {Object} value 元素值
 */
function isInArray(arr,value){
    var index = $.inArray(value,arr);
    if(index >= 0){
        return true;
    }
    return false;
}


function formatAddr(val){
    if(val){
        if (isJSONtest(val)) {  //判读是否是JSON对象
            var obj = JSON.parse(val);
            return    obj.position;
        }else{
            return val;
        }
    }else{
        return '';
    }
}


function formatValue(val) {
    if (val) {
        return val;
    } else {
        return '';
    }
}

//疫苗统计使用
function defaultValue(val) {
    if (val) {
        return val;
    } else {
        return 0;
    }
}

function comitteeNameValue(val) {
    if (val) {
        return val;
    } else {
        return '暂无归属';
    }
}
function isJSONtest(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            console.log('转换成功：'+obj);
            return true;
        } catch(e) {
            console.log('error：'+str+'!!!'+e);
            return false;
        }
    }
    console.log('It is not a string!');
}





function yfjzformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function refreshAll() {
    $("#infostatus").selectpicker('refresh');
    $("#school").selectpicker('refresh');
    $("#abnormal").selectpicker('refresh');
    $("#chilResidence").selectpicker('refresh');
    $("#chilCommittees").selectpicker('refresh');
}