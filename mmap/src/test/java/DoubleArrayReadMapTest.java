package io.algostack.mmap;

import org.junit.Test;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DoubleArrayReadMapTest {


    public static class TestKey implements Serializable {
        private final String compA;
        private final String compB;

        public TestKey(String compA, String compB) {
            this.compA = compA;
            this.compB = compB;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof TestKey))
                return false;

            TestKey testKey = (TestKey) o;

            if (!compA.equals(testKey.compA))
                return false;
            return compB.equals(testKey.compB);
        }

        @Override
        public int hashCode() {
            int result = compA.hashCode();
            result = 31 * result + compB.hashCode();
            return result;
        }
    }

    @Test
    public void testMMapRead() {
        final DoubleArrayWriteMap<TestKey> writeMap = DoubleArrayWriteMap.create();
        writeMap.put(new TestKey("A", "B"), DoubleArray.create(new double[]{1.0,2.0,3.0}));
        writeMap.put(new TestKey("A", "C"), DoubleArray.create(new double[]{2.0,3.0,4.0}));
        writeMap.put(new TestKey("A", "D"), DoubleArray.create(new double[]{3.0,4.0,5.0,6.0}));
        final File file = new File("tmp.dat");
        writeMap.save(file);

        final DoubleArrayReadMap<TestKey> readMap = DoubleArrayReadMap.load(file);
        assertThat(readMap.size(), is(3));

        for (Map.Entry<TestKey, DoubleArray> e :writeMap.entrySet()) {
            assertThat(readMap.keys(), hasItem(e.getKey()));
            final DoubleArray actualArray = readMap.get(e.getKey());
            assertThat(e.getValue().size(), is(actualArray.size()));
            for (int i=0; i<actualArray.size(); i++) {
                assertThat(e.getValue().get(i), is(actualArray.get(i)));
            }
        }
    }

}