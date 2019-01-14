$(function () {
    $("#childGatherGrid").jqGrid({
        url: '../child/childGather?org_id='+orgId,
        datatype: "json",
        colModel: [
			{ label: '年度', name: 'year', width: 60,key:true },
			{ label: '儿童总数', name: 'totalChild', width: 60 },
			{ label: '完整数', name: 'fullCount', width: 45 },
			{ label: '完整率(%)', name: 'integrityRate', width: 60 },
			{ label: '录入数', name: 'fullNameCount', width: 45,formatter:formatterChilName},
			{ label: '录入率', name: 'nameEntryRate', width: 45,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullNameCount / row.totalChild * 100).toFixed(2);
                    }
                }},
			{ label: '录入数', name: 'fullSexCount', width: 45 },
			{ label: '录入率', name: 'sexEntryRate', width: 45,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullSexCount / row.totalChild * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullBirthTimeCount', width: 45 },
            { label: '录入率', name: 'birthTimeEntryRate', width: 45,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullBirthTimeCount / row.totalChild * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullCreateSiteCount', width: 45,formatter:formatterCreateSite },
            { label: '录入率', name: 'createSiteEntryRate', width: 45,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullCreateSiteCount / row.totalChild * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullTelCount', width: 45 ,formatter:formatterChilTel},
            { label: '录入率', name: 'telEntryRate', width: 45 ,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullTelCount / row.totalChild * 100).toFixed(2);
                    }
                }},
            { label: '录入数', name: 'fullMothernameCount', width: 45 ,formatter:formatterParentName},
            { label: '录入率', name: 'mothernameEntryRate', width: 45 ,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullMothernameCount / row.totalChild * 100).toFixed(2);
                    }
                }},
			{ label: '录入数', name: 'fullHabiIdCount', width: 45 },
			{ label: '录入率', name: 'habiIdEntryRate', width: 45,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullHabiIdCount / row.totalChild * 100).toFixed(2);
                    }
                }},
			{ label: '录入数', name: 'fullHukouAddressCount', width: 45 ,formatter:formatterHabiAddress},
			{ label: '录入率', name: 'hukouAddressEntryRate', width: 45 ,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullHukouAddressCount / row.totalChild * 100).toFixed(2);
                    }
                }},
			{ label: '录入数', name: 'fullContactAddressCount', width: 45 ,formatter:formatterAddress},
			{ label: '录入率', name: 'contactAddressEntryRate', width: 45 ,
                formatter:function (value,index,row) {
                    if(row.totalChild==0){
                        return 0;
                    }else {
                        return (row.fullContactAddressCount / row.totalChild * 100).toFixed(2);
                    }
                }}

        ],
		viewrecords: true,
        height: 200,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#childGatherGridPager",
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
        	$("#childGatherGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    $("#childGatherGrid").jqGrid('setGroupHeaders', {
        useColSpanStyle: true,
        groupHeaders:[
            {startColumnName:'fullNameCount', numberOfColumns:2, titleText: '儿童姓名'},
            {startColumnName:'fullSexCount', numberOfColumns: 2, titleText: '性别'},
            {startColumnName:'fullBirthTimeCount', numberOfColumns: 2, titleText: '出生日期'},
            {startColumnName:'fullCreateSiteCount', numberOfColumns: 2, titleText: '建证单位'},
            {startColumnName:'fullTelCount', numberOfColumns: 2, titleText: '联系电话'},
            {startColumnName:'fullMothernameCount', numberOfColumns: 2, titleText: '家长姓名'},
            {startColumnName:'fullHabiIdCount', numberOfColumns: 2, titleText: '户口县国标'},
            {startColumnName:'fullHukouAddressCount', numberOfColumns: 2, titleText: '户口地址'},
            {startColumnName:'fullContactAddressCount', numberOfColumns: 2, titleText: '通讯地址'}
        ]
    });

    $("#childInfoGrid").jqGrid({
        //url: '../recommend/inoNote',
        datatype: "json",
        colModel: [
            { label: '儿童编号', name: 'chilCode', width: 190,key:true,
                formatter:function updateChildFormatter(cellValue, options, rowdata, action){
                    /*return   "<a href=\"javascript:void(0);\" onclick=\"updateChild(" + cellValue + ")\">"+cellValue+"</a>";*/
                    return "<a href=\"javascript:void(0);\" onclick='updateChild("+JSON.stringify(rowdata).replace(/"/g, '&quot;')+")'>"+cellValue+"</a>";
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
            { label: '父亲姓名', name: 'chilFather', width: 80 },
            { label: '母亲姓名', name: 'chilMother', width: 80 },
            { label: '家庭电话', name: 'chilTel', width: 100 },
            { label: '手机号码', name: 'chilMobile', width: 100 },
            { label: '通讯地址', name: 'chilAddress', width: 220 },
            { label: '居委会/行政村', name: 'chilCommittee', width: 100 },
            { label: '在册情况', name: 'chilHere', width: 80 },
            { label: '居住属性', name: 'chilResidence', width: 80 },
            { label: '建卡日期', name: 'createCardTime', width: 100 ,
                formatter:function (value) {
                    if(value!=null){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }

                }
            },
            { label: '儿童身份证号', name: 'chilNo', width: 190 },
            { label: '建证单位', name: 'chilCreatesite', width: 100 },
            { label: '户口县国标', name: 'chilHabiId', width: 80 },
            { label: '户口地址', name: 'chilHabiaddress', width: 200 },
            { label: '现居地址', name: 'chilAddress', width: 180 },
            { label: '家长身份证', name: 'chilMotherno', width: 190 }
        ],
        viewrecords: true,
        height: 300,
        rowNum: 10,
        rowList: [10, 30, 50],
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


    $("#year_start,#year_end").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy',//显示格式
       // minView: "year",//设置只显示到年份
        initialDate: new Date(),
        forceParse:true,
        startView: '4',
        minView: '4',
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        childGatherVM.q.year_end = $("#year_end").val();
        childGatherVM.q.year_start = $("#year_start").val();
    });
    // dataproviceall();//省份加载
    // dataproviceall2();//省份加载
    childGatherVM.loadInfoStatusData();
});

