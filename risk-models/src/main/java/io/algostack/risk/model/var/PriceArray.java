package io.algostack.risk.model.var;

import io.algostack.mmap.DoubleArray;
import io.algostack.risk.model.domain.cds.PriceArrayKey;

public interface PriceArray extends DoubleArray {
    PriceArrayKey getKey();
    double getRefPrice();
    double get(int idx);
    int size();
}
