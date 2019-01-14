$(function(){


    //开始时间
    $("#yearDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: 2,//设置只显示到月份
        maxView: 4,//设置只显示到月份
        startView: 2,
        autoclose: true, //选择完时间后自动关闭，默认false（不关闭）
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    //结束时间
    $("#monthDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: 2,//设置只显示到月份
        maxView: 4,//设置只显示到月份
        startView: 2,
        autoclose: true, //选择完时间后自动关闭，默认false（不关闭）
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });

    //设置本年 当月 当天
    var now= formatDateTime(new Date());
    var date1 = formatDateTime(new Date().setDate(1));
    $("#yearDate").val(date1.substring(0,10));
    $("#monthDate").val(now.substring(0,10));

    vm.updateDate();
    vm.loadCommiteeData();

    // 行政村/居委会拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilCommitteeIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#chilCommitteeIdParent .open input').val();
        var hunt = $("#chilCommittees");
        var value = vm.chilCommittee;
        //如果输入的字符为空,清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].name.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
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
                con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });
});

var vm = new Vue({
    el: '#childrenOfAllAgeGroups',
    data: {
        listData:{},
        yearDate:null,
        monthDate:null
    },
    methods: {
        query:function(){
            layer.msg('加载数据中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
            var year=$("#yearDate").val();
            var month=$("#monthDate").val();
            var chilCommittees = $('#chilCommittees').selectpicker('val');
            if(chilCommittees == null || chilCommittees == undefined){
                chilCommittees = ["无选择"];
            }
            $.ajax({
                url:"../childrenOfAllAgeGroups/query",
                type:"get",
                data:{"committee":chilCommittees},
                success:function (ret) {
                    vm.listData=ret.data;
                    layer.msg("加载完成！") ;
                    var orgNam = ret.org;
                    var address = orgNam.split("/");
                    if(address.length > 1){
                        $("#city").html(address[1].replace("市","").replace("州","").replace("地",""));// 市（州、地）
                        $("#county").html(address[2].replace("县","").replace("市","").replace("区",""));// 县（市、区）
                        $("#countryside").html(address[3].replace("乡","").replace("镇","").replace("街道办",""));//乡（镇、街道办）
                    }
                }
            })
        },
        excel:function(){

            //alert("导出");
            var yearDate = $("#yearDate").val();
            var monthDate = $("#monthDate").val();
            var chilCommittees = $('#chilCommittees').selectpicker('val');
            if(chilCommittees == null || chilCommittees == undefined){
                chilCommittees = ["无选择"];
            }
            //window.location.href="../childrenOfAllAgeGroups/export?year="+yearDate+"&month="+monthDate+"&page=1&limit=10000&time="+new Date()+"&committee="+chilCommittees;
            window.open("../childrenOfAllAgeGroups/export?year="+yearDate+"&month="+monthDate+"&page=1&limit=10000&time="+new Date()+"&committee="+chilCommittees);
        },
        print:function() {
            //设置打印时样式
            vm.updateDate();
            var $tablebk = $("#setPrintTableStyle table thead tr").find("th");
            var $table = $("#setPrintTableStyle table thead tr").find("th.thtc");
            $tablebk.css({"padding-left": "0px", "padding-right": "0"});//8
            $table.css({"padding": "2px"});



            $("#unprintdiv").hide();
            $("#printSytle").show();
            $("#tableBelow").show();
            $("#setPrintTableStyle").css({"margin-top": "10px", "padding": "0"});
            window.print.portrait = false;//横向打印
            window.focus();
            window.print();
            $("#unprintdiv").show();
            $("#printSytle").hide();
            $("#tableBelow").hide();
            $("#setPrintTableStyle").css({"margin-top": "20px", "padding-left": " 20px", "padding-right": "20px"});
            $tablebk.css({"padding": "8px"});//8
            //$table.css({"padding":"8px"});
        },
        changeQuery:function () {
            vm.updateDate();
            vm.query();
        },
        updateDate:function () {
            var yearDate = $("#yearDate").val();
            var monthDate = $("#monthDate").val();
            $("#year").html(monthDate.split("-")[0]);
            $("#month").html(monthDate.split("-")[1]);
        },
        //行政村
        loadCommiteeData:function(){
            //初始化下拉框
            $('#chilCommittees').selectpicker({
                'selectedText': 'cat',
                // actionsBox:true,
                search:false,
            });
            var param = new Array();
            $.ajax({
                url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    vm.chilCommittee = results.page.list;
                    var seldata = results.page.list;
                    $.each(seldata, function (i, n) {
                        $("#chilCommittees").append(" <option value=\"" + n.code + "\">" + n.name + "</option>");
                    })
                    $("#chilCommittees").selectpicker('refresh');
                }
            });
        }
    }
});