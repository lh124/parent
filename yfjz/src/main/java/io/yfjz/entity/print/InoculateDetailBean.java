package io.yfjz.entity.print;


/**
 * 这个类为修改的javabean
 * @Author smile
 * @Date 20180317 19:48
 * @Address 贵阳花果园大街贵阳国际中心2号楼
 */
public class InoculateDetailBean {
    private String bacterinClassName;
    private int page;
    private int inoculate_dateX;
    private int inoculate_dateY;
    private int textX;
    private int textY;
    private int batchnumX;
    private int batchnumY;
    private int factory_nameX;
    private int factory_nameY;
    private int departnameX;
    private int departnameY;
    private int doctorX;
    private int doctorY;
    private int expiration_dateX;
    private int expiration_dateY;

    public int getExpiration_dateX() {
        return expiration_dateX;
    }

    public void setExpiration_dateX(int expiration_dateX) {
        this.expiration_dateX = expiration_dateX;
    }

    public int getExpiration_dateY() {
        return expiration_dateY;
    }

    public void setExpiration_dateY(int expiration_dateY) {
        this.expiration_dateY = expiration_dateY;
    }

    public String getBacterinClassName() {
        return bacterinClassName;
    }

    public void setBacterinClassName(String bacterinClassName) {
        this.bacterinClassName = bacterinClassName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getInoculate_dateX() {
        return inoculate_dateX;
    }

    public void setInoculate_dateX(int inoculate_dateX) {
        this.inoculate_dateX = inoculate_dateX;
    }

    public int getInoculate_dateY() {
        return inoculate_dateY;
    }

    public void setInoculate_dateY(int inoculate_dateY) {
        this.inoculate_dateY = inoculate_dateY;
    }

    public int getTextX() {
        return textX;
    }

    public void setTextX(int textX) {
        this.textX = textX;
    }

    public int getTextY() {
        return textY;
    }

    public void setTextY(int textY) {
        this.textY = textY;
    }

    public int getBatchnumX() {
        return batchnumX;
    }

    public void setBatchnumX(int batchnumX) {
        this.batchnumX = batchnumX;
    }

    public int getBatchnumY() {
        return batchnumY;
    }

    public void setBatchnumY(int batchnumY) {
        this.batchnumY = batchnumY;
    }

    public int getFactory_nameX() {
        return factory_nameX;
    }

    public void setFactory_nameX(int factory_nameX) {
        this.factory_nameX = factory_nameX;
    }

    public int getFactory_nameY() {
        return factory_nameY;
    }

    public void setFactory_nameY(int factory_nameY) {
        this.factory_nameY = factory_nameY;
    }

    public int getDepartnameX() {
        return departnameX;
    }

    public void setDepartnameX(int departnameX) {
        this.departnameX = departnameX;
    }

    public int getDepartnameY() {
        return departnameY;
    }

    public void setDepartnameY(int departnameY) {
        this.departnameY = departnameY;
    }

    public int getDoctorX() {
        return doctorX;
    }

    public void setDoctorX(int doctorX) {
        this.doctorX = doctorX;
    }

    public int getDoctorY() {
        return doctorY;
    }

    public void setDoctorY(int doctorY) {
        this.doctorY = doctorY;
    }

    @Override
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
    }
}
