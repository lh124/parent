$(function () {
    $("#jqGrid").jqGrid({
        url: '../child/list',
        datatype: "json",
        colModel: [
            {label: '姓名', name: 'chilName', width: 80, align: 'center',
                formatter:function updateChildFormatter(cellValue, options, rowdata, action){
                    return "<a href=\"javascript:void(0);\" onclick='showChildDetail("+JSON.stringify(rowdata).replace(/"/g, '&quot;')+")'>"+cellValue+"</a>";
                }
            },
            {label: '性别', name: 'chilSex', width: 60, align: 'center'},
            {label: '出生日期', name: 'chilBirthday', width: 150, align: 'center'},
           /* {label: '出生体重（kg）', name: 'chilBirthweight', width: 100, align: 'center'},
            {label: '民族', name: 'chilNatiId', width: 60, align: 'center'},
            {label: '免疫卡号', name: 'chilCardno', width: 80, align: 'center'},*/
            /*{ label: '身份证号', name: 'chilNo', width: 80 }, */
            /*{ label: '出生证号', name: 'chilBirthno', width: 80 }, */
            {label: '母亲姓名', name: 'chilMother', width: 80, align: 'center'},
            {label: '父亲姓名', name: 'chilFather', width: 80, align: 'center'},
            {label: '行政村/居委会', name: 'chilCommittee', width: 120, align: 'center'},
            {label: '在册情况', name: 'chilHere', width: 80},
            {
                label: '户籍地址', name: 'chilHabiaddress', width: 180, align: 'center'
            },
            {
                label: '现居地址', name: 'chilAddress', width: 180, align: 'center'
            },
            {label: '家庭电话', name: 'chilTel', width: 80, align: 'center'},
            {label: '手机', name: 'chilMobile', width: 100, align: 'center'},
            /*{label: '学校', name: 'chilSchool', width: 80, align: 'center'},*/
            /*{ label: '发卡日期', name: 'chilCarddate', width: 80 },
            { label: '健康档案标识', name: 'chilHealthno', width: 80 },
            { label: '平台字段,未知属性', name: 'chilResidenceclient', width: 80 }, 	*/
            {label: '居住属性', name: 'chilResidence', width: 80, align: 'center'},
            {label: '户籍属性', name: 'chilAccount', width: 80, align: 'center'},
            /*{ label: '户籍县国标', name: 'chilHabiId', width: 80 }, 	*/
            /*{ label: '平台字段,未知属性', name: 'chilInoctype', width: 80 }, 		*/
            /*{ label: '过敏史', name: 'chilSensitivity', width: 80 },
            { label: '母亲HB', name: 'chilMotherhb', width: 80 },
            { label: '母亲HIV', name: 'chilMotherhiv', width: 80 }, 			*/

            {label: '现管单位', name: 'chilCurdepartment', width: 160, align: 'center'},
            { label: '个案变化日期', name: 'chilLeavedate', width: 100,align: 'center',
                formatter:function (value) {
                    if(value!=null){
                        return value.substring(0,10);
                    }
                    return "";
                }
            },
            /*{ label: '迁出分类', name: 'chilLeave', width: 80 },
           { label: '迁出其他备注', name: 'chilLeaveremark', width: 80 }, 	*/
            /*{ label: '现管理单位编码', name: 'chilCurdepartment', width: 80 },
            { label: '前管理单位编码', name: 'chilPredepartment', width: 80 }, 	*/
            /*{ label: '建档县国标', name: 'chilCreatecounty', width: 80 }, */
            /*{ label: '建档单位编码', name: 'chilCreatesite', width: 80 }, 	*/
            {label: '建档日期', name: 'chilCreatedate', width: 100, align: 'center',
                formatter:function (value) {
                    if(value!=null){
                        return value.substring(0,10);
                    }
                    return "";
                }
            },
            /*{ label: '建卡人', name: 'chilCreateman', width: 80 },*/
           /* {label: '建档人', name: 'createUserName', width: 80, align: 'center'},*/
            /*{ label: '受种者单位', name: 'chilUnitimmu', width: 80 },
            { label: '出生医院编码', name: 'chilBirthhospital', width: 80 }, 		*/
            /*{label: '出生医院', name: 'chilBirthhospitalname', width: 80, align: 'center'},*/
            /*{ label: '是否有卡介苗疤痕', name: 'chilBcgScar', width: 80 }, */
            /*{label: '母亲身份证号', name: 'chilMotherno', width: 180, align: 'center'},
            {label: '父亲身份证号', name: 'chilFatherno', width: 180, align: 'center'},*/
            /*{ label: '出省状态', name: 'chilProvince', width: 80 },
            { label: '更新时间', name: 'chilEditDate', width: 80 }, 			*/
            /*{label: '建卡时间', name: 'createCardTime', width: 150, align: 'center'},*/
            /*{ label: 'type,0本地,1平台', name: 'type', width: 80 },
            { label: '同步状态,0未同步;1同步', name: 'syncstatus', width: 80 },
                */
            /*{ label: '创建人id', name: 'createUserId', width: 80 },
            { label: '创建人名字', name: 'createUserName', width: 80 },
            { label: '创建时间', name: 'createTime', width: 80 }, 			*/
            {label: '备注', name: 'remark', width: 80, align: 'center'},
            {label: '儿童编码', name: 'chilCode', width: 170, key: true, align: 'center'}
        ],
        viewrecords: true,
        height: 600,
        rowNum: 15,
        rowList: [15, 30, 50],
        //loadonce:false,
        sortname: 'chil_birthday',
        sortable:true,
        sortorder:'desc',
        rownumbers: true,
        rownumWidth: 45,
        autowidth: true,
        shrinkToFit: false,
        autoScroll: true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            vm.reset();
            // dataproviceall();//省份加载
            //隐藏grid底部滚动条
            /* */
            /*$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });*/
        },
        onSelectRow: function (rowid, iRow, iCol, e) {
            var rowData = $("#jqGrid").jqGrid('getRowData', rowid);
            var chilName = rowData.chilName.substring(rowData.chilName.indexOf('>')+1,rowData.chilName.lastIndexOf('<'));
            console.log(rowData.chilBirthday);
            console.log(rowData["chilBirthday"]);
            var inoculateDateStart=window.localStorage.getItem("inoculateDateStart");
            var inoculateDateEnd=window.localStorage.getItem("inoculateDateEnd");
            $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + rowid+"&birthdays="+rowData.chilBirthday+"&inoculateDateStart="+inoculateDateStart+"&inoculateDateEnd="+inoculateDateEnd);
            $("#move_iframe").attr("src", "../child/tchildmove.html?chilCode=" + rowid + "&chilHere=" + rowData.chilHere + "&chilName=" + chilName);
            $("#abnormal_iframe").attr("src", "../child/tchildabnormal.html?chilCode=" + rowid + "&chilName=" + chilName);
            $("#taboo_iframe").attr("src", "../child/tchildtaboo.html?chilCode=" + rowid + "&chilName=" + chilName);
            $("#allergy_iframe").attr("src", "../child/tchildallergy.html?chilCode=" + rowid + "&chilName=" + chilName);
            $("#infection_iframe").attr("src", "../child/tchildinfection.html?chilCode=" + rowid + "&chilName=" + chilName);


        },
        ondblClickRow: function (rowid, iRow, iCol, e) {
            vm.inoculateInput();
        }
    });
    vm.loadInfoStatusData();
    vm.loadNationData();
    vm.move();
    vm.residence();
    vm.chilBirthday();//出生日期
    vm.chilCreatedate();//建档日期
    vm.loadCommiteeData();//行政村
    vm.loadSchoolData();//学校
    vm.loadHospitalData();//医院
    vm.loadPrintModel();
    loadchilLeaveremark();
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
        vm.q.chilBirthdayEnd = $("#chilBirthdayEnd").val();
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
        vm.q.chilBirthdayStart = $("#chilBirthdayStart").val();
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
        vm.q.inoculateDateEnd = $("#inoculateDateEnd").val();
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
        vm.q.inoculateDateStart = $("#inoculateDateStart").val();
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
        vm.q.chilCreatedateStart = $("#chilCreatedateStart").val();
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
        vm.q.chilCreatedateEnd = $("#chilCreatedateEnd").val();
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
        vm.q.chilLeavedateStart = $("#chilLeavedateStart").val();
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
        vm.q.chilLeavedateEnd = $("#chilLeavedateEnd").val();
    });

    //加载页面时。出生日期获取焦点
    $("#chilBirthdayStart").focus();

    // dataproviceall2();//省份加载
});


