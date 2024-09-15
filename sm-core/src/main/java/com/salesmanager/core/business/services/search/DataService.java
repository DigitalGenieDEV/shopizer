//tmpzk
package com.salesmanager.core.business.services.search;

import com.salesmanager.core.model.catalog.product.dataservice.*;

public interface DataService {

    BIProductInfoResult biproductinfo(BIProductInfoRequest request);

    BIMemberInfoResult bimemberinfo(BIMemberInfoRequest request);

    BIBuyerInfoResult bibuyerinfo(BIBuyerInfoRequest request);

    BISaleInfoResult bisaleinfo(BISaleInfoRequest request);

    BITrafficInfoResult bitrafficinfo(BITrafficInfoRequest request);
}
