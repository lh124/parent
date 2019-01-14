$(function () {
loadInfoStatusData();
loadNationData();
   // CityBind();
   // VillageBind();
    move();
   residence();
   chilBirthday();//出生日期
    chilCreatedate();//建档日期
    loadCommiteeData();//行政村
    loadSchoolData();//学校
    loadHospitalData();//医院
    loadchilLeaveremark();

    //医院根据拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilBirthhospitalnameIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#chilBirthhospitalnameIdParent .open input').val();
        var hunt = $("#chilBirthhospitalname");
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
            var value = null;
            if("undefined" != typeof registerVM){
                value = registerVM.chilBirthhospitalname;
            }
            if("undefined" != typeof vm){
                value = vm.chilBirthhospitalname;
            }
            if("undefined" != typeof childGatherVM){
                value = childGatherVM.chilBirthhospitalname;
            }
            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].hospitalCode + '">' + value[i].hospitalName + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].hospitalName.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].hospitalCode + '">' + value[i].hospitalName + '</option>';
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
            if("undefined" != typeof registerVM){
                value = registerVM.chilBirthhospitalname;
            }
            if("undefined" != typeof vm){
                value = vm.chilBirthhospitalname;
            }
            if("undefined" != typeof childGatherVM){
                value = childGatherVM.chilBirthhospitalname;
            }
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].hospitalCode + '">' + value[i].hospitalName + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });

    // 行政村/居委会拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilCommitteeIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#chilCommitteeIdParent .open input').val();
        var hunt = $("#chilCommittee");

        var value = null;
        if("undefined" != typeof registerVM){
            value = registerVM.chilCommittee;
        }
        if("undefined" != typeof vm){
            value = vm.chilCommittee;
        }
        if("undefined" != typeof childGatherVM){
            value = childGatherVM.chilCommittee;
        }
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
});

//个案状态
function loadInfoStatusData(){
    var param = new Array();
    $.ajax({
        url: '../child/listdiccode?ttype=child_info_status',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            $("#infostatus").append(con);
            $("#chilHere").html(con);
            $("#chheHere").html(con);
            $("#updatahere").html(con);
            $("#updatahere1").html(con);

        }
    });
}

//行政村
function loadCommiteeData(){
    //初始化下拉框
    $('#chilCommittee').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择行政村/居委会',
        //actionsBox:true,
        search:false,
    });
    var param = new Array();
    $.ajax({
        url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
        dataType: "json",
        type: 'POST',
        success: function (results) {

            if("undefined" != typeof registerVM){
                registerVM.chilCommittee = results.page.list;
            }
            if("undefined" != typeof vm){
                vm.chilCommittee = results.page.list;
            }
            if("undefined" != typeof childGatherVM){
                childGatherVM.chilCommittee = results.page.list;
            }

            var con='';
            for (var i = 0; i < results.page.list.length; i++) {
                con += '<option  value="' + results.page.list[i].code + '">'+results.page.list[i].name+'</option>';
            }
            /*$("#chilCommittee").html(con);
            $("#chilCommittees").append(con);*/
            $("#chilCommittee").append(con);
            $("#chilCommittee").selectpicker('refresh');

        }
    });
}
//学校
function loadSchoolData(){
    var param = new Array();
    $.ajax({
        url: '../tbaseschool/list?org_id='+orgId+'&page=1&limit=1000',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con='';
            for (var i = 0; i < results.page.list.length; i++) {
                con += '<option  value="' + results.page.list[i].code + '">'+results.page.list[i].name+'</option>';
            }
            $("#chilSchool").html(con);

        }
    });
}