var vm = new Vue({
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
            currentDepartChild:false,
            chilSex:null
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
            //checkParam();
            // if (!flag) {
            //     alert("请输入查询条件");
            //     return;
            // }
            vm.showList = true;
            vm.reload();
        },
        reset: function () {
            vm.q = {
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
                chilMother:null,
                chilFather:null,
                currentDepartChild:false

            }
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

            vm.showList = false;
            vm.title = "新增";
            vm.tChildInfo = {
                chilCreatesite: orgName,
                createUserName: userName,
                chilCreatedate: vm.chilCreatedate()
            };
            vm.loadAddress();
        },
        update: function (event) {
            var chilCode = getSelectedRow_child();
            if (chilCode == null) {
                return;
            }
            //vm.showList = false;
            vm.title = "修改";
            $("#chilLeaveremark").attr({"disabled":"disabled"});
            //vm.getInfo(chilCode);
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '修改',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ["98%", "98%"],
                content: '../child/childEdit.html?chilCode=' + chilCode
            });

        },
        back: function () {
            vm.showList = true;
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
                Vue.set(vm.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(vm.tChildInfo, 'chilHabiId', chil_habi_id);
                Vue.set(vm.tChildInfo, 'chilBirthhospital', hospital_id);
                Vue.set(vm.tChildInfo, 'chilBirthhospitalname', hospital_name);
                Vue.set(vm.tChildInfo, 'chilCommittee', committee);

                var url = vm.tChildInfo.chilCode == null ? "../child/save" : "../child/update";
                $("#saveOrUpdate").attr("disabled",'disabled');
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tChildInfo),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        $("#saveOrUpdate").removeAttr("disabled");
                        if (r.code === 0) {
                            layer.msg('操作成功');
                            //vm.reload();
                            /*
                          * 先清空条件
                          * jqgrid postData setGridParam 调用多次时查询条件会累加
                          */
                            var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
                            $.each(postData, function (k, v) {
                                delete postData[k];
                            });
                            vm.showList = true;
                            $("#jqGrid").jqGrid('setGridParam', {
                                postData: {},
                                url: '../child/list?chilCode='+vm.tChildInfo.chilCode,
                                page: 1
                            }).trigger("reloadGrid");
                        } else {
                            layer.msg(r.msg);
                        }
                    }
                });
            } else {
                return false;
            }

        },
        del: function (event) {
            var chilCodes = getSelectedRows();
            if (chilCodes == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../child/delete",
                    data: JSON.stringify(chilCodes),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (chilCode) {
            $.ajax({
                type: "GET",
                url: "../child/info/" + chilCode,
                contentType: 'application/json;charset=UTF-8',
                success: function (r) {
                    /* reSetAddress(r);noneSelectedText*/
                    vm.tChildInfo = r.tChildInfo;
                    $('#chilCommittee').selectpicker('val',(vm.tChildInfo.chilCommittee));
                    $('#chilBirthhospitalname').selectpicker('val', (vm.tChildInfo.chilBirthhospital));

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
                }
            });


        },
        reload: function (event) {
            vm.showList = true;

            /*
           * 先清空条件
           * jqgrid postData setGridParam 调用多次时查询条件会累加
           */
            var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            Vue.set(vm.q, 'chilCommittee', $("#chilCommittees").val());

            $("#jqGrid").jqGrid('setGridParam', {
                postData: vm.q,
                url: '../child/list',
                page: 1
            }).trigger("reloadGrid");

            window.localStorage.setItem("chilCode",vm.q.chilCode);
            window.localStorage.setItem("chilName",vm.q.chilName);
            window.localStorage.setItem("chilCommittee",vm.q.chilCommittee);
            window.localStorage.setItem("chilBirthdayStart",vm.q.chilBirthdayStart);
            window.localStorage.setItem("chilBirthdayEnd",vm.q.chilBirthdayEnd);
            window.localStorage.setItem("chilHere",vm.q.chilHere);
            window.localStorage.setItem("inoculateDateStart",vm.q.inoculateDateStart);
            window.localStorage.setItem("inoculateDateEnd",vm.q.inoculateDateEnd);
            window.localStorage.setItem("chilCreatedateStart",vm.q.chilCreatedateStart);
            window.localStorage.setItem("chilCreatedateEnd",vm.q.chilCreatedateEnd);
            window.localStorage.setItem("chilLeavedateStart",vm.q.chilLeavedateStart);
            window.localStorage.setItem("chilLeavedateEnd",vm.q.chilLeavedateEnd);
            window.localStorage.setItem("chilMother",vm.q.chilMother);
            window.localStorage.setItem("isUpload",vm.q.isUpload);
            window.localStorage.setItem("currentDepartChild",vm.q.currentDepartChild);
            window.localStorage.setItem("chilSex",vm.q.chilSex);

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
                    vm.chilCommittee = results.page.list;
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
                    vm.chilBirthhospitalname = results.page.list;
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
                vm.tChildInfo.chilBirthday = $("#chilBirthday").val();
            }).on('hide', function (e) {
                $('#chilForm').data('bootstrapValidator')
                    .updateStatus('chilBirthday', 'NOT_VALIDATED', null)
                    .validateField('chilBirthday');
            });
            /* .on('hide',function(e) {
                     $('#chilForm').data('bootstrapValidator')
                         .updateStatus('chilBirthday', 'NOT_VALIDATED',null)
                         .validateField('chilBirthday');
                 });*/

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
        downloadChild: function () {
            layer.msg("正在下载...");
            $.ajax({
                type: "POST",
                url: "../provincePlatform/saveToLocal?childCode=" + vm.q.chilCode,
                dataType: "json",
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg("下载成功");
                        //$("#jqGrid").trigger("reloadGrid");
                    } else {
                        layer.msg("下载失败");
                    }
                }
            });
        },
        upload: function () {
            /*vm.downloadChild();*/
            var ids = getSelectedRow_child("jqGrid");
            if(!ids){
                return;
            }
            layer.confirm('确定要上传么？', function (index) {
                $.ajax({
                    type: "POST",
                    url: "../provincePlatform/uploadPlat?childCode=" + ids,//http://"$webPath/request/pagelist/SysChild/selectWithPageForList.jhtml?"+params,
                    dataType: "json",
                    success: function (result) {
                        if (result.msg == "1") {
                            layer.msg("上传成功");
                            return;
                        }
                        else if (result.msg == "0") {
                            layer.msg("上传失败");
                            return;
                        }
                        else if (result.msg == "2") {
                            layer.msg("没有权限上报数据");
                        } else if (result.msg == "3") {
                            layer.msg("单位编码或密码错误");
                        } else if (result.msg == "4") {
                            layer.msg("全部数据操作失败，xml或zip压缩数据格式不正确!");
                        } else {
                            layer.msg("出现未知错误");
                        }
                    },
                    error: function () {
                        /* layerFn.center(AppKey.code.code199, "没有查询到儿童相关信息!");*/
                    }
                });
                layer.close(index);
            });
        },
        uploadAllChildren:function(){
            layer.confirm('确定要上传所有未上传数据么？', function(index) {
                layer.msg('上传中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 1000000});
                $.ajax({
                    type: "POST",
                    url: "../provincePlatform/uploadPlatAll?allChild=allChild",//http://"$webPath/request/pagelist/SysChild/selectWithPageForList.jhtml?"+params,
                    dataType: "json",
                    success: function (result) {
                        if(result.msg=="暂无未同步数据"){
                            layer.msg("暂无未同步数据");
                            return;
                        }
                        if (result.msg == "1") {
                            layer.msg("上传成功");
                            return;
                        }
                        else if (result.msg == "0") {
                            layer.msg("上传失败");
                            return;
                        }
                        else if (result.msg == "2") {
                            layer.msg("没有权限上报数据");
                        } else if (result.msg == "3") {
                            layer.msg("单位编码或密码错误");
                        } else if (result.msg == "4") {
                            layer.msg("全部数据操作失败，xml或zip压缩数据格式不正确!");
                        } else {
                            layer.msg("出现未知错误");
                        }
                    },
                    error: function () {
                        /* layerFn.center(AppKey.code.code199, "没有查询到儿童相关信息!");*/
                    }
                });
                layer.close(index);
            });

        },
        print: function () {
            var chilCode = getSelectedRow_child();
            if (chilCode == undefined || chilCode == null) {
                alert("请选择儿童");
                return;
            }
            layer.open({
                title: '打印选择',
                skin: 'printDialog',
                type: 1,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['580px', '480px'],
                content: $("#printDialog")
            });
            vm.loadPrintModel();
            $("#lineCount").val("");
        },
        printCard: function () {
            var chilCode = getSelectedRow_child();
            if (chilCode == undefined || chilCode == null) {
                alert("请选择儿童");
                return;
            }
            layer.open({
                title: '打印选择',
                skin: 'printDialog',
                type: 1,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['580px', '480px'],
                content: $("#printCardDialog")
            });
            $("#cardLineCount").val("");
        },
        inoculateInput:function () {
            console.log(123123)
            var chilCode = getSelectedRow_child();
            var rowData = $("#jqGrid").jqGrid('getRowData', chilCode);
            console.log(rowData.chilBirthday);
            if (chilCode == undefined || chilCode == null) {
                alert("请选择儿童");
                return;
            }
            var index=layer.open({
                title: "接种录入",
                skin: 'printDialog',
                area: ["98%", "98%"],
                type: "1",
                content :'<iframe src="../child/inoculateInput.html?childCode='+chilCode+"&birthday="+rowData.chilBirthday+'" style="width: 100%;height: 100%;overflow:auto" ></iframe>' ,
                success : function(index, layero){
                    $(".layui-layer-page .layui-layer-content").css("overflow-y","hidden");

                },
                end:function () {
                    $("#chilBirthdayStart").focus();
                }
            })
        },
        showChild:function (rowDate) {
            var chilCode = rowDate.chilCode;
            var chilName = rowDate.chilName;
            var chilHere = rowDate.chilHere;
            var birthdays = rowDate.chilBirthday;
            //var rowData = $("#jqGrid").jqGrid('getRowData', chilCode);
            //console.log(rowData.chilBirthday);
            if (chilCode == undefined || chilCode == null) {
                alert("请选择儿童");
                return;
            }
            var index=layer.open({
                title: "儿童信息",
                skin: 'printDialog',
                area: ["98%", "98%"],
                type: "1",
                content :'<iframe src="../child/showChildDetail.html?childCode='+chilCode+'&chilName='+chilName+'&chilHere='+chilHere+'&birthdays='+birthdays+'" style="width: 100%;height: 100%;overflow:auto" ></iframe>' ,
                success : function(index, layero){
                    $(".layui-layer-page .layui-layer-content").css("overflow-y","hidden");

                },
                end:function () {
                    $("#chilBirthdayStart").focus();
                }
            })
        },
        /*省平台查询*/
        queryFromPlat: function () {
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '省平台下载',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ["98%", "98%"],
                content: '../provinceplatform/platformSearch.html'
            });
        },
        loadPrintModel: function () {
            $.ajax({
                url: '../tchildprintmodel/list?orgid=' + orgId + '&xml_name=selfdefineprint' + '&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var info = '';
                    var inoc = '';
                    for (var i = 0; i < results.page.list.length; i++) {
                        if (results.page.list[i].xmlName.indexOf('selfdefineprintInfo') != -1) {
                            info += '<option  value="' + results.page.list[i].xmlName + '">' + results.page.list[i].modelName + '</option>';
                        }
                        if (results.page.list[i].xmlName.indexOf('selfdefineprintInoculate') != -1) {
                            inoc += '<option  value="' + results.page.list[i].xmlName + '">' + results.page.list[i].modelName + '</option>';
                        }
                    }
                    $("#infoModelName").html(info);
                    $("#inocModelName").html(inoc);

                }
            });
        },
        /**
         * 导出
         */
        excel:function () {
            layer.confirm("确定要下载生成的报表文件吗？",function (index) {
                // var formdata = $("#childForm").serialize();序列化查询条件
                console.log(window.localStorage.getItem("chilCode"));

                var chilCode=window.localStorage.getItem("chilCode");
                if (chilCode=="undefined"){
                    chilCode=null;
                }else{
                    localStorage.getItem("chilCode");
                }
                var chilName=window.localStorage.getItem("chilName");
                var chilCommittee=window.localStorage.getItem("chilCommittee");
                var chilBirthdayStart=window.localStorage.getItem("chilBirthdayStart");
                var chilBirthdayEnd=window.localStorage.getItem("chilBirthdayEnd");
                var chilHere=window.localStorage.getItem("chilHere");
                var inoculateDateStart=window.localStorage.getItem("inoculateDateStart");
                var inoculateDateEnd=window.localStorage.getItem("inoculateDateEnd");
                var chilCreatedateStart=window.localStorage.getItem("chilCreatedateStart");
                var chilCreatedateEnd=window.localStorage.getItem("chilCreatedateEnd");
                var chilLeavedateStart=window.localStorage.getItem("chilLeavedateStart");
                var chilLeavedateEnd=window.localStorage.getItem("chilLeavedateEnd");
                var chilMother=window.localStorage.getItem("chilMother");
                var isUpload=window.localStorage.getItem("isUpload");
                var currentDepartChild=window.localStorage.getItem("currentDepartChild");
                var chilSex =window.localStorage.getItem("chilSex");
                if (chilSex == "undefined"){
                    chilSex = null;
                }
                // window.open("../ExcelController/child?" +formdata);
                window.open("../ExcelController/child?chilCode="+chilCode+"&chilName="+chilName+"&chilCommittee="+chilCommittee
                    +"&chilBirthdayStart="+chilBirthdayStart+"&chilBirthdayEnd="+chilBirthdayEnd+"&inoculateDateStart="+inoculateDateStart
                    +"&chilHere="+chilHere+"&inoculateDateEnd="+inoculateDateEnd+"&chilCreatedateStart="+chilCreatedateStart
                    +"&chilCreatedateEnd="+chilCreatedateEnd+"&chilLeavedateStart="+chilLeavedateStart+"&chilLeavedateEnd="+chilLeavedateEnd
                    +"&chilMother="+chilMother+"&isUpload="+isUpload+"&currentDepartChild="+currentDepartChild+"&chilSex="+chilSex
                );
                layer.close(index);
                localStorage.clear();
            });
        },
        /**
         * @desc 导出儿童基本信息及接种信息
         * @author Woods
         */
        exportChildAndInoculate:function(){
            layer.confirm("确定要下载生成的报表文件吗？",function (index) {
                var chilCode=window.localStorage.getItem("chilCode");
                if (chilCode=="undefined"){
                    chilCode=null;
                }else{
                    localStorage.getItem("chilCode");
                }
                var chilName=window.localStorage.getItem("chilName");
                var chilCommittee=window.localStorage.getItem("chilCommittee");
                var chilBirthdayStart=window.localStorage.getItem("chilBirthdayStart");
                var chilBirthdayEnd=window.localStorage.getItem("chilBirthdayEnd");
                var chilHere=window.localStorage.getItem("chilHere");
                var inoculateDateStart=window.localStorage.getItem("inoculateDateStart");
                var inoculateDateEnd=window.localStorage.getItem("inoculateDateEnd");
                var chilCreatedateStart=window.localStorage.getItem("chilCreatedateStart");
                var chilCreatedateEnd=window.localStorage.getItem("chilCreatedateEnd");
                var chilLeavedateStart=window.localStorage.getItem("chilLeavedateStart");
                var chilLeavedateEnd=window.localStorage.getItem("chilLeavedateEnd");
                var chilMother=window.localStorage.getItem("chilMother");
                var isUpload=window.localStorage.getItem("isUpload");
                var currentDepartChild=window.localStorage.getItem("currentDepartChild");
                var chilSex=window.localStorage.getItem("chilSex");
                if (chilSex == "undefined"){
                    chilSex = null;
                }
                // window.open("../ExcelController/child?" +formdata);

                window.open("../ExcelController/exportChildAndInoculate?chilCode="+chilCode+"&chilName="+chilName+"&chilCommittee="+chilCommittee
                    +"&chilBirthdayStart="+chilBirthdayStart+"&chilBirthdayEnd="+chilBirthdayEnd+"&inoculateDateStart="+inoculateDateStart
                    +"&chilHere="+chilHere+"&inoculateDateEnd="+inoculateDateEnd+"&chilCreatedateStart="+chilCreatedateStart
                    +"&chilCreatedateEnd="+chilCreatedateEnd+"&chilLeavedateStart="+chilLeavedateStart+"&chilLeavedateEnd="+chilLeavedateEnd
                    +"&chilMother="+chilMother+"&isUpload="+isUpload+"&currentDepartChild="+currentDepartChild+"&chilSex="+chilSex
                );
                layer.close(index);
                localStorage.clear();
            });
        },
        /**
         * @desc 批量打印儿童条形码
         * @author Tian
         * @date 2018/09/03
         */
        batchPrintCode: function () {

            var ele = $("#jqGrid");
            var ids = ele.getGridParam("selarrrow");
            if (ids.length == 0) {
                layer.msg("请选择一个儿童或多个儿童！")
                return;
            }
            var rows = new Array();
            for (var i = 0; i < ids.length; i++) {
                var row = ele.jqGrid("getRowData", ids[i]);
                rows.push(row);
            }

            var content;
            $.get("../configuration/queryConfiguration?type=barCodeIpAddress", function (data) {
                var ret=data.data;
                var ipAddress = ret.remark + ":7490";
                if (ipAddress != undefined || ipAddress != null || ipAddress != '') {
                    if (window.WebSocket != undefined) {
                        var connection = new WebSocket('ws://' + ipAddress);
                        connection.onopen = wsOpen;

                        //链接成功触发的回调函数,发送数据
                        function wsOpen(event) {
                            for (var i = 0; i < rows.length; i++) {
                                var ChildName = rows[i].chilName;
                                var childId = rows[i].chilCode;
                                if (ChildName == undefined || ChildName == null || ChildName == "") {
                                    content = ":" + childId;
                                } else {
                                    content = ChildName + ":" + childId;
                                }
                                connection.send(content);
                            }


                        }

                        //链接错误时触发
                        connection.onerror = wsError;

                        function wsError(event) {
                            layer.msg("没有启动打印服务或打印机所在IP设置错误！");
                        }

                    }
                } else {
                    layer.msg("设置打印机所在IP！");
                }

            })
        }

    }
});

