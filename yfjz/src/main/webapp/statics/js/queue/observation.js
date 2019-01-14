$(function () {
    var str=$("#search").val();
    if (str!=null || str!=''){
        scan_code_callback(str)
    }
    $("#search").focus();
    clickbtn();

});

function clickbtn() {
    var search="";

    $("#zero").on("click",function () {
        search+="0";
        $("#search").val(search);
    })
    $("#one").on("click",function () {
        search+="1";
        $("#search").val(search);
    })
    $("#two").on("click",function () {
        search+="2";
        $("#search").val(search);
    })
    $("#three").on("click",function () {
        search+="3";
        $("#search").val(search);
    })
    $("#four").on("click",function () {
        search+="4";
        $("#search").val(search);
    })
    $("#five").on("click",function () {
        search+="5";
        $("#search").val(search);
    })
    $("#six").on("click",function () {
        search+="6";
        $("#search").val(search);
    })
    $("#seven").on("click",function () {
        search+="7";
        $("#search").val(search);
    })
    $("#eight").on("click",function () {
        search+="8";
        $("#search").val(search);
    })
    $("#nine").on("click",function () {
        search+="9";
        $("#search").val(search);
    })
    // 清除
    $("#clear").on("click",function () {
        search="";
        $("#search").val("");

    })

    // 查询
    $("#query").on("click",function () {
        var strs=$("#search").val();
        $.ajax({
            type: "GET",        //jsonp跨域请求只能是get请求
            async: true,       //jsonp跨域请求只能是异步请求
            // contentType:'application/x-javascript;charset=utf-8',
            url: '../tchildinoculate/queryobservation?notext='+strs,
            dataType: "jsonp",
            contentType: "application/jsonp;charset=utf-8",
            jsonp: 'jsoncallback',
            success: function(data){   //定义了回调函数名称ajax自己处理返回的数据，传回success属性中
                $("#content").html("");
                $("#contents").html("");
                $("#attention").html("");
                if(data.data.length>0){
                    var con="";
                    var attention="";
                    var obs="";
                    for (var i=0;i<data.data.length;i++){
                        var oDate2 = new Date(formatDateTime(data.data[i].id));
                        var oDate1 = new Date(formatDateTime(data.data[i].create_time));
                        var minute = parseInt(oDate2 - oDate1) / 1000 / 60;
                        $("#finsh").show();
                        if (minute>30){
                            $("#tt").ready(function(){//页面加载完之后，自动执行该方法
                                setTimeout(function(){$("#finsh").hide();},5000);//2秒后执行该方法
                            });
                            obs="完成"
                            $("#finsh").text("留观完成可以离开");
                            // $("#obs").val("留观完成");
                        }else{
                            $("#tt").ready(function(){//页面加载完之后，自动执行该方法
                                setTimeout(function(){$("#finsh").hide();},5000);//2秒后执行该方法
                            });
                            obs ="未完成";
                            $("#finsh").text("留观未完成请勿离开");
                            // $("#obs").val("留观未完成");
                        }
                    }

                    for(var i=0;i<data.data.length;i++){
                        con+='<tr><th>'+data.data[i].child_code+'</th> <th>'+data.data[i].child_name+'</th><th>'+data.data[i].inoc_bact_code+'</th><th>'+data.data[i].inoc_time+'</th><th>'+data.data[i].inoc_inpl_id+'</th><th>'+formatDateTime(data.data[i].create_time)+'</th><th>'+obs+'</th></tr>';
                        attention=data.data[i].attention;
                        console.log(formatDateTime(data.data[i].id));
                        console.log(formatDateTime(data.data[i].create_time));

                        console.log(minute);
                    }
                    $("#content").html(con);
                    $("#attention").html(attention);

                    var cons="";
                    for(var i=0;i<data.list.length;i++){
                        cons+='<tr><th>'+data.list[i].child_code+'</th> <th>'+data.list[i].child_name+'</th><th>'+data.list[i].inoc_bact_code+'</th><th>'+data.list[i].inoc_time+'</th><th>'+data.list[i].inoc_inpl_id+'</th><th>'+formatDateTime(data.list[i].create_time)+'</th></tr>';
                    }
                    $("#contents").html(cons);
                }
            },
            error:function(){}
        });
    })
}


function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
};


function scan_code_callback(code){
    var e = document.createEvent("MouseEvents");
    e.initEvent("click", true, true);
    document.getElementById("query").dispatchEvent(e);
    $("#search").val(code)
    $("#search").on("keydown",function () {
        var str=$("#search").val()
        console.log(str)
        $.ajax({
            type: "GET",        //jsonp跨域请求只能是get请求
            async: true,       //jsonp跨域请求只能是异步请求
            url: '../tchildinoculate/queryobservation?notext='+str,
            dataType: "jsonp",
            contentType: "application/jsonp;charset=utf-8",
            jsonp: 'jsoncallback',
            success: function(data){   //定义了回调函数名称ajax自己处理返回的数据，传回success属性中
                $("#content").html("");
                $("#contents").html("");
                $("#attention").html("");
                var obs="";
                for (var i=0;i<data.data.length;i++){
                    var oDate2 = new Date(formatDateTime(data.data[i].id));
                    var oDate1 = new Date(formatDateTime(data.data[i].create_time));
                    var minute = parseInt(oDate2 - oDate1) / 1000 / 60;
                    $("#finsh").show();
                    if (minute>30){
                        obs="完成"
                        $("#tt").ready(function(){//页面加载完之后，自动执行该方法
                            setTimeout(function(){$("#finsh").hide();},5000);//2秒后执行该方法
                        });
                        $("#finsh").text("留观完成可以离开");
                        // $("#obs").val("留观完成");
                    }else{
                        $("#tt").ready(function(){//页面加载完之后，自动执行该方法
                            setTimeout(function(){$("#finsh").hide();},5000);//2秒后执行该方法
                        });
                        obs ="未完成";
                        $("#finsh").text("留观未完成请勿离开");
                        // $("#obs").val("留观未完成");
                    }
                }

                if(data.data.length>0){
                    var con="";
                    var attention="";
                    for(var i=0;i<data.data.length;i++){
                        con+='<tr><th>'+data.data[i].child_code+'</th> <th>'+data.data[i].child_name+'</th><th>'+data.data[i].inoc_bact_code+'</th><th>'+data.data[i].inoc_time+'</th><th>'+data.data[i].inoc_inpl_id+'</th><th>'+formatDateTime(data.data[i].create_time)+'</th><th>'+obs+'</th></tr>';
                        attention=data.data[i].attention;
                        // console.log(data.data[i].id);
                        // console.log(data.data[i].createTime);
                        // var minute = Math.floor((formatDateTime(data.data[i].id)-formatDateTime(data.data[i].createTime))%86400%3600/60);
                        // console.log(minute);
                    }
                    $("#content").html(con);
                    $("#attention").html(attention);
                    var cons="";
                    for(var i=0;i<data.list.length;i++){
                        cons+='<tr><th>'+data.list[i].child_code+'</th> <th>'+data.list[i].child_name+'</th><th>'+data.list[i].inoc_bact_code+'</th><th>'+data.list[i].inoc_time+'</th><th>'+data.list[i].inoc_inpl_id+'</th><th>'+formatDateTime(data.list[i].create_time)+'</th></tr>';
                    }
                    $("#contents").html(cons);
                }
            }
        });
        $("#search").val("");
    })







}