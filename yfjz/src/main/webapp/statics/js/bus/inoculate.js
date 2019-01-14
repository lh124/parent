var child = [];
var inocVM = new Vue({
    el: '#childrenBody',
    data: {
        message: {
            chilCode:null,
            name: '',
            cardNo: '',
            here: '',
            account: '',
            birthday: '',
            age:'',
            sex:'',
            habiaddress: '',
            montherName: '',
            fatherName:'',
            tel: '',
            mobile:'',
            address: '',
            remark:'',
            inoTime:'',
            allergyHistory:'',
            abnormalHistory: '',
            committee:'',
            taboo: '',
            infectionHistory: '',
        },
        inoculateArr: [],//点击完成注射时,待提交的数组
        sequenceNoId:null,//队列号的id
        childCode:null,//儿童编码
        childName:null,//儿童姓名
        recommend:[],//推荐队列
        inoculateSiteArr:[], //接种部位数组
        channelList:[], //接种途径数组
        inoculateVaccProperties :[],//接种属性
        flag:null
    },
    methods: {
        query: function () {
            inocVM.showList = true;
            inocVM.reload();
        },
        reload: function (event) {
            inocVM.showList = true;
            $.ajax({
                type:'post',
                url:'../child/list?page=1&limit=10',
                data:{'chilCode':inocVM.childCode},
                success:function (data) {
                    child = data.page.list;
                    var age = getAge(child[0].chilBirthday);
                    getTrouble(inocVM.childCode);
                    /*getAllergyHistory(inocVM.childCode);
                    getTaboo(inocVM.childCode);
                    getAbnormalHistory(inocVM.childCode);
                    getInfectionHistory(inocVM.childCode);*/
                    inocVM.message = {
                        name: child[0].chilName,
                        chilCode: child[0].chilCode,
                        cardNo: child[0].chilCardno,
                        account: child[0].chilAccount,
                        birthday: inocVM.dateFormatter(child[0].chilBirthday),
                        address: child[0].chilAddress,
                        montherName: child[0].chilMother,
                        fatherName: child[0].chilFather,
                        mobile:child[0].chilMobile,
                        tel: child[0].chilTel,
                        habiaddress:child[0].chilHabiaddress,
                        remark:child[0].remark,
                        age:age,
                        sex:child[0].chilSex,
                        here:child[0].chilHere,
                        committee: child[0].chilCommittee,
                        /* allergyHistory:rowData.chilSensitivity*/
                        /* taboo: '',
                         infectionHistory:'',
                         abnormalHistory: ''*/
                    }
                    sexCode();//性别
                    move1();//户籍属性
                }
            });

        },
        dateFormatter : function(value){
            var date = new Date(value);
            var year = date.getFullYear().toString();
            if(isNaN(year))return;
            var month = (date.getMonth() + 1);
            var day = date.getDate().toString();
            if (month < 10){
                month = "0" + month;
            }
            if (day < 10){
                day = "0" + day;
            }
            return year + "-" + month + "-" + day;
        },
        // 完成注射
        complete: function () {
            if(inocVM.flag=='false'){
                layer.msg("请呼叫队列进行注射！");
                return;
            }
            //
            var grid = $("#inoculateTable");
            endEdit(grid);
            var rowKey = grid.getGridParam("selrow");
            var rowData = grid.getRowData(rowKey);
            if(rowKey==null){
                layer.msg("请选择一行数据");
                return;
            }
            if(rowData.batchnum==undefined||rowData.batchnum==""||rowData.batchnum==null){
                layer.msg("还未领取疫苗或库存不足。请领取后操作")
                return;
            }
            if(rowData.batchnum=='其他台库存'){
                layer.msg("还未选择疫苗批号，请选择之后操作！")
                return;
            }



            inocVM.inoculateArr = [];//每次提交之前，清空上一次提交的数据
            //结束所有行编辑模式

            //获取选中行的id主键
            var selectRowIds = getSelectedRows("inoculateTable");
            if (selectRowIds.length > 1){
                layer.msg("每次只能注射一剂疫苗");
                return;
            }
            if((rowData.bioCode=='0311' || rowData.bioCode =='0302' || rowData.bioCode =='0305'|| rowData.bioCode =='0301')&& rowData.inoculateSiteCode!='9'){
                layer.msg("该疫苗为口服，请重选接种部位！！！");
                return;
            }
            if((rowData.bioCode=='0311' || rowData.bioCode =='0302' || rowData.bioCode =='0305'|| rowData.bioCode =='0301')&& rowData.channel!='4'){
                layer.msg("该疫苗为口服，请重选接种途径！！！");
                return;
            }
            //点击后禁用
            $("#complete").attr({"disabled":"disabled"});
            var id=$("#inoculateTable").jqGrid('getGridParam','selrow');
            var rowData = $("#inoculateTable").jqGrid('getRowData',id);
            rowData.sequenceNoId = inocVM.sequenceNoId;//队列号的id
            rowData.childCode = inocVM.childCode;//儿童编码
            rowData.childName = inocVM.childName;//儿童姓名
            if(rowData.ismf=='收费'){
                rowData.ismf=0;
            }else if(rowData.ismf=='免费'){
                rowData.ismf=1;
            }
            inocVM.inoculateArr.push(rowData);

            $.ajax({
                type: "POST",
                url: "../tchildinoculate/finished",
                data:JSON.stringify(inocVM.inoculateArr),
                contentType: 'application/json;charset=UTF-8',
                success: function (r) {
                    $("#complete").removeAttr("disabled");
                    if (r.code === 0) {
                        layer.msg('操作成功');
                        inocVM.reload();
                        //移除jqgrid指定行的数据
                        grid.delRowData(inocVM.inoculateArr[0].id);
                        //刷新接种列表
                        //刷新待接种记录列表
                        $("#currentDayWaitInocChild").clearGridData();
                        $("#currentDayInocChild").clearGridData();
                        var pageWait = $("#currentDayWaitInocChild").jqGrid('getGridParam','page');
                        $("#currentDayWaitInocChild").jqGrid('setGridParam',{
                            postData:{ },
                            page:pageWait
                        }).trigger("reloadGrid");
                        var page = $("#currentDayInocChild").jqGrid('getGridParam','page');
                        $("#currentDayInocChild").jqGrid('setGridParam',{
                            postData:{ },
                            page:page
                        }).trigger("reloadGrid");
                    } else {
                        layer.msg(r.msg);
                    }
                }
            });
        },
        printInoculation:function(){
            var chilCode = inocVM.childCode;
            if(chilCode == undefined || chilCode == null){
                layer.msg("请选择儿童");
                return ;
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
                    inocVM.loadPrintModel();
                    $("#lineCount").val("");
                    $("#currentInoculateVacc").jqGrid('clearGridData');
                    $("#currentInoculateVacc").jqGrid('setGridParam', {
                        postData: {},
                        url: '../tchildinoculate/getCurrentInoculateVacc?chilCode='+inocVM.childCode,
                        page: 1,
                    }).trigger("reloadGrid");
                }
            });
            //inocVM.loadPrintModel();
        },
        printCard: function () {
            var chilCode = inocVM.childCode;
            if(chilCode == undefined || chilCode == null){
                layer.msg("请选择儿童");
                return ;
            }
            layer.open({
                title: '打印选择',
                skin: 'printDialog',
                type: 1,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['700px', '550px'],
                content: $("#printCardDialog"),
                success: function () {
                    //表格只能在弹框之后加载，否则无效
                    //inocVM.loadPrintModel();
                    $("#cardLineCount").val("");
                    $("#currentInoculateVaccForCard").jqGrid('clearGridData');
                    $("#currentInoculateVaccForCard").jqGrid('setGridParam', {
                        postData: {},
                        url: '../tchildinoculate/getCurrentInoculateVacc?chilCode='+inocVM.childCode,
                        page: 1,
                    }).trigger("reloadGrid");
                }
            });
        },
        showHistoryInoculations:function(){
            var chilCode = inocVM.childCode;
            if(chilCode == undefined || chilCode == null){
                layer.msg("请选择儿童");
                return ;
            }
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '历史接种记录',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: [widthNum, '98%'],
                //content: $("#CurrentInocChild")
                content:'../child/tchildinoculate.html?chilCode='+inocVM.childCode+"&birthday="+$("#inobirthday").val()
            });
        },
        inoculationPj:function () {
            $.ajax({
                url: "http://localhost:8859/pjserver?pj=yfjz&callback=?",
                dataType:'jsonp',
                processData: false,
                type:'get',
                success: function(data){
                    $.ajax({
                        url: "../tbusevaluate/save",
                        data: {evaluateType:data,childId:inocVM.childCode},
                        dataType: "json",
                        async : false,
                        success: function(return_data){

                        },
                        error: function(){
                        }
                    });
                },
                error: function(data){
                }
            });
        },
        nextOrder:function () {
            var chilCode = inocVM.childCode;
            if(chilCode == undefined || chilCode == null){
                layer.msg("请选择儿童");
                return ;
            }
            layer.open({
                title: '下次预约',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['700px', '530px'],
                content: "../schedule/nextIno.html?childId="+chilCode
            });
        },
        /*loadPrintModel:function(){
            $.ajax({
                url: '../tchildprintmodel/list?orgid='+orgId+'&xml_name=selfdefineprintInoculate'+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].xmlName + '">'+results.page.list[i].modelName+'</option>';
                    }
                    $("#modelName").html(con);
                    $("#cardModelName").html(con);

                }
            });
        },*/
        loadPrintModel:function(){
            $.ajax({
                url: '../tchildprintmodel/list?orgid='+orgId+'&xml_name=selfdefineprint'+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var info='';
                    var inoc='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        if(results.page.list[i].xmlName.indexOf('selfdefineprintInfo')!=-1){
                            info+='<option  value="' + results.page.list[i].xmlName + '">'+results.page.list[i].modelName+'</option>';
                        }
                        if(results.page.list[i].xmlName.indexOf('selfdefineprintInoculate')!=-1) {
                            inoc += '<option  value="' + results.page.list[i].xmlName + '">' + results.page.list[i].modelName + '</option>';
                        }
                    }
                    $("#infoModelName").html(info);
                    $("#inocModelName").html(inoc);

                }
            });
        },
        // 退回登记台
        backToRegister: function () {

        },
        // 延后
        delay: function () {

        },
        // 呼叫
        invoke: function () {
            //通过ajax获取后台数据

        },
        // 暂停
        pause: function () {
            layui.use(['layer', 'form','jquery','common'], function(){
                var form = layui.form,
                    $ = layui.jquery,
                    common = layui.common,
                    layer = layui.layer;
                layer.msg("暂停");
            });
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

        }

    }
});

