$(function () {

    $("#jqGrid").jqGrid({
        url: '../child/currentDayInocChild',
        datatype: "json",
        colModel: [			
			{ label: '儿童编码', name: 'chilCode', width: 170, key: true },
			{ label: '姓名', name: 'chilName', width: 80 }, 			
			{ label: '性别', name: 'chilSex', width: 60 },
			{ label: '出生日期', name: 'chilBirthday', width: 150 },
			{ label: '出生体重（kg）', name: 'chilBirthweight', width: 100 },
			{ label: '民族', name: 'chilNatiId', width: 60 },
			{ label: '母亲姓名', name: 'chilMother', width: 80 }, 			
			{ label: '父亲姓名', name: 'chilFather', width: 80 },
            { label: '家庭电话', name: 'chilTel', width: 80 },
            { label: '手机', name: 'chilMobile', width: 100 },
			{ label: '行政村/居委会', name: 'chilCommittee', width: 80 },
			{ label: '户籍地址', name: 'chilHabiaddress', width: 180
			},
			{ label: '现居地址', name: 'chilAddress', width: 180
			},

			{ label: '备注', name: 'remark', width: 80 }			
        ],
		viewrecords: true,
        height: 220,
        rowNum: 30,
        sortname: 'chil_birthday',
        sortable:true,
        sortorder:'desc',
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 35,
        autowidth:true,
		shrinkToFit:false,
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
            vm.reset();
        	//隐藏grid底部滚动条
        	/* */
			/*$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });*/
        },
		onSelectRow:function(rowid,iRow,iCol,e){

            var rowData = $("#jqGrid").jqGrid('getRowData',rowid);
            $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + rowid+"&birthday="+rowData.chilBirthday);
            vm.q.chilName = rowData.chilName;


		}
    });
    //接种记录查询结果
    $("#historyTable").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id',key:true,hidden:true, width: 150 },
            { label: '疫苗名称', name: 'inocBactCode', width: 150,align:'center' },
            { label: '批号', name: 'inocBatchno', width: 100,align:'center' },
            { label: '剂次', name: 'inocTime', width: 40,align:'center' },
            { label: '接种属性', name: 'inocProperty', width: 80,align:'center' },
            { label: '接种日期', name: 'inocDate', width: 150,align:'center' },
            { label: '接种部位', name: 'inocInplId', width: 80,align:'center' },
            { label: '接种单位', name: 'inocDepaCode', width: 150,align:'center' },
            /* { label: '疫苗失效期', name: 'inocValiddate', width: 85 },*/
            { label: '生产企业', name: 'inocCorpCode', width: 80,align:'center' },
            { label: '是否免费', name: 'inocFree', width: 70,align:'center',
                formatter:function (value) {
                    if(value == 1){
                        return "免费";
                    }else if(value == 0){
                        return "收费";
                    }else{
                        return "";
                    }
                }
            },

            { label: '接种护士', name: 'inocNurse', width: 80,align:'center' }
            /*  { label: '备注', name: 'remark', width: 80 }*/
        ],
        viewrecords: true,
        height: 255,
        rowNum: 40,
        rowList : [40,50,80],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
         multiselect: true,
        //pager: "#inoculationGridPager",
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
            var rows =  $("#historyTable").jqGrid('getRowData');
            var row =  $("#historyTable").getDataIDs();
            for(var i = 0; i<rows.length;i++){
                var current_year = new Date().getFullYear();
                var current_month = new Date().getMonth()+1;
                var current_day = new Date().getDate();
                var currentDay = current_year +"-"+ current_month +"-"+ current_day;
                var inocDate = rows[i].inocDate;
                var inoculate_year = new Date(inocDate).getFullYear();
                var inoculate_month = new Date(inocDate).getMonth()+1;
                var inoculate_day = new Date(inocDate).getDate();
                var inoculate_date = inoculate_year +"-"+ inoculate_month +"-"+ inoculate_day;
                if(currentDay==inoculate_date){
                    //$('#historyTable').jqGrid('setSelection',i,true);
                    $("#historyTable").setSelection(row[i]);
                }

            }

            //隐藏grid底部滚动条
            $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vm.chilBirthday();//出生日期
    vm.loadCommiteeData();//行政村
    vm.loadHospitalData();//医院
    vm.loadPrintModel();
    vm.loadInfoStatusData();//个案状态
    $("#chilBirthdayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.chilBirthdayEnd = $("#chilBirthdayEnd").val();
    });
    $("#chilBirthdayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.chilBirthdayStart = $("#chilBirthdayStart").val();
    });