//选择一条记录
function getSelectedRow_child() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return rowKey;
}

//选择一条记录
function getSelectedRow_ino() {

    var grid = $("#inoculation_iframe").contents().find("#inoculationGrid");
    //$("#ifm").contents().find("#inoculationGrid").click();//jquery 方法1
    var rowKey = grid.getGridParam("selrow");
    /*if(!rowKey){
        alert("请选择一条记录");
        return ;
    }*/

    return rowKey;
}

//打印接种记录
function printInoculation(printType, xmlName) {
    var lineCount = $("#lineCount").val();
    var cardLineCount = $("#cardLineCount").val();
    var fontSize = $("#fontSize").val();
    var cardFontSize = $("#cardFontSize").val();
    if(fontSize == "" || isNaN(fontSize)){
        fontSize = 8;
    }
    if(cardFontSize == "" || isNaN(cardFontSize)){
        cardFontSize = 8;
    }
    var child_id = getSelectedRow_child();
    if (child_id == undefined || child_id == null || child_id == "") {
        layer.msg("请选择儿童！");
        return;
    }
    var id = getSelectedRow_ino();
    if (id == undefined || id == null || id == "") {
        layer.msg("请选择接种记录！");
        return;
    }
    var xmlName = xmlName.toString();
    if (xmlName == '') {
        fontSize = cardFontSize;
        xmlName = "selfdefineprintCard";
    }
    var inoculate_id = $("#inoculation_iframe").contents().find("#inoculationGrid").getGridParam("selarrrow");
    if(lineCount != "" && !isNaN(lineCount)){
        if ($("#lineCountCheck").is(':checked')){
            lineCount=parseInt(lineCount)+21;
        }
        layer.closeAll();
        window.open("../reports/printSecondVaccBylineCount/?lineCount=" + lineCount+"&inoculate_id="+inoculate_id+ "&num=1" + '&printType=' + printType + "&child_id=" + child_id+"&plag=inoculate"+"&fontSize="+fontSize).print();
    }else if(cardLineCount!="" && !isNaN(cardLineCount)){
        layer.closeAll();
        window.open("../reports/printSecondVaccBylineCount/?lineCount=" + cardLineCount+"&inoculate_id="+inoculate_id+ "&num=1" + '&printType=' + printType + "&child_id=" + child_id+"&plag=inoculate"+"&fontSize="+fontSize).print();
    }
    else {
        layer.closeAll();
        window.open("../reports/printChildInoculateByModel/?inoculate_id=" + inoculate_id + "&xmlName=" + xmlName + "&num=1" + '&printType=' + printType + "&child_id=" + child_id+"&fontSize="+fontSize).print();
    }
    //var mywin = window.open("../reports/printChildInoculateByModel?num="+num+"&version="+jrxml_name+"&chilCode="+chilCode+"&printType="+printType).print();


    //window.open("../reports/printchildinfoByModel?jrxml_name="+jrxml_name + "&chilCode=" + chilCode+"&printType="+printType);
}

