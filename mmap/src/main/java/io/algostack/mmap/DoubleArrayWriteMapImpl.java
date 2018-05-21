package io.algostack.mmap;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DoubleArrayWriteMapImpl<K extends Serializable> implements DoubleArrayWriteMap<K> {

    private final Map<K, DoubleArray> memCache = new LinkedHashMap<>();

    public DoubleArrayWriteMapImpl() {
    }

    public DoubleArrayWriteMapImpl(Map<K, DoubleArray> map) {
        memCache.putAll(map);
    }

    @Override
    public void put(K key, DoubleArray array) {
        memCache.put(key, array);
    }

    @Override
    public int size() {
        return memCache.size();
    }

    @Override
    public Set<Map.Entry<K, DoubleArray>> entrySet() {
        return memCache.entrySet();
    }

    @Override
    public void save(File file) {
        MMUtils.save(file, this);
    }
}
