$(function () {
        vm.loadData();
    $("#jqGrid").jqGrid({
        url: '../tmgrstockbase/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 50, key: true,hidden:true },
			{ label: '疫苗id', name: 'fkVaccineCode', width: 80,hidden:true },
			{ label: '疫苗/产品名称', name: 'productName', width: 80 },
			{ label: '生产厂家编号', name: 'factoryId', width: 80 ,hidden:true},
			{ label: '生产厂家', name: 'factoryName', width: 80 },
			{ label: '批号', name: 'batchnum', width: 80 },
			{ label: '失效期', name: 'expiryDate', width: 80 },

			{ label: '接种部位', name: 'inoculationSite', width: 80 },
			{ label: '接种途径', name: 'channel', width: 80 },
			{ label: '计量单位', name: 'doseminUnitCode', width: 80 },
			{ label: '规格', name: 'dosenorm', width: 80 },
			{ label: '剂型', name: 'drug', width: 80 },
			{ label: '人份转换', name: 'conversion', width: 80 },
			{ label: '疫苗单价', name: 'price', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue.toFixed(2);;
                }},
			{ label: '批签发', name: 'lotRelease', width: 80 },
			{ label: '批准文号', name: 'licenseNumber', width: 80 },
            { label: '类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==0?'疫苗':'其他';
                }},
            { label: '状态', name: 'status', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==1?'<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }},
			{ label: '备注', name: 'remark', width: 80 }
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 20,
		rowList : [10,20,30,50],
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
            order: "order",
            sidx: "sidx"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    $("#vaccine").jqGrid({
        url: '../tvacinfo/list',
        datatype: "json",
        colModel: [
            { label: '国标编码', name: 'bioCode', width: 70 ,search:true},
            { label: '疫苗名称', name: 'bioName', width: 130 ,search:true},
            { label: '中文简称', name: 'bioCnSortname', width: 100 ,search:true},
            { label: '英文简称', name: 'bioEnSortname', width: 80 ,search:true},
        ],
        viewrecords: true,
        width:600,
        height: 500,
        rowNum: 1000,
        rowList : [10,30,50],
        rownumWidth: 25,
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
            $("#vaccine").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
		//选中一行，然后将数据保存到输入框中，关闭弹出框
        onSelectRow:function (rowid,status) {
            var row=$("#vaccine").jqGrid("getRowData",rowid);

            $("#fkVaccineCode").val(row.bioCode);
            $("#fkVaccineName").val(row.bioCnSortname);
            Vue.set(vm.tMgrStockBase, 'fkVaccineCode', row.bioCode);
            Vue.set(vm.tMgrStockBase, 'productName', row.bioCnSortname);
            $.get("../tmgrstockbase/getCodeInfo",{"code":row.bioCode},function (data) {
                if(data.tMgrStockBase!=null){
                    $('.selectpicker').selectpicker('val',data.tMgrStockBase.factoryId);
                    Vue.set(vm.tMgrStockBase, 'inoculationSite', data.tMgrStockBase.inoculationSite);
                    Vue.set(vm.tMgrStockBase, 'dosenorm', data.tMgrStockBase.dosenorm);
                    Vue.set(vm.tMgrStockBase, 'conversion', data.tMgrStockBase.conversion);
                    Vue.set(vm.tMgrStockBase, 'doseminUnitCode', data.tMgrStockBase.doseminUnitCode);
                    Vue.set(vm.tMgrStockBase, 'drug', data.tMgrStockBase.drug);
                    Vue.set(vm.tMgrStockBase, 'price', data.tMgrStockBase.price);
                    Vue.set(vm.tMgrStockBase, 'factoryName', data.tMgrStockBase.factoryName);
                    Vue.set(vm.tMgrStockBase, 'factoryId', data.tMgrStockBase.factoryId);
                }
            })
            //vm.tMgrStockBase = {fkVaccineCode:row.bioCode,productName:row.bioName};
            $('#myModal').modal('hide');
            $('#productForm').data('bootstrapValidator')
                .updateStatus('productName', 'NOT_VALIDATED',null)
                .validateField('productName');
        }
    });
    //初始化表单中的下拉选框数据







    //初始化下拉框
    $('.selectpicker').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择生产厂家...',
        actionsBox:true,
        search:false,
    });
    $.ajax({
        // get请求地址
        url: '../tvacfactory/getAllData',
        dataType: "json",
        type: 'POST',
        success: function (data) {
            var result=data.page;
            console.log(result);
            if("undefined" != typeof vm){

                vm.factoryCnName = result;
            }
            $("#factory").append(" <option value='-1'></option>")
            $.each(result, function (i, n) {

                $("#factory").append(" <option value=\"" + n.factoryCode + "\">" + n.factoryCnName + "</option>");
            })
            // $("#factory").selectpicker('refresh');
            $("#factory").selectpicker('refresh');
        }
    });

    //拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#factoryCtrl").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#factoryCtrl .open input').val();
        var hunt = $("#factory");
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
            var value = null;
            if("undefined" != typeof vm){
                value = vm.factoryCnName;
            }

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].factoryCnName.indexOf(inputManagerName) == 0){
                        con += '<option value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
                    }
                }
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            event.stopPropagation();
            return false;
        } else{
            //如果输入的字符为空,清除之前option标签
            hunt.empty();
            var value = null;
            if("undefined" != typeof vm){
                value = vm.factoryCnName;
            }
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });

    //校验表单录入的数据
    $('#factory').on('change',function(){
        var factoryName=$("#factory").find("option:selected").text();
        var factoryId=$("#factory").val();
        if(factoryName!=null&&factoryName!=""&&factoryName!=undefined){
            Vue.set(vm.tMgrStockBase, 'factoryName', factoryName);
        }
        Vue.set(vm.tMgrStockBase, 'factoryId', factoryId);

    });
    //表单JS校验
    $('#productForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            productName: {
                validators: {
                    notEmpty: {
                        message: '疫苗名称不能为空'
                    }
                }
            },
            batchnum: {
                validators: {
                    notEmpty: {
                        message: '批号不能为空'
                    }
                }
            },
            factoryId: {
                validators: {
                    notEmpty: {
                        message: '生产厂家不能为空'
                    }
                }
            },
          /*  factoryId: {
                validators: {
                    notEmpty: {
                        message: '生产厂家不能为空'
                    }
                }
            },*/
            expiryDate: {
                trigger:'change',
                validators: {
                    notEmpty: {
                        message: '失效期不能为空'
                    },
                    callback: {
                        message: '该疫苗已过期或即将过期，不能录入！',
                        callback: function (value, validator, $field) {
                           var nowDate=new Date();
                            nowDate.setDate(nowDate.getDate()+1);
                            if(value.length==8){
                                var year = value.substring(0, 4);
                                var month = value.substring(4, 6);
                                var date = value.substring(6, 8);
                                value=year+"-"+month+"-"+date;
                            }
                            return compareDate(value,formatDateTime(nowDate));

                        }
                    }
                }
            },
            inoculationSite: {
                validators: {
                    choice: {//check控件选择的数量
                        min: 1,
                        max: 1,
                        message: '必须选择接种部位'
                    }
                }
            },
            channel: {
                validators: {
                    choice: {//check控件选择的数量
                        min: 1,
                        max: 1,
                        message: '必须选择接种途径'
                    }
                }
            },
            doseminUnitCode: {
                validators: {
                    notEmpty: {
                        message: '计量单位不能为空'
                    }
                }
            },
           /* dosenorm2: {
                validators: {
                    notEmpty: {
                        message: '规格不能为空'
                    }
                }
            },*/
            dosenorm: {
                validators: {
                    notEmpty: {
                        message: '规格不能为空'
                    }
                }
            },
            drug: {
                validators: {
                    notEmpty: {
                        message: '剂型不能为空'
                    }
                }
            },
            conversion: {
                validators: {
                    notEmpty: {
                        message: '人份转换不能为空'
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        message: '价格不能为空'
                    },
                    numeric: {message: '价格只能输入数字'},
                    callback: {
                        message: '价格不能为负数！',
                        callback: function (value, validator, $field) {
                           var ret= parseFloat(value);
                           if(ret<0){
                               return false;
                           }
                           return true;
                        }
                    }

                }
            },
            lotRelease: {
                validators: {

                    notEmpty: {
                        message: '批签发不能为空'
                    }
                }
            },
            licenseNumber: {
                validators: {

                    notEmpty: {
                        message: '批准文号不能为空'
                    }
                }
            }
        }
    });
    $('#otherForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            productName: {
                validators: {
                    notEmpty: {
                        message: '产品名称不能为空'
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        message: '价格不能为空'
                    },
                    numeric: {message: '价格只能输入数字'},
                    callback: {
                        message: '价格不能为负数！',
                        callback: function (value, validator, $field) {
                            var ret= parseFloat(value);
                            if(ret<0){
                                return false;
                            }
                            return true;
                        }
                    }
                }
            },
            factoryName: {
                validators: {
                    notEmpty: {
                        message: '生产厂家不能为空'
                    }
                }
            },
            /*  factoryId: {
                  validators: {
                      notEmpty: {
                          message: '生产厂家不能为空'
                      }
                  }
              },*/
            expiryDate: {
                validators: {
                    notEmpty: {
                        message: '失效期不能为空'
                    },
                    callback: {
                        message: '该疫苗已过期或即将过期，不能录入！',
                        callback: function (value, validator, $field) {
                            var nowDate=new Date();
                            nowDate.setDate(nowDate.getDate()+1);
                            if(value.length==8){
                                var year = value.substring(0, 4);
                                var month = value.substring(4, 6);
                                var date = value.substring(6, 8);
                                value=year+"-"+month+"-"+date;
                            }

                            return compareDate(value,formatDateTime(nowDate));

                        }
                    }
                }
            }
        }
    });
    $("#tablePosition").find("td").css("width","50%");
});

