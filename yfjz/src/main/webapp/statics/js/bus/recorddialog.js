//数据jqgrid表格
// var mydata = [
//
//         { inocNationcode: "乙肝疫苗HepB", inocTime: "1", inocBactCode: "1111", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: "" },
//         { inocNationcode: "乙肝疫苗HepB", inocTime: "2",  inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: "" },
//         { inocNationcode: "乙肝疫苗HepB", inocTime: "3", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "卡介苗BCG", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: "" },
//         { inocNationcode: "灭活脊灰IPV", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "二价建档脊灰活疫苗bopv", inocTime: "2", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "二价建档脊灰活疫苗bopv", inocTime: "3", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "二价建档脊灰活疫苗bopv", inocTime: "4", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "百白破疫苗DTaP", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "百白破疫苗DTaP", inocTime: "2", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "百白破疫苗DTaP", inocTime: "3", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "百白破疫苗DTaP", inocTime: "4", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "白破疫苗DT", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "麻风疫苗MR", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "麻腮风疫苗MMR", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "麻腮风疫苗MMR", inocTime: "2", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "麻腮疫苗MM", inocTime: "", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "麻疹疫苗MV", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "麻疹疫苗MV", inocTime: "2", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "A群流脑疫苗MPSV-A", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "A群流脑疫苗MPSV-A", inocTime: "2", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "A+C群流脑活疫苗MPSV-AC", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "A+C群流脑活疫苗MPSV-AC", inocTime: "2", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "乙脑减毒灭活疫苗JE-L", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "" ,inocCorpCode: "",inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "乙脑减毒灭活疫苗JE-L", inocTime: "2", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "乙脑灭活疫苗JE-i", inocTime: "1", inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "乙脑灭活疫苗JE-i", inocTime: "2",  inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "乙脑灭活疫苗JE-i", inocTime: "3",  inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//         { inocNationcode: "乙脑灭活疫苗JE-i", inocTime: "4",  inocBactCode: "", inocProperty: "", inocDate: "", inocInplId: "", inocDepaCode: "",inocCorpCode: "" ,inocFree: "",inocNurse: "",inocBatchno: "",name13: ""},
//     ],
//     grid = $("#jzbl_content");



$("#jzbl_content").jqGrid({
    datatype: "json",
    // data: mydata,
    colNames:['id','biocode','疫苗类','剂次','疫苗名称','接种日期','批次','接种部位','是否收费 ','生产厂家 ','接种单位','接种属性','接种医生','操作'],
    // colNames:['疫苗类','操作'],
    colModel:[
        { name: 'id', index: 'id', width: 10, align: 'center',key:true,hidden:true},
        { name: 'inocNationcode', index: 'inocNationcode', width: 10, align: 'center',hidden:true},
        { name: 'remark', index: 'remark', width: 150,align: 'center',
            cellattr: function(rowId, tv, rawObject, cm, rdata) {
                //合并列
                // if (Number(rowId)==4){
                //     return 'colspan=2'
                // }else if (Number(rowId)==13) {
                //     return 'colspan=2'
                // }else if (Number(rowId)==14) {
                //     return 'colspan=2'
                // }else if (Number(rowId)==17) {
                //     return 'colspan=2'
                // } else{
                //合并行单元格
                return 'id=\'remark' + rowId + "\'";
                // }
            }
        },
        { name: 'inocTime', index: 'inocTime', width: 100,align: 'center',editable: true,
            cellattr: function(rowId, tv, rawObject, cm, rdata) {
                //合并单元格
                return 'id=\'inocTime' + rowId + "\'";
            }
        },
        { name: 'inocBactCode', index: 'inocBactCode', width: 220, align: 'center',editable: true, edittype: 'select',editoptions:{value:gettypes()}},

        { name: 'inocDate', index: 'inocDate', width: 220, align: 'center',editable: true, editoptions: {
                dataInit: function (el) {
                    var date = formatDateTime(new Date());
                    $(el).val(date);
                    $(el).datetimepicker(
                        {
                            format: 'yyyy-mm-dd',
                            autoclose: true,
                            todayBtn: true,
                            startView: 'month',
                            minView: 'month',
                            language: 'zh-CN'
                        }
                    );
                }
            }},
        { name: 'inocBatchno', index: 'inocBatchno', width: 150, align: 'center',editable: true},

        { name: 'inocInplId', index: 'inocInplId', width: 220, align: 'center',editable: true, edittype: 'select',editoptions:{value:loadInoculateSiteData()}},
        { name: 'inocFree', index: 'inocFree', width: 150, align: 'center',editable: true, edittype: 'select',editoptions:{value:getinocFree()},
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
        { name: 'inocCorpCode', index: 'inocCorpCode', width: 220, align: 'center',editable: true, edittype: 'select',editoptions:{value:factory()}},
        { name: 'inocDepaCode', index: 'inocDepaCode', width: 220, align: 'center',editable: true},
        { name: 'inocProperty', index: 'inocProperty', width: 220, align: 'center', edittype: 'select',editable: true,editoptions:{value:inoculateRoadData()}},
        { name: 'inocNurse', index: 'inocNurse', width: 150, align: 'center',editable: true},
        { name: 'name13', index: 'name13', width: 80, align: 'center',
            formatter: function (value, grid, rows, state) {
                var df=rows.id;
                var ddd = "addtj('" + df + "')";

                return '<input id="tj'+ df+ '" type="button" value="添加" onclick="'+ ddd + '"/>'
            }
        },
    ],
    viewrecords: true,
    height: 600,
    rowNum: 60,
    // rowList : [10,30,50],
    rownumbers: true,
    rownumWidth: 40,
    autowidth:true,
    shrinkToFit:true,
    // multiselect: true,
    pager: "#jqGridPager",
    jsonReader : {
        root: "page",
        page: "page.currPage",
        total: "page.totalPage",
        records: "page.totalCount"
    },
    prmNames : {
        page:"page",
        rows:"limit",
        order: "order"
    },
    cmTemplate: {
        resizable:false,
        sortable: false
    },
    onSelectRow: function (rowid,status) {
        var rowdata=jQuery("#jzbl_content").jqGrid('getRowData',rowid);
        var bio= rowdata['inocBactCode'];
        if (bio==''||bio =='undenfine'){
            $("#jzbl_content").jqGrid('editRow', rowid);
        }
    },
    gridComplete:function(){
        //在gridComplete调用合并方法
        var gridName = "jzbl_content";
        // Merger(gridName, 'id');
        // $("#jzbl_content").jqGrid('setLabel','rn', '序号', {'text-align':'left'},'');
        //隐藏grid底部滚动条
        $("#jzbl_content").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });

    }

});



