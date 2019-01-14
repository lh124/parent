//登记页面儿童信息
var updata = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilName: '',
            chilBirthday: '',
            chilMother: '',
            chilFather: '',
            chilHabiaddress: '',//户籍地址
            chilAddress: '',//现居地址
            chilTel: '',
            chilMobile: '',
            remark: ''
        }
    }

})
//性别
var selecttypesex = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilSex: '',
        }
    }
})
//行政村
var selecttypecommittee = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilCommittee: '',
        }
    }
})
//居住属性
var selecttypeResidence = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilResidence: '',//居住属性
        }
    }
})
// 户籍属性
var selecttypeAccount = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilAccount: '',//户籍属性
        }
    }
})
// 个案状态
var selecttypeHere = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilHere: ''
        }
    }
})
//登记页面弹框的儿童信息
var updatadialog = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilName: '',
            chilBirthday: '',
            chilMother: '',
            chilFather: '',
        }
    }

})
//接种页面的儿童信息
var updatainoculatechil = new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilName: '',
            chilBirthday: '',
            chilMother: '',
            chilFather:'',
            chilHabiaddress: '',//户籍地址
            chilAddress: '',//现居地址
            chilTel: '',
            remark: '',
        }
    }
})
//儿童档案 接种录入信息修改 strat ----------------
var inocInput=new Vue({
    data: {
        tChildInfo: {
            chilCode: '',
            chilName: '',
            chilBirthday: '',
            chilMother: '',
            // chilHabiaddress: '',//户籍地址
            // chilAddress: '',//现居地址
            chilMobile: '',
            remark: '',
        }
    }
})
//儿童登记信息行政村修改
function updatecommittee() {
    var chil_code = $("#chilcodes").val();
    var status1 = $("#updatastatus11").val();
    var status = $("#updatastatus").val();
    if (status==1){
        //行政村 start
        var chilCommittee1 = $('#updatachilcommittee option:selected').val();//下拉框的值
        var chilCommittee2 = $('#updatachilcommittee option:selected').text();//下拉框的值
        $("#committeeId").val(chilCommittee2);
        //行政村 end
        Vue.set(selecttypecommittee.tChildInfo, 'chilCode', chil_code);
        Vue.set(selecttypecommittee.tChildInfo, 'chilCommittee', chilCommittee1);
        urltype(selecttypecommittee.tChildInfo, chil_code);
        $("#updatachilcommittee").css({"display": "none"});
    }

    if (status1 == 3) {
        var chilCode = $("#inochilCode").text();
        //性别属性 start
        var updatasex1 = $('#updatachilcommittees option:selected').val();//下拉框的值
        var updatasex2 = $('#updatachilcommittees option:selected').text();//下拉框的值
        $("#committeeIds").val(updatasex2);
        //性别属性 end
        Vue.set(selecttypecommittee.tChildInfo, 'chilCode', chilCode);
        Vue.set(selecttypecommittee.tChildInfo, 'chilCommittee', updatasex1);
        urltype1(selecttypecommittee.tChildInfo, chilCode);
        $("#updatachilcommittees").css({"display": "none"});
    }



}
//登记弹框行政村修改
function updatecommittees() {
    var chil_code = $("#chilcodes").val();
    //性别属性 start
    var chilCommittee1 = $('#updatachilcommittees option:selected').val();//下拉框的值
    var chilCommittee2 = $('#updatachilcommittees option:selected').text();//下拉框的值
    $("#committeeIds").val(chilCommittee2);
    //性别属性 end
    Vue.set(selecttypecommittee.tChildInfo, 'chilCode', chil_code);
    Vue.set(selecttypecommittee.tChildInfo, 'chilCommittee', chilCommittee1);
    urltype(selecttypecommittee.tChildInfo, chil_code);
    $("#updatachilcommittees").css({"display": "none"});
}

//接种录入弹框行政村修改
function updatecommitteeinput() {
    var chilCode = $("#inochilCode").text().substring(1);
    //行政村 start
    var chilCommittee1 = $('#updatachilcommittees option:selected').val();//下拉框的值
    var chilCommittee2 = $('#updatachilcommittees option:selected').text();//下拉框的值
    $("#committeeIds").val(chilCommittee2);
    //行政村 end
    Vue.set(selecttypecommittee.tChildInfo, 'chilCode', chilCode);
    Vue.set(selecttypecommittee.tChildInfo, 'chilCommittee', chilCommittee1);
    urltype2(selecttypecommittee.tChildInfo, chilCode);
    $("#updatachilcommittees").css({"display": "none"});
}


