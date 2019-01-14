package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;


/**
 * @author Oceans <oceans.woods@gmail.com>
 * @date 2018/5/28
 */


@XStreamAlias("inoculation")
public class Inoculation implements Serializable{

    @GeneratedValue(generator = "system-uuid")
    private String id;
    //
    @Column(name = "child_id")
    private String childCode;

    //转换vaccCode
    @Column(name = "vaccinecode")
    private String vaccineCode;
    //
    //@Column(name = "inoc_bact_code")
    @Transient
    @XStreamAlias("inoc_bact_code")
    private String inocBactCode;
    //
    @Column(name = "vaccine_property")
    private String vaccineProperty;
    //
    @Column(name = "batchnum")
    @XStreamAlias("inoc_batchno")
    private String inocBatchno;
    //
    @Column(name = "factory_code")
    @XStreamAlias("inoc_corp_code")
    private String inocCorpCode;
    //
    @Column(name = "inoc_countycode")
    @XStreamAlias("inoc_county")
    private String inocCounty;
    //
    @Column(name = "inoculate_date")
    @XStreamAlias("inoc_date")
    private Date inocDate;
    //
    @Column(name = "updatetime")
    @XStreamAlias("inoc_editdate")
    private Date inocEditdate;
    //
    @Column(name = "ismf")
    @XStreamAlias("inoc_free")
    private String inocFree;
    //
    @Column(name = "inoculate_site_code")
    @XStreamAlias("inoc_inpl_id")
    private String inocInplId;

    @Transient
    @XStreamAlias("inoc_modify_code")
    private String inocModifyCode;
    //
    @Column(name = "inoc_nationcode")
    @XStreamAlias("inoc_nationcode")
    private String inocNationcode;
    //
    @Column(name = "jzhs")
    @XStreamAlias("inoc_nurse")
    private String inocNurse;
    //
    @Column(name = "estimate")
    @XStreamAlias("inoc_opinion")
    private String inocOpinion;
    //
    @Column(name = "inoc_pay")
    @XStreamAlias("inoc_pay")
    private String inocPay;
    //
    @Column(name = "inoc_property")
    @XStreamAlias("inoc_property")
    private String inocProperty;
    //
    @Column(name = "inoc_reinforce")
    @XStreamAlias("inoc_reinforce")
    private String inocReinforce;
    //
    @Column(name = "dose_no")
    @XStreamAlias("inoc_time")
    private int inocTime;
    //
    @Column(name = "inoc_union_code")
    @XStreamAlias("inoc_union_code")
    private String inocUnionCode;
    //
    @Column(name = "inoc_useticket")
    @XStreamAlias("inoc_useticket")
    private String inocUseticket;
    //
    @Column(name = "inoc_validdate")
    @XStreamAlias("inoc_validdate")
    private Date inocValiddate;
    //
    @Column(name = "inoc_vcn_kind")
    @XStreamAlias("inoc_vcn_kind")
    private String inocVcnKind;
    //
    @Column(name = "zxhs")
    @XStreamAlias("zxhs")
    private String zxhs;

    //
    @Column(name = "stock_out_id")
    private String stockOutId;

    //
    @Transient
    @XStreamAlias("inoc_depa_Code")
     private String inocDepaCode;


    //
    @Column(name = "inoc_depa_id")
    @XStreamAlias("inoc_depa_id")
    public String inocDepaId;

    @XStreamAlias("inoc_regulatory_code")
    public String inocRegulatoryCode;


    public String getVaccineProperty() {
        return vaccineProperty;
    }

    public void setVaccineProperty(String vaccineProperty) {
        this.vaccineProperty = vaccineProperty;
    }

    public String getInocDepaCode() {
        return inocDepaCode;
    }

    public void setInocDepaCode(String inocDepaCode) {
        this.inocDepaCode = inocDepaCode;
    }

    public String getInocDepaId() {
        return inocDepaId;
    }

    public void setInocDepaId(String inocDepaId) {
        this.inocDepaId = inocDepaId;
    }

