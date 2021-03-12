package com.life.waimaishuo.bean.api.request;

import com.life.waimaishuo.bean.api.request.bean.AddShippingAddress;
import com.life.waimaishuo.bean.api.request.bean.AddUserCollectReqBean;

/**
 * 通用的、一般的 接口
 */
public class CommonReqData {

/*    public static class GetUserInfo extends BaseReqData<Integer>{
        public GetUserInfo(Integer reqData) {
            super(reqData);
        }
    }*/

    /**
     * 收藏店铺
     */
    public static class AddUserCollectReqData extends BaseReqData<AddUserCollectReqBean>{
        public AddUserCollectReqData(AddUserCollectReqBean reqData) {
            super(reqData);
        }
    }

}
