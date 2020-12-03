package com.example.myapplication.mvvm.vm.waimai;

import com.example.base.utils.GsonUtil;
import com.example.myapplication.bean.ElemeGroupedItem;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.waimai.WaiMaiTypeModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.bean.BaseGroupedItem;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiTypeViewModel extends BaseViewModel {

    WaiMaiTypeModel model;
    List<ElemeGroupedItem<ElemeGroupedItem.ItemInfo>> waimaiTypeList = new ArrayList<>();

    @Override
    public BaseModel getModel() {
        if(model == null){
            model = new WaiMaiTypeModel();
        }
        return model;
    }

    @Override
    public void initData() {

    }

    public List<ElemeGroupedItem> getElemeGroupItems() {
        String dataJson = "[\n" +
                "  {\n" +
                "    \"header\": \"优惠\",\n" +
                "    \"isHeader\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"isHeader\": false,\n" +
                "    \"info\": {\n" +
                "      \"content\": \"好吃的食物，增肥神器，有求必应\",\n" +
                "      \"group\": \"优惠\",\n" +
                "      \"title\": \"全家桶\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"header\": \"热卖\",\n" +
                "    \"isHeader\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"isHeader\": false,\n" +
                "    \"info\": {\n" +
                "      \"content\": \"爆款热卖，月销超过 999 件\",\n" +
                "      \"group\": \"热卖\",\n" +
                "      \"title\": \"烤全翅\"\n" +
                "    }\n" +
                "  }\n" +
                "]" ;
        waimaiTypeList =  GsonUtil.gsonToList(dataJson,  ElemeGroupedItem.class);
        return waimaiTypeList;
    }

}
