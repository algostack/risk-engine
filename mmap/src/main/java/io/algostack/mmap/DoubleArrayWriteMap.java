package io.algostack.mmap;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface DoubleArrayWriteMap<K extends Serializable> {
    void put(K key, DoubleArray doubleArray);
    int size();
    Set<Map.Entry<K, DoubleArray>> entrySet();

    void save(File file);

    static <K extends Serializable> DoubleArrayWriteMap<K> create() {
        return new DoubleArrayWriteMapImpl<>();
    }

    static <K extends Serializable> DoubleArrayWriteMap<K> create(Map<K,DoubleArray> map) {
        return new DoubleArrayWriteMapImpl<>(map);
    }

}