vm.pinyincommsreach();
});


var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			chilCode: null,
			chilName:null,
			chilCommittee :null,
			chilBirthdayStart:null,
			chilBirthdayEnd:null,
			chilHere:null,
            flag:true

		},
		showList: true,
		title: null,
		tChildInfo: {},
        chilCommittee:null
	},
	methods: {
		query: function () {
           vm.reload();
		},
        reset:function () {
            vm.q = {
                chilCode: null,
                chilName:null,
                chilCommittee :null,
                chilBirthdayStart:null,
                chilBirthdayEnd:null,
                chilHere:null,
                flag:true,
                isUpload: null

            }
        },
		reload: function (event) {
            vm.showList = true;

            var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
            $.each(postData, function (k, v) {
                delete postData[k];
            });
            Vue.set(vm.q, 'chilCommittee', $("#chilCommittees").val());
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:vm.q,
                page:page
            }).trigger("reloadGrid");
                if($("#chilForm").data('bootstrapValidator')!=null) {
                    $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
                }
            $("#chilCommittees").selectpicker('val',"");
		},
        //民族
        loadNationData:function(){
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
        },
        //行政村
        loadCommiteeData:function(){
            var param = new Array();
            $.ajax({
                url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    if("undefined" != typeof vm){
                        vm.chilCommittee =  results.page.list;
                    }
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].code + '">'+results.page.list[i].name+'</option>';
                    }
                    $("#chilCommittee").html(con);
                    $("#chilCommittees").append(con);
                    $("#chilCommittees").selectpicker('refresh');

                }
            });
        },
        pinyincommsreach:function(){
            //初始化下拉框
            $('#chilCommittees').selectpicker({
                'selectedText': 'cat',
                //noneSelectedText:'请选择行政村/居委会',
                // actionsBox:true,
                search:false,
            });
            //console.log($("#chilCommittees").next().find('.bs-searchbox'));
            // 行政村/居委会拼音搜索
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


                }
            });
        },

        //医院
        loadHospitalData:function(){
            //初始化下拉框
            $('.selectpicker').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择出生医院...',
                actionsBox:true
            });
            var param = new Array();
            $.ajax({
                url: '../tbasehospital/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].hospitalCode + '">'+results.page.list[i].hospitalName+'</option>';
                    }
                    $("#chilBirthhospitalname").append(con);
                    $("#chilBirthhospitalname").selectpicker('refresh');

                }
            });
        },
        chilBirthday:function(){
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
                vm.tChildInfo.chilBirthday = $("#chilBirthday").val();
                console.log($("#chilBirthday").val());
            }).on('hide',function(e) {
                $('#chilForm').data('bootstrapValidator')
                    .updateStatus('chilBirthday', 'NOT_VALIDATED',null)
                    .validateField('chilBirthday');
            });
       /* .on('hide',function(e) {
                $('#chilForm').data('bootstrapValidator')
                    .updateStatus('chilBirthday', 'NOT_VALIDATED',null)
                    .validateField('chilBirthday');
            });*/

        },
        chilCreatedate:function(){
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
        },

        //户籍属性
        move:function(){
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
                }
            });
        },

        //居住属性
        residence:function(){
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
                }
            });
        },
        print:function(){
            var chilCode = getSelectedRow_child();
            if(chilCode == undefined || chilCode == null){
                layer.msg("请选择儿童！");
                return ;
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
        printCard:function () {
            var chilCode = getSelectedRow_child();
            if(chilCode == undefined || chilCode == null){
                layer.msg("请选择儿童！");
                return ;
            }
            layer.open({
                title: '打印选择',
                skin: 'printDialog',
                type: 1,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['580px', '480px'],
                content: $("#printCardDialog")
            });
        },
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
        upload:function(){
            var ids = getSelectedRows("jqGrid");
            layer.confirm('确定要上传么？', function(index) {
                layer.msg('上传中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 1000000});
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
        uploadAll:function(){
           /* var ids = getSelectedRows("jqGrid");*/
            layer.confirm('确定要上传么？', function(index) {
                layer.msg('上传中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 1000000});
                $.ajax({
                    type: "POST",
                    url: "../provincePlatform/uploadPlatAll",//http://"$webPath/request/pagelist/SysChild/selectWithPageForList.jhtml?"+params,
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
        uploadRecord:function () {
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '上传记录',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: [widthNum, '700px'],
                content: '../child/uploadRecord.html'
            });

        }

	}
});
//选择一条记录
function getSelectedRow_child() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
   /* if(!rowKey){
        alert("请选择一条记录");
        return ;
    }*/

    return rowKey;
}

//选择一条记录
function getSelectedRows_child() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
     if(!rowKey){
         alert("请选择一条记录");
         return ;
     }

    return  grid.getGridParam("selarrrow");
}
//选择一条记录
function getSelectedRow_ino() {

    var grid = $("#inoculation_iframe").contents().find("#inoculationGrid");
    //$("#ifm").contents().find("#inoculationGrid").click();//jquery 方法1
    var rowKey = grid.getGridParam("selrow");

    return rowKey;
}

//打印接种记录
function printInoculation(printType,xmlName) {
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
    if(child_id == undefined || child_id == null || child_id == ""){
        layer.msg("请选择儿童！");
        return ;
    }
   /* var id = getSelectedRow_ino();
    if(id == undefined || id == null || id == ""){
        layer.msg("请选择接种记录！");
        return ;
    }
    var xmlName = xmlName.toString();
    if(xmlName==''){
        xmlName = "selfdefineprintCard";
    }
    var inoculate_id = $("#historyTable").getGridParam("selarrrow");
    layer.closeAll();
    window.open("../reports/printChildInoculateByModel/?inoculate_id="+inoculate_id+"&xmlName=" + xmlName+"&num=1"+'&printType='+printType+"&child_id="+child_id).print();
*/
    //var mywin = window.open("../reports/printChildInoculateByModel?num="+num+"&version="+jrxml_name+"&chilCode="+chilCode+"&printType="+printType).print();

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
    //window.open("../reports/printchildinfoByModel?jrxml_name="+jrxml_name + "&chilCode=" + chilCode+"&printType="+printType);
}
//接种证打印儿童信息
function printChildInfo(printType,jrxml_name){

    var chilCode = getSelectedRow_child();
    if(chilCode == undefined || chilCode == null || chilCode == ""){
        layer.msg("请选择儿童！");
        return ;
    }

    layer.closeAll();
    window.open("../reports/printchildinfoByModel?jrxml_name="+jrxml_name + "&chilCode=" + chilCode+"&printType="+printType).print();


}
//接种卡新卡打印
function printChildInfoCard(num) {
    var jrxml_name = "selfdefineprintCard";
    var cardFontSize = $("#cardFontSize").val();
    var chilCode = getSelectedRow_child();
    if(chilCode == undefined || chilCode == null || chilCode == ""){
        layer.msg("请选择儿童！");
        return ;
    }
    if(cardFontSize == "" || isNaN(cardFontSize)){
        cardFontSize = 8;
    }
    layer.closeAll();

    window.open("../reports/printNewCard?num="+ num + "&chilCode=" + chilCode +"&jrxml_name=" + jrxml_name+"&fontSize="+cardFontSize).print();

}
