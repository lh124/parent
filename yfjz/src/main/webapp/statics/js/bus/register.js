var timestamp1;
var registerVM = new Vue({
    el: '#childrenBody',
    data: {
        message: {
            childCode: null,
            name: '',
            cardNo: '',
            here: '',
            account: '',
            birthday: '',
            age: '',
            sex: '',
            habiaddress: '',
            montherName: '',
            committee:'',
            chilResidence: '',
            fatherName: '',
            tel: '',
            mobile: '',
            address: '',
            remark: '',
            inoTime: '',
            allergyHistory: '',
            abnormalHistory: '',
            taboo: '',
            infectionHistory: '',
        },
        biocodemessage: {
            bioCnSortname: ''
        },
        tChildInoculate: {
            chilCode: '',
            inocBactCode: '',
            inocBatchno: '',
            inocCorpCode: '',
            inocInplId: '',
            inocDate: '',
            inocNurse: '',
            inocProperty: '',
            inocRegulatoryCode: ''
        },
        currentIndex: 0,
        q: {
            chilCode: null,
            chilName: null,
            chilCommittee: null,
            chilBirthdayStart: null,
            chilBirthdayEnd: null,
            chilHere: null
        },
        queryString:null,
        isStartQueue:1,//是否启用排队叫号，默认启用
        showList: true,
        title: null,
        childCode: null,
        tChildInfo: {},
        recommend: [],
        //被呼叫的儿童队列号码
        sequenceNoId: null,//被呼叫的队列号的id
        inoculateSiteArr: [], //接种部位数组
        inoculationArr: [],//接种属性
        factoryArr: [],//生产企业
        inocDepaArr: [],//接种单位
        inocBioCodeArr:[],//接种疫苗
        chilBirthhospitalname: null,
        chilCommittee: null,
        nowDateTimes:null
    },
    created: function () {
        //根据儿童编码查询儿童的信息展示
        var widthNum = $(window).width() - 225 + 'px';
        $(".re-wid").css({
            width: widthNum
        });
        var wid = $(window).width() - 50;
        $(".register-box-table-wrap").css({
            width: ((wid - 10) / 2) + 'px'
        });
    },
    methods: {
        query: function () {
            // Bus.$on('pushCurrentNo',function (a) {  //使用on监听事件 a-msg，这样当a组件中使用 emit主动触发了 Event实例的a-msg事件之后，这里就可以接收到
            //     alert('触发了接收'+a);
            // });
            if(registerVM.queryString!=null){
                registerVM.queryString = registerVM.queryString.replace(/\//g,"");
                registerVM.queryString = registerVM.queryString.replace(/-/g,"");
                if(!isNaN(registerVM.queryString) && (registerVM.queryString.length==18 || registerVM.queryString.length==12 )){
                    registerVM.q.chilCode=registerVM.queryString;
                }else if(!isNaN(registerVM.queryString) && registerVM.queryString.length==8){
                    var year = registerVM.queryString.substr(0, 4);
                    var month = registerVM.queryString.substr(4, 2);
                    var day = registerVM.queryString.substr(6, 2);
                    if (day != new Date(year + '/' + month + '/' +day).getDate()) {//校验日期是否合法
                        alert('请输入正确的日期');
                        return;
                    }
                    registerVM.q.chilBirthdayStart=registerVM.queryString;
                }else if(registerVM.queryString!=null){
                    registerVM.q.chilName=registerVM.queryString;
                }
            }
            var flag = checkParam();
            if (!flag) {
                alert("请输入查询条件");
                return;
            }
            registerVM.showList = true;
            registerVM.reload();
        },
        resetCondition: function () {
            registerVM.q = {
                chilCode: null,
                chilName: null,
                chilCommittee: null,
                chilBirthdayStart: null,
                chilBirthdayEnd: null,
                chilHere: null
            };
            registerVM.queryString=null;
            $("#queryString")[0].focus();

        },
        downloadBack:function () {
            var ispass=false;
             layer.confirm('请问您需要下载备份数据吗?',function(index){
                 layer.closeAll();
                if (index){
                    $.ajax({
                        url:"../file/judgeFile",
                        type:"get",
                        success:function (data) {

                            if(data.code==1){
                                if(!ispass){
                                    ispass=true;
                                    window.open("../file/downloadNew?type=download");
                                }
                            }else{
                                layer.msg(data.msg);
                            }
                        }
                    })

                }

            });

        },
        queryFromPlat: function () {
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '省平台下载',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ["98%", "98%"],
                content: '../provinceplatform/platformSearch.html?chilCode=' + registerVM.q.chilCode
            });
        },
        back: function () {
            registerVM.showList = true;
            if ($("#chilForm").data('bootstrapValidator') != null) {
                $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
            }
        },
        reload: function (event) {
            registerVM.showList = true;
            //清空查询条件
            var postData = $('#selectResultTable').jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });

            var page = $("#selectResultTable").jqGrid('getGridParam', 'page');
            $("#selectResultTable").jqGrid('setGridParam', {
                postData: registerVM.q,
                page: page,
                url: '../child/list'
            }).trigger("reloadGrid");
            if ($("#chilForm").data('bootstrapValidator') != null) {
                $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
            }
            $("#queryString")[0].focus();
        },
        loadCallChild: function () {
            if (!registerVM.childCode) {
                return;
            }
            //显示查询结果列表
            //清空查询条件
            var postData = $('#selectResultTable').jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });
            //查询
            var page = $("#selectResultTable").jqGrid('getGridParam', 'page');
            $("#selectResultTable").jqGrid('setGridParam', {
                postData: {chilCode: registerVM.childCode},
                page: page,
                url: '../child/list'
            }).trigger("reloadGrid");
            //显示历史接种记录
            // getHistoryInoculate(registerVM.childCode);
        },
        add: function () {
            debugger
            registerVM.showList = false;
            registerVM.title = "新增";
            $("#chilLeaveremark").attr({"disabled":"disabled"});
            registerVM.tChildInfo = {
                chilCreatesite: orgName,
                createUserName: userName,
                chilCreatedate: chilCreatedate()
            };
            registerVM.loadAddress();
        },
        update: function (event) {

            var chilCode = getSelectedRow_child();
            if (chilCode == null) {
                return;
            }
            //registerVM.showList = false;
            //registerVM.title = "修改";
            //$("#chilLeaveremark").attr({"disabled":"disabled"});
            //registerVM.getInfo(chilCode)
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

        saveOrUpdate: function (event) {
            childvalidator();
            $('#chilForm').bootstrapValidator('validate');//提交验证
            var hospital = $("#chilBirthhospitalname").val();
            var chilCommittee = $("#chilCommittee option:selected").val();
            var chilLeaveremark = $("#chilLeaveremark").val();
            /*if(registerVM.tChildInfo.chilCode != null){
                if(chilLeaveremark==null){
                    alert("请选择在册变更原因...");
                    return;
                }
            }*/

            if ($("#chilForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
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
                Vue.set(registerVM.tChildInfo, 'chilHabiaddress', chilHabiaddress);

                var adress=$("#nowaddress").val().replace(/\//g,' ');
                var chilAddress;
                if(adress!=undefined&&adress!=null&&adress!=""){
                    chilAddress=adress+"-"+$("#nowDetailed").val();
                }else{
                    chilAddress=$("#nowDetailed").val();
                }
                Vue.set(registerVM.tChildInfo, 'chilAddress',chilAddress);
                // Vue.set(registerVM.tChildInfo, 'chilHabiaddress', habiaddress);
                // Vue.set(registerVM.tChildInfo, 'chilAddress', address);

                Vue.set(registerVM.tChildInfo, 'chilBirthday', birthday);
                Vue.set(registerVM.tChildInfo, 'chilCreatedate', chilCreatedate);
                Vue.set(registerVM.tChildInfo, 'chilHabiaddressTownId', chilHabiaddress_town_id);
                Vue.set(registerVM.tChildInfo, 'chilAddressTownId', chilAddress_town_id);
                Vue.set(registerVM.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(registerVM.tChildInfo, 'chilHabiId', chil_habi_id);
                Vue.set(registerVM.tChildInfo, 'chilBirthhospital', hospital_id);
                Vue.set(registerVM.tChildInfo, 'chilBirthhospitalname', hospital_name);
                Vue.set(registerVM.tChildInfo, 'chilCommittee', chilCommittee);


                var url = registerVM.tChildInfo.chilCode == null ? "../child/save" : "../child/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(registerVM.tChildInfo),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            /*alert('操作成功', function (index) {*/
                            layer.msg("操作成功");
                            registerVM.showList = true;
                            var page = $("#selectResultTable").jqGrid('getGridParam', 'page');
                            $("#selectResultTable").jqGrid('setGridParam', {
                                postData: {
                                    'chilCode': null,
                                    'chilName': registerVM.tChildInfo.chilName,
                                    'chilBirthdayStart': null,
                                    'chilBirthdayEnd': null
                                },
                                page: page,
                                url: '../child/list'
                            }).trigger("reloadGrid");

                            //registerVM.reload();
                            /* });*/
                        } else {
                            alert(r.msg);
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
                    /* reSetAddress(r);*/
                    registerVM.tChildInfo = r.tChildInfo;
                    $('#chilBirthhospitalname').selectpicker('val', (registerVM.tChildInfo.chilBirthhospital));
                    $('#chilCommittee').selectpicker('val', (registerVM.tChildInfo.chilCommittee));
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
        /* loadPrintModel: function () {
             $.ajax({
                 url: '../tchildprintmodel/list?orgid=' + orgId + '&xml_name=selfdefineprintInfo' + '&page=1&limit=1000',
                 dataType: "json",
                 type: 'POST',
                 success: function (results) {
                     var con = '';
                     for (var i = 0; i < results.page.list.length; i++) {
                         con += '<option  value="' + results.page.list[i].xmlName + '">' + results.page.list[i].modelName + '</option>';
                     }
                     $("#modelName").html(con);

                 }
             });
         },*/
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
        saveCancelInfo: function () {
            //判断当前查询的儿童编码是否有选中
            if (registerVM.childCode == undefined || registerVM.childCode == "" || registerVM.childCode == null) {
                return;
            }
            //registerVM.sequenceNoId
            //保存取消原因
            var param = {};
            param.childCode = registerVM.childCode;
            param.childName = registerVM.message.name;

            param.sequenceNoId = registerVM.sequenceNoId;

            $.ajax({
                type: 'get',
                url: '../tbusregister/saveCancelInfo',
                dataType: "json",
                data: {
                    "childCode": registerVM.childCode,
                    "childName": registerVM.message.name,
                    "sequenceNoId": registerVM.sequenceNoId
                },
                contentType: 'application/json;charset=UTF-8',
                success: function (results) {

                    log(results);
                }
            });
        },
        // 登记
        register: function () {
            //是否启用排队叫号
            try {
                //获取分队列启用状态
                $.ajaxSettings.async = false;
                $.get("../inocpointmgr/getQueuesSatus", function(result){
                    if(result.code ==0){
                        registerVM.isStartQueue = result.typeValue;
                    }
                });
                $.ajaxSettings.async = true;
            } catch(err){}
            if (registerVM.isStartQueue ==1 && !registerVM.sequenceNoId) {
                alert("请先呼叫号码");
                return;
            }
            registerVM.recommend = [];//清空之前的脏数据
            var rowKey = $("#selectResultTable").getGridParam("selrow");
            if (rowKey == undefined || rowKey == null) {
                alert("请选择一条儿童记录进行登记");
                return;
            }
            //计算出推荐的信息
            /*$.ajax({
                url: "../recommend/recommend",
                data: {"childCode": registerVM.childCode},
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',//重点关注此参数
                success: function (data) {
                    //循环比对，将推荐的疫苗放入推荐数组中
                    var rowDatas = getJQAllData("receiveTable");
                    for (var j = 0; j < data.length; j++) {
                        for (var i = 0; i < rowDatas.length; i++) {//vaccineCode  batchnum
                            if (rowDatas[i].vaccineCode == data[j].code && rowDatas[i].batchnum == data[j].batchnum) {
                                registerVM.addRecommend(rowDatas[i]);//触发可选列表的  添加按钮 点击事件
                            }
                        }
                    }
                }
            });*/
            if(registerVM.message.here=='迁出'){
                registerVM.changeInfoStatus();
            }else{
                registerVM.openRegister();
            }
            /*  */

        },
        //弹出登记框
        openRegister:function () {
            var wid = $(window).width() - 50;
            var widthNum = wid + 'px';
            layer.open({
                title: '登记疫苗',
                skin: 'tmpl',
                type: 1,
                // closeBtn: 0,//取消右上角的x关闭按钮
                area: [widthNum, '98%'],
                content: $("#tmpl"),
                success: function () {
                    //表格只能在弹框之后加载，否则无效
                    loadTable();
                    // registerVM.xsquanbu();

                },
                close:function () {
                    registerVM.nowDateTimes=null;
                }
            });
        },
        // //显示全部推荐疫苗
        // xsquanbu: function () {
        //     $("#receiveTable").jqGrid('setGridParam', {
        //         datatype: 'json',
        //         postData: {'show': 1, "childCode": registerVM.childCode},
        //         page: 1
        //     }).trigger("reloadGrid");
        //
        // },
        changeInfoStatus:function () {
            var index = layer.confirm("<div>儿童的在册情况为迁出，请先变更在册情况，再进行接种,在册情况：<br><div class='form-group'><select class='form-control' id='infoStatus' @change='selectReset' ></select></div></div>", {
                    btn: ['确定', '取消'], cancel: function (index, layero) {
                        //取消操作，点击右上角的X
                        registerVM.openRegister();
                    }
                },
                //确定
                function () {
                    debugger
                    var chil_code = registerVM.message.childCode;
                    var chhePrehere = registerVM.message.here;//原在册情况
                    var chheHere = $("#infoStatus option:selected").val();//现在册情况
                    if(chheHere=='2'){
                        // layer.msg("请选择其他在册情况，或取消变更!");
                    }else{
                        // var chheChilProvinceremark = $("#updataReason1").val();
                        /* if (chheChilProvinceremark==""||chheChilProvinceremark==null){
                             layer.msg("变更原因不能为空");
                             return false;
                         }*/

                        Vue.set(movehere.tChildMove, 'chilCode', chil_code);
                        Vue.set(movehere.tChildMove, 'chhePrehere', chhePrehere);
                        Vue.set(movehere.tChildMove, 'chheHere', chheHere);
                        // Vue.set(movehere.tChildMove, 'chheChilProvinceremark', chheChilProvinceremark);
                        $.ajax({
                            type: "POST",
                            url: "../tchildmove/save",
                            data: JSON.stringify(movehere.tChildMove),
                            contentType: 'application/json;charset=UTF-8',
                            success: function (r) {
                                if (r.code === 0) {
                                    layer.msg("变更成功！")
                                    Vue.set(selecttypeHere.tChildInfo, 'chilHere', chheHere);
                                    Vue.set(selecttypeHere.tChildInfo, 'chilCode', chil_code);
                                    urltype(selecttypeHere.tChildInfo, chil_code);
                                } else {
                                    alert(r.msg);
                                }
                            }
                        });
                    }
                    layer.close(index);
                    registerVM.openRegister();

                },
                //取消
                function () {
                    registerVM.openRegister();
                })
            // $('#infoStatus').selectpicker({
            //     // 'selectedText': 'cat',
            //     noneSelectedText: '',
            //     noneResultsText: '没有匹配的结果',
            //     actionsBox: true
            // });
            $.ajax({
                // get请求地址
                url: '../sys/dict/typeList?type=child_info_status',
                dataType: "json",
                type: 'get',
                success: function (data) {
                    var result = data.page;
                    $("#infoStatus").empty();
                    $.each(result, function (i, n) {

                        $("#infoStatus").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
                    })
                    // $("#infoStatus").selectpicker('refresh');
                    // $("#infoStatus option:selected").val("2");
                    $("#infoStatus").val("2");
                }
            });
            var changeHeight = false;
            $("#infoStatus").on("click", function () {
                if (!changeHeight) {
                    $(".layui-layer-dialog").css('height', '500px');
                    $(".layui-layer-dialog").find('.layui-layer-content').css('height', '400px');
                }
                changeHeight = false;
            });
            $("#infoStatus").on("change", function () {
                changeHeight = true;
                $(".layui-layer-dialog").css('height', '224px');
                $(".layui-layer-dialog").find('.layui-layer-content').css('height', '122px');
            })
        },
        // 打印
        print: function () {
            var rowKey = $("#selectResultTable").getGridParam("selrow");
            if (rowKey == undefined || rowKey == null) {
                alert("请选择儿童");
                return;
            }
            layer.open({
                title: '打印选择',
                skin: 'printDialog',
                type: 1,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['700px', '580px'],
                content: $("#printDialog"),
                success: function () {
                    //表格只能在弹框之后加载，否则无效
                    registerVM.loadPrintModel();
                    $("#lineCount").val("");
                    $("#currentRegisterVacc").jqGrid('clearGridData');
                    $("#currentRegisterVacc").jqGrid('setGridParam', {
                        postData: {},
                        url: '../tchildinoculate/getCurrentRegisterVacc?chilCode='+registerVM.childCode,
                        page: 1,
                    }).trigger("reloadGrid");
                }
            });


        },

        renderSelect: function () {

        },
        //点击可选疫苗的添加按钮，添加到推荐疫苗列表
        addClick: function (rowdata) {
            var rowDatas = registerVM.recommend;
            if (rowDatas.length >= 3) {
                layer.alert("一天最多只能登记最多两剂次一口服");
                return;
            }
            //判断选中的疫苗是否可以登记
            var flag = registerVM.judg(rowdata);
            if (!flag) {
                layer.alert("已选同类疫苗，不能重复选择");
                return;
            }
            //两剂一口服
            var flag3 = registerVM.twoDosesOfOrally(rowdata, rowDatas);
            if (!flag3) {
                return;
            }
            //判断是否能登记
            $.ajax({
                type: "get",
                url: "../recommend/judge",
                dataType: 'json',
                data: {"childCode": registerVM.childCode, "bioCode": rowdata.vaccineCode},
                success: function (r) {
                    if (r.code == 0) {
                        var flag4 = registerVM.judg(rowdata, rowDatas);
                        if (!flag4) {
                            layer.alert("已选同类疫苗，不能重复选择");
                            return;
                        }
                        registerVM.addRecommend(rowdata);
                    } else {
                        layer.confirm(r.msg + '，是否继续接种?', {btn: ['确定', '取消']},
                            function (index) {//确定事件
                                //添加数据到数据库
                                var flag5 = registerVM.judg(rowdata, rowDatas);
                                if (!flag5) {
                                    layer.alert("已选同类疫苗，不能重复选择");
                                    return;
                                }
                                registerVM.addRecommend(rowdata);
                                layer.close(index);
                            }, function () {//取消事件
                                log("您选择了取消添加疫苗到推荐疫苗列表");
                            }
                        );

                    }
                },
                error: function (data) {
                    log(data);
                }
            });
        },

        judg: function (rowdata) {
            var recommends = registerVM.recommend;
            var selectVaccCode = rowdata.vaccineCode.substr(0, 2);//选中的疫苗前两位所属大类编码
            for (var i = 0; i < recommends.length; i++) {
                if (recommends[i].bioCode.substr(0, 2) == selectVaccCode) {
                    return false;
                }
            }
            return true;
        },
        //两剂一口服
        twoDosesOfOrally: function (rowdata, rowDatas) {
            //其它 5   口服 4    皮内 3   皮下 2   肌内	1
            //根据接种部位判断疫苗是不是口服
            var inoculateSite = rowdata.inoculateSite;
            var kf = 0;//口服数量
            var dose = 0;//剂次数量
            for (var i = 0; i < rowDatas.length; i++) {
                if (rowDatas[i].inoculateSite == '口服') {//口服
                    kf++;
                } else {
                    dose++;
                }
            }
            if (inoculateSite == '口服' && kf >= 1) {
                layer.alert("已登记了口服类疫苗，不能继续添加，请检查");
                return false;
            }
            if (inoculateSite != '口服' && dose >= 2) {
                layer.alert("已经登记了两个剂次类疫苗，不能继续添加，请检查");
                return false;
            }
            return true;
        },
        //添加选中的数据或者推荐的数据到登记表
        addRecommend: function (rowdata) {
            rowdata.childCode = registerVM.childCode;
            //预定义需要保存的数据
            var dataRow = {
                "expiryDate": rowdata.expiryDate, //失效期
                "bioName": rowdata.productName,   //疫苗名称
                "childCode": registerVM.childCode, //儿童编码
                "batchnum": rowdata.batchnum,    //疫苗批号
                "factoryName": rowdata.factoryName,  //生产厂家名称
                "factoryCode": rowdata.factoryId,    //生产厂家编码
                "className": rowdata.className,   //疫苗分类名称
                "bioCode": rowdata.vaccineCode,       //疫苗编码
                "sequenceNoId": registerVM.sequenceNoId,  //队列号id
                "bioSpecId": rowdata.bioSpecId,         //疫苗接种途径
                "inoculateSite": rowdata.inoculateSite,//存显示的中文接种部位
                "inoculateSiteCode": rowdata.inoculateSiteCode,//存的是字典表的value值
                "childName": registerVM.message.name

            };
            if (parseInt(rowdata.price) > 0) {
                dataRow.ismf = 0;
            } else {
                dataRow.ismf = 1;
            }
            var param = {
                childCode: registerVM.childCode,
                vaccineCode: rowdata.vaccineCode
            };
            //计算剂次
            $.ajax({
                url: "../tbusregister/countByChildCodeAndVaccCode",
                data: param,
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',//重点关注此参数
                success: function (data) {
                    if (data.code == 0) {
                        dataRow.doseNo = data.total;//该儿童该疫苗所属大类下的下一剂次数
                        // var ids = $("#recommendTable").jqGrid('getDataIDs');
                        //var rowid = Math.max.apply(Math, ids) + 1;
                        $("#recommendTable").jqGrid("addRowData", rowdata.vaccineCode, dataRow, "last");
                        //将data数据添加到数组中  dataRow后期动态获取，这个registerVM.recommend是最终需要提交保存到登记表的数据对象
                        registerVM.recommend.push(dataRow);
                    } else {
                        layer.alert(data.msg)
                    }
                }
            });
        },
        //点击删除选择的列表  弹框中的推荐疫苗列表
        removeAddRegister: function (rowId, rowdata) {
            //移除jqgrid指定行的数据
            $("#recommendTable").delRowData(rowdata.bioCode);
            registerVM.removeObj(registerVM.recommend, rowdata);
        },
        // 提交登记信息
        submitRegisterMessage: function () {
            var date=new Date().getTime();
            log(date);
            log(registerVM.nowDateTimes);
            log(date-registerVM.nowDateTimes);
            if(registerVM.nowDateTimes!=null&&date-registerVM.nowDateTimes<1000){
                return;
            }
            if (registerVM.recommend.length <= 0) {
                layer.alert("请选择登记的疫苗后再提交!");
                return;
            }
            // $("#registerBtn").attr("disabled","disabled");
            var grid = $("#recommendTable");
            endEdit(grid);
            var ids = $("#recommendTable").jqGrid('getDataIDs');
            for(var i=0;i<registerVM.recommend.length;i++){
                var rowData = $("#recommendTable").jqGrid('getRowData',ids[i]);
                if(isNaN(rowData.inoculateSite)){
                    registerVM.recommend[i].inoculateSite = rowData.inoculateSite.substring(rowData.inoculateSite.indexOf(">")+1,rowData.inoculateSite.lastIndexOf("<"));
                    var site = registerVM.recommend[i].inoculateSite;
                    if (site == '左上臂') {
                        registerVM.recommend[i].inoculateSiteCode = 1;
                    } else if (site == '右上臂') {
                        registerVM.recommend[i].inoculateSiteCode = 2;
                    } else if (site == '左大腿') {
                        registerVM.recommend[i].inoculateSiteCode = 4;
                    } else if (site == '右大腿') {
                        registerVM.recommend[i].inoculateSiteCode = 5;
                    } else if (site == '左臀部') {
                        registerVM.recommend[i].inoculateSiteCode = 7;
                    } else if (site == '右臀部') {
                        registerVM.recommend[i].inoculateSiteCode = 8;
                    } else if (site == '口服') {
                        registerVM.recommend[i].inoculateSiteCode = 9;
                    } else {
                        registerVM.recommend[i].inoculateSiteCode = 10;
                    }

                }else {
                    registerVM.recommend[i].inoculateSiteCode = rowData.inoculateSite;
                    if (rowData.inoculateSite == 1) {
                        registerVM.recommend[i].inoculateSite = '左上臂';
                    } else if (rowData.inoculateSite == 2) {
                        registerVM.recommend[i].inoculateSite = '右上臂';
                    } else if (rowData.inoculateSite == 4) {
                        registerVM.recommend[i].inoculateSite = '左大腿';
                    } else if (rowData.inoculateSite == 5) {
                        registerVM.recommend[i].inoculateSite = '右大腿';
                    } else if (rowData.inoculateSite == 7) {
                        registerVM.recommend[i].inoculateSite = '左臀部';
                    } else if (rowData.inoculateSite == 8) {
                        registerVM.recommend[i].inoculateSite = '右臀部';
                    } else if (rowData.inoculateSite == 9) {
                        registerVM.recommend[i].inoculateSite = '口服';
                    }
                    // else {
                    //     registerVM.recommend[i].inoculateSite = '不详';
                    // }
                }
            }

            $.ajax({
                type: "post",
                url: "../tbusregister/addRecommendList",
                dataType: 'json',
                data: JSON.stringify(registerVM.recommend),
                contentType: 'application/json;charset=UTF-8',//重点关注此参数
                success: function (r) {

                    if (r.code == 0) {
                        $("#historyRecord").trigger("reloadGrid");
                        $("#recommendTable").trigger("reloadGrid");
                        //刷新今日带登记和今日已登记列表
                        $("#registerTable").trigger("reloadGrid");
                        $("#waitTable").trigger("reloadGrid");
                        layer.closeAll();
                    } else {
                        alert(r.msg);
                    }
                    // $("#registerBtn").removeAttr("disabled");
                },
                error: function (data) {
                    log(data);
                    // $("#registerBtn").removeAttr("disabled");
                }
            });
            registerVM.sequenceNoId = null;
            registerVM.nowDateTimes=date;
            $('#queryString')[0].focus();
        },
        // 取消登记
        cancelRegister: function () {
            $('#historyRecord').jqGrid('clearGridData');
            layer.closeAll();
        },
        barCodeSave: function () {
            var childBarCode = $("#barCodeInput").val();
            if (childBarCode !== null && childBarCode !== undefined && childBarCode !== ''){//输入内容才响应
                var bindChildCode = registerVM.childCode;
                if (bindChildCode !== null && bindChildCode !== undefined && bindChildCode !== '') {
                    $.ajax({
                        url: "../child/barCodeSave",
                        data:{childCode:bindChildCode,barCode:childBarCode},
                        success: function (data) {
                            layer.msg("绑定成功！");
                        }
                    });
                    $("#barCodeInput").val('');
                }
            }
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
        refresh: function () {

        },
        pushTop: function () {

        },
        pushBottom: function () {

        },
        dateFormatter: function (value) {
            var date = new Date(value);
            var year = date.getFullYear().toString();
            if (isNaN(year)) return;
            var month = (date.getMonth() + 1);
            var day = date.getDate().toString();
            if (month < 10) {
                month = "0" + month;
            }
            if (day < 10) {
                day = "0" + day;
            }
            return year + "-" + month + "-" + day;
        },
        /**
         * 获取推荐数组中元素的下标
         * @param _arr 数组
         * @param _obj 需要查找的对象
         * @returns {number}
         */
        removeObj: function (_arr, _obj) {
            for (var i = 0; i < _arr.length; i++) {
                /*if (_arr[i].id == _obj.id) {//判断对象中的属性的id相同的对象在数组中的下标值
                    registerVM.recommend.splice(i, 1);
                }*/
                if (_arr[i].bioCode == _obj.bioCode) {//判断对象中的属性的id相同的对象在数组中的下标值
                    registerVM.recommend.splice(i, 1);
                }

            }
        },
        /**
         * 打印条形码
         * @author Tingle
         * @date 2018/09/03
         */
        printBarCode: function () {
            var ele = $("#selectResultTable");
            var id = ele.getGridParam("selrow");
            if (id == null || id == undefined) {
                layer.msg("请选择一个儿童！")
                return;
            }
            var row = ele.jqGrid("getRowData", id);
            var ChildName = row.chilName;
            var childId = row.chilCode;
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
                            if (ChildName == undefined || ChildName == null || ChildName == "") {
                                content = ":" + childId;
                            } else {
                                content = ChildName + ":" + childId;
                            }
                            connection.send(content);
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

        },
        //全程接种计划
        allInoPlan: function () {
            var ele = $("#selectResultTable");
            var id = ele.getGridParam("selrow");
            if (id == null || id == undefined) {
                layer.msg("请选择一个儿童！");
                return;
            }
            var row = ele.jqGrid("getRowData", id);
            var childName = row.chilName;
            var childId = row.chilCode;
            var fatherName = row.chilFather;
            var matherName = row.chilMother;
            var birthtime = row.chilBirthday;
            var sex = row.chilSex;
            var address = row.chilHabiaddress;
            var httpurl = "../schedule/allInoPlan.html?childId=" + childId + "&childName=" + childName +
                "&fatherName=" + fatherName +
                "&matherName=" + matherName +
                "&birthtime=" + birthtime +
                "&sex=" + sex +
                "&address=" + address;

            layer.open({
                title: '全程接种计划',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['700px', '99%'],
                content: httpurl
            });
        },
        //入园证明
        schoolAdmission: function () {
            var ele = $("#selectResultTable");
            var id = ele.getGridParam("selrow");
            if (id == null || id == undefined) {
                layer.msg("请选择一个儿童！")
                return;
            }
            var row = ele.jqGrid("getRowData", id);
            var childName = row.chilName;
            var childId = row.chilCode;
            var fatherName = row.chilFather;
            var matherName = row.chilMother;
            var birthtime = row.chilBirthday;
            var chilTel = row.chilTel;
            var chilMobile = row.chilMobile;
            var sex = row.chilSex;
            var address = row.chilHabiaddress;
            var httpurl = "../child/schoolAdmission.html?childId=" + childId + "&childName=" + childName +
                "&fatherName=" + fatherName +
                "&matherName=" + matherName +
                "&birthtime=" + birthtime +
                "&sex=" + sex +
                "&address=" + address +
                "&chilTel=" + chilTel +
                "&chilMobile=" + chilMobile;

            layer.open({
                title: '入园证明',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['800px', '600px'],
                content: httpurl
            });
        },
        //处方单
        prescription: function () {
            var ele = $("#selectResultTable");
            var id = ele.getGridParam("selrow");
            if (id == null || id == undefined) {
                layer.msg("请选择一个儿童！")
                return;
            }
            var row = ele.jqGrid("getRowData", id);
            var childName = row.chilName;
            var childId = row.chilCode;
            var fatherName = row.chilFather;
            var matherName = row.chilMother;
            var birthtime = row.chilBirthday;
            var chilTel = row.chilTel;
            var chilMobile = row.chilMobile;
            var sex = row.chilSex;
            var address = row.chilHabiaddress;
            var httpurl = "../child/prescription.html?childId=" + childId + "&childName=" + childName +
                "&fatherName=" + fatherName +
                "&matherName=" + matherName +
                "&birthtime=" + birthtime +
                "&sex=" + sex +
                "&address=" + address +
                "&chilTel=" + chilTel +
                "&chilMobile=" + chilMobile;

            layer.open({
                title: '处方单',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['800px', '600px'],
                content: httpurl
            });
        },
        //预检告知书
        preCheckNotice: function () {
            var ele = $("#selectResultTable");
            var id = ele.getGridParam("selrow");
            if (id == null || id == undefined) {
                layer.msg("请选择一个儿童！")
                return;
            }
            var row = ele.jqGrid("getRowData", id);
            var childName = row.chilName;
            var childId = row.chilCode;
            var fatherName = row.chilFather;
            var matherName = row.chilMother;
            var birthtime = row.chilBirthday;
            var chilTel = row.chilTel;
            var chilMobile = row.chilMobile;
            var sex = row.chilSex;
            var address = row.chilHabiaddress;
            var httpurl = "../child/preCheckNotice.html?childId=" + childId + "&childName=" + childName +
                "&fatherName=" + fatherName +
                "&matherName=" + matherName +
                "&birthtime=" + birthtime +
                "&sex=" + sex +
                "&address=" + address +
                "&chilTel=" + chilTel +
                "&chilMobile=" + chilMobile;

            layer.open({
                title: '预检告知书',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['1000px', '700px'],
                content: httpurl
            });
        },
        showHistoryInoculations:function(){
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '历史接种记录',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: [widthNum, '98%'],
                //content: $("#CurrentInocChild")
                content: '../child/tchildinoculate.html?chilCode='+registerVM.childCode+"&birthday="+$("#updatabirthday").val()
            });
        },
        //打印待登记列表
        printWait: function () {
            window.open("../tbusregister/printWait", "_blank");
        },
        //打印已登记列表
        printRegister: function () {
            window.open("../tbusregister/printRegister", "_blank");
        },
    },


});


//监听队列呼叫数据， 子组件向父组件发送广播  this.$parent.$emit("pushCurrentNo",this.currentNumber);
registerVM.$on("callingNumber", function (queue) {

    registerVM.sequenceNoId = queue.id;
    if (queue.childCode != null) {
        registerVM.childCode = queue.childCode;
    }
    registerVM.loadCallChild();
})

//监听队列取消数据， 子组件向父组件发送广播  this.$parent.$emit("cancelNumber",this.currentNumber);
registerVM.$on("cancelNumber", function (queue) {
    registerVM.sequenceNoId = queue.id;
    //保存取消原因
    registerVM.saveCancelInfo();
})
registerVM.$on("unSelectQueue", function (queue) {
    if(registerVM.sequenceNoId == queue.id){
        registerVM.sequenceNoId=null;
    }
})

$(function () {
    debugger;
    $("#queryString")[0].focus();
    //儿童查询结果
    $("#selectResultTable").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            {label: '儿童编码', name: 'chilCode', width: 170, key: true,hidden:true},
            {label: '姓名', name: 'chilName', width: 70},
            {label: '性别', name: 'chilSex', width: 40},
            {label: '出生日期', name: 'chilBirthday', width: 140},
            {label: '母亲姓名', name: 'chilMother', width: 70},
            {label: '父亲姓名', name: 'chilFather', width: 70},
            {label: '行政村/居委会', name: 'chilCommittee', width: 90},
            {
                label: '户籍地址', name: 'chilHabiaddress', width: 180

            },
            {
                label: '现居地址', name: 'chilAddress', width: 180

            },
            {label: '家庭电话', name: 'chilTel', width: 80},
            {label: '手机', name: 'chilMobile', width: 100},
            {label: '学校', name: 'chilSchool', width: 80},
            {label: '居住属性', name: 'chilResidence', width: 80},
            {label: '户籍属性', name: 'chilAccount', width: 80},
            {label: '在册情况', name: 'chilHere', width: 80},
            {label: '建档日期', name: 'chilCreatedate', width: 150},
            {label: '建档人', name: 'createUserName', width: 80},
            {label: '出生医院', name: 'chilBirthhospitalname', width: 80},
            {label: '出生体重（kg）', name: 'chilBirthweight', width: 100},
            {label: '民族', name: 'chilNatiId', width: 60},
            {label: '免疫卡号', name: 'chilCardno', width: 80},
            {label: '母亲身份证号', name: 'chilMotherno', width: 180},
            {label: '过敏史', name: 'chilSensitivity', hidden: true, width: 100},
            {label: '父亲身份证号', name: 'chilFatherno', width: 180},
            {label: '备注', name: 'remark', width: 80}

        ],
        viewrecords: true,
        height: 328,
        rowNum: 1000,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 35,
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
            var re_records = $("#selectResultTable").jqGrid('getGridParam', 'records'); //获取数据总条数
            var row = $("#selectResultTable").getDataIDs();

            if (re_records == 0) {
                //根据儿童编码查询儿童的信息展示
                var widthNum = Win.WinW - 100 + 'px';
                layer.open({
                    title: '省平台下载',
                    skin: 'printDialog',
                    type: 2,
                    closeBtn: 1,//取消右上角的x关闭按钮
                    area: ["98%", "98%"],
                    content: '../provinceplatform/platformSearch.html?chilCode=' + registerVM.q.chilCode
                });
                //表格只能在弹框之后加载，否则无效
                /*loadTable();*/
                layer.msg('本地没有查询到儿童信息,去平台下载', {icon: 5});
            }
            if (row.length == 1) {
                $("#selectResultTable").setSelection(row[0]);
            }
            registerVM.resetCondition();
            $("#updatachilAccount").css({"display": "none"});
            $("#updatahere").css({"display": "none"});
            $("#updatachilResidence").css({"display": "none"});
            $("#updatasex").css({"display": "none"});
        },
        onSelectRow: function (rowid, iRow, iCol, e) {
            var rowData = $("#selectResultTable").jqGrid('getRowData', rowid);
            //保存登记全局界面儿童编码
            registerVM.childCode = rowData.chilCode;
            var birthTime = rowData.chilBirthday;
            var age = getAge(birthTime);
            registerVM.message = {
                childCode:rowData.chilCode,
                name: rowData.chilName,
                cardNo: rowData.chilCardno,
                account: rowData.chilAccount,
                birthday: registerVM.dateFormatter(rowData.chilBirthday),
                address: rowData.chilAddress,
                montherName: rowData.chilMother,
                fatherName: rowData.chilFather,
                chilResidence: rowData.chilResidence,
                mobile: rowData.chilMobile,
                tel: rowData.chilTel,
                habiaddress: rowData.chilHabiaddress,
                remark: rowData.remark,
                age: age,
                sex: rowData.chilSex,
                here: rowData.chilHere,
                committee: rowData.chilCommittee,
            }

            $("#lastInoTime").html("：");
            //最后一次接种时间
            getLastInoTime(rowid);
            //历次接种记录
            //getHistoryInoculate(rowid)
            getHistoryInoculate1(rowid);
            //edits(rowid);
            //过敏史
            /* getAllergyHistory(rowid);*/
            //禁忌
            /* getTaboo(rowid);*/
            //传染病史
            /*getInfectionHistory(rowid);
            //异常反应
            getAbnormalHistory(rowid);*/
            getTrouble(rowid);
            loadInoculateSiteDataForRegister();
            //条码绑定框获取焦点
            $("#barCodeInput").focus();
            var frame = document.getElementById('inoculation_iframe');
            frame.src="../child/tchildinoculate.html?chilCode="+rowData.chilCode+"&birthday="+rowData.chilBirthday;
        },
        ondblClickRow: function (rowid, iRow, iCol, e) {
            registerVM.register();
        }
    });
    //今日登记疫苗
    $("#currentRegisterVacc").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', key: true, hidden: true, width: 150},

            {label: '登记疫苗', name: 'bio_name', width: 120},
            {label: '剂次', name: 'inoc_time', width: 40},
            {label: '登记日期', name: 'inoculate_date', width: 100,
                formatter:function (value) {
                    return value.substring(0,10);
                }
            },
            {label: '接种部位', name: 'text', width: 80},
            {label: '疫苗批号', name: 'batchnum', width: 110},
            {label: '生产厂家', name: 'factory_name', width: 110}
            // {label: '批号', name: 'inocDepaCode', width: 150}
        ],
        viewrecords: true,
        height: 'auto',
        // width:1200,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        shrinkToFit: false,
        autoScroll: true,
        multiselect: true,
        // pager: "#registerPage",
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
        onSelectRow:function (rowid, status) {
            /* if (!status) return;//如果是取消选中，则不开启编辑模式
             endEdit($("#currentRegisterVacc"));

             $('#currentRegisterVacc').setColProp('lineCount', {
                 editable: true,editoptions: {buildSelect: null, dataUrl: null, value: ''}
             });

             //开启编辑
             $("#currentRegisterVacc").editRow(rowid);*/
            // endEdit($("#currentRegisterVacc"));
        },
        gridComplete: function () {

            //隐藏grid底部滚动条
            // $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

    //今日已登记儿童
    $("#registerTable").jqGrid({
        url: '../tbusregister/queryToDayRegisterList',
        datatype: "json",
        colModel: [
            // {label: 'id', name: 'id', key: true, hidden: true, width: 150},
            {label: '排队号', name: 'number', width: 80,align:'center'},
            {label: '儿童姓名', name: 'childName', width: 120,align:'center'},
            {label: '登记疫苗', name: 'bioName', width: 300,align:'center',
                formatter: function (cellValue, options, rowdata) {
                    return "<label>"+cellValue+"</label>";
                },
            },
            {label: '接种部位', name: 'inoculate_site', width: 100,align:'center'},
            {label: '剂次', name: 'dose_no', width: 80,align:'center'},
            {label: '性别', name: 'sex', width: 80,align:'center'},
            {label: '出生日期', name: 'birthday', width: 130,align:'center'},
            {label: '母亲姓名', name: 'mother', width: 120,align:'center'},
            {label: '父亲姓名', name: 'father', width: 120,align:'center'},
            {label: '家庭电话', name: 'chilMobile', width: 150,align:'center'},
            {label: '手机', name: 'chilTel', width: 150,align:'center'},
            {label: '儿童编号', name: 'childCode', width: 160, key: true,align:'center'},
            {label: '行政村/居委会', name: 'committee', width: 160,align:'center'},
            {label: '户籍地址', name: 'habiaddress', width: 150,align:'center'},
            {label: '现居地址', name: 'address', width: 150,align:'center'},
            {label: '疫苗批号', name: 'batchnum', width: 300,align:'center'},
            {label: '备注', name: 'remark', width: 150,align:'center',
                formatter: function (cellValue, options, rowdata) {
                    if(cellValue==null || cellValue=="null"){
                        return "";
                    }else{
                        return cellValue;
                    }
                },
            },
            // {label: '批号', name: 'inocDepaCode', width: 150}
        ],
        viewrecords: true,
        height: 260,
        // width:1200,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        shrinkToFit: false,
        autoScroll: true,
        // multiselect: true,
        pager: "#registerPage",
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
            var total = $("#registerTable").jqGrid('getGridParam', 'records');
            $("#registerNumber").html(total);
            //隐藏grid底部滚动条
            // $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    //待登记列表
    $("#waitTable").jqGrid({
        url: '../tbusregister/queryToDayWaitList',
        datatype: "json",
        colModel: [
            {label: '排队号', name: 'noText', width: 100, editable: true},
            {label: '儿童编号', name: 'childCode', width: 120},
            {label: '儿童姓名', name: 'childName', width: 120},
            {label: '性别', name: 'sex', width: 80,formatter:function (cell,opt,data) {
                    if(cell=="男"){
                        return "男";
                    }else if (cell=="女"){
                        return "女";
                    }else{
                        return "";
                    }
                }},
            {label: '出生日期', name: 'birthday', width: 130}
        ],
        viewrecords: true,
        height: 260,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        // multiselect: true,
        pager: "#waitPage",
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
            var total=$("#waitTable").jqGrid('getGridParam', 'records');
            $("#waitNumber").html(total);
            //隐藏grid底部滚动条
            // $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

//历史接种记录
    /*    $("#historyRecord").jqGrid({
            // url: '../tchildinoculate/list?chilCode=' + registerVM.childCode,
            datatype: "json",
            colModel: [
                {label: 'id', name: 'id', key: true, hidden: true, width: 10,align:'center'},
                {label: '疫苗类别', name: 'inocVcnKind', width: 180, editable: true,align:'center',
                    cellattr: function (rowId, tv, rawObject, cm, rdata) {
                        return 'id=\'inocVcnKind' + rowId + "\'";
                    }
                },
                {label: '疫苗编码', name: 'inocUseticket', width: 80,hidden: true, align:'center'},
                {label: '疫苗名称', name: 'inocBactCode', width: 180, editable: true,align:'center'

                },
                {label: '剂次', name: 'inocTime', width: 50,align:'center'},
                {label: '接种属性', name: 'inocProperty', width: 60,align:'center'},
                {label: '接种日期', name: 'inocDate', width: 110,align:'center',
                    formatter:function (value) {
                        return value.substring(0,10);
                    }
                },

                {label: '接种单位', name: 'inocDepaCode', width: 170,align:'center'},
                {label: '疫苗批号', name: 'inocBatchno', width: 110,align:'center'},
                {label: '生产企业', name: 'inocCorpCode', width: 90,align:'center'},
                {label: '接种状态', name: 'inocOpinion', hidden: true, width: 120},
                {
                    label: '是否免费', name: 'inocFree', width: 70,
                    formatter: function (value) {
                        if (value == 1) {
                            return "免费";
                        } else if (value == 0) {
                            return "收费";
                        } else {
                            return "";
                        }
                    },
                    unformatter: function (cellvalue, options, cellobject) {
                        return cellvalue;
                    }
                },
                {label: '接种部位', name: 'inocInplId', width: 60,align:'center'}
               /!* {label: '接种护士', name: 'inocNurse', width: 80,align:'center'},*!/


                /!* { label: '疫苗失效期', name: 'inocValiddate', width: 85 },*!/

                /!* {
                     label: '是否免费', name: 'inocFree', width: 70,
                     formatter: function (value) {
                         if (value == 1) {
                             return "免费";
                         } else if (value == 0) {
                             return "收费";
                         } else {
                             return "";
                         }
                     },
                     unformatter: function (cellvalue, options, cellobject) {
                         return cellvalue;
                     }
                 }*!/



                /!*{label: '留观是否完成', name: 'leaveTime', width: 80, formatter: observe},
                {label: '留观时间', name: 'observeTime', width: 80, formatter: observeTime},
                {label: '添加记录时间', name: 'createTime', width: 80, hidden: true}*!/

                /!*  { label: '备注', name: 'remark', width: 80 }*!/
            ],
            viewrecords: true,
            // height: 165,
            width:'817px',
            height: '550px',
            rowNum: 500,
            rowList: [40, 50, 80],
            rownumbers: true,
            rownumWidth: 25,
            autowidth: true,
            multiselect: true,
            pager: "#inoculationGridPager",
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
                MergerStatistics("historyRecord", 'inocVcnKind');
                //隐藏grid底部滚动条
                // $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            },
            onSelectRow: function (rowid, iRow, iCol, e) {
                var rowData = $("#historyRecord").jqGrid('getRowData',rowid);//获取当前行的数据

            },
            ondblClickRow: function (rowid) {
                var chilCode = getSelectedRow_child();
                var rowData = $("#historyRecord").jqGrid('getRowData',rowid);//获取当前行的数据
                console.log(rowData);
                var httpurl = "../child/updatainoRecorddialog.html?id=" + rowData["id"]+
                    "&inocDepaCode=" + encodeURI(rowData["inocDepaCode"]) +
                    "&chilCode="+ chilCode+"&type=1"
                var index=layer.open({
                    title: "修改接种记录",
                    area: ["800px", "310px"],
                    type: 2,
                    content: httpurl,
                    success: function (index, layero) {

                        $(".layui-layer-page .layui-layer-content").css("overflow-y", "hidden");

                    },
                })
            }
        });*/
    //删除补录
    removeNewAdd = function (op) {
        $("#historyTable1").jqGrid("delRowData", op);
        /*var grid = $("#historyTable1");
        var rowKey = grid.getGridParam("getDataIDs");
        if (rowKey) {
            grid.delGridRow(rowKey);
        } else {
            alert("请选择要删除的行！");
        }*/
    };

    registerVM.recommend = [];
    sexCode();//性别
    //儿童信息
    $("#updatachilAccount").css({"display": "none"});
    $("#updatahere").css({"display": "none"});
    $("#updatachilResidence").css({"display": "none"});
    $("#updatasex").css({"display": "none"});
    $("#updatachilcommittee").css({"display": "none"});
    //儿童信息弹框
    $("#updataaccount1").css({"display": "none"});
    $("#updatahere1").css({"display": "none"});
    $("#updatachilResidence1").css({"display": "none"});
    $("#updatasex1").css({"display": "none"});
    $("#updatachilcommittees").css({"display": "none"});

    Registrationmonitoring();
    mouseoverhistorylist();//查看历史接种记录

    updateloadCommiteeData();

    // historybllist();//补录历史接种记录

});

registerVM.$on("registerFinish",function(){
    //$("#currentDayWaitInocChild").jqGrid('clearGridData');
    setTimeout(function () {
        Registrationmonitoring();
    },500)
    // grid.jqGrid("clearGridData");
});

//删除历史接种记录
function deleteino() {
    // var grid = $("#inoculation_iframe").contents().find("#inoculationGrid");
    var id = $("#inoculation_iframe").contents().find("#inoculationGrid").getGridParam("selrow");
    console.log(id);
    if(!id){
        alert("请选择一条记录");
        return ;
    }
    var ids = [];
    ids.push(id);
    console.log(ids);
    if(id == null){
        return ;
    }
    $.ajax({
        type:"post",
        url:"../tchildinoculate/info/"+id,
        dataType: 'json',
        success:function (data) {
            layer.prompt({title: '请输入删除原因'}, function (reson, index) {
                if (reson == null || reson.trim().length < 2) {
                    layer.msg("请输入删除原因！");
                    return;
                }
                layer.close(index);
                $.ajax({
                    type: "POST",
                    url: "../tchildinoculate/delete?remark=" + reson,
                    data: JSON.stringify(ids),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code == 0) {
                            layer.msg("删除成功");
                            // window.location.reload()
                            document.getElementById('inoculation_iframe').contentWindow.location.reload(true);
                            return;
                        } else {
                            alert(r.msg);
                        }
                    }
                });
                layer.close(index);
            })
        }
    });
}

function getLastInoTime(parm) {
    var lastInoTime = null;
    //获取上一次接种时间
    $.ajax({
        url: "../tchildinoculate/LastInoInfo/" + parm,
        dataType: 'json',
        success: function (data) {
            try {
                $("#lastInoTime").append(registerVM.dateFormatter(data.tChildInoculate.inocDate));
            } catch (E) {
            }
        }
    });
}




//保存
// function saveIno(){
//     var remarks = $("#remark").val();
//     if(remarks.trim().length<2){
//         alert("请输入修改原因");
//         return;
//     }
//     var date = updateVue.ino.inocDate;
//     if(date.length==8){
//         date = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" 00:00:00";
//         Vue.set(updateVue.ino, 'inocDate', date);
//     }
//     if(date.length==10){
//         date = date+" 00:00:00";
//         Vue.set(updateVue.ino, 'inocDate', date);
//     }
//
//     Vue.set(updateVue.ino, 'remark', remarks);
//     Vue.set(updateVue.ino, 'inocModifyCode', orgId);
//     Vue.set(updateVue.ino, 'chilCode', chilCode);
//     Vue.set(updateVue.ino, 'inocBactCode', $("#inocBactCode").val());
//     Vue.set(updateVue.ino, 'inocCorpCode', $("#inocCorpCode").val());
//     /* layer.confirm('保存后上传，确定要保存么？', function(index) {*/
//     layer.msg('正在保存...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 100000});
//     $.ajax({
//         type: "POST",
//         url: "../tchildinoculate/saveAsBackUp",
//         data: JSON.stringify(updateVue.ino),
//         contentType: 'application/json;charset=UTF-8',
//         success: function (r) {
//             if (r.code === 0) {
//                 layer.msg("保存成功");
//                 $('#myModal').modal('hide');
//                 $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + chilCode);
//                 return;
//             } else {
//                 layer.msg("保存失败");
//             }
//         }
//     });
// }

//============================补录==============================

function  getClassIdType(bioCode) {
    $.ajax({
        type: 'POST',
        url: '../tchildinoculate/getbioClassIdType?bioClasssId='+bioCode,
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        success: function (data) {
            registerVM.inocBioCodeArr=data.data;
            console.log(data.data[0].bioCode)
        }
    });
}





//关闭
function colsedialog() {
    $('#myModal').modal('hide');
}
function colsedialog2() {
    $('#jzdw_myModal').modal('hide');
}






//接种部位
function loadInoculateSiteDataForRegister() {
    $.ajax({
        type: "post",
        url: "../child/listdiccode?ttype=code_inoculation_site",
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            registerVM.inoculateSiteArr = data.data;
        }
    });
}

