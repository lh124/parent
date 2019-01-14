$(function () {

    //出入允许拖拽节点的父容器，一般是ul外层的容器
    //drag.init('processSelector');
    $.ajax({
        type:"get",
        url:"../inocpointmgr/processList",
        success:function (data) {
            
            pVm.loadProcess(data);
        }
    })
})
var pVm;
if (!(pVm == null || pVm == undefined)) {
} else {
    pVm = new Vue({
        el: '#processVm',
        data: {},
        methods: {

            add: function () {
                layer.open({
                    type: 1,
                    offset: '50px',
                    title: "选择流程",
                    area: ['400px', '250px'],
                    shade: 0,
                    shadeClose: false,
                    content: jQuery("#selectProcess"),
                    btn: ['确定', '取消'],
                    btn1: function (index) {
                        var sele = $("#selectProcess option:selected").val();
                        if (sele == "check") {
                            $("#processSet").find("button[tag=check]").removeAttr("disabled");
                        } else if (sele == "childProject") {
                            $("#processSet").find("button[tag=childProject]").removeAttr("disabled");
                        }
                        layer.close(index);
                    }
                });
            },
            stop: function () {
                layer.open({
                    type: 1,
                    offset: '50px',
                    title: "选择流程",
                    area: ['400px', '250px'],
                    shade: 0,
                    shadeClose: false,
                    content: jQuery("#selectProcess"),
                    btn: ['确定', '取消'],
                    btn1: function (index) {
                        var sele = $("#selectProcess option:selected").val();
                        if (sele == "check") {
                            $("#processSet").find("button[tag=check]").attr("disabled", "true");
                        } else if (sele == "childProject") {
                            $("#processSet").find("button[tag=childProject]").attr("disabled", "true");
                        }
                        layer.close(index);
                    }
                });
            },
            loadProcess:function (data) {

                var nosel=data.noselect;
                var sel=data.select;
                    $("#selectItem").empty();
                if(nosel.length>0){
                    for(var i=0;i<nosel.length;i++){
                        $("#selectItem").append("<li tag=\'"+nosel[i].tag+"\'>  <span class=\"fontCol\">"+nosel[i].text+"</span></li>\n" );
                    }
                }
                if(sel.length>0){
                    $("#target").empty();
                    for(var i=0;i<sel.length;i++){
                        $("#target").append("<li tag=\'"+sel[i].tag+"\'>  <span class=\"fontCol\">"+sel[i].text+"</span></li>\n" );
                    }
                }
            },
            defaultProcess: function () {

                $("#selectItem").empty();
                $("#selectItem").append("<li tag=\"check\">预检</li>\n" +
                    "<li tag=\"childProject\" >儿保</li>");
                $("#target").empty();

                $("#target").append("<li tag=\"register\">登记</li><li tag=\"inoculate\">接种</li><li tag=\"look\">留观</li>");
            },
            saveProcess: function () {

                var selectItems = $("#selectItem").find("li");
                var targets = $("#target").find("li");
                var noSelect = new Array();
                var select = new Array();
                if (!targets.length > 0) {
                    layer.msg("请设置流程之后再保存！")
                    return;
                }

                selectItems.each(function (index, element) {
                    var data = $(element).attr("tag");
                    noSelect.push(data);
                })
                targets.each(function (index, element) {
                    var data = $(element).attr("tag");
                    select.push(data);
                })
                var register=getIndex(select, "register");
                var inoculate=getIndex(select, "inoculate");
                var look=getIndex(select, "look");
                if(register==undefined){
                    layer.msg("流程中必须包含登记流程");
                    return;
                }
                if(inoculate==undefined){
                    layer.msg("流程中必须包含接种流程");
                    return;
                }
                if(look==undefined){
                    layer.msg("流程中必须包含留观流程");
                    return;
                }
                if(register>inoculate){
                    layer.msg("登记流程必须在接种流程之前！")
                    return;
                }
                if(inoculate>look){
                    layer.msg("接种流程必须在留观流程之前！")
                    return;
                }

                $.ajax({
                    url: "../inoculation/processSet",
                    type: "POST",
                    data: JSON.stringify({"noselect": noSelect, "select": select}),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data) {
                        if (data.code == 0) {
                            layer.msg("保存成功！");
                        } else {
                            layer.msg(data.msg);
                        }
                    }

                })
            }
        }
    });
}

