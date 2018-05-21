package io.algostack.risk.engine;

import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.var.PriceArray;

import java.io.File;
import java.util.Map;

public class PriceArrayLoader {

    public Map<PriceArrayKey, PriceArray> load(String path) {
        final Map<PriceArrayKey, PriceArray> priceArrayMap = new PriceArrayMMapSerializer().deserialize(new File(path));
         return priceArrayMap;
    }

}
