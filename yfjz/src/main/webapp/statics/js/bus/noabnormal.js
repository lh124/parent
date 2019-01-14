// tchildabnormal
$(function () {
    loadAbnormalData();//异常反应
    getAefiBactCode();//疫苗
    loadinfectionData();//传染病
    loadtabooData();//接种禁忌
    $("#chilNamett").val(decodeURI(chilName));
    $("#chilCodett").val(decodeURI(chilCode));
})
function getUrlVars() {
    var vars = [],
        hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < hashes.length; i++) {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}
var chilCode = getUrlVars()["chilCode"];
var chilName = getUrlVars()["chilName"];

//异常反应
var vmtabnormal = new Vue({
    data: {
        tChildAbnormal: {
            chilCode: '',
            aefiBactCode:'',
            aefiCode:'',
            aefiDate:'',
        }
    }
})
//传染病
var vmInfection = new Vue({
    data: {
        tChildInfection: {
            chilCode: '',
            infeCode:'',
            infeDate:'',
        }
    }
})
//过敏史
var vmAllergy = new Vue({
    data: {
        tChildAllergy: {
            chilCode: '',
            describes:'',
            checkDate:'',
        }
    }
})
var vmTaboo = new Vue({
    data: {
        tChildTaboo: {
            chilCode: '',
            istaCode:'',
            istaCheckDate:'',
        }
    }
})

function savetaboo() {
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var istaCheckDate=$("#istaCheckDate").val();
    var istaCode=$("#istaCode option:selected").val();
    if (istaCode==null || istaCode==""){
        layer.msg("接种禁忌不能不能为空");
        return false;
    }
    if(istaCheckDate==null || istaCheckDate ==""){
        layer.msg("时间不能为空");
        return false;
    }
    Vue.set(vmTaboo.tChildTaboo, "istaCheckDate", istaCheckDate);
    Vue.set(vmTaboo.tChildTaboo, "istaCode", istaCode);
    Vue.set(vmTaboo.tChildTaboo, "chilCode", chilCode);
    layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
    console.log(vmTaboo.tChildTaboo);
    $.ajax({
        type: "POST",
        url: "../tchildtaboo/save",
        data: JSON.stringify(vmTaboo.tChildTaboo),
        contentType:'application/json;charset=UTF-8',
        success: function(r){
            if(r.code === 0){
                layer.msg("操作成功");
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);  // 关闭layer
                parent.$("#selectResultTable").trigger("reloadGrid");
            }else{
                alert(r.msg);
            }
        }
    });
}

//过敏史保存
function saveallergy() {
    var  checkDate=$("#checkDate").val();
    var  describes=$("#describes").val();
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
    }
    if (describes==null || describes==""){
        layer.msg("过敏史不能为空");
        return false;
    }
    if(checkDate==null || checkDate ==""){
        layer.msg("时间不能为空");
        return false;
    }
    Vue.set(vmAllergy.tChildAllergy, "checkDate", checkDate);
    Vue.set(vmAllergy.tChildAllergy, "describes", describes);
    Vue.set(vmAllergy.tChildAllergy, "chilCode", chilCode);
    layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
    $.ajax({
        type: "POST",
        url: "../tchildallergy/save",
        data: JSON.stringify(vmAllergy.tChildAllergy),
        contentType:'application/json;charset=UTF-8',
        success: function(r){
            if(r.code === 0){
                layer.msg("操作成功");
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);  // 关闭layer
                parent.$("#selectResultTable").trigger("reloadGrid");
            }else{
                alert(r.msg);
            }
        }
    });
}

//传染病保存
function saveinfection() {
    var infeDate = $("#infeDate").val();
    var infeCode=$("#infeCode option:selected").val();
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    if (infeCode==null || infeCode==""){
        layer.msg("传染病不能为空");
        return false;
    }
    if(infeDate==null || infeDate==""){
        layer.msg("发病日期不能为空");
        return false ;
    }
    Vue.set(vmInfection.tChildInfection,"infeDate", infeDate);
    Vue.set(vmInfection.tChildInfection,"infeCode", infeCode);
    Vue.set(vmInfection.tChildInfection, "chilCode", chilCode);
    console.log(vmInfection.tChildInfection);
    layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
    $.ajax({
        type: "POST",
        url: "../tchildinfection/save",
        data: JSON.stringify(vmInfection.tChildInfection),
        contentType:'application/json;charset=UTF-8',
        success: function(r){
            if(r.code === 0){
                layer.msg("操作成功");
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);  // 关闭layer
                parent.$("#selectResultTable").trigger("reloadGrid");
            }else{
                alert(r.msg);
            }
        }
    });
}


