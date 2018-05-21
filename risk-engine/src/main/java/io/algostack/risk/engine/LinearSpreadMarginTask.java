package io.algostack.risk.engine;

import io.algostack.risk.model.domain.cds.Position;
import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.var.ExpectedShortfall;
import io.algostack.risk.model.var.ExpectedShortfallCalculator;
import io.algostack.risk.model.var.SimulatedPnl;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

public class LinearSpreadMarginTask implements Callable<String>, Serializable {

    private final boolean scaled;
    private final List<Position<PriceArrayKey>> positions;


    public LinearSpreadMarginTask(boolean scaled, List<Position<PriceArrayKey>> positions) {
        this.scaled = scaled;
        this.positions = positions;
    }

    @Override
    public String call() {

        final ExpectedShortfallCalculator expectedShortfallCalculator = scaled ? AppContext.getInstance().getScaledShortfallCalculator()
                : AppContext.getInstance().getUnscaledShortfallCalculator();
        final ExpectedShortfall<SimulatedPnl> es = expectedShortfallCalculator.calc(positions);

        return es.toString();

    }
}