    public String getVaccineCode() {
        return vaccineCode;
    }

    public void setVaccineCode(String vaccineCode) {
        this.vaccineCode = vaccineCode;
    }


    public String getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(String stockOutId) {
        this.stockOutId = stockOutId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInocBactCode() {
        return inocBactCode;
    }

    public void setInocBactCode(String inocBactCode) {
        this.inocBactCode = inocBactCode;
    }

    public String getInocBatchno() {
        return inocBatchno;
    }

    public void setInocBatchno(String inocBatchno) {
        this.inocBatchno = inocBatchno;
    }

    public String getInocCorpCode() {
        return inocCorpCode;
    }

    public void setInocCorpCode(String inocCorpCode) {
        this.inocCorpCode = inocCorpCode;
    }

    public String getInocCounty() {
        return inocCounty;
    }

    public void setInocCounty(String inocCounty) {
        this.inocCounty = inocCounty;
    }

    public Date getInocDate() {
        return inocDate;
    }

    public void setInocDate(Date inocDate) {
        this.inocDate = inocDate;
    }

    public Date getInocEditdate() {
        return inocEditdate;
    }

    public void setInocEditdate(Date inocEditdate) {
        this.inocEditdate = inocEditdate;
    }

    public String getInocFree() {
        return inocFree;
    }

    public void setInocFree(String inocFree) {
        this.inocFree = inocFree;
    }

    public String getInocInplId() {
        return inocInplId;
    }

    public void setInocInplId(String inocInplId) {
        this.inocInplId = inocInplId;
    }

    public String getInocModifyCode() {
        return inocModifyCode;
    }

    public void setInocModifyCode(String inocModifyCode) {
        this.inocModifyCode = inocModifyCode;
    }

    public String getInocNationcode() {
        return inocNationcode;
    }

    public void setInocNationcode(String inocNationcode) {
        this.inocNationcode = inocNationcode;
    }

    public String getInocNurse() {
        return inocNurse;
    }

    public void setInocNurse(String inocNurse) {
        this.inocNurse = inocNurse;
    }

    public String getInocOpinion() {
        return inocOpinion;
    }

    public void setInocOpinion(String inocOpinion) {
        this.inocOpinion = inocOpinion;
    }

    public String getInocPay() {
        return inocPay;
    }

    public void setInocPay(String inocPay) {
        this.inocPay = inocPay;
    }

    public String getInocProperty() {
        return inocProperty;
    }

    public void setInocProperty(String inocProperty) {
        this.inocProperty = inocProperty;
    }

    public String getInocReinforce() {
        return inocReinforce;
    }

    public void setInocReinforce(String inocReinforce) {
        this.inocReinforce = inocReinforce;
    }

    public int getInocTime() {
        return inocTime;
    }

    public void setInocTime(int inocTime) {
        this.inocTime = inocTime;
    }

    public String getInocUnionCode() {
        return inocUnionCode;
    }

    public void setInocUnionCode(String inocUnionCode) {
        this.inocUnionCode = inocUnionCode;
    }

    public String getInocUseticket() {
        return inocUseticket;
    }

    public void setInocUseticket(String inocUseticket) {
        this.inocUseticket = inocUseticket;
    }

    public Date getInocValiddate() {
        return inocValiddate;
    }

    public void setInocValiddate(Date inocValiddate) {
        this.inocValiddate = inocValiddate;
    }

    public String getInocVcnKind() {
        return inocVcnKind;
    }

    public void setInocVcnKind(String inocVcnKind) {
        this.inocVcnKind = inocVcnKind;
    }

    public String getZxhs() {
        return zxhs;
    }

    public void setZxhs(String zxhs) {
        this.zxhs = zxhs;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getInocRegulatoryCode() {
        return inocRegulatoryCode;
    }

    public void setInocRegulatoryCode(String inocRegulatoryCode) {
        this.inocRegulatoryCode = inocRegulatoryCode;
    }
}