function updatainoculateInput() {
    var chilCode = $("#inochilCode").text().substring(1);
    console.log(chilCode);
    var inoname = $("#inoname").val();
    var inobirthday = $("#inobirthday").val();
    var inobirthdays ='';
    if(inobirthday.length==10){
        inobirthdays = $("#inobirthday").val() + " 00:00:00";
    }else if (inobirthday.length==8) {
        inobirthdays = inobirthday.substring(0,4)+"-"+inobirthday.substring(4,6)+"-"+inobirthday.substring(6,8)+" 00:00:00";
    }else if (inobirthday.length==19) {
        inobirthdays=$("#inobirthday").val();
    }else{
        alert("请输入正确的日期格式：如：20180101或2018-01-01");
    }
    var inomontherName = $("#inochilMother").val();
    var inotel = $("#inochilMobile").val();
    var inoremark = $("#inoremark").val();
    if (inotel!=""){
        if (!(/^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|"")$/.test(inotel))) {
            alert('号码格式错误,座机需加区号和符号‘-’')
            return false;
        }
    }
    Vue.set(inocInput.tChildInfo, 'chilCode', chilCode);
    Vue.set(inocInput.tChildInfo, 'chilName', inoname);
    Vue.set(inocInput.tChildInfo, 'chilBirthday', inobirthdays);
    Vue.set(inocInput.tChildInfo, 'chilMobile', inotel);
    Vue.set(inocInput.tChildInfo, 'chilMother', inomontherName);
    Vue.set(inocInput.tChildInfo, 'remark', inoremark);
    urltype2(inocInput.tChildInfo, chilCode);

}

function updatainoInputchilAccount() {
    var chilCode = $("#inochilCode").text().substring(1);
    var chilHere1 = $('#inochilAccount option:selected').val();//下拉框的值
    var chilHere2 = $('#inochilAccount option:selected').text();//下拉框的值
    $("#inochilAccountId").val(chilHere2);
    Vue.set(selecttypeAccount.tChildInfo, 'chilAccount', chilHere1);
    Vue.set(selecttypeAccount.tChildInfo, 'chilCode', chilCode);
    urltype2(selecttypeAccount.tChildInfo, chilCode);
    $("#inochilAccount").css({"display": "none"});
}
function updatainoInputchilSex() {
    var chilCode = $("#inochilCode").text().substring(1);
    var chilHere1 = $('#inosex option:selected').val();//下拉框的值
    var chilHere2 = $('#inosex option:selected').text();//下拉框的值
    $("#inosexId").val(chilHere2);

    Vue.set(selecttypesex.tChildInfo, 'chilSex', chilHere1);
    Vue.set(selecttypesex.tChildInfo, 'chilCode', chilCode);
    urltype2(selecttypesex.tChildInfo, chilCode);
    $("#inosex").css({"display": "none"});
}

// function updatainoInputchilHere() {
//     var chilCode = $("#inochilCode").text().substring(1);
//     var chilHere1 = $('#inochilHere option:selected').val();//下拉框的值
//     var chilHere2 = $('#inochilHere option:selected').text();//下拉框的值
//     $("#inochilHereId").val(chilHere2);
//     Vue.set(selecttypeHere.tChildInfo, 'chilHere', chilHere1);
//     Vue.set(selecttypeHere.tChildInfo, 'chilCode', chilCode);
//     urltype2(selecttypeHere.tChildInfo, chilCode);
// }

//儿童档案 接种录入信息修改 end ----------------


//居住属性
function selectupdataResidencedialog() {
    var chil_code = $("#chilcodes").val();
    //居住属性 start
    var updatachilResidence1 = $('#updatachilResidence1 option:selected').val();//下拉框的值
    var updatachilResidence2 = $('#updatachilResidence1 option:selected').text();//下拉框的值
    $("#chilResidenceId1").val(updatachilResidence2);
    //居住属性 end
    Vue.set(selecttypeResidence.tChildInfo, 'chilResidence', updatachilResidence1);
    Vue.set(selecttypeResidence.tChildInfo, 'chilCode', chil_code);
    urltype(selecttypeResidence.tChildInfo, chil_code);
    $("#updatachilResidence1").css({"display": "none"});
}

function selectupdataResidence() {
    var chil_code = $("#chilcodes").val();
//居住属性 start
    var updatachilResidence1 = $('#updatachilResidence option:selected').val();//下拉框的值
    var updatachilResidence2 = $('#updatachilResidence option:selected').text();//下拉框的值
    $("#chilResidenceId").val(updatachilResidence2);
    //居住属性 end
    Vue.set(selecttypeResidence.tChildInfo, 'chilResidence', updatachilResidence1);
    Vue.set(selecttypeResidence.tChildInfo, 'chilCode', chil_code);
    urltype(selecttypeResidence.tChildInfo, chil_code);
    $("#updatachilResidence").css({"display": "none"});
}

