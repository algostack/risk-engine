package io.algostack.mmap;

import java.nio.DoubleBuffer;

public class MMDoubleArrayImpl implements DoubleArray {

    private final DoubleBuffer buffer;
    private final int size;

    public MMDoubleArrayImpl(DoubleBuffer buffer, int size) {
        this.buffer = buffer;
        this.size = size;
    }

    @Override
    public double get(int index) {
        return buffer.get(index);
    }

    @Override
    public int size() {
        return size;
    }
}
