package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;


/**
 * @author Oceans <oceans.woods@gmail.com>
 * @date 2018/6/2
 */

@XStreamAlias("istabu")
public class Istabu implements Serializable {


    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(name = "chil_code")
    private String chilCode;

    @Column(name = "ista_code")
    @XStreamAlias("ista_code")
    private String istaCode;







    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIstaCode() {
        return istaCode;
    }

    public void setIstaCode(String istaCode) {
        this.istaCode = istaCode;
    }


   /* public int getIsFromPlat() {
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
    }*/

    public String getChilCode() {
        return chilCode;
    }

    public void setChilCode(String chilCode) {
        this.chilCode = chilCode;
    }
}
