package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.LinkageGroupedItemWaimaiType;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopOrderDishesModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.kunminx.linkage.bean.BaseGroupedItem;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderDishesViewModel extends BaseViewModel {

    private ShopOrderDishesModel mModel;

    List<LinkageGroupedItemWaimaiType> shopGoodsLinkageGroupList = new ArrayList<>();

    @Override
    public BaseModel getModel() {
        mModel = new ShopOrderDishesModel();
        return mModel;
    }

    @Override
    public void initData() {

    }

    public String[] getBannerSource() {
        return new String[]{"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2515911597,1913645471&fm=26&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=172347525,3232800407&fm=26&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2755313968,2418553549&fm=26&gp=0.jpg"};
    }

    public List<BaseGroupedItem<LinkageGroupedItemWaimaiType.ItemInfo>> getShopGoodsItems() {
        String dataJson = "[\n" +
                "  {\n" +
                "    \"header\": \"热销\",\n" +
                "    \"isHeader\": true\n" +
                "  },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热销\",\"title\": \"现切压沙瓜\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热销\",\"title\": \"现切压沙瓜\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热销\",\"title\": \"现切压沙瓜\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热销\",\"title\": \"现切压沙瓜\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热销\",\"title\": \"现切压沙瓜\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热销\",\"title\": \"现切压沙瓜\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"好吃的食物，增肥神器，有求必应\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热销\",\"title\": \"现切压沙瓜\"  } },\n" +

                "  {\"header\": \"当季限定\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"当季限定\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"当季限定\",\"title\": \"烤翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"当季限定\",\"title\": \"烤全\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"当季限定\",\"title\": \"全\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"当季限定\",\"title\": \"翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"当季限定\",\"title\": \"烤\"  } },\n" +

                "  {\"header\": \"人气必喝\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"人气必喝\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"人气必喝\",\"title\": \"烤翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"人气必喝\",\"title\": \"烤全\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"人气必喝\",\"title\": \"全\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"人气必喝\",\"title\": \"翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"人气必喝\",\"title\": \"烤\"  } },\n" +

                "  {\"header\": \"果茶家族\",\"isHeader\": true}\n," +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"果茶家族\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"果茶家族\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"果茶家族\",\"title\": \"烤全翅\"  } },\n" +

                "  {\"header\": \"茗茶/奶茶\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"茗茶/奶茶\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"茗茶/奶茶\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"茗茶/奶茶\",\"title\": \"烤全翅\"  } },\n" +

                "  {\"header\": \"波波家族\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"波波家族\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"波波家族\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"波波家族\",\"title\": \"烤全翅\"  } },\n" +

                "  {\"header\": \"喜茶咖啡\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"喜茶咖啡\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"喜茶咖啡\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"喜茶咖啡\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"喜茶咖啡\",\"title\": \"烤全翅\"  } },\n" +

                "  {\"header\": \"热饮推荐\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"热饮推荐\",\"title\": \"烤全翅\"  } },\n" +


                "  {\"header\": \"冷饮推荐\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"冷饮推荐\",\"title\": \"烤全翅\"  } },\n" +


                "  {\"header\": \"春\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"春\",\"title\": \"烤全翅\"  } },\n" +


                "  {\"header\": \"夏\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"夏\",\"title\": \"烤全翅\"  } },\n" +


                "  {\"header\": \"秋\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"秋\",\"title\": \"烤全翅\"  } },\n" +


                "  {\"header\": \"冬\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"冬\",\"title\": \"烤全翅\"  } },\n" +


                "  {\"header\": \"发财\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"发财\",\"title\": \"烤全翅\"  } },\n" +


                "  {\"header\": \"暴富\",\"isHeader\": true},\n" +
                "  {\"isHeader\": false, \"info\": { \"content\": \"热卖\",\"imgUrl\":\"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=599791291,1995352191&fm=26&gp=0.jpg\",\"group\": \"暴富\",\"title\": \"烤全翅\"  } }\n" +
                "]";
        shopGoodsLinkageGroupList = GsonUtil.jsonToList(dataJson, LinkageGroupedItemWaimaiType.class);
        return (List) shopGoodsLinkageGroupList;
    }
}
