package com.life.waimaishuo.mvvm.vm.mall;

import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.ui.LinkageGoodsTypeGroupedItemInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallAllTypeModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.List;

public class MallAllTypeViewModel extends BaseViewModel {

    MallAllTypeModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallAllTypeModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo>> getLinkageGroupItems() {
        List<LinkageGoodsTypeGroupedItemInfo> waimaiTypeList;
        String dataJson = "[\n" +
                "  {\n" +
                "    \"header\": \"优惠\",\n" +
                "    \"isHeader\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"isHeader\": false,\"info\": {\"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全家桶\"  } },\n" +
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
        waimaiTypeList =  GsonUtil.jsonToList(dataJson,  LinkageGoodsTypeGroupedItemInfo.class);
        return (List)waimaiTypeList;
    }

    public String getAdvertisingUrl() {
        return "https://img.pic88.com/16067341360375.jpg";
    }
}
