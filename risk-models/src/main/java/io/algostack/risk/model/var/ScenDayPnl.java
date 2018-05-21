package io.algostack.risk.model.var;

public class ScenDayPnl {

    private final int scenarioDayIndex;
    private final double  pnl;

    public ScenDayPnl(int scenarioDayIndex, double pnl) {
        this.scenarioDayIndex = scenarioDayIndex;
        this.pnl = pnl;
    }

    public int getScenarioDayIndex() {
        return scenarioDayIndex;
    }

    public double getPnl() {
        return pnl;
    }
}