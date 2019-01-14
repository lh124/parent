$(function () {
    $("#jqGrid").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            { label: '疫苗名称', name: 'bioName', width: 160,key:true,formatter: bioNameFormatter},
            { label: '剂次数', name: 'totalInoTimes', width: 50 },
            { label: '完整数', name: 'fullCount', width: 50 },
            { label: '完整率(%)', name: 'inocIntegrityRate', width: 60 },
            { label: '录入数', name: 'fullBioNameCount', width: 50 },
            { label: '录入率', name: 'bioNameEntryRate', width: 55 ,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullBioNameCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullInoDateCount', width: 50 },
            { label: '录入率', name: 'inoDateEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else{
                        return (row.fullInoDateCount/row.totalInoTimes*100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullInocBatchnoCount', width: 50},
            { label: '录入率', name: 'inocBatchnoEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullInocBatchnoCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullInocCorpCodeCount', width: 50},
            { label: '录入率', name: 'inocCorpCodeEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullInocCorpCodeCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullInocRoadCount', width: 50 },
            { label: '录入率', name: 'inocRoadEntryRate', width: 55 ,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullInocRoadCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullInocInplIdCount', width: 50},
            { label: '录入率', name: 'inocInplIdEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullInocInplIdCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullInocPlaceCount', width: 50},
            { label: '录入率', name: 'inocPlaceEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullInocPlaceCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullInocNurseCount', width: 50},
            { label: '录入率', name: 'inocNurseEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullInocNurseCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullPayStatusCount', width: 50 },
            { label: '录入率', name: 'payStatusEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullPayStatusCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullTimelyCount', width: 50 },
            { label: '录入率', name: 'TimelyEntryRate', width: 55,
                formatter:function (value,grid,row) {
                    if(row.totalInoTimes==0){
                        return 0;
                    }else {
                        return (row.fullTimelyCount / row.totalInoTimes * 100).toFixed(2);
                    }
                }}

        ],
        viewrecords: true,
        height: 200,
        rowNum: 50,
        rowList : [20,30,50],
        rownumbers: true,
        rownumWidth: 45,
        autowidth:true,
        /*shrinkToFit:false,*/
        autoScroll: true,
        multiselect: true,
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
            var allCountID = $("#jqGrid").jqGrid('getDataIDs'); //这里获取所有行 主键id 是全的
            //隐藏grid底部滚动条
            //$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    $("#jqGrid").jqGrid('setGroupHeaders', {
        useColSpanStyle: true,
        groupHeaders:[
            {startColumnName:'fullBioNameCount', numberOfColumns:2, titleText: '接种疫苗'},
            {startColumnName:'fullInoDateCount', numberOfColumns: 2, titleText: '接种日期'},
            {startColumnName:'fullInocBatchnoCount', numberOfColumns: 2, titleText: '疫苗批号'},
            {startColumnName:'fullInocCorpCodeCount', numberOfColumns: 2, titleText: '生产企业'},
            {startColumnName:'fullInocRoadCount', numberOfColumns: 2, titleText: '接种途径'},
            {startColumnName:'fullInocInplIdCount', numberOfColumns: 2, titleText: '接种部位'},
            {startColumnName:'fullInocPlaceCount', numberOfColumns: 2, titleText: '接种地点'},
            {startColumnName:'fullInocNurseCount', numberOfColumns: 2, titleText: '接种医生'},
            {startColumnName:'fullPayStatusCount', numberOfColumns: 2, titleText: '收费状态'},
            {startColumnName:'fullTimelyCount', numberOfColumns: 2, titleText: '及时录入'}
        ]
    });
    $("#childInfoGrid").jqGrid({
        //url: '../recommend/inoNote',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 190,key:true,hidden:true},
            { label: '儿童编号', name: 'chilCode', width: 190,
                formatter:function updateChildFormatter(cellValue, options, rowdata, action){
                    return "<a href=\"javascript:;\" onclick='updateChildInoc("+JSON.stringify(rowdata).replace(/"/g, '&quot;')+")'>"+cellValue+"</a>";
                }
            },
            { label: '姓名', name: 'chilName', width: 80 },
            { label: '性别', name: 'chilSex', width: 50,
                formatter:function (value) {
                    if(value==1){
                        return "男";
                    }
                    if(value==2){
                        return "女";
                    }
                }
            },
            { label: '出生日期', name: 'chilBirthday', width: 100,
                formatter:function (value) {
                    if(value!=null){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }

                }
            },
            { label: '疫苗名称', name: 'bioCnSortname', width: 150 },
            { label: '剂次', name: 'inocTime', width: 80 },
            { label: '接种日期', name: 'inocDate', width: 100,
                formatter:function (value) {
                    if(value!=null){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }

                }
            },
            { label:'描述', name: 'describe', width: 380,sortable:false}
        ],
        viewrecords: true,
        height: 300,
        rowNum: 20,
        rowList: [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        autoScroll: true,
        shrinkToFit:false,
        //multiselect: true,
        pager: "#childInfoGridPager",
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
            /*$("#childInfoGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});*/
        }
    });
    vm.loadInfoStatusData();//个案状态
    vm.loadCommiteeData();//行政村
    vm.loadInoBios();//疫苗名称
    vm.chilaccount();//居住属性
    vm.residence();//户籍属性
    vm.loadInoculateSiteData();//接种部位
    vm.factory();//生产厂家
    /* vm.loadInoculationRouteData()//接种途径*/

    $("#birthDayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.birthDayStart = $("#birthDayStart").val();
    });
    $("#birthDayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.birthDayEnd = $("#birthDayEnd").val();
    });

    $("#inocDateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.inocDateStart = $("#inocDateStart").val();
    });
    $("#inocDateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.inocDateEnd = $("#inocDateEnd").val();
    });

    vm.pinyinbiosearch();//疫苗搜索
    vm.pinyincommsearch();//行政村拼音搜索

});

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        tRuleHiv: {},
        tChildInoculate:{},
        q:{
            birthDayStart:null,
            birthDayEnd:null,
            inocDateStart:null,
            inocDateEnd:null,
            childInfoStatus:null,
            inoVacc:null,
            chilAccount:null,
            chilResidence:null,
            chilCommittees:null

        },
        condition:{
            fullInocBatchnoCount:null,
            fullInocCorpCodeCount:null,
            fullInocInplIdCount:null,
            fullInocPlaceCount:null,
            fullInocNurseCount:null
        },
        inoVacc:null,
        chilCommittee:null,
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: function(){
            vm.q = {
                birthDayStart:null,
                birthDayEnd:null,
                inocDateStart:null,
                inocDateEnd:null,
                childInfoStatus:null,
                inoVacc:null,
                chilAccount:null,
                chilResidence:null,
                chilCommittees:null

            }
            $('#childInfoStatus').selectpicker('val', "");
            $('#chilCommittees').selectpicker('val', "");
            $('#chilAccount').selectpicker('val', "");
            $('#inoVacc').selectpicker('val', "");
            $('#chilResidence').selectpicker('val', "");


        },
        update: function (id) {
            vm.showList = false;
            vm.title = "修改";
             $("#inocDepaCode").removeAttr("disabled");

            $("#inocDate").removeAttr("disabled");

            $("#inocBatchno").removeAttr("readonly");

            $("#inocCorpCode").removeAttr("disabled");

            $("#inocFree").removeAttr("disabled");

            $("#inocInplId").removeAttr("disabled");

            $("#inocNurse").removeAttr("readonly");
            vm.getInfo(id);
        },
        getInfo:function(id){
            $.get("../tchildinoculate/info/"+id, function(r){
                vm.tChildInoculate = r.tChildInoculate;
                /*$('#inocBactCode').selectpicker('val',(vm.tChildInoculate.inocBactCode));*/
                vm.tChildInoculate.inocDepaCode = orgName;
                if(vm.tChildInoculate.inocDepaCode.length>8){
                    $("#inocDepaCode").attr("readonly","readonly");
                }
                if(vm.tChildInoculate.inocDate.length>8){
                    $("#inocDate").attr("readonly","readonly");
                }
                if(vm.tChildInoculate.inocBatchno != null && vm.tChildInoculate.inocBatchno.trim().length>2){
                    $("#inocBatchno").attr("readonly","readonly");
                }
                if(vm.tChildInoculate.inocCorpCode!=null && vm.tChildInoculate.inocCorpCode.trim().length>0){
                    $("#inocCorpCode").attr("disabled","disabled");
                    /*$("#inocCorpCode").attr("readonly","readonly");*/
                }
                if(vm.tChildInoculate.inocFree != null && vm.tChildInoculate.inocFree.trim().length>0){
                    $("#inocFree").attr("disabled","disabled");
                    /*$("#inocFree").attr("readonly","readonly");*/
                }
                if(vm.tChildInoculate.inocInplId != null && vm.tChildInoculate.inocInplId.trim().length>0){
                    $("#inocInplId").attr("disabled","disabled");
                    /* $("#inocInplId").attr("readonly","readonly");*/
                }
                /* if(vm.tChildInoculate.inoculateRoad != null && vm.tChildInoculate.inoculateRoad.length>0){
                     $("#inoculateRoad").attr("readonly","readonly");
                 }*/
                if(vm.tChildInoculate.inocNurse != null && vm.tChildInoculate.inocNurse.trim().length>1){
                    $("#inocNurse").attr("readonly","readonly");
                }

            });
        },
        back:function(){
            vm.showList = true;
        },
        excel: function (event) {
            layer.confirm("确定要下载生成的报表文件吗？",function (index) {
                var formdata = $("#inocGatherForm").serialize();
                window.open("../ExcelController/inoculateGather?" +formdata);
                layer.close(index);
            });
        },
        print:function () {
            layer.confirm("确定要打印报表文件吗？",function (index) {
                var formdata = $("#inocGatherForm").serialize();
                window.open("../reports/inoculateGather?" + formdata);
                layer.close(index);
            });

        },
        pinyinbiosearch:function(){
            //初始化下拉框
            $('#inoVacc').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择疫苗',
                // actionsBox:true,
                search:false,
            });
            //选择得到搜索栏input,松开按键后触发事件
            $("#inoVaccId").find('.bs-searchbox').find('input').keyup(function (event) {
                //键入的值
                var inputManagerName = $('#inoVaccId .open input').val();
                var hunt = $("#inoVacc");
                //判定键入的值不为空,才调用ajax
                if (inputManagerName != '') {
                    hunt.empty();
                    var value = null;
                    if("undefined" != typeof vm){
                        value = vm.inoVacc;
                    }
                    var con = '';
                    var reg = new RegExp("^[A-Za-z]+$");
                    if(reg.test(inputManagerName)){
                        for (var i = 0; i < value.length; i++) {
                            if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                                con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
                            }
                        }
                    }else{
                        for (var i = 0; i < value.length; i++) {
                            if(value[i].pinyinInitials != null && value[i].bioCnSortname.indexOf(inputManagerName) == 0){
                                con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
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
                        value = vm.inoVacc;
                    }
                    var con = '';
                    for (var i = 0; i < value.length; i++) {
                        con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
                    }
                    hunt.append(con);
                    hunt.selectpicker('refresh');
                    return false;
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
                    if("undefined" != typeof vm){
                        value = vm.chilCommittee;
                    }
                    var con = '';
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
                    var con = '';
                    for (var i = 0; i < value.length; i++) {
                        con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
                    }
                    hunt.append(con);
                    hunt.selectpicker('refresh');
                    return false;
                }
            });
        },
        saveOrUpdate:function(){
            $("#inocBactCode").attr("disabled",false);
            var remarks = $("#remark").val();
            Vue.set(vm.tChildInoculate, 'inocDepaCode', orgId);
            layer.confirm('保存后上传，确定要保存么？', function(index) {
                layer.msg('正在保存...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 100000});
                $.ajax({
                    type: "POST",
                    url: "../tchildinoculate/update",
                    data: JSON.stringify(vm.tChildInoculate),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            $.ajax({
                                type: "POST",
                                url: "../provincePlatform/uploadPlat?childCode=" + vm.tChildInoculate.chilCode,//http://"$webPath/request/pagelist/SysChild/selectWithPageForList.jhtml?"+params,
                                dataType: "json",
                                success: function (result) {
                                    if (result.msg == "1") {
                                        layer.msg("保存成功");
                                        vm.showList = true;
                                        vm.reload();
                                    }
                                    else if (result.msg == "0") {
                                        layer.msg("保存失败");
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
                                    layer.msg("上传出现错误，请检查网络");
                                    /* layerFn.center(AppKey.code.code199, "没有查询到儿童相关信息!");*/
                                }
                            });
                            /* alert('操作成功', function (index) {
                                 vm.showList = true;
                                 vm.reload();
                             });*/
                        } else {
                            layer.msg("操作失败");
                        }
                    }
                });
                layer.close(index);
            });
        },
        reload: function (event) {
            vm.showList = true;
            //var page = $("#jqGrid").jqGrid('getGridParam','page');
            var formdata = $("#inocGatherForm").serialize();
            $("#jqGrid").jqGrid('clearGridData');  //清空表格
            $("#childInfoGrid").jqGrid('clearGridData');  //清空表格
            //var child_code = $('#child_code').val();
            layer.msg('正在加载,请稍候……', {icon: 16,shade: [0.5,'#f5f5f5'],scrollbar: false, time:100000}) ;
            $.ajax({
                type:"post",
                url :"../tchildinoculate/inoculateGather?page=1&limit=100&org_id="+orgId,
                data:formdata,
                success:function (data) {
                    layer.msg('加载完成！',{time: 1000});
                    var obj = data.page.list;
                    var totalInoTimes=0,fullCount=0,fullBioNameCount=0,fullInoDateCount=0;
                    var fullInocBatchnoCount = 0,fullInocCorpCodeCount = 0,fullInocInplIdCount = 0;
                    var fullInocPlaceCount = 0,fullPayStatusCount = 0,fullInocNurseCount = 0;
                    var inocIntegrityRate = 0,fullTimelyCount = 0,fullInocRoadCount = 0;
                    for(var i=0;i<obj.length;i++){
                        totalInoTimes=totalInoTimes + parseInt(obj[i].totalInoTimes);
                        fullCount=fullCount + parseInt(obj[i].fullCount);
                        fullBioNameCount=fullBioNameCount + parseInt(obj[i].fullBioNameCount);
                        fullInoDateCount=fullInoDateCount + parseInt(obj[i].fullInoDateCount);
                        fullInocBatchnoCount=fullInocBatchnoCount + parseInt(obj[i].fullInocBatchnoCount);
                        fullInocCorpCodeCount=fullInocCorpCodeCount + parseInt(obj[i].fullInocCorpCodeCount);
                        fullInocInplIdCount=fullInocInplIdCount + parseInt(obj[i].fullInocInplIdCount);
                        fullInocPlaceCount=fullInocPlaceCount + parseInt(obj[i].fullInocPlaceCount);
                        fullPayStatusCount=fullPayStatusCount + parseInt(obj[i].fullPayStatusCount);
                        fullInocNurseCount=fullInocNurseCount + parseInt(obj[i].fullInocNurseCount);
                        fullTimelyCount = fullTimelyCount + parseInt(obj[i].fullTimelyCount);
                        fullInocRoadCount = fullInocRoadCount + parseInt(obj[i].fullInocRoadCount);
                    }
                    if(totalInoTimes==0){
                        inocIntegrityRate = 0
                    }else{
                        inocIntegrityRate = (fullCount/totalInoTimes*100).toFixed(2);
                    }
                    obj.push({"totalInoTimes":totalInoTimes,"fullCount":fullCount,"fullBioNameCount":fullBioNameCount,"fullInoDateCount":fullInoDateCount,
                        "fullInocBatchnoCount":fullInocBatchnoCount,"fullInocCorpCodeCount":fullInocCorpCodeCount,"fullInocInplIdCount":fullInocInplIdCount,
                        "fullInocPlaceCount":fullInocPlaceCount,"fullPayStatusCount":fullPayStatusCount,"fullTimelyCount":fullTimelyCount,"fullInocRoadCount":fullInocRoadCount,
                        "fullInocNurseCount":fullInocNurseCount,"inocIntegrityRate":inocIntegrityRate,"bioName":"合计"});
                    $("#jqGrid").setGridParam({datatype : 'local',data: data.page.list}).trigger('reloadGrid');
                }
            })
        },
        //在册情况
        loadInfoStatusData:function(){
            var param = new Array();
            $("#childInfoStatus").empty();
            $("#childInfoStatus").selectpicker('refresh');
            $.ajax({
                url: '../child/listdiccode?ttype=child_info_status',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#childInfoStatus").append(con);
                    $("#childInfoStatus").selectpicker('refresh');
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
                    });
                    $("#chilCommittees").selectpicker('refresh');
                }
            });
        },
        //接种疫苗
        loadInoBios:function(){
            var param = new Array();
            $.ajax({
                url: '../child/listChildInoculate?org_id='+orgId,
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    var seldata = results.page.list;
                    if("undefined" != typeof vm){
                        vm.inoVacc = seldata;
                    }
                    $.each(seldata, function (i, n) {
                        $("#inoVacc").append(" <option value=\"" + n.bioCode + "\">" + n.bioCnSortname + "</option>");
                        $("#inocBactCode").append(" <option value=\"" + n.bioCode + "\">" + n.bioCnSortname + "</option>");
                    })
                    $("#inoVacc").selectpicker('refresh');
                }
            });
        },
        //户籍属性
        chilaccount:function(){
            var params = new Array();
            $("#chilAccount").empty();
            $("#chilAccount").selectpicker('refresh');
            $.ajax({
                url: '../child/listdiccode?ttype=movetype_code',
                dataType: "json",
                type: 'POST',
                success: function (results){
                    var con='';
                    for (var i = 0; i < results.data.length; i++){
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#chilAccount").html(con);
                    $("#chilAccount").selectpicker('refresh');
                }
            });
        },
        //居住属性
        residence:function(){
            var params = new Array();
            $("#chilResidence").empty();
            $("#chilResidence").selectpicker('refresh');
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
                    $("#chilResidence").selectpicker('refresh');
                }
            });
        },
        factory:function() {
            $.ajax({
                type:"post",
                url:"../tvacfactory/getAllData",
                dataType:'json',
                async: false,
                contentType:'application/json;charset=UTF-8',
                success: function (data) {
                    /* vm.factoryArr = data.page;*/
                    $.each(data.page, function (i, n) {
                        $("#inocCorpCode").append(" <option value=\"" + n.factoryCode + "\">" + n.factoryCnName + "</option>");
                    })
                }
            });
        },
        loadInoculateSiteData:function() {
            $.ajax({
                url: '../child/listdiccode?ttype=code_inoculation_site',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#inocInplId").append(con);
                }
            });
        },
    }
});

function bioNameFormatter(value, grid, rows, state){
    if(rows.totalInoTimes > rows.fullCount){
        if(rows.inocBactCode != null && rows.inocBactCode != undefined){
            return '<a href="javascript:selectChildInfo(\''+rows.inocBactCode+'\');">'+value+'</a>';
        }else{
            return value;
        }
    }else{
        return value;
    }
}

/**点击查询接种信息不完整明细儿童查询*/
function selectChildInfo(inoc_bact_code){
    var formdata = $("#inocGatherForm").serialize();
    $("#childInfoGrid").jqGrid('clearGridData');  //清空表格
    $("#childInfoGrid").jqGrid('setGridParam',{
        type:"post",
        url: '../child/viewInocChildInfo?inoc_bact_code='+inoc_bact_code+"&org_id="+orgId+"&"+formdata,
        data:formdata,
    }).trigger("reloadGrid");
}

function updateChildInoc(rowdata) {
    vm.update(rowdata.id);
}