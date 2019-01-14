var $,tab,dataStr,layer;
layui.config({
	base : "statics/js/public/"
}).extend({
	"bodyTab" : "bodyTab"
});
layui.use(['bodyTab','form','element','layer','jquery'],function(){
	var form = layui.form,
		element = layui.element;
		$ = layui.$;
    	layer = parent.layer === undefined ? layui.layer : top.layer;
		tab = layui.bodyTab({
			openTabNum : "50",  //最大可打开窗口数量
			url : "/statics/json/menu.json" //获取菜单json地址
		});
    //获取登录用户的角色，判断是否显示工作台
    $.ajax({
        url: "getLoginUserRole",
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',//重点关注此参数
        success : function(data){
            if (data.code == 0){
                layer.open({
                    type: 1
                    ,title: false //不显示标题栏
                    ,closeBtn: false
                    ,area: ['400px','400px']
                    ,shade: 0.8
                    ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,btn: ['确定']
                    ,btnAlign: 'c'
                    ,moveType: 0
                    ,content: $("#tmpl-content")
                    ,yes: function(layero){

                        saveSelectTowersToSession(layero,data.msg);//第二个参数是返回的角色数量
                    }
                });
                ininSelectTowerId();
            } else{
                //机构管理员不需要选台
            }
        },error:function(){
            alert("获取数据失败,请联系管理员!");
        }
    });

	//通过顶部菜单获取左侧二三级菜单   注：此处只做演示之用，实际开发中通过接口传参的方式获取导航数据
	function getData(){
        $.getJSON("sys/menu/user?_"+$.now(), function(data){
            
            dataStr = data['menuList'];
            //重新渲染左侧菜单
            tab.render();
        });
		/*$.getJSON(tab.tabConfig.url,function(data){
			if(json == "menuList"){
				dataStr = data['menuList'];
				//重新渲染左侧菜单
				tab.render();
			}
		})*/
	}


	//隐藏左侧导航
	$(".hideMenu").click(function(){
		$(".layui-layout-admin").toggleClass("showMenu");
		//渲染顶部窗口
		tab.tabMove();
	});

	//通过顶部菜单获取左侧二三级菜单   注：此处只做演示之用，实际开发中通过接口传参的方式获取导航数据
	getData();

	//手机设备的简单适配
    $('.site-tree-mobile').on('click', function(){
		$('body').addClass('site-mobile');
	});
    $('.site-mobile-shade').on('click', function(){
		$('body').removeClass('site-mobile');
	});

	// 添加新窗口
	$("body").on("click",".layui-nav .layui-nav-item a:not('.mobileTopLevelMenus .layui-nav-item a')",function(){
		//如果不存在子级
		if($(this).siblings().length == 0){
			addTab($(this));
			$('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
		}
		$(this).parent("li").siblings().removeClass("layui-nav-itemed");
	});

	//清除缓存
	$(".clearCache").click(function(){
		window.sessionStorage.clear();
        window.localStorage.clear();
        var index = layer.msg('清除缓存中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            layer.msg("缓存清除成功！");
        },1000);
    });

	//刷新后还原打开的窗口
    if(cacheStr == "true") {
        if (window.sessionStorage.getItem("menu") != null) {
            menu = JSON.parse(window.sessionStorage.getItem("menu"));
            curmenu = window.sessionStorage.getItem("curmenu");
            var openTitle = '';
            for (var i = 0; i < menu.length; i++) {
                openTitle = '';
                if (menu[i].icon) {
                    if (menu[i].icon.split("-")[0] == 'icon') {
                        openTitle += '<i class="seraph ' + menu[i].icon + '"></i>';
                    } else {
                        openTitle += '<i class="layui-icon">' + menu[i].icon + '</i>';
                    }
                }
                openTitle += '<cite>' + menu[i].title + '</cite>';
                openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + menu[i].layId + '">&#x1006;</i>';
                element.tabAdd("bodyTab", {
                    title: openTitle,
                    content: "<iframe src='" + menu[i].href + "' data-id='" + menu[i].layId + "'></frame>",
                    id: menu[i].layId
                });
                //定位到刷新前的窗口
                if (curmenu != "undefined") {
                    if (curmenu == '' || curmenu == "null") {  //定位到后台首页
                        element.tabChange("bodyTab", '');
                    } else if (JSON.parse(curmenu).title == menu[i].title) {  //定位到刷新前的页面
                        element.tabChange("bodyTab", menu[i].layId);
                    }
                } else {
                    element.tabChange("bodyTab", menu[menu.length - 1].layId);
                }
            }
            //渲染顶部窗口
            tab.tabMove();
        }
    }else{
		window.sessionStorage.removeItem("menu");
		window.sessionStorage.removeItem("curmenu");
	}
});