function selectupdataheredialog() {
    var chil_code = $("#chilcodes").val();
    //个案状态 start
    var updatahere1 = $('#updatahere1 option:selected').val();//下拉框的值
    var updatahere2 = $('#updatahere1 option:selected').text();//下拉框的值
    $("#hereId1").val(updatahere2);
    //个案状态 end
    Vue.set(selecttypeHere.tChildInfo, 'chilHere', updatahere1);
    Vue.set(selecttypeHere.tChildInfo, 'chilCode', chil_code);
    urltype(selecttypeHere.tChildInfo, chil_code);
}

// function selectupdatahere() {
//     var chil_code = $("#chilcodes").val();
//     var status1 = $("#updatastatus1").val();
//     var status = $("#updatastatus").val();
//     var chilCode = $("#inochilCode").text();
//     if (status==1){
//         //个案状态 start
//         var updatahere1 = $('#updatahere option:selected').val();//下拉框的值
//         var updatahere2 = $('#updatahere option:selected').text();//下拉框的值
//         $("#hereId").val(updatahere2);
//         //个案状态 end
//         Vue.set(selecttypeHere.tChildInfo, 'chilHere', updatahere1);
//         Vue.set(selecttypeHere.tChildInfo, 'chilCode', chil_code);
//         urltype(selecttypeHere.tChildInfo,chil_code);
//     }

// if (status==3){
//     //个案状态 start
//     var updatahere1 = $('#inohere option:selected').val();//下拉框的值
//     var updatahere2 = $('#inohere option:selected').text();//下拉框的值
//     $("#inohereId").val(updatahere2);
//     //个案状态 end
//     Vue.set(selecttypeHere.tChildInfo, 'chilHere', updatahere1);
//     Vue.set(selecttypeHere.tChildInfo, 'chilCode', chilCode);
//     urltype(selecttypeHere.tChildInfo,chilCode);
// }
// }

function selectupdataaccountdialog() {
    var chil_code = $("#chilcodes").val();
    //户籍属性 start
    var selected = $('#updataaccount1 option:selected').val();//下拉框的值
    var selected1 = $('#updataaccount1 option:selected').text();//下拉框的值
    $("#accountId1").val(selected1);
    //户籍属性 end
    Vue.set(selecttypeAccount.tChildInfo, 'chilCode', chil_code);
    Vue.set(selecttypeAccount.tChildInfo, 'chilAccount', selected);
    urltype(selecttypeAccount.tChildInfo, chil_code);
    $("#updataaccount1").css({"display": "none"});
}

function selectupdataaccount() {
    var chil_code = $("#chilcodes").val();
    var status1 = $("#updatastatus11").val();
    var status = $("#updatastatus").val();
    if (status == 1) {
        //户籍属性 start
        var selected = $('#updatachilAccount option:selected').val();//下拉框的值
        var selected1 = $('#updatachilAccount option:selected').text();//下拉框的值
        $("#chilAccountId").val(selected1);
        //户籍属性 end
        console.log($("#chilResidenceId").val());
        Vue.set(selecttypeAccount.tChildInfo, 'chilCode', chil_code);
        Vue.set(selecttypeAccount.tChildInfo, 'chilAccount', selected);
        urltype(selecttypeAccount.tChildInfo, chil_code);
        $("#updatachilAccount").css({"display": "none"});
    }
    if (status1 == 3) {
        var chilCode = $("#inochilCode").text();
        //性别属性 start
        var updatasex1 = $('#inoaccount option:selected').val();//下拉框的值
        var updatasex2 = $('#inoaccount option:selected').text();//下拉框的值
        $("#inoaccountId").val(updatasex2);
        //性别属性 end
        Vue.set(selecttypeAccount.tChildInfo, 'chilCode', chilCode);
        Vue.set(selecttypeAccount.tChildInfo, 'chilAccount', updatasex1);
        urltype1(selecttypeAccount.tChildInfo, chilCode);
        $("#inoaccount").css({"display": "none"});
    }

}

function selectupdatasexdialog() {
    var chil_code = $("#chilcodes").val();
    //性别属性 start
    var updatasex1 = $('#updatasex1 option:selected').val();//下拉框的值
    var updatasex2 = $('#updatasex1 option:selected').text();//下拉框的值
    $("#sexId1").val(updatasex2);
    //性别属性 end
    Vue.set(selecttypesex.tChildInfo, 'chilCode', chil_code);
    Vue.set(selecttypesex.tChildInfo, 'chilSex', updatasex1);
    urltype(selecttypesex.tChildInfo, chil_code);
    $("#updatasex1").css({"display": "none"});
}

