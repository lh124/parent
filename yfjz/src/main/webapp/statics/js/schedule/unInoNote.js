$(function () {
    $("#jqGrid").jqGrid({
        //url: '../recommend/inoNote',
        datatype: "json",
        sortable:false,
        colModel: [
            {label: '儿童编码', name: 'chilCode', width: 130, key: true,sortable:false},
            {label: '行政村', name: 'committee', hidden: true,sortable:false},
            {label: '姓名', name: 'chilName', width: 60, align: "center",sortable:false},
            {label: '性别', name: 'chilSex', width: 50, align: "center",sortable:false},
            {label: '出生日期', name: 'chilBirthday', width: 60,sortable:false,},
            {label: '父亲姓名', name: 'fatherName', width: 60,sortable:false},
            {label: '母亲姓名', name: 'matherName', width: 60,sortable:false},
            {label: '家庭电话', name: 'chilTel', width: 80,sortable:false},
            {label: '手机', name: 'chilMobile', width: 80,sortable:false},
            {label: '联系地址', name: 'address', width: 150,sortable:false},
            {label: '疫苗', name: 'planName', width: 80,sortable:false},
            {label: '剂次', name: 'times', width: 80, align: "center",sortable:false},
            {
                label: '日期', name: 'inoDate', width: 80, formatter: function (value, grid, rows, state) {
                    if (value != null && value != "" && value.length >= 10) {
                        return value.substring(0, 10);
                    }
                    return "";
                },sortable:false
            }
        ],
        viewrecords: true,
        height: 'auto',
        rowNum: 100000,
        //rowList : [10,30,50],
        // rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        // pager: "#jqGridPager",
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            $("#jqGrid").setGridParam().hideCol("committee");
        },

        grouping: true,
        groupingView: {
            groupField: ['committee']
        }

    });
    var now = new Date();
    var preMonth = new Date(getMonthBeforeFormatAndDay(-3, '-', now.getDate()));//向前推三个月
    var date = yfjzformatter(preMonth);
    var nowDate = yfjzformatter(now);

    $("#chilBirthdayStart").val(date);
    $("#chilBirthdayEnd").val(nowDate);

    //统计grid
    $("#statisticsGrid").jqGrid({
        //url: '../recommend/inoNote',
        datatype: "json",
        colModel: [
            {label: '', name: 'id', width: 1, key: true, hidden: true},
            {label: '行政村', name: 'committee', width: 100},
            {label: '疫苗剂次', name: 'planName', width: 120},
            {label: '人数', name: 'times', width: 60, align: "center"}
        ],
        viewrecords: true,
        height: 'auto',
        autowidth: true,
        rowNum: 10000,
        rownumWidth: 25,
        multiselect: true,
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#statisticsGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    //按疫苗统计grid
    $("#inoTotalGrid").jqGrid({
        //url: '../recommend/inoNote',
        datatype: "json",
        colModel: [
            {label: '', name: 'id', width: 1, key: true, hidden: true},
            {label: '疫苗名称', name: 'planName', width: 120},
            {label: '人数', name: 'times', width: 60, align: "center"}
        ],
        viewrecords: true,
        height: 'auto',
        autowidth: true,
        rowNum: 1000,
        rownumWidth: 25,
        multiselect: true,
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#inoTotalGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    vm.loadInfoStatusData();//个案状态
    vm.loadCommiteeData();//行政村
    vm.loadBios();//疫苗类别
    vm.chilaccount();//居住属性
    vm.residence();//户籍属性
    vm.loadSchoolData();//入学入托


    $("#chilBirthdayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vm.q.chilBirthdayEnd = $("#chilBirthdayEnd").val();
    });
    $("#chilBirthdayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        vm.q.chilBirthdayStart = $("#chilBirthdayStart").val();
    });
    $("#selectDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        startDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    $("#planDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        startDate: new Date(),
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });

    vm.pinyincommsearch();

});

function yfjzformatter(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}

/**
 *  *  求自然月日期
 *  by 刘琪  2018-05-18
 *
 *
 * @param num 向前，向后推迟的月份数
 * @param format  日期连接符  “-”，“/”等等
 * @param day  向前，向后推迟的天数
 */
function getMonthBeforeFormatAndDay(num, format, day) {
    var date = new Date();
    date.setMonth(date.getMonth() + num, 1);
    //读取日期自动会减一，所以要加一
    var mo = date.getMonth() + 1;
    //小月
    if (mo == 4 || mo == 6 || mo == 9 || mo == 11) {
        if (day > 30) {
            day = 30
        }
    } else if (mo == 2) {
        //2月
        if (isLeapYear(date.getFullYear())) {
            if (day > 29) {
                day = 29
            } else {
                day = 28
            }
        }
        if (day > 28) {
            day = 28
        }
    } else {
        //大月
        if (day > 31) {
            day = 31
        }
    }
    return date.format('yyyy' + format + 'MM' + format + day);
}

