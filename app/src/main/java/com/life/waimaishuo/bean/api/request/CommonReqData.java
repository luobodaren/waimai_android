package com.life.waimaishuo.bean.api.request;

import com.life.waimaishuo.bean.api.request.bean.AddUserCollectReqBean;

/**
 * 通用的、一般的 接口
 */
public class CommonReqData {


    public static class AddUserCollectReqData extends BaseReqData<AddUserCollectReqBean>{

        public AddUserCollectReqData(AddUserCollectReqBean reqData) {
            super(reqData);
        }
    }

}