//监听转台状态，然后清空接种界面登记的疫苗列表
inocVM.$on("transfer",function(){
    /*$("#currentDayWaitInocChild").trigger("reloadGrid");*/
    var grid = $("#inoculateTable");
    grid.clearGridData();
    //$("#currentDayWaitInocChild").jqGrid('clearGridData');
    setTimeout(function () {
        $("#currentDayWaitInocChild").clearGridData();
        $("#currentDayWaitInocChild").trigger("reloadGrid");
    },500)

    // grid.jqGrid("clearGridData");
});

//监听接收台状态，然后清空接种界面登记的疫苗列表
inocVM.$on("reciverTransfer",function(){
    //$("#currentDayWaitInocChild").jqGrid('clearGridData');
    setTimeout(function () {
        $("#currentDayWaitInocChild").clearGridData();
        $("#currentDayWaitInocChild").trigger("reloadGrid");
    },500)
    // grid.jqGrid("clearGridData");
});

inocVM.$on("registerFinish",function(){
    //$("#currentDayWaitInocChild").jqGrid('clearGridData');
    setTimeout(function () {
        $("#currentDayWaitInocChild").clearGridData();
        $("#currentDayWaitInocChild").trigger("reloadGrid");
    },500)
    // grid.jqGrid("clearGridData");
});


