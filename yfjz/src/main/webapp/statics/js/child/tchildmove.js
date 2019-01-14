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
var chil_here =  getUrlVars()["chilHere"];
var chilName =  getUrlVars()["chilName"];
$(function () {
    $("#moveGrid").jqGrid({
        url: '../tchildmove/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [

			{ label: '现在册情况', name: 'chheHere', width: 80,align:'center' },
			{ label: '原在册情况', name: 'chhePrehere', width: 80,align:'center' },
			/*{ label: '出省状态', name: 'chheChilProvince', width: 80 },*/
			{ label: '变更时间', name: 'chheChangedate', width: 150,align:'center' },
			{ label: '变更单位', name: 'chheDepaId', width: 180,align:'center' },
            { label: '变更原因', name: 'chheChilProvinceremark', width: 80,align:'center' }
			/*{ label: '是否来源平台 0:本地 ，1：平台', name: 'type', width: 80 },*/
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
			/*{ label: '状态：0：正常；-1：删除', name: 'status', width: 80 },*/
			/*{ label: '创建人id', name: 'createUserId', width: 80 },

			{ label: '机构编码', name: 'orgId', width: 80 }			*/
        ],
		viewrecords: true,
        height: 500,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
		/*caption:'变更记录',*/
		hidegrid:true,
        multiselect: true,
        pager: "#moveGridPager",
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
        	$("#moveGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vmmove.loadInfoStatusData();
    vmmove.chheChangedate();
    $("#chilName").val(decodeURI(chilName));
});

var vmmove = new Vue({
	el:'#rrapp_move',
	data:{
		showList: true,
		title: null,
		tChildMove: {}
	},
	methods: {
		query: function () {
			vmmove.reload();
		},
		add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
			vmmove.showList = false;
			vmmove.title = "新增";
			vmmove.tChildMove = {
                chhePrehere:decodeURI(chil_here),
                chheChangedate:vmmove.chheChangedate(),
                chheDepaId:parent.orgName,
                chilCode:chilCode
			};
		},
		update: function (event) {


			var id = getSelectedRowsMove();
			if(id == null){
				return ;
			}
			vmmove.showList = false;
            vmmove.title = "修改";

            vmmove.getInfo(id)
		},
		saveOrUpdate: function (event) {

            movevalidator();
            $('#move').bootstrapValidator('validate');//提交验证

            if ($("#move").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                Vue.set(vmmove.tChildMove, "chheDepaId", parent.orgId);
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
                var url = vmmove.tChildMove.id == null ? "../tchildmove/save" : "../tchildmove/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vmmove.tChildMove),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("操作成功");
                            vmmove.reload();

                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }
		},
		del: function (event) {
			var ids = getSelectedRowsMove();
			if(ids == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tchildmove/delete",
				    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#moveGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../tchildmove/info/"+id, function(r){
                vmmove.tChildMove = r.tChildMove;
            });
		},
		reload: function (event) {
			vmmove.showList = true;
			var page = $("#moveGrid").jqGrid('getGridParam','page');
			$("#moveGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
			if( $("#move").data('bootstrapValidator')!=null) {
                $("#move").data('bootstrapValidator').resetForm("move");
            }
		},
        //个案状态
        loadInfoStatusData:function(){
            var param = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_info_status',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#infostatus").html(con);
                    $("#chilHere").html(con);
                    $("#chheHere").html(con);
                    //$("#chhePrehere").html(con);

                }
            });
        },
        chheChangedate:function(){

            //默认获取当前日期
            var today = new Date();
            var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate()+" "+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
            //对日期格式进行处理
            var date = new Date(nowdate);
            var mon = date.getMonth() + 1;
            var day = date.getDate();
            var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day)+" "+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
            /*document.getElementById("chilCreatedate").value = mydate;*/
            return mydate;
        }
	}
});
//选择多条记录
function getSelectedRowsMove() {

    var grid = $("#moveGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }
    return grid.getGridParam("selarrrow");
}

function movevalidator(){
    $('#move').bootstrapValidator({
        live: 'disabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            chheHere: {
                validators: {
                    notEmpty: {
                        message: '现在册情况不能为空'
                    }
                }
            },
            chheChilProvinceremark: {
                validators: {
                    notEmpty: {
                        message: '变更原因不能为空'
                    }
                }
            }

        }
    });
}