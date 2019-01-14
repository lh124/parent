package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.Date;



/**
 * 儿童基本信息表
 *
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 15:02:10
 */
@XStreamAlias("children")
public class ProvincePlatformChildDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @XStreamAlias("child")
        private Child child;

        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }

    //儿童编码
    @XStreamAlias("child")
   static class Child implements Serializable{

        private String chilCode;
        //姓名
        private String chilName;
        //性别
        private String chilSex;
        //出生日期
        private Date chilBirthday;
        //出生体重
        private Double chilBirthweight;
        //民族
        private String chilNatiId;
        //免疫卡号
        private String chilCardno;
        //身份证号
        private String chilNo;
        //出生证号
        private String chilBirthno;
        //母亲姓名
        private String chilMother;
        //父亲姓名
        private String chilFather;
        //行政村/居委会
        private String chilCommittee;
        //学校
        private String chilSchool;
        //户籍地址
        private String chilHabiaddress;
        //家庭住址
        private String chilAddress;
        //家庭电话
        private String chilTel;
        //手机
        private String chilMobile;
        //发卡日期
        private Date chilCarddate;
        //健康档案标识
        private String chilHealthno;
        //平台字段,未知属性
        private String chilResidenceclient;
        //居住属性
        private String chilResidence;
        //户籍属性
        private String chilAccount;
        //户籍县国标
        private String chilHabiId;
        //平台字段,未知属性
        private String chilInoctype;
        //过敏史
        private String chilSensitivity;
        //母亲HB
        private String chilMotherhb;
        //母亲HIV
        private String chilMotherhiv;
        //个案状态
        private String chilHere;
        //在册变化日期
        private Date chilLeavedate;
        //迁出分类
        private String chilLeave;
        //迁出其他备注
        private String chilLeaveremark;
        //现管理单位编码
        private String chilCurdepartment;
        //前管理单位编码
        private String chilPredepartment;
        //建档县国标
        private String chilCreatecounty;
        //建档单位编码
        private String chilCreatesite;
        //建档日期
        private Date chilCreatedate;
        //建卡人
        private String chilCreateman;
        //受种者单位
        private String chilUnitimmu;
        //出生医院编码
        private String chilBirthhospital;
        //出生医院名称
        private String chilBirthhospitalname;
        //是否有卡介苗疤痕
        private String chilBcgScar;
        //母亲身份证号
        private Integer chilMotherno;
        //父亲身份证号
        private Integer chilFatherno;
        //出省状态
        private String chilProvince;
        //更新时间
        private Date chilEditDate;
        //建卡时间
        private Date createCardTime;
        //type,0本地,1平台
        private Integer type;
        //同步状态,0未同步;1同步
        private Integer syncstatus;
        //状态,0正常,-1删除
        private Integer status;
        //创建人id
        private String createUserId;
        //创建人名字
        private String createUserName;
        //创建时间
        private Date createTime;
        //备注
        private String remark;

        /**
         * 设置：儿童编码
         */
        public void setChilCode(String chilCode) {
            this.chilCode = chilCode;
        }
        /**
         * 获取：儿童编码
         */
        public String getChilCode() {
            return chilCode;
        }
        /**
         * 设置：姓名
         */
        public void setChilName(String chilName) {
            this.chilName = chilName;
        }
        /**
         * 获取：姓名
         */
        public String getChilName() {
            return chilName;
        }
        /**
         * 设置：性别
         */
        public void setChilSex(String chilSex) {
            this.chilSex = chilSex;
        }
        /**
         * 获取：性别
         */
        public String getChilSex() {
            return chilSex;
        }
        /**
         * 设置：出生日期
         */
        public void setChilBirthday(Date chilBirthday) {
            this.chilBirthday = chilBirthday;
        }
        /**
         * 获取：出生日期
         */
        public Date getChilBirthday() {
            return chilBirthday;
        }
        /**
         * 设置：出生体重
         */
        public void setChilBirthweight(Double chilBirthweight) {
            this.chilBirthweight = chilBirthweight;
        }
        /**
         * 获取：出生体重
         */
        public Double getChilBirthweight() {
            return chilBirthweight;
        }
        /**
         * 设置：民族
         */
        public void setChilNatiId(String chilNatiId) {
            this.chilNatiId = chilNatiId;
        }
        /**
         * 获取：民族
         */
        public String getChilNatiId() {
            return chilNatiId;
        }
        /**
         * 设置：免疫卡号
         */
        public void setChilCardno(String chilCardno) {
            this.chilCardno = chilCardno;
        }
        /**
         * 获取：免疫卡号
         */
        public String getChilCardno() {
            return chilCardno;
        }
        /**
         * 设置：身份证号
         */
        public void setChilNo(String chilNo) {
            this.chilNo = chilNo;
        }
        /**
         * 获取：身份证号
         */
        public String getChilNo() {
            return chilNo;
        }
        /**
         * 设置：出生证号
         */
        public void setChilBirthno(String chilBirthno) {
            this.chilBirthno = chilBirthno;
        }
        /**
         * 获取：出生证号
         */
        public String getChilBirthno() {
            return chilBirthno;
        }
        /**
         * 设置：母亲姓名
         */
        public void setChilMother(String chilMother) {
            this.chilMother = chilMother;
        }
        /**
         * 获取：母亲姓名
         */
        public String getChilMother() {
            return chilMother;
        }
        /**
         * 设置：父亲姓名
         */
        public void setChilFather(String chilFather) {
            this.chilFather = chilFather;
        }
        /**
         * 获取：父亲姓名
         */
        public String getChilFather() {
            return chilFather;
        }
        /**
         * 设置：行政村/居委会
         */
        public void setChilCommittee(String chilCommittee) {
            this.chilCommittee = chilCommittee;
        }
        /**
         * 获取：行政村/居委会
         */
        public String getChilCommittee() {
            return chilCommittee;
        }
        /**
         * 设置：户籍地址
         */
        public void setChilHabiaddress(String chilHabiaddress) {
            this.chilHabiaddress = chilHabiaddress;
        }
        /**
         * 获取：户籍地址
         */
        public String getChilHabiaddress() {
            return chilHabiaddress;
        }
        /**
         * 设置：家庭住址
         */
        public void setChilAddress(String chilAddress) {
            this.chilAddress = chilAddress;
        }
        /**
         * 获取：家庭住址
         */
        public String getChilAddress() {
            return chilAddress;
        }
        /**
         * 设置：家庭电话
         */
        public void setChilTel(String chilTel) {
            this.chilTel = chilTel;
        }
        /**
         * 获取：家庭电话
         */
        public String getChilTel() {
            return chilTel;
        }
        /**
         * 设置：手机
         */
        public void setChilMobile(String chilMobile) {
            this.chilMobile = chilMobile;
        }
        /**
         * 获取：手机
         */
        public String getChilMobile() {
            return chilMobile;
        }
        /**
         * 设置：发卡日期
         */
        public void setChilCarddate(Date chilCarddate) {
            this.chilCarddate = chilCarddate;
        }
        /**
         * 获取：发卡日期
         */
        public Date getChilCarddate() {
            return chilCarddate;
        }
        /**
         * 设置：健康档案标识
         */
        public void setChilHealthno(String chilHealthno) {
            this.chilHealthno = chilHealthno;
        }
        /**
         * 获取：健康档案标识
         */
        public String getChilHealthno() {
            return chilHealthno;
        }
        /**
         * 设置：平台字段,未知属性
         */
        public void setChilResidenceclient(String chilResidenceclient) {
            this.chilResidenceclient = chilResidenceclient;
        }
        /**
         * 获取：平台字段,未知属性
         */
        public String getChilResidenceclient() {
            return chilResidenceclient;
        }
        /**
         * 设置：居住属性
         */
        public void setChilResidence(String chilResidence) {
            this.chilResidence = chilResidence;
        }
        /**
         * 获取：居住属性
         */
        public String getChilResidence() {
            return chilResidence;
        }
        /**
         * 设置：户籍属性
         */
        public void setChilAccount(String chilAccount) {
            this.chilAccount = chilAccount;
        }
        /**
         * 获取：户籍属性
         */
        public String getChilAccount() {
            return chilAccount;
        }
        /**
         * 设置：户籍县国标
         */
        public void setChilHabiId(String chilHabiId) {
            this.chilHabiId = chilHabiId;
        }
        /**
         * 获取：户籍县国标
         */
        public String getChilHabiId() {
            return chilHabiId;
        }
        /**
         * 设置：平台字段,未知属性
         */
        public void setChilInoctype(String chilInoctype) {
            this.chilInoctype = chilInoctype;
        }
        /**
         * 获取：平台字段,未知属性
         */
        public String getChilInoctype() {
            return chilInoctype;
        }
        /**
         * 设置：过敏史
         */
        public void setChilSensitivity(String chilSensitivity) {
            this.chilSensitivity = chilSensitivity;
        }
        /**
         * 获取：过敏史
         */
        public String getChilSensitivity() {
            return chilSensitivity;
        }
        /**
         * 设置：母亲HB
         */
        public void setChilMotherhb(String chilMotherhb) {
            this.chilMotherhb = chilMotherhb;
        }
        /**
         * 获取：母亲HB
         */
        public String getChilMotherhb() {
            return chilMotherhb;
        }
        /**
         * 设置：母亲HIV
         */
        public void setChilMotherhiv(String chilMotherhiv) {
            this.chilMotherhiv = chilMotherhiv;
        }
        /**
         * 获取：母亲HIV
         */
        public String getChilMotherhiv() {
            return chilMotherhiv;
        }
        /**
         * 设置：个案状态
         */
        public void setChilHere(String chilHere) {
            this.chilHere = chilHere;
        }
        /**
         * 获取：个案状态
         */
        public String getChilHere() {
            return chilHere;
        }
        /**
         * 设置：在册变化日期
         */
        public void setChilLeavedate(Date chilLeavedate) {
            this.chilLeavedate = chilLeavedate;
        }
        /**
         * 获取：在册变化日期
         */
        public Date getChilLeavedate() {
            return chilLeavedate;
        }
        /**
         * 设置：迁出分类
         */
        public void setChilLeave(String chilLeave) {
            this.chilLeave = chilLeave;
        }
        /**
         * 获取：迁出分类
         */
        public String getChilLeave() {
            return chilLeave;
        }
        /**
         * 设置：迁出其他备注
         */
        public void setChilLeaveremark(String chilLeaveremark) {
            this.chilLeaveremark = chilLeaveremark;
        }
        /**
         * 获取：迁出其他备注
         */
        public String getChilLeaveremark() {
            return chilLeaveremark;
        }
        /**
         * 设置：现管理单位编码
         */
        public void setChilCurdepartment(String chilCurdepartment) {
            this.chilCurdepartment = chilCurdepartment;
        }
        /**
         * 获取：现管理单位编码
         */
        public String getChilCurdepartment() {
            return chilCurdepartment;
        }
        /**
         * 设置：前管理单位编码
         */
        public void setChilPredepartment(String chilPredepartment) {
            this.chilPredepartment = chilPredepartment;
        }
        /**
         * 获取：前管理单位编码
         */
        public String getChilPredepartment() {
            return chilPredepartment;
        }
        /**
         * 设置：建档县国标
         */
        public void setChilCreatecounty(String chilCreatecounty) {
            this.chilCreatecounty = chilCreatecounty;
        }
        /**
         * 获取：建档县国标
         */
        public String getChilCreatecounty() {
            return chilCreatecounty;
        }
        /**
         * 设置：建档单位编码
         */
        public void setChilCreatesite(String chilCreatesite) {
            this.chilCreatesite = chilCreatesite;
        }
        /**
         * 获取：建档单位编码
         */
        public String getChilCreatesite() {
            return chilCreatesite;
        }
        /**
         * 设置：建档日期
         */
        public void setChilCreatedate(Date chilCreatedate) {
            this.chilCreatedate = chilCreatedate;
        }
        /**
         * 获取：建档日期
         */
        public Date getChilCreatedate() {
            return chilCreatedate;
        }
        /**
         * 设置：建卡人
         */
        public void setChilCreateman(String chilCreateman) {
            this.chilCreateman = chilCreateman;
        }
        /**
         * 获取：建卡人
         */
        public String getChilCreateman() {
            return chilCreateman;
        }
        /**
         * 设置：受种者单位
         */
        public void setChilUnitimmu(String chilUnitimmu) {
            this.chilUnitimmu = chilUnitimmu;
        }
        /**
         * 获取：受种者单位
         */
        public String getChilUnitimmu() {
            return chilUnitimmu;
        }
        /**
         * 设置：出生医院编码
         */
        public void setChilBirthhospital(String chilBirthhospital) {
            this.chilBirthhospital = chilBirthhospital;
        }
        /**
         * 获取：出生医院编码
         */
        public String getChilBirthhospital() {
            return chilBirthhospital;
        }
        /**
         * 设置：出生医院名称
         */
        public void setChilBirthhospitalname(String chilBirthhospitalname) {
            this.chilBirthhospitalname = chilBirthhospitalname;
        }
        /**
         * 获取：出生医院名称
         */
        public String getChilBirthhospitalname() {
            return chilBirthhospitalname;
        }
        /**
         * 设置：是否有卡介苗疤痕
         */
        public void setChilBcgScar(String chilBcgScar) {
            this.chilBcgScar = chilBcgScar;
        }
        /**
         * 获取：是否有卡介苗疤痕
         */
        public String getChilBcgScar() {
            return chilBcgScar;
        }
        /**
         * 设置：母亲身份证号
         */
        public void setChilMotherno(Integer chilMotherno) {
            this.chilMotherno = chilMotherno;
        }
        /**
         * 获取：母亲身份证号
         */
        public Integer getChilMotherno() {
            return chilMotherno;
        }
        /**
         * 设置：父亲身份证号
         */
        public void setChilFatherno(Integer chilFatherno) {
            this.chilFatherno = chilFatherno;
        }
        /**
         * 获取：父亲身份证号
         */
        public Integer getChilFatherno() {
            return chilFatherno;
        }
        /**
         * 设置：出省状态
         */
        public void setChilProvince(String chilProvince) {
            this.chilProvince = chilProvince;
        }
        /**
         * 获取：出省状态
         */
        public String getChilProvince() {
            return chilProvince;
        }
        /**
         * 设置：更新时间
         */
        public void setChilEditDate(Date chilEditDate) {
            this.chilEditDate = chilEditDate;
        }
        /**
         * 获取：更新时间
         */
        public Date getChilEditDate() {
            return chilEditDate;
        }
        /**
         * 设置：建卡时间
         */
        public void setCreateCardTime(Date createCardTime) {
            this.createCardTime = createCardTime;
        }
        /**
         * 获取：建卡时间
         */
        public Date getCreateCardTime() {
            return createCardTime;
        }
        /**
         * 设置：type,0本地,1平台
         */
        public void setType(Integer type) {
            this.type = type;
        }
        /**
         * 获取：type,0本地,1平台
         */
        public Integer getType() {
            return type;
        }
        /**
         * 设置：同步状态,0未同步;1同步
         */
        public void setSyncstatus(Integer syncstatus) {
            this.syncstatus = syncstatus;
        }
        /**
         * 获取：同步状态,0未同步;1同步
         */
        public Integer getSyncstatus() {
            return syncstatus;
        }
        /**
         * 设置：状态,0正常,-1删除
         */
        public void setStatus(Integer status) {
            this.status = status;
        }
        /**
         * 获取：状态,0正常,-1删除
         */
        public Integer getStatus() {
            return status;
        }
        /**
         * 设置：创建人id
         */
        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }
        /**
         * 获取：创建人id
         */
        public String getCreateUserId() {
            return createUserId;
        }
        /**
         * 设置：创建人名字
         */
        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }
        /**
         * 获取：创建人名字
         */
        public String getCreateUserName() {
            return createUserName;
        }
        /**
         * 设置：创建时间
         */
        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }
        /**
         * 获取：创建时间
         */
        public Date getCreateTime() {
            return createTime;
        }
        /**
         * 设置：备注
         */
        public void setRemark(String remark) {
            this.remark = remark;
        }
        /**
         * 获取：备注
         */
        public String getRemark() {
            return remark;
        }

        public String getChilSchool() {
            return chilSchool;
        }

        public void setChilSchool(String chilSchool) {
            this.chilSchool = chilSchool;
        }
    }
}
