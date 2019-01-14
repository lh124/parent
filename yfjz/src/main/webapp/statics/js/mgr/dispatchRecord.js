var vm= new Vue({
    el:"#dispatch",
    data:{
        showList: true,
        title: null,
        tMgrStore: {posId:null},
        items:[],
        rowData:{
            productName:null,
            dosenorm:null,
            drug:null,
            doseminUnitCode:null,
            conversion:null
        }
    },
    methods:{
        print:function () {
           window.open("../tmgrstockinfo/printDispatch?infoId="+vm.rowData.id+"&dosenorm="+vm.rowData.dosenorm+"&drug="+vm.rowData.drug+"&doseminUnitCode="+vm.rowData.doseminUnitCode+"&vaccine="+vm.rowData.productName,"_blank");
        },
        excel:function () {
            window.open("../tmgrstockinfo/excelDispatch?infoId="+vm.rowData.id+"&dosenorm="+vm.rowData.dosenorm+"&drug="+vm.rowData.drug+"&doseminUnitCode="+vm.rowData.doseminUnitCode+"&vaccine="+vm.rowData.productName);
        }
    }
})

$(function () {
    $("#dispatchTable").jqGrid({
        // url: '../tmgrstockinfo/queryDispatchList',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true ,hidden:true},
            { label: '日期', name: 'createTime', width: 85},
            { label: '摘要(来源/去向)', name: 'storeName', width: 100},
            { label: '生产企业', name: 'factoryName', width: 100 },
            { label: '批准文号', name: 'licenseNumber', width: 100 },
            { label: '批签发合格证编号', name: 'lotRelease', width: 100 },
            { label: '数量(支)', name: 'inAmount', width: 60 },
            { label: '数量(人份)', name: 'inPersonAmount', width: 60 },
            { label: '批号', name: 'inBatchnum', width: 90 },
            { label: '失效期', name: 'inExpiryDate', width: 85 },
            { label: '数量(支)', name: 'outAmount', width: 60 },
            { label: '数量(人份)', name: 'outPersonAmount', width: 60 },
            { label: '批号', name: 'outBatchnum', width: 90 },
            { label: '失效期', name: 'outExpiryDate', width: 80 },
            { label: '数量(支)', name: 'remainAmount', width: 60 },
            { label: '数量(人份)', name: 'remainPersonAmount', width: 60 },
            { label: '批号', name: 'remainBatchnum', width: 90 },
            { label: '失效期', name: 'remainExpiryDate', width: 85 },
            { label: '备注(领苗人)', name: 'realName', width: 80 },
        ],
        viewrecords: true,
        height: 'auto',
        rowNum: 1000,
        rowList : [10,20,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        // multiselect: true,
        pager: "#dispatchPager",
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


        },
        ondblClickRow:function(row){

        },

    });
    $("#dispatchTable").jqGrid('setGroupHeaders', {
        useColSpanStyle: true,
        groupHeaders:[
            {startColumnName:'inAmount', numberOfColumns:4, titleText: '收入'},
            {startColumnName:'outAmount', numberOfColumns:4, titleText: '发出'},
            {startColumnName:'remainAmount', numberOfColumns:4, titleText: '结余'},
        ]
    })

})
   var child;
function setChildValue(data) {
    vm.rowData={};
    vm.rowData=data;
    if(vm.rowData.conversion){
        vm.rowData.dosenorm= vm.rowData.dosenorm+'/'+vm.rowData.conversion+'人份';
    }
    $("#dispatchTable").jqGrid('clearGridData');  //清空表格
    $.ajax({
        url:'../tmgrstockinfo/queryDispatchList',
        type:'get',
        dataType:"json",
        data:{'infoId':vm.rowData.id},
        success:function (data) {
            $("#dispatchTable").setGridParam({datatype : 'local',data: data.page.list}).trigger('reloadGrid');
        }

    })

}