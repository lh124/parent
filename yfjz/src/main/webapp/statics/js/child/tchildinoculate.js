
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
var inoculateDateStart = getUrlVars()["inoculateDateStart"];
var inoculateDateEnd = getUrlVars()["inoculateDateEnd"];
if(inoculateDateStart!="null" && inoculateDateStart != null && inoculateDateStart.length==8){
    inoculateDateStart = inoculateDateStart.substring(0,4)+"-"+inoculateDateStart.substring(4,6)+"-"+inoculateDateStart.substring(6,8);
}
if(inoculateDateEnd!="null" && inoculateDateEnd != null && inoculateDateEnd.length==8){
    inoculateDateEnd = inoculateDateEnd.substring(0,4)+"-"+inoculateDateEnd.substring(4,6)+"-"+inoculateDateEnd.substring(6,8);
}
var chilCode = getUrlVars()["chilCode"];
var birthday = getUrlVars()["birthday"];
var birthdays = getUrlVars()["birthdays"];
var birthdayss="";
if (birthdays!=null&&birthdays!="undefinde"){
    birthdayss=birthdays.replace("%20"," ");
}

var birthdaycurrendy=getUrlVars()["birthday"];
var birthdaycurrendys="";
if (birthdaycurrendy!=null&&birthdaycurrendy!="undefinde"){
    birthdaycurrendys=birthdaycurrendy.replace("%20"," ");
}
var birthdayupdatainoRecord=getUrlVars()["birthdays"];
var birthdayupdatainoRecords="";
if (birthdayupdatainoRecord!=null&&birthdayupdatainoRecord!="undefinde"){
    birthdayupdatainoRecords=birthdayupdatainoRecord.replace("%20"," ");
}
var birthdayinoc=getUrlVars()["birthday"];
var birthdayinocs="";
if (birthdayinoc!=null&&birthdayinoc!="undefinde"){
    birthdayinocs=birthdayinoc.replace("%20"," ");
}

