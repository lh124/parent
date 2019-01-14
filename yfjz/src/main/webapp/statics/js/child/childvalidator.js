function childvalidator(){
    $('#chilForm').bootstrapValidator({
        live: 'disabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            chilName: {
                validators: {
                    notEmpty: {
                        message: '儿童姓名不能为空'
                    }
                }
            },
            chilAddress: {
                validators: {
                    notEmpty: {
                        message: '现居地址不能为空'
                    }
                }
            },
            chilHabiaddress: {
                validators: {
                    notEmpty: {
                        message: '户籍地址不能为空'
                    }
                }
            },
            chilSex: {
                validators: {
                    notEmpty: {
                        message: '性别不能为空'
                    }
                }
            },
            /*chilBirthhospitalname: {
                validators: {
                    notEmpty: {
                        message: '出生医院不能为空'
                    }
                }
            },*/
            chilBirthday: {
                validators: {
                    notEmpty: {
                        message: '出生日期不能为空'
                    },
                    // date: {//验证指定的日期格式
                    //     format: 'YYYY-MM-DD hh:ii:ss',
                    //     message:'日期格式不对'
                    // }

                }
            },
            chilBirthweight: {
                validators: {
                    /*notEmpty: {
                        message: '出生重量不能为空'
                    },*/
                    regexp: {
                        regexp: /^[0-9|.]+$/,
                        message: '出生重量只能为数字！'
                    }

                }
            },
            chilCommittee: {
                validators: {
                    notEmpty: {
                        message: '行政村不能为空'
                    }
                }
            },
            chilHere: {
                validators: {
                    notEmpty: {
                        message: '在册情况不能为空'
                    }
                }
            },
            chilResidence : {
                validators: {
                    notEmpty: {
                        message: '居住属性不能为空'
                    }
                }
            },
            chilAccount: {
                validators: {
                    notEmpty: {
                        message: '户籍属性不能为空'
                    }
                }
            },
            chilMother : {
                validators: {
                    notEmpty: {
                        message: '母亲姓名不能为空'
                    }
                }
            },
            chilTel: {
                validators: {
                    // notEmpty: {
                    //     message: '家庭电话不能为空'
                    // },
                    // stringLength: {//检测长度
                    //     min: 7,
                    //     max: 13,
                    //     message: '请输入正确的座机号码或手机号码'
                    // },
                    regexp: {
                        regexp: /^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|"")$/,
                        message: '号码格式错误,座机需加区号和符号‘-’'
                    },
                    // callback: {
                    //     /*自定义，可以在这里与其他输入项联动校验*/
                    //     message: '输入的家庭号码无效，请输入手机号码或座机号码，座机号码以 - 区分！',
                    //     callback: function (value, validator, $field) {
                    //         // var pattern = /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|^1[345678]\d{9}$/;
                    //         var pattern = /^((0\d{2,3}-\d{7,8})|(1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8})|无)$/;
                    //         if (pattern.test(value)) {
                    //             return true;
                    //         }
                    //         else {
                    //             return false;
                    //         }
                    //     }
                    // }
                    /*phone:{
                        message:'电话号码格式不对'
                    }*/
                }
            },
            chilMobile: {
                validators: {
                    regexp: {
                        regexp: /^1[34578]\d{9}$/,
                        message: '请输入正确的手机号码！'
                    },
                    stringLength: {//检测长度
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    }
                    /*phone:{
                        message:'电话号码格式不对'
                    }*/
                }
            }
           /* chilNatiId: {
                validators: {
                    notEmpty: {
                        message: '民族不能为空'
                    }
                }
            }*/,
            chilMotherno: {
                validators: {
                   /* stringLength: {
                        min: 18,
                        max: 18,
                        message: '请输入18位有效身份证号码'
                    },*/
                    regexp: {
                        regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
                        message: '身份证号码格式不正确，为15位和18位身份证号码！'
                    },
                    callback: {
                        /*自定义，可以在这里与其他输入项联动校验*/
                        message: '身份证号码无效！',
                        callback: function (value, validator, $field) {
                            if(value.length==0){
                                return true;
                            }
                            //15位和18位身份证号码的正则表达式
                            var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
                            //如果通过该验证，说明身份证格式正确，但准确性还需计算
                            var idCard = value;
                            if (regIdCard.test(idCard)) {
                                if (idCard.length == 18) {
                                    var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                                    var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                                    var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                                    for (var i = 0; i < 17; i++) {
                                        idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
                                    }
                                    var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                                    var idCardLast = idCard.substring(17);//得到最后一位身份证号码
                                    //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                                    if (idCardMod == 2) {
                                        if (idCardLast == "X" || idCardLast == "x") {
                                            return true;
                                            //alert("恭喜通过验证啦！");
                                        } else {
                                            return false;
                                            //alert("身份证号码错误！");
                                        }
                                    } else {
                                        //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                                        if (idCardLast == idCardY[idCardMod]) {
                                            //alert("恭喜通过验证啦！");
                                            return true;
                                        } else {
                                            return false;
                                            //alert("身份证号码错误！");
                                        }
                                    }
                                }
                            } else {
                                //alert("身份证格式不正确!");
                                return false;
                            }
                        }
                    }
                }
            },
            chilFatherno: {
                validators: {
                   /* stringLength: {
                        min: 18,
                        max: 18,
                        message: '请输入18位有效身份证号码'
                    },*/
                    regexp: {
                        regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
                        message: '身份证号码格式不正确，为15位和18位身份证号码！'
                    },
                    callback: {
                        /*自定义，可以在这里与其他输入项联动校验*/
                        message: '身份证号码无效！',
                        callback: function (value, validator, $field) {
                            if(value.length==0){
                                return true;
                            }
                            //15位和18位身份证号码的正则表达式
                            var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
                            //如果通过该验证，说明身份证格式正确，但准确性还需计算
                            var idCard = value;
                            if (regIdCard.test(idCard)) {
                                if (idCard.length == 18) {
                                    var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                                    var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                                    var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                                    for (var i = 0; i < 17; i++) {
                                        idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
                                    }
                                    var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                                    var idCardLast = idCard.substring(17);//得到最后一位身份证号码
                                    //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                                    if (idCardMod == 2) {
                                        if (idCardLast == "X" || idCardLast == "x") {
                                            return true;
                                            //alert("恭喜通过验证啦！");
                                        } else {
                                            return false;
                                            //alert("身份证号码错误！");
                                        }
                                    } else {
                                        //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                                        if (idCardLast == idCardY[idCardMod]) {
                                            //alert("恭喜通过验证啦！");
                                            return true;
                                        } else {
                                            return false;
                                            //alert("身份证号码错误！");
                                        }
                                    }
                                }
                            } else {
                                //alert("身份证格式不正确!");
                                return false;
                            }
                        }
                    }

                }
            },
            chilNo:{
                validators:{
                    regexp: {
                        regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
                        message: '身份证号码格式不正确，为15位和18位身份证号码！'
                    },
                    callback: {
                        /*自定义，可以在这里与其他输入项联动校验*/
                        message: '身份证号码无效！',
                        callback: function (value, validator, $field) {
                            if(value.length==0){
                                return true;
                            }
                            //15位和18位身份证号码的正则表达式
                            var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
                            //如果通过该验证，说明身份证格式正确，但准确性还需计算
                            var idCard = value;
                            if (regIdCard.test(idCard)) {
                                if (idCard.length == 18) {
                                    var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                                    var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                                    var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                                    for (var i = 0; i < 17; i++) {
                                        idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
                                    }
                                    var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                                    var idCardLast = idCard.substring(17);//得到最后一位身份证号码
                                    //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                                    if (idCardMod == 2) {
                                        if (idCardLast == "X" || idCardLast == "x") {
                                            return true;
                                            //alert("恭喜通过验证啦！");
                                        } else {
                                            return false;
                                            //alert("身份证号码错误！");
                                        }
                                    } else {
                                        //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                                        if (idCardLast == idCardY[idCardMod]) {
                                            //alert("恭喜通过验证啦！");
                                            return true;
                                        } else {
                                            return false;
                                            //alert("身份证号码错误！");
                                        }
                                    }
                                }
                            } else {
                                //alert("身份证格式不正确!");
                                return false;
                            }
                        }
                    }
                }
            }

        }
    });


    /*$("#dataForm").bootstrapValidator('destroy');*/

}
function checkTel(tel) {

}

