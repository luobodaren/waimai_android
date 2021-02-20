package com.life.waimaishuo.bean.api.request;

import com.life.waimaishuo.bean.api.request.bean.RecommendReqData;

/**
 * 外卖请求接受数据bean类
 */
public class WaiMaiReqData {

    public static class WaiMaiSecondKillReqData extends BaseReqData<Integer>{

        public WaiMaiSecondKillReqData(int reqData) {
            super(reqData);
        }
    }

    public static class WaiMaiRecommendReqData extends BaseReqData<RecommendReqData>{

        public WaiMaiRecommendReqData(RecommendReqData reqData) {
            super(reqData);
        }
    }


}