function selectupdatasex() {
    var chil_code = $("#chilcodes").val();
    var status = $("#updatastatus").val();
    var status1 = $("#updatastatus11").val();
    var chilCode = $("#inochilCode").text();
    if (status == 1) {
        //性别属性 start
        var updatasex1 = $('#updatasex option:selected').val();//下拉框的值
        var updatasex2 = $('#updatasex option:selected').text();//下拉框的值
        $("#sexId").val(updatasex2);
        //性别属性 end
        Vue.set(selecttypesex.tChildInfo, 'chilCode', chil_code);
        Vue.set(selecttypesex.tChildInfo, 'chilSex', updatasex1);
        urltype(selecttypesex.tChildInfo, chil_code);
        $("#updatasex").css({"display": "none"});
    }
    if (status1 == 3) {
        //性别属性 start
        var updatasex1 = $('#inosex option:selected').val();//下拉框的值
        var updatasex2 = $('#inosex option:selected').text();//下拉框的值
        $("#inosexId").val(updatasex2);
        //性别属性 end
        Vue.set(selecttypesex.tChildInfo, 'chilCode', chilCode);
        Vue.set(selecttypesex.tChildInfo, 'chilSex', updatasex1);
        urltype1(selecttypesex.tChildInfo, chilCode);
        $("#inosex").css({"display": "none"});
    }
}

function updatainoculate() {

    var chilCode = $("#inochilCode").text();
    var inoname = $("#inoname").val();
    // var inobirthday = $("#inobirthday").val() + " 00:00:00";
    var inobirthday = $("#inobirthday").val();
    var inobirthdays ='';
    if(inobirthday.length==10){
        inobirthdays = $("#inobirthday").val() + " 00:00:00";
    }else if (inobirthday.length==8) {
        inobirthdays = inobirthday.substring(0,4)+"-"+inobirthday.substring(4,6)+"-"+inobirthday.substring(6,8)+" 00:00:00";
    }else{
        alert("请输入正确的日期格式：如：20180101或2018-01-01");
    }

    var inomontherName = $("#inomontherName").val();
    var inofatherName = $("#inofatherName").val();
    var inotel = $("#inotel").val();
    var inoaddress = $("#inoaddress").val();
    var inohabiaddress = $("#inohabiaddress").val();
    var inoremark = $("#inoremark").val();
    // if (!(/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/).test(inobirthday) ){
    //     alert("日期格式不正确，正确格式为：2018-01-01");
    //     return false;
    // }
    if (inotel!=""){
        if (!(/^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|"")$/.test(inotel))) {
            alert('号码格式错误,座机需加区号和符号‘-’')
            return false;
        }
    }


    Vue.set(updatainoculatechil.tChildInfo, 'chilCode', chilCode);
    Vue.set(updatainoculatechil.tChildInfo, 'chilName', inoname);
    Vue.set(updatainoculatechil.tChildInfo, 'chilAddress', inoaddress);
    Vue.set(updatainoculatechil.tChildInfo, 'chilHabiaddress', inohabiaddress);
    Vue.set(updatainoculatechil.tChildInfo, 'chilBirthday', inobirthdays);
    Vue.set(updatainoculatechil.tChildInfo, 'chilTel', inotel);
    Vue.set(updatainoculatechil.tChildInfo, 'chilMother', inomontherName);
    Vue.set(updatainoculatechil.tChildInfo, 'chilFather', inofatherName);
    Vue.set(updatainoculatechil.tChildInfo, 'remark', inoremark);
    urltype1(updatainoculatechil.tChildInfo, chilCode);

}

//登记页面弹框儿童信息修改
function updatachildialog() {
    var chil_code = $("#chilcodes").val();
    var updatachil_name = $("#updatachil_name1").val()
    var updatamonther_name = $("#updatamontherName1").val()
    var updatafather_name = $("#updatafatherName1").val()
    // var updatabirthday = $("#updatabirthday1").val() + " 00:00:00";
    var updatabirthday = $("#updatabirthday1").val();
    var tel_1 = $("#tel_1").val();
    var tel_2 = $("#tel_2").val();

    if (tel_1!=""){
        if (!(/^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|"")$/.test(tel_1))) {
            alert('号码格式错误,座机需加区号和符号‘-’')
            return false;
        }
    }
    if (tel_2!=""){
        if (!(/^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|"")$/.test(tel_2))) {
            alert('号码格式错误,座机需加区号和符号‘-’')
            return false;
        }
    }

    var updatabirthdays ='';
    if(updatabirthday.length==10){
        updatabirthdays = $("#updatabirthday1").val() + " 00:00:00";
    }else if (updatabirthday.length==8) {
        updatabirthdays = updatabirthday.substring(0,4)+"-"+updatabirthday.substring(4,6)+"-"+updatabirthday.substring(6,8)+" 00:00:00";
    }else{
        alert("请输入正确的日期格式：如：20180101或2018-01-01");
    }
    Vue.set(updatadialog.tChildInfo, 'chilCode', chil_code);
    Vue.set(updatadialog.tChildInfo, 'chilName', updatachil_name);
    Vue.set(updatadialog.tChildInfo, 'chilBirthday', updatabirthdays);
    Vue.set(updatadialog.tChildInfo, 'chilMother', updatamonther_name);
    Vue.set(updatadialog.tChildInfo, 'chilFather', updatafather_name);
    Vue.set(updatadialog.tChildInfo, 'chilTel', tel_1);

    Vue.set(updatadialog.tChildInfo, 'chilMobile', tel_2);
    urltype(updatadialog.tChildInfo, chil_code);
}

