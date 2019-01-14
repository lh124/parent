$(function () {

    $("#jqGrid").jqGrid({
        url: '../child/list',
        datatype: "json",
        colModel: [
			{ label: '儿童编码', name: 'chilCode', width: 170, key: true },
			{ label: '姓名', name: 'chilName', width: 80 },
			{ label: '性别', name: 'chilSex', width: 60 },
			{ label: '出生日期', name: 'chilBirthday', width: 150 },
			{ label: '出生体重（kg）', name: 'chilBirthweight', width: 100 },
			{ label: '民族', name: 'chilNatiId', width: 60 },
			{ label: '免疫卡号', name: 'chilCardno', width: 80 },
			/*{ label: '身份证号', name: 'chilNo', width: 80 }, */
			/*{ label: '出生证号', name: 'chilBirthno', width: 80 }, */
			{ label: '母亲姓名', name: 'chilMother', width: 80 },
			{ label: '父亲姓名', name: 'chilFather', width: 80 },
			{ label: '行政村/居委会', name: 'chilCommittee', width: 80 },
			{ label: '户籍地址', name: 'chilHabiaddress', width: 180

			},
			{ label: '现居地址', name: 'chilAddress', width: 180
			},
			{ label: '家庭电话', name: 'chilTel', width: 80 },
			{ label: '手机', name: 'chilMobile', width: 100 },
            { label: '学校', name: 'chilSchool', width: 80 },
			/*{ label: '发卡日期', name: 'chilCarddate', width: 80 },
			{ label: '健康档案标识', name: 'chilHealthno', width: 80 },
			{ label: '平台字段,未知属性', name: 'chilResidenceclient', width: 80 }, 	*/
			{ label: '居住属性', name: 'chilResidence', width: 80 },
			{ label: '户籍属性', name: 'chilAccount', width: 80 },
			/*{ label: '户籍县国标', name: 'chilHabiId', width: 80 }, 	*/
			/*{ label: '平台字段,未知属性', name: 'chilInoctype', width: 80 }, 		*/
			/*{ label: '过敏史', name: 'chilSensitivity', width: 80 },
			{ label: '母亲HB', name: 'chilMotherhb', width: 80 },
			{ label: '母亲HIV', name: 'chilMotherhiv', width: 80 }, 			*/
			{ label: '在册情况', name: 'chilHere', width: 80 },
			/*{ label: '在册变化日期', name: 'chilLeavedate', width: 80 },
			{ label: '迁出分类', name: 'chilLeave', width: 80 },
			{ label: '迁出其他备注', name: 'chilLeaveremark', width: 80 }, 	*/
			/*{ label: '现管理单位编码', name: 'chilCurdepartment', width: 80 },
			{ label: '前管理单位编码', name: 'chilPredepartment', width: 80 }, 	*/
			/*{ label: '建档县国标', name: 'chilCreatecounty', width: 80 }, */
			/*{ label: '建档单位编码', name: 'chilCreatesite', width: 80 }, 	*/
			{ label: '建档日期', name: 'chilCreatedate', width: 150},
			/*{ label: '建卡人', name: 'chilCreateman', width: 80 },*/
            { label: '建档人', name: 'createUserName', width: 80 },
			/*{ label: '受种者单位', name: 'chilUnitimmu', width: 80 },
			{ label: '出生医院编码', name: 'chilBirthhospital', width: 80 }, 		*/
			{ label: '出生医院', name: 'chilBirthhospitalname', width: 80 },
			/*{ label: '是否有卡介苗疤痕', name: 'chilBcgScar', width: 80 }, */
			{ label: '母亲身份证号', name: 'chilMotherno', width: 180 },
			{ label: '父亲身份证号', name: 'chilFatherno', width: 180 },
			/*{ label: '出省状态', name: 'chilProvince', width: 80 },
			{ label: '更新时间', name: 'chilEditDate', width: 80 }, 			*/
			{ label: '建卡时间', name: 'createCardTime', width: 150 },
			/*{ label: 'type,0本地,1平台', name: 'type', width: 80 },
			{ label: '同步状态,0未同步;1同步', name: 'syncstatus', width: 80 },
				*/
			/*{ label: '创建人id', name: 'createUserId', width: 80 },
			{ label: '创建人名字', name: 'createUserName', width: 80 },
			{ label: '创建时间', name: 'createTime', width: 80 }, 			*/
			{ label: '备注', name: 'remark', width: 80 }
        ],
		viewrecords: true,
        height: 220,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 35,
        autowidth:true,
		shrinkToFit:false,
		autoScroll: true,
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
        	/* */
			/*$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });*/
        },
		onSelectRow:function(rowid,iRow,iCol,e){

            var rowData = $("#jqGrid").jqGrid('getRowData',rowid);
			$("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode="+rowid);
			$("#move_iframe").attr("src", "../child/tchildmove.html?chilCode="+rowid+"&chilHere="+rowData.chilHere+"&chilName="+rowData.chilName);
			$("#abnormal_iframe").attr("src", "../child/tchildabnormal.html?chilCode="+rowid+"&chilName="+rowData.chilName);
			$("#taboo_iframe").attr("src", "../child/tchildtaboo.html?chilCode="+rowid+"&chilName="+rowData.chilName);
			$("#allergy_iframe").attr("src", "../child/tchildallergy.html?chilCode="+rowid+"&chilName="+rowData.chilName);
			$("#infection_iframe").attr("src", "../child/tchildinfection.html?chilCode="+rowid+"&chilName="+rowData.chilName);

           /* window.onload = function(){
                document.getElementById("move_iframe").contentWindow.document.getElementById("chhePrehere").value = "成功";
            };*/
          /* $("#move_iframe").contents().find("chhePrehere").val("dfsdf");
            $("#chhePrehere").val(rowData.chilHere);*/

			/*$.ajax({
				type:"post",
				url: '../tchildmove/list?chilCode='+rowid+'&page=1&limit=1000',
				datatype: "json",
				success:function (data) {
					$("#moveGrid").jqGrid("clearGridData");
					$("#moveGrid").setGridParam({datatype : 'local',data: data.page.list}).trigger('reloadGrid');
				}
			});*/


		}
    });
vm.loadInfoStatusData();
vm.loadNationData();
    vm.move();
    vm.residence();
    vm.chilBirthday();//出生日期
    vm.chilCreatedate();//建档日期
    vm.loadCommiteeData();//行政村
    vm.loadSchoolData();//学校
    vm.loadHospitalData();//医院
    vm.loadPrintModel();
    $("#chilBirthdayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.chilBirthdayEnd = $("#chilBirthdayEnd").val();
    });
    $("#chilBirthdayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.chilBirthdayStart = $("#chilBirthdayStart").val();
    });
    $("#inoculateDateEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.inoculateDateEnd = $("#inoculateDateEnd").val();
    });
    $("#inoculateDateStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('changeDate',function(ev){
        vm.q.inoculateDateStart = $("#inoculateDateStart").val();
    });


});


