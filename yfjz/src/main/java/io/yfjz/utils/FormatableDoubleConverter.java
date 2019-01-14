package io.yfjz.utils;

import com.thoughtworks.xstream.converters.basic.DoubleConverter;

import java.text.DecimalFormat;

/**
 * xml转实体Double 转格式
 * @author 饶士培
 * @date 2018/7/25
 */

public class FormatableDoubleConverter extends DoubleConverter {
    private String pattern;
    private DecimalFormat formatter;

    public FormatableDoubleConverter(String pattern) {
        this.pattern = pattern;
        this.formatter = new DecimalFormat(pattern);
    }

    @Override
    public String toString(Object obj) {
        if(formatter == null) {
            return super.toString(obj);
        } else {
            return formatter.format(obj);
        }
    }

    @Override
    public Object fromString(String str) {
        if(str!=null && str.trim().length()<=0){
            str = "0";
        }
        try {
            return super.fromString(str);
        } catch(Exception ex) {
            if(formatter != null) {
                try {
                    return formatter.parse(str);
                } catch(Exception e) {
                    throw new IllegalArgumentException("Cannot parse <" + str + "> to double value", e);
                }
            }
            throw new IllegalArgumentException("Cannot parse <" + str + "> to double value", ex);
        }
    }

    public String getPattern() {
        return pattern;
    }
}
