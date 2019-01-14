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
    $("#abnormalGrid").jqGrid({
        url: '../tchildabnormal/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true,hidden:true },
            /*{ label: '儿童编码', name: 'chilCode', width: 80 }, 		*/
			{ label: '疫苗', name: 'aefiBactCode', width: 200,align:'center' },
			{ label: '反应症状', name: 'aefiCode', width: 200,align:'center' },
			{ label: '反应日期', name: 'aefiDate', width: 180,align:'center' }
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
			/*{ label: '0:正常；-1删除', name: 'status', width: 80 },
			{ label: '创建人id', name: 'createUserId', width: 80 }, 			
			{ label: '创建人名字', name: 'createUserName', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', width: 80 }, 			
			{ label: '机构编码', name: 'orgId', width: 80 }			*/
        ],
		viewrecords: true,
        height: 500,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#abnormalGridPager",
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
        	$("#abnormalGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vmtabnormal.loadAbnormalData();
    vmtabnormal.getAefiBactCode();
    $("#chilName").val(decodeURI(chilName));
    $("#aefiDate").datetimepicker({
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
            .updateStatus('aefiDate', 'NOT_VALIDATED',null)
            .validateField('aefiDate');
    });


    // 疫苗拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#aefiBactCode").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#aefiBactCode");
        var value = vmtabnormal.aefiBactCode;
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
});

var vmtabnormal = new Vue({
	el:'#rrapp_abnormal',
	data:{
		showList: true,
		title: null,
		tChildAbnormal: {
            aefiDate:null
        },
        aefiBactCode:new Array()
	},
	methods: {
		query: function () {
			vmtabnormal.reload();
		},
		add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
			vmtabnormal.showList = false;
			vmtabnormal.title = "新增";
			vmtabnormal.tChildAbnormal = {
                chilCode:chilCode
            };
		},
		update: function (event) {
			var id = getSelectedRowsAbnormal();
			if(id == null){
				return ;
			}
			vmtabnormal.showList = false;
            vmtabnormal.title = "修改";
            
            vmtabnormal.getInfo(id)
		},
		saveOrUpdate: function (event) {
            abnormalvalidator();
            $('#abnormal').bootstrapValidator('validate');//提交验证
            var bacterin = $("#aefiBactCode").val();
            if(bacterin==null){
                alert("请选择疫苗...");
                return;
            }

            if ($("#abnormal").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                var aefiDate = $("#aefiDate").val();
                var bioCode = $("#aefiBactCode option:selected").val();
                var aefiCode = $("#aefiCode option:selected").val();
                Vue.set(vmtabnormal.tChildAbnormal,"aefiDate", aefiDate);
                Vue.set(vmtabnormal.tChildAbnormal,"aefiBactCode", bioCode);
                Vue.set(vmtabnormal.tChildAbnormal,"aefiCode", aefiCode);
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
                //vmtabnormal.tChildAbnormal.aefiDate = aefiDate;
                var url = vmtabnormal.tChildAbnormal.id == null ? "../tchildabnormal/save" : "../tchildabnormal/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vmtabnormal.tChildAbnormal),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code === 0){
                            layer.msg("操作成功");
                            vmtabnormal.reload();
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }
		},
		del: function (event) {
			var ids = getSelectedRowsAbnormal();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tchildabnormal/delete",
				    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#abnormalGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../tchildabnormal/info/"+id, function(r){
                vmtabnormal.tChildAbnormal = r.tChildAbnormal;
            });
		},
		reload: function (event) {
			vmtabnormal.showList = true;
			var page = $("#abnormalGrid").jqGrid('getGridParam','page');
			$("#abnormalGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
			if($("#abnormal").data('bootstrapValidator')!=null) {
                $("#abnormal").data('bootstrapValidator').resetForm("abnormal");
            }
		},
        loadAbnormalData:function(){
            $('#aefiCode').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择反应症状...',
                // actionsBox:true,
                // search:true,
            });
            $.ajax({
                url: '../child/listdiccode?ttype=code_AEFI',
                dataType: "json",
                type: 'POST',
                success: function (results) {

                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    // $("#aefiCode").html(con);
                    $("#aefiCode").append(con);
                    $("#aefiCode").selectpicker('refresh');
                }
            });
        },
        getAefiBactCode:function () {
            //初始化下拉框
            $('#aefiBactCode').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择疫苗...',
                actionsBox:true,
                search:true,
            });
            $.ajax({
                url: '../tvacinfo/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    vmtabnormal.aefiBactCode = results.page.list;
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].bioCode + '">'+results.page.list[i].bioCnSortname+'</option>';
                    }
                    //$("#aefiBactCode").html(con);
                    $("#aefiBactCode").append(con);
                    $("#aefiBactCode").selectpicker('refresh');

                }
            });
        },
	}
});
//选择多条记录
function getSelectedRowsAbnormal() {
    var grid = $("#abnormalGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }
    return grid.getGridParam("selarrrow");
}

function abnormalvalidator(){
    $('#abnormal').bootstrapValidator({
        live: 'disabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            aefiBactCode: {
                validators: {
                    notEmpty: {
                        message: '疫苗不能为空'
                    }
                }
            },
            aefiCode: {
                validators: {
                    notEmpty: {
                        message: '反应症状不能为空'
                    }
                }
            },
            aefiDate: {
                validators: {
                    notEmpty: {
                        message: '反应日期不能为空'
                    }
                }
            }

        }
    });
}
