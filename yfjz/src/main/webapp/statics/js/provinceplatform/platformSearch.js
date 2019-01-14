/**
 * @author 饶士培
 * @time 2018-08-21
 * @type {Array}
 */
var dataCache = [];//定义缓存平台数据
var bioObjCache = []//缓存疫苗信息//用于显示疫苗名称
var department = [];//缓存部门信息
var nations = [];//民族
var child_info_status = {"":""};//个案状态
var child_residence_code = {"":""};//居住属性
var movetype_code = {"":""};//户籍属性
$(function () {
    $.ajax({
        type:'post',
        url: '../sys/dict/queryResultTable',
        //data:platformSearchVM.q,
        dataType:"json",
        async:false,
        success: function(data){
            console.log(data);
            for(var info in data.data.child_info_status){
                child_info_status[data.data.child_info_status[info].value] = data.data.child_info_status[info].text;
            }
            for(var residence in data.data.child_residence_code){
                child_residence_code[data.data.child_residence_code[residence].value] = data.data.child_residence_code[residence].text;
            }
            for(var movetype in data.data.movetype_code){
                movetype_code[data.data.movetype_code[movetype].value] = data.data.movetype_code[movetype].text;
            }
            /*child_info_status = data.data.child_info_status;
            child_residence_code = data.data.child_residence_code;
            movetype_code = data.data.movetype_code;*/

        },
        error:function(r){

        }
    });
    console.log(child_info_status);
    console.log(child_residence_code);
    console.log(movetype_code);
    //儿童查询结果
    $("#queryResultTable").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            { label: '儿童编码', name: 'chilCode', width: 170, key: true,align:'center' },
            { label: '姓名', name: 'chilName', width: 80,align:'center' },
            { label: '性别', name: 'chilSex', width: 50,align:'center',formatter:function (value) {
                    if (value == 1) {
                        return "男";
                    } else if (value == 2) {
                        return "女";
                    } else {
                        return "";
                    }
                } },
            { label: '出生日期', name: 'chilBirthday', width: 150,align:'center' },
            { label: '出生体重（kg）', name: 'chilBirthweight', width: 100,align:'center' },
            { label: '民族', name: 'chilNatiId', width: 60,align:'center',formatter:function (value) {
                    for(var i = 0;i < nations.length;i++){

                        if(nations[i].value == value){
                            return nations[i].text;
                        }
                        if(value.trim()==""){
                            return "";
                        }
                    }
                } },
            { label: '免疫卡号', name: 'chilCardno', width: 80,align:'center' },
            { label: '母亲姓名', name: 'chilMother', width: 80,align:'center' },
            { label: '父亲姓名', name: 'chilFather', width: 80,align:'center' },
            { label: '家庭电话', name: 'chilTel', width: 80,align:'center' },
            { label: '手机', name: 'chilMobile', width: 100,align:'center' },
            { label: '居住属性', name: 'chilResidence', width: 100,align:'center',editable:true,edittype:'select',search : false,
                editoptions :{value:child_residence_code},
                unformat:function( cellvalue, options, cell){
                    for(var i in child_residence_code){
                        if(cellvalue == child_residence_code[i]){
                            return i;
                        }
                    }
                    return cellvalue;
                },formatter:function (value) {
                    for(var i in child_residence_code){
                        if(value == i){
                            return child_residence_code[i];
                        }
                    }
                    return value;
                },},

            { label: '在册情况', name: 'chilHere', width: 122,
                editable:true,edittype:'select',search : false,editoptions :{value:child_info_status},
                unformat:function( cellvalue, options, cell){
                    for(var i in child_info_status){
                        if(cellvalue == child_info_status[i]){
                            return i;
                        }
                    }
                    return cellvalue;
                },formatter:function (value) {
                    for(var i in child_info_status){
                        if(value == i){
                            return child_info_status[i];
                        }
                    }
                    return value;
                },},
            { label: '户籍属性', name: 'chilAccount', width: 100,align:'center',editable:true,edittype:'select',search : false,
                editoptions :{value:movetype_code},
                unformat:function( cellvalue, options, cell){
                    for(var i in movetype_code){
                        if(cellvalue == movetype_code[i]){
                            return i;
                        }
                    }
                    return cellvalue;
                },formatter:function (value) {
                    for(var i in movetype_code){
                        if(value == i){
                            return movetype_code[i];
                        }
                    }
                    return value;
                },},
            { label: '建档日期', name: 'chilCreatedate', width: 100,align:'center',
                formatter:function (value) {
                    return value.substring(0, value.length - 8);

                }
            },
            { label: '操作', name: 'oper',align:'center', width: 90,formatter:function () {
                    return "<a href=\"javascript:void(0);\" onclick=\"platformSearchVM.downloadChild(" + 1 + ")\">下载到本地</a>"
                }}
        ],
        viewrecords: true,
        height: 100,
        rowNum: 30,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
        shrinkToFit:false,
        autoScroll: true,
        multiselect: false,
        /* pager: "#jqGridPager",*/
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

            //var ids = $("queryResultTable").jqGrid('getDataIDs');
            //$("#queryResultTable").jqGrid('setSelection', 1);  //无法选中
            if(dataCache[0]!=null) {
                $("#queryResultTable").setSelection(dataCache[0].chilCode, false);
            }
            //$("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        onSelectRow:function(rowid,iRow,iCol,e){
            loadInoculateData(rowid);
            var rowData = $("#queryResultTable").jqGrid('getRowData',rowid);
            //判断是否要开启编辑
            if(!movetype_code.hasOwnProperty(rowData.chilAccount)){//户籍属性
                $('#queryResultTable').setColProp('chilAccount', {
                    editable: false
                });
            }
            if(!child_info_status.hasOwnProperty(rowData.chilHere)){//个案状态
                $('#queryResultTable').setColProp('chilHere', {
                    editable: false
                });
            }
            if(!child_residence_code.hasOwnProperty(rowData.chilResidence)){//居住属性
                $('#queryResultTable').setColProp('chilResidence', {
                    editable: false
                });
            }

            $('#queryResultTable').editRow(rowid, true);//开启行编辑
        },
        /*onCellSelect:function(rowid,iCol,cellcontent,e){
            console.log(rowid);
            $('#queryResultTable').editRow(rowid, true);
        }*/
    });
    bactFormatter();//加载疫苗信息
    getDepart();//获取接种机构信息
    getNations();//获取民族
    //接种记录查询结果
    $("#inoculationGrid").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id',key:true,hidden:true, width: 150 },
            { label: '疫苗编码', name: 'inocBactCode', width: 150,formatter:function (value) {
                    for(var i = 0; i<bioObjCache.length;i++){
                        if (bioObjCache[i].bioCode == value) {
                            return "(" + value + ")" + bioObjCache[i].bioCnSortname;
                        }
                    }

                }
            },
            { label: '批号', name: 'inocBatchno', width: 100 },
            { label: '剂次', name: 'inocTime', width: 40 },
            { label: '接种属性', name: 'inocProperty', width: 70,
                formatter:propertyFomatter
            },
            { label: '接种日期', name: 'inocDate', width: 140 },
            { label: '联合疫苗编码', name: 'inocUnionCode', width: 90 },
            { label: '接种部位', name: 'inocInplId', width: 70,
                formatter:posFormatter
            },
            { label: '接种县国标', name: 'inocCounty', width: 80 },
            { label: '接种单位编码', name: 'inocNationcode', width: 90 },
            /* { label: '疫苗失效期', name: 'inocValiddate', width: 85 },*/
            /* { label: '生产企业', name: 'inocCorpCode', width: 80 },*/
            { label: '是否免费', name: 'inocFree', width: 60,
                formatter:function (value) {
                    if(value == 1){
                        return "免费";
                    }else if(value == 0){
                        return "收费";
                    }else {
                        return "";
                    }
                }
            },

            { label: '接种人员', name: 'inocNurse', width: 70 },
            { label: '录入日期', name: 'inocEditdate', width: 140 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 50,
        rowList : [50,60,80],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        // multiselect: true,
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
            //隐藏grid底部滚动条
            //$("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    //个案状态
    $("#moveGrid").jqGrid({
        url: '',
        datatype: "json",
        colModel: [

            { label: '现在册情况', name: 'chheHere', width: 100,
                formatter:childHereFormatter
            },
            { label: '原在册情况', name: 'chhePrehere', width: 100,
                formatter:childHereFormatter
            },
            /*{ label: '出省状态', name: 'chheChilProvince', width: 80 },*/
            { label: '变更时间', name: 'changedate', width: 150 },
            { label: '变更单位', name: 'chheDepaId', width: 180,
                formatter:function (value) {
                    for(var i = 0;i < department.length;i++){
                        var departId=department[i].id;
                        if(departId==value){
                            return department[i].name;
                        }
                        if(i==department.length-1){
                            return '';
                        }
                    }
                }
            },
            { label: '变更原因', name: 'chheChilProvinceremark', width: 80 }

        ],
        viewrecords: true,
        height: 385,
        rowNum: 40,
        rowList : [40,50,70],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        /*caption:'变更记录',*/
        hidegrid:true,
        multiselect: true,
        // pager: "#moveGridPager",
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
            $("#moveGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

});

var platformSearchVM = new Vue({
    el: '#platformSearch',
    data: {
        currentIndex: 0,
        q:{
            childCode:chilCode=="null"?null:chilCode,
            childName:null,
            sex:null,
            birthday:null,
            motherName:null,
            fatherName:null
        },

        showList: true,
        title: null,
        childCode:null,
        tChildInfo: {},
        recommend:{}
    },
    methods: {
        query: function () {
            var flag = checkParam();
            if (!flag){
                alert("请输入查询条件");
                return;
            }
            platformSearchVM.showList = true;
            platformSearchVM.reload();
        },
        resetCondition: function(){
            platformSearchVM.q = {
                childCode: null,
                childName:null,
                birthday:null,
                motherName:null,
                fatherName:null,
                sex:null
            }

        },
        reload: function (event) {
            platformSearchVM.showList = true;
            layer.msg("正在查询...");
            $.ajax({
                type:'post',
                url: '../provincePlatform/findChildOnProvincePlatform',
                data:platformSearchVM.q,
                dataType:"json",
                success: function(r){
                    console.log(r);
                    if(r.code==500){
                        layer.msg("暂无数据!");
                        dataCache = [];
                        $("#queryResultTable").jqGrid("clearGridData");
                        return;
                    }
                    layer.msg("查询成功!");
                    dataCache = r.page.list;//缓存数据
                    var arr=r.page.list[0].inoculationList;
                    for (var  i=0;i<arr.length;i++){
                        var re=/^[0-9]+.?[0-9]*$/;
                        if (re.test(r.page.list[0].inoculationList[i].inocUnionCode)){
                           console.log(i);
                            arr.remove(r.page.list[0].inoculationList[i])
                            for (var i=0;i<arr.length;i++){
                                if (re.test(r.page.list[0].inoculationList[i].inocUnionCode)) {
                                    arr.remove(r.page.list[0].inoculationList[i])
                                }
                            }
                        }
                    }
                    $("#queryResultTable").jqGrid("clearGridData");
                    $("#queryResultTable").setGridParam({datatype : 'local',data: r.page.list}).trigger('reloadGrid');
                    $("#inoculationGrid").jqGrid("clearGridData");
                    $("#inoculationGrid").setGridParam({datatype : 'local',data: arr}).trigger('reloadGrid');
                    $("#tabooGrid").jqGrid("clearGridData");
                    $("#tabooGrid").setGridParam({datatype : 'local',data: r.page.list[0].istabuList}).trigger('reloadGrid');
                    $("#infectionGrid").jqGrid("clearGridData");
                    $("#infectionGrid").setGridParam({datatype : 'local',data: r.page.list[0].infectionList}).trigger('reloadGrid');
                    $("#abnormalGrid").jqGrid("clearGridData");
                    $("#abnormalGrid").setGridParam({datatype : 'local',data: r.page.list[0].aefiList}).trigger('reloadGrid');
                    $("#moveGrid").jqGrid("clearGridData");
                    $("#moveGrid").setGridParam({datatype : 'local',data: r.page.list[0].childHeresList}).trigger('reloadGrid');
                }
            });
            //$("#chilForm").data('bootstrapValidator').resetForm("chilForm");
        },
        downloadChild: function () {
            //endEdit($("#queryResultTable"));
            var rowId = $("#queryResultTable").jqGrid('getGridParam','selrow');
            //$("#queryResultTable").saveRow(rowId);//保存行数据
            if (rowId == null || rowId == undefined) {
                layer.msg("请选择一个儿童！")
                return;
            }
            var chilAccount = $('#' + rowId + "_chilAccount option:selected").val();
            var chilHere = $('#' + rowId + "_chilHere option:selected").val();
            var chilResidence = $('#' + rowId + "_chilResidence option:selected").val();
            var getRowData = $("#queryResultTable").getRowData(rowId);
            if(chilAccount == null || chilAccount == undefined){
                chilAccount = getRowData.chilAccount;
            }
            if(chilHere == null || chilHere == undefined){
                chilHere = getRowData.chilHere;
            }
            if(chilResidence == null || chilResidence == undefined){
                chilResidence = getRowData.chilResidence;
            }
            console.log(chilHere);
            console.log(chilResidence);
            layer.msg("正在下载...");
            $.ajax({
                type: "POST",
                url: "../provincePlatform/saveToLocal?childCode="+dataCache[0].chilCode,
                data:{"chilAccount":chilAccount,"chilHere":chilHere,"chilResidence":chilResidence},
                dataType:"json",
                success: function(r){
                    console.log(r);
                    if(r.code == 0){
                        layer.msg("下载成功");
                        var chilCode= dataCache[0].chilCode;
                        if(parent.registerVM!=null) {
                            parent.registerVM.showList = true;
                            var page = parent.$("#selectResultTable").jqGrid('getGridParam', 'page');
                            parent.$("#selectResultTable").jqGrid('setGridParam', {
                                postData: {
                                    'chilCode': chilCode,
                                    'chilName': null,
                                    'chilBirthdayStart': null,
                                    'chilBirthdayEnd': null
                                },
                                page: page,
                                url: '../child/list'
                            }).trigger("reloadGrid");
                            var index = parent.layer.getFrameIndex(window.name);
                            setTimeout(function () {
                                return parent.layer.close(index)
                            }, 1000);
                        }
                        if(parent.vm!=null) {
                            /*
                         * 先清空条件
                         * jqgrid postData setGridParam 调用多次时查询条件会累加
                         */
                            var postData = $('#jqGrid').jqGrid("getGridParam", "postData");
                            $.each(postData, function (k, v) {
                                delete postData[k];
                            });
                            parent.vm.showList = true;
                            var page = parent.$("#jqGrid").jqGrid('getGridParam', 'page');
                            parent.$("#jqGrid").jqGrid('setGridParam', {
                                postData: {
                                    'chilCode': chilCode,
                                    'chilName': null,
                                    'chilBirthdayStart': null,
                                    'chilBirthdayEnd': null
                                },
                                page: page,
                                url: '../child/list'
                            }).trigger("reloadGrid");
                            var index = parent.layer.getFrameIndex(window.name);
                            setTimeout(function () {
                                return parent.layer.close(index)
                            }, 1000);
                        }
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getInfo: function(chilCode){
            $.ajax({
                type: "GET",
                url: "../child/info/"+chilCode,
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    /* reSetAddress(r);*/
                    platformSearchVM.tChildInfo = r.tChildInfo;

                }
            });
        },
        renderSelect:function(){

        },
        //点击删除选择的列表  弹框中的推荐疫苗列表
        removeAddRegister:function(rowId,rowdata){
            //移除jqgrid指定行的数据
            $("#recommendTable").delRowData(rowId);
            platformSearchVM.removeObj(platformSearchVM.recommend,rowdata);
        },
        // 刷新
        refresh: function() {

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
        /**
         * 获取推荐数组中元素的下标
         * @param _arr 数组
         * @param _obj 需要查找的对象
         * @returns {number}
         */
        removeObj: function (_arr,_obj) {
            for(var i = 0; i < _arr.length; i++){
                if(_arr[i].id == _obj.id){//判断对象中的属性的id相同的对象在数组中的下标值
                    platformSearchVM.recommend.splice(i, 1);
                }
            }
        }
    }
});
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};


function getAge(birthTime) {
    var age = getDateTimeDiff(new Date(birthTime));
    var age_str = age.year > 0 ? age.year + "年" : "";
    age_str += age.month > 0 ? age.month + "月" : "";
    if(!isNaN(parseInt(age_str))){
        age_str += age.day + "日";
        //$("#monthOld").val(age_str);
        return age_str;
    }else{
        age_str += age.day > 0 ? age.day + "日" : "";
        var time1 = new Date(birthTime).Format("yyyy-MM-dd");
        var time2 = new Date().Format("yyyy-MM-dd");
        if(time1 == time2){
            return "0年0月0日";
        }else{
            return age_str;
        }
    }
}


Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
// 计算出生时间
function getDateTimeDiff(startTime) {
    var now = new Date();
    var result = {};
    result.year = now.getFullYear() - startTime.getFullYear();
    //result.month = now.getMonth() - startTime.getMonth();
    if (now.getMonth() >= startTime.getMonth()) {
        result.month = now.getMonth() - startTime.getMonth();
    } else {
        result.year--;
        result.month = 12 - startTime.getMonth() + now.getMonth();
    }
    if (now.getDate() >= startTime.getDate()) {//如果大于出生那天
        result.day = now.getDate() - startTime.getDate();
    } else {
        if (result.month > 0) {
            result.month--;
        } else {
            result.year--;
            result.month = 12 - (startTime.getMonth() - now.getMonth()) - 1;
        }
        result.day = getMonthLastDay(startTime.getFullYear(), startTime
                .getMonth() + 1)
            - startTime.getDate() + now.getDate();
    }
    return result;
}

//获取某月份的最后一天
function getMonthLastDay(year, month) {
    return new Date(new Date(year, month + 1, 1).getTime() - 3600000 * 24).getDate();
}

function getLastInoTime(parm) {
    var lastInoTime = null;
    //获取上一次接种时间
    $.ajax({
        url: "../tchildinoculate/LastInoInfo/" + parm,
        dataType: 'json',
        success: function (data) {
            try{
                $("#lastInoTime").append(platformSearchVM.dateFormatter(data.tChildInoculate.inocDate));
            }catch (E){}

        }
    });

}

//选择一条记录
function getSelectedRow_child() {

    var grid = $("#selectResultTable");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return rowKey;
}

//查询条件判断
function checkParam() {
    if (platformSearchVM.q.childCode != null) {
        return true;
    }else if (platformSearchVM.q.childName != null) {
        return true;
    } else if (platformSearchVM.q.motherName != null) {
        return true;
    } else if (platformSearchVM.q.fatherName != null) {
        return true;
    } else if (platformSearchVM.q.sex != null) {
        return true
    } else if (platformSearchVM.q.birthday != null) {
        return true;
    }else {
        return false;
    }
}

//疫苗编码formatter
function bactFormatter() {
    $.ajax({
        type:'post',
        url:'../tvacinfo/list?page=1&limit=1000',
        dataType:'json',
        success:function (data) {
            bioObjCache = data.page.list;
        }
    });
}
//获取部门信息
function getDepart() {
    $.ajax({
        type:'post',
        url:'../sys/depart/listDepart?page=1&limit=8000&sidx=1&order=1',
        dataType:'json',
        success:function (data) {
            for(var i = 0;i<data.page.list.length;i++){
                if(data.page.list[i].id.length==10){
                    department.push(data.page.list[i]);
                }
            }
        }
    });
}

//获取部门信息
function getNations() {
    $.ajax({
        url: '../child/listdiccode?ttype=nation_code',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            nations = results.data;

        }
    });
}
// 接种部位格式化
function posFormatter(value, row, index) {
    if (value == 1) {
        return "左上臂";
    } else if (value == 2) {
        return "右上臂";
    } else if(value == 4){
        return "左大腿";
    }else if(value== 5){
        return "右大腿";
    }else if(value==9){
        return "口服";
    }

    // else if(value==10){
    //     return "不详";
    // }
    else{
        return "";
    }
}

// 接种属性格式化
function propertyFomatter(value, row, index) {
    if (value == 0) {
        return "基础";
    } else if (value == 1) {
        return "加强";
    } else if (value == 2) {
        return "强化";
    } else if (value == 3) {
        return "应急";
    }
}

// 个案状态格式化
function childHereFormatter(value, row, index) {
    if (value == 1) {
        return "本地";
    } else if (value == 2) {
        return "迁出";
    } else if (value == 3) {
        return "临时迁出";
    } else if (value == 4) {
        return "死亡";
    } else if (value == 5) {
        return "删除";
    } else {
        return "临时接种";
    }
}

function loadInoculateData(chilCode){
    $.ajax({
        type:'post',
        url: '../provincePlatform/findChildOnProvincePlatform?childCode='+chilCode,
        data:{},
        dataType:"json",
        success: function(r){
            console.log(r);
            if(r.code==500){
                layer.msg("暂无数据!");
                dataCache = [];
                return;
            }
            //layer.msg("查询成功!");
            dataCache = r.page.list;//缓存数据
            var arr=r.page.list[0].inoculationList;
            for (var  i=0;i<arr.length;i++){
                var re=/^[0-9]+.?[0-9]*$/;
                if (re.test(r.page.list[0].inoculationList[i].inocUnionCode)){
                    console.log(i);
                    arr.remove(r.page.list[0].inoculationList[i])
                    for (var i=0;i<arr.length;i++){
                        if (re.test(r.page.list[0].inoculationList[i].inocUnionCode)) {
                            arr.remove(r.page.list[0].inoculationList[i])
                        }
                    }
                }
            }


            $("#inoculationGrid").jqGrid("clearGridData");
            $("#inoculationGrid").setGridParam({datatype : 'local',data: arr}).trigger('reloadGrid');
            $("#tabooGrid").jqGrid("clearGridData");
            $("#tabooGrid").setGridParam({datatype : 'local',data: r.page.list[0].istabuList}).trigger('reloadGrid');
            $("#infectionGrid").jqGrid("clearGridData");
            $("#infectionGrid").setGridParam({datatype : 'local',data: r.page.list[0].infectionList}).trigger('reloadGrid');
            $("#abnormalGrid").jqGrid("clearGridData");
            $("#abnormalGrid").setGridParam({datatype : 'local',data: r.page.list[0].aefiList}).trigger('reloadGrid');
            $("#moveGrid").jqGrid("clearGridData");
            $("#moveGrid").setGridParam({datatype : 'local',data: r.page.list[0].childHeresList}).trigger('reloadGrid');
        }
    });
}




