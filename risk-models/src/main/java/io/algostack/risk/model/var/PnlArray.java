package io.algostack.risk.model.var;

import io.algostack.risk.model.domain.cds.Position;

public class PnlArray<K> {

    private final Position<K> position;
    private final double referencePrice;
    private final double[] pnls;

    public PnlArray(Position<K> position, double referencePrice, double[] pnls) {
        this.position = position;
        this.referencePrice = referencePrice;
        this.pnls = pnls;
    }

    public Position<K> getPosition() {
        return position;
    }

    public double getReferencePrice() {
        return referencePrice;
    }

    public double[] getPnls() {
        return pnls;
    }
}