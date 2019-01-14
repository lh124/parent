
$(function () {
    //疫苗
    $("#BactCode").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            {label: '疫苗编码', name: 'bioCode', width: 80,align:'center'},
            {label: '疫苗名称', name: 'bioCnSortname', width: 150,align:'center'},
        ],
        viewrecords: true,
        height: $(window).height() - 240,
        rowNum: 200,
        // rowList : [40,50,80],
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,

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
            //隐藏grid底部滚动条
            // $("#BactCode").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            // jqgridColResize();
        },
        onSelectRow: function (rowid) {
            var rowData = $("#BactCode").jqGrid('getRowData',rowid);//获取当前行的数据
            console.log(rowData);
            getClassIdType(rowData.bioCode.substring(0,2));
        },
        /*onSelectRow: function (rowid) {*/
        ondblClickRow: function (rowid, iRow, iCol, e) {
            //接种部位
            var rowData= $("#BactCode").jqGrid("getRowData",rowid);

            var str = "";
            $.each(addVm.inoculateSiteArr, function (index, item) {
                str = str + item.value + ":" + item.text + ";";
            });
            str = str.substring(0, str.length - 1);
            $('#historyTable1').setColProp('inocInplId', {
                editoptions: {buildSelect: null, dataUrl: null, value: str}
            });
            //接种属性
            var con = "";
            $.each(addVm.inoculationArr, function (index, item) {
                con = con + item.value + ":" + item.text + ";";
            });
            con = con.substring(0, con.length - 1);
            $('#historyTable1').setColProp('inocProperty', {
                editoptions: {buildSelect: null, dataUrl: null, value: con}
            });
            //接种疫苗
            var conbiocode =rowData.bioCode+":"+rowData.bioCnSortname;
            /*$.each(addVm.inocBioCodeArr, function (index, item) {
                conbiocode = conbiocode + item.bioCode + ":" + item.bioCnSortname + ";";
            });
            conbiocode = conbiocode.substring(0, conbiocode.length - 1);*/
            $('#historyTable1').setColProp('inocBactCode', {
                editoptions: {buildSelect: null, dataUrl: null, value: conbiocode}
            });
            var param = new Array();//定义数组
            param.push({"text": '免费', "value": '1'});
            param.push({"text": '自费', "value": '0'});
            var inocFree = "";
            $.each(param, function (index, item) {
                inocFree = inocFree + item.value + ":" + item.text + ";";
            });
            inocFree = inocFree.substring(0, inocFree.length - 1);
            $('#historyTable1').setColProp('inocFree', {
                editoptions: {buildSelect: null, dataUrl: null, value: inocFree}
            });
            var out = Math.floor(Math.random() * 9000) + 1000;
            timestamp1 = Date.parse(new Date() + out);//时间
            //获取所有历史接种记录
            var tempData;
            var preId;
            endEdit($("#historyTable1"));
            var rows=getRows($("#historyTable1"));
            //结束编辑
            for(var i=rows.length-1;i>=0;i--){
                var row=rows[i];
                var hisClass=row.inocUseticket.substring(0,2);
                var nowClass=rowData.bioCode.substring(0,2);
                if(hisClass==nowClass){
                    tempData=row;
                    preId=row.id;
                    break;
                }
            }
            beginEdit(rows);
            if(tempData==undefined){
                tempData={inocTime:null,inocBactCode:null,inocUseticket:null,id:null};
                tempData.inocTime=1;
                tempData.inocBactCode=rowData.bioCnSortname;
                tempData.inocUseticket=rowData.bioCode;
                tempData.inocVcnKind=null;
                tempData.id=timestamp1;

                $("#historyTable1").addRowData(timestamp1,tempData,"last");
            }else{
                tempData.inocTime=parseInt(tempData.inocTime)+1;
                tempData.inocBactCode=rowData.bioCnSortname;
                tempData.inocUseticket=rowData.bioCode;
                tempData.inocDate=null;
                tempData.inocBatchno=null;
                tempData.inocNurse=null;
                tempData.id=timestamp1;
                $("#historyTable1").addRowData(timestamp1,tempData,"after",preId);
            }
            $("#historyTable1").jqGrid('editRow', timestamp1);
            var data = $("#BactCode").getRowData(rowid);
            // // 获取行某一个数据
            var rowData = $("#BactCode").jqGrid("getRowData", rowid, data);
            // var inocBactCodeId = timestamp1 + "_inocBactCode";
            // $('#' + inocBactCodeId).val(rowData.bioCnSortname + rowData.bioCode);
            var ids = jQuery("#historyTable1").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var be = "<input style='height:35px;width:200px;' id='btndept_"+timestamp1+"' class='form-control' type='text' onclick='setfocus(this)'  oninput='setinput(this)'/>" +
                    "<select name='makeupCoSe_"+timestamp1+"'  id='typenum_"+timestamp1+"' onchange='changeF(this)' size='8' style='display:none;'></select>"
                jQuery("#historyTable1").jqGrid('setRowData',timestamp1,
                    {
                        inocDepaCode: be,
                    });
                $("#typenum_"+ids[i]).css({"display":"none"});
            }

            // console.log($("#" + timestamp1+'_inocDepaCode').val());
            // var str=$("#btndept_"+timestamp1).val();
            // console.log(str);
            // if (!str.indexOf("-")){
            //     $("#typenum_"+timestamp1).css({"display":"none"});
            // }


        }
    });

    $("#historyTable1").jqGrid({
        // url: '',
        datatype: "local",
        colModel: [
            {label: 'id', name: 'id', key: true, hidden: true,align:'center'},
            { label: '疫苗类别', name: 'inocVcnKind', width: 100,align:'center',
                cellattr: function (rowId, tv, rawObject, cm, rdata) {
                    return 'id=\'inocVcnKind' + rowId + "\'";
                }
            },
            {label: '疫苗名称', name: 'inocBactCode', width: 150, editable: true,align:'center',},

            {label: '疫苗编码', name: 'inocUseticket',hidden: true, align:'center'},
            {label: '剂次', name: 'inocTime', width: 60, editable: true,align:'center'},
            {label: '批号', name: 'inocBatchno', width: 100, editable: true,align:'center'},
            {label: '接种属性', name: 'inocProperty', width: 100, editable: true, edittype: "select",align:'center'},
            {label: '接种状态', name: 'inocOpinion', hidden: true, width: 120,align:'center'},
            {
                label: '接种日期', name: 'inocDate', width: 150, editable: true,align:'center',
                editoptions: {
                    dataInit: function (el) {
                        // var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            {label: '接种部位', name: 'inocInplId', width: 100, editable: true, edittype: "select",align:'center'},

            {label: '接种单位', name: 'inocDepaCode', width: 150, editable: true,inputtype: "select",align:'center'},

            {label: '生产企业', name: 'inocCorpCode', width: 150, editable: true, edittype: "select",align:'center',editoptions:{value:factory()}},
            {
                label: '是否免费', name: 'inocFree', width: 80, editable: true, edittype: "select",align:'center',
                formatter: function (value) {
                    if (value == 1) {
                        return "免费";
                    } else if (value == 0) {
                        return "收费";
                    } else {
                        return "";
                    }
                }
            },
            {label: '接种护士', name: 'inocNurse', width: 100, editable: true,align:'center'},
            {label: '操作', name: 'remove', width: 60,
                formatter: function (cellValue, options, rowdata, action) {
                    if(options.rowId.length>15){
                        return "";
                    }
                    return "<a href='javaScript:void(0)' onclick='removeNewAdd(\""+options.rowId+"\")'>删除</a>";
                }
            }
        ],
        viewrecords: true,
        height: $(window).height() - 200,
        rowNum: 50,
        rowList: [50, 60, 80],
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
        // multiselect: true,
        // pager: "#inoculationGridPager",
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
            // $("#historyTable1").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            MergerStatistics("historyTable1", 'inocVcnKind');
            // jqgridColResize();
            // $(".ui-jqgrid,.ui-jqgrid-htable,.ui-jqgrid-hdiv,.table-responsive,.ui-jqgrid-bdiv,#historyTable1").css("width","auto");
            // $(".ui-jqgrid-htable,#historyTable1").css("width","auto");
        },
        onSelectRow: function (rowid, iRow, iCol, e) {
            var rowData = $("#historyTable1").jqGrid('getRowData',rowid);//获取当前行的数据
            getClassIdType(rowData.inocUseticket.substring(0,2));
        }
        ,
        ondblClickRow: function (rowid) {
            // // 选中行实际表示的位置
            // var ind = $("#historyTable1").getInd(rowid);//获取行号
            // // 新插入行的位置
            // var newInd = ind + 1;
            // console.log(ind);
            // console.log(newInd);
            var rowData = $("#historyTable1").jqGrid('getRowData',rowid);//获取当前行的数据
            var out = Math.floor(Math.random() * 9000) + 1000;
            var  timestamp1 = Date.parse(new Date() + out);//时间
            //接种部位
            var str = "";
            $.each(addVm.inoculateSiteArr, function (index, item) {
                str = str + item.value + ":" + item.text + ";";
            });
            str = str.substring(0, str.length - 1);
            $('#historyTable1').setColProp('inocInplId', {
                editoptions: {buildSelect: null, dataUrl: null, value: str}
            });
            //接种属性
            var con = "";
            $.each(addVm.inoculationArr, function (index, item) {
                con = con + item.value + ":" + item.text + ";";
            });
            con = con.substring(0, con.length - 1);
            $('#historyTable1').setColProp('inocProperty', {
                editoptions: {buildSelect: null, dataUrl: null, value: con}
            });
            //接种疫苗
            var conbiocode = "";
            $.each(addVm.inocBioCodeArr, function (index, item) {
                conbiocode = conbiocode + item.bioCode + ":" + item.bioCnSortname + ";";
            });
            conbiocode = conbiocode.substring(0, conbiocode.length - 1);
            $('#historyTable1').setColProp('inocBactCode', {edittype: "select",
                editoptions: {buildSelect: null, dataUrl: null, value: conbiocode}
            });
            var param = new Array();//定义数组
            param.push({"text": '免费', "value": '1'});
            param.push({"text": '自费', "value": '0'});
            var inocFree = "";
            $.each(param, function (index, item) {
                inocFree = inocFree + item.value + ":" + item.text + ";";
            });
            inocFree = inocFree.substring(0, inocFree.length - 1);
            $('#historyTable1').setColProp('inocFree', {
                editoptions: {buildSelect: null, dataUrl: null, value: inocFree}
            });
            var strinocTime =parseInt(rowData["inocTime"])+1//新增行 剂次在上一行加1
            if (rowid.length>=36){
                $("#historyTable1").addRowData(timestamp1,rowData,"after",rowid);
            }
            $("#historyTable1").jqGrid('setCell',timestamp1,"inocTime",strinocTime);
            $("#historyTable1").jqGrid('editRow', timestamp1);
            var ids = jQuery("#historyTable1").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                be = "<input style='height:35px;width:200px;' id='btndept_"+timestamp1+"' class='form-control' type='text' onclick='setfocus(this)'  oninput='setinput(this)'/>" +
                    "<select name='makeupCoSe_"+timestamp1+"'  id='typenum_"+timestamp1+"' onchange='changeF(this)' size='8' style='display:none;'></select>"
                jQuery("#historyTable1").jqGrid('setRowData',timestamp1,
                    {
                        inocDepaCode: be,
                    });
                $("#typenum_"+ids[i]).css({"display":"none"});
            }
            // var inocBactCodeId = timestamp1 + "_inocBactCode";
            // $('#' + inocBactCodeId).val(rowData.inocBactCode + rowData.inocUseticket);


        }
    });
    $("#outsideInoculate").jqGrid({
        datatype: "local",
        colModel: [
            {label: '疫苗编码', name: 'bioCode', width: 150,hidden: true,align:'center'},
            {label: '疫苗名称', name: 'bioCnSortname', width: 150,align:'center'},
            {label: '第1剂次', name: 'one', width: 150,'editable': false,align:'center',
                cellattr: function(rowId, val, rawObject, cm, rdata) {
                    // return "style=' color:blue'";
                },
                editoptions: {
                    dataInit: function (el) {
                        var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            // {label: '1', name: 'one_1', width: 150,'editable': true,hidden: true},
            {label: '第2剂次', name: 'two', width: 150,'editable': false,align:'center',
                editoptions: {
                    dataInit: function (el) {
                        var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            {label: '第3剂次', name: 'three', width: 150,'editable': false,align:'center',
                editoptions: {
                    dataInit: function (el) {
                        var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            {label: '第4剂次', name: 'four', width: 150,'editable': false,align:'center',
                editoptions: {
                    dataInit: function (el) {
                        var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            {label: '第5剂次', name: 'five', width: 150,'editable': false,align:'center',
                editoptions: {
                    dataInit: function (el) {
                        var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            {label: '第6剂次', name: 'six', width: 150,'editable': false,align:'center',
                editoptions: {
                    dataInit: function (el) {
                        var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            {label: '第7剂次', name: 'seven', width: 150,'editable': false,align:'center',
                editoptions: {
                    dataInit: function (el) {
                        var date = formatDateTime(new Date());
                        // $(el).val(date);
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
                },
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
        ],
        viewrecords: true,
        height: $(window).height() - 200,
        rowNum: 200,
        // rowList : [40,50,80],
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,

        // pager: "#inoculationGridPager",
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
        // cellEdit: true,
        gridComplete: function (rowid) {
            $("#outsideInoculate").find("td").on("click",function () {
                var rowid=$(this.closest("tr")).attr("id");
                var val=$(this).html();
                var cell= $(this).attr("aria-describedby");
                var cellName=cell.substring(cell.lastIndexOf("_")+1,cell.length);
                console.log(cellName)
                //<td role="gridcell" style="text-align:center;" aria-describedby="outsideInoculate_three"></td>
                // outsideinoc(val,cellName);
                var inputs=  $(this).find("input");
                if(val==""&&inputs.length<=0){
                    $(this).append("<input type=\"text\" id="+rowid+'_'+cellName+" name="+cellName+" rowid="+rowid+" role=\"textbox\" class=\"editable inline-edit-cell form-control\" style=\"width: 96%;\">");
                }
                // $("#outsideInoculate").jqGrid('editRow', rowid);
            });
        },
        onSelectRow:function(rowid,cellname,value){
            /* var rowData = $("#outsideInoculate").jqGrid('getRowData',rowid);
             var one= rowData["one"];
             var two=rowData["two"];
             var three=rowData["three"];
             var four=rowData["four"];
             var five=rowData["five"];
             var six=rowData["six"];
             var seven=rowData["seven"];
             outsideinoc(one,"one");
             outsideinoc(two,"two");
             outsideinoc(three,"three");
             outsideinoc(four,"four");
             outsideinoc(five,"five");
             outsideinoc(six,"six");
             outsideinoc(seven,"seven");*/


        },
    })

    var  chilCode= getUrlVars()["childCode"];
    addVm.childCode=chilCode;
    addVm.childBirthday= getUrlVars()["childBirthday"];
    // $("#inputbio input[name='empAddress']").hide();
    /*    $("#bsaddress").hide();
        $("#wsaddress").hide();
        $("#wsjzdw").hide();
        $("#address").hide();*/
    $("#vaccine").show();
    $("#addres").hide();
    $("#outsideInoculate").jqGrid("clearGridData");
    $("#historyTable1").jqGrid("clearGridData");
    getAefiBactCodeoutside(chilCode);
    getHistoryInoculate1(chilCode);//历史记录
    searchNames();//疫苗
    inoculateRoadData();//接种属性
    hospital();//接种单位
    loadInoculateSiteDataForRegister();//接种部位
    historybllist();
    $("#myTabs li").on("click",function () {

        if ($("#myTabs li[class='active']").find("a").html()=="省内补录" ) {
            getAefiBactCodeoutside(chilCode);
            $("#vaccine").hide();
            $("#addres").show();

        }else if ($("#myTabs li[class='active']").find("a").html()=="外省补录") {
            getHistoryInoculate1(chilCode);//历史记录
            $("#vaccine").show();
            $("#addres").hide();

        }
    })
})
function hospital(this_) {
    $.ajax({
        type: "post",
        url: "../tbaseposition/gethospital",
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            addVm.inocDepaArr = data.data;
        }
    });
}
var str=[];
function getHistoryInoculate1(chilCode) {
    $.ajax({
        type: 'POST',
        url: '../tchildinoculate/list?limit=100&page=1&chilCode=' + chilCode,
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        success: function (data) {
            for (var i=0;i<data.page.list.length;i++) {
                var obj ={
                    "inocBactCode": data.page.list[i].inocUseticket,
                    "inocBatchno": data.page.list[i].inocBatchno,
                    "inocCorpCode": data.page.list[i].inocCorpCode,
                    "inocInplId": data.page.list[i].inocInplId,
                    "inocTime": data.page.list[i].inocTime,
                    "inocDate": data.page.list[i].inocDate,
                    "inocProperty": data.page.list[i].inocProperty,
                    "inocNurse": data.page.list[i].inocNurse,
                    "inocDepaCode": data.page.list[i].inocDepaCode,
                    "inocRegulatoryCode": data.page.list[i].inocRegulatoryCode,
                    "chilCode": data.page.list[i].chilCode,
                    "inocFree": data.page.list[i].inocFree
                };
                str.push(obj);
            }
            $("#historyTable1").setGridParam({datatype : 'local',data:data.page.list}).trigger('reloadGrid');

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

//疫苗查询
function bioinput() {
    var radios = document.getElementsByName("empBio");
    for ( var i = 0; i < radios.length; i++) {
        if (radios[i].checked==true) {
            if (radios[i].value==1){
                searchNames()
            }else if (radios[i].value==2) {
                searchName();
            }
        }
    }
}
//常规疫苗
function searchNames() {
    var val = $("#searchName").val();
    $("#BactCode").jqGrid("clearGridData");
    var url = "../tchildinoculate/outsideinoculatebio";
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',//重点关注此参数
        success: function (data) {
            console.log(data.data)
            doSearch(val, data.data, ['bioCode','bioName','bioCnSortname','bioEnSortname','pinyinInitials'], $("#BactCode"));
        }
    })
}

//全部疫苗
function searchName() {
    $("#BactCode").jqGrid("clearGridData");
    var val = $("#searchName").val();
    console.log(val)
    var url = "../tvacinfo/getAllData";
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',//重点关注此参数
        success: function (data) {
            console.log(data.page)
            doSearch(val, data.page, ['bioCode','bioName','bioCnSortname','bioEnSortname','pinyinInitials'], $("#BactCode"));
        }
    })
}
//外省接种单位
function outsideinput() {
    var radios = document.getElementsByName("empAddress");
    for ( var i = 0; i < radios.length; i++) {
        if (radios[i].checked==true) {
            if (radios[i].value==3){
                $("#wsjzdw").hide();
                $("#address").hide();
                // $("#address").val(orgName);
            }else if (radios[i].value==4) {
                $("#wsjzdw").show();
                $("#address").show();
                $("#address").val("外省");
            }
        }
    }
}
//设置常规疫苗和全部疫苗的显示隐藏
$("#inoculateoutside").on('click',function () {
    $("#inputbio input[name='empBio']").hide();
    $("#inputbio input[name='empAddress']").show();
    $("#cbio").hide();
    $("#abio").hide();
    $("#wsjzdw").show();
    $("#address").show();
    $("#bsaddress").show();
    $("#wsaddress").show();
})
//设置常规疫苗和全部疫苗的显示隐藏
$("#inoculatein").on('click',function () {
    $("#inputbio input[name='empBio']").show();
    $("#cbio").show();
    $("#abio").show();
    $("#inputbio input[name='empAddress']").hide();
    $("#bsaddress").hide();
    $("#wsaddress").hide();
    $("#wsjzdw").hide();
    $("#address").hide();
})

function setinput(this_){
    var id=this_.id.substring(8);
    var select = $("#typenum_"+id);
    select.html("");
    for(var i=0;i<addVm.inocDepaArr.length;i++){
        //若找到以txt的内容开头的，添option
        console.log(addVm.inocDepaArr[i].name.substring(0,this_.value.length).indexOf(this_.value));
        if(addVm.inocDepaArr[i].name.substring(0,this_.value.length).indexOf(this_.value)==0){
            var option = $("<option value='"+addVm.inocDepaArr[i].id+"'>"+addVm.inocDepaArr[i].name+"</option>");
            select.append(option);
        }
    }

}
function setfocus(this_){
    var id=this_.id.substring(8);
    $("#typenum_"+id).css({"display":""});
    var select = $("#typenum_"+id);
    for(var i=0;i<addVm.inocDepaArr.length;i++){
        var option = $("<option value='"+addVm.inocDepaArr[i].id+"'>"+addVm.inocDepaArr[i].name+"</option>");
        select.append(option);
    }
    if ($("#btndept_"+id).val().indexOf("-")!=-1) {

        $("#typenum_"+id).css({"display":"none"});
    }
}
//接种单位
function changeF(this_) {
    var id=this_.id.substring(8);
    $(this_).prev("input").val($(this_).find("option:selected").text()+"-"+$(this_).find("option:selected").val());
    $("#typenum_"+id).css({"display":"none"});
    $("#" + id+'_inocDepaCode').val($(this_).find("option:selected").text()+"-"+$(this_).find("option:selected").val());
    $("#typenum_"+id).css({"display":"none"});
}

//省内补录
function savedialog1() {
    var childCode =addVm.childCode;
    var ids = $("#historyTable1").jqGrid('getDataIDs');
    var productArray = new Array();
    productArray.splice(0, productArray.length);
    for (var i = 0; i < ids.length; i++) {
        if (ids[i] >= 36) {
            var row=$("#historyTable1").getRowData(ids[i]);
            var inocBactCodeId = ids[i] + "_inocBactCode";
            var inocBatchnoId = ids[i] + "_inocBatchno";
            var inocTimeId = ids[i] + "_inocTime";
            var inocFreeId = ids[i] + "_inocFree";
            var inocCorpCodeId = ids[i] + "_inocCorpCode";
            var inocInplIdId = ids[i] + "_inocInplId";
            var inocDateId = ids[i] + "_inocDate";
            var inocPropertyId = ids[i] + "_inocProperty";
            var inocNurseId = ids[i] + "_inocNurse";
            var inocRegulatoryCodeId = ids[i] + "_inocRegulatoryCode";
            // var inocUseticket = ids[i] + "_inocUseticket";
            // var inocDepaCodeId = ids[i] + "_inocDepaCode";
            var inocDepaCodeId =  "btndept_"+ids[i] ;
            var inocBactCodett = $('#' + inocBactCodeId+" option:selected").val();

            if(isEmpty(inocBactCodett)){
                inocBactCodett=row.inocUseticket;
            }
            // if (inocBactCodett != "undefined" && inocBactCodett != null) {
            //     var str = inocBactCodett.substr(-4);
            // }
            var inocBatchno = $('#' + inocBatchnoId).val();
            var inocTime = $('#' + inocTimeId).val();
            //只能是数字
            if(inocTime!=""){//剂次不等于空时候需要校验
                var times=parseInt(inocTime);
                if (!(/^[0-9]+$/.test(times))) {
                    alert("剂次只能输入数字")
                    return false;
                }
            }
            if(isEmpty(inocBactCodett)){
                layer.msg("请选择疫苗");
                return;
            }
            var inocFree = $('#' + inocFreeId + " option:selected").val();
            // var inocDepaCode = $('#' + inocDepaCodeId + " option:selected").val();
            var inocDepaCode = $('#' + inocDepaCodeId).val();
            // $('#' + inocDepaCodeId).val(strinocDepaCode)
            var inocCorpCode = $('#' + inocCorpCodeId + " option:selected").val();
            var inocInplId = $('#' + inocInplIdId + " option:selected").val();
            var inocNurse = $('#' + inocNurseId).val();
            var inocDate = $('#' + inocDateId).val();
            if(isEmpty(inocDate)){
                layer.msg("接种时间不能为空，请输入接种时间。");
                return;
            }else{
                if(inocDate.indexOf("-")>0){
                    inocDate=inocDate.replace(/\-/g,"");
                }
            }
            if(!checkInputDate(inocDate)){
                return
            }
            var inocProperty = $('#' + inocPropertyId + " option:selected").val();
            var inocRegulatoryCode = $('#' + inocRegulatoryCodeId).val();
            productArray.push({
                "inocBactCode": inocBactCodett,
                "inocBatchno": inocBatchno,
                "inocCorpCode": inocCorpCode,
                "inocInplId": inocInplId,
                "inocTime": inocTime,
                "inocDate": inocDate,
                "inocProperty": inocProperty,
                "inocNurse": inocNurse,
                "inocDepaCode": inocDepaCode,
                "inocRegulatoryCode": inocRegulatoryCode,
                "chilCode": childCode,
                "inocFree": inocFree
            });
        }
    }
    endEdit($("#historyTable1"));
    var rows=getRows($("#historyTable1"));
    for(var i=0;i<productArray.length;i++){
        var subRow=productArray[i];
        for(var j=0;j<rows.length;j++){
            var nowRow=rows[j];
            var subTimes=subRow.inocTime;
            var nowTimes=nowRow.inocTime;
            var subDate=subRow.inocDate;
            var nowDate=nowRow.inocDate;
            var nowCode=nowRow.inocUseticket;
            var subCode=subRow.inocBactCode;
            if(nowCode==subCode&&subTimes!=1&&((parseInt(subTimes)-1)==nowTimes)){
                var now=new Date(nowDate);
                var sub;
                if(subDate.indexOf("-")<0){
                    var dates=subDate.substring(0,4)+"-"+subDate.substring(4,6)+"-"+subDate.substring(6,8);
                    sub=new Date(dates);
                }else{
                    sub=new Date(subDate);
                }
                var ret=now.getTime()-sub.getTime();
                if(ret>=0){
                    layer.msg(nowRow.inocBactCode+"的第"+subTimes+"剂次接种时间小于或等于第"+nowTimes+"的接种时间");
                    beginEdit(rows);
                    return;
                }
            }
        }

    }
    for (var i=0;i<str.length;i++){
        for (var j=0;j<productArray.length;j++) {
            console.log(str[i].inocDate.substring(0,10).replace(/-/g,""))
            console.log(productArray[j].inocDate);
            console.log(str[i].inocDate.substring(0,10).replace(/-/g,"")==productArray[j].inocDate);
            console.log(str[i].inocInplId=='口服')
            var inocinplIds=productArray[j].inocInplId;
            var strt='';
            if (inocinplIds == 1) {
                strt= '左上臂';
            } else if (inocinplIds == 2) {
                strt= '右上臂';
            } else if (inocinplIds == 4) {
                strt= '左大腿';
            } else if (inocinplIds == 5) {
                strt= '右大腿';
            } else if (inocinplIds == 7) {
                strt= '左臀部';
            } else if (inocinplIds == 8) {
                strt= '右臀部';
            } else if (inocinplIds == 9) {
                strt= '口服';
            }
            console.log(productArray[j].inocInplId);
            if ( str[i].inocDate.substring(0,10).replace(/-/g,"")==productArray[j].inocDate
                && str[i].inocInplId==strt) {
                layer.msg("同一天接种部位不能重复");
                beginEdit(rows);
                return false;
            }
            if (str[i].inocBactCode.substring(0, 2) == productArray[j].inocBactCode.substring(0, 2)
                &&  productArray[j].inocDate <str[i].inocDate.substring(0,10).replace(/-/g,"") ){
                layer.msg("同类疫苗当前剂次不能小于上一剂次");
                beginEdit(rows);
                return false;
            }
        }
    }

    for (var i=0;i<productArray.length;i++){
        for (var j=0;j<i;j++) {
            if ( productArray[i].inocDate==productArray[j].inocDate
                && productArray[i].inocInplId==productArray[j].inocInplId ) {
                layer.msg("同一天接种部位不能重复");
                beginEdit(rows);
                return false;
            }
        }
    }

    $.ajax({
        type: 'POST',
        url: '../tchildinoculate/batchInsertAccountInfo',
        data: {inoculate: JSON.stringify(productArray)},
        //dataType : 'json',
        // contentType : 'application/json;charset=utf-8',
        success: function (r) {
            console.log(r)
            if (r.code === 0) {
                alert('操作成功', function (index) {
                    getHistoryInoculate1(childCode);//历史记录
                    // addVm.reload();
                });
            } else {
                alert(r.msg);
            }
        }
    });
    //结束编辑状态
    endEdit($("#historyTable1"));
};
function saveoutside() {
    var childCode = addVm.childCode;
    var ids = $("#outsideInoculate").jqGrid('getDataIDs');
    var radios = document.getElementsByName("empAddress");
    var address ="";
    for ( var i = 0; i < radios.length; i++) {
        if (radios[i].checked==true) {
            if (radios[i].value==3){
                $("#wsjzdw").hide();
                $("#address").hide();
                address=radios[i].value;
                console.log(radios[i].value);
            }else if (radios[i].value==4) {
                $("#wsjzdw").show();
                $("#address").show();
                address=$("#address").val();
                console.log(radios[i].value);
            }
        }
    }

    var productArray=new Array();
    for (var i = 0; i <= ids.length; i++) {
        var row= $("#outsideInoculate").jqGrid("getRowData",ids[i]);
        var bioCodeId = ids[i] + "_bioCode";
        var oneId = ids[i] + "_one";
        var twoId = ids[i] + "_two";
        var threeId = ids[i] + "_three";
        var fourId = ids[i] + "_four";
        var fiveId = ids[i] + "_five";
        var sixId = ids[i] + "_six";
        var sevenId = ids[i] + "_seven";
        var bioCode = row.bioCode;
        var ones = $('#' + oneId).val();
        var twos = $('#' + twoId).val();
        var threes = $('#' + threeId).val();
        var fours = $('#' + fourId).val();
        var fives = $('#' + fiveId).val();
        var sixs = $('#' + sixId).val();
        var sevens = $('#' + sevenId).val();
        if (ones != null && ones != "" && ones != undefined && ones != "undefined" && ones.length>=8) {
            var one = ones+","+"1"+","+bioCode+","+childCode;
            var resultone=one.split(",");
            productArray.push({
                "inocBactCode":bioCode ,
                "inocDate": ones,
                "inocTime": 1,
                "chilCode": childCode,
                "inocDepaCode":address,
                "inocProperty": 0,//默认为基础
            });

        }
        if (twos != null && twos != "" && twos != undefined && twos != "undefined" && twos.length>=8) {
            var two = twos+","+"2"+","+bioCode+","+childCode;
            var resultone=two.split(",");
            productArray.push({
                "inocBactCode":bioCode ,
                "inocDate": twos,
                "inocTime": 2,
                "chilCode": childCode,
                "inocProperty": 0,//默认为基础
                "inocDepaCode":address
            });

        }
        if (threes != null && threes != "" && threes != undefined && threes != "undefined" && threes.length>=8) {
            var three = threes+","+"3"+","+bioCode+","+childCode;
            var resultone=three.split(",");
            productArray.push({
                "inocBactCode":bioCode ,
                "inocDate": threes,
                "inocTime": 3,
                "chilCode": childCode,
                "inocProperty": 0,//默认为基础
                "inocDepaCode":address
            });
        }
        if (fours != null && fours != "" && fours != undefined && fours != "undefined" && fours.length>=8) {
            var four = fours+","+"4"+","+bioCode+","+childCode;
            var resultone=four.split(",");
            productArray.push({
                "inocBactCode":bioCode ,
                "inocDate": fours,
                "inocTime": 4,
                "chilCode": childCode,
                "inocDepaCode":address
            });
        }
        if (fives != null && fives != "" && fives != undefined && fives != "undefined" && fives.length>=8) {
            var five = fives+","+"5"+","+bioCode+","+childCode;
            var resultone=five.split(",");
            productArray.push({
                "inocBactCode":bioCode ,
                "inocDate": fives,
                "inocTime": 5,
                "inocProperty": 0,//默认为基础
                "chilCode": childCode,
                "inocDepaCode":address
            });
        }
        if (sixs != null && sixs != "" && sixs != undefined && sixs != "undefined" && sixs.length>=8) {
            var six = sixs+","+"6"+","+bioCode+","+childCode;
            var resultone=six.split(",");
            productArray.push({
                "inocBactCode":bioCode ,
                "inocDate": sevens,
                "inocTime": 6,
                "inocProperty": 0,//默认为基础
                "chilCode": childCode,
                "inocDepaCode":address
            });
        }
        if (sevens != null && sevens != "" && sevens != undefined && sevens != "undefined" && sevens.length>=8) {
            var seven = sevens+","+"7"+","+bioCode+","+childCode;
            var resultone=seven.split(",");
            productArray.push({
                "inocBactCode":bioCode ,
                "inocDate": sevens,
                "inocTime": 7,
                "inocProperty": 0,//默认为基础
                "chilCode": childCode,
                "inocDepaCode":address
            });
            // for(var i=0;i<resultone.length;i++){
            //
            // }
        }
    }
    for(var i=0;i<productArray.length;i++){
        var inocDate= productArray[i].inocDate;
        if(inocDate.indexOf("-")>0){
            inocDate=inocDate.replace(/\-/g,"");
        }
        var ret=checkInputDate(inocDate);
        if(!ret){
            return;
        }
        if(!productArray[i].inocBactCode){
            return;
        }
    }
    //常规疫苗
    if(productArray.length>0){
        $.ajax({
            type: 'POST',
            url: '../tchildinoculate/outsideinoculate',
            data: {inoculate: JSON.stringify(productArray)},
            success: function (r) {
                console.log(r)
                if (r.code === 0) {
                    layer.msg('操作成功');
                    getAefiBactCodeoutside(childCode);//重载表格数据
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }
    //结束编辑状态
    $("#outsideInoculate").trigger("reloadGrid");
}

function outsideinoc(value,cell) {
    if(value.length>=6){
        $('#outsideInoculate').setColProp(cell, {
            editable: false
        });
    }else {
        $('#outsideInoculate').setColProp(cell, {
            editable: true
        });
    }
}

function historybllist() {
    $("#historyTable1").on("mouseover", 'tr[role="row"]', function () {
        var trid = $(this).attr("id");
        //里面写相关的操作。
        var rowData = $("#historyTable1").jqGrid('getRowData',trid);
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
            // addVm.factoryArr = data.page;
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
//接种部位
function loadInoculateSiteDataForRegister() {
    $.ajax({
        type: "post",
        url: "../child/listdiccode?ttype=code_inoculation_site",
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            addVm.inoculateSiteArr = data.data;
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
            addVm.inoculationArr = data.data;
        }
    });
}

function  getClassIdType(bioCode) {
    $.ajax({
        type: 'POST',
        url: '../tchildinoculate/getbioClassIdType?bioClasssId='+bioCode,
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        success: function (data) {
            addVm.inocBioCodeArr=data.data;
            console.log(data.data[0].bioCode)
        }
    });
}
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


var addVm=new Vue({
    el: '#addrecord',
    data: {
        tChildInfo: {},
        recommend: [],
        childBirthday:null,
        childCode:null,
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
    }
})

function savaRecord() {
    if ($("#myTabs li[class='active']").find("a").html()=="手工补录" ) {
        savedialog1();
    }else if ($("#myTabs li[class='active']").find("a").html()=="批量补录") {
        saveoutside();
    }
}
function colse() {
    window.parent.layer.closeAll();
}

function beginEdit(rows) {
    for(var i=0;i<rows.length;i++){
        var row=rows[i];
        if(!isNaN(row.id)){
            $("#historyTable1").jqGrid('editRow', row.id);
            var be = "<input style='height:35px;width:200px;' id='btndept_"+row.id+"' class='form-control' type='text' onclick='setfocus(this)'  oninput='setinput(this)'/>" +
                "<select name='makeupCoSe_"+row.id+"'  id='typenum_"+row.id+"' onchange='changeF(this)' size='8' style='display:none;'></select>"
            jQuery("#historyTable1").jqGrid('setRowData',row.id,
                {
                    inocDepaCode: be,
                });
            $("#typenum_"+row.id).css({"display":"none"});
        }
    }
}