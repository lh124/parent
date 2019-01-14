
$(function() {
    initInfo();//初始化坐标
    $("#childInfoGrid").jqGrid({
        url: '../SetPrintForm/selectInoculatePrintSetModel?remark=1&xml_name=selfdefineprintInfo',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', width: 50, key: true, hidden: true},
            {label: '模板名称', name: 'model_name', width: 180},
            {label: '用户', name: 'username', width: 80}

        ],
        viewrecords: true,
        height: 285,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        //pager: "#jqGridPager",
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
});
function initInfo(){
    $.ajax({
        type:"post",
        url:"../SetPrintForm/selectChildInfoPrintSet.jhtml?printType=1",
        success:function (data) {

            $.each(data.listmap,function(i,item){
                if(item.child_properties =='childcode'){
                    $("input[name='childCode_X']").val(item.properties_X);
                    $("input[name='childCode_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='cname'){
                    $("input[name='cname_X']").val(item.properties_X);
                    $("input[name='cname_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='guardianname'){
                    $("input[name='guardianName_X']").val(item.properties_X);
                    $("input[name='guardianName_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='identitycard'){
                    $("input[name='identityCard_X']").val(item.properties_X);
                    $("input[name='identityCard_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='birthcard'){
                    $("input[name='birthCard_X']").val(item.properties_X);
                    $("input[name='birthCard_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='year'){
                    $("input[name='year_X']").val(item.properties_X);
                    $("input[name='year_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='birthweight'){
                    $("input[name='birthWeight_X']").val(item.properties_X);
                    $("input[name='birthWeight_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='contraindication'){
                    $("input[name='avoid_X']").val(item.properties_X);
                    $("input[name='avoid_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='allergichistory'){
                    $("input[name='allergy_X']").val(item.properties_X);
                    $("input[name='allergy_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='unittel'){
                    $("input[name='telephone_X']").val(item.properties_X);
                    $("input[name='telephone_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='hos_name'){
                    $("input[name='hospital_X']").val(item.properties_X);
                    $("input[name='hospital_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='sexcode'){
                    $("input[name='sex_X']").val(item.properties_X);
                    $("input[name='sex_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='text'){
                    $("input[name='relationShip_X']").val(item.properties_X);
                    $("input[name='relationShip_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='province_name'){
                    $("input[name='province_name_X']").val(item.properties_X);
                    $("input[name='province_name_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='city_name'){
                    $("input[name='city_name_X']").val(item.properties_X);
                    $("input[name='city_name_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='county_name'){
                    $("input[name='county_name_X']").val(item.properties_X);
                    $("input[name='county_name_Y']").val(item.properties_Y);

                }if(item.child_properties =='town_name'){
                    $("input[name='town_name_X']").val(item.properties_X);
                    $("input[name='town_name_Y']").val(item.properties_Y);

                }if(item.child_properties =='dateiled'){
                    $("input[name='dateiled_X']").val(item.properties_X);
                    $("input[name='dateiled_Y']").val(item.properties_Y);

                }if(item.child_properties =='province_name_h'){
                    $("input[name='province_name_h_X']").val(item.properties_X);
                    $("input[name='province_name_h_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='city_name_h'){
                    $("input[name='city_name_h_X']").val(item.properties_X);
                    $("input[name='city_name_h_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='county_name_h'){
                    $("input[name='county_name_h_X']").val(item.properties_X);
                    $("input[name='county_name_h_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='town_name_h'){
                    $("input[name='town_name_h_X']").val(item.properties_X);
                    $("input[name='town_name_h_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='orgname'){
                    $("input[name='govement_X']").val(item.properties_X);
                    $("input[name='govement_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='printYear'){
                    $("input[name='printYear_X']").val(item.properties_X);
                    $("input[name='printYear_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='printMonth'){
                    $("input[name='printMonth_X']").val(item.properties_X);
                    $("input[name='printMonth_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='printDay'){
                    $("input[name='printDay_X']").val(item.properties_X);
                    $("input[name='printDay_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='month'){
                    $("input[name='month_X']").val(item.properties_X);
                    $("input[name='month_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='day'){
                    $("input[name='day_X']").val(item.properties_X);
                    $("input[name='day_Y']").val(item.properties_Y);

                }



            })
            //console.info(obj);
        }
    });

}
function saveInfoSet() {
    var allX = $("#allInfoX").val();
    var allY = $("#allInfoY").val();
    var data= $("#fff").serializeArray();
    var childCocde = [];
    var birthCard = [];
    var identityCard = [];
    var cname = [];
    var sex = [];
    var year = [];
    var month = [];
    var day = [];
    var hospital = [];
    var birthWeight = [];
    var guardianName = [];
    var relationShip = [];
    /*var familyAddress = [];
     var hukouregioncode = [];*/
    var province_name = [];
    var city_name = [];
    var county_name = [];
    var town_name = [];
    var dateiled = [];

    var province_name_h = [];
    var city_name_h = [];
    var county_name_h = [];
    var town_name_h = [];
    var allergy = [];
    var avoid = [];
    var telephone = [];
    var govement = [];
    var printYear = [];
    var printMonth = [];
    var printDay = [];
    for(var i = 0; i<data.length;i++){
        if(i%28==0){
            childCocde.push(data[i]);
        }
        if(i%28==1){
            birthCard.push(data[i]);
        }
        if(i%28==2){
            identityCard.push(data[i]);
        }
        if(i%28==3){
            cname.push(data[i]);
        }
        if(i%28==4){
            sex.push(data[i]);
        }
        if(i%28==5){
            year.push(data[i]);
        }
        if(i%28==6){
            month.push(data[i]);
        }
        if(i%28==7){
            day.push(data[i]);
        }
        if(i%28==8){
            hospital.push(data[i]);
        }
        if(i%28==9){
            birthWeight.push(data[i]);
        }
        if(i%28==10){
            guardianName.push(data[i]);
        }
        if(i%28==11){
            relationShip.push(data[i]);
        }
        if(i%28==12){
            province_name.push(data[i]);
        }
        if(i%28==13){
            city_name.push(data[i]);
        }
        if(i%28==14){
            county_name.push(data[i]);
        }
        if(i%28==15){
            town_name.push(data[i]);
        }
        if(i%28==16){
            dateiled.push(data[i]);
        }
        if(i%28==17){
            province_name_h.push(data[i]);
        }
        if(i%28==18){
            city_name_h.push(data[i]);
        }
        if(i%28==19){
            county_name_h.push(data[i]);
        }
        if(i%28==20){
            town_name_h.push(data[i]);
        }
        if(i%28==21){
            allergy.push(data[i]);
        }
        if(i%28==22){
            avoid.push(data[i]);
        }
        if(i%28==23){
            telephone.push(data[i]);
        }
        if(i%28==24){
            govement.push(data[i]);
        }
        if(i%28==25){
            printYear.push(data[i]);
        }
        if(i%28==26){
            printMonth.push(data[i]);
        }
        if(i%28==27){
            printDay.push(data[i]);
        }
    }
    var datas = [];
    datas.push(childCocde) ;
    datas.push(birthCard);
    datas.push(identityCard);
    datas.push(cname);
    datas.push(sex);
    datas.push(year);
    datas.push(month);
    datas.push(day);
    datas.push(hospital);
    datas.push(birthWeight);
    datas.push(guardianName);
    datas.push(relationShip);
    datas.push(province_name);
    datas.push(city_name);
    datas.push(county_name);
    datas.push(town_name);
    datas.push(dateiled);
    datas.push(province_name_h);
    datas.push(city_name_h);
    datas.push(county_name_h);
    datas.push(town_name_h);
    datas.push(allergy);
    datas.push(avoid);
    datas.push(telephone);
    datas.push(govement);
    datas.push(printYear);
    datas.push(printMonth);
    datas.push(printDay);
    var datass = JSON.stringify(datas);


    $.ajax({
        type: "post",
        url: '../SetPrintForm/ChildInfoPrintSet.jhtml?printType=1'+"&allX="+allX+"&allY="+allY,
        dataType: "json",
        data:{"datass":datass},
        success: function (data) {
            if(data.message=='true'){
                $.ajax({
                    type: "post",
                    url: '../SetPrintForm/ChildInfoSetIntoTable.jhtml?printType=1',
                    dataType: "json",
                    data: {"datass":datass},
                    success: function (data) {
                        if(data.message=='true'){
                            layer.msg("保存成功",{icon: 1});
                            initInfo();
                        }else{
                            layer.msg("保存失败",{icon: 5});
                        }

                    },
                    error: function (response, err) {
                        layer.msg("保存失败",{icon: 5});
                    }
                });
                /*layer.msg("保存成功",{icon: 1});*/

            }else{
                layer.msg("修改失败",{icon: 5});
            }

        },
        error: function (response, err) {
            layer.msg("修改失败",{icon: 5});
        }
    });
}

function saveInfoSetAs() {
    layer.prompt({title:'另存为模板'},function(model_name,index) {
        if (model_name == null || model_name.trim() == "") {
            layer.msg("模板名不能为空！");
            return;
        }
        layer.close(index);
        if (model_name != null && model_name.trim() != "") {
            var allX = $("#allInfoX").val();
            var allY = $("#allInfoY").val();
            var printType = '1';
            var date = new Date();
            var xml_name = "selfdefineprintInfo" +date.getMinutes()+ date.getSeconds();
            var data = $("#fff").serializeArray();
            var childCocde = [];
            var birthCard = [];
            var identityCard = [];
            var cname = [];
            var sex = [];
            var year = [];
            var month = [];
            var day = [];
            var hospital = [];
            var birthWeight = [];
            var guardianName = [];
            var relationShip = [];
            /*var familyAddress = [];
             var hukouregioncode = [];*/
            var province_name = [];
            var city_name = [];
            var county_name = [];
            var town_name = [];
            var dateiled = [];

            var province_name_h = [];
            var city_name_h = [];
            var county_name_h = [];
            var town_name_h = [];
            var allergy = [];
            var avoid = [];
            var telephone = [];
            var govement = [];
            var printYear = [];
            var printMonth = [];
            var printDay = [];
            for (var i = 0; i < data.length; i++) {
                if (i % 28 == 0) {
                    childCocde.push(data[i]);
                }
                if (i % 28 == 1) {
                    birthCard.push(data[i]);
                }
                if (i % 28 == 2) {
                    identityCard.push(data[i]);
                }
                if (i % 28 == 3) {
                    cname.push(data[i]);
                }
                if (i % 28 == 4) {
                    sex.push(data[i]);
                }
                if (i % 28 == 5) {
                    year.push(data[i]);
                }
                if (i % 28 == 6) {
                    month.push(data[i]);
                }
                if (i % 28 == 7) {
                    day.push(data[i]);
                }
                if (i % 28 == 8) {
                    hospital.push(data[i]);
                }
                if (i % 28 == 9) {
                    birthWeight.push(data[i]);
                }
                if (i % 28 == 10) {
                    guardianName.push(data[i]);
                }
                if (i % 28 == 11) {
                    relationShip.push(data[i]);
                }
                if (i % 28 == 12) {
                    province_name.push(data[i]);
                }
                if (i % 28 == 13) {
                    city_name.push(data[i]);
                }
                if (i % 28 == 14) {
                    county_name.push(data[i]);
                }
                if (i % 28 == 15) {
                    town_name.push(data[i]);
                }
                if (i % 28 == 16) {
                    dateiled.push(data[i]);
                }
                if (i % 28 == 17) {
                    province_name_h.push(data[i]);
                }
                if (i % 28 == 18) {
                    city_name_h.push(data[i]);
                }
                if (i % 28 == 19) {
                    county_name_h.push(data[i]);
                }
                if (i % 28 == 20) {
                    town_name_h.push(data[i]);
                }
                if (i % 28 == 21) {
                    allergy.push(data[i]);
                }
                if (i % 28 == 22) {
                    avoid.push(data[i]);
                }
                if (i % 28 == 23) {
                    telephone.push(data[i]);
                }
                if (i % 28 == 24) {
                    govement.push(data[i]);
                }
                if (i % 28 == 25) {
                    printYear.push(data[i]);
                }
                if (i % 28 == 26) {
                    printMonth.push(data[i]);
                }
                if (i % 28 == 27) {
                    printDay.push(data[i]);
                }
            }
            var datas = [];
            datas.push(childCocde);
            datas.push(birthCard);
            datas.push(identityCard);
            datas.push(cname);
            datas.push(sex);
            datas.push(year);
            datas.push(month);
            datas.push(day);
            datas.push(hospital);
            datas.push(birthWeight);
            datas.push(guardianName);
            datas.push(relationShip);
            datas.push(province_name);
            datas.push(city_name);
            datas.push(county_name);
            datas.push(town_name);
            datas.push(dateiled);
            datas.push(province_name_h);
            datas.push(city_name_h);
            datas.push(county_name_h);
            datas.push(town_name_h);
            datas.push(allergy);
            datas.push(avoid);
            datas.push(telephone);
            datas.push(govement);
            datas.push(printYear);
            datas.push(printMonth);
            datas.push(printDay);
            var datass = JSON.stringify(datas);


            $.ajax({
                type: "post",
                url:  '../SetPrintForm/ChildInfoPrintSetModelAs.jhtml?printType=' + printType + '&xml_name=' + xml_name+"&allX="+allX+"&allY="+allY,
                dataType: "json",
                data: {"datass": datass},
                success: function (data) {
                    if (data.message == 'true') {
                        $.ajax({
                            type: "post",
                            url: '../SetPrintForm/ChildInoculatePrintSetModelIntoTable.jhtml?model_name=' + model_name + '&remark=' + printType + '&xml_name=' + xml_name,
                            dataType: "json",
                            data: {"datass": datass},
                            success: function (data) {
                                if (data.message == 'true') {
                                    layer.msg("保存成功", {icon: 1});
                                } else {
                                    layer.msg("保存失败", {icon: 5});
                                }
                                var page = $("#childInfoGrid").jqGrid('getGridParam', 'page');
                                $("#childInfoGrid").jqGrid('setGridParam',{
                                    postData: {},
                                    page: page
                                }).trigger("reloadGrid");

                            },
                            error: function (response, err) {
                                layer.msg("保存失败", {icon: 5});
                            }
                        });
                        /*layer.msg("保存成功",{icon: 1});*/

                    } else {
                        layer.msg("修改失败", {icon: 5});
                    }

                },
                error: function (response, err) {
                    layer.msg("修改失败", {icon: 5});
                }
            });
        }
    })
}

var vm = new Vue({
    el:'#tab2',
    data:{
        showList2: true,
        title: null,
        tMgrStore: {posId:null},
        items:[]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        loadData:function(){
            var param = new Array();//定义数组
            //下拉选框
            $.ajax({
                // get请求地址
                url: '../tmgrstore/getTowerList',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result=data.page;
                    $.each(result, function (index, item) {
                        param.push({"text":item.towerName,"value":item.id});
                    });
                    vm.items = param;
                }
            });
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.tMgrStore = {};
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
        del: function (event) {
            var ids = getSelectedRows("childInfoGrid");
            //var rowData = $("#inoculateGrid").jqGrid('getRowData',rowid);
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../tchildprintmodel/delete",
                    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#childInfoGrid").trigger("reloadGrid");
                            });

                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            $("#storeForm").bootstrapValidator('validate');//提交验证
            if ($("#storeForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                var url = vm.tMgrStore.id == null ? "../tmgrstore/save" : "../tmgrstore/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tMgrStore),
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
            }
        },
        stop: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }

            confirm('确定要操作选中的仓库吗？', function(){
                $.ajax({
                    type: "POST",
                    url: "../tmgrstore/updateStatus?id="+id,
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#childInfoGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get("../tmgrstore/info/"+id, function(r){
                vm.tMgrStore = r.tMgrStore;

            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#childInfoGrid").jqGrid('getGridParam','page');
            $("#childInfoGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        }
    }
});