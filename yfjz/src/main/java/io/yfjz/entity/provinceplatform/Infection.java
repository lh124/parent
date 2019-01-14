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

@XStreamAlias("infection")
public class Infection implements Serializable {

    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(name = "child_code")
    private String childCode;

    @Column(name = "r1")
    @XStreamAlias("infe_code")
    private String infeCode;

    @Column(name = "happend_date")
    @XStreamAlias("infe_date")
    private Date infeDate;



    public String getInfeCode() {
        return infeCode;
    }

    public void setInfeCode(String infeCode) {
        this.infeCode = infeCode;
    }

    public Date getInfeDate() {
        return infeDate;
    }

    public void setInfeDate(Date infeDate) {
        this.infeDate = infeDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }


}
