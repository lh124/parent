Vue.component('hall',{
    template:"#hall",
    /**
     * host:websocket连接地址及端口，ws://localhost:61616
     * type:操作台类别，预检precheck,儿保healthcare,登记register,接种inoculate
     * tower:操作台id
     * user:当前登录用户
     */
    props:['host','type','tower'],
    data:function(){
        var self = this;
        return{
            queueTypes:[{},{queueName:'登记台队列',queueCode:'register'},{queueName:'接种台队列',queueCode:'inoculate'},{queueName:'儿保台队列',queueCode:'healthcare'},{queueName:'预检台队列',queueCode:'precheck'}],
            towerTypes:{1:'登记台队列',2:'接种台队列',3:'儿保台队列',4:'预检台队列'},
            queues:{},
            towerList:{},
            queueStatus:0,
            client:(function(){
                return self.init(self.host,self.type)
            })()
        }
    },
    methods:{
        //初始化stompClient
        init:function(host){
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
            this.client = this.init(this.host,this.type);
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
                case "CALL":
                    self.refresh(msg);
                    if(msg.step == 'register'){
                        var target = self.queues[1]['towerList'][msg.position]
                        Vue.set(target,'current',msg);
                        if(typeof extools !== 'undefined'){
                            extools.tts("请"+(msg.noText==null?"":msg.noText+"号，")+(msg.childName==null?"":msg.childName)+"。到"+target.tower.towerName+"登记");
                        }
                        target = self.queues[1]['queueList'];
                        Vue.delete(target,msg.id);
                    }else if(msg.step == 'inoculate'){
                        var target = self.queues[2]['towerList'][msg.position]
                        Vue.set(target,'current',msg);
                        if(typeof extools !== 'undefined'){
                            extools.tts("请"+(msg.noText==null?"":msg.noText+"号，")+(msg.childName==null?"":msg.childName)+"。到"+target.tower.towerName+"接种");
                        }
                        target = self.queues[2]['queueList'];
                        Vue.delete(target,msg.id);
                    }else if(msg.step == 'precheck'){
                        var target = self.queues[4]['towerList'][msg.position]
                        Vue.set(target,'current',msg);
                        if(typeof extools !== 'undefined'){
                            extools.tts("请"+(msg.noText==null?"":msg.noText+"号，")+(msg.childName==null?"":msg.childName)+"。到"+target.tower.towerName+"预检");
                        }
                        target = self.queues[4]['queueList'];
                        Vue.delete(target,msg.id);
                    }else if(msg.step == 'healthcare'){
                        var target = self.queues[3]['towerList'][msg.position]
                        Vue.set(target,'current',msg);
                        if(typeof extools !== 'undefined'){
                            extools.tts("请"+(msg.noText==null?"":msg.noText+"号，")+(msg.childName==null?"":msg.childName)+"。到"+target.tower.towerName+"体检");
                        }
                        target = self.queues[3]['queueList'];
                        Vue.delete(target,msg.id);
                    }
                    break;
                case "CREATE":
                    if(msg.step == 'register'){
                        var target = self.queues[1]['queueList'];
                        Vue.set(target,msg.id,msg);
                    }else if(msg.step == 'inoculate'){
                        var target = self.queues[2]['queueList'];
                        Vue.set(target,msg.id,msg);
                    }else if(msg.step == 'precheck'){
                        var target = self.queues[4]['queueList'];
                        Vue.set(target,msg.id,msg);
                    }else if(msg.step == 'healthcare'){
                        var target = self.queues[3]['queueList'];
                        Vue.set(target,msg.id,msg);
                    }
                    break;
                case "QUEUEREFRESH":
                    if(msg.src != null){
                        self.queueStatus = msg.src;
                    }
                    break;
                default:
                    break;
            }
        },
        copy:function(source) {
            return JSON.parse(JSON.stringify(source));
        },
        refresh:function(msg){
            var self = this;
            Object.keys(self.queues).forEach(function(key){
                Object.keys(self.queues[key]['towerList']).forEach(function(child_key){
                    if(self.queues[key]['towerList'][child_key].current.id && self.queues[key]['towerList'][child_key].current.id == msg.id){
                        self.queues[key]['towerList'][child_key].current={};
                    }
                })
            })
        }
    },
    beforeCreate:function(){
        var self = this;
        //获取分队列启用状态
        $.get("../inocpointmgr/getQueuesDistinction", function(result){
            if(result.code ==0){
                self.queueStatus = result.typeValue;
            }
        });
        //加载台和队列
        $.get("../tbasetower/listAll", function(result){
            if(result.tower){
                result.tower.forEach(function(item){
                    var key = item.towerNatureId;
                    var child = self.queues[key];
                    if(!child){
                        child = Vue.set(self.queues,key,{queueList:{},towerList:{}});
                        var target = child;
                        $.get("./getStepQueueList/"+self.queueTypes[key].queueCode, function(result){

                            result.forEach(function(q){
                                Vue.set(target.queueList,q.id,q);
                            })

                        })
                    }
                    Vue.set(child.towerList,item.id,{current:{},tower:item});
                })
            }
        });
    },
    updated:function(){
       /* var self = this;
        var wHeight = $(window).height();
        var top = $('.items-top').innerHeight();
        $(".item-content").css({
            height: wHeight - top + 'px'
        });

        var limit = Object.keys(self.queues).length;
        $(".row-items").css({
            width: (100 / limit) + '%'
        });*/
    },
    destroyed:function(){
        this.client.disconnect();
    }

});


