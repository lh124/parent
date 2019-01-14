//vue 初始化
var inocLogsVM = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        chilCommittee:null,
        biotypes:null,
        batchnums:null
        /*        q:{
                    chilBirthdayStart:null,//出生日期
                    chilBirthdayEnd:null,//出生日期
                    inoculateStart:null,//接种日期
                    inoculateEnd:null,//接种日期
                    chilCommittee :null,//行政村、居委会
                    chilResidence :null,//居住属性
                    infostatus:null,//个案状态
                    biotypes:null,//疫苗类别
                    school:null//入学|入托机构
                }*/
    },
    methods: {

        query: function () {
            layer.msg('请等待统计...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
            //清空上一次的结果
            $("#inoculate_range_label").empty();
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
            //疫苗类别:
            var biotypes = $('#biotypes').selectpicker('val');
            // 疫苗接种剂次:
            var bioNos = $('#bioNos').selectpicker('val');
            //入学|入托机构:
            var schools = $('#school').selectpicker('val');
            //接种医生
            var inocDoctors = $('#inocDoctor').selectpicker('val');
            //疫苗批号
            var batchnum = $('#inocbatchno').selectpicker('val')==null?"":$('#inocbatchno').selectpicker('val');
            //赋值
            $("#inoculate_range_label").append(inoculateStart +"--"+inoculateEnd);

            var param = {};
            param.chilBirthdayStart = chilBirthdayStart;
            param.chilBirthdayEnd = chilBirthdayEnd;
            param.inoculateStart = inoculateStart;
            param.inoculateEnd = inoculateEnd;
            param.chilCommittees = chilCommittees;
            param.chilResidences = chilResidences;
            param.infostatus = infostatus;
            param.biotypes = biotypes;
            param.bioNos = bioNos;
            param.schools = schools;
            param.inocDoctors = inocDoctors;
            param.inocbatchno = batchnum;

            //根据查询条件查询结果
            $.ajax({
                url: '../inoculatelogs/queryInoculateLogs',
                data:param,
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    layer.msg("成功！");
                    var childcount = data.childcount;
                    $("#inoculate_child_count_label").html(childcount);
                    var historys = data.historys;//根据查询条件查询出接种日志
                    var inocDoses = data.inocDoses;//按照疫苗接种剂次来分组统计
                    var inocpropertys = data.inocpropertys;//按照疫苗接种属性来分组统计
                    inocLogsVM.viewInocDoses(inocDoses);
                    inocLogsVM.viewHistorys(historys);
                    inocLogsVM.viewInocpropertys(inocpropertys);

                }
            });
        },
        excel:function(){
            //清空上一次的结果
            $("#inoculate_range_label").empty();
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
            //疫苗类别:
            var biotypes = $('#biotypes').selectpicker('val')==null?"": $('#biotypes').selectpicker('val');
            // 疫苗接种剂次:
            var bioNos = $('#bioNos').selectpicker('val')==null?"":$('#bioNos').selectpicker('val');
            //接种医生
            var inocDoctors = $('#inocDoctor').selectpicker('val')==null?"":$('#inocDoctor').selectpicker('val');
            //入学|入托机构:
            var schools = $('#school').selectpicker('val')==null?"":$('#school').selectpicker('val');
            //疫苗批号
            var batchnum = $('#inocbatchno').selectpicker('val')==null?"":$('#inocbatchno').selectpicker('val');

            layer.confirm("确定要下载生成的报表文件吗？",function (index) {
                window.open("../ExcelController/ExcelInoLog?chilBirthdayStart="+chilBirthdayStart+"&chilBirthdayEnd="+chilBirthdayEnd
                    +"&inoculateStart="+inoculateStart+"&inoculateEnd="+inoculateEnd
                    +"&chilCommittees="+chilCommittees+"&chilResidences="+chilResidences+"&infostatus="+infostatus
                    +"&biotypes="+biotypes+"&bioNos="+bioNos+"&inocDoctors="+inocDoctors+"&schools="+schools+"&inocbatchno="+batchnum+"&page=1&limit=100000");
                layer.close(index);
            });
        },
        print:function(){
            //清空上一次的结果
            $("#inoculate_range_label").empty();
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
            //疫苗类别:
            var biotypes = $('#biotypes').selectpicker('val')==null?"": $('#biotypes').selectpicker('val');
            // 疫苗接种剂次:
            var bioNos = $('#bioNos').selectpicker('val')==null?"":$('#bioNos').selectpicker('val');
            //接种医生
            var inocDoctors = $('#inocDoctor').selectpicker('val')==null?"":$('#inocDoctor').selectpicker('val');
            //入学|入托机构:
            var schools = $('#school').selectpicker('val')==null?"":$('#school').selectpicker('val');
            //疫苗批号
            var batchnum = $('#inocbatchno').selectpicker('val')==null?"":$('#inocbatchno').selectpicker('val');
            window.open("../reports/InoculateLogs?chilBirthdayStart="+chilBirthdayStart+"&chilBirthdayEnd="+chilBirthdayEnd
                +"&inoculateStart="+inoculateStart+"&inoculateEnd="+inoculateEnd
                +"&chilCommittees="+chilCommittees+"&chilResidences="+chilResidences+"&infostatus="+infostatus
                +"&biotypes="+biotypes+"&bioNos="+bioNos+"&inocDoctors="+inocDoctors+"&schools="+schools+"&inocbatchno="+batchnum);
        },
        viewInocDoses: function(inocDoses) {
            $("#inoc_dose_table").html("");
            var html="";
            html += "<table style='padding-left: 50px;' border='1'>";
            html +="<tr><td style='width: 150px;'>疫苗名称</td><td>剂次1</td><td>剂次2</td><td>剂次3</td>" +
                "<td>剂次4</td><td>剂次5</td><td>剂次6</td><td>剂次7</td>" +
                "<td>剂次8</td><td>小计</td></tr>";
            var totalDoseNum = 0;
            for (var m in inocDoses){
                var obj = inocDoses[m];
                var total = parseInt(obj.剂次1)+parseInt(obj.剂次2)+parseInt(obj.剂次3)
                    + parseInt(obj.剂次4) + parseInt(obj.剂次5)+parseInt(obj.剂次6)
                    +parseInt(obj.剂次7)+parseInt(obj.剂次8);
                totalDoseNum += total;
                html += "<tr><td>" + defaultValue(obj.疫苗) +
                    "</td><td>" + defaultValue(obj.剂次1) +
                    "</td><td>" + defaultValue(obj.剂次2) +
                    "</td><td>" + defaultValue(obj.剂次3) +
                    "</td><td>" + defaultValue(obj.剂次4) +
                    "</td><td>" + defaultValue(obj.剂次5) +
                    "</td><td>" + defaultValue(obj.剂次6) +
                    "</td><td>" + defaultValue(obj.剂次7) +
                    "</td><td>" + defaultValue(obj.剂次8) +
                    "</td><td>"+total+"</td></tr>";
            }
            var total_dose1=0;
            var total_dose2=0;
            var total_dose3=0;
            var total_dose4=0;
            var total_dose5=0;
            var total_dose6=0;
            var total_dose7=0;
            var total_dose8=0;
            for (var n in inocDoses){
                var obj = inocDoses[n];
                total_dose1 += parseInt(obj.剂次1);
                total_dose2 += parseInt(obj.剂次2);
                total_dose3 += parseInt(obj.剂次3);
                total_dose4 += parseInt(obj.剂次4);
                total_dose5 += parseInt(obj.剂次5);
                total_dose6 += parseInt(obj.剂次6);
                total_dose7 += parseInt(obj.剂次7);
                total_dose8 += parseInt(obj.剂次8);
            }
            html += "<tr><td>合计</td>" +
                "<td>" + total_dose1 +
                "</td><td>" + total_dose2 +
                "</td><td>" + total_dose3 +
                "</td><td>" + total_dose4 +
                "</td><td>" + total_dose5 +
                "</td><td>" + total_dose6 +
                "</td><td>" + total_dose7 +
                "</td><td>" + total_dose8 +
                "</td><td>"+totalDoseNum+"</td></tr>";
            html+="<tr></tr>";
            html+="<tr></tr>";
            html +="</table>";
            $(html).appendTo("#inoc_dose_table");
        },
        viewHistorys:function (historys) {
            $("#committees_all_inoc_table").html("");
            var html="";
            var dataArr = new Array();
            for (var i in historys) {
                var comitteeName = historys[i].chil_committee;
                if(!isInArray(dataArr,comitteeName)){
                    dataArr.push(comitteeName);
                }
            }
            html="<h3>接种日志：</h3>";
            for (var j=0;j < dataArr.length;j++){
                var name = dataArr[j];
                html +="所在区域:"+comitteeNameValue(name)+"<br/>";
                html += "<table style='margin-bottom: 50px;width: 100%;' border='1'>";
                html +="<thead><tr>" +
                    "<td style='width: 100px;'>儿童姓名</td>" +
                    "<td  style='width: 70px;'>性别</td>" +
                    "<td  style='width: 100px;'>出生日期</td>" +
                    "<td  style='width: 100px;'>母亲姓名</td>" +
                    "<td  style='width: 100px;'>父亲姓名</td>" +
                    "<td>联系电话</td><td>手机号码</td>" +
                    "<td>居委会/行政村</td><td>居住属性</td>" +
                    "<td>户籍属性</td>" +
                    "<td style='width: 100px;'>建卡日期</td>" +
                    "<td style='width: 100px;'>在册情况</td>" +
                    "<td style='width: 200px;'>通讯地址</td>" +
                    "<td  style='width: 200px;'>接种疫苗</td>" +
                    "<td>剂次</td><td  style='width: 150px;'>生产厂家</td>" +
                    "<td style='width: 100px;' >失效期</td>" +
                    "<td style='width: 100px;'>接种日期</td>" +
                    "<td style='width: 100px;'>疫苗批号</td>" +
                    "<td style='width: 100px;'>接种途径</td>" +
                    "<td style='width: 100px;'>接种部位</td>" +
                    "<td style='width: 100px;'>接种医生</td>" +
                    "<td style='width: 200px;'>接种医院</td>" +
                    "<td style='width: 65px;'>免费</td></tr></thead>";
                for (var k in historys){
                    var obj = historys[k];
                    var cname = obj.chil_committee;
                    if(name == cname){
                        html+="<tr><td>"+ formatValue(obj.chil_name)+
                            "</td><td>"+formatValue(obj.chil_sex)+
                            "</td><td>"+formatValue(obj.chil_birthday)+"</td>" +
                            "<td>"+formatValue(obj.chil_mother)+
                            "<td>"+formatValue(obj.chil_father)+
                            "</td><td>"+formatValue(obj.chilTel)+"</td>" +
                            "<td>"+formatValue(obj.chilMobile)+
                            "<td>"+formatValue(obj.chil_committee)+
                            "<td>"+formatValue(obj.chil_residence)+
                            "<td>"+formatValue(obj.chil_account)+
                            "</td><td>"+formatValue(obj.create_card_time)+
                            "</td><td>"+formatValue(obj.chil_here)+"</td>" +
                            "<td>"+formatAddr(obj.chil_address)+"</td>" +
                            "<td>"+formatValue(obj.bio_cn_sortname)+
                            "</td><td>"+formatValue(obj.inoc_time)+"</td>" +
                            "</td><td>"+formatValue(obj.factoryname)+"</td>" +
                            "<td>"+formatValue(obj.inoc_validdate)+
                            "</td><td>"+formatValue(obj.inoc_date)+
                            "</td><td>"+formatValue(obj.inoc_batchno)+
                            "</td><td>"+formatValue(obj.spec_name)+
                            "</td><td>"+formatValue(obj.inpl_name)+
                            "</td><td>"+formatValue(obj.inoc_nurse)+
                            "</td><td>"+formatValue(obj.inoc_depa_name)+
                            "</td><td>"+formatValue(obj.inoc_free)+"</td></tr>";
                    }
                }
                html+="<tr></tr>";
                html+="<tr></tr>";
                html +="</table>";
            }
            $(html).appendTo("#committees_all_inoc_table");
        },
        viewInocpropertys:function (inocpropertys) {
            $("#inoc_property_table").html("");
            var html="";
            html += "<table style='padding-left: 50px;' border='1'>";
            html +="<tr><td>疫苗名称</td><td>基础</td><td>加强</td><td>强化</td>" +
                "<td>应急</td><td>小计</td></tr>";
            var totalDoseNum = 0;
            for (var m in inocpropertys){
                var obj = inocpropertys[m];
                var total = parseInt(obj.基础)+parseInt(obj.加强)+parseInt(obj.强化)
                    + parseInt(obj.应急);
                totalDoseNum += total;
                html += "<tr><td>" + defaultValue(obj.疫苗) +
                    "</td><td>" + defaultValue(obj.基础) +
                    "</td><td>" + defaultValue(obj.加强) +
                    "</td><td>" + defaultValue(obj.强化) +
                    "</td><td>" + defaultValue(obj.应急) +
                    "</td><td>"+total+"</td></tr>";
            }
            var total_dose1=0;
            var total_dose2=0;
            var total_dose3=0;
            var total_dose4=0;
            var total_dose5=0;
            var total_dose6=0;
            var total_dose7=0;
            var total_dose8=0;
            for (var n in inocpropertys){
                var obj = inocpropertys[n];
                total_dose1 += parseInt(obj.基础);
                total_dose2 += parseInt(obj.加强);
                total_dose3 += parseInt(obj.强化);
                total_dose4 += parseInt(obj.应急);
            }
            html += "<tr><td>合计</td>" +
                "<td>" + total_dose1 +
                "</td><td>" + total_dose2 +
                "</td><td>" + total_dose3 +
                "</td><td>" + total_dose4 +
                "</td><td>"+totalDoseNum+"</td></tr>";
            html+="<tr></tr>";
            html+="<tr></tr>";
            html +="</table>";
            $(html).appendTo("#inoc_property_table");
        },
        reload: function (event) {
            inocLogsVM.showList = true;
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
        //行政村
        loadCommiteeData:function(){
            //初始化下拉框
            $('#chilCommittees').selectpicker({
                'selectedText': 'cat',
                //noneSelectedText:'请选择行政村/居委会',
                //actionsBox:true,
                search:false,
            });
            $.ajax({
                url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    inocLogsVM.chilCommittee = seldata;
                    $.each(seldata, function (i, n) {
                        $("#chilCommittees").append(" <option value=\"" + n.code + "\">" + n.name + "</option>");
                    });
                    $("#chilCommittees").selectpicker('refresh');
                }
            });
        },
        //接种医生
        loadDoctorData:function(){
            $.ajax({
                url: '../sys/user/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    $.each(seldata, function (i, n) {
                        $("#inocDoctor").append(" <option value=\"" + n.realName + "\">" + n.realName + "</option>");
                    });
                    $("#inocDoctor").selectpicker('refresh');
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
        //疫苗类别
        loadBios:function(){
            //初始化下拉框
            $('#biotypes').selectpicker({
                'selectedText': 'cat',
                search:false,
            });
            $.ajax({
                url: '../tvacclass/queryAllVaccClassList',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.list;
                    inocLogsVM.biotypes = seldata;
                    console.log(seldata);
                    $.each(seldata, function (i, n) {
                        $("#biotypes").append(" <option value=\"" + n.classCode + "\">" + n.classCnName + "</option>");
                    });
                    $("#biotypes").selectpicker('refresh');
                }
            });
        },
        //疫苗批号
        inocBatchno:function(){
            //初始化下拉框
            $('#inocbatchno').selectpicker({
                'selectedText': 'cat',
                // search:false,
            });
            $.ajax({
                url: '../tmgrstockbase/getAllBatchnum',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    console.log(results);
                    var seldata = results.data;
                    console.log(results.data);
                    inocLogsVM.batchnums = seldata;
                    // console.log(seldata);
                    $.each(seldata, function (i, n) {
                        $("#inocbatchno").append('<option value="'+n.batchnum+'"> '+n.productName+'-'+n.batchnum+'</option>');
                    });
                    $("#inocbatchno").selectpicker('refresh');
                }
            });
        },
        //疫苗接种剂次:
        loadBioNos:function(){
            var param = new Array();
            param.push({"id":"1","name":"第一剂次"});
            param.push({"id":"2","name":"第二剂次"});
            param.push({"id":"3","name":"第三剂次"});
            param.push({"id":"4","name":"第四剂次"});
            param.push({"id":"5","name":"第五剂次"});
            param.push({"id":"6","name":"第六剂次"});
            param.push({"id":"7","name":"第七剂次"});
            param.push({"id":"8","name":"第八剂次"});
            param.push({"id":"9","name":"第九剂次"});
            $.each(param, function (i, n) {
                $("#bioNos").append(" <option value=\"" + n.id + "\">" + n.name + "</option>");
            });
            $("#bioNos").selectpicker('refresh');
        },
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
    }
});

