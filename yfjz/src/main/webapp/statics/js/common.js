//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';


var basePath;//获取项目根路径
var Win;


$(function () {
    basePath = getRootPath();
    Win = autoPage();
});
/**
 * js获取项目根路径
 host  "localhost:8080"
 hostname "localhost"
 href "http://localhost:8080/yfjz/admin/hospital.html"
 origin "http://localhost:8080"
 pathname "/yfjz/admin/hospital.html"
 port  "8080"
 protocol "http:"
 */
function getRootPath() {
    var curPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curPath.indexOf(pathName);
    var localhostPath = curPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPath + projectName;
}

/**
 * 字符串替换
 * @param str 源字符串
 * @param source 需要替换的字符串
 * @param dest 需要被替换成具体的字符串
 * @returns {string | void}
 */
function my_replace(str, source, dest) {
    //第一个参数是要替换掉的内容，第二个参数"g"表示替换全部（global）。
    var regexp = new RegExp(source, "g"); //定义正则表达式
    // 第一个参数是正则表达式。 本例会将全部匹配项替换为第二个参数。
    return str.replace(regexp, dest);
}

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
};

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
};


//选择一条记录
/**
 *
 * @param _jqgirdId 可传，可不传  默认：jqGrid
 * @returns {*}
 */
function getSelectedRow(_jqgirdId) {

    if (_jqgirdId == undefined || _jqgirdId == ''){
        _jqgirdId = "jqGrid";
    }
    var grid = $("#" + _jqgirdId);
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    return selectedIDs[0];
}


//选择多条记录
/**
 *
 * @param _jqgirdId 可传，可不传  默认：jqGrid
 * @returns {*}
 */
function getSelectedRows(_jqgirdId) {
    if (_jqgirdId == undefined || _jqgirdId == ''){
        _jqgirdId = "jqGrid";
    }
    var grid = $("#"+_jqgirdId);
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	layer.msg("请选择一条记录");
    	return ;
    }
    return grid.getGridParam("selarrrow");
}

/**
 * 获取给定id的jqgrid的所有行的数据
 * @param _jqgirdId
 * @returns {any[]}
 */
function getJQAllData(_jqgirdId) {
    //拿到grid对象
    var obj = $("#" + _jqgirdId);
    //获取grid表中所有的rowid值
    var rowIds = obj.getDataIDs();
    //初始化一个数组arrayData容器，用来存放rowData
    var arrayData = new Array();
    if (rowIds.length > 0) {
        for (var i = 0; i < rowIds.length; i++) {
            arrayData.push(obj.getRowData(rowIds[i]));
        }
    }
    return arrayData;
}

/*******************************************************************************************************************************/
var urlarr = new Array();//文件上传全局变量 存放服务器返回的url地址
var filenamearr = new Array();//文件上传全局变量 存放服务器返回的文件名称
var filesizearr = new Array();//文件上传全局变量 存放服务器返回的文件大小
/**
 * 专门做图片上传
 * @param _fileid  input type=file id = _fileid
 * @param _file_list 需要展示信息的id <div id="file_list"></div>
 * @param _msg_erro_id 展示错误信息的span的id <span id="msg_erro_id"></span>
 * @returns {boolean}
 */
function jquerAjaxImageUpload(_fileid, _file_list, _msg_erro_id, _imgid) {
    $("#" + _imgid).show();//显示显示的gif动画
    $('#' + _msg_erro_id).empty();//每次调用文件上传都需要清除错误提示文字
    $.ajaxFileUpload({
            url: basePath + '/file/uploadfile',//用于图片，文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: _fileid, //文件上传域的ID
            contentType:"application/json",
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data, status) {
                /*var d = eval('('+data+')'); 解决web后端返回的是json字符串，而不是json对象*/
                $("#img_div_hidden_id").hide();
                if (data.code == 1) {
                    var arr = data.msg.split("##");
                    var fileUrl = arr[0];
                    var pos = fileUrl.lastIndexOf("/") + 1;
                    fileUrl = fileUrl.substr(pos);
                    var pos2 = fileUrl.lastIndexOf(".");
                    fileUrl = fileUrl.substring(0, pos2);
                    var span_id = fileUrl;//通过文件名作为每个文件button的id
                    urlarr.push(arr[0]);//只存相对路径
                    filenamearr.push(arr[1]);
                    filesizearr.push(arr[2]);
                    var html = '<span id="' + span_id + '" style="line-height: 36px; padding-left:24%">';
                    html += '<lable>文件名称：' + arr[1] + '</lable>';
                    html += '<button type="button" class="btn btn-del" style="float:right;">删除文件</button>';
                    html += "<br/></span>";
                    $('#' + _file_list).append(html);
                    onlistenbingclick(arr[0], arr[1],arr[2], $("#" + span_id));//该方法只能在动态添加的dom后面执行，否则找不到元素
                } else {
                    var html = "<h6 style='color:red; padding-left:24%'>" + data.msg + "</h6>";//有待优化界面展示效果，错误提示
                    $('#' + _msg_erro_id).append(html);
                }
                $("#" + _imgid).hide();//关闭显示的gif动画
            },
            error: function (data, status, e) {
                console.log("发生异常");
            }
        }
    );
    return false;
}