$(function () {
    $("#inoculationGrid").jqGrid({
        url: '../tchildinoculate/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [

            { label: 'id', name: 'id', width: 80,align:'center',hidden:true },
            { label: '疫苗类别', name: 'inocVcnKind', width: 150,align:'center',
                cellattr: function (rowId, tv, rawObject, cm, rdata) {
                    return 'id=\'inocVcnKind' + rowId + "\'";
                }
            },
            { label: '疫苗名称', name: 'inocBactCode', width: 215,align:'center',
                formatter: function (cellValue, options, rowdata) {
                    return "<label>"+cellValue+"</label>";
                },
            },
            { label: '剂次', name: 'inocTime', width: 60,align:'center' },
            { label: '接种属性', name: 'inocProperty', width: 80,align:'center' },
            { label: '接种日期', name: 'inocDate', width: 150,align:'center',
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return  "<label>"+value.substring(0,10)+"</label>";
                        // return  value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            { label: '接种单位', name: 'inocDepaCode', width: 120,align:'center' },

            { label: '疫苗批号', name: 'inocBatchno', width: 150,align:'center' },
            { label: '生产企业', name: 'inocCorpCode', width: 120,align:'center' },
            { label: '是否免费', name: 'inocFree', width: 80,align:'center',
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
            { label: '接种部位', name: 'inocInplId', width: 80,align:'center' },
            {label: '接种状态', name: 'inocOpinion', hidden: true, width: 120},
            {label: '接种途径', name: 'channel',  hidden: true, width: 120}

             // { label: '接种医生', name: 'inocNurse', width: 80,align:'center' },
 // */

            /*{ label: '联合疫苗编码', name: 'inocUnionCode', width: 80 }, 	*/
            /*{ label: '疫苗失效期', name: 'inocValiddate', width: 80,align:'center' },*/

            /*{ label: '疫苗监管码', name: 'inocRegulatoryCode', width: 80 },*/

            /*
            { label: '接种记录修改单位机构编码(存下载时的值)', name: 'inocModifyCode', width: 80 },
            { label: '修改记录时间', name: 'inocEditdate', width: 80 }, 		*/
            /*{ label: '留观是否完成', name: 'leaveTime', width: 80,align:'center',formatter:observe},
            { label: '留观时间', name: 'observeTime', width: 80,align:'center',formatter:observeTime},
            { label: '添加记录时间', name: 'createTime', width: 80,hidden:true },*/
            /*{ label: '0:本地；1：平台', name: 'type', width: 80 }, 		*/
            /*{ label: '同步状态', name: 'syncstatus', width: 80,
                formatter:function (value) {
                    if(value==0){
                        return "未同步";
                    }
                    if(value==1){
                        return "同步";
                    }
                }
            },*/

            /*{ label: '备注', name: 'remark', width: 80,align:'center' }*/
        ],
        viewrecords: true,
        height: 'auto',
        rowNum: 50,
        rowList : [50,60,80],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        /*shrinkToFit: true,
        multiboxonly: true,*/
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
        onSelectRow: function () {
        },
        ondblClickRow: function (rowid) {
            var rowData = $("#inoculationGrid").jqGrid('getRowData',rowid);//获取当前行的数据
            console.log(chilCode);
            console.log(rowid);
            console.log(rowData);
            var httpurl = "../child/updatainoRecorddialog.html?id=" + rowData["id"]+
                "&inocDepaCode=" + encodeURI(rowData["inocDepaCode"]) +
                "&chilCode="+ chilCode
            var index=layer.open({
                title: "修改接种记录",
                area: ["800px", "340px"],
                type: 2,
                content: httpurl,
                success: function (index, layero) {

                    $(".layui-layer-page .layui-layer-content").css("overflow-y", "hidden");

                },
            })
        },
        gridComplete:function(){
            MergerStatistics("inoculationGrid", 'inocVcnKind');
            var rowIDs =  $("#inoculationGrid").getDataIDs();
            var current_year = new Date().getFullYear();
            var current_month = new Date().getMonth()+1;
            var current_day = new Date().getDate();
            var currentDay = current_year +"-"+ current_month +"-"+ current_day;
            var inoculateDateStartDate = "";
            var inoculateDateEndDate = "";
            if(inoculateDateStart!="null"){
                 inoculateDateStartDate = new Date(inoculateDateStart).getTime();
            }
            if(inoculateDateEnd!="null"){
                inoculateDateEndDate = new Date(inoculateDateEnd).getTime();
            }

            for(var i = 0; i<rowIDs.length;i++){
                var rowData = $("#inoculationGrid").jqGrid('getRowData',rowIDs[i]);
                var inocDate = rowData.inocDate.replace("<label>","").replace("</label>","");
                var inocDateTime = new Date(inocDate).getTime();
                var inoculate_year = new Date(inocDate).getFullYear();
                var inoculate_month = new Date(inocDate).getMonth()+1;
                var inoculate_day = new Date(inocDate).getDate();
                var inoculate_date = inoculate_year +"-"+ inoculate_month +"-"+ inoculate_day;
                if(currentDay==inoculate_date){
                    //$('#historyTable').jqGrid('setSelection',i,true);
                    $("#inoculationGrid").setSelection(rowIDs[i]);
                    $("#"+rowIDs[i]+ " td").css("background-color","yellow");//--------(1)
                }else if((inoculateDateStartDate!="" && inoculateDateEndDate != "") && inoculateDateStartDate <= inocDateTime && inoculateDateEndDate >= inocDateTime){
                    $("#inoculationGrid").setSelection(rowIDs[i]);
                    $("#"+rowIDs[i]+ " td").css("background-color","yellow");//--------(1)
                }else if(inoculateDateStartDate!="" && inoculateDateStartDate == inocDateTime){
                    $("#inoculationGrid").setSelection(rowIDs[i]);
                    $("#"+rowIDs[i]+ " td").css("background-color","yellow");//--------(1)
                }

            }
            //隐藏grid底部滚动条
            $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });


            dateSortDisplay(rowIDs);
        }
    });
    inoculationGridlist();
});

//日期排序高亮显示
function dateSortDisplay(rowIDs){
    var arr = new Array();
    for(var i = 0; i<rowIDs.length;i++){
        var rowData = $("#inoculationGrid").jqGrid('getRowData',rowIDs[i]);
        rowData.inocDate = rowData.inocDate.replace("<label>","").replace("</label>","");
        arr.push(rowData);
    }
    arr.sort(function(a,b){
        return Date.parse(a.inocDate) - Date.parse(b.inocDate);//时间正序
    });

    var childDate = arr[arr.length -1];
    for(var i =0,l=arr.length;i<l;i++){
        if(childDate.inocDate == arr[i].inocDate){
            $("#"+arr[i].id).css("background-color","yellow");
        }
    }




}

