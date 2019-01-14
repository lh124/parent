Vue.component('queuer',{
    template:`
	<section class="layui-col-md2 layui-col-sm3 rightBar mt10"  id="queue" xmlns="http://java.sun.com/jsf/html">
	   <!-- <span class="line-column" :style="{height: realHeight}"></span>-->
		<div class="rightBar-top" >
		  <div style="font-weight: bold;">
			<div class="layui-row"  style="padding-top: 10px">
				<a href="javascript: void(0)" class="w-50 tc action-items" @click.prevent="call">
					<i class="layui-icon layui-icon-speaker"></i>
					<p>呼叫</p>
				</a>
				<a href="javascript: void(0)" class="w-50 tc action-items" @click.prevent="cancel">
					<i class="layui-icon layui-icon-delete"></i>
					<p>取消</p>
				</a>
			</div>
			<div class="layui-row mt10" style="padding-bottom: 15px" >
				<a href="javascript: void(0)" class="w-50 tc action-items" @click.prevent="delay" >
					<i class="layui-icon layui-icon-snowflake"></i>
					<p>延后</p>
				</a>
				<a v-if="type == 'precheck' " href="javascript: void(0)" class="w-50 tc action-items" disabled="true" @click.prevent="finish">
					<i class="layui-icon layui-icon-ok" ></i>
					<p>完成</p>
				</a>
				<a v-if="type != 'precheck' && type !='healthcare' && type !='register' " href="javascript: void(0)" @click.prevent="back" class="w-50 tc action-items" disabled="true">
					<i class="layui-icon layui-icon-return"></i>
					<p>退回</p>
				</a>
				<a v-if="type == 'register'" href="javascript: void(0)" @click.prevent="suspend" class="w-50 tc action-items" disabled="true">
					<i id="stopicon" class="layui-icon layui-icon-pause"></i>
					<p id="stop">暂停取号</p>
				</a>
			</div>
			<!--只有接种台可以转台-->
			<div class="layui-row mt10" v-if="type=='inoculate'" style="margin-top: -15px">
				<a href="javascript: void(0)" class="w-50 tc action-items" @click.prevent="transfer" >
					<i class="layui-icon layui-icon-share"></i>
					<p>转台</p>
				</a>
			</div>
			</div>
			<!-- 转台选择窗口 -->
			<div id="towerSelect" style="display:none">
			    <select class="form-control" size="5" multiple="false">
                    <option v-for="tower in otherTowers" value="{{tower.id}}" @click="transferDest = tower.id">{{tower.towerName}}</option>
                </select>
            </div>
			
            
		<div class="rightBar-body mt15">
			<div class="queue-content">
				<div class="layui-row queue-title">
					<div class="w-50 tc queue-name">等候人数：</div>
					<span class="w-50 tc number-color">{{queueList.length}}人</span>
				</div>
				<ul class="current-queue">
				    <li class="layui-row current-items" v-show="currentNumber.no">
						<div class="w-50 tc handpoint">{{currentNumber.noText}}</div>
						<div class="w-50 tc textset">{{currentNumber.childName}}</div>
					</li>
					<li v-for="(item,index) in queueList" class="layui-row" :key="item.nubmer" @click="selectQueue(item)">
						<div :class="['w-50','tc', 'handpoint',{'selected-items':item.id == selected.id},{'back-queue':item.action=='BACK' || item.action=='TRANSFER'}]">{{item.noText}}<span v-if="item.action=='TRANSFER'">(转)</span><span v-if="item.action=='BACK'">(退)</span></div>
						<div :class="['w-50','tc', 'textset',{'selected-items':item.id == selected.id},{'back-queue':item.action=='BACK' || item.action=='TRANSFER'}]">{{item.childName}}</div>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="queue-content mt10">
            <div class="layui-row queue-title">
                <div class="w-50 tc queue-name">延后队列：</div>
                <span class="w-50 tc number-color">{{delayQueueList.length}}人</span>
            </div>
            <ul class="delay-queue">
                <li :class="['layui-row']" v-for="(item,index) in delayQueueList" :key="item.id" @click="selectQueue(item)">
                    <div :class="['w-50','tc', 'handpoint',{'selected-items':item.id == selected.id}]">{{item.noText}}</div>
                    <div :class="['w-50','tc', 'textset',{'selected-items':item.id == selected.id}]">{{item.childName}}</div>
                </li>
            </ul>
        </div>
		<div id="cancelForm" class="tmpl layui-row none">
		    <textarea id="cancleReason" placeholder="取消原因"></textarea>
        </div>
	</section>`,
    /**
     * host:websocket连接地址及端口，ws://localhost:61616
     * type:操作台类别，预检precheck,儿保healthcare,登记register,接种inoculate
     * tower:操作台id
     * user:当前登录用户
     */
    props:['host','type','tower','user'],
    data:function(){
        var self = this;
        return{
            realHeight: 0,
            queueList:[],
            delayQueueList:[],
            currentNumber:{},
            selected:{},
            otherTowers:[],
            transferDest:'',
            client:(function(){
                return self.init(self.host,self.type)
            })()
        }
    },
    created: function(){
        this.realHeight = $(window).height() - 60 + 'px';

    },
    methods:{
        //初始化stompClient
        init:function(host,type){
            if(this.client && this.client.connected){
                return;
            }
            var client=Stomp.client(host);
            client.connect({},host,this.connected_cb,this.disconnect_cb);
            return client;
        },
        //连接成功的回调函数
        connected_cb:function(frame){
            this.subscribe(this.type)
        },
        //失去连接或连接失败的回调
        disconnect_cb:function(){
            this.client = null;
            this.client = this.init(this.host,this.type,this.user);
        },
        //订阅
        subscribe:function(topic){
            this.client.subscribe('/topic/yfjz.'+topic,this.onMessage,{});
        },
        //消息处理
        onMessage:function(frame){
            var self = this;
            var msg = JSON.parse(frame.body);
            switch (msg.action){
                case "CREATE":
                    if(!msg.dest || msg.dest == self.tower_id){
                        if(msg.step == "inoculate"){
                            if(self.tower == msg.position || !msg.position){
                                self.queueList.push(JSON.parse(frame.body))
                            }
                            self.$parent.$emit("registerFinish");
                        }else {
                            self.queueList.push(JSON.parse(frame.body))
                        }
                    }
                    self.$parent.$emit("registerFinish");
                    break;
                case "CANCEL":
                    self.queueList.forEach(function(queue,index){
                        if(queue.id == msg.id){
                            self.queueList.splice(index,1);
                        }
                    });
                    self.delayQueueList.forEach(function(queue,index){
                        if(queue.id == msg.id){
                            self.delayQueueList.splice(index,1);
                        }
                    });
                    self.unSelectQueue(msg);
                    self.$parent.$emit("registerFinish");
                    break;
                case "CALL":
                    if(msg.position != self.tower){
                        self.queueList.forEach(function(item,index){
                            if(item.id == msg.id){
                                self.queueList.splice(index,1)
                            }
                        })
                    }
                    self.$parent.$emit("registerFinish");
                    break;
                case "DELAY":
                    if(msg.position == self.tower){
                        self.queueList.forEach(function(item,index){
                            if(item.id == msg.id){
                                self.delayQueueList.push(msg);
                                self.queueList.splice(index,1);
                            }
                        })
                    }
                    break;
                case "BACK":
                    //退回的号，放入队列首位
                    if(msg.position != self.tower && self.type=="register"){
                        self.queueList.unshift(JSON.parse(frame.body))
                    }
                    if(self.type == "inoculate"){//接种队列移除
                        self.queueList.forEach(function(item,index){
                            if(item.id == msg.id){
                                self.queueList.splice(index,1);
                            }
                        });
                        self.delayQueueList.forEach(function(queue,index){
                            if(queue.id == msg.id){
                                self.delayQueueList.splice(index,1);
                            }
                        });
                        self.unSelectQueue(msg);
                    }
                    self.$parent.$emit("registerFinish");
                    break;
                case "FINISH":
                    if(self.currentNumber && self.currentNumber.id == msg.id){
                        self.currentNumber={};
                        self.selected = {};
                    }
                    self.queueList.forEach(function(queue,index){
                        if(queue.id == msg.id){
                            self.queueList.splice(index,1);
                            self.currentNumber={};
                            self.selected = {};
                        }
                    });
                    self.delayQueueList.forEach(function(queue,index){
                        if(queue.id == msg.id){
                            self.delayQueueList.splice(index,1);
                            self.currentNumber={};
                            self.selected = {};
                        }
                    });
                    self.$parent.$emit("registerFinish");
                    break;
                case "TRANSFER":
                    if(self.tower == msg.dest){
                        self.queueList.unshift(JSON.parse(frame.body));
                        self.$parent.$emit("reciverTransfer");
                    }
                    if(msg.position == self.tower){
                        self.queueList.forEach(function(item,index){
                            if(item.id == msg.id){
                                self.queueList.splice(index,1)
                            }
                        });
                        self.delayQueueList.forEach(function(queue,index){
                            if(queue.id == msg.id){
                                self.delayQueueList.splice(index,1);
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        },
        //发送消息
        send:function(msg){
            var headers = {
                priority:0,
              /*  source:this.table,
                user:this.user*/
            }
            var msg_copy = this.copy(msg);
            msg_copy.position = this.tower;
            msg_copy.operator = this.user;
            msg_copy.step = this.type;
            this.client.send('/topic/yfjz.'+this.type,headers,JSON.stringify(msg_copy));
        },
        //取消号码
        cancel:function(){
            var self = this;
            if(this.currentNumber.id  || self.selected.id){
                var todoQueue;
                if(self.currentNumber.id){
                    todoQueue = self.currentNumber;
                }else if(self.selected.id){//先取当前再取选中
                    todoQueue =  self.selected;
                }else{
                    return;
                }

                layer.open({
                    title: '取消操作',
                    skin: 'tmpl',
                    type: 1,
                    closeBtn: 1,//取消右上角的x关闭按钮
                    area: ['300px', '270px'],
                    content: "<div><span>取消原因：</span><select id='cancelReason' onchange='changeItem()'></select></div>",
                    btn: ['确定', '取消'],
                    btn1: function (index) {
                        //self.currentNumber.action="CANCEL"
                        todoQueue.action="CANCEL"
                        var reason="";
                        var sele=  $("#cancelReason option:selected").text();
                        if(sele=="其他"){
                            var otherReason=$("#otherAre").find("textarea").val();
                            reason=sele+":"+otherReason;
                        }else{
                            reason=sele;
                        }
                        //self.currentNumber.remark=reason;
                        todoQueue.remark=reason;
                        self.send(todoQueue);
                        self.$parent.$emit("cancelNumber",todoQueue);
                        self.currentNumber = {};
                        self.selected = {};
                        layer.close(index);
                    }

                });
               /* $('#cancelReason').selectpicker({
                    'selectedText': 'cat',
                    noneSelectedText:'',
                    noneResultsText:'没有匹配的结果',
                    //actionsBox:true,
                    search:false,
                });*/
                $.ajax({
                    // get请求地址
                    url: '../sys/dict/typeList?type=cancel_reason',
                    dataType: "json",
                    type: 'POST',
                    success: function (data) {
                        var result=data.page;
                       var con = '';
                        $.each(result, function (i, n) {

                            con+='<option value="' + n.value + '">' + n.text + '</option>';
                        })
                        $("#cancelReason").append(con);
                        //$("#cancelReason").selectpicker('refresh');
                    }
                });
            }else{
                //layer.msg("还未呼叫号码，不能取消！")
                layer.msg("请选择需要取消的队列！");
            }

        },
        //叫号
        call:function(){
            //当前有cruuentNumber 重呼
            var self = this;
            if(self.currentNumber.id){

            }else if(self.selected.id){//当前无currentNumber，有selected，呼叫selected
                self.currentNumber = self.selected;
            }else if(self.queueList.length>0){//当前无cruuentNumber 从队列中弹出号码到cureentNumber
                self.currentNumber = self.queueList.shift();
            }else{
                return;
            }
            self.$parent.$emit("callingNumber",this.currentNumber);
            self.currentNumber.action = "CALL";
            self.send(this.currentNumber);
            self.queueList.forEach(function(item,index){
                if(item.id == self.currentNumber.id){
                    self.queueList.splice(index,1)
                }
            })

            self.delayQueueList.forEach(function(item,index){
                if(item.id == self.currentNumber.id){
                    self.delayQueueList.splice(index,1)
                }
            })
            self.currentNumber.position = self.tower;//呼叫后个该号赋台
        },
        //退回
        back:function(){
            var self = this;
            if(self.type == "inoculate"){
                if(this.currentNumber.id  || self.selected.id){
                    var todoQueue;
                    if(self.currentNumber.id){
                        todoQueue = self.currentNumber;
                    }else if(self.selected.id){//先取当前再取选中
                        todoQueue =  self.selected;
                    }else{
                        return;
                    }
                    var lock = false; //默认未锁定
                    layer.confirm('确定退回儿童"'+todoQueue.childName+'"',function(index){
                        if(!lock) {
                            lock = true;//锁定
                            todoQueue.action="BACK"
                        self.send(todoQueue);
                        self.currentNumber = {};
                        self.selected = {};
                        layer.close(index);
                        }
                    })
                }else{
                    layer.msg("请选择需要退回的队列！")
                }
            }else{
                layer.msg("此台不能操作退回！")
            }

        },
        //暂停取号
        suspend:function(html){
            var text = $("#stop").html();
            if(text == "暂停取号"){
                $.ajax({
                    url:"../configuration/offernumberStatus",
                    data:{"type":"pause_number","startUsing":0},
                    success:function (result) {
                        if(result.code == 0){
                            $("#stop").html("开始取号")
                            $("#stopicon").removeClass("layui-icon-pause");
                            $("#stopicon").addClass("layui-icon-play");
                            layer.msg("已经暂停取号！")
                        }else{
                            alert(result.msg);
                        }
                    }
                });

            }else{
                $.ajax({
                    url:"../configuration/offernumberStatus",
                    data:{"type":"pause_number","startUsing":1},
                    success:function (result) {
                        if(result.code == 0){
                            $("#stop").html("暂停取号")
                            $("#stopicon").removeClass("layui-icon-play");
                            $("#stopicon").addClass("layui-icon-pause");
                            layer.msg("已经开始取号！")
                        }else{
                            alert(result.msg);
                        }
                    }
                });

            }

        },
        //转台
        transfer:function(){
            var self = this;
            self.otherTowers=[];
            self.transferDest='';
            if(self.currentNumber.id || self.selected.id){
                var delayQueue;
                if(self.currentNumber.id){
                    delayQueue = self.currentNumber;
                    // self.currentNumber={};
                }else if(self.selected.id){//当前无currentNumber，有selected，延后selected
                    delayQueue =  self.selected;
                }else{
                    return;
                }
                if(delayQueue.position ==null || "null"==delayQueue.position){
                    if(!self.currentNumber.id){
                        layer.msg('公共队列不需要转台！');
                        return;
                    }

                }
                self.selected={}
                delayQueue.action = "DELAY";
                $.get("../queue/getLoginTowers/2", function(result){
                    if(result.length <= 1){
                        layer.msg('无台可转');
                    }else{
                        layer.open({
                            type: 1,
                            skin: 'layui-layer-rim', //加上边框
                            area: ['180px', '240px'], //宽高
                            content: $('#towerSelect'),
                            title:'转台',
                            btn:['确定','取消'],
                            yes:function(){
                                if(self.transferDest){
                                    self.currentNumber={};
                                    delayQueue.action = "TRANSFER";
                                    delayQueue.dest = self.transferDest;
                                    self.send(delayQueue);
                                    layer.closeAll();
                                    self.currentNumber = {};
                                    self.selected = {};
                                    self.$parent.$emit("transfer");
                                }else{
                                    layer.msg("请选择工作台！")
                                }
                            }
                        });
                        result.forEach(function(tower){
                            if(tower.id != self.tower){
                                self.otherTowers.push(tower)
                            }
                        })
                    }
                });
            }else{
                layer.msg('请选择需要转台的号码！')
            }
          /*  if(this.currentNumber.noText){

             }else{
                layer.msg("还未呼叫号码，不能转台！")
            }*/

        },
        delay:function(){
            //currentNumber 延迟 currentNumber
            var self = this;
            if(self.currentNumber.id || self.selected.id || self.selected.status!= 1){
                var delayQueue;
                if(self.currentNumber.id){
                    delayQueue = self.currentNumber;
                    self.currentNumber={};
                    self.delayQueueList.push(delayQueue)
                }else if(self.selected.id){//当前无currentNumber，有selected，延后selected
                    delayQueue =  self.selected;
                }else{
                    return;
                }
                self.selected={}
                delayQueue.action = "DELAY";
                self.send(delayQueue);
                self.queueList.forEach(function(item,index){
                    if(item.id == self.currentNumber.id){
                        self.queueList.splice(index,1)
                    }
                })
                self.delayQueueList.forEach(function(item,index){
                    if(item.id == self.currentNumber.id){
                        self.queueList.splice(index,1)
                    }
                })
            }else{
                layer.msg('请选择需要延迟的号码！')
            }

        },
        finish:function(){
            var self = this;
            if(self.currentNumber.id){
                self.selected={}
                self.currentNumber.action = "FINISH";
                //self.send(self.currentNumber);
                self.$parent.$emit('precheckcomplite',self.currentNumber)

            }else{
                layer.msg('请先呼叫号码！')
            }
        },

        copy:function(source) {
            return JSON.parse(JSON.stringify(source));
        },

        selectQueue:function(queue){
            var self = this;
            self.selected = queue;
           /* self.queueList.forEach(function(item,index){
                if(item.id == queue.id){
                    self.queueList.splice(index,1)
                }
            })*/
            /*this.queueList.forEach(function(queue){
                if(queue.id == id){
                    self.currentNumber  = queue;
                }
            })*/
            if(queue.position && self.tower == queue.position && !self.currentNumber.id){//已经呼叫过且当前没有分配
                self.$parent.$emit("callingNumber",queue);
            }else if(self.type=="register" && !self.currentNumber.id && queue.childCode){//已经呼叫过且当前没有分配
                self.$parent.$emit("callingNumber",queue);
            }
        },
        unSelectQueue:function(queue){//取消队列选择
            var self = this;
            self.selected = {};
            self.$parent.$emit("unSelectQueue",queue);
        },
        test:function(){
            alert('test')
        }

    },
    mounted:function(){
        var self = this;
        $.get("../queue/getOwnQueueList/"+self.type+"/"+self.tower, function(result){
            self.queueList = result;
        });

        $.get("../queue/getOwnDelayQueueList/"+self.type+"/"+self.tower, function(result){
            self.delayQueueList = result;
        });
    },
    destroyed:function(){
        this.client.disconnect();
    }
});
function changeItem(event) {
    var sele=  $("#cancelReason option:selected").val();
    var other=document.getElementById('otherAre');
    if(other==null&&sele==4){
        $("#cancelReason").after("<div id='otherAre'><br><textarea  rows='5' cols='28' style='margin-left: 70px;margin-top: 10px' placeholder='请输入原因...'></textarea></div>")
        // $("#cancelReason").after("13212312312")
        // console.log(  $("#cancelReason"))
    }else{
        $("#otherAre").remove();
    }
}

$(function(){
    var realHeight = $(window).height()+ 'px';
    $("#queue").css({"background":"#EBEBEB","height":realHeight});
})

