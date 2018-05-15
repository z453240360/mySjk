package com.dd.mylibrary.bean;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ParamsBeam {


    /**
     * app_key : app_id_2
     * data : {"isEvaluated":"0","pagebean":{"pageNumber":1,"pageSize":10},"status":0}
     * name : appOrder.getOrderPage
     * format : json
     * version : 1.0
     * nonce : 4b75b6f5-686b-40c7-9cc0-02ddbbe142dc
     * timestamp : 1521096145810
     * token : F7AHNFQOKPRQTKYHDWUKCR2X5IP7P4IQNNCPRN6VQNVN6NHTTULOLHZS5OTDCQQBOOX3LCUSO4NFA2KG3P2LEE7CERH5SHVK5VFUCPKQE3XXUU6WCT53B4KKE2LXNZTEOFKMADQBRNAD3ET3WM2ZUT4OQNOF56ZT6PPIMXBRL2AEU4UTKXVVUC2JDN6KUOY34HXU26HUYUXNX2ASEPBTGWSFSHT7VJ2NSENIJGSLHMQ7OUVBMRKL6EWH3TPGXJV5JFUZKW7N5C5IF77MM4FRNSRF4Y7KIHPALW44JLFAX7BHZQBR6LLA
     */

    private String app_key;
    private DataBean data;
    private String name;
    private String format;
    private String version;
    private String nonce;
    private long timestamp;
    private String token;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * isEvaluated : 0
         * pagebean : {"pageNumber":1,"pageSize":10}
         * status : 0
         */

        private String isEvaluated;
        private PagebeanBean pagebean;
        private int status;

        public String getIsEvaluated() {
            return isEvaluated;
        }

        public void setIsEvaluated(String isEvaluated) {
            this.isEvaluated = isEvaluated;
        }

        public PagebeanBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PagebeanBean pagebean) {
            this.pagebean = pagebean;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class PagebeanBean {
            /**
             * pageNumber : 1
             * pageSize : 10
             */

            private int pageNumber;
            private int pageSize;

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
    }
}
