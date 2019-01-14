
function loadUserInfo() {

    $.ajax({
        type: "get",
        url: "getLoginUserInfo",
        success: function (data) {
            var orgName=data.user.orgName;
            var orgId=data.user.orgId;
            var register=data.user.registerTowerName;
            var inoculate=data.user.inoculateTowerName;
            var child=data.user.childProtectionTowerName;
            var pre=data.user.preCheckName;
            $("#unitName").html(isEmpty(orgName)?"":orgName);
            $("#orgid").html(isEmpty(orgId)?"":orgId);
            $("#versionName").html(isEmpty(data.versionName)?"":data.versionName);
            var towers="";
            if(!isEmpty(register)){
                towers=towers+register+",";
            }
            if(!isEmpty(inoculate)){
                towers=towers+inoculate+",";
            }
            if(!isEmpty(child)){
                towers=towers+child+",";
            }
            if(!isEmpty(pre)){
                towers=towers+pre+",";
            }
            towers=  towers.substring(0,towers.length-1);
            $("#pos").html(towers);

        }
    })
}
function isEmpty(str) {
    if(str==null||str==undefined||str==""){
        return true;
    }
    return false;
}