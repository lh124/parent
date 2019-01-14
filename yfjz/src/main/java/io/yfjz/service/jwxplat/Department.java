/**
 * Department.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package io.yfjz.service.jwxplat;

public class Department  extends io.yfjz.service.jwxplat.CommonDTO  implements java.io.Serializable {
    private java.lang.String[] citys_ID;

    private java.lang.String countyid;

    private java.lang.String db_flag;

    private java.lang.String dein_workday;

    private java.lang.String depa_area;

    private java.lang.String depa_begindate;

    private java.lang.String depa_ca_indate;

    private java.lang.Integer depa_ca_use;

    private java.lang.String depa_cardprivilege;

    private java.lang.String depa_cardverify;

    private java.lang.String depa_code;

    private java.lang.String[] depa_codes;

    private java.lang.String depa_controlstate;

    private java.lang.Integer depa_count;

    private java.lang.String depa_date;

    private java.lang.String depa_district;

    private java.lang.String depa_download;

    private java.lang.String depa_downloadall;

    private java.lang.String depa_editdate;

    private java.lang.String depa_enddate;

    private java.lang.String depa_firsttime;

    private java.lang.String depa_fullname;

    private java.lang.String depa_hospital;

    private java.lang.String depa_id;

    private java.lang.String depa_ismaternity;

    private java.lang.String depa_isrpt;

    private java.lang.String depa_ldtdprivilege;

    private java.lang.String depa_ldtdverify;

    private java.lang.String depa_licence;

    private java.lang.String depa_md5;

    private java.lang.String depa_name;

    private java.lang.String depa_nationid;

    private java.lang.String depa_obstetrics_use;

    private java.lang.String depa_password;

    private java.lang.String depa_paystate;

    private java.lang.String depa_privilege;

    private java.lang.Integer depa_queue;

    private java.lang.String depa_rank;

    private java.lang.String depa_relation_depaid;

    private java.lang.String depa_secondtime;

    private java.lang.String depa_use;

    private java.lang.String depa_validperiod;

    private java.lang.String depa_zone;

    private java.lang.String department;

    private java.lang.String districtcode;

    private java.lang.String dplevel;

    private java.lang.String dprs_appointmentAllPart;

    private java.lang.String dprs_checkupgradeprivilege;

    private java.lang.String dprs_childName;

    private java.lang.String dprs_delayed;

    private java.lang.String dprs_digipriv;

    private java.lang.String[] dprs_digiprivs;

    private java.lang.String dprs_encryption;

    private java.lang.String dprs_hook;

    private java.lang.String[] dprs_hooks;

    private java.lang.String dprs_pay;

    private java.lang.String dprs_permitbinding;

    private java.lang.String dprs_upgradeprivilege;

    private java.lang.String dprs_useapp;

    private java.lang.String dprs_vaccineshortage;

    private java.lang.String enddate;

    private java.lang.String id;

    private java.lang.String immunitytimes;

    private java.lang.String include_43;

    private java.lang.String mode;

    private java.lang.String prefix_code;

    private java.lang.String school_type1;

    private java.lang.String school_type2;

    private java.lang.String selected_depa_level;

    private java.lang.String startdate;

    public Department() {
    }

    public Department(
           java.lang.Integer count,
           java.lang.String[] depa_ids,
           java.lang.String depa_level,
           java.lang.String depa_type,
           java.lang.Integer end,
           java.lang.Integer endPage,
           java.lang.Integer page,
           java.lang.Integer pageCount,
           java.lang.Integer pages,
           java.lang.String queryYear,
           java.lang.Integer start,
           java.lang.Integer startPage,
           java.lang.Integer topCount,
           java.lang.String controlDepartmentSelect,
           java.lang.String deleteSql,
           java.lang.String insertSql,
           java.lang.String queryOneSql,
           java.lang.String querySql,
           java.lang.String updateSql,
           java.lang.String[] citys_ID,
           java.lang.String countyid,
           java.lang.String db_flag,
           java.lang.String dein_workday,
           java.lang.String depa_area,
           java.lang.String depa_begindate,
           java.lang.String depa_ca_indate,
           java.lang.Integer depa_ca_use,
           java.lang.String depa_cardprivilege,
           java.lang.String depa_cardverify,
           java.lang.String depa_code,
           java.lang.String[] depa_codes,
           java.lang.String depa_controlstate,
           java.lang.Integer depa_count,
           java.lang.String depa_date,
           java.lang.String depa_district,
           java.lang.String depa_download,
           java.lang.String depa_downloadall,
           java.lang.String depa_editdate,
           java.lang.String depa_enddate,
           java.lang.String depa_firsttime,
           java.lang.String depa_fullname,
           java.lang.String depa_hospital,
           java.lang.String depa_id,
           java.lang.String depa_ismaternity,
           java.lang.String depa_isrpt,
           java.lang.String depa_ldtdprivilege,
           java.lang.String depa_ldtdverify,
           java.lang.String depa_licence,
           java.lang.String depa_md5,
           java.lang.String depa_name,
           java.lang.String depa_nationid,
           java.lang.String depa_obstetrics_use,
           java.lang.String depa_password,
           java.lang.String depa_paystate,
           java.lang.String depa_privilege,
           java.lang.Integer depa_queue,
           java.lang.String depa_rank,
           java.lang.String depa_relation_depaid,
           java.lang.String depa_secondtime,
           java.lang.String depa_use,
           java.lang.String depa_validperiod,
           java.lang.String depa_zone,
           java.lang.String department,
           java.lang.String districtcode,
           java.lang.String dplevel,
           java.lang.String dprs_appointmentAllPart,
           java.lang.String dprs_checkupgradeprivilege,
           java.lang.String dprs_childName,
           java.lang.String dprs_delayed,
           java.lang.String dprs_digipriv,
           java.lang.String[] dprs_digiprivs,
           java.lang.String dprs_encryption,
           java.lang.String dprs_hook,
           java.lang.String[] dprs_hooks,
           java.lang.String dprs_pay,
           java.lang.String dprs_permitbinding,
           java.lang.String dprs_upgradeprivilege,
           java.lang.String dprs_useapp,
           java.lang.String dprs_vaccineshortage,
           java.lang.String enddate,
           java.lang.String id,
           java.lang.String immunitytimes,
           java.lang.String include_43,
           java.lang.String mode,
           java.lang.String prefix_code,
           java.lang.String school_type1,
           java.lang.String school_type2,
           java.lang.String selected_depa_level,
           java.lang.String startdate) {
        super(
            count,
            depa_ids,
            depa_level,
            depa_type,
            end,
            endPage,
            page,
            pageCount,
            pages,
            queryYear,
            start,
            startPage,
            topCount,
            controlDepartmentSelect,
            deleteSql,
            insertSql,
            queryOneSql,
            querySql,
            updateSql);
        this.citys_ID = citys_ID;
        this.countyid = countyid;
        this.db_flag = db_flag;
        this.dein_workday = dein_workday;
        this.depa_area = depa_area;
        this.depa_begindate = depa_begindate;
        this.depa_ca_indate = depa_ca_indate;
        this.depa_ca_use = depa_ca_use;
        this.depa_cardprivilege = depa_cardprivilege;
        this.depa_cardverify = depa_cardverify;
        this.depa_code = depa_code;
        this.depa_codes = depa_codes;
        this.depa_controlstate = depa_controlstate;
        this.depa_count = depa_count;
        this.depa_date = depa_date;
        this.depa_district = depa_district;
        this.depa_download = depa_download;
        this.depa_downloadall = depa_downloadall;
        this.depa_editdate = depa_editdate;
        this.depa_enddate = depa_enddate;
        this.depa_firsttime = depa_firsttime;
        this.depa_fullname = depa_fullname;
        this.depa_hospital = depa_hospital;
        this.depa_id = depa_id;
        this.depa_ismaternity = depa_ismaternity;
        this.depa_isrpt = depa_isrpt;
        this.depa_ldtdprivilege = depa_ldtdprivilege;
        this.depa_ldtdverify = depa_ldtdverify;
        this.depa_licence = depa_licence;
        this.depa_md5 = depa_md5;
        this.depa_name = depa_name;
        this.depa_nationid = depa_nationid;
        this.depa_obstetrics_use = depa_obstetrics_use;
        this.depa_password = depa_password;
        this.depa_paystate = depa_paystate;
        this.depa_privilege = depa_privilege;
        this.depa_queue = depa_queue;
        this.depa_rank = depa_rank;
        this.depa_relation_depaid = depa_relation_depaid;
        this.depa_secondtime = depa_secondtime;
        this.depa_use = depa_use;
        this.depa_validperiod = depa_validperiod;
        this.depa_zone = depa_zone;
        this.department = department;
        this.districtcode = districtcode;
        this.dplevel = dplevel;
        this.dprs_appointmentAllPart = dprs_appointmentAllPart;
        this.dprs_checkupgradeprivilege = dprs_checkupgradeprivilege;
        this.dprs_childName = dprs_childName;
        this.dprs_delayed = dprs_delayed;
        this.dprs_digipriv = dprs_digipriv;
        this.dprs_digiprivs = dprs_digiprivs;
        this.dprs_encryption = dprs_encryption;
        this.dprs_hook = dprs_hook;
        this.dprs_hooks = dprs_hooks;
        this.dprs_pay = dprs_pay;
        this.dprs_permitbinding = dprs_permitbinding;
        this.dprs_upgradeprivilege = dprs_upgradeprivilege;
        this.dprs_useapp = dprs_useapp;
        this.dprs_vaccineshortage = dprs_vaccineshortage;
        this.enddate = enddate;
        this.id = id;
        this.immunitytimes = immunitytimes;
        this.include_43 = include_43;
        this.mode = mode;
        this.prefix_code = prefix_code;
        this.school_type1 = school_type1;
        this.school_type2 = school_type2;
        this.selected_depa_level = selected_depa_level;
        this.startdate = startdate;
    }


    /**
     * Gets the citys_ID value for this Department.
     * 
     * @return citys_ID
     */
    public java.lang.String[] getCitys_ID() {
        return citys_ID;
    }


    /**
     * Sets the citys_ID value for this Department.
     * 
     * @param citys_ID
     */
    public void setCitys_ID(java.lang.String[] citys_ID) {
        this.citys_ID = citys_ID;
    }


    /**
     * Gets the countyid value for this Department.
     * 
     * @return countyid
     */
    public java.lang.String getCountyid() {
        return countyid;
    }


    /**
     * Sets the countyid value for this Department.
     * 
     * @param countyid
     */
    public void setCountyid(java.lang.String countyid) {
        this.countyid = countyid;
    }


    /**
     * Gets the db_flag value for this Department.
     * 
     * @return db_flag
     */
    public java.lang.String getDb_flag() {
        return db_flag;
    }


    /**
     * Sets the db_flag value for this Department.
     * 
     * @param db_flag
     */
    public void setDb_flag(java.lang.String db_flag) {
        this.db_flag = db_flag;
    }


    /**
     * Gets the dein_workday value for this Department.
     * 
     * @return dein_workday
     */
    public java.lang.String getDein_workday() {
        return dein_workday;
    }


    /**
     * Sets the dein_workday value for this Department.
     * 
     * @param dein_workday
     */
    public void setDein_workday(java.lang.String dein_workday) {
        this.dein_workday = dein_workday;
    }


    /**
     * Gets the depa_area value for this Department.
     * 
     * @return depa_area
     */
    public java.lang.String getDepa_area() {
        return depa_area;
    }


    /**
     * Sets the depa_area value for this Department.
     * 
     * @param depa_area
     */
    public void setDepa_area(java.lang.String depa_area) {
        this.depa_area = depa_area;
    }


    /**
     * Gets the depa_begindate value for this Department.
     * 
     * @return depa_begindate
     */
    public java.lang.String getDepa_begindate() {
        return depa_begindate;
    }


    /**
     * Sets the depa_begindate value for this Department.
     * 
     * @param depa_begindate
     */
    public void setDepa_begindate(java.lang.String depa_begindate) {
        this.depa_begindate = depa_begindate;
    }


    /**
     * Gets the depa_ca_indate value for this Department.
     * 
     * @return depa_ca_indate
     */
    public java.lang.String getDepa_ca_indate() {
        return depa_ca_indate;
    }


    /**
     * Sets the depa_ca_indate value for this Department.
     * 
     * @param depa_ca_indate
     */
    public void setDepa_ca_indate(java.lang.String depa_ca_indate) {
        this.depa_ca_indate = depa_ca_indate;
    }


    /**
     * Gets the depa_ca_use value for this Department.
     * 
     * @return depa_ca_use
     */
    public java.lang.Integer getDepa_ca_use() {
        return depa_ca_use;
    }


    /**
     * Sets the depa_ca_use value for this Department.
     * 
     * @param depa_ca_use
     */
    public void setDepa_ca_use(java.lang.Integer depa_ca_use) {
        this.depa_ca_use = depa_ca_use;
    }


    /**
     * Gets the depa_cardprivilege value for this Department.
     * 
     * @return depa_cardprivilege
     */
    public java.lang.String getDepa_cardprivilege() {
        return depa_cardprivilege;
    }


    /**
     * Sets the depa_cardprivilege value for this Department.
     * 
     * @param depa_cardprivilege
     */
    public void setDepa_cardprivilege(java.lang.String depa_cardprivilege) {
        this.depa_cardprivilege = depa_cardprivilege;
    }


    /**
     * Gets the depa_cardverify value for this Department.
     * 
     * @return depa_cardverify
     */
    public java.lang.String getDepa_cardverify() {
        return depa_cardverify;
    }


    /**
     * Sets the depa_cardverify value for this Department.
     * 
     * @param depa_cardverify
     */
    public void setDepa_cardverify(java.lang.String depa_cardverify) {
        this.depa_cardverify = depa_cardverify;
    }


    /**
     * Gets the depa_code value for this Department.
     * 
     * @return depa_code
     */
    public java.lang.String getDepa_code() {
        return depa_code;
    }


    /**
     * Sets the depa_code value for this Department.
     * 
     * @param depa_code
     */
    public void setDepa_code(java.lang.String depa_code) {
        this.depa_code = depa_code;
    }


    /**
     * Gets the depa_codes value for this Department.
     * 
     * @return depa_codes
     */
    public java.lang.String[] getDepa_codes() {
        return depa_codes;
    }


    /**
     * Sets the depa_codes value for this Department.
     * 
     * @param depa_codes
     */
    public void setDepa_codes(java.lang.String[] depa_codes) {
        this.depa_codes = depa_codes;
    }


    /**
     * Gets the depa_controlstate value for this Department.
     * 
     * @return depa_controlstate
     */
    public java.lang.String getDepa_controlstate() {
        return depa_controlstate;
    }


    /**
     * Sets the depa_controlstate value for this Department.
     * 
     * @param depa_controlstate
     */
    public void setDepa_controlstate(java.lang.String depa_controlstate) {
        this.depa_controlstate = depa_controlstate;
    }


    /**
     * Gets the depa_count value for this Department.
     * 
     * @return depa_count
     */
    public java.lang.Integer getDepa_count() {
        return depa_count;
    }


    /**
     * Sets the depa_count value for this Department.
     * 
     * @param depa_count
     */
    public void setDepa_count(java.lang.Integer depa_count) {
        this.depa_count = depa_count;
    }


    /**
     * Gets the depa_date value for this Department.
     * 
     * @return depa_date
     */
    public java.lang.String getDepa_date() {
        return depa_date;
    }


    /**
     * Sets the depa_date value for this Department.
     * 
     * @param depa_date
     */
    public void setDepa_date(java.lang.String depa_date) {
        this.depa_date = depa_date;
    }


    /**
     * Gets the depa_district value for this Department.
     * 
     * @return depa_district
     */
    public java.lang.String getDepa_district() {
        return depa_district;
    }


    /**
     * Sets the depa_district value for this Department.
     * 
     * @param depa_district
     */
    public void setDepa_district(java.lang.String depa_district) {
        this.depa_district = depa_district;
    }


    /**
     * Gets the depa_download value for this Department.
     * 
     * @return depa_download
     */
    public java.lang.String getDepa_download() {
        return depa_download;
    }


    /**
     * Sets the depa_download value for this Department.
     * 
     * @param depa_download
     */
    public void setDepa_download(java.lang.String depa_download) {
        this.depa_download = depa_download;
    }


    /**
     * Gets the depa_downloadall value for this Department.
     * 
     * @return depa_downloadall
     */
    public java.lang.String getDepa_downloadall() {
        return depa_downloadall;
    }


    /**
     * Sets the depa_downloadall value for this Department.
     * 
     * @param depa_downloadall
     */
    public void setDepa_downloadall(java.lang.String depa_downloadall) {
        this.depa_downloadall = depa_downloadall;
    }


    /**
     * Gets the depa_editdate value for this Department.
     * 
     * @return depa_editdate
     */
    public java.lang.String getDepa_editdate() {
        return depa_editdate;
    }


    /**
     * Sets the depa_editdate value for this Department.
     * 
     * @param depa_editdate
     */
    public void setDepa_editdate(java.lang.String depa_editdate) {
        this.depa_editdate = depa_editdate;
    }


    /**
     * Gets the depa_enddate value for this Department.
     * 
     * @return depa_enddate
     */
    public java.lang.String getDepa_enddate() {
        return depa_enddate;
    }


    /**
     * Sets the depa_enddate value for this Department.
     * 
     * @param depa_enddate
     */
    public void setDepa_enddate(java.lang.String depa_enddate) {
        this.depa_enddate = depa_enddate;
    }


    /**
     * Gets the depa_firsttime value for this Department.
     * 
     * @return depa_firsttime
     */
    public java.lang.String getDepa_firsttime() {
        return depa_firsttime;
    }


    /**
     * Sets the depa_firsttime value for this Department.
     * 
     * @param depa_firsttime
     */
    public void setDepa_firsttime(java.lang.String depa_firsttime) {
        this.depa_firsttime = depa_firsttime;
    }


    /**
     * Gets the depa_fullname value for this Department.
     * 
     * @return depa_fullname
     */
    public java.lang.String getDepa_fullname() {
        return depa_fullname;
    }


    /**
     * Sets the depa_fullname value for this Department.
     * 
     * @param depa_fullname
     */
    public void setDepa_fullname(java.lang.String depa_fullname) {
        this.depa_fullname = depa_fullname;
    }


    /**
     * Gets the depa_hospital value for this Department.
     * 
     * @return depa_hospital
     */
    public java.lang.String getDepa_hospital() {
        return depa_hospital;
    }


    /**
     * Sets the depa_hospital value for this Department.
     * 
     * @param depa_hospital
     */
    public void setDepa_hospital(java.lang.String depa_hospital) {
        this.depa_hospital = depa_hospital;
    }


    /**
     * Gets the depa_id value for this Department.
     * 
     * @return depa_id
     */
    public java.lang.String getDepa_id() {
        return depa_id;
    }


    /**
     * Sets the depa_id value for this Department.
     * 
     * @param depa_id
     */
    public void setDepa_id(java.lang.String depa_id) {
        this.depa_id = depa_id;
    }


    /**
     * Gets the depa_ismaternity value for this Department.
     * 
     * @return depa_ismaternity
     */
    public java.lang.String getDepa_ismaternity() {
        return depa_ismaternity;
    }


    /**
     * Sets the depa_ismaternity value for this Department.
     * 
     * @param depa_ismaternity
     */
    public void setDepa_ismaternity(java.lang.String depa_ismaternity) {
        this.depa_ismaternity = depa_ismaternity;
    }


    /**
     * Gets the depa_isrpt value for this Department.
     * 
     * @return depa_isrpt
     */
    public java.lang.String getDepa_isrpt() {
        return depa_isrpt;
    }


    /**
     * Sets the depa_isrpt value for this Department.
     * 
     * @param depa_isrpt
     */
    public void setDepa_isrpt(java.lang.String depa_isrpt) {
        this.depa_isrpt = depa_isrpt;
    }


    /**
     * Gets the depa_ldtdprivilege value for this Department.
     * 
     * @return depa_ldtdprivilege
     */
    public java.lang.String getDepa_ldtdprivilege() {
        return depa_ldtdprivilege;
    }


    /**
     * Sets the depa_ldtdprivilege value for this Department.
     * 
     * @param depa_ldtdprivilege
     */
    public void setDepa_ldtdprivilege(java.lang.String depa_ldtdprivilege) {
        this.depa_ldtdprivilege = depa_ldtdprivilege;
    }


    /**
     * Gets the depa_ldtdverify value for this Department.
     * 
     * @return depa_ldtdverify
     */
    public java.lang.String getDepa_ldtdverify() {
        return depa_ldtdverify;
    }


    /**
     * Sets the depa_ldtdverify value for this Department.
     * 
     * @param depa_ldtdverify
     */
    public void setDepa_ldtdverify(java.lang.String depa_ldtdverify) {
        this.depa_ldtdverify = depa_ldtdverify;
    }


    /**
     * Gets the depa_licence value for this Department.
     * 
     * @return depa_licence
     */
    public java.lang.String getDepa_licence() {
        return depa_licence;
    }


    /**
     * Sets the depa_licence value for this Department.
     * 
     * @param depa_licence
     */
    public void setDepa_licence(java.lang.String depa_licence) {
        this.depa_licence = depa_licence;
    }


    /**
     * Gets the depa_md5 value for this Department.
     * 
     * @return depa_md5
     */
    public java.lang.String getDepa_md5() {
        return depa_md5;
    }


    /**
     * Sets the depa_md5 value for this Department.
     * 
     * @param depa_md5
     */
    public void setDepa_md5(java.lang.String depa_md5) {
        this.depa_md5 = depa_md5;
    }


    /**
     * Gets the depa_name value for this Department.
     * 
     * @return depa_name
     */
    public java.lang.String getDepa_name() {
        return depa_name;
    }


    /**
     * Sets the depa_name value for this Department.
     * 
     * @param depa_name
     */
    public void setDepa_name(java.lang.String depa_name) {
        this.depa_name = depa_name;
    }


    /**
     * Gets the depa_nationid value for this Department.
     * 
     * @return depa_nationid
     */
    public java.lang.String getDepa_nationid() {
        return depa_nationid;
    }


    /**
     * Sets the depa_nationid value for this Department.
     * 
     * @param depa_nationid
     */
    public void setDepa_nationid(java.lang.String depa_nationid) {
        this.depa_nationid = depa_nationid;
    }


    /**
     * Gets the depa_obstetrics_use value for this Department.
     * 
     * @return depa_obstetrics_use
     */
    public java.lang.String getDepa_obstetrics_use() {
        return depa_obstetrics_use;
    }


    /**
     * Sets the depa_obstetrics_use value for this Department.
     * 
     * @param depa_obstetrics_use
     */
    public void setDepa_obstetrics_use(java.lang.String depa_obstetrics_use) {
        this.depa_obstetrics_use = depa_obstetrics_use;
    }


    /**
     * Gets the depa_password value for this Department.
     * 
     * @return depa_password
     */
    public java.lang.String getDepa_password() {
        return depa_password;
    }


    /**
     * Sets the depa_password value for this Department.
     * 
     * @param depa_password
     */
    public void setDepa_password(java.lang.String depa_password) {
        this.depa_password = depa_password;
    }


    /**
     * Gets the depa_paystate value for this Department.
     * 
     * @return depa_paystate
     */
    public java.lang.String getDepa_paystate() {
        return depa_paystate;
    }


    /**
     * Sets the depa_paystate value for this Department.
     * 
     * @param depa_paystate
     */
    public void setDepa_paystate(java.lang.String depa_paystate) {
        this.depa_paystate = depa_paystate;
    }


    /**
     * Gets the depa_privilege value for this Department.
     * 
     * @return depa_privilege
     */
    public java.lang.String getDepa_privilege() {
        return depa_privilege;
    }


    /**
     * Sets the depa_privilege value for this Department.
     * 
     * @param depa_privilege
     */
    public void setDepa_privilege(java.lang.String depa_privilege) {
        this.depa_privilege = depa_privilege;
    }


    /**
     * Gets the depa_queue value for this Department.
     * 
     * @return depa_queue
     */
    public java.lang.Integer getDepa_queue() {
        return depa_queue;
    }


    /**
     * Sets the depa_queue value for this Department.
     * 
     * @param depa_queue
     */
    public void setDepa_queue(java.lang.Integer depa_queue) {
        this.depa_queue = depa_queue;
    }


    /**
     * Gets the depa_rank value for this Department.
     * 
     * @return depa_rank
     */
    public java.lang.String getDepa_rank() {
        return depa_rank;
    }


    /**
     * Sets the depa_rank value for this Department.
     * 
     * @param depa_rank
     */
    public void setDepa_rank(java.lang.String depa_rank) {
        this.depa_rank = depa_rank;
    }


    /**
     * Gets the depa_relation_depaid value for this Department.
     * 
     * @return depa_relation_depaid
     */
    public java.lang.String getDepa_relation_depaid() {
        return depa_relation_depaid;
    }


    /**
     * Sets the depa_relation_depaid value for this Department.
     * 
     * @param depa_relation_depaid
     */
    public void setDepa_relation_depaid(java.lang.String depa_relation_depaid) {
        this.depa_relation_depaid = depa_relation_depaid;
    }


    /**
     * Gets the depa_secondtime value for this Department.
     * 
     * @return depa_secondtime
     */
    public java.lang.String getDepa_secondtime() {
        return depa_secondtime;
    }


    /**
     * Sets the depa_secondtime value for this Department.
     * 
     * @param depa_secondtime
     */
    public void setDepa_secondtime(java.lang.String depa_secondtime) {
        this.depa_secondtime = depa_secondtime;
    }


    /**
     * Gets the depa_use value for this Department.
     * 
     * @return depa_use
     */
    public java.lang.String getDepa_use() {
        return depa_use;
    }


    /**
     * Sets the depa_use value for this Department.
     * 
     * @param depa_use
     */
    public void setDepa_use(java.lang.String depa_use) {
        this.depa_use = depa_use;
    }


    /**
     * Gets the depa_validperiod value for this Department.
     * 
     * @return depa_validperiod
     */
    public java.lang.String getDepa_validperiod() {
        return depa_validperiod;
    }


    /**
     * Sets the depa_validperiod value for this Department.
     * 
     * @param depa_validperiod
     */
    public void setDepa_validperiod(java.lang.String depa_validperiod) {
        this.depa_validperiod = depa_validperiod;
    }


    /**
     * Gets the depa_zone value for this Department.
     * 
     * @return depa_zone
     */
    public java.lang.String getDepa_zone() {
        return depa_zone;
    }


    /**
     * Sets the depa_zone value for this Department.
     * 
     * @param depa_zone
     */
    public void setDepa_zone(java.lang.String depa_zone) {
        this.depa_zone = depa_zone;
    }


    /**
     * Gets the department value for this Department.
     * 
     * @return department
     */
    public java.lang.String getDepartment() {
        return department;
    }


    /**
     * Sets the department value for this Department.
     * 
     * @param department
     */
    public void setDepartment(java.lang.String department) {
        this.department = department;
    }


    /**
     * Gets the districtcode value for this Department.
     * 
     * @return districtcode
     */
    public java.lang.String getDistrictcode() {
        return districtcode;
    }


    /**
     * Sets the districtcode value for this Department.
     * 
     * @param districtcode
     */
    public void setDistrictcode(java.lang.String districtcode) {
        this.districtcode = districtcode;
    }


    /**
     * Gets the dplevel value for this Department.
     * 
     * @return dplevel
     */
    public java.lang.String getDplevel() {
        return dplevel;
    }


    /**
     * Sets the dplevel value for this Department.
     * 
     * @param dplevel
     */
    public void setDplevel(java.lang.String dplevel) {
        this.dplevel = dplevel;
    }


    /**
     * Gets the dprs_appointmentAllPart value for this Department.
     * 
     * @return dprs_appointmentAllPart
     */
    public java.lang.String getDprs_appointmentAllPart() {
        return dprs_appointmentAllPart;
    }


    /**
     * Sets the dprs_appointmentAllPart value for this Department.
     * 
     * @param dprs_appointmentAllPart
     */
    public void setDprs_appointmentAllPart(java.lang.String dprs_appointmentAllPart) {
        this.dprs_appointmentAllPart = dprs_appointmentAllPart;
    }


    /**
     * Gets the dprs_checkupgradeprivilege value for this Department.
     * 
     * @return dprs_checkupgradeprivilege
     */
    public java.lang.String getDprs_checkupgradeprivilege() {
        return dprs_checkupgradeprivilege;
    }


    /**
     * Sets the dprs_checkupgradeprivilege value for this Department.
     * 
     * @param dprs_checkupgradeprivilege
     */
    public void setDprs_checkupgradeprivilege(java.lang.String dprs_checkupgradeprivilege) {
        this.dprs_checkupgradeprivilege = dprs_checkupgradeprivilege;
    }


    /**
     * Gets the dprs_childName value for this Department.
     * 
     * @return dprs_childName
     */
    public java.lang.String getDprs_childName() {
        return dprs_childName;
    }


    /**
     * Sets the dprs_childName value for this Department.
     * 
     * @param dprs_childName
     */
    public void setDprs_childName(java.lang.String dprs_childName) {
        this.dprs_childName = dprs_childName;
    }


    /**
     * Gets the dprs_delayed value for this Department.
     * 
     * @return dprs_delayed
     */
    public java.lang.String getDprs_delayed() {
        return dprs_delayed;
    }


    /**
     * Sets the dprs_delayed value for this Department.
     * 
     * @param dprs_delayed
     */
    public void setDprs_delayed(java.lang.String dprs_delayed) {
        this.dprs_delayed = dprs_delayed;
    }


    /**
     * Gets the dprs_digipriv value for this Department.
     * 
     * @return dprs_digipriv
     */
    public java.lang.String getDprs_digipriv() {
        return dprs_digipriv;
    }


    /**
     * Sets the dprs_digipriv value for this Department.
     * 
     * @param dprs_digipriv
     */
    public void setDprs_digipriv(java.lang.String dprs_digipriv) {
        this.dprs_digipriv = dprs_digipriv;
    }


    /**
     * Gets the dprs_digiprivs value for this Department.
     * 
     * @return dprs_digiprivs
     */
    public java.lang.String[] getDprs_digiprivs() {
        return dprs_digiprivs;
    }


    /**
     * Sets the dprs_digiprivs value for this Department.
     * 
     * @param dprs_digiprivs
     */
    public void setDprs_digiprivs(java.lang.String[] dprs_digiprivs) {
        this.dprs_digiprivs = dprs_digiprivs;
    }


    /**
     * Gets the dprs_encryption value for this Department.
     * 
     * @return dprs_encryption
     */
    public java.lang.String getDprs_encryption() {
        return dprs_encryption;
    }


    /**
     * Sets the dprs_encryption value for this Department.
     * 
     * @param dprs_encryption
     */
    public void setDprs_encryption(java.lang.String dprs_encryption) {
        this.dprs_encryption = dprs_encryption;
    }


    /**
     * Gets the dprs_hook value for this Department.
     * 
     * @return dprs_hook
     */
    public java.lang.String getDprs_hook() {
        return dprs_hook;
    }


    /**
     * Sets the dprs_hook value for this Department.
     * 
     * @param dprs_hook
     */
    public void setDprs_hook(java.lang.String dprs_hook) {
        this.dprs_hook = dprs_hook;
    }


    /**
     * Gets the dprs_hooks value for this Department.
     * 
     * @return dprs_hooks
     */
    public java.lang.String[] getDprs_hooks() {
        return dprs_hooks;
    }


    /**
     * Sets the dprs_hooks value for this Department.
     * 
     * @param dprs_hooks
     */
    public void setDprs_hooks(java.lang.String[] dprs_hooks) {
        this.dprs_hooks = dprs_hooks;
    }


    /**
     * Gets the dprs_pay value for this Department.
     * 
     * @return dprs_pay
     */
    public java.lang.String getDprs_pay() {
        return dprs_pay;
    }


    /**
     * Sets the dprs_pay value for this Department.
     * 
     * @param dprs_pay
     */
    public void setDprs_pay(java.lang.String dprs_pay) {
        this.dprs_pay = dprs_pay;
    }


    /**
     * Gets the dprs_permitbinding value for this Department.
     * 
     * @return dprs_permitbinding
     */
    public java.lang.String getDprs_permitbinding() {
        return dprs_permitbinding;
    }


    /**
     * Sets the dprs_permitbinding value for this Department.
     * 
     * @param dprs_permitbinding
     */
    public void setDprs_permitbinding(java.lang.String dprs_permitbinding) {
        this.dprs_permitbinding = dprs_permitbinding;
    }


    /**
     * Gets the dprs_upgradeprivilege value for this Department.
     * 
     * @return dprs_upgradeprivilege
     */
    public java.lang.String getDprs_upgradeprivilege() {
        return dprs_upgradeprivilege;
    }


    /**
     * Sets the dprs_upgradeprivilege value for this Department.
     * 
     * @param dprs_upgradeprivilege
     */
    public void setDprs_upgradeprivilege(java.lang.String dprs_upgradeprivilege) {
        this.dprs_upgradeprivilege = dprs_upgradeprivilege;
    }


    /**
     * Gets the dprs_useapp value for this Department.
     * 
     * @return dprs_useapp
     */
    public java.lang.String getDprs_useapp() {
        return dprs_useapp;
    }


    /**
     * Sets the dprs_useapp value for this Department.
     * 
     * @param dprs_useapp
     */
    public void setDprs_useapp(java.lang.String dprs_useapp) {
        this.dprs_useapp = dprs_useapp;
    }


    /**
     * Gets the dprs_vaccineshortage value for this Department.
     * 
     * @return dprs_vaccineshortage
     */
    public java.lang.String getDprs_vaccineshortage() {
        return dprs_vaccineshortage;
    }


    /**
     * Sets the dprs_vaccineshortage value for this Department.
     * 
     * @param dprs_vaccineshortage
     */
    public void setDprs_vaccineshortage(java.lang.String dprs_vaccineshortage) {
        this.dprs_vaccineshortage = dprs_vaccineshortage;
    }


    /**
     * Gets the enddate value for this Department.
     * 
     * @return enddate
     */
    public java.lang.String getEnddate() {
        return enddate;
    }


    /**
     * Sets the enddate value for this Department.
     * 
     * @param enddate
     */
    public void setEnddate(java.lang.String enddate) {
        this.enddate = enddate;
    }


    /**
     * Gets the id value for this Department.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Department.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the immunitytimes value for this Department.
     * 
     * @return immunitytimes
     */
    public java.lang.String getImmunitytimes() {
        return immunitytimes;
    }


    /**
     * Sets the immunitytimes value for this Department.
     * 
     * @param immunitytimes
     */
    public void setImmunitytimes(java.lang.String immunitytimes) {
        this.immunitytimes = immunitytimes;
    }


    /**
     * Gets the include_43 value for this Department.
     * 
     * @return include_43
     */
    public java.lang.String getInclude_43() {
        return include_43;
    }


    /**
     * Sets the include_43 value for this Department.
     * 
     * @param include_43
     */
    public void setInclude_43(java.lang.String include_43) {
        this.include_43 = include_43;
    }


    /**
     * Gets the mode value for this Department.
     * 
     * @return mode
     */
    public java.lang.String getMode() {
        return mode;
    }


    /**
     * Sets the mode value for this Department.
     * 
     * @param mode
     */
    public void setMode(java.lang.String mode) {
        this.mode = mode;
    }


    /**
     * Gets the prefix_code value for this Department.
     * 
     * @return prefix_code
     */
    public java.lang.String getPrefix_code() {
        return prefix_code;
    }


    /**
     * Sets the prefix_code value for this Department.
     * 
     * @param prefix_code
     */
    public void setPrefix_code(java.lang.String prefix_code) {
        this.prefix_code = prefix_code;
    }


    /**
     * Gets the school_type1 value for this Department.
     * 
     * @return school_type1
     */
    public java.lang.String getSchool_type1() {
        return school_type1;
    }


    /**
     * Sets the school_type1 value for this Department.
     * 
     * @param school_type1
     */
    public void setSchool_type1(java.lang.String school_type1) {
        this.school_type1 = school_type1;
    }


    /**
     * Gets the school_type2 value for this Department.
     * 
     * @return school_type2
     */
    public java.lang.String getSchool_type2() {
        return school_type2;
    }


    /**
     * Sets the school_type2 value for this Department.
     * 
     * @param school_type2
     */
    public void setSchool_type2(java.lang.String school_type2) {
        this.school_type2 = school_type2;
    }


    /**
     * Gets the selected_depa_level value for this Department.
     * 
     * @return selected_depa_level
     */
    public java.lang.String getSelected_depa_level() {
        return selected_depa_level;
    }


    /**
     * Sets the selected_depa_level value for this Department.
     * 
     * @param selected_depa_level
     */
    public void setSelected_depa_level(java.lang.String selected_depa_level) {
        this.selected_depa_level = selected_depa_level;
    }


    /**
     * Gets the startdate value for this Department.
     * 
     * @return startdate
     */
    public java.lang.String getStartdate() {
        return startdate;
    }


    /**
     * Sets the startdate value for this Department.
     * 
     * @param startdate
     */
    public void setStartdate(java.lang.String startdate) {
        this.startdate = startdate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Department)) return false;
        Department other = (Department) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.citys_ID==null && other.getCitys_ID()==null) || 
             (this.citys_ID!=null &&
              java.util.Arrays.equals(this.citys_ID, other.getCitys_ID()))) &&
            ((this.countyid==null && other.getCountyid()==null) || 
             (this.countyid!=null &&
              this.countyid.equals(other.getCountyid()))) &&
            ((this.db_flag==null && other.getDb_flag()==null) || 
             (this.db_flag!=null &&
              this.db_flag.equals(other.getDb_flag()))) &&
            ((this.dein_workday==null && other.getDein_workday()==null) || 
             (this.dein_workday!=null &&
              this.dein_workday.equals(other.getDein_workday()))) &&
            ((this.depa_area==null && other.getDepa_area()==null) || 
             (this.depa_area!=null &&
              this.depa_area.equals(other.getDepa_area()))) &&
            ((this.depa_begindate==null && other.getDepa_begindate()==null) || 
             (this.depa_begindate!=null &&
              this.depa_begindate.equals(other.getDepa_begindate()))) &&
            ((this.depa_ca_indate==null && other.getDepa_ca_indate()==null) || 
             (this.depa_ca_indate!=null &&
              this.depa_ca_indate.equals(other.getDepa_ca_indate()))) &&
            ((this.depa_ca_use==null && other.getDepa_ca_use()==null) || 
             (this.depa_ca_use!=null &&
              this.depa_ca_use.equals(other.getDepa_ca_use()))) &&
            ((this.depa_cardprivilege==null && other.getDepa_cardprivilege()==null) || 
             (this.depa_cardprivilege!=null &&
              this.depa_cardprivilege.equals(other.getDepa_cardprivilege()))) &&
            ((this.depa_cardverify==null && other.getDepa_cardverify()==null) || 
             (this.depa_cardverify!=null &&
              this.depa_cardverify.equals(other.getDepa_cardverify()))) &&
            ((this.depa_code==null && other.getDepa_code()==null) || 
             (this.depa_code!=null &&
              this.depa_code.equals(other.getDepa_code()))) &&
            ((this.depa_codes==null && other.getDepa_codes()==null) || 
             (this.depa_codes!=null &&
              java.util.Arrays.equals(this.depa_codes, other.getDepa_codes()))) &&
            ((this.depa_controlstate==null && other.getDepa_controlstate()==null) || 
             (this.depa_controlstate!=null &&
              this.depa_controlstate.equals(other.getDepa_controlstate()))) &&
            ((this.depa_count==null && other.getDepa_count()==null) || 
             (this.depa_count!=null &&
              this.depa_count.equals(other.getDepa_count()))) &&
            ((this.depa_date==null && other.getDepa_date()==null) || 
             (this.depa_date!=null &&
              this.depa_date.equals(other.getDepa_date()))) &&
            ((this.depa_district==null && other.getDepa_district()==null) || 
             (this.depa_district!=null &&
              this.depa_district.equals(other.getDepa_district()))) &&
            ((this.depa_download==null && other.getDepa_download()==null) || 
             (this.depa_download!=null &&
              this.depa_download.equals(other.getDepa_download()))) &&
            ((this.depa_downloadall==null && other.getDepa_downloadall()==null) || 
             (this.depa_downloadall!=null &&
              this.depa_downloadall.equals(other.getDepa_downloadall()))) &&
            ((this.depa_editdate==null && other.getDepa_editdate()==null) || 
             (this.depa_editdate!=null &&
              this.depa_editdate.equals(other.getDepa_editdate()))) &&
            ((this.depa_enddate==null && other.getDepa_enddate()==null) || 
             (this.depa_enddate!=null &&
              this.depa_enddate.equals(other.getDepa_enddate()))) &&
            ((this.depa_firsttime==null && other.getDepa_firsttime()==null) || 
             (this.depa_firsttime!=null &&
              this.depa_firsttime.equals(other.getDepa_firsttime()))) &&
            ((this.depa_fullname==null && other.getDepa_fullname()==null) || 
             (this.depa_fullname!=null &&
              this.depa_fullname.equals(other.getDepa_fullname()))) &&
            ((this.depa_hospital==null && other.getDepa_hospital()==null) || 
             (this.depa_hospital!=null &&
              this.depa_hospital.equals(other.getDepa_hospital()))) &&
            ((this.depa_id==null && other.getDepa_id()==null) || 
             (this.depa_id!=null &&
              this.depa_id.equals(other.getDepa_id()))) &&
            ((this.depa_ismaternity==null && other.getDepa_ismaternity()==null) || 
             (this.depa_ismaternity!=null &&
              this.depa_ismaternity.equals(other.getDepa_ismaternity()))) &&
            ((this.depa_isrpt==null && other.getDepa_isrpt()==null) || 
             (this.depa_isrpt!=null &&
              this.depa_isrpt.equals(other.getDepa_isrpt()))) &&
            ((this.depa_ldtdprivilege==null && other.getDepa_ldtdprivilege()==null) || 
             (this.depa_ldtdprivilege!=null &&
              this.depa_ldtdprivilege.equals(other.getDepa_ldtdprivilege()))) &&
            ((this.depa_ldtdverify==null && other.getDepa_ldtdverify()==null) || 
             (this.depa_ldtdverify!=null &&
              this.depa_ldtdverify.equals(other.getDepa_ldtdverify()))) &&
            ((this.depa_licence==null && other.getDepa_licence()==null) || 
             (this.depa_licence!=null &&
              this.depa_licence.equals(other.getDepa_licence()))) &&
            ((this.depa_md5==null && other.getDepa_md5()==null) || 
             (this.depa_md5!=null &&
              this.depa_md5.equals(other.getDepa_md5()))) &&
            ((this.depa_name==null && other.getDepa_name()==null) || 
             (this.depa_name!=null &&
              this.depa_name.equals(other.getDepa_name()))) &&
            ((this.depa_nationid==null && other.getDepa_nationid()==null) || 
             (this.depa_nationid!=null &&
              this.depa_nationid.equals(other.getDepa_nationid()))) &&
            ((this.depa_obstetrics_use==null && other.getDepa_obstetrics_use()==null) || 
             (this.depa_obstetrics_use!=null &&
              this.depa_obstetrics_use.equals(other.getDepa_obstetrics_use()))) &&
            ((this.depa_password==null && other.getDepa_password()==null) || 
             (this.depa_password!=null &&
              this.depa_password.equals(other.getDepa_password()))) &&
            ((this.depa_paystate==null && other.getDepa_paystate()==null) || 
             (this.depa_paystate!=null &&
              this.depa_paystate.equals(other.getDepa_paystate()))) &&
            ((this.depa_privilege==null && other.getDepa_privilege()==null) || 
             (this.depa_privilege!=null &&
              this.depa_privilege.equals(other.getDepa_privilege()))) &&
            ((this.depa_queue==null && other.getDepa_queue()==null) || 
             (this.depa_queue!=null &&
              this.depa_queue.equals(other.getDepa_queue()))) &&
            ((this.depa_rank==null && other.getDepa_rank()==null) || 
             (this.depa_rank!=null &&
              this.depa_rank.equals(other.getDepa_rank()))) &&
            ((this.depa_relation_depaid==null && other.getDepa_relation_depaid()==null) || 
             (this.depa_relation_depaid!=null &&
              this.depa_relation_depaid.equals(other.getDepa_relation_depaid()))) &&
            ((this.depa_secondtime==null && other.getDepa_secondtime()==null) || 
             (this.depa_secondtime!=null &&
              this.depa_secondtime.equals(other.getDepa_secondtime()))) &&
            ((this.depa_use==null && other.getDepa_use()==null) || 
             (this.depa_use!=null &&
              this.depa_use.equals(other.getDepa_use()))) &&
            ((this.depa_validperiod==null && other.getDepa_validperiod()==null) || 
             (this.depa_validperiod!=null &&
              this.depa_validperiod.equals(other.getDepa_validperiod()))) &&
            ((this.depa_zone==null && other.getDepa_zone()==null) || 
             (this.depa_zone!=null &&
              this.depa_zone.equals(other.getDepa_zone()))) &&
            ((this.department==null && other.getDepartment()==null) || 
             (this.department!=null &&
              this.department.equals(other.getDepartment()))) &&
            ((this.districtcode==null && other.getDistrictcode()==null) || 
             (this.districtcode!=null &&
              this.districtcode.equals(other.getDistrictcode()))) &&
            ((this.dplevel==null && other.getDplevel()==null) || 
             (this.dplevel!=null &&
              this.dplevel.equals(other.getDplevel()))) &&
            ((this.dprs_appointmentAllPart==null && other.getDprs_appointmentAllPart()==null) || 
             (this.dprs_appointmentAllPart!=null &&
              this.dprs_appointmentAllPart.equals(other.getDprs_appointmentAllPart()))) &&
            ((this.dprs_checkupgradeprivilege==null && other.getDprs_checkupgradeprivilege()==null) || 
             (this.dprs_checkupgradeprivilege!=null &&
              this.dprs_checkupgradeprivilege.equals(other.getDprs_checkupgradeprivilege()))) &&
            ((this.dprs_childName==null && other.getDprs_childName()==null) || 
             (this.dprs_childName!=null &&
              this.dprs_childName.equals(other.getDprs_childName()))) &&
            ((this.dprs_delayed==null && other.getDprs_delayed()==null) || 
             (this.dprs_delayed!=null &&
              this.dprs_delayed.equals(other.getDprs_delayed()))) &&
            ((this.dprs_digipriv==null && other.getDprs_digipriv()==null) || 
             (this.dprs_digipriv!=null &&
              this.dprs_digipriv.equals(other.getDprs_digipriv()))) &&
            ((this.dprs_digiprivs==null && other.getDprs_digiprivs()==null) || 
             (this.dprs_digiprivs!=null &&
              java.util.Arrays.equals(this.dprs_digiprivs, other.getDprs_digiprivs()))) &&
            ((this.dprs_encryption==null && other.getDprs_encryption()==null) || 
             (this.dprs_encryption!=null &&
              this.dprs_encryption.equals(other.getDprs_encryption()))) &&
            ((this.dprs_hook==null && other.getDprs_hook()==null) || 
             (this.dprs_hook!=null &&
              this.dprs_hook.equals(other.getDprs_hook()))) &&
            ((this.dprs_hooks==null && other.getDprs_hooks()==null) || 
             (this.dprs_hooks!=null &&
              java.util.Arrays.equals(this.dprs_hooks, other.getDprs_hooks()))) &&
            ((this.dprs_pay==null && other.getDprs_pay()==null) || 
             (this.dprs_pay!=null &&
              this.dprs_pay.equals(other.getDprs_pay()))) &&
            ((this.dprs_permitbinding==null && other.getDprs_permitbinding()==null) || 
             (this.dprs_permitbinding!=null &&
              this.dprs_permitbinding.equals(other.getDprs_permitbinding()))) &&
            ((this.dprs_upgradeprivilege==null && other.getDprs_upgradeprivilege()==null) || 
             (this.dprs_upgradeprivilege!=null &&
              this.dprs_upgradeprivilege.equals(other.getDprs_upgradeprivilege()))) &&
            ((this.dprs_useapp==null && other.getDprs_useapp()==null) || 
             (this.dprs_useapp!=null &&
              this.dprs_useapp.equals(other.getDprs_useapp()))) &&
            ((this.dprs_vaccineshortage==null && other.getDprs_vaccineshortage()==null) || 
             (this.dprs_vaccineshortage!=null &&
              this.dprs_vaccineshortage.equals(other.getDprs_vaccineshortage()))) &&
            ((this.enddate==null && other.getEnddate()==null) || 
             (this.enddate!=null &&
              this.enddate.equals(other.getEnddate()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.immunitytimes==null && other.getImmunitytimes()==null) || 
             (this.immunitytimes!=null &&
              this.immunitytimes.equals(other.getImmunitytimes()))) &&
            ((this.include_43==null && other.getInclude_43()==null) || 
             (this.include_43!=null &&
              this.include_43.equals(other.getInclude_43()))) &&
            ((this.mode==null && other.getMode()==null) || 
             (this.mode!=null &&
              this.mode.equals(other.getMode()))) &&
            ((this.prefix_code==null && other.getPrefix_code()==null) || 
             (this.prefix_code!=null &&
              this.prefix_code.equals(other.getPrefix_code()))) &&
            ((this.school_type1==null && other.getSchool_type1()==null) || 
             (this.school_type1!=null &&
              this.school_type1.equals(other.getSchool_type1()))) &&
            ((this.school_type2==null && other.getSchool_type2()==null) || 
             (this.school_type2!=null &&
              this.school_type2.equals(other.getSchool_type2()))) &&
            ((this.selected_depa_level==null && other.getSelected_depa_level()==null) || 
             (this.selected_depa_level!=null &&
              this.selected_depa_level.equals(other.getSelected_depa_level()))) &&
            ((this.startdate==null && other.getStartdate()==null) || 
             (this.startdate!=null &&
              this.startdate.equals(other.getStartdate())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    @Override
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCitys_ID() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCitys_ID());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCitys_ID(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCountyid() != null) {
            _hashCode += getCountyid().hashCode();
        }
        if (getDb_flag() != null) {
            _hashCode += getDb_flag().hashCode();
        }
        if (getDein_workday() != null) {
            _hashCode += getDein_workday().hashCode();
        }
        if (getDepa_area() != null) {
            _hashCode += getDepa_area().hashCode();
        }
        if (getDepa_begindate() != null) {
            _hashCode += getDepa_begindate().hashCode();
        }
        if (getDepa_ca_indate() != null) {
            _hashCode += getDepa_ca_indate().hashCode();
        }
        if (getDepa_ca_use() != null) {
            _hashCode += getDepa_ca_use().hashCode();
        }
        if (getDepa_cardprivilege() != null) {
            _hashCode += getDepa_cardprivilege().hashCode();
        }
        if (getDepa_cardverify() != null) {
            _hashCode += getDepa_cardverify().hashCode();
        }
        if (getDepa_code() != null) {
            _hashCode += getDepa_code().hashCode();
        }
        if (getDepa_codes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepa_codes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepa_codes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDepa_controlstate() != null) {
            _hashCode += getDepa_controlstate().hashCode();
        }
        if (getDepa_count() != null) {
            _hashCode += getDepa_count().hashCode();
        }
        if (getDepa_date() != null) {
            _hashCode += getDepa_date().hashCode();
        }
        if (getDepa_district() != null) {
            _hashCode += getDepa_district().hashCode();
        }
        if (getDepa_download() != null) {
            _hashCode += getDepa_download().hashCode();
        }
        if (getDepa_downloadall() != null) {
            _hashCode += getDepa_downloadall().hashCode();
        }
        if (getDepa_editdate() != null) {
            _hashCode += getDepa_editdate().hashCode();
        }
        if (getDepa_enddate() != null) {
            _hashCode += getDepa_enddate().hashCode();
        }
        if (getDepa_firsttime() != null) {
            _hashCode += getDepa_firsttime().hashCode();
        }
        if (getDepa_fullname() != null) {
            _hashCode += getDepa_fullname().hashCode();
        }
        if (getDepa_hospital() != null) {
            _hashCode += getDepa_hospital().hashCode();
        }
        if (getDepa_id() != null) {
            _hashCode += getDepa_id().hashCode();
        }
        if (getDepa_ismaternity() != null) {
            _hashCode += getDepa_ismaternity().hashCode();
        }
        if (getDepa_isrpt() != null) {
            _hashCode += getDepa_isrpt().hashCode();
        }
        if (getDepa_ldtdprivilege() != null) {
            _hashCode += getDepa_ldtdprivilege().hashCode();
        }
        if (getDepa_ldtdverify() != null) {
            _hashCode += getDepa_ldtdverify().hashCode();
        }
        if (getDepa_licence() != null) {
            _hashCode += getDepa_licence().hashCode();
        }
        if (getDepa_md5() != null) {
            _hashCode += getDepa_md5().hashCode();
        }
        if (getDepa_name() != null) {
            _hashCode += getDepa_name().hashCode();
        }
        if (getDepa_nationid() != null) {
            _hashCode += getDepa_nationid().hashCode();
        }
        if (getDepa_obstetrics_use() != null) {
            _hashCode += getDepa_obstetrics_use().hashCode();
        }
        if (getDepa_password() != null) {
            _hashCode += getDepa_password().hashCode();
        }
        if (getDepa_paystate() != null) {
            _hashCode += getDepa_paystate().hashCode();
        }
        if (getDepa_privilege() != null) {
            _hashCode += getDepa_privilege().hashCode();
        }
        if (getDepa_queue() != null) {
            _hashCode += getDepa_queue().hashCode();
        }
        if (getDepa_rank() != null) {
            _hashCode += getDepa_rank().hashCode();
        }
        if (getDepa_relation_depaid() != null) {
            _hashCode += getDepa_relation_depaid().hashCode();
        }
        if (getDepa_secondtime() != null) {
            _hashCode += getDepa_secondtime().hashCode();
        }
        if (getDepa_use() != null) {
            _hashCode += getDepa_use().hashCode();
        }
        if (getDepa_validperiod() != null) {
            _hashCode += getDepa_validperiod().hashCode();
        }
        if (getDepa_zone() != null) {
            _hashCode += getDepa_zone().hashCode();
        }
        if (getDepartment() != null) {
            _hashCode += getDepartment().hashCode();
        }
        if (getDistrictcode() != null) {
            _hashCode += getDistrictcode().hashCode();
        }
        if (getDplevel() != null) {
            _hashCode += getDplevel().hashCode();
        }
        if (getDprs_appointmentAllPart() != null) {
            _hashCode += getDprs_appointmentAllPart().hashCode();
        }
        if (getDprs_checkupgradeprivilege() != null) {
            _hashCode += getDprs_checkupgradeprivilege().hashCode();
        }
        if (getDprs_childName() != null) {
            _hashCode += getDprs_childName().hashCode();
        }
        if (getDprs_delayed() != null) {
            _hashCode += getDprs_delayed().hashCode();
        }
        if (getDprs_digipriv() != null) {
            _hashCode += getDprs_digipriv().hashCode();
        }
        if (getDprs_digiprivs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDprs_digiprivs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDprs_digiprivs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDprs_encryption() != null) {
            _hashCode += getDprs_encryption().hashCode();
        }
        if (getDprs_hook() != null) {
            _hashCode += getDprs_hook().hashCode();
        }
        if (getDprs_hooks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDprs_hooks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDprs_hooks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDprs_pay() != null) {
            _hashCode += getDprs_pay().hashCode();
        }
        if (getDprs_permitbinding() != null) {
            _hashCode += getDprs_permitbinding().hashCode();
        }
        if (getDprs_upgradeprivilege() != null) {
            _hashCode += getDprs_upgradeprivilege().hashCode();
        }
        if (getDprs_useapp() != null) {
            _hashCode += getDprs_useapp().hashCode();
        }
        if (getDprs_vaccineshortage() != null) {
            _hashCode += getDprs_vaccineshortage().hashCode();
        }
        if (getEnddate() != null) {
            _hashCode += getEnddate().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getImmunitytimes() != null) {
            _hashCode += getImmunitytimes().hashCode();
        }
        if (getInclude_43() != null) {
            _hashCode += getInclude_43().hashCode();
        }
        if (getMode() != null) {
            _hashCode += getMode().hashCode();
        }
        if (getPrefix_code() != null) {
            _hashCode += getPrefix_code().hashCode();
        }
        if (getSchool_type1() != null) {
            _hashCode += getSchool_type1().hashCode();
        }
        if (getSchool_type2() != null) {
            _hashCode += getSchool_type2().hashCode();
        }
        if (getSelected_depa_level() != null) {
            _hashCode += getSelected_depa_level().hashCode();
        }
        if (getStartdate() != null) {
            _hashCode += getStartdate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Department.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.vaccine.nipm.jwx.com", "Department"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("citys_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "citys_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countyid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "countyid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("db_flag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "db_flag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dein_workday");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dein_workday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_area");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_begindate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_begindate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_ca_indate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_ca_indate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_ca_use");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_ca_use"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_cardprivilege");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_cardprivilege"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_cardverify");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_cardverify"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_codes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_codes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_controlstate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_controlstate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_count");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_district");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_district"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_download");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_download"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_downloadall");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_downloadall"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_editdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_editdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_enddate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_enddate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_firsttime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_firsttime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_fullname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_fullname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_hospital");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_hospital"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_ismaternity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_ismaternity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_isrpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_isrpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_ldtdprivilege");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_ldtdprivilege"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_ldtdverify");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_ldtdverify"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_licence");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_licence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_md5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_md5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_nationid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_nationid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_obstetrics_use");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_obstetrics_use"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_paystate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_paystate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_privilege");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_privilege"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_queue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_queue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_rank");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_rank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_relation_depaid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_relation_depaid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_secondtime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_secondtime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_use");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_use"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_validperiod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_validperiod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_zone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_zone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("department");
        elemField.setXmlName(new javax.xml.namespace.QName("", "department"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("districtcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "districtcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dplevel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dplevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_appointmentAllPart");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_appointmentAllPart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_checkupgradeprivilege");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_checkupgradeprivilege"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_childName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_childName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_delayed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_delayed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_digipriv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_digipriv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_digiprivs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_digiprivs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_encryption");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_encryption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_hook");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_hook"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_hooks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_hooks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_pay");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_pay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_permitbinding");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_permitbinding"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_upgradeprivilege");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_upgradeprivilege"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_useapp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_useapp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dprs_vaccineshortage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dprs_vaccineshortage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enddate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enddate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("immunitytimes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "immunitytimes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("include_43");
        elemField.setXmlName(new javax.xml.namespace.QName("", "include_43"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prefix_code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prefix_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("school_type1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "school_type1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("school_type2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "school_type2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selected_depa_level");
        elemField.setXmlName(new javax.xml.namespace.QName("", "selected_depa_level"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
