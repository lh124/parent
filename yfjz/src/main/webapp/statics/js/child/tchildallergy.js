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
    $("#allergyGrid").jqGrid({
        url: '../tchildallergy/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [			
			/*{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '儿童表ID', name: 'chilCode', width: 80 }, 	*/
			{ label: '过敏信息', name: 'describes', width: 80,align:'center' },
            { label: '检测日期', name: 'checkDate', width: 80,align:'center' }
            /*{ label: '同步状态', name: 'syncstatus', width: 80,
                formatter:function (value) {
                    if(value==0){
                        return "未同步";
                    }
                    if(value==1){
                        return "同步";
                    }
                }
            }*/

        ],
		viewrecords: true,
        height: 500,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#allergyGridPager",
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
        	$("#allergyGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
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
    $("#chilName").val(decodeURI(chilName));
});

var vmallergy = new Vue({
	el:'#rrapp_allergy',
	data:{
		showList: true,
		title: null,
		tChildAllergy: {}
	},
	methods: {
		query: function () {
			vmallergy.reload();
		},
		add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
			vmallergy.showList = false;
			vmallergy.title = "新增";
			vmallergy.tChildAllergy = {
				chilCode:chilCode
			};
		},
		update: function (event) {
			var id = getSelectedRowsAllergy();
			if(id == null){
				return ;
			}
			vmallergy.showList = false;
            vmallergy.title = "修改";
            
            vmallergy.getInfo(id)
		},

        /*BioCodeData:function(){
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
        },*/
		saveOrUpdate: function (event) {
            allergyvalidator();
            $('#allergy').bootstrapValidator('validate');//提交验证
            if ($("#allergy").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                var checkDate = $("#checkDate").val();
                Vue.set(vmallergy.tChildAllergy,"checkDate", checkDate);
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
            	var url = vmallergy.tChildAllergy.id == null ? "../tchildallergy/save" : "../tchildallergy/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vmallergy.tChildAllergy),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code === 0){
                            layer.msg("操作成功");
                            vmallergy.reload();

                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }

		},
		del: function (event) {
			var ids = getSelectedRowsAllergy();
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
								$("#allergyGrid").trigger("reloadGrid");
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
                vmallergy.tChildAllergy = r.tChildAllergy;
            });
		},
		reload: function (event) {
			vmallergy.showList = true;
			var page = $("#allergyGrid").jqGrid('getGridParam','page');
			$("#allergyGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
			if($("#allergy").data('bootstrapValidator')!=null) {
                $("#allergy").data('bootstrapValidator').resetForm("allergy");
            }
		}
	}
});

//选择多条记录
function getSelectedRowsAllergy() {
    var grid = $("#allergyGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    return grid.getGridParam("selarrrow");
}

function allergyvalidator(){
    $('#allergy').bootstrapValidator({
        live: 'disabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            describes: {
                validators: {
                    notEmpty: {
                        message: '过敏信息不能为空'
                    }
                }
            }

        }
    });
}