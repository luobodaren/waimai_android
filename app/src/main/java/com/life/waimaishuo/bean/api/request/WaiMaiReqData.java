package com.life.waimaishuo.bean.api.request;

import com.life.waimaishuo.bean.api.request.bean.RecommendReqBean;
import com.life.waimaishuo.bean.api.request.bean.SearchReqBean;
import com.life.waimaishuo.bean.api.request.bean.SecondKillReqBean;
import com.life.waimaishuo.bean.api.request.bean.SubTypeNameReqBean;

/**
 * 外卖请求接受数据bean类
 */
public class WaiMaiReqData {

    //搜索
    public static class WaiMaiSearchReqData extends BaseReqData<SearchReqBean>{
        public WaiMaiSearchReqData(SearchReqBean reqData) {
            super(reqData);
        }
    }
    //推荐列表
    public static class WaiMaiRecommendReqData extends BaseReqData<RecommendReqBean>{
        public WaiMaiRecommendReqData(RecommendReqBean reqData) {
            super(reqData);
        }
    }
    //子类集
    public static class WaiMaiSubTypeReqData extends BaseReqData<SubTypeNameReqBean>{
        public WaiMaiSubTypeReqData(SubTypeNameReqBean reqData) {
            super(reqData);
        }
    }
    //限时秒杀
    public static class WaiMaiSecondKillReqData extends BaseReqData<Integer>{
        public WaiMaiSecondKillReqData(int reqData) { super(reqData); }
    }
    //限时秒杀内容
    public static class WaiMaiSecondKillContentListReqData extends BaseReqData<SecondKillReqBean>{
        public WaiMaiSecondKillContentListReqData(SecondKillReqBean reqData) { super(reqData); }
    }

}
