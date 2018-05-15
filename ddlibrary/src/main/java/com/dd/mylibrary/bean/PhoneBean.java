package com.dd.mylibrary.bean;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class PhoneBean {


    private String date;

    private String tel;
    private String code;

    private String captcha;
    private String mobile;

    private String name;
    private String sex;
    private String address;

    private String idCardObv;// 正面
    private String idCardRev;//反面
    private String idCard;//身份证号码

    private String xgtoken;

    private int pageNumber;
    private int pageSize;

    private String fileName;
    private String fileContentBase64;

    private String status;
    private String orderNo;

    private String comIds;

    private String arrivalTime;
    private String id;
    private String isEvaluated;

    private String cancelReason;

    private String score;
    private PagerBean pageBean;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public PagerBean getPageBean() {
        return pageBean;
    }

    public String getXgtoken() {
        return xgtoken;
    }

    public void setXgtoken(String xgtoken) {
        this.xgtoken = xgtoken;
    }

    public void setPageBean(PagerBean pageBean) {
        this.pageBean = pageBean;
    }

    public static class PagerBean {
        int pageNumber;
        int pageSize;

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(String isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public String getComIds() {
        return comIds;
    }

    public void setComIds(String comIds) {
        this.comIds = comIds;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContentBase64() {
        return fileContentBase64;
    }

    public void setFileContentBase64(String fileContentBase64) {
        this.fileContentBase64 = fileContentBase64;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