//接种证打印儿童信息
function printChildInfo(printType, jrxml_name) {

    var chilCode = getSelectedRow_child();
    if (chilCode == undefined || chilCode == null || chilCode == "") {
        layer.msg("请选择儿童！");
        return;
    }

    layer.closeAll();
    window.open("../reports/printchildinfoByModel?jrxml_name=" + jrxml_name + "&chilCode=" + chilCode + "&printType=" + printType).print();


}

//接种卡新卡打印
function printChildInfoCard(num) {
    var jrxml_name = "selfdefineprintCard";
    var cardFontSize = $("#cardFontSize").val();
    var chilCode = getSelectedRow_child();
    if (chilCode == undefined || chilCode == null || chilCode == "") {
        layer.msg("请选择儿童！");
        return;
    }
    if(cardFontSize == "" || isNaN(cardFontSize)){
        cardFontSize = 8;
    }
    layer.closeAll();

    window.open("../reports/printNewCard?num=" + num + "&chilCode=" + chilCode + "&jrxml_name=" + jrxml_name+"&fontSize="+cardFontSize).print();

}

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

    // 行政村/居委会拼音搜索(搜索条件)
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilCommittees").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#chilCommittees");
        var value = vm.chilCommittee;
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







//查询条件判断
function checkParam() {
    if (vm.q.chilCode != null) {
        window.localStorage.setItem("chilCode",vm.q.chilCode);
        return true;
    } else if (vm.q.chilName != null) {
        window.localStorage.setItem("chilName",vm.q.chilName);
        return true;
    } else if (vm.q.chilCommittee != null) {
        window.localStorage.setItem("chilCommittee",vm.q.chilCommittee);
        return true;
    } else if (vm.q.chilBirthdayStart != null) {
        window.localStorage.setItem("chilBirthdayStart",vm.q.chilBirthdayStart);
        return true;
    } else if (vm.q.chilBirthdayEnd != null) {
        window.localStorage.setItem("chilBirthdayEnd",vm.q.chilBirthdayEnd);
        return true
    } else if (vm.q.chilHere != null) {
        window.localStorage.setItem("chilHere",vm.q.chilHere);
        return true;
    }else if (vm.q.inoculateDateStart != null) {
        window.localStorage.setItem("inoculateDateStart",vm.q.inoculateDateStart);
        return true;
    } else if (vm.q.inoculateDateEnd != null) {
        window.localStorage.setItem("inoculateDateEnd",vm.q.inoculateDateEnd);
        return true;
    } else  if(vm.q.chilLeavedateStart != null){
        window.localStorage.setItem("chilCreatedateStart",vm.q.chilCreatedateStart);
        return true;
    }else 　if (vm.q.chilLeavedateEnd != null) {
        window.localStorage.setItem("chilCreatedateEnd",vm.q.chilCreatedateEnd);
        return true;
    }else if (vm.q.chilCreatedateStart != null){
        window.localStorage.setItem("chilLeavedateStart",vm.q.chilLeavedateStart);
        return true;
    }else if (vm.q.chilCreatedateEnd != null) {
        window.localStorage.setItem("chilLeavedateEnd",vm.q.chilLeavedateEnd);
        return true;
    } else if (vm.q.chilMother != null){
        window.localStorage.setItem("chilMother",vm.q.chilMother);
        return true;
    } else if(vm.q.isUpload != null){
        window.localStorage.setItem("isUpload",vm.q.isUpload);
        return true;
    }else if(vm.q.chilSex != null){
        window.localStorage.setItem("chilSex",vm.q.chilSex);
        return true;
    } else {
        return false;
    }
}

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
function showChildDetail(rowdata) {
    vm.showChild(rowdata);
    //$("#chilLeaveremark").removeAttr("disabled");
}
function over(obj){
    //obj.innerHTML = $("#condition").text();
    document.getElementById("condition").style.display="block";

    //obj.style.background = "pink";

}

function out(obj){
    $("#condition").hide();

   // obj.style.background = "#ccc";

}