inocVM.$on("callingNumber",function(queue){
    inocVM.flag = 'true';
    inocVM.sequenceNoId = queue.id;
    //重新reload三个列表
    inocVM.childCode = queue.childCode;
    inocVM.childName = queue.childName;
    //加载推荐列表
    var page = $("#inoculateTable").jqGrid('getGridParam','page');
    $("#inoculateTable").jqGrid('setGridParam',{
        postData:{ },
        url: "../tbusregister/getTodayRegisterList?childCode="+inocVM.childCode,
        page:page
    }).trigger("reloadGrid");
    //加载历次接种记录列表
    var page = $("#historyTable").jqGrid('getGridParam','page');
    $("#historyTable").jqGrid('setGridParam',{
        postData:{ },
        url: '../tchildinoculate/list?limit=1000&page=1&chilCode='+inocVM.childCode,
        page:page
    }).trigger("reloadGrid");
    inocVM.reload();
    setTimeout(function () {
        $("#currentDayWaitInocChild").clearGridData();
        $("#currentDayWaitInocChild").trigger("reloadGrid");
    },500)


});
inocVM.$on("unSelectQueue", function (queue) {
    if(inocVM.sequenceNoId == queue.id){
        inocVM.sequenceNoId=null;
        inocVM.message ={};
        jQuery("#inoculateTable").jqGrid("clearGridData");
    }
})
inocVM.$on("registerFinish",function(){
    //$("#currentDayWaitInocChild").jqGrid('clearGridData');
    setTimeout(function () {
        Registrationmonitoring();
    },500)
    // grid.jqGrid("clearGridData");
});
$(function () {
    loadRecommendVaccTable();//初始化加载推荐疫苗列表
    loadInoculateSiteData();//初始化时加载接种部位到全局数组中
    loadChannel();//添加接种途径
    inocVM.loadPrintModel();
    loadCurrentDayInocChild();//今日已接种儿童
    loadCurrentDayWaitInocChild();//今日待接种儿童
    formatStr();

    Registrationmonitoring();
    $("#myTab li").on("click",function () {
        var current=$(this).find("a").html();
        if(current=='领取疫苗'){
            // $("#receive_iframe").attr('src', '../bus/receviceVaccine.html');
            document.getElementById('receive_iframe').contentWindow.location.reload(true);
        }else if(current=='归还疫苗'){
            $("#return_iframe").attr('src', '../bus/returnVaccine.html');
        }else if(current=='儿童接种'){
            $("#inoculateTable").jqGrid('resetSelection');
            endEdit($("#inoculateTable"));
        }
    })

    $("#currentInoculateVacc").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', key: true, hidden: true, width: 1},

            {label: '接种疫苗', name: 'bio_name', width: 180,align:'center'},
            {label: '剂次', name: 'inoc_time', width: 40,align:'center'},
            {label: '接种日期', name: 'inoculate_date', width: 100,align:'center',
                formatter:function (value) {
                    return value.substring(0,10);
                }
            },
            {label: '接种部位', name: 'text', width: 80,align:'center'},
            {label: '疫苗批号', name: 'batchnum', width: 110,align:'center'},
            {label: '生产厂家', name: 'factory_name', width: 110,align:'center'}
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
    $("#currentInoculateVaccForCard").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', key: true, hidden: true, width: 1},
            {label: '接种疫苗', name: 'bio_name', width: 180,align:'center'},
            {label: '剂次', name: 'inoc_time', width: 40,align:'center'},
            {label: '接种日期', name: 'inoculate_date', width: 100,align:'center',
                formatter:function (value) {
                    return value.substring(0,10);
                }
            },
            {label: '接种部位', name: 'text', width: 80,align:'center'},
            {label: '疫苗批号', name: 'batchnum', width: 110,align:'center'},
            {label: '生产厂家', name: 'factory_name', width: 110,align:'center'}
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
        },
        gridComplete: function () {

            //隐藏grid底部滚动条
            // $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    updateloadCommiteeData();//行政村
    $("#inosex").css({"display": "none"});
    // $("#inohere").css({"display": "none"});
    $("#inoaccount").css({"display": "none"});
    $("#updatachilcommittees").css({"display": "none"});

});