/**
 * 日期格式化
 * @param format
 * @returns {*}
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        tRuleHiv: {},
        q: {
            chilCode: null,
            chilName: null,
            chilCommittee: null,
            chilBirthdayStart: null,
            chilBirthdayEnd: null,
            chilHere: null

        },
        chilCommittee: null,
        biotypes: null
    },
    methods: {
        query: function () {

            var selectDate=   $("#selectDate").val();//统计日期
            var planDate=   $("#planDate").val();//安排日期
            var stdt=new Date(selectDate.replace("-","/"));
            var etdt=new Date(planDate.replace("-","/"));
            if(stdt>etdt){
                $("#selectDate").val("");
                $("#planDate").val("");
                alert("统计日期必须小于安排日期")
            }
            vm.reload();
        },
        printNote: function () {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            } else {
                var pureIds = [];
                var pureId = null;
                for (var i = 0; i < ids.length; i++) {
                    if (ids[i] != pureId && ids[i] != " ") {
                        pureId = ids[i];
                        pureIds.push(pureId);
                    }
                }
                if (pureIds.length == 0) {
                    layer.alert("请选择儿童数据！");
                } else {
                    var childCodes;
                    var childNames;
                    var motherNames;
                    var fatherNames;
                    var inoDates;
                    var addresss;
                    for (var i = 0; i < pureIds.length; i++) {
                        var row = $("#jqGrid").jqGrid("getRowData", pureIds[i]);
                        if (i == 0) {
                            childCodes = pureIds[i];
                            childNames = row.chilName;
                            motherNames = row.matherName;
                            fatherNames = row.fatherName;
                            inoDates = row.inoDate;
                            addresss = row.address;
                        } else {
                            childCodes += ",";
                            childNames += ",";
                            motherNames += ",";
                            fatherNames += ",";
                            inoDates += ",";
                            addresss += ",";

                            childCodes += pureIds[i];
                            childNames += row.chilName;
                            motherNames += row.matherName;
                            fatherNames += row.fatherName;
                            inoDates += row.inoDate;
                            addresss += row.address;
                        }

                    }
                    layer.open({
                        type: 2,
                        area: ['700px', '530px'],
                        fix: false, //不固定
                        title: '未种通知单',
                        maxmin: true,
                        content: '../schedule/noInoNotice.html?childCode=' + childCodes + '&childName=' + childNames + '&motherName=' + motherNames + '&fatherName=' + fatherNames + '&detailaddress=' + addresss + '&orgName=省人民医医&inoculateDate=' + inoDates
                    });
                }
            }

        },
        excel: function (event) {
            //window.location.href = '../recommend/inoNoteExcel?' + $("#unInoNoteForm").serialize();
            window.open('../recommend/inoNoteExcel?' + $("#unInoNoteForm").serialize(),"_blank");
        },
        print: function () {
            $("#unprintdiv").hide();
            window.focus();
            window.print();
            $("#unprintdiv").show();
        },
        reload: function (event) {
            vm.showList = true;
            //var page = $("#jqGrid").jqGrid('getGridParam','page');
            var formdata = $("#unInoNoteForm").serialize();
            $("#jqGrid").clearGridData();  //清空表格
            $("#jqGrid").jqGrid('clearGridData');  //清空表格
            $("#statisticsGrid").jqGrid('clearGridData');  //清空表格
            $("#inoTotalGrid").jqGrid('clearGridData');  //清空表格
            /*$("#jqGrid").jqGrid('setGridParam',{
                url: '../recommend/inoNote',
                page:page,
                postData : formdata
            }).trigger("reloadGrid");*/
            layer.msg('努力中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 1000000});
            $.ajax({
                url: '../recommend/inoNote',
                data: formdata,
                success: function (data) {
                    layer.msg('加载完成！', {time: 1000});
                    if(data.code == 0){
                        //$("#jqGrid")[0].addJSONData(data.page.list); //这个添加数据的时候之前查询的分组还在，数据不在
                        $("#jqGrid").jqGrid('setGridParam',{  // 重新加载数据 ，这个添加数据不会出现之前的分组，会重新刷新整个表格
                            datatype:'local',
                            data : [],   //  newdata 是符合格式要求的需要重新加载的数据
                            page:1
                        }).trigger("reloadGrid");
                        // vm.reload();
                        // jQuery("#jqGrid").trigger("reloadGrid");//刷新表格数据
                        $("#jqGrid")[0].addJSONData(data.page.list);
                        $("#statisticsGrid")[0].addJSONData(data.counList);
                        $("#inoTotalGrid")[0].addJSONData(data.inototals);
                    }else if(data.code == 100){
                        layer.msg('没有查询到儿童信息！', {icon:6});
                    }
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
                    var seldata = results.data;
                    $.each(seldata, function (i, n) {
                        $("#infostatus").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
                    })
                    $("#infostatus").selectpicker('refresh');
                }
            });
        },

        //行政村
        loadCommiteeData: function () {
            var param = new Array();
            $.ajax({
                url: '../tbasecommittee/list?org_id=' + orgId + '&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    if ("undefined" != typeof vm) {
                        vm.chilCommittee = seldata;
                    }
                    $.each(seldata, function (i, n) {
                        $("#chilCommittees").append(" <option value=\"" + n.code + "\">" + n.name + "</option>");
                    })
                    $("#chilCommittees").selectpicker('refresh');
                }
            });
        },

        pinyincommsearch: function () {
            //初始化下拉框
            $('#chilCommittees').selectpicker({
                'selectedText': 'cat',
                noneSelectedText: '请选择行政村/居委会',
                //     actionsBox:true,
                search: false,
            });
            // 行政村/居委会拼音搜索
            //选择得到搜索栏input,松开按键后触发事件
            $("#chilCommitteeIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
                //键入的值
                var inputManagerName = $('#chilCommitteeIdParent .open input').val();
                var hunt = $("#chilCommittees");
                //判定键入的值不为空,才调用ajax
                if (inputManagerName != '') {
                    hunt.empty();
                    var value = null;
                    if ("undefined" != typeof vm) {
                        value = vm.chilCommittee;
                    }
                    var con = '<option value=""></option>';
                    var reg = new RegExp("^[A-Za-z]+$");
                    if (reg.test(inputManagerName)) {
                        for (var i = 0; i < value.length; i++) {
                            if (value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0) {
                                con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
                            }
                        }
                    } else {
                        for (var i = 0; i < value.length; i++) {
                            if (value[i].pinyinInitials != null && value[i].name.indexOf(inputManagerName) == 0) {
                                con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
                            }
                        }
                    }
                    hunt.append(con);
                    hunt.selectpicker('refresh');
                    event.stopPropagation();
                    return false;
                } else {
                    //如果输入的字符为空,清除之前option标签
                    hunt.empty();
                    var value = null;
                    if ("undefined" != typeof vm) {
                        value = vm.chilCommittee;
                    }
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
        //疫苗类别
        loadBios: function () {
            //初始化下拉框
            /*$('#biotypes').selectpicker({
                'selectedText': 'cat',
                search: false,
            });*/
            var param = new Array();
            $.ajax({
                url: '../truledic/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var seldata = results.page.list;
                    vm.biotypes = seldata;
                    console.log(seldata);
                    $.each(seldata, function (i, n) {
                        $("#biotypes").append(" <option value=\"" + n.id + "\">" + n.name + "</option>");
                    })
                    $("#biotypes").selectpicker('refresh');
                }
            });
        },

        //户籍属性
        chilaccount: function () {
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=movetype_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '<option></option>';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
                    }
                    $("#chilAccount").html(con);
                }
            });
        },
        //居住属性
        residence: function () {
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_residence_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '<option></option>';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">' + results.data[i].text + '</option>';
                    }
                    $("#chilResidence").html(con);
                }
            });
        },
        //学校
        loadSchoolData: function () {
            var param = new Array();
            $.ajax({
                url: '../tbaseschool/list?org_id=' + orgId + '&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '<option  value=""></option>';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].name + '">' + results.page.list[i].name + '</option>';
                    }
                    $("#chilSchool").html(con);

                }
            });
        },
        historyRecord: function () {
            layer.open({
                title: "批量补录",
                area: ["95%", "95%"],
                type: "1",
                content: '<iframe src="../schedule/batchInoculate.html" data-id="batch" style="width: 100%;height: 100%;overflow:auto" ></iframe>',
                success: function (index, layero) {
                    $(".layui-layer-page .layui-layer-content").css("overflow-y", "hidden");

                }
            })
        }
    }
});


/*$(function () {
    // 疫苗类别拼音搜索 这个是免疫规划字典表，国家规划名称不是疫苗类别
    //选择得到搜索栏input,松开按键后触发事件
    $("#biotypes").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#biotypes");
        var value = vm.biotypes;
        //清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if (reg.test(inputManagerName)) {
                for (var i = 0; i < value.length; i++) {
                    if (value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0) {
                        con += '<option  value="' + value[i].classCode + '">' + value[i].classCnName + '</option>';
                    }
                }
            } else {
                for (var i = 0; i < value.length; i++) {
                    if (value[i].classCnName.indexOf(inputManagerName) == 0) {
                        con += '<option  value="' + value[i].classCode + '">' + value[i].classCnName + '</option>';
                    }
                }
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            event.stopPropagation();
            return false;
        } else {
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].classCode + '">' + value[i].classCnName + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });

})*/