//ajax请求 登记
function urltype(url, chilCode) {
    $.ajax({
        type: 'post',
        url: "../child/updatainfo",
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',//重点关注此参数
        data: JSON.stringify(url),
        success: function (data) {
            layer.msg("更改成功！");
            registerVM.q.chilCode = chilCode
            registerVM.reload();

        }
    });
}
function urltype2(url, chilCode) {
    $.ajax({
        type: 'post',
        url: "../child/updatainfo",
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',//重点关注此参数
        data: JSON.stringify(url),
        success: function (data) {
            layer.msg("更改成功！");
            parent.vm.reload();
        }
    });
}
//接种
function urltype1(url, chilCode) {
    $.ajax({
        type: 'post',
        url: "../child/updatainfo",
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',//重点关注此参数
        data: JSON.stringify(url),
        success: function (data) {
            layer.msg("更改成功！");
            console.log(chilCode);
            // loadCurrentDayWaitInocChild();
            $("#inoculateTable").jqGrid('clearGridData');  //清空表格
            $("#inoculateTable").jqGrid('setGridParam',{
                url: "../tbusregister/getTodayRegisterList?childCode="+chilCode,
                page:1
            }).trigger("reloadGrid");
        }
    });
}

var movehere = new Vue({
    data: {
        tChildMove: {
            chilCode: '',
            chheHere: '',//现在册情况
            chhePrehere: '',//原在册情况
            chheDepaId: '',//变更单位
            chheChilProvinceremark: '',//变更原因
        }
    }

})
$(function () {
    //变更原因弹框
    $("#updatahere").on('dblclick', function () {
        $('#myModal_here').modal('show')
    })
    $("#updatahere1").on('dblclick', function () {
        var index = layer.open({
            title: '变更原因',
            skin: 'tmpl',
            type: 1,
            btn:['提交更改','取消'],
            // closeBtn: 1,//取消右上角的x关闭按钮
            area: ['300px', '200px'],
            content: $("#myModal_here1"),
            success: function () {

            },btn1: function (index) {
                layer.close(index);
            },yes:function () {
                var chil_code = $("#chilcodes").val();
                var chhePrehere = $("#hereId1").val();//原在册情况
                var chheHere = $("#updatahere1 option:selected").val();//现在册情况
                var chheChilProvinceremark = $("#updataReason1").val();
                if (chheChilProvinceremark==""||chheChilProvinceremark==null){
                    layer.msg("变更原因不能为空");
                    return false;
                }

                Vue.set(movehere.tChildMove, 'chilCode', chil_code);
                Vue.set(movehere.tChildMove, 'chhePrehere', chhePrehere);
                Vue.set(movehere.tChildMove, 'chheHere', chheHere);
                Vue.set(movehere.tChildMove, 'chheChilProvinceremark', chheChilProvinceremark);
                $.ajax({
                    type: "POST",
                    url: "../tchildmove/save",
                    data: JSON.stringify(movehere.tChildMove),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        var index = layer.open();
                        layer.close(index);
                        if (r.code === 0) {
                            Vue.set(selecttypeHere.tChildInfo, 'chilHere', chheHere);
                            Vue.set(selecttypeHere.tChildInfo, 'chilCode', chil_code);
                            urltype(selecttypeHere.tChildInfo, chil_code);
                        } else {
                            alert(r.msg);
                        }
                    }
                });
                layer.close(index);
            },  end:function () {
                $(".layui-layer-shade").css("display","none");

            }
        });
    })
})