//医院
function loadHospitalData(){
    //初始化下拉框
    $('#chilBirthhospitalname').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择出生医院...',
        actionsBox:true,
        search:false,
    });
    var param = new Array();
    $.ajax({
        url: '../tbasehospital/list?page=1&limit=10000',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            if("undefined" != typeof registerVM){
                registerVM.chilBirthhospitalname = results.page.list;
            }
            if("undefined" != typeof vm){
                vm.chilBirthhospitalname = results.page.list;
            }
            if("undefined" != typeof childGatherVM){
                childGatherVM.chilBirthhospitalname = results.page.list;
            }

            var con='';
            for (var i = 0; i < results.page.list.length; i++) {
                con += '<option  value="' + results.page.list[i].hospitalCode + '">'+results.page.list[i].hospitalName+'</option>';
            }
            $("#chilBirthhospitalname").append(con);
            $("#chilBirthhospitalname").selectpicker('refresh');

        }
    });
}
//生日
function chilBirthday(){
    $('#chilBirthday').datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        registerVM.tChildInfo.chilBirthday = $("#chilBirthday").val();
    }).on('hide',function(e) {
        if($('#chilForm').data('bootstrapValidator')!=null){
            $('#chilForm').data('bootstrapValidator')
                .updateStatus('chilBirthday', 'NOT_VALIDATED',null)
                .validateField('chilBirthday');
        }
    });
    /* .on('hide',function(e) {
             $('#chilForm').data('bootstrapValidator')
                 .updateStatus('chilBirthday', 'NOT_VALIDATED',null)
                 .validateField('chilBirthday');
         });*/

}
function chilCreatedate(){
    $('#datetimepicker4').datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    //默认获取当前日期
    var today = new Date();
    var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate()+" "+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
    //对日期格式进行处理
    var date = new Date(nowdate);
    var mon = date.getMonth() + 1;
    var day = date.getDate();
    var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day)+" "+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
    /*document.getElementById("chilCreatedate").value = mydate;*/
    return mydate;
}
//民族
function loadNationData(){
    $.ajax({
        url: '../child/listdiccode?ttype=nation_code',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            $("#chilNatiId").html(con);
            /* var result=results.data;
             $.each(result, function (index, item) {
                 param.push({"text":item.text,"value":item.value});
             });
             vm.items = param;*/
        }
    });
}
//户籍属性
function move(){
    var params = new Array();
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
            $("#updatachilAccount").html(con);
            $("#updataaccount1").html(con);
        }
    });
}

//居住属性
function residence(){
    var params = new Array();
    $.ajax({
        url: '../child/listdiccode?ttype=child_residence_code',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            $("#chilResidence").html(con);
            $("#updatachilResidence").html(con);
            $("#updatachilResidence1").html(con);
        }
    });
}
function Bind(str) {
    // alert($("#Province").html());
    $("#Province").val(str);
    $("#Province_home").val(str);
}

