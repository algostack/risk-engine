package io.algostack.mmap;

public interface DoubleArray {
    double get(int index);
    int size();

    static DoubleArray create(double[] array) {
        return new DoubleArray() {
            @Override public double get(int index) {
                return array[index];
            }

            @Override public int size() {
                return array.length;
            }
        };
    }
}
