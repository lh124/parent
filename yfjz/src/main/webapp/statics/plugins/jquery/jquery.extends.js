// 1.jquery插件封装方法1：
(function ($) {
    $.fn.extend({
        test:function (opt) {
            opt=jQuery.extend({
                name:'name'
            },opt)
            //这里是实现代码
        }
    })
})(jQuery);




// 通过$.extend()向jQuery添加了一个log函数，然后通过$直接调用。
// 到此你可以认为我们已经完成了一个简单的jQuery插件了。
$.extend({
    log: function(message) {
        var now = new Date(),
            y = now.getFullYear(),
            m = now.getMonth() + 1, //！JavaScript中月分是从0开始的
            d = now.getDate(),
            h = now.getHours(),
            min = now.getMinutes(),
            s = now.getSeconds(),
            time = y + '/' + m + '/' + d + ' ' + h + ':' + min + ':' + s;
        console.log(time + ' My App: ' + message);
    }
});
//调用
$.log('initializing...');





//直接在插件上创建方法
/*
在插件名字定义的这个函数内部，this指代的是我们在调用该插件时，用jQuery选择器选中的元素，一般是一个jQuery类型的集合。比如$('a')返回的是页面上所有a标签的集合，且这个集合已经是jQuery包装类型了，也就是说，在对其进行操作的时候可以直接调用jQuery的其他方法而不需要再用美元符号来包装一下。
所以在上面插件代码中，我们在this身上调用jQuery的css()方法，也就相当于在调用 $('a').css()。
理解this在这个地方的含义很重要。这样你才知道为什么可以直接商用jQuery方法同时在其他地方this指代不同时我们又需要用jQuery重新包装才能调用，下面会讲到。初学容易被this的值整晕，但理解了就不难。
现在就可以去页面试试我们的代码了，在页面上放几个链接，调用插件后链接字体变成红色。*/
$.fn.myPlugin = function() {
    //在这里面,this指的是用jQuery选中的元素
    //example :$('a'),则this=$('a')
    this.css('color', 'red');
};

//调用方式
/*
<ul>
    <li>
        <a href="http://www.webo.com/liuwayong">我的微博</a>
    </li>
    <li>
        <a href="http://http://www.cnblogs.com/Wayou/">我的博客</a>
    </li>
    <li>
        <a href="http://wayouliu.duapp.com/">我的小站</a>
    </li>
    </ul>
    <p>这是p标签不是a标签，我不会受影响</p>
<script src="jquery-1.11.0.min.js"></script>
    <script src="jquery.myplugin.js"></script>
    <script type="text/javascript">
    $(function(){
        $('a').myPlugin();
    })
</script>*/






/*我们已经知道this指代jQuery选择器返回的集合，那么通过调用jQuery的.each()方法就可以处理合集中的每个元素了，
但此刻要注意的是，在each方法内部，this指带的是普通的DOM元素了，如果需要调用jQuery的方法那就需要用$来重新包装一下。
比如现在我们要在每个链接显示链接的真实地址，首先通过each遍历所有a标签，然后获取href属性的值再加到链接文本后面。
更改后我们的插件代码为：*/
$.fn.myPlugin = function() {
    //在这里面,this指的是用jQuery选中的元素
    this.css('color', 'red');
    this.each(function() {
        //对每个元素进行操作
        $(this).append(' ' + $(this).attr('href'));
    });
};






//一个好的做法是将一个新的空对象做为$.extend的第一个参数，defaults和用户传递的参数对象紧随其后，
// 这样做的好处是所有值被合并到这个空对象上，保护了插件里面的默认值。
//到此，插件可以接收和处理参数后，就可以编写出更健壮而灵活的插件了。若要编写一个复杂的插件，代码量会很大，
// 如何组织代码就成了一个需要面临的问题，没有一个好的方式来组织这些代码，
// 整体感觉会杂乱无章，同时也不好维护，所以将插件的所有方法属性包装到一个对象上，用面向对象的思维来进行开发，无疑会使工作轻松很多。
$.fn.myPlugin = function(options) {
    var defaults = {
        'color': 'red',
        'fontSize': '12px'
    };
    var settings = $.extend({},defaults, options);//将一个空对象做为第一个参数
    return this.css({
        'color': settings.color,
        'fontSize': settings.fontSize
    });
};