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
    $("#tabooGrid").jqGrid({
        url: '../tchildtaboo/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [			
			/*{ label: 'id', name: 'id', width: 50, key: true },
			{ label: '儿童编码', name: 'chilCode', width: 80 }, 		*/
			{ label: '接种禁忌', name: 'istaCode', width: 280,align:'center' },
			/*{ label: '疫苗', name: 'istaBioCode', width: 80 },*/
			{ label: '检测日期', name: 'istaCheckDate', width: 280,align:'center' }
			/*{ label: 'type,0本地,1平台', name: 'type', width: 80 }, 		*/
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
			/*{ label: '状态,0正常,-1删除', name: 'status', width: 80 },
			{ label: '创建人id', name: 'createUserId', width: 80 }, 			
			{ label: '创建人名字', name: 'createUserName', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', width: 80 }, 			
			{ label: '备注', name: 'remark', width: 80 }		*/
        ],
		viewrecords: true,
        height: 500,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#tabooGridPager",
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
        	$("#tabooGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vmtaboo.loadtabooData();
    vmtaboo.getIstaBioCode();
    $("#chilName").val(decodeURI(chilName));

    $("#istaCheckDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('hide',function(e) {
        $('#abnormal').data('bootstrapValidator')
            .updateStatus('istaCheckDate', 'NOT_VALIDATED',null)
            .validateField('istaCheckDate');
    });
});

var vmtaboo = new Vue({
	el:'#rrapp_taboo',
	data:{
		showList: true,
		title: null,
		tChildTaboo: {}
	},
	methods: {
		query: function () {
			vmtaboo.reload();
		},
		add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
			vmtaboo.showList = false;
			vmtaboo.title = "新增";
			vmtaboo.tChildTaboo = {
                chilCode:chilCode
			};
		},
		update: function (event) {
			var id = getSelectedRowsTaboo();
			if(id == null){
				return ;
			}
			vmtaboo.showList = false;
            vmtaboo.title = "修改";
            
            vmtaboo.getInfo(id)
		},
		saveOrUpdate: function (event) {
            taboovalidator();
            $('#taboo').bootstrapValidator('validate');//提交验证
            if ($("#taboo").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                var istaCheckDate = $("#istaCheckDate").val();
                var istaCode = $("#istaCode option:selected").val();
                Vue.set(vmtaboo.tChildTaboo,"istaCheckDate", istaCheckDate);
                Vue.set(vmtaboo.tChildTaboo,"istaCode", istaCode);

                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 5000});
                var url = vmtaboo.tChildTaboo.id == null ? "../tchildtaboo/save" : "../tchildtaboo/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vmtaboo.tChildTaboo),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code === 0){
                            layer.msg("操作成功");
                            vmtaboo.reload();

                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }

		},
		del: function (event) {
			var ids = getSelectedRowsTaboo();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tchildtaboo/delete",
				    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#tabooGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../tchildtaboo/info/"+id, function(r){
                vmtaboo.tChildTaboo = r.tChildTaboo;
            });
		},
		reload: function (event) {
			vmtaboo.showList = true;
			var page = $("#tabooGrid").jqGrid('getGridParam','page');
			$("#tabooGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
			if($("#taboo").data('bootstrapValidator')!=null) {
                $("#taboo").data('bootstrapValidator').resetForm("taboo");
            }
		},
        loadtabooData:function(){
            //初始化下拉框
            $('#istaCode').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择接种禁忌...',
                // actionsBox:true,
                // search:false,
            });
            $.ajax({
                url: '../child/listdiccode?ttype=code_avoid',
                dataType: "json",
                type: 'POST',
                success: function (results) {

                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    // $("#istaCode").html(con);
                    $("#istaCode").append(con);
                    $("#istaCode").selectpicker('refresh');

                }
            });
        },
        getIstaBioCode:function () {
            $.ajax({
                url: '../tvacinfo/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {

                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].bioCode + '">'+results.page.list[i].bioCnSortname+'</option>';
                    }
                    $("#istaBioCode").html(con);

                }
            });
        }
	}
});
//选择多条记录
function getSelectedRowsTaboo() {
    var grid = $("#tabooGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }
    return grid.getGridParam("selarrrow");
}
function taboovalidator(){
    $('#taboo').bootstrapValidator({
        live: 'disabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            istaCode: {
                validators: {
                    notEmpty: {
                        message: '禁忌不能为空'
                    }
                }
            }

        }
    });
}