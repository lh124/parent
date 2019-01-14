/*
function reSetAddress(r) {

    var province_id = r.tChildInfo.chilHabiaddressTownId.substring(0,2);
    var city_id = r.tChildInfo.chilHabiaddressTownId.substring(0,4);
    var country_id = r.tChildInfo.chilHabiaddressTownId.substring(0,6);
    var town_id = r.tChildInfo.chilHabiaddressTownId.substring(0,8);

    var province_home_id = r.tChildInfo.chilAddressTownId.substring(0,2);
    var city_home_id = r.tChildInfo.chilAddressTownId.substring(0,4);
    var country_home_id = r.tChildInfo.chilAddressTownId.substring(0,6);
    var town_home_id = r.tChildInfo.chilAddressTownId.substring(0,8);
    ProviceBind(province_id);
    CityBind(city_id);
    VillageBind(country_id);
    Twon(town_id);

    Provice_homeBind(province_home_id);
    City_homeBind(city_home_id);
    Village_homeBind(country_home_id);
    Twon_home(town_home_id);
}
function Bind(str) {
    // alert($("#Province").html());
    $("#Province").val(str);
    $("#Province_home").val(str);
}
function ProviceBind(province_id) {
    //清空下拉数据
    $("#Province").html("");
    var str = "";
    // var str = "<option>==请选择===</option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listprovince?province_id="+province_id,
        data: { "parentiD": "", "MyColums": "Province" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                str += "<option value=" + item.id + ">" + item.name + "</option>";
                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#Province").append(str);
        },
        error: function () { alert("Error"); }
    });
}
function Provice_homeBind(province_id) {

    //清空下拉数据
    $("#Province_home").html("");
    var str = "";
    // var str = "<option>==请选择===</option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listprovince?province_id="+province_id,
        data: { "parentiD": "", "MyColums": "Province" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                str += "<option value=" + item.id + ">" + item.name + "</option>";
                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#Province_home").append(str);
        },
        error: function () { alert("Error"); }
    });
}
function CityBind(city_id) {
   // var provice = $("#Province option:selected").val();
    //判断省份这个下拉框选中的值是否为空
    if (city_id == "") {
        return;
    }
    $("#City").html("");
    var str = "";
    // var str = "<option>==请选择===</option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcity?city_id="+city_id,
        data: { "city_id": city_id, "MyColums": "City" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                str += "<option value=" + item.id + ">" + item.name + "</option>";
                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#City").append(str);
        },
        error: function () { alert("Error"); }
    });
}
function City_homeBind(city_id) {

    var provice_home = $("#provice_home option:selected").val();
    console.log($("#Province option:selected").text());
    //判断省份这个下拉框选中的值是否为空
    if (provice_home == "") {
        return;
    }
    $("#City_home").html("");
    var str = "";
    // var str = "<option>==请选择===</option>";
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcity?city_id="+city_id,
        data: { "city_id": city_id, "MyColums": "City" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                str += "<option value=" + item.id + ">" + item.name + "</option>";
                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#City_home").append(str);
        },
        error: function () { alert("Error"); }
    });

}

function VillageBind (country_id) {
    // var  provice = $("#City").attr("value");
    var provice = $("#City option:selected").val();
    console.log("市:"+provice);
    //判断市这个下拉框选中的值是否为空
    if (provice == "") {
        return;
    }
    $("#Village").html("");
    // var str = "<option>==请选择===</option>";
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcounty?country_id="+country_id,
        data: { "country_id": country_id, "MyColums": "Village" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                str += "<option value=" + item.id + ">" + item.name + "</option>";
                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#Village").append(str);
        },
        error: function () { alert("Error"); }
    });
}
function Village_homeBind (country_id) {

    // var  provice = $("#City").attr("value");
    var provice_home = $("#City_home option:selected").val();
    //判断市这个下拉框选中的值是否为空
    if (provice_home == "") {
        return;
    }
    $("#Village_home").html("");
    // var str = "<option>==请选择===</option>";
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listcounty?country_id="+country_id,
        data: { "country_id": country_id, "MyColums": "Village" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            $.each(result.data, function (index, item) {
                // for (var i=0;i<result.data.length;i++){
                str += "<option value=" + item.id + ">" + item.name + "</option>";
                // }
            });
            //将数据添加到省份这个下拉框里面
            $("#Village_home").append(str);
        },
        error: function () { alert("Error"); }
    });
}
function Twon(town_id) {
    // var  provice = $("#City").attr("value");
    var provice = $("#Village option:selected").val();
    //判断市这个下拉框选中的值是否为空
    if (provice == "") {
        return;
    }
    $("#Twon").html("");
    // var str = "<option>==请选择===</option>";
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listtown?town_id="+town_id,
        data: { "town_id": town_id, "MyColums": "Twon" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#Twon").append(str)

        },
        error: function () { alert("Error"); }
    });
}
function Twon_home(town_id) {

    // var  provice = $("#City").attr("value");
    var provice_home = $("#Village_home option:selected").val();
    //判断市这个下拉框选中的值是否为空
    if (provice_home == "") {
        return;
    }
    $("#Twon_home").html("");
    // var str = "<option>==请选择===</option>";
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: "../tbaseposition/listtown?town_id="+town_id,
        data: { "town_id": town_id, "MyColums": "Twon" },
        dataType: "JSON",
        async: false,
        success: function (result) {
            //从服务器获取数据进行绑定
            for (var i=0;i<result.data.length;i++){
                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
            }
            //将数据添加到省份这个下拉框里面
            $("#Twon_home").append(str)

        },
        error: function () { alert("Error"); }
    });
}
*/


/*
$(function () {
    $("#jqGrid").jqGrid({
        url: '../tbaseposition/list',
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '', name: 'provinceId', width: 80 },
			{ label: '', name: 'provinceName', width: 80 },
			{ label: '', name: 'cityId', width: 80 },
			{ label: '', name: 'cityName', width: 80 },
			{ label: '', name: 'countyId', width: 80 },
			{ label: '', name: 'countyName', width: 80 },
			{ label: '', name: 'hosptialId', width: 80 },
			{ label: '', name: 'hosptialName', width: 80 },
			{ label: '', name: 'townId', width: 80 },
			{ label: '', name: 'townName', width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
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
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tBasePosition: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tBasePosition = {};
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
		saveOrUpdate: function (event) {
			var url = vm.tBasePosition.id == null ? "../tbaseposition/save" : "../tbaseposition/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tBasePosition),
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
				    url: "../tbaseposition/delete",
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
			$.get("../tbaseposition/info/"+id, function(r){
                vm.tBasePosition = r.tBasePosition;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
		}
	}
});*/
