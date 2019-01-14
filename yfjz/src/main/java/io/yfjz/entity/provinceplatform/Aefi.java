package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;


/**
 * @author Oceans <oceans.woods@gmail.com>
 * @date 2018/6/2
 */

@XStreamAlias("aefi")

public class Aefi implements Serializable{


    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(name = "r2")
    @XStreamAlias("aefi_bact_code")
    private String aefiBactCode;

    @Column(name = "happend_date")
    @XStreamAlias("aefi_date")
    private Date aefiDate;

    @Column(name = "aefi_code")
    @XStreamAlias("aefi_code")
    private String aefiCode;

    @Column(name = "child_code")
    private String childCode;

    @Column(name = "ISFROMPLAT")
    private int isFromPlat;

    @Column(name = "ISUPLOADPLAT")
    private int isUploadPlat;

    public int getIsFromPlat() {
        return isFromPlat;
    }

    public void setIsFromPlat(int isFromPlat) {
        this.isFromPlat = isFromPlat;
    }

    public int getIsUploadPlat() {
        return isUploadPlat;
    }

    public void setIsUploadPlat(int isUploadPlat) {
        this.isUploadPlat = isUploadPlat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAefiBactCode() {
        return aefiBactCode;
    }

    public void setAefiBactCode(String aefiBactCode) {
        this.aefiBactCode = aefiBactCode;
    }

    public Date getAefiDate() {
        return aefiDate;
    }

    public void setAefiDate(Date aefiDate) {
        this.aefiDate = aefiDate;
    }

    public String getAefiCode() {
        return aefiCode;
    }

    public void setAefiCode(String aefiCode) {
        this.aefiCode = aefiCode;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }


}
