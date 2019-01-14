$(function () {
    $("#startDate").val(formatDateTime(new Date()));
    $("#endDate").val(formatDateTime(new Date()));
    $('#startDate').datetimepicker(
        {
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN',
            initialDate:new Date()
        }
    );
    $('#endDate').datetimepicker(
        {
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN'
        }
    );



    // $("#vacScheduleVillag").jqGrid({
    //     // url: '../work/list?',
    //     datatype: "json",
    //     colModel: [
    //         { label: '疫苗与剂次（人份）', name: 'username1', width: 130, key: true },
    //         { label: '上月累计入库', name: 'username2', width:80 },
    //         { label: '本月延苗数', name: 'username3', width: 80 },
    //         { label: '本年累计入库', name: 'username4', width: 80 },
    //         { label: '本月底库存', name: 'username5', width: 80 },
    //         { label: '下月领取计划', name: 'username6', width: 80 },
    //         { label: '耗损数', name: 'username7', width: 80 },
    //         { label: '耗损原因', name: 'username8', width: 80 },
    //     ],
    //     viewrecords: true,
    //     rowNum: -1,
    //     autowidth:true,
    //     multiselect: true,
    //     jsonReader : {
    //         root: "page.list",
    //         page: "page.currPage",
    //         total: "page.totalPage",
    //         records: "page.totalCount"
    //     },
    //     prmNames : {
    //         page:"page",
    //         rows:"limit",
    //         order: "order"
    //     },
    //     gridComplete:function(){
    //         //隐藏grid底部滚动条
    //         $("#vacTransport").closest(".ui-jqgrid-bdiv").css("overflow-x","hidden");
    //     },
    //     onSelectRow:function (rowid,status) {
    //     }
    // });

    //数据jqgrid表格

    var mydata = [
            { name1: "乙肝疫苗HepB", name2: "1", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "乙肝疫苗HepB", name2: "首剂及时", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "乙肝疫苗HepB", name2: "2", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "乙肝疫苗HepB", name2: "3", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "卡介苗BCG", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "脊灰灭活", name2: "1", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "二价脊灰（人份）", name2: "2", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "二价脊灰（人份）", name2: "3", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "二价脊灰（人份）", name2: "4", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "百白破疫苗DPT", name2: "1", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "百白破疫苗DPT", name2: "2", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "百白破疫苗DPT", name2: "3", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "百白破疫苗DPT", name2: "4", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "白破疫苗DT(人份)", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "麻疹疫苗M", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "麻风疫苗MR", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "麻腮风疫苗MMR1", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "麻腮疫苗MM", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "A群流脑疫苗MenA(人份)", name2: "1", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "A群流脑疫苗MenA(人份)", name2: "2", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "A+C群流脑疫苗MenAC", name2: "1", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "A+C群流脑疫苗MenAC", name2: "2", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "乙肝减毒灭活JE-1", name2: "1", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "乙肝减毒灭活JE-1", name2: "2", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "甲肝减毒活疫苗HepA-1", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "AD注射器____ml", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "AD注射器_____ml", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "",name9: "" },
            { name1: "一次性注射器_0.5___ml", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
            { name1: "一次性注射器_0.25___ml", name2: "", name3: "", name4: "", name5: "", name6: "", name7: "", name8: "" ,name9: ""},
        ],
        grid = $("#vacScheduleVillag");

    grid.jqGrid({
        datatype: "local",
        data: mydata,
        colNames:['疫苗与剂次（人份）','疫苗与剂次（人份）','上月累计入库','本月延苗数','本年累计入库','本月底库存','下月领取计划','耗损数','耗损原因'],
        colModel:[
            { name: 'name1', index: 'name1', width: 150,align: 'center',sortable: false,
                cellattr: function(rowId, tv, rawObject, cm, rdata) {
                    //合并列
                    if (Number(rowId)==5){
                        return 'colspan=2'
                    }else if (Number(rowId)==14) {
                        return 'colspan=2'
                    }else if (Number(rowId)==15) {
                        return 'colspan=2'
                    }else if (Number(rowId)==16) {
                        return 'colspan=2'
                    }else if (Number(rowId)==17) {
                        return 'colspan=2'
                    }else if (Number(rowId)==18) {
                        return 'colspan=2'
                    }else if (Number(rowId)==25) {
                        return 'colspan=2'
                    }else if (Number(rowId)==26) {
                        return 'colspan=2'
                    }else if (Number(rowId)==27) {
                        return 'colspan=2'
                    }else if (Number(rowId)==28) {
                        return 'colspan=2'
                    }else if (Number(rowId)==29){
                        return 'colspan=2';
                    }else{
                        //合并行单元格
                        return 'id=\'name1' + rowId + "\'";
                    }
                }
            },
            { name: 'name2', index: 'name2', width: 100,align: 'center',
                cellattr: function(rowId, tv, rawObject, cm, rdata) {
                    //合并单元格
                    return 'id=\'name2' + rowId + "\'";
                }
            },
            { name: 'name3', index: 'name3', width: 80, align: 'center'},
            { name: 'name4', index: 'name4', width: 80, align: 'center'},
            { name: 'name5', index: 'name5', width: 80, align: 'center'},
            { name: 'name6', index: 'name6', width: 80, align: 'center'},
            { name: 'name7', index: 'name7', width: 80, align: 'center'},
            { name: 'name8', index: 'name8', width: 80, align: 'center'},
            { name: 'name9', index: 'name9', width: 80, align: 'center'},
        ],
        viewrecords: true,
        height: 600,
        rowNum: 60,
        // rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 40,
        autowidth:true,
        shrinkToFit:true,
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
        cmTemplate: {
            resizable:false,
            sortable: false
        },
        gridComplete:function(){
            //在gridComplete调用合并方法
            var gridName = "vacScheduleVillag";
            MergerShe(gridName, 'name1');
            var newWidth = $("#vacScheduleVillag_name1").width() +
                $("#vacScheduleVillag_name2").outerWidth(true) ;

            $("#vacScheduleVillag").jqGrid("setLabel", "name1", "疫苗与剂次（人份）", "", {
                style: "width: " + newWidth + "px;",
                colspan: "2"

            });
            //
            $("#vacScheduleVillag").jqGrid('setLabel','rn', '序号', {'text-align':'left'},'');
            $("#vacScheduleVillag").jqGrid("setLabel", "name2", "疫苗与剂次（人份）", "", {style: "display: none"});

            //隐藏grid底部滚动条
            $("#vacScheduleVillag").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });

        }

    });

    //公共调用方法
    // function Merger(gridName, CellName) {
    //     //得到显示到界面的id集合
    //     var mya = $("#" + gridName + "").getDataIDs();
    //     //当前显示多少条
    //     var length = mya.length;
    //     for (var i = 0; i < length; i++) {
    //         //从上到下获取一条信息
    //         var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
    //         //定义合并行数
    //         var rowSpanTaxCount = 1;
    //         for (var  j = i + 1; j <= length; j++) {
    //             //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
    //             var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);
    //             if (before[CellName] == end[CellName]) {
    //                 rowSpanTaxCount++;
    //                 $("#" + gridName + "").setCell(mya[j], CellName, '', { display: 'none' });
    //             } else {
    //                 rowSpanTaxCount = 1;
    //                 break;
    //             }
    //             $("#" + CellName + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
    //         }
    //     }
    // }


    function MergerShe(gridName, CellName) {

        //得到显示到界面的id集合

        var mya = $("#" + gridName + "").getDataIDs();

        //当前显示多少条

        var length = mya.length;

        for (var i = 0; i < length; i++) {

            //从上到下获取一条信息

            var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);

            //定义合并行数

            var rowSpanTaxCount = 1;

            for (j = i + 1; j <= length; j++) {

                //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏

                var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);

                var cellNames = CellName.split(",");

                for (var n = 0; n < cellNames.length; n++) {

                    if (before[cellNames[0]] == end[cellNames[0]] ) {

                        rowSpanTaxCount++;

                        $("#" + gridName + "").setCell(mya[j], cellNames[0], '', { display: 'none' });

                    } else {

                        rowSpanTaxCount = 1;

                        break;

                    }

                    $("#" + cellNames[0] + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);//最后合并需要合并的行与合并的行数

                }

            }

        }

    }

});



function queryWork() {
    var start=$("#startDate").val();
    var end=$("#endDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择查询开始时间！")
        return;
    }
    if (end==undefined||end==null||end==""){

        layer.msg("请选择查询结束时间！")
        return;
    }
    $("#satisfiedGrid").jqGrid('setGridParam',{
        postData: {"startDate":start,"endDate":end},
    }).trigger("reloadGrid");
}
function workExcel() {

    window.location.href="../ExcelController/vacScheduleVillag";
}
function printWork() {
    var start=$("#startDate").val();
    var end=$("#endDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择查询开始时间！")
        return;
    }
    if (end==undefined||end==null||end==""){
        layer.msg("请选择查询结束时间！")
        return;
    }
    $("#content").hide();
    $("#content").after("<h1 align='center'>国家免疫规划疫苗计划表</h1>");
    window.focus();
    window.print();
    $("#content").next("h1").remove();
    $("#content").show();
}