$(function () {
    inocLogsVM.loadTimePicker();
    inocLogsVM.loadInfoStatusData();//个案状态
    inocLogsVM.loadCommiteeData();//行政村
    inocLogsVM.loadBios();//疫苗类别
    inocLogsVM.inocBatchno();//疫苗批号
    inocLogsVM.loadBioNos();//疫苗接种剂次
    inocLogsVM.residence();//居住属性
    inocLogsVM.chilaccount();//户籍属性
    inocLogsVM.LoadSchools();//学校
    inocLogsVM.loadDoctorData();//医生

    var now = new Date();
    var preMonth = new Date(getMonthBeforeFormatAndDay(-1,'-', now.getDate()));//向前推一个月
    var date=yfjzformatter(preMonth);
    var nowDate=yfjzformatter(now);
    $("#inoculateStart").val(nowDate);
    $("#inoculateEnd").val(nowDate);
    inocLogsVM.query();
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

/**
 *  *  求自然月日期
 *  by 刘琪  2018-05-18
 *
 *
 * @param num 向前，向后推迟的月份数
 * @param format  日期连接符  “-”，“/”等等
 * @param day  向前，向后推迟的天数
 */
function getMonthBeforeFormatAndDay(num, format, day) {
    var date = new Date();
    date.setMonth(date.getMonth() + num, 1);
    //读取日期自动会减一，所以要加一
    var mo = date.getMonth() + 1;
    //小月
    if (mo == 4 || mo == 6 || mo == 9 || mo == 11) {
        if (day > 30) {
            day = 30
        }
    } else if (mo == 2) {
        //2月
        if (isLeapYear(date.getFullYear())) {
            if (day > 29) {
                day = 29
            } else {
                day = 28
            }
        }
        if (day > 28) {
            day = 28
        }
    } else {
        //大月
        if (day > 31) {
            day = 31
        }
    }
    return date.format('yyyy' + format + 'MM' + format + day);
}


/**
 * 日期格式化
 * @param format
 * @returns {*}
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

function yfjzformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

$(function(){


    //console.log($("#chilCommittees").next().find('.bs-searchbox'));
    // 行政村/居委会拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilCommittees").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#chilCommittees");
        var value = inocLogsVM.chilCommittee;
        //清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {

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
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });
    // 疫苗类别拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#biotypes").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#biotypes");
        var value = inocLogsVM.biotypes;
        //清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].classCode + '">' + value[i].classCnName + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].classCnName.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].classCode + '">' + value[i].classCnName + '</option>';
                    }
                }
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            event.stopPropagation();
            return false;
        } else{
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].classCode + '">' + value[i].classCnName + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });
})
