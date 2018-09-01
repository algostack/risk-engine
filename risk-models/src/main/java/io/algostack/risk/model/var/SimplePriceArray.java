package io.algostack.risk.model.var;

import io.algostack.risk.model.domain.cds.PriceArrayKey;

public class SimplePriceArray implements PriceArray {

    private final PriceArrayKey key;
    private final double refPrice;
    private final double[] mtms;

    public SimplePriceArray(PriceArrayKey key, double refPrice, double[] mtms) {
        this.key = key;
        this.refPrice = refPrice;
        this.mtms = mtms;
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
    public double get(int index) {
        return mtms[index];
    }

    @Override
    public int size() {
        return mtms.length;
    }
}
