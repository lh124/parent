package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


/**
 * @author Oceans <oceans.woods@gmail.com>
 * @date 2018/5/28
 */

@Getter
@Setter
@XStreamAlias("newbornInoculate")
public class NewbornInoculate extends Inoculation implements Serializable{

    @GeneratedValue(generator = "system-uuid")
    private String id;
    private String childCode;
    //
    //@Column(name = "inoc_bact_code")
    @Transient
    @XStreamAlias("nebi_bact_code")
    private String inocBactCode;

    //

    @XStreamAlias("nebi_batchno")
    private String inocBatchno;

    @XStreamAlias("nebi_bact_no")
    private String inocBactNo;
    //

    @XStreamAlias("nebi_corp_code")
    private String inocCorpCode;
    //

    @XStreamAlias("inoc_county")
    private String inocCounty;
    //
    @XStreamAlias("nebi_date")
    private Date inocDate;
    //
    @XStreamAlias("nebi_editdate")
    private Date inocEditdate;
    //
    @XStreamAlias("nebi_free")
    private String inocFree;
    //
    @XStreamAlias("nebi_inpl_id")
    private String inocInplId;

    //
    @XStreamAlias("nebi_nurse")
    private String inocNurse;

    //
    @XStreamAlias("nebi_time")
    private int inocTime;

    @XStreamAlias("nebi_inoculateway")
    private int inocuWay;

    @XStreamAlias("nebi_dose")
    private String inocDose;


    @XStreamAlias("nebi_depa_id")
    public String inocDepaId;


}
