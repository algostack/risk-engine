package io.algostack.risk.model.var;

import io.algostack.risk.model.domain.cds.Position;
import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.domain.cds.ProductKey;
import io.algostack.risk.model.var.LinearPositionPnlCalculator;
import io.algostack.risk.model.var.LinearPriceArrayProvider;
import io.algostack.risk.model.var.PriceArray;
import io.algostack.risk.model.var.ScenConfig;

import java.util.List;

public class ProductPnlCalculator {

    private final ScenConfig scenConfig;
    private final LinearPriceArrayProvider priceArrayProvider;

    public ProductPnlCalculator(ScenConfig scenConfig, LinearPriceArrayProvider priceArrayProvider) {
        this.scenConfig = scenConfig;
        this.priceArrayProvider = priceArrayProvider;
    }

    public double[] calc(ProductKey productKey, List<Position<PriceArrayKey>> positions) {
        final LinearPositionPnlCalculator positionPnlCalculator = new LinearPositionPnlCalculator(scenConfig);
        final double[] prodPnl = new double[scenConfig.getScenarioDays()];
        for (int i=0; i<positions.size(); i++) {
            final Position<PriceArrayKey> position = positions.get(i);
            final PriceArray priceArray = priceArrayProvider.get(position.getKey());
            positionPnlCalculator.calcPositionPnl(position.getKey(), priceArray, position.getNotional(), prodPnl);
        }
        return prodPnl;
    }
}