var childGatherVM = new Vue({
	el:'#rrapp',
	data:{

		showList: true,
		title: null,
		tRuleHiv: {},
        q:{
            year_end:null,
            year_start:null,
            childInfoStatus:null

        },
        condition:{
            fullNameCount:null,
            fullTelCount:null,
            fullMothernameCount:null,
            fullHukouAddressCount:null,
            fullContactAddressCount:null,
            childInfoStatus:null

        },
        tChildInfo:{}
	},
	methods: {
		query: function () {
			childGatherVM.reload();
		},
        reset: function(){
			childGatherVM.q = {
                    year_end:null,
                    year_start:null

            }
		},
        update:function(event){
           /* childGatherVM.showList = false;
            childGatherVM.title = "修改";
            childGatherVM.getInfo(event)*/
            $("#chilLeaveremark").attr({"disabled":"disabled"});
            //vm.getInfo(chilCode);
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '修改',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ["98%", "98%"],
                content: '../child/childEdit.html?chilCode=' + event
            });
        },
        excel: function (event) {
           layer.confirm("确定要下载生成的报表文件吗？",function (index) {
                window.open("../ExcelController/IntegrityRate?year_end=" + childGatherVM.q.year_end + "&year_start=" + childGatherVM.q.year_start+"&org_id="+orgId);
               layer.close(index);
           });

		},
        print:function () {
		   /* $("#unprintdiv").hide();
            window.focus();
            window.print();
            $("#unprintdiv").show();*/
            layer.confirm("确定要打印报表文件吗？",function (index) {
                window.open("../reports/printchildGather?year_end=" + childGatherVM.q.year_end + "&year_start=" + childGatherVM.q.year_start+"&org_id="+orgId);
                layer.close(index);
            });

        },
		reload: function (event) {
			childGatherVM.showList = true;
			//var page = $("#jqGrid").jqGrid('getGridParam','page');
            var childInfoStatus=$("#infostatus").selectpicker('val');
            childGatherVM.q.childInfoStatus=null;
            if(childInfoStatus!=null&&childInfoStatus!=""&&childInfoStatus!=undefined){
                childGatherVM.q.childInfoStatus=childInfoStatus;
            }
            $("#childGatherGrid").jqGrid('clearGridData');  //清空表格
            layer.msg('努力中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
            $.ajax({
                type:"post",
                url :"../child/childGather?page=1&limit=100&org_id="+orgId,
                data:childGatherVM.q,
                success:function (data) {
                    layer.msg('加载完成！',{time: 1000});
                    var obj = data.page.list;
                    var totalChild=0,fullCount=0,fullNameCount=0,fullSexCount=0;
                    var fullBirthTimeCount = 0,fullTelCount = 0,fullMothernameCount = 0;
                    var fullHabiIdCount = 0,fullHukouAddressCount = 0,fullContactAddressCount = 0;
                    var integrityRate = 0,fullCreateSiteCount = 0;
                    for(var i=0;i<obj.length;i++){
                        totalChild=totalChild + parseInt(obj[i].totalChild);
                        fullCount=fullCount + parseInt(obj[i].fullCount);
                        fullNameCount=fullNameCount + parseInt(obj[i].fullNameCount);
                        fullSexCount=fullSexCount + parseInt(obj[i].fullSexCount);
                        fullBirthTimeCount=fullBirthTimeCount + parseInt(obj[i].fullBirthTimeCount);
                        fullTelCount=fullTelCount + parseInt(obj[i].fullTelCount);
                        fullMothernameCount=fullMothernameCount + parseInt(obj[i].fullMothernameCount);
                        fullHabiIdCount=fullHabiIdCount + parseInt(obj[i].fullHabiIdCount);
                        fullHukouAddressCount=fullHukouAddressCount + parseInt(obj[i].fullHukouAddressCount);
                        fullContactAddressCount=fullContactAddressCount + parseInt(obj[i].fullContactAddressCount);
                        fullCreateSiteCount=fullCreateSiteCount + parseInt(obj[i].fullCreateSiteCount);
                    }
                    if(totalChild==0){
                        integrityRate = 0
                    }else{
                        integrityRate = (fullCount/totalChild*100).toFixed(2);
                    }

                    obj.push({"totalChild":totalChild,"fullCount":fullCount,"fullNameCount":fullNameCount,"fullSexCount":fullSexCount,
                        "fullBirthTimeCount":fullBirthTimeCount,"fullCreateSiteCount":fullCreateSiteCount, "fullTelCount":fullTelCount,"fullMothernameCount":fullMothernameCount,
                        "fullHabiIdCount":fullHabiIdCount,"fullHukouAddressCount":fullHukouAddressCount,
                        "fullContactAddressCount":fullContactAddressCount,"integrityRate":integrityRate,"year":"合计"});
                    //$("#jqGrid").trigger("reloadGrid");
                    $("#childGatherGrid").setGridParam({datatype : 'local',data: data.page.list}).trigger('reloadGrid');
                }
            })
           /* $("#jqGrid").jqGrid('setGridParam',{
                url: '../child/childGather',
                page:page,
                postData : childGatherVM.q
            }).trigger("reloadGrid");*/
		},
        back:function () {
            childGatherVM.showList = true;
        },
        loadInfoStatusData: function () {
            var param = new Array();
            $("#infostatus").empty();
            $("#infostatus").selectpicker('refresh');
            $.ajax({
                url: '../child/listdiccode?ttype=child_info_status',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.data;
                    var con=""
                    for (var i=0;i<seldata.length;i++){
                        con+="<option value='"+seldata[i].value+"'>"+seldata[i].text+"</option>"
                    }
                    $("#infostatus").html(con);
                    $("#infostatus").selectpicker('refresh');
                }
            });
        }
	}
});