function addtj(id) {
    var out = Math.floor(Math.random() * 9000) + 1000;
    var  timestamp1 = Date.parse(new Date() + out);//时间
    var rowDatas = $("#jzbl_content").jqGrid('getRowData', id);//获取上一行数据
    $("#jzbl_content").jqGrid("addRowData", timestamp1,rowDatas , "last");
    var ids = $("#jzbl_content").jqGrid("getDataIDs");
    for (var i=0;i<ids.length;i++){
        if(ids[i]>=36){//如果天数等于0，则背景色置灰显示
            $("#"+ids[i]).css({"background-color":"#4cfcff"});
        }
    }
    $("#jzbl_content").jqGrid('editRow', timestamp1);
}
function otherbiocode() {
    var out = Math.floor(Math.random() * 9000) + 1000;
    var  timestamp1 = Date.parse(new Date() + out);//时间
    $("#jzbl_content").jqGrid("addRowData", timestamp1 , "last");
    var ids = $("#jzbl_content").jqGrid("getDataIDs");
    for (var i=0;i<ids.length;i++){
        if(ids[i]>=36){//如果天数等于0，则背景色置灰显示
            $("#"+ids[i]).css({"background-color":"#4cfcff"});
        }
    }
    $("#jzbl_content").jqGrid('editRow', timestamp1);
}

//公共调用方法
function Merger(gridName, CellName) {
    //得到显示到界面的id集合
    var mya = $("#" + gridName + "").getDataIDs();
    //当前显示多少条
    var length = mya.length;
    for (var i = 0; i < length; i++) {
        //从上到下获取一条信息
        var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
        //定义合并行数
        var rowSpanTaxCount = 1;
        for (var  j = i + 1; j <= length; j++) {
            //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
            var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);
            if (before[CellName] == end[CellName]) {
                rowSpanTaxCount++;
                $("#" + gridName + "").setCell(mya[j], CellName, '', { display: 'none' });
            } else {
                rowSpanTaxCount = 1;
                break;
            }
            $("#" + CellName + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
        }
    }
}


