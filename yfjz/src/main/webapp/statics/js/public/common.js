layui.define(['jquery'], function(exports){ 
    var $ = layui.jquery;

    function merge(json1, json2) {
    var resultJsonObject={};
    for(var attr in json1){
        resultJsonObject[attr]=json1[attr];
    }
    for(var attr in json2){
        resultJsonObject[attr]=json2[attr];
    }
        return resultJsonObject;
    };
    var obj = {
        jsonToParams: function(json) {
            var str = '';
            for(var key in json) {
                str = str +key+'='+json[key] + "&"
            }
            return str.substr(0,str.length-1);
        },
        getJsonLen: function(json) {
            var i = 0;
            for(var key in json) {
                i++;
            }
            return i;
        },
        ajaxPost: function (url,data,successCallBack,errorCallBack) {
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                data: data,
                success: function(res) {
                    successCallBack && successCallBack(res);
                },
                error: function(res) {
                    errorCallBack && errorCallBack(res);
                }
            });
        },
        ajaxGet: function (url,data,successCallBack,errorCallBack) {
            var params = this.jsonToParams(data);
            $.ajax({
                url: url + '?' + params,
                type: 'get',
                dataType: 'json',
                data: data,
                success: function(res) {
                    successCallBack && successCallBack(res);
                },
                error: function(res) {
                    errorCallBack && errorCallBack(res);
                }
            });
        },
        getJSON: function(url,callback) {
            $.getJSON(url,function(res) {
                callback && callback(res);
            })
        }
    };
    //输出接口
    exports('common', obj);
});