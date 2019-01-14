
var chilCode= getUrlVars()["chilCode"];
$(function () {
    vmEdit.loadInfoStatusData();
    vmEdit.loadNationData();
    vmEdit.move();
    vmEdit.residence();
    vmEdit.chilBirthday();//出生日期
    vmEdit.chilCreatedate();//建档日期
    vmEdit.loadCommiteeData();//行政村
    vmEdit.loadSchoolData();//学校
    vmEdit.loadHospitalData();//医院
    loadchilLeaveremark();
    vmEdit.getInfo(chilCode);
    $("#chilBirthdayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.chilBirthdayEnd = $("#chilBirthdayEnd").val();
    });
    $("#chilBirthdayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.chilBirthdayStart = $("#chilBirthdayStart").val();
    });
    $("#inoculateDateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.inoculateDateEnd = $("#inoculateDateEnd").val();
    });
    $("#inoculateDateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.inoculateDateStart = $("#inoculateDateStart").val();
    });
    $("#chilCreatedateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.chilCreatedateStart = $("#chilCreatedateStart").val();
    });
    $("#chilCreatedateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.chilCreatedateEnd = $("#chilCreatedateEnd").val();
    });
    $("#chilLeavedateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.chilLeavedateStart = $("#chilLeavedateStart").val();
    });
    $("#chilLeavedateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vmEdit.q.chilLeavedateEnd = $("#chilLeavedateEnd").val();
    });

    //加载页面时。出生日期获取焦点
    $("#chilBirthdayStart").focus();

    // dataproviceall2();//省份加载
});


