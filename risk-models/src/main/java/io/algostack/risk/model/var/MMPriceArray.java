package io.algostack.risk.model.var;

import io.algostack.mmap.DoubleArray;
import io.algostack.risk.model.domain.cds.PriceArrayKey;

public class MMPriceArray implements PriceArray {

    private final PriceArrayKey key;
    private final double refPrice;
    private final DoubleArray memMappedArray;

    public MMPriceArray(PriceArrayKey key, double refPrice, DoubleArray memMappedArray) {
        this.key = key;
        this.refPrice = refPrice;
        this.memMappedArray = memMappedArray;
    }

    @Override
    public PriceArrayKey getKey() {
        return key;
    }

    @Override
    public double getRefPrice() {
        return refPrice;
    }

    @Override
    public double get(int idx) {
        return memMappedArray.get(idx);
    }

    @Override
    public int size() {
        return memMappedArray.size();
    }
}