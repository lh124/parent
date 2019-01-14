Vue.component('hallplayer',{
    template:"#hallplayer",
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
            player:{},
            playList:[],
            current:0,
            showNotice:false,
            notice:'',
            client:(function(){
                return self.init(self.host,self.type)
            })()
        }
    },
    mounted:function(){
        var self = this;
        var fullWidth = window.innerWidth;
        var fullHeight = window.innerHeight;
        self.player = videojs('hallplayer', {controls: true,autoplay: true,preload: 'auto',muted:false,width:fullWidth,height:fullHeight})
        $.get("../queue/queryListByPlayerAreaId/1", function(result){
            if(result.fileurls){
                result.fileurls.forEach(function(file){
                    self.playList.push(file.fileurl)
                })
            }
            self.player.src({type:'video/mp4',src:self.playList[self.current]})
        });
        $.get("../queue/notice/info/1", function(result){
            self.showNotice =  result['tQueueNotice']['show'];
            self.notice =  result['tQueueNotice']['content'];
            if(self.notice.length>120){
                var sec = self.notice.length/2;
                $('.marquee')[0].style.animationDuration=sec+'s';
            }
            self.player.src({type:'video/mp4',src:self.playList[self.current]})
        });
        self.player.on('ended',function(){
            self.current++;
            if(self.current >= self.playList.length){
                self.current = 0;
            }
            self.player.src({type:'video/mp4',src:self.playList[self.current]})
            self.player.play();
        })
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
                    $.get("../queue/queryListByPlayerAreaId/1", function(result){
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
                    $.get("../queue/notice/info/1", function(result){
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
    destroyed:function(){
        this.player.dispose();
        this.client.disconnect();
    }

});