// function cellEditIsValue(){
//     var ids = $("#jzbl_content").jqGrid("getDataIDs");
//     var objs = $("#jzbl_content").jqGrid("getRowData");
//     for ( var i = 0; i < objs.length; i++) {
//         var getRed = objs[i].REDISTRIBUTION;
//         if(isNaN(getRed)){
//             var stationNoValue = ids[i] + "_inocBactCode";
//             var count= $("#"+stationNoValue).val();
//             $("#list4").jqGrid("setRowData",ids[i],{REDISTRIBUTION:count},'');
//         }
//     }
// }





function getinocFree() {
    var  str='';
    var param = new Array();//定义数组
    param.push({"text": '免费', "value": '1'});
    param.push({"text": '自费', "value": '0'});
    var jsonobj=eval(param);
    var length=jsonobj.length;
    for(var i=0;i<length;i++){
        if(i!=length-1){
            str+=jsonobj[i].value+":"+jsonobj[i].text+";";
        }else{
            str+=jsonobj[i].value+":"+jsonobj[i].text;// 这里是option里面的 value:label
        }
    }
    return str;
}

//接种部位
function loadInoculateSiteData() {
    var str="";
    $.ajax({
        type: "post",
        url: "../child/listdiccode?ttype=code_inoculation_site",
        async:false,
        success: function (data) {
            console.log("接种部位");
            // inoculateSiteArr1 = data.data;
            if (data != null) {
                var jsonobj=eval(data.data);
                var length=jsonobj.length;
                for(var i=0;i<length;i++){
                    if(i!=length-1){
                        str+=jsonobj[i].value+":"+jsonobj[i].text+";";
                    }else{
                        str+=jsonobj[i].value+":"+jsonobj[i].text;// 这里是option里面的 value:label
                    }
                }
            }
        }
    });
    return str;
}

//接种属性
function inoculateRoadData() {
    var str=""
    $.ajax({
        type: "post",
        url: "../child/listdiccode?ttype=code_vaccine_property",
        async:false,
        success: function (data) {
            console.log("接种属性");
            // inoculationArr1 = data.data;
            if (data != null) {
                var jsonobj=eval(data.data);
                console.log(data.data)
                var length=jsonobj.length;
                for(var i=0;i<length;i++){
                    if(i!=length-1){
                        str+=jsonobj[i].value+":"+jsonobj[i].text+";";
                    }else{
                        str+=jsonobj[i].value+":"+jsonobj[i].text;// 这里是option里面的 value:label
                    }
                }
            }

        }
    });
    return str;
}

//生产厂家
function factory() {
    var str="";
    $.ajax({
        type: "post",
        url: "../tvacfactory/getAllData",
        async:false,
        success: function (data) {
            console.log("生产厂家");
            if (data != null) {
                var jsonobj=eval(data.page);
                console.log(data.page[1].factoryCnName)
                var length=jsonobj.length;
                for(var i=0;i<length;i++){
                    if(i!=length-1){

                        str+=jsonobj[i].factoryCode+":"+jsonobj[i].factoryCnName+";";
                    }else{
                        str+=jsonobj[i].factoryCode+":"+jsonobj[i].factoryCnName;// 这里是option里面的 value:label
                    }
                }
            }
        }
    });
    return str;
}

function getHistoryInoculate1(chilCode) {
    var page = $("#jzbl_content").jqGrid('getGridParam', 'page');
    $("#jzbl_content").jqGrid('setGridParam', {
        postData: chilCode,
        page: page,
        url: '../tchildinoculate/jzbl?chilCode=' + chilCode,
    }).trigger("reloadGrid");

}


// $("#jzbl_content").jqGrid('setGridParam', {
//     postData: chilCode,
//     page: page,
//     url: '../tchildinoculate/list?limit=1000&page=1&chilCode=' + chilCode,
// }).trigger("reloadGrid");
// for (var i = 0; i <= registerVM.recommend.length; i++) {
//     $("#jzbl_content").jqGrid('addRowData', i + 1, registerVM.recommend[i]);
// }

function gettypes(){
//动态生成select内容
    var str="";
    $.ajax({
        type:"post",
        async:false,
        url:"../tvacinfo/list?page=1&limit=1000",
        success:function(data){
            if (data != null) {
                var jsonobj=eval(data.page.list);
                var length=jsonobj.length;
                for(var i=0;i<length;i++){
                    if(i!=length-1){
                        str+=jsonobj[i].bioCode+":"+jsonobj[i].bioCnSortname+";";
                    }else{
                        str+=jsonobj[i].bioCode+":"+jsonobj[i].bioCnSortname;// 这里是option里面的 value:label
                    }
                }
            }
        }
    });
    return str;

}