var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			chilCode: null,
			chilName:null,
			chilCommittee :null,
			chilBirthdayStart:null,
			chilBirthdayEnd:null,
			chilHere:null,
            inoculateDateStart:null,
            inoculateDateEnd:null,
            isUpload:null

		},
		showList: true,
		title: null,
		tChildInfo: {}
	},
	methods: {
		query: function () {
           vm.reload();
		},
        reset:function(){
            vm.q={
                chilCode: null,
                    chilName:null,
                    chilCommittee :null,
                    chilBirthdayStart:null,
                    chilBirthdayEnd:null,
                    chilHere:null,
                    inoculateDateStart:null,
                    inoculateDateEnd:null,
                    isUpload:null

            }
        },
		add: function(){

			vm.showList = false;
			vm.title = "新增";
            vm.Bind();
            vm.ProviceBind();//省
            vm.Provice_homeBind();//省
            vm.CityBind();//市
            vm.City_homeBind();//市
            vm.VillageBind();//县
            vm.Village_homeBind();//县
            vm.Twon();//乡镇
            vm.Twon_home();//乡镇
			vm.tChildInfo = {
                chilCreatesite:orgName,
                createUserName:userName,
                chilCreatedate:vm.chilCreatedate()
            };
		},
		update: function (event) {
			var chilCode = getSelectedRow_child();
			if(chilCode == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(chilCode)
		},
		saveOrUpdate: function (event) {
            childvalidator();
            $('#chilForm').bootstrapValidator('validate');//提交验证
            var hospital = $("#chilBirthhospitalname").val();
            if(hospital==null){
                alert("请选择出生医院...");
                return;
            }

            if ($("#chilForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码

                if ($("#detailed").val() != null && $("#detailed").val().length < 9) {
                    var habiaddress = $("#Province option:selected").text() + $("#City option:selected").text() + $("#Village option:selected").text() + $("#Twon option:selected").text() + $("#detailed").val();
                } else if ($("#detailed").val() != null) {
                    var habiaddress = $("#detailed").val();
                } else {
                    var habiaddress = $("#Province option:selected").text() + $("#City option:selected").text() + $("#Village option:selected").text() + $("#Twon option:selected").text();
                }
                if ($("#detailed_home").val() != null && $("#detailed").val().length < 9) {
                    var address = $("#Province_home option:selected").text() + $("#City_home option:selected").text() + $("#Village_home option:selected").text() + $("#Twon_home option:selected").text() + $("#detailed_home").val();

                } else if ($("#detailed_home").val() != null) {
                    var address = $("#detailed_home").val();
                } else {
                    var address = $("#Province_home option:selected").text() + $("#City_home option:selected").text() + $("#Village_home option:selected").text() + $("#Twon_home option:selected").text()
                }
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
                Vue.set(vm.tChildInfo, 'chilHabiaddress', habiaddress);
                Vue.set(vm.tChildInfo, 'chilAddress', address);
                Vue.set(vm.tChildInfo, 'chilBirthday', birthday);
                Vue.set(vm.tChildInfo, 'chilCreatedate', chilCreatedate);
                Vue.set(vm.tChildInfo, 'chilHabiaddressTownId', chilHabiaddress_town_id);
                Vue.set(vm.tChildInfo, 'chilAddressTownId', chilAddress_town_id);
                Vue.set(vm.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(vm.tChildInfo, 'chilCreatecounty', chil_createcounty);
                Vue.set(vm.tChildInfo, 'chilHabiId', chil_habi_id);
                Vue.set(vm.tChildInfo, 'chilBirthhospital', hospital_id);
                Vue.set(vm.tChildInfo, 'chilBirthhospitalname', hospital_name);

                var url = vm.tChildInfo.chilCode == null ? "../child/save" : "../child/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tChildInfo),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }else{
                return false;
            }

		},
		del: function (event) {
			var chilCodes = getSelectedRows();
			if(chilCodes == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../child/delete",
				    data: JSON.stringify(chilCodes),
                    contentType:'application/json;charset=UTF-8',
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
		getInfo: function(chilCode){
            $.ajax({
                type: "GET",
                url: "../child/info/"+chilCode,
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                   /* reSetAddress(r);noneSelectedText*/
                    vm.tChildInfo = r.tChildInfo;
                    $('#chilBirthhospitalname').selectpicker('val',(vm.tChildInfo.chilBirthhospital));
                    vm.Bind();
                    vm.ProviceBind(r.tChildInfo);//省
                    vm.Provice_homeBind(r.tChildInfo);//省
                    vm.CityBind(r.tChildInfo);//市
                    vm.City_homeBind(r.tChildInfo);//市
                    vm.VillageBind(r.tChildInfo);//县
                    vm.Village_homeBind(r.tChildInfo);//县
                    vm.Twon(r.tChildInfo);//乡镇
                    vm.Twon_home(r.tChildInfo);//乡镇
                    var chilHabiaddress = r.tChildInfo.chilHabiaddress;
                    var chilAddress = r.tChildInfo.chilAddress;
                    if(chilHabiaddress!=null && chilHabiaddress.length<9){
                        $("#detailed").val(chilHabiaddress);
                    }else if(chilHabiaddress!=null){
                        if(chilHabiaddress.lastIndexOf("乡")!=-1){
                            $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("乡")+1));
                        }
                        if(chilHabiaddress.lastIndexOf("镇")!=-1){
                            $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("镇")+1));
                        }
                        if(chilHabiaddress.lastIndexOf("处")!=-1){
                            $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("处")+1));
                        }
                        if((chilHabiaddress.lastIndexOf("区")!=-1 || chilHabiaddress.lastIndexOf("县")!=-1)&& chilHabiaddress.lastIndexOf(" ")!=-1){
                            $("#detailed").val(chilHabiaddress.substring(chilHabiaddress.lastIndexOf("区")+1));
                        }

                    }
                    if(chilAddress!=null && chilAddress.length<9){

                        $("#detailed_home").val(r.tChildInfo.chilAddress);
                    }
                    else if(chilAddress!=null ){
                        if(chilAddress.lastIndexOf("乡")!=-1){
                            $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("乡")+1));
                        }
                        if(chilAddress.lastIndexOf("镇")!=-1){
                            $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("镇")+1));
                        }
                        if(chilAddress.lastIndexOf("处")!=-1){
                            $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("处")+1));
                        }
                        if((chilAddress.lastIndexOf("区")!=-1||chilAddress.lastIndexOf("县")!=-1)&& chilAddress.lastIndexOf(" ")!=-1 ){
                            $("#detailed_home").val(chilAddress.substring(chilAddress.lastIndexOf("县")+1));
                        }
                    }
                }
            });
		},
		reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:vm.q,
                page:page
            }).trigger("reloadGrid");
            if($("#chilForm").data('bootstrapValidator')!=null){
                $("#chilForm").data('bootstrapValidator').resetForm("chilForm");
            }

		},
        //民族
        loadNationData:function(){
            $.ajax({
                url: '../child/listdiccode?ttype=nation_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#chilNatiId").html(con);
                   /* var result=results.data;
                    $.each(result, function (index, item) {
                        param.push({"text":item.text,"value":item.value});
                    });
                    vm.items = param;*/
                }
            });
        },
        //个案状态
        loadInfoStatusData:function(){
            var param = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_info_status',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#infostatus").append(con);
                    $("#chilHere").html(con);
                    $("#chheHere").html(con);

                }
            });
        },
        //行政村
        loadCommiteeData:function(){
            var param = new Array();
            $.ajax({
                url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].code + '">'+results.page.list[i].name+'</option>';
                    }
                    $("#chilCommittee").html(con);
                    $("#chilCommittees").append(con);

                }
            });
        },
        //学校
        loadSchoolData:function(){
            var param = new Array();
            $.ajax({
                url: '../tbaseschool/list?org_id='+orgId+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].name + '">'+results.page.list[i].name+'</option>';
                    }
                    $("#chilSchool").html(con);

                }
            });
        },

        //医院
        loadHospitalData:function(){
            //初始化下拉框
            $('.selectpicker').selectpicker({
                'selectedText': 'cat',
                noneSelectedText:'请选择出生医院...',
                actionsBox:true
            });
            var param = new Array();
            $.ajax({
                url: '../tbasehospital/list?page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].hospitalCode + '">'+results.page.list[i].hospitalName+'</option>';
                    }
                    $("#chilBirthhospitalname").append(con);
                    $("#chilBirthhospitalname").selectpicker('refresh');

                }
            });
        },
        chilBirthday:function(){
            $('#chilBirthday').datetimepicker({
                language: 'zh-CN',//显示中文
                format: 'yyyy-mm-dd hh:ii:ss',//显示格式
                minView: "month",//设置只显示到月份
                initialDate: new Date(),
                forceParse:true,
                autoclose: true,//选中自动关闭
                todayBtn: true,//显示今日按钮
                locale: moment.locale('zh-cn')
            }).on('changeDate',function(ev){
                vm.tChildInfo.chilBirthday = $("#chilBirthday").val();
                console.log($("#chilBirthday").val());
            }).on('hide',function(e) {
                $('#chilForm').data('bootstrapValidator')
                    .updateStatus('chilBirthday', 'NOT_VALIDATED',null)
                    .validateField('chilBirthday');
            });
       /* .on('hide',function(e) {
                $('#chilForm').data('bootstrapValidator')
                    .updateStatus('chilBirthday', 'NOT_VALIDATED',null)
                    .validateField('chilBirthday');
            });*/

        },
        chilCreatedate:function(){
            $('#datetimepicker4').datetimepicker({
                language: 'zh-CN',//显示中文
                format: 'yyyy-mm-dd hh:ii:ss',//显示格式
                minView: "month",//设置只显示到月份
                initialDate: new Date(),
                autoclose: true,//选中自动关闭
                todayBtn: true,//显示今日按钮
                locale: moment.locale('zh-cn')
            });
            //默认获取当前日期
            var today = new Date();
            var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate()+" "+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
            //对日期格式进行处理
            var date = new Date(nowdate);
            var mon = date.getMonth() + 1;
            var day = date.getDate();
            var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day)+" "+today.getHours()+":"+today.getMinutes()+":"+today.getSeconds();
            /*document.getElementById("chilCreatedate").value = mydate;*/
            return mydate;
        },

        //户籍属性
        move:function(){
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=movetype_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#chilAccount").html(con);
                }
            });
        },

        //居住属性
        residence:function(){
            var params = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=child_residence_code',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#chilResidence").html(con);
                }
            });
        },
       Bind:function(str) {
            // alert($("#Province").html());
            $("#Province").val(str);
            $("#Province_home").val(str);
            },
        ProviceBind:function(tChildInfo) {
            //清空下拉数据
            $("#Province").html("");
            var str = "";
            // var str = "<option>==请选择===</option>";
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listprovince",
                data: { "parentiD": "", "MyColums": "Province" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    $.each(result.data, function (index, item) {
                        // for (var i=0;i<result.data.length;i++){
                        if(tChildInfo!=null){
                            if(tChildInfo.chilHabiaddressTownId!=null && item.id==tChildInfo.chilHabiaddressTownId.substring(0,2)){
                                str += "<option value=" + item.id +" selected"+ ">" + item.name + "</option>";
                            }else if(tChildInfo.chilHabiId!=null && item.id==tChildInfo.chilHabiId.substring(0,2)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(orgId!=null && item.id ==orgId.substring(0,2)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
                                str += "<option value=" + item.id +">" + item.name + "</option>";
                            }
                        }else if(orgId!=null && item.id==orgId.substring(0,2)){
                            str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                        }else{
                            str += "<option value=" + item.id +">" + item.name + "</option>";
                        }

                        //str += "<option value=" + item.id + ">" + item.name + "</option>";
                        // }
                    });
                    //将数据添加到省份这个下拉框里面
                    $("#Province").html(str);
                },
                error: function () { alert("Error"); }
            });
        },
         Provice_homeBind:function(tChildInfo) {
            //清空下拉数据
            $("#Province_home").html("");
            var str = "";
            // var str = "<option>==请选择===</option>";
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listprovince",
                data: { "parentiD": "", "MyColums": "Province" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    $.each(result.data, function (index, item) {
                        // for (var i=0;i<result.data.length;i++){
                        if(tChildInfo!=null){
                            if(tChildInfo.chilAddressTownId!=null && item.id==tChildInfo.chilAddressTownId.substring(0,2)){
                                str += "<option value=" + item.id +" selected"+ ">" + item.name + "</option>";
                            }else if(tChildInfo.chilCurdepartment!=null && item.id==tChildInfo.chilCurdepartment.substring(0,2)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(orgId!=null &&item.id==orgId.substring(0,2)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
                                str += "<option value=" + item.id +">" + item.name + "</option>";
                            }
                        }else if(orgId!=null && item.id == orgId.substring(0,2)){
                            str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                        }else{
                            str += "<option value=" + item.id +">" + item.name + "</option>";
                        }



                        // }
                    });
                    //将数据添加到省份这个下拉框里面
                    $("#Province_home").html(str);
                },
                error: function () { alert("Error"); }
            });
        },
         CityBind:function(tChildInfo) {
            var provice = $("#Province option:selected").val();
            //判断省份这个下拉框选中的值是否为空
            if (provice == "") {
                return;
            }
            $("#City").html("");
            var str = "";
            // var str = "<option>==请选择===</option>";
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listcity?provinceId="+provice,
                data: { "provinceId": provice, "MyColums": "City" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    $.each(result.data, function (index, item) {
                        // for (var i=0;i<result.data.length;i++){
                        if(tChildInfo!=null){
                            if(tChildInfo.chilHabiaddressTownId!=null && item.id==tChildInfo.chilHabiaddressTownId.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(tChildInfo.chilHabiId!=null && item.id ==tChildInfo.chilHabiId.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(orgId!=null && item.id==orgId.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
                                str += "<option value=" + item.id +">" + item.name + "</option>";
                            }
                        }else if(orgId!=null && item.id == orgId.substring(0,4)){
                            str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                        }else{
                            str += "<option value=" + item.id +">" + item.name + "</option>";
                        }


                    });
                    //将数据添加到省份这个下拉框里面
                    $("#City").html(str);
                },
                error: function () { alert("Error"); }
            });
            vm.VillageBind(tChildInfo);
        },
         City_homeBind:function(tChildInfo) {
             var provice_home = $("#Province_home option:selected").val();
            //判断省份这个下拉框选中的值是否为空
            if (provice_home == "") {
                return;
            }
            $("#City_home").html("");
            var str = "";
            // var str = "<option>==请选择===</option>";
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listcity?provinceId="+provice_home,
                data: { "provinceId": provice_home, "MyColums": "City" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    $.each(result.data, function (index, item) {
                        if(tChildInfo!=null){
                            if(tChildInfo.chilAddressTownId!=null && item.id==tChildInfo.chilAddressTownId.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(tChildInfo.chilCurdepartment!=null && item.id ==tChildInfo.chilCurdepartment.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }
                            else if(orgId!=null && item.id==orgId.substring(0,4)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
                                str += "<option value=" + item.id +">" + item.name + "</option>";
                            }
                        }else if(orgId!=null && item.id==orgId.substring(0,4)){
                            str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                        }else{
                            str += "<option value=" + item.id +">" + item.name + "</option>";
                        }


                    });
                    //将数据添加到省份这个下拉框里面
                    $("#City_home").html(str);
                },
                error: function () { alert("Error"); }
            });
            vm.Village_homeBind(tChildInfo);
        },

         VillageBind:function (tChildInfo) {
            // var  provice = $("#City").attr("value");
            var provice = $("#City option:selected").val();
            console.log("市:"+provice);
            //判断市这个下拉框选中的值是否为空
            if (provice == "") {
                return;
            }
            $("#Village").html("");
            // var str = "<option>==请选择===</option>";
            var str = "";
            //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listcounty?cityId="+provice,
                data: { "cityId": provice, "MyColums": "Village" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    $.each(result.data, function (index, item) {
                        if(tChildInfo!=null){
                            if(tChildInfo.chilHabiaddressTownId!=null && item.id==tChildInfo.chilHabiaddressTownId.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(tChildInfo.chilHabiId!=null && item.id ==tChildInfo.chilHabiId.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(orgId!=null && item.id==orgId.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
                                str += "<option value=" + item.id +">" + item.name + "</option>";
                            }
                        }else if(orgId!=null && item.id==orgId.substring(0,6)){
                            str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                        }else{
                            str += "<option value=" + item.id +">" + item.name + "</option>";
                        }


                    });
                    //将数据添加到省份这个下拉框里面
                    $("#Village").html(str);
                },
                error: function () { alert("Error"); }
            });
             vm.Twon(tChildInfo);
        },
         Village_homeBind:function (tChildInfo) {
            // var  provice = $("#City").attr("value");
            var provice_home = $("#City_home option:selected").val();
            console.log("市:"+provice_home);
            //判断市这个下拉框选中的值是否为空
            if (provice_home == "") {
                return;
            }
            $("#Village_home").html("");
            // var str = "<option>==请选择===</option>";
            var str = "";
            //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listcounty?cityId="+provice_home,
                data: { "cityId": provice_home, "MyColums": "Village" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    $.each(result.data, function (index, item) {
                        if(tChildInfo!=null){
                            if(tChildInfo.chilAddressTownId!=null && item.id==tChildInfo.chilAddressTownId.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else if(tChildInfo.chilCurdepartment!=null && item.id ==tChildInfo.chilCurdepartment.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }
                            else if(orgId!=null && item.id==orgId.substring(0,6)){
                                str += "<option value=" + item.id + " selected"+">" + item.name + "</option>";
                            }else{
                                str += "<option value=" + item.id +">" + item.name + "</option>";
                            }
                        }else if(orgId!=null && item.id==orgId.substring(0,6)){
                            str += "<option value=" + item.id +" selected"+">" + item.name + "</option>";
                        }else{
                            str += "<option value=" + item.id +">" + item.name + "</option>";
                        }


                    });
                    //将数据添加到省份这个下拉框里面
                    $("#Village_home").html(str);
                },
                error: function () { alert("Error"); }
            });
             vm.Twon_home(tChildInfo);
        },
         Twon:function(tChildInfo) {
            // var  provice = $("#City").attr("value");
            var country = $("#Village option:selected").val();
            //判断市这个下拉框选中的值是否为空
            if (country == "") {
                return;
            }
            $("#Twon").html("");
            // var str = "<option>==请选择===</option>";
            var str = "";
            //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listtown?countyId="+country,
                data: { "countyId": country, "MyColums": "Twon" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    for (var i=0;i<result.data.length;i++){
                        if(tChildInfo!=null){
                            if(result.data[i].id==tChildInfo.chilHabiaddressTownId){
                                str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                            }else if(tChildInfo!=null && tChildInfo!={}){
                                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
                            }else{
                                str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
                            }
                        }else if(orgId!=null && result.data[i].id==orgId.substring(0,8)){
                            str += "<option value=" + result.data[i].id +" selected"+ ">" + result.data[i].name + "</option>";
                        }else{
                            str += "<option value=" + result.data[i].id + ">" + result.data[i].name + "</option>";
                        }

                    }
                    //将数据添加到省份这个下拉框里面
                    $("#Twon").html(str);

                },
                error: function () { alert("Error"); }
            });
        },
         Twon_home:function(tChildInfo) {
            // var  provice = $("#City").attr("value");
            var provice_home = $("#Village_home option:selected").val();
            //判断市这个下拉框选中的值是否为空
            if (provice_home == "") {
                return;
            }
            $("#Twon_home").html("");
            // var str = "<option>==请选择===</option>";
            var str = "";
            //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
            $.ajax({
                type: "POST",
                url: "../tbaseposition/listtown?countyId="+provice_home,
                data: { "countyId": provice_home, "MyColums": "Twon" },
                dataType: "JSON",
                async: false,
                success: function (result) {
                    //从服务器获取数据进行绑定
                    for (var i=0;i<result.data.length;i++){

                            if(tChildInfo!=null && result.data[i].id==tChildInfo.chilAddressTownId){
                                str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                            }else if(tChildInfo!=null && tChildInfo.chilCurdepartment!=null && result.data[i].id ==tChildInfo.chilCurdepartment.substring(0,8)){
                                str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                            }
                            else if(orgId!=null && result.data[i].id==orgId.substring(0,8)){
                                str += "<option value=" + result.data[i].id + " selected"+">" + result.data[i].name + "</option>";
                            }else{
                                str += "<option value=" + result.data[i].id +">" + result.data[i].name + "</option>";
                            }



                    }
                    //将数据添加到省份这个下拉框里面
                    $("#Twon_home").html(str);

                },
                error: function () { alert("Error"); }
            });
        },
		downloadChild: function () {
            layer.msg("正在下载...");
			$.ajax({
				type: "POST",
				url: "../provincePlatform/downloadChild",
				dataType:"json",
				success: function(r){
					console.log(r);
					if(r.code == 0){
						layer.msg(r.msg);
						//$("#jqGrid").trigger("reloadGrid");
					}else{
                        layer.msg(r.msg);
					}
				}
			});
		},
        upload:function(){
            /*vm.downloadChild();*/
            var ids = getSelectedRows("jqGrid");
            $.ajax({
                type: "POST",
                url: "../provincePlatform/uploadPlat?childCode="+ids,//http://"$webPath/request/pagelist/SysChild/selectWithPageForList.jhtml?"+params,
                dataType:"json",
                success: function(result) {
                   if(result.msg=="1"){
                       layer.msg("上传成功");
                       return;
                   }
                    else if(result.msg=="0"){
                        layer.msg("上传失败");
                        return;
                    }
                    else if(result.msg=="2"){
                        layer.msg("没有权限上报数据");
                   }else if(result.msg=="3"){
                        layer.msg("单位编码或密码错误");
                   }else if(result.msg=="4"){
                       layer.msg("全部数据操作失败，xml或zip压缩数据格式不正确!");
                   }else{
                        layer.msg("出现未知错误");
                   }
                },
                error: function() {
                   /* layerFn.center(AppKey.code.code199, "没有查询到儿童相关信息!");*/
                }
            });
        },
        print:function(){
            var chilCode = getSelectedRow_child();
            if(chilCode == undefined || chilCode == null){
                alert("请选择儿童");
                return ;
            }
            layer.open({
                title: '打印选择',
                skin: 'printDialog',
                type: 1,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['480px', '300px'],
                content: $("#printDialog")
            });

            /*layui.use(['layer', 'form','jquery'], function(){
                layer.msg("打印")
            });*/
        },
        printCard:function () {
            var chilCode = getSelectedRow_child();
            if(chilCode == undefined || chilCode == null){
                alert("请选择儿童");
                return ;
            }
            layer.open({
                title: '打印选择',
                skin: 'printDialog',
                type: 1,
                closeBtn: 1,//取消右上角的x关闭按钮
                area: ['480px', '300px'],
                content: $("#printCardDialog")
            });
        },
        loadPrintModel:function(){
            $.ajax({
                url: '../tchildprintmodel/list?orgid='+orgId+'&xml_name=selfdefineprint'+'&page=1&limit=1000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].xmlName + '">'+results.page.list[i].modelName+'</option>';
                    }
                    $("#modelName").html(con);
                    $("#cardModelName").html(con);

                }
            });
        },
        /**
         * @desc 批量打印儿童条形码
         * @author Tian
         * @date 2018/09/03
         */
        batchPrintCode:function () {

            var ele=$("#jqGrid");
            var ids = ele.getGridParam("selarrrow");
            if(ids.length == 0){
                layer.msg("请选择一个儿童或多个儿童！")
                return ;
            }
            var rows=new Array();
            for(var i=0;i<ids.length;i++){
                var row=ele.jqGrid("getRowData",ids[i]);
                rows.push(row);
            }

            var content;
            $.get("../configuration/queryConfiguration?type=barCodeIpAddress",function(data){
                var ret=data.data;
                var ipAddress=ret.remark+":7490";
                if (ipAddress!=undefined||ipAddress!=null||ipAddress!=''){
                    if(window.WebSocket != undefined) {
                        var connection = new WebSocket('ws://'+ipAddress);
                        connection.onopen = wsOpen;
                        //链接成功触发的回调函数,发送数据
                        function wsOpen (event) {
                            for (var i=0;i<rows.length;i++){
                                var ChildName= rows[i].chilName;
                                var childId= rows[i].chilCode;
                                if(ChildName == undefined || ChildName == null || ChildName == ""){
                                    content=":"+childId;
                                }else{
                                    content=ChildName+":"+childId;
                                }
                                connection.send(content);
                            }


                        }
                        //链接错误时触发
                        connection.onerror = wsError;
                        function wsError(event) {
                            layer.msg("没有启动打印服务或打印机所在IP设置错误！");
                        }

                    }
                }else{
                    layer.msg("设置打印机所在IP！");
                }

            })
        }

	}
});
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
//选择一条记录
function getSelectedRow_ino() {

    var grid = $("#inoculation_iframe").contents().find("#inoculationGrid");
    //$("#ifm").contents().find("#inoculationGrid").click();//jquery 方法1
    var rowKey = grid.getGridParam("selrow");
    /*if(!rowKey){
        alert("请选择一条记录");
        return ;
    }*/

    return rowKey;
}
//打印接种记录
function printInoculation(printType,xmlName) {
    var child_id = getSelectedRow_child();
    if(child_id == undefined || child_id == null || child_id == ""){
        layer.msg("请选择儿童！");
        return ;
    }
    var id = getSelectedRow_ino();
    if(id == undefined || id == null || id == ""){
        layer.msg("请选择接种记录！");
        return ;
    }
    var xmlName = xmlName.toString();
    if(xmlName==''){
        xmlName = "selfdefineprintCard";
    }
    var inoculate_id = $("#inoculation_iframe").contents().find("#inoculationGrid").getGridParam("selarrrow");
    layer.closeAll();
    window.open("../reports/printChildInoculateByModel/?inoculate_id="+inoculate_id+"&xmlName=" + xmlName+"&num=1"+'&printType='+printType+"&child_id="+child_id).print();

    //var mywin = window.open("../reports/printChildInoculateByModel?num="+num+"&version="+jrxml_name+"&chilCode="+chilCode+"&printType="+printType).print();


    //window.open("../reports/printchildinfoByModel?jrxml_name="+jrxml_name + "&chilCode=" + chilCode+"&printType="+printType);
}
//接种证打印儿童信息
function printChildInfo(printType,jrxml_name){

    var chilCode = getSelectedRow_child();
    if(chilCode == undefined || chilCode == null || chilCode == ""){
        layer.msg("请选择儿童！");
        return ;
    }

    layer.closeAll();
    window.open("../reports/printchildinfoByModel?jrxml_name="+jrxml_name + "&chilCode=" + chilCode+"&printType="+printType).print();


}
//接种卡新卡打印
function printChildInfoCard(num) {
    var jrxml_name = "selfdefineprintCard";
    var chilCode = getSelectedRow_child();
    if(chilCode == undefined || chilCode == null || chilCode == ""){
        layer.msg("请选择儿童！");
        return ;
    }

    layer.closeAll();

    window.open("../reports/printNewCard?num="+ num + "&chilCode=" + chilCode +"&jrxml_name=" + jrxml_name).print();

}