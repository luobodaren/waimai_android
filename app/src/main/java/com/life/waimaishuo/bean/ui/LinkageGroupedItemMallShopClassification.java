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

public class LinkageGroupedItemMallShopClassification extends BaseGroupedItem<LinkageGroupedItemMallShopClassification.ItemInfo> {


    public LinkageGroupedItemMallShopClassification(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public LinkageGroupedItemMallShopClassification(ItemInfo info) {
        super(info);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private String imgUrl;
        private String price;
        private String[] describeTags;

        public ItemInfo(String title, String group, String[] describeTags) {
            super(title, group);
            this.describeTags = describeTags;
        }

        public ItemInfo(String title, String group, String[] describeTags, String imgUrl) {
            this(title, group, describeTags);
            this.imgUrl = imgUrl;
        }

        public ItemInfo(String title, String group, String[] describeTags, String imgUrl, String price) {
            this(title, group, describeTags, imgUrl);
            this.price = price;
        }

        public String[] getDescribeTags() {
            return describeTags;
        }

        public void setDescribeTags(String[] describeTags) {
            this.describeTags = describeTags;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
