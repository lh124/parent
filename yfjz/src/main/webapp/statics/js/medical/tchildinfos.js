$(function () {

    $("#jqGrid").jqGrid({
        url: '',
        // url: '../child/list',
        datatype: "json",
        colModel: [
            { label: '儿童编码', name: 'chilCode', width: 170, key: true },
            { label: '姓名', name: 'chilName', width: 80 },
            { label: '性别', name: 'chilSex', width: 60,
                formatter: function (r) {
                    if(r == 1){
                        return '男';
                    }else if(r == 2){
                        return '女';
                    }else if (r == null){
                        return '';
                    }
                    else{
                        return r;
                    }
                }
            },
            { label: '出生日期', name: 'chilBirthday', width: 85 },
            { label: '出生体重（kg）', name: 'chilBirthweight', width: 100 },
            { label: '民族', name: 'chilNatiId', width: 60 },
            { label: '免疫卡号', name: 'chilCardno', width: 80 },
            /*{ label: '身份证号', name: 'chilNo', width: 80 }, */
            /*{ label: '出生证号', name: 'chilBirthno', width: 80 }, */
            { label: '母亲姓名', name: 'chilMother', width: 80 },
            { label: '父亲姓名', name: 'chilFather', width: 80 },
            { label: '行政村/居委会', name: 'chilCommittee', width: 80 },
            { label: '户籍地址', name: 'chilHabiaddress', width: 180 ,
                formatter:function (value,row,index) {
                    try{
                        return JSON.parse(value).position;
                    }catch(e) {
                        return value;
                    }
                }
            },
            { label: '现居地址', name: 'chilAddress', width: 180,
                formatter:function (value,row,index) {
                    try{
                        return JSON.parse(value).position;
                    }catch(e) {
                        if(isNaN(value)){
                            return value;
                        }else{
                            return "";
                        }

                    }
                }
            },
            { label: '家庭电话', name: 'chilTel', width: 80 },
            { label: '手机', name: 'chilMobile', width: 100 },
            { label: '学校', name: 'chilSchool', width: 80 },
            /*{ label: '发卡日期', name: 'chilCarddate', width: 80 },
            { label: '健康档案标识', name: 'chilHealthno', width: 80 },
            { label: '平台字段,未知属性', name: 'chilResidenceclient', width: 80 }, 	*/
            { label: '居住属性', name: 'chilResidence', width: 80
            },
            { label: '户籍属性', name: 'chilAccount', width: 80
            },
            { label: '在册情况', name: 'chilHere', width: 80

            },
            { label: '建档日期', name: 'chilCreatedate', width: 150},
            { label: '建档人', name: 'createUserName', width: 80 },
            { label: '出生医院', name: 'chilBirthhospitalname', width: 80 },
            { label: '母亲身份证号', name: 'chilMotherno', width: 180 },
            { label: '父亲身份证号', name: 'chilFatherno', width: 180 },
            { label: '备注', name: 'remark', width: 80 }
        ],
        viewrecords: true,
        height: 220,
        rowNum: 10,
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
            // $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        // 延后
        delay: function () {

        },
        // 呼叫
        invoke: function () {

        },
        // 暂停
        pause: function () {

        },
        // 取消
        cancel: function () {

        },
        // 刷新
        refresh: function() {

        },
        pushTop: function() {

        },
        pushBottom: function() {

        },
        gridComplete: function () {
            var row = $("#jqGrid").getDataIDs();
            if (row.length == 1) {
                $("#jqGrid").setSelection(row[0]);
            }
        },
        onSelectRow:function(rowid,iRow,iCol,e){

            var rowData = $("#jqGrid").jqGrid('getRowData',rowid);

            $("#tchildhealthcare_iframe").attr("src", "../medical/tchildhealthcare.html?chilCode="+rowid+"&chilName="+rowData.chilName+"&sequenceNoId="+vm.sequenceNoId);
        }
    });
    vm.loadCommiteeData();
    vm.loadData();
    vm.loadSchoolData();
    vm.recommend = [];
    vm.loadHospitalData();//医院
    Registrationmonitoring();

    // dataproviceall();//省份加载
    // dataproviceall2();//省份加载
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilBirthhospitalnameIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#chilBirthhospitalnameIdParent .open input').val();
        var hunt = $("#chilBirthhospitalname");
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
            var value = vm.chilBirthhospitalname;
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
            var value = vm.chilBirthhospitalname;
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
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
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
            if("undefined" != typeof registerVM){
                value = registerVM.chilCommittee;
            }
            if("undefined" != typeof vm){
                value = vm.chilCommittee;
            }
            if("undefined" != typeof childGatherVM){
                value = childGatherVM.chilCommittee;
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



function loadTypeData(listType,url) {
    var param = new Array();//定义数组
    $.ajax({
        // get请求地址
        url: url,
        dataType: "json",
        success: function (data) {
            var result = data.data;
            $.each(result, function (index, item) {
                param.push({"text": item.text, "value": item.value});
            });
            vm[listType] = param;
        }
    });

};


var setchilcode;
var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            chilName: null,
            chilCode:null,
            chilCommittee :null,
            chilBirthdayStart:null,
            chilBirthdayEnd:null,
            chilHere:null

        },
        showList: true,
        title: null,
        tChildInfo: {},
        childCode:null,
        childName:null,
        items:[],//下拉绑定
        itemss:[],
        juzhus:[],
        hukou:[],
        account :[],
        infostatus:[],
        infostatuss:[],
        leavecode:[],
        chilBirthhospitalname:null,
        chilCommittee:null,
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){

            vm.showList = false;
            vm.title = "新增";
            $('#Province').selectpicker('val', "");
            $('#City').selectpicker('val', "");
            $('#Village').selectpicker('val', "");
            $('#Twon').selectpicker('val', "");
            $("#detailed_home").val("");
            $("#detailed").val("");
            $('#Province_home').selectpicker('val', "");
            $('#City_home').selectpicker('val', "");
            $('#Village_home').selectpicker('val', "");
            $('#Twon_home').selectpicker('val', "");

            // vm.Bind();
            // vm.ProviceBind();//省
            // vm.Provice_homeBind();//省
            // vm.CityBind();//市
            // vm.City_homeBind();//市
            // vm.VillageBind();//县
            // vm.Village_homeBind();//县
            // vm.Twon();//乡镇
            // vm.Twon_home();//乡镇
            vm.tChildInfo = {
                chilCreatesite:orgName,
                createUserName:userName,
                chilCreatedate:vm.chilCreatedate()
            };
        },
        update: function (event) {
            var chilCode = getSelectedRow_child();
            if(chilCode == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getInfo(chilCode)
        },
        loadData : function(){
            loadTypeData("items","../child/listdiccode?ttype=nation_code");
            loadTypeData("account","../child/listdiccode?ttype=movetype_code");
            loadTypeData("hukou","../child/listdiccode?ttype=child_residence_code");
            loadTypeData("infostatus","../child/listdiccode?ttype=child_info_status");
            loadTypeData("infostatuss","../child/listdiccode?ttype=child_info_status");
            loadTypeData("leavecode","../child/listdiccode?ttype=leave_code");
        },
        //学校
        loadSchoolData:function(){
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
        },
        //医院
        loadHospitalData:function(){
            //初始化下拉框
            $('.selectpicker').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择医院...',
                actionsBox:true,
                search:false,
            });
            var param = new Array();
            $.ajax({
                url: '../tbasehospital/list?page=1&limit=10000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    vm.chilBirthhospitalname = results.page.list;
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].hospitalCode + '">'+results.page.list[i].hospitalName+'</option>';
                    }
                    $("#chilBirthhospitalname").append(con);
                    $("#chilBirthhospitalname").selectpicker('refresh');

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
                    $("#chilCommittee").append(con);
                    $("#chilCommittee").selectpicker('refresh');
                }
            });
        },

        loadCallChild: function () {
            if (!setchilcode) {
                return;
            }
            //显示查询结果列表
            // var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                url: '../child/list?page=1&chilCode='+setchilcode
            }).trigger("reloadGrid");

        },

        saveOrUpdate: function (event) {

            childvalidator();
            $('#chilForm').bootstrapValidator('validate');//提交验证

            if ($("#chilForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码

                var birthday = $("#chilBirthday").val();
                var chilCreatedate = $("#chilCreatedate").val();
                var chilHabiaddress_town_id = $("#Twon option:selected").val();
                var chilAddress_town_id = $("#Twon_home option:selected").val();
                var chil_createcounty = orgId.substring(0, 6);
                var chil_habi_id = $("#Village option:selected").val();
                var hospital_id = $("#chilBirthhospitalname option:selected").val();
                var hospital_name = $("#chilBirthhospitalname option:selected").text()=="请选择出生医院..."?"":$("#chilBirthhospitalname option:selected").text();
                var chilCommittee = $("#chilCommittee option:selected").val();
                var chilLeave = $("#chilLeave option:selected").val();

                // Vue.set(vm.tChildInfo, 'chilHabiaddress', habiaddress);
                // Vue.set(vm.tChildInfo, 'chilAddress', address);

                var hbAdress=$("#hbaddress").val().replace(/\//g,' ');
                var chilHabiaddress;
                if(hbAdress!=undefined&&hbAdress!=null&&hbAdress!=""){
                    chilHabiaddress=hbAdress+"-"+$("#hbDetailed").val();
                }else{
                    chilHabiaddress=$("#hbDetailed").val();
                }
                Vue.set(vm.tChildInfo, 'chilHabiaddress', chilHabiaddress);

                var adress=$("#nowaddress").val().replace(/\//g,' ');
                var chilAddress;
                if(adress!=undefined&&adress!=null&&adress!=""){
                    chilAddress=adress+"-"+$("#nowDetailed").val();
                }else{
                    chilAddress=$("#nowDetailed").val();
                }
                Vue.set(vm.tChildInfo, 'chilAddress',chilAddress);

                Vue.set(vm.tChildInfo, 'chilBirthday', birthday);
                Vue.set(vm.tChildInfo, 'chilCreatedate', chilCreatedate);
                Vue.set(vm.tChildInfo, 'chilHabiaddressTownId', chilHabiaddress_town_id);
                Vue.set(vm.tChildInfo, 'chilAddressTownId', chilAddress_town_id);
                Vue.set(vm.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(vm.tChildInfo, 'chilHabiId', chil_habi_id);
                Vue.set(vm.tChildInfo, 'chilBirthhospital', hospital_id);
                Vue.set(vm.tChildInfo, 'chilBirthhospitalname', hospital_name);
                Vue.set(vm.tChildInfo, 'chilCommittee', chilCommittee);//行政村
                Vue.set(vm.tChildInfo, 'chilLeave', chilLeave);//行政村

                var url = vm.tChildInfo.chilCode == null ? "../child/save" : "../child/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tChildInfo),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }

        },
        del: function (event) {
            var chilCodes = getSelectedRows();
            if(chilCodes == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../child/delete",
                    data: JSON.stringify(chilCodes),
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
        getInfo: function(chilCode){
            $.ajax({
                type: "GET",
                url: "../child/info/"+chilCode,
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    /* reSetAddress(r);*/
                    vm.tChildInfo = r.tChildInfo;
                    $('#chilBirthhospitalname').selectpicker('val',(vm.tChildInfo.chilBirthhospital));
                    // vm.Bind();
                    // vm.ProviceBind(r.tChildInfo);//省
                    // vm.Provice_homeBind(r.tChildInfo);//省
                    // vm.CityBind(r.tChildInfo);//市
                    // vm.City_homeBind(r.tChildInfo);//市
                    // vm.VillageBind(r.tChildInfo);//县
                    // vm.Village_homeBind(r.tChildInfo);//县
                    // vm.Twon(r.tChildInfo);//乡镇
                    // vm.Twon_home(r.tChildInfo);//乡镇

                    var chilHabiaddress = r.tChildInfo.chilHabiaddress;
                    var chilAddress = r.tChildInfo.chilAddress;


                    if(chilHabiaddress.indexOf('-')!=-1){
                        var hbAdress=chilHabiaddress.split("-");
                        $("#hbaddress").citypicker("reset");
                        $("#hbaddress").citypicker("destroy");
                        $("#hbaddress").val(hbAdress[0].replace(/\s+/g,"/"));
                        $("#hbaddress").citypicker({
                            province: '',
                            city: '',
                            district: '',
                            county:''
                        });
                        $("#hbDetailed").val(hbAdress[1]);
                    }else{
                        $("#hbaddress").citypicker("reset");
                        $("#hbDetailed").val(chilHabiaddress);
                    }

                    if(chilAddress.indexOf('-')!=-1){
                        var adress=chilAddress.split("-");
                        $("#nowaddress").citypicker("reset");
                        $("#nowaddress").citypicker("destroy");
                        $("#nowaddress").val(adress[0].replace(/\s+/g,"/"));
                        $("#nowaddress").citypicker({
                            province: '',
                            city: '',
                            district: '',
                            county:''
                        });
                        $("#nowDetailed").val(adress[1]);
                    }else{
                        $("#nowaddress").citypicker("reset");
                        $("#nowDetailed").val(chilAddress);
                    }

                    // $("#detailed").val(chilHabiaddress);
                    // $("#detailed_home").val(chilAddress);
                    // var chilHabiaddressTownId=   r.tChildInfo.chilHabiaddressTownId;
                    // var chilAddressTownId=  r.tChildInfo.chilAddressTownId;
                    // console.log(chilHabiaddressTownId);
                    // console.log(chilAddressTownId);
                    // if (chilHabiaddressTownId!=null){
                    //     $('#Province').selectpicker('val', chilHabiaddressTownId.substring(0,2));
                    //     datacityall(chilHabiaddressTownId.substring(0,2));
                    //     $('#City').selectpicker('val', chilHabiaddressTownId.substring(0,4)+"00000000");
                    //     datavallageall(chilHabiaddressTownId.substring(0,4)+"00000000");
                    //     $('#Village').selectpicker('val', chilHabiaddressTownId.substring(0,6)+"000000");
                    //     datatownall(chilHabiaddressTownId.substring(0,6)+"000000");
                    //     $('#Twon').selectpicker('val', chilHabiaddressTownId.substring(0,9)+"000");
                    // }else{
                    //     $('#Province').selectpicker('val', "");
                    //     $('#City').selectpicker('val', "");
                    //     $('#Village').selectpicker('val', "");
                    //     $('#Twon').selectpicker('val', "");
                    // }
                    // if (chilAddressTownId!=null){
                    //     $('#Province_home').selectpicker('val', chilAddressTownId.substring(0,2));
                    //     datacityall2(chilAddressTownId.substring(0,2));
                    //     $('#City_home').selectpicker('val', chilAddressTownId.substring(0,4)+"00000000");
                    //     datavallageall2(chilAddressTownId.substring(0,4)+"00000000");
                    //     $('#Village_home').selectpicker('val', chilAddressTownId.substring(0,6)+"000000");
                    //     datatownall2(chilAddressTownId.substring(0,6)+"000000");
                    //     $('#Twon_home').selectpicker('val', chilAddressTownId.substring(0,9)+"000");
                    // }else{
                    //     $('#Province_home').selectpicker('val', "");
                    //     $('#City_home').selectpicker('val', "");
                    //     $('#Village_home').selectpicker('val', "");
                    //     $('#Twon_home').selectpicker('val', "");
                    // }


                    // if(chilHabiaddress!=null && chilHabiaddress.length<9){
                    //     $("#detailed").val(chilHabiaddress);
                    // }else if(chilHabiaddress!=null){
                    //     if(chilHabiaddress.lastIndexOf("乡")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("乡")+1));
                    //     }
                    //     if(chilHabiaddress.lastIndexOf("镇")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("镇")+1));
                    //     }
                    //     if(chilHabiaddress.lastIndexOf("处")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("处")+1));
                    //     }
                    //     if((chilHabiaddress.lastIndexOf("区")!=-1 || chilHabiaddress.lastIndexOf("县")!=-1)&& chilHabiaddress.lastIndexOf(" ")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("区")+1));
                    //     }
                    //
                    // }
                    // if(chilAddress!=null && chilAddress.length<9){
                    //
                    //     $("#detailed_home").val(r.tChildInfo.chilAddress);
                    // }
                    // else if(chilAddress!=null ){
                    //     if(chilAddress.lastIndexOf("乡")!=-1){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("乡")+1));
                    //     }
                    //     if(chilAddress.lastIndexOf("镇")!=-1){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("镇")+1));
                    //     }
                    //     if(chilAddress.lastIndexOf("处")!=-1){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("处")+1));
                    //     }
                    //     if((chilAddress.lastIndexOf("区")!=-1||chilAddress.lastIndexOf("县")!=-1)&& chilAddress.lastIndexOf(" ")!=-1 ){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("县")+1));
                    //     }
                    // }
                }
            });
        },

        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'chilName': vm.q.chilName,
                    'chilCode':vm.q.chilCode,
                    // 'chilBirthno':vm.q.chilBirthno,
                    // 'chilMother':vm.q.chilMother,
                    // 'chilBirthday':$("#searchDate").val(),
                    'chilCommittee' :vm.q.chilCommittee,
                    'chilBirthdayStart':vm.q.chilBirthdayStart,
                    'chilBirthdayEnd':vm.q.chilBirthdayEnd,
                    'chilHere':vm.q.chilHere
                },
                url: '../child/list',
                page:page
            }).trigger("reloadGrid");
            $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
        },

        Bind:function(str) {
            // alert($("#Province").html());
            $("#Province").val(str);
            $("#Province_home").val(str);
        },
        ProviceBind:function(tChildInfo) {
            //清空下拉数据
            $("#Province").html("");
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
                            if(tChildInfo.chilHabiaddressTownId!=null && item.id==tChildInfo.chilHabiaddressTownId.substring(0,2)){
                                str += "<option value=" + item.id +" selected"+ ">" + item.name + "</option>";
                            }else if(tChildInfo.chilHabiId!=null && item.id==tChildInfo.chilHabiId.substring(0,2)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(orgId!=null && item.id ==orgId.substring(0,2)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
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
        },
        Provice_homeBind:function(tChildInfo) {
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
                            }else if(orgId!=null &&item.id==orgId.substring(0,2)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
                                str += "<option value=" + item.id +">" + item.name + "</option>";
                            }
                        }else if(orgId!=null && item.id == orgId.substring(0,2)){
                            str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                        }else{
                            str += "<option value=" + item.id +">" + item.name + "</option>";
                        }
                    });
                    //将数据添加到省份这个下拉框里面
                    $("#Province_home").html(str);
                },
                error: function () { alert("Error"); }
            });
        },
        CityBind:function(tChildInfo) {

            var provice = $("#Province option:selected").val();
            //判断省份这个下拉框选中的值是否为空
            if (provice == "") {
                return;
            }
            $("#City").html("");
            var str = "";
            // var str = "<option>==请选择===</option>";
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
                            }else if(orgId!=null && item.id==orgId.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
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
            vm.VillageBind(tChildInfo);
        },
        City_homeBind:function(tChildInfo) {
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
                            else if(orgId!=null && item.id==orgId.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
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
            vm.Village_homeBind(tChildInfo);
        },

        VillageBind:function (tChildInfo) {
            // var  provice = $("#City").attr("value");
            var provice = $("#City option:selected").val();
            console.log("市:"+provice);
            //判断市这个下拉框选中的值是否为空
            if (provice == "") {
                return;
            }
            $("#Village").html("");
            // var str = "<option>==请选择===</option>";
            var str = "";
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
                            }else if(orgId!=null && item.id==orgId.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
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
        },
        Village_homeBind:function (tChildInfo) {
            // var  provice = $("#City").attr("value");
            var provice_home = $("#City_home option:selected").val();
            console.log("市:"+provice_home);
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
                            else if(orgId!=null && item.id==orgId.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
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
        },
        Twon:function(tChildInfo) {
            // var  provice = $("#City").attr("value");
            var country = $("#Village option:selected").val();
            //判断市这个下拉框选中的值是否为空
            if (country == "") {
                return;
            }
            $("#Twon").html("");
            // var str = "<option>==请选择===</option>";
            var str = "";
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
        },
        Twon_home:function(tChildInfo) {
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
        },

    }
});

//监听队列呼叫数据， 子组件向父组件发送广播  this.$parent.$emit("pushCurrentNo",this.currentNumber);
vm.$on("callingNumber",function(queue){

    this.sequenceNoId = queue.id;
    if (queue.childCode != null) {
        setchilcode = queue.childCode;
    }
    vm.loadCallChild();
});

//选择一条记录
function getSelectedRow_child() {

    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return rowKey;
}
vm.$on("registerFinish",function(){
    //$("#currentDayWaitInocChild").jqGrid('clearGridData');
    setTimeout(function () {
        Registrationmonitoring();
    },500)
    // grid.jqGrid("clearGridData");
});