package com.feifandaiyu.feifan.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by houdaichang on 2017/6/30.
 */

public class FinishWorkBean {

    /**
     * code : 1
     * list : {"06":[{"summoney":"400000","month":"2017-06","max":"1600000","tc":2000}],"07":[{"summoney":"500000","month":"2017-07","max":"500000","tc":2500}],"01":[{"month":"2017-01","summoney":"0","max":"0","tc":"0"}],"02":[{"month":"2017-02","summoney":"0","max":"0","tc":"0"}],"03":[{"month":"2017-03","summoney":"0","max":"0","tc":"0"}],"04":[{"month":"2017-04","summoney":"0","max":"0","tc":"0"}],"05":[{"month":"2017-05","summoney":"0","max":"0","tc":"0"}],"08":[{"month":"2017-08","summoney":"0","max":"0","tc":"0"}],"09":[{"month":"2017-09","summoney":"0","max":"0","tc":"0"}],"10":[{"month":"2017-10","summoney":"0","max":"0","tc":"0"}],"11":[{"month":"2017-11","summoney":"0","max":"0","tc":"0"}],"12":[{"month":"2017-12","summoney":"0","max":"0","tc":"0"}]}
     */

    private int code;
    private ListBean list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        @SerializedName("06")
        private List<_$06Bean> _$06;
        @SerializedName("07")
        private List<_$07Bean> _$07;
        @SerializedName("01")
        private List<_$01Bean> _$01;
        @SerializedName("02")
        private List<_$02Bean> _$02;
        @SerializedName("03")
        private List<_$03Bean> _$03;
        @SerializedName("04")
        private List<_$04Bean> _$04;
        @SerializedName("05")
        private List<_$05Bean> _$05;
        @SerializedName("08")
        private List<_$08Bean> _$08;
        @SerializedName("09")
        private List<_$09Bean> _$09;
        @SerializedName("10")
        private List<_$10Bean> _$10;
        @SerializedName("11")
        private List<_$11Bean> _$11;
        @SerializedName("12")
        private List<_$12Bean> _$12;

        public List<_$06Bean> get_$06() {
            return _$06;
        }

        public void set_$06(List<_$06Bean> _$06) {
            this._$06 = _$06;
        }

        public List<_$07Bean> get_$07() {
            return _$07;
        }

        public void set_$07(List<_$07Bean> _$07) {
            this._$07 = _$07;
        }

        public List<_$01Bean> get_$01() {
            return _$01;
        }

        public void set_$01(List<_$01Bean> _$01) {
            this._$01 = _$01;
        }

        public List<_$02Bean> get_$02() {
            return _$02;
        }

        public void set_$02(List<_$02Bean> _$02) {
            this._$02 = _$02;
        }

        public List<_$03Bean> get_$03() {
            return _$03;
        }

        public void set_$03(List<_$03Bean> _$03) {
            this._$03 = _$03;
        }

        public List<_$04Bean> get_$04() {
            return _$04;
        }

        public void set_$04(List<_$04Bean> _$04) {
            this._$04 = _$04;
        }

        public List<_$05Bean> get_$05() {
            return _$05;
        }

        public void set_$05(List<_$05Bean> _$05) {
            this._$05 = _$05;
        }

        public List<_$08Bean> get_$08() {
            return _$08;
        }

        public void set_$08(List<_$08Bean> _$08) {
            this._$08 = _$08;
        }

        public List<_$09Bean> get_$09() {
            return _$09;
        }

        public void set_$09(List<_$09Bean> _$09) {
            this._$09 = _$09;
        }

        public List<_$10Bean> get_$10() {
            return _$10;
        }

        public void set_$10(List<_$10Bean> _$10) {
            this._$10 = _$10;
        }

        public List<_$11Bean> get_$11() {
            return _$11;
        }

        public void set_$11(List<_$11Bean> _$11) {
            this._$11 = _$11;
        }

        public List<_$12Bean> get_$12() {
            return _$12;
        }

        public void set_$12(List<_$12Bean> _$12) {
            this._$12 = _$12;
        }

        public static class _$06Bean {
            /**
             * summoney : 400000
             * month : 2017-06
             * max : 1600000
             * tc : 2000
             */

            private int summoney;
            private String month;
            private int max;
            private int tc;

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$07Bean {
            /**
             * summoney : 500000
             * month : 2017-07
             * max : 500000
             * tc : 2500
             */

            private int summoney;
            private String month;
            private int max;
            private int tc;

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$01Bean {
            /**
             * month : 2017-01
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$02Bean {
            /**
             * month : 2017-02
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$03Bean {
            /**
             * month : 2017-03
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$04Bean {
            /**
             * month : 2017-04
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$05Bean {
            /**
             * month : 2017-05
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$08Bean {
            /**
             * month : 2017-08
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$09Bean {
            /**
             * month : 2017-09
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$10Bean {
            /**
             * month : 2017-10
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$11Bean {
            /**
             * month : 2017-11
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }

        public static class _$12Bean {
            /**
             * month : 2017-12
             * summoney : 0
             * max : 0
             * tc : 0
             */

            private String month;
            private int summoney;
            private int max;
            private int tc;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getSummoney() {
                return summoney;
            }

            public void setSummoney(int summoney) {
                this.summoney = summoney;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getTc() {
                return tc;
            }

            public void setTc(int tc) {
                this.tc = tc;
            }
        }
    }
}
