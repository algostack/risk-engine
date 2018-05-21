package io.algostack.risk.model.var;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;


public class ExpectedShortfall<R> {

    public static final double EPSILON = 10e-10;

    private final double shortfall;
    private final List<Integer> worstCaseScenarioDays;
    private final R sourcePnl;
    private final ScenConfig scenarioConfig;

    public ExpectedShortfall(double shortfall, List<Integer> worstCaseScenarioDays, R sourcePnl, ScenConfig scenarioConfig) {
        this.shortfall = shortfall;
        this.worstCaseScenarioDays = worstCaseScenarioDays;
        this.sourcePnl = sourcePnl;
        this.scenarioConfig = scenarioConfig;
    }

    public double getShortfall() {
        return shortfall;
    }

    public List<Integer> getWorstCaseScenarioDays() {
        return worstCaseScenarioDays;
    }

    public ScenConfig getScenarioConfig() {
        return scenarioConfig;
    }

    public R getSourcePnl() {
        return sourcePnl;
    }

    public List<Integer> getWorstCaseScenarios() {
        return worstCaseScenarioDays.stream()
                .map(i-> scenarioConfig.getScenario(i))
                .collect(Collectors.toList());
    }

    public static int getWorstDayIndexForScenario(double[] pnl, int scenario, ScenConfig scenarioDay) {
        int minDayIndex = scenario;
        for (int j = 1; j < scenarioDay.getDays(); j++) {
            final int dayIndex = j * scenarioDay.getScenarios() + scenario;
            if (pnl[dayIndex] < pnl[minDayIndex]) {
                minDayIndex = dayIndex;
            }
        }
        return minDayIndex;
    }

    public static ExpectedShortfall of(double[] pnl, ScenConfig scenarioDay, int maxWorstCases) {
        return of(pnl, pnl, scenarioDay, maxWorstCases);
    }

    public static <T> ExpectedShortfall<T> of(double[] pnlArray, T sourcePnl, ScenConfig ScenConfig, int maxWorstCases) {
        if (maxWorstCases > 0) {
            PriorityQueue<ScenDayPnl> worstScenarios = new PriorityQueue<>(maxWorstCases, (o1, o2) -> Double.compare(o2.getPnl(), o1.getPnl()));
            for (int i = 0; i < ScenConfig.getScenarios(); i++) {
                final int worstScenarioDayIndex = getWorstDayIndexForScenario(pnlArray, i, ScenConfig);
                final double worstPnl = pnlArray[worstScenarioDayIndex];
                if (worstScenarios.size() == maxWorstCases) {
                    if (worstScenarios.peek().getPnl() > worstPnl) {
                        worstScenarios.poll();
                        worstScenarios.add(new ScenDayPnl(worstScenarioDayIndex, worstPnl));
                    }
                } else {
                    worstScenarios.add(new ScenDayPnl(worstScenarioDayIndex, worstPnl));
                }
            }

            final ScenDayPnl[] scenDayPnls = worstScenarios.toArray(new ScenDayPnl[0]);

            final List<Integer> worstCaseScenarios = new ArrayList<>();
            double worstScenariosTotalPnl = 0.0D;
            for (int i = 0; i < scenDayPnls.length; i++) {
                worstScenariosTotalPnl += scenDayPnls[i].getPnl();
                worstCaseScenarios.add(scenDayPnls[i].getScenarioDayIndex());
            }

            final double expectedShortfall = maxWorstCases > 0 ? (worstScenariosTotalPnl / maxWorstCases) : 0.0D;

            return new ExpectedShortfall(expectedShortfall, worstCaseScenarios, sourcePnl, ScenConfig);
        } else {
            return new ExpectedShortfall(0.0D, Collections.emptyList(), sourcePnl, ScenConfig);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ExpectedShortfall<?> that = (ExpectedShortfall<?>) o;

        if (Double.compare(that.shortfall, shortfall) != 0)
            return false;
        if (worstCaseScenarioDays != null ? !worstCaseScenarioDays.equals(that.worstCaseScenarioDays) : that.worstCaseScenarioDays != null)
            return false;
        return scenarioConfig != null ? scenarioConfig.equals(that.scenarioConfig) : that.scenarioConfig == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(shortfall);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (worstCaseScenarioDays != null ? worstCaseScenarioDays.hashCode() : 0);
        result = 31 * result + (scenarioConfig != null ? scenarioConfig.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExpectedShortfall{" +
                "shortfall=" + shortfall +
                ", worstCaseScenarioDays=" + worstCaseScenarioDays +
                '}';
    }

}
