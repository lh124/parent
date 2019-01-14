Vue.filter("countDown",function(count){
    var min,sec;
    if(count <= 0){
        return "留观完成"
    }else{
        min = Math.floor(count/60)<10?'0'+Math.floor(count/60):Math.floor(count/60);
        sec = count % 60 < 10?'0'+count % 60:count % 60;
    }
    return min +":"+ sec;
});
Vue.component('observe',{
    template:"#observe",
    /**
     * host:websocket连接地址及端口，ws://localhost:61616
     * type:操作台类别，预检precheck,儿保healthcare,登记register,接种inoculate
     * tower:操作台id
     * user:当前登录用户
     */
    props:['host','type'],
    data:function(){
        var self = this;
        return{
            observeList:[],
            //留观时间(秒)
            observeTime:1800,
            //静音时间(秒)
            silentTime:0,
            showNotice:false,
            notice:'',
            client:(function(){
                return self.init(self.host,self.type)
            })(),
            player:{},
            playList:[],
            current:0
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
                case "CREATE":
                    msg.count = self.observeTime;
                    self.observeList.push(msg);
                    self.silentTime+=7;
                    break;
                case "PAUSE":
                    self.player.pause();
                    break;
                case "PLAY":
                    self.player.play();
                    break;
                case "STOP":
                    self.player.currentSource() ;
                    self.player.pause()
                    break;
                case "RELOAD":
                    $.get("../queue/queryListByPlayerAreaId/2", function(result){
                        self.playList =[];
                        if(result.fileurls){
                            result.fileurls.forEach(function(file){
                                self.playList.push(file.fileurl)
                            })
                        }
                        self.player.src({type:'video/mp4',src:self.playList[self.current]})
                    });
                    break;
                case "NOTICE":
                    self.showNotice = true;
                    $.get("../queue/notice/info/2", function(result){
                        self.notice = result['tQueueNotice']['content'];
                            if(self.notice.length>120){
                                var sec = self.notice.length/2;
                                $('.marquee')[0].style.animationDuration=sec+'s';
                            }


                    });
                    //self.notice = "9价HPV（人乳头瘤病毒）疫苗8月16日登陆深圳市罗湖中医院下属罗湖成人预防接种门诊，并于当日中午12点正式开放预约。这也是该疫苗在深圳市的首次露面。不少市民了解到该信息时，发现约1060个名额（每天10个名额，开放至11月30日）全部约满9价HPV（人乳头瘤病毒）疫苗8月16日登陆深圳市罗湖中医院下属罗湖成人预防接种门诊，并于当日中午12点正式开放预约。这也是该疫苗在深圳市的首次露面。不少市民了解到该信息时，发现约1060个名额（每天10个名额，开放至11月30日）全部约满9价HPV（人乳头瘤病毒）疫苗8月16日登陆深圳市罗湖中医院下属罗湖成人预防接种门诊，并于当日中午12点正式开放预约。这也是该疫苗在深圳市的首次露面。不少市民了解到该信息时，发现约1060个名额（每天10个名额，开放至11月30日）全部约满9价HPV（人乳头瘤病毒）疫苗8月16日登陆深圳市罗湖中医院下属罗湖成人预防接种门诊，并于当日中午12点正式开放预约。这也是该疫苗在深圳市的首次露面。不少市民了解到该信息时，发现约1060个名额（每天10个名额，开放至11月30日）全部约满9价HPV（人乳头瘤病毒）疫苗8月16日登陆深圳市罗湖中医院下属罗湖成人预防接种门诊，并于当日中午12点正式开放预约。这也是该疫苗在深圳市的首次露面。不少市民了解到该信息时，发现约1060个名额（每天10个名额，开放至11月30日）全部约满9价HPV（人乳头瘤病毒）疫苗8月16日登陆深圳市罗湖中医院下属罗湖成人预防接种门诊，并于当日中午12点正式开放预约。这也是该疫苗在深圳市的首次露面。不少市民了解到该信息时，发现约1060个名额（每天10个名额，开放至11月30日）全部约满";

                    break;
                case "UNNOTICE":
                    self.showNotice = false;
                    self.notice ='';
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
        var fullWidth = window.innerWidth;
        var fullHeight = window.innerHeight;

        $.get("../queue/queryListByPlayerAreaId/2", function(result){
            if(result.fileurls){
                result.fileurls.forEach(function(file){
                    self.playList.push(file.fileurl)
                })
            }
            self.player.src({type:'video/mp4',src:self.playList[self.current]})
        });
        $.get("../queue/notice/info/2", function(result){
            self.showNotice =  result['tQueueNotice']['show'];
            self.notice =  result['tQueueNotice']['content'];
            if(self.notice.length>120){
                var sec = self.notice.length/2;
                $('.marquee')[0].style.animationDuration=sec+'s';
            }
            self.player.src({type:'video/mp4',src:self.playList[self.current]})
        });
        document.querySelector('#observe-list').style.width = (fullWidth/4)-5 +'px';
        document.querySelector('#observe-list').style.height = innerHeight+'px';
        self.player = videojs('observe-video', {controls: true,autoplay: true,preload: 'auto',muted:false,width:fullWidth/4*3,height:fullHeight})
        self.player.on('ended',function(){
            self.current++;
            if(self.current >= self.playList.length){
                self.current = 0;
            }
            self.player.src({type:'video/mp4',src:self.playList[self.current]})
            self.player.play();
        })
        window.setInterval(function(){
            console.log(self.silentTime)
            if(self.silentTime>0){
                self.silentTime--;
                self.player.muted(true);
            }else{
                if(self.player.muted() == true){
                    self.player.muted(false);
                }
            }
            self.observeList.forEach(function(item,index){
                if(item.count == self.observeTime){
                    console.log((item.noText==null?"":item.noText+"号，")+(item.childName==null?"":item.childName)+"疫苗接种完成。请到留观室留观30分钟")
                    if(typeof extools !== 'undefined'){
                        extools.tts((item.noText==null || item.noText=="null"?"":item.noText+"号，")+(item.childName==null?"":item.childName)+"疫苗接种完成。请到留观室留观30分钟");
                    }
                }else if(item.count == 1 ){
                    self.silentTime+=7;

                }else if(item.count == 0){
                    console.log((item.noText==null?"":item.noText+"号，")+(item.childName==null?"":item.childName)+"留观时间已到。如无异常，可以离开。");
                    if(typeof extools !== 'undefined'){
                        extools.tts((item.noText==null || item.noText=="null"?"":item.noText+"号，")+(item.childName==null?"":item.childName)+"留观时间已到。如无异常，可以离开。");
                    }
                }else if (item.count <= -120){
                    self.observeList.splice(index,1)
                }

                item.count--;
            })
        },1000)

        $.get("../queue/getObserveNo", function(result){
            console.info(result);
            if(result){
                result.forEach(function(msg){
                    //var msg = JSON.parse(noMsg);
                    console.info(msg);
                    console.info("--------------"+msg.status);
                    msg.count = msg.status;//self.observeTime;
                    self.observeList.push(msg);
                    //self.silentTime+=7;
                })
            }
        });

    },

    destroyed:function(){
        this.client.disconnect();
        this.player.dispose();
    }

});


