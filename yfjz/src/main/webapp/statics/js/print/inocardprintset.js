
$(function(){
    init();
    })
function init() {
    $.ajax({
        type:"post",
        url:"../SetPrintForm/selectInoculatePrintSet.jhtml?printType=2",
        success:function (data) {

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
                    $("input[name='HepBExpX']").val(item.expiration_dateX);
                    $("input[name='HepBExpY']").val(item.expiration_dateY);
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
                    $("input[name='BCGExpX']").val(item.expiration_dateX);
                    $("input[name='BCGExpY']").val(item.expiration_dateY);
                    $("input[name='BCGDocX']").val(item.doctorX);
                    $("input[name='BCGDocY']").val(item.doctorY);


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
                    $("input[name='OPVExpX']").val(item.expiration_dateX);
                    $("input[name='OPVExpY']").val(item.expiration_dateY);
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
                    $("input[name='DTPExpX']").val(item.expiration_dateX);
                    $("input[name='DTPExpY']").val(item.expiration_dateY);
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
                    $("input[name='DTExpX']").val(item.expiration_dateX);
                    $("input[name='DTExpY']").val(item.expiration_dateY);
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
                    $("input[name='MRExpX']").val(item.expiration_dateX);
                    $("input[name='MRExpY']").val(item.expiration_dateY);
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
                    $("input[name='MMRExpX']").val(item.expiration_dateX);
                    $("input[name='MMRExpY']").val(item.expiration_dateY);
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
                    $("input[name='MMExpX']").val(item.expiration_dateX);
                    $("input[name='MMExpY']").val(item.expiration_dateY);
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
                    $("input[name='MVExpX']").val(item.expiration_dateX);
                    $("input[name='MVExpY']").val(item.expiration_dateY);
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
                    $("input[name='MPVExpX']").val(item.expiration_dateX);
                    $("input[name='MPVExpY']").val(item.expiration_dateY);
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
                    $("input[name='MPVplusExpX']").val(item.expiration_dateX);
                    $("input[name='MPVplusExpY']").val(item.expiration_dateY);
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
                    $("input[name='JEVExpX']").val(item.expiration_dateX);
                    $("input[name='JEVExpY']").val(item.expiration_dateY);
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
                    $("input[name='JEVMExpX']").val(item.expiration_dateX);
                    $("input[name='JEVMExpY']").val(item.expiration_dateY);
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
                    $("input[name='HepAExpX']").val(item.expiration_dateX);
                    $("input[name='HepAExpY']").val(item.expiration_dateY);
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
                    $("input[name='HepAMExpX']").val(item.expiration_dateX);
                    $("input[name='HepAMExpY']").val(item.expiration_dateY);
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
                    $("input[name='VarVExpX']").val(item.expiration_dateX);
                    $("input[name='VarVExpY']").val(item.expiration_dateY);
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
                    $("input[name='HibExpX']").val(item.expiration_dateX);
                    $("input[name='HibExpY']").val(item.expiration_dateY);
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
                    $("input[name='ORVExpX']").val(item.expiration_dateX);
                    $("input[name='ORVExpY']").val(item.expiration_dateY);
                    $("input[name='ORVDocX']").val(item.doctorX);
                    $("input[name='ORVDocY']").val(item.doctorY);
                }

                console.info(item.batchnumX);
            })
            //console.info(obj);
        }
    });
}
    function saveSet() {
        var allX = $("#allX").val();
        var allY = $("#allY").val();
        var printType = '2';
        var data= $("#ff").serializeArray();
        var HepBObj = [];
        var BCGObj = [];
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
                OPVObj.push(data[i]);
            }
            if(i>=39&&i<52){
                DTPObj.push(data[i]);
            }
            if(i>=52&&i<65){
                DTObj.push(data[i]);
            }
            if(i>=65&&i<78){
                MRObj.push(data[i]);
            }
            if(i>=78&&i<91){
                MMRObj.push(data[i]);
            }
            if(i>=91&&i<104){
                MMObj.push(data[i]);
            }
            if(i>=104&&i<117){
                MVObj.push(data[i]);
            }
            if(i>=117&&i<130){
                MPVObj.push(data[i]);
            }
            if(i>=130&&i<143){
                MPVplusObj.push(data[i]);
            }
            if(i>=143&&i<156){
                JEVObj.push(data[i]);
            }
            if(i>=156&&i<169){
                JEVMObj.push(data[i]);
            }
            if(i>=169&&i<182){
                HepAObj.push(data[i]);
            }
            if(i>=182&&i<195){
                HepAMObj.push(data[i]);
            }
            if(i>=195&&i<208){
                VarVObj.push(data[i]);
            }
            if(i>=208&&i<221){
                HibObj.push(data[i]);
            }
            if(i>=221&&i<234){
                ORVObj.push(data[i]);
            }
        }
        var datas = [];
        datas.push(HepBObj) ;
        datas.push(BCGObj);
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

    function saveSetAs() {
        layer.prompt({title:'另存为模板'},function(model_name,index) {
            if (model_name == null || model_name.trim() == "") {
                layer.msg("模板名不能为空！");
                return;
            }
            layer.close(index);
            if (model_name != null && model_name.trim() != "") {
                var printType = '2';
                var date = new Date();
                var xml_name = "selfDefinePrintModel"+date.getSeconds();
                var data = $("#ff").serializeArray();
                var HepBObj = [];
                var BCGObj = [];
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
                for (var i = 0; i < data.length; i++) {
                    if (i < 13) {
                        HepBObj.push(data[i]);
                    }
                    if (i >= 13 && i < 26) {
                        BCGObj.push(data[i]);
                    }
                    if (i >= 26 && i < 39) {
                        OPVObj.push(data[i]);
                    }
                    if (i >= 39 && i < 52) {
                        DTPObj.push(data[i]);
                    }
                    if (i >= 52 && i < 65) {
                        DTObj.push(data[i]);
                    }
                    if (i >= 65 && i < 78) {
                        MRObj.push(data[i]);
                    }
                    if (i >= 78 && i < 91) {
                        MMRObj.push(data[i]);
                    }
                    if (i >= 91 && i < 104) {
                        MMObj.push(data[i]);
                    }
                    if (i >= 104 && i < 117) {
                        MVObj.push(data[i]);
                    }
                    if (i >= 117 && i < 130) {
                        MPVObj.push(data[i]);
                    }
                    if (i >= 130 && i < 143) {
                        MPVplusObj.push(data[i]);
                    }
                    if (i >= 143 && i < 156) {
                        JEVObj.push(data[i]);
                    }
                    if (i >= 156 && i < 169) {
                        JEVMObj.push(data[i]);
                    }
                    if (i >= 169 && i < 182) {
                        HepAObj.push(data[i]);
                    }
                    if (i >= 182 && i < 195) {
                        HepAMObj.push(data[i]);
                    }
                    if (i >= 195 && i < 208) {
                        VarVObj.push(data[i]);
                    }
                    if (i >= 208 && i < 221) {
                        HibObj.push(data[i]);
                    }
                    if (i >= 221 && i < 234) {
                        ORVObj.push(data[i]);
                    }
                }
                var datas = [];
                datas.push(HepBObj);
                datas.push(BCGObj);
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
                    url:  '../SetPrintForm/ChildInoculatePrintSetSaveAs.jhtml?printType=' + printType+'&xml_name='+xml_name,
                    dataType: "json",
                    data: {"datass": datass},
                    success: function (data) {
                        if (data.message == 'true') {
                            $.ajax({
                                type: "post",
                                url:  '../SetPrintForm/ChildInoculatePrintSetModelIntoTable.jhtml?model_name='+model_name+'&remark=' + printType+'&xml_name='+xml_name,
                                dataType: "json",
                                data: {"datass": datass},
                                success: function (data) {
                                    if (data.message == 'true') {
                                        layer.msg("保存成功", {icon: 1});
                                    } else {
                                        layer.msg("保存失败", {icon: 5});
                                    }

                                },
                                error: function (response, err) {
                                    layer.msg("保存失败", {icon: 5});
                                }
                            });
                            /*layer.msg("保存成功",{icon: 1});*/
                        } else {
                            layer.msg("保存失败", {icon: 5});
                        }

                    },
                    error: function (response, err) {
                        layer.msg("保存失败", {icon: 5});
                    }

                });
            }
        })
    }

    function saveSetIntoTable(){
        var printType = '2';
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

    /*$(".date").change(function(){
     alert("fgd");
     });*/
/*选择批量改变值*/
$(document).on("change",'select#HepBY',function(){
    $("input[name='HepBDateY']").val($("#HepBY").val());
    $("input[name='HepBSiteY']").val($("#HepBY").val());
    $("input[name='HepBNumY']").val($("#HepBY").val());
    $("input[name='HepBFacY']").val($("#HepBY").val());
    $("input[name='HepBExpY']").val($("#HepBY").val());
    $("input[name='HepBDocY']").val($("#HepBY").val());

});
$(document).on("change",'select#BCGY',function(){
    $("input[name='BCGDateY']").val($("#BCGY").val());
    $("input[name='BCGSiteY']").val($("#BCGY").val());
    $("input[name='BCGNumY']").val($("#BCGY").val());
    $("input[name='BCGFacY']").val($("#BCGY").val());
    $("input[name='BCGExpY']").val($("#BCGY").val());
    $("input[name='BCGDocY']").val($("#BCGY").val());

});
$(document).on("change",'select#IPVY',function(){
    $("input[name='IPVDateY']").val($("#IPVY").val());
    $("input[name='IPVSiteY']").val($("#IPVY").val());
    $("input[name='IPVNumY']").val($("#IPVY").val());
    $("input[name='IPVFacY']").val($("#IPVY").val());
    $("input[name='IPVExpY']").val($("#IPVY").val());
    $("input[name='IPVDocY']").val($("#IPVY").val());

});
$(document).on("change",'select#OPVY',function(){
    $("input[name='OPVDateY']").val($("#OPVY").val());
    $("input[name='OPVSiteY']").val($("#OPVY").val());
    $("input[name='OPVNumY']").val($("#OPVY").val());
    $("input[name='OPVFacY']").val($("#OPVY").val());
    $("input[name='OPVExpY']").val($("#OPVY").val());
    $("input[name='OPVDocY']").val($("#OPVY").val());

});
$(document).on("change",'select#DTPY',function(){
    $("input[name='DTPDateY']").val($("#DTPY").val());
    $("input[name='DTPSiteY']").val($("#DTPY").val());
    $("input[name='DTPNumY']").val($("#DTPY").val());
    $("input[name='DTPFacY']").val($("#DTPY").val());
    $("input[name='DTPExpY']").val($("#DTPY").val());
    $("input[name='DTPDocY']").val($("#DTPY").val());

});
$(document).on("change",'select#DTY',function(){
    $("input[name='DTDateY']").val($("#DTY").val());
    $("input[name='DTSiteY']").val($("#DTY").val());
    $("input[name='DTNumY']").val($("#DTY").val());
    $("input[name='DTFacY']").val($("#DTY").val());
    $("input[name='DTExpY']").val($("#DTY").val());
    $("input[name='DTDocY']").val($("#DTY").val());

});
$(document).on("change",'select#MRY',function(){
    $("input[name='MRDateY']").val($("#MRY").val());
    $("input[name='MRSiteY']").val($("#MRY").val());
    $("input[name='MRNumY']").val($("#MRY").val());
    $("input[name='MRFacY']").val($("#MRY").val());
    $("input[name='MRExpY']").val($("#MRY").val());
    $("input[name='MRDocY']").val($("#MRY").val());

});
$(document).on("change",'select#MMRY',function(){
    $("input[name='MMRDateY']").val($("#MMRY").val());
    $("input[name='MMRSiteY']").val($("#MMRY").val());
    $("input[name='MMRNumY']").val($("#MMRY").val());
    $("input[name='MMRFacY']").val($("#MMRY").val());
    $("input[name='MMRExpY']").val($("#MMRY").val());
    $("input[name='MMRDocY']").val($("#MMRY").val());

});
$(document).on("change",'select#MMY',function(){
    $("input[name='MMDateY']").val($("#MMY").val());
    $("input[name='MMSiteY']").val($("#MMY").val());
    $("input[name='MMNumY']").val($("#MMY").val());
    $("input[name='MMFacY']").val($("#MMY").val());
    $("input[name='MMExpY']").val($("#MMY").val());
    $("input[name='MMDocY']").val($("#MMY").val());

});
$(document).on("change",'select#MVY',function(){
    $("input[name='MVDateY']").val($("#MVY").val());
    $("input[name='MVSiteY']").val($("#MVY").val());
    $("input[name='MVNumY']").val($("#MVY").val());
    $("input[name='MVFacY']").val($("#MVY").val());
    $("input[name='MVExpY']").val($("#MVY").val());
    $("input[name='MVDocY']").val($("#MVY").val());

});
$(document).on("change",'select#MPVY',function(){
    $("input[name='MPVDateY']").val($("#MPVY").val());
    $("input[name='MPVSiteY']").val($("#MPVY").val());
    $("input[name='MPVNumY']").val($("#MPVY").val());
    $("input[name='MPVFacY']").val($("#MPVY").val());
    $("input[name='MPVExpY']").val($("#MPVY").val());
    $("input[name='MPVDocY']").val($("#MPVY").val());

});
$(document).on("change",'select#MPVplusY',function(){
    $("input[name='MPVplusDateY']").val($("#MPVplusY").val());
    $("input[name='MPVplusSiteY']").val($("#MPVplusY").val());
    $("input[name='MPVplusNumY']").val($("#MPVplusY").val());
    $("input[name='MPVplusFacY']").val($("#MPVplusY").val());
    $("input[name='MPVplusExpY']").val($("#MPVplusY").val());
    $("input[name='MPVplusDocY']").val($("#MPVplusY").val());

});
$(document).on("change",'select#JEVY',function(){
    $("input[name='JEVDateY']").val($("#JEVY").val());
    $("input[name='JEVSiteY']").val($("#JEVY").val());
    $("input[name='JEVNumY']").val($("#JEVY").val());
    $("input[name='JEVFacY']").val($("#JEVY").val());
    $("input[name='JEVExpY']").val($("#JEVY").val());
    $("input[name='JEVDocY']").val($("#JEVY").val());

});
$(document).on("change",'select#JEVMY',function(){
    $("input[name='JEVMDateY']").val($("#JEVMY").val());
    $("input[name='JEVMSiteY']").val($("#JEVMY").val());
    $("input[name='JEVMNumY']").val($("#JEVMY").val());
    $("input[name='JEVMFacY']").val($("#JEVMY").val());
    $("input[name='JEVMExpY']").val($("#JEVMY").val());
    $("input[name='JEVMDocY']").val($("#JEVMY").val());

});
$(document).on("change",'select#HepAY',function(){
    $("input[name='HepADateY']").val($("#HepAY").val());
    $("input[name='HepASiteY']").val($("#HepAY").val());
    $("input[name='HepANumY']").val($("#HepAY").val());
    $("input[name='HepAFacY']").val($("#HepAY").val());
    $("input[name='HepAExpY']").val($("#HepAY").val());
    $("input[name='HepADocY']").val($("#HepAY").val());

});
$(document).on("change",'select#HepAMY',function(){
    $("input[name='HepAMDateY']").val($("#HepAMY").val());
    $("input[name='HepAMSiteY']").val($("#HepAMY").val());
    $("input[name='HepAMNumY']").val($("#HepAMY").val());
    $("input[name='HepAMFacY']").val($("#HepAMY").val());
    $("input[name='HepAMExpY']").val($("#HepAMY").val());
    $("input[name='HepAMDocY']").val($("#HepAMY").val());

});
$(document).on("change",'select#VarVY',function(){
    $("input[name='VarVDateY']").val($("#VarVY").val());
    $("input[name='VarVSiteY']").val($("#VarVY").val());
    $("input[name='VarVNumY']").val($("#VarVY").val());
    $("input[name='VarVFacY']").val($("#VarVY").val());
    $("input[name='VarVExpY']").val($("#VarVY").val());
    $("input[name='VarVDocY']").val($("#VarVY").val());

});
$(document).on("change",'select#HibY',function(){
    $("input[name='HibDateY']").val($("#HibY").val());
    $("input[name='HibSiteY']").val($("#HibY").val());
    $("input[name='HibNumY']").val($("#HibY").val());
    $("input[name='HibFacY']").val($("#HibY").val());
    $("input[name='HibExpY']").val($("#HibY").val());
    $("input[name='HibDocY']").val($("#HibY").val());

});
$(document).on("change",'select#ORVY',function(){
    $("input[name='ORVDateY']").val($("#ORVY").val());
    $("input[name='ORVSiteY']").val($("#ORVY").val());
    $("input[name='ORVNumY']").val($("#ORVY").val());
    $("input[name='ORVFacY']").val($("#ORVY").val());
    $("input[name='ORVExpY']").val($("#ORVY").val());
    $("input[name='ORVDocY']").val($("#ORVY").val());

});

    $(document).on("change",'select#date',function(){
        $("input[name='HepBDateX']").val($("#date").val());
        $("input[name='BCGDateX']").val($("#date").val());
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
    $(document).on("change",'select#Exp',function(){
        $("input[name='HepBExpX']").val($("#Exp").val());
        $("input[name='BCGExpX']").val($("#Exp").val());
        $("input[name='OPVExpX']").val($("#Exp").val());
        $("input[name='DTPExpX']").val($("#Exp").val());
        $("input[name='DTExpX']").val($("#Exp").val());
        $("input[name='MRExpX']").val($("#Exp").val());
        $("input[name='MMRExpX']").val($("#Exp").val());
        $("input[name='MMExpX']").val($("#Exp").val());
        $("input[name='MVExpX']").val($("#Exp").val());
        $("input[name='MPVExpX']").val($("#Exp").val());
        $("input[name='MPVplusExpX']").val($("#Exp").val());
        $("input[name='JEVExpX']").val($("#Exp").val());
        $("input[name='JEVMExpX']").val($("#Exp").val());
        $("input[name='HepAExpX']").val($("#Exp").val());
        $("input[name='HepAMExpX']").val($("#Exp").val());
        $("input[name='VarVExpX']").val($("#Exp").val());
        $("input[name='HibExpX']").val($("#Exp").val());
        $("input[name='ORVExpX']").val($("#Exp").val());
    });
    $(document).on("change",'select#doc',function(){
        $("input[name='HepBDocX']").val($("#doc").val());
        $("input[name='BCGDocX']").val($("#doc").val());
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