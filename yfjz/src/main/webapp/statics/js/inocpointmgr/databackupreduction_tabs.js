
$(function () {
    $("#backupGird").jqGrid({
        url: '../file/getdownloadfiles?type=download',
        datatype: "json",
        //caption:'库存信息',
        colModel: [
            { label: '文件名', name: 'fileName',width: 150},
            { label: '文件大小(M)', name: 'fileSize',formatter:size_formatter,width: 150},
            { label: '创建时间', name: 'lastModifyTime',width: 150},
            { label: '最后修改时间', name: 'lastModifyTime',width: 150}
        ],
        viewrecords: true,
        height:400,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        autowidth:true,
        shrinkToFit:false,
        multiselect: true,
        pager: "#backupGirdPage",
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
            $("#stockJqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            /*$(".ui-jqgrid-bdiv").removeClass("overflow");
            $(".ui-jqgrid-bdiv").css({ "overflow-x" : "auto" })*/
        },
        ondblClickRow:function(rowid,rowData){
            layer.confirm('请问您需要下载备份数据吗?',function(index){
                if (index){
                    var row=$("#backupGird").jqGrid("getRowData",rowid);
                    $('<form action="../file/downloadfile" method="get">' +  // action请求路径及推送方法
                        '<input type="text" name="filename" value="'+encodeURI(encodeURI(row.fileName))+'"/>' + // 文件名称
                        '</form>')
                        .appendTo('body').submit().remove();
                }
                layer.close(index);
            });

        }
    });
    $("#restoreGird").jqGrid({
        url: '../file/getdownloadfiles?type=upload',
        datatype: "json",
        //caption:'库存信息',
        colModel: [
            { label: '文件名', name: 'fileName',width: 150},
            { label: '文件大小(M)', name: 'fileSize',formatter:size_formatter,width: 150},
            { label: '创建时间', name: 'lastModifyTime',width: 150},
            { label: '最后修改时间', name: 'lastModifyTime',width: 150}
        ],
        viewrecords: true,
        height:400,
        rowNum: 10,
       // width: 600,
        rowList : [10,30,50],
        rownumbers: true,
         autowidth:true,
        shrinkToFit:false,
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
            $("#stockJqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            /*$(".ui-jqgrid-bdiv").removeClass("overflow");
           $(".ui-jqgrid-bdiv").css({ "overflow-x" : "auto" })*/
        }
    });
})

function size_formatter(value,rows) {
    if(value!=null){
        return ((value/1024)/1024).toFixed(2);
    }else{
        return ;
    }
}

function shellhandler(opt) {
    // $('#w').window('open');

    var url = 'ws://' +window.location.host+"/yfjz/backupWebSocketHandler";
    var websocket = null;
    if ('WebSocket' in window) {
        //Websocket的连接
        websocket = new WebSocket(url);//WebSocket对应的地址
    }else {
        //SockJS的连接
        websocket = new SockJS(url);    //SockJS对应的地址
    }
    connect(websocket,opt);

    //  disconnect(websocket);
}

/**
 * 上传备份文件
 */
function upload() {

    var fileName=$("#uploadFile").val();
    if (fileName==null||fileName==""||fileName==undefined){
        layer.msg("请选择备份文件！");
        return;
    }
    var d=fileName.length-"des3".length;
    var ret=d>0&&fileName.lastIndexOf("des3")==d;
    if (!ret){
        layer.msg("选择的文件不正确，请选择正确的备份文件！");
        return;
    }
        $.ajaxFileUpload({
            url :'../file/uploadfile',//后台请求地址
            type: 'post',//请求方式  当要提交自定义参数时，这个参数要设置成post
            secureuri : false,//是否启用安全提交，默认为false。
            fileElementId : 'uploadFile',// 需要上传的文件域的ID，即<input type="file">的ID。
            dataType : 'json',//服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。如果json返回的带pre,这里修改为json即可解决。
            success : function (result,status) {//提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                layer.msg(result.message);
                $("#restoreGird").trigger("reloadGrid");
                $("#uploadFile").val("");
            },
            error : function (json, status, e) {//提交失败自动执行的处理函数。
                console.log(12312)
            }
        });
}
function download_formatter(value,rows) {
    if(value!=null&&value!=''&&value!='undifined'){
        return "<a href='#' onclick='download("+value+")'>"+value+"</a>";
    }else{
        return ;
    }
}

function log(message,opt) {
    var handler;
    if ("backup"==opt){
        handler =document.getElementById("console_output");
    }else{
        handler =document.getElementById("console");
    }

    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    if(message.indexOf("成功")>=0){
        console.log(message);
        $("#backupGird").trigger('reloadGrid');
    }
    handler.appendChild(p);
    handler.scrollTop = handler.scrollHeight;
}
function test(opt) {
    for(var i=0;i<1000;i++){
        log(i,opt);
    }
}
function connect(ws,opt) {

    ws.onopen = function () {
        log('开始准备: 备份或还原数据库需要几分钟时间，请耐心等待....',opt);
        ws.send("{'opt':'"+opt+"'}");
    };
    ws.onmessage = function (event) {
        debugger
        log('日志信息: ' + event.data,opt);
    };
    ws.onclose = function (event) {
        log('结束: 关闭连接.',opt);
        log(event,opt);
    };
}

function restore() {
   layer.confirm('请问您需要恢复数据吗?',function (index) {
        if(index){
            shellhandler('restore');
        }
        layer.close(index);
    })
}
function downloadFile() {
    debugger
    var rowId = $("#backupGird").jqGrid('getGridParam','selarrrow');
    //$("#queryResultTable").saveRow(rowId);//保存行数据
    if (rowId == null || rowId == undefined||rowId.length>1||rowId.length==0) {
        layer.msg("请选择一个备份文件！")
        return;
    }

    var row=$("#backupGird").jqGrid("getRowData",rowId[0]);
    $('<form action="../file/downloadfile" method="get">' +  // action请求路径及推送方法
        '<input type="text" name="filename" value="'+encodeURI(encodeURI(row.fileName))+'"/>' + // 文件名称
        '</form>')
        .appendTo('body').submit().remove();
}