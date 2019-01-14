$(function () {
    $("#childInfoGrid").jqGrid({
        //url: '../recommend/inoNote',
        datatype: "json",
        colModel: [
            {label: '儿童编码', name: 'chilCode', width: 130, key: true},
            {label: '行政村', name: 'committee',  width: 100},
            {label: '姓名', name: 'chilName', width: 80, align: "center"},
            {label: '性别', name: 'chilSex', width: 50, align: "center"},
            {label: '出生日期', name: 'chilBirthday', width: 100},
            {label: '父亲姓名', name: 'fatherName', width: 80},
            {label: '母亲姓名', name: 'matherName', width: 80},
            {label: '家庭电话', name: 'chilTel', width: 100},
            {label: '手机', name: 'chilMobile', width: 100},
            {label: '联系地址', name: 'address', width: 150}
        ],
        viewrecords: true,
        height: 'auto',
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
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
            $("#childInfoGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    selectChildInfo = function (selectType,classId,agentTime) {
        //vm.showList = true;
       // var page = $("#childInfoGrid").jqGrid('getGridParam','page');
       // var limit = $("#childInfoGrid").jqGrid('getGridParam','rowNum');// rows
        var formdata = $("#unInoNoteForm").serializeObject();
       // var postdata = formdata+'&selectType='+selectType+'&classId='+classId+'&agentTime='+agentTime+'&page='+page+'&limit='+limit
        formdata.selectType=selectType;
        formdata.classId=classId;
        formdata.agentTime=agentTime;
        //console.info(formdata);
        $("#childInfoGrid").jqGrid('clearGridData');  //清空表格
        $("#childInfoGrid").jqGrid('setGridParam',{
            url: '../inoStatistics/rateChildInfo',
            postData : formdata,
            page:1
        }).trigger("reloadGrid");
    }
});
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};