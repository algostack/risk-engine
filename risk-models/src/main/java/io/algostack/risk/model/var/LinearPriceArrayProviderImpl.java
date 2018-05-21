package io.algostack.risk.model.var;

import io.algostack.risk.model.domain.cds.PriceArrayKey;

import java.util.Map;
import java.util.Set;

public class LinearPriceArrayProviderImpl implements LinearPriceArrayProvider {

    private final Map<PriceArrayKey, PriceArray> priceArrayMap;

    public LinearPriceArrayProviderImpl(Map<PriceArrayKey, PriceArray> priceArrayMap) {
        this.priceArrayMap = priceArrayMap; //assumes immutable map
    }

    @Override
    public PriceArray get(PriceArrayKey key) {
        return priceArrayMap.get(key);
    }

    @Override
    public Set<PriceArrayKey> keySet() {
        return priceArrayMap.keySet();
    }
}
