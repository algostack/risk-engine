package io.algostack.risk.model.domain.cds;

import java.io.Serializable;

public class Position<K>  implements Serializable {
    private final K key;
    private final double notional;


    public Position(K key, double notional) {
        this.key = key;
        this.notional = notional;
    }

    public K getKey() {
        return key;
    }
    public double getNotional() {
        return notional;
    }





    @Override
    public String toString() {
        return "Position{" +
                "priceArrayKey=" + key +
                ", notional=" + notional +
                '}';
    }
}