function Provice_homeBind(tChildInfo) {
    //清空下拉数据
    $("#Province_home").html("");
    var str = "";
    // var str = "<option>==请选择===</option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listprovince",
        data: { "parentiD": "", "MyColums": "Province" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                if(tChildInfo!=null){
                    if(tChildInfo.chilAddressTownId!=null && item.id==tChildInfo.chilAddressTownId.substring(0,2)){
                        str += "<option value=" + item.id +" selected"+ ">" + item.name + "</option>";
                    }else if(tChildInfo.chilCurdepartment!=null && item.id==tChildInfo.chilCurdepartment.substring(0,2)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }/*else if(orgId!=null &&item.id==orgId.substring(0,2)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }*/else{
                        str += "<option value=" + item.id +">" + item.name + "</option>";
                    }
                }else if(orgId!=null && item.id == orgId.substring(0,2)){
                    str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                }else{
                    str += "<option value=" + item.id +">" + item.name + "</option>";
                }



                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#Province_home").html(str);
        },
        error: function () { alert("Error"); }
    });
}
function CityBind(tChildInfo) {
    var provice = $("#Province option:selected").val();
    //判断省份这个下拉框选中的值是否为空
    if (provice == "") {
        return;
    }
    $("#City").html("");
    //var str = "";
     var str = "<option value=''></option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcity?provinceId="+provice,
        data: { "provinceId": provice, "MyColums": "City" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                if(tChildInfo!=null){
                    if(tChildInfo.chilHabiaddressTownId!=null && item.id==tChildInfo.chilHabiaddressTownId.substring(0,4)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }else if(tChildInfo.chilHabiId!=null && item.id ==tChildInfo.chilHabiId.substring(0,4)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }/*else if(orgId!=null && item.id==orgId.substring(0,4)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }*/else{
                        str += "<option value=" + item.id +">" + item.name + "</option>";
                    }
                }else if(orgId!=null && item.id == orgId.substring(0,4)){
                    str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                }else{
                    str += "<option value=" + item.id +">" + item.name + "</option>";
                }


            });
            //将数据添加到省份这个下拉框里面
            $("#City").html(str);
        },
        error: function () { alert("Error"); }
    });
    VillageBind(tChildInfo);
}
function City_homeBind(tChildInfo) {
    var provice_home = $("#Province_home option:selected").val();
    //判断省份这个下拉框选中的值是否为空
    if (provice_home == "") {
        return;
    }
    $("#City_home").html("");
    var str = "";
    // var str = "<option>==请选择===</option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcity?provinceId="+provice_home,
        data: { "provinceId": provice_home, "MyColums": "City" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                if(tChildInfo!=null){
                    if(tChildInfo.chilAddressTownId!=null && item.id==tChildInfo.chilAddressTownId.substring(0,4)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }else if(tChildInfo.chilCurdepartment!=null && item.id ==tChildInfo.chilCurdepartment.substring(0,4)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }
                   /* else if(orgId!=null && item.id==orgId.substring(0,4)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }*/else{
                        str += "<option value=" + item.id +">" + item.name + "</option>";
                    }
                }else if(orgId!=null && item.id==orgId.substring(0,4)){
                    str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                }else{
                    str += "<option value=" + item.id +">" + item.name + "</option>";
                }


            });
            //将数据添加到省份这个下拉框里面
            $("#City_home").html(str);
        },
        error: function () { alert("Error"); }
    });
    Village_homeBind(tChildInfo);
}



function VillageBind(tChildInfo) {

    // var  provice = $("#City").attr("value");
    var provice = $("#City option:selected").val();
    //判断市这个下拉框选中的值是否为空
    if (provice == "") {
        return;
    }
    $("#Village").html("");
     var str = "<option value=''></option>";
    //var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcounty?cityId="+provice,
        data: { "cityId": provice, "MyColums": "Village" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                if(tChildInfo!=null){
                    if(tChildInfo.chilHabiaddressTownId!=null && item.id==tChildInfo.chilHabiaddressTownId.substring(0,6)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }else if(tChildInfo.chilHabiId!=null && item.id ==tChildInfo.chilHabiId.substring(0,6)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }/*else if(orgId!=null && item.id==orgId.substring(0,6)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }*/else{
                        str += "<option value=" + item.id +">" + item.name + "</option>";
                    }
                }else if(orgId!=null && item.id==orgId.substring(0,6)){
                    str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                }else{
                    str += "<option value=" + item.id +">" + item.name + "</option>";
                }


            });
            //将数据添加到省份这个下拉框里面
            $("#Village").html(str);
        },
        error: function () { alert("Error"); }
    });
    TwonBind(tChildInfo);
}
function Village_homeBind(tChildInfo) {
    // var  provice = $("#City").attr("value");
    var provice_home = $("#City_home option:selected").val();
    //判断市这个下拉框选中的值是否为空
    if (provice_home == "") {
        return;
    }
    $("#Village_home").html("");
    // var str = "<option>==请选择===</option>";
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcounty?cityId="+provice_home,
        data: { "cityId": provice_home, "MyColums": "Village" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                if(tChildInfo!=null){
                    if(tChildInfo.chilAddressTownId!=null && item.id==tChildInfo.chilAddressTownId.substring(0,6)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }else if(tChildInfo.chilCurdepartment!=null && item.id ==tChildInfo.chilCurdepartment.substring(0,6)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }
                   /* else if(orgId!=null && item.id==orgId.substring(0,6)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }*/else{
                        str += "<option value=" + item.id +">" + item.name + "</option>";
                    }
                }else if(orgId!=null && item.id==orgId.substring(0,6)){
                    str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                }else{
                    str += "<option value=" + item.id +">" + item.name + "</option>";
                }


            });
            //将数据添加到省份这个下拉框里面
            $("#Village_home").html(str);
        },
        error: function () { alert("Error"); }
    });
    Twon_homeBind(tChildInfo);
}
function TwonBind(tChildInfo) {
    var country = $("#Village option:selected").val();
    //判断市这个下拉框选中的值是否为空
    if (country == "") {
        return;
    }
    $("#Twon").html("");
     var str = "<option value=''></option>";
    //var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listtown?countyId="+country,
        data: { "countyId": country, "MyColums": "Twon" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                if(tChildInfo!=null){
                    if(result.data[i].id==tChildInfo.chilHabiaddressTownId){
                        str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                    }else if(tChildInfo!=null && tChildInfo!={}){
                        str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
                    }else{
                        str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
                    }
                }else if(orgId!=null && result.data[i].id==orgId.substring(0,8)){
                    str += "<option value=" + result.data[i].id +" selected"+ ">" + result.data[i].name + "</option>";
                }else{
                    str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
                }

            }
            //将数据添加到省份这个下拉框里面
            $("#Twon").html(str);

        },
        error: function () { alert("Error"); }
    });
}
function Twon_homeBind(tChildInfo) {
    // var  provice = $("#City").attr("value");
    var provice_home = $("#Village_home option:selected").val();
    //判断市这个下拉框选中的值是否为空
    if (provice_home == "") {
        return;
    }
    $("#Twon_home").html("");
    // var str = "<option>==请选择===</option>";
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listtown?countyId="+provice_home,
        data: { "countyId": provice_home, "MyColums": "Twon" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){

                if(tChildInfo!=null && result.data[i].id==tChildInfo.chilAddressTownId){
                    str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                }else if(tChildInfo!=null && tChildInfo.chilCurdepartment!=null && result.data[i].id ==tChildInfo.chilCurdepartment.substring(0,8)){
                    str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                }
                else if(orgId!=null && result.data[i].id==orgId.substring(0,8)){
                    str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                }else{
                    str += "<option value=" + result.data[i].id +">" + result.data[i].name + "</option>";
                }



            }
            //将数据添加到省份这个下拉框里面
            $("#Twon_home").html(str);

        },
        error: function () { alert("Error"); }
    });
}


