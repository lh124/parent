package io.yfjz.utils;

import com.thoughtworks.xstream.converters.basic.DateConverter;

import java.util.TimeZone;

/**
 * @author Oceans <oceans.woods@gmail.com>
 * @date 2018/6/2
 */

public class CustomDateConverter extends DateConverter {
    public CustomDateConverter(){
        super();
    }

    @Override
    public Object fromString(String str) {
        if(str!=null && str.length()<10){
            return null;
        }
        if(str!=null && str.length()<15){
            return super.fromString(str);
        }
        return super.fromString(str.split("\\.")[0]);
    }

    public CustomDateConverter(String format,String[] formats,TimeZone timeZone){
        super(format,formats,timeZone);
    }
}