//接种录入个案状态变更
$("#inochilHere").on('dblclick', function () {
    var index = layer.open({
        title: '变更原因',
        skin: 'tmpl',
        type: 1,
        btn:['提交更改','取消'],
        // closeBtn: 1,//取消右上角的x关闭按钮
        area: ['300px', '200px'],
        content: $("#myModal_here2"),
        success: function () {

        },btn1: function (index) {
            layer.close(index);
        },yes:function () {
            var chil_code = $("#inochilCode").text().substring(1);
            var chhePrehere = $("#hereId1").val();//原在册情况
            var chheHere = $("#inochilHere option:selected").val();//现在册情况
            var chheChilProvinceremark = $("#updataReason2").val();
            if (chheChilProvinceremark==""||chheChilProvinceremark==null){
                layer.msg("变更原因不能为空");
                return false;
            }
            //
            Vue.set(movehere.tChildMove, 'chilCode', chil_code);
            Vue.set(movehere.tChildMove, 'chhePrehere', chhePrehere);
            Vue.set(movehere.tChildMove, 'chheHere', chheHere);
            Vue.set(movehere.tChildMove, 'chheChilProvinceremark', chheChilProvinceremark);
            $.ajax({
                type: "POST",
                url: "../tchildmove/save",
                data: JSON.stringify(movehere.tChildMove),
                contentType: 'application/json;charset=UTF-8',
                success: function (r) {
                    var index = layer.open();
                    layer.close(index);
                    if (r.code === 0) {
                        Vue.set(selecttypeHere.tChildInfo, 'chilHere', chheHere);
                        Vue.set(selecttypeHere.tChildInfo, 'chilCode', chil_code);
                        urltype(selecttypeHere.tChildInfo, chil_code);
                    } else {
                        alert(r.msg);
                    }
                }
            });
            layer.close(index);
        },  end:function () {
            $(".layui-layer-shade").css("display","none");

        }
    });
})

function display() {
    document.getElementById("box").style.display = "block";
}
function disappear() {
    document.getElementById("box").style.display = "none";
}
function display1() {
    document.getElementById("box1").style.display = "block";
}
function disappear1() {
    document.getElementById("box1").style.display = "none";
}

$("#btnReason").on('click', function () {
    var chil_code = $("#chilcodes").val();
    var chhePrehere = $("#hereId").val();//原在册情况
    var chheHere = $("#updatahere option:selected").val();//现在册情况
    var chheChilProvinceremark = $("#updataReason").val();
    if (chheChilProvinceremark==""||chheChilProvinceremark==null){
        layer.msg("变更原因不能为空");
        return false;
    }
    Vue.set(movehere.tChildMove, 'chilCode', chil_code);
    Vue.set(movehere.tChildMove, 'chhePrehere', chhePrehere);
    Vue.set(movehere.tChildMove, 'chheHere', chheHere);
    Vue.set(movehere.tChildMove, 'chheChilProvinceremark', chheChilProvinceremark);
    $.ajax({
        type: "POST",
        url: "../tchildmove/save",
        data: JSON.stringify(movehere.tChildMove),
        contentType: 'application/json;charset=UTF-8',
        success: function (r) {
            if (r.code === 0) {
                Vue.set(selecttypeHere.tChildInfo, 'chilHere', chheHere);
                Vue.set(selecttypeHere.tChildInfo, 'chilCode', chil_code);
                urltype(selecttypeHere.tChildInfo, chil_code);
                $('#myModal_here').modal('hide')
            } else {
                alert(r.msg);
            }
        }
    });
})

//登记页面儿童信息修改
function updatachilinfo() {
    var chil_code = $("#chilcodes").val();
    var updatachil_name = $("#updatachil_name").val()
    var updatamonther_name = $("#updatamonther_name").val()
    var updatafather_name = $("#updatafather_name").val()
    var updatatel = $("#updatatel").val();
    if($("#updatatel").val()!=""){
        if (!(/^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|"")$/.test(updatatel))) {
            alert('号码格式错误,座机需加区号和符号‘-’')
            return false;
        }
    }
    var updatamobile = $("#updatamobile").val()
    if (updatamobile!=""){
        if (!(/^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|"")$/.test(updatamobile))) {
            alert('号码格式错误,座机需加区号和符号‘-’')
            return false;
        }
    }
    var updatabirthday = $("#updatabirthday").val();
    var updatabirthdays ='';
    if(updatabirthday.length==10){
        updatabirthdays = $("#updatabirthday").val() + " 00:00:00";
    }else if (updatabirthday.length==8) {
        updatabirthdays = updatabirthday.substring(0,4)+"-"+updatabirthday.substring(4,6)+"-"+updatabirthday.substring(6,8)+" 00:00:00";
    }else{
        alert("请输入正确的日期格式：如：20180101或2018-01-01");
        return false;
    }
    // var updatabirthdays = $("#updatabirthday").val() + " 00:00:00";
    var updataaddress = $("#updataaddress").val()
    var updatahabiaddress = $("#updatahabiaddress").val()
    var updataremark = $("#updataremark").val()

    Vue.set(updata.tChildInfo, 'chilCode', chil_code);
    Vue.set(updata.tChildInfo, 'chilName', updatachil_name);
    Vue.set(updata.tChildInfo, 'chilAddress', updataaddress);
    Vue.set(updata.tChildInfo, 'chilHabiaddress', updatahabiaddress);
    Vue.set(updata.tChildInfo, 'chilBirthday', updatabirthdays);
    Vue.set(updata.tChildInfo, 'chilTel', updatatel);

    Vue.set(updata.tChildInfo, 'chilMobile', updatamobile);
    Vue.set(updata.tChildInfo, 'chilMother', updatamonther_name);
    Vue.set(updata.tChildInfo, 'chilFather', updatafather_name);
    Vue.set(updata.tChildInfo, 'remark', updataremark);
    urltype(updata.tChildInfo, chil_code);
}