var vminoculation = new Vue({
    el:'#rrapp_inoculation',
    data:{
        showList: true,
        title: null,
        tChildInoculate: {}
    },
    methods: {
        query: function () {
            vminoculation.reload();
        },
        add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
            vminoculation.showList = false;
            vminoculation.title = "新增";
            vminoculation.tChildInoculate = {};
        },
        update: function (event) {
            var id = getSelectedRowsInoculation();
            if(id == null){
                return ;
            }
            vminoculation.showList = false;
            vminoculation.title = "修改";

            vminoculation.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vminoculation.tChildInoculate.id == null ? "../tchildinoculate/save?chilCode="+chilCode : "../tchildinoculate/update?chilCode="+chilCode;
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vminoculation.tChildInoculate),
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vminoculation.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRowsInoculation();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../tchildinoculate/delete",
                    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#inoculationGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get("../tchildinoculate/info/"+id, function(r){
                vminoculation.tChildInoculate = r.tChildInoculate;
            });
        },
        reload: function (event) {
            vminoculation.showList = true;
            var page = $("#inoculationGrid").jqGrid('getGridParam','page');
            $("#inoculationGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        },
        printInoculation:function () {

        }
    }
});
//选择多条记录
function getSelectedRowsInoculation() {
    var grid = $("#inoculationGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }
    return grid.getGridParam("selarrrow");
}

function observe(value, options, row) {

    if(row.createTime==null){
        return "";
    }
    var date1 = new Date(row.createTime);  //开始时间
    //alert("aa");
    var date2 = new Date(row.leaveTime);    //结束时间
    var date3 = date2.getTime() - date1.getTime();  //香蕉差的毫秒数

    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    //计算出小时数
    var hours = Math.floor(date3 %(24 * 3600 * 1000)/ (3600 * 1000));
    //计算相差分钟数
    var minutes = Math.floor(date3 %(3600 * 1000)/ (60 * 1000));
    //alert(minutes);
    if (isNaN(minutes)) {
        return "未留观";
    }
    if (minutes >= 30||hours>1) {
        return "完成"
    } else if(minutes<30 && minutes>0) {
        return "未完成";
    }else{
        return "未留观";
    }
    //  return "未留观";
}

function observeTime(value, options, row) {
    var date1 = new Date(row.createTime);  //开始时间
    //alert("aa");
    var date2 = new Date(row.leaveTime);    //结束时间
    var date3 = date2.getTime() - date1.getTime();  //香蕉差的毫秒数

    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    //计算出小时数
    var hours = Math.floor(date3 %(24 * 3600 * 1000)/ (3600 * 1000));
    //计算相差分钟数
    var minutes = Math.floor(date3 %(3600 * 1000)/ (60 * 1000));
    if (isNaN(minutes)) {
        return "";
    }
    if(minutes<0){
        return "";
    }else{
        return minutes;
    }

}

function inoculationGridlist() {
    $("#inoculationGrid").on("mouseover", 'tr[role="row"]', function () {
        var trid = $(this).attr("id");
        //里面写相关的操作。
        var rowData = $("#inoculationGrid").jqGrid('getRowData', trid);
        var emergencySencondMgrId = rowData["inocDate"].replace("<label>","").replace("</label>","");//列名和jGrid定义时候的值一样
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
        if (birthdayss.length>9) {
            layer.msg("<p align='left'>接种月龄:"+dateDiff(birthdayss, emergencySencondMgrId) + "</p><p align='left'>接种评价：" + str+"</p>"+"<p align='left'>接种途径：" + ch+"</p>");
        } else if (birthdaycurrendys.length>9) {
            layer.msg("<p align='left'>接种月龄:"+dateDiff(birthdaycurrendys, emergencySencondMgrId) + "</p><p align='left'>接种评价：" + str+"</p>"+"<p align='left'>接种途径：" + ch+"</p>");
        } else if (birthdayupdatainoRecords.length>9) {
            layer.msg("<p align='left'>接种月龄:"+dateDiff(birthdayupdatainoRecords, emergencySencondMgrId) + "</p><p align='left'>接种评价：" + str+"</p>"+"<p align='left'>接种途径：" + ch+"</p>");
        }else if (birthdayinocs.length>9){
            layer.msg("<p align='left'>接种月龄:"+dateDiff(birthdayinocs, emergencySencondMgrId) + "</p><p align='left'>接种评价：" + str+"</p>"+"<p align='left'>接种途径：" + ch+"</p>");
        }
    })
}
