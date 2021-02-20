package com.life.waimaishuo.mvvm.vm.mine;

import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.ui.LinkageGoodsTypeGroupedItemInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineSelectCategoryModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineSelectCategoryViewModel extends BaseViewModel {

    private MineSelectCategoryModel mModel;

    private List<LinkageGoodsTypeGroupedItemInfo> groupedItemGoodsTypeList = new ArrayList<>();
    public int currentSelectedType = 1;    //1 商城 2 外卖

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineSelectCategoryModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo>> getLinkageGroupItems() {
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
        //groupedItemGoodsTypeList =  GsonUtil.jsonToList(dataJson,  BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo>.class);
        return (List)groupedItemGoodsTypeList;
    }

    /**
     * 刷新linkageRecycler数据 商城和外卖两个类型切换
     */
    public void refreshLinkageData() {  // TODO: 2021/1/26 完善
        if(currentSelectedType == 1){

        }else{

        }
    }

}
