package io.algostack.risk.model.var;

import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.var.PriceArray;
import io.algostack.risk.model.var.ScenConfig;

public class LinearPositionPnlCalculator {

    private final ScenConfig scenConfig;

    public LinearPositionPnlCalculator(ScenConfig scenConfig) {
        this.scenConfig = scenConfig;
    }

    public void calcPositionPnl(PriceArrayKey priceArrayKey, PriceArray priceArray, double notional, double[] pnl) {
        final double currentMtm = priceArray.getRefPrice() * notional;
        for (int i = 0; i < priceArray.size(); i++) {
            final double unitaryPrice = priceArray.get(i);
            final double scenarioMtm = (unitaryPrice * notional);
            final double fxRate =  1.0D;
            pnl[i] += scenarioMtm - fxRate * currentMtm;
        }
    }

}