var vmEdit = new Vue({
    el: '#rrapp',
    data: {
        q: {
            chilCode: null,
            chilName: null,
            chilCommittee: null,
            chilBirthdayStart: null,
            chilBirthdayEnd: null,
            chilHere: null,
            inoculateDateStart: null,
            inoculateDateEnd: null,
            chilCreatedateStart:null,
            chilCreatedateEnd:null,
            chilLeavedateStart:null,
            chilLeavedateEnd:null,
            isUpload: null,
            currentDepartChild:true

        },
        showList: true,
        title: null,
        tChildInfo: {},
        chilBirthhospitalname:null,
        chilCommittee:null
    },
    methods: {
        query: function () {
            var flag = checkParam();
            checkParam();

            vmEdit.showList = true;
            vmEdit.reload();
        },
        add: function () {
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

            vmEdit.showList = false;
            vmEdit.title = "新增";
            vmEdit.tChildInfo = {
                chilCreatesite: orgName,
                createUserName: userName,
                chilCreatedate: vmEdit.chilCreatedate()
            };
            vmEdit.loadAddress();
        },

        back: function () {
            //vmEdit.showList = true;
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            if ($("#chilForm").data('bootstrapValidator') != null) {
                $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
            }
        },
        saveOrUpdate: function (event) {

            childvalidator();
            $('#chilForm').bootstrapValidator('validate');//提交验证
            var hospital = $("#chilBirthhospitalname").val();
            var committee = $("#chilCommittee").val();
            if ($("#chilForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                //var habiaddress_town_id=$("#Province option:selected").val()+$("#City option:selected").val()+$("#Village option:selected").val()+$("#Twon option:selected").val();
                //var address_town_id = $("#Province_home option:selected").val()+$("#City_home option:selected").val()+$("#Village_home option:selected").val()+$("#Twon_home option:selected").val();
                var birthday = $("#chilBirthday").val();
                var chilCreatedate = $("#chilCreatedate").val();
                var chilHabiaddress_town_id = $("#Twon option:selected").val();
                var chilAddress_town_id = $("#Twon_home option:selected").val();
                var chil_createcounty = orgId.substring(0, 6);
                var chil_habi_id = $("#Village option:selected").val();
                var hospital_id = $("#chilBirthhospitalname option:selected").val();
                var hospital_name = $("#chilBirthhospitalname option:selected").text()=="请选择出生医院..."?"":$("#chilBirthhospitalname option:selected").text();
                var hbAdress=$("#hbaddress").val().replace(/\//g,' ');
                var chilHabiaddress;
                if(hbAdress!=undefined&&hbAdress!=null&&hbAdress!=""){
                    chilHabiaddress=hbAdress+"-"+$("#hbDetailed").val();
                }else{
                    chilHabiaddress=$("#hbDetailed").val();
                }
                Vue.set(vmEdit.tChildInfo, 'chilHabiaddress', chilHabiaddress);

                var adress=$("#nowaddress").val().replace(/\//g,' ');
                var chilAddress;
                if(adress!=undefined&&adress!=null&&adress!=""){
                    chilAddress=adress+"-"+$("#nowDetailed").val();
                }else{
                    chilAddress=$("#nowDetailed").val();
                }
                Vue.set(vmEdit.tChildInfo, 'chilAddress',chilAddress);

                Vue.set(vmEdit.tChildInfo, 'chilBirthday', birthday);
                Vue.set(vmEdit.tChildInfo, 'chilCreatedate', chilCreatedate);
                Vue.set(vmEdit.tChildInfo, 'chilHabiaddressTownId', chilHabiaddress_town_id);
                Vue.set(vmEdit.tChildInfo, 'chilAddressTownId', chilAddress_town_id);
                Vue.set(vmEdit.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(vmEdit.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(vmEdit.tChildInfo, 'chilHabiId', chil_habi_id);
                Vue.set(vmEdit.tChildInfo, 'chilBirthhospital', hospital_id);
                Vue.set(vmEdit.tChildInfo, 'chilBirthhospitalname', hospital_name);
                Vue.set(vmEdit.tChildInfo, 'chilCommittee', committee);

                var url = vmEdit.tChildInfo.chilCode == null ? "../child/save" : "../child/update";
                $("#saveOrUpdate").attr("disabled",'disabled');
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vmEdit.tChildInfo),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        $("#saveOrUpdate").removeAttr("disabled");
                        if (r.code === 0) {
                            //vm.reload();
                            if(parent.registerVM!=null) {
                                parent.registerVM.showList = true;
                                var page = parent.$("#selectResultTable").jqGrid('getGridParam', 'page');
                                parent.$("#selectResultTable").jqGrid('setGridParam', {
                                    postData: {
                                        'chilCode': chilCode,
                                        'chilName': null,
                                        'chilBirthdayStart': null,
                                        'chilBirthdayEnd': null
                                    },
                                    page: page,
                                    url: '../child/list'
                                }).trigger("reloadGrid");
                                var index = parent.layer.getFrameIndex(window.name);
                                setTimeout(function () {
                                    return parent.layer.close(index)
                                }, 1000);
                            }
                            if(parent.vm!=null) {
                                /*
                             * 先清空条件
                             * jqgrid postData setGridParam 调用多次时查询条件会累加
                             */
                                var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
                                $.each(postData, function (k, v) {
                                    delete postData[k];
                                });
                                parent.vm.showList = true;
                                var page = parent.$("#jqGrid").jqGrid('getGridParam', 'page');
                                parent.$("#jqGrid").jqGrid('setGridParam', {
                                    postData: {
                                        'chilCode': chilCode,
                                        'chilName': null,
                                        'chilBirthdayStart': null,
                                        'chilBirthdayEnd': null
                                    },
                                    page: page,
                                    url: '../child/list'
                                }).trigger("reloadGrid");
                                var index = parent.layer.getFrameIndex(window.name);
                                setTimeout(function () {
                                    return parent.layer.close(index)
                                }, 1000);
                            }
                            if(parent.newBornVm!=null) {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index)

                            }
                            if(parent.childGatherVM!=null) {
                                parent.childGatherVM.showList = true;
                                parent.childGatherVM.reload();
                                if(parent.$("#chilForm").data('bootstrapValidator')!=null) {
                                    parent.$("#chilForm").data('bootstrapValidator').resetForm("chilForm");
                                }
                                var page =parent.$("#childInfoGrid").jqGrid('getGridParam', 'page');
                                parent.$("#childInfoGrid").jqGrid('setGridParam',{
                                    postData:parent.childGatherVM.condition,
                                    page: page,
                                    url: '../child/imperfectChild'
                                }).trigger("reloadGrid");
                                var index = parent.layer.getFrameIndex(window.name);
                                     parent.layer.close(index);
                            }
                            if(parent.vm!=null) {
                                /*
                             * 先清空条件
                             * jqgrid postData setGridParam 调用多次时查询条件会累加
                             */
                                var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
                                $.each(postData, function (k, v) {
                                    delete postData[k];
                                });
                                parent.vm.showList = true;
                                var page = parent.$("#jqGrid").jqGrid('getGridParam', 'page');
                                parent.$("#jqGrid").jqGrid('setGridParam', {
                                    postData: {
                                        'chilCode': chilCode,
                                        'chilName': null,
                                        'chilBirthdayStart': null,
                                        'chilBirthdayEnd': null
                                    },
                                    page: page,
                                    url: '../child/list'
                                }).trigger("reloadGrid");
                                var index = parent.layer.getFrameIndex(window.name);
                                setTimeout(function () {
                                    return parent.layer.close(index)
                                }, 1000);
                            }
                            if(parent.imperfectChildVm!=null) {
                                parent.imperfectChildVm.showList = true;
                                var page =parent.$("#jqGrid").jqGrid('getGridParam', 'page');
                                parent.$("#jqGrid").jqGrid('setGridParam',{
                                    postData:{org_id:orgId
                                    },
                                    page: page,
                                    url: '../child/imperfectChild'
                                }).trigger("reloadGrid");
                                if(parent.$("#chilForm").data('bootstrapValidator')!=null) {
                                    parent.$("#chilForm").data('bootstrapValidator').resetForm("chilForm");
                                }
                                var index = parent.layer.getFrameIndex(window.name);
                                     parent.layer.close(index)
                            }
                            layer.msg('修改成功');
                        } else {
                            layer.msg(r.msg);
                        }
                    }
                });
            } else {
                return false;
            }

        },

        getInfo: function (chilCode) {
            $.ajax({
                type: "GET",
                url: "../child/info/" + chilCode,
                contentType: 'application/json;charset=UTF-8',
                success: function (r) {
                    /* reSetAddress(r);noneSelectedText*/
                    vmEdit.tChildInfo = r.tChildInfo;
                    $('#chilCommittee').selectpicker('val',(vmEdit.tChildInfo.chilCommittee));
                    $('#chilBirthhospitalname').selectpicker('val', (vmEdit.tChildInfo.chilBirthhospital));

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

                }
            });

        },
        reload: function (event) {
            vmEdit.showList = true;

            /*
           * 先清空条件
           * jqgrid postData setGridParam 调用多次时查询条件会累加
           */
            var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            Vue.set(vmEdit.q, 'chilCommittee', $("#chilCommittees").val());

            $("#jqGrid").jqGrid('setGridParam', {
                postData: vmEdit.q,
                url: '../child/list',
                page: page
            }).trigger("reloadGrid");

            window.localStorage.setItem("chilCode",vmEdit.q.chilCode);
            window.localStorage.setItem("chilName",vmEdit.q.chilName);
            window.localStorage.setItem("chilCommittee",vmEdit.q.chilCommittee);
            window.localStorage.setItem("chilBirthdayStart",vmEdit.q.chilBirthdayStart);
            window.localStorage.setItem("chilBirthdayEnd",vmEdit.q.chilBirthdayEnd);
            window.localStorage.setItem("chilHere",vmEdit.q.chilHere);
            window.localStorage.setItem("inoculateDateStart",vmEdit.q.inoculateDateStart);
            window.localStorage.setItem("inoculateDateEnd",vmEdit.q.inoculateDateEnd);
            window.localStorage.setItem("chilCreatedateStart",vmEdit.q.chilCreatedateStart);
            window.localStorage.setItem("chilCreatedateEnd",vmEdit.q.chilCreatedateEnd);
            window.localStorage.setItem("chilLeavedateStart",vmEdit.q.chilLeavedateStart);
            window.localStorage.setItem("chilLeavedateEnd",vmEdit.q.chilLeavedateEnd);
            window.localStorage.setItem("chilMother",vmEdit.q.chilMother);
            window.localStorage.setItem("isUpload",vmEdit.q.isUpload);
            window.localStorage.setItem("currentDepartChild",vmEdit.q.currentDepartChild);

            if ($("#chilForm").data('bootstrapValidator') != null) {
                $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
            }
            $("#chilCommittees").selectpicker('val',$("#chilCommittees").find('option:first').val());
            // vm.reset();
        },
        loadAddress:function () {
           var address=getCurrentAddress();
            $("#hbaddress").citypicker("reset");
            $("#hbaddress").citypicker("destroy");
            $("#hbaddress").val(address);
            $("#hbaddress").citypicker({
                province: '',
                city: '',
                district: '',
                county:''
            });

            $("#nowaddress").citypicker("reset");
            $("#nowaddress").citypicker("destroy");
            $("#nowaddress").val(address);
            $("#nowaddress").citypicker({
                province: '',
                city: '',
                district: '',
                county:''
            });

        },
        //民族
        loadNationData: function () {
            $.ajax({
                url: '../child/listdiccode?ttype=nation_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
                    }
                    $("#chilNatiId").html(con);
                    /* var result=results.data;
                     $.each(result, function (index, item) {
                         param.push({"text":item.text,"value":item.value});
                     });
                     vm.items = param;*/
                }
            });
        },
        //个案状态
        loadInfoStatusData: function () {
            var param = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_info_status',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
                    }
                    $("#infostatus").append(con);
                    $("#chilHere").html(con);
                    $("#chheHere").html(con);

                }
            });
        },
        //行政村
        loadCommiteeData: function () {
            //初始化下拉框
            $('#chilCommittees').selectpicker({
                'selectedText': 'cat',
                search:false,
            });
            var param = new Array();
            $.ajax({
                url: '../tbasecommittee/list?org_id=' + orgId + '&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    vmEdit.chilCommittee = results.page.list;
                    var con = '';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].code + '">' + results.page.list[i].name + '</option>';
                    }
                    /*$("#chilCommittee").html(con);
                    $("#chilCommittees").append(con);*/
                    $("#chilCommittees").append(con);
                    $("#chilCommittee").append(con);
                    $("#chilCommittees").selectpicker('refresh');
                    $("#chilCommittee").selectpicker('refresh');

                }
            });
        },
        //学校
        loadSchoolData: function () {
            var param = new Array();
            $.ajax({
                url: '../tbaseschool/list?org_id=' + orgId + '&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].code + '">' + results.page.list[i].name + '</option>';
                    }
                    $("#chilSchool").html(con);

                }
            });
        },

        //医院
        loadHospitalData: function () {
            //初始化下拉框
            $('.selectpicker').selectpicker({
                'selectedText': 'cat',
                noneSelectedText: '请选择出生医院...',
                actionsBox: true,
                search:false,
            });
            var param = new Array();
            $.ajax({
                url: '../tbasehospital/list?page=1&limit=10000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    vmEdit.chilBirthhospitalname = results.page.list;
                    var con = '';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].hospitalCode + '">' + results.page.list[i].hospitalName + '</option>';
                    }
                    $("#chilBirthhospitalname").append(con);
                    $("#chilBirthhospitalname").selectpicker('refresh');

                }
            });
        },
        chilBirthday: function () {
            $('#chilBirthday').datetimepicker({
                language: 'zh-CN',//显示中文
                format: 'yyyy-mm-dd hh:ii:ss',//显示格式
                minView: "month",//设置只显示到月份
                initialDate: new Date(),
                forceParse: true,
                autoclose: true,//选中自动关闭
                todayBtn: true,//显示今日按钮
                locale: moment.locale('zh-cn')
            }).on('changeDate', function (ev) {
                vmEdit.tChildInfo.chilBirthday = $("#chilBirthday").val();
            }).on('hide', function (e) {
                $('#chilForm').data('bootstrapValidator')
                    .updateStatus('chilBirthday', 'NOT_VALIDATED', null)
                    .validateField('chilBirthday');
            });

        },
        chilCreatedate: function () {
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
            var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate() + " " + today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
            //对日期格式进行处理
            var date = new Date(nowdate);
            var mon = date.getMonth() + 1;
            var day = date.getDate();
            var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day) + " " + today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
            /*document.getElementById("chilCreatedate").value = mydate;*/
            return mydate;
        },

        //户籍属性
        move: function () {
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=movetype_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
                    }
                    $("#chilAccount").html(con);
                }
            });
        },

        //居住属性
        residence: function () {
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_residence_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
                    }
                    $("#chilResidence").html(con);
                }
            });
        },


    }
});


$(function () {
    //医院根据拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilBirthhospitalnameIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#chilBirthhospitalnameIdParent .open input').val();
        var hunt = $("#chilBirthhospitalname");
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
            var value = vmEdit.chilBirthhospitalname;
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
            var value = vmEdit.chilBirthhospitalname;
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
                value = vmEdit.chilCommittee;
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

    // 行政村/居委会拼音搜索(搜索条件)
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilCommittees").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#chilCommittees");
        var value = vmEdit.chilCommittee;
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
                    if(value[i].name.indexOf(inputManagerName) == 0){
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



