/**************************
 *Desc:提交操作时遮罩
 *Argument:type=0 全屏遮 1局部遮
 *Author:Zery-Zhang
 *Date:2014-09-18
 *Version:1.0.0
 **************************/
;(function ($) {
    $.fn.jqLoading = function (option) {
        var defaultVal = {
            backgroudColor: "#ECECEC",//背景色
            backgroundImage: "../statics/images/loading.gif",//背景图片
            text: "正在加载中，请耐心等待....",//文字
            //width: 150,//宽度
            height: 60,//高度
            type: 0 //0全部遮，1 局部遮

        };
        var opt = $.extend({}, defaultVal, option);
        if (opt.type == 0) {
            //全屏遮
            openLayer();
        } else {
            //局部遮(当前对象应为需要被遮挡的对象)
            openPartialLayer(this);
        }

        //销毁对象
        if (option === "destroy") {
            closeLayer();
        }

        //设置背景层高
        function height() {
            var scrollHeight, offsetHeight;
            // handle IE 6
            if ($.support.boxModel && $.support.leadingWhitespace) {
                scrollHeight = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
                offsetHeight = Math.max(document.documentElement.offsetHeight, document.body.offsetHeight);
                if (scrollHeight < offsetHeight) {
                    return $(window).height();
                } else {
                    return scrollHeight;
                }
                // handle "good" browsers
            }
            else if ($.support.objectAll) {
                return $(document).height() - 4;
            }
            else {
                return $(document).height() + 500;
            }
        };

        //设置背景层宽
        function width() {
            var scrollWidth, offsetWidth;
            // handle IE
            if ($.support.boxModel) {
                scrollWidth = Math.max(document.documentElement.scrollWidth, document.body.scrollWidth);
                offsetWidth = Math.max(document.documentElement.offsetWidth, document.body.offsetWidth);
                if (scrollWidth < offsetWidth) {
                    return $(window).width();
                } else {
                    return scrollWidth;
                }
                // handle "good" browsers
            }
            else {
                return $(document).width();
            }
        };

        /*==========全部遮罩=========*/
        function openLayer() {
            //背景遮罩层
            var layer = $("<div id='layer'></div>");
            layer.css({
                zIndex: 9998,
                position: "absolute",
                height: "100%",
                width: "100%",
                background: "black",
                top: 0,
                left: 0,
                filter: "alpha(opacity=30)",
                opacity: 0.01

            });

            //图片及文字层
            var content = $("<div id='content'></div>");
            content.css({
                textAlign: "left",
                position: "absolute",
                zIndex: 9999,
                height: opt.height + "px",
                width: opt.width + "px",
                top: "50%",
                left: "50%",
                verticalAlign: "middle",
                background: opt.backgroudColor,
                borderRadius: "8px",
                fontSize: "13px"
            });

            content.append("<img style='vertical-align:middle;margin:" + (opt.height / 4) + "px; 0 0 5px;margin-right:5px;' src='" + opt.backgroundImage + "' /><span style='text-align:center; vertical-align:middle;'>" + opt.text + "</span>");
            $("body").append(layer).append(content);
            var top = content.css("top").replace('px', '');
            var left = content.css("left").replace('px', '');
            content.css("top", (parseFloat(top) - opt.height / 2)).css("left", (parseFloat(left) - opt.width / 2));

            return this;
        }

        //销毁对象
        function closeLayer() {
            $("#layer,#content,#partialLayer").remove();
            return this;
        }

        /*==========局部遮罩=========*/
        function openPartialLayer(obj) {

            var eheight = $(obj).css("height");//元素带px的高宽度
            var ewidth = $(obj).css("width");
            var top = $(obj).offset().top; // 元素在文档中位置 滚动条不影响
            var left = $(obj).offset().left;
            var layer = $("<div id='partialLayer'></div>");
            layer.css({
                style: 'z-index:9998',
                position: "absolute",
                height: eheight,
                width: ewidth,
                background: "black",
                top: top,
                left: left,
                filter: "alpha(opacity=60)",
                opacity: 0.6,
                borderRadius: "3px",
                dispaly: "block"
            });
            $("body").append(layer);
            return this;
        }
    };

})(jQuery);

(function ($) {

    window.Ewin = function () {
        var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
            '<div class="modal-dialog modal-sm">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
            '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
            '</div>' +
            '<div class="modal-body">' +
            '<p>[Message]</p>' +
            '</div>' +
            '<div class="modal-footer">' +
            '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
            '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';


        var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
            '<div class="modal-dialog">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
            '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
            '</div>' +
            '<div class="modal-body">' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
        var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
        var generateId = function () {
            var date = new Date();
            return 'mdl' + date.valueOf();
        }
        var init = function (options) {
            options = $.extend({}, {
                title: "操作提示",
                message: "提示内容",
                btnok: "确定",
                btncl: "取消",
                width: 200,
                auto: false
            }, options || {});
            var modalId = generateId();
            var content = html.replace(reg, function (node, key) {
                return {
                    Id: modalId,
                    Title: options.title,
                    Message: options.message,
                    BtnOk: options.btnok,
                    BtnCancel: options.btncl
                }[key];
            });
            $('body').append(content);
            $('#' + modalId).modal({
                width: options.width,
                backdrop: 'static'
            });
            $('#' + modalId).on('hide.bs.modal', function (e) {
                $('body').find('#' + modalId).remove();
            });
            return modalId;
        }

        return {
            alert: function (options) {
                if (typeof options == 'string') {
                    options = {
                        message: options
                    };
                }
                var id = init(options);
                var modal = $('#' + id);
                modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
                modal.find('.cancel').hide();

                return {
                    id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () {
                                callback(true);
                            });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            confirm: function (options) {
                var id = init(options);
                var modal = $('#' + id);
                modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
                modal.find('.cancel').show();
                return {
                    id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () {
                                callback(true);
                            });
                            modal.find('.cancel').click(function () {
                                callback(false);
                            });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            dialog: function (options) {
                options = $.extend({}, {
                    title: 'title',
                    url: '',
                    width: 800,
                    height: 550,
                    onReady: function () {
                    },
                    onShown: function (e) {
                    }
                }, options || {});
                var modalId = generateId();

                var content = dialogdHtml.replace(reg, function (node, key) {
                    return {
                        Id: modalId,
                        Title: options.title
                    }[key];
                });
                $('body').append(content);
                var target = $('#' + modalId);
                target.find('.modal-body').load(options.url);
                if (options.onReady())
                    options.onReady.call(target);
                target.modal();
                target.on('shown.bs.modal', function (e) {
                    if (options.onReady(e))
                        options.onReady.call(target, e);
                });
                target.on('hide.bs.modal', function (e) {
                    $('body').find(target).remove();
                });
            }
        }
    }();
})(jQuery);