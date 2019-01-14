package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import io.yfjz.entity.PersistentEntity;
import io.yfjz.utils.FormatableDoubleConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;

import javax.persistence.Transient;



/**
 * @author Oceans <oceans.woods@gmail.com>
 * @date 2018/5/27
 */

@XStreamAlias("child")
public class Child implements PersistentEntity ,Serializable{

    @GeneratedValue(generator = "system-uuid")
    private String id;

    //户籍属性
    @Column(name = "chil_account")
    @XStreamAlias("chil_account")
    private String chilAccount;
    //
    @Column(name = "chil_address")
    @XStreamAlias("chil_address")
    private String chilAddress;
    //
    @Column(name = "chil_age")
    @XStreamAlias("chil_age")
    private String ChilAge;
    //
    @Column(name = "chil_bcg_scar")
    @XStreamAlias("chil_bcg_scar")
    private String chilBcgScar;
    //
    private String chilLeaveremark;

    @Column(name = "chil_birthday")
    @XStreamAlias("chil_birthday")
    private Date chilBirthday;
    //
    @Column(name = "chil_birthno")
    @XStreamAlias("chil_birthno")
    private String chilBirthno;
    //
    @Column(name = "chil_birthweight")
    @XStreamAsAttribute
    @XStreamConverter(value=FormatableDoubleConverter.class, strings={"###,##0.0########"})
    @XStreamAlias("chil_birthweight")
    private Double chilBirthweight;
    //
    @Column(name = "chil_carddate")
    @XStreamAlias("chil_carddate")
    private Date chilCarddate;
    //
    @Column(name = "chil_cardno") //免疫卡号
    @XStreamAlias("chil_cardno")
    private String chilCardno;
    //
    @XStreamAlias("chil_code")
    @Column(name="chil_code")
    private String chilCode;
    //
    private String chilBirthhospitalname;

    @Column(name = "chil_createcounty")
    @XStreamAlias("chil_createcounty")
    private String chilCreatecounty;
    //建档日期
    @Column(name = "chil_createdate")
    @XStreamAlias("chil_createdate")
    private Date chilCreatedate;
    //
    @Column(name = "chil_createman")
    @XStreamAlias("chil_createman")
    private String chilCreateman;
    //
    @Column(name = "chil_createsite")
    @XStreamAlias("chil_createsite")
    private String chilCreatesite;
    //
    @Column(name = "ISSUEUNITNAME")

    private String createsiteName;
    //居委会
    private String chilCommittee;

    @Column(name = "chil_curdepartment")
    @XStreamAlias("chil_curdepartment")
    private String chilCurdepartment;
    //
    @Column(name = "chil_edit_date")
    @XStreamAlias("chil_edit_date")
    private Date chilEditDate;
    //
    @XStreamAlias("chil_father")
    @Column(name = "chil_father")
    private String chilFather;
    //
    @XStreamAlias("chil_fatherno")
    @Column(name = "chil_fatherno")
    private String chilFatherno;
    //户籍国标
    @Column(name = "chil_habi_id")
    @XStreamAlias("chil_habi_id")
    private String chilHabiId;
    //户籍地址
    @Column(name = "chil_habiaddress")
    @XStreamAlias("chil_habiaddress")
    private String chilHabiaddress;

    //检查是否转换ok
    @Column(name = "chil_here")
    @XStreamAlias("chil_here")
    private String chilHere;
    //
    @Column(name = "chil_inoctype")
    @XStreamAlias("chil_inoctype")
    private String chilInoctype;
    //
    @Column (name = "child_leavedate")
    @XStreamAlias("chil_leavedate")
    private String chilLeavedate;
    //

    private String chilLeave;

