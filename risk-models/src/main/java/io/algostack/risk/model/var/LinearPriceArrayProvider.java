package io.algostack.risk.model.var;

import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.var.PriceArray;

import java.util.Set;

public interface LinearPriceArrayProvider {
    PriceArray get(PriceArrayKey key);
    Set<PriceArrayKey> keySet();
}