//初始化时，加载接种部位
function loadInoculateSiteData() {
    $.ajax({
        type:"post",
        url:"../sys/dict/maplist",
        dataType:'json',
        async: false,
        contentType:'application/json;charset=UTF-8',
        success: function (data) {

            inocVM.inoculateSiteArr = data.list[0];
            inocVM.inoculateVaccProperties = data.list[1];
        }
    });

}
//加载接种途径
function loadChannel() {
    var param = new Array();//定义数组
    $.ajax({
        // get请求地址
        url: '../sys/dict/typeList',
        dataType: "json",
        type: 'POST',
        data: {"type": "code_inoculation_route"},
        success: function (data) {
            var result = data.page;
            $.each(result, function (index, item) {
                param.push({"text": item.text, "value": item.value});
            });
            inocVM.channelList= param;
        }
    });
}

function loadRecommendVaccTable(){
    //登记表格
    $("#inoculateTable").jqGrid({
        url: "../tbusregister/getTodayRegisterList?childCode="+inocVM.childCode,
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true ,hidden:true},
            { label: '儿童编号', name: 'childCode', width: 120,hidden:true },
            { label: '疫苗分类', name: 'className', width: 120,hidden:true },
            { label: '疫苗名称', name: 'bioName', width: 150,align:"center" ,
                formatter: function (cellValue, options, rowdata) {
                    return "<label>"+cellValue+"</label>";
                }
            },
            { label: '疫苗编码', name: 'bioCode', width: 120,hidden:true },
            { label: '接种部位', name: 'inoculateSiteCode', width: 100 ,align:"center",
                formatter: function(cellValue, options, rowdata) {
                    if (cellValue == 1 || cellValue == '左上臂') {
                        return "<label>左上臂</label>";
                    } else if (cellValue == 2 || cellValue == '右上臂') {
                        return "<label>右上臂</label>";
                    } else if (cellValue == 4 || cellValue == '左大腿') {
                        return "<label>左大腿</label>";
                    } else if (cellValue == 5 || cellValue == '右大腿') {
                        return "<label>右大腿</label>";
                    } else if (cellValue == 7 || cellValue == '左臀部') {
                        return "<label>左臀部</label>";
                    } else if (cellValue == 8 || cellValue == '右臀部') {
                        return "<label>右臀部</label>";
                    } else if (cellValue == 9 || cellValue == '口服') {
                        return "<label>口服</label>";
                    } else {
                        return "";
                    }
                },
                unformat:Unformat_Select
            },
            { label: '接种途径', name: 'channel', width: 100 ,align:"center",
                formatter: function(cellValue, options, rowdata) {
                    if (cellValue == 1 || cellValue == '肌内') {
                        return "<label>肌内</label>";
                    } else if (cellValue == 2 || cellValue == '皮下') {
                        return "<label>皮下</label>";
                    } else if (cellValue == 3 || cellValue == '皮内') {
                        return "<label>皮内</label>";
                    } else if (cellValue == 4 || cellValue == '口服') {
                        return "<label>口服</label>";
                    } else if (cellValue == 5 || cellValue == '其他') {
                        return "<label>其他</label>";
                    }else{
                        return "";
                    }
                },
                unformat:Unformat_Select
            },
            { label: '剂次', name: 'doseNo', width: 40,align:"center" },
            { label: '批号', name: 'batchnum', width: 150,align:"center" },
            { label: '失效期', name: 'expiryDate', width: 80,align:"center" },
            { label: '接种属性', name: 'inocProperty', width: 100,align:"center",
                formatter: function(cellValue, options, rowdata) {
                    if (cellValue == 0 || cellValue == '基础' || cellValue == undefined) {
                        return "<label>基础</label>";
                    } else if (cellValue == 1 || cellValue == '加强') {
                        return "<label>加强</label>";
                    } else if (cellValue == 2 || cellValue == '强化') {
                        return "<label>强化</label>";
                    } else if (cellValue == 3 || cellValue == '应急') {
                        return "<label>应急</label>";
                    }
                },
                unformat:Unformat_Select},//基础，加强，强化，应急
            { label: '是否收费', name: 'ismf', width: 70,align:"center",
                formatter: function(cellValue, options, rowdata) {
                    if (cellValue == 1){
                        return "<label>免费</label>";
                    } else {
                        return "<label>收费</label>";
                    }
                },unformat:function( cellvalue, options, cell){
                    return cellvalue;
                }
            },
            { label: '生产企业', name: 'factoryName', width: 120,align:"center" },
            { label: '生产企业编码', name: 'factoryCode', width: 120,hidden:true },
            { label: '登记医生', name: 'doctorName', width: 80,align:"center" },
            { label: '登记时间', name: 'createTime', width: 150,align:"center" },
            { label: '登记台', name: 'towerName', width: 120,align:"center" },
            { label: '登记台ID', name: 'fkPosId', width: 120,hidden:true },
            { label: '电子监管码录入', name: 'inocRegulatoryCode', width: 120,align:"center" },
            { label: '登记医生', name: 'doctorId', width: 120,hidden:true },
            { label: '仓库Id', name: 'storeId', width: 120,hidden:true },
            { label: '备注', name: 'remark', width: 120,align:"center" }
        ],
        viewrecords: true,
        height: 220,
        rowNum: 30,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
        // beforeSelectRow: beforeSelectRow,
        shrinkToFit:false,
        autoScroll: true,
        multiselect: true,
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
        onSelectRow:function (rowid,status) {

            if (!status) return;//如果是取消选中，则不开启编辑模式
            endEdit($("#inoculateTable"));
            selectBio();
            var str = "";
            $.each(inocVM.inoculateSiteArr, function (index, item) {
                str = str + item.value + ":" + item.text + ";";
            });
            str = str.substring(0, str.length - 1);
            $('#inoculateTable').setColProp('inoculateSiteCode', {
                editable: true, edittype: "select",editoptions: {buildSelect: null, dataUrl: null, value: str}
            });
            var cha="";
            $.each(inocVM.channelList, function (index, item) {
                cha = cha + item.value + ":" + item.text + ";";
            });
            cha = cha.substring(0, cha.length - 1);
            $('#inoculateTable').setColProp('channel', {
                editable: true, edittype: "select",editoptions: {buildSelect: null, dataUrl: null, value: cha}
            });
            //查询工作台领取的疫苗批号
            var data=$('#inoculateTable').jqGrid("getRowData",rowid);
            //绑定change事件
            var noPass;
            $.ajax({
                type:"get",
                url:"../receive/queryBatchNum",
                data:{"code":data.bioCode,"type":"remove"},
                async:false,
                success:function (ret) {
                    console.log(ret)
                    var nowData=ret.data;
                    if(ret.data.length<=0){
                        // noPass="pass";
                       layer.msg("还未领取疫苗或库存不足。请领取疫苗或使用其他台领取的疫苗！")
                       var  defaultString="':;other:其他台库存";
                        selectBatchnum(defaultString,rowid,nowData);
                        return;
                    }else{
                        var batchStr="";
                        $.each(ret.data, function (index, item) {
                            batchStr = batchStr + item.batchnum + ":" +item.batchnum+";";
                        });
                        batchStr = batchStr+"other:其他台库存";
                        var rows=ret.data[0];
                        $("#inoculateTable").setRowData(rowid,{"batchnum":rows.batchnum,"expiryDate":rows.expiryDate,"factoryName":rows.factoryName,"factoryCode":rows.factoryCode,"ismf":rows.ismf});
                        selectBatchnum(batchStr,rowid,nowData);
                    }
                }
            })
            if(noPass!=undefined&&noPass=="pass"){
                return;
            }
            $("#" + rowid+'_batchnum').on("change",function(){
                console.log(123);
            })
            //接种属性
            var vaccProstr = "";
            $.each(inocVM.inoculateVaccProperties, function (index, item) {
                vaccProstr = vaccProstr + item.value + ":" + item.text + ";";
            });
            vaccProstr = vaccProstr.substring(0, vaccProstr.length - 1);

            $('#inoculateTable').setColProp('inocProperty', {
                editable: true, edittype: "select",editoptions: {buildSelect: null, dataUrl: null, value: vaccProstr}
            });

            //电子监管码
            $('#inoculateTable').setColProp('inocRegulatoryCode', {
                editable: true
            });
            //开启编辑
            $("#inoculateTable").editRow(rowid);
        },
        loadComplete:function (data) {
            if(data.hb && data.hb != ''){
                layer.msg(data.hb);
            }
        }
        /*gridComplete:function(){
            //隐藏grid底部滚动条
            $("#inoculateTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }*/
    });
}