//性别
function sexCode() {
    $.ajax({
        url: '../child/listdiccode?ttype=sex_code',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con = '';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
            }
            $("#updatasex").html(con);
            $("#updatasex1").html(con);
            $("#inosex").html(con);
        }
    });
}

//个案状态
function loadInfoStatusDatainput(){
    $.ajax({
        url: '../child/listdiccode?ttype=child_info_status',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con='';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
            }
            $("#inochilHere").html(con);

        }
    });
}
//户籍属性
function move1() {
    $.ajax({
        url: '../child/listdiccode?ttype=movetype_code',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var con = '';
            for (var i = 0; i < results.data.length; i++) {
                con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
            }
            $("#inoaccount").html(con);
            $("#inochilAccount").html(con);
        }
    });
}

function updateloadCommiteeData () {
    //初始化下拉框
    // $('#updatachilcommittee').selectpicker({
    //     'selectedText': 'cat',
    //     search:false,
    // });
    var param = new Array();
    $.ajax({
        url: '../tbasecommittee/list?org_id=' + orgId + '&page=1&limit=1000',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            // vm.chilCommittee = results.page.list;
            var con = '';
            for (var i = 0; i < results.page.list.length; i++) {
                con += '<option  value="' + results.page.list[i].code + '">' + results.page.list[i].name + '</option>';
            }
            $("#updatachilcommittee").append(con);
            $("#updatachilcommittees").append(con);
            // $("#updatachilcommittee").selectpicker('refresh');
        }
    });
}

