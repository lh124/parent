
$(function() {
    initChildInfo();//初始化坐标
});
function initChildInfo(){
    $.ajax({
        type:"post",
        url:"../SetPrintForm/selectChildInfoPrintSet.jhtml?printType=2",
        success:function (data) {
            $.each(data.listmap,function(i,item){
                if(item.child_properties =='CHILDCODE'){
                    $("input[name='childCode_X']").val(item.properties_X);
                    $("input[name='childCode_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='CNAME'){
                    $("input[name='cname_X']").val(item.properties_X);
                    $("input[name='cname_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='guardianname'){
                    $("input[name='guardianName_X']").val(item.properties_X);
                    $("input[name='guardianName_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='IDENTITYCARD'){
                    $("input[name='identityCard_X']").val(item.properties_X);
                    $("input[name='identityCard_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='BIRTHCARD'){
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
                if(item.child_properties =='abnormal'){
                    $("input[name='abnormal_X']").val(item.properties_X);
                    $("input[name='abnormal_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='chilTel'){
                    $("input[name='chilTel_X']").val(item.properties_X);
                    $("input[name='chilTel_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='hos_name'){
                    $("input[name='hospital_X']").val(item.properties_X);
                    $("input[name='hospital_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='SEXCODE'){
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
                if(item.child_properties =='createUser'){
                    $("input[name='createUser_X']").val(item.properties_X);
                    $("input[name='createUser_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='createYear'){
                    $("input[name='createYear_X']").val(item.properties_X);
                    $("input[name='createYear_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='createMonth'){
                    $("input[name='createMonth_X']").val(item.properties_X);
                    $("input[name='createMonth_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='createDay'){
                    $("input[name='createDay_X']").val(item.properties_X);
                    $("input[name='createDay_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='month'){
                    $("input[name='month_X']").val(item.properties_X);
                    $("input[name='month_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='day'){
                    $("input[name='day_X']").val(item.properties_X);
                    $("input[name='day_Y']").val(item.properties_Y);

                }
                if(item.child_properties =='hours'){
                    $("input[name='hours_X']").val(item.properties_X);
                    $("input[name='hours_Y']").val(item.properties_Y);

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
    var CHILDCODE = [];
    var BIRTHCARD = [];
    var IDENTITYCARD = [];
    var CNAME = [];
    var sex = [];
    var year = [];
    var month = [];
    var day = [];
    var hours = [];
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
    var abnormal = [];
    var avoid = [];
    var guardianphone = [];
    var createUser = [];
    var createYear = [];
    var createMonth = [];
    var createDay = [];
    for(var i = 0; i<data.length;i++){
        if(i%29==0){
            CHILDCODE.push(data[i]);
        }
        if(i%29==1){
            BIRTHCARD.push(data[i]);
        }
        if(i%29==2){
            IDENTITYCARD.push(data[i]);
        }
        if(i%29==3){
            CNAME.push(data[i]);
        }
        if(i%29==4){
            sex.push(data[i]);
        }
        if(i%29==5){
            year.push(data[i]);
        }
        if(i%29==6){
            month.push(data[i]);
        }
        if(i%29==7){
            day.push(data[i]);
        }
        if(i%29==8){
            hours.push(data[i]);
        }
        if(i%29==9){
            hospital.push(data[i]);
        }
        if(i%29==10){
            birthWeight.push(data[i]);
        }
        if(i%29==11){
            guardianName.push(data[i]);
        }
        if(i%29==12){
            relationShip.push(data[i]);
        }
        if(i%29==13){
            guardianphone.push(data[i]);
        }
        if(i%29==14){
            province_name.push(data[i]);
        }
        if(i%29==15){
            city_name.push(data[i]);
        }
        if(i%29==16){
            county_name.push(data[i]);
        }
        if(i%29==17){
            town_name.push(data[i]);
        }
        if(i%29==18){
            dateiled.push(data[i]);
        }
        if(i%29==19){
            province_name_h.push(data[i]);
        }
        if(i%29==20){
            city_name_h.push(data[i]);
        }
        if(i%29==21){
            county_name_h.push(data[i]);
        }
        if(i%29==22){
            town_name_h.push(data[i]);
        }
        if(i%29==23){
            abnormal.push(data[i]);
        }
        if(i%29==24){
            avoid.push(data[i]);
        }

        if(i%29==25){
            createUser.push(data[i]);
        }
        if(i%29==26){
            createYear.push(data[i]);
        }
        if(i%29==27){
            createMonth.push(data[i]);
        }
        if(i%29==28){
            createDay.push(data[i]);
        }
    }
    var datas = [];
    datas.push(CHILDCODE) ;
    datas.push(BIRTHCARD);
    datas.push(IDENTITYCARD);
    datas.push(CNAME);
    datas.push(sex);
    datas.push(year);
    datas.push(month);
    datas.push(day);
    datas.push(hours);
    datas.push(hospital);
    datas.push(birthWeight);
    datas.push(guardianName);
    datas.push(relationShip);
    datas.push(guardianphone);
    datas.push(province_name);
    datas.push(city_name);
    datas.push(county_name);
    datas.push(town_name);
    datas.push(dateiled);
    datas.push(province_name_h);
    datas.push(city_name_h);
    datas.push(county_name_h);
    datas.push(town_name_h);
    datas.push(abnormal);
    datas.push(avoid);
    /*datas.push(telephone);*/
    datas.push(createUser);
    datas.push(createYear);
    datas.push(createMonth);
    datas.push(createDay);
    var datass = JSON.stringify(datas);


    $.ajax({
        type: "post",
        url: '../SetPrintForm/ChildInfoPrintSet.jhtml?printType=2'+"&allX="+allX+"&allY="+allY,
        dataType: "json",
        data:{"datass":datass},
        success: function (data) {
            if(data.message=='true'){
                $.ajax({
                    type: "post",
                    url: '../SetPrintForm/ChildInfoSetIntoTable.jhtml?printType=2',
                    dataType: "json",
                    success: function (data) {
                        if(data.message=='true'){
                            layer.msg("保存成功",{icon: 1});
                            initChildInfo();
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
            var printType = '2';
            var date = new Date();
            var xml_name = "selfDefinePrintCardModel" + date.getSeconds();
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
                url: '../SetPrintForm/ChildInfoPrintSetModelAs.jhtml?printType=' + printType + '&xml_name=' + xml_name,
                dataType: "json",
                data: {"datass": datass},
                success: function (data) {
                    if (data.message == 'true') {
                        $.ajax({
                            type: "post",
                            url:  '../SetPrintForm/ChildInoculatePrintSetModelIntoTable.jhtml?model_name=' + model_name + '&remark=' + printType + '&xml_name=' + xml_name,
                            dataType: "json",
                            data: {"datass": datass},
                            success: function (data) {
                                if (data.message == 'true') {
                                    layer.msg("保存成功", {icon: 1});
                                } else {
                                    layer.msg("保存失败", {icon: 5});
                                }

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

