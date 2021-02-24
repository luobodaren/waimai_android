package com.life.waimaishuo.bean.api.request;

import com.life.waimaishuo.bean.api.request.bean.RecommendReqData;
import com.life.waimaishuo.bean.api.request.bean.SearchReqData;
import com.life.waimaishuo.bean.api.request.bean.SecondKillReqData;
import com.life.waimaishuo.bean.api.request.bean.SubTypeNameReqData;

/**
 * 外卖请求接受数据bean类
 */
public class WaiMaiReqData {

    //搜索
    public static class WaiMaiSearchReqData extends BaseReqData<SearchReqData>{
        public WaiMaiSearchReqData(SearchReqData reqData) {
            super(reqData);
        }
    }
    //推荐列表
    public static class WaiMaiRecommendReqData extends BaseReqData<RecommendReqData>{
        public WaiMaiRecommendReqData(RecommendReqData reqData) {
            super(reqData);
        }
    }
    //子类集
    public static class WaiMaiSubTypeReqData extends BaseReqData<SubTypeNameReqData>{
        public WaiMaiSubTypeReqData(SubTypeNameReqData reqData) {
            super(reqData);
        }
    }
    //限时秒杀
    public static class WaiMaiSecondKillReqData extends BaseReqData<Integer>{
        public WaiMaiSecondKillReqData(int reqData) { super(reqData); }
    }
    //限时秒杀内容
    public static class WaiMaiSecondKillContentListReqData extends BaseReqData<SecondKillReqData>{
        public WaiMaiSecondKillContentListReqData(SecondKillReqData reqData) { super(reqData); }
    }

}
