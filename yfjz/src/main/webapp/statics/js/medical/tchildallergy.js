
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
$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchildallergy/list',
        datatype: "json",
        colModel: [			
			// { label: 'id', name: 'id', width: 50, key: true },
			// { label: '儿童姓名', name: 'chilCode', width: 80 },
			// { label: '过敏疫苗', name: 'bioCode', width: 80 },
			{ label: '过敏信息', name: 'describes', width: 80 },
			{ label: '检测日期', name: 'checkDate', width: 80 },
			// { label: '登记人', name: 'registrant', width: 80 },
			// { label: '备注', name: 'remark', width: 80 }
        ],
		viewrecords: true,
        height: 600,
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
        postData:{
            "chilCode": $("#chilCode").val()
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    // vm.checkDate();
    // vm.BioCodeData();
    $("#checkDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('hide',function(e) {
        $('#allergy').data('bootstrapValidator')
            .updateStatus('checkDate', 'NOT_VALIDATED',null)
            .validateField('checkDate');
    });
    // $('#dallergyForm').bootstrapValidator({
    //     live: 'enabled',
    //     excluded: [':disabled', ':hidden', ':not(:visible)'],
    //     feedbackIcons: {
    //         valid: 'glyphicon glyphicon-ok',
    //         invalid: 'glyphicon glyphicon-remove',
    //         validating: 'glyphicon glyphicon-refresh'
    //     },
    //     fields: {
    //         BioCode: {
    //             validators: {
    //                 notEmpty: {
    //                     message: '疫苗不能为空'
    //                 }
    //             }
    //         },
    //         describes: {
    //             validators: {
    //                 notEmpty: {
    //                     message: '过敏信息不能为空'
    //                 }
    //             }
    //         }
    //     }
    // });
    // $("#vaccine").jqGrid({
    //     url: '../tvacinfo/list',
    //     datatype: "json",
    //     colModel: [
    //         { label: '国标编码', name: 'bioCode', width: 70 ,search:true},
    //         { label: '疫苗名称', name: 'bioName', width: 130 ,search:true},
    //         { label: '中文简称', name: 'bioCnSortname', width: 100 ,search:true},
    //         { label: '英文简称', name: 'bioEnSortname', width: 80 ,search:true},
    //     ],
    //     viewrecords: true,
    //     width:600,
    //     height: 385,
    //     rowNum: 1000,
    //     rowList : [10,30,50],
    //     rownumWidth: 25,
    //     jsonReader : {
    //         root: "page.list",
    //         page: "page.currPage",
    //         total: "page.totalPage",
    //         records: "page.totalCount"
    //     },
    //     prmNames : {
    //         page:"page",
    //         rows:"limit",
    //         order: "order"
    //     },
    //     gridComplete:function(){
    //         //隐藏grid底部滚动条
    //         $("#vaccine").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    //     },
    //     //选中一行，然后将数据保存到输入框中，关闭弹出框
    //     onSelectRow:function (rowid,status) {
    //         var row=$("#vaccine").jqGrid("getRowData",rowid);
    //         $("#fkVaccineCode").val(row.bioCode);
    //         $("#fkVaccineName").val(row.bioName);
    //         //vm.tMgrStockBase = {fkVaccineCode:row.bioCode,productName:row.bioName};
    //         $('#myModal').modal('hide')
    //         $("#myModal").on("hidden", function() {
    //             $(this).removeData("modal");
    //         });
    //     }
    // });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            chilCode:null,
        },
		showList: true,
		title: null,
		tChildAllergy: {}
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
			vm.tChildAllergy = {  chilCode:chilCode};
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

        // checkDate:function(){
        //     $('#datetimepicker1').datetimepicker({
        //         language: 'zh-CN',//显示中文
        //         format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        //         minView: "month",//设置只显示到月份
        //         initialDate: new Date(),
        //         autoclose: true,//选中自动关闭
        //         todayBtn: true,//显示今日按钮
        //         locale: moment.locale('zh-cn')
        //     });
        //     //默认获取当前日期
        //     var today = new Date();
        //     var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate();
        //     //对日期格式进行处理
        //     var date = new Date(nowdate);
        //     var mon = date.getMonth() + 1;
        //     var day = date.getDate();
        //     var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
        //     document.getElementById("checkDate").value = mydate;
        //
        // },

        BioCodeData:function(){
            var param = new Array();
            $.ajax({
                url: '../tvacinfo/list?page=1&limit=200&order=&sidx=',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    console.log(results)
                    // var r=results.data
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].bioCode + '">'+results.page.list[i].bioCnSortname+"("+results.page.list[i].bioCode +")"+'</option>';
                    }
                    $("#BioCode").html(con);
                }
            });
        },

        saveOrUpdate: function (event) {

            // var vacinneId=$("#fkVaccineCode").val();
            var chilCode=$("#chilCode").val();
            // Vue.set(vm.tChildAllergy, 'bioCode', vacinneId);
            Vue.set(vm.tChildAllergy, 'chilCode', chilCode);
            var checkDate = $("#checkDate").val();
            Vue.set(vm.tChildAllergy,"checkDate", checkDate);
            $("#dallergyForm").bootstrapValidator('validate');//提交验证
            if ($("#dallergyForm").data('bootstrapValidator').isValid()) {
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
                var url = vm.tChildAllergy.id == null ? "../tchildallergy/save" : "../tchildallergy/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tChildAllergy),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tchildallergy/delete",
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
			$.get("../tchildallergy/info/"+id, function(r){
                vm.tChildAllergy = r.tChildAllergy;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'chilCode':vm.q.chilCode},
                page:page
            }).trigger("reloadGrid");
		}
	}
});