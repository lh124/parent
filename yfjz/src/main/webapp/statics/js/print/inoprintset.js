
$(function() {
    init();
    $("#inoculateGrid").jqGrid({
        url: '../SetPrintForm/selectInoculatePrintSetModel?remark=1&xml_name=selfdefineprintInoculate',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 1, key: true,hidden:true },
            { label: '模板名称', name: 'model_name', width: 220 },
            { label: '用户', name: 'username', width: 80 }

        ],
        viewrecords: true,
        height: 285,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        //pager: "#jqGridPager",
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
            $("#inoculateGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});
function init(){
    $.ajax({
        type:"post",
        url:"../SetPrintForm/selectInoculatePrintSet.jhtml?printType=1",
        success:function (data) {

           /* var json = eval('(' + data + ')');*/
            $.each(data.page.list,function(i,item){
                if(item.ename =='HepB'){
                    $("input[name='HepBDateX']").val(item.inoculate_dateX);
                    $("input[name='HepBDateY']").val(item.inoculate_dateY);
                    $("input[name='HepBSiteX']").val(item.textX);
                    $("input[name='HepBSiteY']").val(item.textY);
                    $("input[name='HepBNumX']").val(item.batchnumX);
                    $("input[name='HepBNumY']").val(item.batchnumY);
                    $("input[name='HepBFacX']").val(item.factory_nameX);
                    $("input[name='HepBFacY']").val(item.factory_nameY);
                    $("input[name='HepBDeptX']").val(item.departnameX);
                    $("input[name='HepBDeptY']").val(item.departnameY);
                    $("input[name='HepBDocX']").val(item.doctorX);
                    $("input[name='HepBDocY']").val(item.doctorY);

                }
                if(item.ename =='BCG'){
                    $("input[name='BCGDateX']").val(item.inoculate_dateX);
                    $("input[name='BCGDateY']").val(item.inoculate_dateY);
                    $("input[name='BCGSiteX']").val(item.textX);
                    $("input[name='BCGSiteY']").val(item.textY);
                    $("input[name='BCGNumX']").val(item.batchnumX);
                    $("input[name='BCGNumY']").val(item.batchnumY);
                    $("input[name='BCGFacX']").val(item.factory_nameX);
                    $("input[name='BCGFacY']").val(item.factory_nameY);
                    $("input[name='BCGDeptX']").val(item.departnameX);
                    $("input[name='BCGDeptY']").val(item.departnameY);
                    $("input[name='BCGDocX']").val(item.doctorX);
                    $("input[name='BCGDocY']").val(item.doctorY);

                }
                if(item.ename =='IPV'){
                    $("input[name='IPVDateX']").val(item.inoculate_dateX);
                    $("input[name='IPVDateY']").val(item.inoculate_dateY);
                    $("input[name='IPVSiteX']").val(item.textX);
                    $("input[name='IPVSiteY']").val(item.textY);
                    $("input[name='IPVNumX']").val(item.batchnumX);
                    $("input[name='IPVNumY']").val(item.batchnumY);
                    $("input[name='IPVFacX']").val(item.factory_nameX);
                    $("input[name='IPVFacY']").val(item.factory_nameY);
                    $("input[name='IPVDeptX']").val(item.departnameX);
                    $("input[name='IPVDeptY']").val(item.departnameY);
                    $("input[name='IPVDocX']").val(item.doctorX);
                    $("input[name='IPVDocY']").val(item.doctorY);

                }
                if(item.ename =='OPV'){
                    $("input[name='OPVDateX']").val(item.inoculate_dateX);
                    $("input[name='OPVDateY']").val(item.inoculate_dateY);
                    $("input[name='OPVSiteX']").val(item.textX);
                    $("input[name='OPVSiteY']").val(item.textY);
                    $("input[name='OPVNumX']").val(item.batchnumX);
                    $("input[name='OPVNumY']").val(item.batchnumY);
                    $("input[name='OPVFacX']").val(item.factory_nameX);
                    $("input[name='OPVFacY']").val(item.factory_nameY);
                    $("input[name='OPVDeptX']").val(item.departnameX);
                    $("input[name='OPVDeptY']").val(item.departnameY);
                    $("input[name='OPVDocX']").val(item.doctorX);
                    $("input[name='OPVDocY']").val(item.doctorY);

                }
                if(item.ename =='DTP'){
                    $("input[name='DTPDateX']").val(item.inoculate_dateX);
                    $("input[name='DTPDateY']").val(item.inoculate_dateY);
                    $("input[name='DTPSiteX']").val(item.textX);
                    $("input[name='DTPSiteY']").val(item.textY);
                    $("input[name='DTPNumX']").val(item.batchnumX);
                    $("input[name='DTPNumY']").val(item.batchnumY);
                    $("input[name='DTPFacX']").val(item.factory_nameX);
                    $("input[name='DTPFacY']").val(item.factory_nameY);
                    $("input[name='DTPDeptX']").val(item.departnameX);
                    $("input[name='DTPDeptY']").val(item.departnameY);
                    $("input[name='DTPDocX']").val(item.doctorX);
                    $("input[name='DTPDocY']").val(item.doctorY);
                }
                if(item.ename =='DT'){
                    $("input[name='DTDateX']").val(item.inoculate_dateX);
                    $("input[name='DTDateY']").val(item.inoculate_dateY);
                    $("input[name='DTSiteX']").val(item.textX);
                    $("input[name='DTSiteY']").val(item.textY);
                    $("input[name='DTNumX']").val(item.batchnumX);
                    $("input[name='DTNumY']").val(item.batchnumY);
                    $("input[name='DTFacX']").val(item.factory_nameX);
                    $("input[name='DTFacY']").val(item.factory_nameY);
                    $("input[name='DTDeptX']").val(item.departnameX);
                    $("input[name='DTDeptY']").val(item.departnameY);
                    $("input[name='DTDocX']").val(item.doctorX);
                    $("input[name='DTDocY']").val(item.doctorY);
                }
                if(item.ename =='MR'){
                    $("input[name='MRDateX']").val(item.inoculate_dateX);
                    $("input[name='MRDateY']").val(item.inoculate_dateY);
                    $("input[name='MRSiteX']").val(item.textX);
                    $("input[name='MRSiteY']").val(item.textY);
                    $("input[name='MRNumX']").val(item.batchnumX);
                    $("input[name='MRNumY']").val(item.batchnumY);
                    $("input[name='MRFacX']").val(item.factory_nameX);
                    $("input[name='MRFacY']").val(item.factory_nameY);
                    $("input[name='MRDeptX']").val(item.departnameX);
                    $("input[name='MRDeptY']").val(item.departnameY);
                    $("input[name='MRDocX']").val(item.doctorX);
                    $("input[name='MRDocY']").val(item.doctorY);
                }
                if(item.ename =='MMR'){
                    $("input[name='MMRDateX']").val(item.inoculate_dateX);
                    $("input[name='MMRDateY']").val(item.inoculate_dateY);
                    $("input[name='MMRSiteX']").val(item.textX);
                    $("input[name='MMRSiteY']").val(item.textY);
                    $("input[name='MMRNumX']").val(item.batchnumX);
                    $("input[name='MMRNumY']").val(item.batchnumY);
                    $("input[name='MMRFacX']").val(item.factory_nameX);
                    $("input[name='MMRFacY']").val(item.factory_nameY);
                    $("input[name='MMRDeptX']").val(item.departnameX);
                    $("input[name='MMRDeptY']").val(item.departnameY);
                    $("input[name='MMRDocX']").val(item.doctorX);
                    $("input[name='MMRDocY']").val(item.doctorY);
                }
                if(item.ename =='MM'){
                    $("input[name='MMDateX']").val(item.inoculate_dateX);
                    $("input[name='MMDateY']").val(item.inoculate_dateY);
                    $("input[name='MMSiteX']").val(item.textX);
                    $("input[name='MMSiteY']").val(item.textY);
                    $("input[name='MMNumX']").val(item.batchnumX);
                    $("input[name='MMNumY']").val(item.batchnumY);
                    $("input[name='MMFacX']").val(item.factory_nameX);
                    $("input[name='MMFacY']").val(item.factory_nameY);
                    $("input[name='MMDeptX']").val(item.departnameX);
                    $("input[name='MMDeptY']").val(item.departnameY);
                    $("input[name='MMDocX']").val(item.doctorX);
                    $("input[name='MMDocY']").val(item.doctorY);
                }
                if(item.ename =='MV'){
                    $("input[name='MVDateX']").val(item.inoculate_dateX);
                    $("input[name='MVDateY']").val(item.inoculate_dateY);
                    $("input[name='MVSiteX']").val(item.textX);
                    $("input[name='MVSiteY']").val(item.textY);
                    $("input[name='MVNumX']").val(item.batchnumX);
                    $("input[name='MVNumY']").val(item.batchnumY);
                    $("input[name='MVFacX']").val(item.factory_nameX);
                    $("input[name='MVFacY']").val(item.factory_nameY);
                    $("input[name='MVDeptX']").val(item.departnameX);
                    $("input[name='MVDeptY']").val(item.departnameY);
                    $("input[name='MVDocX']").val(item.doctorX);
                    $("input[name='MVDocY']").val(item.doctorY);

                }
                if(item.ename =='MPV'){
                    $("input[name='MPVDateX']").val(item.inoculate_dateX);
                    $("input[name='MPVDateY']").val(item.inoculate_dateY);
                    $("input[name='MPVSiteX']").val(item.textX);
                    $("input[name='MPVSiteY']").val(item.textY);
                    $("input[name='MPVNumX']").val(item.batchnumX);
                    $("input[name='MPVNumY']").val(item.batchnumY);
                    $("input[name='MPVFacX']").val(item.factory_nameX);
                    $("input[name='MPVFacY']").val(item.factory_nameY);
                    $("input[name='MPVDeptX']").val(item.departnameX);
                    $("input[name='MPVDeptY']").val(item.departnameY);
                    $("input[name='MPVDocX']").val(item.doctorX);
                    $("input[name='MPVDocY']").val(item.doctorY);

                }
                if(item.ename =='MPVplus'){
                    $("input[name='MPVplusDateX']").val(item.inoculate_dateX);
                    $("input[name='MPVplusDateY']").val(item.inoculate_dateY);
                    $("input[name='MPVplusSiteX']").val(item.textX);
                    $("input[name='MPVplusSiteY']").val(item.textY);
                    $("input[name='MPVplusNumX']").val(item.batchnumX);
                    $("input[name='MPVplusNumY']").val(item.batchnumY);
                    $("input[name='MPVplusFacX']").val(item.factory_nameX);
                    $("input[name='MPVplusFacY']").val(item.factory_nameY);
                    $("input[name='MPVplusDeptX']").val(item.departnameX);
                    $("input[name='MPVplusDeptY']").val(item.departnameY);
                    $("input[name='MPVplusDocX']").val(item.doctorX);
                    $("input[name='MPVplusDocY']").val(item.doctorY);

                }
                if(item.ename =='JEV'){
                    $("input[name='JEVDateX']").val(item.inoculate_dateX);
                    $("input[name='JEVDateY']").val(item.inoculate_dateY);
                    $("input[name='JEVSiteX']").val(item.textX);
                    $("input[name='JEVSiteY']").val(item.textY);
                    $("input[name='JEVNumX']").val(item.batchnumX);
                    $("input[name='JEVNumY']").val(item.batchnumY);
                    $("input[name='JEVFacX']").val(item.factory_nameX);
                    $("input[name='JEVFacY']").val(item.factory_nameY);
                    $("input[name='JEVDeptX']").val(item.departnameX);
                    $("input[name='JEVDeptY']").val(item.departnameY);
                    $("input[name='JEVDocX']").val(item.doctorX);
                    $("input[name='JEVDocY']").val(item.doctorY);

                }
                if(item.ename =='JEVM'){
                    $("input[name='JEVMDateX']").val(item.inoculate_dateX);
                    $("input[name='JEVMDateY']").val(item.inoculate_dateY);
                    $("input[name='JEVMSiteX']").val(item.textX);
                    $("input[name='JEVMSiteY']").val(item.textY);
                    $("input[name='JEVMNumX']").val(item.batchnumX);
                    $("input[name='JEVMNumY']").val(item.batchnumY);
                    $("input[name='JEVMFacX']").val(item.factory_nameX);
                    $("input[name='JEVMFacY']").val(item.factory_nameY);
                    $("input[name='JEVMDeptX']").val(item.departnameX);
                    $("input[name='JEVMDeptY']").val(item.departnameY);
                    $("input[name='JEVMDocX']").val(item.doctorX);
                    $("input[name='JEVMDocY']").val(item.doctorY);

                }
                if(item.ename =='HepA'){
                    $("input[name='HepADateX']").val(item.inoculate_dateX);
                    $("input[name='HepADateY']").val(item.inoculate_dateY);
                    $("input[name='HepASiteX']").val(item.textX);
                    $("input[name='HepASiteY']").val(item.textY);
                    $("input[name='HepANumX']").val(item.batchnumX);
                    $("input[name='HepANumY']").val(item.batchnumY);
                    $("input[name='HepAFacX']").val(item.factory_nameX);
                    $("input[name='HepAFacY']").val(item.factory_nameY);
                    $("input[name='HepADeptX']").val(item.departnameX);
                    $("input[name='HepADeptY']").val(item.departnameY);
                    $("input[name='HepADocX']").val(item.doctorX);
                    $("input[name='HepADocY']").val(item.doctorY);

                }
                if(item.ename =='HepAM'){
                    $("input[name='HepAMDateX']").val(item.inoculate_dateX);
                    $("input[name='HepAMDateY']").val(item.inoculate_dateY);
                    $("input[name='HepAMSiteX']").val(item.textX);
                    $("input[name='HepAMSiteY']").val(item.textY);
                    $("input[name='HepAMNumX']").val(item.batchnumX);
                    $("input[name='HepAMNumY']").val(item.batchnumY);
                    $("input[name='HepAMFacX']").val(item.factory_nameX);
                    $("input[name='HepAMFacY']").val(item.factory_nameY);
                    $("input[name='HepAMDeptX']").val(item.departnameX);
                    $("input[name='HepAMDeptY']").val(item.departnameY);
                    $("input[name='HepAMDocX']").val(item.doctorX);
                    $("input[name='HepAMDocY']").val(item.doctorY);

                }
                if(item.ename =='VarV'){
                    $("input[name='VarVDateX']").val(item.inoculate_dateX);
                    $("input[name='VarVDateY']").val(item.inoculate_dateY);
                    $("input[name='VarVSiteX']").val(item.textX);
                    $("input[name='VarVSiteY']").val(item.textY);
                    $("input[name='VarVNumX']").val(item.batchnumX);
                    $("input[name='VarVNumY']").val(item.batchnumY);
                    $("input[name='VarVFacX']").val(item.factory_nameX);
                    $("input[name='VarVFacY']").val(item.factory_nameY);
                    $("input[name='VarVDeptX']").val(item.departnameX);
                    $("input[name='VarVDeptY']").val(item.departnameY);
                    $("input[name='VarVDocX']").val(item.doctorX);
                    $("input[name='VarVDocY']").val(item.doctorY);

                }
                if(item.ename =='Hib'){
                    $("input[name='HibDateX']").val(item.inoculate_dateX);
                    $("input[name='HibDateY']").val(item.inoculate_dateY);
                    $("input[name='HibSiteX']").val(item.textX);
                    $("input[name='HibSiteY']").val(item.textY);
                    $("input[name='HibNumX']").val(item.batchnumX);
                    $("input[name='HibNumY']").val(item.batchnumY);
                    $("input[name='HibFacX']").val(item.factory_nameX);
                    $("input[name='HibFacY']").val(item.factory_nameY);
                    $("input[name='HibDeptX']").val(item.departnameX);
                    $("input[name='HibDeptY']").val(item.departnameY);
                    $("input[name='HibDocX']").val(item.doctorX);
                    $("input[name='HibDocY']").val(item.doctorY);

                }
                if(item.ename =='ORV'){

                    $("input[name='ORVDateX']").val(item.inoculate_dateX);
                    $("input[name='ORVDateY']").val(item.inoculate_dateY);
                    $("input[name='ORVSiteX']").val(item.textX);
                    $("input[name='ORVSiteY']").val(item.textY);
                    $("input[name='ORVNumX']").val(item.batchnumX);
                    $("input[name='ORVNumY']").val(item.batchnumY);
                    $("input[name='ORVFacX']").val(item.factory_nameX);
                    $("input[name='ORVFacY']").val(item.factory_nameY);
                    $("input[name='ORVDeptX']").val(item.departnameX);
                    $("input[name='ORVDeptY']").val(item.departnameY);
                    $("input[name='ORVDocX']").val(item.doctorX);
                    $("input[name='ORVDocY']").val(item.doctorY);
                }

                console.info(item.batchnumX);
            })
            //console.info(obj);
        }
    });

}

function saveInoSet() {
    var allX = $("#allX").val();
    var allY = $("#allY").val();
    var printType = '1';
    var data= $("#ff").serializeArray();
    var HepBObj = [];
    var BCGObj = [];
    var IPVObj = [];
    var OPVObj = [];
    var DTPObj = [];
    var DTObj = [];
    var MRObj = [];
    var MMRObj = [];
    var MMObj = [];
    var MVObj = [];
    var MPVObj = [];
    var MPVplusObj = [];
    var JEVObj = [];
    var JEVMObj = [];
    var HepAObj = [];
    var HepAMObj = [];
    var VarVObj = [];
    var HibObj = [];
    var ORVObj = [];
    for(var i = 0; i<data.length;i++){
        if(i<13){
            HepBObj.push(data[i]);
        }
        if(i>=13&&i<26){
            BCGObj.push(data[i]);
        }
        if(i>=26&&i<39){
            IPVObj.push(data[i]);
        }
        if(i>=39&&i<52){
            OPVObj.push(data[i]);
        }
        if(i>=52&&i<65){
            DTPObj.push(data[i]);
        }
        if(i>=65&&i<78){
            DTObj.push(data[i]);
        }
        if(i>=78&&i<91){
            MRObj.push(data[i]);
        }
        if(i>=91&&i<104){
            MMRObj.push(data[i]);
        }
        if(i>=104&&i<117){
            MMObj.push(data[i]);
        }
        if(i>=117&&i<130){
            MVObj.push(data[i]);
        }
        if(i>=130&&i<143){
            MPVObj.push(data[i]);
        }
        if(i>=143&&i<156){
            MPVplusObj.push(data[i]);
        }
        if(i>=156&&i<169){
            JEVObj.push(data[i]);
        }
        if(i>=169&&i<182){
            JEVMObj.push(data[i]);
        }
        if(i>=182&&i<195){
            HepAObj.push(data[i]);
        }
        if(i>=195&&i<208){
            HepAMObj.push(data[i]);
        }
        if(i>=208&&i<221){
            VarVObj.push(data[i]);
        }
        if(i>=221&&i<234){
            HibObj.push(data[i]);
        }
        if(i>=234&&i<247){
            ORVObj.push(data[i]);
        }
    }

    var datas = [];
    datas.push(HepBObj) ;
    datas.push(BCGObj);
    datas.push(IPVObj);
    datas.push(OPVObj);
    datas.push(DTPObj);
    datas.push(DTObj);
    datas.push(MRObj);
    datas.push(MMRObj);
    datas.push(MMObj);
    datas.push(MVObj);
    datas.push(MPVObj);
    datas.push(MPVplusObj);
    datas.push(JEVObj);
    datas.push(JEVMObj);
    datas.push(HepAObj);
    datas.push(HepAMObj);
    datas.push(VarVObj);
    datas.push(HibObj);
    datas.push(ORVObj);
    var datass = JSON.stringify(datas);

    $.ajax({
        type: "post",
        url: '../SetPrintForm/ChildInoculatePrintSet.jhtml?printType='+printType+"&allX="+allX+"&allY="+allY,
        dataType: "json",
        data: {"datass":datass},
        success: function (data) {
            if(data.message=='true'){
                $.ajax({
                    type: "post",
                    url: '../SetPrintForm/ChildInoculatePrintSetIntoTable.jhtml?printType='+printType,
                    dataType: "json",
                    data: {"datass":datass},
                    success: function (data) {
                        if(data.message=='true'){
                            layer.msg("保存成功",{icon: 1});
                            init();
                        }else{
                            layer.msg("保存失败",{icon: 5});
                        }

                    },
                    error: function (response, err) {
                        layer.msg("保存失败",{icon: 5});
                    }
                });
                /*layer.msg("保存成功",{icon: 1});*/
            }else{
                layer.msg("保存失败",{icon: 5});
            }

        },
        error: function (response, err) {
            layer.msg("保存失败",{icon: 5});
        }
    });
}

function saveSetIntoTable(){
    var printType = '1';
    $.ajax({
        type: "post",
        url: '../SetPrintForm/ChildInoculatePrintSetIntoTable.jhtml?printType='+printType,
        dataType: "json",
        success: function (data) {
            if(data.message=='true'){
                layer.msg("保存成功",{icon: 1});
            }else{
                layer.msg("保存失败",{icon: 5});
            }

        },
        error: function (response, err) {
            layer.msg("保存失败",{icon: 5});
        }
    });
}

    function saveInoSetAs(){
        /*var childId = ($('#inoculateGrid').datagrid('getRows')[row_index]['child_id']);*/
        layer.prompt({title:'另存为模板'},function(model_name,index) {
            if(model_name==null || model_name.trim()==""){
                layer.msg("模板名不能为空！");
                return;
            }
            layer.close(index);
            if (model_name != null && model_name.trim() != "") {
                var allX = $("#allX").val();
                var allY = $("#allY").val();
                var printType = '1';
                var date = new Date();
                var xml_name = "selfdefineprintInoculate"+date.getMinutes()+date.getSeconds();
                var data= $("#ff").serializeArray();
                var HepBObj = [];
                var BCGObj = [];
                var IPVObj = [];
                var OPVObj = [];
                var DTPObj = [];
                var DTObj = [];
                var MRObj = [];
                var MMRObj = [];
                var MMObj = [];
                var MVObj = [];
                var MPVObj = [];
                var MPVplusObj = [];
                var JEVObj = [];
                var JEVMObj = [];
                var HepAObj = [];
                var HepAMObj = [];
                var VarVObj = [];
                var HibObj = [];
                var ORVObj = [];
                for(var i = 0; i<data.length;i++){
                    if(i<13){
                        HepBObj.push(data[i]);
                    }
                    if(i>=13&&i<26){
                        BCGObj.push(data[i]);
                    }
                    if(i>=26&&i<39){
                        IPVObj.push(data[i]);
                    }
                    if(i>=39&&i<52){
                        OPVObj.push(data[i]);
                    }
                    if(i>=52&&i<65){
                        DTPObj.push(data[i]);
                    }
                    if(i>=65&&i<78){
                        DTObj.push(data[i]);
                    }
                    if(i>=78&&i<91){
                        MRObj.push(data[i]);
                    }
                    if(i>=91&&i<104){
                        MMRObj.push(data[i]);
                    }
                    if(i>=104&&i<117){
                        MMObj.push(data[i]);
                    }
                    if(i>=117&&i<130){
                        MVObj.push(data[i]);
                    }
                    if(i>=130&&i<143){
                        MPVObj.push(data[i]);
                    }
                    if(i>=143&&i<156){
                        MPVplusObj.push(data[i]);
                    }
                    if(i>=156&&i<169){
                        JEVObj.push(data[i]);
                    }
                    if(i>=169&&i<182){
                        JEVMObj.push(data[i]);
                    }
                    if(i>=169&&i<182){
                        HepAObj.push(data[i]);
                    }
                    if(i>=195&&i<208){
                        HepAMObj.push(data[i]);
                    }
                    if(i>=208&&i<221){
                        VarVObj.push(data[i]);
                    }
                    if(i>=221&&i<234){
                        HibObj.push(data[i]);
                    }
                    if(i>=234&&i<247){
                        ORVObj.push(data[i]);
                    }
                }
                var datas = [];
                datas.push(HepBObj) ;
                datas.push(BCGObj);
                datas.push(IPVObj);
                datas.push(OPVObj);
                datas.push(DTPObj);
                datas.push(DTObj);
                datas.push(MRObj);
                datas.push(MMRObj);
                datas.push(MMObj);
                datas.push(MVObj);
                datas.push(MPVObj);
                datas.push(MPVplusObj);
                datas.push(JEVObj);
                datas.push(JEVMObj);
                datas.push(HepAObj);
                datas.push(HepAMObj);
                datas.push(VarVObj);
                datas.push(HibObj);
                datas.push(ORVObj);
                var datass = JSON.stringify(datas);

                $.ajax({
                    type: "post",
                    url: '../SetPrintForm/ChildInoculatePrintSetSaveAs.jhtml?printType='+printType+'&xml_name='+xml_name+"&allX="+allX+"&allY="+allY,
                    dataType: "json",
                    data: {"datass":datass},
                    success: function (data) {
                        if(data.message=='true'){
                            $.ajax({
                                type: "post",
                                url: '../SetPrintForm/ChildInoculatePrintSetModelIntoTable.jhtml?model_name='+model_name+'&remark=1'+'&xml_name='+xml_name,
                                dataType: "json",
                                data: {"datass":datass},
                                success: function (data) {
                                    if(data.message=='true'){
                                        layer.msg("保存成功",{icon: 1});
                                    }else{
                                        layer.msg("保存失败",{icon: 5});
                                    }
                                    var page = $("#inoculateGrid").jqGrid('getGridParam', 'page');
                                    $("#inoculateGrid").jqGrid('setGridParam',{
                                        postData: {},
                                        page: page
                                    }).trigger("reloadGrid");

                                },
                                error: function (response, err) {
                                    layer.msg("保存失败",{icon: 5});
                                }
                            });
                            /*layer.msg("保存成功",{icon: 1});*/
                        }else{
                            layer.msg("保存失败",{icon: 5});
                        }

                    },
                    error: function (response, err) {
                        layer.msg("保存失败",{icon: 5});
                    }
                });
            }
        });

    }

/*选择全部改变值X*/
$(document).on("change",'select#allX',function(){
    var allX = Number($("#allX").val());

});
/*选择全部改变值Y*/
$(document).on("change",'select#allY',function(){

});

/*选择批量改变值*/
$(document).on("change",'select#HepBY',function(){
    $("input[name='HepBDateY']").val($("#HepBY").val());
    $("input[name='HepBSiteY']").val($("#HepBY").val());
    $("input[name='HepBNumY']").val($("#HepBY").val());
    $("input[name='HepBFacY']").val($("#HepBY").val());
    $("input[name='HepBDeptY']").val($("#HepBY").val());
    $("input[name='HepBDocY']").val($("#HepBY").val());

});
$(document).on("change",'select#BCGY',function(){
    $("input[name='BCGDateY']").val($("#BCGY").val());
    $("input[name='BCGSiteY']").val($("#BCGY").val());
    $("input[name='BCGNumY']").val($("#BCGY").val());
    $("input[name='BCGFacY']").val($("#BCGY").val());
    $("input[name='BCGDeptY']").val($("#BCGY").val());
    $("input[name='BCGDocY']").val($("#BCGY").val());

});
$(document).on("change",'select#IPVY',function(){
    $("input[name='IPVDateY']").val($("#IPVY").val());
    $("input[name='IPVSiteY']").val($("#IPVY").val());
    $("input[name='IPVNumY']").val($("#IPVY").val());
    $("input[name='IPVFacY']").val($("#IPVY").val());
    $("input[name='IPVDeptY']").val($("#IPVY").val());
    $("input[name='IPVDocY']").val($("#IPVY").val());

});
$(document).on("change",'select#OPVY',function(){
    $("input[name='OPVDateY']").val($("#OPVY").val());
    $("input[name='OPVSiteY']").val($("#OPVY").val());
    $("input[name='OPVNumY']").val($("#OPVY").val());
    $("input[name='OPVFacY']").val($("#OPVY").val());
    $("input[name='OPVDeptY']").val($("#OPVY").val());
    $("input[name='OPVDocY']").val($("#OPVY").val());

});
$(document).on("change",'select#DTPY',function(){
    $("input[name='DTPDateY']").val($("#DTPY").val());
    $("input[name='DTPSiteY']").val($("#DTPY").val());
    $("input[name='DTPNumY']").val($("#DTPY").val());
    $("input[name='DTPFacY']").val($("#DTPY").val());
    $("input[name='DTPDeptY']").val($("#DTPY").val());
    $("input[name='DTPDocY']").val($("#DTPY").val());

});
$(document).on("change",'select#DTY',function(){
    $("input[name='DTDateY']").val($("#DTY").val());
    $("input[name='DTSiteY']").val($("#DTY").val());
    $("input[name='DTNumY']").val($("#DTY").val());
    $("input[name='DTFacY']").val($("#DTY").val());
    $("input[name='DTDeptY']").val($("#DTY").val());
    $("input[name='DTDocY']").val($("#DTY").val());

});
$(document).on("change",'select#MRY',function(){
    $("input[name='MRDateY']").val($("#MRY").val());
    $("input[name='MRSiteY']").val($("#MRY").val());
    $("input[name='MRNumY']").val($("#MRY").val());
    $("input[name='MRFacY']").val($("#MRY").val());
    $("input[name='MRDeptY']").val($("#MRY").val());
    $("input[name='MRDocY']").val($("#MRY").val());

});
$(document).on("change",'select#MMRY',function(){
    $("input[name='MMRDateY']").val($("#MMRY").val());
    $("input[name='MMRSiteY']").val($("#MMRY").val());
    $("input[name='MMRNumY']").val($("#MMRY").val());
    $("input[name='MMRFacY']").val($("#MMRY").val());
    $("input[name='MMRDeptY']").val($("#MMRY").val());
    $("input[name='MMRDocY']").val($("#MMRY").val());

});
$(document).on("change",'select#MMY',function(){
    $("input[name='MMDateY']").val($("#MMY").val());
    $("input[name='MMSiteY']").val($("#MMY").val());
    $("input[name='MMNumY']").val($("#MMY").val());
    $("input[name='MMFacY']").val($("#MMY").val());
    $("input[name='MMDeptY']").val($("#MMY").val());
    $("input[name='MMDocY']").val($("#MMY").val());

});
$(document).on("change",'select#MVY',function(){
    $("input[name='MVDateY']").val($("#MVY").val());
    $("input[name='MVSiteY']").val($("#MVY").val());
    $("input[name='MVNumY']").val($("#MVY").val());
    $("input[name='MVFacY']").val($("#MVY").val());
    $("input[name='MVDeptY']").val($("#MVY").val());
    $("input[name='MVDocY']").val($("#MVY").val());

});
$(document).on("change",'select#MPVY',function(){
    $("input[name='MPVDateY']").val($("#MPVY").val());
    $("input[name='MPVSiteY']").val($("#MPVY").val());
    $("input[name='MPVNumY']").val($("#MPVY").val());
    $("input[name='MPVFacY']").val($("#MPVY").val());
    $("input[name='MPVDeptY']").val($("#MPVY").val());
    $("input[name='MPVDocY']").val($("#MPVY").val());

});
$(document).on("change",'select#MPVplusY',function(){
    $("input[name='MPVplusDateY']").val($("#MPVplusY").val());
    $("input[name='MPVplusSiteY']").val($("#MPVplusY").val());
    $("input[name='MPVplusNumY']").val($("#MPVplusY").val());
    $("input[name='MPVplusFacY']").val($("#MPVplusY").val());
    $("input[name='MPVplusDeptY']").val($("#MPVplusY").val());
    $("input[name='MPVplusDocY']").val($("#MPVplusY").val());

});
$(document).on("change",'select#JEVY',function(){
    $("input[name='JEVDateY']").val($("#JEVY").val());
    $("input[name='JEVSiteY']").val($("#JEVY").val());
    $("input[name='JEVNumY']").val($("#JEVY").val());
    $("input[name='JEVFacY']").val($("#JEVY").val());
    $("input[name='JEVDeptY']").val($("#JEVY").val());
    $("input[name='JEVDocY']").val($("#JEVY").val());

});
$(document).on("change",'select#JEVMY',function(){
    $("input[name='JEVMDateY']").val($("#JEVMY").val());
    $("input[name='JEVMSiteY']").val($("#JEVMY").val());
    $("input[name='JEVMNumY']").val($("#JEVMY").val());
    $("input[name='JEVMFacY']").val($("#JEVMY").val());
    $("input[name='JEVMDeptY']").val($("#JEVMY").val());
    $("input[name='JEVMDocY']").val($("#JEVMY").val());

});
$(document).on("change",'select#HepAY',function(){
    $("input[name='HepADateY']").val($("#HepAY").val());
    $("input[name='HepASiteY']").val($("#HepAY").val());
    $("input[name='HepANumY']").val($("#HepAY").val());
    $("input[name='HepAFacY']").val($("#HepAY").val());
    $("input[name='HepADeptY']").val($("#HepAY").val());
    $("input[name='HepADocY']").val($("#HepAY").val());

});
$(document).on("change",'select#HepAMY',function(){
    $("input[name='HepAMDateY']").val($("#HepAMY").val());
    $("input[name='HepAMSiteY']").val($("#HepAMY").val());
    $("input[name='HepAMNumY']").val($("#HepAMY").val());
    $("input[name='HepAMFacY']").val($("#HepAMY").val());
    $("input[name='HepAMDeptY']").val($("#HepAMY").val());
    $("input[name='HepAMDocY']").val($("#HepAMY").val());

});
$(document).on("change",'select#VarVY',function(){
    $("input[name='VarVDateY']").val($("#VarVY").val());
    $("input[name='VarVSiteY']").val($("#VarVY").val());
    $("input[name='VarVNumY']").val($("#VarVY").val());
    $("input[name='VarVFacY']").val($("#VarVY").val());
    $("input[name='VarVDeptY']").val($("#VarVY").val());
    $("input[name='VarVDocY']").val($("#VarVY").val());

});
$(document).on("change",'select#HibY',function(){
    $("input[name='HibDateY']").val($("#HibY").val());
    $("input[name='HibSiteY']").val($("#HibY").val());
    $("input[name='HibNumY']").val($("#HibY").val());
    $("input[name='HibFacY']").val($("#HibY").val());
    $("input[name='HibDeptY']").val($("#HibY").val());
    $("input[name='HibDocY']").val($("#HibY").val());

});
$(document).on("change",'select#ORVY',function(){
    $("input[name='ORVDateY']").val($("#ORVY").val());
    $("input[name='ORVSiteY']").val($("#ORVY").val());
    $("input[name='ORVNumY']").val($("#ORVY").val());
    $("input[name='ORVFacY']").val($("#ORVY").val());
    $("input[name='ORVDeptY']").val($("#ORVY").val());
    $("input[name='ORVDocY']").val($("#ORVY").val());

});

/*选择批量改变值*/
$(document).on("change",'select#date',function(){
    $("input[name='HepBDateX']").val($("#date").val());
    $("input[name='BCGDateX']").val($("#date").val());
    $("input[name='IPVDateX']").val($("#date").val());
    $("input[name='OPVDateX']").val($("#date").val());
    $("input[name='DTPDateX']").val($("#date").val());
    $("input[name='DTDateX']").val($("#date").val());
    $("input[name='MRDateX']").val($("#date").val());
    $("input[name='MMRDateX']").val($("#date").val());
    $("input[name='MMDateX']").val($("#date").val());
    $("input[name='MVDateX']").val($("#date").val());
    $("input[name='MPVDateX']").val($("#date").val());
    $("input[name='MPVplusDateX']").val($("#date").val());
    $("input[name='JEVDateX']").val($("#date").val());
    $("input[name='JEVMDateX']").val($("#date").val());
    $("input[name='HepADateX']").val($("#date").val());
    $("input[name='HepAMDateX']").val($("#date").val());
    $("input[name='VarVDateX']").val($("#date").val());
    $("input[name='HibDateX']").val($("#date").val());
    $("input[name='ORVDateX']").val($("#date").val());
});
$(document).on("change",'select#site',function(){
    $("input[name='HepBSiteX']").val($("#site").val());
    $("input[name='BCGSiteX']").val($("#site").val());
    $("input[name='IPVSiteX']").val($("#site").val());
    $("input[name='OPVSiteX']").val($("#site").val());
    $("input[name='DTPSiteX']").val($("#site").val());
    $("input[name='DTSiteX']").val($("#site").val());
    $("input[name='MRSiteX']").val($("#site").val());
    $("input[name='MMRSiteX']").val($("#site").val());
    $("input[name='MMSiteX']").val($("#site").val());
    $("input[name='MVSiteX']").val($("#site").val());
    $("input[name='MPVSiteX']").val($("#site").val());
    $("input[name='MPVplusSiteX']").val($("#site").val());
    $("input[name='JEVSiteX']").val($("#site").val());
    $("input[name='JEVMSiteX']").val($("#site").val());
    $("input[name='HepASiteX']").val($("#site").val());
    $("input[name='HepAMSiteX']").val($("#site").val());
    $("input[name='VarVSiteX']").val($("#site").val());
    $("input[name='HibSiteX']").val($("#site").val());
    $("input[name='ORVSiteX']").val($("#site").val());
});
$(document).on("change",'select#num',function(){
    $("input[name='HepBNumX']").val($("#num").val());
    $("input[name='BCGNumX']").val($("#num").val());
    $("input[name='IPVNumX']").val($("#num").val());
    $("input[name='OPVNumX']").val($("#num").val());
    $("input[name='DTPNumX']").val($("#num").val());
    $("input[name='DTNumX']").val($("#num").val());
    $("input[name='MRNumX']").val($("#num").val());
    $("input[name='MMRNumX']").val($("#num").val());
    $("input[name='MMNumX']").val($("#num").val());
    $("input[name='MVNumX']").val($("#num").val());
    $("input[name='MPVNumX']").val($("#num").val());
    $("input[name='MPVplusNumX']").val($("#num").val());
    $("input[name='JEVNumX']").val($("#num").val());
    $("input[name='JEVMNumX']").val($("#num").val());
    $("input[name='HepANumX']").val($("#num").val());
    $("input[name='HepAMNumX']").val($("#num").val());
    $("input[name='VarVNumX']").val($("#num").val());
    $("input[name='HibNumX']").val($("#num").val());
    $("input[name='ORVNumX']").val($("#num").val());
});
$(document).on("change",'select#fac',function(){
    $("input[name='HepBFacX']").val($("#fac").val());
    $("input[name='BCGFacX']").val($("#fac").val());
    $("input[name='IPVFacX']").val($("#fac").val());
    $("input[name='OPVFacX']").val($("#fac").val());
    $("input[name='DTPFacX']").val($("#fac").val());
    $("input[name='DTFacX']").val($("#fac").val());
    $("input[name='MRFacX']").val($("#fac").val());
    $("input[name='MMRFacX']").val($("#fac").val());
    $("input[name='MMFacX']").val($("#fac").val());
    $("input[name='MVFacX']").val($("#fac").val());
    $("input[name='MPVFacX']").val($("#fac").val());
    $("input[name='MPVplusFacX']").val($("#fac").val());
    $("input[name='JEVFacX']").val($("#fac").val());
    $("input[name='JEVMFacX']").val($("#fac").val());
    $("input[name='HepAFacX']").val($("#fac").val());
    $("input[name='HepAMFacX']").val($("#fac").val());
    $("input[name='VarVFacX']").val($("#fac").val());
    $("input[name='HibFacX']").val($("#fac").val());
    $("input[name='ORVFacX']").val($("#fac").val());
});
$(document).on("change",'select#dept',function(){
    $("input[name='HepBDeptX']").val($("#dept").val());
    $("input[name='BCGDeptX']").val($("#dept").val());
    $("input[name='IPVDeptX']").val($("#dept").val());
    $("input[name='OPVDeptX']").val($("#dept").val());
    $("input[name='DTPDeptX']").val($("#dept").val());
    $("input[name='DTDeptX']").val($("#dept").val());
    $("input[name='MRDeptX']").val($("#dept").val());
    $("input[name='MMRDeptX']").val($("#dept").val());
    $("input[name='MMDeptX']").val($("#dept").val());
    $("input[name='MVDeptX']").val($("#dept").val());
    $("input[name='MPVDeptX']").val($("#dept").val());
    $("input[name='MPVplusDeptX']").val($("#dept").val());
    $("input[name='JEVDeptX']").val($("#dept").val());
    $("input[name='JEVMDeptX']").val($("#dept").val());
    $("input[name='HepADeptX']").val($("#dept").val());
    $("input[name='HepAMDeptX']").val($("#dept").val());
    $("input[name='VarVDeptX']").val($("#dept").val());
    $("input[name='HibDeptX']").val($("#dept").val());
    $("input[name='ORVDeptX']").val($("#dept").val());
});
$(document).on("change",'select#doc',function(){
    $("input[name='HepBDocX']").val($("#doc").val());
    $("input[name='BCGDocX']").val($("#doc").val());
    $("input[name='IPVDocX']").val($("#doc").val());
    $("input[name='OPVDocX']").val($("#doc").val());
    $("input[name='DTPDocX']").val($("#doc").val());
    $("input[name='DTDocX']").val($("#doc").val());
    $("input[name='MRDocX']").val($("#doc").val());
    $("input[name='MMRDocX']").val($("#doc").val());
    $("input[name='MMDocX']").val($("#doc").val());
    $("input[name='MVDocX']").val($("#doc").val());
    $("input[name='MPVDocX']").val($("#doc").val());
    $("input[name='MPVplusDocX']").val($("#doc").val());
    $("input[name='JEVDocX']").val($("#doc").val());
    $("input[name='JEVMDocX']").val($("#doc").val());
    $("input[name='HepADocX']").val($("#doc").val());
    $("input[name='HepAMDocX']").val($("#doc").val());
    $("input[name='VarVDocX']").val($("#doc").val());
    $("input[name='HibDocX']").val($("#doc").val());
    $("input[name='ORVDocX']").val($("#doc").val());
});

var vmInoculation = new Vue({
    el:'#tab1',
    data:{
        showList: true,
        title: null,
        tMgrStore: {posId:null},
        items:[]
    },
    methods: {
        query: function () {
            vmInoculation.reload();
        },
        loadData:function(){
            var param = new Array();//定义数组
            //下拉选框
            $.ajax({
                // get请求地址
                url: '../tmgrstore/getTowerList',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result=data.page;
                    $.each(result, function (index, item) {
                        param.push({"text":item.towerName,"value":item.id});
                    });
                    vmInoculation.items = param;
                }
            });
        },
        add: function(){
            vmInoculation.showList = false;
            vmInoculation.title = "新增";
            vmInoculation.tMgrStore = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vmInoculation.showList = false;
            vmInoculation.title = "修改";

            vmInoculation.getInfo(id)
        },
        del: function (event) {
            var ids = getSelectedRows("inoculateGrid");
            //var rowData = $("#inoculateGrid").jqGrid('getRowData',rowid);
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../tchildprintmodel/delete",
                    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#inoculateGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            $("#storeForm").bootstrapValidator('validate');//提交验证
            if ($("#storeForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                var url = vm.tMgrStore.id == null ? "../tmgrstore/save" : "../tmgrstore/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tMgrStore),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }
        },

        getInfo: function(id){
            $.get("../tmgrstore/info/"+id, function(r){
                vm.tMgrStore = r.tMgrStore;

            });
        },
        reload: function (event) {
            vmInoculation.showList = true;
            var page = $("#inoculateGrid").jqGrid('getGridParam','page');
            $("#inoculateGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        }
    }
});