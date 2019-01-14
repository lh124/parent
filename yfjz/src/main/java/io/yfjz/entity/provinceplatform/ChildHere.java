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

@XStreamAlias("childhere")

public class ChildHere implements Serializable {

    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(name = "child_code")
    private String childCode;

    @Column(name = "move_date")
    @XStreamAlias("chhe_changedate")
    private Date changedate;

    @Column(name = "move_code")
    private String moveCode;

    @Column(name = "chhe_chil_id")
    @XStreamAlias("chhe_chil_id")
    private String chheChilId;

    @Column(name = "chhe_depa_id")
    @XStreamAlias("chhe_depa_id")
    private String chheDepaId;

    @Column(name = "here")
    @XStreamAlias("chhe_here")
    private String chheHere;

    @Column(name = "prehere")
    @XStreamAlias("chhe_prehere")
    private String chhePrehere;

    @XStreamAlias("chhe_chil_province")
    private String chheChilProvince;

    @XStreamAlias("chhe_chil_provinceremark")
    private String chheChilProvinceremark;

    public String getChheChilProvince() {
        return chheChilProvince;
    }

    public void setChheChilProvince(String chheChilProvince) {
        this.chheChilProvince = chheChilProvince;
    }

    public String getChheChilProvinceremark() {
        return chheChilProvinceremark;
    }

    public void setChheChilProvinceremark(String chheChilProvinceremark) {
        this.chheChilProvinceremark = chheChilProvinceremark;
    }

    public String getMoveCode() {
        return moveCode;
    }

    public void setMoveCode(String moveCode) {
        this.moveCode = moveCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getChangedate() {
        return changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    public String getChheChilId() {
        return chheChilId;
    }

    public void setChheChilId(String chheChilId) {
        this.chheChilId = chheChilId;
    }

    public String getChheDepaId() {
        return chheDepaId;
    }

    public void setChheDepaId(String chheDepaId) {
        this.chheDepaId = chheDepaId;
    }

    public String getChheHere() {
        return chheHere;
    }

    public void setChheHere(String chheHere) {
        this.chheHere = chheHere;
    }

    public String getChhePrehere() {
        return chhePrehere;
    }

    public void setChhePrehere(String chhePrehere) {
        this.chhePrehere = chhePrehere;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }


}
