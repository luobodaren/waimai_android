package com.example.myapplication.mvvm.vm.waimai;

import com.example.base.utils.GsonUtil;
import com.example.myapplication.bean.ElemeGroupedItem;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.waimai.WaiMaiTypeModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.kunminx.linkage.bean.BaseGroupedItem;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiTypeViewModel extends BaseViewModel {

    WaiMaiTypeModel model;
    List<ElemeGroupedItem> waimaiTypeList = new ArrayList<>();

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

    public List<BaseGroupedItem<ElemeGroupedItem.ItemInfo>> getElemeGroupItems() {
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
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全家\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全桶\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"家桶\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"家\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"桶\"  } }," +

                "  {\"header\": \"热卖\",\"isHeader\": true}," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤全翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤\"  } }," +

                "  {\"header\": \"超市便利\",\"isHeader\": true}," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤全翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤\"  } }" +
                "]" ;
        waimaiTypeList =  GsonUtil.jsonToList(dataJson,  ElemeGroupedItem.class);
        return (List)waimaiTypeList;
    }

}
