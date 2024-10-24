package com.salesmanager.shop.model.catalog.product.product;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Data
public class OemConfig {
    private Boolean enable;
    private Boolean includeSticker;
    private Boolean includeOppPackage;
    private List<QuantityRange> quantityRangeList;


    @Data
    public static class QuantityRange {
        private Integer from;
        private Integer to;
        private Integer stickerPrice;
        private Integer oppPackagePrice;
    }

    public void check() {
        Assert.isTrue(quantityRangeList.size() <= 5, "price range size can not greater than 5");

        for (QuantityRange quantityRange : quantityRangeList) {
            if (Boolean.TRUE.equals(includeSticker)) {
                Assert.notNull(quantityRange.getStickerPrice(), "sticker price can not be null");
            }
            if (Boolean.TRUE.equals(includeOppPackage)) {
                Assert.notNull(quantityRange.getOppPackagePrice(), "opp package price can not be null");
            }
        }

        for (int i = 0; i < quantityRangeList.size() - 1; i++) {
            QuantityRange quantityRange = quantityRangeList.get(i);
            QuantityRange next = quantityRangeList.get(i + 1);
            if (quantityRange.getFrom() == null) {
                throw new IllegalArgumentException("price range:" + (i + 1) + " from can not be null");
            }
            if (quantityRange.getTo() == null) {
                throw new IllegalArgumentException("price range:" + (i + 1) + " to can not be null");
            }
            if (next.getFrom() == null) {
                throw new IllegalArgumentException("price range:" + (i + 2) + " from can not be null");
            }
            Assert.isTrue(Objects.equals(quantityRange.getTo(), next.getFrom() - 1), "quantity should be continuity except:" + (quantityRange.getTo() + 1) + ", but actual:" + (next.getFrom()));
        }
    }


    public static void main(String[] args) {
        OemConfig oemConfig = JSON.parseObject("{\n" +
                "  \"enable\": true,\n" +
                "  \"includeSticker\": true,\n" +
                "  \"includeOppPackage\": true,\n" +
                "  \"quantityRangeList\": [\n" +
                "    {\n" +
                "      \"from\": 0,\n" +
                "      \"to\": 99,\n" +
                "      \"stickerPrice\": 20,\n" +
                "      \"oppPackagePrice\": 30\n" +
                "    },\n" +
                "    {\n" +
                "      \"from\": 100,\n" +
                "      \"to\": 199,\n" +
                "      \"stickerPrice\": 40,\n" +
                "      \"oppPackagePrice\": 60\n" +
                "    },\n" +
                "    {\n" +
                "      \"from\": 200,\n" +
                "      \"to\": 299,\n" +
                "      \"stickerPrice\": 60,\n" +
                "      \"oppPackagePrice\": 90\n" +
                "    },\n" +
                "    {\n" +
                "      \"from\": 300,\n" +
                "      \"to\": 399,\n" +
                "      \"stickerPrice\": 80,\n" +
                "      \"oppPackagePrice\": 120\n" +
                "    },\n" +
                "    {\n" +
                "      \"from\": 400,\n" +
                "      \"to\": 499,\n" +
                "      \"stickerPrice\": 100,\n" +
                "      \"oppPackagePrice\": 150\n" +
                "    }\n" +
                "  ]\n" +
                "}", OemConfig.class);

        oemConfig.check();
        System.out.println(JSON.toJSONString(oemConfig));
    }
}

