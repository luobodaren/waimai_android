/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.life.waimaishuo.bean.ui;

import com.kunminx.linkage.bean.BaseGroupedItem;

public class LinkageShopGoodsGroupedItemInfo extends BaseGroupedItem.ItemInfo {
    // FIXME: 2020/12/24 后续对接时 完善数据属性 并在layout文件中进行数据绑定
    private String content;
    private String imgUrl;
    private String cost;

    public LinkageShopGoodsGroupedItemInfo(String title, String group, String content) {
        super(title, group);
        this.content = content;
    }

    public LinkageShopGoodsGroupedItemInfo(String title, String group, String content, String imgUrl) {
        this(title, group, content);
        this.imgUrl = imgUrl;
    }

    public LinkageShopGoodsGroupedItemInfo(String title, String group, String content, String imgUrl, String cost) {
        this(title, group, content, imgUrl);
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

}
