package io.yfjz.entity.print;


/**
 * 这个类为avabean
 * @Author rsp
 * @Date 20180323 10:48
 * @Address 贵阳花果园大街贵阳国际中心2号楼
*/

public class ChildInfoBean {
    private String property;
    private int property_X;
    private int property_Y;

    public ChildInfoBean(){}

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int getProperty_X() {
        return property_X;
    }

    public void setProperty_X(int property_X) {
        this.property_X = property_X;
    }

    public int getProperty_Y() {
        return property_Y;
    }

    public void setProperty_Y(int property_Y) {
        this.property_Y = property_Y;
    }

   /* @Override
    public String toString() {
        return "InoculateDetailBean{" +
                "bacterinClassName='" + bacterinClassName + '\'' +
                ", page=" + page +
                ", inoculate_dateX=" + inoculate_dateX +
                ", inoculate_dateY=" + inoculate_dateY +
                ", textX=" + textX +
                ", textY=" + textY +
                ", batchnumX=" + batchnumX +
                ", batchnumY=" + batchnumY +
                ", factory_nameX=" + factory_nameX +
                ", factory_nameY=" + factory_nameY +
                ", departnameX=" + departnameX +
                ", departnameY=" + departnameY +
                '}';
    }*/
}
