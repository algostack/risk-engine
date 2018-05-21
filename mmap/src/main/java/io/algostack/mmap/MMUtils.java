package io.algostack.mmap;

import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.DoubleBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.Map;

public class MMUtils {

    public static <K extends Serializable> DoubleArrayReadMap<K> load(File file) {
        try {

            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            final MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, randomAccessFile.length());
            final int size = mappedByteBuffer.getInt();
            final Map<K, DoubleArray> map = new LinkedHashMap<>();
            for (int i = 0; i < size; i++) {
                final int keySize = mappedByteBuffer.getInt();
                byte[] serializedKey = new byte[keySize];
                mappedByteBuffer.get(serializedKey);
                int arrayLength = mappedByteBuffer.getInt();
                final DoubleBuffer doubleBuffer = mappedByteBuffer.slice().asDoubleBuffer();
                final K key = SerializationUtils.deserialize(serializedKey);
                final MMDoubleArrayImpl mmDoubleArray = new MMDoubleArrayImpl(doubleBuffer, arrayLength);
                map.put(key, mmDoubleArray);
                if (i<size) {
                    mappedByteBuffer.position(mappedByteBuffer.position() + (Double.BYTES * arrayLength));
                }
            }
            return new DoubleArrayReadMapImpl(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <K extends Serializable> void save(File file, DoubleArrayWriteMap<K> map) {

        try (final RandomAccessFile raf = new RandomAccessFile(file, "rw")){
            raf.writeInt(map.size());
            for (Map.Entry<K, DoubleArray> e : map.entrySet()) {
                final K key = e.getKey();
                final DoubleArray array = e.getValue();
                final byte[] headerBytes = SerializationUtils.serialize(key);
                raf.writeInt(headerBytes.length);
                raf.write(headerBytes);
                raf.writeInt(array.size());
                for (int i=0; i<array.size(); i++) {
                    raf.writeDouble(array.get(i));
                }
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }
}