//接种属性
function inoculateRoadData() {
    $.ajax({
        type: "post",
        url: "../child/listdiccode?ttype=code_vaccine_property",
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            registerVM.inoculationArr = data.data;
        }
    });
}


//生产厂家
function factory() {
    var str="";
    $.ajax({
        type: "post",
        url: "../tvacfactory/getAllData",
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            console.log(data)
            // registerVM.factoryArr = data.page;
            if (data != null) {
                var jsonobj = eval(data.page);
                console.log(jsonobj);
                var length = jsonobj.length;
                for (var i = 0; i < length; i++) {
                    if (i != length - 1) {
                        str += jsonobj[i].factoryCode + ":" + jsonobj[i].factoryCnName + ";";
                    } else {
                        str += jsonobj[i].factoryCode + ":" + jsonobj[i].factoryCnName;// 这里是option里面的 value:label
                    }
                }
            }
        }
    });
    return str;
}


function hospital(this_) {
    $.ajax({
        type: "post",
        url: "../tbaseposition/gethospital",
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            registerVM.inocDepaArr = data.data;
        }
    });
}




function getAefiBactCodeoutside(chilCode) {
    $.ajax({
        url: "../tchildinoculate/outsideinoculatebio?chilCode="+chilCode,
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',//重点关注此参数
        success: function (data) {
            console.log(data);
            var dataall=data.data;
            $("#outsideInoculate").setGridParam({datatype : 'local',data: data.data}).trigger('reloadGrid');
            $.ajax({
                url: '../tchildinoculate/list?limit=100&page=1&chilCode=' + chilCode,
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',//重点关注此参数
                success: function (data) {
                    console.log(data.page.list);
                    for (var i=0;i<dataall.length;i++){
                        for (var j=0;j<data.page.list.length;j++){
                            if(dataall[i].bioCnSortname==data.page.list[j].inocBactCode){
                                var selIDs = $("#outsideInoculate").getDataIDs();
                                for(var x=0;x<selIDs.length;x++){
                                    if (selIDs[x] = i+1){
                                        if (data.page.list[j].inocTime==1) {
                                            $("#outsideInoculate").jqGrid("setCell",selIDs[x],"one",data.page.list[j].inocDate);
                                        }else if (data.page.list[j].inocTime==2) {
                                            $("#outsideInoculate").jqGrid("setCell",selIDs[x],"two",data.page.list[j].inocDate);
                                        }else if (data.page.list[j].inocTime==3){
                                            $("#outsideInoculate").jqGrid("setCell",selIDs[x],"three",data.page.list[j].inocDate);
                                        }else if (data.page.list[j].inocTime==4){
                                            $("#outsideInoculate").jqGrid("setCell",selIDs[x],"four",data.page.list[j].inocDate);
                                        }else if (data.page.list[j].inocTime==5){
                                            $("#outsideInoculate").jqGrid("setCell",selIDs[x],"five",data.page.list[j].inocDate);
                                        }else if (data.page.list[j].inocTime==6){
                                            $("#outsideInoculate").jqGrid("setCell",selIDs[x],"six",data.page.list[j].inocDate);
                                        }else if (data.page.list[j].inocTime==7) {
                                            $("#outsideInoculate").jqGrid("setCell",selIDs[x],"seven",data.page.list[j].inocDate);
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            });


        }
    });

}

function getHistoryInoculate1(chilCode) {
    $("#historyTable1").jqGrid('setGridParam', {
        url: '../tchildinoculate/list?limit=100&page=1&chilCode=' + chilCode,
    }).trigger("reloadGrid");
}

//=====================补录end==========================


//获取历次接种记录
function getHistoryInoculate() {
    /*$("#historyTable").jqGrid('setGridParam', {
        postData: {},
        page: 1,
        url: '../tchildinoculate/list?limit=1000&page=1&chilCode=' + registerVM.childCode,
    }).trigger("reloadGrid");*/
    registerVM.showHistoryInoculations();
}


//选择一条记录
function getSelectedRow_child() {

    var grid = $("#selectResultTable");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        layer.msg("请选择一条记录");
        return;
    }

    return rowKey;
}


//打印儿童信息
function printChildInfo(printType, jrxml_name) {
    var chilCode = getSelectedRow_child();
    if (chilCode == undefined || chilCode == null || chilCode == "") {
        layer.msg("请选择一个儿童！");
        return;
    }
    layer.closeAll();
    window.open("../reports/printchildinfoByModel?jrxml_name=" + jrxml_name + "&chilCode=" + chilCode + "&printType=" + printType).print();
    setTimeout(window.close, 0);
}

//打印登记疫苗

function printWaitInocBioName(num,printType,jrxml_name) {
    var lineCount = $("#lineCount").val();
    var fontSize = $("#fontSize").val();
    if(fontSize == "" || isNaN(fontSize)){
        fontSize = 8;
    }
    var chilCode = getSelectedRow_child();
    if(chilCode == undefined || chilCode == null || chilCode == ""){
        layer.msg("请选择一个儿童！");
        return ;
    }
    /* var grid = $("#currentRegisterVacc");
     endEdit(grid);*/
    var inoculate_id = $("#currentRegisterVacc").getGridParam("selarrrow");
    var lineCounts = [];
    if(inoculate_id!=null && inoculate_id.length!=0 && lineCount != "" && !isNaN(lineCount)){
        /* for(var i = 0; i< inoculate_id.length;i++){
             var rowData = $("#currentRegisterVacc").jqGrid('getRowData', inoculate_id[i]);
             var lineCount = rowData.lineCount;
             lineCounts.push(lineCount);
         }*/
        if ($("#lineCountCheck").is(':checked')){
            lineCount=parseInt(lineCount)+21;
        }
        layer.closeAll();
        window.open("../reports/printSecondVaccBylineCount/?lineCount=" + lineCount+"&inoculate_id="+inoculate_id+ "&num=1" + '&printType=' + printType + "&child_id=" + chilCode+"&plag=register"+"&fontSize="+fontSize).print();
    }else {
        layer.closeAll();
        var mywin = window.open("../reports/printCurrentDayInoculateByModel?num=" + num + "&version=" + jrxml_name + "&chilCode=" + chilCode + "&printType=" + printType+"&fontSize="+fontSize).print();
    }

    $('#queryString')[0].focus();
}

//查询条件判断
function checkParam() {
    if (registerVM.q.chilCode != null) {
        return true;
    } else if (registerVM.q.chilName != null) {
        return true;
    } else if (registerVM.q.chilCommittee != null) {
        return true;
    } else if (registerVM.q.chilBirthdayStart != null) {
        return true;
    } else if (registerVM.q.chilBirthdayEnd != null) {
        return true
    } else if (registerVM.q.chilHere != null) {
        return true;
    } else {
        return false;
    }
}

/**
 * 加载登记界面的弹框中的两个jqgrid 表格
 */
function loadTable() {
    //计算出推荐的信息
    /* $.ajax({
         url: "../recommend/recommend",
         data: {"childCode": registerVM.childCode},
         dataType: 'json',
         contentType: 'application/json;charset=UTF-8',//重点关注此参数
         success: function (data) {
             //循环比对，将推荐的疫苗放入推荐数组中
             var rowDatas = getJQAllData("receiveTable");
             for (var j = 0; j < data.length; j++) {
                 for (var i = 0; i < rowDatas.length; i++) {//vaccineCode  batchnum
                     if (rowDatas[i].vaccineCode == data[j].code && rowDatas[i].batchnum == data[j].batchnum) {
                         registerVM.addRecommend(rowDatas[i]);//触发可选列表的  添加按钮 点击事件
                     }
                 }
             }
         }
     });*/
    //更新了参数之后，需要重新加载
    $('#recommendTable').jqGrid('clearGridData');
    //推荐疫苗
    $("#recommendTable").jqGrid({
        datatype: "local",
        colModel: [
            /*{label: 'id', name: 'id', width: 150, key: true, hidden: true},*/
            {label: '疫苗分类', name: 'className', width: 1,align:'center',hidden: true},
            {label: '疫苗编号', name: 'bioCode', key: true, width: 1,align:'center',hidden: true},
            {label: '疫苗名称', name: 'bioName', width: 180,align:'center',
                formatter: function (cellValue, options, rowdata) {
                    return "<label>"+cellValue+"</label>";
                },
            },
            {label: '剂次', name: 'doseNo', width: 70,align:'center'},
            {label: '疫苗批号', name: 'batchnum', width: 110,align:'center'},
            {label: '失效期', name: 'expiryDate', width: 100,align:'center'},
            {label: '生产厂家编码', name: 'factoryCode', width: 1, hidden: true},
            {label: '接种部位', name: 'inoculateSite', width: 90,align:'center',
                formatter: function (cellValue, options, rowdata) {
                    return "<label>"+cellValue+"</label>";
                },unformat:Unformat_Select
            },
            {label: '接种部位编码', name: 'inoculateSiteCode', width: 80, hidden: true,
            },
            {label: '生产厂家名称', name: 'factoryName', width: 150,align:'center'},
            {
                label: '是否收费', name: 'ismf', width: 80,align:'center',
                formatter: function (cellValue, options, rowdata) {
                    if (cellValue == 1) {
                        return "<label>免费</label>";
                    } else {
                        return "<label>收费</label>";
                    }
                }, unformat: function (cellvalue, options, cell) {
                    return cellvalue;
                }
            },
            {
                label: '操作', name: '', width: 80,align:'center',
                formatter: function (cellValue, options, rowdata, action) {
                    return "<a style='cursor:pointer;' onclick='registerVM.removeAddRegister(" + options.rowId + "," + JSON.stringify(rowdata).replace(/"/g, '&quot;') + ")'>删除</a>";
                }
            }
        ],
        viewrecords: true,
        height: 100,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        /* multiselect: true,*/
        autowidth: true,
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
            //隐藏grid底部滚动条
            // $("#recommendTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        onSelectRow:function (rowid,status) {
            if (!status) return;//如果是取消选中，则不开启编辑模式
            endEdit($("#recommendTable"));
            var str = "";
            $.each(registerVM.inoculateSiteArr, function (index, item) {
                str = str + item.value + ":" + item.text + ";";
            });
            str = str.substring(0, str.length - 1);
            $('#recommendTable').setColProp('inoculateSite', {
                editable: true, edittype: "select",editoptions: {buildSelect: null, dataUrl: null, value: str}
            });
            //开启编辑
            $("#recommendTable").editRow(rowid);
        }
    });
    $("#historyRecord").jqGrid('clearGridData');
    $("#historyRecord").jqGrid('setGridParam', {
        postData: {"chilCode": registerVM.childCode},
        url: '../tchildinoculate/list',
        page: 1,
    }).trigger("reloadGrid");
    /*for (var i = 0; i <= registerVM.recommend.length; i++) {
        $("#recommendTable").jqGrid('addRowData', i + 1, registerVM.recommend[i]);
    }*/

    //=================================================================================================
    //可选疫苗
    $("#receiveTable").jqGrid({
        url: '../tmgrstockinfo/registerList',
        datatype: "json",
        mtype: "post",
        colModel: [
            {label: 'id', name: 'id', key: true, width: 1, hidden: true},
            {label: '疫苗编码', name: 'vaccineCode', width: 1, hidden: true},
            {label: '疫苗名称', name: 'productName', width: 230,align:'center',
                formatter:function (cellValue, options, rowdata) {
                    return cellValue+'('+rowdata.vaccineCode+')';
                }
            },
            {label: '失效期', name: 'expiryDate', width: 120,align:'center'},
            {label: '生产厂家编号', name: 'factoryId', width: 10, hidden: true},
            {label: '生产厂家名称', name: 'factoryName', width: 130,align:'center'},
            {label: '疫苗批号', name: 'batchnum', width: 140,align:'center'},
            {label: '疫苗价格', name: 'price',width: 80, align:'center'},
            {label: '库存数量', name: 'amount',width: 80,align:'center'},
            {
                label: '接种途径', name: 'bioSpecId', width: 80,align:'center',
                formatter: function (cellValue, options, rowdata) {
                    if (cellValue == 1) {
                        return "<label>肌内</label>";
                    } else if (cellValue == 2) {
                        return "<label>皮内</label>";
                    } else if (cellValue == 31) {
                        return "<label>皮下</label>";
                    } else if (cellValue == 4) {
                        return "<label>口服</label>";
                    } else {
                        return "<label>其他</label>";
                    }
                }, unformat: function (cellvalue, options, cell) {
                    return cellvalue;
                }
                , hidden: true
            },
            {label: '接种部位', name: 'inoculateSite', width: 120, hidden: true},//存显示的中文
            {label: '接种部位值', name: 'inoculateSiteCode', width: 20, hidden: true},//存的是字典表的value值
            {label: '人份数量', name: 'personAmount', width: 80, hidden: true},
            {label: '疫苗分类', name: 'className', width: 80, hidden: true},
            {label: '转换人份', name: 'conversion', width: 80, hidden: true},
            {label: '疫苗编码', name: 'vaccineCode', width: 80, hidden: true},
            {
                label: '操作', name: '',width: 80,align:'center',
                formatter: function (cellValue, options, rowdata, action) {
                    return "<a style='cursor:pointer;' onclick='registerVM.addClick(" + JSON.stringify(rowdata).replace(/"/g, '&quot;') + ")'>添加</a>";
                }
            }
        ],
        viewrecords: true,
        //height: 100,
        height: 370,
        rowNum: 100,
        rownumbers: true,
        // rowList : [20,30,40],
        rownumWidth: 25,
        autowidth: true,
        shrinkToFit: false,
        autoScroll: true,
        multiselect: false,
        //autowidth: true,
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
            childCode: registerVM.childCode,
        },
        /*setGridParam:{
            datatype:'json',
            postData:{'childCode':registerVM.childCode},
            page:1
        },*/
        // gridComplete: function () {
        //     //隐藏grid底部滚动条
        //     // $("#receiveTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        // },
        gridComplete: function () {
            var ids = $("#receiveTable").getDataIDs();
            for (var i = 0; i < ids.length; i++) {
                var rowData = $("#receiveTable").getRowData(ids[i]);
                var time1 = Date.parse(new Date(rowData.expiryDate));
                var time2 = Date.parse(new Date());
                var day = Math.abs(parseInt((time1 - time2) / 1000 / 3600 / 24));
                if (day <= 30) {
                    $('#' + ids[i]).css("background-color", "red");
                } else if (day <= 90) {
                    $('#' + ids[i]).css("background-color", "yellow");
                }

            }
        },
        ondblClickRow: function (rowid, iRow, iCol, e) {
            var rowData = jQuery(this).getRowData(rowid);
            console.info(rowData);
            registerVM.addClick(rowData);
        }
    });

    $('#receiveTable').jqGrid('clearGridData');
    $("#receiveTable").jqGrid('setGridParam', {
        postData: {"childCode": registerVM.childCode},
        page: 1,
    }).trigger("reloadGrid");

//修改后疫苗推荐，邓20181109
    $.ajax({
        url: "../recommend/recommend",
        data: {"childCode": registerVM.childCode},
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',//重点关注此参数
        success: function (data) {
            //循环比对，将推荐的疫苗放入推荐数组中
            var rowDatas = getJQAllData("receiveTable");
            for (var j = 0; j < data.length; j++) {
                for (var i = 0; i < rowDatas.length; i++) {//vaccineCode  batchnum
                    if (rowDatas[i].vaccineCode == data[j].code && rowDatas[i].batchnum == data[j].batchnum) {
                        registerVM.addRecommend(rowDatas[i]);//触发可选列表的  添加按钮 点击事件
                    }
                }
            }
        }
    });


}

$(function(){
    cc();
    function cc(){
        var index = layer.load();
        $.ajax({
            url:"../configuration/queryConfiguration",
            data:{"type":"pause_number","startUsing":1},
            async:false,
            success:function (result) {
                if(result.code == 0){
                    var status = result.data.startUsing;
                    if(status == 1){
                        $("#stop").html("暂停取号");
                        $("#stopicon").removeClass("layui-icon-play");
                        $("#stopicon").addClass("layui-icon-pause");
                    }else{
                        $("#stop").html("开始取号");
                        $("#stopicon").removeClass("layui-icon-pause");
                        $("#stopicon").addClass("layui-icon-play");
                    }

                }else{
                    //alert("门诊接种点的取号设置无设置，请设置");
                    alert(result.msg);
                }
                layer.closeAll();
            },
            error:function(){
                layer.closeAll();
            }
        });
    }

    //获取域名
    var host = window.location.host;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    var websocketUrl = host + projectName;
    var websocketSuspend  = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocketSuspend = new WebSocket("ws://"+websocketUrl+"/configurationWebsoket");
    }else {
        cc();
    }

    //连接发生错误的回调方法
    websocketSuspend.onerror = function () {
        setMessageInnerHTML("连接发生错误..");
    };

    //连接成功建立的回调方法
    websocketSuspend.onopen = function () {
        setMessageInnerHTML("连接成功....");
    }

    //接收到消息的回调方法
    websocketSuspend.onmessage = function (event) {
        var data = JSON.parse(event.data);
        var status = data.startUsing;
        if (status == 1) {
            $("#stop").html("暂停取号");
            $("#stopicon").removeClass("layui-icon-play");
            $("#stopicon").addClass("layui-icon-pause");
        } else {
            $("#stop").html("开始取号");
            $("#stopicon").removeClass("layui-icon-pause");
            $("#stopicon").addClass("layui-icon-play");
        }

    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        //websocketSuspend.close();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        console.log(innerHTML);
    }

})

function mouseoverhistorylist() {
    $("#historyRecord").on("mouseover", 'tr[role="row"]', function () {
        var trid = $(this).attr("id");
        //里面写相关的操作。
        var rowData = $("#historyRecord").jqGrid('getRowData', trid);
        var emergencySencondMgrId = rowData["inocDate"];//列名和jGrid定义时候的值一样
        var statas = rowData["inocOpinion"];//列名和jGrid定义时候的值一样
        var channel = parseInt(rowData["channel"]);//列名和jGrid定义时候的值一样
        var str = '';
        var ch="";
        switch (channel){
            case 1:ch="肌内";break;
            case 2:ch="皮下";break;
            case 3:ch="皮内";break;
            case 4:ch="口服";break;
            default :ch="其他";
        }
        if (statas == 1) {
            str = '及时'
        } else if (statas == 2) {
            str = '合格'
        } else if (statas == 3) {
            str = '超期'
        } else if (statas == 4) {
            str = '首针提前'
        } else if (statas == 5) {
            str = '间短'
        }
        layer.msg("<p align='left'>接种月龄:"+dateDiff(birthdayinocs, emergencySencondMgrId) + "</p><p align='left'>接种评价：" + str+"</p>"+"<p align='left'>接种途径：" + ch+"</p>");
    })
}


function single() {
    var radios = document.getElementsByName("empBio");
    var chilCode = getSelectedRow_child();
    if (chilCode == undefined || chilCode == null || chilCode == "") {
        layer.msg("请选择一个儿童！");
        return;
    }
    layer.closeAll();
    $("#chilCode1").val(chilCode);
    factory();//生产厂家、
    var indexAdd=layer.open({
        title: "接种补录",
        skin: 'printDialog',
        area: ["98%", "98%"],
        type: "1",
        content :'<iframe id="addrecord" src="../bus/addrecord.html?childCode='+chilCode+'&childBirthday='+$("#updatabirthday").val()+'" style="width: 100%;height: 100%;overflow:auto" ></iframe>' ,
        // btn:["保存","关闭"],
        btnAlign: 'c',
        success : function(index, layero){
            /* $("#inputbio input[name='empAddress']").hide();
             $("#bsaddress").hide();
             $("#wsaddress").hide();
             $("#wsjzdw").hide();
             $("#address").hide();

             $("#outsideInoculate").jqGrid("clearGridData");
             $("#historyTable1").jqGrid("clearGridData");
             getHistoryInoculate1(chilCode);//历史记录
             getAefiBactCodeoutside(chilCode);
             searchNames();//疫苗*/
            $(".layui-layer-page .layui-layer-content").css("overflow-y","hidden");
            // inoculateRoadData();//接种属性
            // hospital();//接种单位

        },
        yes:function(index, layero){
            /* var iframeWin2 = window["layui-layer-iframe" + index];
             $('#'+frameId)[0].contentWindow.savedialog1();
             if ($("#myTabs li[class='active']").find("a").html()=="省内补录" ) {
                 savedialog1();
             }else if ($("#myTabs li[class='active']").find("a").html()=="外省补录") {
                 $('#'+frameId)[0].contentWindow.saveoutside();
             }*/
        },
        btn2:function(index, layero){
        },
        end:function () {
            // $("#chilBirthdayStart").focus();
        }
    })
    // $('#myModal').modal({backdrop: 'static', show:true});//点空白才禁止关闭弹框

}
//异常反应
function noabnormal(){
    var chilCode = getSelectedRow_child();
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= registerVM.message.name;
    var indexAdd=layer.open({
        title: "异常反应",
        skin: 'printDialog',
        area: ["52%", "52%"],
        type: 2,
        closeBtn: 1,//取消右上角的x关闭按钮
        content: '../bus/abnormal.html?chilCode='+chilCode+'&chilName='+chilName,
        success: function (index, layero) {

        },
    })
}
//传染病

function infection() {
    var chilCode = getSelectedRow_child();
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= registerVM.message.name;
    var indexAdd=layer.open({
        title: "传染病",
        skin: 'printDialog',
        area: ["52%", "52%"],
        type: 2,
        closeBtn: 1,//取消右上角的x关闭按钮
        content: '../bus/infection.html?chilCode='+chilCode+'&chilName='+chilName,
        success: function (index, layero) {

        },
    })
}
//接种禁忌

function taboo() {
    var chilCode = getSelectedRow_child();
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= registerVM.message.name;
    var indexAdd=layer.open({
        title: "接种禁忌",
        skin: 'printDialog',
        area: ["52%", "52%"],
        type: 2,
        closeBtn: 1,//取消右上角的x关闭按钮
        content: '../bus/taboo.html?chilCode='+chilCode+'&chilName='+chilName,
        success: function (index, layero) {

        },
    })

}
//过敏史
function allergy() {
    var chilCode = getSelectedRow_child();
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= registerVM.message.name;
    var indexAdd=layer.open({
        title: "过敏史",
        skin: 'printDialog',
        area: ["52%", "52%"],
        type: 2,
        closeBtn: 1,//取消右上角的x关闭按钮
        content: '../bus/allergy.html?chilCode='+chilCode+'&chilName='+chilName,
        success: function (index, layero) {

        },
    })
}


$(function () {
    $("#chilCommittee").next("div").on("click",function () {
        $("#chilCommittee").next("div").addClass("open");
    })
    $("#chilBirthhospitalname").next("div").on("click",function () {
        $("#chilBirthhospitalname").next("div").addClass("open");
    })
    //为下载备份添加提示
    $('#downloadBack').on({
        mouseenter:function(){
            var that = this;
            tips =layer.tips("<span style='color:#000;font-size:15px'>请每天工作完成之后，下载备份数据。</span>",that,{tips:[1,'#fff'],time:0,area: 'auto',maxWidth:500});
        },
        mouseleave:function(){
            layer.close(tips);
        }
    });
})