function searchName() {
        var val=$("#searchName").val();
        console.log(val)
        var url="../tvacinfo/getAllData";
        $.ajax({
            url:url,
            type:'post',
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',//重点关注此参数
            success: function (data) {

                doSearch(val,data.page,['bioCode','bioName','bioCnSortname','bioEnSortname','pinyinInitials'],$("#vaccine"));
            }
        })
}


/**
 * 向字典表中查询数据方法
 * @param type 字典表类型
 * @param list VEU中对应的select集合
 * @param url  请求的地址
 */
function loadTypeData(type,listType,url) {
    var param = new Array();//定义数组
    $.ajax({
        // get请求地址
        url: url,
        dataType: "json",
        type: 'POST',
        data: {"type": type},
        success: function (data) {

            var result = data.page;
            $.each(result, function (index, item) {
                param.push({"text": item.text, "value": item.value});
            });
            vm[listType] = param;
        }
    });
}
var vaccineForm;
var otherForm;
var vm = new Vue({
    el:'#rrapp',
    data:{

        showList: true,
        title: null,
        tMgrStockBase: {type:'0',price:""},
        items:[],
        doseminUnitCode:[],
        siteList:[],
        channelList:[],
        dosenorm:[],
        drug:[],
        factoryCnName:null,

	},
	methods: {
		query: function () {
            var date=$("#searchDate").val();
            var searchProductName=$("#searchProductName").val();
            var searchFactoryName=$("#searchFactoryName").val();
            var searchBatch=$("#searchBatch").val();
            var searchType=$("#searchType").val();
            $("#jqGrid").jqGrid('setGridParam',{

                postData:{
                    'searchProductName': searchProductName,
                    'searchFactoryName': searchFactoryName,
                    'searchBatch': searchBatch,
                    'searchDate': date,
                    'searchType': searchType,
                },
                page:1
            }).trigger("reloadGrid");
		},
        loadData:function(){
		    //类型数据
            var param = new Array();//定义数组
			param.push({"text":'疫苗',"value":'0'});
			param.push({"text":'其他',"value":'1'});
            vm.items = param;
            //接种部位数据加载
            loadTypeData("code_inoculation_site","siteList",'../sys/dict/typeList');
            //加载计量单位数据
            loadTypeData("vaccine_unit","doseminUnitCode",'../sys/dict/typeList');

            //加载规格单位
            loadTypeData("dose_code","dosenorm",'../sys/dict/typeList');
            //加载剂型数据
            loadTypeData("code_drug","drug",'../sys/dict/typeList');
            //添加接种途径
            loadTypeData("code_inoculation_route","channelList",'../sys/dict/typeList');


        },
        stop: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }

            confirm('确定要操作选中的产品吗？', function(){
                $.ajax({
                    type: "POST",
                    url: "../tmgrstockbase/updateStatus?id="+id,
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
		add: function(){
			vm.showList = false;
			vm.title = "新增";
            vm.tMgrStockBase={};
            vm.resetForm();
            Vue.set(vm.tMgrStockBase, 'lotRelease', "LRA");
            Vue.set(vm.tMgrStockBase, 'licenseNumber', "S");
            var vaccine=$("#myTab").find("li[tag='vaccine']");
            var other=$("#myTab").find("li[tag='other']");
            other.removeClass("active")
            other.attr("aria-expanded",'false')
            other.find("a").attr("aria-expanded",'false')
            $("#other").removeClass("active in")
            other.css("display","block");
            vaccine.css("display","block");
            vaccine.addClass("active");
            vaccine.attr("aria-expanded",'true')
            vaccine.find("a").attr("aria-expanded",'true')
            $("#product").addClass("active in")
			//vm.tMgrStockBase = {lotRelease:'LRA',type:'0'};
            /*if (otherForm!=undefined||vaccineForm!=undefined){
                var div="<li class=\"active\" tag=\"vaccine\">\n" +
                "\t\t\t\t<a href=\"#product\" data-toggle=\"tab\" >\n" +
                "\t\t\t\t\t新增疫苗\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</li>\n" +
                "\t\t\t<li tag=\"other\"><a href=\"#other\" data-toggle=\"tab\" >{{title}}其他</a></li>";
                $("#myTab li").empty();
                $("#myTab").append(div);
            }*/
		},
		update: function (event) {

			var id = getSelectedRow();
			if(id == null){
				return ;
			}

            vm.title = "修改";
            vm.tMgrStockBase={};
            vm.resetForm();
            vm.getInfo(id);
            

            vm.showList = false;

		},
		saveOrUpdate: function (event) {

            var vaccine=$("#myTab").find("li[tag='vaccine']");
            var other=$("#myTab").find("li[tag='other']");
           var lease= vm.tMgrStockBase.lotRelease;
           var licenseNumber= vm.tMgrStockBase.licenseNumber;
            if(vaccine.attr("class")!=undefined&&vaccine.attr("class").indexOf("active")>=0){
                vm.tMgrStockBase.type=0;
                if (lease.trim()=='LRA'||lease==''||lease==undefined||lease==null){
                    layer.msg("批签发不能为空！");
                    return;
                }
                if (licenseNumber.trim()=='S'||licenseNumber==''||licenseNumber==undefined||licenseNumber==null){
                    layer.msg("批准文号不能为空！");
                    return;
                }
                saveData($("#productForm"))
            }else{
                vm.tMgrStockBase.type=1;
                vm.tMgrStockBase.lotRelease="";
                saveData($("#otherForm"))
            }

		},
		del: function (event) {
			var ids = getSelectedRows();
                if(ids == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../tmgrstockbase/delete",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
		    var vaccine=$("#myTab").find("li[tag='vaccine']");
		    var other=$("#myTab").find("li[tag='other']");

            $.ajax({
                type: "POST",
                url: "../tmgrstockbase/info/"+id,
                async: false,
                contentType:'application/json;charset=UTF-8',
                success: function(r){
                    var bioCode=r.tMgrStockBase.fkVaccineCode;
                    if(bioCode==null||bioCode==undefined||bioCode==""){
                        vaccine.removeClass("active")
                        vaccine.attr("aria-expanded",'false')
                        vaccine.find("a").attr("aria-expanded",'false')
                        $("#product").removeClass("active in")
                        vaccine.css("display","none");
                        other.css("display","block");
                        other.addClass("active");
                        other.attr("aria-expanded",'true')
                        other.find("a").attr("aria-expanded",'true')
                        $("#other").addClass("active in")
                    }else{
                        other.removeClass("active")
                        other.attr("aria-expanded",'false')
                        other.find("a").attr("aria-expanded",'false')
                        $("#other").removeClass("active in")
                        other.css("display","none");
                        vaccine.css("display","block");
                        vaccine.addClass("active");
                        vaccine.attr("aria-expanded",'true')
                        vaccine.find("a").attr("aria-expanded",'true')
                        $("#product").addClass("active in")
                    }
                    $('.selectpicker').selectpicker('val',r.tMgrStockBase.factoryId);
                    vm.tMgrStockBase = r.tMgrStockBase;
                }
            });
			/*$.get("../tmgrstockbase/info/"+id, function(r){

			   console.log(r)
                

            });*/
		},
		reload: function (event) {

			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
            vm.tMgrStockBase={};
		},
        reset:function(){
            $("#searchForm")[0].reset();
        },
        changePrice :function () {

                var vaccineId=$("#fkVaccineCode").val();
                var vaccineName=$("#fkVaccineName").val();
                if(vaccineId != undefined && vaccineId != ""){
                    Vue.set(vm.tMgrStockBase, 'fkVaccineCode', vaccineId);
                    Vue.set(vm.tMgrStockBase, 'productName', vaccineName);
                }
                    var val=$("#factory option:selected").val();
                    var text=$("#factory option:selected").html();
                if(val != undefined && val != ""){
                    Vue.set(vm.tMgrStockBase, 'factoryId', val);
                    Vue.set(vm.tMgrStockBase, 'factoryName', text);
                }
                vm.tMgrStockBase.price=Number(vm.tMgrStockBase.price).toFixed(2);

        },
        //清除校验状态
        resetForm:function () {
            $("#productForm").data('bootstrapValidator').resetForm();
            $("#otherForm").data('bootstrapValidator').resetForm();
        },
        updateStatus:function(str){
            $('#productForm').data('bootstrapValidator')
                .updateStatus(str, 'NOT_VALIDATED',null)
                .validateField(str);
        }

	}


});
function saveData(formId){
    var ele=formId
    ele.bootstrapValidator('validate');//提交验证
    if (ele.data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
        //禁用按钮
        $("#sureBtn").attr("disabled","disabled");
        var url = vm.tMgrStockBase.id == null ? "../tmgrstockbase/save" : "../tmgrstockbase/update";

        //封装疫苗数据
        var vaccineId=$("#fkVaccineCode").val();
        var vaccineName=$("#fkVaccineName").val();
        Vue.set(vm.tMgrStockBase, 'fkVaccineCode', vaccineId);
        Vue.set(vm.tMgrStockBase, 'productName', vaccineName);
        if (vm.tMgrStockBase.factoryName==null||vm.tMgrStockBase.factoryName==undefined||vm.tMgrStockBase.factoryName==""){
            layer.msg("生产厂家不能为空！");
            return;
        }
       var expiryDate= vm.tMgrStockBase.expiryDate;
        if(expiryDate.length==8){
               var year = expiryDate.substring(0, 4);
               var month = expiryDate.substring(4, 6);
               var date = expiryDate.substring(6, 8);
               var retDate=year+"-"+month+"-"+date;
               vm.tMgrStockBase.expiryDate=retDate;
        }
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(vm.tMgrStockBase),
            contentType:'application/json;charset=UTF-8',
            success: function(r){
                $("#sureBtn").removeAttr("disabled");
                if(r.code === 0){
                    layer.msg('操作成功');
                        vm.reload();
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    }
}