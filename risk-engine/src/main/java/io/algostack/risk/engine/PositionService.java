package io.algostack.risk.engine;

import io.algostack.risk.model.domain.cds.Position;
import io.algostack.risk.model.domain.cds.PriceArrayKey;

import java.util.List;

public interface PositionService {
    List<Position<PriceArrayKey>> getPositions(String portfolio);
}
