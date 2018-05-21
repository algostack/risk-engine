package io.algostack.risk.engine;

import com.google.common.collect.ImmutableList;
import io.algostack.risk.model.var.ExpectedShortfallCalculator;
import io.algostack.risk.model.var.PriceArray;
import io.algostack.risk.model.var.ScenConfig;
import io.algostack.risk.model.var.SimplePriceArray;
import io.algostack.risk.model.domain.cds.*;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PriceArrayGenerator {

    private final static  Random random = new Random();

    private final AtomicInteger prodCount = new AtomicInteger(0);

    public static void main(String args[]) {
        new PriceArrayGenerator().generate(args[0]);
    }


    public void generate(String path) {
        final Set<String> entityNames = IntStream.rangeClosed(1, 150).mapToObj(i -> RandomString.upperCaseAlphabets(6))
                .collect(Collectors.toSet());
        final List<ContractualDefinition> cDefs = ImmutableList.of(ContractualDefinition.ISDA_2003, ContractualDefinition.ISDA_2014);
        final List<TransactionType> tTypes = ImmutableList.of(TransactionType.STEC, TransactionType.SEFC, TransactionType.EC, TransactionType.SNAC);
        final List<Integer> coupons = ImmutableList.of(100, 200, 300, 400, 500, 600);
        final LocalDate localDate = LocalDate.of(2018, 05, 16);
        final List<LocalDate> maturities = IntStream.rangeClosed(1, 10)
                .mapToObj(i->localDate.plusYears(i))
                .collect(Collectors.toList());
        final Set<PriceArrayKey> priceArrayKeys = new HashSet<>();
        for (String entity : entityNames) {
            for (ContractualDefinition cDef : cDefs) {
                for (TransactionType tType : tTypes) {
                    final ProductKey productKey = new ProductKey(entity, cDef, Tier.SNRFOR, tType);
                    for (LocalDate maturity : maturities) {
                        for (Integer coupon : coupons) {
                            priceArrayKeys.add(new PriceArrayKey(productKey, coupon, maturity));
                        }
                    }
                }
            }
        }

        final Map<ProductKey, List<PriceArrayKey>> priceArraysByProducts = priceArrayKeys.stream().collect(Collectors.groupingBy(e -> e.getProductKey()));

        priceArraysByProducts.entrySet()
                .parallelStream()
                .forEach(e->genPriceArrays(e.getKey(), e.getValue(), path, priceArraysByProducts.size()));
    }

    private void genPriceArrays(ProductKey prodKey, List<PriceArrayKey> priceArrayKeys, String path, int totalProducts) {
        final List<PriceArray> priceArrays = priceArrayKeys.stream().map(e -> createPriceArray(e, new ScenConfig(2500, 5)))
                .collect(Collectors.toList());
        new PriceArrayMMapSerializer().serialize(new File(path), prodKey, priceArrays);
        System.out.println("Generated price array for " + prodKey + "count: " + prodCount.incrementAndGet() + " of " + totalProducts);
    }

    private SimplePriceArray createPriceArray(PriceArrayKey priceArrayKey, ScenConfig config) {
        final double[] mtms = new double[config.getScenarioDays()];
        for (int i=0; i<mtms.length; i++) {
            mtms[i] = random.nextDouble();
        }
        return new SimplePriceArray(priceArrayKey, random.nextDouble(), mtms);
    }

}