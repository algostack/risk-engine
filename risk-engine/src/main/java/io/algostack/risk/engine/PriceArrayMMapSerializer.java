package io.algostack.risk.engine;

import com.google.common.base.Stopwatch;
import io.algostack.mmap.DoubleArray;
import io.algostack.mmap.DoubleArrayReadMap;
import io.algostack.mmap.DoubleArrayWriteMap;
import io.algostack.risk.model.domain.cds.PriceArrayKey;
import io.algostack.risk.model.domain.cds.ProductKey;
import io.algostack.risk.model.var.MMPriceArray;
import io.algostack.risk.model.var.PriceArray;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PriceArrayMMapSerializer {


    private static final Logger LOG = Logger.getLogger(PriceArrayMMapSerializer.class.getName());

    public PriceArrayMMapSerializer() {
    }



    public void serialize(File folder, ProductKey productKey,  List<PriceArray> priceArrays) {
        final File file = new File(folder, productKey.toUniqKey() + ".bin");
        final Map<PriceArrayKey, DoubleArray> map = priceArrays.stream().collect(Collectors.toMap(e -> e.getKey(), Function.identity()));
        final DoubleArrayWriteMap<PriceArrayKey> writeMap = DoubleArrayWriteMap.create(map);
        writeMap.save(file);
    }

    public Map<PriceArrayKey, PriceArray> deserialize(File folder) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        final Random random = new Random();
        final File[] files = folder.listFiles((dir, name) -> name.endsWith(".bin"));
        final Map<PriceArrayKey, PriceArray> map = Arrays.stream(files)
                .parallel()
                .map(DoubleArrayReadMap::<PriceArrayKey>load)
                .flatMap(e -> e.entrySet().stream())
                .collect(Collectors.toMap(e -> e.getKey(), e -> new MMPriceArray(e.getKey(), random.nextDouble(), e.getValue())));

        LOG.info("Read price matrices  " +  map.size() + " time: " + stopwatch);
        return map;

    }

}
