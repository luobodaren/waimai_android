package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.ObservableField;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Goods implements Parcelable {

    private ObservableField<String> choiceNumberObservable = new ObservableField<>(); //用于DataBinding 展示购买数量数据

    @SerializedName(value = "id")
    private int id;
    @SerializedName(value = "shopId")
    private int shopId;
    @SerializedName(value = "brandId")
    private int brandId;
    @SerializedName(value = "goodsName")
    private String name;
    private String shopName;

    @SerializedName(value = "versions")
    private String versions;    //商品版本

    @SerializedName(value = "goodsContent")
    private String details;     //商品内容 描述
    @SerializedName(value = "goodsPrimaryImg")
    private String goodsImgUrl; //商品主图
    @SerializedName(value = "goodsMoreImgs")
    private String goodsMoreImgs; //商品多图
    @SerializedName(value = "allInventory")
    private int allInventory;    //库存
    @SerializedName(value = "choiceNumber")
    private int choiceNumber = 0;   //选择的总数量
    @SerializedName(value = "goodsUnit")
    private String goodsUnit;   //单位
    @SerializedName(value = "goods_special")
    private String goods_special;   //特色标签

    @SerializedName(value = "state")
    private int state;  //商品状态商品状态 0:上架，1：下架 2：永久下架（平台下架）3放入回收站

    @SerializedName(value = "favorable_rate")
    private int favorable_rate; //好评率

    @SerializedName(value = "monSalesVolume")
    private int monSalesVolume;  //月销量

    @SerializedName(value = "goodsPrice")
    private String price;   //价格
    @SerializedName(value = "minPrice")
    private String minPrice;    //最低价格
    @SerializedName(value = "maxPrice")
    private String maxPrice;    //最高价格
    @SerializedName(value = "specialPrice")
    private String specialPrice;    //特价
    @SerializedName(value = "isBargainGoods")
    private int isBargainGoods;  //是否是特价商品
    @SerializedName(value = "linePrice")
    private String linePrice;

    private int time_send;  //配送时间

    private String price_deliver;   //配送费

    private String price_avg_per_man;   //人均价格
    @SerializedName(value = "salesVolume")
    private String salesVolume; //销量
    private String score;   //评分
    private String lowestPriceOf15Days; //15天内最低价格

    @SerializedName(value = "goodsTag")
    private int goodsTag;

    @SerializedName(value = "mealsFee")
    private String mealsFee;    //餐盒费

    @SerializedName(value = "discount")
    private String discount;    //打折

    @SerializedName(value = "isChooseSpecs")
    private int isChooseSpecs;  //是否选规格 0需要选择 1不需要选择

    @SerializedName(value = "goodsSpecList")
    private List<Specification> specificationList;

    @SerializedName(value = "attributeJsonList")
    private List<Attribute> attributeList;

    /**
     * 将购买的数量，供添加购物车时使用
     */
    private String wantBuyCount;

    /**
     * 选择的规格，供添加购物车时使用
     */
    private String specSelected;

    /**
     * 选择的属性，供添加购物车时使用
     */
    private List<String> attrsSelected;

    /**
     * 选择的属性拼接完成后的字符串
     */
    private String attrs;

    public Goods() {
    }

    public Goods(String name, int time_send, String goodsImgUrl, String price_deliver, int monSalesVolume, String score) {
        this.name = name;
        this.goodsImgUrl = goodsImgUrl;
        this.time_send = time_send;
        this.price_deliver = price_deliver;
        this.monSalesVolume = monSalesVolume;
        this.score = score;
    }

    public Goods(String name, String describe, String goodsImgUrl, int monSalesVolume, String price) {
        this.name = name;
        this.details = describe;
        this.goodsImgUrl = goodsImgUrl;
        this.monSalesVolume = monSalesVolume;
        this.price = price;
    }


    /**
     * 构造方法 由购物车类对象生成商品对象
     * @param goodsShoppingCart
     */
    public Goods(GoodsShoppingCart goodsShoppingCart){
        this.id = goodsShoppingCart.getGoodsId();
        this.shopId = goodsShoppingCart.getShopId();
        this.name = goodsShoppingCart.getGoodsName();
        this.goodsImgUrl = goodsShoppingCart.getGoodsPrimaryImg();
        this.specSelected = goodsShoppingCart.getSpecs();
        this.attrs = goodsShoppingCart.getAttrs();
        this.isBargainGoods = goodsShoppingCart.getIsBargainGoods();
        this.specialPrice = goodsShoppingCart.getPrice();
        this.choiceNumber = Integer.valueOf(goodsShoppingCart.getBuyCount());
        this.details = goodsShoppingCart.getDescribe();
        this.mealsFee = goodsShoppingCart.getBoxPrice();
        this.versions = goodsShoppingCart.getVersions();
    }


    protected Goods(Parcel in) {
        id = in.readInt();
        shopId = in.readInt();
        brandId = in.readInt();
        name = in.readString();
        shopName = in.readString();
        versions = in.readString();
        details = in.readString();
        goodsImgUrl = in.readString();
        goodsMoreImgs = in.readString();
        allInventory = in.readInt();
        choiceNumber = in.readInt();
        goodsUnit = in.readString();
        goods_special = in.readString();
        state = in.readInt();
        favorable_rate = in.readInt();
        monSalesVolume = in.readInt();
        price = in.readString();
        minPrice = in.readString();
        maxPrice = in.readString();
        specialPrice = in.readString();
        isBargainGoods = in.readInt();
        linePrice = in.readString();
        time_send = in.readInt();
        price_deliver = in.readString();
        price_avg_per_man = in.readString();
        salesVolume = in.readString();
        score = in.readString();
        lowestPriceOf15Days = in.readString();
        goodsTag = in.readInt();
        mealsFee = in.readString();
        discount = in.readString();
        isChooseSpecs = in.readInt();
        specificationList = in.createTypedArrayList(Specification.CREATOR);
        attributeList = in.createTypedArrayList(Attribute.CREATOR);
        wantBuyCount = in.readString();
        specSelected = in.readString();
        attrsSelected = in.createStringArrayList();
        attrs = in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public ObservableField<String> getChoiceNumberObservable() {
        return choiceNumberObservable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getGoodsMoreImgs() {
        return goodsMoreImgs;
    }

    public void setGoodsMoreImgs(String goodsMoreImgs) {
        this.goodsMoreImgs = goodsMoreImgs;
    }

    public int getAllInventory() {
        return allInventory;
    }

    public void setAllInventory(int allInventory) {
        this.allInventory = allInventory;
    }

    public int getChoiceNumber() {
        return choiceNumber;
    }

    public void setChoiceNumber(int choiceNumber) {
        this.choiceNumber = choiceNumber;
        choiceNumberObservable.set(String.valueOf(choiceNumber));
        choiceNumberObservable.notifyChange();
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoods_special() {
        return goods_special;
    }

    public void setGoods_special(String goods_special) {
        this.goods_special = goods_special;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getFavorable_rate() {
        return favorable_rate;
    }

    public void setFavorable_rate(int favorable_rate) {
        this.favorable_rate = favorable_rate;
    }

    public int getMonSalesVolume() {
        return monSalesVolume;
    }

    public void setMonSalesVolume(int monSalesVolume) {
        this.monSalesVolume = monSalesVolume;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public int getIsBargainGoods() {
        return isBargainGoods;
    }

    public void setIsBargainGoods(int isBargainGoods) {
        this.isBargainGoods = isBargainGoods;
    }

    public String getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(String linePrice) {
        this.linePrice = linePrice;
    }

    public int getTime_send() {
        return time_send;
    }

    public void setTime_send(int time_send) {
        this.time_send = time_send;
    }

    public String getPrice_deliver() {
        return price_deliver;
    }

    public void setPrice_deliver(String price_deliver) {
        this.price_deliver = price_deliver;
    }

    public String getPrice_avg_per_man() {
        return price_avg_per_man;
    }

    public void setPrice_avg_per_man(String price_avg_per_man) {
        this.price_avg_per_man = price_avg_per_man;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLowestPriceOf15Days() {
        return lowestPriceOf15Days;
    }

    public void setLowestPriceOf15Days(String lowestPriceOf15Days) {
        this.lowestPriceOf15Days = lowestPriceOf15Days;
    }

    public int getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(int goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getMealsFee() {
        return mealsFee;
    }

    public void setMealsFee(String mealsFee) {
        this.mealsFee = mealsFee;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getIsChooseSpecs() {
        return isChooseSpecs;
    }

    public void setIsChooseSpecs(int isChooseSpecs) {
        this.isChooseSpecs = isChooseSpecs;
    }

    public List<Specification> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<Specification> specificationList) {
        this.specificationList = specificationList;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
        StringBuilder attrsBuilder = new StringBuilder();
        attrsBuilder.append("");
        if(attrsSelected != null && attrsSelected.size() > 0){
            for (String attr : attrsSelected) {
                attrsBuilder.append(attr).append("、");
            }
            attrsBuilder.deleteCharAt(attrsSelected.size()-1);
        }
        attrs = attrsBuilder.toString();
    }

    public String getWantBuyCount() {
        return wantBuyCount;
    }

    public void setWantBuyCount(String wantBuyCount) {
        this.wantBuyCount = wantBuyCount;
    }

    public String getSpecSelected() {
        return specSelected;
    }

    public void setSpecSelected(String specSelected) {
        this.specSelected = specSelected;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public void setAddShoppingCartData(String wantBuyCount, String specSelected, List<String> attrsSelected){
        this.wantBuyCount = wantBuyCount;
        setSpecSelected(specSelected);
        setAttributeList(attributeList);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", brandId=" + brandId +
                ", name='" + name + '\'' +
                ", shopName='" + shopName + '\'' +
                ", versions='" + versions + '\'' +
                ", details='" + details + '\'' +
                ", goodsImgUrl='" + goodsImgUrl + '\'' +
                ", goodsMoreImgs='" + goodsMoreImgs + '\'' +
                ", allInventory=" + allInventory +
                ", choiceNumber=" + choiceNumber +
                ", goodsUnit='" + goodsUnit + '\'' +
                ", goods_special='" + goods_special + '\'' +
                ", state=" + state +
                ", favorable_rate=" + favorable_rate +
                ", monSalesVolume=" + monSalesVolume +
                ", price='" + price + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", specialPrice='" + specialPrice + '\'' +
                ", isBargainGoods=" + isBargainGoods +
                ", linePrice='" + linePrice + '\'' +
                ", time_send=" + time_send +
                ", price_deliver='" + price_deliver + '\'' +
                ", price_avg_per_man='" + price_avg_per_man + '\'' +
                ", salesVolume='" + salesVolume + '\'' +
                ", score='" + score + '\'' +
                ", lowestPriceOf15Days='" + lowestPriceOf15Days + '\'' +
                ", goodsTag=" + goodsTag +
                ", mealsFee='" + mealsFee + '\'' +
                ", discount='" + discount + '\'' +
                ", isChooseSpecs=" + isChooseSpecs +
                ", specificationList=" + specificationList +
                ", attributeList=" + attributeList +
                ", wantBuyCount='" + wantBuyCount + '\'' +
                ", specSelected='" + specSelected + '\'' +
                ", attrsSelected=" + attrsSelected +
                ", attrs='" + attrs + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(shopId);
        dest.writeInt(brandId);
        dest.writeString(name);
        dest.writeString(shopName);
        dest.writeString(versions);
        dest.writeString(details);
        dest.writeString(goodsImgUrl);
        dest.writeString(goodsMoreImgs);
        dest.writeInt(allInventory);
        dest.writeInt(choiceNumber);
        dest.writeString(goodsUnit);
        dest.writeString(goods_special);
        dest.writeInt(state);
        dest.writeInt(favorable_rate);
        dest.writeInt(monSalesVolume);
        dest.writeString(price);
        dest.writeString(minPrice);
        dest.writeString(maxPrice);
        dest.writeString(specialPrice);
        dest.writeInt(isBargainGoods);
        dest.writeString(linePrice);
        dest.writeInt(time_send);
        dest.writeString(price_deliver);
        dest.writeString(price_avg_per_man);
        dest.writeString(salesVolume);
        dest.writeString(score);
        dest.writeString(lowestPriceOf15Days);
        dest.writeInt(goodsTag);
        dest.writeString(mealsFee);
        dest.writeString(discount);
        dest.writeInt(isChooseSpecs);
        dest.writeTypedList(specificationList);
        dest.writeTypedList(attributeList);
        dest.writeString(wantBuyCount);
        dest.writeString(specSelected);
        dest.writeStringList(attrsSelected);
        dest.writeString(attrs);
    }


    public static class Specification implements Parcelable {
        @SerializedName(value = "specs")
        String name;

        @SerializedName(value = "specialPrice")
        String specialPrice;    //特价

        protected Specification(Parcel in) {
            name = in.readString();
            specialPrice = in.readString();
        }

        public static final Creator<Specification> CREATOR = new Creator<Specification>() {
            @Override
            public Specification createFromParcel(Parcel in) {
                return new Specification(in);
            }

            @Override
            public Specification[] newArray(int size) {
                return new Specification[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(specialPrice);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpecialPrice() {
            return specialPrice;
        }

        public void setSpecialPrice(String specialPrice) {
            this.specialPrice = specialPrice;
        }

        @Override
        public String toString() {
            return "Specification{" +
                    "name='" + name + '\'' +
                    ", specialPrice='" + specialPrice + '\'' +
                    '}';
        }
    }

    public static class Attribute implements Parcelable{
        @SerializedName(value = "name")
        String name;
        @SerializedName(value = "value")
        String[] value;

        protected Attribute(Parcel in) {
            name = in.readString();
            value = in.createStringArray();
        }

        public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
            @Override
            public Attribute createFromParcel(Parcel in) {
                return new Attribute(in);
            }

            @Override
            public Attribute[] newArray(int size) {
                return new Attribute[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeStringArray(value);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getValue() {
            return value;
        }

        public void setValue(String[] value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Attribute{" +
                    "name='" + name + '\'' +
                    ", value=" + Arrays.toString(value) +
                    '}';
        }
    }
}
