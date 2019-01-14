$(function () {
	//全局变量
	var childTotal = 0;
	var noPlanTotal = 0;
	//表格
    $("#jqGrid").jqGrid({
        url: '../initplan/noPlanChilds',
        datatype: "json",
        colModel: [			
			{ label: '儿童编码', name: 'chilCode', width: 80, key: true },
			{ label: '姓名', name: 'chilName', width: 80 },
			{ label: '性别', name: 'chilSex', width: 80 ,
				formatter:function(value,options,rowData){
					if( value == 1 ){
						return '男';
					}else{
						return '女';
					}
				}
			},
			{ label: '出生时间', name: 'chilBirthday', width: 80 }
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
//加载儿童总数
    $.ajax({
        type: "POST",
        url: "../initplan/childTotal",
        contentType:'application/json;charset=UTF-8',
        success: function(r){
            if(r.code == 0){
                childTotal = r.number;
                $("#childtotal").html(r.number);
            }else{
                alert(r.msg);
            }
        }
    });
//规划儿童数
    initPlanNumber = function () {
        $.ajax({
            type: "POST",
            url: "../initplan/childNoPlanTotal",
            contentType:'application/json;charset=UTF-8',
            success: function(r){
                if(r.code == 0){
                    noPlanTotal = r.number;
                    $("#childnoplan").html(r.number);
                    $("#childplan").html(childTotal-noPlanTotal);
                }else{
                    alert(r.msg);
                }
            }
        });

    };
//显示进度条
    showInitProgress = function () {
        $("#initprogress").modal({
            backdrop: 'static',
            keyboard: false,//禁止键盘
            show:true
        });
        setProgressValue("50%");
    };
    //关闭进度条
    closeInitProgress = function () {
        $("#initprogress").modal('hide');
    };
    //设置进度条值，如：50%
    setProgressValue = function(value){
        $("#initprogressbar").css("width",value);
        $("#initprogressbar").html(value);
    };

    //循环更新进度条
    updateBar = function(){
        var intervalId = setInterval(function () {
            $.ajax({
                type : "POST",
                url : "../initplan/progressNumber",
                success : function(data) {
                    var jvalue = data / childTotal * 100;
                    setProgressValue(jvalue+"%");
                    if(data == childTotal){
                        clearInterval(intervalId);
                        closeInitProgress();
                        alert('初始化成功');
                        initPlanNumber();
                        var page = $("#jqGrid").jqGrid('getGridParam','page');
                        $("#jqGrid").jqGrid('setGridParam',{
                            page:page
                        }).trigger("reloadGrid");

                    }
                }
            });
        }, 3000);
    };
    //初始化
    initPlanNumber();

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tRulePlan: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
        initAll: function (event) {
            layer.confirm('从新初始化将引发后台大量数据交换，您确定要初始化全部数据？', function(index) {
                var url = "../initplan/initAll";
                showInitProgress();
                setProgressValue("0%");
                $.ajax({
                    type: "POST",
                    url: url,
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                    }
                });
                updateBar();//更新进度条
                layer.close(index);
            });
		},
        init: function (event) {
			var ids = getSelectedRows();
            if(ids == null){
                return ;
            }
            layer.confirm('确定要初始化选中的记录？', function(index){
                showInitProgress();
                setProgressValue("10%");
				$.ajax({
					type: "POST",
				    url: "../initplan/init",
				    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
                        setProgressValue("100%");
                        closeInitProgress();
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
                            initPlanNumber();
						}else{
							alert(r.msg);
						}
					}
				});
                layer.close(index);
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
});