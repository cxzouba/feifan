package com.feifandaiyu.feifan.bean;

import android.support.annotation.NonNull;

import com.feifandaiyu.feifan.utils.PinYinUtil;

import java.util.List;

/**
 * Created by davidzhao on 2017/6/7.
 */

public class BrandCarUrlBean {

    /**
     * code : 1
     * list : [{"brand":"AC Schnitzer"},{"brand":"Arash"},{"brand":"阿尔法罗密欧"},{"brand":"阿斯顿·马丁"},{"brand":"安凯客车"},{"brand":"奥迪"},{"brand":"巴博斯"},{"brand":"宝骏"},{"brand":"宝马"},{"brand":"宝沃"},{"brand":"保时捷"},{"brand":"北京"},{"brand":"北汽幻速"},{"brand":"北汽绅宝"},{"brand":"北汽威旺"},{"brand":"北汽新能源"},{"brand":"北汽制造"},{"brand":"奔驰"},{"brand":"奔腾"},{"brand":"本田"},{"brand":"比亚迪"},{"brand":"标致"},{"brand":"别克"},{"brand":"宾利"},{"brand":"布加迪"},{"brand":"昌河"},{"brand":"长安"},{"brand":"长安商用"},{"brand":"长城"},{"brand":"成功汽车"},{"brand":"Dacia"},{"brand":"DMC"},{"brand":"DS"},{"brand":"大发"},{"brand":"大众"},{"brand":"道奇"},{"brand":"东风"},{"brand":"东风风度"},{"brand":"东风风神"},{"brand":"东风风行"},{"brand":"东风小康"},{"brand":"东南"},{"brand":"Faraday Future"},{"brand":"Fisker"},{"brand":"法拉利"},{"brand":"菲亚特"},{"brand":"丰田"},{"brand":"福迪"},{"brand":"福汽启腾"},{"brand":"福特"},{"brand":"福田"},{"brand":"GMC"},{"brand":"Gumpert"},{"brand":"观致"},{"brand":"光冈"},{"brand":"广汽传祺"},{"brand":"广汽吉奥"},{"brand":"Hennessey"},{"brand":"哈飞"},{"brand":"哈弗"},{"brand":"海格"},{"brand":"海马"},{"brand":"悍马"},{"brand":"恒天"},{"brand":"红旗"},{"brand":"华利"},{"brand":"华普"},{"brand":"华骐"},{"brand":"华颂"},{"brand":"华泰"},{"brand":"黄海"},{"brand":"Jeep"},{"brand":"吉利汽车"},{"brand":"江淮"},{"brand":"江铃"},{"brand":"江铃集团轻汽"},{"brand":"捷豹"},{"brand":"金杯"},{"brand":"金龙"},{"brand":"金旅"},{"brand":"九龙"},{"brand":"KTM"},{"brand":"卡尔森"},{"brand":"卡威"},{"brand":"开瑞"},{"brand":"凯佰赫"},{"brand":"凯迪拉克"},{"brand":"凯翼"},{"brand":"科尼赛克"},{"brand":"克莱斯勒"},{"brand":"兰博基尼"},{"brand":"蓝旗亚"},{"brand":"朗世"},{"brand":"劳斯莱斯"},{"brand":"雷克萨斯"},{"brand":"雷诺"},{"brand":"理念"},{"brand":"力帆"},{"brand":"莲花汽车"},{"brand":"猎豹汽车"},{"brand":"林肯"},{"brand":"铃木"},{"brand":"领志"},{"brand":"陆风"},{"brand":"路虎"},{"brand":"路特斯"},{"brand":"MG"},{"brand":"MINI"},{"brand":"马自达"},{"brand":"玛莎拉蒂"},{"brand":"迈巴赫"},{"brand":"迈凯伦"},{"brand":"摩根"},{"brand":"nanoFLOWCELL"},{"brand":"Noble"},{"brand":"纳智捷"},{"brand":"南京金龙"},{"brand":"讴歌"},{"brand":"欧宝"},{"brand":"欧朗"},{"brand":"帕加尼"},{"brand":"佩奇奥"},{"brand":"奇瑞"},{"brand":"启辰"},{"brand":"起亚"},{"brand":"前途"},{"brand":"Rezvani"},{"brand":"Rinspeed"},{"brand":"日产"},{"brand":"荣威"},{"brand":"如虎"},{"brand":"瑞麒"},{"brand":"Scion"},{"brand":"smart"},{"brand":"SPIRRA"},{"brand":"SSC"},{"brand":"萨博"},{"brand":"赛麟"},{"brand":"三菱"},{"brand":"上海"},{"brand":"上汽大通"},{"brand":"世爵"},{"brand":"双环"},{"brand":"双龙"},{"brand":"思铭"},{"brand":"斯巴鲁"},{"brand":"斯柯达"},{"brand":"特斯拉"},{"brand":"腾势"},{"brand":"威麟"},{"brand":"威兹曼"},{"brand":"潍柴英致"},{"brand":"沃尔沃"},{"brand":"沃克斯豪尔"},{"brand":"五菱汽车"},{"brand":"五十铃"},{"brand":"西雅特"},{"brand":"现代"},{"brand":"雪佛兰"},{"brand":"雪铁龙"},{"brand":"野马汽车"},{"brand":"一汽"},{"brand":"依维柯"},{"brand":"英菲尼迪"},{"brand":"永源"},{"brand":"游侠"},{"brand":"Zenvo"},{"brand":"之诺"},{"brand":"知豆"},{"brand":"中华"},{"brand":"中兴"},{"brand":"众泰"}]
     */

    private int code;
    private List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Comparable<ListBean>{
        /**
         * brand : AC Schnitzer
         */

        private String brand;
        public String pinyin;


        public String getString(String brand){
            this.pinyin = PinYinUtil.getPinyin(brand);
            return pinyin;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        @Override
        public int compareTo(@NonNull ListBean o) {
            return pinyin.compareTo(o.pinyin);
        }
    }
}