/**
 * 执行删除文件绑定方法 点击删除文件时
 * @param _url
 * @param _filename
 * @param _spaninfo
 */
function onlistenbingclick(_url, _filename,_filesize, _spaninfo) {
    $("#file_list button").click(function () {
        jquerAjaxFileDelete(_url, _filename,_filesize, _spaninfo);
    });
}

/**
 * 删除文件
 * @param _url 文件路径
 * @param _showmsgid 消息显示节点的id
 * @returns {boolean}
 */
function jquerAjaxFileDelete(_url, _filename,_filesize, spaninfo) {
    var param = {
        fileUrl: _url
    };
    /**
     * 方法说明：
     * data:JSON.stringify(param),
     * contentType:"application/json",
     * dataType: 'json',这三个参数必须同时使用，web后端可以使用@RequestBody Map<String, String> params这个方式来接收
     * public R deleteFileFromServer(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String, String> params)
     */
    $.ajax({
        type: "POST",
        url:'../file/deletefile',
        data:JSON.stringify(param),
        contentType:"application/json",
        dataType: 'json',
        success: function (data) {
            alert(data.msg);
            if(data.code > 0){
                $("#show_delete_msg").text(data.msg);
                urlarr.splice($.inArray(_url, urlarr), 1);//文件路径 将新增的文件的服务器路径从数组中删除
                filenamearr.splice($.inArray(_filename, filenamearr), 1);//文件名 将新增的文件的服务器路径从数组中删除
                filesizearr.splice($.inArray(_filesize, filesizearr), 1);//文件名 将新增的文件的服务器路径从数组中删除
                $(spaninfo).remove();//删除动态生成的每个文件上传的url地址 对应的是一个span下展示的信息全部删除
            }
        },
        error: function () {
            console.log("error");
        }
    });
    return false;
}

/**
 *
 * @param _fileid  input type=file id = _fileid
 * @param _file_list 需要展示信息的id <div id="file_list"></div>
 * @param _msg_erro_id 展示错误信息的span的id <span id="msg_erro_id"></span>
 * @returns {boolean}
 */
function uploadFile(_fileid) {
    $("#show_msg_span_id").html("<font color='red'>文件正在上传。。。</font>");
    $.ajaxFileUpload({
            url: basePath + '/file/commonuploadfile',
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: _fileid, //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data, status) {
                if (data.code == 0) {
                    $("#show_msg_span_id").html("<font color='blue'>文件上传成功</font>");
                    $("#jqGrid").trigger("reloadGrid");
                } else {
                    $("#show_msg_span_id").html("<font color='red'>"+data.data+"</font>");
                }
            },
            error: function (data, status, e) {
            }
        }
    );
    return false;
}

/**
 * 前端模糊查询的方法
 * 调用 案例 doSearch(val,data,['bioCode','bioName','bioCnSortname','bioEnSortname'],$("#vaccine"));
 * 田金海
 * @param q 查询关键字
 * @param data 查询数据，加载所有的数据
 * @param searchList 数据的字段名
 * @param ele  jqgrid对象
 */
