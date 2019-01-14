var chilCode;//全局儿童
var registerVM = new Vue({
    el: '#childrenBody',
    data: {
        q:{
            chilCode: null,
            chilName:null,
            chilCommittee :null,
            chilBirthdayStart:null,
            chilBirthdayEnd:null,
            chilHere:null
        },
        ino:{
            inocBactCode:null
        },

    },
    created: function() {
        //根据儿童编码查询儿童的信息展示
        var widthNum = $(window).width() - 200 + 'px';
        $(".re-wid").css({
            width: widthNum
        });
    },
    methods: {
        query: function () {
            // Bus.$on('pushCurrentNo',function (a) {  //使用on监听事件 a-msg，这样当a组件中使用 emit主动触发了 Event实例的a-msg事件之后，这里就可以接收到
            //     alert('触发了接收'+a);
            // });
            var flag = checkParam();
            if (!flag){
                alert("请输入查询条件");
                return;
            }
            registerVM.reload();
        },
        resetCondition: function(){
            registerVM.q = {
                chilCode: null,
                chilName:null,
                chilCommittee :null,
                chilBirthdayStart:null,
                chilBirthdayEnd:null,
                chilHere:null
            }

        },
        reload: function (event) {
            var page = $("#selectResultTable").jqGrid('getGridParam', 'page');
            $("#selectResultTable").jqGrid('setGridParam',{
                postData: registerVM.q,
                page: page,
                url: '../child/list'
            }).trigger("reloadGrid");
            if($("#chilForm").data('bootstrapValidator')!=null) {
                $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
            }
        }

    }
});

