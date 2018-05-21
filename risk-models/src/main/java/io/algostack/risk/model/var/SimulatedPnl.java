package io.algostack.risk.model.var;

import io.algostack.risk.model.domain.cds.KeyExtractor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for product and portfolio pnl
 */
public class SimulatedPnl<T> {

    private final Map<T, double[]> itemPnls;

    public SimulatedPnl(Map<T, double[]> itemPnls) {
        this.itemPnls = itemPnls;
    }

    public Map<T, double[]> getItemPnls() {
        return itemPnls;
    }

    public double[] getAggregatedPnl() {
        return getAggregatedPnl(itemPnls);
    }

    public <O> SimulatedPnl<O> toAggregatedPnl(KeyExtractor<T,O> keyExtractor) {
        return aggregate(itemPnls, keyExtractor);
    }

    /**
     * Creates an immutable result of this, second and third
     * @param second
     * @param third
     * @return
     */
    public SimulatedPnl<T> add(SimulatedPnl<T> second, SimulatedPnl<T> third) {
        return new Builder<T>().add(this.getItemPnls()).add(second.getItemPnls())
                .add(third.getItemPnls()).build();
    }

    public SimulatedPnl<T> add(SimulatedPnl<T> another) {
        return new Builder<T>().add(this.getItemPnls()).add(another.getItemPnls()).build();
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private final Map<T, double[]> productPnlMap = new HashMap<>();

        private Builder() {

        }

        public Builder add(T itemKey, double[] pnl) {
            double[] productPnl = productPnlMap.computeIfAbsent(itemKey, e->new double[pnl.length]);
            for (int j=0; j<pnl.length; j++) {
                productPnl[j] += pnl[j];
            }
            return this;
        }

        public Builder add(Map<T, double[]> itemPnlMap) {
            for (Map.Entry<T, double[]> e : itemPnlMap.entrySet()) {
                add(e.getKey(), e.getValue());
            }
            return this;
        }

        public SimulatedPnl build() {
            return new SimulatedPnl(productPnlMap);
        }
    }

    public static <T> double[] getAggregatedPnl(Map<T, double[]> productPnlMap) {
        final Collection<double[]> values = productPnlMap.values();
        double[] portfolioPnl = new double[values.isEmpty() ? 0 : values.iterator().next().length];
        for (double[] productPnl : values) {
            for (int i=0; i<productPnl.length; i++) {
                portfolioPnl[i] += productPnl[i];
            }
        }
        return portfolioPnl;
    }

    public static <K,T> SimulatedPnl<T> aggregate(Map<K,double[]> pnls, KeyExtractor<K,T> keyExtractor) {
        Map<T, double[]> aggregatedPnls = new HashMap<>();
        for (Map.Entry<K, double[]> entry : pnls.entrySet()) {
            final double[] contractPnl = entry.getValue();
            final double[] aggregatedPnl = aggregatedPnls.computeIfAbsent(keyExtractor.extract(entry.getKey()), e->new double[contractPnl.length]);
            for (int i=0; i<contractPnl.length; i++) {
                aggregatedPnl[i] += contractPnl[i];
            }
        }
        return new SimulatedPnl<>(aggregatedPnls);
    }

    public static String toScenDayPriceArray(double[] pnlArray, ScenConfig scenarioDaysConfig) {
        final StringBuilder builder = new StringBuilder();
        for (int scen=0; scen<scenarioDaysConfig.getScenarios(); scen++) {
            for (int day=0; day<scenarioDaysConfig.getDays(); day++) {
                builder.append(pnlArray[scenarioDaysConfig.getIndex(scen, day)]).append(",");
            }
            builder.append("\n");
        }
        return builder.toString();
    }


}