function showChilds(datatype,year) {
    selectChildInfo(datatype,year);
}

function formatterChilName(value, grid, rows, state){
    if(rows.totalChild>value){
        return '<a href="javascript:showChilds(\'fullNameCount\',\''+rows.year+'\');">'+value+'</a>';
    }else{
        return value;
    }
}
function formatterChilTel(value, grid, rows, state){
    if(rows.totalChild>value){
        return '<a href="javascript:showChilds(\'fullTelCount\',\''+rows.year+'\');">'+value+'</a>';
    }else{
        return value;
    }
}
function formatterCreateSite(value, grid, rows, state){
    if(rows.totalChild>value){
        return '<a href="javascript:showChilds(\'fullCreateSiteCount\',\''+rows.year+'\');">'+value+'</a>';
    }else{
        return value;
    }
}

function formatterParentName(value, grid, rows, state){
    if(rows.totalChild>value){
        return '<a href="javascript:showChilds(\'fullMothernameCount\',\''+rows.year+'\');">'+value+'</a>';
    }else{
        return value;
    }
}
function formatterHabiAddress(value, grid, rows, state){
    if(rows.totalChild>value){
        return '<a href="javascript:showChilds(\'fullHukouAddressCount\',\''+rows.year+'\');">'+value+'</a>';
    }else{
        return value;
    }
}
function formatterAddress(value, grid, rows, state){
    if(rows.totalChild>value){
        return '<a href="javascript:showChilds(\'fullContactAddressCount\',\''+rows.year+'\');">'+value+'</a>';
    }else{
        return value;
    }
}

