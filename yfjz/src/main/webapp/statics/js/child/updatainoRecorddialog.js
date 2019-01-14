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

var id = getUrlVars()["id"];
var chilCode = getUrlVars()["chilCode"];
var inocDepaCode = decodeURI(getUrlVars()["inocDepaCode"]);
var type = decodeURI(getUrlVars()["type"]);
var updateVue = new Vue({
    el: '#updateInosss',
    data: {
        ino: {
            id: null,
            inocBactCode: null,
            inocBatchno: null,
            inocTime: null,
            inocProperty: null,
            inocDate: null,
            inocInplId: null,
            inocDepaCode: null,
            inocModifyCode: null,
            inocCorpCode: null,
            inocFree: null,
            remark: null,
            chilCode: null,
            inocNurse: null
        },
        inocBactCode: new Array(),
        inocCorpCode: new Array()
    },
});

$(function () {

    $.ajax({
        type:"post",
        url:"../tchildinoculate/info/"+id,
        dataType: 'json',
        success:function (data) {
            if (data.tChildInoculate !=null){
                updateVue.ino = data.tChildInoculate;
                /*if(orgId!=data.tChildInoculate.inocDepaCode && data.tChildInoculate.type!=3){
                    alert("不能修改非本点接种记录");
                    return;
                }*/
                console.log( updateVue.ino);
                updateVue.ino.inocModifyCode = orgName;


                $('#inocBactCode').selectpicker();
                $('#inocCorpCode').selectpicker();
                $('#inocDepaCode').selectpicker();
                $('#inocBactCode').selectpicker('val',(updateVue.ino.inocBactCode));
                $('#inocCorpCode').selectpicker('val',(updateVue.ino.inocCorpCode));
                $('#inocDepaCode').selectpicker('val',(updateVue.ino.inocDepaCode));
                console.log(updateVue.ino.inocDepaCode);
                if (updateVue.ino.inocDepaCode.length>=8){//有接种单位的不能修改
                    $('#inocDepaCode').attr("disabled","disabled");
                }
            }
        }
    });
})
//保存
function saveInott(){
    var remarks = $("#updatasremark").val();
    if(remarks.trim().length<2){
        alert("请输入修改原因");
        return;
    }
    var date = updateVue.ino.inocDate;
    if(date.length==8){
        date = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" 00:00:00";
        Vue.set(updateVue.ino, 'inocDate', date);
    }
    if(date.length==10){
        date = date+" 00:00:00";
        Vue.set(updateVue.ino, 'inocDate', date);
    }

    Vue.set(updateVue.ino, 'remark', remarks);
    Vue.set(updateVue.ino, 'inocModifyCode', orgId);
    Vue.set(updateVue.ino, 'chilCode', chilCode);
    Vue.set(updateVue.ino, 'inocBactCode', $("#inocBactCode").val());
    Vue.set(updateVue.ino, 'inocCorpCode', $("#inocCorpCode").val());
    Vue.set(updateVue.ino, 'inocDepaCode', $("#inocDepaCode").val());

    /* layer.confirm('保存后上传，确定要保存么？', function(index) {*/
    layer.msg('正在保存...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 100000});
    $.ajax({
        type: "POST",
        url: "../tchildinoculate/saveAsBackUp",
        data: JSON.stringify(updateVue.ino),
        contentType: 'application/json;charset=UTF-8',
        success: function (r) {
            if (r.code === 0) {
                layer.msg("保存成功");
                if (type!=1){
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    console.log(index);
                    parent.layer.close(index);  // 关闭layer
                    console.log(chilCode);
                    window.parent.location.reload()
                }else{
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);  // 关闭layer
                    parent.$("#historyRecord").trigger("reloadGrid");
                }
                // parent.parent.parent.$("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + chilCode);
                return;
            } else {
                layer.msg(r.msg);
            }
        }
    });
}


