$(function () {
    $("#jqGrid").jqGrid({
        url: '../child/imperfectChild?org_id='+orgId,
        datatype: "json",
        colModel: [
			{ label: '儿童编号', name: 'chilCode', width: 160,key:true,
                formatter:function updateChildFormatter(cellValue, options, rowdata, action){
                    /*return   "<a href=\"javascript:void(0);\" onclick=\"updateChild(" + cellValue + ")\">"+cellValue+"</a>";*/
                    return "<a href=\"javascript:void(0);\" onclick='updateChild("+JSON.stringify(rowdata).replace(/"/g, '&quot;')+")'>"+cellValue+"</a>";
                }
            },
			{ label: '姓名', name: 'chilName', width: 80 },
			{ label: '性别', name: 'chilSex', width: 50,
                formatter:function (value) {
                    if(value==1){
                        return "男";
                    }
                    if(value==2){
                        return "女";
                    }
                }
            },
			{ label: '出生日期', name: 'chilBirthday', width: 100,
                formatter:function (value) {
                    if(value!=null){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
			{ label: '父亲姓名', name: 'chilFather', width: 80 },
			{ label: '母亲姓名', name: 'chilMother', width: 80 },
            { label: '家庭电话', name: 'chilTel', width: 110 },
            { label: '手机号码', name: 'chilMobile', width: 110 },
            { label: '居委会/行政村', name: 'chilCommittee', width: 100 },
			{ label: '在册情况', name: 'chilHere', width: 80 },
			{ label: '居住属性', name: 'chilResidence', width: 80 },
			{ label: '建卡日期', name: 'createCardTime', width: 100,
                formatter:function (value) {
                    if(value!=null){
                        return value.substring(0,10);
                    }else{
                        return "";
                    }
                }
            },
			{ label: '建证单位', name: 'chilCreatesite', width: 150 },
			{ label: '户口县国标', name: 'chilHabiId', width: 80 },
            { label: '通讯地址', name: 'chilAddress', width: 200 },
			{ label: '户口地址', name: 'chilHabiaddress', width: 200 },
			{ label: '现居地址', name: 'chilAddress', width: 200 },
			{ label: '家长身份证', name: 'chilMotherno', width: 180 },
            { label: '儿童身份证号', name: 'chilNo', width: 180 }
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 20,
		rowList : [20,30,50],
        rownumbers: true,
        rownumWidth: 45,
        shrinkToFit:false,
        autowidth:true,
        autoScroll: true,
        multiselect: false,
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
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

    // dataproviceall();//省份加载
    // dataproviceall2();//省份加载
});

var imperfectChildVm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
        q:{
            cname:null,
            sexcode:null,
            birthtime:null,
            mothername:null,
            fathername:null,
            homephone:null,
            child_mobile:null,
            detailaddress:null,
            identitycard:null,
            here:null,
            child_residence:null,
            jkrq:null,
            committee:null,
            departname:null,
            hukouregioncode:null,
            familyregioncode:null,
            child_habi_id:null,
            guardiancard:null
        },
        tChildInfo:{}
	},
	methods: {
		query: function () {
			imperfectChildVm.reload();
		},
        reset: function(){
			imperfectChildVm.q = {
                cname:null,
                sexcode:null,
                birthtime:null,
                mothername:null,
                fathername:null,
                homephone:null,
                child_mobile:null,
                detailaddress:null,
                identitycard:null,
                here:null,
                child_residence:null,
                jkrq:null,
                committee:null,
                departname:null,
                hukouregioncode:null,
                familyregioncode:null,
                child_habi_id:null,
                guardiancard:null

            }
		},
        update:function(event){
           /* var chilCode = getSelectedRow_child();
            if(chilCode == null){
                return ;
            }*/
            /*imperfectChildVm.showList = false;
            imperfectChildVm.title = "修改";
            imperfectChildVm.getInfo(event)*/
            $("#chilLeaveremark").attr({"disabled":"disabled"});
            //imperfectChildVm.getInfo(chilCode);
            var widthNum = Win.WinW - 100 + 'px';
            layer.open({
                title: '修改',
                skin: 'printDialog',
                type: 2,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ["98%", "98%"],
                content: '../child/childEdit.html?chilCode=' + event
            });
        },
        back:function(){
            imperfectChildVm.showList = true;
        },
        getInfo: function(chilCode){
            $.ajax({
                type: "GET",
                url: "../child/info/"+chilCode,
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    /* reSetAddress(r);*/
                    imperfectChildVm.tChildInfo = r.tChildInfo;
                    // Bind();
                    // ProviceBind(r.tChildInfo);//省
                    // Provice_homeBind(r.tChildInfo);//省
                    // CityBind(r.tChildInfo);//市
                    // City_homeBind(r.tChildInfo);//市
                    // VillageBind(r.tChildInfo);//县
                    // Village_homeBind(r.tChildInfo);//县
                    // TwonBind(r.tChildInfo);//乡镇
                    // Twon_homeBind(r.tChildInfo);//乡镇


                    var chilHabiaddress = r.tChildInfo.chilHabiaddress;
                    var chilAddress = r.tChildInfo.chilAddress;

                    if(chilHabiaddress.indexOf('-')!=-1){
                        var hbAdress=chilHabiaddress.split("-");
                        $("#hbaddress").citypicker("reset");
                        $("#hbaddress").citypicker("destroy");
                        $("#hbaddress").val(hbAdress[0].replace(/\s+/g,"/"));
                        $("#hbaddress").citypicker({
                            province: '',
                            city: '',
                            district: '',
                            county:''
                        });
                        $("#hbDetailed").val(hbAdress[1]);
                    }else{
                        $("#hbaddress").citypicker("reset");
                        $("#hbDetailed").val(chilHabiaddress);
                    }

                    if(chilAddress.indexOf('-')!=-1){
                        var adress=chilAddress.split("-");
                        $("#nowaddress").citypicker("reset");
                        $("#nowaddress").citypicker("destroy");
                        $("#nowaddress").val(adress[0].replace(/\s+/g,"/"));
                        $("#nowaddress").citypicker({
                            province: '',
                            city: '',
                            district: '',
                            county:''
                        });
                        $("#nowDetailed").val(adress[1]);
                    }else{
                        $("#nowaddress").citypicker("reset");
                        $("#nowDetailed").val(chilAddress);
                    }

                    // $("#detailed").val(chilHabiaddress);
                    // $("#detailed_home").val(chilAddress);
                    // var chilHabiaddressTownId=   r.tChildInfo.chilHabiaddressTownId;
                    // var chilAddressTownId=  r.tChildInfo.chilAddressTownId;
                    // console.log(chilHabiaddressTownId);
                    // console.log(chilAddressTownId);
                    // if (chilHabiaddressTownId!=null){
                    //     $('#Province').selectpicker('val', chilHabiaddressTownId.substring(0,2));
                    //     datacityall(chilHabiaddressTownId.substring(0,2));
                    //     $('#City').selectpicker('val', chilHabiaddressTownId.substring(0,4)+"00000000");
                    //     datavallageall(chilHabiaddressTownId.substring(0,4)+"00000000");
                    //     $('#Village').selectpicker('val', chilHabiaddressTownId.substring(0,6)+"000000");
                    //     datatownall(chilHabiaddressTownId.substring(0,6)+"000000");
                    //     $('#Twon').selectpicker('val', chilHabiaddressTownId.substring(0,9)+"000");
                    // }else{
                    //     $('#Province').selectpicker('val', "");
                    //     $('#City').selectpicker('val', "");
                    //     $('#Village').selectpicker('val', "");
                    //     $('#Twon').selectpicker('val', "");
                    // }
                    // if (chilAddressTownId!=null){
                    //     $('#Province_home').selectpicker('val', chilAddressTownId.substring(0,2));
                    //     datacityall2(chilAddressTownId.substring(0,2));
                    //     $('#City_home').selectpicker('val', chilAddressTownId.substring(0,4)+"00000000");
                    //     datavallageall2(chilAddressTownId.substring(0,4)+"00000000");
                    //     $('#Village_home').selectpicker('val', chilAddressTownId.substring(0,6)+"000000");
                    //     datatownall2(chilAddressTownId.substring(0,6)+"000000");
                    //     $('#Twon_home').selectpicker('val', chilAddressTownId.substring(0,9)+"000");
                    // }else{
                    //     $('#Province_home').selectpicker('val', "");
                    //     $('#City_home').selectpicker('val', "");
                    //     $('#Village_home').selectpicker('val', "");
                    //     $('#Twon_home').selectpicker('val', "");
                    // }
                    $('#chilCommittee').selectpicker('val', imperfectChildVm.tChildInfo.chilCommittee);
                    $('#chilBirthhospitalname').selectpicker('val', imperfectChildVm.tChildInfo.chilBirthhospital);

                    // if(chilHabiaddress!=null && chilHabiaddress.length<9){
                    //     $("#detailed").val(chilHabiaddress);
                    // }else if(chilHabiaddress!=null){
                    //     if(chilHabiaddress.lastIndexOf("乡")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("乡")+1));
                    //     }
                    //     if(chilHabiaddress.lastIndexOf("镇")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("镇")+1));
                    //     }
                    //     if(chilHabiaddress.lastIndexOf("处")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("处")+1));
                    //     }
                    //     if((chilHabiaddress.lastIndexOf("区")!=-1 || chilHabiaddress.lastIndexOf("县")!=-1)&& chilHabiaddress.lastIndexOf(" ")!=-1){
                    //         $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("区")+1));
                    //     }
                    //
                    // }
                    // if(chilAddress!=null && chilAddress.length<9){
                    //
                    //     $("#detailed_home").val(r.tChildInfo.chilAddress);
                    // }
                    // else if(chilAddress!=null ){
                    //     if(chilAddress.lastIndexOf("乡")!=-1){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("乡")+1));
                    //     }
                    //     if(chilAddress.lastIndexOf("镇")!=-1){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("镇")+1));
                    //     }
                    //     if(chilAddress.lastIndexOf("处")!=-1){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("处")+1));
                    //     }
                    //     if((chilAddress.lastIndexOf("区")!=-1||chilAddress.lastIndexOf("县")!=-1)&& chilAddress.lastIndexOf(" ")!=-1 ){
                    //         $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("县")+1));
                    //     }
                    // }
                }
            });
        },
        saveOrUpdate: function (event) {
            childvalidator();
            $('#chilForm').bootstrapValidator('validate');//提交验证
            var hospital = $("#chilBirthhospitalname").val();

            if ($("#chilForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                if(hospital==null){
                    alert("请选择出生医院...");
                    return;
                }

                // if ($("#detailed").val() != null && $("#detailed").val().length < 9) {
                //     var habiaddress = $("#Province option:selected").text() + $("#City option:selected").text() + $("#Village option:selected").text() + $("#Twon option:selected").text() + $("#detailed").val();
                // } else if ($("#detailed").val() != null) {
                //     var habiaddress = $("#detailed").val();
                // } else {
                //     var habiaddress = $("#Province option:selected").text() + $("#City option:selected").text() + $("#Village option:selected").text() + $("#Twon option:selected").text();
                // }
                // if ($("#detailed_home").val() != null && $("#detailed").val().length < 9) {
                //     var address = $("#Province_home option:selected").text() + $("#City_home option:selected").text() + $("#Village_home option:selected").text() + $("#Twon_home option:selected").text() + $("#detailed_home").val();
                //
                // } else if ($("#detailed_home").val() != null) {
                //     var address = $("#detailed_home").val();
                // } else {
                //     var address = $("#Province_home option:selected").text() + $("#City_home option:selected").text() + $("#Village_home option:selected").text() + $("#Twon_home option:selected").text()
                // }
                //var habiaddress_town_id=$("#Province option:selected").val()+$("#City option:selected").val()+$("#Village option:selected").val()+$("#Twon option:selected").val();
                //var address_town_id = $("#Province_home option:selected").val()+$("#City_home option:selected").val()+$("#Village_home option:selected").val()+$("#Twon_home option:selected").val();
                var birthday = $("#chilBirthday").val();
                var chilCreatedate = $("#chilCreatedate").val();
                var chilHabiaddress_town_id = $("#Twon option:selected").val();
                var chilAddress_town_id = $("#Twon_home option:selected").val();
                var chil_createcounty = orgId.substring(0, 6);
                var chil_habi_id = $("#Village option:selected").val();
                var hospital_id = $("#chilBirthhospitalname option:selected").val();
                var hospital_name = $("#chilBirthhospitalname option:selected").text();
                var hbAdress=$("#hbaddress").val().replace(/\//g,' ');
                var chilHabiaddress;
                if(hbAdress!=undefined&&hbAdress!=null&&hbAdress!=""){
                    chilHabiaddress=hbAdress+"-"+$("#hbDetailed").val();
                }else{
                    chilHabiaddress=$("#hbDetailed").val();
                }
                Vue.set(imperfectChildVm.tChildInfo, 'chilHabiaddress', chilHabiaddress);

                var adress=$("#nowaddress").val().replace(/\//g,' ');
                var chilAddress;
                if(adress!=undefined&&adress!=null&&adress!=""){
                    chilAddress=adress+"-"+$("#nowDetailed").val();
                }else{
                    chilAddress=$("#nowDetailed").val();
                }
                // Vue.set(imperfectChildVm.tChildInfo, 'chilHabiaddress', hbAdress);
                Vue.set(imperfectChildVm.tChildInfo, 'chilAddress', chilAddress);

                // Vue.set(imperfectChildVm.tChildInfo, 'chilHabiaddress', habiaddress);
                // Vue.set(imperfectChildVm.tChildInfo, 'chilAddress', address);

                Vue.set(imperfectChildVm.tChildInfo, 'chilBirthday', birthday);
                Vue.set(imperfectChildVm.tChildInfo, 'chilCreatedate', chilCreatedate);
                Vue.set(imperfectChildVm.tChildInfo, 'chilHabiaddressTownId', chilHabiaddress_town_id);
                Vue.set(imperfectChildVm.tChildInfo, 'chilAddressTownId', chilAddress_town_id);
                Vue.set(imperfectChildVm.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(imperfectChildVm.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(imperfectChildVm.tChildInfo, 'chilHabiId', chil_habi_id);
                Vue.set(imperfectChildVm.tChildInfo, 'chilBirthhospital', hospital_id);
                Vue.set(imperfectChildVm.tChildInfo, 'chilBirthhospitalname', hospital_name);
                Vue.set(imperfectChildVm.tChildInfo, 'chilCommittee', $("#chilCommittee").val());

                var url = imperfectChildVm.tChildInfo.chilCode == null ? "../child/save" : "../child/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(imperfectChildVm.tChildInfo),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("操作成功");
                            imperfectChildVm.showList = true;
                            var page =$("#jqGrid").jqGrid('getGridParam', 'page');
                            $("#jqGrid").jqGrid('setGridParam',{
                                postData:{org_id:orgId
                                },
                                page: page,
                                url: '../child/imperfectChild'
                            }).trigger("reloadGrid");
                            if($("#chilForm").data('bootstrapValidator')!=null) {
                                $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
                            }

                            //registerimperfectChildVm.reload();
                            /* });*/
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }else{
                return false;
            }

        },
        excel: function (event) {
            var cname= $("input[id='cname']").is(':checked');
            var sexcode= $("input[id='sexcode']").is(':checked');
            var birthtime= $("input[id='birthtime']").is(':checked');
            var mothername= $("input[id='mothername']").is(':checked');
            var fathername= $("input[id='fathername']").is(':checked');
            var homephone= $("input[id='homephone']").is(':checked');
            var child_mobile= $("input[id='child_mobile']").is(':checked');
            var detailaddress= $("input[id='detailaddress']").is(':checked');
            var identitycard= $("input[id='identitycard']").is(':checked');
            var here= $("input[id='here']").is(':checked');
            var addtime= $("input[id='addtime']").is(':checked');//建卡日期
            var child_residence= $("input[id='child_residence']").is(':checked');
            var committee= $("input[id='committee']").is(':checked');
            var departname= $("input[id='departname']").is(':checked');
            var hukouregioncode= $("input[id='hukouregioncode']").is(':checked');
            var familyregioncode= $("input[id='familyregioncode']").is(':checked');
            var child_habi_id= $("input[id='child_habi_id']").is(':checked');
            var guardiancard= $("input[id='guardiancard']").is(':checked');
            layer.confirm("确定要下载生成的报表文件吗？",function (index) {
                window.open("../ExcelController/imperfectChild?cname="+cname+"&sexcode="+sexcode
                    +"&birthtime="+birthtime+"&mothername="+mothername
                    +"&fathername="+fathername+"&homephone="+homephone+"&child_mobile="+child_mobile
                    +"&detailaddress="+detailaddress+"&identitycard="+identitycard
                    +"&here="+here+"&child_residence="+child_residence+"&committee="+committee
                    +"&departname="+departname+"&hukouregioncode="+hukouregioncode+"&familyregioncode="+familyregioncode+
                    "&child_habi_id="+child_habi_id+"&guardiancard="+guardiancard+"&jkrq="+addtime+"&page=1&limit=100000");
                layer.close(index);
            });

		},
        print:function () {
            var cname= $("input[id='cname']").is(':checked');
            var sexcode= $("input[id='sexcode']").is(':checked');
            var birthtime= $("input[id='birthtime']").is(':checked');
            var mothername= $("input[id='mothername']").is(':checked');
            var fathername= $("input[id='fathername']").is(':checked');
            var homephone= $("input[id='homephone']").is(':checked');
            var child_mobile= $("input[id='child_mobile']").is(':checked');
            var detailaddress= $("input[id='detailaddress']").is(':checked');
            var identitycard= $("input[id='identitycard']").is(':checked');
            var here= $("input[id='here']").is(':checked');
            var addtime= $("input[id='addtime']").is(':checked');//建卡日期
            var child_residence= $("input[id='child_residence']").is(':checked');
            var committee= $("input[id='committee']").is(':checked');
            var departname= $("input[id='departname']").is(':checked');
            var hukouregioncode= $("input[id='hukouregioncode']").is(':checked');
            var familyregioncode= $("input[id='familyregioncode']").is(':checked');
            var child_habi_id= $("input[id='child_habi_id']").is(':checked');
            var guardiancard= $("input[id='guardiancard']").is(':checked');
            window.open("../reports/imperfectChild?cname="+cname+"&sexcode="+sexcode
                +"&birthtime="+birthtime+"&mothername="+mothername
                +"&fathername="+fathername+"&homephone="+homephone+"&child_mobile="+child_mobile
                +"&detailaddress="+detailaddress+"&identitycard="+identitycard
                +"&here="+here+"&child_residence="+child_residence+"&committee="+committee
                +"&departname="+departname+"&hukouregioncode="+hukouregioncode+"&familyregioncode="+familyregioncode+
                "&child_habi_id="+child_habi_id+"&guardiancard="+guardiancard+"&jkrq="+addtime+"&page=1&limit=100000");
        },
		reload: function (event) {
			imperfectChildVm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('clearGridData');  //清空表格
            $("#jqGrid").jqGrid('setGridParam',{
                url: '../child/imperfectChild?org_id='+orgId,
                page:page,
                postData :imperfectChildVm.q
            }).trigger("reloadGrid");
		}
        /*$("#childImperfectForm").serialize()+"&page="+page+"&limit=20"*/
	}
});

function updateChild(rowdata) {
    imperfectChildVm.update(rowdata.chilCode);
}
//选择一条记录
function getSelectedRow_child() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return rowKey;
}