Vue.component('caller',{
    template:"#caller",
    /**
     * host:websocket连接地址及端口，ws://localhost:61616
     * type:操作台类别，预检precheck,儿保healthcare,登记register,接种inoculate
     * tower:操作台id
     * user:当前登录用户
     */
    props:['host','type','tid','tname','ttype'],
    data:function(){
        var self = this;
        return{
            endWords:["","登记","接种","体检","预检"],
            towerList:[],
            currentTower:{},
            currentQueue:{childName:"请取号排队"},
            queueList:{},
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
                    if(msg.position == self.tid){
                       self.currentQueue = msg;
                       if(typeof extools !== 'undefined'){
                           extools.tts("请"+msg.noText+"号，"+(msg.childName==null?"":msg.childName)+"。到"+self.tname+self.endWords[self.ttype]);
                       }
                    }else if(msg.id == self.currentQueue.id){
                        self.currentQueue={};
                    }

                    Vue.delete(self.queueList,msg.id);
                    break;
                case "CREATE":
                    Vue.set(self.queueList,msg.id,msg);
                    break;
                case "FINISH":
                    if(msg.step == 'inoculate' && self.currentQueue.id == msg.id){
                        self.currentQueue.vaccine ="请到留观室留观30分钟";
                        if(typeof extools !== 'undefined') {
                            extools.tts("请" + (msg.noText == null ? "" : msg.noText + "号，") + (msg.childName == null ? "" : msg.childName) + "。到留观室留观三十分钟");
                        }
                        window.setTimeout(function(){
                            if(self.currentQueue.id == msg.id){
                                self.currentQueue={childName:"请取号排队"};
                            }
                        },10000)
                    }
                    if(msg.step == 'register' && self.currentQueue.id == msg.id){
                            self.currentQueue={childName:"请取号排队"};
                    }
                    break;
                case "CANCEL":
                    if(self.currentQueue.id == msg.id){
                        self.currentQueue={childName:"请取号排队"};
                    }
                    break;
                case "DELAY":
                    if(self.currentQueue.id == msg.id){
                        self.currentQueue={childName:"请取号排队"};
                    }
                    break;
                case "BACK":
                    if(self.currentQueue.id == msg.id){
                        self.currentQueue={childName:"请取号排队"};
                    }
                    break;
                case "TRANSFER":
                    if(self.currentQueue.id == msg.id){
                        self.currentQueue={childName:"请取号排队"};
                    }
                    break;
                default:
                    break;
            }
        },
        copy:function(source) {
            return JSON.parse(JSON.stringify(source));
        }

    },
    mounted:function(){
        var self = this;
        $.get("../queue/getOwnQueueList/"+self.type+"/"+self.tid, function(result){
            result.forEach(function(item){
                Vue.set(self.queueList,item.id,item);
            })
        });

        $.get("../queue/getOwnDelayQueueList/"+self.type+"/"+self.tid, function(result){
            result.forEach(function(item){
                Vue.set(self.queueList,item.id,item);
            })
        });
    },
    watch:{
        currentTower:function(cur,old){
            this.currentQueue={};
        }
    },
    destroyed:function(){
        this.client.disconnect();
    }

});