function ProviceBind(tChildInfo) {
    //清空下拉数据
    $("#Province").html("");
    //var str = "";
     var str = "<option value=''></option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listprovince",
        data: { "parentiD": "", "MyColums": "Province" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                if(tChildInfo!=null){
                    if(tChildInfo.chilHabiaddressTownId!=null && item.id==tChildInfo.chilHabiaddressTownId.substring(0,2)){
                        str += "<option value=" + item.id +" selected"+ ">" + item.name + "</option>";
                    }else if(tChildInfo.chilHabiId!=null && item.id==tChildInfo.chilHabiId.substring(0,2)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }/*else if(orgId!=null && item.id ==orgId.substring(0,2)){
                        str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                    }*/else{
                        str += "<option value=" + item.id +">" + item.name + "</option>";
                    }
                }else if(orgId!=null && item.id==orgId.substring(0,2)){
                    str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                }else{
                    str += "<option value=" + item.id +">" + item.name + "</option>";
                }

                //str += "<option value=" + item.id + ">" + item.name + "</option>";
                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#Province").html(str);
        },
        error: function () { alert("Error"); }
    });
}

function downloadChild() {
    $.ajax({
        type: "POST",
        url: "../provincePlatform/saveToLocal?childCode="+vm.q.chilCode,
        dataType:"json",
        success: function(r){
            if(r.code == 0){
                alert('下载成功');
                $("#jqGrid").trigger("reloadGrid");
            }else{
                alert(r.msg);
            }
        }
    });
}
/*
//选择一条记录
function getSelectedRow_child() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return rowKey;
}*/
function loadchilLeaveremark() {
    var param = new Array();
    $.ajax({
        url: '../child/listdiccode?ttype=chil_leaveremark_code',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            $("#chilLeaveremark").append(con);

        }
    });
    
}

function openChangeRemark() {
    $("#chilLeaveremark").removeAttr("disabled");
}
