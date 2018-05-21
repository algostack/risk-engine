package io.algostack.risk.model.var;

import io.algostack.risk.model.var.SimulatedPnl;

public interface LinearPnlCalculator<T> {
     SimulatedPnl<T> calcPnl();
}