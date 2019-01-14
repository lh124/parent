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
var chilCode = getUrlVars()["chilCode"];
var chilName = getUrlVars()["chilName"];
var birthdays = getUrlVars()["birthday"];
var birthdayss="";
if (birthdays!=null&&birthdays!="undefinde"){
    birthdayss=birthdays.replace("%20"," ");
}



$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchildinoculate/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [
            { label: '疫苗名称', name: 'inocBactCode', width: 150 },
            { label: '批号', name: 'inocBatchno', width: 80 },
            { label: '剂次', name: 'inocTime', width: 80 },
            { label: '接种属性', name: 'inocProperty', width: 80 },
            { label: '接种日期', name: 'inocDate', width: 150 ,
                formatter:function (value) {
                    if (value!=null && value != "" && value.length>6 && value!='undefined' && value!=undefined ){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
            {label: '接种状态', name: 'inocOpinion', hidden: true, width: 120},
            { label: '接种部位', name: 'inocInplId', width: 80 },
            { label: '接种单位', name: 'inocDepaCode', width: 80 },
            { label: '疫苗失效期', name: 'inocValiddate', width: 80 },
            { label: '生产企业', name: 'inocCorpCode', width: 80 },
            { label: '是否免费', name: 'inocFree', width: 80,
                formatter:function (value) {
                    if(value == 1){
                        return "免费";
                    }else
                    if(value == 0){
                        return "收费";
                    } else {
                        return "";
                    }
                }
            },
            { label: '接种护士', name: 'inocNurse', width: 80 },
            { label: '留观是否完成', name: 'leaveTime', width: 80,formatter:observe},
            { label: '留观时间', name: 'observeTime', width: 80,formatter:observeTime},
            { label: '添加记录时间', name: 'createTime', width: 80,hidden:true },
            { label: '备注', name: 'remark', width: 80 }
        ],
        viewrecords: true,
        height: 600,
        // rowNum: 10,
        // rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        // pager: "#jqGridPager",
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
        postData:{
            chilCode:$("#chilCode").val()
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

    $("#vaccine").jqGrid({
        url: '../tvacinfo/list',
        datatype: "json",
        colModel: [
            { label: '国标编码', name: 'bioCode', width: 70 ,search:true},
            { label: '疫苗名称', name: 'bioName', width: 130 ,search:true},
            { label: '中文简称', name: 'bioCnSortname', width: 100 ,search:true},
            { label: '英文简称', name: 'bioEnSortname', width: 80 ,search:true},
        ],
        viewrecords: true,
        width:600,
        height: 100,
        rowNum: 1000,
        rowList : [10,30,50],
        rownumWidth: 25,
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
            $("#vaccine").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        //选中一行，然后将数据保存到输入框中，关闭弹出框
        onSelectRow:function (rowid,status) {
            var row=$("#vaccine").jqGrid("getRowData",rowid);
            $("#fkVaccineCode").val(row.bioCode);
            $("#fkVaccineName").val(row.bioName);
            //vm.tMgrStockBase = {fkVaccineCode:row.bioCode,productName:row.bioName};
            $('#myModal').modal('hide')

            $("#myModal").on("hidden", function() {
                $(this).removeData("modal");
            });

        }
    });
    vm.loadData();
    vm.factoryCodeDate();
    //初始化输入框
    $(".selectpicker").selectpicker({
        noneSelectedText:'请选择生产厂家...',
    })
});


function searchName() {
    var val=$("#searchName").val();
    console.log(val)
    var url="../tvacinfo/getAllVaccine";
    $.ajax({
        url:url,
        type:'post',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',//重点关注此参数
        success: function (data) {
            doSearch(val,data.page,['bioCode','bioName','bioCnSortname','bioEnSortname'],$("#vaccine"));
        }
    })
}

function loadTypeData(listType,url) {
    var param = new Array();//定义数组
    $.ajax({
        // get请求地址
        url: url,
        dataType: "json",
        success: function (data) {
            var result = data.data;
            $.each(result, function (index, item) {
                param.push({"text": item.text, "value": item.value});
            });
            vm[listType] = param;
        }
    });

}

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            chilCode:null
        },
        showList: true,
        title: null,
        tChildInoculate: {},
        items:[],
        inocInplIdCode:[],
        inoculateRoadCode:[],
        inocOpinionCode:[],
        inocPropertyCode:[],
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
            vm.showList = false;
            vm.title = "新增";
            vm.tChildInoculate = {
                chilCode:chilCode
            };
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        loadData:function(){
            //接种部位
            loadTypeData("inocInplIdCode","../child/listdiccode?ttype=code_inoculation_site");
            //接种途径
            loadTypeData("inoculateRoadCode","../child/listdiccode?ttype=code_inoculation_route");
            //接种评价
            loadTypeData("inocOpinionCode","../child/listdiccode?ttype=opinion_code");
            //接种属性
            loadTypeData("inocPropertyCode","../child/listdiccode?ttype=code_vaccine_property");
        },

        factoryCodeDate:function(){
            $.ajax({
                // get请求地址
                url: '../tvacfactory/getAllData',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result=data.page;
                    $("#factory").append(" <option value='-1'></option>")
                    $.each(result, function (i, n) {

                        $("#factory").append(" <option value=\"" + n.factoryCode + "\">" + n.factoryName + "</option>");
                    })
                    $("#factory").selectpicker('refresh');
                }
            });

        },

        saveOrUpdate: function (event) {
            var vacinneId=$("#fkVaccineCode").val();
            var  chilCode=$("#chilCode").val();
            var inocInplId=$("#inocInplId").val();
            // var inoculateRoad=$("#inoculateRoad").val();
            var inocOpinion=$("#inocOpinion").val();
            var inocPropertyCode=$("#inocProperty").val();

            Vue.set(vm.tChildInoculate, 'inocBactCode', vacinneId);
            Vue.set(vm.tChildInoculate,'chilCode',chilCode);
            Vue.set(vm.tChildInoculate,'inocInplId',inocInplId);
            // Vue.set(vm.tChildInoculate,'inoculateRoad',inoculateRoad);
            Vue.set(vm.tChildInoculate,'inocOpinion',inocOpinion);
            Vue.set(vm.tChildInoculate,'inocProperty',inocPropertyCode);


            var url = vm.tChildInoculate.id == null ? "../tchildinoculate/save" : "../tchildinoculate/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.tChildInoculate),
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
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
                                $("#jqGrid").trigger("reloadGrid");
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
                vm.tChildInoculate = r.tChildInoculate;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{    'chilCode':vm.q.chilCode},
                page:page
            }).trigger("reloadGrid");
        }
    }
});

$("#jqGrid").on("mouseover", 'tr[role="row"]', function () {
    var trid = $(this).attr("id");
    //里面写相关的操作。
    var rowData = $("#jqGrid").jqGrid('getRowData', trid);
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