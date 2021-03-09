package com.life.waimaishuo.bean.api.respon;

public class ShopStatEvaluate {

    /**
     * id : 119
     * shopId : 4352
     * goodsId : 0
     * brandId : 15
     * allGrade : 0
     * smellGrade : 0
     * packGrade : 0
     * distGrade : 0
     * createTime : 2021-01-14 11:00:10
     */

    private int id; //评分Id
    private int shopId; //店铺ID
    private int goodsId;    //商品ID
    private int brandId;    //品牌ID
    private String allGrade;    //总评分
    private String smellGrade;  //味道评分
    private String packGrade;   //打包评分
    private String distGrade;   //配送评分
    private String createTime;  //评价时间（创建时间）

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

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getAllGrade() {
        return allGrade;
    }

    public void setAllGrade(String allGrade) {
        this.allGrade = allGrade;
    }

    public String getSmellGrade() {
        return smellGrade;
    }

    public void setSmellGrade(String smellGrade) {
        this.smellGrade = smellGrade;
    }

    public String getPackGrade() {
        return packGrade;
    }

    public void setPackGrade(String packGrade) {
        this.packGrade = packGrade;
    }

    public String getDistGrade() {
        return distGrade;
    }

    public void setDistGrade(String distGrade) {
        this.distGrade = distGrade;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
