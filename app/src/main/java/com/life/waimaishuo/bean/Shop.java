package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.List;

public class Shop implements Parcelable {

    @SerializedName(value = "shopId")
    int shopId;
    BigInteger organization_id; //组织ID
    @SerializedName(value = "brandId")
    BigInteger brand_id;    //品牌ID
    BigInteger account_id;  //账户ID
    @SerializedName(value = "shopType")
    int shop_type;   //1 外卖  2商场

    @SerializedName(value = "shopHeadPortrait")
    String shop_head_portrait;  //店铺头像
    @SerializedName(value = "shopName")
    String shop_name;   //店铺名称
    @SerializedName(value = "reservationCall")
    String reservation_call;    //订餐电话
    String alternate_phone; //备用电话
    String province;    //省
    String city;    //市
    String district;    //区
    @SerializedName(value = "lon")
    String log; //经度
    @SerializedName(value = "lat")
    String lat; //纬度
    @SerializedName(value = "shopAddress")
    String shop_address;    //门店地址
    @SerializedName(value = "shopCategory")
    String shop_category;   //门店品类
    @SerializedName(value = "favorableRate")
    String favorable_rate;  //评分
    @SerializedName(value = "monSalesVolume")
    String monSalesVolume;
    String goods_pictures;  //门店的展示商品的图片
    String shop_qr_code;    //门店二维码
    String shop_number; //门店号
    String admin_phone; //管理员手机号
    String last_equipment_name; //最后一次登陆设备
    String last_login_address;  //最后一次登录地址
    String last_login_time; //最后一次登录时间
    @SerializedName(value = "notice")
    String notice;  //公告
    String synopsis;    //简介
    String invoice; //发票
    @SerializedName(value = "shopTags")
    String tag_value;   //标签值
    int audit_state;//审核状态 0审核中 1审核通过 2审核未通过
    String audit_cause; //审核未通过原因
    @SerializedName(value = "state")
    int state;   //状态 0正常 1关闭 2租用到期 3注销 4删除
    double sort;    //排序
    BigInteger create_id;   //创建人ID
    String create_time; //创建时间
    BigInteger update_id;   //更新人ID
    String update_time; //更新时间
    @SerializedName(value = "openState")
    int open_state;  //营业状态 1上班 2下班
    @SerializedName(value = "effectiveDate")
    String effectiveDate;   //生效时间(9:00-18:00)
    String polygon; //多边形轮廓坐标值

    String create_name; //创建人
    String account_number;  //账号
    String user_pwd;   //密码
    @SerializedName(value = "shopNature")
    int shop_nature; //店铺性质 1自提和外卖 2仅外卖 3仅自提
    @SerializedName(value = "synopsisImg")
    String synopsis_img;    //简介图 ["地址1","地址2"]
    @SerializedName(value = "shopSign")
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
    @SerializedName(value = "shopImage")
    String shopImage;
    @SerializedName(value = "goodsInfo")
    List<Goods> goodsInfoList;  //商品列表

    @SerializedName(value = "distance")
    double distance;    //距离
    @SerializedName(value = "distPattern")
    int distPattern;    //配送模式 1专送 2自配送
    @SerializedName(value = "distTeamName")
    String distTeamName;    //配送团队名称
    @SerializedName(value = "distTime")
    int distTime;   //外卖配送时间（分钟）
    @SerializedName(value = "minPrice")
    String minPrice;    //最低价格
    @SerializedName(value = "deliveryPrice")
    String deliveryPrice;   //配送价格
    @SerializedName(value = "avgPrice")
    String avgPrice;        //人均价格
    @SerializedName(value = "couponList")
    List<Coupon> couponList;    //优惠卷列表

    // FIXME: 2020/12/18 暂存以下属性值
    @SerializedName(value = "fansNum")
    int number_of_fans;  //粉丝数
    MemberCard memberCard;

    public Shop() {
    }

    public Shop(String shop_name) {
        this.shop_name = shop_name;
    }