//异常反应保存
function  saveabnormal() {
    var bacterin = $("#aefiBactCode").val();

    if (bacterin == null || bacterin=="") {
        layer.msg("请选择疫苗...");
        return false;
    }
    var aefiDate = $("#aefiDate").val();
    var bioCode = $("#aefiBactCode option:selected").val();
    var aefiCode=$("#aefiCode option:selected").val();
    if (aefiDate==null || aefiDate==""){
        layer.msg("反应日期不能为空");
        return false;
    }
    if(aefiCode==null || aefiCode==""){
        layer.msg("反应症状不能为空");
        return false;
    }
    Vue.set(vmtabnormal.tChildAbnormal, "aefiDate", aefiDate);
    Vue.set(vmtabnormal.tChildAbnormal, "chilCode", chilCode);
    Vue.set(vmtabnormal.tChildAbnormal, "aefiBactCode", bioCode);
    Vue.set(vmtabnormal.tChildAbnormal, "aefiCode", aefiCode);
    layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
    $.ajax({
        type: "POST",
        url: "../tchildabnormal/save",
        data: JSON.stringify(vmtabnormal.tChildAbnormal),
        contentType:'application/json;charset=UTF-8',
        success: function(r){
            if(r.code === 0){
                layer.msg("操作成功");
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);  // 关闭layer
                parent.$("#selectResultTable").trigger("reloadGrid");
            }else{
                alert(r.msg);
            }
        }
    });
}
function colsebtn() {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);  // 关闭layer
}
function loadAbnormalData(){
    $('#aefiCode').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择异常反应...',
    });
    $.ajax({
        url: '../child/listdiccode?ttype=code_AEFI',
        dataType: "json",
        type: 'POST',
        success: function (results) {

            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            // $("#aefiCode").html(con);
            $("#aefiCode").append(con);
            $("#aefiCode").selectpicker('refresh');
        }
    });
}
function getAefiBactCode () {
    //初始化下拉框
    $('#aefiBactCode').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择疫苗...',
        actionsBox:true,
        search:false,
    });
    $.ajax({
        url: '../tvacinfo/list?page=1&limit=1000',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            // vmtabnormal.aefiBactCode = results.page.list;
            var con='';
            for (var i = 0; i < results.page.list.length; i++) {
                con += '<option  value="' + results.page.list[i].bioCode + '">'+results.page.list[i].bioCnSortname+'</option>';
            }
            //$("#aefiBactCode").html(con);
            $("#aefiBactCode").append(con);
            $("#aefiBactCode").selectpicker('refresh');

        }
    });
}

function loadinfectionData(){
    $('#infeCode').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择传染病...',
    });
    $.ajax({
        url: '../child/listdiccode?ttype=infection_code',
        dataType: "json",
        type: 'POST',
        success: function (results) {

            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            // $("#infeCode").html(con);
            $("#infeCode").append(con);
            $("#infeCode").selectpicker('refresh');
        }
    });
}
//接种禁忌
function loadtabooData(){
    $('#istaCode').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择接种禁忌...',
    });
    $.ajax({
        url: '../child/listdiccode?ttype=code_avoid',
        dataType: "json",
        type: 'POST',
        success: function (results) {

            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            // $("#istaCode").html(con);
            $("#istaCode").append(con);
            $("#istaCode").selectpicker('refresh');
        }
    });
}

$("#aefiDate").datetimepicker({
    language: 'zh-CN',//显示中文
    format: 'yyyy-mm-dd hh:ii:ss',//显示格式
    minView: "month",//设置只显示到月份
    initialDate: new Date(),
    forceParse:true,
    autoclose: true,//选中自动关闭
    todayBtn: true,//显示今日按钮
    locale: moment.locale('zh-cn')
})
//     .on('hide',function(e) {
//     $('#abnormal').data('bootstrapValidator')
//         // .updateStatus('aefiDate', 'NOT_VALIDATED',null)
//         .validateField('aefiDate');
// });

$("#infeDate").datetimepicker({
    language: 'zh-CN',//显示中文
    format: 'yyyy-mm-dd hh:ii:ss',//显示格式
    minView: "month",//设置只显示到月份
    initialDate: new Date(),
    forceParse:true,
    autoclose: true,//选中自动关闭
    todayBtn: true,//显示今日按钮
    locale: moment.locale('zh-cn')
})
//     .on('hide',function(e) {
//     $('#infection').data('bootstrapValidator')
//         // .updateStatus('infeDate', 'NOT_VALIDATED',null)
//         .validateField('infeDate');
// });


$("#checkDate").datetimepicker({
    language: 'zh-CN',//显示中文
    format: 'yyyy-mm-dd hh:ii:ss',//显示格式
    minView: "month",//设置只显示到月份
    initialDate: new Date(),
    forceParse:true,
    autoclose: true,//选中自动关闭
    todayBtn: true,//显示今日按钮
    locale: moment.locale('zh-cn')
})

$("#istaCheckDate").datetimepicker({
    language: 'zh-CN',//显示中文
    format: 'yyyy-mm-dd hh:ii:ss',//显示格式
    minView: "month",//设置只显示到月份
    initialDate: new Date(),
    forceParse:true,
    autoclose: true,//选中自动关闭
    todayBtn: true,//显示今日按钮
    locale: moment.locale('zh-cn')
})