package com.dd.mylibrary.bean;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class UserInfoBean {


    /**
     * id : 9
     * name : 哟哟
     * idCard : null
     * tel : 18915272004
     * status : 0
     * icon : null
     * authStatus : 0
     * idCardObv : null
     * idCardRev : null
     * sex : 女
     * address : 可怜了
     * updateDate : Mon Apr 09 10:50:36 CST 2018
     * delFlag : 0
     * createDate : Mon Apr 09 10:50:36 CST 2018
     */

    private String id;
    private String name;
    private String idCard;
    private String tel;
    private String status;
    private String icon;
    private String authStatus;
    private String idCardObv;
    private String idCardRev;
    private String sex;
    private String address="";
    private String updateDate;
    private String delFlag;
    private String createDate;

    public String work ="0";
    public String workStatus="1";

    public String isNeedReresh = "0";//返回主界面是否需要刷新订单列表 0 不需要  1 需要

    public String getIsNeedReresh() {
        return isNeedReresh;
    }

    public void setIsNeedReresh(String isNeedReresh) {
        this.isNeedReresh = isNeedReresh;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getIdCardObv() {
        return idCardObv;
    }

    public void setIdCardObv(String idCardObv) {
        this.idCardObv = idCardObv;
    }

    public String getIdCardRev() {
        return idCardRev;
    }

    public void setIdCardRev(String idCardRev) {
        this.idCardRev = idCardRev;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
