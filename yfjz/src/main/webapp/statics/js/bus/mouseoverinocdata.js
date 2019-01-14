function dateDiff(d1,d2){//计算两个日期之间相差多少年多少月多少天
    d1 = new Date(d1.replace(/-/g,'/'));
    d2 = new Date(d2.replace(/-/g,'/'));
    var obj={},
        M1=d1.getMonth()+1,
        D1=d1.getDate(),
        M2=d2.getMonth()+1,
        D2=d2.getDate();
    obj.Y=d2.getFullYear() - d1.getFullYear() + (M1*100+D1 > M2*100+D2 ? -1 :0);//年份计
    obj.M=(d2.getFullYear() - d1.getFullYear()) * 12 + M2 - M1 + (D1 > D2 ? -1 : 0);
    if(D2>D1){//结束时间的日期大于开始时间的日期，则相差天数为结束日期天数减开始日 期天数加 1
        obj.D=D2-D1;
        if(M2==1||M2==3||M2==5||M2==7||M2==8||M2==10||M2==12){
            setDay(obj,"31");
        }else if(M2==4||M2==6||M2==9||M2==11){
            setDay(obj,"30");
        }else if(M2==2){
            if(d2.getFullYear()%4==0){ setDay(obj,"29");
            }else{setDay(obj,"28");
            }
        }
    }else if(D2==D1){//结束时间的日期等于开始时间的日期，则相差天数为 1
        obj.D=1;
    }else{//结束时间的日期小于于开始时间的日期，则相差天数为开始时间所在月的天数减去开始时间日期加上结束时间的日期
        if(M1==1||M1==3||M1==5||M1==7||M1==8||M1==10||M1==12){
            obj.D=31-D1+D2;
            setDay(obj,"31");
        }else if(M1==4||M1==6||M1==9||M1==11){
            obj.D=30-D1+D2;
            setDay(obj,"30");
        }else if(M1==2){
            if(d1.getFullYear()%4==0){
                obj.D=29-D1+D2;
                setDay(obj,"29");
            }else{
                obj.D=28-D1+D2;
                setDay(obj,"28");
            }
        };
    };
    obj.M=obj.M>=12?obj.M%12:obj.M;
    var text="";
    if(obj.D>0){
        text=obj.D+"天";
    };
    if(obj.M>0){
        text=obj.M+"个月"+text;
        if(obj.Y>0){
            text=obj.Y+"年"+text;
        }
    };
    if(obj.M==0){
        if(obj.Y>0){
            if(text){
                text=obj.Y+"年零"+text;
            }else{
                text=obj.Y+"年";
            };
        };
    };
    return text;
}
function setDay(obj,dTime) {//当两个时间通过计算后的相差天数等于开始时间所在月的天数时，月份加一，天数重置为零
    if (obj.D >= parseInt(dTime)) {
        obj.D = obj.D - parseInt(dTime);
        obj.M = obj.M >= 12 ? obj.M % 12 : obj.M;
        obj.M += 1;
        if (obj.M >= 12) {
            obj.Y += 1;
        }
    }
}