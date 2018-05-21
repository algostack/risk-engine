package io.algostack.mmap;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class DoubleArrayReadMapImpl<K extends Serializable> implements DoubleArrayReadMap<K> {

    private final Map<K, DoubleArray> map;

    public DoubleArrayReadMapImpl(Map<K, DoubleArray> map) {
        this.map = map;
    }

    @Override
    public DoubleArray get(K key) {
        return map.get(key);
    }

    @Override
    public Set<Map.Entry<K, DoubleArray>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Set<K> keys() {
        return map.keySet();
    }

    @Override
    public int size() {
        return map.size();
    }
}