$(function () {
    //儿童查询结果
    $("#selectResultTable").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            {label: '儿童编码', name: 'chilCode', width: 170, key: true},
            {label: '姓名', name: 'chilName', width: 80,align:'center'},
            {label: '性别', name: 'chilSex', width: 60,align:'center'},
            {label: '出生日期', name: 'chilBirthday', width: 150,align:'center'},
            {label: '出生体重（kg）', name: 'chilBirthweight', width: 100,align:'center'},
            {label: '民族', name: 'chilNatiId', width: 60,align:'center'},
            {label: '免疫卡号', name: 'chilCardno', width: 80,align:'center'},
            {label: '母亲姓名', name: 'chilMother', width: 80,align:'center'},
            {label: '父亲姓名', name: 'chilFather', width: 80,align:'center'},
            {label: '行政村/居委会', name: 'chilCommittee', width: 80,align:'center'},
            {
                label: '户籍地址', name: 'chilHabiaddress', width: 180,align:'center'
            },
            {
                label: '现居地址', name: 'chilAddress', width: 180,align:'center'
            },
            {label: '家庭电话', name: 'chilTel', width: 80,align:'center'},
            {label: '手机', name: 'chilMobile', width: 100,align:'center'},
            {label: '学校', name: 'chilSchool', width: 80,align:'center'},
            {label: '居住属性', name: 'chilResidence', width: 80,align:'center'},
            {label: '户籍属性', name: 'chilAccount', width: 80,align:'center'},
            {label: '在册情况', name: 'chilHere', width: 80,align:'center'},
            {label: '建档日期', name: 'chilCreatedate', width: 150,align:'center'},
            {label: '建档人', name: 'createUserName', width: 80,align:'center'},
            {label: '出生医院', name: 'chilBirthhospitalname', width: 80,align:'center'},
            {label: '母亲身份证号', name: 'chilMotherno', width: 180,align:'center'},
            {label: '过敏史', name: 'chilSensitivity', hidden: true, width: 100,align:'center'},
            {label: '父亲身份证号', name: 'chilFatherno', width: 180,align:'center'}

        ],
        viewrecords: true,
        height: 100,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 35,
        autowidth: true,
        shrinkToFit: false,
        autoScroll: true,
        multiselect: false,
        pager: "#jqGridPager",
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
        onSelectRow: function (rowid, iRow, iCol, e) {
            var rowData = $("#selectResultTable").jqGrid('getRowData', rowid);
            $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + rowid+"&birthdays="+rowData.chilBirthday);
            chilCode = rowid;
            //历次接种记录
            //getHistoryInoculate(rowid)

        }
    });


    //接种记录查询结果
    $("#historyTable").jqGrid({
        url: '',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', key: true, hidden: true, width: 150},
            {label: '疫苗名称', name: 'inocBactCode', width: 150, editable: true},
            {label: '批号', name: 'inocBatchno', width: 100},
            {label: '剂次', name: 'inocTime', width: 40},
            {label: '接种属性', name: 'inocProperty', width: 80},
            {label: '接种日期', name: 'inocDate', width: 150},
            {label: '接种部位', name: 'inocInplId', width: 80},
            {label: '接种单位', name: 'inocDepaCode', width: 150},
            /* { label: '疫苗失效期', name: 'inocValiddate', width: 85 },*/
            {label: '生产企业', name: 'inocCorpCode', width: 80},
            {label: '是否免费', name: 'inocFree', width: 70,
                formatter: function (value) {
                    if (value == 1) {
                        return "免费";
                    } else if (value == 0) {
                        return "收费";
                    } else {
                        return "";
                    }
                },
                unformatter: function (cellvalue, options, cellobject) {
                    return cellvalue;
                }
            },

            {label: '接种护士', name: 'inocNurse', width: 80}
        ],
        viewrecords: true,
        height: 300,
        rowNum: 40,
        rowList: [40, 50, 80],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
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
            // $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

    updateVue.loadInocBactCode();
    //getAefiBactCode();//疫苗
    updateVue.inoculateRoadData();//接种属性
    updateVue.factory();//生产厂家
    updateVue.loadInoculateSiteData();//初始化时加载接种部位到全局数组中
    $("#inocDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        updateVue.ino.inocDate = $("#inocDate").val();
    });

});
var updateVue = new Vue({
    el: '#updateIno',
    data: {
        ino:{
            id:null,
            inocBactCode:null,
            inocBatchno:null,
            inocTime:null,
            inocProperty:null,
            inocDate:null,
            inocInplId:null,
            inocDepaCode:null,
            inocModifyCode:null,
            inocCorpCode:null,
            inocFree:null,
            remark:null,
            chilCode:null,
            inocNurse:null
        },
        inocBactCode:new Array(),
        inocCorpCode:new Array()
    },
    methods: {
        /*reload:function(event){
            getHistoryInoculate(chilCode);
        },*/
        loadInocBactCode: function () {
            //初始化下拉框
            $('#inocBactCode').selectpicker({
                'selectedText': 'cat',
                search:false,
            });
            $.ajax({
                url: '../tvacinfo/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con = '';
                    var seldata = results.page.list;
                    updateVue.inocBactCode = seldata;
                    $.each(seldata, function (i, n) {
                        $("#inocBactCode").append(" <option value=\"" + n.bioCode + "\">" + n.bioCnSortname + "</option>");
                    })
                    $("#inocBactCode").selectpicker('refresh');
                }
            });
        },
        loadInoculateSiteData:function() {
            $.ajax({
                url: '../child/listdiccode?ttype=code_inoculation_site',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#inocInplId").append(con);


                }
            });

        },
        inoculateRoadData:function(){
            $.ajax({
                url: '../child/listdiccode?ttype=code_vaccine_property',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#inocProperty").append(con);


                }
            });
        },
        factory:function() {
            //初始化下拉框
            $('#inocCorpCode').selectpicker({
                'selectedText': 'cat',
                search:false,
            });
            $.ajax({
                type:"post",
                url:"../tvacfactory/getAllData",
                dataType:'json',
                async: false,
                contentType:'application/json;charset=UTF-8',
                success: function (data) {
                    registerVM.factoryArr = data.page;
                    updateVue.inocCorpCode = data.page;
                    $.each(data.page, function (i, n) {
                        $("#inocCorpCode").append(" <option value=\"" + n.factoryCode + "\">" + n.factoryCnName + "</option>");
                    });
                    $("#inocCorpCode").selectpicker('refresh');
                }
            });
        }
    }
});

