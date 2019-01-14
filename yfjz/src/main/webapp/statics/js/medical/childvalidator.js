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
            // chilBirthhospitalname: {
            //     validators: {
            //         notEmpty: {
            //             message: '出生医院不能为空'
            //         }
            //     }
            // },
            // chilBirthday: {
            //     validators: {
            //         notEmpty: {
            //             message: '出生日期不能为空'
            //         }
            //     }
            // },
            chilBirthweight: {
                validators: {
                    notEmpty: {
                        message: '出生重量不能为空'
                    }
                }
            },
            // chilCommittee: {
            //     validators: {
            //         notEmpty: {
            //             message: '行政村不能为空'
            //         }
            //     }
            // },
            // chilHere: {
            //     validators: {
            //         notEmpty: {
            //             message: '个案状态不能为空'
            //         }
            //     }
            // },
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
                    notEmpty: {
                        message: '家庭电话不能为空'
                    }
                }
            },
            chilNatiId: {
                validators: {
                    notEmpty: {
                        message: '民族不能为空'
                    }
                }
            },

        }
    });
}