//今日待接种儿童查询结果
function loadCurrentDayWaitInocChild(){
    $("#currentDayWaitInocChild").jqGrid({
        url: '../child/currentDayWaitInocChild?towerId='+towerId,
        datatype: "json",
        colModel: [
            { label: '排队号', name: 'createUserId', width: 70,key: true,align:"center" },
            { label: '儿童编码', name: 'chilCode', width: 170, align:"center" },
            { label: '姓名', name: 'chilName', width: 80,align:"center" },
            { label: '性别', name: 'chilSex', width: 60,align:"center" },
            { label: '出生日期', name: 'chilBirthday', width: 120,align:"center",
                formatter:function (value) {
                    return value.substring(0,10);
                }
            },
            /* { label: '出生体重（kg）', name: 'chilBirthweight', width: 120 },*/
            /*{ label: '民族', name: 'chilNatiId', width: 60 },*/
            { label: '母亲姓名', name: 'chilMother', width: 80,align:"center" },
            { label: '父亲姓名', name: 'chilFather', width: 80,align:"center" },
            { label: '家庭电话', name: 'chilTel', width: 80,align:"center" },
            { label: '手机', name: 'chilMobile', width: 100,align:"center" },
            { label: '行政村/居委会', name: 'chilCommittee', width: 80,align:"center" },
            { label: '户籍地址', name: 'chilHabiaddress', width: 180,align:"center" ,
                formatter:function (value,row,index) {
                    try{
                        return JSON.parse(value).position;
                    }catch(e) {
                        return value;
                    }
                }
            },
            { label: '现居地址', name: 'chilAddress', width: 180,align:"center",
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

            { label: '备注', name: 'remark', width: 80,align:"center" }
        ],
        viewrecords: true,
        height: 220,
        rowNum: 20,
        rowList : [20,50,80],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        shrinkToFit:false,
        autoScroll: true,
        // multiselect: true,
        pager: "#currentDayWaitInocChildGridPager",
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
        onSelectRow:function(rowid, iRow, iCol, e){
            inocVM.flag = 'false';
            var rowData = $("#currentDayWaitInocChild").jqGrid('getRowData', rowid);
            inocVM.childCode = rowData.chilCode;
            var page = $("#inoculateTable").jqGrid('getGridParam','page');
            $("#inoculateTable").jqGrid('setGridParam',{
                postData:{ },
                url: "../tbusregister/getTodayRegisterList?childCode="+inocVM.childCode,
                page:page
            }).trigger("reloadGrid");
            inocVM.reload();
            $("#inosex").css({"display": "none"});
            // $("#inohere").css({"display": "none"});
            $("#inoaccount").css({"display": "none"});
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            //$("#historyTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
}
//今日已接种儿童查询结果
function loadCurrentDayInocChild(){
    $("#currentDayInocChild").jqGrid({
        url: '../child/currentDayInocChild?flag=false',
        datatype: "json",
        colModel: [
            { label: '排队号', name: 'createUserId', width: 70, key: true,align:"center"},
            { label: '儿童编码', name: 'chilCode', width: 170,align:"center" },
            { label: '姓名', name: 'chilName', width: 80,align:"center" },
            { label: '性别', name: 'chilSex', width: 60,align:"center" },
            { label: '出生日期', name: 'chilBirthday', width: 120,align:"center",
                formatter:function (value) {
                    return value.substring(0,10);
                }
            },
           /* { label: '出生体重（kg）', name: 'chilBirthweight', width: 120 },*/
            /*{ label: '民族', name: 'chilNatiId', width: 60 },*/
            { label: '母亲姓名', name: 'chilMother', width: 80,align:"center" },
            { label: '父亲姓名', name: 'chilFather', width: 80,align:"center" },
            { label: '家庭电话', name: 'chilTel', width: 80,align:"center" },
            { label: '手机', name: 'chilMobile', width: 100,align:"center" },
            { label: '行政村/居委会', name: 'chilCommittee', width: 80,align:"center" },
            { label: '户籍地址', name: 'chilHabiaddress', width: 180 ,align:"center",
                formatter:function (value,row,index) {
                    try{
                        return JSON.parse(value).position;
                    }catch(e) {
                        return value;
                    }
                }
            },
            { label: '现居地址', name: 'chilAddress', width: 180,align:"center",
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

            { label: '备注', name: 'remark', width: 80,align:"center" }
        ],
        viewrecords: true,
        height: 220,
        rowNum: 20,
        rowList : [20,50,80],
        rownumbers: true,
        rownumWidth: 25,
        align:'center',
        autowidth:true,
        shrinkToFit:false,
        autoScroll: true,
        // multiselect: true,
        pager: "#currentDayInocChildGridPager",
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
            //$("#historyTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
}
//打印儿童信息
function printChildInfo(printType, jrxml_name) {
    var chilCode = inocVM.childCode;
    if (chilCode == undefined || chilCode == null || chilCode == "") {
        layer.msg("请选择一个儿童！");
        return;
    }
    layer.closeAll();
    window.open("../reports/printchildinfoByModel?jrxml_name=" + jrxml_name + "&chilCode=" + chilCode + "&printType=" + printType).print();
    setTimeout(window.close, 0);
}

/**
 * 打印今日接种记录
 * @param num
 * @param printType
 * @param jrxml_name
 */
function printCurrentDayInoculation(num,printType,jrxml_name) {
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
    var chilCode = inocVM.childCode;
    if(chilCode == undefined || chilCode == null || chilCode == ""){
        layer.msg("请选择一个儿童！");
        return ;
    }
    var inoculate_id = $("#currentInoculateVacc").getGridParam("selarrrow");
    var inoculate_id2 = $("#currentInoculateVaccForCard").getGridParam("selarrrow");
    var jrxml_name = jrxml_name.toString();
    if (jrxml_name == '') {
        fontSize = cardFontSize;
        jrxml_name = "selfdefineprintCard";
    }
    var lineCounts = [];
    if(inoculate_id!=null && inoculate_id.length!=0&& lineCount!="" && !isNaN(lineCount)){
        if ($("#lineCountCheck").is(':checked')){
            lineCount=parseInt(lineCount)+21;
        }
        layer.closeAll();
        window.open("../reports/printSecondVaccBylineCount/?lineCount=" + lineCount+"&inoculate_id="+inoculate_id+ "&num=1" + '&printType=' + printType + "&child_id=" + chilCode+"&plag=inoculate"+"&fontSize="+fontSize).print();
    }else if(inoculate_id2!=null && inoculate_id2.length!=0&& cardLineCount!="" && !isNaN(cardLineCount)){
        layer.closeAll();
        window.open("../reports/printSecondVaccBylineCount/?lineCount=" + cardLineCount+"&inoculate_id="+inoculate_id2+ "&num=1" + '&printType=' + printType + "&child_id=" + chilCode+"&plag=inoculate"+"&fontSize="+fontSize).print();
    }else if(printType.toString()=="2" && !(cardLineCount!="" && !isNaN(cardLineCount))){
        layer.closeAll();
        window.open("../reports/printChildInoculateByModel/?xmlName=" + jrxml_name + "&num=1" + '&printType=' + printType + "&child_id=" + chilCode+"&fontSize="+fontSize).print();
    }
    else {
        layer.closeAll();
        var mywin = window.open("../reports/printCurrentDayInoculateByModel?num=" + num + "&version=" + jrxml_name + "&chilCode=" + chilCode + "&printType=" + printType+"&fontSize="+fontSize).print();
    }

}

/**
 * 新卡打印
 */
function printChildInfoCard(num) {
    var jrxml_name = "selfdefineprintCard";
    var cardFontSize = $("#cardFontSize").val();
    var chilCode = inocVM.childCode;
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
function selectBio() {
    var grid = $("#inoculateTable");
    //获取选中行的id主键
    var selectRowIds = getSelectedRows("inoculateTable");
    if (selectRowIds!=null && selectRowIds.length > 0){
        $("#complete").removeAttr("disabled");
    }else{
        $("#complete").attr({"disabled":"disabled"});
    }
}

function single() {
    inocVM.showHistoryInoculations();

}

//关闭
function colsedialog() {
    $('#myModal').modal('hide');
}
//打印今日已接种儿童
function printCurrentDayInocChild(){
    var mywin = window.open("../reports/printCurrentDayInocChild?flag=false").print();

}
function printCurrentDayWaitInocChild() {
    var mywin = window.open("../reports/printCurrentDayWaitInocChild?towerId="+towerId).print();
}

function beforeSelectRow()
{
    $("#inoculateTable").jqGrid('resetSelection');
    return(true);
}

//异常反应
function noabnormal(){
    var chilCode = inocVM.childCode;
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= inocVM.childName;
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
    var chilCode = inocVM.childCode;
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= inocVM.childName;
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
    var chilCode = inocVM.childCode;
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= inocVM.childName;
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
    var chilCode = inocVM.childCode;
    if (chilCode==null|| chilCode=="" || chilCode=='undefined'){
        layer.msg("请选择儿童");
        return ;
    }
    var chilName= inocVM.childName;
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

function selectBatchnum(defaultString,rowid,nowData) {
    var data=$('#inoculateTable').jqGrid("getRowData",rowid);
    $('#inoculateTable').setColProp('batchnum', {
        editable: true, edittype: "select",editoptions: {buildSelect: null, dataUrl: null, value: defaultString, dataEvents:[
                {
                    type:'change',
                    fn:function(e){
                        var val= this.value;
                        $.ajax({
                            type:"get",
                            url:"../receive/queryOtherBatchNum",
                            data:{"code":data.bioCode},
                            // async:false,
                            success:function (ret) {
                                var vaccs=ret.data;
                                if(vaccs.length<1&&val=='other'){
                                    layer.msg("其他接种台没有领取该疫苗，请领取！")
                                    return;
                                }
                                if(val=='other'){
                                    layer.open({
                                        title: '其他接种台库存',
                                        // skin: 'tmpl',
                                        type: 1,
                                        closeBtn: 1,//取消右上角的x关闭按钮
                                        area: ['300px', '270px'],
                                        content: "<div><select id='otherStock' class='form-control' size='9' ></select></div>",
                                        btn: ['确定', '取消'],
                                        btn1: function (index) {
                                            var selVal=$("#otherStock option:selected").val();
                                            if(isEmpty(selVal)){
                                                layer.msg("请选择一个接种台的批号。")
                                                return;
                                            }
                                            $("#inoculateTable").saveRow(rowid);
                                            $.each(vaccs, function (index, item) {
                                                if(item.batchnum==selVal){
                                                    $("#inoculateTable").setRowData(rowid,{"batchnum":item.batchnum});
                                                    $("#inoculateTable").setRowData(rowid,{"expiryDate":item.expiryDate});
                                                    $("#inoculateTable").setRowData(rowid,{"factoryName":item.factoryName});
                                                    $("#inoculateTable").setRowData(rowid,{"factoryCode":item.factoryCode});
                                                    $("#inoculateTable").setRowData(rowid,{"ismf":item.ismf});
                                                    $("#inoculateTable").setRowData(rowid,{"storeId":item.storeId});
                                                }
                                            });
                                            layer.closeAll();
                                        },
                                        success:function (event) {
                                            $("#otherStock").empty();
                                            for(var i=0;i<vaccs.length;i++){
                                                var row=vaccs[i];
                                                var text=row.towerName+" 批号:"+row.batchnum;
                                                $("#otherStock").append("<option value='"+row.batchnum+"'>"+text+"</option>");
                                            }

                                        }

                                    });
                                }
                                else if(val!=""&&val!="other"){
                                    $.each(nowData, function (index, item) {
                                        if(item.batchnum==val){
                                            $("#inoculateTable").setRowData(rowid,{"expiryDate":item.expiryDate});
                                            $("#inoculateTable").setRowData(rowid,{"factoryName":item.factoryName});
                                            $("#inoculateTable").setRowData(rowid,{"factoryCode":item.factoryCode});
                                            $("#inoculateTable").setRowData(rowid,{"ismf":item.ismf});
                                            $("#inoculateTable").setRowData(rowid,{"storeId":''});
                                        }
                                    });
                                }
                            }
                        })
                    }
                }
            ]}
    });
    
}