    @Column(name = "chil_mobile")
    @XStreamAlias("chil_mobile")
    private String chilMobile;
    //
    @Column(name = "chil_mother")
    @XStreamAlias("chil_mother")
    private String chilMother;
    //
    @Column(name = "chil_motherhb")
    @XStreamAlias("chil_motherhb")
    private String chilMotherhb;
    //
    @Column(name = "chil_motherhiv")
    @XStreamAlias("chil_motherhiv")
    private String chilMotherhiv;
    //
    @Column(name = "chil_motherno")
    @XStreamAlias("chil_motherno")
    private String chilMotherno;
    //
    @Column(name = "chil_name")
    @XStreamAlias("chil_name")
    private String chilName;
    //
    @Column(name = "chil_nati_id")
    @XStreamAlias("chil_nati_id")
    private String chilNatiId;
    //
    @Column(name = "chil_no")
    @XStreamAlias("chil_no")
    private String chilNo;
    //
    @Column(name = "chil_predepartment")
    @XStreamAlias("chil_predepartment")
    private String chilPredepartment;
    //未用到？？
    @Transient
    @XStreamAlias("chil_province")
    private String chilProvince;

    /**检查转换ok*/
    @Column(name = "chil_residence")
    @XStreamAlias("chil_residence")
    private String chilResidence;
    //
    @Column(name = "chil_residenceclient")
    @XStreamAlias("chil_residenceclient")
    private String chilResidenceclient;
    //
    @Column(name = "chil_sensitivity")
    @XStreamAlias("child_sensitivity")
    private String chilSensitivity;
    //
    @XStreamAlias("chil_sex")
    @Column(name = "chil_sex")
    private String chilSex;
    //
    @Column(name = "chil_tel")
    @XStreamAlias("chil_tel")
    private String chilTel;
    //
    @Column(name = "chil_unitimmu")
    @XStreamAlias("chil_unitimmu")
    private String chilUnitimmu;
    //
    @Column(name = "chil_birthhospital")
    @XStreamAlias("chil_birthhospital")
    private String chilBirthhospital;

    private String chilHealthno;

    @XStreamOmitField
    private Integer type;
    //同步状态,0未同步;1同步
    @XStreamOmitField
    private Integer syncstatus;
    //状态,0正常,-1删除
    @XStreamOmitField
    private Integer status;
    //创建人id
    @XStreamOmitField
    private String createUserId;
    //创建人名字
    @Column(name = "chil_createman")
    @XStreamAlias("chil_createman")
    private String createUserName;
    //创建时间
    @XStreamOmitField
    private Date createTime;
    //备注
    @XStreamOmitField
    private String remark;

    private Date createCardTime;

    //xml中没有，需自行加入
    @Column(name = "ORG_ID")
    private String orgId;


    /**
     * ##########################################################
     */



    @Column(name = "ISFROMPLAT")
    private int isFromPlat;

    public int getIsFromPlat() {
        return isFromPlat;
    }

    public void setIsFromPlat(int isFromPlat) {
        this.isFromPlat = isFromPlat;
    }

    @Transient
    @XStreamAlias("inoculations")
    private List<Inoculation> inoculationList = new ArrayList<>();

    @Transient
    @XStreamAlias("childheres")
    private List<ChildHere> childHeresList = new ArrayList<>();

    @Transient
    @XStreamAlias("infections")
    private List<Infection> infectionList = new ArrayList<>();

    public String getChilBirthhospitalname() {
        return chilBirthhospitalname;
    }

    public void setChilBirthhospitalname(String chilBirthhospitalname) {
        this.chilBirthhospitalname = chilBirthhospitalname;
    }

    public String getChilHealthno() {
        return chilHealthno;
    }

    public String getChilBirthhospital() {
        return chilBirthhospital;
    }

    public void setChilBirthhospital(String chilBirthhospital) {
        this.chilBirthhospital = chilBirthhospital;
    }

    public Date getCreateCardTime() {
        return createCardTime;
    }

    public void setCreateCardTime(Date createCardTime) {
        this.createCardTime = createCardTime;
    }

    public void setChilHealthno(String chilHealthno) {
        this.chilHealthno = chilHealthno;
    }

    public String getChilLeaveremark() {
        return chilLeaveremark;
    }