function doSearch(q,data,searchList,ele){
    //ele.datagrid('loadData', []);
    console.log(data);
    ele.setGridParam({datatype : 'local',data: []}).trigger('reloadGrid');
    if(q == ""){
        //ele.datagrid('loadData', data);
        ele.setGridParam({datatype : 'local',data: data}).trigger('reloadGrid');
        return;
    }
    var rows = [];
    $.each(data,function(i,obj){
        for(var p in searchList){
            var v = obj[searchList[p]];
            if (!!v && v.toString().indexOf(q) >= 0){
                rows.push(obj);
                break;
            }
        }
    });
    if(rows.length == 0){
        //ele.datagrid('loadData', []);
        ele.setGridParam({datatype : 'local',data: []}).trigger('reloadGrid');
        return;
    }
    //ele.datagrid('loadData', rows);
    ele.clearGridData();
    ele.setGridParam({datatype : 'local',data: rows}).trigger('reloadGrid');
}
/**
 * JS时间格式化
 * @param inputTime 时间
 * @returns {string} 格式化后的时间
 */
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
    // return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    return y + '-' + m + '-' + d;
}
/**
 * 获取表格中的所有行数据
 * @param ele 表格jquery对象
 * @returns {any[]} 表格中所有行数据
 */
function getRows(ele) {
    var ids=ele.jqGrid("getDataIDs");
    var rows=new Array();
    for(var i=0;i<ids.length;i++){
        var row=ele.jqGrid("getRowData",ids[i]);
        rows.push(row);
        ele.getRowData()
    }
    return rows;
}
/**
 * 结束编辑表格模式
 * @param ele 表格jquery对象
 */
function endEdit(ele) {
    var ids=ele.jqGrid("getDataIDs");
    for(var i=0;i<ids.length;i++){
        ele.saveRow(ids[i]);
    }
}

/**
 * 自适应浏览器高度，宽度
 * @returns {{WinW: *, WinH: *}}
 */
function autoPage() {
    if(window.innerHeight) {// all except IE
        winW = window.innerWidth;
        winH = window.innerHeight;
    } else if (document.documentElement && document.documentElement.clientHeight) {// IE 6 Strict Mode
        winW = document.documentElement.clientWidth;
        winH = document.documentElement.clientHeight;
    } else if (document.body) { // other
        winW = document.body.clientWidth;
        winH = document.body.clientHeight;
    }  // for small pages with total size less then the viewport
    return {WinW:winW, WinH:winH};
}

//输出对象到控制台
function log(obj) {
    console.log(obj);
}

function Unformat_Select(cellvalue, options, cellobject){
    var unformatValue;
    var name=options.colModel.editoptions.value;
    var result= eval('({"' + name.replace(/:/g, '":"').replace(/;/g, '","') + '"})');
    $.each(result, function (k, value)
    {
        if (cellvalue == value)
        {
            unformatValue = k;
        }
    });
    return unformatValue;
}

/**
 * 判断字符串是否以某个字符串结尾
 * @author tingle
 * @date 2018/8/31
 * @param endStr
 * @returns {boolean}
 */
String.prototype.endWith=function(endStr){
    var d=this.length-endStr.length;
    return (d>=0&&this.lastIndexOf(endStr)==d)
}
/**
 * 比较两个时间
 * @param date1 例如2018-09-01
 * @param date2 例如2018-09-11
 * @returns {boolean}
 */
function compareDate(date1,date2) {
    var d1 = new Date(date1.replace(/\-/g, "\/"));
    var d2 = new Date(date2.replace(/\-/g, "\/"));
    if(d1 >d2)
    {
        return  true;
    }
    return false;
}
/**
 *  *  求自然月日期
 *  by 刘琪  2018-05-18
 *
 *
 * @param num 向前，向后推迟的月份数
 * @param format  日期连接符  “-”，“/”等等
 * @param day  向前，向后推迟的天数
 */
