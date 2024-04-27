package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class WangwangnickOpenuidDecryptParam extends AbstractAPIRequest<WangwangnickOpenuidDecryptResult> {

    public WangwangnickOpenuidDecryptParam() {
        super();
        oceanApiId = new APIId("com.alibaba.account", "wangwangnick.openuid.decrypt", 1);
    }

    private String openUid;

    /**
     * @return 待解密的openUid
     */
    public String getOpenUid() {
        return openUid;
    }

    /**
     *     待解密的openUid     *
     *    
     *    
     */
    public void setOpenUid(String openUid) {
        this.openUid = openUid;
    }

}