    public void setChilLeaveremark(String chilLeaveremark) {
        this.chilLeaveremark = chilLeaveremark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSyncstatus() {
        return syncstatus;
    }

    public void setSyncstatus(Integer syncstatus) {
        this.syncstatus = syncstatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    @XStreamAlias("aefis")
    private List<Aefi> aefiList = new ArrayList<>();

    @Transient
    @XStreamAlias("istabus")
    private List<Istabu> istabuList = new ArrayList<>();

    public String getCreatesiteName() {
        return createsiteName;
    }

    public void setCreatesiteName(String createsiteName) {
        this.createsiteName = createsiteName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }


    public String getChilAccount() {
        return chilAccount;
    }

    public void setChilAccount(String chilAccount) {
        this.chilAccount = chilAccount;
    }

    public String getChilAddress() {
        return chilAddress;
    }

    public void setChilAddress(String chilAddress) {
        this.chilAddress = chilAddress;
    }

    public String getChilAge() {
        return ChilAge;
    }

    public void setChilAge(String chilAge) {
        ChilAge = chilAge;
    }

    public String getChilBcgScar() {
        return chilBcgScar;
    }

    public void setChilBcgScar(String chilBcgScar) {
        this.chilBcgScar = chilBcgScar;
    }

    public Date getChilBirthday() {
        return chilBirthday;
    }

    public void setChilBirthday(Date chilBirthday) {
        this.chilBirthday = chilBirthday;
    }

    public String getChilBirthno() {
        return chilBirthno;
    }

    public void setChilBirthno(String chilBirthno) {
        this.chilBirthno = chilBirthno;
    }

    public Double getChilBirthweight() {
        return chilBirthweight;
    }

    public void setChilBirthweight(Double chilBirthweight) {
        this.chilBirthweight = chilBirthweight;
    }

    public Date getChilCarddate() {
        return chilCarddate;
    }

    public void setChilCarddate(Date chilCarddate) {
        this.chilCarddate = chilCarddate;
    }

    public String getChilCardno() {
        return chilCardno;
    }

    public void setChilCardno(String chilCardno) {
        this.chilCardno = chilCardno;
    }

    public String getChilCode() {
        return chilCode;
    }

    public void setChilCode(String chilCode) {
        this.chilCode = chilCode;
    }

    public String getChilCreatecounty() {
        return chilCreatecounty;
    }

    public void setChilCreatecounty(String chilCreatecounty) {
        this.chilCreatecounty = chilCreatecounty;
    }

    public Date getChilCreatedate() {
        return chilCreatedate;
    }

    public void setChilCreatedate(Date chilCreatedate) {
        this.chilCreatedate = chilCreatedate;
    }

    public String getChilCreateman() {
        return chilCreateman;
    }

    public void setChilCreateman(String chilCreateman) {
        this.chilCreateman = chilCreateman;
    }

    public String getChilCreatesite() {
        return chilCreatesite;
    }

    public void setChilCreatesite(String chilCreatesite) {
        this.chilCreatesite = chilCreatesite;
    }

    public String getChilCurdepartment() {
        return chilCurdepartment;
    }

    public void setChilCurdepartment(String chilCurdepartment) {
        this.chilCurdepartment = chilCurdepartment;
    }

    public Date getChilEditDate() {
        return chilEditDate;
    }

    public void setChilEditDate(Date chilEditDate) {
        this.chilEditDate = chilEditDate;
    }

    public String getChilFather() {
        return chilFather;
    }

    public void setChilFather(String chilFather) {
        this.chilFather = chilFather;
    }

    public String getChilFatherno() {
        return chilFatherno;
    }

    public void setChilFatherno(String chilFatherno) {
        this.chilFatherno = chilFatherno;
    }

    public String getChilHabiId() {
        return chilHabiId;
    }

    public void setChilHabiId(String chilHabiId) {
        this.chilHabiId = chilHabiId;
    }

    public String getChilHabiaddress() {
        return chilHabiaddress;
    }

    public void setChilHabiaddress(String chilHabiaddress) {
        this.chilHabiaddress = chilHabiaddress;
    }

    public String getChilHere() {
        return chilHere;
    }

    public void setChilHere(String chilHere) {
        this.chilHere = chilHere;
    }

    public String getChilInoctype() {
        return chilInoctype;
    }

    public void setChilInoctype(String chilInoctype) {
        this.chilInoctype = chilInoctype;
    }

    public String getChilLeavedate() {
        return chilLeavedate;
    }

    public void setChilLeavedate(String chilLeavedate) {
        this.chilLeavedate = chilLeavedate;
    }

    public String getChilMobile() {
        return chilMobile;
    }

    public void setChilMobile(String chilMobile) {
        this.chilMobile = chilMobile;
    }

    public String getChilMother() {
        return chilMother;
    }

    public void setChilMother(String chilMother) {
        this.chilMother = chilMother;
    }

    public String getChilMotherhb() {
        return chilMotherhb;
    }

    public void setChilMotherhb(String chilMotherhb) {
        this.chilMotherhb = chilMotherhb;
    }

    public String getChilMotherhiv() {
        return chilMotherhiv;
    }

    public void setChilMotherhiv(String chilMotherhiv) {
        this.chilMotherhiv = chilMotherhiv;
    }

    public String getChilMotherno() {
        return chilMotherno;
    }

    public void setChilMotherno(String chilMotherno) {
        this.chilMotherno = chilMotherno;
    }

    public String getChilName() {
        return chilName;
    }

    public void setChilName(String chilName) {
        this.chilName = chilName;
    }

    public String getChilNatiId() {
        return chilNatiId;
    }

    public void setChilNatiId(String chilNatiId) {
        this.chilNatiId = chilNatiId;
    }

    public String getChilNo() {
        return chilNo;
    }

    public void setChilNo(String chilNo) {
        this.chilNo = chilNo;
    }

    public String getChilPredepartment() {
        return chilPredepartment;
    }

    public void setChilPredepartment(String chilPredepartment) {
        this.chilPredepartment = chilPredepartment;
    }

    public String getChilProvince() {
        return chilProvince;
    }

    public void setChilProvince(String chilProvince) {
        this.chilProvince = chilProvince;
    }

    public String getChilResidence() {
        return chilResidence;
    }

    public void setChilResidence(String chilResidence) {
        this.chilResidence = chilResidence;
    }

    public String getChilResidenceclient() {
        return chilResidenceclient;
    }

    public void setChilResidenceclient(String chilResidenceclient) {
        this.chilResidenceclient = chilResidenceclient;
    }

    public String getChilSensitivity() {
        return chilSensitivity;
    }

    public void setChilSensitivity(String chilSensitivity) {
        this.chilSensitivity = chilSensitivity;
    }

    public String getChilSex() {
        return chilSex;
    }

    public void setChilSex(String chilSex) {
        this.chilSex = chilSex;
    }

    public String getChilTel() {
        return chilTel;
    }

    public void setChilTel(String chilTel) {
        this.chilTel = chilTel;
    }

    public String getChilUnitimmu() {
        return chilUnitimmu;
    }

    public void setChilUnitimmu(String chilUnitimmu) {
        this.chilUnitimmu = chilUnitimmu;
    }


    public List<Inoculation> getInoculationList() {
        return inoculationList;
    }

    public void setInoculationList(List<Inoculation> inoculationList) {
        this.inoculationList = inoculationList;
    }

    public List<ChildHere> getChildHeresList() {
        return childHeresList;
    }

    public void setChildHeresList(List<ChildHere> childHeresList) {
        this.childHeresList = childHeresList;
    }

    public List<Infection> getInfectionList() {
        return infectionList;
    }

    public void setInfectionList(List<Infection> infectionList) {
        this.infectionList = infectionList;
    }

    public List<Aefi> getAefiList() {
        return aefiList;
    }

    public void setAefiList(List<Aefi> aefiList) {
        this.aefiList = aefiList;
    }

    public List<Istabu> getIstabuList() {
        return istabuList;
    }

    public void setIstabuList(List<Istabu> istabuList) {
        this.istabuList = istabuList;
    }

    public String getChilCommittee() {
        return chilCommittee;
    }

    public void setChilCommittee(String chilCommittee) {
        this.chilCommittee = chilCommittee;
    }

    public String getChilLeave() {
        return chilLeave;
    }

    public void setChilLeave(String chilLeave) {
        this.chilLeave = chilLeave;
    }
}