    protected Shop(Parcel in) {
        shopId = in.readInt();
        shop_type = in.readInt();
        shop_head_portrait = in.readString();
        shop_name = in.readString();
        reservation_call = in.readString();
        alternate_phone = in.readString();
        province = in.readString();
        city = in.readString();
        district = in.readString();
        log = in.readString();
        lat = in.readString();
        shop_address = in.readString();
        shop_category = in.readString();
        favorable_rate = in.readString();
        monSalesVolume = in.readString();
        goods_pictures = in.readString();
        shop_qr_code = in.readString();
        shop_number = in.readString();
        admin_phone = in.readString();
        last_equipment_name = in.readString();
        last_login_address = in.readString();
        last_login_time = in.readString();
        notice = in.readString();
        synopsis = in.readString();
        invoice = in.readString();
        tag_value = in.readString();
        audit_state = in.readInt();
        audit_cause = in.readString();
        state = in.readInt();
        sort = in.readDouble();
        create_time = in.readString();
        update_time = in.readString();
        open_state = in.readInt();
        effectiveDate = in.readString();
        polygon = in.readString();
        create_name = in.readString();
        account_number = in.readString();
        user_pwd = in.readString();
        shop_nature = in.readInt();
        synopsis_img = in.readString();
        shop_sign = in.readString();
        logistics_rate = in.readString();
        serve_rate = in.readString();
        dist_priority = in.readInt();
        pick_priority = in.readInt();
        return_province = in.readString();
        return_city = in.readString();
        return_district = in.readString();
        return_address = in.readString();
        return_contacts = in.readString();
        return_phone = in.readString();
        cash_deposit = in.readString();
        is_message = in.readInt();
        shop_province = in.readString();
        shop_city = in.readString();
        shop_district = in.readString();
        shopImage = in.readString();
        goodsInfoList = in.createTypedArrayList(Goods.CREATOR);
        distance = in.readDouble();
        distPattern = in.readInt();
        distTeamName = in.readString();
        distTime = in.readInt();
        minPrice = in.readString();
        deliveryPrice = in.readString();
        avgPrice = in.readString();
        couponList = in.createTypedArrayList(Coupon.CREATOR);
        number_of_fans = in.readInt();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

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

    public int getNumber_of_fans() {
        return number_of_fans;
    }

    public void setNumber_of_fans(int number_of_fans) {
        this.number_of_fans = number_of_fans;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getMonSalesVolume() {
        return monSalesVolume;
    }

    public void setMonSalesVolume(String monSalesVolume) {
        this.monSalesVolume = monSalesVolume;
    }

    public MemberCard getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCard memberCard) {
        this.memberCard = memberCard;
    }

    public BigInteger getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(BigInteger organization_id) {
        this.organization_id = organization_id;
    }

    public BigInteger getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(BigInteger brand_id) {
        this.brand_id = brand_id;
    }

    public BigInteger getAccount_id() {
        return account_id;
    }

    public void setAccount_id(BigInteger account_id) {
        this.account_id = account_id;
    }

    public int getShop_type() {
        return shop_type;
    }

    public void setShop_type(int shop_type) {
        this.shop_type = shop_type;
    }

    public String getReservation_call() {
        return reservation_call;
    }

    public void setReservation_call(String reservation_call) {
        this.reservation_call = reservation_call;
    }

    public String getAlternate_phone() {
        return alternate_phone;
    }

    public void setAlternate_phone(String alternate_phone) {
        this.alternate_phone = alternate_phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_category() {
        return shop_category;
    }

    public void setShop_category(String shop_category) {
        this.shop_category = shop_category;
    }

    public String getGoods_pictures() {
        return goods_pictures;
    }

    public void setGoods_pictures(String goods_pictures) {
        this.goods_pictures = goods_pictures;
    }

    public String getShop_qr_code() {
        return shop_qr_code;
    }

    public void setShop_qr_code(String shop_qr_code) {
        this.shop_qr_code = shop_qr_code;
    }

    public String getShop_number() {
        return shop_number;
    }

    public void setShop_number(String shop_number) {
        this.shop_number = shop_number;
    }

    public String getAdmin_phone() {
        return admin_phone;
    }

    public void setAdmin_phone(String admin_phone) {
        this.admin_phone = admin_phone;
    }

    public String getLast_equipment_name() {
        return last_equipment_name;
    }

    public void setLast_equipment_name(String last_equipment_name) {
        this.last_equipment_name = last_equipment_name;
    }

    public String getLast_login_address() {
        return last_login_address;
    }

    public void setLast_login_address(String last_login_address) {
        this.last_login_address = last_login_address;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getTag_value() {
        return tag_value;
    }

    public void setTag_value(String tag_value) {
        this.tag_value = tag_value;
    }

    public int getAudit_state() {
        return audit_state;
    }

    public void setAudit_state(int audit_state) {
        this.audit_state = audit_state;
    }

    public String getAudit_cause() {
        return audit_cause;
    }

    public void setAudit_cause(String audit_cause) {
        this.audit_cause = audit_cause;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getSort() {
        return sort;
    }

    public void setSort(double sort) {
        this.sort = sort;
    }

    public BigInteger getCreate_id() {
        return create_id;
    }

    public void setCreate_id(BigInteger create_id) {
        this.create_id = create_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public BigInteger getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(BigInteger update_id) {
        this.update_id = update_id;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getOpen_state() {
        return open_state;
    }

    public void setOpen_state(int open_state) {
        this.open_state = open_state;
    }

    public String getPolygon() {
        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public int getShop_nature() {
        return shop_nature;
    }

    public void setShop_nature(int shop_nature) {
        this.shop_nature = shop_nature;
    }

    public String getSynopsis_img() {
        return synopsis_img;
    }

    public void setSynopsis_img(String synopsis_img) {
        this.synopsis_img = synopsis_img;
    }

    public String getShop_sign() {
        return shop_sign;
    }

    public void setShop_sign(String shop_sign) {
        this.shop_sign = shop_sign;
    }

    public String getLogistics_rate() {
        return logistics_rate;
    }

    public void setLogistics_rate(String logistics_rate) {
        this.logistics_rate = logistics_rate;
    }

    public String getServe_rate() {
        return serve_rate;
    }

    public void setServe_rate(String serve_rate) {
        this.serve_rate = serve_rate;
    }

    public int getDist_priority() {
        return dist_priority;
    }

    public void setDist_priority(int dist_priority) {
        this.dist_priority = dist_priority;
    }

    public int getPick_priority() {
        return pick_priority;
    }

    public void setPick_priority(int pick_priority) {
        this.pick_priority = pick_priority;
    }

    public String getReturn_province() {
        return return_province;
    }

    public void setReturn_province(String return_province) {
        this.return_province = return_province;
    }

    public String getReturn_city() {
        return return_city;
    }

    public void setReturn_city(String return_city) {
        this.return_city = return_city;
    }

    public String getReturn_district() {
        return return_district;
    }

    public void setReturn_district(String return_district) {
        this.return_district = return_district;
    }

    public String getReturn_address() {
        return return_address;
    }

    public void setReturn_address(String return_address) {
        this.return_address = return_address;
    }

    public String getReturn_contacts() {
        return return_contacts;
    }

    public void setReturn_contacts(String return_contacts) {
        this.return_contacts = return_contacts;
    }

    public String getReturn_phone() {
        return return_phone;
    }

    public void setReturn_phone(String return_phone) {
        this.return_phone = return_phone;
    }

    public String getCash_deposit() {
        return cash_deposit;
    }

    public void setCash_deposit(String cash_deposit) {
        this.cash_deposit = cash_deposit;
    }

    public int getIs_message() {
        return is_message;
    }

    public void setIs_message(int is_message) {
        this.is_message = is_message;
    }

    public String getShop_province() {
        return shop_province;
    }

    public void setShop_province(String shop_province) {
        this.shop_province = shop_province;
    }

    public String getShop_city() {
        return shop_city;
    }

    public void setShop_city(String shop_city) {
        this.shop_city = shop_city;
    }

    public String getShop_district() {
        return shop_district;
    }

    public void setShop_district(String shop_district) {
        this.shop_district = shop_district;
    }

    public BigInteger getRecommend_type() {
        return recommend_type;
    }

    public void setRecommend_type(BigInteger recommend_type) {
        this.recommend_type = recommend_type;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public List<Goods> getGoodsInfoList() {
        return goodsInfoList;
    }

    public void setGoodsInfoList(List<Goods> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDistPattern() {
        return distPattern;
    }

    public void setDistPattern(int distPattern) {
        this.distPattern = distPattern;
    }

    public String getDistTeamName() {
        return distTeamName;
    }

    public void setDistTeamName(String distTeamName) {
        this.distTeamName = distTeamName;
    }

    public int getDistTime() {
        return distTime;
    }

    public void setDistTime(int distTime) {
        this.distTime = distTime;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shopId);
        dest.writeInt(shop_type);
        dest.writeString(shop_head_portrait);
        dest.writeString(shop_name);
        dest.writeString(reservation_call);
        dest.writeString(alternate_phone);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(log);
        dest.writeString(lat);
        dest.writeString(shop_address);
        dest.writeString(shop_category);
        dest.writeString(favorable_rate);
        dest.writeString(monSalesVolume);
        dest.writeString(goods_pictures);
        dest.writeString(shop_qr_code);
        dest.writeString(shop_number);
        dest.writeString(admin_phone);
        dest.writeString(last_equipment_name);
        dest.writeString(last_login_address);
        dest.writeString(last_login_time);
        dest.writeString(notice);
        dest.writeString(synopsis);
        dest.writeString(invoice);
        dest.writeString(tag_value);
        dest.writeInt(audit_state);
        dest.writeString(audit_cause);
        dest.writeInt(state);
        dest.writeDouble(sort);
        dest.writeString(create_time);
        dest.writeString(update_time);
        dest.writeInt(open_state);
        dest.writeString(effectiveDate);
        dest.writeString(polygon);
        dest.writeString(create_name);
        dest.writeString(account_number);
        dest.writeString(user_pwd);
        dest.writeInt(shop_nature);
        dest.writeString(synopsis_img);
        dest.writeString(shop_sign);
        dest.writeString(logistics_rate);
        dest.writeString(serve_rate);
        dest.writeInt(dist_priority);
        dest.writeInt(pick_priority);
        dest.writeString(return_province);
        dest.writeString(return_city);
        dest.writeString(return_district);
        dest.writeString(return_address);
        dest.writeString(return_contacts);
        dest.writeString(return_phone);
        dest.writeString(cash_deposit);
        dest.writeInt(is_message);
        dest.writeString(shop_province);
        dest.writeString(shop_city);
        dest.writeString(shop_district);
        dest.writeString(shopImage);
        dest.writeTypedList(goodsInfoList);
        dest.writeDouble(distance);
        dest.writeInt(distPattern);
        dest.writeString(distTeamName);
        dest.writeInt(distTime);
        dest.writeString(minPrice);
        dest.writeString(deliveryPrice);
        dest.writeString(avgPrice);
        dest.writeTypedList(couponList);
        dest.writeInt(number_of_fans);
    }
}
