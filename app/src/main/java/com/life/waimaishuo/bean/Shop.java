package com.life.waimaishuo.bean;

import java.math.BigInteger;
import java.util.List;

public class Shop {


    BigInteger organization_id; //组织ID
    BigInteger brand_id;    //品牌ID
    BigInteger account_id;  //账户ID
    int shop_type;   //1 外卖  2商场

    String shop_head_portrait;  //店铺头像
    String shop_name;   //店铺名称
    String reservation_call;    //订餐电话
    String alternate_phone; //备用电话
    String province;    //省
    String city;    //市
    String district;    //区

    String shop_address;    //门店地址
    String shop_category;   //门店品类
    String favorable_rate;  //评分
    String goods_pictures;  //门店的展示商品的图片
    String shop_image;  //门店图片
    String shop_qr_code;    //门店二维码
    String shop_number; //门店号
    String admin_phone; //管理员手机号
    String last_equipment_name; //最后一次登陆设备
    String last_login_address;  //最后一次登录地址
    String last_login_time; //最后一次登录时间
    String notice;  //公告
    String synopsis;    //简介
    String invoice; //发票
    String effective_date;  //生效时间
    String tag_value;   //标签值
    int audit_state;//审核状态 0审核中 1审核通过 2审核未通过
    String audit_cause; //审核未通过原因
    int state;   //状态 0正常 1关闭 2租用到期 3注销 4删除
    double sort;    //排序
    BigInteger create_id;   //创建人ID
    String create_time; //创建时间
    BigInteger update_id;   //更新人ID
    String update_time; //更新时间
    int open_state;  //营业状态 1上班 2下班
    String polygon; //多边形轮廓坐标值

    String create_name; //创建人
    String account_number;  //账号
    String user_pwd;   //密码
    int shop_nature; //店铺性质 1自提和外卖 2仅外卖 3仅自提
    List<String> synopsis_img;    //简介图 ["地址1","地址2"]
    String shop_sign;   //门店招牌
    String logistics_rate;  //物流服务
    String serve_rate;  //服务态度
    int dist_priority;   //外卖是否优先 1优先 2不优先
    int pick_priority;   //自提是否优先 1优先 2不优先
    String return_province; //退货省
    String return_city; //退货市
    String return_district; //退货区
    String return_address;  //退货详细地址
    String return_contacts; //退件收货人
    String return_phone;    //退件收货人手机号
    String cash_deposit;    //保证金
    int is_message;  //是否开启消息推送 1是 2否
    String shop_province;   //门店省
    String shop_city;   //门店市
    String shop_district;   //门店区
    BigInteger recommend_type;  //推荐分类ID


    // FIXME: 2020/12/18 暂存以下属性值
    String number_of_fans;  //粉丝数
    String sale_count_per_month;
    MemberCard memberCard;

    public Shop() {
    }

    public Shop(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_head_portrait() {
        return shop_head_portrait;
    }

    public void setShop_head_portrait(String shop_head_portrait) {
        this.shop_head_portrait = shop_head_portrait;
    }

    public String getFavorable_rate() {
        return favorable_rate;
    }

    public void setFavorable_rate(String favorable_rate) {
        this.favorable_rate = favorable_rate;
    }

    public String getNumber_of_fans() {
        return number_of_fans;
    }

    public void setNumber_of_fans(String number_of_fans) {
        this.number_of_fans = number_of_fans;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getSale_count_per_month() {
        return sale_count_per_month;
    }

    public void setSale_count_per_month(String sale_count_per_month) {
        this.sale_count_per_month = sale_count_per_month;
    }

    public MemberCard getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCard memberCard) {
        this.memberCard = memberCard;
    }
}
