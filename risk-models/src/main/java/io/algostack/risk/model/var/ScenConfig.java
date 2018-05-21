package io.algostack.risk.model.var;

public class ScenConfig {

    private final int scenarios;
    private final int days;

    public ScenConfig(int scenarios, int days) {
        this.scenarios = scenarios;
        this.days = days;
    }

    public int getScenarios() {
        return scenarios;
    }

    public int getDays() {
        return days;
    }

    public final int getScenario(int scenarioDayIndex) {
        return scenarioDayIndex/scenarios;
    }

    public int getScenarioDays() { return scenarios * days; };

    public final int getIndex(int scenario, int day) {
        return day * scenarios + scenario;
    }
}