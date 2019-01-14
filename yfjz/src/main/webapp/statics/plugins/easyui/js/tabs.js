/*
Author：唐旭峰
Date:20160824
*/
var functionid;
var _webPath=null;
function OpenTab(title, url,id, icon){
	if($("#tabs").tabs('exists', title)){
		$("#tabs").tabs('select', title);
	}else{
		$("#tabs").tabs('add',{
			title: title,
			content: createTabContent(url,id),
			closable: true,
			icon: icon
		});
	}	
}

function checkWH(obj){
//	var reg = /^w+((-w+)|(.w+))*?[A-Za-z0-9]+$/;
//	　　if (!reg.test(obj)) {
//	　　	return false;
//	　　}
//	else
//		return true;
	return obj.indexOf("?") > -1;
}

function createTabContent(url,id){
	var url1;
	if(checkWH(url)) url1= url + '&functionid='+id;
	else url1= url + '?functionid='+id;
	return '<iframe style="width:100%;height:100%;" scrolling="no" frameborder="0" src="' + url1 + '"></iframe>';
}
/**
 * 初始化菜单导航
 * @author 贝国刚
 * @日期 2016年11月1日, AM 11:26:35
 */
function getznodes(){
	$.ajax({
		type : 'post',
		url : _webPath + 'menu/getmenu.jhtml',
		dataType : 'json',
		success : function(data) {
			var str= "<ul class='yiji'>"+data.list +"</ul>";
			$("#nav").html(str);
		}
	});
}
/**
 * 首页-切换风格选择窗口
 * @author 贝国刚
 * @日期 2016年10月31日, AM 09:20:54
 */
function switchStyle(title,url){
	layer.open({
		  type:2,
	      title: title,
	      area: ['600px', '400px'],
	      content: url,
	      scrollbar: false,
	      btn: ['确定', '取消'],
		  yes: function(index, layero){
		    //按钮【按钮一】的回调
			  alert(1);
		  }
	    });
}

/**
 * 首页-切换风格选择窗口
 * @author 贝国刚
 * @日期 2016年10月31日, AM 09:20:54
 */
function switchdepart(title,url){
	layer.open({
		  type:2,
	      title: title,
	      area: ['400px', '600px'],
	      content: url,
	      scrollbar: true,
	      btn: ['确定', '取消'],
		  yes: function(index, layero){
		    //按钮【按钮一】的回调
			  alert(1);
		  }
	    });
}
$(function(){
	//...绑定首页菜单导航点击事件  2016年10月28日, AM 10:02:19  @贝国刚
	$("#nav").ajaxComplete(function(){
		$('.inactive').click(function(){
			var funurl = $(this).parent("li").attr("functionurl");
			var funid = $(this).parent("li").attr("functionid");
			
			if(funurl.length > 0){
				funurl = _webPath + funurl;
				//alert(funurl);
				OpenTab($(this).html(),funurl,funid,null);
				return;
			}
			var cls = $(this).attr("class");
			if(cls == "inactive one"){
				var listtop = $(".list").offset().top;
				var listheight =$(document).height() - listtop -15;//总高度
				var liheight =$(this).parent('li').height();//li 高度
				var lilen = $(".yiji >li").length; //li 个数
				var heg = listheight-(liheight*lilen);
				var h = $(this).siblings('ul').height();
				
				$(this).addClass("click").parent("li").siblings("li").find("a").removeClass("click");
				$(this).parent('li').css("background-color","#fff")
				$(this).parent('li').siblings('li').children('ul').slideUp(100);//隐藏
				if($(this).siblings('ul').css('display')=='block'){
					$(this).siblings('ul').slideUp(100);//隐藏
					return;
				}
				//if(heg < 400) heg = 400;
				//$(this).next('ul').height(heg+"px").slideDown(100);
				$(this).next('ul').slideDown(100);
			}else if(cls == "inactive active"){
				var parhei = $(this).parents('ul').height();//父 ul 高度
				var lilen =$(this).parent("li").siblings().length + 1;
				var liheight =$(this).parent('li').height() * lilen;//li 高度
				var heg = parhei - liheight;
				$(this).addClass("click2").parent("li").siblings("li").find("a").removeClass("click2");
				$(this).parents('li').siblings('li').children('ul').slideUp(100);//隐藏
				if($(this).siblings('ul').css('display')=='block'){
					$(this).siblings('ul').slideUp(100);//隐藏
					return;
				}
				//if(heg < 200) heg = 200;
				//$(this).next('ul').height(heg+"px").slideDown(100);
				$(this).next('ul').slideDown(100);
			}else{
				if($(this).siblings('ul').css('display')=='block'){
					$(this).siblings('ul').slideUp(100);//隐藏
					$(this).removeClass("click");
				}else{
					$(this).siblings('ul').slideDown(100);//显示
				}
			}
		});
	});
	$("#m-refresh").click(function(){
		var currTab = $('#tabs').tabs('getSelected');	//��ȡѡ�еı�ǩ��
		var url = $(currTab.panel('options').content).attr('src');	//��ȡ��ѡ������ݱ�ǩ��iframe���� src ����
		if(title=='欢迎使用'){
			url="test-list.htm";
		}
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content: createTabContent(url)
			}
		})
	});
	
	$("#m-closeall").click(function(){
		$(".tabs li").each(function(i, n){
			var title = $(n).text();
			if(title!='欢迎使用'){
				$('#tabs').tabs('close',title);
			}
		});
	});
	
	//��ǰ֮��ر�����
	$("#m-closeother").click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		currTitle = currTab.panel('options').title;	
		
		$(".tabs li").each(function(i, n){
			var title = $(n).text();
			if(currTitle!='欢迎使用'){
				if(currTitle != title){
					$('#tabs').tabs('close',title);			
				}
			}
		});
	});
	
	//�رյ�ǰ
	$("#m-close").click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		currTitle = currTab.panel('options').title;	
		if(currTitle!='欢迎使用'){
			$('#tabs').tabs('close', currTitle);
		}
	});
	
});