$(function () {
    //双击事件
    $("#tel_1").on('dblclick', function () {
        $("#tel_1").css({"border-width": "1px"});
    })
//onblur事件
    $("#tel_1").on('blur', function () {
        $("#tel_1").css({"border-width": "0px"});
    })
    //双击事件
    $("#tel_2").on('dblclick', function () {
        $("#tel_2").css({"border-width": "1px"});
    })
//onblur事件
    $("#tel_2").on('blur', function () {
        $("#tel_2").css({"border-width": "0px"});
    })

    //双击事件
    $("#updatachil_name").on('dblclick', function () {
        $("#updatachil_name").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatachil_name").on('blur', function () {
        $("#updatachil_name").css({"border-width": "0px"});
    })


    $("#updatamobile").on('dblclick', function () {
        $("#updatamobile").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatamobile").on('blur', function () {
        $("#updatamobile").css({"border-width": "0px"});
    })

    $("#updatamonther_name").on('dblclick', function () {
        $("#updatamonther_name").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatamonther_name").on('blur', function () {
        $("#updatamonther_name").css({"border-width": "0px"});
    })


    $("#updatafather_name").on('dblclick', function () {
        $("#updatafather_name").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatafather_name").on('blur', function () {
        $("#updatafather_name").css({"border-width": "0px"});
    })


    $("#updatatel").on('dblclick', function () {
        $("#updatatel").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatatel").on('blur', function () {
        $("#updatatel").css({"border-width": "0px"});
    })


    $("#updatabirthday").on('dblclick', function () {
        $("#updatabirthday").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatabirthday").on('blur', function () {
        $("#updatabirthday").css({"border-width": "0px"});
    })

    $("#updataaddress").on('dblclick', function () {
        $("#updataaddress").css({"border-width": "1px"});
    })
//onblur事件
    $("#updataaddress").on('blur', function () {
        $("#updataaddress").css({"border-width": "0px"});
    })

    $("#updatahabiaddress").on('dblclick', function () {
        $("#updatahabiaddress").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatahabiaddress").on('blur', function () {
        $("#updatahabiaddress").css({"border-width": "0px"});
    })


    $("#updataremark").on('dblclick', function () {
        $("#updataremark").css({"border-width": "1px"});
    })
//onblur事件
    $("#updataremark").on('blur', function () {
        $("#updataremark").css({"border-width": "0px"});
    })


    $("#updatachil_name1").on('dblclick', function () {
        $("#updatachil_name1").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatachil_name1").on('blur', function () {
        $("#updatachil_name1").css({"border-width": "0px"});
    })


    $("#updatamontherName1").on('dblclick', function () {
        $("#updatamontherName1").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatamontherName1").on('blur', function () {
        $("#updatamontherName1").css({"border-width": "0px"});
    })


    $("#updatafatherName1").on('dblclick', function () {
        $("#updatafatherName1").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatafatherName1").on('blur', function () {
        $("#updatafatherName1").css({"border-width": "0px"});
    })


    $("#updatabirthday1").on('dblclick', function () {
        $("#updatabirthday1").css({"border-width": "1px"});
    })
//onblur事件
    $("#updatabirthday1").on('blur', function () {
        $("#updatabirthday1").css({"border-width": "0px"});
    })


    $("#inoname").on('dblclick', function () {
        $("#inoname").css({"border-width": "1px"});
    })
//onblur事件
    $("#inoname").on('blur', function () {
        $("#inoname").css({"border-width": "0px"});
    })

    $("#inobirthday").on('dblclick', function () {
        $("#inobirthday").css({"border-width": "1px"});
    })
//onblur事件
    $("#inobirthday").on('blur', function () {
        $("#inobirthday").css({"border-width": "0px"});
    })

    $("#inomontherName").on('dblclick', function () {
        $("#inomontherName").css({"border-width": "1px"});
    })
//onblur事件
    $("#inomontherName").on('blur', function () {
        $("#inomontherName").css({"border-width": "0px"});
    })

    $("#inofatherName").on('dblclick', function () {
        $("#inofatherName").css({"border-width": "1px"});
    })
//onblur事件
    $("#inofatherName").on('blur', function () {
        $("#inofatherName").css({"border-width": "0px"});
    })


    $("#inotel").on('dblclick', function () {
        $("#inotel").css({"border-width": "1px"});
    })
//onblur事件
    $("#inotel").on('blur', function () {
        $("#inotel").css({"border-width": "0px"});
    })


    $("#inoaddress").on('dblclick', function () {
        $("#inoaddress").css({"border-width": "1px"});
    })
//onblur事件
    $("#inoaddress").on('blur', function () {
        $("#inoaddress").css({"border-width": "0px"});
    })

    $("#inohabiaddress").on('dblclick', function () {
        $("#inohabiaddress").css({"border-width": "1px"});
    })
//onblur事件
    $("#inohabiaddress").on('blur', function () {
        $("#inohabiaddress").css({"border-width": "0px"});
    })

    $("#inoremark").on('dblclick', function () {
        $("#inoremark").css({"border-width": "1px"});
    })
//onblur事件
    $("#inoremark").on('blur', function () {
        $("#inoremark").css({"border-width": "0px"});
    })


    //显示下拉框
    $("#chilAccountId").on('click', function () {
        $("#updatachilAccount").css({"display": ""});
    })
    $("#hereId").on('click', function () {
        $("#updatahere").css({"display": ""});
    })
    $("#chilResidenceId").on('click', function () {
        $("#updatachilResidence").css({"display": ""});
    })
    $("#committeeId").on('click', function () {
        $("#updatachilcommittee").css({"display": ""});
    })
    $("#committeeIds").on('click', function () {
        $("#updatachilcommittees").css({"display": ""});
    })
    $("#sexId").on('click', function () {
        $("#updatasex").css({"display": ""});
    })
    $("#accountId1").on('click', function () {
        $("#updataaccount1").css({"display": ""});
    })
    $("#hereId1").on('click', function () {
        $("#updatahere1").css({"display": ""});
    })
    $("#chilResidenceId1").on('click', function () {
        $("#updatachilResidence1").css({"display": ""});
    })
    $("#sexId1").on('click', function () {
        $("#updatasex1").css({"display": ""});
    })
    $("#inosexId").on('click', function () {
        $("#inosex").css({"display": ""});
    })
//===================
    $("#inochilHereId").on('click', function () {
        $("#inochilHere").css({"display": ""});
    })
    $("#inochilAccountId").on('click', function () {
        $("#inochilAccount").css({"display": ""});
    })
// $("#inosexId").on('click', function () {
//     $("#inosex").css({"display": ""});
// })
// $("#inohereId").on('click',function () {
//     $("#inohere").css({"display": ""});
// })
    $("#inoaccountId").on('click', function () {
        $("#inoaccount").css({"display": ""});
    })

    //双击事件
    $("#inobirthday").on('dblclick', function () {
        $("#inobirthday").css({"border-width": "1px"});
    })
//onblur事件
    $("#inobirthday").on('blur', function () {
        $("#inobirthday").css({"border-width": "0px"});
    })
// //双击事件
    $("#inochilMother").on('dblclick', function () {
        $("#inochilMother").css({"border-width": "1px"});
    })
//onblur事件
    $("#inochilMother").on('blur', function () {
        $("#inochilMother").css({"border-width": "0px"});
    })
//双击事件
    $("#inochilMobile").on('dblclick', function () {
        $("#inochilMobile").css({"border-width": "1px"});
    })
//onblur事件
    $("#inochilMobile").on('blur', function () {
        $("#inochilMobile").css({"border-width": "0px"});
    })

})


