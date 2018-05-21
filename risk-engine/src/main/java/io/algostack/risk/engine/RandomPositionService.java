package io.algostack.risk.engine;

import com.google.common.collect.ImmutableList;
import io.algostack.risk.model.var.LinearPriceArrayProvider;
import io.algostack.risk.model.domain.cds.Position;
import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.domain.cds.ProductKey;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPositionService implements PositionService {

    private final Random random = new Random();

    private final LinearPriceArrayProvider priceArrayProvider;

    private final Map<String, List<Position<PriceArrayKey>>> positionsMap;

    private final List<PriceArrayKey> priceArrayKeys;

    public RandomPositionService(LinearPriceArrayProvider priceArrayProvider) {
        this.priceArrayProvider = priceArrayProvider;
        this.positionsMap = IntStream.rangeClosed(1, 10)
                .mapToObj(e->"PORTFOLIO" + e)
                .collect(Collectors.toMap(Function.identity(), this::getInitialPositions));
       priceArrayKeys = ImmutableList.copyOf(priceArrayProvider.keySet());
    }

    public List<Position<PriceArrayKey>> getPositions(String portfolio) {
        final List<Position<PriceArrayKey>> positions = positionsMap.get(portfolio);
        final int index = random.nextInt(priceArrayKeys.size());
        final Position<PriceArrayKey> position = new Position<>(priceArrayKeys.get(index), random.nextDouble());
        return ImmutableList.<Position<PriceArrayKey>>builder().addAll(positions).add(position).build();
    }

    public List<Position<PriceArrayKey>> getInitialPositions(String portfolio) {
        final Set<PriceArrayKey> priceArrayKeys = priceArrayProvider.keySet();
        final Map<ProductKey, List<PriceArrayKey>> map = priceArrayKeys.stream()
                .collect(Collectors.groupingBy(PriceArrayKey::getProductKey));
        final List<Position<PriceArrayKey>> simPositions = new ArrayList<>();
        for (Map.Entry<ProductKey, List<PriceArrayKey>> e : map.entrySet()) {
            for (int i = 0; i < 6; i++) {
                final int index = random.nextInt(e.getValue().size());
                final Position<PriceArrayKey> position = new Position<>(e.getValue().get(index), random.nextDouble());
                simPositions.add(position);
            }
        }
        return simPositions;
    }
}
