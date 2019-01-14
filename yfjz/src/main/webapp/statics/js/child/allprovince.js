//新增
// $(function () {
//     dataproviceall();
//     dataproviceall2();
// })
function dataproviceall() {
    $('#Province').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择(省份)...',
        // actionsBox: true,
        // search:true,
    });
    var str="";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listprovince",
        dataType: "JSON",
        async: false,
        success: function (result) {
            // dataprovice=result.data;
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            $("#Province").html(str);
            $("#Province").selectpicker('refresh');
            $("#Province_home").html(str);
            $("#Province_home").selectpicker('refresh');
            /*//将数据添加到省份这个下拉框里面
            window.addEventListener('load',function () {
                dataproviceall2();//省份加载
            })*/
        }
    });
    // datacityall($("#Province option:selected").val());
}
function datacityall(cityId) {
    $('#City').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择(市)...',
        // actionsBox: true,
        // search:true,
    });
    var str=""
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcity?provinceId="+cityId,
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#City").html(str);
            $("#City").selectpicker('refresh');
        },
        error: function () {
            alert("Error");
        }
    });
    // datavallageall($("#City option:selected").val());
}
function datavallageall(vallageId) {
    $('#Village').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择(县/区) ...',
        // actionsBox: true,
        // search:false,
    });
    var str=""
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcounty?cityId="+vallageId,
        dataType: "JSON",
        async: false,

        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#Village").html(str);
            $("#Village").selectpicker('refresh');
        },
        error: function () {
            alert("Error");
        }
    });
    // datatownall($("#Village option:selected").val());
}
function datatownall(townId) {
    $('#Twon').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择(乡镇/街道)...',
        // actionsBox: true,
        // search:false,
    });
    var str=""
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listtown?countyId="+townId,
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#Twon").html(str);
            $("#Twon").selectpicker('refresh');
        },
        error: function () {
            alert("Error");
        }
    });
}

function dataproviceall2() {
    $('#Province_home').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择（省份）...',
        // actionsBox: true,
        // search:false,
    });
    var str="";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listprovince",
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#Province_home").html(str);
            $("#Province_home").selectpicker('refresh');
        },
        error: function () {
            alert("Error");
        }
    });
    // datacityall2($("#Province option:selected").val());
}
function datacityall2(cityId) {
    $('#City_home').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择（市）...',
        // actionsBox: true,
        // search:false,
    });
    var str=""
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcity?provinceId="+cityId,
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#City_home").html(str);
            $("#City_home").selectpicker('refresh');
        },
        error: function () {
            alert("Error");
        }
    });
    // datavallageall2($("#City option:selected").val());
}
function datavallageall2(vallageallId) {
    $('#Village_home').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择(县/区)...',
        // actionsBox: true,
        // search:false,
    });
    var str=""
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcounty?cityId="+vallageallId,
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#Village_home").html(str);
            $("#Village_home").selectpicker('refresh');
        }

    });
    // datatownall2($("#Village option:selected").val());
}
function datatownall2(townId) {
    $('#Twon_home').selectpicker({
        'selectedText': 'cat',
        noneSelectedText: '请选择(乡镇/街道)...',
        actionsBox: true,
        // search:false,
    });
    var str=""
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listtown?countyId="+townId,
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#Twon_home").html(str);
            $("#Twon_home").selectpicker('refresh');
        }

    });
}
