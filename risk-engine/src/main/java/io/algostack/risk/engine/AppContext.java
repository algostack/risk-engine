package io.algostack.risk.engine;

import io.algostack.risk.model.var.LinearPriceArrayProvider;
import io.algostack.risk.model.var.LinearPriceArrayProviderImpl;
import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.var.ExpectedShortfallCalculator;
import io.algostack.risk.model.var.PriceArray;
import io.algostack.risk.model.var.ScenConfig;

import java.util.Map;

public class AppContext {



    private static AppContext instance;
    private final LinearPriceArrayProviderImpl unscaledLinearPriceArrayProvider;
    private final ExpectedShortfallCalculator unscaledShortfallCalculator;

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    private final PriceArrayLoader priceArrayLoader;
    private final LinearPriceArrayProvider scaledLinearPriceArrayProvider;
    private final ExpectedShortfallCalculator scaledShortfallCalculator;
    private final TaskExecutor taskExecutor;
    private final RiskEngineGrpcImpl riskEngineGrpc;
    private final ExpectedShortfallService expectedShortfallService;
    private final PositionService positionService;

    public AppContext() {
        this.priceArrayLoader = new PriceArrayLoader();
        final Map<PriceArrayKey, PriceArray> priceArrayMap = priceArrayLoader.load(AppProperties.getESScaledPath());
        this.scaledLinearPriceArrayProvider = new LinearPriceArrayProviderImpl(priceArrayMap);
        this.unscaledLinearPriceArrayProvider = new LinearPriceArrayProviderImpl(priceArrayLoader.load(AppProperties.getESUnscaledPath()));
        this.scaledShortfallCalculator = new ExpectedShortfallCalculator(new ScenConfig(2500, 5), getScaledLinearPriceArrayProvider());
        this.unscaledShortfallCalculator = new ExpectedShortfallCalculator(new ScenConfig(2500, 5), getScaledLinearPriceArrayProvider());
        this.taskExecutor = new LocalTaskExecutor();
        this.positionService = new RandomPositionService(scaledLinearPriceArrayProvider);
        this.expectedShortfallService = new ExpectedShortfallService(positionService, taskExecutor);
        this.riskEngineGrpc = new RiskEngineGrpcImpl(expectedShortfallService);
    }

    public PriceArrayLoader getPriceArrayLoader() {
        return priceArrayLoader;
    }

    public LinearPriceArrayProvider getScaledLinearPriceArrayProvider() {
        return scaledLinearPriceArrayProvider;
    }

    public ExpectedShortfallCalculator getScaledShortfallCalculator() {
        return scaledShortfallCalculator;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public RiskEngineGrpcImpl getRiskEngineGrpc() {
        return riskEngineGrpc;
    }

    public ExpectedShortfallCalculator getUnscaledShortfallCalculator() {
        return unscaledShortfallCalculator;
    }
}
