/**
 * ServiceChild.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package io.yfjz.service.jwxplat;

public interface ServiceChild extends java.rmi.Remote {
    public int downloadChildrenCount(java.lang.String username, java.lang.String password, java.lang.String depCode, java.lang.String downloadDatetime) throws java.rmi.RemoteException;
    public byte[] downloadChildren(java.lang.String username, java.lang.String password, java.lang.String chil_id, java.lang.String depCode, java.lang.String downloadDatetime) throws java.rmi.RemoteException;
    public byte[] downloadTransfer(java.lang.String username, java.lang.String password, java.lang.String depCode, java.lang.String downloadDatetime) throws java.rmi.RemoteException;
    public byte[] downloadInoculation(java.lang.String username, java.lang.String password, java.lang.String chil_id, java.lang.String depCode, java.lang.String downloadDatetime, java.lang.String stat) throws java.rmi.RemoteException;
    public java.lang.String downloadTimeModify(java.lang.String username, java.lang.String password, java.lang.String depCode) throws java.rmi.RemoteException;
    public java.lang.String uploadTimeModify(java.lang.String username, java.lang.String password, java.lang.String depCode) throws java.rmi.RemoteException;
    public java.lang.String uploadChildrenH1N1_old(java.lang.String username, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadChildrenH1N1(java.lang.String depacode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadChildrenInfo(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadChildrenInfoDG(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public int getChildrenCount(java.lang.String username, java.lang.String password, java.lang.String chil_birthday, java.lang.String chil_name, java.lang.String chil_sex, java.lang.String chil_mother, java.lang.String chil_father) throws java.rmi.RemoteException;
    public int childrenCountGet(java.lang.String username, java.lang.String password, java.lang.String depCode, java.lang.String downloadDatetime) throws java.rmi.RemoteException;
    public byte[] childrenDownload(java.lang.String username, java.lang.String password, java.lang.String chil_id, java.lang.String depCode, java.lang.String downloadDatetime) throws java.rmi.RemoteException;
    public byte[] downloadDepartmentHabiaddress(java.lang.String departmentCode, java.lang.String password, java.lang.String uploadDate) throws java.rmi.RemoteException;
    public java.lang.String uploadPoIntensifyListReport(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadPoIntensifyInocReport(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadPoIntensifyDetailList(java.lang.String departmentCode, java.lang.String password, java.lang.String deleFlag, byte[] info) throws java.rmi.RemoteException;
    public byte[] downloadImmunization(java.lang.String departmentCode, java.lang.String password, java.lang.String maxDatetime) throws java.rmi.RemoteException;
    public byte[] downloadNewBornInoculateCommunal(java.lang.String departmentCode, java.lang.String password, java.lang.String motherName, java.lang.String newBornSex, java.lang.String newBornBirthDate) throws java.rmi.RemoteException;
    public java.lang.String uploadNewbornBackCommunal(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public byte[] downloadNewBornFJCommunal(java.lang.String departmentCode, java.lang.String password, java.lang.String motherName, java.lang.String newBornSex, java.lang.String newBornBirthDate) throws java.rmi.RemoteException;
    public java.lang.String updateChildAttributive(java.lang.String departmentCode, java.lang.String password, java.lang.String childcode, java.lang.String chatCurdepartment) throws java.rmi.RemoteException;
    public byte[] downloadChildRepeatLeave(java.lang.String departmentCode, java.lang.String password, java.lang.String delDate) throws java.rmi.RemoteException;
    public java.lang.String uploadDepartmentHabiaddress(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public byte[] downloadVaccByChildNoInside_15(java.lang.String acode, java.lang.String departmentCode, java.lang.String password, java.lang.String childNo, java.lang.String flag) throws java.rmi.RemoteException;
    public byte[] downloadVaccByTicketno(java.lang.String departmentCode, java.lang.String password, java.lang.String childTicketno) throws java.rmi.RemoteException;
    public byte[] downloadNewBornFJCondition(java.lang.String departmentCode, java.lang.String password, java.lang.String type, java.lang.String value) throws java.rmi.RemoteException;
    public byte[] pushClientMessageToAppS1(java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String uploadVersion(java.lang.String depaCode, java.lang.String version, java.lang.String upgradedate) throws java.rmi.RemoteException;
    public byte[] downloadVaccCertiInspection(java.lang.String departmentCode, java.lang.String password, java.lang.String maxDatetime) throws java.rmi.RemoteException;
    public int checkChildHere(java.lang.String chil_code) throws java.rmi.RemoteException;
    public java.lang.String uploadCaseReportData(java.lang.String departmentCode, java.lang.String depaPassword, java.lang.String organCode, java.lang.String userPassword, java.lang.String user, java.lang.String arg1, byte[] arg2) throws java.rmi.RemoteException;
    public java.lang.String uploadVaccineReportData(java.lang.String departmentCode, java.lang.String depaPassword, java.lang.String organCode, java.lang.String userPassword, java.lang.String user, java.lang.String arg1, byte[] arg2) throws java.rmi.RemoteException;
    public java.lang.String uploadVaccinateRatioReportData(java.lang.String departmentCode, java.lang.String depaPassword, java.lang.String organCode, java.lang.String userPassword, java.lang.String user, java.lang.String arg1, byte[] arg2) throws java.rmi.RemoteException;
    public java.lang.String immigrateChild(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3) throws java.rmi.RemoteException;
    public java.lang.String queryChildA(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1) throws java.rmi.RemoteException;
    public java.lang.String queryChildB(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public java.lang.String queryChildC(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1) throws java.rmi.RemoteException;
    public java.lang.String queryChildren(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenA(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenB(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenBatch(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenC(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4, java.lang.String arg5) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenD(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenE(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenF(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, java.lang.String arg4) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenG(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public java.lang.String queryChildrenH(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1) throws java.rmi.RemoteException;
    public java.lang.String queryPiats(java.lang.String departmentCode, java.lang.String password, java.lang.Object[] arg1) throws java.rmi.RemoteException;
    public byte[] pushMessageInfoToApps(java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String pushClientMessageToAppS(java.lang.String type, java.lang.String jsonData) throws java.rmi.RemoteException;
    public java.lang.String ipvSetup(java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String bopvSetup(java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String noticeDownload(java.lang.String depaCode, java.lang.String date) throws java.rmi.RemoteException;
    public java.lang.String notificationDownloadState(java.lang.String id) throws java.rmi.RemoteException;
    public byte[] uploadBaseInfo(java.lang.String departmentCode, java.lang.String password, java.lang.String dataType, java.lang.String lastUpdateTime) throws java.rmi.RemoteException;
    public java.lang.String pushChild(java.lang.String nebo_code_fy) throws java.rmi.RemoteException;
    public java.lang.String uploadDigitalClinicInfos(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadDoseInfoChange(java.lang.String departmentCode, java.lang.String password, byte[] info, java.lang.String uploadType) throws java.rmi.RemoteException;
    public java.lang.String childInoculationControl(java.lang.String updateDate) throws java.rmi.RemoteException;
    public java.lang.String uploadEpiManagerVersion(java.lang.String depaCode, java.lang.String version, java.lang.String upgradedate) throws java.rmi.RemoteException;
    public byte[] downloadNewBornInoculateBatchGz(java.lang.String departmentCode, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String uploadNewbornBackCommunalGz(java.lang.String departmentCode, java.lang.String password, java.lang.String nebo_id, java.lang.String nebo_downmac, java.lang.String nebo_downtype) throws java.rmi.RemoteException;
    public java.lang.String childlowbirthweight(java.lang.String uploadDate) throws java.rmi.RemoteException;
    public byte[] downloadVaccination(java.lang.String departmentCode, java.lang.String password, java.lang.String maxDatetime) throws java.rmi.RemoteException;
    public byte[] downloadVaccByCardNo(java.lang.String departmentCode, java.lang.String password, java.lang.String childCardno) throws java.rmi.RemoteException;
    public byte[] downloadClientSet(java.lang.String departmentCode, java.lang.String password) throws java.rmi.RemoteException;
    public byte[] downloadChildRepeatCode(java.lang.String departmentCode, java.lang.String password, java.lang.String delDate) throws java.rmi.RemoteException;
    public byte[] downloadNewBornInoculate(java.lang.String departmentCode, java.lang.String password, java.lang.String motherName, java.lang.String newBornSex, java.lang.String newBornBirthDate) throws java.rmi.RemoteException;
    public byte[] downloadNewBornInoculateNew(java.lang.String departmentCode, java.lang.String password, java.lang.String motherName, java.lang.String newBornSex, java.lang.String newBornBirthDate) throws java.rmi.RemoteException;
    public byte[] downloadNewBornInoculateByIdNew(java.lang.String departmentCode, java.lang.String password, java.lang.String nebo_id) throws java.rmi.RemoteException;
    public byte[] downloadNewBornInoculateByCardNo(java.lang.String departmentCode, java.lang.String password, java.lang.String cardno) throws java.rmi.RemoteException;
    public byte[] downloadNewBornInoculateBatch(java.lang.String departmentCode, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String uploadNewbornBack(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadReportAEFI(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadVaccCompletionReport(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadRoutineImmuTimesReport(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public byte[] downloadRoutineImmuTimesReport(java.lang.String departmentCode, java.lang.String password, java.lang.String datetime) throws java.rmi.RemoteException;
    public java.lang.String getServerDatetime() throws java.rmi.RemoteException;
    public java.lang.String uploadMigrationFromCity(java.lang.String departmentCode, java.lang.String password, byte[] info, java.lang.String flag) throws java.rmi.RemoteException;
    public java.lang.String uploadRoutineImmuReportFromCity(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadSecondImmuReportFromCity(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadChildTransInfo(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public byte[] downloadMigrationChildNo(java.lang.String departmentCode, java.lang.String password, java.lang.String immuDate) throws java.rmi.RemoteException;
    public byte[] downloadVaccByChildNo(java.lang.String departmentCode, java.lang.String password, java.lang.String childNo, java.lang.String state) throws java.rmi.RemoteException;
    public java.lang.String uploadRoutineImmuReport(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadClientBackup(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String uploadSecondImmuReport(java.lang.String departmentCode, java.lang.String password, byte[] info) throws java.rmi.RemoteException;
    public java.lang.String queryEmigratedChildren(java.lang.String departmentCode, java.lang.String password, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException;
    public byte[] downloadChildRepeatDelete(java.lang.String departmentCode, java.lang.String password, java.lang.String delDate) throws java.rmi.RemoteException;
    public java.lang.String getServerDatetimeNew(java.lang.String departmentCode, java.lang.String password) throws java.rmi.RemoteException;
    public byte[] downloadVaccByName(java.lang.String departmentCode, java.lang.String password, java.lang.String childName, java.lang.String birthDay, java.lang.String sex, java.lang.String motherName, java.lang.String regCountyCode) throws java.rmi.RemoteException;
    public byte[] downloadVaccByFarName(java.lang.String departmentCode, java.lang.String password, java.lang.String childName, java.lang.String birthDay, java.lang.String sex, java.lang.String fatherName, java.lang.String regCountyCode) throws java.rmi.RemoteException;
    public byte[] downloadVaccByChildID(java.lang.String departmentCode, java.lang.String password, java.lang.String childID) throws java.rmi.RemoteException;
    public byte[] downloadVaccByBirthNo(java.lang.String departmentCode, java.lang.String password, java.lang.String birthNo) throws java.rmi.RemoteException;
    public void main(java.lang.String[] args) throws java.rmi.RemoteException;
    public io.yfjz.service.jwxplat.Department getHighestLevelDepartment() throws java.rmi.RemoteException;
    public boolean checkPassword(java.lang.String str) throws java.rmi.RemoteException;
    public java.lang.String genRandomNum(int pwd_len) throws java.rmi.RemoteException;
}