//修改
function single () {
   $("#remark").val('');
    var grid = $("#inoculation_iframe").contents().find("#inoculationGrid");
    //$("#ifm").contents().find("#inoculationGrid").click();//jquery 方法1
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    var rowData = grid.getRowData(rowKey);
    var gridChild = $("#selectResultTable");
    var rowKeyChild = gridChild.getGridParam("selrow");
   /* $.ajax({//修改前下载完整数据
        type: "POST",
        url: "../provincePlatform/saveToLocal?childCode="+rowKeyChild,
        dataType:"json",
        success: function(r){
            console.log(r);
            if(r.code == 0 || r.msg=='查不到该儿童'){*/
                $.ajax({
                    type:"post",
                    url:"../tchildinoculate/info/"+rowData.id,
                    dataType: 'json',
                    success:function (data) {
                        updateVue.ino = data.tChildInoculate;
                        /*if(orgId!=data.tChildInoculate.inocDepaCode && data.tChildInoculate.type!=3){
                            alert("不能修改非本点接种记录");
                            return;
                        }*/
                        updateVue.ino.inocModifyCode = orgName,
                        $('#inocBactCode').selectpicker('val',(updateVue.ino.inocBactCode));
                        $('#inocCorpCode').selectpicker('val',(updateVue.ino.inocCorpCode));

                        $('#myModal').modal('show');
                    }
                });
            /*}else{
                alert(r.msg);
            }*/
      /*  }
    });*/


}
//保存
function saveIno(){
    var remarks = $("#remark").val();
    if(remarks.trim().length<2){
        alert("请输入修改原因");
        return;
    }
    var date = updateVue.ino.inocDate;
    if(date.length==8){
        date = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" 00:00:00";
        Vue.set(updateVue.ino, 'inocDate', date);
    }
    if(date.length==10){
        date = date+" 00:00:00";
        Vue.set(updateVue.ino, 'inocDate', date);
    }
    /*var bagin_r = updateVue.ino.inocDate.match(/^(\d{4})(-)(\d{2})(-)(\d{2})$/);

    if(bagin_r==null){
        alert("请输入正确的开始时间格式,如:2017-01-01");
        return false;
    }
    var b_d=new Date(bagin_r[1],bagin_r[3]-1,bagin_r[5]);
    var b_num = (b_d.getFullYear()==bagin_r[1]&&(b_d.getMonth()+1)==bagin_r[3]&&b_d.getDate()==bagin_r[5]);

    if(b_num==0){
        alert("开始时间不合法,请输入正确的开始时间");
        return false;
    }

    CheckAdd(updateVue.ino.inocDate);*/
    Vue.set(updateVue.ino, 'remark', remarks);
    Vue.set(updateVue.ino, 'inocModifyCode', orgId);
    Vue.set(updateVue.ino, 'chilCode', chilCode);
    Vue.set(updateVue.ino, 'inocBactCode', $("#inocBactCode").val());
    Vue.set(updateVue.ino, 'inocCorpCode', $("#inocCorpCode").val());
   /* layer.confirm('保存后上传，确定要保存么？', function(index) {*/
        layer.msg('正在保存...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 100000});
        $.ajax({
            type: "POST",
            url: "../tchildinoculate/saveAsBackUp",
            data: JSON.stringify(updateVue.ino),
            contentType: 'application/json;charset=UTF-8',
            success: function (r) {
                if (r.code === 0) {
                    layer.msg("保存成功");
                    $('#myModal').modal('hide');
                    $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + chilCode);
                    return;
                    /*$.ajax({
                        type: "POST",
                        url: "../provincePlatform/uploadPlat?childCode=" + chilCode,//http://"$webPath/request/pagelist/SysChild/selectWithPageForList.jhtml?"+params,
                        dataType: "json",
                        success: function (result) {
                            if (result.msg == "1") {
                                layer.msg("保存成功");
                                $('#myModal').modal('hide');
                                $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + chilCode);
                                return;
                            }
                            else if (result.msg == "0") {
                                layer.msg("保存失败");
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
                            layer.msg("上传出现错误，请检查网络");
                            /!* layerFn.center(AppKey.code.code199, "没有查询到儿童相关信息!");*!/
                        }
                    });*/
                    /* alert('操作成功', function (index) {
                         $('#myModal').modal('hide');
                         updateVue.reload();
                     });*/
                } else {
                    layer.msg("备份失败");
                }
            }
        });
        /*layer.close(index);*/
   /* });*/
}
//删除
function deleteIno(){
    var grid = $("#inoculation_iframe").contents().find("#inoculationGrid");
    //$("#ifm").contents().find("#inoculationGrid").click();//jquery 方法1
    var id = grid.getGridParam("selrow");
    if(!id){
        alert("请选择一条记录");
        return ;
    }
    //var id = getSelectedRowsIno();
    var ids = [];
    ids.push(id);
    if(id == null){
        return ;
    }
    $.ajax({
        type:"post",
        url:"../tchildinoculate/info/"+id,
        dataType: 'json',
        success:function (data) {
           /* if(orgId!=data.tChildInoculate.inocDepaCode && data.tChildInoculate.type!=3){
                alert("不能删除非本点接种记录");
                return;
            }*/
            layer.prompt({title: '请输入删除原因'}, function (reson, index) {
                if (reson == null || reson.trim().length < 2) {
                    layer.msg("请输入删除原因！");
                    return;
                }
                layer.close(index);
               /* layer.confirm('确定后保存并上传，确定要保存么？', function(index) {*/
                    $.ajax({
                        type: "POST",
                        url: "../tchildinoculate/delete?remark=" + reson,
                        data: JSON.stringify(ids),
                        contentType: 'application/json;charset=UTF-8',
                        success: function (r) {
                            if (r.code == 0) {
                          /*      $.ajax({
                                    type: "POST",
                                    url: "../provincePlatform/uploadPlat?childCode=" + chilCode,//http://"$webPath/request/pagelist/SysChild/selectWithPageForList.jhtml?"+params,
                                    dataType: "json",
                                    success: function (result) {
                                        if (result.msg == "1") {
                                            layer.msg("删除成功");
                                            $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + chilCode);
                                            return;
                                        }
                                        else if (result.msg == "0") {
                                            layer.msg("删除失败");
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
                                        layer.msg("上传出现错误，请检查网络");
                                        /!* layerFn.center(AppKey.code.code199, "没有查询到儿童相关信息!");*!/
                                    }
                                });*/
                                layer.msg("删除成功");
                                $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + chilCode);
                                return;
                                /*alert('操作成功', function (index) {
                                    getHistoryInoculate(chilCode);
                                });*/
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                    layer.close(index);
               /* });*/
            })
        }
    });

}
//关闭t_child_inoculate_update_record
function colsedialog() {
    $('#myModal').modal('hide');
}

//获取历次接种记录
/*function getHistoryInoculate(chilCode) {
    var grid = $("#inoculation_iframe").contents().find("#inoculationGrid");
    grid.jqGrid("clearGridData");
    var page = grid.jqGrid('getGridParam', 'page');
    grid.jqGrid('setGridParam',{
        postData: {},
        page: page,
        url:'../tchildinoculate/list?limit=1000&page=1&chilCode='+chilCode,
    }).trigger("reloadGrid");
}*/
//查询条件判断
function checkParam() {
    if (registerVM.q.chilCode != null) {
        return true;
    }else if (registerVM.q.chilName != null) {
        return true;
    } else if (registerVM.q.chilCommittee != null) {
        return true;
    } else if (registerVM.q.chilBirthdayStart != null) {
        return true;
    } else if (registerVM.q.chilBirthdayEnd != null) {
        return true
    } else if (registerVM.q.chilHere != null) {
        return true;
    }else {
        return false;
    }
}

//选择1条记录
function getSelectedRowsIno() {
    var grid = $("#historyTable");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    return rowKey;
}

function RQcheck(RQ) {
    var date = RQ;
    //(-|\/)分隔符
    var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if (result == null)
        return false;
    var d = new Date(result[1], result[3] - 1, result[4]);
    return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);
}
function CheckAdd(date) {
    var ret = true;
    if (!RQcheck(date)) {
        alert("请输入正确的日期");
        return;
    }
    return ret;
}

$(function(){
    // 疫苗拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#inocBactCode").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#inocBactCode");
        var value = updateVue.inocBactCode;
        //清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].bioCnSortname.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
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
                con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });

    // 疫苗生产厂家拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#inocCorpCode").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#inocCorpCode");
        var value = updateVue.inocCorpCode;
        //清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].factoryCnName.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
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
                con += '<option  value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });
})
