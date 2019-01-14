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
    $("#infectionGrid").jqGrid({
        url: '../tchildinfection/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [			
			/*{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '儿童编码', name: 'chilCode', width: 80 }, 	*/
			{ label: '传染病', name: 'infeCode', width: 280,align:'center' },
			{ label: '发病日期', name: 'infeDate', width: 280,align:'center' }
			/*{ label: '0:本地；：平台', name: 'type', width: 80 }, */
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
        pager: "#infectionGridPager",
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
        	$("#infectionGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vminfection.loadinfectionData();
    $("#infeDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('hide',function(e) {
        $('#infection').data('bootstrapValidator')
            .updateStatus('infeDate', 'NOT_VALIDATED',null)
            .validateField('infeDate');
    });
    $("#chilName").val(decodeURI(chilName));
/*.on('hide',function(e) {
        $('#infection').data('bootstrapValidator')
            .updateStatus('infeDate', 'NOT_VALIDATED',null)
            .validateField('infeDate');
    })*/
});

var vminfection = new Vue({
	el:'#rrapp_infection',
	data:{
		showList: true,
		title: null,
		tChildInfection: {}
	},
	methods: {
		query: function () {
			vminfection.reload();
		},
		add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
			vminfection.showList = false;
			vminfection.title = "新增";
			vminfection.tChildInfection = {
				chilCode:chilCode

			};
		},
		update: function (event) {
			var id = getSelectedRowsInfection();
			if(id == null){
				return ;
			}
			vminfection.showList = false;
            vminfection.title = "修改";
            
            vminfection.getInfo(id)
		},
		saveOrUpdate: function (event) {
            infectionvalidator();
            $('#infection').bootstrapValidator('validate');//提交验证

            if ($("#infection").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                var infeDate = $("#infeDate").val();
                var infeCode = $("#infeCode option:selected").val();
                Vue.set(vminfection.tChildInfection,"infeDate", infeDate);
                Vue.set(vminfection.tChildInfection,"infeCode", infeCode);
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
                var url = vminfection.tChildInfection.id == null ? "../tchildinfection/save" : "../tchildinfection/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vminfection.tChildInfection),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code === 0){
                            layer.msg("操作成功");
                            vminfection.reload();

                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }

		},
		del: function (event) {
			var ids = getSelectedRowsInfection();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tchildinfection/delete",
				    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#infectionGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../tchildinfection/info/"+id, function(r){
                vminfection.tChildInfection = r.tChildInfection;
            });
		},
		reload: function (event) {
			vminfection.showList = true;
			var page = $("#infectionGrid").jqGrid('getGridParam','page');
			$("#infectionGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
			if($("#infection").data('bootstrapValidator')!=null) {
                $("#infection").data('bootstrapValidator').resetForm("infection");
            }
		},
        loadinfectionData:function(){
            $('#infeCode').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择传染病...',
                // actionsBox:true,
                // search:true,
            });
            $.ajax({
                url: '../child/listdiccode?ttype=infection_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {

                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    // $("#infeCode").html(con);
                    $("#infeCode").append(con);
                    $("#infeCode").selectpicker('refresh');
                }
            });
        }
	}
});
//选择多条记录
function getSelectedRowsInfection() {
    var grid = $("#infectionGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }
    return grid.getGridParam("selarrrow");
}

function infectionvalidator(){
    $('#infection').bootstrapValidator({
        live: 'disabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            infeCode: {
                validators: {
                    notEmpty: {
                        message: '传染病不能为空'
                    }
                }
            },
            infeDate: {
                validators: {
                    notEmpty: {
                        message: '发病日期不能为空'
                    }
                }
            }

        }
    });
}