//打开新窗口
function addTab(_this){
	tab.tabAdd(_this);
}

function ininSelectTowerId(){
    $("#selectSM").empty();
    $.ajax({
        url: "getTowerList",
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',//重点关注此参数
        success : function(data){
            if (data.code ==0){
                for (var i = 0; i < data.towers.length; i++) {
                    /*if (i % 3 == 0){
                        $("#selectSM").append("<br/>");
                    }*/
                    $("#selectSM").append("<p><input type='checkbox' class='chk-input-towers' name='towers' onclick='judg("+ JSON.stringify(data.towers[i]).replace(/"/g, '&quot;')+")' class='tower-left' value='"+data.towers[i].id+"'/><label class='chk-input-towers-text'>"+data.towers[i].tower_name+"</label></p>");
                }
                    // $("#selectSM").find("input[type=checkbox]").css("margin","0");
                    $("#selectSM").find("label").css("margin","0");
                console.log($("#selectSM").find("input[type=checkbox]"))
            }
        },error:function(){
            alert("获取数据失败,请联系管理员!");
        }
    });
}
//存放选中的工作台,点击确定按钮时，需要提交到后台，保存到session中
var towerlist = new Array();
//点击判断是否已经勾选了同类工作台
function judg(obj) {

    //先判断是选中还是取消，如果是取消，则需要移除数组中的对象
    var isCheck = $("input[name=towers][value="+obj.id+"]").prop("checked");
    if (!isCheck){
        //取消选中时，移除保存的值
        for (var index = 0; index< towerlist.length; index++) {
            if (towerlist[index].tower_name ==obj.tower_name){
                towerlist.splice(index,1);
            }
        }
        return;
    }
    //循环判断数组中是否存在新添加的对象
    for (var i = 0; i < towerlist.length; i++) {
        if (towerlist[i].tower_nature_id==obj.tower_nature_id){
            layer.alert("不能同时选择相同类型的工作台");
            //取消当前选中的input的选中状态
            $("input[name=towers][value="+obj.id+"]").prop("checked", false);//自定义的DOM属性
            return;
        }
    }
    towerlist.push(obj);
}

/**
 * 将选中的工作台存入session中
 * layero:弹框的索引号
 */
function saveSelectTowersToSession(layero,num) {
    if (towerlist.length <= 0){
        layer.alert("请选择工作台");
        return;
    }
    if (towerlist.length < num){
        layer.alert("您选择的工作台数量与角色数量不匹配，您的角色数量为"+num);
        return;
    }
    $.ajax({
        type: "POST",
        url: "saveSelectTowersToSession",
        data: JSON.stringify(towerlist),
        contentType:'application/json;charset=UTF-8',
        success: function(data){
            if (data.code == 0){
                layer.close(layero);
                $("#selectSM").css("display","none");
                connect(towerlist);

                var main = $('#main')[0].contentWindow;
                main.loadUserInfo();
            }else{
                layer.alert(data.msg);
            }
        }
    });
}

//图片管理弹窗
function showImg(){
    $.getJSON('json/images.json', function(json){
        var res = json;
        layer.photos({
            photos: res,
            anim: 5
        });
    });
}