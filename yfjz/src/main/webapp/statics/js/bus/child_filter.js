layui.use(['table', 'form'], function () {
    var table = layui.table;
    var form = layui.form;
    var $1 = layui.jquery, layer = layui.layer;
    var repeat = table.render({
        elem: '#test',
        id: "tests",
        url: '../child/listDataChildFilter?data=' + new Date().getTime(),
        method: "POST",
        toolbar: '#barDemo',
        defaultToolbar: false,
        title: '用户数据表',
        //even: true, //开启隔行背景
        //size: 'sm', //小尺寸的表格
        height: 'full-65',
        text: {
            none: "没有查询到有重复儿童数据" //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        cols: [[
            {field: 'id', checkbox: true},
            {field: 'chilCode', title: '儿童编号', align: 'left', width: 200},
            {field: 'chilName', title: '儿童姓名', align: 'left',width: 100},
            {
                field: 'chilSex', title: '性别', width: 45, align: 'left',
                templet: function (value) {
                    if (value.chilSex == 1) {
                        return '男';
                    } else if (value.chilSex == 2) {
                        return '女';
                    } else {
                        return '未知';
                    }
                }
            },
            {field: 'chilBirthday', title: '出生时间', align: 'left',width: 180, sortable: true},
            {
                field: 'chilBirthweight', title: '出生体重(kg)', width: 115, align: 'left',
                templet: function (val) {
                    var text = val.chilBirthweight + "";
                    if (val.chilBirthweight == null) {
                        return '';
                    }
                    if (val.chilBirthweight == 0) {
                        return val.chilBirthweight;
                    }

                    var index = text.indexOf(".");
                    if (text.length == 1) {
                       // return text * 1000;
                        return text;
                    }
                    if (text.length == 3 && index == 1) {
                        //return text * 1000;
                        return text;
                    }
                    if (text.length == 2 && index == -1) {
                        return text * 100;
                    }
                    if (text.length == 3 && index == -1) {
                        return text * 10;
                    }
                    if (text.substring(0, index).length == 1) {
                        //return text * 1000;
                        return text;
                    }
                    if (text.length > 6) {
                        return text / 1000000;
                    }
                    if (index == text.length - 1) {
                        return text.substring(0, text.length - 1);
                    }
                    if (index == text.length - 2) {
                        return text.substring(0, text.length - 2);
                    }
                    else {
                        return text;
                    }
                    return text;
                }
            },
            {field: 'chilMother', title: '母亲姓名', align: 'left',width: 100},

            //{field: 'MOTHERPHONE', title: '母亲电话',  align: 'left'},
            {field: 'chilFather', title: '父亲姓名', align: 'left',width: 100},
            {field: 'chilTel', title: '家庭电话', align: 'left',width: 110},
            {field: 'chilMobile', title: '手机', align: 'left',width: 110},
            /*{field: 'chilNatiId', title: '儿童民族', align: 'left'},*/
            {
                field: 'chilHabiaddress', title: '户籍地址', align: 'left',width: 200
            },
            {field: 'chilNo', title: '儿童身份证号', align: 'left',width: 150},
            {field: 'chilMotherno', title: '母亲身份证号', align: 'left',width: 150},
            {field: 'chilBirthno', title: '出生证号', align: 'left',width: 150},
            //{field: 'ISTEMPORARY', title: '是否临时数据',  align: 'left'},
            {field: 'createTime', title: '添加日期', align: 'left',width: 110},
            {field: 'remark', title: '备注', width: 80, align: 'left',width: 100},
        ]],
        page: {
            first: '首页',
            last: '尾页',
            prev: '上一页',
            next: '下一页',
            layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
        },
        request: {
            pageName: 'pageNo', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
        response: {
            statusName: 'code', //规定数据状态的字段名称，默认：code
            // ,statusCode: 0 //规定成功的状态码，默认：0
            //,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            countName: 'totalCount' //规定数据总数的字段名称，默认：count
            , dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        where: { //设定异步数据接口的额外参数，任意设
            chilSex: 1,
            //chilBirthday: new Date()
            //…
        },
        done: function (res, curr, count) {

        }
        /*parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                //"msg": res.msg, //解析提示文本
                "count": res.page.totalCount, //解析数据长度
                "data": res.page.data //解析数据列表
            };
        }*/
    });

    //头工具栏事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;
                if (data.length < 2) {
                    return layer.msg('请选择需要合并的儿童！！', {icon: 5});
                }
                if (data.length > 2) {
                    return layer.msg('只能选择两个儿童进行合并操作！！', {icon: 5});
                }
                var textData = "";

                $.each(data, function (index, element) {
                    if (textData != "") {
                        return false;
                    }

                    $.each(data, function (index2, element2) {

                        if (element.chilName != element2.chilName) {
                            textData += "姓名";
                        }

                        if (element.chilSex != element2.chilSex) {
                            if (textData.indexOf("姓名") != -1) {
                                textData += "和性别";
                            } else {
                                textData += "性别";
                            }
                        }

                        if (element.chilBirthday != element2.chilBirthday) {

                            if (textData.indexOf("姓名") != -1 || textData.indexOf("性别") != -1) {
                                textData += "和出生日期";
                            } else {
                                textData += "出生日期";
                            }
                        }

                        if (textData != "") {
                            return false;
                        }
                    });
                });
                if (textData != "") {
                    layer.confirm('' + textData + '不一样，确定要合并选择的两个儿童吗？', {icon: 3, title: '系统提示'}, function (index) {
                        layer.close(index);
                        popup(data);
                    });
                } else {
                    popup(data);
                }
                // popup(data);
                break;
        }
        ;
    });

    /*$("#merge").bind("click", function () {

        var checkStatus = table.checkStatus('tests'); //test即为基础参数id对应的值
        var data = checkStatus.data;

    })*/

    var childs;//保存两个儿童的数据查询到的数据
    //查出选择两个儿童的相关数据
    function popup(data) {
        //取消合并保留儿童单选按钮
        $("#save_child1").prop('checked', false);
        $("#save_child2").prop('checked', false);
        form.render();
        var param = {"firstChildCode": data[0].chilCode, "secondChildCode": data[1].chilCode};
        var index = layer.load(1); //换了种风格
        $.ajax({
            url: '../child/getInoculateInfoByChildId',
            dataType: 'json',
            data: param,
            success: function (data) {
                var child = data.data;
                childs = child;
                $("#child_id1").html(child.firstChilHere.chil_code);
                $("#child_name1").html(child.firstChilHere.chil_name);
                $("#zcqk1").html(child.firstChilHere.text);
                $("#dose_no1").html(child.firstChilNeedle.length);
                $("#inoculate_date1").html(child.firstChilDate);

                $("#children01").jqGrid('clearGridData');  //清空表格
                $("#children01").jqGrid('setGridParam', {  // 重新加载数据
                    //datatype:'local',
                    data: child.firstChilNeedle,   //  data 是符合格式要求的需要重新加载的数据
                    rowNum: child.firstChilNeedle.length,
                }).trigger("reloadGrid");


                $("#child_id2").html(child.secondChilHere.chil_code);
                $("#child_name2").html(child.secondChilHere.chil_name);
                $("#zcqk2").html(child.secondChilHere.text);
                $("#dose_no2").html(child.secondChilNeedle.length);
                $("#inoculate_date2").html(child.secondChilDate);

                $("#children02").jqGrid('clearGridData');  //清空表格
                $("#children02").jqGrid('setGridParam', {  // 重新加载数据
                    //datatype:'local',
                    data: child.secondChilNeedle,   //  data 是符合格式要求的需要重新加载的数据
                    rowNum: child.secondChilNeedle.length,
                }).trigger("reloadGrid");


                $.each(child.firstChilNeedle, function (index, time) {
                    var bio_code = false;//默认没有相同的疫苗
                    var bio_code1 = false;//同剂次 同疫苗 同接种日期 标绿色
                    var bio_code3 = false;//同剂次 同疫苗 不同接种日期 标浅红色
                    var bio_code2 = false;//不同剂次 同疫苗 标黄色
                    var bio_code4 = false; //同剂次 同疫苗 标浅黄色 #F0E68C
                    //验证第一个儿童的接种疫苗在第二个儿童的接种疫苗是否有如果没有标红色
                    $.each(child.secondChilNeedle, function (index2, time2) {
                        if (time.bio_code == time2.bio_code) {
                            bio_code = true;//有相同的疫苗
                        }
                        //同剂次 同疫苗 同接种日期 标绿色
                        if (bio_code1 == false && time.bio_code == time2.bio_code && time.inoc_time == time2.inoc_time && time.inoc_date == time2.inoc_date) {
                            bio_code1 = true;
                            //$("#" + time.id).css({"background-color": "green"});
                        }
                    });
                    $.each(child.secondChilNeedle, function (index2, time2) {
                        //同剂次 同疫苗 不同接种日期 标浅红色 #FF1493
                        if (bio_code1 == false && bio_code3 == false && time.bio_code == time2.bio_code && time.inoc_time == time2.inoc_time && time.inoc_date != time2.inoc_date) {
                            bio_code3 = true;
                            //$("#" + time.id).css({"background-color": "#FF1493"});
                        }
                    });
                    $.each(child.secondChilNeedle, function (index2, time2) {
                        //同剂次 同疫苗 标浅黄色 #F0E68C
                        if (bio_code3 == false && bio_code1 == false && bio_code4 == false && bio_code1 == false && time.bio_code == time2.bio_code && time.inoc_time == time2.inoc_time) {
                            bio_code4 = true;
                            //$("#" + time.id).css({"background-color": "yellow"});
                        }
                    });
                    $.each(child.secondChilNeedle, function (index2, time2) {
                        //不同剂次 同疫苗 标黄色
                        if (bio_code1 == false && bio_code3 == false && bio_code4 == false && bio_code2 == false && time.bio_code == time2.bio_code && time.inoc_time != time2.inoc_time) {
                            bio_code2 = true;
                            //$("#" + time.id).css({"background-color": "yellow"});
                        }
                    });

                    if (!bio_code) {
                        $("#" + time.id).css({"background-color": "red"});
                        $("#" + time.id).attr("child","red");
                    }
                    if (bio_code1) {
                        $("#" + time.id).css({"background-color": "green"});
                        $("#" + time.id).attr("child","green");
                    }
                    if (bio_code2) {
                        $("#" + time.id).css({"background-color": "red"});
                        $("#" + time.id).attr("child","red");
                    }
                    if (bio_code3) {
                        $("#" + time.id).css({"background-color": "yellow"});
                        $("#" + time.id).attr("child","yellow");
                    }

                    /*if (bio_code4) {
                       $("#" + time.id).css({"background-color": "#E0F0A0"});
                   }*/
                });


                $.each(child.secondChilNeedle, function (index, time) {
                    var bio_code = false;//默认没有相同的疫苗
                    var bio_code1 = false;//同剂次 同疫苗 同接种日期 标绿色
                    var bio_code3 = false;//同剂次 同疫苗 不同接种日期 标浅红色
                    var bio_code2 = false;//不同剂次 同疫苗 标黄色
                    var bio_code4 = false; //同剂次 同疫苗 标浅黄色 #F0E68C
                    //验证第一个儿童的接种疫苗在第二个儿童的接种疫苗是否有如果没有标红色
                    $.each(child.firstChilNeedle, function (index2, time2) {
                        if (time.bio_code == time2.bio_code) {
                            bio_code = true;//有相同的疫苗
                        }
                        //同剂次 同疫苗 同接种日期 标绿色
                        if (bio_code1 == false && time.bio_code == time2.bio_code && time.inoc_time == time2.inoc_time && time.inoc_date == time2.inoc_date) {
                            bio_code1 = true;
                            //$("#" + time.id).css({"background-color": "green"});
                        }
                    });
                    $.each(child.firstChilNeedle, function (index2, time2) {
                        //同剂次 同疫苗 不同接种日期 标浅红色 #FF1493
                        if (bio_code1 == false && bio_code3 == false && time.bio_code == time2.bio_code && time.inoc_time == time2.inoc_time && time.inoc_date != time2.inoc_date) {
                            bio_code3 = true;
                            //$("#" + time.id).css({"background-color": "#FF1493"});
                        }
                    });
                    $.each(child.firstChilNeedle, function (index2, time2) {
                        //同剂次 同疫苗 标浅黄色 #F0E68C
                        if (bio_code3 == false && bio_code1 == false && bio_code4 == false && bio_code1 == false && time.bio_code == time2.bio_code && time.inoc_time == time2.inoc_time) {
                            bio_code4 = true;
                            //$("#" + time.id).css({"background-color": "yellow"});
                        }
                    });
                    $.each(child.firstChilNeedle, function (index2, time2) {
                        //不同剂次 同疫苗 标黄色
                        if (bio_code1 == false && bio_code3 == false && bio_code4 == false && bio_code2 == false && time.bio_code == time2.bio_code && time.inoc_time != time2.inoc_time) {
                            bio_code2 = true;
                            //$("#" + time.id).css({"background-color": "yellow"});
                        }
                    });

                    if (!bio_code) {
                        $("#" + time.id).css({"background-color": "red"});
                        $("#" + time.id).attr("child","red");
                    }
                    if (bio_code1) {
                        $("#" + time.id).css({"background-color": "green"});
                        $("#" + time.id).attr("child","green");
                    }
                    if (bio_code2) {
                        $("#" + time.id).css({"background-color": "red"});
                        $("#" + time.id).attr("child","red");
                    }
                    if (bio_code3) {
                        $("#" + time.id).css({"background-color": "yellow"});
                        $("#" + time.id).attr("child","yellow");
                    }

                    /*if (bio_code4) {
                       $("#" + time.id).css({"background-color": "#E0F0A0"});
                   }*/
                });


                layer.close(index);
            },
            error: function () {
                layer.close(index);
            }
        })
        //弹出合并框
        layer.open({
            type: 1,
            title: '合并儿童数据',
            shade: 0,
            maxmin: true,
            //zIndex: layer.zIndex,
            area: ['1700px', '705px'],
            anim: 0,
            offset: '30px',
            content: $("#popup") //这里content是一个普通的String
        });
    }

    //根据条件查询儿童数据 监听按钮事件
    $("#searchChild").on("click", function () {
        read();
    })

    //根据条件查询儿童数据
    function read(flush) {
        var chilFather = $("#chilFather").prop("checked");
        var chilMother = $("#chilMother").prop("checked");
        var chilName = $("#chilName").prop("checked");
        if(!flush){
            if (chilFather == false && chilMother == false && chilName == false) {
                return layer.msg('必选一项,没有选择!', {icon: 5});
            }
        }

        var objId = document.getElementById("tt");
        if (objId == undefined) {
            return;
        }
        var reloadValue = {};
        for (var i = 0; i < objId.elements.length; i++) {
            if (objId.elements[i].type == "checkbox" && objId.elements[i].checked == true) {
                reloadValue[objId.elements[i].name] = objId.elements[i].value;
            }
        }
        delete reloadValue.chilBirthday;
        repeat.reload({
            where: reloadValue,
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }

    //重置表单(查询儿童重复数据)
    $("#reset").on("click", function () {
        var objId = document.getElementById("tt");
        if (objId == undefined) {
            return;
        }
        for (var i = 0; i < objId.elements.length; i++) {

            if (objId.elements[i].type == "text") {
                objId.elements[i].value = "";
            }
            else if (objId.elements[i].type == "password") {
                objId.elements[i].value = "";
            }
            else if (objId.elements[i].type == "radio" && objId.elements[i].defaultRadio == false) {
                objId.elements[i].checked = false;
            }
            else if (objId.elements[i].type == "checkbox" && objId.elements[i].defaultChecked == false) {
                objId.elements[i].checked = false;
            }
            else if (objId.elements[i].type == "select-one") {
                objId.elements[i].options[0].selected = true;
            }
            else if (objId.elements[i].type == "select-multiple") {
                for (var j = 0; j < objId.elements[i].options.length; j++) {
                    objId.elements[i].options[j].selected = false;
                }
            }
            else if (objId.elements[i].type == "textarea") {
                objId.elements[i].value = "";
            }
        }
    });

    //合并儿童接种信息消息
    $("#theMergeRecord").on("click", function () {
        var ck = $("input[name='save_child']:checked").val();
        if (ck == undefined || ck == '') {
            layer.msg('请选择需要保留的儿童');
            return;
        }

        var children01 = $("#children01").getDataIDs();
        var children02 = $("#children02").getDataIDs();
        var children01Ids = $("#children01").jqGrid('getGridParam','selarrrow');
        var children02Ids = $("#children02").jqGrid('getGridParam','selarrrow');
        if(children01Ids.length == 0 && children02Ids.length == 0){
            alert("请选择要合并的接种记录");
            return false;
        }
        var cd1 = [];
        var cd2 = [];
        //筛选第一儿童没有选中的接种记录
        $.each(children01,function(index,time){
            var ac = false;
            $.each(children01Ids,function(index2,time2){
                if(time == time2){
                    ac = true;
                    return false;
                }
            });
            if(!ac){
                cd1.push(time);
            }
        });

        //筛选第一儿童没有选中的接种记录
        $.each(children02,function(index,time){
            var ac = false;
            $.each(children02Ids,function(index2,time2){
                if(time == time2){
                    ac = true;
                    return false;
                }
            });
            if(!ac){
                cd2.push(time);
            }
        });

        var colourRed01=0,colourGreen01=0,colourYellow01=0,colourRed02=0,colourGreen02=0,colourYellow02=0;
        var colourRedText = "没有相同疫苗接种记录或有相同疫苗没有相同剂次";
        var colourGreenText = "相同疫苗 相同剂次 相同接种日期";
        var colourYellowText = "相同疫苗 相同剂次 不相同接种日期";
        $.each(cd1,function(index,time){
            var back = $("#" + time).attr("child");
            if(back == "red"){
                colourRed01++;
            }else if(back == "green"){
                colourGreen01++;
            }else if(back == "yellow"){
                colourYellow01++;
            }
        });

        $.each(cd2,function(index,time){
           var back = $("#" + time).attr("child");
            if(back == "red"){
                colourRed02++;
            }else if(back == "green"){
                colourGreen02++;
            }else if(back == "yellow"){
                colourYellow02++;
            }
        });
        //提示信息
        var text = "";
        if(colourRed01 != null && colourRed01 != 0){
            text += "<br/>儿童1：有"+ colourRed01 + "条"+ colourRedText + "没有选择，";
        }
        if(colourGreen01 != null && colourGreen01 != 0){
            text += "<br/>儿童1：有"+ colourGreen01 + "条"+ colourGreenText + "没有选择，";
        }
        if(colourYellow01 != null && colourYellow01 != 0){
            text += "<br/>儿童1：有"+ colourYellow01 + "条"+ colourYellowText + "没有选择。";
        }

        if(colourRed02 != null && colourRed02 != 0){
            text += "<br/>\n\r儿童2：有"+ colourRed02 + "条"+ colourRedText + "没有选择,";
        }
        if(colourGreen02 != null && colourGreen02 != 0){
            text += "<br/>儿童2：有"+ colourGreen02 + "条"+ colourGreenText + "没有选择，";
        }
        if(colourYellow02 != null && colourYellow02 != 0){
            text += "<br/>儿童2：有"+ colourYellow02 + "条"+ colourYellowText + "没有选择。";
        }


        var savechildCode;//获取选中需要保留的儿童编码
        var repeatchildCode;//获取选中需要保留的儿童编码
        var selRows;//获取需要合并的接种记录信息
        var childNoMerge;//保留儿童不合并接种记录
        if (ck == 1) {
            savechildCode = $("#child_id1").text();
            repeatchildCode = $("#child_id2").text();
            selRows = $.merge(children01Ids,children02Ids);
            childNoMerge = cd1;
        } else if (ck == 2) {
            savechildCode = $("#child_id2").text();
            repeatchildCode = $("#child_id1").text();
            selRows = $.merge(children01Ids,children02Ids);
            childNoMerge = cd2;
        };

        /*layer.confirm(text+'<br/>一旦合并将不能恢复，您确定要合并吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            alert("合并");
        })
        return false;*/

        var param = new Array();
        var tmpJson = {};
        if (selRows.length == 0) {
            /*var tmpJson = {};
            tmpJson["savechildCode"] = savechildCode;
            tmpJson["repeatchildCode"] = repeatchildCode;
            param[0] = tmpJson;*/
            alert("请选择接种记录！");
            return false;
        } else {
            /*for (var i = 0; i < selRows.length; i++) {
                var tmpJson = {};
                tmpJson["savechildCode"] = savechildCode;
                tmpJson["repeatchildCode"] = repeatchildCode;
                tmpJson["repeatinoculateId"] = selRows[i];
                param[i] = tmpJson;
            }*/
            tmpJson["savechildCode"] = savechildCode;
            tmpJson["repeatchildCode"] = repeatchildCode;
            tmpJson["repeatinoculateId"] = selRows;
            tmpJson["childNoMerge"] = childNoMerge;
        }
        layer.confirm(text+'<br/>一旦合并将不能恢复，您确定要合并吗？', {
            btn: ['确定', '取消'] //按钮
        }, function (r) {
            var load = layer.load({zIndex:9989101499});
            $.ajax({
                type: "post",
                url: '../child/margeInoculate',
                data: {"param": JSON.stringify(tmpJson)},
                success: function (data) {
                    layer.close(load);
                    if (data.code != 0) {
                        var msg = layer.msg('合并失败,请联系管理员', {icon: 1,zIndex:9989101499});
                        //table.reload('tests');

                        setTimeout(function () {
                            //layer.closeAll()
                            layer.close(msg);
                            //read(true);
                        }, 10000);
                    } else {
                        var msg =  layer.msg('合并成功', {icon: 1,zIndex:9989101499});
                        //table.reload('tests');

                        setTimeout(function () {
                            layer.close(msg);
                            layer.closeAll();
                            read(true);
                        }, 2000);
                    }


                },
                error: function (data) {
                    layer.close(load);
                    layer.msg('合并异常', {icon: 1});
                }
            });
        });

    });

});
$(function () {
    $("#children01").jqGrid({
        datatype: "local",
        colModel: [
            {name: 'id', label: 'id', hidden: true, align: "center"},
            {name: 'bio_name', label: '疫苗名称', minWidth: 180, align: "center"},
            {name: 'inoc_time', label: '剂次', width: 60, align: "center"},
            {name: 'inoc_batchno', label: '批号', width: 100, align: "center"},
            {name: 'inoc_date', label: '接种日期', width: 110, align: "center"},
            {name: 'factory', label: '生产厂家', width: 120, align: "center"},
            {name: 'inoc_inpl_id', label: '接种部位', width: 70, align: "center"},
            {name: 'inoc_depa_code', label: '接种单位', width: 150, align: "center"},
            {name: 'inoc_nurse', label: '接种医生', width: 120, align: "center"},
            {name: 'bio_code', label: '疫苗编号', hidden: true,},
            {name: 'inoc_property', label: '接种属性', hidden: true,},
        ],
        viewrecords: true,
        shrinkToFit: false,
        autoScroll: true,
        multiselect: true,
        rownumbers: true,
        height: 460,
        width:820,
        //autowidth: true,
        onSelectRow:function(rowid,status){
            console.log(rowid);
            console.log(status);

            if(!status){
                return false;
            }
            var rowData = $("#children01").getRowData(rowid);
            var children02Ids = $("#children02").jqGrid('getGridParam','selarrrow');
            console.log(rowData);
            console.log(children02Ids);
            var zj = false;
            $.each(children02Ids,function (index,time) {
                var rowData2 = $("#children02").getRowData(time);
                console.log(rowData2);
                if(rowData.bio_code == rowData2.bio_code && rowData.inoc_property == rowData2.inoc_property && rowData.inoc_time == rowData2.inoc_time){
                    zj = true;
                    return false;
                }
            });

            if(zj){
                layer.msg("当前选择的疫苗和这个剂次的接种记录在另一个儿童已经选择了。",{ time: 2000});
            }


        }
    });

    $("#children02").jqGrid({
        datatype: "local",
        colModel: [
            {name: 'id', label: 'id', hidden: true, align: "center"},
            {name: 'bio_name', label: '疫苗名称', minWidth: 180, align: "center"},
            {name: 'inoc_time', label: '剂次', width: 60, align: "center"},
            {name: 'inoc_batchno', label: '批号', width: 100, align: "center"},
            {name: 'inoc_date', label: '接种日期', width: 110, align: "center"},
            {name: 'factory', label: '生产厂家', width: 120, align: "center"},
            {name: 'inoc_inpl_id', label: '接种部位', width: 70, align: "center"},
            {name: 'inoc_depa_code', label: '接种单位', width: 150, align: "center"},
            {name: 'inoc_nurse', label: '接种医生', width: 120, align: "center"},
            {name: 'bio_code', label: '疫苗编号', hidden: true,},
            {name: 'inoc_property', label: '接种属性', hidden: true,},
        ],
        viewrecords: true,
        shrinkToFit: false,
        autoScroll: true,
        multiselect: true,
        rownumbers: true,
        height: 460,
        width:820,
       // autowidth: true,
        onSelectRow:function(rowid,status){
            console.log(rowid);
            console.log(status);
            if(!status){
                return false;
            }
            var rowData = $("#children02").getRowData(rowid);
            var children01Ids = $("#children01").jqGrid('getGridParam','selarrrow');
            console.log(rowData);
            console.log(children01Ids);
            var zj = false;
            $.each(children01Ids,function (index,time) {
                var rowData2 = $("#children01").getRowData(time);
                console.log(rowData2);
                if(rowData.bio_code == rowData2.bio_code && rowData.inoc_property == rowData2.inoc_property && rowData.inoc_time == rowData2.inoc_time){
                    zj = true;
                    return false;
                }
            });

            if(zj){
                layer.msg("当前选择的疫苗和这个剂次的接种记录在另一个儿童已经选择了。",{ time: 2000});
            }
        }
    });
})
