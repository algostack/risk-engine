package io.algostack.risk.model.var;

import com.google.common.base.Stopwatch;
import io.algostack.risk.model.domain.cds.*;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ExpectedShortfallCalculator {

    private static final Logger LOG = Logger.getLogger(ExpectedShortfallCalculator.class.getName());


    private final ScenConfig scenConfig;
    private final LinearPriceArrayProvider priceArrayProvider;


    public ExpectedShortfallCalculator(ScenConfig scenConfig, LinearPriceArrayProvider priceArrayProvider) {
        this.scenConfig = scenConfig;
        this.priceArrayProvider = priceArrayProvider;
    }


    public ExpectedShortfall<SimulatedPnl> calc(Collection<Position<PriceArrayKey>> positions) {
        final Stopwatch stopwatch = Stopwatch.createStarted();

        final Map<ProductKey, List<Position<PriceArrayKey>>> positionsByProduct = positions.stream().collect(Collectors.groupingBy(e -> e.getKey().getProductKey()));
        final Map<ProductKey, double[]> prodPnls = calcProductPnl(positionsByProduct);
        final SimulatedPnl simulatedPnl = SimulatedPnl.<ProductKey>builder().add(prodPnls).build();

        final ExpectedShortfall<SimulatedPnl> expectedShortfall = ExpectedShortfall.of(simulatedPnl.getAggregatedPnl(), simulatedPnl, scenConfig, 9);

        LOG.info("Calculated expected shortfall for positions: " + positions.size() + " in: " + stopwatch);
        return expectedShortfall;
    }

    private Map<ProductKey, double[]> calcProductPnl(Map<ProductKey, List<Position<PriceArrayKey>>> positionsByProduct) {

        final Stopwatch stopwatch = Stopwatch.createStarted();
        final ProductPnlCalculator productPnlCalculator = new ProductPnlCalculator(scenConfig, priceArrayProvider);
        final Map<ProductKey, double[]> prodPnl = positionsByProduct.entrySet()
                .parallelStream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), productPnlCalculator.calc(e.getKey(), e.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        LOG.info("Calculated product pnl in: " + stopwatch);
        return prodPnl;
    }


}
