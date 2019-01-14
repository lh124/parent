$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', width: 45, key: true,hidden: true},
			{ label: '用户名', name: 'username', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '角色', name: 'roleNames', width: 100 },
			{ label: '创建时间', name: 'createTime', width: 80}                   
        ],
		viewrecords: true,
        height: $(window).height() - 140,
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


    // 加载菜单树权限
    vm.getDepartMenuTree(null);
});


var setting = {
    view: {
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: 0
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true,
        chkStyle: "checkbox",
        chkboxType: { "Y": "", "N": "" }
       /* Y 属性定义 checkbox 被勾选后的情况；
		N 属性定义 checkbox 取消勾选后的情况；
		"p" 表示操作会影响父级节点；
		"s" 表示操作会影响子级节点。
		请注意大小写，不要改变*/
    }
};
var ztree;


var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null
		},
        type:1,
		showList: true,
		title:null,
		roleList:{},
		user:{
			status:1,
			roleIdList:[],
            userIds:null,
            orgId:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.type=1;
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.user = {status:1,roleIdList:[]};
			
			//获取角色信息
			this.getRoleList();
		},
		update: function () {
            vm.type=2;
			var userId = getSelectedRow();
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
			
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/user/delete",
                    contentType:'application/json;charset=UTF-8',
				    data: JSON.stringify(userIds),
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
		saveOrUpdate: function (event) {

			var url = vm.user.userId == null ? "../sys/user/save" : "../sys/user/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.user),
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
		getUser: function(userId){
			$.get("../sys/user/info/"+userId, function(r){
				vm.user = r.user;
			});
		},
		getRoleList: function(){
			$.get("../sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
        getDepartMenuTree: function(pid) {
            //加载菜单树
            $.get("../sys/depart/departMenuTree?pid="+pid, function(r){
                ztree = $.fn.zTree.init($("#departTree"), setting, r.departMenuList);
                //展开所有节点
                // ztree.expandAll(true);
                // if(roleId != null){
                //     vm.getRole(roleId);
                // }
            });
        },
        //绑定功能 将机构编码与用户进行绑定
        bindingDepart:function(){
            //获取选择的菜单
            var nodes = ztree.getCheckedNodes(true);
            if (nodes.length==0){
                alert("请选择相应的卫生院进行绑定");
                return;
			}

            if (nodes.length>=2){
                alert("只能勾选一个机构");
                return;
			}
			if(nodes[0].id.length != 10){
                alert("请选择相应的卫生院或者卫生服务中心");
            	return;
			}
            //获取选中的角色 两行组合获取jqgrid一行的数据
            var rowIds = getSelectedRows();
            var selectUserIds = new Array();
            for(var i=0; i<rowIds.length; i++) {
                var rowData = $("#jqGrid").jqGrid('getRowData',rowIds[i]);
                selectUserIds.push(rowData.userId)
            }
            vm.user.userIds = selectUserIds;
			vm.user.orgId= nodes[0].id;

            var url = "../sys/user/bindingDepart";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.user),
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
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
		}
	}
});