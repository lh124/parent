
$(function () {
    var click=function(){
        alert("click");
    }
	//视频列表
    $("#jqGrid").jqGrid({
        url: '../tmvupload/queryListAndPlayerArea',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true,hidden:true },
            { label: '预览',width:50 ,formatter:function(a,b,c){return '<a class="btn btn-primary" onclick="vm.review(\''+c.url+'\')"><i class="fa fa-play"></i>&nbsp;预览</a>'}},
			{ label: '视频的真实名称', name: 'realname', width: 150 },
			{ label: '视频的真实全路径', name: 'url', width: 150 },
			{ label: '添加视频的时间', name: 'createTime', width: 80 },
			{ label: '文件大小', name: 'filesize', width: 80 },
			{ label: '播放台名称', name: 'playerareaId', width: 80 ,
                formatter: function(cellValue, options, rowdata, action) {
                    if (cellValue == null || cellValue == undefined || cellValue ==""){
                        return "暂无";
                    }
                    var paids = cellValue.split(",");
                    var html = "";
                    for (var i = 0; i < paids.length; i++) {
                        if (paids[i] == 1){
                            html += "大厅视频" +",";
                        } else if (paids[i] ==2){
                            html += "留观视频"+",";
                        }
                    }
                    return html.substr(0,html.length-1);
                }
            }
        ],
		viewrecords: true,
        height: 400,
        rowNum: 100,
		rowList : [100],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
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
        },
        ondblClickRow:function(rowid,iRow,iCol,e){
            var rowData = $("#jqGrid").jqGrid('getRowData',rowid);
            vm.relationalShip(rowData);
        }
    });

    //工作台列表
    $("#playerArea_jqGrid").jqGrid({
            url:'../queue/notice/list',
            datatype : "json",
            height:100,
            colNames : [ '编号', '播放区名称','是否显示通知','通知内容','操作' ],
            colModel : [
                {name : 'id',key:true,width:50},
                {name : 'type',width:100},
                {name : 'show',width:50,formatter:function(value,row){return value?'<input type="checkbox" checked style="width:20px;height:20px" onchange="vm.toggleNotice('+row.rowId+',event)"/>':'<input type="checkbox" style="width:20px;height:20px" onchange="vm.toggleNotice('+row.rowId+',event)"/>'}},
                {name : 'content',formatter:function(value){return value.replace(/[\n\r]/g,'').replace(/<\/?.+?>/g,"") }},
                {name : 'operate',formatter : function(value,row){return '<a class="btn btn-primary" onclick="vm.play('+row.rowId+')"><i class="fa fa-play"></i>&nbsp;播放</a>&nbsp;<a class="btn btn-primary" onclick="vm.stop('+row.rowId+')"><i class="fa fa-stop"></i>&nbsp;停止</a>&nbsp;<a class="btn btn-primary" onclick="vm.refresh('+row.rowId+')"><i class="fa fa-refresh"></i>&nbsp;刷新</a>'}}
            ],
            jsonReader : {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            },
            onCellSelect : function(rowid, index, contents){
                if(index == 4){
                    var data = $("#playerArea_jqGrid").jqGrid('getRowData',rowid)
                    var show;
                    originalData.forEach(function(item){
                        if(item.id == rowid){
                           show = item.show;
                        }
                    })
                    vm.editNotice(rowid,contents,show)
                }
            },
            multiselect : true,
            gridComplete:function(){
                //隐藏grid底部滚动条
                $("#playerArea_jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            },
            loadComplete: function (res) {
                // res是服务器返回的数据
                originalData = res.page.list;
            }
        });
   /* var mydata = [
        {id : "1",playerArea : "大厅视频"},
        {id : "2",playerArea : "留观视频"}
    ];
    for ( var i = 0; i <= mydata.length; i++){
        $("#playerArea_jqGrid").jqGrid('addRowData', i + 1, mydata[i]);
    }*/
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tMvUpload: {},
        target:['all','hallplayer','observe']
	},
    updated:function(){
        $("#playerArea_jqGrid").width='100%'
    },
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tMvUpload = {};
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
			var url = vm.tMvUpload.id == null ? "../tmvupload/save" : "../tmvupload/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tMvUpload),
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
				alert("请选择需要删除的文件列表");
				return ;
			}

            $.ajax({
                type: "POST",
                url: "../file/deletefile",
                data: JSON.stringify(ids),
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    if(r.code == 0){
                        $("#jqGrid").trigger("reloadGrid");
                    }else{
                        alert(r.msg);
                    }
                }
            });
			/*
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../file/deletefile",
				    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
				    success: function(r){
						if(r.code == 0){
                            $("#jqGrid").trigger("reloadGrid");
						}else{
							alert(r.msg);
						}
					}
				});
			});*/
		},
        relevance:function(){
			//保存关联关系
            var param =new Array();
            var mvIds = getSelectedRows('jqGrid');
            var playerAreaIds = getSelectedRows('playerArea_jqGrid');
            if (!mvIds){
                layer.msg("请选择需要关联的视频！");
                return;
            }
            if (!playerAreaIds){
                layer.msg("请选择需要关联的播放台！");
                return;
            }
            param.push({"mvIds":mvIds});
            param.push({"playerAreaIds":playerAreaIds});

            $.ajax({
                url: "../tmvupload/saveRelevance",
                dataType: 'json',
                type: 'POST',
                contentType:'application/json;charset=UTF-8',//重点关注此参数
                data: JSON.stringify(param),
                success: function (r) {
                    if (r.code ==0){
                        $("#jqGrid").trigger("reloadGrid");
                        $("#playerArea_jqGrid").trigger("reloadGrid");
                        layer.msg(r.msg)
                    } else{
                        alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("删除失败，请联系管理员！");
                }
            });
		},
        relationalShip:function(rowData){
            $("#jqGrid").jqGrid('resetSelection');//取消所有选中的行，防止视觉错误
            $("#tower_jqGrid").jqGrid('resetSelection');//取消所有选中的行，防止视觉错误

            $("#jqGrid").jqGrid('setSelection',rowData.id,false);//再次触发一下选中

            var iDs=$("#tower_jqGrid").jqGrid("getDataIDs");//获得所有行的ID数组：
            $.ajax({
                url: "../tmvupload/lisByMvid/"+rowData.id,
                success:function (data) {
                    $.each(data.list, function (index, item) {
                        $.each(iDs,function (i,idItem) {
                            if (idItem == item.towerId){
                                $("#tower_jqGrid").jqGrid('setSelection',idItem,false);
                            }
                        });
                    });
                },
                error: function () {
                }
            });
        },
		getInfo: function(id){
			$.get("../tmvupload/info/"+id, function(r){
                vm.tMvUpload = r.tMvUpload;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        stop:function(id){
            var self = this;
            $.ajax({
                url: "../queue/video/"+self.target[id]+"/STOP",
                success:function (data) {
                    layer.msg("停止指令已发送")
                },
                error: function () {
                }
            });
        },
        play:function(id){
		    var self = this;
            $.ajax({
                url: "../queue/video/"+self.target[id]+"/PLAY",
                success:function (data) {
                    layer.msg("播放指令已发送")
                },
                error: function () {
                }
            });
        },
        refresh:function(id){
            var self = this;
            $.ajax({
                url: "../queue/video/"+self.target[id]+"/RELOAD",
                success:function (data) {
                    layer.msg("刷新指令已发送")
                },
                error: function () {
                }
            });
        },
        toggleNotice:function(id,event){
            var self = this;
            if(event.target.checked){
                $.ajax({
                    url: "../queue/video/"+self.target[id]+"/NOTICE",
                    success:function (data) {
                        layer.msg("通知指令已发送")
                    },
                    error: function () {
                    }
                });
            }else{
                $.ajax({
                    url: "../queue/video/"+self.target[id]+"/UNNOTICE",
                    success:function (data) {
                        layer.msg("关闭通知指令已发送")
                    },
                    error: function () {
                    }
                });
            }
            $.ajax({
                type:"POST",
                contentType: "application/json",
                url: "../queue/notice/update",
                data:JSON.stringify({"id":id+'',"show":event.target.checked?1:0}),
                success:function (result) {
                    if(result.code == 0){
                        $("#playerArea_jqGrid").trigger("reloadGrid");
                    }
                },
                error: function () {
                }
            });

        },
        editNotice:function(id,content,show){
		    var self = this;
            layer.open({
                type: 1,
                title:'通知内容',
                skin: 'layui-layer-rim', //加上边框
                area: ['400px', '600px'], //宽高
                content: '<div style="margin:0px 5px"><textarea id="noticeContent" style="width:100%" rows="22">'+content+'</textarea></div>',
                btn:['确定'],
                yes:function(index){
                    $.ajax({
                        type:"POST",
                        contentType: "application/json",
                        url: "../queue/notice/update",
                        data:JSON.stringify({"id":id+'',"content":$('#noticeContent').val()}),
                        success:function (result) {
                            if(result.code==0){
                                $("#playerArea_jqGrid").trigger("reloadGrid");
                                layer.close(index);
                                if(show){
                                    $.ajax({
                                        url: "../queue/video/"+self.target[id]+"/NOTICE",
                                        success:function (data) {
                                            layer.msg("通知指令已发送")
                                        },
                                        error: function () {
                                        }
                                    });
                                }
                            }else{
                                layer.msg("系统错误，请联系管理员！");
                            }
                        },
                        error: function () {
                        }
                    });
                }
            });
        },
        review:function(url){
            layer.open({
                type: 2,
                title: false,
                area: ['630px', '360px'],
                shade: 0.8,
                closeBtn: 0,
                shadeClose: true,
                content: url
            });
        }

	}
});