function getMonthBeforeFormatAndDay(num, format, day) {
    var date = new Date();
    date.setMonth(date.getMonth() + num, 1);
    //读取日期自动会减一，所以要加一
    var mo = date.getMonth() + 1;
    //小月
    if (mo == 4 || mo == 6 || mo == 9 || mo == 11) {
        if (day > 30) {
            day = 30
        }
    } else if (mo == 2) {
        //2月
        if (isLeapYear(date.getFullYear())) {
            if (day > 29) {
                day = 29
            } else {
                day = 28
            }
        }
        if (day > 28) {
            day = 28
        }
    } else {
        //大月
        if (day > 31) {
            day = 31
        }
    }
    return date.format('yyyy' + format + 'MM' + format + day);
}
/**
 * 日期格式化
 * @param format
 * @returns {*}
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};


function checkInputDate(strDate) {

    var reg = /^[\d-]{8}$/;
    if (!reg.test(strDate)) {
        layer.msg("日期格式不正确!/n正确格式为:20040101");
        return false;
    }
    var year = strDate.substring(0, 4);
    var month = strDate.substring(4, 6);
    var date = strDate.substring(6, 8);

    if (!checkYear(year)) {
        return false;
    }
    if (!checkMonth(month)) {
        return false;
    }
    if (!checkDate(year, month, date)) {
        return false;
    }
    if (!checkNow(year,month,date)) {
        return false;
    }
    return true;
}
function checkNow(year,month,date) {
    var now=new Date().getTime();
    var srcDate=new Date(year,month-1,date).getTime();
    if(srcDate>now){
        layer.msg("接种的时间不能大于当前时间!")
        return false;
    }
    return true;
}
function checkYear(year){
    var date=new Date();
    if(isNaN(parseInt(year)))
    {layer.msg("年份输入有误,请重新输入!");
        return false;
    }
    else if(parseInt(year)<1900 || parseInt(year) >date.getFullYear())
    {
        layer.msg("年份应该在1900-"+date.getFullYear()+"之间!");
        return false;
    }
    else return true;
}
function checkMonth(month){
    if(isNaN(parseInt(month,10))){layer.msg("月份输入有误,请重新输入!"); return false;}
    else if(parseInt(month,10)<1 || parseInt(month,10) >12)
    { layer.msg("月份应该在1-12之间!");
        return false;}
    else return true;
}
function checkDate(year,month,date){
    var daysOfMonth=CalDays(parseInt(year),parseInt(month));
    if(isNaN(parseInt(date))){layer.msg("日期输入有误,请重新输入!"); return false;}
    else if(parseInt(date)<1||parseInt(date)>daysOfMonth){ layer.msg("日期应该在1-"+daysOfMonth+"之间!"); return false;}
    else return true;
}
function CalDays(year,month){
    var date= new Date(year,month,0);
    return date.getDate();
}
function isLeapYear(year){
    if((year %4==0 && year %100!=0) || (year %400==0)) return true;
    else return false;
}

function submitForm(){
    if($('date_hour')){
        $('date_hour').value = '';
    }
    if($('fromDate_day')){
        $('fromDate_day').value = '';
    }
    if($('toDate_day')){
        $('toDate_day').value = '';
    }
    $('loginCountStatForm').submit();
}

function getCurrentAddress() {
    var str;
    $.ajax({
        url:'../child/getCurrentAddress',
        type:'get',
        async: false,
        success:function (data) {
            str=data;
        }
    })
    return str;
}

function unique5(array){
    var r = [];
    for(var i = 0, l = array.length; i < l; i++) {
        for(var j = i + 1; j < l; j++)
            if (array[i] === array[j]) j = ++i;
        r.push(array[i]);
    }
    return r;
}
function uniqueArr(arr){
    // 遍历arr，把元素分别放入tmp数组(不存在才放)
    var tmp = new Array();
    for(var i in arr){
        //该元素在tmp内部不存在才允许追加
        if(tmp.indexOf(arr[i])==-1){
            tmp.push(arr[i]);
        }
    }
    return tmp;
}

function isEmpty(str) {
    if(str==undefined||str==null||str==""){
        return true;
    }
    return false;
}
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
/**
 * 合并jqGrid方法
 * */
function MergerStatistics(gridName, CellName) {

    //得到显示到界面的id集合

    var mya = $("#" + gridName + "").getDataIDs();

    //当前显示多少条

    var length = mya.length;

    for (var i = 0; i < length; i++) {

        //从上到下获取一条信息

        var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);

        //定义合并行数

        var rowSpanTaxCount = 1;

        for (j = i + 1; j <= length; j++) {

            //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏

            var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);

            var cellNames = CellName.split(",");

            for (var n = 0; n < cellNames.length; n++) {

                if (before[cellNames[0]] == end[cellNames[0]] ) {

                    rowSpanTaxCount++;

                    $("#" + gridName + "").setCell(mya[j], cellNames[0], '', { display: 'none' });

                } else {

                    rowSpanTaxCount = 1;

                    break;

                }

                $("#" + cellNames[0] + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);//最后合并需要合并的行与合并的行数

            }

        }

    }

}