function selectChildInfo(datatype,year) {

    if(datatype=='fullNameCount'){
        childGatherVM.condition={
            cname:"true",
            year:year,
            org_id:orgId
        }
    }
    if(datatype=='fullCreateSiteCount'){
        childGatherVM.condition={
            departname:"true",
            year:year,
            org_id:orgId
        }
    }
    if(datatype=='fullTelCount'){
        childGatherVM.condition={
            homephone:"true",
            child_mobile:"true",
            year:year,
            org_id:orgId
        }
    }
    if(datatype=='fullMothernameCount'){
        childGatherVM.condition={
            mothername:"true",
            year:year,
            org_id:orgId
        }
    }
    if(datatype=='fullHukouAddressCount'){
        childGatherVM.condition={
            hukouregioncode:"true",
            year:year,
            org_id:orgId
        }
    }
    if(datatype=='fullContactAddressCount'){
        childGatherVM.condition={
            familyregioncode:"true",
            year:year,
            org_id:orgId
        }
    }
    $("#childInfoGrid").jqGrid('clearGridData');  //清空表格
    $('#jqGrid').jqGrid('clearGridData');  //清空表格  数据
    var childInfoStatus=$("#infostatus").selectpicker('val');
    childGatherVM.condition.childInfoStatus=null;
    if(childInfoStatus!=null&&childInfoStatus!=""&&childInfoStatus!=undefined){
        childGatherVM.condition.childInfoStatus=childInfoStatus;
    }
    /*
    * 先清空条件
    * jqgrid postData setGridParam 调用多次时查询条件会累加
    */
    var postData = $('#childInfoGrid').jqGrid("getGridParam", "postData");
    $.each(postData, function (k, v) {
        delete postData[k];
    });
    var page = $("#childInfoGrid").jqGrid('getGridParam','page');
    $("#childInfoGrid").jqGrid('setGridParam',{
        postData:childGatherVM.condition,
        url: '../child/imperfectChild',
        page:page
    }).trigger("reloadGrid");
}

function updateChild(rowdata) {
    childGatherVM.update(rowdata.chilCode);
}