function getIndex(arr,a){
    var index;
    for(var i=0; i<arr.length; i++){
        if(a == arr[i]){
            index = i;
            break;
        }
    }
    return index;
}
var drag = {

    class_name : null,  //允许放置的容器
    permitDrag : false,	//是否允许移动标识

    _x : 0,             //节点x坐标
    _y : 0,			    //节点y坐标
    _left : 0,          //光标与节点坐标的距离
    _top : 0,           //光标与节点坐标的距离

    old_elm : null,     //拖拽原节点
    tmp_elm : null,     //跟随光标移动的临时节点
    new_elm : null,     //拖拽完成后添加的新节点

    //初始化
    init : function (className){

        //允许拖拽节点的父容器的classname(可按照需要，修改为id或其他)
        drag.class_name = className;

        //监听鼠标按下事件，动态绑定要拖拽的节点（因为节点可能是动态添加的）
        $('.' + drag.class_name).on('mousedown', 'ol li', function(event){
            //当在允许拖拽的节点上监听到点击事件，将标识设置为可以拖拽
            drag.permitDrag = true;
            //获取到拖拽的原节点对象
            drag.old_elm = $(this);
            //执行开始拖拽的操作
            drag.mousedown(event);
            return false;
        });

        //监听鼠标移动
        $(document).mousemove(function(event){
            //判断拖拽标识是否为允许，否则不进行操作
            if(!drag.permitDrag) return false;
            //执行移动的操作
            drag.mousemove(event);
            return false;
        });

        //监听鼠标放开
        $(document).mouseup(function(event){
            //判断拖拽标识是否为允许，否则不进行操作
            if(!drag.permitDrag) return false;
            //拖拽结束后恢复标识到初始状态
            drag.permitDrag = false;
            //执行拖拽结束后的操作
            drag.mouseup(event);
            return false;
        });

    },

    //按下鼠标 执行的操作
    mousedown : function (event){

        console.log('我被mousedown了');
        //1.克隆临时节点，跟随鼠标进行移动
        drag.tmp_elm = $(drag.old_elm).clone();

        //2.计算 节点 和 光标 的坐标
        drag._x = $(drag.old_elm).offset().left;
        drag._y = $(drag.old_elm).offset().top;

        var e = event || window.event;
        drag._left = e.pageX - drag._x;
        drag._top = e.pageY - drag._y;

        //3.修改克隆节点的坐标，实现跟随鼠标进行移动的效果
        $(drag.tmp_elm).css({
            'position' : 'absolute',
            'background-color' : '#484eff',
            'left' : drag._x,
            'top' : drag._y,
        });

        //4.添加临时节点
        tmp = $(drag.old_elm).parent().append(drag.tmp_elm);
        drag.tmp_elm = $(tmp).find(drag.tmp_elm);
        $(drag.tmp_elm).css('cursor', 'move');

    },

    //移动鼠标 执行的操作
    mousemove : function (event){

        console.log('我被mousemove了');

        //2.计算坐标
        var e = event || window.event;
        var x = e.pageX - drag._left;
        var y = e.pageY - drag._top;
        var maxL = $(document).width() - $(drag.old_elm).outerWidth();
        var maxT = $(document).height() - $(drag.old_elm).outerHeight();
        //不允许超出浏览器范围
        x = x < 0 ? 0: x;
        x = x > maxL ? maxL: x;
        y = y < 0 ? 0: y;
        y = y > maxT ? maxT: y;

        //3.修改克隆节点的坐标
        $(drag.tmp_elm).css({
            'left' : x,
            'top' : y,
        });

        //判断当前容器是否允许放置节点
        $.each($('.' + drag.class_name + ' ol'), function(index, value){

            //获取容器的坐标范围 (区域)
            var box_x = $(value).offset().left;     //容器左上角x坐标
            var box_y = $(value).offset().top;      //容器左上角y坐标
            var box_width = $(value).outerWidth();  //容器宽
            var box_height = $(value).outerHeight();//容器高

            //给可以放置的容器加背景色
            if(e.pageX > box_x && e.pageX < box_x-0+box_width && e.pageY > box_y && e.pageY < box_y-0+box_height){

                //判断是否不在原来的容器下（使用坐标进行判断：x、y任意一个坐标不等于原坐标，则表示不是原来的容器）
                if($(value).offset().left !== drag.old_elm.parent().offset().left
                    || $(value).offset().top !== drag.old_elm.parent().offset().top){

                    $(value).css('background-color', '#f5f5f5');
                }
            }else{
                //恢复容器原背景色
                $(value).css('background-color', '#f5f5f5');
            }

        });

    },

    //放开鼠标 执行的操作
    mouseup : function (event){

        console.log('我被mouseup了');
        //移除临时节点
        $(drag.tmp_elm).remove();

        //判断所在区域是否允许放置节点
        var e = event || window.event;

        $.each($('.' + drag.class_name + ' ol'), function(index, value){

            //获取容器的坐标范围 (区域)
            var box_x = $(value).offset().left;     //容器左上角x坐标
            var box_y = $(value).offset().top;      //容器左上角y坐标
            var box_width = $(value).outerWidth();  //容器宽
            var box_height = $(value).outerHeight();//容器高

            //判断放开鼠标位置是否想允许放置的容器范围内
            if(e.pageX > box_x && e.pageX < box_x-0+box_width && e.pageY > box_y && e.pageY < box_y-0+box_height){

                //判断是否不在原来的容器下（使用坐标进行判断：x、y任意一个坐标不等于原坐标，则表示不是原来的容器）
                if($(value).offset().left !== drag.old_elm.parent().offset().left
                    || $(value).offset().top !== drag.old_elm.parent().offset().top){
                    //向目标容器添加节点并删除原节点
                    tmp = $(drag.old_elm).clone();
                    var newObj = $(value).append(tmp);
                    $(drag.old_elm).remove();
                    //获取新添加节点的对象
                    drag.new_elm = $(newObj).find(tmp);
                }
            }
            //恢复容器原背景色
            $(value).css('background-color', '#f5f5f5');
        });

    },

};