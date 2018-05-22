package io.algostack.risk.engine;

import io.algostack.risk.model.domain.cds.Position;
import io.algostack.risk.model.domain.cds.PriceArrayKey;

import java.util.List;
import java.util.concurrent.Future;

public class ExpectedShortfallService {

    private final PositionService positionService;
    private final TaskExecutor executor;

    public ExpectedShortfallService(PositionService positionService, TaskExecutor executor) {
        this.positionService = positionService;
        this.executor = executor;
    }

    public String calcES(String portfolio) {
        final List<Position<PriceArrayKey>> positions = positionService.getPositions(portfolio);

        final LinearSpreadMarginTask scaled = new LinearSpreadMarginTask(true, positions);
        final LinearSpreadMarginTask unscaled = new LinearSpreadMarginTask(false, positions);

        final Future<String> scaledFut = executor.execute(scaled);
        final Future<String> unscaledFut = executor.execute(unscaled);

        try {
            scaledFut.get();
            return unscaledFut.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
