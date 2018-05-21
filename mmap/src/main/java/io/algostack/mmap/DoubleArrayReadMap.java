package io.algostack.mmap;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface DoubleArrayReadMap<K extends Serializable> {
    DoubleArray get(K key);
    Set<Map.Entry<K, DoubleArray>> entrySet();
    Set<K> keys();
    int size();

    static <K extends Serializable> DoubleArrayReadMap<K> load(File file) {
        return MMUtils.load(file);
    }
}
